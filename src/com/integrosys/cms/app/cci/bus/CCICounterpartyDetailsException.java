package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 *
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $
 * Tag: $Name:  $
 *
 */
public class CCICounterpartyDetailsException
        extends OFAException {

    /**
     * Default Constructor
     */
    public CCICounterpartyDetailsException() {
        super();
    }


    /**
     * Constructor
     * @param msg - the message string
     */
    public CCICounterpartyDetailsException(String msg) {
        super(msg);
    }


    /**
     * Constructor
     * @param t - Throwable
     */
    public CCICounterpartyDetailsException(Throwable t) {
        super(t);
    }


    /**
     * Constructor
     * @param msg - message String
     * @param t - Throwable
     */
    public CCICounterpartyDetailsException(String msg, Throwable t) {
        super(msg, t);
    }
}
