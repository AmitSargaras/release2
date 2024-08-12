package com.integrosys.cms.app.diary.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface IDiaryItemProxyManager {

	/**
	 * retrieves the number of non-expired diary items
	 * @param team team that owns the diary items, can be further used such as
	 *        by team, or team type.
	 * @param allowedCountries
	 * @return int - number of non-expired diary items
	 * @throws DiaryItemException
	 */
	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException;

	/**
	 * retrieves the number of diary items that are due on the current date
	 * @param team team that owns the diary items, can be further used such as
	 *        by team, or team type.
	 * @param allowedCountries
	 * @return int- number of items due
	 * @throws SearchDAOException
	 */
	public int getDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException;

	/**
	 * retrieves a diary item
	 * @param itemID - diary item ID
	 * @return IDiaryItem - diary item value object
	 * @throws com.integrosys.cms.app.diary.bus.DiaryItemException
	 */
	public IDiaryItem getDiaryItem(long itemID) throws DiaryItemException;

	/**
	 * creates a diary item
	 * @param item - diary item value object
	 * @throws com.integrosys.cms.app.diary.bus.DiaryItemException
	 */
	public IDiaryItem createDiaryItem(IDiaryItem item) throws DiaryItemException;

	/**
	 * updates the details of a diary item
	 * @param item - diary item value object
	 * @throws com.integrosys.cms.app.diary.bus.DiaryItemException
	 */
	public IDiaryItem updateDiaryItem(IDiaryItem item) throws DiaryItemException, ConcurrentUpdateException;

	/**
	 * retrieves a list of non-expired diary items
	 * @param criteria
	 * @return list of non-expired diary items
	 * @throws DiaryItemException
	 * @throws SearchDAOException
	 */
	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException,
			SearchDAOException;

	/**
	 * retrieves a list of diary items due on current date
	 * @param criteria
	 * @return list of non-expired diary items
	 * @throws DiaryItemException
	 * @throws SearchDAOException
	 */
	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException,
			SearchDAOException;

	public SearchResult getAllDiaryItemList() throws DiaryItemException;
	
	public List getRegionAndSegment(String legalReference);
	
	public SearchResult getGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException,
	SearchDAOException;
	
	
	public SearchResult getTodayGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getTodayGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getTodayDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getTodayDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getTodayTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException,
	SearchDAOException;

	public SearchResult getTodayTotalListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException,
	SearchDAOException;
	
	public SearchResult getTodayOverDueListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException,
	SearchDAOException;

	public SearchResult getTodayOverDueListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException,
	SearchDAOException;
}
