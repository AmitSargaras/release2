/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/SBDiaryItemBusManager.java,v 1.5 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.diary.bus;

//java

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Session bean remote interface for the services provided by the diary item bus
 * manager
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface SBDiaryItemBusManager extends EJBObject {

	/**
	 * retrieves the number of non-expired diary items
	 * @param team team that owns the diary items
	 * @param allowedCountries
	 * @return int - number of non-expired diary items
	 * @throws DiaryItemException
	 * @throws RemoteException
	 */
	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws DiaryItemException,
			RemoteException;

	/**
	 * retrieves the number of diary items due on the current date
	 * @param teamTypeID
	 * @param allowedCountries
	 * @return int - number of diary items due
	 * @throws DiaryItemException
	 * @throws SearchDAOException
	 * @throws RemoteException
	 */
	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws DiaryItemException,
			SearchDAOException, RemoteException;

	/**
	 * retrieves a diary item
	 * @param itemID - diary item ID
	 * @return IDiaryItem - diary item value object
	 * @throws DiaryItemException
	 * @throws RemoteException
	 */
	public IDiaryItem getDiaryItem(long itemID) throws DiaryItemException, RemoteException;

	/**
	 * creates a diary item
	 * @param item - diary item value object
	 * @throws DiaryItemException
	 * @throws RemoteException - if error occurs during remote method call
	 */
	public IDiaryItem createDiaryItem(IDiaryItem item) throws DiaryItemException, RemoteException;

	/**
	 * updates the details of a diary item
	 * @param item - diary item value object
	 * @throws ConcurrentUpdateException
	 * @throws DiaryItemException
	 * @throws RemoteException - if error occurs during remote method call
	 */
	public IDiaryItem updateDiaryItem(IDiaryItem item) throws ConcurrentUpdateException, DiaryItemException,
			RemoteException;

	/**
	 * retrieves a list of non-expired diary items
	 * @param criteria - Diary Item Search Criteria
	 * @return SearchResult
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 * @throws RemoteException
	 */
	public SearchResult getNonExpiredDiaryItems(DiaryItemSearchCriteria criteria) throws DiaryItemException,
			SearchDAOException, RemoteException;

	/**
	 * retrieves a list of diary items due on current date
	 * @param criteria - Diary Item Search Criteria
	 * @return SearchResult - indexed results
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 */
	public SearchResult getDiaryItemsDueFor(DiaryItemSearchCriteria criteria) throws DiaryItemException,
			SearchDAOException, RemoteException;

}
