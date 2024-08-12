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
public interface EBSalesProceeds extends EJBObject {

	/**
	 * Return the SalesProceeds ID of the SalesProceeds
	 * @return long - the SalesProceeds ID
	 */
	public long getProceedsID() throws RemoteException;

	/**
	 * Return the common reference of the SalesProceeds
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the SalesProceeds information.
	 * @return ISalesProceeds
	 */
	public ISalesProceeds getValue() throws RemoteException;

	/**
	 * Sets the ISalesProceeds object.
	 * @param value of type ISalesProceeds
	 */
	public void setValue(ISalesProceeds value) throws RemoteException;
}
