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
public interface EBDisbursementHome extends EJBHome {

	/**
	 * Create a Disbursement object
	 * @param obj is the IDisbursement object
	 * @return EBDisbursement
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBDisbursement create(IDisbursement obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Disbursement ID.
	 * @param pk of long type
	 * @return EBDisbursement
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBDisbursement findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBDisbursement
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBDisbursement findByCommonRef(long commonRef) throws
	// FinderException, RemoteException;

}
