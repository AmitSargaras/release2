/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;

/**
 * Update the Edited Entity Limit
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class UpdateEntityLimitListCommand extends AbstractCommand {
public class UpdateEntityLimitListCommand extends EntityLimitCommand {

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

	            HashMap entityLimitMap = (HashMap)map.get("entityLimitMap");

	        	IEntityLimitTrxValue value = (IEntityLimitTrxValue)map.get(
                		"EntityLimitTrxValue");
	        	IEntityLimit[] stagingEntityLimitArr = value.getStagingEntityLimit();
	        	IEntityLimit[] tempEntityLimitArr = value.getTempEntityLimit();
	        	
	        	if (stagingEntityLimitArr == null) stagingEntityLimitArr = new IEntityLimit[0];
	        	if (tempEntityLimitArr == null) tempEntityLimitArr = new IEntityLimit[0];

	        	String editedPosStr = (String)entityLimitMap.get("editedPos");
	        	int editedPos = -1;
	        	
	        	editedPos = (editedPosStr != null) ? Integer.parseInt(editedPosStr) : -1;
	        	
	        	DefaultLogger.debug(this, ">>>> editedPos : " + editedPos);
	        	
	            IEntityLimit[] inputEntriesArr = (IEntityLimit[])entityLimitMap.get("entityLimitMap");
	            
	            if (inputEntriesArr != null) {
	                DefaultLogger.debug(this,
	                        "number of existing entries = " + inputEntriesArr.length);
	                
	                if (tempEntityLimitArr.length > 0 && editedPos == -1) {
	                	// newly added entity limits 
	                	
	                	List newEntityLimitList = new ArrayList();
		                for (int i = 0; i < inputEntriesArr.length; i++) {
		                	IEntityLimit entityLimit = tempEntityLimitArr[i];
		                	entityLimit.setLimitLastReviewDate(
		                            inputEntriesArr[i].getLimitLastReviewDate());
		                	
		                	entityLimit.setLimitAmount(
		                            inputEntriesArr[i].getLimitAmount());
		                	
		                	newEntityLimitList.add(entityLimit);
		                	
		                    // Add list into as the last item of the array.
		                    IEntityLimit[] newEntriesArr = new IEntityLimit[stagingEntityLimitArr.length + newEntityLimitList.size()];

		                   	DefaultLogger.debug(this, "**************> newEntriesArr size : " + newEntriesArr.length);
		                   	
		                    System.arraycopy(stagingEntityLimitArr, 0, newEntriesArr, 0,
		                    		stagingEntityLimitArr.length);
		                    
		                    for (int j = 0; j < newEntityLimitList.size(); j++) {
		                    	IEntityLimit entry = (IEntityLimit)newEntityLimitList.get(j);
		                    	newEntriesArr[stagingEntityLimitArr.length + j] = entry;
		                    }
		                    
		                    // sort the new entries
		                    if (newEntriesArr != null)
		                	Arrays.sort(newEntriesArr, new Comparator() {
		                        public int compare(Object a, Object b) {
		                            IEntityLimit entry1 = (IEntityLimit)a;
		                            IEntityLimit entry2 = (IEntityLimit)b;
		                            if (entry1.getCustomerName() == null) {
		                                entry1.setCustomerName("");
		                            }
		                            if (entry2.getCustomerName() == null) {
		                                entry2.setCustomerName("");
		                            }
		                            return entry1.getCustomerName().compareTo(entry2.getCustomerName());
		                        }
		                    });
		                	
		                    value.setStagingEntityLimit(newEntriesArr);
		                    
		    	            // empty temp entitylimit
		    	            value.setTempEntityLimit(null);
		                }
	                } else {
	                	// existing entity limit, update the entity limit at stagingEntityLimitArr[editedPos]
	                	if (0 <= editedPos && editedPos < stagingEntityLimitArr.length) {
		                	stagingEntityLimitArr[editedPos].setLimitLastReviewDate(
		                            inputEntriesArr[0].getLimitLastReviewDate());
		                	
		                	stagingEntityLimitArr[editedPos].setLimitAmount(
		                            inputEntriesArr[0].getLimitAmount());
	                	}
	                }
	            }
	            
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
