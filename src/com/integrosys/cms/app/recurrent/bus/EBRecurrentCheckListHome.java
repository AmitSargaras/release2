/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListHome.java,v 1.7 2005/01/24 02:11:06 ckchua Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import com.integrosys.base.businfra.search.SearchDAOException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Home interface for the recurrent checklist entity bean
 * 
 * @author $Author: ckchua $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/01/24 02:11:06 $ Tag: $Name: $
 */

public interface EBRecurrentCheckListHome extends EJBHome {
	/**
	 * Create a recurrent checklist
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @return EBRecurrentCheckList - the remote handler for the created
	 *         recurrent checklist
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBRecurrentCheckList create(IRecurrentCheckList anIRecurrentCheckList) throws CreateException,
			RemoteException;

	/**
	 * Find by primary Key, the recurrent checklist ID
	 * @param aPK - Long
	 * @return EBRecurrentCheckList - the remote handler for the recurrent
	 *         checklist that has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBRecurrentCheckList findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by limit profile ID which is the unique key
	 * @param aLimitProfileID of Long type
	 * @param aSubProfileID of Long type
	 * @return EBRecurrentCheckList - the remote handler for the recurrent
	 *         checklist that has the limit profile ID as specified
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 * @throws RemoteException on remote errors
	 */
	public EBRecurrentCheckList findByLimitProfileAndSubProfile(Long aLimitProfileID, Long aSubProfileID)
			throws FinderException, RemoteException;

	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throw SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public IRecurrentCheckListItem[] getRecurrentItemHistory(long anItemReference) throws SearchDAOException,
			RemoteException;

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public RecurrentSearchResult[] getCheckList(long aLimitProfileID, long aSubProfileID, String[] aStatusList)
			throws SearchDAOException, RemoteException;
	
	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @param annexureId of String type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	
	                               
	public RecurrentSearchResult[] getCheckListByAnnexureId(long aLimitProfileID, long aSubProfileID, String[] aStatusList, String annexureId)
			throws SearchDAOException, RemoteException;

	public IRecurrentCheckListItem getRecurrentCheckListItem(long anItemReference) throws SearchDAOException,
			RemoteException;

	// cr 26
	public IConvenant[] getConvenantHistory(long anItemReference) throws SearchDAOException, RemoteException;

	public IConvenant getConvenant(long anItemReference) throws SearchDAOException, RemoteException;
}