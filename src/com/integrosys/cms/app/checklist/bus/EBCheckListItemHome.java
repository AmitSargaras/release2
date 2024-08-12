/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListItemHome.java,v 1.2 2006/03/27 03:13:43 jitendra Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface for the EBCheckListItem Entity Bean.
 * 
 * @author $Author: jitendra $
 * @version $Revision: 1.2 $
 * @since $Date: 2006/03/27 03:13:43 $ Tag: $Name: $
 */
public interface EBCheckListItemHome extends EJBHome {
	/**
	 * Create a checklist item information
	 * 
	 * @param legalID the credit application ID of type long
	 * @param value is the IContact object
	 * @return EBCheckListItem
	 * @throws CreateException on error
	 */
	public EBCheckListItem create(Long aCheckListID, ICheckListItem anICheckListItem) throws CheckListException,
			CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the checklist item ID.
	 * @param aCheckListItemID of long type
	 * @return EBCheckListItem
	 * @throws FinderException on error
	 */
	public EBCheckListItem findByPrimaryKey(Long aCheckListItemID) throws FinderException, RemoteException;

	/**
	 * Find by unique Key which is the checklist item ID.
	 * @param aCheckListItemRef of long type
	 * @return EBCheckListItem
	 * @throws FinderException on error
	 */
	public EBCheckListItem findByCheckListItemRef(long aCheckListItemRef) throws FinderException, RemoteException;
}