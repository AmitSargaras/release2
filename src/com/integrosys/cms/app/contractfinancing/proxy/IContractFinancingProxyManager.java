package com.integrosys.cms.app.contractfinancing.proxy;

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
public interface IContractFinancingProxyManager {

	/**
	 * Maker creates contract finance
	 * @param ctx of ITrxContext type
	 * @param trxValue of IContractFinancingTrxValue type
	 * @param contractFinancing of IContractFinancing type
	 * @return IContractFinancingTrxValue - the interface representing the
	 *         contract finance trx obj
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerCreateContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue, IContractFinancing contractFinancing)
			throws ContractFinancingException;

	/**
	 * Maker creates contract finance
	 * @param ctx of ITrxContext type
	 * @param trxValue of IContractFinancingTrxValue type
	 * @param contractFinancing of IContractFinancing type
	 * @return IContractFinancingTrxValue - the interface representing the
	 *         contract finance trx obj
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerSaveContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue,
			IContractFinancing contractFinancing) throws ContractFinancingException;

	/**
	 * Maker updates contract finance
	 * @param ctx of ITrxContext type
	 * @param trxValue of IContractFinancingTrxValue type
	 * @param contractFinancing of IContractFinancing type
	 * @return IContractFinancingTrxValue - the interface representing the
	 *         contract finance trx obj
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerUpdateContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue, IContractFinancing contractFinancing)
			throws ContractFinancingException;

	/**
	 * Maker delete the contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be deleted
	 * @return Delete contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerDeleteContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue)
			throws ContractFinancingException;

	/**
	 * Approve the contract finance update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be approved
	 * @return Approved contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue checkerApproveContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException;

	/**
	 * Rejects the contract finance update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be rejected
	 * @return Rejected contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue checkerRejectContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException;

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerCloseContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue)
			throws ContractFinancingException;

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed updated contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerCloseUpdateContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException;

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed updated contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerCloseDeleteContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException;

	/**
	 * Get the contract finance transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValue(long pk) throws ContractFinancingException;

	/**
	 * Get the contract finance transaction object via transaction id
	 * @param trxID - transaction ID
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValueByTrxID(String trxID)
			throws ContractFinancingException;

	/**
	 * Get the list of contract finance summary info.
	 * @param aLimitProfileID of long type
	 * @return IContractFinancingSummary[] - the list of contract finance
	 *         summary
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID)
			throws ContractFinancingException;
}
