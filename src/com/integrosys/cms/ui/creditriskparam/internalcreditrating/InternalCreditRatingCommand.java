package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating.IInternalCreditRatingProxy;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 26, 2010
 * Time: 5:10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class InternalCreditRatingCommand extends AbstractCommand {

    private IInternalCreditRatingProxy internalCreditRatingProxy;

    public IInternalCreditRatingProxy getInternalCreditRatingProxy() {
        return internalCreditRatingProxy;
    }

    public void setInternalCreditRatingProxy(IInternalCreditRatingProxy internalCreditRatingProxy) {
        this.internalCreditRatingProxy = internalCreditRatingProxy;
    }
}
