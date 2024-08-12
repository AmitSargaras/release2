/*
 * Created on Apr 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ValuationNotRequiredException extends ValuationException {
	private String subtype;

	public ValuationNotRequiredException(String msg, String subtype) {
		super(msg);
		this.subtype = subtype;
	}
	
	public ValuationNotRequiredException(String msg) {
		super(msg);
	}
	
	public ValuationNotRequiredException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
