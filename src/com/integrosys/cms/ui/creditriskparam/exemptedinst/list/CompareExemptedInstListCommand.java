/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;

/**
 * @author user
 *
 */
public class CompareExemptedInstListCommand extends AbstractCommand {
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

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

    	IExemptedInstTrxValue value = (IExemptedInstTrxValue)map.get(
                "ExemptedInstTrxValue");

        int offset = ((Integer)map.get("offset")).intValue();
        int length = ((Integer)map.get("length")).intValue();

        IExemptedInst[] actualExemptedInstArr = value.getExemptedInst();
        IExemptedInst[] stagingExemptedInstArr = value.getStagingExemptedInst();

        DefaultLogger.debug(this, "actualExemptedInstArr : " + actualExemptedInstArr);
        DefaultLogger.debug(this, "stagingExemptedInstArr : " + stagingExemptedInstArr);

        try {

    	if (actualExemptedInstArr != null)
        	Arrays.sort(actualExemptedInstArr, new Comparator() {
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
        	
        	if (stagingExemptedInstArr != null)
        	Arrays.sort(stagingExemptedInstArr, new Comparator() {
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
            	
            List compareResultsList = CompareOBUtil.compOBArray(stagingExemptedInstArr, actualExemptedInstArr);

            offset = ExemptedInstListMapper.adjustOffset(offset, length,
                    compareResultsList.size());

            resultMap.put("compareResultsList", compareResultsList);
            resultMap.put("ExemptedInstTrxValue", value);
            resultMap.put("offset", new Integer(offset));

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

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
        return new String[][]{
	           	{"ExemptedInstTrxValue",
	           		"com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, 
                {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
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
          	{"ExemptedInstTrxValue",
                "com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, 
            {"offset", "java.lang.Integer", SERVICE_SCOPE}};
            }
    }
