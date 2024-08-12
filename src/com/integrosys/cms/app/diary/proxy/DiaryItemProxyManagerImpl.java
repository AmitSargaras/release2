/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.diary.proxy;

import java.util.ArrayList;
import java.util.List;

// java

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemBusManager;
import com.integrosys.cms.app.diary.bus.IDiaryItemUI;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This class act as a facade to the services offered by the diary item modules
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public class DiaryItemProxyManagerImpl implements IDiaryItemProxyManager {

	private IDiaryItemBusManager diaryItemBusManager;

	public IDiaryItemBusManager getDiaryItemBusManager() {
		return diaryItemBusManager;
	}

	public void setDiaryItemBusManager(IDiaryItemBusManager diaryItemBusManager) {
		this.diaryItemBusManager = diaryItemBusManager;
	}

	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException {
		return getDiaryItemBusManager().getNoOfNonExpiredDiaryItems(team, allowedCountries);
	}

	public int getDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException {
		return getDiaryItemBusManager().getNoOfDiaryItemsDueFor(team, allowedCountries);

	}

	public IDiaryItem getDiaryItem(long itemID) throws DiaryItemException {
		return getDiaryItemBusManager().getDiaryItem(itemID);

	}

	public IDiaryItem createDiaryItem(IDiaryItem item) throws DiaryItemException {
		IDiaryItemUI itemui = (IDiaryItemUI) item;
		return getDiaryItemBusManager().createDiaryItem(itemui);
	}

	public IDiaryItem updateDiaryItem(IDiaryItem item) throws DiaryItemException, ConcurrentUpdateException {
		IDiaryItemUI itemui = (IDiaryItemUI) item;
		return getDiaryItemBusManager().updateDiaryItem(itemui);
	}

	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getNonExpiredDiaryItems(criteria);

	}

	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getDiaryItemsDueFor(criteria);

	}
	
	public SearchResult getAllDiaryItemList() throws DiaryItemException {
		return getDiaryItemBusManager().getAllDiaryItemList();
	}
	
	public List getRegionAndSegment(String legalReference) {
		return  getDiaryItemBusManager().getRegionAndSegment(legalReference);
	}
	
	public SearchResult getGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {

		return getDiaryItemBusManager().getGenericListWithSegment(criteria,segmentName);

	}
	
	public SearchResult getGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getGenericListWithoutSegment(criteria);

	}
	
	public SearchResult getDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {

		return getDiaryItemBusManager().getDropLineListWithSegment(criteria,segmentName);

	}
	
	public SearchResult getDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getDropLineListWithoutSegment(criteria);

	}
	
	public SearchResult getTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {

		return getDiaryItemBusManager().getTotalListWithSegment(criteria,segmentName);

	}
	
	
	
	
	
	public SearchResult getTodayGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayGenericListWithSegment(criteria,segmentName);

	}
	
	public SearchResult getTodayGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayGenericListWithoutSegment(criteria);

	}
	
	public SearchResult getTodayDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayDropLineListWithSegment(criteria,segmentName);

	}
	
	public SearchResult getTodayDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayDropLineListWithoutSegment(criteria);

	}
	
	public SearchResult getTodayTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayTotalListWithSegment(criteria,segmentName);

	}
	
	public SearchResult getTodayTotalListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayTotalListWithoutSegment(criteria);

	}
	
	public SearchResult getTodayOverDueListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayOverDueListWithSegment(criteria,segmentName);

	}
	
	public SearchResult getTodayOverDueListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {

		return getDiaryItemBusManager().getTodayOverDueListWithoutSegment(criteria);

	}
	
}
