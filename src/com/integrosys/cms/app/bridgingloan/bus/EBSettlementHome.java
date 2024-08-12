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
public interface EBSettlementHome extends EJBHome {

	/**
	 * Create a Settlement object
	 * @param obj is the ISettlement object
	 * @return EBSettlement
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBSettlement create(ISettlement obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Settlement ID.
	 * @param pk of long type
	 * @return EBSettlement
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBSettlement findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBSettlement
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBSettlement findByCommonRef(long commonRef) throws
	// FinderException, RemoteException;
}