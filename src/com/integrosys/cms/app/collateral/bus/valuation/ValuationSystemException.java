package com.integrosys.cms.app.collateral.bus.valuation;

/**
 * @author Administrator
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ValuationSystemException extends ValuationException {
	private String subtype;

	public ValuationSystemException(String msg, String subtype) {
		super(msg);
		this.subtype = subtype;
	}

	public ValuationSystemException(String msg) {
		super(msg);
	}

	public ValuationSystemException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
