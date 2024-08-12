package com.integrosys.cms.app.bridgingloan.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public class MakerUpdateBridgingLoanOperation extends AbstractBridgingLoanTrxOperation {

	/**
	 * Default Constructor
	 */
	public MakerUpdateBridgingLoanOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_BRIDGING_LOAN;
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
		DefaultLogger.debug(this, ">>>>>>> in MakerUpdateBridgingLoanOperation.performProcess");
		IBridgingLoanTrxValue trxValue = createStagingBridgingLoan(getBridgingLoanTrxValue(anITrxValue));
		DefaultLogger.debug(this, ">>>>>>> created staging");
		trxValue = updateBridgingLoanTransaction(trxValue);
		DefaultLogger.debug(this, ">>>>>>> updated bridging loan transaction");
		return super.prepareResult(trxValue);
	}

}
