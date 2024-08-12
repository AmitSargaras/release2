package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerRejectOperation extends AbstractTrxOperation{

	 public CheckerRejectOperation(){
		 super();
	 }
	  
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_REJECT_BANKING_ARRANGEMENT_FAC_EXCLUSION;
    }
	
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IBankingArrangementFacExclusionTrxValue trxValue = super.getTrxValue(anITrxValue);
        trxValue = super.updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}