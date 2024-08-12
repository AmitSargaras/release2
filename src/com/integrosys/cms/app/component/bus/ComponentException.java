package com.integrosys.cms.app.component.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class ComponentException extends OFAException {
	
	public ComponentException(String msg) {
		super(msg);
	}
	
	
	public ComponentException(String msg, Throwable t) {
		super(msg, t);
	}

}
