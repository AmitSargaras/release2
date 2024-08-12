package com.integrosys.cms.app.discrepency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class CheckerRejectUpdateDiscrepencyOperation extends AbstractDiscrepencyTrxOperation{
	

	 public CheckerRejectUpdateDiscrepencyOperation()
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
	        return ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY;
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
	        IDiscrepencyTrxValue trxValue = super.getDiscrepencyTrxValue(anITrxValue);
	        trxValue = super.updateDiscrepencyTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }
}
