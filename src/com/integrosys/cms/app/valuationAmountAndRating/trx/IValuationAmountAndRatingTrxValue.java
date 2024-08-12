package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IValuationAmountAndRatingTrxValue extends ICMSTrxValue{

	public IValuationAmountAndRating getValuationAmountAndRating();

	public IValuationAmountAndRating getStagingValuationAmountAndRating();

	public void setValuationAmountAndRating(IValuationAmountAndRating value);

	public void setStagingValuationAmountAndRating(IValuationAmountAndRating value);
}
