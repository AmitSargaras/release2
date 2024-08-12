/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;

import java.util.List;

/**
 * Contains actual and staging Country Limit for
 * transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface ICountryLimitTrxValue extends ICMSTrxValue
{
    /**
     * Gets the actual countryLimitParam objects in this transaction.
     *
     * @return The actual countryLimitParam objects
     */
    public ICountryLimitParam getCountryLimitParam();

    /**
     * Sets the actual countryLimitParam objects for this transaction.
     *
     * @param countryLimit the actual countryLimitParam objects
     */
    public void setCountryLimitParam(ICountryLimitParam countryLimit);

    /**
     * Gets the staging countryLimitParam objects in this transaction.
     *
     * @return the staging countryLimitParam objects
     */
    public ICountryLimitParam getStagingCountryLimitParam();

    /**
     * Sets the staging countryLimitParam objects for this transaction.
     *
     * @param countryLimit the staging countryLimitParam objects
     */
    public void setStagingCountryLimitParam(ICountryLimitParam countryLimit);
	

}
