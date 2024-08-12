package com.integrosys.cms.app.bridgingloan.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBProjectScheduleHome extends EJBHome {

	/**
	 * Create a ProjectSchedule object
	 * @param obj is the IProjectSchedule object
	 * @return EBProjectSchedule
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBProjectSchedule create(IProjectSchedule obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Project Schedule ID.
	 * @param pk of long type
	 * @return EBProjectSchedule
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBProjectSchedule findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBProjectSchedule
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBProjectSchedule findByCommonRef(long commonRef) throws
	// FinderException, RemoteException;

}
