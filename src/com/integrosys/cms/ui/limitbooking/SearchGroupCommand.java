package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.HashMap;

/**
 * @author priya
 *
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
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
                {LimitBookingAction.SEARCH_CRITERIA, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", FORM_SCOPE},
                {"indicator", "java.lang.String", REQUEST_SCOPE},
                {"searchType", "java.lang.String", REQUEST_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
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
                {LimitBookingAction.SEARCH_RESULT, "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {LimitBookingAction.SESSION_SEARCH_RESULT, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {LimitBookingAction.SESSION_SEARCH_CRITERIA, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", SERVICE_SCOPE},
                {"searchType", "java.lang.String", REQUEST_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE},
                {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
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

        String indicator = (String) map.get("indicator");
        String searchType = (String) map.get("searchType");
        String startIndex = (String) map.get("startIndex");
        OBTrxContext theOBTrxContext = (OBTrxContext) map .get("theOBTrxContext");
        GroupMemberSearchCriteria formCriteria = (GroupMemberSearchCriteria) map.get(LimitBookingAction.SEARCH_CRITERIA);

        GroupMemberSearchCriteria service_criteria  =null;
        if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
            service_criteria = (GroupMemberSearchCriteria) map.get(LimitBookingAction.SESSION_SEARCH_CRITERIA);
            if (service_criteria != null) {
                service_criteria.setStartIndex(formCriteria.getStartIndex());
                service_criteria.setNItems(formCriteria.getNItems());
            }
        }
        if (service_criteria == null) {
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

        ICustGrpIdentifierProxy custproxy = CustGrpIdentifierProxyFactory.getProxy();

        SearchResult sr = null;
        try {
            searchCriteria.setSearchType("ByGroup");
            sr = custproxy.searchGroup(searchCriteria);
            resultMap.put("searchType",searchType);
            resultMap.put("startIndex", startIndex);
            resultMap.put(LimitBookingAction.SEARCH_RESULT, sr);

            resultMap.put(LimitBookingAction.SESSION_SEARCH_RESULT, sr);

            resultMap.put(LimitBookingAction.SESSION_SEARCH_CRITERIA, searchCriteria);
            resultMap.put(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, (String)map.get(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT));
        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Existing doExecute()");

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

          search.setSearchType(searchType);
          search.setGrpID(trim(criteria.getGrpID()));
          search.setGrpNo(trim(criteria.getGrpNo()));
          search.setGroupName(trim(criteria.getGroupName()));
          search.setMasterGroupInd(false);
          search.setStartIndex(startIndex);
          search.setIdNO(criteria.getIdNO());
          search.setAll(criteria.getAll());
          search.setNItems(criteria.getNItems());
          search.setForLimitBooking(true);

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
}
