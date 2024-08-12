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
public interface EBDisbursementDetailHome extends EJBHome {

	/**
	 * Create a Disbursement Detail object
	 * @param obj is the IDisbursementDetail object
	 * @return EBDisbursementDetail
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBDisbursementDetail create(IDisbursementDetail obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Disbursement Detail ID.
	 * @param pk of long type
	 * @return EBDisbursementDetail
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBDisbursementDetail findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBDisbursementDetail
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBDisbursementDetail findByCommonRef(long commonRef) throws
	// FinderException, RemoteException;

}
