package com.integrosys.cms.host.eai;

/**
 * Exception to be raised wherever there is mismatch of code description between
 * the message and the local.
 * 
 * @author Chong Jun Yong
 * 
 */
public class StandardCodeStaleStateException extends EAIStandardCodeException {

	private static final long serialVersionUID = -6987329234833165032L;

	private String originalCodeDescription;

	public void setOriginalCodeDescription(String originalCodeDescription) {
		this.originalCodeDescription = originalCodeDescription;
	}

	public String getOriginalCodeDescription() {
		return originalCodeDescription;
	}

	public StandardCodeStaleStateException(StandardCode standardCode, String originalCodeDescription) {
		super("Standard Code [" + standardCode + "]'s code description from the message is not equal with the one ["
				+ originalCodeDescription + "]");
		setStandardCode(standardCode);
		setOriginalCodeDescription(originalCodeDescription);
	}

}
