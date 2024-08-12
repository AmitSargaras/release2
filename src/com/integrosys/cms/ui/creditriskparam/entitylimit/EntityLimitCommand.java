package com.integrosys.cms.ui.creditriskparam.entitylimit;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 26, 2010
 * Time: 5:10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EntityLimitCommand extends AbstractCommand {

    private IEntityLimitProxy entityLimitProxy;

    public IEntityLimitProxy getEntityLimitProxy() {
        return entityLimitProxy;
    }

    public void setEntityLimitProxy(IEntityLimitProxy entityLimitProxy) {
        this.entityLimitProxy = entityLimitProxy;
    }
}