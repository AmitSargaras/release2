package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityReplicationUtils;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;

public class MakerSaveUpdateExcludedFacilityOperation extends AbstractExcludedFacilityTrxOperation {

	/**
	    * Defaulc Constructor
	    */
	public MakerSaveUpdateExcludedFacilityOperation() {
		super();
	}
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_EXCLUDED_FACILITY;
	}
	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IExcludedFacilityTrxValue idxTrxValue = getExcludedFacilityTrxValue(anITrxValue);
        IExcludedFacility stage = idxTrxValue.getStagingExcludedFacility();
        IExcludedFacility replicatedExcludedFacility = ExcludedFacilityReplicationUtils.replicateExcludedFacilityForCreateStagingCopy(stage);
     //   replicatedFacilityNewMaster.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingExcludedFacility(replicatedExcludedFacility);

        IExcludedFacilityTrxValue trxValue = createStagingExcludedFacility(idxTrxValue);
        trxValue = updateExcludedFacilityTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
