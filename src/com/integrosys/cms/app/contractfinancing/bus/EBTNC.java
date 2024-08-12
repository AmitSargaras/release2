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
public interface EBTNC extends EJBObject {

	/**
	 * Return the ID of the terms and conditions
	 * @return long - the terms and conditions ID
	 */
	public long getTncID() throws RemoteException;

	/**
	 * Return the common reference of the terms and conditions
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the terms and conditions information.
	 * @return ITNC
	 */
	public ITNC getValue() throws RemoteException;

}
