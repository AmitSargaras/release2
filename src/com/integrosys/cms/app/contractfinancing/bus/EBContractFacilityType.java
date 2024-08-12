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
public interface EBContractFacilityType extends EJBObject {

	/**
	 * Return the contract facility type ID of the contract facility type
	 * @return long - the contract facility type ID
	 */
	public long getFacilityTypeID() throws RemoteException;

	/**
	 * Return the contract facility type reference of the contract facility type
	 * @return long - the contract facility type reference
	 */
	public long getCommonRef() throws RemoteException;

	/**
	 * Return an object representation of the contract facility type
	 * information.
	 * 
	 * @return IContractFacilityType
	 */
	public IContractFacilityType getValue() throws RemoteException;

}
