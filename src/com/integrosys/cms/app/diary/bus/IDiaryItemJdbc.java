/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.diary.bus;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * 
 * This interface defines the data access methods for diary items
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface IDiaryItemJdbc {

	/**
	 * retrieves the number of diary items that are not expired
	 * 
	 * @param team
	 *            team that owns the diary items
	 * @param allowedCountries
	 * @return int - number of non-expired diary items
	 * @throws SearchDAOException
	 */
	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException;

	/**
	 * retrieves the number of diary items that due on the current date
	 * 
	 * @param team
	 *            team that owns the diary items
	 * @param allowedCountries
	 * @return int - number of diary items
	 * @throws SearchDAOException
	 */
	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException;

	/**
	 * retrieves the diary items based on the search criteria
	 * 
	 * @param criteria
	 * @return SearchResult - indexed results
	 * @throws SearchDAOException
	 */
	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	/**
	 * retrieves the diary items that are due on the current date
	 * 
	 * @param criteria
	 * @return SearchResult - indexed results
	 * @throws SearchDAOException
	 */
	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public List getRegionAndSegment(String legalReference);

	public List getListOfFacilityBoardCategory(String customerName);

	public List getListOfFacilityLineNumber(String customerName, String facilityBoardCategory);

	public List getListOfFacilitySerialNumber(String customerName, String facilityBoardCategory, String facilityLineNo);

	public List getActivityList();
	
	public List getFacilityBoardCategory();

	public List getActionList();

	public String getDiarySequence();

	public void insertODScheduleData(StringBuffer sqlInsertQuery);

	public void deleteDiaryItem(String isDelete, long itemId);

	public HashMap<String, String> getODTargetDateList(String diaryNumber);

	public void closeAllODitems(long diaryNumber);

	public SearchResult getGenericListWithSegment(DiaryItemSearchCriteria criteria, String segmentName)
			throws SearchDAOException;

	public SearchResult getGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public SearchResult getDropLineListWithSegment(DiaryItemSearchCriteria criteria, String segmentName)
			throws SearchDAOException;

	public SearchResult getDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public SearchResult getTotalListWithSegment(DiaryItemSearchCriteria criteria, String segmentName)
			throws SearchDAOException;

	public List getMonthAndClosingAmountForOD(String applicationDate, long diaryNumber);

	public SearchResult getTodayGenericListWithSegment(DiaryItemSearchCriteria criteria, String segmentName)
			throws SearchDAOException;

	public SearchResult getTodayGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public SearchResult getTodayDropLineListWithSegment(DiaryItemSearchCriteria criteria, String segmentName)
			throws SearchDAOException;

	public SearchResult getTodayDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public SearchResult getTodayTotalListWithSegment(DiaryItemSearchCriteria criteria, String segmentName)
			throws SearchDAOException;

	public void closeODitems(long diaryNumber, String month);

	public int getMaxMonthOfODScheduleDiary(long diaryNumber);

	public SearchResult getTodayTotalListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public SearchResult getTodayOverDueListWithSegment(DiaryItemSearchCriteria criteria, String segmentName)
			throws SearchDAOException;

	public SearchResult getTodayOverDueListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public void updateItemId(long diaryNumber,Long itemid);

	public Long getItemid(long diaryNumber);

	public String getRegionName(String regionId);
}
