/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.shareholder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;
import com.integrosys.cms.ui.custrelationship.CustRelUtils;


/**
 * @author Siew Kheat
 *
 */
public class DeleteShareHolderListCommand extends AbstractCommand {

	   public String[][] getParameterDescriptor() {
	        return (new String[][]{
	            {"shareHolderMap", "java.util.HashMap", FORM_SCOPE}, 
	            {"CustShareHolderTrxValue", 
	            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, 
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
	            {"CustShareHolderTrxValue", 
	            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, 
	            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
	            {"length", "java.lang.Integer", SERVICE_SCOPE}, 
	            {"entRelLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
	            {"entRelValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
	            {ShareHolderListForm.MAPPER,
	                 "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", FORM_SCOPE}});
	        	
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

	            HashMap shareHolderMap = (HashMap)map.get("shareHolderMap");
	            ICustShareholder[] shareHolderArr = (ICustShareholder[])shareHolderMap.get("shareHolder");

	            int offset = ((Integer)map.get("offset")).intValue();
	            int length = ((Integer)map.get("length")).intValue();

	            ICustShareholderTrxValue value = (ICustShareholderTrxValue)map.get(
	                    "CustShareHolderTrxValue");
	            ICustShareholder[] stagingShareHolderArr = value.getStagingCustShareholder();

	            if (stagingShareHolderArr != null) {
	                DefaultLogger.debug(this,
	                        "number of existing entries = " + stagingShareHolderArr.length);

	                for (int i = 0; i < shareHolderArr.length; i++) {
	                	stagingShareHolderArr[offset + i].setPercentageOwn(
	                			shareHolderArr[i].getPercentageOwn());
	                }
	            }
	            
	            // chkDeletesArr contains Strings which index into the entries
	            // array of trx value, 0-based.
	            String[] chkDeletesArr = (String[])shareHolderMap.get("shareHolderDeletes");

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
	            	stagingShareHolderArr[indexDeletesArr[i]] = null;
	            }

	            // Pack the array of entries, discarding null references.
	            ICustShareholder[] newEntriesArr = new ICustShareholder[stagingShareHolderArr.length -
	                    indexDeletesArr.length];
	            counter = 0; //reuse
	            // Copy only non-null references.
	            for (int i = 0; i < stagingShareHolderArr.length; i++) {
	                if (stagingShareHolderArr[i] != null) {
	                    newEntriesArr[counter++] = stagingShareHolderArr[i];
	                }
	            }

	            DefaultLogger.debug(this,
	                    "new number of entries = " + newEntriesArr.length);

	            value.setStagingCustShareholder(newEntriesArr);
	            offset = ShareHolderListMapper.adjustOffset(offset, length,
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
	            
	            resultMap.put("CustShareHolderTrxValue", value);
	            resultMap.put("offset", new Integer(offset));
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
