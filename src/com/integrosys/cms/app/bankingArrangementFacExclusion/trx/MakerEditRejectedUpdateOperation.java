package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.OBBankingArrangementFacExclusion;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerEditRejectedUpdateOperation extends AbstractTrxOperation{

	 public MakerEditRejectedUpdateOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION; 
   }
	
   public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
       IBankingArrangementFacExclusionTrxValue idxTrxValue = super.getTrxValue(anITrxValue);
       IBankingArrangementFacExclusion stage = idxTrxValue.getStaging();
       IBankingArrangementFacExclusion replicated= new OBBankingArrangementFacExclusion();
       
       replicated.setSystem(stage.getSystem());
       replicated.setFacCategory(stage.getFacCategory());
       replicated.setFacName(stage.getFacName());
       replicated.setExcluded(stage.getExcluded());
       replicated.setDeprecated(stage.getDeprecated());
       replicated.setVersionTime(stage.getVersionTime());
       replicated.setId(stage.getId());
       replicated.setStatus(stage.getStatus());
       replicated.setMasterId(stage.getMasterId());
       replicated.setVersionTime(stage.getVersionTime());
       replicated.setCreationDate(stage.getCreationDate());
       replicated.setCreateBy(stage.getCreateBy());
       replicated.setLastUpdateDate(stage.getLastUpdateDate());
       replicated.setLastUpdateBy(stage.getLastUpdateBy());
       replicated.setOperationName(stage.getOperationName());
       
       idxTrxValue.setStaging(replicated);
       IBankingArrangementFacExclusionTrxValue trxValue = createStaging(idxTrxValue);
       trxValue = super.updateTrx(trxValue);
       return super.prepareResult(trxValue);
   }

}