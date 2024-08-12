package com.integrosys.cms.app.baselmaster.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class BaselMasterException extends OFAException {
	
	public BaselMasterException(String msg) {
		super(msg);
	}
	
	
	public BaselMasterException(String msg, Throwable t) {
		super(msg, t);
	}

}
