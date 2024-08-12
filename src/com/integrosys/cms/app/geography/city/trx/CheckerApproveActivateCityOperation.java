package com.integrosys.cms.app.geography.city.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICity;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class CheckerApproveActivateCityOperation extends AbstractCityTrxOperation{

	/**
	 * Default Constructor
	 */
	public CheckerApproveActivateCityOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_ACTIVATE_CITY;
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
		
		ICityTrxValue trxValue = getCityTrxValue(anITrxValue);
		trxValue = updateActualCity(trxValue);
		trxValue = updateCityTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ICityTrxValue updateActualCity(ICityTrxValue anICityTrxValue)
			throws TrxOperationException {
		try {
			ICity staging = anICityTrxValue.getStagingCity();
			ICity actual = anICityTrxValue.getActualCity();

			ICity updatedCity = getCityBusManager().updateToWorkingCopy(actual, staging);
			anICityTrxValue.setActualCity(updatedCity);

			return anICityTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
