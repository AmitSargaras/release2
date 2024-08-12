package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.common.util.CommonUtil;

import java.util.HashMap;

/**
 * This class implements command
 */
public class EditCustGrpIdentifierCommand extends AbstractCommand {

    public EditCustGrpIdentifierCommand() {

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
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
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
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
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
        String grpID = (String) inputMap.get("grpID");
        String from_event = (String) inputMap.get("from_event");

        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        ICustGrpIdentifier form_custGrpIdentifierObj = (ICustGrpIdentifier) inputMap.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue);

        Debug("event = " + event);
        Debug("from_event = " + from_event);
        Debug("grpID = " + grpID);

        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        ICustGrpIdentifier staging  = null;

        try {
            if (trxValue != null) {
                ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
                staging = (ICustGrpIdentifier) CommonUtil.deepClone(actual);
                trxValue.setStagingCustGrpIdentifier(staging);

                /* if (from_event != null && from_event.equals("read")) {
                    resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, trxValue.getCustGrpIdentifier());
                    resultMap.put("event", from_event);
                } else {
                    resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, trxValue.getStagingCustGrpIdentifier());
                    resultMap.put("event", "update");
                }
                ICustGrpIdentifier checkOBj = trxValue.getStagingCustGrpIdentifier();
                if (checkOBj != null) {
                    System.out.println("checkOBj.get = " + checkOBj.getGroupName());
                } else {
                    System.out.println("checkOBj.getGroupName is  null");
                }*/

                trxValue.setHasLimitBooking(CustGroupUIHelper.groupHasLimitBooking(trxValue));
                
            } else {
                trxValue = new OBCustGrpIdentifierTrxValue();
//                System.out.println(" ICustGrpIdentifierTrxValue is null");

            }
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        resultMap.put(CustGroupUIHelper.form_custGrpIdentifierObj, staging);
        resultMap.put(CustGroupUIHelper.service_groupTrxValue, trxValue);
        resultMap.put("event", event);

        DefaultLogger.debug(this, "Existing doExecute()");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"ReadCustGrpIdentifierCommand = " + msg);
    }


}