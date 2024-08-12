/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ShareSecurityException extends OFAException {
	public ShareSecurityException() {
		super();
	}

	public ShareSecurityException(String pMsg) {
		super(pMsg);
	}

	public ShareSecurityException(Throwable pEx) {
		super(pEx);
	}

	public ShareSecurityException(String pMsg, Throwable pEx) {
		super(pMsg, pEx);
	}
}
