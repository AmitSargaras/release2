package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerRejectOperation extends AbstractTrxOperation{

	 public CheckerRejectOperation(){
		 super();
	 }
	  
	public String getOperationName() {
        return ICMSConstant.LimitsOfAuthorityMaster.ACTION_CHECKER_REJECT_LIMITS_OF_AUTHORITY;
    }
	
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ILimitsOfAuthorityMasterTrxValue trxValue = super.getTrxValue(anITrxValue);
        trxValue = super.updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}