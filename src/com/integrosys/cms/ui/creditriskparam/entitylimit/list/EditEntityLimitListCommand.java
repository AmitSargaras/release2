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
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;

/**
 * Maker is about to edit the selected or newly added Entity Limit
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class EditEntityLimitListCommand extends AbstractCommand {
public class EditEntityLimitListCommand extends EntityLimitCommand {
	   public String[][] getParameterDescriptor() {
	        return (new String[][]{
	            {"entityLimitMap", "java.util.HashMap", FORM_SCOPE}, 
	           	{"EntityLimitTrxValue",
	                 "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE}, 
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}});
	    }


	    /**
	     * Defines a two dimensional array with the result list to be
	     * expected as a result from the doExecute method using a HashMap
	     * syntax for the array is (HashMapkey,classname,scope)
	     * The scope may be request,form or service
	     *
	     * @return the two dimensional String array
	     */
	    public String[][] getResultDescriptor() {
	        return (new String[][]{
	            // Produce the updated session-scoped trx value.
	            {"EntityLimitTrxValue",
                "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE}, 
	            {"editedPos", "java.lang.Integer", REQUEST_SCOPE}, 
	            {EntityLimitListForm.MAPPER,
	                 "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", FORM_SCOPE}});
	    }


	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.Here reading for Company Borrower is done.
	     *
	     * @param map is of type HashMap
	     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException on errors
	     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException on errors
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map)
	            throws CommandProcessingException, CommandValidationException {
	        DefaultLogger.debug(this, "Map is " + map);
	        
	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        HashMap returnMap = new HashMap();

	        try {
	        	
	        	HashMap inputHash = (HashMap)map.get("entityLimitMap");
	            int pos = -1;
	            
	            pos = (inputHash.get("editedPos") == null) ? -1 : new Integer((String)inputHash.get("editedPos")).intValue();
	            String editedRemarks = (inputHash.get("editedRemarks") == null) ? "" : (String)inputHash.get("editedRemarks");
	            
	        	IEntityLimitTrxValue value = (IEntityLimitTrxValue)map.get(
                		"EntityLimitTrxValue");
	        	
	        	// set the editedRemarks in the trx object
	        	if (value != null && editedRemarks != null) value.setEditedRemarks(editedRemarks);
	        	
	        	IEntityLimit[] stagingEntityLimitArr = value.getStagingEntityLimit();
	        	
	        	

	            // Getting the entitylimit that needed to be edited
	            IEntityLimit editEntityLimit = null;
	            if (0 <= pos && pos < stagingEntityLimitArr.length) {
	            	editEntityLimit = stagingEntityLimitArr[pos];
	            }
	            
	            resultMap.put("editedPos", new Integer(pos));
	            resultMap.put("EntityLimitTrxValue", value);
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
