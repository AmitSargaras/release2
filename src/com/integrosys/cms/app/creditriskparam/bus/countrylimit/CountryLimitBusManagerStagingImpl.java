package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:33:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountryLimitBusManagerStagingImpl extends CountryLimitBusManagerImpl {

    public String getCountryLimitEntityName() {
        return ICountryLimitDao.STAGING_ENTITY_NAME;
    }

    public String getCountryRatingEntityName() {
        return ICountryRatingDao.STAGING_ENTITY_NAME;
    }

}