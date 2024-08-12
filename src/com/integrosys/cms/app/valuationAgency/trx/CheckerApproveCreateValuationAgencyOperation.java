package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyReplicationUtils;

/**
 * @author rajib.aich Checker approve Operation to approve update made by maker
 */

public class CheckerApproveCreateValuationAgencyOperation extends
		AbstractValuationAgencyTrxOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_VALUATION_AGENCY;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the
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
		try {
			trxValue = createActualValuationAgency(trxValue);
			trxValue = updateValuationAgencyTrx(trxValue);
		} catch (TrxOperationException e) {
			throw new TrxOperationException(e.getMessage());
		} catch (Exception e) {
			throw new TrxOperationException(e.getMessage());
		}

		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual property index
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ICCDocumentLocationTrxValue - the document item trx value
	 * @throws ConcurrentUpdateException
	 * @throws TransactionException
	 * @throws TrxParameterException
	 * @throws ValuationAgencyException
	 */
	private IValuationAgencyTrxValue createActualValuationAgency(
			IValuationAgencyTrxValue idxTrxValue)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		try {
			IValuationAgency staging = idxTrxValue.getStagingValuationAgency();
			IValuationAgency replicatedValuationAgency = ValuationAgencyReplicationUtils
					.replicateValuationAgencyForCreateStagingCopy(staging);
			IValuationAgency actual = getValuationAgencyBusManager()
					.createValuationAgency(replicatedValuationAgency);
			idxTrxValue.setValuationAgency(actual);
			idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
			getValuationAgencyBusManager().updateValuationAgency(actual);
			return idxTrxValue;
		} catch (PropertyIdxException ex) {
			throw new TrxOperationException(ex);
		}
	}
}
