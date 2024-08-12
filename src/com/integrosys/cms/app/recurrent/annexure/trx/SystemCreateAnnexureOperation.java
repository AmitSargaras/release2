/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCreateRecurrentCheckListOperation.java,v 1.1 2003/07/28 02:17:38 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//java

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.recurrent.trx.OBRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending document item
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 02:17:38 $ Tag: $Name: $
 */
public class SystemCreateAnnexureOperation extends AbstractAnnexureTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCreateAnnexureOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CREATE_ANNEXURE_CHECKLIST;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent checklist transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IRecurrentCheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		IRecurrentCheckList staging = trxValue.getStagingCheckList();
		try {
			if (staging != null) {
				
				ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(
						String.valueOf(staging.getLimitProfileID()), ICMSConstant.INSTANCE_LIMIT_PROFILE);
				trxValue.setTrxReferenceID(parentTrx.getTransactionID());
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
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IRecurrentCheckListTrxValue trxValue = super.getCheckListTrxValue(anITrxValue);
		//trxValue = createStagingCheckList(trxValue);
		trxValue = createCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anICheckListTrxValue - IRecurrentCheckListTrxValue
	 * @return IRecurrentCheckListTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IRecurrentCheckListTrxValue createCheckListTransaction(IRecurrentCheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			anICheckListTrxValue = prepareTrxValue(anICheckListTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICheckListTrxValue);
			OBRecurrentCheckListTrxValue checkListTrxValue = new OBRecurrentCheckListTrxValue(trxValue);
			checkListTrxValue.setStagingCheckList(anICheckListTrxValue.getStagingCheckList());
			checkListTrxValue.setCheckList(anICheckListTrxValue.getCheckList());
			return checkListTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}