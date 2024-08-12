package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBProjectScheduleLocal extends EJBLocalObject {

	/**
	 * Return the ProjectSchedule ID of the ProjectSchedule
	 * @return long - the Terms and Conditions ID
	 */
	public long getScheduleID();

	/**
	 * Return the common reference of the ProjectSchedule
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the ProjectSchedule information.
	 * @return IProjectSchedule
	 */
	public IProjectSchedule getValue() throws BridgingLoanException;

	/**
	 * Persist a ProjectSchedule information
	 * @param value is of type IProjectSchedule
	 */
	public void setValue(IProjectSchedule value) throws BridgingLoanException;

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
	 * Synchronize development document items.
	 * @param value - IProjectSchedule object that contains the list of
	 *        development document items
	 */
	public void synchronizeDevelopmentDocItemList(IProjectSchedule value) throws BridgingLoanException;

}