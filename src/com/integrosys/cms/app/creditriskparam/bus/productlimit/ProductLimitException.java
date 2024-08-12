package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.techinfra.exception.OFAException;

public class ProductLimitException extends OFAException {
	
	private static final long serialVersionUID = 1L;

    public ProductLimitException() {
        super();
    }

    public ProductLimitException(String msg) {
        super(msg);
    }

    public ProductLimitException(Throwable throwable) {
        super(throwable);
    }

    public ProductLimitException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
