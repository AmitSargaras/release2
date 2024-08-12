package com.integrosys.cms.app.contractfinancing.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBPayment extends EJBObject {

	/**
	 * Return the Payment ID of the Payment Object
	 * @return long - the Payment ID
	 */
	public long getPaymentID() throws RemoteException;

	/**
	 * Return the common reference of the Payment Object
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the Payment information.
	 * @return IPayment
	 */
	public IPayment getValue() throws RemoteException;

}
