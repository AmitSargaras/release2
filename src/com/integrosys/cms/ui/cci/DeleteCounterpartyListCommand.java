package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.OBCCICounterpartyDetails;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.trx.OBCCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.HashMap;
import java.util.List;

public class DeleteCounterpartyListCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"chkDeletes", "java.lang.String", REQUEST_SCOPE},
                {"limitProfileID", "java.lang.String", REQUEST_SCOPE},
                {"groupCCINo", "java.lang.String", REQUEST_SCOPE},
                {"ICCICounterparty", "com.integrosys.cms.app.cci.bus.ICCICounterparty", FORM_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
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
                {"request.ITrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", REQUEST_SCOPE},
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
        SearchResult customerlist = (SearchResult) inputMap.get("session.customerlist");

        String customerID = (String) inputMap.get("chkDeletes");
        String groupCCINo = (String) inputMap.get("groupCCINo");
        String limitProfileID = (String) inputMap.get("limitProfileID");

         // Session-scoped trx value.
            ICCICounterpartyDetailsTrxValue value = (ICCICounterpartyDetailsTrxValue) inputMap.get("ICCICounterpartyDetailsTrxValue");

            if (value == null) {
                value = new OBCCICounterpartyDetailsTrxValue();
            }

        //ICCICounterpartyDetails CounterpartyDetails = value.getStagingCCICounterpartyDetails();
        ITrxContext trxContext = (ITrxContext) inputMap.get("theOBTrxContext");
        ICCICounterpartyDetailsTrxValue resultValue = null;
        ICCICustomerProxy proxy = CCICustomerProxyFactory.getProxy();
        ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();

        try {

            if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
            } else {
                ICCICounterpartyDetails actualDetails = custproxy.getCCICounterpartyDetails(groupCCINo);
                ICCICounterpartyDetails stagingDetails = getICCICounterpartyList(actualDetails, customerlist,groupCCINo ) ;
                value.setStagingCCICounterpartyDetails(stagingDetails) ;
                value.setCCICounterpartyDetails(actualDetails) ;
                resultValue = proxy.makerDeleteICCICustomer(trxContext, value, value.getStagingCCICounterpartyDetails());
            }

            resultMap.put("request.ITrxValue", resultValue);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;
    }




    private  ICCICounterpartyDetails   getICCICounterpartyList(ICCICounterpartyDetails actualDetails, SearchResult customerlist , String groupCCINo) {

        ICCICounterpartyDetails stagingDetails = new OBCCICounterpartyDetails();
        AccessorUtil.copyValue(actualDetails, stagingDetails);
        ICCICounterparty[]  stagingParty = stagingDetails.getICCICounterparty() ;
        if (stagingParty != null && stagingParty.length > 0) {
            for (int index = 0; index < stagingParty.length; index++) {
                ICCICounterparty OB = stagingParty[index];
                OB.setDeletedInd(true);
            }
        }
        actualDetails.setICCICounterparty(stagingParty);
        return actualDetails ;
    }


    private void Debug(String msg) {
        //System.out.println("DeleteCounterpartyListCommand = " + msg);
    }


    private void printList(ICCICounterparty[] iCCICounterpartyList) {
        if (iCCICounterpartyList != null) {
            for (int i = 0; i < iCCICounterpartyList.length; i++) {
                Debug("printList getCustomerName = " + iCCICounterpartyList[i].getCustomerName());
            }
        }

    }

}
