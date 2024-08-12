/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author priya
 *
 */
public class CheckerRejectUpdateInternalCreditRatingOperation extends AbstractInternalCreditRatingTrxOperation {
	
	private static final long serialVersionUID = 1L;
	
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_REJECT_INTERNAL_CREDIT_RATING;
    }

    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
	        IInternalCreditRatingTrxValue trxValue = super.getInternalCreditRatingTrxValue (value);

	        trxValue = super.updateTransaction (trxValue);

	        return super.prepareResult(trxValue);
	    }
	    catch (TrxOperationException e) {
	        throw e;
	    }
	    catch (Exception e) {
	        throw new TrxOperationException ("Exception caught!", e);
	    }
    }

}
