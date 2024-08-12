/**
 *
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author priya
 */
public abstract class AbstractCountryLimitBusManager implements ICountryLimitBusManager {

    private CountryLimitDaoImpl countryLimitDao;
    private CountryRatingDaoImpl countryRatingDao;

    public CountryLimitDaoImpl getCountryLimitDao() {
        return countryLimitDao;
    }

    public void setCountryLimitDao(CountryLimitDaoImpl countryLimitDao) {
        this.countryLimitDao = countryLimitDao;
    }

    public CountryRatingDaoImpl getCountryRatingDao() {
        return countryRatingDao;
    }

    public void setCountryRatingDao(CountryRatingDaoImpl countryRatingDao) {
        this.countryRatingDao = countryRatingDao;
    }

    public List findAll_CountryLimit() {
        return getCountryLimitDao().findAll(getCountryLimitEntityName());
    }

    public List findAll_CountryRating() {
        return getCountryRatingDao().findAll(getCountryRatingEntityName());
    }

    public ICountryLimit findByCountryCode(String countryCode) {
        return (ICountryLimit) getCountryLimitDao().findByCountryCode(getCountryLimitEntityName(), countryCode);
    }

    public List findByGroupId_CountryLimit(long groupId) {
        return getCountryLimitDao().findByGroupId(getCountryLimitEntityName(), groupId);        
    }

    public List findByGroupId_CountryRating(long groupId) {
        return getCountryRatingDao().findByGroupId(getCountryRatingEntityName(), groupId);
    }

    public ICountryLimit findByPrimaryKey_CountryLimit(long key) {
        return (ICountryLimit) getCountryLimitDao().findByPrimaryKey(getCountryLimitEntityName(), key);    
    }

    public ICountryRating findByPrimaryKey_CountryRating(long key) {
        return (ICountryRating) getCountryRatingDao().findByPrimaryKey(getCountryRatingEntityName(), key);
    }

    public ICountryLimit createCountryLimit_CountryLimit(ICountryLimit iCL) {
        return (ICountryLimit) getCountryLimitDao().createCountryLimit(getCountryLimitEntityName(), iCL);
    }

    public ICountryRating createCountryLimit_CountryRating(ICountryRating iCR) {
        return (ICountryRating) getCountryRatingDao().createCountryRating(getCountryRatingEntityName(), iCR);
    }

    public ICountryLimit updateCountryLimit_CountryLimit(ICountryLimit iCL) {
        return (ICountryLimit) getCountryLimitDao().updateCountryLimit(getCountryLimitEntityName(), iCL);    
    }

    public ICountryRating updateCountryLimit_CountryRating(ICountryRating iCR) {
        return (ICountryRating) getCountryRatingDao().updateCountryRating(getCountryRatingEntityName(), iCR);
    }

    public abstract String getCountryLimitEntityName();

    public abstract String getCountryRatingEntityName();

}
