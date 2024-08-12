package com.integrosys.cms.app.creditriskparam.bus.internallimit;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Aug 11, 2010
 * Time: 11:58:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class InternalLimitParameterBusManagerStagingImpl extends InternalLimitParameterBusManagerImpl {

    public String getInternalLimitParameterEntityName() {
        return IInternalLimitParameterDao.STAGING_ENTITY_NAME;
    }
    
}
