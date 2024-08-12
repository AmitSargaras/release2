package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class CheckerRejectBaselOperation extends AbstractBaselTrxOperation{
	
	public CheckerRejectBaselOperation()
    {
        super(); 
    }
	
	public String getOperationName()
    {
        return ICMSConstant.ACTION_CHECKER_REJECT_BASEL;
    }

    /**
    * Process the transaction
    * 1.    Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
    	IBaselMasterTrxValue trxValue = super.getBaselTrxValue(anITrxValue);
        trxValue = super.updateBaselTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}
