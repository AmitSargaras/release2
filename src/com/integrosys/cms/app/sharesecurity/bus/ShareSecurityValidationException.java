package com.integrosys.cms.app.sharesecurity.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class ShareSecurityValidationException extends OFAException {
	public ShareSecurityValidationException() {
		super();
	}

	public ShareSecurityValidationException(String pMsg) {
		super(pMsg);
	}

	public ShareSecurityValidationException(Throwable pEx) {
		super(pEx);
	}

	public ShareSecurityValidationException(String pMsg, Throwable pEx) {
		super(pMsg, pEx);
	}
}
