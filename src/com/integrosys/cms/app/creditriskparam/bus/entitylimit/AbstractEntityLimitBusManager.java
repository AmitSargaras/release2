/**
 *
 */
package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

import java.util.List;

/**
 * @author priya
 */
public abstract class AbstractEntityLimitBusManager implements IEntityLimitBusManager {

    private IEntityLimitDao entityLimitDao;

    public IEntityLimitDao getEntityLimitDao() {
        return entityLimitDao;
    }

    public void setEntityLimitDao(IEntityLimitDao entityLimitDao) {
        this.entityLimitDao = entityLimitDao;
    }

    public List findAll() {
        return getEntityLimitDao().findAll(getEntityLimitxEntityName());
    }

    public List findByGroupID(long groupId) {
        return getEntityLimitDao().findByGroupID(getEntityLimitxEntityName(), groupId);
    }

    public IEntityLimit findBySubProfileID(long subProfileID) {
        return getEntityLimitDao().findBySubProfileID(getEntityLimitxEntityName(), subProfileID);
    }

    public IEntityLimit create(IEntityLimit iEL) {
        return getEntityLimitDao().createEntityLimit(getEntityLimitxEntityName(), iEL);
    }

    public IEntityLimit update(IEntityLimit iEL) {
        return getEntityLimitDao().updateEntityLimit(getEntityLimitxEntityName(), iEL);
    }

    public IEntityLimit findByPrimaryKey(long key) {
        return getEntityLimitDao().findByPrimaryKey(getEntityLimitxEntityName(), key);
    }

    public abstract String getEntityLimitxEntityName();

}