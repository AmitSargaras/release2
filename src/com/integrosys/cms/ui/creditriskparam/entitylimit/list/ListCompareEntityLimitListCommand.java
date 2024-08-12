/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;


/**
 * @author $Author: siew kheat $
 * @version $Revision: 1.0 $
 * @since $Date: $
 * Tag: $Name:  $
 */
//public class ListCompareEntityLimitListCommand extends AbstractCommand {
public class ListCompareEntityLimitListCommand extends EntityLimitCommand {

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map)
            throws CommandValidationException, CommandProcessingException,
            AccessDeniedException {
        DefaultLogger.debug(this, "entering doExecute(...)");

        //int targetOffset = ((Integer)map.get(EntityLimitListForm.MAPPER)).intValue();
        
    	HashMap inputHash = (HashMap)map.get("entityLimitMap");
        int targetOffset = -1;
        
        targetOffset = (inputHash.get("targetOffset") == null) ? -1 : new Integer((String)inputHash.get("targetOffset")).intValue();
        String editedRemarks = (inputHash.get("editedRemarks") == null) ? "" : (String)inputHash.get("editedRemarks");
        
        int length = ((Integer)map.get("length")).intValue();

        // Pass through to the mapper to prepare for display.

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        IEntityLimitTrxValue trxValue = (IEntityLimitTrxValue)map.get(
                "EntityLimitTrxValue");
        
        // set the editedRemarks in the trx object
        if (trxValue != null && editedRemarks != null) trxValue.setEditedRemarks(editedRemarks);
        
        List compareResultsList = (List)map.get("compareResultsList");

        targetOffset = EntityLimitListMapper.adjustOffset(targetOffset,
                length, compareResultsList.size());

        resultMap.put("compareResultsList", compareResultsList);
        resultMap.put("EntityLimitTrxValue", trxValue);
        resultMap.put("offset", new Integer(targetOffset));

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
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
        return new String[][]{// Consume the results list.
            {"compareResultsList", "java.util.List", SERVICE_SCOPE}, // Consume the trx value.
            {"EntityLimitTrxValue",
            	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE},
            //{EntityLimitListForm.MAPPER, "java.lang.Integer", FORM_SCOPE}, 
            {"entityLimitMap", "java.util.HashMap", FORM_SCOPE},
            {"length", "java.lang.Integer", SERVICE_SCOPE}, 
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}};
    }


    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return new String[][]{// Produce the comparision results list.
            {"compareResultsList", "java.util.List", SERVICE_SCOPE}, // Produce the trx value nevertheless.
            {"EntityLimitTrxValue",
            	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE},
            {"offset", "java.lang.Integer", SERVICE_SCOPE}
            // To update the offset for proper display.
        };
    }
}
