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
public interface EBPaymentHome extends EJBHome {

	/**
	 * Create a Payment object
	 * @param obj is the IPayment object
	 * @return EBPayment
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBPayment create(IPayment obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Payment ID.
	 * @param pk of long type
	 * @return EBPayment
	 * @throws javax.ejb.FinderException on error
	 * @throws RemoteException on remote errors
	 */
	public EBPayment findByPrimaryKey(Long pk) throws FinderException, RemoteException;

}
