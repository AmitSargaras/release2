package com.integrosys.cms.ui.cci;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.OBCCICounterparty;
import com.integrosys.cms.app.cci.bus.OBCCICounterpartyDetails;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddCounterpartyListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                //{"EntityLimitTrxValue", "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"offset", "java.lang.Integer", SERVICE_SCOPE},
                {"length", "java.lang.Integer", SERVICE_SCOPE},
                {"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", GLOBAL_SCOPE},
                {"ICCICounterparty", "com.integrosys.cms.app.cci.bus.ICCICounterparty", FORM_SCOPE},
                //{"customerID", "java.lang.String", REQUEST_SCOPE},
                {CounterpartySearchForm.MAPPER, "java.lang.Object", FORM_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"counterpartyList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", GLOBAL_SCOPE},
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", FORM_SCOPE},
                //{"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"offset", "java.lang.Integer", SERVICE_SCOPE},
                {"length", "java.lang.Integer", SERVICE_SCOPE},
                //{CounterpartySearchForm.MAPPER, "java.util.List", FORM_SCOPE}
        });
    }

    public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {

//        DefaultLogger.debug(this, "Map is " + map);

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        List tempList = new ArrayList();

        SearchResult customerlist = (SearchResult) map.get("session.customerlist");
        ICCICounterpartyDetails details = (ICCICounterpartyDetails) map.get("session.ICCICounterpartyDetails");
        ICCICounterpartyDetailsTrxValue trxValue = (ICCICounterpartyDetailsTrxValue) map.get("ICCICounterpartyDetailsTrxValue");

//        List selectedCustList = (List)map.get(CounterpartySearchForm.MAPPER);
//        String[] customerIDArr = (String[])selectedCustList.get(0);

        String[] customerIDArr = (String[]) map.get(CounterpartySearchForm.MAPPER);

        if (customerIDArr == null || customerIDArr.length == 0) {
            exceptionMap.put("errDuplicate", new ActionMessage("error.no.selection", " customer"));
        }

        ICCICounterparty[] objlist = null;

        if (details != null) {
            objlist = details.getICCICounterparty();
        } else {
            if (trxValue != null) {
                details = trxValue.getStagingCCICounterpartyDetails();
            } else {
                details = new OBCCICounterpartyDetails();
            }
            objlist = details.getICCICounterparty();
        }

/*
        if (details == null) {
            details = new OBCCICounterpartyDetails();
        }
*/
        DefaultLogger.error(this, ">>>>>>>>>>objlist is " + objlist);

        for (int i = 0; objlist != null && i < objlist.length; tempList.add(objlist[i++])) ;

        try {

            if (exceptionMap.isEmpty()) {

                List errList = new ArrayList();
                for (int i = 0; i < customerIDArr.length; i++) {
                    ICCICounterparty newObj = getCounterparty(customerlist, customerIDArr[i]);
//                    objlist = details.getICCICounterparty();

                    if (!isExist(tempList, newObj)) {
                        tempList.add(newObj);
                    } else {
                        StringBuffer sb = new StringBuffer();
                        sb.append("[").append(newObj.getLmpLeID()).append("]").append("Duplicate CIF No.");
                        errList.add(sb.toString());
                    }
                }

                if (errList.isEmpty()) {
                    List subIdList = new ArrayList();
                    for (int i = 0; i < customerIDArr.length; i++) {
                        ICCICounterparty cp = getCounterparty(customerlist, customerIDArr[i]);
                        subIdList.add("" + cp.getSubProfileID());
                    }
                    ICCICustomerProxy proxy = CCICustomerProxyFactory.getProxy();
                    String[] subId = (String[]) subIdList.toArray(new String[subIdList.size()]);
                    HashMap emap = proxy.isExistCCICustomer(ICMSConstant.LONG_INVALID_VALUE, subId);
                    if (ICMSConstant.TRUE_VALUE.equals(emap.get("isExistCCICustomer"))) {
                        errList = (List) emap.get("ERRORMSG");
                    }
                }

                if (errList.isEmpty()) {
                    DefaultLogger.debug(this, ">>>>>errList.isEmpty()");
                    objlist = (ICCICounterparty[]) tempList.toArray(new ICCICounterparty[tempList.size()]);
                    details.setICCICounterparty(objlist);
                    //trxValue.setCCICounterpartyDetails(details);
                } else {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < errList.size(); i++) {
                        sb.append(errList.get(i)).append("<br>");
                    }
                    exceptionMap.put("errDuplicate", new ActionMessage("error.string.cci.errormsg", sb.toString()));
                }

/*
                if (objlist!=null) {
                    DefaultLogger.error(this, ">>>>>>>>>>objlist.length is " + objlist.length);
                    if (objlist.length>0)
                        DefaultLogger.error(this, ">>>>>>>>>>objlist[0].getGroupCCINo is " + objlist[0].getGroupCCINo());
                }
*/

                resultMap.put("counterpartyList", customerlist);
                resultMap.put("session.customerlist", customerlist);
                resultMap.put("ICCICounterpartyDetails", details);
                resultMap.put("session.ICCICounterpartyDetails", details);
                resultMap.put("ICCICounterpartyDetailsTrxValue", trxValue);

            }

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }


        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }

    private ICCICounterparty getCounterparty(SearchResult customerlist, String customerID) {
        ICCICounterparty obj = null;
        if (customerID == null) {
            return null;
        }

        List v = (List) customerlist.getResultList();
        if (v != null) {
            for (int i = 0; i < v.size(); i++) {
                OBCustomerSearchResult col = (OBCustomerSearchResult) v.get(i);

                if (customerID != null && customerID.equals(col.getSubProfileID() + "")) {
//                    Debug(" to be add Customer Name = " + col.getCustomerName());
                    obj = createObjectFromResult(col);
                    return obj;
                }
            }
        }
        return obj;

    }

    private ICCICounterparty createObjectFromResult(OBCustomerSearchResult col) {
        ICCICounterparty obj = null;
        if (col == null) {
            return obj;
        } else {
            obj = new OBCCICounterparty();
            obj.setLegalName(col.getLegalName());
            obj.setCustomerName(col.getCustomerName());
            obj.setSourceID(col.getSourceID());

            DefaultLogger.debug(this, "AddCounterParty Source ID = " + obj.getSourceID());

            obj.setLegalID(col.getLegalID());
            obj.setIdNO(col.getIdNo());
            obj.setLeIDType(col.getIdType());
            obj.setLmpLeID(col.getLmpLeID());

            DefaultLogger.debug(this, "AddCounterParty CIF ID 1 = " + obj.getLegalID());
            DefaultLogger.debug(this, "AddCounterParty CIF ID 2 = " + col.getLegalReference());

            obj.setIncorporationDate(col.getIncorporationDate());
            obj.setDob(col.getDob());

            // Store Address
            obj.setAddress(col.getAddress());

            // Set CMS_CUSTOMER_ID
            obj.setSubProfileID(col.getSubProfileID());
        }
        return obj;

    }

    private boolean isExist(List tempList, ICCICounterparty obj) {
        if (tempList.isEmpty() || obj == null)
            return false;

        for (int i = 0; i < tempList.size(); i++) {
            ICCICounterparty cp = (ICCICounterparty) tempList.get(i);
            //Debug(list[i].getSubProfileID() + "[ " + list[i].getLeIDType() + "] = " + obj.getSubProfileID() + "[ " + obj.getLeIDType() + "]");
            if (!cp.getDeletedInd() && cp.getLmpLeID() != null && obj.getLmpLeID() != null) {
                if (cp.getLmpLeID().equals(obj.getLmpLeID()))
                    return true;
            }
        }

        return false;
    }
}
