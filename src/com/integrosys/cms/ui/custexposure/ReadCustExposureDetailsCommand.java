package com.integrosys.cms.ui.custexposure;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.custexposure.bus.*;
import com.integrosys.cms.app.custexposure.proxy.CustExposureProxyFactory;
import com.integrosys.cms.app.custexposure.proxy.ICustExposureProxy;
import com.integrosys.cms.app.custexposure.trx.IExposureTrxValue;
import com.integrosys.cms.app.custexposure.trx.OBExposureTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custexposure.CustExposureDetailsCommandHelper;
import com.integrosys.cms.app.custexposure.bus.ICustExposureEntityRelationship;
import com.integrosys.cms.app.custexposure.bus.OBCustExposureEntityRelationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements command
 */
public class ReadCustExposureDetailsCommand extends AbstractCommand {

    public ReadCustExposureDetailsCommand() {

    }

    /**
     * Defines a two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"sub_profile_id", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"grpID", "java.lang.String", REQUEST_SCOPE},
                {"session.grpID", "java.lang.String", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustExposureUIHelper.service_CustExposureTrxValue, "com.integrosys.cms.app.custexposure.trx.IExposureTrxValue", SERVICE_SCOPE},
                {CustExposureUIHelper.service_CustExposureSearchListObj, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {IGlobalConstant.GLOBAL_CUSTEXPOSURE_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custexposure.bus.CustExposureSearchCriteria", GLOBAL_SCOPE},
        });
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"ICustExposure", "com.integrosys.cms.app.custexposure.bus.ICustExposure", FORM_SCOPE},
                {"session.ICustExposure", "com.integrosys.cms.app.custexposure.bus.ICustExposure", SERVICE_SCOPE},
                {"sub_profile_id", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"grpID", "java.lang.String", REQUEST_SCOPE},
                {"session.grpID", "java.lang.String", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustExposureUIHelper.service_CustExposureTrxValue, "com.integrosys.cms.app.custexposure.trx.IExposureTrxValue", SERVICE_SCOPE},
                {CustExposureUIHelper.service_CustExposureSearchListObj, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {IGlobalConstant.GLOBAL_CUSTEXPOSURE_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custexposure.bus.CustExposureSearchCriteria", GLOBAL_SCOPE},
                {IGlobalConstant.GLOBAL_CUSTEXPOSURE_SUB_PROFILE_ID, "java.lang.String", GLOBAL_SCOPE},
        }
        );
    }


    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String sub_profile_id_str = (String) inputMap.get("sub_profile_id");
        String from_event = (String) inputMap.get("from_event");
        String event = (String) inputMap.get("event");
        String grpID = (String) inputMap.get("grpID");
        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        IExposureTrxValue trxValue = (IExposureTrxValue) inputMap.get(CustExposureUIHelper.service_CustExposureTrxValue);
        if (trxValue == null){
            trxValue = new OBExposureTrxValue();
        }

        ICustExposure aICustExposure = new OBCustExposure();

        try {
            long subProfileID = (sub_profile_id_str == null) ? 0 : Long.parseLong(sub_profile_id_str);

            if (subProfileID != 0){

                Debug("getCustomer by >>>>>>>>>> " + subProfileID);
                CustExposureUIHelper.getCustomer(subProfileID, aICustExposure);
                
                CustExposureDetailsCommandHelper h = new CustExposureDetailsCommandHelper();
              
                aICustExposure = h.getCustExposure(theOBTrxContext, subProfileID);
                
                ICustExposureEntityRelationship[] entityRelationship = aICustExposure.getCustExposureEntityRelationship();
                
                IContingentLiabilities[] contingentData = aICustExposure.getContingentLiabilities(); 
                
                ICustExposureGroupRelationship[] groupRelationship = aICustExposure.getCustExposureGroupRelationship(); 
                
                if (from_event != null && !from_event.equals("")){
                    resultMap.put(IGlobalConstant.GLOBAL_CUSTEXPOSURE_SUB_PROFILE_ID, sub_profile_id_str);
                }
            }

            trxValue.setCustExposure(aICustExposure);
            resultMap.put("sub_profile_id", sub_profile_id_str);
            resultMap.put("ICustExposure", aICustExposure);
            resultMap.put("session.ICustExposure", aICustExposure);
            resultMap.put("event",event);
            resultMap.put("grpID",grpID);
            resultMap.put("session.grpID",grpID);
            resultMap.put("from_event",event);
            resultMap.put(CustExposureUIHelper.service_CustExposureTrxValue, trxValue);
            resultMap.put(CustExposureUIHelper.service_groupResult, resultMap.get(CustExposureUIHelper.service_groupResult));

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Existing doExecute()\n");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    /**
     * @param msg
     */
    private void Debug(String msg) {
    	DefaultLogger.debug(this,ReadCustExposureDetailsCommand.class.getName() + " = " + msg);
    }


}
