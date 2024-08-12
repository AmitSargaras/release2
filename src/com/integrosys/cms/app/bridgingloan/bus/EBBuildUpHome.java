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
public interface EBBuildUpHome extends EJBHome {

	/**
	 * Create a BuildUp object
	 * @param obj is the IBuildUp object
	 * @return EBBuildUp
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBBuildUp create(IBuildUp obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the BuildUp ID.
	 * @param pk of long type
	 * @return EBBuildUp
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBBuildUp findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBBuildUp
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBBuildUp findByCommonRef(long commonRef) throws FinderException,
	// RemoteException;

}
