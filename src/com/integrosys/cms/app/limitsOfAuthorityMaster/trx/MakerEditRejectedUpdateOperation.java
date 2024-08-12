package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.OBLimitsOfAuthorityMaster;

public class MakerEditRejectedUpdateOperation extends AbstractTrxOperation{

	 public MakerEditRejectedUpdateOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_EDIT_REJECTED_UPDATE_LIMITS_OF_AUTHORITY; 
   }
	
   public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
       ILimitsOfAuthorityMasterTrxValue idxTrxValue = super.getTrxValue(anITrxValue);
       ILimitsOfAuthorityMaster stage = idxTrxValue.getStaging();
       ILimitsOfAuthorityMaster replicated= new OBLimitsOfAuthorityMaster();
       
       replicated.setEmployeeGrade(stage.getEmployeeGrade());
	   replicated.setFacilityCamCovenant(stage.getFacilityCamCovenant());
	   replicated.setFdAmount(stage.getFdAmount());
	   replicated.setLimitReleaseAmt(stage.getLimitReleaseAmt());
	   replicated.setTotalSanctionedLimit(stage.getTotalSanctionedLimit());
	   replicated.setPropertyValuation(stage.getPropertyValuation());
	   replicated.setRankingOfSequence(stage.getRankingOfSequence());
	   replicated.setSblcSecurityOmv(stage.getSblcSecurityOmv());
	   replicated.setSegment(stage.getSegment());
	   replicated.setDrawingPower(stage.getDrawingPower());
       replicated.setDeprecated(stage.getDeprecated());
       replicated.setId(stage.getId());
       replicated.setStatus(stage.getStatus());
       replicated.setVersionTime(stage.getVersionTime());
       replicated.setCreationDate(stage.getCreationDate());
       replicated.setCreatedBy(stage.getCreatedBy());
       replicated.setLastUpdateDate(stage.getLastUpdateDate());
       replicated.setLastUpdateBy(stage.getLastUpdateBy());
       
       idxTrxValue.setStaging(replicated);
       ILimitsOfAuthorityMasterTrxValue trxValue = createStaging(idxTrxValue);
       trxValue = super.updateTrx(trxValue);
       return super.prepareResult(trxValue);
   }

}