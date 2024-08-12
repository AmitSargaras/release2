package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.*;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

public class CheckerReadCounterpartyCommand extends AbstractCommand {


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
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", FORM_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"cciNo", "java.lang.String", REQUEST_SCOPE},
                {"TrxId", "java.lang.String", REQUEST_SCOPE},
        });
    }


    public String[][] getResultDescriptor() {
        return new String[][]{
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", FORM_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", FORM_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},

        };
    }

    public HashMap doExecute(HashMap inputMap) throws CommandValidationException, CommandProcessingException, AccessDeniedException {


        DefaultLogger.debug(this, " Inside doExecute");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
        ICCICounterpartyDetails aICCICounterpartyDetails = null;

        String event = (String) inputMap.get("event");
        String cciNo = (String) inputMap.get("cciNo");
        String trxId = (String) inputMap.get("TrxId");
        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap .get("theOBTrxContext");
        ICCICounterpartyDetailsTrxValue trxValue = (ICCICounterpartyDetailsTrxValue) inputMap.get("ICCICounterpartyDetailsTrxValue");

        //System.out.println("CheckerReadCounterpartyCommand cciNo = " + cciNo);
        //System.out.println("CheckerReadCounterpartyCommand event = " + event);
        //System.out.println("CheckerReadCounterpartyCommand trxId = " + trxId);


        if (trxValue != null) {
            trxId = trxValue.getTransactionID();
            trxId = (trxId != null) ? trxId.trim() : trxId;
        }

       // System.out.println("CheckerReadCounterpartyCommand trxId = " + trxId);

        try {
            if (trxValue == null) {
                DefaultLogger.debug(this, " getCCICounterpartyDetailsByTrxID getting by trx id " + trxId);
                trxValue = custproxy.getCCICounterpartyDetailsByTrxID(theOBTrxContext, trxId);
            }
  
            resultMap.put("event", event);
            resultMap.put("ICCICounterpartyDetailsTrxValue", trxValue);
            resultMap.put("ICCICounterpartyDetails", aICCICounterpartyDetails);
            resultMap.put("session.ICCICounterpartyDetails", aICCICounterpartyDetails);

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, " Existing doExecute()");

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


}
