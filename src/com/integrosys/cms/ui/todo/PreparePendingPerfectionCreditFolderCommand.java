package com.integrosys.cms.ui.todo;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * UI Command class to cater for Pending Perfection Credit Folder function.
 * 
 * @author Chong Jun Yong
 * 
 */
public class PreparePendingPerfectionCreditFolderCommand extends AbstractCommand {

	private SBCMSTrxManager workflowManager;

	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public void setWorkflowManager(SBCMSTrxManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	public SBCMSTrxManager getWorkflowManager() {
		return workflowManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "legalName", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ "searchLeID", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
				{ "searchAANumber", "java.lang.String", REQUEST_SCOPE },
				{ "isBtnClicked", "java.lang.String", REQUEST_SCOPE },
				{ "isMenu", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },

		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Entering doExecute()");
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();

		int stindex = 0;
		String startIndex = (String) map.get("startIndex");
		String searchLeID = (String) map.get("searchLeID");
		String searchCustomerName = (String) map.get("searchCustomerName");
		String searchAANumber = (String) map.get("searchAANumber");

		String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
		String legalName = (String) map.get("legalName");

		try {
			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
			CMSTrxSearchCriteria globalSearch = (CMSTrxSearchCriteria) map
					.get(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ);

			CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
			boolean isFormScope = false;

			if (StringUtils.isBlank(startIndex)) {
				if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
					stindex = 0;
					startIndex = String.valueOf(stindex);
					result.put("startIndex", startIndex);

				}
				else {
					stindex = Integer.parseInt(globalStartIndex);
					startIndex = globalStartIndex;
					result.put("startIndex", startIndex);
				}
			}
			else {
				stindex = Integer.parseInt(startIndex);
				result.put("startIndex", startIndex);
			}

			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

			criteria.setNItems(10);
			criteria.setTeamID(team.getTeamID());

			if ("Y".equalsIgnoreCase((String) map.get("isBtnClicked"))) {
				isFormScope = true;
			}

			if (StringUtils.isNotBlank(searchCustomerName)) {
				criteria.setCustomerName(searchCustomerName);
			}

			if (StringUtils.isNotBlank(legalName) && StringUtils.isBlank(criteria.getCustomerName())) {
				criteria.setCustomerName(legalName);
			}

			if (StringUtils.isNotBlank(searchLeID)) {
				criteria.setLegalID(searchLeID);
			}

			if (StringUtils.isNotBlank(searchAANumber)) {
				criteria.setAaNumber(searchAANumber);
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
			}

			criteria.setStartIndex(stindex);
			criteria.setPendingTask(ICMSConstant.CREDIT_FOLDER);

			int totalAvalaibleRecordsForPages = this.recordsPerPageForPagination * this.totalPageForPagination;
			if (stindex % totalAvalaibleRecordsForPages == 0) {
				criteria.setTotalCountForCurrentTotalPages(null);
			}

			SearchResult searchResult = getSearchResult(criteria);
			criteria.setTotalCountForCurrentTotalPages(new Integer(searchResult.getNItems()));

			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ, criteria);
			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, startIndex);
			result.put("startIndex", startIndex);
			result.put("searchResult", searchResult);
			result.put("session.searchResult", searchResult);
			result.put(IGlobalConstant.USER_TEAM, team);
			result.put(IGlobalConstant.USER, user);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return returnMap;
		}
		catch (Throwable e) {
			throw new CommandProcessingException("not able to submit pending perfection credit folder transaction", e);
		}
	}

	protected SearchResult getSearchResult(CMSTrxSearchCriteria criteria) throws TrxOperationException,
			SearchDAOException, RemoteException {
		return getWorkflowManager().searchPendingCases(criteria);
	}
}
