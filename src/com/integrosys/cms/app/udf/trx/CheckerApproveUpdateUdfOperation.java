package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.udf.bus.IUdf;

/**
 * 
 * Checker approve Operation to approve update made by maker
 */
public class CheckerApproveUpdateUdfOperation extends AbstractUdfTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateUdfOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_UDF;
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
		IUdfTrxValue trxValue = getUdfTrxValue(anITrxValue);
		trxValue = updateActualUdf(trxValue);
		trxValue = updateUdfTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IUdfTrxValue updateActualUdf(IUdfTrxValue anICCUdfTrxValue)
			throws TrxOperationException {
		try {
			IUdf staging = anICCUdfTrxValue.getStagingUdf();
			IUdf actual = anICCUdfTrxValue.getUdf();

			IUdf updatedUdf = getUdfBusManager().updateToWorkingCopy(actual, staging);
			anICCUdfTrxValue.setUdf(updatedUdf);

			return anICCUdfTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
