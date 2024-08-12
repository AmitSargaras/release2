package com.integrosys.cms.app.contractfinancing.proxy;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.contractfinancing.bus.ContractFinancingException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancingSummary;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public interface SBContractFinancingProxyManager extends EJBObject {

	/**
	 * Maker creates contract finance
	 * @param ctx of ITrxContext type
	 * @param trxValue of IContractFinancingTrxValue type
	 * @param contractFinancing of IContractFinancing type
	 * @return IContractFinancingTrxValue - the interface representing the
	 *         contract finance trx obj
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue makerCreateContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue, IContractFinancing contractFinancing)
			throws ContractFinancingException, RemoteException;

	/**
	 * Maker creates contract finance
	 * @param ctx of ITrxContext type
	 * @param trxValue of IContractFinancingTrxValue type
	 * @param contractFinancing of IContractFinancing type
	 * @return IContractFinancingTrxValue - the interface representing the
	 *         contract finance trx obj
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue makerSaveContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue,
			IContractFinancing contractFinancing) throws ContractFinancingException, RemoteException;

	/**
	 * Maker updates contract finance
	 * @param ctx of ITrxContext type
	 * @param trxValue of IContractFinancingTrxValue type
	 * @param contractFinancing of IContractFinancing type
	 * @return IContractFinancingTrxValue - the interface representing the
	 *         contract finance trx obj
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue makerUpdateContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue, IContractFinancing contractFinancing)
			throws ContractFinancingException, RemoteException;

	/**
	 * Maker delete the contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be deleted
	 * @return Deleted contract finance
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue makerDeleteContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue)
			throws ContractFinancingException, RemoteException;

	/**
	 * Approve the contract finance update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be approved
	 * @return Approved contract finance
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue checkerApproveContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException, RemoteException;

	/**
	 * Rejects the contract finance update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be rejected
	 * @return Rejected contract finance
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue checkerRejectContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException, RemoteException;

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed contract finance
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue makerCloseContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue)
			throws ContractFinancingException, RemoteException;

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed updated contract finance
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue makerCloseUpdateContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException, RemoteException;

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed updated contract finance
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue makerCloseDeleteContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException, RemoteException;

	/**
	 * Get the contract finance transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValue(long pk) throws ContractFinancingException,
			RemoteException;

	/**
	 * Get the contract finance transaction object via transaction id
	 * @param trxID - transaction ID
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValueByTrxID(String trxID)
			throws ContractFinancingException, RemoteException;

	/**
	 * Get the list of contract finance summary info.
	 * @param aLimitProfileID of long type
	 * @return IContractFinancingSummary[] - the list of contract finance
	 *         summary
	 * @throws ContractFinancingException on errors
	 * @throws RemoteException on remote errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID)
			throws ContractFinancingException, RemoteException;

}
