package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBBuildUpLocal extends EJBLocalObject {

	/**
	 * Return the BuildUp ID of the BuildUp
	 * @return long - the Terms and Conditions ID
	 */
	public long getBuildUpID();

	/**
	 * Return the common reference of the BuildUp
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the BuildUp information.
	 * @return IBuildUp
	 */
	public IBuildUp getValue() throws BridgingLoanException;

	/**
	 * Persist a BuildUp information
	 * @param value is of type IBuildUp
	 */
	public void setValue(IBuildUp value) throws BridgingLoanException;

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

	/**
	 * Synchronize sales proceeds items.
	 * @param value - IBuildUp object that contains the list of sales proceeds
	 *        items
	 */
	public void synchronizeSalesProceedsItemList(IBuildUp value) throws BridgingLoanException;
}