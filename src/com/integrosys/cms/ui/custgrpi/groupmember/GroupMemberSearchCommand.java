/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: July 5, 2008
 */
public class GroupMemberSearchCommand extends AbstractCommand {

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
                {CustGroupUIHelper.form_groupMemberSearchCriteria, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", FORM_SCOPE},
                {CustGroupUIHelper.service_groupMemberSearchCriteria, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"indicator", "java.lang.String", REQUEST_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},});
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
                {CustGroupUIHelper.form_groupMemberSearchList, "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {CustGroupUIHelper.service_groupMemberSearchList, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {CustGroupUIHelper.service_groupMemberSearchCriteria, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", SERVICE_SCOPE},
                {IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria", GLOBAL_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue",SERVICE_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException,
            CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        OBTrxContext theOBTrxContext = (OBTrxContext) map
                .get("theOBTrxContext");

		String event = (String) map.get("event");
		String startIndex = (String) map.get("startIndex");
		String indicator = (String) map.get("indicator");
		GroupMemberSearchCriteria formCriteria = (GroupMemberSearchCriteria) map
				.get(CustGroupUIHelper.form_groupMemberSearchCriteria);
		GroupMemberSearchCriteria searchCriteria = null;

		if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
			DefaultLogger.debug(this, "- Search Criteria from Session !");
			searchCriteria = (GroupMemberSearchCriteria) map
					.get(CustGroupUIHelper.service_groupMemberSearchCriteria);
			if (searchCriteria != null) {
				if (startIndex != null) {
					int startIndexInt = Integer.parseInt(startIndex);
					searchCriteria.setStartIndex(startIndexInt);
				} else {
					searchCriteria.setStartIndex(formCriteria.getStartIndex());
				}

				searchCriteria.setNItems(formCriteria.getNItems());
			}
		}

        if (searchCriteria == null) {
			DefaultLogger.debug(this, "- Search Criteria from Form !"
					+ formCriteria.toString());
			searchCriteria = formCriteria;
        }

        //searchCriteria.setCustomerSeach(false);

        //set NItems to default 10 when not passed in
        if(searchCriteria.getNItems()==0)
            searchCriteria.setNItems(10);

        ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
        long teamTypeID = team.getTeamType().getTeamTypeID();

        if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
            searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
        }
        searchCriteria.setCtx(theOBTrxContext);
        searchCriteria.setCustomerSeach(true);
        DefaultLogger.debug(this, "start index: " + searchCriteria.getStartIndex());
//        DefaultLogger.debug(this, "getCustomerName: " + searchCriteria.getCustomerName());
        DefaultLogger.debug(this, "getLegalID: " + searchCriteria.getLegalID());
        DefaultLogger.debug(this, "getLeIDType: " + searchCriteria.getSourceType());
        DefaultLogger.debug(this, "getIdNO: " + searchCriteria.getIdNO());
        DefaultLogger.debug(this, "getAll: " + searchCriteria.getAll());
        DefaultLogger.debug(this, "getNItems: " + searchCriteria.getNItems());

        try {
//            ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
//            SearchResult sr = proxy.searchEntryDetails(searchCriteria);

//            ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
//            SearchResult sr = custproxy.searchCustomer(searchCriteria);

            ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
            SearchResult sr = custproxy.searchCCICustomer(searchCriteria);

            result.put(CustGroupUIHelper.form_groupMemberSearchList, sr);
			result.put(CustGroupUIHelper.service_groupMemberSearchList, sr);
            result.put(CustGroupUIHelper.service_groupMemberSearchCriteria, searchCriteria);
            result.put(IGlobalConstant.GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ, searchCriteria);

        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }
        
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;

    }

	private boolean isEmptySearchCriteria(GroupMemberSearchCriteria criteria) {

		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getLegalID())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getSourceType())) {
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

}