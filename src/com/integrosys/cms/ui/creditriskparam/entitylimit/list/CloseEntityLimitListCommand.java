/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;

/**
 * Maker Close the Rejected Entity Limit List Command
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class CloseEntityLimitListCommand extends AbstractCommand {
public class CloseEntityLimitListCommand extends EntityLimitCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            // Consume the current feed entries to be saved as a whole.
           	 {"EntityLimitTrxValue",
                 "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE}, 
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            {"length", "java.lang.Integer", SERVICE_SCOPE}, 
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}};
    }


    public String[][] getResultDescriptor() {
        return new String[][]{{"request.ITrxValue",
            "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", REQUEST_SCOPE}};
    }


    public HashMap doExecute(HashMap map)
            throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Map is " + map);

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

        	IEntityLimitTrxValue value = (IEntityLimitTrxValue)map.get(
                    "EntityLimitTrxValue");

            ITrxContext trxContext = (ITrxContext)map.get("theOBTrxContext");

            // Added because when some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(null);
            trxContext.setLimitProfile(null);

            if (trxContext == null) {
                DefaultLogger.debug(this,
                        "trxContext obtained from map is null.");
            }

//            IEntityLimitProxy proxy = EntityLimitProxyFactory.getProxy();
            IEntityLimitProxy proxy = getEntityLimitProxy();
            
         	value = proxy.makerCloseEntityLimit(trxContext, value);
        	
            resultMap.put("request.ITrxValue", value);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}

