package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;

/**
 * This command lists the diary items that are due on the current date $Author:
 * jtan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/05/27 00:42:54 $ Tag: $Name: $
 */
public class ListDueItemsCommand extends AbstractListDiaryItemsCommand {

	/**
	 * Default Constructor
	 */
	public ListDueItemsCommand() {
		super();
	}

	/**
	 * performs the search for diary items that are due
	 * @param map
	 * @return SearchResult - indexed results
	 * @throws DiaryItemException
	 * @throws SearchDAOException
	 */
	protected SearchResult performSearch(HashMap map) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getDiaryItemsDueFor(criteria);

	}

	@Override
	protected SearchResult getGenericListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getGenericListWithSegment(criteria,segmentName);
	}

	@Override
	protected SearchResult getGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getGenericListWithoutSegment(criteria);
	}

	@Override
	protected SearchResult getDropLineListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getDropLineListWithSegment(criteria,segmentName);
	}

	@Override
	protected SearchResult getDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getDropLineListWithoutSegment(criteria);
	}

	@Override
	protected SearchResult getTotalListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTotalListWithSegment(criteria,segmentName);
	}
	
	
	
	
	
	@Override
	protected SearchResult getTodayGenericListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayGenericListWithSegment(criteria,segmentName);
	}

	@Override
	protected SearchResult getTodayGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayGenericListWithoutSegment(criteria);
	}

	@Override
	protected SearchResult getTodayDropLineListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayDropLineListWithSegment(criteria,segmentName);
	}

	@Override
	protected SearchResult getTodayDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayDropLineListWithoutSegment(criteria);
	}

	@Override
	protected SearchResult getTodayTotalListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayTotalListWithSegment(criteria,segmentName);
	}

	@Override
	protected SearchResult getTodayTotalListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayTotalListWithoutSegment(criteria);
	}
	
	@Override
	protected SearchResult getTodayOverDueListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayOverDueListWithSegment(criteria,segmentName);
	}

	@Override
	protected SearchResult getTodayOverDueListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");
		return proxyMgr.getTodayOverDueListWithoutSegment(criteria);
	}
}
