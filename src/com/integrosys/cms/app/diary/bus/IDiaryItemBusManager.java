package com.integrosys.cms.app.diary.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public interface IDiaryItemBusManager {
	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException;

	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException;

	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException;

	public IDiaryItemUI getDiaryItem(long itemID) throws DiaryItemException;

	public IDiaryItemUI createDiaryItem(IDiaryItemUI item) throws DiaryItemException;

	public IDiaryItemUI updateDiaryItem(IDiaryItemUI item) throws ConcurrentUpdateException, DiaryItemException;

	public SearchResult getAllDiaryItemList() throws DiaryItemException;
	
	public List getRegionAndSegment(String legalReference);

	public SearchResult getGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException;
	
	public SearchResult getGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;
	
	public SearchResult getDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException;
	
	public SearchResult getDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;
	
	public SearchResult getTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException;
	
	public SearchResult getTodayGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException;
	
	public SearchResult getTodayGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;
	
	public SearchResult getTodayDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException;
	
	public SearchResult getTodayDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;
	
	public SearchResult getTodayTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException;

	public SearchResult getTodayTotalListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;
	
	public SearchResult getTodayOverDueListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException;

	public SearchResult getTodayOverDueListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException;
}
