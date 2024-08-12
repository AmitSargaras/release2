/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.OBEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;
import com.integrosys.cms.ui.creditriskparam.entitylimit.list.EntityLimitListForm;


/**
 * Add New Entity Limit into the Staging Entity Limit Array.
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class AddNewEntityLimitCommand extends AbstractCommand {
public class AddNewEntityLimitCommand extends EntityLimitCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            {"EntityLimitTrxValue", 
            	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            {"length", "java.lang.Integer", SERVICE_SCOPE},
			{"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", GLOBAL_SCOPE },
			{"selectedCustList", "java.util.List", FORM_SCOPE},
			{ELCustSearchForm.MAPPER, "java.util.List", FORM_SCOPE}
				
        };
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
            {"EntityLimitTrxValue", 
            	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE},
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the length.
            {"length", "java.util.Integer", SERVICE_SCOPE}, // To populate the form.
            {EntityLimitListForm.MAPPER,
             "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", FORM_SCOPE}
        });
    }
    
    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
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

            IEntityLimitTrxValue value = (IEntityLimitTrxValue)map.get(
                    "EntityLimitTrxValue");
            
            value.setTempEntityLimit(null);

            if (exceptionMap.isEmpty()) {
                // Only proceed if there are no errors.
            	
            	List selectedCustList = (List)map.get(ELCustSearchForm.MAPPER);
            	String[] custSubProfileIDArr = (String[])selectedCustList.get(0);
            	
            	DefaultLogger.debug(this, "**************> custSubProfileIDArr length : " + custSubProfileIDArr.length );
            	
            	SearchResult sr = (SearchResult)map.get("session.customerlist");
            	Collection c = sr.getResultList();
            	
            	Iterator iter = c.iterator();
            	
            	List selectedCustomerList = new ArrayList();
            	
            	while (iter.hasNext()) {
            		OBCustomerSearchResult obsr = (OBCustomerSearchResult)iter.next();
            		long profileID = obsr.getSubProfileID();
            		
            		if (custSubProfileIDArr != null) {
            			for (int i = 0; custSubProfileIDArr.length > i; i++) {
            				long tempProfileID = Long.parseLong(custSubProfileIDArr[i]);
            				
            				if (profileID == tempProfileID) {
            					// found the selected customer
            					IEntityLimit entry = new OBEntityLimit();
            					entry.setCustomerName(obsr.getLegalName());
            					entry.setLEReference(obsr.getLegalReference());
            	            	entry.setEntityLimitID(ICMSConstant.LONG_INVALID_VALUE);
            	            	entry.setGroupID(ICMSConstant.LONG_INVALID_VALUE);
            	            	entry.setCommonRef(ICMSConstant.LONG_INVALID_VALUE);
            	            	entry.setCustomerID(obsr.getSubProfileID());
            	            	entry.setCustIDSource(obsr.getSourceID());
            	            	entry.setLimitAmount(null);
            	            	entry.setLimitLastReviewDate(null);
            	            	entry.setStatus("N");
            	            	
            	            	selectedCustomerList.add(entry);
            				}
            			}
            		}
            	}

                // Add list into as the last item of the array.
                IEntityLimit[] newEntriesArr = new IEntityLimit[selectedCustomerList.size()];

               	DefaultLogger.debug(this, "**************> selectedCustomerList size : " + selectedCustomerList.size() );

                for (int i = 0; i < selectedCustomerList.size(); i++) {
                	IEntityLimit entry = (IEntityLimit)selectedCustomerList.get(i);
                	newEntriesArr[i] = entry;
                }

                value.setTempEntityLimit(newEntriesArr);
                
                resultMap.put("EntityLimitTrxValue", value);
                
            }
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }
        

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
