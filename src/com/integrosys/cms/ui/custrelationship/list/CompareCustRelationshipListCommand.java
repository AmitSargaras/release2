/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

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
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;

/**
 * @author user
 *
 */
public class CompareCustRelationshipListCommand extends AbstractCommand {
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

        com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue value = (ICustRelationshipTrxValue)map.get(
                "CustRelationshipTrxValue");

        int offset = ((Integer)map.get("offset")).intValue();
        int length = ((Integer)map.get("length")).intValue();

        ICustRelationship[] actualCustRelArr = value.getCustRelationship();
        ICustRelationship[] stagingCustRelArr = value.getStagingCustRelationship();

        DefaultLogger.debug(this, "actualCustRelArr : " + actualCustRelArr);
        DefaultLogger.debug(this, "stagingCustRelArr : " + stagingCustRelArr);
        
        try {

            if (stagingCustRelArr != null && stagingCustRelArr.length != 0) {
                Arrays.sort(stagingCustRelArr, new Comparator() {
                    public int compare(Object a, Object b) {
                        ICustRelationship entry1 = (ICustRelationship)a;
                        ICustRelationship entry2 = (ICustRelationship)b;

                        return (int)(entry1.getCustRelationshipID() -
                                entry2.getCustRelationshipID());
                    }
                });
            }
            
             if (actualCustRelArr != null && actualCustRelArr.length != 0) {
                Arrays.sort(actualCustRelArr, new Comparator() {
                    public int compare(Object a, Object b) {
                    	ICustRelationship entry1 = (ICustRelationship)a;
                    	ICustRelationship entry2 = (ICustRelationship)b;

                        return (int)(entry1.getCustRelationshipID() -
                                entry2.getCustRelationshipID());
                    }
                });
            }
             
            List compareResultsList = CompareOBUtil.compOBArray(stagingCustRelArr, actualCustRelArr);

            offset = CustRelationshipListMapper.adjustOffset(offset, length,
                    compareResultsList.size());

            resultMap.put("compareResultsList", compareResultsList);
            resultMap.put("CustRelationshipTrxValue", value);
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
        return new String[][]{{"CustRelationshipTrxValue",
                               "com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, 
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
            {"CustRelationshipTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, {"offset", "java.lang.Integer", SERVICE_SCOPE}};
            }
    }
