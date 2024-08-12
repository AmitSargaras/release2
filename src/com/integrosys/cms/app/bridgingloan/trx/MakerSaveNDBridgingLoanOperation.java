package com.integrosys.cms.app.bridgingloan.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 16, 2007 Time: 11:01:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class MakerSaveNDBridgingLoanOperation extends AbstractBridgingLoanTrxOperation {
	/**
	 * Default Constructor
	 */
	public MakerSaveNDBridgingLoanOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_BRIDGING_LOAN;
	}

	/**
	 * Process the transaction 1. Create staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IBridgingLoanTrxValue trxValue = createStagingBridgingLoan(getBridgingLoanTrxValue(anITrxValue));
		DefaultLogger.debug(this, "in MakerSaveNDBridgingLoanOperation");
		trxValue = createBridgingLoanTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}