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
public class MakerCloseUpdateInternalCreditRatingOperation extends AbstractInternalCreditRatingTrxOperation {
	
	private static final long serialVersionUID = 1L;
	
	public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_INTERNAL_CREDIT_RATING;
    }

    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
            IInternalCreditRatingTrxValue trxValue = super.getInternalCreditRatingTrxValue (value);
			
			trxValue.setStagingICRList(trxValue.getActualICRList());

			trxValue = super.createStagingInternalCreditRating (trxValue);

            trxValue = super.updateTransaction(trxValue);

            return super.prepareResult(trxValue);
        }
        catch(TrxOperationException e) {
            throw e;
        }
        catch(Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }

}
