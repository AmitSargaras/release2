/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

import java.util.List;

/**
 * Contains actual and staging countryLimit for transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class OBCountryLimitTrxValue extends OBCMSTrxValue implements ICountryLimitTrxValue
{
    private ICountryLimitParam actual;
    private ICountryLimitParam staging;

    /**
     * Default constructor.
     */
    public OBCountryLimitTrxValue() {
        super();
        super.setTransactionType(ICMSConstant.INSTANCE_COUNTRY_LIMIT);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICountryLimitTrxValue
     */
    public OBCountryLimitTrxValue (ICountryLimitTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICMSTrxValue
     */
    public OBCountryLimitTrxValue (ICMSTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue#getCountryLimitParam
    */
    public ICountryLimitParam getCountryLimitParam() {
        return this.actual;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue#setCountryLimitParam
    */
    public void setCountryLimitParam (ICountryLimitParam countryLimit) {
        this.actual = countryLimit;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue#getStagingCountryLimitParam
    */
    public ICountryLimitParam getStagingCountryLimitParam() {
        return this.staging;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue#setStagingCountryLimitParam
    */
    public void setStagingCountryLimitParam (ICountryLimitParam countryLimit) {
        this.staging = countryLimit;
    }

    /**
     * Return a String representation of the object
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }
}
