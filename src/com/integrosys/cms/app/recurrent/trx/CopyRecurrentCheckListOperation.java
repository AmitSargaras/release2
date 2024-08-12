/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CopyRecurrentCheckListOperation.java,v 1.1 2003/08/25 12:26:35 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

import java.rmi.RemoteException;

/**
 * This operation creates a checklist into active state directly
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 12:26:35 $ Tag: $Name: $
 */
public class CopyRecurrentCheckListOperation extends MakerCreateRecurrentCheckListOperation {
	/**
	 * Defaulc Constructor
	 */
	public CopyRecurrentCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_COPY_RECURRENT_CHECKLIST;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent checklist transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		return super.preProcess(anITrxValue);
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
		trxValue = createStagingCheckList(trxValue);
		trxValue = createActualCheckList(trxValue);
		trxValue = createCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anIRecurrentCheckListTrxValue - IRecurrentCheckListTrxValue
	 * @return IRecurrentCheckListTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IRecurrentCheckListTrxValue createCheckListTransaction(
			IRecurrentCheckListTrxValue anIRecurrentCheckListTrxValue) throws TrxOperationException {
		try {
			anIRecurrentCheckListTrxValue = prepareTrxValue(anIRecurrentCheckListTrxValue);
			ICMSTrxValue trxValue = createTransaction(anIRecurrentCheckListTrxValue);
			OBRecurrentCheckListTrxValue checkListTrxValue = new OBRecurrentCheckListTrxValue(trxValue);
			checkListTrxValue.setStagingCheckList(anIRecurrentCheckListTrxValue.getStagingCheckList());
			checkListTrxValue.setCheckList(anIRecurrentCheckListTrxValue.getCheckList());
			return checkListTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Create the actual document item
	 * @param anIRecurrentCheckListTrxValue - ITrxValue
	 * @return IRecurrentCheckListTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IRecurrentCheckListTrxValue createActualCheckList(IRecurrentCheckListTrxValue anIRecurrentCheckListTrxValue)
			throws TrxOperationException {
		try {
			IRecurrentCheckList checkList = anIRecurrentCheckListTrxValue.getStagingCheckList();
			IRecurrentCheckList actualCheckList = getRecurrentBusManager().create(checkList);
			anIRecurrentCheckListTrxValue.setCheckList(actualCheckList);
			anIRecurrentCheckListTrxValue.setReferenceID(String.valueOf(actualCheckList.getCheckListID()));
			return anIRecurrentCheckListTrxValue;
		}
		catch (RecurrentException cex) {
			throw new TrxOperationException(cex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCheckList(): " + ex.toString());
		}
	}
}