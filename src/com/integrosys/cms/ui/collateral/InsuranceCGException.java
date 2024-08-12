package com.integrosys.cms.ui.collateral;

import com.integrosys.base.techinfra.exception.OFAException;

public class InsuranceCGException extends OFAException{

	public InsuranceCGException(String msg) {
		super(msg);
	}
	
	
	public InsuranceCGException(String msg, Throwable t) {
		super(msg, t);
	}
}
