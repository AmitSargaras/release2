package com.integrosys.cms.app.diary.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class DiaryItemBusManagerImpl implements IDiaryItemBusManager {

	private IDiaryDao diaryDao;

	private IDiaryItemJdbc diaryItemJdbc;

	public IDiaryDao getDiaryDao() {
		return diaryDao;
	}

	public void setDiaryDao(IDiaryDao diaryDao) {
		this.diaryDao = diaryDao;
	}

	public String getDiaryItemName() {
		return IDiaryDao.ACTUAL_DIARY_ITEM_NAME;
	}

	public IDiaryItemJdbc getDiaryItemJdbc() {
		return diaryItemJdbc;
	}

	public void setDiaryItemJdbc(IDiaryItemJdbc diaryItemJdbc) {
		this.diaryItemJdbc = diaryItemJdbc;
	}

	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException {
		return getDiaryItemJdbc().getNoOfNonExpiredDiaryItems(team, allowedCountries);
	}

	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException {
		return getDiaryItemJdbc().getNoOfDiaryItemsDueFor(team, allowedCountries);
	}

	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getNonExpiredDiaryItems(criteria);
	}

	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getDiaryItemsDueFor(criteria);
	}

	public IDiaryItemUI getDiaryItem(long itemID) throws DiaryItemException {
		return getDiaryDao().getDiaryItem(getDiaryItemName(), new Long(itemID));
	}

	public IDiaryItemUI createDiaryItem(IDiaryItemUI item) throws DiaryItemException {
		return getDiaryDao().createDiaryItem(getDiaryItemName(), item);
	}

	public IDiaryItemUI updateDiaryItem(IDiaryItemUI item) throws ConcurrentUpdateException, DiaryItemException {
		try {
			return getDiaryDao().updateDiaryItem(getDiaryItemName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new ConcurrentUpdateException("current diary item [" + item + "] was updated before by ["
					+ item.getLastUpdatedBy() + "] at [" + item.getLastUpdatedTime() + "]");
		}
	}
	
	public SearchResult getAllDiaryItemList() throws DiaryItemException {
		return getDiaryDao().getAllDiaryItemList(getDiaryItemName());
	}
	
	public List getRegionAndSegment(String legalReference) {
		 return getDiaryItemJdbc().getRegionAndSegment( legalReference);
		
	}
	
	public SearchResult getGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		return getDiaryItemJdbc().getGenericListWithSegment(criteria,segmentName);
	}
	
	public SearchResult getGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getGenericListWithoutSegment(criteria);
	}
	
	public SearchResult getDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		return getDiaryItemJdbc().getDropLineListWithSegment(criteria,segmentName);
	}
	
	public SearchResult getDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getDropLineListWithoutSegment(criteria);
	}
	
	public SearchResult getTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		return getDiaryItemJdbc().getTotalListWithSegment(criteria,segmentName);
	}
	
	
	
	
	public SearchResult getTodayGenericListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayGenericListWithSegment(criteria,segmentName);
	}
	
	public SearchResult getTodayGenericListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayGenericListWithoutSegment(criteria);
	}
	
	public SearchResult getTodayDropLineListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayDropLineListWithSegment(criteria,segmentName);
	}
	
	public SearchResult getTodayDropLineListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayDropLineListWithoutSegment(criteria);
	}
	
	public SearchResult getTodayTotalListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayTotalListWithSegment(criteria,segmentName);
	}

	public SearchResult getTodayTotalListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayTotalListWithoutSegment(criteria);
	}
	
	public SearchResult getTodayOverDueListWithSegment(DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayOverDueListWithSegment(criteria,segmentName);
	}

	public SearchResult getTodayOverDueListWithoutSegment(DiaryItemSearchCriteria criteria) throws SearchDAOException {
		return getDiaryItemJdbc().getTodayOverDueListWithoutSegment(criteria);
	}
}
