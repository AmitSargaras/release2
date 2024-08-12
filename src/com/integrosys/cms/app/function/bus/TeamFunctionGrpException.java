package com.integrosys.cms.app.function.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class TeamFunctionGrpException extends OFAException {
	public TeamFunctionGrpException () {
		super();
	}
	
	public TeamFunctionGrpException (String msg) {
		super(msg);
	}
	
	public TeamFunctionGrpException (Throwable t) {
		super(t);
	}
	
	public TeamFunctionGrpException (String msg, Throwable t) {
		super(msg, t);
	}
}
