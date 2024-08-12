/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.ExemptFacilityProxyFactory;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.IExemptFacilityProxy;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.OBExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacility;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.*;


/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $
 * Tag: $Name:  $
 */
public class PrepareExemptFacilityCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public PrepareExemptFacilityCommand() {
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
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            {"exemptFacilityID", "java.lang.String", REQUEST_SCOPE},
//            {"isRejected", "java.lang.String", REQUEST_SCOPE},
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
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            {"exemptFacility", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacility", FORM_SCOPE},
//            {"exemptFacilityMap", "java.util.HashMap", FORM_SCOPE},
//            {"isRejected", "java.lang.String", REQUEST_SCOPE},
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
        String exemptFacilityID = (String)map.get("exemptFacilityID");
        boolean isEdit = (isRejected == null);
        OBTrxContext  trxContext = (OBTrxContext ) map.get("theOBTrxContext");
        IExemptFacilityGroupTrxValue trxValue = null;

        // Get the OB from group by ID
//        System.out.println("exemptFacilityID = " + exemptFacilityID);
        //Lini try
        trxValue = new OBExemptFacilityGroupTrxValue();
        OBExemptFacilityGroup grp = new OBExemptFacilityGroup();
        OBExemptFacility fac = new OBExemptFacility();

        if (EVENT_PREPARE.equals(event)){
            fac = new OBExemptFacility();
        }

//        try {
//
//            IExemptFacilityProxy proxy = ExemptFacilityProxyFactory.getProxy();
//            DefaultLogger.debug(this, ">>>>>>>>>>>> trxID field = " + trxID);
//            if(trxID == null || trxID.equals("")) {       //Maker Edit
//                DefaultLogger.debug(this, ">>>>>>>>>>>> Getting the Transaction object" );
//                trxValue = proxy.getExemptFacilityGroup(trxContext);
//                /*if(exemptFacilityList != null && exemptFacilityList.size() > 0) {
//                    DefaultLogger.debug(this, ">>>>>>>>>>>>> groupID = " + exemptFacilityList[0].getGroupID());
//                    trxValue = proxy.getExemptFacilityTrxValueByTrxID(exemptFacilityList[0].getGroupID());
//                }*/
//            } else {
//                //Maker/Checher from To-Do
//
//                long lTrxID = new Long(trxID).longValue();
//                DefaultLogger.debug(this, ">>>>>>>>>>>> In getting by trxID");
//                trxValue = proxy.getExemptFacilityTrxValueByTrxID(trxContext, lTrxID);
//                DefaultLogger.debug(this, ">>>>>>>>>>>> trxValue="+trxValue);
//            }
//            IExemptFacility[] exemptFacilities;
//            if(trxValue != null) {
//                if (trxValue.getExemptFacilityGroup()!=null){
//                    exemptFacilities = trxValue.getExemptFacilityGroup().getExemptFacility();
//                    DefaultLogger.debug(this, "ExemptFacilities =  "+exemptFacilities);
//                }
//
//                String trxStatus = trxValue.getStatus();
//                DefaultLogger.debug(this, ">>>>>> event = " + event);
//                DefaultLogger.debug(this, ">>>>>> trxStatus = " + trxStatus);
//                if(ExemptFacilityAction.EVENT_MAKER_EDIT.equals(event)) {
//                    DefaultLogger.debug(this, ">>>>>>>>>>> isEdit = " + isEdit);
//                    if(isEdit && !(ICMSConstant.STATE_ACTIVE.equals(trxStatus))) {
//                            resultMap.put("wip", "wip");
//                    }
//                }
//
//                HashMap exemptFacilityMap = new HashMap();
//                exemptFacilityMap.put("exemptFacilityList", (isEdit) ? trxValue.getExemptFacilityGroup() : trxValue.getStagingExemptFacilityGroup());
//                resultMap.put("exemptFacilityMap", exemptFacilityMap);
//
//            }
        try{

        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Going out of doExecute()");
        resultMap.put("isRejected", isRejected);
        resultMap.put("exemptFacility", fac);
        resultMap.put("exemptFacilityTrxValue", trxValue);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }

}