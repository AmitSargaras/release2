package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 9, 2007
 * Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class SearchGroupCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public SearchGroupCommand() {

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
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
                {CustGroupUIHelper.form_groupMemberSearchCriteria, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", FORM_SCOPE},
                {CustGroupUIHelper.service_groupMemberSearchCriteria, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", SERVICE_SCOPE},
          
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"searchType", "java.lang.String", REQUEST_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"indicator", "java.lang.String", REQUEST_SCOPE},
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
                {CustGroupUIHelper.form_searchGroupResult, "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {CustGroupUIHelper.service_groupMemberSearchList, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", GLOBAL_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"searchType", "java.lang.String", REQUEST_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE},
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





        OBTrxContext theOBTrxContext = (OBTrxContext) map .get("theOBTrxContext");
        GroupMemberSearchCriteria formCriteria = (GroupMemberSearchCriteria) map.get(CustGroupUIHelper.form_groupMemberSearchCriteria);
        ICustGrpIdentifierTrxValue groupTrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (groupTrxValue == null) {
            groupTrxValue = new OBCustGrpIdentifierTrxValue();
        }

       // Debug("groupTrxValue = " + groupTrxValue);
        GroupMemberSearchCriteria service_criteria  =null;
        if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
            DefaultLogger.debug(this, "- Search Criteria from Session !");
            service_criteria = (GroupMemberSearchCriteria) map.get(CustGroupUIHelper.service_groupMemberSearchCriteria);
            if (service_criteria != null) {
                service_criteria.setStartIndex(formCriteria.getStartIndex());
                service_criteria.setNItems(formCriteria.getNItems());
            }
        }
        if (service_criteria == null) {
            DefaultLogger.debug(this, "- Search Criteria from Form !");
            service_criteria = formCriteria;
        }

        ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
        long teamTypeID = team.getTeamType().getTeamTypeID();


        CustGrpIdentifierSearchCriteria searchCriteria = mergeSearchCriteria(service_criteria,map)  ;

        if (teamTypeID == ICMSConstant.TEAM_TYPE_GEMS_AM_MAKER) {
            searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
        }
        if (theOBTrxContext != null)
            searchCriteria.setCtx(theOBTrxContext);

        searchCriteria.setNItems(20);

//        DefaultLogger.debug(this, "getCustomerName: " + searchCriteria.getCustomerName());
        DefaultLogger.debug(this, "getLegalID: " + searchCriteria.getLegalID());
        DefaultLogger.debug(this, "getLeIDType: " + searchCriteria.getLeIDType());
        DefaultLogger.debug(this, "getIdNO: " + searchCriteria.getIdNO());
        DefaultLogger.debug(this, "getSearchType: " + searchCriteria.getSearchType());

        DefaultLogger.debug(this, "start index: " + searchCriteria.getStartIndex());
        DefaultLogger.debug(this, "getAll: " + searchCriteria.getAll());
        DefaultLogger.debug(this, "getNItems: " + searchCriteria.getNItems());


        ICustGrpIdentifierProxy custproxy = CustGrpIdentifierProxyFactory.getProxy();

        SearchResult sr = null;
        try {
            if ("ByGroup".equals(searchType)) {
                searchCriteria.setSearchType("ByGroup");
            } else {
                searchCriteria.setSearchType("ByGroupMember");
            }
     
            sr = custproxy.searchGroup(searchCriteria);


            resultMap.put("searchType",searchType);
            resultMap.put("startIndex", startIndex);
            resultMap.put(CustGroupUIHelper.form_searchGroupResult, sr);

            // shld be same as in adding on AddGroupMemberCommand
            resultMap.put(CustGroupUIHelper.service_groupMemberSearchList, sr);

            resultMap.put(CustGroupUIHelper.service_groupMemberSearchCriteria, searchCriteria);
            resultMap.put(IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ, searchCriteria);
        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Existing doExecute()");

        resultMap.put(CustGroupUIHelper.service_groupTrxValue, groupTrxValue);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

     private CustGrpIdentifierSearchCriteria   mergeSearchCriteria(GroupMemberSearchCriteria criteria ,HashMap map) {
         CustGrpIdentifierSearchCriteria  search = new   CustGrpIdentifierSearchCriteria();
        String searchType = (String) map.get("searchType");
        String startIndexStr = (String) map.get("startIndex");
          int startIndex=0;
        if (startIndexStr != null) {
           startIndex= Integer.parseInt(startIndexStr) ;
        }

          //search.setSearchType(trim(criteria.getSearchType()));
         search.setSearchType(searchType);
          search.setGrpID(trim(criteria.getGrpID()));
          search.setGroupName(trim(criteria.getGroupName()));
          search.setMasterGroupInd(true);
          search.setCustomerName(trim(criteria.getCustomerName()));
          search.setLegalID(trim(criteria.getLegalID()));
          search.setCustomerSeach(criteria.getCustomerSeach());
          //search.setStartIndex(criteria.getStartIndex());
         search.setStartIndex(startIndex);

          search.setIdNO(criteria.getIdNO());
         // search.setStartIndex(criteria.getStartIndex());
          search.setAll(criteria.getAll());
          search.setNItems(criteria.getNItems());

        return  search;

    }


    private boolean isEmptySearchCriteria(GroupMemberSearchCriteria criteria) {

        if (isNotEmptyStr(criteria.getGrpID())) {
            return false;
        }
        if (isNotEmptyStr(criteria.getGroupName())) {
            return false;
        }
        return true;
    }

      private String trim(String str) {
        if (str == null) {
            return null;
        }else{
          return str.trim()  ;
        }

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


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"SearchGroupCommand = " + msg);
    }

}
