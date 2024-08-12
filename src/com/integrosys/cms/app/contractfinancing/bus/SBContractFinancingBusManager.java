package com.integrosys.cms.app.contractfinancing.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public interface SBContractFinancingBusManager extends EJBObject {

	/**
	 * Create a contract finance object
	 * @param contractFinancingObj - IContractFinance
	 * @return IContractFinance - the contract finance being created
	 * @throws ContractFinancingException on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public IContractFinancing create(IContractFinancing contractFinancingObj) throws ContractFinancingException,
			RemoteException;

	/**
	 * Update a contract finance object
	 * @param contractFinancingObj - IContractFinance
	 * @return ICheckList - the contract finance being updated
	 * @throws ContractFinancingException on errors
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         on concurrent updates
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancing update(IContractFinancing contractFinancingObj) throws ConcurrentUpdateException,
			ContractFinancingException, RemoteException;

	/**
	 * Get the contract finance object by the primary key
	 * @param id - ID (Primary Key) used to retrieve the trxValue
	 * @return IContractFinanceTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancing getContractFinancingByID(Long id) throws ContractFinancingException, RemoteException;

	/**
	 * Get the list of contract finance summary info.
	 * @param aLimitProfileID of long type
	 * @return IContractFinanceSummary[] - the list of contract finance summary
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID)
			throws ContractFinancingException, RemoteException;

}
