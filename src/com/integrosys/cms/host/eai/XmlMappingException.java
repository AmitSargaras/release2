package com.integrosys.cms.host.eai;

/**
 * Base class for exception to be thrown in the XML Object mapping routine
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class XmlMappingException extends EAIMessageException {

	private static final long serialVersionUID = 2044699738650943691L;

	private static final String XML_ERROR_CODE = "XML_ERROR";

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public XmlMappingException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public XmlMappingException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public String getErrorCode() {
		return XML_ERROR_CODE;
	}

}
