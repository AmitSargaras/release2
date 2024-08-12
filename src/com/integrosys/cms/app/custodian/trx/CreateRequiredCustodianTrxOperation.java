package com.integrosys.cms.app.custodian.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class CreateRequiredCustodianTrxOperation extends AbstractCustodianTrxOperation {

	public CreateRequiredCustodianTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_REQUIRED;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * checklist transaction ID to be appended as trx parent ref
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);
		ICustodianTrxValue trxValue = getCustodianTrxValue(value);
		// get checklist id from staging custodian doc
		long checkListID = trxValue.getStagingCustodianDoc().getCheckListID();
		if (ICMSConstant.LONG_INVALID_VALUE == checkListID) {
			throw new TrxOperationException("CheckList ID is undefined in ICustodianDoc: " + checkListID);
		}
		try {
			ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(checkListID),
					ICMSConstant.INSTANCE_CHECKLIST);
			trxValue.setTrxReferenceID(parentTrx.getTransactionID());
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
	 * Process the transaction 1.Create staging custodian doc 2.Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICustodianTrxValue trxValue = super.getCustodianTrxValue(anITrxValue);
		trxValue = super.createStagingCustodianDoc(trxValue);
		if (trxValue.getTransactionID() == null) {
			trxValue = super.createCustodianDocTransaction(trxValue);
		}
		else {
			trxValue = super.updateCustodianDocTransaction(trxValue);
		}
		return super.prepareResult(trxValue);
	}
}
