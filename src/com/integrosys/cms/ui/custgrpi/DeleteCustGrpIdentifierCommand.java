package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.struts.action.ActionMessage;

public class DeleteCustGrpIdentifierCommand extends AbstractCommand {


   public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"grpID", "java.lang.String", REQUEST_SCOPE},
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
                {"request.ITrxValue", "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
        }
        );
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap inputMap) throws CommandValidationException, CommandProcessingException, AccessDeniedException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String event = (String) inputMap.get("event");
        String trxId = (String) inputMap.get("TrxId");
        String grpID = (String) inputMap.get("grpID");

        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        ICustGrpIdentifier form_custGrpIdentifierObj = (ICustGrpIdentifier) inputMap.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);

         ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
         ICustGrpIdentifier staging   = null;
        try {
//            if (trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
//            } else {
            ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
            List groupIDList = new ArrayList();
            groupIDList.add(new Long(actual.getMasterGroupEntityID()).toString());

            if (proxy.groupHasLimitBooking(groupIDList)) {
                exceptionMap.put("hasLimitBookErr", new ActionMessage("error.group.haslimitbooking"));
            }

            if (exceptionMap.isEmpty()) {
                staging = (ICustGrpIdentifier) CommonUtil.deepClone(actual);
                staging.setStatus(ICMSConstant.STATE_DELETED);
                trxValue.setStagingCustGrpIdentifier(staging);
                trxValue = proxy.makerDeleteCustGrpIdentifier(theOBTrxContext, trxValue, trxValue.getStagingCustGrpIdentifier());
            }
//            }

            resultMap.put("request.ITrxValue", trxValue);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, trxValue.getCustGrpIdentifier());
        resultMap.put(CustGroupUIHelper.service_groupTrxValue, trxValue);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return temp;
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"DeleteCounterpartyListCommand = " + msg);
    }


}
