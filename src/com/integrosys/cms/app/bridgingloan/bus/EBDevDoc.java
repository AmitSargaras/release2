package com.integrosys.cms.app.bridgingloan.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBDevDoc extends EJBObject {

	/**
	 * Return the DevelopmentDoc ID of the DevelopmentDoc
	 * @return long - the DevelopmentDoc ID
	 */
	public long getDevDocID() throws RemoteException;

	/**
	 * Return the common reference of the DevelopmentDoc
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the DevelopmentDoc information.
	 * @return IDevelopmentDoc
	 */
	public IDevelopmentDoc getValue() throws RemoteException;

	/**
	 * Sets the DevelopmentDoc object.
	 * @throws BridgingLoanException
	 */
	public void setValue(IDevelopmentDoc value) throws RemoteException;
}
