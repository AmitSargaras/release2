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
public interface EBAdvanceHome extends EJBHome {

	/**
	 * Create a Advance object
	 * @param obj is the IAdvance object
	 * @return EBAdvance
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBAdvance create(IAdvance obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Advance ID.
	 * @param pk of long type
	 * @return EBAdvance
	 * @throws javax.ejb.FinderException on error
	 * @throws RemoteException on remote errors
	 */
	public EBAdvance findByPrimaryKey(Long pk) throws FinderException, RemoteException;

}
