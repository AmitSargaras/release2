package com.integrosys.cms.ui.todo;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.IDiscrepancyJdbc;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.OBCMSTrxSearchResult;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

public class PrepareTodoDiscrepancyCommand extends AbstractCommand {

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
	public PrepareTodoDiscrepancyCommand() {

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
				{ "legalName", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexMain", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				// CR-120 Search by cutomer name, legal name, legal ID
				{ "searchLeID", "java.lang.String", REQUEST_SCOPE },
				{ "searchLegalName", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
				{ "searchLoginID", "java.lang.String", REQUEST_SCOPE },
				{ "lastUpdatedBy", "java.lang.String", REQUEST_SCOPE },
				{ "searchAANumber", "java.lang.String", REQUEST_SCOPE },
				{ "sort", "java.lang.String", REQUEST_SCOPE },
				{ "field", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchLeID", "java.lang.String", SERVICE_SCOPE },
				{ "session.lastUpdatedBy", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchAANumber", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchCustomerName", "java.lang.String", SERVICE_SCOPE },
				{ "session.sort", "java.lang.String", SERVICE_SCOPE },
				{ "session.field", "java.lang.String", SERVICE_SCOPE },
				{ "isBtnClicked", "java.lang.String", REQUEST_SCOPE },
				{ "isMenu", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
				{ "todoGlobal",
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
				{ "session.searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "session.searchLeID", "java.lang.String", SERVICE_SCOPE },
				{ "session.lastUpdatedBy", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchAANumber", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchCustomerName", "java.lang.String", SERVICE_SCOPE },
				{ "session.sort", "java.lang.String", SERVICE_SCOPE },
				{ "session.field", "java.lang.String", SERVICE_SCOPE },
				{ "startIndexMain", "java.lang.String", REQUEST_SCOPE },
				{ "todoGlobal",
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
		String legalName = (String) map.get("legalName");
		String totalCount = (String) map.get("totalCount");
		String event = (String) map.get("event");
		String startIndexMain = (String) map.get("startIndexMain");
		String sort="";
		String field="";
		String searchLeID = "";
		String lastUpdatedBy = "";
		String searchLeIDType = "";
		String searchLegalName = "";
		String searchCustomerName ="";
		String searchLoginID = "";
		String searchAANumber ="";
		
		if("prepare_discrepancy_sort".equals(event)){
			searchLeID = (String) map.get("session.searchLeID");
			lastUpdatedBy = (String) map.get("session.lastUpdatedBy");
			searchLeIDType = (String) map.get("searchLeIDType");
			searchLegalName = (String) map.get("searchLegalName");
			searchCustomerName = (String) map.get("session.searchCustomerName");
			searchLoginID = (String) map.get("searchLoginID");
			searchAANumber = (String) map.get("session.searchAANumber");
			sort=(String)map.get("sort");
			field=(String)map.get("field");
			
		}
		else if("search_discrepancy_todo".equals(event)){
		searchLeID = (String) map.get("searchLeID");
		lastUpdatedBy = (String) map.get("lastUpdatedBy");
		searchLeIDType = (String) map.get("searchLeIDType");
		searchLegalName = (String) map.get("searchLegalName");
		searchCustomerName = (String) map.get("searchCustomerName");
		searchLoginID = (String) map.get("searchLoginID");
		searchAANumber = (String) map.get("searchAANumber");
		}
		
		if(sort==null||sort.equals("")){
			sort=(String)map.get("session.sort");
		}
		if(field==null||field.equals("")){
			field=(String)map.get("session.field");
		}
		
		
		String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
		
		

		try {
			// todo-type can be set based on the role type

			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
			CMSTrxSearchCriteria globalSearch = (CMSTrxSearchCriteria) map
					.get("todoGlobal");
			
		
			
			CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
			boolean isFormScope = false;

			if (StringUtils.isBlank(startIndexMain)) {
				if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
					stindex = 0;
					// DefaultLogger.debug(this,
					// "startIndexMain Loading with First Time = 0");
					startIndexMain = String.valueOf(stindex);
					result.put("startIndexMain", startIndexMain);

				}
				else {
					// DefaultLogger.debug(this,"startIndexMain Loading with Other "
					// + startIndexMain);
					stindex = Integer.parseInt(globalStartIndex);
					startIndexMain = globalStartIndex;
					// result.put("startIndexMain", String.valueOf(stindex));
					result.put("startIndexMain", startIndexMain);
				}
			}
			else {
				// DefaultLogger.debug(this,"startIndexMain Loading with Same" +
				// startIndexMain);
				stindex = Integer.parseInt(startIndexMain);
				result.put("startIndexMain", startIndexMain);
			}

			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

			// criteria.setStartIndex(stindex);

			// use the pagination query method only for borrower list, otherwise
			// still use the old way
			if("todo_cancel".equals(event)){
			 criteria=(CMSTrxSearchCriteria) map.get("todoGlobal");
			}
				if(criteria ==null){
				criteria = new CMSTrxSearchCriteria();
				}
				
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
						criteria.setCustomerName(searchCustomerName.trim());
					}
					if (StringUtils.isNotBlank(searchLoginID)) {
						criteria.setLoginID(searchLoginID);
					}
					if (StringUtils.isNotBlank(searchLeID)) {
						criteria.setLegalID(searchLeID.trim());
					}
					if (StringUtils.isNotBlank(lastUpdatedBy)) {
						criteria.setLastUpdatedBy(lastUpdatedBy.trim());
					}
					if (StringUtils.isNotBlank(searchLeIDType)) {
						criteria.setLegalIDType(searchLeIDType);
					}
		
					if (StringUtils.isNotBlank(searchAANumber)) {
						criteria.setAaNumber(searchAANumber.trim());
					}
					
					if (StringUtils.isNotBlank(field)) {
						criteria.setSortBy(field.trim());
					}
					
					if (StringUtils.isNotBlank(sort)) {
						criteria.setFilterByType(sort.trim());
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
						startIndexMain = String.valueOf(stindex);
					}
					else {
						if (!isFormScope) {
							if(!"search_discrepancy_todo".equals(event)&& !"prepare_discrepancy_sort".equals(event)){
							if (globalSearch != null) {
								
								DefaultLogger.debug(this, "Setting Search with Global Session " + startIndexMain);
								criteria = globalSearch;
							}
							}
						}
						else {
							DefaultLogger.debug(this, "Search from Form ");
						}
					}
		
					criteria.setStartIndex(stindex);
					
					String teamTypeMemId = Long.toString(teamTypeMembershipID);
					
				/*	if("1001".equals(teamTypeMemId)){
						
						teamTypeMemID = Integer.toString(ICMSConstant.TEAM_TYPE_SSC_MAKER);
						int teamId = Integer.parseInt(teamTypeMemID);
						long teamMembershipIdForCpuMakerUser = getTeamMemberShipIdForBranchUser(teamId);
						long teamIdForCpuMakerUser = getTeamIdForBranchUser(teamId);
						criteria.setTeamTypeMembershipID(ICMSConstant.TEAM_TYPE_SSC_MAKER);
						criteria.setTeamMembershipID(teamMembershipIdForCpuMakerUser);
						criteria.setTeamID(teamIdForCpuMakerUser);
						
					}*/
					
			SearchResult searchResult=new SearchResult();
			DefaultLogger.debug(this, "#################Before getting search result ");
			if("search_discrepancy_todo".equals(event)){
				searchResult = this.getToDoSearchResult(criteria);
			}else if("prepare_discrepancy_sort".equals(event)||"todo_cancel".equals(event)){
				
				searchResult = this.getToDoSortResult(criteria);
			}
			else{
				if("prepare_discrepancy".equals(event)){
					if(globalSearch!=null){
						globalSearch.setSearchIndicator("Todo");
					criteria = globalSearch;
					searchResult = this.getToDoSortResult(criteria);
					
					}else{
						searchResult = this.getSearchResult(criteria);
					}
				}else{
					searchResult = this.getSearchResult(criteria);	
				}
				
			}
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
			 
			result.put("todoGlobal", criteria);
			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, startIndexMain);

			result.put("startIndexMain", startIndexMain);

			// System.out.println("startIndexMain = " + result.get("startIndexMain"));
			// System.out.println("criteria startIndexMain = " +
			// criteria.getStartIndex());
			
				
					result.put("session.searchLeID", criteria.getLegalID());				
					result.put("session.lastUpdatedBy", criteria.getLastUpdatedBy());				
					result.put("session.searchCustomerName", criteria.getCustomerName());				
					result.put("session.searchAANumber", criteria.getAaNumber());
					result.put("session.sort", criteria.getFilterByType());				
					result.put("session.field", criteria.getSortBy());
				
			
			/*if("prepare_discrepancy".equals(event)){
				result.put("searchLeID", searchLeID);				
				result.put("lastUpdatedBy", lastUpdatedBy);				
				result.put("searchCustomerName", searchCustomerName);				
				result.put("searchAANumber", searchAANumber);
			}*/
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
		
		IDiscrepancyJdbc discrepencyJdbc= (IDiscrepancyJdbc)BeanHouse.get("discrepencyJdbc");
		return discrepencyJdbc.searchTransactions(criteria);
	}
	
	protected SearchResult getToDoSearchResult(CMSTrxSearchCriteria criteria) throws TrxOperationException,
	SearchDAOException, RemoteException {

		IDiscrepancyJdbc discrepencyJdbc= (IDiscrepancyJdbc)BeanHouse.get("discrepencyJdbc");
		return discrepencyJdbc.searchTransactionsByCriteria(criteria);
}
	protected SearchResult getToDoSortResult(CMSTrxSearchCriteria criteria ) throws TrxOperationException,
	SearchDAOException, RemoteException {

		IDiscrepancyJdbc discrepencyJdbc= (IDiscrepancyJdbc)BeanHouse.get("discrepencyJdbc");
		return discrepencyJdbc.sortTransactions(criteria);
}

	
	public long getTeamMemberShipIdForBranchUser(int teamID) throws SearchDAOException{
		DBUtil dbUtil = null;
		long  teamMembershipIdForBranchUser = 0;
		try {
			dbUtil = new DBUtil();
			String sql = "SELECT TEAM_MEMBERSHIP_ID FROM CMS_TEAM_MEMBER WHERE USER_ID = (SELECT USER_ID FROM CMS_USER WHERE TEAM_TYPE_MEMBERSHIP_ID = '"+teamID+"' AND ROWNUM = 1)";
			dbUtil.setSQL(sql);
			System.out.println("getTeamMemberShipIdForBranchUser() =>sql=>"+sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				teamMembershipIdForBranchUser=rs.getLong("TEAM_MEMBERSHIP_ID");
				System.out.println("getTeamMemberShipIdForBranchUser() for branch user=>teamMembershipIdForBranchUser=>"+teamMembershipIdForBranchUser);
			}
			rs.close();
			return teamMembershipIdForBranchUser;
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}
	
	
	public long getTeamIdForBranchUser(int teamID) throws SearchDAOException{
		DBUtil dbUtil = null;
		long  teamIdForBranchUser = 0;
		try {
			dbUtil = new DBUtil();
			String sql = "SELECT TEAM_ID FROM CMS_TEAM WHERE TEAM_TYPE_ID = (SELECT DISTINCT TEAM_TYPE_ID FROM CMS_TEAM_TYPE_MEMBERSHIP WHERE TEAM_TYPE_MEMBERSHIP_ID = '"+teamID+"')";
			dbUtil.setSQL(sql);
			System.out.println("getTeamIdForBranchUser() =>sql=>"+sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				teamIdForBranchUser=rs.getLong("TEAM_ID");
				System.out.println("getTeamIdForBranchUser() for branch user=>teamIdForBranchUser=>"+teamIdForBranchUser);
			}
			rs.close();
			return teamIdForBranchUser;
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}
	
	
}
