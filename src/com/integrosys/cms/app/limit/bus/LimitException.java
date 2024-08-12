/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/LimitException.java,v 1.1 2003/07/07 04:57:54 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * Description: This exception represents a generic exception that occurs during
 * the execution of Limit module services.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/07 04:57:54 $ Tag: $Name: $
 */
public class LimitException extends OFACheckedException {

	public static final String ERR_CANNOT_DELETE_LMT_PROFILE = "ERR_CANNOT_DELETE_LMT_PROFILE";

	public static final String ERR_DUPLICATE_AA_NUM = "ERR_DUPLICATE_AA_NUM";

	private String errorCode;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public LimitException(String msg) {
		super(msg);
	}

	/**
	 * Constructor provided throwable cause, and with detailed error message
	 * 
	 * @param cause exception that cause this exception to be thrown
	 * @deprecated consider to use constructor with detailed message and
	 *             throwable cause provided
	 */
	public LimitException(Throwable cause) {
		super("Error raised in limit module", cause);

	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public LimitException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Return this exception message, and also include throwable cause message.
	 */
	public String getMessage() {
		StringBuffer buf = new StringBuffer();

		if ((getErrorCode() != null) && (getErrorCode().length() > 0)) {
			buf.append("#Error Code [").append(getErrorCode()).append("] ");
		}

		buf.append(super.getMessage());

		if (getCause() != null) {
			buf.append("; nested exception is ");
			buf.append(getCause());
		}

		return buf.toString();
	}	
}
