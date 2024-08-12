package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBSalesProceedsLocal extends EJBLocalObject {

	/**
	 * Return the SalesProceeds ID of the SalesProceeds
	 * @return long - the Terms and Conditions ID
	 */
	public long getProceedsID();

	/**
	 * Return the common reference of the SalesProceeds
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the SalesProceeds information.
	 * @return ISalesProceeds
	 */
	public ISalesProceeds getValue();

	/**
	 * Persist a SalesProceeds information
	 * @param value is of type ISalesProceeds
	 */
	public void setValue(ISalesProceeds value);

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
