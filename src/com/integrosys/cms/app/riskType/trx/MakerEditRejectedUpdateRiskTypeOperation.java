package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.OBRiskType;

/**
 * @author Santosh.Sonmankar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateRiskTypeOperation extends AbstractRiskTypeTrxOperation{

	 public MakerEditRejectedUpdateRiskTypeOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_RISK_TYPE; 
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
       IRiskTypeTrxValue idxTrxValue = super.getRiskTypeTrxValue(anITrxValue);
       IRiskType stage = idxTrxValue.getStagingRiskType();
       IRiskType replicatedRiskType= new OBRiskType();
       
       replicatedRiskType.setRiskTypeName(stage.getRiskTypeName());
       replicatedRiskType.setRiskTypeCode(stage.getRiskTypeCode());
      
       replicatedRiskType.setDeprecated(stage.getDeprecated());
       replicatedRiskType.setVersionTime(stage.getVersionTime());
       replicatedRiskType.setId(stage.getId());
       replicatedRiskType.setStatus(stage.getStatus());
       replicatedRiskType.setMasterId(stage.getMasterId());
       replicatedRiskType.setVersionTime(stage.getVersionTime());
       replicatedRiskType.setCreationDate(stage.getCreationDate());
       replicatedRiskType.setCreateBy(stage.getCreateBy());
       replicatedRiskType.setLastUpdateDate(stage.getLastUpdateDate());
       replicatedRiskType.setLastUpdateBy(stage.getLastUpdateBy());
//       replicatedRiskType.setCpsId(stage.getCpsId());
       replicatedRiskType.setOperationName(stage.getOperationName());
       
       idxTrxValue.setStagingRiskType(replicatedRiskType);
       IRiskTypeTrxValue trxValue = createStagingRiskType(idxTrxValue);
       trxValue = super.updateRiskTypeTrx(trxValue);
       return super.prepareResult(trxValue);
   }
}
