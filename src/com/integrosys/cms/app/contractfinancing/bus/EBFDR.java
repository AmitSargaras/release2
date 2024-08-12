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
public interface EBFDR extends EJBObject {

	/**
	 * Return the FDR ID of the FDR
	 * @return long - the FDR ID
	 */
	public long getFdrID() throws RemoteException;

	/**
	 * Return the common reference of the FDR
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the FDR information.
	 * @return IFDR
	 */
	public IFDR getValue() throws RemoteException;

}
