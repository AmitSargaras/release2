/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author siew kheat
 *
 */
public class PaginateCustRelationshipListCommand extends AbstractCommand {

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
            // Produce all the cust relationship entries to aid in display. For save and list.
            {"CustRelationshipTrxValue",
                	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, 	       
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the update of form. For save and list.
            {CustRelationshipListForm.MAPPER,
                	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue",  FORM_SCOPE}, 
             {"request.ITrxValue", 
                		"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", REQUEST_SCOPE}};
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
            // cust relationship OB.
            {"custRelationshipMap", "java.util.HashMap", FORM_SCOPE}, // Consume the current custrel to be saved as a whole.
            {"CustRelationshipTrxValue",
            	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE},             {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
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

        	HashMap inputHash = (HashMap)map.get("custRelationshipMap");
            int offset = ((Integer) map.get("offset")).intValue();
            int length = ((Integer) map.get("length")).intValue();

            String targetOffsetStr = (String)inputHash.get("targetOffset");
            int targetOffset = (targetOffsetStr == null) ? 0 : Integer.parseInt(targetOffsetStr);
            
            ICustRelationship[] inputCustRelArr = (ICustRelationship[])inputHash.get("custRel");

            ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

            // Added because when going from "view limits" to "cust relationship
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(null);
            trxContext.setLimitProfile(null);

            // Session-scoped trx value.
            ICustRelationshipTrxValue value = (ICustRelationshipTrxValue) map.get(
                    "CustRelationshipTrxValue");
            ICustRelationship[] stagingCustRelArr = value.getStagingCustRelationship();

            // Update using the input unit prices.
            if (inputCustRelArr != null) {
                DefaultLogger.debug(this,
                        "number of existing entries = " + stagingCustRelArr.length);

                for (int i = 0; i < inputCustRelArr.length; i++) {
                	stagingCustRelArr[offset + i].setRelationshipValue(
                			inputCustRelArr[i].getRelationshipValue());
                	stagingCustRelArr[offset + i].setRemarks(
                			inputCustRelArr[i].getRemarks());
                	
                	stagingCustRelArr[offset + i].setLastUpdateDate(new java.util.Date());
                	stagingCustRelArr[offset + i].setLastUpdateUser(trxContext.getUser().getUserName());
                }
            }

            for (int i = 0; i < inputCustRelArr.length; i++) {
                DefaultLogger.debug(this, "before saving, entriesArr[" + i +
                        "] = " + inputCustRelArr[i].getRelationshipValue());
            }

            // Sort the array.

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

            value.setStagingCustRelationship(stagingCustRelArr);

            targetOffset = CustRelationshipListMapper.adjustOffset(targetOffset,
                    length, stagingCustRelArr.length);

            resultMap.put("request.ITrxValue", value);
            resultMap.put("CustRelationshipTrxValue", value);
            resultMap.put("offset", new Integer(targetOffset));
            resultMap.put(CustRelationshipListForm.MAPPER, value);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;

    }
}
