/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.entitylimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for Entity Limit
 * given its transaction parameters.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class EntityLimitTrxControllerFactory implements ITrxControllerFactory
{

    private ITrxController entityLimitReadController;

    private ITrxController entityLimitTrxController;

    public ITrxController getEntityLimitReadController() {
        return entityLimitReadController;
    }

    public void setEntityLimitReadController(ITrxController entityLimitReadController) {
        this.entityLimitReadController = entityLimitReadController;
    }

    public ITrxController getEntityLimitTrxController() {
        return entityLimitTrxController;
    }

    public void setEntityLimitTrxController(ITrxController entityLimitTrxController) {
        this.entityLimitTrxController = entityLimitTrxController;
    }

    /**
     * Default Constructor
     */
    public EntityLimitTrxControllerFactory() {
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
//            return new EntityLimitReadController();
            return getEntityLimitReadController();
        }
//        return new EntityLimitTrxController();
        return getEntityLimitTrxController();
    }

    /**
     * Helper method to check if the action requires a read operation or not.
     *
     * @param anAction of type String
     * @return boolean true if requires a read operation, otherwise false
     */
    private boolean isReadOperation (String anAction)
    {
        if (anAction.equals (ICMSConstant.ACTION_READ_ENTITY_LIMIT) ||
            anAction.equals (ICMSConstant.ACTION_READ_ENTITY_LIMIT_BY_TRXID)) {
            return true;
        }
        else {
            return false;
        }
    }
}
