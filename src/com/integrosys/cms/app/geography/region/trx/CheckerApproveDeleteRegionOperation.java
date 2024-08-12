package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.region.bus.IRegion;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class CheckerApproveDeleteRegionOperation extends AbstractRegionTrxOperation{

	/**
	 * Default Constructor
	 */
	public CheckerApproveDeleteRegionOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_REGION;
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
		
		IRegionTrxValue trxValue = getRegionTrxValue(anITrxValue);
		trxValue = updateActualRegion(trxValue);
		trxValue = updateRegionTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IRegionTrxValue updateActualRegion(IRegionTrxValue anIRegionTrxValue)
			throws TrxOperationException {
		try {
			IRegion actual = anIRegionTrxValue.getActualRegion();

			IRegion updatedRegion = getRegionBusManager().deleteRegion(actual);
			anIRegionTrxValue.setActualRegion(updatedRegion);

			return anIRegionTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
