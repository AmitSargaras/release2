package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDocumentExpiryInfo;

/**
 * Monitors the document expiry event
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/11/15 10:07:02 $ Tag: $Name: $
 */

public class MonDocumentExpiry extends AbstractMonCommon {

	private static final String EVENT_DOCUMENT_EXPIRY = "EV_DOC_EXP";

	private List monitorDaoList;

	private ICheckListProxyManager checklistProxyManager;

	public List getMonitorDaoList() {
		return monitorDaoList;
	}

	public ICheckListProxyManager getChecklistProxyManager() {
		return checklistProxyManager;
	}

	public void setMonitorDaoList(List monitorDaoList) {
		this.monitorDaoList = monitorDaoList;
	}

	public void setChecklistProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public String getEventName() {
		return EVENT_DOCUMENT_EXPIRY;
	}

	public IMonitorDAO[] getDAOArray() {
		return (IMonitorDAO[]) this.monitorDaoList.toArray(new IMonitorDAO[0]);
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new DocExpiryMonRule();
	}

	/**
	 * This Method constructs the monitor specific rule parameters
	 * @param ruleNum - the bussiness object to be processed by the Rule..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_DOC_EXP");
		param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(), "num_of_days" + "." + ruleNum));
		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * <p>
	 * Override super class which fire notification generation, instead this
	 * expire the document in the results query via jdbc.
	 * <p>
	 * Event Monitor rule and rule parameter is suppressed to be used to check
	 * as the jdbc query has catered it.
	 */
	protected void doProcessResult(final IMonRule rule, final IRuleParam ruleParam, final List processingItemList) {
		if (processingItemList == null || processingItemList.isEmpty()) {
			return;
		}

		getTransactionTemplate().execute(new TransactionCallback() {

			public Object doInTransaction(TransactionStatus status) {
				for (Iterator itr = processingItemList.iterator(); itr.hasNext();) {
					OBDocumentExpiryInfo docExpiryInfo = (OBDocumentExpiryInfo) itr.next();

					try {
						getChecklistProxyManager().expireCheckListItem(docExpiryInfo.getCheckListItemID(),
								docExpiryInfo.getCheckListItemRef());
					}
					catch (CheckListException ex) {
						logger.warn("failed to expire checklist item, staging checklist item id ["
								+ docExpiryInfo.getCheckListItemID() + "], checklist item ref ["
								+ docExpiryInfo.getCheckListItemRef() + "], proceed to next item.", ex);
					}
				}
				return null;
			}
		});

	}
}
