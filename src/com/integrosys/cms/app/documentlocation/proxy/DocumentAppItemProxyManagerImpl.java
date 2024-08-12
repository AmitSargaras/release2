/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.documentlocation.proxy;

// java

import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemUI;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppItemBusManager;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This class act as a facade to the services offered by the diary item modules
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public class DocumentAppItemProxyManagerImpl implements IDocumentAppItemProxyManager {

	private IDocumentAppItemBusManager documentItemAppBusManager;
	
	public IDocumentAppItemBusManager getDocumentItemAppBusManager() {
		return documentItemAppBusManager;
	}

	public void setDocumentItemAppBusManager(
			IDocumentAppItemBusManager documentItemAppBusManager) {
		this.documentItemAppBusManager = documentItemAppBusManager;
	}
	
	public IDocumentAppTypeItem getDocumentItemAppById(long documentID)
	{
		return getDocumentItemAppBusManager().getDocumentItemAppById(documentID);
	}
	
	public List getDocumentItemAppByDocumentId(long documentID , String status)
	{
		return getDocumentItemAppBusManager().getDocumentItemAppByDocumentId(documentID,status);
	}
	
	public void saveDocumentAppTypeItemListings(String[] appList , long documentID , String status)
	{
		getDocumentItemAppBusManager().saveDocumentAppTypeItemListings(appList, documentID , status);
		return;
	}
	
	public void closeDocAppTypeItemByDocId(String key , String status)
	{
		getDocumentItemAppBusManager().deleteDocAppTypeItemByDocId(key, status);
		getDocumentItemAppBusManager().updateDocAppTypeItemByDocId(key, status);
		return;
	}
	
	public void deleteDocAppTypeItemByDocId(String key , String status)
	{
		getDocumentItemAppBusManager().deleteDocAppTypeItemByDocId(key, status);
		return;
	}
	
	public void updateDocAppTypeItemId(final String key , final String itemId)
	{
		getDocumentItemAppBusManager().updateDocAppTypeItemId(key, itemId);
		return;
	}
	
	public List getDocIdByMasterListId(String masterid)
	{
		return getDocumentItemAppBusManager().getDocIdByMasterListId(masterid);
	}
	
//	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException {
//		return getDiaryItemBusManager().getNoOfNonExpiredDiaryItems(team, allowedCountries);
//	}
//
//	public int getDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException {
//		return getDiaryItemBusManager().getNoOfDiaryItemsDueFor(team, allowedCountries);
//
//	}
//
//	public IDiaryItem getDiaryItem(long itemID) throws DiaryItemException {
//		return getDiaryItemBusManager().getDiaryItem(itemID);
//
//	}
//
//	public IDiaryItem createDiaryItem(IDiaryItem item) throws DiaryItemException {
//		IDiaryItemUI itemui = (IDiaryItemUI) item;
//		return getDiaryItemBusManager().createDiaryItem(itemui);
//	}
//
//	public IDiaryItem updateDiaryItem(IDiaryItem item) throws DiaryItemException, ConcurrentUpdateException {
//		IDiaryItemUI itemui = (IDiaryItemUI) item;
//		return getDiaryItemBusManager().updateDiaryItem(itemui);
//	}
//
//	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException {
//
//		return getDiaryItemBusManager().getNonExpiredDiaryItems(criteria);
//
//	}
//
//	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException {
//
//		return getDiaryItemBusManager().getDiaryItemsDueFor(criteria);
//
//	}


}
