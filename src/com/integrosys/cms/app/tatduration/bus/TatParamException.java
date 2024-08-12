package com.integrosys.cms.app.tatduration.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 4:44:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatParamException extends OFAException {

    public TatParamException() 
    {
    }

    public TatParamException(String msg) 
    {
        super(msg);
    }

    public TatParamException(Throwable obj)
    {
        super(obj);
    }

    public TatParamException(String msg, Throwable obj)
    {
        super(msg, obj);
    }
}
