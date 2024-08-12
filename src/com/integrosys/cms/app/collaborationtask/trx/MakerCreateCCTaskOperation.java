/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/MakerCreateCCTaskOperation.java,v 1.2 2003/09/20 09:23:07 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending CC task
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/20 09:23:07 $ Tag: $Name: $
 */
public class MakerCreateCCTaskOperation extends AbstractCCTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateCCTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_CC_TASK;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICCTaskTrxValue trxValue = getCCTaskTrxValue(anITrxValue);
		ICCTask staging = trxValue.getStagingCCTask();
		try {
			if (staging != null) {
				ICMSTrxValue parentTrx = null;
				if (staging.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getLimitProfileID()),
							ICMSConstant.INSTANCE_LIMIT_PROFILE);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				else if (staging.getCustomerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getCustomerID()),
							ICMSConstant.INSTANCE_CUSTOMER);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				return trxValue;
			}
			return trxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in preProcess: " + ex.toString());
		}
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCTaskTrxValue trxValue = super.getCCTaskTrxValue(anITrxValue);
		trxValue = createStagingCCTask(trxValue);
		trxValue = createCCTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a CC task transaction
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the CC Task specific transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ICCTaskTrxValue createCCTaskTransaction(ICCTaskTrxValue anICCTaskTrxValue) throws TrxOperationException {
		try {
			anICCTaskTrxValue = prepareTrxValue(anICCTaskTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCTaskTrxValue);
			OBCCTaskTrxValue colTaskTrxValue = new OBCCTaskTrxValue(trxValue);
			colTaskTrxValue.setStagingCCTask(anICCTaskTrxValue.getStagingCCTask());
			colTaskTrxValue.setCCTask(anICCTaskTrxValue.getCCTask());
			return colTaskTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}