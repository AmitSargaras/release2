package com.integrosys.cms.app.insurancecoveragedtls.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * @author dattatray.thorat
 * Checker approve Operation to approve delete made by maker
 */

public class CheckerApproveDeleteInsuranceCoverageDtlsOperation extends AbstractInsuranceCoverageDtlsTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveDeleteInsuranceCoverageDtlsOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_INSURANCE_COVERAGE_DTLS;
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
		
		IInsuranceCoverageDtlsTrxValue trxValue = getInsuranceCoverageDtlsTrxValue(anITrxValue);
		trxValue = updateActualInsuranceCoverageDtls(trxValue);
		trxValue = updateInsuranceCoverageDtlsTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IInsuranceCoverageDtlsTrxValue updateActualInsuranceCoverageDtls(IInsuranceCoverageDtlsTrxValue anICCInsuranceCoverageDtlsTrxValue)
			throws TrxOperationException {
		try {
			IInsuranceCoverageDtls actual = anICCInsuranceCoverageDtlsTrxValue.getInsuranceCoverageDtls();

			IInsuranceCoverageDtls updatedInsuranceCoverageDtls = getInsuranceCoverageDtlsBusManager().deleteInsuranceCoverageDtls(actual);
			anICCInsuranceCoverageDtlsTrxValue.setInsuranceCoverageDtls(updatedInsuranceCoverageDtls);

			return anICCInsuranceCoverageDtlsTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
