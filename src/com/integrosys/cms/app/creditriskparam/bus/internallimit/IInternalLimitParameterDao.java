package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Aug 10, 2010
 * Time: 1:42:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IInternalLimitParameterDao {
    
    public final static String ACTUAL_ENTITY_NAME = "actualInternalLimitParameter";
    public final static String STAGING_ENTITY_NAME = "stageInternalLimitParameter";

    public List findAll(String entityName);

    public List findByGroupId(String entityName, Long groupId);

    public IInternalLimitParameter findByEntityType(String entityName, String entityType);

    public IInternalLimitParameter findByPrimaryKey(String entityName, Long key);

    public IInternalLimitParameter createInternalLimitParameter(String entityName, IInternalLimitParameter ilp);

    public IInternalLimitParameter updateInternalLimitParameter(String entityName, IInternalLimitParameter ilp);
}
