/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.shareholder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
/**
 * @author user
 *
 */
public class PaginateShareHolderListCommand extends AbstractCommand {

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
            // Produce all the share holder entries to aid in display. For save and list.
	        {"CustShareHolderTrxValue", 
	            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, 
	        {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the update of form. For save and list.
            {ShareHolderListForm.MAPPER,
             "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValuee",
             FORM_SCOPE}, 
             {"request.ITrxValue", "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue",
                           REQUEST_SCOPE}};
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
            // share holder group OB.
            {"shareHolderMap", "java.util.HashMap", FORM_SCOPE}, // Consume the current shareholder to be saved as a whole.
	        {"CustShareHolderTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, 
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
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

        	HashMap inputHash = (HashMap)map.get("shareHolderMap");
            int offset = ((Integer) map.get("offset")).intValue();
            int length = ((Integer) map.get("length")).intValue();

            String targetOffsetStr = (String)inputHash.get("targetOffset");
            int targetOffset = (targetOffsetStr == null) ? 0 : Integer.parseInt(targetOffsetStr);
            
            ICustShareholder[] inputCustShareHolderArr = (ICustShareholder[])inputHash.get("shareHolder");

            ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

            // Added because when going from "view limits" to "share holders
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(null);
            trxContext.setLimitProfile(null);

            // Session-scoped trx value.
            ICustShareholderTrxValue value = (ICustShareholderTrxValue) map.get(
                    "CustShareHolderTrxValue");
            ICustShareholder[] stagingShareHolderArr = value.getStagingCustShareholder();

            // Update using the input unit prices.
            if (inputCustShareHolderArr != null) {
                DefaultLogger.debug(this,
                        "number of existing entries = " + stagingShareHolderArr.length);

                for (int i = 0; i < inputCustShareHolderArr.length; i++) {
                	stagingShareHolderArr[offset + i].setPercentageOwn(
                			inputCustShareHolderArr[i].getPercentageOwn());
                	
                	stagingShareHolderArr[offset + i].setLastUpdateDate(new java.util.Date());
                	stagingShareHolderArr[offset + i].setLastUpdateUser(trxContext.getUser().getUserName());
                }
            }

            for (int i = 0; i < inputCustShareHolderArr.length; i++) {
                DefaultLogger.debug(this, "before saving, entriesArr[" + i +
                        "] = " + inputCustShareHolderArr[i].getPercentageOwn());
            }

            // Sort the array.

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

            value.setStagingCustShareholder(stagingShareHolderArr);

            targetOffset = ShareHolderListMapper.adjustOffset(targetOffset,
                    length, stagingShareHolderArr.length);

            resultMap.put("request.ITrxValue", value);
            resultMap.put("CustShareHolderTrxValue", value);
            resultMap.put("offset", new Integer(targetOffset));
            resultMap.put(ShareHolderListForm.MAPPER, value);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;

    }
}
