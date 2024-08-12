package com.integrosys.cms.app.bridgingloan.bus;

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
public interface SBBridgingLoanBusManager extends EJBObject {

	/**
	 * Create a bridging loan object
	 * @param bridgingLoanObj - IBridgingLoan
	 * @return IBridgingLoan - the bridging loan being created
	 * @throws BridgingLoanException on errors
	 * @throws RemoteException on remote errors
	 */
	public IBridgingLoan create(IBridgingLoan bridgingLoanObj) throws BridgingLoanException, RemoteException;

	/**
	 * Update a bridging loan object
	 * @param bridgingLoanObj - IBridgingLoan
	 * @return ICheckList - the bridging loan being updated
	 * @throws BridgingLoanException on errors
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         on concurrent updates
	 * @throws RemoteException on remote errors
	 */
	public IBridgingLoan update(IBridgingLoan bridgingLoanObj) throws ConcurrentUpdateException, BridgingLoanException,
			RemoteException;

	/**
	 * Get the bridging loan object by the primary key
	 * @param id - ID (Primary Key) used to retrieve the trxValue
	 * @return IBridgingLoan
	 * @throws BridgingLoanException on errors
	 * @throws RemoteException on remote errors
	 */
	public IBridgingLoan getBridgingLoanByID(Long id) throws BridgingLoanException, RemoteException;

	/**
	 * Get the list of bridging loan summary info.
	 * @param aLimitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws BridgingLoanException on errors
	 * @throws RemoteException on remote errors
	 */
	public IBridgingLoanSummary[] getBridgingLoanSummaryList(long aLimitProfileID) throws BridgingLoanException,
			RemoteException;

}
