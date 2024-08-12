package com.integrosys.cms.app.udf.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class UdfException extends OFAException {

	public UdfException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UdfException(String arg0) {
		super(arg0);
	}
	
}
