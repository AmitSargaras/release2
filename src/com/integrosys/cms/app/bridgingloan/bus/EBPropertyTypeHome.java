package com.integrosys.cms.app.bridgingloan.bus;

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
public interface EBPropertyTypeHome extends EJBHome {

	/**
	 * Create a PropertyType object
	 * @param obj is the IPropertyType object
	 * @return EBPropertyType
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBPropertyType create(IPropertyType obj) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the Property Type ID.
	 * @param pk of long type
	 * @return EBPropertyType
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public EBPropertyType findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBPropertyType
	 * @throws javax.ejb.FinderException on error
	 * @throws java.rmi.RemoteException on remote errors
	 */
	// public EBPropertyType findByCommonRef(long commonRef) throws
	// FinderException, RemoteException;

}
