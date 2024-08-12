package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityReplicationUtils;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;

public class MakerEditRejectedCreateExcludedFacilityOperation extends AbstractExcludedFacilityTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedCreateExcludedFacilityOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 *
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_EXCLUDED_FACILITY;
	}
	
	/**
	    * Process the transaction
	    * 1. Create Staging record
	    * 2. Update the transaction record
	    * @param anITrxValue - ITrxValue
	    * @return ITrxResult - the transaction result
	    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
	    */
	    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	    {
	        IExcludedFacilityTrxValue idxTrxValue = getExcludedFacilityTrxValue(anITrxValue);
	        IExcludedFacility stage = idxTrxValue.getStagingExcludedFacility();
	        IExcludedFacility replicatedExcludedFacility = ExcludedFacilityReplicationUtils.replicateExcludedFacilityForCreateStagingCopy(stage);
	        idxTrxValue.setStagingExcludedFacility(replicatedExcludedFacility);

	        IExcludedFacilityTrxValue trxValue = createStagingExcludedFacility(idxTrxValue);
	        trxValue = updateExcludedFacilityTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }
}
