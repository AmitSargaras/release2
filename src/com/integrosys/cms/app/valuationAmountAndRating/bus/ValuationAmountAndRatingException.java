package com.integrosys.cms.app.valuationAmountAndRating.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class ValuationAmountAndRatingException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public ValuationAmountAndRatingException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public ValuationAmountAndRatingException(String msg, Throwable t) {
		super(msg, t);
	}
}
