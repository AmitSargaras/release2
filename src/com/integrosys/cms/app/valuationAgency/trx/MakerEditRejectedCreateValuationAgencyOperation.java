package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyReplicationUtils;

/**
 * @author rajib.aich 
 * Checker approve Operation to approve update made by maker
 */
public class MakerEditRejectedCreateValuationAgencyOperation extends
		AbstractValuationAgencyTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedCreateValuationAgencyOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_VALUATION_AGENCY;
	}

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		IValuationAgencyTrxValue idxTrxValue = getValuationAgencyTrxValue(anITrxValue);
		IValuationAgency stage = idxTrxValue.getStagingValuationAgency();
		IValuationAgency replicatedValuationAgency = ValuationAgencyReplicationUtils
				.replicateValuationAgencyForCreateStagingCopy(stage);
		idxTrxValue.setStagingValuationAgency(replicatedValuationAgency);

		IValuationAgencyTrxValue trxValue = createStagingValuationAgency(idxTrxValue);
		trxValue = updateValuationAgencyTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
