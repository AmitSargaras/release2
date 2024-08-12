/**
 *
 */
package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

/**
 * @author priya
 */
public interface IEntityLimitBusManager {

    public IEntityLimit[] getAllEntityLimit();

    public IEntityLimit[] getEntityLimitByGroupID(long groupID);

    public IEntityLimit getEntityLimitBySubProfileID(long subProfileID);

    public IEntityLimit[] createEntityLimit(IEntityLimit[] value);

    public IEntityLimit[] updateEntityLimit(IEntityLimit[] value);

}