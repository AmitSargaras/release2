package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBDisbursementLocal extends EJBLocalObject {

	/**
	 * Return the Disbursement ID of the Disbursement
	 * @return long - the Terms and Conditions ID
	 */
	public long getDisbursementID();

	/**
	 * Return the common reference of the Disbursement
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the Disbursement information.
	 * @return IDisbursement
	 */
	public IDisbursement getValue() throws BridgingLoanException;

	/**
	 * Persist a Disbursement information
	 * @param value is of type IDisbursement
	 */
	public void setValue(IDisbursement value) throws BridgingLoanException;

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
	 * Synchronize disbursement detail items.
	 * @param value - IDisbursement object that contains the list of
	 *        disbursement detail items
	 */
	public void synchronizeDisbursementDetailItemList(IDisbursement value) throws BridgingLoanException;
}