/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.EJBLocalObject;

/**
 * @author user
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface EBConvenantSubItemLocal extends EJBLocalObject {
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
	 * @return IConvenantSubItem
	 */
	public IConvenantSubItem getValue();

	/**
	 * Persist a checklist item information
	 * 
	 * @param value is of IConvenantSubItem type
	 */
	public void setValue(IConvenantSubItem value);

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

	/**
	 * Set the item as Verified
	 * @param anIsVerifiedInd of boolean type
	 */
	public void setIsVerifiedInd(boolean anIsVerifiedInd);

	/**
	 * Get the item as Verified
	 * @return boolean - the Verified indicator
	 */
	public boolean getIsVerifiedInd();
}
