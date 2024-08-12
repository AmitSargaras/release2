/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for Customer Relationship
 * given its transaction parameters.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CustRelationshipTrxControllerFactory implements ITrxControllerFactory
{
    /**
     * Default Constructor
     */
    public CustRelationshipTrxControllerFactory() {
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
            return new CustRelationshipReadController();
        }
        return new CustRelationshipTrxController();
    }

    /**
     * Helper method to check if the action requires a read operation or not.
     *
     * @param anAction of type String
     * @return boolean true if requires a read operation, otherwise false
     */
    private boolean isReadOperation (String anAction)
    {
        if (anAction.equals (ICMSConstant.ACTION_READ_CUST_RELNSHIP) ||
            anAction.equals (ICMSConstant.ACTION_READ_CUST_RELNSHIP_BY_TRXID)) {
            return true;
        }
        else {
            return false;
        }
    }
}
