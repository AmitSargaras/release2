package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:33:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityLimitBusManagerStagingImpl extends EntityLimitBusManagerImpl {

    public String getEntityLimitxEntityName() {
        return IEntityLimitDao.STAGING_ENTITY_NAME;
    }
    
}