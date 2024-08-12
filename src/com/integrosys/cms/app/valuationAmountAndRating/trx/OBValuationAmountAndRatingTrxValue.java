package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBValuationAmountAndRatingTrxValue extends OBCMSTrxValue implements
IValuationAmountAndRatingTrxValue{

	public OBValuationAmountAndRatingTrxValue() {
	}
	
	IValuationAmountAndRating valuationAmountAndRating;
	IValuationAmountAndRating stagingValuationAmountAndRating;
	
	public OBValuationAmountAndRatingTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}
	
	public IValuationAmountAndRating getValuationAmountAndRating() {
		return valuationAmountAndRating;
	}
	public void setValuationAmountAndRating(IValuationAmountAndRating valuationAmountAndRating) {
		this.valuationAmountAndRating = valuationAmountAndRating;
	}
	public IValuationAmountAndRating getStagingValuationAmountAndRating() {
		return stagingValuationAmountAndRating;
	}
	public void setStagingValuationAmountAndRating(IValuationAmountAndRating stagingValuationAmountAndRating) {
		this.stagingValuationAmountAndRating = stagingValuationAmountAndRating;
	}
}
