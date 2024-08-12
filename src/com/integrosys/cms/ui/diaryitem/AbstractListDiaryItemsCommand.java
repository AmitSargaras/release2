/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/AbstractListDiaryItemsCommand.java,v 1.8 2005/11/13 12:06:04 jtan Exp $
 */

package com.integrosys.cms.ui.diaryitem;

import java.util.Collection;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.bus.IDiaryDao;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This command lists the diary item belonging to a team $Author: jtan $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/11/13 12:06:04 $ Tag: $Name: $
 */
public abstract class AbstractListDiaryItemsCommand extends DiaryItemsCommand {

	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	/**
	 * Default Constructor
	 */
	public AbstractListDiaryItemsCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "DiaryItemSearchCriteria", "com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria", FORM_SCOPE },
				
				{ "genericCount", "java.lang.String", REQUEST_SCOPE },
				{ "odCount", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{ "segmentName", "java.lang.String", REQUEST_SCOPE },
				{ "overDueCount", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerIndex", "java.lang.String", REQUEST_SCOPE },
				{ "countryFilter", "java.lang.String", REQUEST_SCOPE },
				{ "allowedCountry", "java.lang.String", REQUEST_SCOPE },
				{ "session.frompage", "java.lang.String", SERVICE_SCOPE },
				{ "session.startIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.customerIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.countryFilter", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				// CR-120 Search Diary Item
				{ "searchLeID", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
				{ "totalCountForCurrentTotalPages", "java.lang.Integer", SERVICE_SCOPE } });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "DiaryItemList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "recordsPerPage", "java.lang.Integer", REQUEST_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
				{ "session.frompage", "java.lang.String", SERVICE_SCOPE },
				{ "session.startIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.customerIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.countryFilter", "java.lang.String", SERVICE_SCOPE },
				{ "CountryValues", "java.util.Collection", REQUEST_SCOPE },
				{ "CountryLabels", "java.util.Collection", REQUEST_SCOPE },
				// CR-120 Search Diary Item
				{ "session.CountryValues", "java.util.Collection", SERVICE_SCOPE },
				{ "session.CountryLabels", "java.util.Collection", SERVICE_SCOPE },
				{ "totalCountForCurrentTotalPages", "java.lang.Integer", SERVICE_SCOPE },
				
				{ "genericCount", "java.lang.String", SERVICE_SCOPE },
				{ "odCount", "java.lang.String", SERVICE_SCOPE },
				{ "totalCount", "java.lang.String", SERVICE_SCOPE },
				{ "segment", "java.lang.String", SERVICE_SCOPE },
				{ "overDueCount", "java.lang.String", SERVICE_SCOPE },
				{ "segmentName", "java.lang.String", SERVICE_SCOPE }
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		DefaultLogger.debug(this, "IN method AbstractListDiaryItemsCommand.doExecute !!! ");

		String fromPage = (String) map.get("frompage");
		String startIndex = (String) map.get("startIndex");
		String customerIndex = (String) map.get("customerIndex");

		String countryFilter = (String) map.get("countryFilter");
		final String allowedCountry = (String) map.get("allowedCountry");

		String searchLeID = (String) map.get("searchLeID");
		String searchCustomerName = (String) map.get("searchCustomerName");
		
		String genericCount = (String) map.get("genericCount");
		String odCount = (String) map.get("odCount");
		String totalCount = (String) map.get("totalCount");
		String segment = (String) map.get("segment");
		String overDueCount = (String) map.get("overDueCount");
		
		String event = (String) map.get("event");
		
		String segmentName = (String) map.get("segmentName");

		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		if (criteria != null) {
			if (startIndex != null && !startIndex.equals("null")) {
				criteria.setStartIndex(Integer.parseInt(startIndex));
				int totalCountForCurrentTotalPages = this.recordsPerPageForPagination * this.totalPageForPagination;
				if (criteria.getStartIndex() % totalCountForCurrentTotalPages == 0) {
					criteria.setTotalCountForCurrentTotalPages(null);
				}
				else {
					Integer totalCountForCurrentTotalPagesObject = (Integer) map.get("totalCountForCurrentTotalPages");
					criteria.setTotalCountForCurrentTotalPages(totalCountForCurrentTotalPagesObject);
				}
			}
			else {
				criteria.setStartIndex(0);
			}

			if (countryFilter != null) {
				if (countryFilter.equals(DiaryItemListMapper.NO_FILTER)) {
					criteria.setCountryFilter(null);
					countryFilter = null;
				}
				else {
					criteria.setCountryFilter(countryFilter);
				}
			}
			else if (allowedCountry != null) {
				if (allowedCountry.equals(DiaryItemListMapper.NO_SELECT)
						|| allowedCountry.equals(DiaryItemListMapper.NO_FILTER)) {
					criteria.setCountryFilter(null);
					countryFilter = null;
				}
				else {
					criteria.setCountryFilter(allowedCountry);
					countryFilter = allowedCountry;
				}
			}

			if (!DiaryItemHelper.isNull(customerIndex)) {
				criteria.setCustomerIndex(customerIndex);
				criteria.setCountryFilter(null);
				countryFilter = null;
			}

			if (!DiaryItemHelper.isNull(searchCustomerName)) {
				criteria.setCustomerIndex(searchCustomerName);
				criteria.setCountryFilter(null);
				countryFilter = null;
			}
//			==============added by rajendra for Asst validator============
			if(searchCustomerName!=null && !searchCustomerName.trim().equals("")){ 
				 
				 boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(searchCustomerName);
					if( codeFlag == true)
					{
						criteria.setCustomerIndex("");
						criteria.setCountryFilter(null);
						countryFilter = null;
						
					}
					
				}
			if(searchCustomerName!=null && !searchCustomerName.trim().equals("") 
					&& searchCustomerName.length()>50)
			{
				criteria.setCustomerIndex("");
				criteria.setCountryFilter(null);
				countryFilter = null;
				
					
			}

			if (!DiaryItemHelper.isNull(searchLeID)) {
				criteria.setLeID(searchLeID);
				criteria.setCountryFilter(null);
				countryFilter = null;
			}
//			==============added by rajendra for Asst validator============
			if (!DiaryItemHelper.isNull(searchLeID)) {
					if(searchLeID!=null && !searchLeID.trim().equals("")){ 
					boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(searchLeID);
						if( codeFlag == true)
						{
							criteria.setLeID("");
							criteria.setCountryFilter(null);
							countryFilter = null;
						}
					}
				}
		}
		if(searchLeID!=null && !searchLeID.trim().equals("") 
					&& searchLeID.length()>30)
			{
			criteria.setLeID("");
			criteria.setCountryFilter(null);
			countryFilter = null;
					
					
			}

		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

		if (team == null) {
			throw new CommandProcessingException("Team information is null!");
		}
		HashMap reverseCountryMap = DiaryItemHelper.getAllowedCountries(team);

		Collection countryLabels = DiaryItemHelper.getSortedCountryLabels(reverseCountryMap);

		Collection countryValues = DiaryItemHelper.getSortedCountryValues(countryLabels, reverseCountryMap);

		result.put("CountryValues", countryValues);
		result.put("CountryLabels", countryLabels);
		result.put("session.CountryValues", countryValues);
		result.put("session.CountryLabels", countryLabels);

		result.put("frompage", fromPage);
		result.put("session.frompage", fromPage);
		result.put("session.startIndex", startIndex);
		result.put("session.customerIndex", customerIndex);
		result.put("session.countryFilter", countryFilter);
		
		IDiaryDao diaryDao =(IDiaryDao)BeanHouse.get("diaryDao");
		SearchResult searchResult = null;
		if("list_non_expired".equals(event))
		{
			if("Y".equalsIgnoreCase(genericCount)){
				if("Y".equalsIgnoreCase(segment)){
					 searchResult = getGenericListWithSegment(map,segmentName);
				}else{
					 searchResult = getGenericListWithoutSegment(map);
				}
			}else if("Y".equalsIgnoreCase(odCount)){
				if("Y".equalsIgnoreCase(segment)){
					 searchResult = getDropLineListWithSegment(map,segmentName);
				}else{
					 searchResult = getDropLineListWithoutSegment(map);
				}
			}else if("Y".equalsIgnoreCase(totalCount)){
				if("Y".equalsIgnoreCase(segment)){
					 searchResult = getTotalListWithSegment(map,segmentName);
				}else{
					 searchResult = performSearch(map);
				}
			}
		}else if("list_due_items".equals(event)){
			if("Y".equalsIgnoreCase(genericCount)){
				if("Y".equalsIgnoreCase(segment)){
					 searchResult = getTodayGenericListWithSegment(map,segmentName);
				}else{
					 searchResult = getTodayGenericListWithoutSegment(map);
				}
			}else if("Y".equalsIgnoreCase(odCount)){
				if("Y".equalsIgnoreCase(segment)){
					 searchResult = getTodayDropLineListWithSegment(map,segmentName);
				}else{
					 searchResult = getTodayDropLineListWithoutSegment(map);
				}
			}else if("Y".equalsIgnoreCase(overDueCount)){
				if("Y".equalsIgnoreCase(segment)){
					 searchResult = getTodayOverDueListWithSegment(map,segmentName);
				}else{
					 searchResult = getTodayOverDueListWithoutSegment(map);
				}
			}else if("Y".equalsIgnoreCase(totalCount)){
				if("Y".equalsIgnoreCase(segment)){
					 searchResult = getTodayTotalListWithSegment(map,segmentName);
				}else{
					 searchResult = getTodayTotalListWithoutSegment(map);
				}
			}
		}
		//searchResult = performSearch(map);
		
		result.put("genericCount", genericCount);
		result.put("segment", segment);
		result.put("odCount", odCount);
		result.put("totalCount", totalCount);
		result.put("segmentName", segmentName);
		result.put("overDueCount", overDueCount);
		
		result.put("DiaryItemList", searchResult);
		result.put("recordsPerPage", new Integer(DiaryItemListMapper.getDefaultPaginationCount()));

		if (searchResult != null) {
			result.put("totalCountForCurrentTotalPages", new Integer(searchResult.getNItems()));
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	protected abstract SearchResult performSearch(HashMap map) throws DiaryItemException, SearchDAOException;

	protected abstract SearchResult getGenericListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getDropLineListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getTotalListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException;

	
	protected abstract SearchResult getTodayGenericListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getTodayGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getTodayDropLineListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getTodayDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException;
	
	protected abstract SearchResult getTodayTotalListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException;

	protected abstract SearchResult getTodayTotalListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException;

	protected abstract SearchResult getTodayOverDueListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException;

	protected abstract SearchResult getTodayOverDueListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException;
}
