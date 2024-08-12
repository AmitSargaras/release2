package com.integrosys.cms.host.eai;

/**
 * Base class of exception to be thrown when the field value doesn't met the
 * requirement of length or format.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class FieldValueRequirementNotMetException extends MessageFieldException {

	private static final long serialVersionUID = 8611988792588572048L;

	public FieldValueRequirementNotMetException(String msg) {
		super(msg);
	}

}
