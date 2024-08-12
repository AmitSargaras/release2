package com.integrosys.cms.host.eai;

/**
 * Exception to be thrown when the standard code's category code expected is not
 * same with the standard code to be validated.
 * 
 * @author Chong Jun Yong
 * 
 */
public class StandardCodeCategoryIntegrityException extends EAIStandardCodeException {

	private static final long serialVersionUID = 8660649523719308652L;

	public StandardCodeCategoryIntegrityException(String expectedCategoryCode, StandardCode standardCode) {
		super("Standard Code's Category code expected [" + expectedCategoryCode + "] but ["
				+ standardCode.getStandardCodeNumber() + "] found, standard code supplied [" + standardCode + "]");
		setStandardCode(standardCode);
	}
}
