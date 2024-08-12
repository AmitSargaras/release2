package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.trx.AbstractSegmentWiseEmailTrxOperation;
import com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue;

public class CheckerApproveUpdateSegmentWiseEmailOperation extends AbstractSegmentWiseEmailTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateSegmentWiseEmailOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_SEGMENT_WISE_EMAIL;
    }
	
	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISegmentWiseEmailTrxValue trxValue = getSegmentWiseEmailTrxValue(anITrxValue);
		trxValue = updateActualSegmentWiseEmail(trxValue);
		trxValue = updateSegmentWiseEmailTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ISegmentWiseEmailTrxValue updateActualSegmentWiseEmail(ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue)
			throws TrxOperationException {
		try {
			ISegmentWiseEmail staging = anICCSegmentWiseEmailTrxValue.getStagingSegmentWiseEmail();
			ISegmentWiseEmail actual = anICCSegmentWiseEmailTrxValue.getSegmentWiseEmail();

			ISegmentWiseEmail updatedProductMaster = getSegmentWiseEmailBusManager().updateToWorkingCopy(actual, staging);
			anICCSegmentWiseEmailTrxValue.setSegmentWiseEmail(updatedProductMaster);

			return anICCSegmentWiseEmailTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
