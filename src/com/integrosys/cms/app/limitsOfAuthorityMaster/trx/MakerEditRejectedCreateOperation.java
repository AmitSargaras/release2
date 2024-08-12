package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerEditRejectedCreateOperation extends AbstractTrxOperation{

	 public MakerEditRejectedCreateOperation(){
		 super();
	 }
	  
	public String getOperationName() {
      return ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_EDIT_REJECTED_CREATE_LIMITS_OF_AUTHORITY;
  }
	
  public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
	  
      ILimitsOfAuthorityMasterTrxValue idxTrxValue = super.getTrxValue(anITrxValue);
      ILimitsOfAuthorityMasterTrxValue trxValue = createStaging(idxTrxValue);
      trxValue = super.updateTrx(trxValue);
      return super.prepareResult(trxValue);
  }
  
}