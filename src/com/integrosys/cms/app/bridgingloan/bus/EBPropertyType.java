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
public interface EBPropertyType extends EJBObject {

	/**
	 * Return the PropertyType ID of the PropertyType
	 * @return long - the PropertyType ID
	 */
	public long getPropertyTypeID() throws RemoteException;

	/**
	 * Return the common reference of the PropertyType
	 * @return long - the common reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the PropertyType information.
	 * @return IPropertyType
	 */
	public IPropertyType getValue() throws RemoteException;

	/**
	 * Sets the IPropertyType object.
	 */
	public void setValue(IPropertyType value) throws RemoteException;
}