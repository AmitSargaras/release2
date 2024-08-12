package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerCloseRejectedUpdateTatMasterOperation extends AbstractTatMasterTrxOperation{

	
	 public MakerCloseRejectedUpdateTatMasterOperation()
	    {
	        super();
	    }
	 
	 public String getOperationName()
	    {
	        return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_TAT_MASTER;
	    }
	 
	 
	 public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	    {
		 ITatMasterTrxValue trxValue = super.getTatMasterTrxValue(anITrxValue);
	        trxValue = updateTatMasterTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }
}
