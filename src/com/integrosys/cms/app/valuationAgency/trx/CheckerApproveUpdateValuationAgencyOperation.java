package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;

/**
 * @author rajib.aich Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateValuationAgencyOperation extends
		AbstractValuationAgencyTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateValuationAgencyOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_VALUATION_AGENCY;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		IValuationAgencyTrxValue trxValue = getValuationAgencyTrxValue(anITrxValue);
		trxValue = updateActualValuationAgency(trxValue);
		trxValue = updateValuationAgencyTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IValuationAgencyTrxValue updateActualValuationAgency(
			IValuationAgencyTrxValue anICCValuationAgencyTrxValue)
			throws TrxOperationException {
		try {
			IValuationAgency staging = anICCValuationAgencyTrxValue
					.getStagingValuationAgency();
			IValuationAgency actual = anICCValuationAgencyTrxValue
					.getValuationAgency();

			IValuationAgency updatedValuationAgency = getValuationAgencyBusManager()
					.updateToWorkingCopy(actual, staging);
			anICCValuationAgencyTrxValue
					.setValuationAgency(updatedValuationAgency);

			return anICCValuationAgencyTrxValue;
		} catch (Exception ex) {
			throw new TrxOperationException(ex);
		}

	}
}
