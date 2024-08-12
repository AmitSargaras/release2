package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBSettlementLocal extends EJBLocalObject {

	/**
	 * Return the Settlement ID of the Settlement
	 * @return long - the Terms and Conditions ID
	 */
	public long getSettlementID();

	/**
	 * Return the common reference of the Settlement
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the Settlement information.
	 * @return ISettlement
	 */
	public ISettlement getValue();

	/**
	 * Persist a Settlement information
	 * @param value is of type ISettlement
	 */
	public void setValue(ISettlement value);

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