package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Title: CLIMS
 * Description: General exception class for the ddn package. It should be thrown when there is any exception encountered in the
 * ddn process that requires no special handling.
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 17, 2008
 */

public class BankEntityBranchParamException extends OFAException
{
    /**
    * Default Constructor
    */
    public BankEntityBranchParamException()
    {
        super();
    }

    /**
    * Constructor
    * @param msg - the message string
    */
    public BankEntityBranchParamException(String msg)
    {
        super(msg);
    }

    /**
    * Constructor
    * @param t - Throwable
    */
    public BankEntityBranchParamException(Throwable t)
    {
        super(t);
    }

    /**
    * Constructor
    * @param msg - message String
    * @param t - Throwable
    */
    public BankEntityBranchParamException(String msg, Throwable t)
    {
        super(msg, t);
    }
}
