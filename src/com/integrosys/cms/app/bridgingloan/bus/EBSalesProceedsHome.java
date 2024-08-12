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
public interface EBSalesProceedsHome extends EJBHome {

	/**
	 * Create a SalesProceeds object
	 * @param obj is the ISalesProceeds object
	 * @return EBSalesProceeds
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBSalesProceeds create(ISalesProceeds obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Sales Proceeds ID.
	 * @param pk of long type
	 * @return EBSalesProceeds
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBSalesProceeds findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBSalesProceeds
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBSalesProceeds findByCommonRef(long commonRef) throws
	// FinderException, RemoteException;

}
