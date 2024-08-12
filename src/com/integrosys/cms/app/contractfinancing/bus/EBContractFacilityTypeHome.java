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
public interface EBContractFacilityTypeHome extends EJBHome {

	/**
	 * Create a contract facility type information
	 * @param contractFacilityTypeObj is the IContractFacilityType object
	 * @return EBContractFacilityType
	 * @throws javax.ejb.CreateException on error
	 * @throws RemoteException on remote errors
	 */
	public EBContractFacilityType create(IContractFacilityType contractFacilityTypeObj) throws CreateException,
			RemoteException;

	/**
	 * Find by Primary Key which is the contract facility type ID.
	 * @param pk of long type
	 * @return EBContractFacilityType
	 * @throws javax.ejb.FinderException on error
	 * @throws RemoteException on remote errors
	 */
	public EBContractFacilityType findByPrimaryKey(Long pk) throws FinderException, RemoteException;

}
