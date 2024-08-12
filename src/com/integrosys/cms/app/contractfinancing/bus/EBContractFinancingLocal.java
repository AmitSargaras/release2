package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public interface EBContractFinancingLocal extends EJBLocalObject {

	/**
	 * Retrieve an instance of a contract financing
	 * @return IContractFinancing - the object encapsulating the contract
	 *         finance info
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancing getValue() throws ContractFinancingException;

	/**
	 * Set the contract financing object
	 * @param contractFinancing - IContractFinancing
	 * @throws ContractFinancingException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IContractFinancing contractFinancing) throws ContractFinancingException,
			ConcurrentUpdateException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param contractFinancing - IContractFinancing
	 * @param verTime is the version time to be compared against the beans'
	 *        version
	 * @throws ContractFinancingException ConcurrentUpdateException on error
	 */
	// public void createDependants(IContractFinancing contractFinancing, long
	// contactID, long verTime) throws ConcurrentUpdateException,
	// ContractFinancingException;
	/**
	 * Get the deleted indicator
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeleted();

	/**
	 * Get the deleted indicator
	 * @param isDeleted - the deleted indicator
	 */
	public void setIsDeleted(boolean isDeleted);

}
