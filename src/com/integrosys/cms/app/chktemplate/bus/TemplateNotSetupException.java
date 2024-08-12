/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/TemplateNotSetupException.java,v 1.1 2003/07/16 09:08:33 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

/**
 * This exception is thrown when there is no template found for a checklist.
 * Should not to have throwable cause constructor, because it is the root cause.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/16 09:08:33 $ Tag: $Name: $
 */
public class TemplateNotSetupException extends CheckListTemplateException {

	private String errorCode;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public TemplateNotSetupException(String msg) {
		super(msg);
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
