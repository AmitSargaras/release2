package com.integrosys.cms.app.eod.sync.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class EODSyncStatusException extends OFAException {

	public EODSyncStatusException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EODSyncStatusException(String arg0) {
		super(arg0);
	}
	
}
