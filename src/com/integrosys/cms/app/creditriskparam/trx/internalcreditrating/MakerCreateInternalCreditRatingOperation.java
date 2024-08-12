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
public class MakerCreateInternalCreditRatingOperation extends AbstractInternalCreditRatingTrxOperation {

	private static final long serialVersionUID = 1L;

    public MakerCreateInternalCreditRatingOperation() {
    	
    }

    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_INTERNAL_CREDIT_RATING;
    }


    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        try {
        	IInternalCreditRatingTrxValue trxValue = (IInternalCreditRatingTrxValue)(value);

            trxValue = createStagingInternalCreditRating(trxValue);

			if (trxValue.getStatus().equals (ICMSConstant.STATE_ND))
			    trxValue = super.createTransaction (trxValue);
		    else
				trxValue = super.updateTransaction(trxValue);

            return prepareResult(trxValue);

        } catch (Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }
}
