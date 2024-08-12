package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBPropertyTypeLocal extends EJBLocalObject {

	/**
	 * Return the PropertyType ID of the PropertyType
	 * @return long - the Terms and Conditions ID
	 */
	public long getPropertyTypeID();

	/**
	 * Return the common reference of the PropertyType
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the PropertyType information.
	 * @return IPropertyType
	 */
	public IPropertyType getValue();

	/**
	 * Persist a PropertyType information
	 * @param value is of type IPropertyType
	 */
	public void setValue(IPropertyType value);

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
