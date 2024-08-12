package com.integrosys.cms.app.contractfinancing.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBContractFinancing extends EJBObject {

	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Retrieve an instance of a contract financing
	 * @return IContractFinancing - the object encapsulating the contract
	 *         finance info
	 * @throws ContractFinancingException on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public IContractFinancing getValue() throws ContractFinancingException, RemoteException;

	/**
	 * Set the contract financing object
	 * @param contractFinancing - IContractFinancing
	 * @throws ContractFinancingException on errors
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         if the version number of the object to be updated does not match
	 *         with that in entity
	 * @throws RemoteException on remote errors
	 */
	public void setValue(IContractFinancing contractFinancing) throws ContractFinancingException,
			ConcurrentUpdateException, RemoteException;

	// /**
	// * Method to create child dependants via CMR
	// *
	// * @param contractFinancing - IContractFinancing
	// * @param verTime is the long value of the version time to be compared
	// against.
	// * @throws ConcurrentUpdateException RemoteException on error
	// *
	// */
	// public void createDependants(IContractFinancing contractFinancing, long
	// contactID, long verTime) throws ContractFinancingException,
	// ConcurrentUpdateException, RemoteException;
}
