package com.integrosys.cms.app.lei.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class LeiException extends OFAException {


	public LeiException(String msg) {
		super(msg);
	}

	public LeiException(String msg, Throwable t) {
		super(msg, t);
	}
}
