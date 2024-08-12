package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:31:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityLimitBusManagerImpl extends AbstractEntityLimitBusManager {

    protected static final Logger logger = LoggerFactory.getLogger(EntityLimitBusManagerImpl.class);

    public String getEntityLimitxEntityName() {
        return IEntityLimitDao.ACTUAL_ENTITY_NAME;
    }

    public IEntityLimit[] getAllEntityLimit()
            throws EntityLimitException {
        try {
            List results = findAll();

            if (results == null) {
                return null;
            }
            
            Iterator i = results.iterator();

            ArrayList arrList = new ArrayList();

            while (i.hasNext()) {
                IEntityLimit entityLimit = (IEntityLimit) i.next();
                arrList.add(entityLimit);
            }
            return (IEntityLimit[]) arrList.toArray(new OBEntityLimit[0]);
        }
        catch (Exception e) {
            throw new EntityLimitException("Exception caught at getAllEntityLimit " + e.toString());
        }
    }

    public IEntityLimit[] getEntityLimitByGroupID(long groupID)
            throws EntityLimitException {
        try {
            List results = findByGroupID(groupID);

            if (results == null) {
                return null;
            }
            
            Iterator i = results.iterator();

            ArrayList arrList = new ArrayList();
            ArrayList subProfileIDList = new ArrayList();

            while (i.hasNext()) {
                IEntityLimit entityLimit = (IEntityLimit) i.next();
                subProfileIDList.add(new Long(entityLimit.getCustomerID()));
                arrList.add(entityLimit);
            }
            //retreive customer main information for display at UI
            if (subProfileIDList != null && subProfileIDList.size() != 0) {
                Map custMap = CustomerDAOFactory.getDAO().getCustomerMainDetails(subProfileIDList);
                if (arrList.size() > 0) {
                    for (int j = 0; j < arrList.size(); j++) {
                        IEntityLimit entityLimit = (IEntityLimit) arrList.get(j);
                        ICMSCustomer custDtls = (ICMSCustomer) custMap.get(new Long(entityLimit.getCustomerID()));
                        ICMSLegalEntity obLegalEntity = custDtls.getCMSLegalEntity();
                        entityLimit.setCustomerName(obLegalEntity.getLegalName());
                        entityLimit.setLEReference(obLegalEntity.getLEReference());
                        entityLimit.setCustIDSource(obLegalEntity.getSourceID());
                    }
                }

            }
            return (IEntityLimit[]) arrList.toArray(new OBEntityLimit[0]);
        }
        catch (Exception e) {
            throw new EntityLimitException("Exception caught at getEntityLimitByGroupID " + e.toString());
        }
    }

    public IEntityLimit getEntityLimitBySubProfileID(long subProfileID)
            throws EntityLimitException {
        try {
            
            return findBySubProfileID(subProfileID);

        }
        catch (Exception e) {
            throw new EntityLimitException("Exception caught at getEntityLimitBySubProfileID " + e.toString());
        }
    }

    public IEntityLimit[] createEntityLimit(IEntityLimit[] value)
            throws EntityLimitException {
        if (value == null || value.length == 0)
            throw new EntityLimitException("IEntityLimit[] is null");

        try {
            ArrayList arrList = new ArrayList();
            long groupID = ICMSConstant.LONG_MIN_VALUE;

            for (int i = 0; i < value.length; i++) {
                IEntityLimit entityLimit = new OBEntityLimit(value[i]);

                entityLimit.setGroupID(groupID);
                entityLimit.setLimitCurrency(entityLimit.getLimitAmount().getCurrencyCode());

                entityLimit = create(entityLimit);

                if (entityLimit.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
                    entityLimit.setCommonRef(entityLimit.getEntityLimitID());
                }

                if (entityLimit.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
                    entityLimit.setGroupID(entityLimit.getEntityLimitID());
                }

                entityLimit = update(entityLimit);

                groupID = entityLimit.getGroupID();

                arrList.add(entityLimit);
            }
            return (IEntityLimit[]) arrList.toArray(new OBEntityLimit[0]);
        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new EntityLimitException("CreateException caught! " + e.toString());
        }
    }

    public IEntityLimit[] updateEntityLimit(IEntityLimit[] value)
            throws EntityLimitException {

        try {

            ArrayList arrList = new ArrayList();

            for (int i = 0; i < value.length; i++) {
                IEntityLimit entityLimit = value[i];
                entityLimit.setLimitCurrency(entityLimit.getLimitAmount().getCurrencyCode());

                DefaultLogger.debug(this, " processing entityLimit: " + entityLimit);

                if (ICMSConstant.LONG_INVALID_VALUE == entityLimit.getEntityLimitID()) {
                    DefaultLogger.debug(this, " Create Entity Limit for: " + entityLimit.getEntityLimitID());

                    IEntityLimit createdEntityLimit = create(entityLimit);

                    if (createdEntityLimit.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
                        createdEntityLimit.setCommonRef(createdEntityLimit.getEntityLimitID());
                    }

                    if (createdEntityLimit.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
                        createdEntityLimit.setGroupID(createdEntityLimit.getEntityLimitID());
                    }

                    createdEntityLimit = update(createdEntityLimit);

                    arrList.add(createdEntityLimit);
                } else if (entityLimit.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    DefaultLogger.debug(this, " Delete Entity Limit for: " + value[i].getEntityLimitID());
                    //do soft delete
                    IEntityLimit deletedEntityLimit = findByPrimaryKey(entityLimit.getEntityLimitID());
                    if (deletedEntityLimit != null) {
                        deletedEntityLimit.setStatus(ICMSConstant.STATE_DELETED);
                        deletedEntityLimit = update(deletedEntityLimit);
                    }
                } else {
                    DefaultLogger.debug(this, " Update Entity Limit for: " + value[i].getEntityLimitID());

                    IEntityLimit updatedEntityLimit = findByPrimaryKey(entityLimit.getEntityLimitID());

                    AccessorUtil.copyValue(entityLimit, updatedEntityLimit, new String[]{"getEntityLimitID"});
                    updatedEntityLimit = update(updatedEntityLimit);

                    arrList.add(updatedEntityLimit);
                }
            }

            return (IEntityLimit[]) arrList.toArray(new OBEntityLimit[0]);

        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new EntityLimitException("CreateException caught! " + e.toString());
        }
    }
}