package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

public class CheckerReadCustGrpIdentifierCommand extends AbstractCommand {


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
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", FORM_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"TrxId", "java.lang.String", REQUEST_SCOPE},
        });
    }


    public String[][] getResultDescriptor() {
        return new String[][]{
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
        };
    }

    public HashMap doExecute(HashMap inputMap) throws CommandValidationException, CommandProcessingException, AccessDeniedException {

        DefaultLogger.debug(this, "inside doExecute ");
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String event = (String) inputMap.get("event");
        String trxId = (String) inputMap.get("TrxId");
        String from_event = (String) inputMap.get("from_event");

        Debug("Event  = " + event);
        Debug("trxId  = " + trxId);

        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap .get("theOBTrxContext");
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);

        if (trxValue != null) {
            trxId = trxValue.getTransactionID();
            trxId = (trxId != null) ? trxId.trim() : trxId;
        }
        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        ICustGrpIdentifier stagingObj = null;

        try {
            if (trxValue == null) {
                trxValue = proxy.getCustGrpIdentifierByTrxID(theOBTrxContext, trxId);
                stagingObj = trxValue.getStagingCustGrpIdentifier();
                CustGroupUIHelper.printDependantsRecords(stagingObj);
            }
            resultMap.put("event", event);
            resultMap.put(CustGroupUIHelper.service_groupTrxValue, trxValue);
            resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, stagingObj);

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }


        resultMap.put("event", event);

        DefaultLogger.debug(this, "Existing doExecute ");

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"CheckerReadCustGrpIdentifierCommand = " + msg);
    }
}
