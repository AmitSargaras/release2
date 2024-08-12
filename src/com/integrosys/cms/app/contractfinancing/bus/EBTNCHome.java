package com.integrosys.cms.app.contractfinancing.bus;

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
public interface EBTNCHome extends EJBHome {

	/**
	 * Create a TNC object
	 * @param obj is the ITNC object
	 * @return EBTNC
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBTNC create(Long fkey, ITNC obj) throws
	// ContractFinancingException, CreateException, RemoteException;
	public EBTNC create(ITNC obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Terms and Conditions ID.
	 * @param pk of long type
	 * @return EBTNC
	 * @throws javax.ejb.FinderException on error
	 * @throws RemoteException on remote errors
	 */
	public EBTNC findByPrimaryKey(Long pk) throws FinderException, RemoteException;

}
