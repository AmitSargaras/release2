package com.integrosys.cms.app.documentlocation.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public interface IDocumentAppItemBusManager {
	
	public IDocumentAppTypeItem getDocumentItemAppById(long documentID);
	
	public List getDocumentItemAppByDocumentId(long documentID , String status);
	
	public void saveDocumentAppTypeItemListings(String[] appList , long documentID , String status);
	
	public void deleteDocAppTypeItemByDocId(String key , String status);
	
	public void updateDocAppTypeItemByDocId(String key , String status);
	
	public List getDocIdByMasterListId(String masterid);
	
	public void updateDocAppTypeItemId(final String key , final String itemId);
	
	
//	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException;
//
//	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException;
//
//	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException;
//
//	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException;
//
//	public IDiaryItemUI getDiaryItem(long itemID) throws DiaryItemException;
//
//	public IDiaryItemUI createDiaryItem(IDiaryItemUI item) throws DiaryItemException;
//
//	public IDiaryItemUI updateDiaryItem(IDiaryItemUI item) throws ConcurrentUpdateException, DiaryItemException;

}
