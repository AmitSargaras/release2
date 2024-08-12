package com.integrosys.cms.host.eai;

/**
 * Exception to be raised when the common code is not found in the system
 * 
 * @author Chong Jun Yong
 * 
 */
public class StandardCodeNotFoundException extends EAIStandardCodeException {

	private static final long serialVersionUID = 1232197614369909866L;

	private static String errorCode = "SC_NOT_FOUND";

	/**
	 * Default Constructor to supplied the standard code in the message which is
	 * not found in the system
	 * 
	 * @param standardCode the standard code in the message
	 */
	public StandardCodeNotFoundException(StandardCode standardCode) {
		super("Standard Code [" + standardCode + "] not found in the system.");
		setStandardCode(standardCode);
	}

	public String getErrorCode() {
		return errorCode;
	}

}