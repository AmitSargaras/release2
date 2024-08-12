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
public interface EBFDRHome extends EJBHome {

	/**
	 * Create a FDR object
	 * @param fdrObj is the IFDR object
	 * @return EBFDR
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBFDR create(IFDR fdrObj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the FDR ID.
	 * @param pk of long type
	 * @return EBFDR
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBFDR findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBFDR
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBFDR findByCommonRef(long commonRef) throws FinderException,
	// RemoteException;

}
