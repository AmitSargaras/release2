package com.integrosys.cms.app.bridgingloan.proxy;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoanSummary;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class BridgingLoanProxyManagerImpl implements IBridgingLoanProxyManager {

	/**
	 * Default Constructor
	 */
	public BridgingLoanProxyManagerImpl() {
	}

	// ==========================================
	// Implementation Methods
	// ==========================================
	/**
	 * Get the list of bridging loan summary info.
	 * @param aLimitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanSummary[] getBridgingLoanSummaryList(long aLimitProfileID) throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().getBridgingLoanSummaryList(aLimitProfileID);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in getBridgingLoanSummaryList: " + ex.toString());
		}
	}

	/**
	 * Get the bridging loan transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @return IBridgingLoanTrxValue
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue getBridgingLoanTrxValue(long pk) throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().getBridgingLoanTrxValue(pk);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in getBridgingLoanTrxValue: " + ex.toString());
		}
	}

	/**
	 * Get the bridging loan transaction object via transaction id
	 * @param trxID - transaction ID
	 * @return IBridgingLoanTrxValue
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue getBridgingLoanTrxValueByTrxID(String trxID) throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().getBridgingLoanTrxValueByTrxID(trxID);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in getBridgingLoanTrxValueByTrxID: " + ex.toString());
		}
	}

	/**
	 * Maker creates bridging loan
	 * @param ctx of ITrxContext type
	 * @param trxValue of IBridgingLoanTrxValue type
	 * @param bridgingLoan of IBridgingLoan type
	 * @return IBridgingLoanTrxValue - the interface representing the bridging
	 *         loan trx obj
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerCreateBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue,
			IBridgingLoan bridgingLoan) throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().makerCreateBridgingLoan(ctx, trxValue, bridgingLoan);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in makerCreateBridgingLoan: " + ex.toString());
		}
	}

	public IBridgingLoanTrxValue makerSaveBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue,
			IBridgingLoan bridgingLoan) throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().makerSaveBridgingLoan(ctx, trxValue, bridgingLoan);

		}
		catch (RemoteException ex) {
			ex.printStackTrace();
			throw new BridgingLoanException("Exception in makerSaveBridgingLoan: " + ex.toString());
		}
	}

	/**
	 * Maker updates bridging loan
	 * @param ctx of ITrxContext type
	 * @param trxValue of IBridgingLoanTrxValue type
	 * @param bridgingLoan of IBridgingLoan type
	 * @return IBridgingLoanTrxValue - the interface representing the bridging
	 *         loan trx obj
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	public IBridgingLoanTrxValue makerUpdateBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue,
			IBridgingLoan bridgingLoan) throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().makerUpdateBridgingLoan(ctx, trxValue, bridgingLoan);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in makerUpdateBridgingLoan: " + ex.toString());
		}
	}

	/**
	 * Maker delete the bridging loan.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - bridging loan to be deleted
	 * @return Delete bridging loan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerDeleteBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().makerDeleteBridgingLoan(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in makerDeleteBridgingLoan: " + ex.toString());
		}
	}

	/**
	 * Approve the bridging loan update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be approved
	 * @return Approved Policy Cap
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue checkerApproveBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().checkerApproveBridgingLoan(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in checkerApproveBridgingLoan: " + ex.toString());
		}
	}

	/**
	 * Rejects the bridging loan update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be rejected
	 * @return Rejected Policy Cap
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue checkerRejectBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().checkerRejectBridgingLoan(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in checkerRejectBridgingLoan: " + ex.toString());
		}
	}

	/**
	 * Close the (rejected) bridging loan.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be closed
	 * @return Closed Policy Cap
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerCloseBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().makerCloseBridgingLoan(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in makerCloseBridgingLoan: " + ex.toString());
		}
	}

	/**
	 * Close the (rejected) bridging loan.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - bridging loan to be closed
	 * @return Closed updated bridging loan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerCloseUpdateBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().makerCloseUpdateBridgingLoan(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in makerCloseUpdateBridgingLoan: " + ex.toString());
		}
	}

	/**
	 * Close the (rejected) bridging loan.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - bridging loan to be closed
	 * @return Closed updated bridging loan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerCloseDeleteBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		try {
			return getBridgingLoanProxyManager().makerCloseDeleteBridgingLoan(ctx, trxValue);

		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in makerCloseDeleteBridgingLoan: " + ex.toString());
		}
	}

	// **************** Private Method ************
	/**
	 * Get the SB reference proxy bean
	 * 
	 * @return SBBridgingLoanProxyManager
	 */
	private SBBridgingLoanProxyManager getBridgingLoanProxyManager() {
		SBBridgingLoanProxyManager proxy = (SBBridgingLoanProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_BRIDGING_LOAN_PROXY_JNDI, SBBridgingLoanProxyManagerHome.class.getName());
		return proxy;
	}

	protected void rollback() throws BridgingLoanException {
		// do nothing
	}
}