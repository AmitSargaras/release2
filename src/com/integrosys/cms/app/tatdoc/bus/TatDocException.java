package com.integrosys.cms.app.tatdoc.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 4:44:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatDocException extends OFAException {

    public TatDocException() {
    }

    public TatDocException(String msg) {
        super(msg);
    }

    public TatDocException(Throwable obj) {
        super(obj);
    }

    public TatDocException(String msg, Throwable obj) {
        super(msg, obj);
    }
}
