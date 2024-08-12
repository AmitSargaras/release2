package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.exception.OFAException;


public class CustGrpIdentifierException
        extends OFAException {

    /**
     * Default Constructor
     */
    public CustGrpIdentifierException() {
        super();
    }


    /**
     * Constructor
     * @param msg - the message string
     */
    public CustGrpIdentifierException(String msg) {
        super(msg);
    }


    /**
     * Constructor
     * @param t - Throwable
     */
    public CustGrpIdentifierException(Throwable t) {
        super(t);
    }


    /**
     * Constructor
     * @param msg - message String
     * @param t - Throwable
     */
    public CustGrpIdentifierException(String msg, Throwable t) {
        super(msg, t);
    }
}
