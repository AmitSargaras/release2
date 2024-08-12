package com.integrosys.cms.app.documentlocation.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class DocumentItemAppBusManagerImpl implements IDocumentAppItemBusManager {

	private IDocumentAppTypeItemDao docAppTypeItemDao;

	public IDocumentAppTypeItemDao getDocAppTypeItemDao() {
		return docAppTypeItemDao;
	}

	public void setDocAppTypeItemDao(IDocumentAppTypeItemDao docAppTypeItemDao) {
		this.docAppTypeItemDao = docAppTypeItemDao;
	}
	
	public void deleteDocAppTypeItemByDocId(String key , String status)
	{
		getDocAppTypeItemDao().deleteDocAppTypeItemByDocId(key, status);
		return ;
	}
	
	public void updateDocAppTypeItemId(final String key , final String itemId)
	{
		getDocAppTypeItemDao().updateDocAppTypeItemId(key, itemId);
		return;
	}
	
	public List getDocIdByMasterListId(String masterid)
	{
		return getDocAppTypeItemDao().getDocIdByMasterListId(masterid);
	}
	
	public void updateDocAppTypeItemByDocId(String key , String status)
	{
		getDocAppTypeItemDao().updateDocAppTypeItemStatus(key, status);
		return;
	}
	
	public IDocumentAppTypeItem getDocumentItemAppById(long documentID)
	{
		
		return getDocAppTypeItemDao().getDocAppTypeItem(new Long(documentID));
	}
	
	public List getDocumentItemAppByDocumentId(long documentID , String status)
	{
		
		return getDocAppTypeItemDao().getDocAppTypeItemByDocId(""+documentID , status);
	}
	
	public void saveDocumentAppTypeItemListings(String[] appList , long documentID , String status)
	{
		
		if(status.equalsIgnoreCase("APPROVED"))
		{
			getDocAppTypeItemDao().updateDocAppTypeItemStatus(""+documentID , "L_STATUS");
			for(int i = 0; i < appList.length; i++)
			{
				IDocumentAppTypeItem item = new OBDocumentAppTypeItem();
				item.setDocumentId(new Long(documentID));
				if(appList[i] != null)
				{
					item.setAppType(appList[i]);
					getDocAppTypeItemDao().saveOrUpdateDocAppTypeItemByDocId(item);
				}
			}
		}else if(status.equalsIgnoreCase("PEND_STS"))
		{
			getDocAppTypeItemDao().deleteDocAppTypeItemByDocId(""+documentID , "APPROVED");
			for(int i = 0; i < appList.length; i++)
			{
				IDocumentAppTypeItem item = new OBDocumentAppTypeItem();
				item.setDocumentId(new Long(documentID));
				if(appList[i] != null)
				{
					item.setAppType(appList[i]);
					getDocAppTypeItemDao().saveOrUpdateDocAppTypeItemByDocId(item);
				}
			}
		}
		
		return;
	}

//	public String getDiaryItemName() {
//		return IDiaryDao.ACTUAL_DIARY_ITEM_NAME;
//	}

//	public IDiaryItemJdbc getDiaryItemJdbc() {
//		return diaryItemJdbc;
//	}

//	public void setDiaryItemJdbc(IDiaryItemJdbc diaryItemJdbc) {
//		this.diaryItemJdbc = diaryItemJdbc;
//	}
//
//	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException {
//		return getDiaryItemJdbc().getNoOfNonExpiredDiaryItems(team, allowedCountries);
//	}
//
//	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException {
//		return getDiaryItemJdbc().getNoOfDiaryItemsDueFor(team, allowedCountries);
//	}
//
//	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException {
//		return getDiaryItemJdbc().getNonExpiredDiaryItems(criteria);
//	}
//
//	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException {
//		return getDiaryItemJdbc().getDiaryItemsDueFor(criteria);
//	}
//
//	public IDiaryItemUI getDiaryItem(long itemID) throws DiaryItemException {
//		return getDiaryDao().getDiaryItem(getDiaryItemName(), new Long(itemID));
//	}
//
//	public IDiaryItemUI createDiaryItem(IDiaryItemUI item) throws DiaryItemException {
//		return getDiaryDao().createDiaryItem(getDiaryItemName(), item);
//	}
//
//	public IDiaryItemUI updateDiaryItem(IDiaryItemUI item) throws ConcurrentUpdateException, DiaryItemException {
//		try {
//			return getDiaryDao().updateDiaryItem(getDiaryItemName(), item);
//		}
//		catch (HibernateOptimisticLockingFailureException ex) {
//			throw new ConcurrentUpdateException("current diary item [" + item + "] was updated before by ["
//					+ item.getLastUpdatedBy() + "] at [" + item.getLastUpdatedTime() + "]");
//		}
//	}
}
