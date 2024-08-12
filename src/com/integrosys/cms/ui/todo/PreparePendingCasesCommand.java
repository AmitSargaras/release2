package com.integrosys.cms.ui.todo;

import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class PreparePendingCasesCommand extends PrepareTodoCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "task", "java.lang.String", REQUEST_SCOPE },
				{ "sortBy", "java.lang.String", REQUEST_SCOPE },
				{ "filterByType", "java.lang.String", REQUEST_SCOPE },
				{ "filterByValue", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "isMenu", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE }, });
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
				{ "session.searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
				{ "filterByTypeCode", "java.util.Collection", REQUEST_SCOPE },
				{ "filterByTypeValues", "java.util.Collection", REQUEST_SCOPE },
				{ "sortByCode", "java.util.Collection", REQUEST_SCOPE },
				{ "sortByValues", "java.util.Collection", REQUEST_SCOPE },
				{ "filterByValueList", "java.util.List", REQUEST_SCOPE },
				{ "cmsSegmentList", "java.util.List", REQUEST_SCOPE },
				{ "todoMapper", "com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", FORM_SCOPE }, });
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			int startIndex = 0;
			String startIndexStr = (String) map.get("startIndex");
			if ((startIndexStr != null) && (startIndexStr.trim().length() > 0)) {
				startIndex = Integer.parseInt(startIndexStr);
			}
			else {
				String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
				if ((globalStartIndex != null) && (globalStartIndex.trim().length() > 0)
						&& !globalStartIndex.trim().equals("null")) {
					startIndex = Integer.parseInt(globalStartIndex);
				}
			}

			CMSTrxSearchCriteria searchCriteria = null;

			String isMenu = (String) map.get("isMenu");
			if ((isMenu != null) && isMenu.equals("Y")) {
				searchCriteria = new CMSTrxSearchCriteria();
				startIndex = 0;
			}
			else {
				searchCriteria = (CMSTrxSearchCriteria) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ);
			}

			if ((isMenu == null) || !isMenu.equals("N")) {
				searchCriteria.setSortBy((String) map.get("sortBy"));
				searchCriteria.setFilterByType((String) map.get("filterByType"));
				searchCriteria.setFilterByValue((String) map.get("filterByValue"));
				searchCriteria.setPendingTask((String) map.get("task"));
			}

			searchCriteria.setStartIndex(startIndex);
			searchCriteria.setNItems(10);

			result.put("todoMapper", searchCriteria);

			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			searchCriteria.setTeamID(team.getTeamID());

			SearchResult searchResult = this.getSearchResult(searchCriteria);

			CommonCodeList codeList = CommonCodeList.getInstance(null, ICategoryEntryConstant.FILTER_BY);
			result.put("filterByTypeCode", codeList.getCommonCodeValues());
			result.put("filterByTypeValues", codeList.getCommonCodeLabels());

			codeList = CommonCodeList.getInstance(null, ICategoryEntryConstant.SORT_BY);
			result.put("sortByCode", codeList.getCommonCodeValues());
			result.put("sortByValues", codeList.getCommonCodeLabels());

			result.put("filterByValueList", TodoHelper.getFilterByValueList(team, searchCriteria.getFilterByType()));

			result.put("cmsSegmentList", TodoHelper.getCMSSegmentList());

			result.put("searchResult", searchResult);
			result.put("session.searchResult", searchResult);

			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, String.valueOf(startIndex));
			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ, searchCriteria);
		}
		catch (Throwable e) {
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(COMMAND_RESULT_MAP, result);
		return returnMap;
	}

	protected SearchResult getSearchResult(CMSTrxSearchCriteria criteria) throws TrxOperationException,
			SearchDAOException, RemoteException {
		return getWorkflowManager().searchPendingCases(criteria);
	}

}