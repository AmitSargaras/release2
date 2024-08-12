/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListSubItemLocal.java,v 1.2 2004/10/04 03:44:53 btan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBRecurrentCheckListSubItem entity bean.
 * 
 * @author $Author: btan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/10/04 03:44:53 $ Tag: $Name: $
 */
public interface EBRecurrentCheckListSubItemLocal extends EJBLocalObject {
	/**
	 * Return the checklist item ID of the checklist item
	 * @return long - the checklist item ID
	 */
	public long getSubItemID();

	/**
	 * Return the checklist item reference of the checklist item
	 * @return long - the checklist item reference
	 */
	public long getSubItemRef();

	/**
	 * Return an object representation of the recurrent checklist sub item
	 * information.
	 * @return IRecurrentCheckListSubItem
	 */
	public IRecurrentCheckListSubItem getValue();

	/**
	 * Persist a checklist item information
	 * 
	 * @param value is of IRecurrentCheckListSubItem type
	 */
	public void setValue(IRecurrentCheckListSubItem value);

	/**
	 * Set the checklist item's deferred count.
	 * @param deferredCount deferred count
	 */
	public void setDeferredCount(long deferredCount);

	/**
	 * Return the checklist item's deferred count.
	 * @return long - the checklist item deferred count
	 */
	public long getDeferredCount();

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

}
