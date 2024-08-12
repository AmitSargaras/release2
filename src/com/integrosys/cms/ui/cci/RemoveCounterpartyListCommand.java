package com.integrosys.cms.ui.cci;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.OBCCICounterpartyDetails;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RemoveCounterpartyListCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"DeleteCounterpartyListMapper", "java.util.List", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"chkDeletes", "java.lang.String", REQUEST_SCOPE},
                {"ICCICounterparty", "com.integrosys.cms.app.cci.bus.ICCICounterparty", FORM_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"maker_process_event", "java.lang.String", GLOBAL_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
        };
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
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", FORM_SCOPE},
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"maker_process_event", "java.lang.String", GLOBAL_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
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
        HashMap returnMap = new HashMap();

        List inputList = (List) inputMap.get("DeleteCounterpartyListMapper");
        String customerID = (String) inputMap.get("chkDeletes");
        String maker_process_event = (String) inputMap.get("maker_process_event");
        String event = (String) inputMap.get("event");
        ICCICounterpartyDetails details = (ICCICounterpartyDetails) inputMap.get("session.ICCICounterpartyDetails");
        Debug("customerID = " + customerID);

        ICCICounterparty[]  list = null;
        String[] chkDeletesArr = (String[]) inputList.get(0);
        Debug("chkDeletes = " + chkDeletesArr);

        try {
            Debug("customerID  to be deleted = " + customerID);
            if (details != null) {
                list = getICCICounterpartyList(details, chkDeletesArr);
            } else {
                details = new OBCCICounterpartyDetails();
            }

            details.setICCICounterparty(list);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        Debug("maker_process_event = " + maker_process_event);
        resultMap.put("event", event);
        resultMap.put("maker_process_event", maker_process_event);
        resultMap.put("ICCICounterpartyDetails", details);
        resultMap.put("session.ICCICounterpartyDetails", details);
        resultMap.put("ICCICounterpartyDetailsTrxValue",(ICCICounterpartyDetailsTrxValue)inputMap.get("ICCICounterpartyDetailsTrxValue"));
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;
    }


    /**
     * Helper method to delete the record
     * @param iCCICounterpartyDetails
     * @return ICCICounterparty
     */


    private ICCICounterparty[]   getICCICounterpartyList(ICCICounterpartyDetails iCCICounterpartyDetails, String[] chkDeletesArr) {

        ICCICounterparty[]  objList = iCCICounterpartyDetails.getICCICounterparty();
        List newList = new ArrayList();
        if (objList != null && objList.length > 0) {
            for (int i = 0; i < objList.length; i++) {
                ICCICounterparty obj = objList[i];
                if (chkDeletesArr != null) {
                    for (int j = 0; j < chkDeletesArr.length; j++) {
                        if (chkDeletesArr[j] != null && chkDeletesArr[j].equals(obj.getSubProfileID() + "")) {
                            obj.setDeletedInd(true);
                        }
                    }
                }
                // Filter new records  which are added by user recently and deleted
                if (obj.getGroupCCIMapID() == ICMSConstant.LONG_INVALID_VALUE && obj.getDeletedInd()) {
                    Debug("New Records deleted by User in jsp = " + obj.getLegalName());
                /*} else if (counterparty.getGroupCCIMapID() != ICMSConstant.LONG_INVALID_VALUE
                        && counterparty.getDeletedInd()) {
                    Debug("DataBase Records deleted by User in jsp = " + counterparty.getLegalName());*/
                } else {
                    // add all records to database
                    newList.add(obj);
                }

            }
        }
        return (ICCICounterparty[]) newList.toArray(new ICCICounterparty[0]);

    }

    /**
     * helper   method to print
     * @param msg
     */

    private void Debug(String msg) {
        System.out.println("RemoveCounterpartyListCommand = " + msg);
    }


}
