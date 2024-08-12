/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;


/**
 * @author Siew Kheat
 *
 */
public class DeleteExemptedInstListCommand extends AbstractCommand {

	   public String[][] getParameterDescriptor() {
	        return (new String[][]{
	            {"exemptedInstMap", "java.util.HashMap", FORM_SCOPE}, 
	           	 {"ExemptedInstTrxValue",
	                 "com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, 
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
	            {"ExemptedInstTrxValue",
                 "com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, 
	            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
	            {"length", "java.lang.Integer", SERVICE_SCOPE}, 
	            {ExemptedInstListForm.MAPPER,
	                 "com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", FORM_SCOPE}});
	        	
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

	            HashMap exemptedInstMap = (HashMap)map.get("exemptedInstMap");

	            int offset = ((Integer)map.get("offset")).intValue();
	            int length = ((Integer)map.get("length")).intValue();

	        	IExemptedInstTrxValue value = (IExemptedInstTrxValue)map.get(
                		"ExemptedInstTrxValue");
	        	IExemptedInst[] stagingExemptedInstArr = value.getStagingExemptedInst();

	            
	            // chkDeletesArr contains Strings which index into the entries
	            // array of trx value, 0-based.
	            String[] chkDeletesArr = (String[]) exemptedInstMap.get("exemptedInstDeletes");

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
	            	stagingExemptedInstArr[indexDeletesArr[i]] = null;
	            }

	            // Pack the array of entries, discarding null references.
	            IExemptedInst[] newEntriesArr = new IExemptedInst[stagingExemptedInstArr.length -
	                    indexDeletesArr.length];
	            counter = 0; //reuse
	            // Copy only non-null references.
	            for (int i = 0; i < stagingExemptedInstArr.length; i++) {
	                if (stagingExemptedInstArr[i] != null) {
	                    newEntriesArr[counter++] = stagingExemptedInstArr[i];
	                }
	            }

	            DefaultLogger.debug(this,
	                    "new number of entries = " + newEntriesArr.length);

	            value.setStagingExemptedInst(newEntriesArr);
	            offset = ExemptedInstListMapper.adjustOffset(offset, length,
	                    newEntriesArr.length);
	            
	            resultMap.put("ExemptedInstTrxValue", value);
	            resultMap.put("offset", new Integer(offset));
	            resultMap.put(ExemptedInstListForm.MAPPER, value);

	        } catch (Exception e) {
	            DefaultLogger.error(this, "Exception caught in doExecute()", e);
	            exceptionMap.put("application.exception", e);
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

	        return returnMap;
	    }
}
