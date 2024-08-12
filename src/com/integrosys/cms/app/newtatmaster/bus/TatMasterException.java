package com.integrosys.cms.app.newtatmaster.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class TatMasterException extends OFAException{
	
	public TatMasterException(String msg) {
		super(msg);
	}
	
	
	public TatMasterException(String msg, Throwable t) {
		super(msg, t);
	}

}
