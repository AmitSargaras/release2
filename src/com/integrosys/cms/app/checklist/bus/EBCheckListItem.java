/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListItem.java,v 1.4 2006/07/18 07:59:40 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * This is the interface to the EBCheckListItem entity bean.
 * 
 * @author $Author: Abhijit Rudrakshawar $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/07/18 07:59:40 $ Tag: $Name: $
 */
public interface EBCheckListItem extends EJBObject {
	/**
	 * Return the checklist item ID of the checklist item
	 * @return long - the checklist item ID
	 */
	public long getCheckListItemID() throws RemoteException;

	/**
	 * Return the checklist item reference of the checklist item
	 * @return long - the checklist item reference
	 */
	public long getCheckListItemRef() throws RemoteException;

	/**
	 * Return an object representation of the checklist item information.
	 * 
	 * @return ICheckListItem
	 */
	public ICheckListItem getValue() throws CheckListException, RemoteException;

	/**
	 * Return an object representation of the checklist item information.
	 * 
	 * @return ICheckListItem
	 */
	public ICheckListItem getValue(boolean getParentSharedDocOnly, long childCheckListID) throws CheckListException,
			RemoteException;

	/**
	 * Persist a checklist item information
	 * 
	 * @param value is of type ICheckListItem
	 */
	// for cr-17
	public void setValue(ICheckListItem value) throws CheckListException, RemoteException;

	/**
	 * Persist the checklist item status
	 * @param anItemStatus of String type
	 */
	public void setItemStatus(String anItemStatus) throws RemoteException;

	/**
	 * Set the item as deleted
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) throws RemoteException;

	/**
	 * Get the item as deleted
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeletedInd() throws RemoteException;

}
