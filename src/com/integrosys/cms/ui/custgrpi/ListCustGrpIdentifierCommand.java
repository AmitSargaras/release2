package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.HashMap;
import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 9, 2007
 * Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class ListCustGrpIdentifierCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public ListCustGrpIdentifierCommand() {

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
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
                {CustGroupUIHelper.form_CustGrpIdentifierSearchObj, "com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria", FORM_SCOPE},
                {CustGroupUIHelper.service_CustGrpIdentifierSearchObj, "com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria", FORM_SCOPE},
                {"custGrpIdentifierSearchCriteria1", "com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"searchType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"indicator", "java.lang.String", REQUEST_SCOPE},

                {"startIndex", "java.lang.String", REQUEST_SCOPE},
                {"pageIndex", "java.lang.String", REQUEST_SCOPE},
                {"customerName", "java.lang.String", REQUEST_SCOPE},
                {"customerSeach", "java.lang.String", REQUEST_SCOPE},
                {"grpNo", "java.lang.String", REQUEST_SCOPE},
                {"grpID", "java.lang.String", REQUEST_SCOPE},
                {"groupName", "java.lang.String", REQUEST_SCOPE},
                {"legalName", "java.lang.String", REQUEST_SCOPE},
                {"searchType", "java.lang.String", REQUEST_SCOPE},
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
                {CustGroupUIHelper.form_groupResult, "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {CustGroupUIHelper.service_CustGrpIdentifierSearchListObj, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {IGlobalConstant.GLOBAL_CUSTGRPIDENTIFIER_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria", GLOBAL_SCOPE},
                {CustGroupUIHelper.service_groupResult, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE},
                {"searchType", "java.lang.String", REQUEST_SCOPE},
        });

    }


    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();


        String event = (String) map.get("event");
        String indicator = (String) map.get("indicator");
        String searchType = (String) map.get("searchType");
        String startIndex = (String) map.get("startIndex");
        String pageIndex = (String) map.get("pageIndex");




        OBTrxContext theOBTrxContext = (OBTrxContext) map .get("theOBTrxContext");
        CustGrpIdentifierSearchCriteria formCriteria = (CustGrpIdentifierSearchCriteria) map.get(CustGroupUIHelper.form_CustGrpIdentifierSearchObj);
        CustGrpIdentifierSearchCriteria searchCriteria = null;

        if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
            searchCriteria = (CustGrpIdentifierSearchCriteria) map.get(CustGroupUIHelper.service_CustGrpIdentifierSearchObj);
            if (searchCriteria != null) {
                searchCriteria.setStartIndex(formCriteria.getStartIndex());
                searchCriteria.setNItems(formCriteria.getNItems());
            }
        }
        if (searchCriteria == null) {
            DefaultLogger.debug(this, "- Search Criteria from Form !");
            searchCriteria = formCriteria;
        }

        if (pageIndex != null){
           DefaultLogger.debug(this, "- Search Criteria from pageIndex !");
          searchCriteria = createCriteriaFromMap(map);
        }

        ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
        long teamTypeID = team.getTeamType().getTeamTypeID();

        if (teamTypeID == ICMSConstant.TEAM_TYPE_GEMS_AM_MAKER) {
            searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
        }
        if (theOBTrxContext != null)
            searchCriteria.setCtx(theOBTrxContext);

        searchCriteria.setNItems(20);

        DefaultLogger.debug(this, "start index: " + searchCriteria.getStartIndex());
//        DefaultLogger.debug(this, "getCustomerName: " + searchCriteria.getCustomerName());
        DefaultLogger.debug(this, "getLegalID: " + searchCriteria.getLegalID());
        DefaultLogger.debug(this, "getLeIDType: " + searchCriteria.getLeIDType());
        DefaultLogger.debug(this, "getIdNO: " + searchCriteria.getIdNO());
        DefaultLogger.debug(this, "getAll: " + searchCriteria.getAll());
        DefaultLogger.debug(this, "getNItems: " + searchCriteria.getNItems());
        DefaultLogger.debug(this, "getSearchType: " + searchCriteria.getSearchType());

        SearchResult sr = null;
        ICustGrpIdentifierProxy custproxy = CustGrpIdentifierProxyFactory.getProxy();

        try {
            if ("ByGroup".equals(searchType)) {
                searchCriteria.setSearchType("ByGroup");
            } else {
                searchCriteria.setSearchType("ByGroupMember");
            }

            sr = custproxy.searchGroup(searchCriteria);

            resultMap.put("searchType", searchType);
            resultMap.put(CustGroupUIHelper.form_groupResult, sr);
            resultMap.put(CustGroupUIHelper.service_groupResult, sr);
            resultMap.put(IGlobalConstant.GLOBAL_CUSTGRPIDENTIFIER_SEARCH_CRITERIA_OBJ, searchCriteria);
            resultMap.put(CustGroupUIHelper.service_CustGrpIdentifierSearchObj, searchCriteria);
        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Existing doExecute()");

         resultMap.put("startIndex", startIndex);

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


    private boolean isEmptySearchCriteria(CustGrpIdentifierSearchCriteria criteria) {

        if (isNotEmptyStr(criteria.getCustomerName())) {
            return false;
        }
        if (isNotEmptyStr(criteria.getLegalID())) {
            return false;
        }
        if (isNotEmptyStr(criteria.getLeIDType())) {
            return false;
        }
        if (isNotEmptyStr(criteria.getIdNO())) {
            return false;
        }
        return true;
    }


    private boolean isNotEmptyStr(String str) {
        if (str == null) {
            return false;
        }
        if (str.trim().equals("")) {
            return false;
        }
        return true;
    }


    private CustGrpIdentifierSearchCriteria createCriteriaFromMap(HashMap map) {
         CustGrpIdentifierSearchCriteria obj = new CustGrpIdentifierSearchCriteria();

        String customerName = (String) map.get("customerName");
        String customerSeach = (String) map.get("customerSeach");
        String searchType = (String) map.get("searchType");
        String startIndexStr = (String) map.get("startIndex");
        String grpID = (String) map.get("grpID");
        String grpNo = (String) map.get("grpNo");
        String groupName = (String) map.get("groupName");
        String legalName = (String) map.get("legalName");

        int startIndex=0;
        if (startIndexStr != null) {
           startIndex= Integer.parseInt(startIndexStr) ;
        }

        if (customerSeach != null && "true".equals(customerSeach)){
            obj.setCustomerSeach(true);
        }
        if (customerName != null){
            obj.setCustomerName(customerName);
        }

        obj.setGrpNo(grpNo);
        obj.setGrpID(grpID);
        obj.setGroupName(groupName);
        obj.setLegalID(legalName);
        obj.setSearchType(searchType);
        obj.setStartIndex(startIndex);

        return  obj ;

    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"ListCustGrpIdentifierCommand = " + msg);
    }

}
