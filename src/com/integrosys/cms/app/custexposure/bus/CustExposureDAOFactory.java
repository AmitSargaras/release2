package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.cms.app.transaction.ITrxContext;

public class CustExposureDAOFactory {

    /**
     * Create a default customer dao implementation
     *
     * @return ICustExposureDAO
     */
    public static ICustExposureDAO getDAO(ITrxContext ctx) {
        if (ctx != null) {
            return new CustExposureDAO();
        }
        return new CustExposureDAO();
    }

    /**
     * Return default DAO
     *
     * @return ICustExposureDAO
     */
    protected static ICustExposureDAO getDAO() {
        return new CustExposureDAO();
    }

}
