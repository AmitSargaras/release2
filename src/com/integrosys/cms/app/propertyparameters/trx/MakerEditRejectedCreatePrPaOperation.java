package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:03:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class MakerEditRejectedCreatePrPaOperation extends AbstractProPaTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedCreatePrPaOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_PRPA;
	}

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPrPaTrxValue trxValue = createStagingCCDocumentLocation(getCCDocumentLocationTrxValue(anITrxValue));
		trxValue = updateCCDocumentLocationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
