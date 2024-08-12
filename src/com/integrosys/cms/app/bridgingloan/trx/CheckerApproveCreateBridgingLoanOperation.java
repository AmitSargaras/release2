package com.integrosys.cms.app.bridgingloan.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 16, 2007 Time: 11:07:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckerApproveCreateBridgingLoanOperation extends AbstractBridgingLoanTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateBridgingLoanOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_BRIDGING_LOAN;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IBridgingLoanTrxValue trxValue = getBridgingLoanTrxValue(anITrxValue);
		trxValue = createActualBridgingLoan(trxValue);
		trxValue = super.updateBridgingLoanTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}