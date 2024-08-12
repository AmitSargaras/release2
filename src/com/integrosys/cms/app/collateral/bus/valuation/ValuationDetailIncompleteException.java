package com.integrosys.cms.app.collateral.bus.valuation;

import java.util.Iterator;
import java.util.List;

public class ValuationDetailIncompleteException extends ValuationException {
	private List errorCodes;

	public ValuationDetailIncompleteException(String msg) {
		super(msg);
	}

	public ValuationDetailIncompleteException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ValuationDetailIncompleteException(String msg, List errorCodes) {
		super(msg);
		this.errorCodes = errorCodes;
	}

	public ValuationDetailIncompleteException(List errorCodes) {
		super("empty message");
		this.errorCodes = errorCodes;
	}

	/**
	 * @return Returns the errorCodes.
	 */
	public List getErrorCodes() {
		return errorCodes;
	}

	public String getMessage() {
		StringBuffer errorMessageBuf = new StringBuffer();
		errorMessageBuf.append(super.getMessage()).append("; ");

		if (errorCodes != null && !errorCodes.isEmpty()) {
			for (Iterator itr = errorCodes.iterator(); itr.hasNext();) {
				errorMessageBuf.append((String) itr.next()).append("; ");
			}
		}

		return errorMessageBuf.toString();
	}

	public String toString() {
		return getMessage();
	}
}
