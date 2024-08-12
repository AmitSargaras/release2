package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class LimitsOfAuthorityMasterException extends OFAException {

	public LimitsOfAuthorityMasterException(String msg) {
		super(msg);
	}

	public LimitsOfAuthorityMasterException(String msg, Throwable t) {
		super(msg, t);
	}
}
