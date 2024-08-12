package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;

import java.util.HashMap;


public class ReturnCustGrpIdentifierCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public ReturnCustGrpIdentifierCommand() {

    }

    /**
     * Defines a two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
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
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
//                   {CustGroupUIHelper.form_custGrpIdentifierObj, "java.lang.Object", FORM_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
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
        DefaultLogger.debug(this, "Inside of doExecute()");

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        String event = (String) map.get("event");
        String from_event = (String) map.get("from_event");

        ICustGrpIdentifierTrxValue itrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);

        if (itrxValue != null) {
            if (from_event != null && from_event.equals("read")) {
                result.put(CustGroupUIHelper.form_custGrpIdentifierObj, itrxValue.getCustGrpIdentifier());
                result.put("event", from_event);
            } else {
                result.put(CustGroupUIHelper.form_custGrpIdentifierObj, itrxValue.getStagingCustGrpIdentifier());
                result.put("event", "update");
            }
            ICustGrpIdentifier checkOBj = itrxValue.getStagingCustGrpIdentifier();
            if (checkOBj != null) {
            	DefaultLogger.debug(this,"checkOBj.get = " + checkOBj.getGroupName());
            } else {
            	DefaultLogger.debug(this,"checkOBj.getGroupName is  null");
            }

        } else {
        	DefaultLogger.debug(this," ICustGrpIdentifierTrxValue is null");

        }
        result.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);
        result.put("event", event);

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Going out of doExecute()");
        return temp;

    }

}
