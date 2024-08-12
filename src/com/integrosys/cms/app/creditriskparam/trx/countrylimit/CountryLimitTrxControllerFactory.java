/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for Country Limit
 * given its transaction parameters.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CountryLimitTrxControllerFactory implements ITrxControllerFactory
{

    private ITrxController ctryLimitReadController;

    private ITrxController ctryLimitTrxController;

    public ITrxController getCtryLimitReadController() {
        return ctryLimitReadController;
    }

    public void setCtryLimitReadController(ITrxController ctryLimitReadController) {
        this.ctryLimitReadController = ctryLimitReadController;
    }

    public ITrxController getCtryLimitTrxController() {
        return ctryLimitTrxController;
    }

    public void setCtryLimitTrxController(ITrxController ctryLimitTrxController) {
        this.ctryLimitTrxController = ctryLimitTrxController;
    }

    /**
     * Default Constructor
     */
    public CountryLimitTrxControllerFactory() {
        super();
    }

    /**
     * Get the ITrxController given the ITrxValue and ITrxParameter objects.
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return security parameter transaction controller
     * @throws TrxParameterException if any error occurs
     */
    public ITrxController getController (ITrxValue value, ITrxParameter param) throws TrxParameterException
    {
        if (isReadOperation (param.getAction())) {
//            return new CountryLimitReadController();
            return getCtryLimitReadController();
        }
//        return new CountryLimitTrxController();
        return getCtryLimitTrxController();
    }

    /**
     * Helper method to check if the action requires a read operation or not.
     *
     * @param anAction of type String
     * @return boolean true if requires a read operation, otherwise false
     */
    private boolean isReadOperation (String anAction)
    {
        if (anAction.equals (ICMSConstant.ACTION_READ_COUNTRY_LIMIT) ||
            anAction.equals (ICMSConstant.ACTION_READ_COUNTRY_LIMIT_BY_TRXID)) {
            return true;
        }
        else {
            return false;
        }
    }
}
