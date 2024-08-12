package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * This class implements command
 */
public class ReadCustGrpIdentifierCommand extends AbstractCommand {

    public ReadCustGrpIdentifierCommand() {

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
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"TrxId", "java.lang.String", REQUEST_SCOPE},
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
                {"event", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
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
        String trxId = (String) inputMap.get("TrxId");
        String grpID = (String) inputMap.get("grpID");

        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        ICustGrpIdentifier form_custGrpIdentifierObj = (ICustGrpIdentifier) inputMap.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);

        if (trxValue != null) {
            trxId = trxValue.getTransactionID();
        }

        if (trxValue == null) {
            trxValue = new OBCustGrpIdentifierTrxValue();
        }

        Debug("event = " + event);
        Debug("trxId = " + trxId);
        Debug("grpID = " + grpID);

        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();

        try {

            if (grpID != null && !"".equals(grpID)) {
                Debug("getting by grpID " + grpID);
                trxValue = proxy.getCustGrpIdentifierByGrpID(theOBTrxContext, grpID);
            } else if (trxId != null && !"".equals(trxId)) {
                Debug("getting by trx id.");
                trxValue = proxy.getCustGrpIdentifierByTrxID(theOBTrxContext, trxId);
            }


            if (trxValue == null) {
                trxValue = new OBCustGrpIdentifierTrxValue();
            }

            if (event != null && event.equals("read")) {
                form_custGrpIdentifierObj = trxValue.getCustGrpIdentifier();
            } else if (event != null && event.equals("prepare_delete")) {
                form_custGrpIdentifierObj = trxValue.getCustGrpIdentifier();
            } else {
                form_custGrpIdentifierObj = trxValue.getStagingCustGrpIdentifier();
            }


        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }


        resultMap.put("event", event);
        resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, form_custGrpIdentifierObj);
        resultMap.put(CustGroupUIHelper.service_groupTrxValue, trxValue);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");


        return temp;
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"ReadCustGrpIdentifierCommand = " + msg);
    }


}
