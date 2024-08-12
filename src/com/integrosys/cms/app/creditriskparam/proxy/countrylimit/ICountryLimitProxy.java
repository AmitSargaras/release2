/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.countrylimit;

import com.integrosys.cms.app.creditriskparam.bus.countrylimit.CountryLimitException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in CMS with
 * respect to the Exempted Inst life cycle.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICountryLimitProxy
{

    /**
     * Gets the Country Limit trx value.
     *
     * @param ctx transaction context
     * @return Country Limit transaction value
     * @throws CountryLimitException on errors encountered
     */
    public ICountryLimitTrxValue getCountryLimitTrxValue (ITrxContext ctx)
        throws CountryLimitException;

    /**
     * Gets the Country Limit trx value by transaction id.
     *
     * @param ctx transaction context
     * @param trxID transaction id
     * @return Country Limit transaction value
     * @throws CountryLimitException on errors encountered
     */
    public ICountryLimitTrxValue getCountryLimitTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CountryLimitException;

    /**
     * Maker updates a list of Country Limit.
     *
     * @param ctx transaction context
     * @param trxVal Country Limit transaction value
     * @param countryLimit a list of Country Limit objects to use for updating.
     * @return updated Country Limit transaction value
     * @throws CountryLimitException on errors encountered
     */
    public ICountryLimitTrxValue makerUpdateCountryLimit (ITrxContext ctx,
           ICountryLimitTrxValue trxVal, ICountryLimitParam countryLimit)
    throws CountryLimitException;
    

    /**
     * Maker close Country Limit rejected by a checker.
     *
     * @param ctx transaction context
     * @param trxVal Country Limit transaction value
     * @return the updated Country Limit transaction value
     * @throws CountryLimitException on errors encountered
     */
    public ICountryLimitTrxValue makerCloseCountryLimit (ITrxContext ctx,
           ICountryLimitTrxValue trxVal) throws CountryLimitException;

    /**
     * Checker approve Country Limit updated by a maker.
     *
     * @param ctx transaction context
     * @param trxVal Country Limit transaction value
     * @return updated transaction value
     * @throws CountryLimitException on errors encountered
     */
    public ICountryLimitTrxValue checkerApproveUpdateCountryLimit (
        ITrxContext ctx, ICountryLimitTrxValue trxVal)
    throws CountryLimitException;

    /**
     * Checker reject Country Limit updated by a maker.
     *
     * @param ctx transaction context
     * @param trxVal Country Limit transaction value
     * @return updated Country Limit transaction value
     * @throws CountryLimitException on errors encountered
     */
    public ICountryLimitTrxValue checkerRejectUpdateCountryLimit (
        ITrxContext ctx, ICountryLimitTrxValue trxVal)
    throws CountryLimitException;
	
}
