/**
 *
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

/**
 * @author priya
 */
public interface ICountryLimitBusManager {

    public ICountryRating[] getCountryRating()
            throws CountryLimitException;

    public ICountryLimit getCountryLimitByCountryCode(String countryCode)
            throws CountryLimitException;

    public ICountryLimitParam getCountryLimitParamByGroupID(long groupID)
            throws CountryLimitException;

    public ICountryLimitParam createCountryLimitParam(ICountryLimitParam value)
            throws CountryLimitException;

    public ICountryLimit[] updateCountryLimitAmount(ICountryLimit[] value)
            throws CountryLimitException;

    public ICountryLimitParam updateCountryLimitParam(ICountryLimitParam value)
            throws CountryLimitException;

}
