package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class BankingArrangementFacExclusionException extends OFAException {

	public BankingArrangementFacExclusionException(String msg) {
		super(msg);
	}

	public BankingArrangementFacExclusionException(String msg, Throwable t) {
		super(msg, t);
	}
}
