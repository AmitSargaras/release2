package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaTraqCodeMaster;

public class MakerEditRejectedDeleteNpaTraqCodeMasterOperation extends AbstractNpaTraqCodeMasterTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedDeleteNpaTraqCodeMasterOperation() {
		super();
	}
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
     public String getOperationName()
     {
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_NPA_TRAQ_CODE_MASTER;
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
    	  INpaTraqCodeMasterTrxValue idxTrxValue = getNpaTraqCodeMasterTrxValue(anITrxValue);
          INpaTraqCodeMaster stage = idxTrxValue.getStagingNpaTraqCodeMaster();
          INpaTraqCodeMaster replicatedNpaTraqCodeMaster= new OBNpaTraqCodeMaster();
          
          replicatedNpaTraqCodeMaster.setNpaTraqCode(stage.getNpaTraqCode());
          replicatedNpaTraqCodeMaster.setSecurityType(stage.getSecurityType());
          replicatedNpaTraqCodeMaster.setSecuritySubType(stage.getSecuritySubType());
          replicatedNpaTraqCodeMaster.setPropertyTypeCodeDesc(stage.getPropertyTypeCodeDesc());
          replicatedNpaTraqCodeMaster.setDeprecated(stage.getDeprecated());
          replicatedNpaTraqCodeMaster.setVersionTime(stage.getVersionTime());
          replicatedNpaTraqCodeMaster.setId(stage.getId());
          replicatedNpaTraqCodeMaster.setStatus(stage.getStatus());
          replicatedNpaTraqCodeMaster.setMasterId(stage.getMasterId());
          replicatedNpaTraqCodeMaster.setVersionTime(stage.getVersionTime());
          replicatedNpaTraqCodeMaster.setCreationDate(stage.getCreationDate());
          replicatedNpaTraqCodeMaster.setCreateBy(stage.getCreateBy());
          replicatedNpaTraqCodeMaster.setLastUpdateDate(stage.getLastUpdateDate());
          replicatedNpaTraqCodeMaster.setLastUpdateBy(stage.getLastUpdateBy());
          replicatedNpaTraqCodeMaster.setCpsId(stage.getCpsId());
          replicatedNpaTraqCodeMaster.setOperationName(stage.getOperationName());
          
          idxTrxValue.setStagingNpaTraqCodeMaster(replicatedNpaTraqCodeMaster);

          INpaTraqCodeMasterTrxValue trxValue = createStagingNpaTraqCodeMaster(idxTrxValue);
          trxValue = updateNpaTraqCodeMasterTrx(trxValue);
          return super.prepareResult(trxValue);
      }
}
