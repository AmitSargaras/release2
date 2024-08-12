package com.integrosys.cms.app.collateral.bus.valuation;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Base exception for valuation package.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class ValuationException extends OFAException {
	/**
	 * Default constructor supplied the error message that for this exception
	 * 
	 * @param msg error message description
	 */
	public ValuationException(String msg) {
		super(msg);
	}

	/**
	 * Default constructor supplied error message and cause that throw this
	 * exception
	 * 
	 * @param msg error message description
	 * @param cause the throwable that cause this exception
	 */
	public ValuationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
