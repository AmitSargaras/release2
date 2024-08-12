package com.integrosys.cms.app.bridgingloan.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBProjectSchedule extends EJBObject {

	/**
	 * Return the ProjectSchedule ID of the ProjectSchedule
	 * @return long - the ProjectSchedule ID
	 */
	public long getScheduleID() throws RemoteException;

	/**
	 * Return the common reference of the ProjectSchedule
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the ProjectSchedule information.
	 * @return IProjectSchedule
	 */
	public IProjectSchedule getValue() throws BridgingLoanException, RemoteException;

	/**
	 * Sets the IProjectSchedule object.
	 * @throws BridgingLoanException
	 */
	public void setValue(IProjectSchedule value) throws BridgingLoanException, RemoteException;
}