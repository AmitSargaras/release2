/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;
import org.apache.struts.action.ActionMessage;


/**
 * Delete the maker selected Entity Limit
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class DeleteEntityLimitListCommand extends AbstractCommand {
public class DeleteEntityLimitListCommand extends EntityLimitCommand {

	   public String[][] getParameterDescriptor() {
	        return (new String[][]{
	            {"entityLimitMap", "java.util.HashMap", FORM_SCOPE}, 
	           	{"EntityLimitTrxValue",
	                 "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE}, 
            	{"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            	{"length", "java.lang.Integer", SERVICE_SCOPE}, 
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
	            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
	            {"length", "java.lang.Integer", SERVICE_SCOPE}, 
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

	            int offset = ((Integer)map.get("offset")).intValue();
	            int length = ((Integer)map.get("length")).intValue();

	        	IEntityLimitTrxValue value = (IEntityLimitTrxValue)map.get(
                		"EntityLimitTrxValue");
	        	IEntityLimit[] stagingEntityLimitArr = value.getStagingEntityLimit();

//                IEntityLimitProxy proxy = EntityLimitProxyFactory.getProxy();
                IEntityLimitProxy proxy = getEntityLimitProxy();
                List customerIdList = new ArrayList();
                for (int i = 0; i < stagingEntityLimitArr.length; customerIdList.add(new Long(stagingEntityLimitArr[i++].getCustomerID())));
                List hasLimitBookedList = proxy.retrieveLimitBookedCustomer(customerIdList);

//                List hasLimitBookedList = new ArrayList();
//                hasLimitBookedList.add(new Long(20070101000001L));

	            
                // chkDeletesArr contains Strings which index into the entries
	            // array of trx value, 0-based.
	            String[] chkDeletesArr = (String[]) entityLimitMap.get("entityLimitDeletes");

	            int counter = 0;
	            int[] indexDeletesArr = new int[chkDeletesArr.length];
	            for (int i = 0; i < chkDeletesArr.length; i++) {
	                indexDeletesArr[counter++] =
	                        Integer.parseInt(chkDeletesArr[i]);
	            }

	            DefaultLogger.debug(this,
	                    "number of entries to remove = " + chkDeletesArr.length);
	            for (int i = 0; i < indexDeletesArr.length; i++) {
	                DefaultLogger.debug(this,
	                        "must remove entry " + indexDeletesArr[i]);
	            }

	            // indexDeletesArr contains the indexes of entriesArr for entries
	            // that are to be removed.
	            // Null all the array element references for entries that are to be
	            // removed.
                int addedBack = 0;
                for (int i = 0; i < indexDeletesArr.length; i++) {
                    if (hasLimitBookedList.contains(new Long(stagingEntityLimitArr[indexDeletesArr[i]].getCustomerID()))) {
                        exceptionMap.put("errEntityLimit"+stagingEntityLimitArr[indexDeletesArr[i]].getCustomerID(), new ActionMessage("error.group.haslimitbooking"));
                        addedBack++;
                    } else {
                        stagingEntityLimitArr[indexDeletesArr[i]] = null;
                    }
                }

                if (!exceptionMap.isEmpty()) {
                    resultMap.put("foundError","Customer has Limit Booking");
                }

                // Pack the array of entries, discarding null references.
	            IEntityLimit[] newEntriesArr = new IEntityLimit[stagingEntityLimitArr.length -
	                    indexDeletesArr.length + addedBack];
	            counter = 0; //reuse
	            // Copy only non-null references.
	            for (int i = 0; i < stagingEntityLimitArr.length; i++) {
	                if (stagingEntityLimitArr[i] != null) {
	                    newEntriesArr[counter++] = stagingEntityLimitArr[i];
	                }
	            }

	            DefaultLogger.debug(this,
	                    "new number of entries = " + newEntriesArr.length);

	            value.setStagingEntityLimit(newEntriesArr);
	            offset = EntityLimitListMapper.adjustOffset(offset, length,
	                    newEntriesArr.length);
	            
	            resultMap.put("EntityLimitTrxValue", value);
	            resultMap.put("offset", new Integer(offset));
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
