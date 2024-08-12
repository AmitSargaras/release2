package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.proxy.CustRelationshipProxyFactory;
import com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.custrelationship.trx.OBCustRelationshipTrxValue;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.app.custrelationship.trx.shareholder.OBCustShareholderTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.custrelationship.shareholder.ShareHolderListAction;
import com.integrosys.cms.ui.custrelationship.shareholder.ShareHolderListForm;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 7, 2007
 * Time: 7:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadGroupMemberCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"sub_profile_id", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
        });
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
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the length.
                {"length", "java.lang.Integer", SERVICE_SCOPE}, // To populate the form.
                {ShareHolderListForm.MAPPER, "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", FORM_SCOPE},
                {"CustShareHolderTrxValue", "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE},
                {"CustRelationshipTrxValue", "com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE},
        });
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        String indexID = (String) map.get("indexID");
        String event = (String) map.get("event");
        String from_event = (String) map.get("from_event");
        String parentSubProfileIDStr = (String) map.get("sub_profile_id");
        OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");

        ICustShareholderTrxValue trxValue = null;
        ICustRelationshipTrxValue trxValue1 = null;
        ICustRelationshipProxy custRelaionshipProxy = CustRelationshipProxyFactory.getProxy();


        try {
            long parentSubProfileID = (parentSubProfileIDStr == null) ? 0 : Long.parseLong(parentSubProfileIDStr);
            Debug("sub_profile_id" + parentSubProfileID);

            try {
                trxValue =  custRelaionshipProxy.getCustShareholderTrxValue(theOBTrxContext, parentSubProfileID);
                trxValue1 = custRelaionshipProxy.getCustRelationshipTrxValue(theOBTrxContext, parentSubProfileID);
             } catch (Exception e) {
                DefaultLogger.error(this, "Exception caught when calling  custRelaionshipProxy()", e);
            }

            Debug("after getting customer relationship trx from proxy.");


           // DefaultLogger.debug(this, "trxValue : " + trxValue);

            if (trxValue == null){
                trxValue = new OBCustShareholderTrxValue();
                trxValue.setParentSubProfileID(parentSubProfileID);
            }
            if (trxValue.getCustShareholder() == null){
                trxValue.setCustShareholder(new ICustShareholder[0]);
            }

             if (trxValue1 == null){
                trxValue1 = new OBCustRelationshipTrxValue();
                trxValue1.setParentSubProfileID(parentSubProfileID);
            }
            if (trxValue1.getCustRelationship() == null){
                trxValue1.setCustRelationship(new ICustRelationship[0]);
            }

            // if sub profile id is not in request scope...
            // get it from trxValue if available
            if (parentSubProfileID == 0 && trxValue.getParentSubProfileID() != ICMSConstant.LONG_INVALID_VALUE){
                parentSubProfileID = trxValue.getParentSubProfileID();
                parentSubProfileIDStr = String.valueOf(parentSubProfileID);
            }
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        result.put("CustShareHolderTrxValue", trxValue);
        result.put("CustRelationshipTrxValue", trxValue1);
        result.put("offset", new Integer(0));
        result.put("length", new Integer(10));
        result.put("sub_profile_id", parentSubProfileIDStr);


        String return_event = event;
        if ("maker_close_cgid".equals(from_event)){
            return_event = "maker_close_return";
        } else if ("checker_edit_cgid".equals(from_event)){
            return_event = "process_return";
        } else if ("checker2_edit_cgid".equals(from_event)){
            return_event = "process2_return";
        } else if ("to_track".equals(from_event) || "track_return".equals(from_event)){
            return_event = "track_return";
        } else if ("maker2_edit".equals(from_event) || "maker2_edit_return".equals(from_event)){
            return_event = "maker2_edit_return";
        }else  if ("process2_update".equals(from_event) || "process2_update_return".equals(from_event)
                 || "prepare2".equals(from_event)       || "prepare2_update".equals(from_event)
                 ) {
            return_event = "process2_update_return";
        } else if ("maker2_close_cgid".equals(from_event) || "maker2_close_return".equals(from_event)){
            return_event = "maker2_close_return";
        }else  if ("maker2_edit_cgid_reject".equals(from_event) || "maker2_edit_cgid_reject_return".equals(from_event)) {
            return_event = "maker2_edit_cgid_reject_return";
         }else  if ("prepare_delete".equals(from_event) || "prepare_delete_return".equals(from_event)) {
            return_event = "prepare_delete_return";
         }

        Debug(" $$$$$$$$$$$$$$ Event = " + event + ",  return_event= " + return_event);
        result.put("event", return_event);
        result.put("indexID", indexID);
        result.put("itemType", CustGroupUIHelper.GROUPMEMBER);
        result.put("from_event", from_event);
        result.put(CustGroupUIHelper.service_groupTrxValue, map.get(CustGroupUIHelper.service_groupTrxValue));

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return temp;
    }





    private void Debug(String msg) {
    	DefaultLogger.debug(this,ReadGroupMemberCommand.class.getName() +" = " + msg);
    }

}
