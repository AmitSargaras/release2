package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBDevDocLocal extends EJBLocalObject {

	/**
	 * Return the DevDoc ID of the DevDoc
	 * @return long - the DevDoc ID
	 */
	public long getDevDocID();

	/**
	 * Return the common reference of the DevDoc
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the DevDoc information.
	 * @return IDevDoc
	 */
	public IDevelopmentDoc getValue();

	/**
	 * Persist a DevDoc information
	 * @param value is of type IDevDoc
	 */
	public void setValue(IDevelopmentDoc value);

	/**
	 * Get the deleted indicator
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeletedInd();

	/**
	 * Get the deleted indicator
	 * @param isDeleted - the deleted indicator
	 */
	public void setIsDeletedInd(boolean isDeleted);

}
