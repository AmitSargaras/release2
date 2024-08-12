/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.ExemptedInstProxyFactory;
import com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.OBExemptedInstTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
/**
 * @author siew kheat
 *
 */
public class ReadExemptedInstListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
	        {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	        {"event", "java.lang.String", REQUEST_SCOPE},
            {"ExemptedInstTrxValue", 
            	"com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, // Produce the offset.
	        {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{ "session.customerlist",
				"com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
			{"trxId", "java.lang.String", REQUEST_SCOPE}
        });
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
            	"com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, 
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the length.
            {"length", "java.lang.Integer", SERVICE_SCOPE}, // To populate the form.
            {ExemptedInstListForm.MAPPER,
             "com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue",
             FORM_SCOPE}});
            
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
        //DefaultLogger.debug(this, "Map is " + map);

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        IExemptedInstTrxValue trxValue = new OBExemptedInstTrxValue();
        String parentSubProfileIDStr = "";
        
        try {
        	
        	OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
        	String trxId = (String)map.get("trxId");
        	DefaultLogger.debug(this, "*** trxId = " + trxId);
        	
            IExemptedInstProxy exemptedInstProxy = ExemptedInstProxyFactory.getProxy();
            
            if(trxId == null || trxId.equals("")) {
            	trxValue = exemptedInstProxy.getExemptedInstTrxValue(theOBTrxContext);
            } else
            	trxValue = exemptedInstProxy.getExemptedInstTrxValueByTrxID(theOBTrxContext, trxId);

            DefaultLogger.debug(this,
                    "after getting exempted inst trx from proxy.");

            String event = (String)map.get("event");
            
            DefaultLogger.debug(this, "trxValue : " + trxValue);
            
            if (trxValue == null) {
            	trxValue = new OBExemptedInstTrxValue();
            }
            
            // If this is the very first online read, then there will be
            // no staging records. So copy the actual records as staging
            // records.
            if (trxValue.getStagingExemptedInst() == null && 
            		trxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CREATE)) {
                trxValue.setStagingExemptedInst((IExemptedInst[])CommonUtil.deepClone(
                        trxValue.getExemptedInst()));
            }

            if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE) ||
                    trxValue.getStatus().equals(ICMSConstant.STATE_ND) ||
                    ExemptedInstListAction.EVENT_READ.equals(event)) {
                // Set the staging to be the same as actual.
                trxValue.setStagingExemptedInst((IExemptedInst[])CommonUtil.deepClone(
                        trxValue.getExemptedInst()));
            }

            if (trxValue.getExemptedInst() == null) {
                trxValue.setExemptedInst(new IExemptedInst[0]);
            }
            
            if (trxValue.getStagingExemptedInst() != null) {
            	Arrays.sort(trxValue.getStagingExemptedInst(), new Comparator() {
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
            }
            
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        resultMap.put("ExemptedInstTrxValue", trxValue);
        resultMap.put("offset", new Integer(0));
        resultMap.put("length", new Integer(10));
        resultMap.put(ExemptedInstListForm.MAPPER, trxValue);
        resultMap.put("sub_profile_id", parentSubProfileIDStr);

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
