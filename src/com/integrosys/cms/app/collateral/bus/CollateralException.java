/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralException.java,v 1.2 2005/08/05 12:42:11 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * This exception is thrown whenever an error occurs in any processes within the
 * collateral.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/05 12:42:11 $ Tag: $Name: $
 */
public class CollateralException extends OFACheckedException {
	/** Forex error in collateral module. */
	public static final String FX_ERR_CODE = "1";

	private String errorCode;

	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public CollateralException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public CollateralException(String msg, Throwable t) {
		super(msg, t);
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
}