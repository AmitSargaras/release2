package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.excLineforstpsrm.bus.OBExcLineForSTPSRM;

public class MakerEditRejectedUpdateOperation extends AbstractTrxOperation{

	 public MakerEditRejectedUpdateOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_EXC_LINE_FR_STP_SRM;
   }
	
   public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
       IExcLineForSTPSRMTrxValue idxTrxValue = super.getTrxValue(anITrxValue);
       IExcLineForSTPSRM stage = idxTrxValue.getStaging();
       IExcLineForSTPSRM replicated= new OBExcLineForSTPSRM();
       
       replicated.setLineCode(stage.getLineCode());
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
       IExcLineForSTPSRMTrxValue trxValue = createStaging(idxTrxValue);
       trxValue = super.updateTrx(trxValue);
       return super.prepareResult(trxValue);
   }

}