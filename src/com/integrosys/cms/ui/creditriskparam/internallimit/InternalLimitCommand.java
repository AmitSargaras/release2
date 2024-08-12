package com.integrosys.cms.ui.creditriskparam.internallimit;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditriskparam.proxy.internallimit.IInternalLimitProxy;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Aug 11, 2010
 * Time: 5:33:29 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class InternalLimitCommand extends AbstractCommand {
    
    private IInternalLimitProxy internalLimitProxy;

    public IInternalLimitProxy getInternalLimitProxy() {
        return internalLimitProxy;
    }

    public void setInternalLimitProxy(IInternalLimitProxy internalLimitProxy) {
        this.internalLimitProxy = internalLimitProxy;
    }
}
