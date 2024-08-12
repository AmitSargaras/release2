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
public interface EBSettlement extends EJBObject {

	/**
	 * Return the Settlement ID of the Settlement
	 * @return long - the Settlement ID
	 */
	public long getSettlementID() throws RemoteException;

	/**
	 * Return the common reference of the Settlement
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the Settlement information.
	 * @return ISettlement
	 */
	public ISettlement getValue() throws RemoteException;

	/**
	 * Sets the ISettlement object.
	 * @param value of type ISettlement
	 */
	public void setValue(ISettlement value) throws RemoteException;
}