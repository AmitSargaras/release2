/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;

/**
 * Entity Limit Paginator
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class PaginateEntityLimitListCommand extends AbstractCommand {
public class PaginateEntityLimitListCommand extends EntityLimitCommand {

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     * 
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return new String[][]{
            // Produce all the entity limit entries to aid in display. For save and list.
            {"EntityLimitTrxValue",
                	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE}, 	       
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the update of form. For save and list.
            {EntityLimitListForm.MAPPER,
                "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue",  FORM_SCOPE}, 
             {"request.ITrxValue", 
                "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", REQUEST_SCOPE}};
    }

    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     * 
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return new String[][]{
            // Consume the input fields as a List of target offset and
            {"entityLimitMap", "java.util.HashMap", FORM_SCOPE}, 
            {"EntityLimitTrxValue",
            	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE},             
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            {"length", "java.lang.Integer", SERVICE_SCOPE}, 
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}};
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     * 
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandValidationException,
            CommandProcessingException, AccessDeniedException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

        	HashMap inputHash = (HashMap)map.get("entityLimitMap");
            int offset = ((Integer) map.get("offset")).intValue();
            int length = ((Integer) map.get("length")).intValue();

            String targetOffsetStr = (String)inputHash.get("targetOffset");
            int targetOffset = (targetOffsetStr == null) ? 0 : Integer.parseInt(targetOffsetStr);

            ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

            // Added because when going from "view limits" to "entity limit
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(null);
            trxContext.setLimitProfile(null);

            // Session-scoped trx value.
            IEntityLimitTrxValue value = (IEntityLimitTrxValue) map.get(
                    "EntityLimitTrxValue");
            IEntityLimit[] stagingCustRelArr = value.getStagingEntityLimit();


            targetOffset = EntityLimitListMapper.adjustOffset(targetOffset,
                    length, stagingCustRelArr.length);

            resultMap.put("request.ITrxValue", value);
            resultMap.put("EntityLimitTrxValue", value);
            resultMap.put("offset", new Integer(targetOffset));
            resultMap.put(EntityLimitListForm.MAPPER, value);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;

    }
}
