package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class InternalLimitException extends OFAException {
	private static final long serialVersionUID = 1L;

	public InternalLimitException() {
		super();
	}

	public InternalLimitException(String msg) {
		super(msg);
	}

	public InternalLimitException(Throwable obj) {
		super(obj);
	}

	public InternalLimitException(String msg, Throwable obj) {
		super(msg, obj);
	}
}
