/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.event.EventManager;
import com.integrosys.base.techinfra.event.IEventListener;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.enabledisableuser.MonEnableDisableUser;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.InvalidParameterBatchJobException;
import com.integrosys.cms.batch.factory.TransactionControlledBatchJob;

/**
 * This Abstract class protocols the operations to be performed by the event
 * monitors..
 * 
 * @author hmbao
 * @author Chong Jun Yong
 * @since 2006/10/09 06:11:38
 */
public abstract class AbstractMonCommon implements IMonitor, IMonitorConstant, TransactionControlledBatchJob {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private TransactionTemplate transactionTemplate;

	private List eventListenersList;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setEventListenersList(List eventListenersList) {
		this.eventListenersList = eventListenersList;
	}

	protected TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void execute(Map context) throws BatchJobException {
		String country = (String) context.get("country");
		if (!(this instanceof MonEnableDisableUser) && country == null) {
			throw new InvalidParameterBatchJobException(
					"missing 'country' parameter required for event monitor batch job.");
		}
		start(country, null);
	}

	/**
	 * Return the array of jdbc dao required to be invoked to generate the item
	 * we need to notify against.
	 * 
	 * @return array of jdbc dao
	 */
	protected abstract IMonitorDAO[] getDAOArray();

	/**
	 * Return the event monitor rule required to check against the items
	 * generated from jdbc dao, which in turn setting the flag whether to
	 * generated notification
	 * 
	 * @return the event monitor rule use for the event monitor.
	 */
	protected abstract IMonRule getTriggerRule();

	/**
	 * Return the event name of the event monitor, required to retrieve
	 * notification information from the properties file.
	 * 
	 * @return the event name for this event monitor
	 */
	public abstract String getEventName();

	/**
	 * To construct the rule parameter required to evaluate the item generated
	 * from the jdbc dao, normally some others parameters, eg percentage to
	 * check against, this supposed to retrieve from the properties file.
	 */
	public abstract IRuleParam constructRuleParam(int ruleNum) throws EventMonitorException;

	public void start(final String ctyCode, SessionContext context) throws EventMonitorException {
		int commitRecordsCount = getCommitRecordsCount();
		try {
			logger.debug("- country: " + ctyCode);

			final int count = getSelectionFilterCount();
			for (int index = 0; index < count; index++) {
				final int ruleIndex = index;

				Map objectMap = (Map) getTransactionTemplate().execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						IRuleParam ruleParam = getRuleParam(ctyCode, count, ruleIndex);
						IMonitorDAOResult monitorDaoResult = null;
						try {
							monitorDaoResult = getFilteredIterator(ruleIndex, ruleParam);
						}
						catch (InterruptedException e) {
							status.setRollbackOnly();
						}

						Map objectMap = new HashMap();
						objectMap.put("ruleParam", ruleParam);
						objectMap.put("monitorDaoResult", monitorDaoResult);
						return objectMap;
					}
				});

				processResult(commitRecordsCount, (IMonitorDAOResult) objectMap.get("monitorDaoResult"),
						(IRuleParam) objectMap.get("ruleParam"));
			}
		}
		catch (Throwable t) {
			throw new IncompleteBatchJobException("Failed to complete event monitor batch job", t);
		}
	}

	/**
	 * <p>
	 * Process result retrieved from query persistent storage using jdbc.
	 * <p>
	 * Result will be processd in a sum of commit records batch one batch, each
	 * batch will be processed further in a single transaction.
	 * 
	 * @param commitRecordsCount the commit records count for a batch to be
	 *        processed further in a single transaction
	 * @param daoResult result query via jdbc
	 * @param ruleParam the rule parameter to be used to check against the each
	 *        result item, together with the event monitor rule
	 */
	protected void processResult(int commitRecordsCount, IMonitorDAOResult daoResult, IRuleParam ruleParam) {

		IMonRule rule = getTriggerRule();
		if (rule == null) {
			logger.debug("No rules defined, skip process send notification");
			return;
		}

		logger.debug("[START]\t Notification for event [" + getEventName() + "] at " + DateUtil.getDate());

		List processItemList = new ArrayList();
		while (daoResult.hasNextElement()) {
			processItemList.add(daoResult.getElement());

			if (processItemList.size() == commitRecordsCount) {
				doProcessResult(rule, ruleParam, processItemList);
				processItemList.clear();
			}
		}

		// process last piece of records which doesn't match commit records
		// count
		if (!processItemList.isEmpty()) {
			doProcessResult(rule, ruleParam, processItemList);
		}

		logger.debug("[END]\t Notification for event [" + getEventName() + "] at " + DateUtil.getDate());

	}

	/**
	 * <p>
	 * For a list of result query from jdbc, process the items in a single
	 * transaction.
	 * <p>
	 * This default implementation will fire to generate notification,
	 * subclasses to override it to others such as update certain fields of the
	 * object.
	 * 
	 * @param rule event monitor rule to be checked against the result item
	 *        together with rule parameter
	 * @param ruleParam rule parameter used together with event monitor rule.
	 * @param processingItemList a list of result item query via jdbc
	 */
	protected void doProcessResult(final IMonRule rule, final IRuleParam ruleParam, final List processingItemList) {

		getTransactionTemplate().execute(new TransactionCallback() {

			public Object doInTransaction(TransactionStatus status) {
				for (Iterator itr = processingItemList.iterator(); itr.hasNext();) {
					Object item = itr.next();

					String returnCode = evaluateTriggerRule(rule, ruleParam, item);

					logger.info("item to be checked against [" + item + "], rule result [" + returnCode
							+ "] using rule [" + rule.getClass().getName() + "]");

					if (!returnCode.equals(EVENT_NONE)) {
						processEvent(returnCode, item, ruleParam);
					}
				}

				return null;
			}
		});

	}

	/**
	 * Returns a number to indicate the number of selection filters implemented
	 * in the subclass Selection filter refers to the DAOs that gets the initial
	 * result set for selection This can only be used if the IMonitorDAOResult
	 * shares the same class.
	 */
	protected int getSelectionFilterCount() {
		return getDAOArray().length;
	}

	/**
	 * To evaluate the item generated from the jdbc dao using the event monitor
	 * rule provided by subclasses
	 */
	public String evaluateTriggerRule(IMonRule rule, IRuleParam ruleParam, Object o) throws EventMonitorException {
		return rule.evaluateRule(ruleParam, o);
	}

	/**
	 * This method will process the event monitored based on the protocaled
	 * codes and Bussiness OB passed in.
	 */
	private void processEvent(String code, Object o, IRuleParam ruleParam) throws EventMonitorException {
		try {
			List params = new ArrayList();
			params.add(o);
			params.add(code);
			params.add(ruleParam);

			// fall back to Event Manager mechanism if event listener not
			// available
			if (this.eventListenersList == null || this.eventListenersList.isEmpty()) {
				EventManager.getInstance().fireEvent(getEventName(), params);
			}

			for (Iterator itr = this.eventListenersList.iterator(); itr.hasNext();) {
				IEventListener eventListener = (IEventListener) itr.next();
				eventListener.fireEvent(getEventName(), params);
			}
		}
		catch (EventHandlingException e) {
			throw new EventMonitorException("failed to fire event for [" + getEventName() + "]", e);
		}
	}

	private int getCommitRecordsCount() {
		String strCommitloops = PropertyManager.getValue(ICMSConstant.BATCH_TRX_COMMIT_LOOPS_PROP);
		int commitRecordsCount = 0;
		try {
			commitRecordsCount = Integer.parseInt(strCommitloops);
		}
		catch (Exception e) {
			commitRecordsCount = 10;
			logger.debug("Set commit loops to 10 ");
		}
		return commitRecordsCount;
	}

	private IRuleParam getRuleParam(String ctyCode, int count, int index) throws EventMonitorException {
		IRuleParam ruleParam = constructRuleParam(index);
		ruleParam.setCountryCode(ctyCode);

		if (count != 1) {
			ruleParam.setRuleNum(index);
		}
		else {
			ruleParam.setRuleNum(-1);
		}
		logger.debug("rule parameters are " + AccessorUtil.printMethodValue(ruleParam));
		return ruleParam;
	}

	private IMonitorDAOResult getFilteredIterator(int index, IRuleParam ruleParam) throws InterruptedException {
		logger.debug("[START]\t MonitorDAO for event [" + getEventName() + "] at " + DateUtil.getDate());
		IMonitorDAOResult firstFilteredIterator = null;
		try {
			firstFilteredIterator = getSelectionForFilter(ruleParam, index);
		}
		catch (Exception ex) {
			logger.warn(" - retry due to error!", ex);
			Thread.sleep(30000);
			try {
				firstFilteredIterator = getSelectionForFilter(ruleParam, index);
			}
			catch (Exception iex) {
				logger.error(" - error happened !", iex);
			}
		}
		logger.debug("[END]\t MonitorDAO for event [" + getEventName() + "] at " + DateUtil.getDate());

		return firstFilteredIterator;
	}

	/**
	 * This method will create appropriate search criteria and invokes a module
	 * specific DAO process the search result and returns a filtered list of
	 * bussiness ob for further process..
	 * 
	 * @param ruleParam the rule parameter object
	 * @param index the index to the DAO to use to get the selection
	 * @return IMonitorDAOResult a result wrapper
	 */
	public IMonitorDAOResult getSelectionForFilter(IRuleParam ruleParam, int index) throws EventMonitorException {
		IMonitorDAO dao = getDAOArray()[index];
		return dao.getInitialSet(ruleParam);
	}
}
