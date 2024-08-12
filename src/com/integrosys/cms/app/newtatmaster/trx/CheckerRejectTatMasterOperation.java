package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class CheckerRejectTatMasterOperation extends AbstractTatMasterTrxOperation{
	
	 public CheckerRejectTatMasterOperation()
	    {
	        super();
	    }

	    /**
	    * Get the operation name of the current operation
	    *
	    * @return String - the operation name of the current operation
	    */
	    public String getOperationName()
	    {
	        return ICMSConstant.ACTION_CHECKER_REJECT_TAT_MASTER;
	    }
	    
	    
	    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	    {
	    	ITatMasterTrxValue trxValue = super.getTatMasterTrxValue(anITrxValue);
	        trxValue = super.updateTatMasterTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }

}
