/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.OBExemptedInst;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.ui.creditriskparam.exemptedinst.list.ExemptedInstListForm;


/**
 * Add New Exempted Institution into the Staging Exempted Institutioin Array.
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class AddNewExemptedInstCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"ExemptedInstTrxValue", 
                	"com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, // Produce the offset.
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
                {"length", "java.lang.Integer", SERVICE_SCOPE},
				{"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", GLOBAL_SCOPE },
				{"selectedCustList", "java.util.List", FORM_SCOPE},
				{EICustSearchForm.MAPPER, "java.util.List", FORM_SCOPE}
				
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
        return (new String[][]{// Produce all the feed entries.
            {"ExemptedInstTrxValue", 
            	"com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, // Produce the offset.
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the length.
            {"length", "java.util.Integer", SERVICE_SCOPE}, // To populate the form.
            {ExemptedInstListForm.MAPPER,
             "com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE},
			{ "customerList",
				"com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE }
//			{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
//				"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", GLOBAL_SCOPE }
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
        //DefaultLogger.debug(this, "Map is " + map);

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

            IExemptedInstTrxValue value = (IExemptedInstTrxValue)map.get(
                    "ExemptedInstTrxValue");
            
            IExemptedInst[] exemptedInstArr = value.getStagingExemptedInst();

            // Validate that the (buy currency, sell currency) pair cannot be
            // the same as one of the existing pairs.
            if (exemptedInstArr == null) {
            	exemptedInstArr = new IExemptedInst[0];
            }

            if (exceptionMap.isEmpty()) {
                // Only proceed if there are no errors.
            	
            	List selectedCustList = (List)map.get(EICustSearchForm.MAPPER);
            	String[] custSubProfileIDArr = (String[])selectedCustList.get(0);
            	
            	DefaultLogger.debug(this, "**************> custSubProfileIDArr length : " + custSubProfileIDArr.length );
            	
            	boolean foundCustExist = false;
            	for (int i = 0; i < exemptedInstArr.length; i++) {
            		for (int j = 0; j < custSubProfileIDArr.length; j++) {
            			long tempProfileID = Long.parseLong(custSubProfileIDArr[j]);
            			if (exemptedInstArr[i].getCustomerID() ==
            				tempProfileID) {
            				foundCustExist = true;
            				break;
            			}
            		}
            	}
            	
            	if (foundCustExist) {
            		
            		exceptionMap.put("error.duplicate.exempt.inst", new ActionMessage("error.duplicate.exempt.inst"));
            		resultMap.put("customerList", map.get("session.customerlist"));
            		//resultMap.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, map.get("session.customerlist"));
            		
            	} else {
            	
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
	            					IExemptedInst entry = new OBExemptedInst();
	            					entry.setCustomerName(obsr.getCustomerName());
	            					entry.setLEReference(obsr.getLegalReference());
	            	            	entry.setExemptedInstID(ICMSConstant.LONG_INVALID_VALUE);
	            	            	entry.setGroupID(ICMSConstant.LONG_INVALID_VALUE);
	            	            	entry.setCommonRef(ICMSConstant.LONG_INVALID_VALUE);
	            	            	entry.setCustomerID(obsr.getSubProfileID());
	            	            	entry.setCustIDSource(obsr.getSourceID());
	            	            	entry.setStatus("N");
	            	            	
	            	            	selectedCustomerList.add(entry);
	            				}
	            			}
	            		}
	            	}
	
	                // Add list into as the last item of the array.
	                IExemptedInst[] newEntriesArr = new IExemptedInst[exemptedInstArr.length +
	                        selectedCustomerList.size()];
	
	                System.arraycopy(exemptedInstArr, 0, newEntriesArr, 0,
	                		exemptedInstArr.length);
	
	               	DefaultLogger.debug(this, "**************> selectedCustomerList size : " + selectedCustomerList.size() );
	
	                for (int i = 0; i < selectedCustomerList.size(); i++) {
	                	IExemptedInst entry = (IExemptedInst)selectedCustomerList.get(i);
	                	newEntriesArr[exemptedInstArr.length + i] = entry;
	                }
	                
                    // sort the new entries
                    if (newEntriesArr != null)
                	Arrays.sort(newEntriesArr, new Comparator() {
                        public int compare(Object a, Object b) {
                        	IExemptedInst entry1 = (IExemptedInst)a;
                        	IExemptedInst entry2 = (IExemptedInst)b;
                            if (entry1.getCustomerName() == null) {
                                entry1.setCustomerName("");
                            }
                            if (entry2.getCustomerName() == null) {
                                entry2.setCustomerName("");
                            }
                            return entry1.getCustomerName().compareTo(entry2.getCustomerName());
                        }
                    });
	
	                value.setStagingExemptedInst(newEntriesArr);
	                resultMap.put("ExemptedInstTrxValue", value);
            	}
                //resultMap.put("ExemptedInstTrxValue", value);
                
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
