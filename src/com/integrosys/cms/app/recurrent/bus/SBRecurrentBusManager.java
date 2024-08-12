package com.integrosys.cms.app.recurrent.bus;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * Session bean remote interface for the services provided by the checklist bus
 * manager
 *
 * @author $Author: hshii $<br>
 * @version $Revision: 1.61 $
 * @since $Date: 2006/10/09 05:41:15 $ Tag: $Name: $
 */
public interface SBRecurrentBusManager extends EJBObject {


	/**
	 * Get a recurrent checklist by ID
	 * @param aCheckListID - long
	 * @return IRecurrentCheckList - the recurrent checklist
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 * @throws java.rmi.RemoteException
	 */
	public IRecurrentCheckList getRecurrentCheckListByID(long aCheckListID) throws RecurrentException, RemoteException;

	/**
	 * Get the recurrent checklist using the limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return IRecurrentCheckList - the recurrent checklist of the limit
	 *         profile
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public IRecurrentCheckList getRecurrentCheckList(long aLimitProfileID, long aSubProfileID)
			throws RecurrentException, RemoteException;

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public RecurrentSearchResult[] getRecurrentCheckList(long aLimitProfileID, long aSubProfileID, String[] aStatusList)
			throws SearchDAOException, RecurrentException, RemoteException;
	
	
	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @param annexureId of String type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public RecurrentSearchResult[] getRecurrentCheckListByAnnexureId(long aLimitProfileID, long aSubProfileID, String[] aStatusList, String annexureId)
			throws SearchDAOException, RecurrentException, RemoteException;
	
	/**
	 * Create a recurrent checklist
	 * @param anIRecurrentCheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckList - the recurrent checkList being created
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 * @throws java.rmi.RemoteException
	 */
	public IRecurrentCheckList create(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException,
			RemoteException;

	/**
	 * Update a recurrent checklist
	 * @param anIRecurrentCheckList of IRecurrentCheckList tyoe
	 * @return IRecurrentCheckList - the recurrent checkList being updated
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 * @throws java.rmi.RemoteException
	 */
	public IRecurrentCheckList update(IRecurrentCheckList anIRecurrentCheckList) throws ConcurrentUpdateException,
			RecurrentException, RemoteException;

	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO errors
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public IRecurrentCheckListItem[] getRecurrentItemHistory(long anItemReference) throws SearchDAOException,
			RecurrentException, RemoteException;

	public IRecurrentCheckListItem getRecurrentCheckListItemByRef(long anItemRef) throws RecurrentException,
			RemoteException;

	public List createRecurrentCheckListSubItem(long anItemID, List aCreateList) throws RecurrentException,
			RemoteException;

	// cr26
	public IConvenant[] getConvenantHistory(long anItemReference) throws SearchDAOException, RecurrentException,
			RemoteException;

	public IConvenant getConvenantByRef(long anItemRef) throws RecurrentException, RemoteException;

	public List createConvenantSubItem(long anItemID, List aCreateList) throws RecurrentException, RemoteException;
	
	public long getRecurrentDocId(long limitProfileId, long subProfileId)throws SearchDAOException, RecurrentException, RemoteException;
	
	public String getBankingType(long limitProfileId, long subProfileId)throws RecurrentException,RemoteException;
	
	public void insertAnnexures(ILimitProfile limitProfile)throws RecurrentException,RemoteException;

}
