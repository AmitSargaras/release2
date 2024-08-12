/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;
import com.integrosys.cms.ui.custrelationship.CustRelUtils;


/**
 * @author Siew Kheat
 *
 */
public class DeleteCustRelationshipListCommand extends AbstractCommand {

	   public String[][] getParameterDescriptor() {
	        return (new String[][]{
	            {"custRelationshipMap", "java.util.HashMap", FORM_SCOPE}, 
	            {"CustRelationshipTrxValue",
	            	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE},
            	{"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            	{"length", "java.lang.Integer", SERVICE_SCOPE}, 
            	{"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
            	{"customerType", "java.lang.String", SERVICE_SCOPE},
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
	        	{"CustRelationshipTrxValue",
	        		"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE},
	            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
	            {"length", "java.lang.Integer", SERVICE_SCOPE}, 
	            {"entRelLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
	            {"entRelValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
	            {CustRelationshipListForm.MAPPER,
	                 "com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", FORM_SCOPE}});
	        	
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

	            HashMap custRelationshipMap = (HashMap)map.get("custRelationshipMap");
	            ICustRelationship[] custRelArr = (ICustRelationship[])custRelationshipMap.get("custRel");

	            int offset = ((Integer)map.get("offset")).intValue();
	            int length = ((Integer)map.get("length")).intValue();

	            ICustRelationshipTrxValue value = (ICustRelationshipTrxValue)map.get(
	                    "CustRelationshipTrxValue");
	            ICustRelationship[] stagingCustRelArr = value.getStagingCustRelationship();

	            if (stagingCustRelArr != null) {
	                DefaultLogger.debug(this,
	                        "number of existing entries = " + stagingCustRelArr.length);

	                for (int i = 0; i < custRelArr.length; i++) {
	                	stagingCustRelArr[offset + i].setRelationshipValue(
	                			custRelArr[i].getRelationshipValue());
	                	stagingCustRelArr[offset + i].setRemarks(
	                			custRelArr[i].getRemarks());
	                }
	            }
	            
	            // chkDeletesArr contains Strings which index into the entries
	            // array of trx value, 0-based.
	            String[] chkDeletesArr = (String[])custRelationshipMap.get("custRelDeletes");

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
	            for (int i = 0; i < indexDeletesArr.length; i++) {
	            	stagingCustRelArr[indexDeletesArr[i]] = null;
	            }

	            // Pack the array of entries, discarding null references.
	            ICustRelationship[] newEntriesArr = new ICustRelationship[stagingCustRelArr.length -
	                    indexDeletesArr.length];
	            counter = 0; //reuse
	            // Copy only non-null references.
	            for (int i = 0; i < stagingCustRelArr.length; i++) {
	                if (stagingCustRelArr[i] != null) {
	                    newEntriesArr[counter++] = stagingCustRelArr[i];
	                }
	            }

	            DefaultLogger.debug(this,
	                    "new number of entries = " + newEntriesArr.length);

	            value.setStagingCustRelationship(newEntriesArr);
	            offset = CustRelationshipListMapper.adjustOffset(offset, length,
	                    newEntriesArr.length);

	            CommonCodeList commonCode = CommonCodeList .getInstance(null, null, ICMSUIConstant.ENTITY_REL, null);
	            Collection entRelValues = commonCode.getCommonCodeValues();
	            Collection entRelLabels = commonCode.getCommonCodeLabels();
	            if (entRelValues == null) {
	            	entRelValues = new ArrayList();
	            }
	            if (entRelLabels == null) {
	            	entRelLabels = new ArrayList();
	            }
	            
	            entRelValues = CustRelUtils.removeIntFromCollection(CustRelConstants.SHAREHOLDER_ID, entRelValues);
	            entRelLabels = CustRelUtils.removeStringFromCollection(CustRelConstants.SHAREHOLDER_LABEL, entRelLabels);
	            resultMap.put("entRelValues", entRelValues);
	            resultMap.put("entRelLabels", entRelLabels);
	            
	            resultMap.put("CustRelationshipTrxValue", value);
	            resultMap.put("offset", new Integer(offset));
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
