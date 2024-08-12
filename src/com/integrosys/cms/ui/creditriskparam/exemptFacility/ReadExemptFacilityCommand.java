/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityException;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.ExemptFacilityProxyFactory;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.IExemptFacilityProxy;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;


/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $
 * Tag: $Name:  $
 */
public class ReadExemptFacilityCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public ReadExemptFacilityCommand() {
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
        return (new String[][]{
            {"InitialExemptFacilityGroup", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"trxID", "java.lang.String", REQUEST_SCOPE},
            {"isRejected", "java.lang.String", REQUEST_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},         });
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
        return (new String[][]{
            {"InitialExemptFacilityGroup", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup", SERVICE_SCOPE},
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            {"isRejected", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();

        String event = (String)map.get("event");
        String trxID = (String)map.get("trxID");
        String isRejected = (String)map.get("isRejected");
        boolean isEdit = (isRejected == null);
        OBTrxContext  trxContext = (OBTrxContext ) map.get("theOBTrxContext");
        IExemptFacilityGroupTrxValue trxValue = null;
        try {
        	
        	DefaultLogger.debug(this, ">>>>>> event = " + event);
        	DefaultLogger.debug(this, ">>>>>>>>>>>> trxID field = " + trxID);
        	 
            IExemptFacilityProxy proxy = ExemptFacilityProxyFactory.getProxy();

            if(trxID == null || trxID.equals("")) {       //SC Maker Listing
                trxValue = proxy.getExemptFacilityTrxValue(trxContext);
            } else {
                trxValue = proxy.getExemptFacilityTrxValueByTrxID(trxContext, trxID);
            }

            String trxStatus = trxValue.getStatus();
            DefaultLogger.debug(this, "In getting by trxID, trxValue=" + trxValue);
            DefaultLogger.debug(this, ">>>>>> trxStatus = " + trxStatus);
            DefaultLogger.debug(this, ">>>>>> event = " + event);

            if(ExemptFacilityAction.EVENT_LIST.equals(event)) {
                DefaultLogger.debug(this, ">>>>>>>>>>> isEdit = " + isEdit);
                if(isEdit && !(ICMSConstant.STATE_ACTIVE.equals(trxStatus) || ICMSConstant.STATE_ND.equals(trxStatus)
                		|| ICMSConstant.STATE_CLOSED.equals(trxStatus))) {
//                    System.out.println("************** wip *************");
                    resultMap.put("wip", "wip");

                }
            }
            
            if (trxValue.getExemptFacilityGroup() == null) {
            	trxValue.setExemptFacilityGroup(new OBExemptFacilityGroup());
            	trxValue.getExemptFacilityGroup().setExemptFacilityGroupID(ICMSConstant.LONG_INVALID_VALUE);
            }
            
            if (trxValue.getExemptFacilityGroup().getExemptFacility() == null) {
            	trxValue.getExemptFacilityGroup().setExemptFacility(new IExemptFacility[0]);
            }
            
            if (trxValue.getStagingExemptFacilityGroup() == null) {
            	trxValue.setStagingExemptFacilityGroup(new OBExemptFacilityGroup());
            	trxValue.getStagingExemptFacilityGroup().setExemptFacilityGroupID(ICMSConstant.LONG_INVALID_VALUE);
            }
            
            if (trxValue.getStagingExemptFacilityGroup().getExemptFacility() == null) {
            	trxValue.getStagingExemptFacilityGroup().setExemptFacility(new IExemptFacility[0]);
            }
            
            if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE) ||
                    trxValue.getStatus().equals(ICMSConstant.STATE_ND) ||
                    ExemptFacilityAction.EVENT_LIST.equals(event)) {
                // Set the staging to be the same as actual.
                trxValue.setStagingExemptFacilityGroup((IExemptFacilityGroup)CommonUtil.deepClone(
                        trxValue.getExemptFacilityGroup()));
            }
            
            // If rejected get from Stage
            if (ICMSConstant.STATE_REJECTED_CREATE.equals( trxStatus)
                || ICMSConstant.STATE_REJECTED_UPDATE.equals( trxStatus ))
            {
                isRejected = ICMSConstant.TRUE_VALUE;
                resultMap.put("isRejected", isRejected);
                //resultMap.put("InitialExemptFacilityGroup", trxValue.getStagingExemptFacilityGroup());
            } // else show actual

            resultMap.put("exemptFacilityTrxValue", trxValue);
            
        } catch (ExemptFacilityException e) {
        	
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        } catch (Exception e) {
        	
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Going out of doExecute()");
        resultMap.put("isRejected", isRejected);
        resultMap.put("exemptFacilityTrxValue", trxValue);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }

}