package com.integrosys.cms.app.contractfinancing.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public interface EBContractFinancingHome extends EJBHome {

	/**
	 * Create contract finance record.
	 * 
	 * @param contractFinancing of type IContractFinance
	 * @return EBContractFinance - ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBContractFinancing create(IContractFinancing contractFinancing) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the contract finance ID
	 * @param pk - Long
	 * @return EBContractFinance - the remote handler for the contract finance
	 *         that has the PK as specified
	 * @throws javax.ejb.FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBContractFinancing findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Get the list of contract finance summary info.
	 * @param aLimitProfileID of long type
	 * @return IContractFinanceSummary[] - the list of contract finance summary
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID) throws SearchDAOException,
			RemoteException;

}
