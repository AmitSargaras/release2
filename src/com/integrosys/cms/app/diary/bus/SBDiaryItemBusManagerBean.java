package com.integrosys.cms.app.diary.bus;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Session bean implementation of the services provided by the diary item bus
 * manager.
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public class SBDiaryItemBusManagerBean implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context;

	/**
	 * Default constructor.
	 */
	public SBDiaryItemBusManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		_context = sc;
	}

	/**
	 * retrieves the number of non-expired diary items
	 * @param team
	 * @return int - number of non-expired diary items
	 * @throws DiaryItemException
	 */
	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws DiaryItemException {
		try {
			return getDiaryItemJdbcDao().getNoOfNonExpiredDiaryItems(team, allowedCountries);
		}
		catch (SearchDAOException ex) {
			throw new DiaryItemException("Exception in getNoOfNonExpiredDiaryItems: ", ex);
		}
	}

	/**
	 * retrieves the number of diary items due on the current date
	 * @param team
	 * @return int - number of diary items due
	 * @throws DiaryItemException
	 * @deprecated
	 */
	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws DiaryItemException,
			SearchDAOException {
		return getDiaryItemJdbcDao().getNoOfDiaryItemsDueFor(team, allowedCountries);
	}

	/**
	 * retrieves a list of non-expired diary items
	 * @param criteria
	 * @return list of non-expired diary items
	 * @throws DiaryItemException
	 */
	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws DiaryItemException,
			SearchDAOException {
		return getDiaryItemJdbcDao().getNonExpiredDiaryItems(criteria);
	}

	/**
	 * retrieves a list of diary items due on current date
	 * @param criteria
	 * @return list of non-expired diary items
	 * @throws DiaryItemException
	 */
	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws DiaryItemException,
			SearchDAOException {
		return getDiaryItemJdbcDao().getDiaryItemsDueFor(criteria);
	}

	/**
	 * retrieves a diary item
	 * @param itemID - diary item ID
	 * @return IDiaryItem - diary item value object
	 * @throws DiaryItemException
	 */
	public IDiaryItem getDiaryItem(long itemID) throws DiaryItemException {
		return getDiaryDao().getDiaryItem(IDiaryDao.ACTUAL_DIARY_ITEM_NAME, new Long(itemID));
	}

	/**
	 * creates a diary item
	 * @param item - diary item value object
	 * @return item - diary item value object
	 * @throws DiaryItemException
	 */
	public IDiaryItem createDiaryItem(IDiaryItem item) throws DiaryItemException {

		if (item == null) {
			throw new DiaryItemException("Diary Item to be created is null!");
		}

		IDiaryItemUI diaryItem = new OBDiaryItem();
		AccessorUtil.copyValue(item, diaryItem);

		return getDiaryDao().createDiaryItem(IDiaryDao.ACTUAL_DIARY_ITEM_NAME, diaryItem);
	}

	/**
	 * updates the details of a diary item
	 * @param item - diary item value object
	 * @return item - diary item value object
	 * @throws ConcurrentUpdateException
	 * @throws DiaryItemException
	 */
	public IDiaryItem updateDiaryItem(IDiaryItem item) throws ConcurrentUpdateException, DiaryItemException {

		if (item == null) {
			throw new DiaryItemException("Diary Item to be updated is null!");
		}

		IDiaryItemUI diaryItem = new OBDiaryItem();
		AccessorUtil.copyValue(item, diaryItem);

		try {
			return getDiaryDao().updateDiaryItem(IDiaryDao.ACTUAL_DIARY_ITEM_NAME, diaryItem);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new ConcurrentUpdateException("diary item [" + item
					+ "] has been updated before; nested exception is " + ex);
		}
	}

	protected IDiaryItemJdbc getDiaryItemJdbcDao() {
		return (IDiaryItemJdbc) BeanHouse.get("diaryItemJdbc");
	}

	protected IDiaryDao getDiaryDao() {
		return (IDiaryDao) BeanHouse.get("diaryDao");
	}

}
