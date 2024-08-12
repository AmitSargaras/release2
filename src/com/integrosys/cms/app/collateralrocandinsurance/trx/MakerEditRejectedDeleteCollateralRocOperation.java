package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.collateralrocandinsurance.bus.OBCollateralRoc;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;

public class MakerEditRejectedDeleteCollateralRocOperation extends AbstractCollateralRocTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedDeleteCollateralRocOperation() {
		super();
	}
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
     public String getOperationName()
     {
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_COLLATERAL_ROC;
     }
     /**
      * Process the transaction
      * 1.    Create Staging record
      * 2.    Update the transaction record
      * @param anITrxValue - ITrxValue
      * @return ITrxResult - the transaction result
      * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
      */
      public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
      {
    	  ICollateralRocTrxValue idxTrxValue = getCollateralRocTrxValue(anITrxValue);
          ICollateralRoc stage = idxTrxValue.getStagingCollateralRoc();
          ICollateralRoc replicatedCollateralRoc = new OBCollateralRoc();
          replicatedCollateralRoc.setId(stage.getId());

          replicatedCollateralRoc.setCollateralCategory(stage.getCollateralCategory());
          replicatedCollateralRoc.setCollateralRocCode(stage.getCollateralRocCode());
          replicatedCollateralRoc.setCollateralRocDescription(stage.getCollateralRocDescription());
          replicatedCollateralRoc.setIrbCategory(stage.getIrbCategory());
          replicatedCollateralRoc.setInsuranceApplicable(stage.getInsuranceApplicable());
          
          replicatedCollateralRoc.setCreateBy(stage.getCreateBy());
          replicatedCollateralRoc.setCreationDate(stage.getCreationDate());
          replicatedCollateralRoc.setDeprecated(stage.getDeprecated());
          replicatedCollateralRoc.setLastUpdateBy(stage.getLastUpdateBy());
          replicatedCollateralRoc.setLastUpdateDate(stage.getLastUpdateDate());
          
          replicatedCollateralRoc.setStatus(stage.getStatus());
          replicatedCollateralRoc.setVersionTime(stage.getVersionTime());
          
          idxTrxValue.setStagingCollateralRoc(replicatedCollateralRoc);

          ICollateralRocTrxValue trxValue = createStagingCollateralRoc(idxTrxValue);
          trxValue = updateCollateralRocTrx(trxValue);
          return super.prepareResult(trxValue);
      }
}
