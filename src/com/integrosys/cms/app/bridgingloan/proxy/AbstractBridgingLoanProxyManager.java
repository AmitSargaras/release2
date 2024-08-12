package com.integrosys.cms.app.bridgingloan.proxy;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoanSummary;
import com.integrosys.cms.app.bridgingloan.trx.BridgingLoanTrxControllerFactory;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.bridgingloan.trx.OBBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public abstract class AbstractBridgingLoanProxyManager implements IBridgingLoanProxyManager {
	/**
	 * Get the bridging loan transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @return IBridgingLoanTrxValue
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue getBridgingLoanTrxValue(long pk) throws BridgingLoanException {
		if (pk == ICMSConstant.LONG_INVALID_VALUE) {
			throw new BridgingLoanException(
					"Invalid primary key. Unable to get IBridgingLoanTrxValue object from invalid pk");
		}

		IBridgingLoanTrxValue trxValue = new OBBridgingLoanTrxValue();
		trxValue.setReferenceID(String.valueOf(pk));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_BRIDGING_LOAN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	/**
	 * Get the bridging loan transaction object via transaction id
	 * @param trxID - transaction ID
	 * @return IBridgingLoanTrxValue
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue getBridgingLoanTrxValueByTrxID(String trxID) throws BridgingLoanException {
		if ((trxID == null) || trxID.equals("")) {
			throw new BridgingLoanException(
					"Invalid trxID. Unable to get IBridgingLoanTrxValue object from invalid transaction id");
		}

		IBridgingLoanTrxValue trxValue = new OBBridgingLoanTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_BRIDGING_LOAN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_BRIDGING_LOAN_ID);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new BridgingLoanException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be updated is null!!!");
		}
		if (bridgingLoan == null) {
			throw new BridgingLoanException("The IBridgingLoan to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, bridgingLoan);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	public IBridgingLoanTrxValue makerSaveBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue,
			IBridgingLoan bridgingLoan) throws BridgingLoanException {
		if (ctx == null) {
			throw new BridgingLoanException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be updated is null!!!");
		}
		if (bridgingLoan == null) {
			throw new BridgingLoanException("The IBridgingLoan to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, bridgingLoan);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	/**
	 * Maker updates bridging loan
	 * @param ctx of ITrxContext type
	 * @param trxValue of IBridgingLoanTrxValue type
	 * @param bridgingLoan of IBridgingLoan type
	 * @return IBridgingLoanTrxValue - the interface representing the bridging
	 *         loan trx obj
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerUpdateBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue,
			IBridgingLoan bridgingLoan) throws BridgingLoanException {
		if (ctx == null) {
			throw new BridgingLoanException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be updated is null!!!");
		}
		if (bridgingLoan == null) {
			throw new BridgingLoanException("The IBridgingLoan to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, bridgingLoan);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	/**
	 * Maker delete the bridging loan.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - bridging loan to be deleted
	 * @return Deleted bridging loan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerDeleteBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		if (ctx == null) {
			throw new BridgingLoanException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be approved is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	/**
	 * Approve the bridging loan update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - bridging loan to be approved
	 * @return Approved bridging loan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue checkerApproveBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		if (ctx == null) {
			throw new BridgingLoanException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be approved is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	/**
	 * Rejects the bridging loan update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - bridging loan to be rejected
	 * @return Rejected bridging loan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue checkerRejectBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		if (ctx == null) {
			throw new BridgingLoanException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be rejected is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	/**
	 * Close the (rejected) bridging loan.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - bridging loan to be closed
	 * @return Closed bridging loan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoanTrxValue makerCloseBridgingLoan(ITrxContext ctx, IBridgingLoanTrxValue trxValue)
			throws BridgingLoanException {
		if (ctx == null) {
			throw new BridgingLoanException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be closed is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_BRIDGING_LOAN);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new BridgingLoanException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be closed is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_BRIDGING_LOAN);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new BridgingLoanException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new BridgingLoanException("The IBridgingLoanTrxValue to be closed is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_BRIDGING_LOAN);
		return operate(trxValue, param);
	}

	// **************** Helper Methods ************
	private IBridgingLoanTrxValue operate(ITrxValue trxVal, ITrxParameter param) throws BridgingLoanException {
		if (trxVal == null) {
			throw new BridgingLoanException("ITrxValue is null!");
		}

		try {
			ITrxController controller = new BridgingLoanTrxControllerFactory().getController(trxVal, param);
			if (controller == null) {
				throw new BridgingLoanException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return (IBridgingLoanTrxValue) obj;

		}
		catch (TransactionException e) {
			rollback();
			e.printStackTrace();
			throw new BridgingLoanException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			e.printStackTrace();
			throw new BridgingLoanException("Exception caught! " + e.toString(), e);
		}
	}

	/**
	 * Formulate the document item trx object
	 * @param anITrxContext - ITrxContext
	 * @param trxValue - IBridgingLoanTrxValue
	 * @return IBridgingLoanTrxValue - the policy cap trx interface formulated
	 */
	private IBridgingLoanTrxValue formulateTrxValue(ITrxContext anITrxContext, IBridgingLoanTrxValue trxValue) {
		trxValue.setTrxContext(anITrxContext);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_BRIDGING_LOAN);
		return trxValue;
	}

	/**
	 * Formulate the bridging loan Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param bridgingLoan - IBridgingLoan
	 * @return IBridgingLoanTrxValue - the bridging loan trx interface
	 *         formulated
	 */
	private IBridgingLoanTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IBridgingLoan bridgingLoan) {
		IBridgingLoanTrxValue bridgingLoanTrxValue = null;
		if (anICMSTrxValue != null) {
			bridgingLoanTrxValue = new OBBridgingLoanTrxValue(anICMSTrxValue);
		}
		else {
			bridgingLoanTrxValue = new OBBridgingLoanTrxValue();
		}
		bridgingLoanTrxValue.setStagingBridgingLoan(bridgingLoan);
		bridgingLoanTrxValue = formulateTrxValue(anITrxContext, bridgingLoanTrxValue);
		return bridgingLoanTrxValue;
	}

	// *********************
	// Abstract Methods
	// *********************
	protected abstract void rollback() throws BridgingLoanException;

	/**
	 * Get the list of bridging loan summary info.
	 * @param aLimitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws BridgingLoanException on errors
	 */
	public abstract IBridgingLoanSummary[] getBridgingLoanSummaryList(long aLimitProfileID)
			throws BridgingLoanException;
}