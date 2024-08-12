package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaTraqCodeMaster;

/**
 * @author Santosh.Sonmankar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateNpaTraqCodeMasterOperation extends AbstractNpaTraqCodeMasterTrxOperation{

	 public MakerEditRejectedUpdateNpaTraqCodeMasterOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_NPA_TRAQ_CODE_MASTER;
   }
	
	/**
    * Process the transaction
    * 1.	Create the actual data
    * 2.	Update the transaction record
    *
    * @param anITrxValue of ITrxValue type
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException
    *          if encounters any error during the processing of the transaction
    */
   public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
       INpaTraqCodeMasterTrxValue idxTrxValue = super.getNpaTraqCodeMasterTrxValue(anITrxValue);
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
       trxValue = super.updateNpaTraqCodeMasterTrx(trxValue);
       return super.prepareResult(trxValue);
   }
}
