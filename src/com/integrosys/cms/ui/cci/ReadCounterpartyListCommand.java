package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.trx.OBCCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * This class implements command
 */
public class ReadCounterpartyListCommand extends AbstractCommand {

    public ReadCounterpartyListCommand() {

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
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"TrxId", "java.lang.String", REQUEST_SCOPE},
                {"groupCCINo", "java.lang.String", REQUEST_SCOPE},
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
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", FORM_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE}
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

        String event = (String) inputMap.get("event");
        String groupCCINo = (String) inputMap.get("groupCCINo");
        String trxId = (String) inputMap.get("TrxId");
        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        ICCICounterpartyDetailsTrxValue trxValue = (ICCICounterpartyDetailsTrxValue) inputMap.get("ICCICounterpartyDetailsTrxValue");

        if (trxValue != null) {
            trxId = trxValue.getTransactionID();
        }

        if (trxValue == null) {
            trxValue = new OBCCICounterpartyDetailsTrxValue();
        }


       // System.out.println("ReadCounterpartyListCommand groupCCINo = " + groupCCINo);
        //System.out.println("ReadCounterpartyListCommand event = " + event);
        //System.out.println("ReadCounterpartyListCommand trxId = " + trxId);

        ICCICounterpartyDetails aICCICounterpartyDetails = null;
        ICCICustomerProxy ccproxy = CCICustomerProxyFactory.getProxy();

        try {
            if (groupCCINo != null && !"".equals(groupCCINo)) {
                DefaultLogger.debug(this, "getCCICounterpartyDetailsByGroupCCINo --> getting by GroupCCINo." + groupCCINo);
                trxValue = ccproxy.getCCICounterpartyDetailsByGroupCCINo(theOBTrxContext, groupCCINo);
                if (trxValue != null) {
                    aICCICounterpartyDetails = trxValue.getCCICounterpartyDetails();

                }
            } else {
                if (trxId != null && !"".equals(trxId)) {
                    DefaultLogger.debug(this, "getting by trx id.");
                    trxValue = ccproxy.getCCICounterpartyDetailsByTrxID(theOBTrxContext, trxId);
                } else {
                    DefaultLogger.debug(this, "getCCICounterpartyDetailsByGroupCCINo --> getting by GroupCCINo." + groupCCINo);
                    trxValue = ccproxy.getCCICounterpartyDetailsByGroupCCINo(theOBTrxContext, groupCCINo);
                    if (trxValue != null) {
                        aICCICounterpartyDetails = trxValue.getCCICounterpartyDetails();
                    }

                }
            }
            resultMap.put("event", event);
            resultMap.put("ICCICounterpartyDetailsTrxValue", trxValue);
            resultMap.put("ICCICounterpartyDetails", aICCICounterpartyDetails);
            resultMap.put("session.ICCICounterpartyDetails", aICCICounterpartyDetails);

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Existing doExecute()\n");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


}
