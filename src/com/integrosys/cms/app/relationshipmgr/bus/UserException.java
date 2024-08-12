package com.integrosys.cms.app.relationshipmgr.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class UserException extends OFAException{

	public UserException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public UserException(String msg, Throwable t) {
		super(msg, t);
	}
	
	
}
