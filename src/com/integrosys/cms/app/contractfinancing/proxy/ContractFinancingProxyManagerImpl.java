package com.integrosys.cms.app.contractfinancing.proxy;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
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
public class ContractFinancingProxyManagerImpl implements IContractFinancingProxyManager {

	// ==========================================
	// Implementation Methods
	// ==========================================
	/**
	 * Get the list of contract finance summary info.
	 * @param aLimitProfileID of long type
	 * @return IContractFinancingSummary[] - the list of contract finance
	 *         summary
	 * @throws com.integrosys.cms.app.contractfinancing.bus.ContractFinancingException
	 *         on errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID)
			throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().getContractFinancingSummaryList(aLimitProfileID);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in getContractFinancingSummaryList: " + ex.toString());
		}
	}

	/**
	 * Get the contract finance transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValue(long pk) throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().getContractFinancingTrxValue(pk);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in getContractFinancingTrxValue: " + ex.toString());
		}
	}

	/**
	 * Get the contract finance transaction object via transaction id
	 * @param trxID - transaction ID
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValueByTrxID(String trxID)
			throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().getContractFinancingTrxValueByTrxID(trxID);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in getContractFinancingTrxValueByTrxID: " + ex.toString());
		}
	}

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
			throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().makerCreateContractFinancing(ctx, trxValue, contractFinancing);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in makerCreateContractFinancing: " + ex.toString());
		}
	}

	public IContractFinancingTrxValue makerSaveContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue,
			IContractFinancing contractFinancing) throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().makerSaveContractFinancing(ctx, trxValue, contractFinancing);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in makerSaveContractFinancing: " + ex.toString());
		}
	}

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
			throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().makerUpdateContractFinancing(ctx, trxValue, contractFinancing);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in makerUpdateContractFinancing: " + ex.toString());
		}
	}

	/**
	 * Maker delete the contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be deleted
	 * @return Delete contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerDeleteContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue)
			throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().makerDeleteContractFinancing(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in makerDeleteContractFinancing: " + ex.toString());
		}
	}

	/**
	 * Approve the contract finance update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be approved
	 * @return Approved contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue checkerApproveContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().checkerApproveContractFinancing(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in checkerApproveContractFinancing: " + ex.toString());
		}
	}

	/**
	 * Rejects the contract finance update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be rejected
	 * @return Rejected contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue checkerRejectContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().checkerRejectContractFinancing(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in checkerRejectContractFinancing: " + ex.toString());
		}
	}

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerCloseContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue)
			throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().makerCloseContractFinancing(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in makerCloseContractFinancing: " + ex.toString());
		}
	}

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed updated contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerCloseUpdateContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().makerCloseUpdateContractFinancing(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in makerCloseUpdateContractFinancing: " + ex.toString());
		}
	}

	/**
	 * Close the (rejected) contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be closed
	 * @return Closed updated contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerCloseDeleteContractFinancing(ITrxContext ctx,
			IContractFinancingTrxValue trxValue) throws ContractFinancingException {
		try {
			return getContractFinancingProxyManager().makerCloseDeleteContractFinancing(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in makerCloseDeleteContractFinancing: " + ex.toString());
		}
	}

	// **************** Private Method ************
	/**
	 * Get the SB reference proxy bean
	 * 
	 * @return SBContractFinancingProxyManager
	 */
	private SBContractFinancingProxyManager getContractFinancingProxyManager() {
		SBContractFinancingProxyManager proxy = (SBContractFinancingProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CONTRACT_FINANCING_PROXY_JNDI, SBContractFinancingProxyManagerHome.class.getName());
		return proxy;
	}

	protected void rollback() throws ContractFinancingException {
		// do nothing
	}

}
