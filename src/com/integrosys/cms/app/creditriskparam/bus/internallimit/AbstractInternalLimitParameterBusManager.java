/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/AbstractForexFeedBusManager.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 04:08:19 $ Tag: $Name: $
 */
public abstract class AbstractInternalLimitParameterBusManager implements IInternalLimitParameterBusManager {

    private IInternalLimitParameterDao internalLimitParameterDao;

    // Getter / Setter
    public IInternalLimitParameterDao getInternalLimitParameterDao() {
        return internalLimitParameterDao;
    }

    public void setInternalLimitParameterDao(IInternalLimitParameterDao internalLimitParameterDao) {
        this.internalLimitParameterDao = internalLimitParameterDao;
    }

    

    // Returns Entity name to differenciate Staging / Actual
    // To be implemented by actual and staging Bus Managers
    public abstract String getInternalLimitParameterEntityName(); 

    

    // Method implementations
    public List findAll() throws InternalLimitException {
        return getInternalLimitParameterDao().findAll(getInternalLimitParameterEntityName());
    }

	public List findByGroupId(long groupID) throws InternalLimitException {
        return getInternalLimitParameterDao().findByGroupId(getInternalLimitParameterEntityName(), new Long(groupID));
    }

    public IInternalLimitParameter findByEntityType(String type) throws InternalLimitException {
        return getInternalLimitParameterDao().findByEntityType(getInternalLimitParameterEntityName(), type);
    }

    public IInternalLimitParameter findByPrimaryKey(Long key) {
        return getInternalLimitParameterDao().findByPrimaryKey(getInternalLimitParameterEntityName(), key);
    }

	public IInternalLimitParameter create(IInternalLimitParameter ilp) throws InternalLimitException {
        return getInternalLimitParameterDao().createInternalLimitParameter(getInternalLimitParameterEntityName(), ilp);
    }

	public IInternalLimitParameter update(IInternalLimitParameter ilp) throws InternalLimitException {
        return getInternalLimitParameterDao().updateInternalLimitParameter(getInternalLimitParameterEntityName(), ilp);
    }

}
