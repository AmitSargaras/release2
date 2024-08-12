package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCustGrpIdentifierCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"customerID", "java.lang.String", REQUEST_SCOPE},
                {"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
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
                {"counterpartyList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
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
        List list = new ArrayList();

        String event = (String) inputMap.get("event");
        String customerID = (String) inputMap.get("customerID");
        SearchResult customerlist = (SearchResult) inputMap.get("session.customerlist");
        Debug("customerID = " + customerID);

        try {
            if (customerlist != null && customerlist.getResultList() != null) {
            } else {
                Debug("customerlist is null ");
            }


        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }


        resultMap.put("counterpartyList", customerlist);
        resultMap.put("session.customerlist", customerlist);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"AddCounterpartyListCommand = " + msg);
    }


}