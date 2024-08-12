package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.country.bus.ICountry;

public class CheckerApproveUpdateCountryOperation extends AbstractCountryTrxOperation{

	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCountryOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY;
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
		
		ICountryTrxValue trxValue = getCountryTrxValue(anITrxValue);
		trxValue = updateActualCountry(trxValue);
		trxValue = updateCountryTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ICountryTrxValue updateActualCountry(ICountryTrxValue anICountryTrxValue)
			throws TrxOperationException {
		try {
			ICountry staging = anICountryTrxValue.getStagingCountry();
			ICountry actual = anICountryTrxValue.getActualCountry();

			ICountry updatedCountry = getCountryBusManager().updateToWorkingCopy(actual, staging);
			anICountryTrxValue.setActualCountry(updatedCountry);

			return anICountryTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
