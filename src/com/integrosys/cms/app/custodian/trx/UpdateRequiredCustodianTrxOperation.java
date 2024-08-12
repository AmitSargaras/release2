package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class UpdateRequiredCustodianTrxOperation extends AbstractCustodianTrxOperation {

	public UpdateRequiredCustodianTrxOperation() {
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
	 * Process the transaction 1.Create staging custodian doc 2.Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICustodianTrxValue trxValue = (ICustodianTrxValue) anITrxValue;
		trxValue = super.updateCustodianDocTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
