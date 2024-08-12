package com.integrosys.cms.ui.todo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.transaction.CMSTransactionDAOFactory;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.OBCMSTrxSearchResult;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

public class PrepareTodoCommand extends AbstractCommand {

	public static final String WORKSPACEMGR_SB_HOME = "SBWorkspaceManagerHome";

	public static final String WORKSPACEMGR_SB_HOME_PATH = "com.integrosys.cms.app.workspace.SBWorkspaceManagerHome";

	private SBCMSTrxManager workflowManager;

	public void setWorkflowManager(SBCMSTrxManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	public SBCMSTrxManager getWorkflowManager() {
		return workflowManager;
	}

	/**
	 * Default Constructor
	 */
	public PrepareTodoCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "resetCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "sort", "java.lang.String", REQUEST_SCOPE },
				{ "sortSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSortSession", "java.lang.String", GLOBAL_SCOPE },
				{ "field", "java.lang.String", REQUEST_SCOPE },
				{ "fieldSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackFieldSession", "java.lang.String", GLOBAL_SCOPE },
				{ "legalName", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				// CR-120 Search by cutomer name, legal name, legal ID
				{ "searchLeID", "java.lang.String", REQUEST_SCOPE },
				{ "searchLeIDSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSearchLeIDSession", "java.lang.String", GLOBAL_SCOPE },
				{ "searchLegalName", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerNameSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSearchCustomerNameSession", "java.lang.String", GLOBAL_SCOPE },
				{ "searchLoginID", "java.lang.String", REQUEST_SCOPE },
				{ "lastUpdatedBy", "java.lang.String", REQUEST_SCOPE },
				{ "lastUpdatedBySession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackLastUpdatedBySession", "java.lang.String", GLOBAL_SCOPE },
				{ "searchAANumberSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSearchAANumberSession", "java.lang.String", GLOBAL_SCOPE },
				{ "searchAANumber", "java.lang.String", REQUEST_SCOPE },
				{ "transactionType", "java.lang.String", REQUEST_SCOPE },
				{ "transactionTypeSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackTransactionTypeSession", "java.lang.String", GLOBAL_SCOPE },
				{ "toDoStatus", "java.lang.String", REQUEST_SCOPE },
				{ "statusSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackStatusSession", "java.lang.String", GLOBAL_SCOPE },
				{ "isBtnClicked", "java.lang.String", REQUEST_SCOPE },
				{ "isMenu", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reset", "java.lang.String", REQUEST_SCOPE },
				{ "sort", "java.lang.String", GLOBAL_SCOPE },
				{ "sortSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSortSession", "java.lang.String", GLOBAL_SCOPE },
				{ "field","java.lang.String", GLOBAL_SCOPE },
				{ "fieldSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackFieldSession", "java.lang.String", GLOBAL_SCOPE },
				{ "transactionTypeList", "java.util.List", GLOBAL_SCOPE },
				{ "lastUpdatedBy", "java.lang.String", GLOBAL_SCOPE },
				{ "lastUpdatedBySession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackLastUpdatedBySession", "java.lang.String", GLOBAL_SCOPE },
				{ "searchAANumberSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSearchAANumberSession", "java.lang.String", GLOBAL_SCOPE },
				{ "searchAANumber", "java.lang.String", GLOBAL_SCOPE },
				{ "searchCustomerName", "java.lang.String", GLOBAL_SCOPE },
				{ "searchCustomerNameSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSearchCustomerNameSession", "java.lang.String", GLOBAL_SCOPE },
				{ "searchLeID", "java.lang.String", GLOBAL_SCOPE },
				{ "searchLeIDSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackSearchLeIDSession", "java.lang.String", GLOBAL_SCOPE },
				{ "transactionType", "java.lang.String", GLOBAL_SCOPE },
				{ "transactionTypeSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackTransactionTypeSession", "java.lang.String", GLOBAL_SCOPE },
				{ "toDoStatus", "java.lang.String", GLOBAL_SCOPE },
				{ "statusSession", "java.lang.String", GLOBAL_SCOPE },
				{ "totrackStatusSession", "java.lang.String", GLOBAL_SCOPE },
				{ "session.searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here preparation for company borrower is
	 * done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Entering doExecute()");
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		int stindex = 0;
		String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
		String legalName = (String) map.get("legalName");
		String totalCount = (String) map.get("totalCount");
		String event = (String) map.get("event");
		String startIndex = (String) map.get("startIndex");
		String searchLeIDType = (String) map.get("searchLeIDType");
		String searchLegalName = (String) map.get("searchLegalName");
		String searchLoginID = (String) map.get("searchLoginID");
		 result.put("transactionTypeList", getTransactionTypeList(teamTypeMemID));
		/***/
		String lastUpdatedBy="",
		searchAANumber="",	
		toDoStatus="",	
		searchCustomerName="",	
		searchLeID="",	
		transactionType="",	
		sort="",	
		field="";
		
		String lastUpdatedBySession=(String) map.get("lastUpdatedBySession"),
		searchAANumberSession=(String) map.get("searchAANumberSession"),
		statusSession=(String) map.get("statusSession"),	
		searchCustomerNameSession=(String) map.get("searchCustomerNameSession"),	
		searchLeIDSession=(String) map.get("searchLeIDSession"),	
		transactionTypeSession=(String) map.get("transactionTypeSession"),	
		sortSession=(String) map.get("sortSession"),	
		fieldSession=(String) map.get("fieldSession");	
		
		// get values from session
		String totrackLastUpdatedBySession=(String) map.get("totrackLastUpdatedBySession"),
	    totrackSearchAANumberSession=(String) map.get("totrackSearchAANumberSession"),	
		totrackStatusSession=(String) map.get("totrackStatusSession"),	
		totrackSearchCustomerNameSession=(String) map.get("totrackSearchCustomerNameSession"),	
		totrackSearchLeIDSession=(String) map.get("totrackSearchLeIDSession"),	
		totrackTransactionTypeSession=(String) map.get("totrackTransactionTypeSession"),
		totrackSortSession=(String) map.get("totrackSortSession"),	
		totrackFieldSession=(String) map.get("totrackFieldSession");		
		/***/
		// get all session search criterias for todo
		if(event.equalsIgnoreCase("prepare")||event.equalsIgnoreCase("searchtodo")){
	    lastUpdatedBy=(String) map.get("lastUpdatedBy");
		searchAANumber=(String) map.get("searchAANumber");	
		toDoStatus=(String) map.get("toDoStatus");	
		searchCustomerName=(String) map.get("searchCustomerName");
		searchLeID=(String) map.get("searchLeID");	
		transactionType=getTransactionName((String) map.get("transactionType"),teamTypeMemID);	
		sort=(String) map.get("sort");	
		field=(String) map.get("field");
		
		if("".equals(lastUpdatedBySession))
			lastUpdatedBySession=null;
		if(lastUpdatedBySession!=null&&lastUpdatedBy==null){
			lastUpdatedBy=lastUpdatedBySession;
		}else{
			lastUpdatedBySession=lastUpdatedBy;
		}
		
		if("".equals(searchAANumberSession))
			searchAANumberSession=null;
		if(searchAANumberSession!=null&&searchAANumber==null){
			searchAANumber=searchAANumberSession;
		}else{
			searchAANumberSession=searchAANumber;
		}
		
		if(null!=toDoStatus){
			toDoStatus=toDoStatus.replace(' ','_');
		}
		
		if("".equals(statusSession))
			statusSession=null;
		if(statusSession!=null&&toDoStatus==null){
			toDoStatus=statusSession;
		}else{
			statusSession=toDoStatus;
		}
		
		if("".equals(searchCustomerNameSession))
			searchCustomerNameSession=null;
		if(searchCustomerNameSession!=null&&searchCustomerName==null){
			searchCustomerName=searchCustomerNameSession;
		}else{
			searchCustomerNameSession=searchCustomerName;
		}
		
		if("".equals(searchLeIDSession))
			searchLeIDSession=null;
		if(searchLeIDSession!=null&&searchLeID==null){
			searchLeID=searchLeIDSession;
		}else{
			searchLeIDSession=searchLeID;
		}
		
		if("".equals(transactionTypeSession))
			transactionTypeSession=null;
		if(transactionTypeSession!=null&&transactionType==null){
			transactionType=transactionTypeSession;
		}else{
			transactionTypeSession=transactionType;
		}
		if(null!=((String)map.get("transactionType"))){
		if(((String)map.get("transactionType")).equals("select")){
			transactionType=transactionTypeSession=null;
		}
		}
		//start
		sort = (String) map.get("sort");
		if(sort!=null){
			sortSession=sort;
		}else{
			sort=sortSession;
		}
		field = (String) map.get("field");
		if(field!=null){
			fieldSession=field;
		}else{
			field=fieldSession;
		}
		//end
		String reset=(String)map.get("resetCriteria");
		if(reset!=null){
		if(reset.equals("on")){
			searchAANumber=searchAANumberSession=null;
			lastUpdatedBy=lastUpdatedBySession=null;
			searchLeID=searchLeIDSession=null;
			searchCustomerName=searchCustomerNameSession=null;
			transactionType=transactionTypeSession=null;
			toDoStatus=statusSession=null;
			sort=sortSession=field=fieldSession=null;
		}
		}
		}
		// get all session search criterias for totrack
		if(event.equalsIgnoreCase("totrack")){
		    lastUpdatedBy=(String) map.get("lastUpdatedBy");
			searchAANumber=(String) map.get("searchAANumber");	
			toDoStatus=(String) map.get("toDoStatus");	
			searchCustomerName=(String) map.get("searchCustomerName");
			searchLeID=(String) map.get("searchLeID");	
			transactionType=getTransactionName((String) map.get("transactionType"),teamTypeMemID);	
			sort=(String) map.get("sort");	
			field=(String) map.get("field");
			
			if("".equals(totrackLastUpdatedBySession))
				totrackLastUpdatedBySession=null;
			if(totrackLastUpdatedBySession!=null&&lastUpdatedBy==null){
				lastUpdatedBy=totrackLastUpdatedBySession;
			}else{
				totrackLastUpdatedBySession=lastUpdatedBy;
			}
			
			if("".equals(totrackSearchAANumberSession))
				totrackSearchAANumberSession=null;
			if(totrackSearchAANumberSession!=null&&searchAANumber==null){
				searchAANumber=totrackSearchAANumberSession;
			}else{
				totrackSearchAANumberSession=searchAANumber;
			}
			
			if(null!=toDoStatus){
				toDoStatus=toDoStatus.replace(' ','_');
			}
			
			if("".equals(totrackStatusSession))
				totrackStatusSession=null;
			if(totrackStatusSession!=null&&toDoStatus==null){
				toDoStatus=totrackStatusSession;
			}else{
				totrackStatusSession=toDoStatus;
			}
			
			if("".equals(totrackSearchCustomerNameSession))
				totrackSearchCustomerNameSession=null;
			if(totrackSearchCustomerNameSession!=null&&searchCustomerName==null){
				searchCustomerName=totrackSearchCustomerNameSession;
			}else{
				totrackSearchCustomerNameSession=searchCustomerName;
			}
			
			if("".equals(totrackSearchLeIDSession))
				totrackSearchLeIDSession=null;
			if(totrackSearchLeIDSession!=null&&searchLeID==null){
				searchLeID=totrackSearchLeIDSession;
			}else{
				totrackSearchLeIDSession=searchLeID;
			}
			
			if("".equals(totrackTransactionTypeSession))
				totrackTransactionTypeSession=null;
			if(totrackTransactionTypeSession!=null&&transactionType==null){
				transactionType=totrackTransactionTypeSession;
			}else{
				totrackTransactionTypeSession=transactionType;
			}
			if(null!=((String)map.get("transactionType"))){
				if(((String)map.get("transactionType")).equals("select")){
					transactionType=totrackTransactionTypeSession=null;
				}
				}
			//start
			sort = (String) map.get("sort");
			if(sort!=null){
				totrackSortSession=sort;
			}else{
				sort=totrackSortSession;
			}
			field = (String) map.get("field");
			if(field!=null){
				totrackFieldSession=field;
			}else{
				field=totrackFieldSession;
			}
			//end
			String reset=(String)map.get("resetCriteria");  // code to reset all search criteria
			if(reset!=null){
			if(reset.equals("on")){
				searchAANumber=totrackSearchAANumberSession=null;
				lastUpdatedBy=totrackLastUpdatedBySession=null;
				searchLeID=totrackSearchLeIDSession=null;
				searchCustomerName=totrackSearchCustomerNameSession=null;
				transactionType=totrackTransactionTypeSession=null;
				toDoStatus=totrackStatusSession=null;
				sort=totrackSortSession=field=totrackFieldSession=null;
			}
			}
			}

		try {
			// todo-type can be set based on the role type

			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
			CMSTrxSearchCriteria globalSearch = (CMSTrxSearchCriteria) map
					.get(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ);

			CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
			boolean isFormScope = false;

			if (StringUtils.isBlank(startIndex)) {
				if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
					stindex = 0;
					// DefaultLogger.debug(this,
					// "startIndex Loading with First Time = 0");
					startIndex = String.valueOf(stindex);
					result.put("startIndex", startIndex);

				}
				else {
					// DefaultLogger.debug(this,"startIndex Loading with Other "
					// + startIndex);
					stindex = Integer.parseInt(globalStartIndex);
					startIndex = globalStartIndex;
					// result.put("startIndex", String.valueOf(stindex));
					result.put("startIndex", startIndex);
				}
			}
			else {
				// DefaultLogger.debug(this,"startIndex Loading with Same" +
				// startIndex);
				stindex = Integer.parseInt(startIndex);
				result.put("startIndex", startIndex);
			}

			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

			// criteria.setStartIndex(stindex);

			// use the pagination query method only for borrower list, otherwise
			// still use the old way
			if (TodoAction.EVENT_NEW_LIMIT_PROFILE.equals(event)
					|| TodoAction.EVENT_EXISTING_LIMIT_PROFILE.equals(event)
					|| TodoAction.EVENT_NEW_BORROWER.equals(event) || TodoAction.EVENT_NEW_NONBORROWER.equals(event)) {
				if (StringUtils.isBlank(totalCount)) {
					criteria.setNItems(-1);
				}
				else {
					criteria.setNItems(Integer.parseInt(totalCount));
				}
			}
			else {
				criteria.setNItems(10);
			}
			criteria.setTeamID(team.getTeamID());
			// criteria.setAllowedCountries(team.getCountryCodes());
			// criteria.setAllowedOrganisations(team.getOrganisationCodes());
			// criteria.setAllowedSegments(team.getSegmentCodes());
			criteria = this.prepareSearchCriteria(criteria, event);

			if ("Y".equalsIgnoreCase((String) map.get("isBtnClicked"))) {
				isFormScope = true;
			}
			// Alpha Index
			if (StringUtils.isNotBlank(legalName)) {
				criteria.setLegalName(legalName);
			}
			else {
				// CR-120 Search by customer name, legal name and leID
				if (StringUtils.isNotBlank(searchLegalName)) {
					criteria.setLegalName(searchLegalName);
				}
			}

			if (StringUtils.isNotBlank(searchCustomerName)) {
				criteria.setCustomerName(searchCustomerName);
			}
			if (StringUtils.isNotBlank(searchLoginID)) {
				criteria.setLoginID(searchLoginID);
			}
			if (StringUtils.isNotBlank(searchLeID)) {
				criteria.setLegalID(searchLeID);
			}
			if (StringUtils.isNotBlank(searchLeIDType)) {
				criteria.setLegalIDType(searchLeIDType);
			}

			if (StringUtils.isNotBlank(lastUpdatedBy)) {
				criteria.setLastUpdatedBy(lastUpdatedBy);
			}
			
			if (StringUtils.isNotBlank(transactionType)) {
				criteria.setTransactionType(transactionType);
			}
			
			if (StringUtils.isNotBlank(toDoStatus)) {
				criteria.setStatus(toDoStatus);
			}
			
			if (StringUtils.isNotBlank(searchAANumber)) {
				criteria.setAaNumber(searchAANumber);
			}
			
			if (StringUtils.isNotBlank(sort)) {
				criteria.setSort(sort);
			}
			
			if (StringUtils.isNotBlank(field)) {
				criteria.setField(field);
			}

			long teamTypeMembershipID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			if ((teamTypeMemID != null) && (teamTypeMemID.trim().length() > 0)) {
				teamTypeMembershipID = Long.parseLong(teamTypeMemID);
			}

			for (int i = 0; i < team.getTeamMemberships().length; i++) {
				ITeamMembership membership = team.getTeamMemberships()[i];
				if (membership.getTeamTypeMembership().getMembershipID() == teamTypeMembershipID) {
					criteria.setTeamMembershipID(membership.getTeamMembershipID());
					break;
				}
			}

			criteria.setTeamTypeMembershipID(teamTypeMembershipID);
			// +01/07/2006
			criteria.setUserID(user.getUserID());

			String isMenu = (String) map.get("isMenu");

			if ("Y".equals(isMenu)) {
				DefaultLogger.debug(this, "Search from Menu ");
				stindex = 0;
				startIndex = String.valueOf(stindex);
			}
			else {
				if (!isFormScope) {
					if (globalSearch != null) {
						DefaultLogger.debug(this, "Setting Search with Global Session " + startIndex);
						criteria = globalSearch;
					}
				}
				else {
					DefaultLogger.debug(this, "Search from Form ");
				}
			}

			criteria.setStartIndex(stindex);
			DefaultLogger.debug(this, "#################Before getting search result ");
			SearchResult searchResult = this.getSearchResult(criteria);
			DefaultLogger.debug(this, "#################after getting search result :"+searchResult);
			
			if(searchResult!=null){
			List todoList = (List)searchResult.getResultList();
			List newTodoList = (List)searchResult.getResultList();
			DefaultLogger.debug(this, "#################after getting search result todoList:");
			//searchResult.getResultList().removeAll(searchResult.getResultList());
			if (todoList != null && todoList.size() > 0) {
				DefaultLogger.debug(this, "#################after getting search result todoList in for loop:");
				for (int i = 0; i < todoList.size(); i++) {
					
					OBCMSTrxSearchResult obj = (OBCMSTrxSearchResult) todoList.get(i);
					OBCMSTrxSearchResult obj1 = (OBCMSTrxSearchResult) todoList.get(i);
					if (obj != null) {
						if (obj.getTransactionType().equals("COL")) {
							ICollateralDAO collateralDao = (ICollateralDAO) BeanHouse.get("collateralDao");
							if(obj.getReferenceID()!=null){
							ICollateralSubType coll = collateralDao.getCollateralByCollateralID(new Long(obj.getReferenceID()));
							obj1.setCustomerName(coll.getTypeName() + "-"+ coll.getSubTypeName());
							newTodoList.remove(i);
							newTodoList.add(i, obj1);
							}
						}
					}
				}
				searchResult = new SearchResult(0, 0, searchResult.getNItems(),
						newTodoList);
			}
			}
			DefaultLogger.debug(this, "#################Before getting search result "); 
			 
			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ, criteria);
			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, startIndex);

			result.put("startIndex", startIndex);

			// System.out.println("startIndex = " + result.get("startIndex"));
			// System.out.println("criteria startIndex = " +
			// criteria.getStartIndex());
			result.put("sort", sort);
			result.put("sortSession", sortSession);
			result.put("totrackSortSession", totrackSortSession);
			result.put("field", field);
			result.put("fieldSession", fieldSession);
			result.put("totrackFieldSession", totrackFieldSession);
			result.put("transactionType", transactionType);
			result.put("transactionTypeSession", transactionTypeSession);
			result.put("totrackTransactionTypeSession", totrackTransactionTypeSession);
			result.put("toDoStatus", toDoStatus);
			result.put("statusSession", statusSession);
			result.put("totrackStatusSession", totrackStatusSession);
            result.put("lastUpdatedBy",lastUpdatedBy);
            result.put("lastUpdatedBySession",lastUpdatedBySession);
            result.put("totrackLastUpdatedBySession",totrackLastUpdatedBySession);
            result.put("searchCustomerName",searchCustomerName);
            result.put("searchCustomerNameSession",searchCustomerNameSession);
            result.put("totrackSearchCustomerNameSession",totrackSearchCustomerNameSession);
            result.put("searchAANumber",searchAANumber);
            result.put("searchAANumberSession",searchAANumberSession);
            result.put("totrackSearchAANumberSession",totrackSearchAANumberSession);
            result.put("searchLeID",searchLeID);
            result.put("searchLeIDSession",searchLeIDSession);
            result.put("totrackSearchLeIDSession",totrackSearchLeIDSession);
			result.put("searchResult", searchResult);
			result.put("session.searchResult", searchResult);
			result.put(IGlobalConstant.USER_TEAM, team);
			result.put(IGlobalConstant.USER, user);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return returnMap;
		}
		catch (Throwable e) {
			DefaultLogger.error(this, "error encounter when doing prepare for [" + event + "]", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

	protected CMSTrxSearchCriteria prepareSearchCriteria(CMSTrxSearchCriteria criteria, String event) {
		criteria.setSearchIndicator(ICMSConstant.TODO_ACTION);
		return criteria;
	}

	protected SearchResult getSearchResult(CMSTrxSearchCriteria criteria) throws TrxOperationException,
			SearchDAOException, RemoteException {
		return getWorkflowManager().searchTransactions(criteria);
	}

	private List getTransactionTypeList(String teamID) {
		List lbValList = new ArrayList();
		try {
			List idList = CMSTransactionDAOFactory.getDAO().getTransactionTypeList(teamID);
			for (int i = 0; i < idList.size(); i++) {
				String[] trans= (String[]) idList.get(i);
				String id ="";
				if(trans[1]==null){
					id = trans[0]+"-";
				}else{
				id = trans[0]+"-"+trans[1];
				}
				String val = trans[2];
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	public String getTransactionName(String x,String teamID) {
		List lbValList = new ArrayList();
		try {
			List idList = CMSTransactionDAOFactory.getDAO().getTransactionTypeList(teamID);
			for (int i = 0; i < idList.size(); i++) {
				String[] trans= (String[]) idList.get(i);
				String id ="";
				if(trans[1]==null){
					id = trans[0]+"-";
				}else{
				id = trans[0]+"-"+trans[1];
				}
				if(id.equals(x))
					return trans[2];
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}