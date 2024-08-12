package com.integrosys.cms.host.eai.castor;

import org.exolab.castor.mapping.MappingException;

import com.integrosys.cms.host.eai.XmlMappingException;

/**
 * Exception to be raised when there is any error loading the castor xml mapping
 * file
 * 
 * @author Chong Jun Yong
 * 
 */
public class MappingMalformedException extends XmlMappingException {

	private static final long serialVersionUID = 3864919170460949800L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public MappingMalformedException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown, the
	 *        Mapping Exception
	 */
	public MappingMalformedException(String msg, MappingException cause) {
		super(msg, cause);
	}

}
