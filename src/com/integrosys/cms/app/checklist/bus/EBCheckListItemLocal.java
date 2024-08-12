/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListItemLocal.java,v 1.9 2006/07/18 07:59:40 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBCheckListItem entity bean.
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.9 $
 * @since $Date: 2006/07/18 07:59:40 $ Tag: $Name: $
 */
public interface EBCheckListItemLocal extends EJBLocalObject {
	/**
	 * Return the checklist ID
	 * @return Long - the checklist ID
	 */
	public Long getCMPCheckListID();

	/**
	 * Return the checklist item ID of the checklist item
	 * @return long - the checklist item ID
	 */
	public long getCheckListItemID();

	/**
	 * Return the checklist item reference of the checklist item
	 * @return long - the checklist item reference
	 */
	public long getCheckListItemRef();

	/**
	 * Return an object representation of the checklist item information.
	 * 
	 * @return ICheckListItem
	 */
	public ICheckListItem getValue() throws CheckListException;

	/**
	 * Return an object representation of the checklist item information.
	 * 
	 * @return ICheckListItem
	 */
	public ICheckListItem getValue(boolean getParentSharedDocOnly, long childCheckListID) throws CheckListException;

	/**
	 * Persist a checklist item information
	 * 
	 * @param value is of type ICheckListItem
	 */
	public void setValue(ICheckListItem value) throws CheckListException;

	/**
	 * Persist the checklist item status
	 * @param anItemStatus of String type
	 */
	public void setItemStatus(String anItemStatus);

	/**
	 * Set the item as deleted
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Get the item as deleted
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeletedInd();

	public void createDocumentshareList(ICheckListItem anICheckListItem) throws CheckListException;

}
