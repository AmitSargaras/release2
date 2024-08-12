package com.integrosys.cms.ui.cci;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.OBCCICounterparty;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrepareCounterpartyCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
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
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", FORM_SCOPE},
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
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

        DefaultLogger.debug(this, "Inside  doExecute()");
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String) inputMap.get("event");
        resultMap.put("ICCICounterpartyDetails", null);
        resultMap.put("ICCICounterpartyDetailsTrxValue", null);
        resultMap.put("session.ICCICounterpartyDetails", null);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;
    }

    private ICCICounterparty getICCICounterparty(OBCustomerSearchResult col) {
        ICCICounterparty iICCICounterparty = new OBCCICounterparty();
        iICCICounterparty.setCustomerName(col.getCustomerName());
        iICCICounterparty.setLegalID(col.getLegalID());
        iICCICounterparty.setSubProfileID(col.getSubProfileID());
        return iICCICounterparty;

    }


    private ICCICounterparty getOBCustomerSearchResult(SearchResult customerlist, String customerID) {

        ICCICounterparty obj = null;
        Debug("result.getResultList().size() = " + customerlist.getResultList().size());
        List v = (List) customerlist.getResultList();
        if (v != null) {
            for (int i = 0; i < v.size(); i++) {
                OBCustomerSearchResult col = (OBCustomerSearchResult) v.get(i);
                if (customerID != null && customerID.equals(col.getSubProfileID() + "")) {
                    Debug("To be Added New Customer Name" + col.getCustomerName());
                    obj = getICCICounterparty(col);
                    return obj;
                }
            }
        }
        return obj;

    }

    private ICCICounterparty[] getICCICounterpartyList(ICCICounterparty[] iCCICounterpartyList, ICCICounterparty obj) {
        List list = new ArrayList();
        if (iCCICounterpartyList != null) {
            for (int i = 0; i < iCCICounterpartyList.length; i++) {
                list.add(iCCICounterpartyList[i]);
            }
            list.add(obj);
        }
        return (ICCICounterparty[]) list.toArray(new ICCICounterparty[0]);

    }


    private void Debug(String msg) {
        //System.out.println("PrepareUpdateCounterpartyCommand = " + msg);
    }


}
