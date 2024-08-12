/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating;

import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.InternalCreditRatingException;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author priya
 *
 */
public interface IInternalCreditRatingProxy {
	
    public IInternalCreditRatingTrxValue getInternalCreditRatingTrxValue (ITrxContext ctx) throws InternalCreditRatingException;

    public IInternalCreditRatingTrxValue getInternalCreditRatingTrxValueByTrxID (ITrxContext ctx, String trxID) throws InternalCreditRatingException;

    public IInternalCreditRatingTrxValue makerUpdateInternalCreditRating (ITrxContext ctx,IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException;

    public IInternalCreditRatingTrxValue makerCloseInternalCreditRating (ITrxContext ctx,IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException;

    public IInternalCreditRatingTrxValue checkerApproveUpdateInternalCreditRating (ITrxContext ctx, IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException;

    public IInternalCreditRatingTrxValue checkerRejectUpdateInternalCreditRating (ITrxContext ctx, IInternalCreditRatingTrxValue trxVal) throws InternalCreditRatingException;
	
}
