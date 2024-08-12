package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe this class.
 * Purpose: for checker to approve the transaction
 * Description: command that let the checker to approve the transaction that being make by the maker
 *
 * @author $Author: Jitu<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$
 *        Tag: $Name$
 */

public class CheckerApproveEditCustGrpIdentifierCommand extends AbstractCommand implements ICommonEventConstant {

    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */

    public String[][] getParameterDescriptor() {
        return (new String[][]
                {
                        {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                        {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                        {"description", "java.lang.String", REQUEST_SCOPE},   // remarks is missing so setting again

                }
        );
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
        return (new String[][]
                {
                        {"errorGroupNameExist", "java.lang.String", REQUEST_SCOPE},
                        {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
                }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here approval for Liquidation is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();

        String description = (String) map.get("description");
        OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
        ICustGrpIdentifierTrxValue trxVal = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);

        if (trxVal == null) {
            throw (new CommandProcessingException("ICustGrpIdentifierTrxValue is null"));
        }

        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();

        try {
//            if (checkGroupByName(trxVal)) {
//                resultMap.put("errorGroupNameExist", "true");
            ICustGrpIdentifier actual = trxVal.getCustGrpIdentifier();
            long masterGroupEntityID = ICMSConstant.LONG_INVALID_VALUE;
            if (actual != null) {
                masterGroupEntityID = actual.getMasterGroupEntityID();
            }
            ICustGrpIdentifier details = trxVal.getStagingCustGrpIdentifier();
            if (details.getMasterGroupInd() && masterGroupEntityID != ICMSConstant.LONG_INVALID_VALUE) {
                List groupIDList = new ArrayList();
                try {
                    groupIDList.add(new Long(masterGroupEntityID).toString());
                    List bankGroupMasterListSearchResult = proxy.retrieveMasterGroupBySubGroupID(groupIDList);
                    if (bankGroupMasterListSearchResult.size() > 0) {
                        exceptionMap.put("masterGroupErr", new ActionMessage("error.group.mastergroup.subgroup", details.getGroupName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            Map chkMap = getGroupByName(trxVal);
            if (!chkMap.isEmpty()) {
                exceptionMap.put("errorGroupNameExist", new ActionMessage("error.group.name.exist", chkMap.get("name"), chkMap.get("id")));
            }

            if (exceptionMap.isEmpty()) {
                if (description != null && !"".equals(description.trim())) {
                    trxVal.setRemarks(description);
                    trxContext.setRemarks(description);
                }
                proxy.checkerApproveUpdateCustGrp(trxContext, trxVal);
            }

        } catch (CustGrpIdentifierException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        resultMap.put("request.ITrxValue", trxVal);
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }

    private Map getGroupByName(ICustGrpIdentifierTrxValue trxVal) {
        Map returnMap = new HashMap();
        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        ICustGrpIdentifier details = trxVal.getStagingCustGrpIdentifier();
        ICustGrpIdentifier actual = trxVal.getCustGrpIdentifier();

        if (details == null || details.getGroupName() == null) {
            DefaultLogger.debug(this, "Staging ICustGrpIdentifier or Group Name  null  ");
            return returnMap;
        }

        CustGrpIdentifierSearchCriteria criteria = new CustGrpIdentifierSearchCriteria();
        criteria.setGroupName(details.getGroupName());
        criteria.setSearchType("ByGroup");
        criteria.setStartIndex(0);
        criteria.setNItems(20);
        criteria.setExactSearch(true);
        // DefaultLogger.debug(this,"checkGroupByName  Group Name = " + criteria.getGroupName());
        // DefaultLogger.debug(this,"checkGroupByName  actual.getGrpNo  = " + actual.getGrpNo());
        try {
            SearchResult result = proxy.searchGroup(criteria);

            if (result != null && result.getResultList() != null) {
                List v = (List) result.getResultList();
                if (v != null) {
                    for (int i = 0; i < v.size(); i++) {
                        OBCustomerSearchResult obj = (OBCustomerSearchResult) v.get(i);
                        if (obj != null && obj.getGrpNo() != null) {
                            if (Long.valueOf(obj.getGrpNo()).longValue() != details.getGrpNo()) {
                                returnMap.put("name", obj.getGroupName());
                                returnMap.put("id", obj.getGrpNo());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;

    }

}

