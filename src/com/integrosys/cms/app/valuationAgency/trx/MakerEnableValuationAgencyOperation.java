package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyReplicationUtils;

/**
 * @author rajib.aich Checker approve Operation to approve update made by maker
 */
public class MakerEnableValuationAgencyOperation extends
		AbstractValuationAgencyTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEnableValuationAgencyOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_ENABLE_VALUATION_AGENCY;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * 
	 * @param anITrxValue
	 *            is of type ITrxValue
	 * @return ITrxValue
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IValuationAgencyTrxValue trxValue = getValuationAgencyTrxValue(anITrxValue);
		return trxValue;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException
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
		IValuationAgencyTrxValue trxValue = enableStagingValuationAgency(idxTrxValue);
		trxValue = updateValuationAgencyTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
