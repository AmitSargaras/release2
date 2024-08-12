package com.integrosys.cms.app.collateral.bus.valuation;

public class UnknownSecurityTypeException extends ValuationException {
	private String securitySubtype;

	public UnknownSecurityTypeException(String msg, String subtype) {
		super(msg);
		securitySubtype = subtype;
	}

	public String getMessage() {
		return super.getMessage() + "; unknown security type: " + securitySubtype;
	}
	
	public String toString() {
		return getMessage();
	}
}
