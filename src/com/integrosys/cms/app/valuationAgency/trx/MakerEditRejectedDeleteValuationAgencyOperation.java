package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency;

/**
 * @author rajib.aich Checker approve Operation to approve update made by maker
 */
public class MakerEditRejectedDeleteValuationAgencyOperation extends
		AbstractValuationAgencyTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedDeleteValuationAgencyOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_VALUATION_AGENCY;
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
		IValuationAgency replicatedValuationAgency = new OBValuationAgency();
		replicatedValuationAgency.setId(stage.getId());
		replicatedValuationAgency.setAddress(stage.getAddress());
		replicatedValuationAgency.setCityTown(stage.getCityTown());
		replicatedValuationAgency.setCountry(stage.getCountry());
		replicatedValuationAgency.setCreateBy(stage.getCreateBy());
		replicatedValuationAgency.setCreationDate(stage.getCreationDate());
		replicatedValuationAgency.setDeprecated(stage.getDeprecated());
		replicatedValuationAgency.setLastUpdateBy(stage.getLastUpdateBy());
		replicatedValuationAgency.setLastUpdateDate(stage.getLastUpdateDate());
		replicatedValuationAgency.setMasterId(stage.getMasterId());
		replicatedValuationAgency.setRegion(stage.getRegion());
		replicatedValuationAgency.setState(stage.getState());
		replicatedValuationAgency.setStatus(stage.getStatus());
		replicatedValuationAgency.setValuationAgencyCode(stage
				.getValuationAgencyCode());
		replicatedValuationAgency.setValuationAgencyName(stage
				.getValuationAgencyName());
		replicatedValuationAgency.setVersionTime(stage.getVersionTime());

		idxTrxValue.setStagingValuationAgency(replicatedValuationAgency);

		IValuationAgencyTrxValue trxValue = createStagingValuationAgency(idxTrxValue);
		trxValue = updateValuationAgencyTrx(trxValue);
		return super.prepareResult(trxValue);
	}

}
