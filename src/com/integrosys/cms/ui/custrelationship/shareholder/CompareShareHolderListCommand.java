/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.shareholder;

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
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;

/**
 * @author user
 *
 */
public class CompareShareHolderListCommand extends AbstractCommand {
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

        ICustShareholderTrxValue value = (ICustShareholderTrxValue)map.get(
                "CustShareHolderTrxValue");

        int offset = ((Integer)map.get("offset")).intValue();
        int length = ((Integer)map.get("length")).intValue();

        ICustShareholder[] actualShareHolderArr = value.getCustShareholder();
        ICustShareholder[] stagingShareHolderArr = value.getStagingCustShareholder();
        
        DefaultLogger.debug(this, "actualShareHolderArr : " + actualShareHolderArr);
        DefaultLogger.debug(this, "stagingShareHolderArr : " + stagingShareHolderArr);

        try {
        	
            if (stagingShareHolderArr != null && stagingShareHolderArr.length != 0) {
                Arrays.sort(stagingShareHolderArr, new Comparator() {
                    public int compare(Object a, Object b) {
                        ICustShareholder entry1 = (ICustShareholder)a;
                        ICustShareholder entry2 = (ICustShareholder)b;

                        return (int)(entry1.getCustRelationshipID() -
                                entry2.getCustRelationshipID());
                    }
                });
            }
            
             if (actualShareHolderArr != null && actualShareHolderArr.length != 0) {
                Arrays.sort(actualShareHolderArr, new Comparator() {
                    public int compare(Object a, Object b) {
                    	ICustShareholder entry1 = (ICustShareholder)a;
                    	ICustShareholder entry2 = (ICustShareholder)b;

                        return (int)(entry1.getCustRelationshipID() -
                                entry2.getCustRelationshipID());
                    }
                });
            }
             
            List compareResultsList = CompareOBUtil.compOBArray(stagingShareHolderArr, actualShareHolderArr);

            offset = ShareHolderListMapper.adjustOffset(offset, length,
                    compareResultsList.size());

            resultMap.put("compareResultsList", compareResultsList);
            resultMap.put("CustShareHolderTrxValue", value);
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
        return new String[][]{{"CustShareHolderTrxValue", 
        						"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, 
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
            {"CustShareHolderTrxValue", 
				"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE},
			{"offset", "java.lang.Integer", SERVICE_SCOPE}};
    }
}
