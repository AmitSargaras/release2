package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerEditRejectedCreateOperation extends AbstractTrxOperation{

	 public MakerEditRejectedCreateOperation(){
		 super();
	 }
	  
	public String getOperationName() {
      return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION;
  }
	
  public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
	  
      IBankingArrangementFacExclusionTrxValue idxTrxValue = super.getTrxValue(anITrxValue);
      IBankingArrangementFacExclusionTrxValue trxValue = createStaging(idxTrxValue);
      trxValue = super.updateTrx(trxValue);
      return super.prepareResult(trxValue);
  }
  
}