/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTrxOperation.java,v 1.29 2006/10/06 02:57:55 lini Exp $
 */
package com.integrosys.cms.app.transaction;

import java.rmi.RemoteException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.AbstractTrxOperation;
import com.integrosys.base.businfra.transaction.EBTrxHistoryHome;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.OBTrxHistoryValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxLogException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.component.common.transaction.ITrxHistoryLogger;
import com.integrosys.component.common.transaction.TrxHistoryLoggerFactory;

/**
 * This class provides common transaction operation services to any transaction
 * operations that extends it, while by itself, it does not contain any relevant
 * transaction services.
 * 
 * @author Alfred Lee
 * @author lini
 * @author Chong Jun Yong
 * @since 2006/10/06
 */
public abstract class CMSTrxOperation extends AbstractTrxOperation implements ITrxRouteOperation {

	private static final long serialVersionUID = 6677563942960609583L;

	public abstract String getOperationName();

	/**
	 * Sets the next route requirements into the ITrxValue. This is the default
	 * implementation
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * This method defines the process that should initialised values that would
	 * be required in the <code>performProcess</code> method
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue object that has been initialised with required values
	 * @throws TrxOperationException on errors
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICMSTrxValue trxValue = getCMSTrxValue(value);
			Date d = DateUtil.getDate();
			trxValue.setTransactionDate(d);
			trxValue.setFromState(trxValue.getStatus());
			trxValue.setPreviousState(trxValue.getStatus());
			trxValue.setOpDesc(getOperationName());

			ITrxContext trxContext = trxValue.getTrxContext();

			if (trxContext != null) {
				String code = trxContext.getTrxCountryOrigin();
				if (StringUtils.isBlank(code)) {
					trxContext.setTrxCountryOrigin(trxValue.getOriginatingCountry());
				}
				code = trxContext.getTrxOrganisationOrigin();
				if (StringUtils.isBlank(code)) {
					trxContext.setTrxOrganisationOrigin(trxValue.getOriginatingOrganisation());
				}
			}

			trxValue = (ICMSTrxValue) mapContextToTrx(trxContext, trxValue);

			return trxValue;
		}
		catch (TransactionException e) {
			throw new TrxOperationException("failed to preprocess trx value; "
					+ constructTrxValueInfoMessage((ICMSTrxValue) value), e);
		}
	}

	/**
	 * Method to map trx context into trx value. Override this method to return
	 * the input parameter as output if there is no desire to map context into
	 * trx value
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue object that has been initialised with required values
	 * @throws TrxOperationException on errors
	 */
	protected ITrxValue mapContextToTrx(ITrxContext context, ITrxValue value) throws TransactionException {
		ICMSTrxValue trxValue = (ICMSTrxValue) TrxOperationHelper.mapTrxContext(context, value);
		return trxValue;
	}

	/**
	 * Construct an ITrxResult by merging some of the value in the orginal trx
	 * value into the new trx value and set the new trx value into the trx
	 * result
	 * @param anOrgTrxValue - ITrxValue
	 * @param aNewTrxValue - ITrxValue
	 * @return ITrxResult - the trx result containing the new trx value
	 */
	public ITrxResult constructITrxResult(ITrxValue anOrgTrxValue, ITrxValue aNewTrxValue) {
		ICMSTrxValue cmsTrxValue = (ICMSTrxValue) aNewTrxValue;
		cmsTrxValue.setPreviousState(anOrgTrxValue.getPreviousState());
		cmsTrxValue.setInstanceName(anOrgTrxValue.getInstanceName());

		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(cmsTrxValue);
		return result;
	}

	/**
	 * Logs the transaction into the transaction log
	 * @param result is of type ITrxResult
	 * @return ITrxResult
	 * @throws TrxLogException on error
	 */
	public ITrxResult logProcess(ITrxResult result) throws TrxLogException {
		ITrxHistoryLogger logger = TrxHistoryLoggerFactory.getLogger();
		OBTrxHistoryValue history = logger.logProcess(result);

		persistLog(history);

		return result;
	}

	/**
	 * Method to persist the transaction log into database. This method is
	 * implemented using EJB instead of DAO for persistence.
	 * 
	 * @param history is the OBTrxHistoryValue object to be persisted
	 * @throws TrxLogException on error
	 */
	protected void persistLog(OBTrxHistoryValue history) throws TrxLogException {
		try {
			getTrxHistoryHome().create(history);
		}
		catch (Throwable t) {
			throw new TrxLogException("failed to create history log; transaction id [" + history.getTransactionId()
					+ "]", t);
		}

	}

	/**
	 * Return the EBTrxHistoryHome object
	 * 
	 * @return EBTrxHistoryHome
	 * @throws TrxOperationException if the EJB Home is null
	 */
	private EBTrxHistoryHome getTrxHistoryHome() throws TrxOperationException {
		EBTrxHistoryHome home = (EBTrxHistoryHome) BeanController.getEJBHome(ICMSJNDIConstant.JNDI_EBTrxHistory,
				ICMSJNDIConstant.HOME_EBTrxHistory);

		if (null == home) {
			throw new TrxOperationException("failed to find trx history home using jndi name ["
					+ ICMSJNDIConstant.JNDI_EBTrxHistory + "]");
		}

		return home;
	}

	/**
	 * To update a transaction
	 * @param trxValue - ICMSTrxValue
	 * @return ICMSTrxValue - the updated transaction
	 * @throws TrxOperationException
	 */
	protected ICMSTrxValue updateTransaction(ICMSTrxValue trxValue) throws TrxOperationException {
		
		DefaultLogger.debug(this, "===============================105-8-1-1=========update============= ");
		SBCMSTrxManager mgr = getTrxManager();

		// store the prev state and instance name for logging purposes
		String prevState = trxValue.getFromState();
		String instanceName = trxValue.getInstanceName();
		ITrxContext trxContext = trxValue.getTrxContext();
		DefaultLogger.debug(this, "===============================105-8-1-2=========update============= ");
		try {
			DefaultLogger.debug(this, "===============================105-8-1-3=========update============= ");
			trxValue = mgr.updateTransaction(trxValue);
			DefaultLogger.debug(this, "===============================105-8-1-4=========update============= ");
			/*
			 * set previous state as current from state, because after perform
			 * process, status/fromState will be == toState
			 */
			trxValue.setPreviousState(prevState);
			trxValue.setInstanceName(instanceName);
			trxValue.setTrxContext(trxContext);
			return trxValue;
		}
		catch (TransactionException te) {
			throw new TrxOperationException("failed to update transaction; " + constructTrxValueInfoMessage(trxValue),
					te);
		}
		catch (RemoteException e) {
			throw new TrxOperationException("failed to remote call on workflow manager [" + mgr.getClass()
					+ "]; transaction" + constructTrxValueInfoMessage(trxValue) + "; throwing root cause", e.getCause());
		}
	}

	/**
	 * To create a transaction
	 * @param trxValue - ICMSTrxValue
	 * @return ICMSTrxValue - the updated transaction
	 * @throws TrxOperationException
	 */
	protected ICMSTrxValue createTransaction(ICMSTrxValue trxValue) throws TrxOperationException {
		SBCMSTrxManager mgr = getTrxManager();

		// store the prev state and instance name for logging purposes
		String prevState = trxValue.getFromState();		
		String instanceName = trxValue.getInstanceName();
		ITrxContext trxContext = trxValue.getTrxContext();

		try {
			trxValue = mgr.createTransaction(trxValue);

			/*
			 * set previous state as current from state, because after perform
			 * process, status/fromState will be == toState
			 */
			trxValue.setPreviousState(prevState);
			trxValue.setInstanceName(instanceName);
			trxValue.setTrxContext(trxContext);

			return trxValue;
		}
		catch (TransactionException te) {
			throw new TrxOperationException("failed to create transaction; " + constructTrxValueInfoMessage(trxValue),
					te);
		}
		catch (RemoteException e) {
			throw new TrxOperationException("failed to remote call on workflow manager [" + mgr.getClass()
					+ "] transaction; " + constructTrxValueInfoMessage(trxValue) + "; throwing root cause", e
					.getCause());
		}
	}

	protected String constructTrxValueInfoMessage(ICMSTrxValue trxValue) {
		StringBuffer buf = new StringBuffer();
		buf.append("transaction id ").append("[").append(trxValue.getTransactionID()).append("]");
		buf.append("reference id ").append("[").append(trxValue.getReferenceID()).append("]");
		buf.append("staging reference id ").append("[").append(trxValue.getStagingReferenceID()).append("]");
		buf.append("transaction type ").append("[").append(trxValue.getTransactionType()).append("]");

		return buf.toString();
	}

	protected SBCMSTrxManager getTrxManager() throws TrxOperationException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());

		if (null == mgr) {
			throw new TrxOperationException("failed to find cms trx manager remote interface using jndi name ["
					+ ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME + "]");
		}
		else {
			return mgr;
		}
	}

	/**
	 * Method to convert a parent class into its child
	 * 
	 * @param value is of type ITrxValue
	 * @return ICMSTrxValue
	 */
	protected ICMSTrxValue getCMSTrxValue(ITrxValue value) throws TrxOperationException {
		return (ICMSTrxValue) value;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(System.identityHashCode(this)).append("; ");
		buf.append("Operation Name: [").append(getOperationName()).append("]");

		return buf.toString();
	}

}