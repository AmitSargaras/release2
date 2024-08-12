package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBDisbursementDetailLocal extends EJBLocalObject {

	/**
	 * Return the Disbursement Detail ID
	 * @return long - the Disbursement Detail ID
	 */
	public long getDisburseDetailID();

	/**
	 * Return the common reference of the Disbursement Detail
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the Disbursement Detail information.
	 * @return IDisbursementDetail
	 */
	public IDisbursementDetail getValue();

	/**
	 * Persist a Disbursement Detail information
	 * @param value is of type IDisbursementDetail
	 */
	public void setValue(IDisbursementDetail value);

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
