package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 1:51:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IEntityLimitDao {

    public final static String ACTUAL_ENTITY_NAME = "actualEntityLimit";
    public final static String STAGING_ENTITY_NAME = "stageEntityLimit";

    public List findAll(String entityName);

    public IEntityLimit findBySubProfileID(String entityName, long subProfileId);

    public List findByGroupID(String entityName, long groupId);

    public IEntityLimit findByPrimaryKey(String entityName, long key);

    public IEntityLimit createEntityLimit(String entityName, IEntityLimit iEL);

    public IEntityLimit updateEntityLimit(String entityName, IEntityLimit iEl);

}