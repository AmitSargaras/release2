/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for Customer Shareholder
 * given its transaction parameters.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CustShareholderTrxControllerFactory implements ITrxControllerFactory
{
    /**
     * Default Constructor
     */
    public CustShareholderTrxControllerFactory() {
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
            return new CustShareholderReadController();
        }
        return new CustShareholderTrxController();
    }

    /**
     * Helper method to check if the action requires a read operation or not.
     *
     * @param anAction of type String
     * @return boolean true if requires a read operation, otherwise false
     */
    private boolean isReadOperation (String anAction)
    {
        if (anAction.equals (ICMSConstant.ACTION_READ_SHAREHOLDER) ||
            anAction.equals (ICMSConstant.ACTION_READ_SHAREHOLDER_BY_TRXID)) {
            return true;
        }
        else {
            return false;
        }
    }
}
