package com.integrosys.cms.host.stp.castor;

import com.integrosys.cms.host.stp.XmlMappingException;
import org.exolab.castor.xml.XMLException;

/**
 * Exception to be raised when there is failure on marshalling object to xml
 *
 * @author Chong Jun Yong
 *
 */
public class MarshallingFailureException extends XmlMappingException {

	private static final long serialVersionUID = -8170892682655095530L;

	/**
	 * Default Constructor to provide error message
	 *
	 * @param msg error message for this exception
	 */
	public MarshallingFailureException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 *
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown, the XML
	 *        Exception
	 */
	public MarshallingFailureException(String msg, XMLException cause) {
		super(msg, cause);
	}

}