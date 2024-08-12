/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Factory that return a Group Exposure DAO implementation
 * @author skchai
 *
 */
public class GroupExposureDAOFactory {

    /**
     * Create a default customer dao implementation
     *
     * @return IGroupExposureDAO
     */
    public static IGroupExposureDAO getDAO(ITrxContext ctx) {
        if (ctx != null) {
            return new GroupExposureDAO();
        }
        return new GroupExposureDAO();
    }

    /**
     * Return default DAO
     *
     * @return IGroupExposureDAO
     */
    protected static IGroupExposureDAO getDAO() {
        return new GroupExposureDAO();
    }
}
