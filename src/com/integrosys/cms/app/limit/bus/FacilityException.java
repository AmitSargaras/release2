package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Exception to be used for facility loading purpose
 * 
 * @author Chong Jun Yong
 * 
 */
public class FacilityException extends OFAException {
	public FacilityException(String msg) {
		super(msg);
	}

	public FacilityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
