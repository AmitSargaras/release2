package com.integrosys.cms.app.contractfinancing.proxy;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.ContractFinancingException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancingSummary;
import com.integrosys.cms.app.contractfinancing.trx.ContractFinancingTrxControllerFactory;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;
import com.integrosys.cms.app.contractfinancing.trx.OBContractFinancingTrxValue;
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
public abstract class AbstractContractFinancingProxyManager implements IContractFinancingProxyManager {

	/**
	 * Get the contract finance transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValue(long pk) throws ContractFinancingException {
		DefaultLogger.debug(this, ">>>>>>>>>>> In getContractFinancingTrxValue");
		if (pk == ICMSConstant.LONG_INVALID_VALUE) {
			throw new ContractFinancingException(
					"Invalid primary key. Unable to get IContractFinancingTrxValue object from invalid pk");
		}

		DefaultLogger.debug(this, "Reading from ReadContractFinancingOperation");
		IContractFinancingTrxValue trxValue = new OBContractFinancingTrxValue();
		trxValue.setReferenceID(String.valueOf(pk));
		DefaultLogger.debug(this, "after setReferenceID");
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CONTRACT_FINANCING);
		DefaultLogger.debug(this, "after setTransactionType");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CONTRACT_FINANCING);
		DefaultLogger.debug(this, "after setAction");
		return operate(trxValue, param);
	}

	/**
	 * Get the contract finance transaction object via transaction id
	 * @param trxID - transaction ID
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue getContractFinancingTrxValueByTrxID(String trxID)
			throws ContractFinancingException {
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> getContractFinancingTrxValueByTrxID: Trx ID=" + trxID);
		if ((trxID == null) || trxID.equals("")) {
			throw new ContractFinancingException(
					"Invalid trxID. Unable to get IContractFinancingTrxValue object from invalid transaction id");
		}

		IContractFinancingTrxValue trxValue = new OBContractFinancingTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CONTRACT_FINANCING);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CONTRACT_FINANCING_ID);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new ContractFinancingException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be updated is null!!!");
		}
		if (contractFinancing == null) {
			throw new ContractFinancingException("The IContractFinancing to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, contractFinancing);
		DefaultLogger.debug(this, "in AbstractContractFinancingProxyManager");

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CONTRACT_FINANCING);
		return operate(trxValue, param);
	}

	public IContractFinancingTrxValue makerSaveContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue,
			IContractFinancing contractFinancing) throws ContractFinancingException {
		if (ctx == null) {
			throw new ContractFinancingException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be updated is null!!!");
		}
		if (contractFinancing == null) {
			throw new ContractFinancingException("The IContractFinancing to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, contractFinancing);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CONTRACT_FINANCING);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new ContractFinancingException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be updated is null!!!");
		}
		if (contractFinancing == null) {
			throw new ContractFinancingException("The IContractFinancing to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, contractFinancing);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CONTRACT_FINANCING);
		return operate(trxValue, param);
	}

	/**
	 * Maker delete the contract finance.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - contract finance to be deleted
	 * @return Deleted contract finance
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingTrxValue makerDeleteContractFinancing(ITrxContext ctx, IContractFinancingTrxValue trxValue)
			throws ContractFinancingException {
		if (ctx == null) {
			throw new ContractFinancingException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be approved is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CONTRACT_FINANCING);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new ContractFinancingException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be approved is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CONTRACT_FINANCING);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new ContractFinancingException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be rejected is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CONTRACT_FINANCING);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new ContractFinancingException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be closed is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_CONTRACT_FINANCING);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new ContractFinancingException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be closed is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CONTRACT_FINANCING);
		return operate(trxValue, param);
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
		if (ctx == null) {
			throw new ContractFinancingException("The ITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new ContractFinancingException("The IContractFinancingTrxValue to be closed is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_CONTRACT_FINANCING);
		return operate(trxValue, param);
	}

	// **************** Helper Methods ************
	private IContractFinancingTrxValue operate(ITrxValue trxVal, ITrxParameter param) throws ContractFinancingException {
		if (trxVal == null) {
			throw new ContractFinancingException("ITrxValue is null!");
		}

		try {
			ITrxController controller = new ContractFinancingTrxControllerFactory().getController(trxVal, param);
			if (controller == null) {
				throw new ContractFinancingException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return (IContractFinancingTrxValue) obj;

		}
		catch (TransactionException e) {
			rollback();
			throw new ContractFinancingException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			throw new ContractFinancingException("Exception caught! " + e.toString(), e);
		}
	}

	/**
	 * Formulate the document item trx object
	 * @param anITrxContext - ITrxContext
	 * @param trxValue - IContractFinancingTrxValue
	 * @return IContractFinancingTrxValue - the contract financing trx interface
	 *         formulated
	 */
	private IContractFinancingTrxValue formulateTrxValue(ITrxContext anITrxContext, IContractFinancingTrxValue trxValue) {
		trxValue.setTrxContext(anITrxContext);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CONTRACT_FINANCING);
		return trxValue;
	}

	/**
	 * Formulate the contract finance Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param contractFinancing - IContractFinancing
	 * @return IContractFinancingTrxValue - the contract finance trx interface
	 *         formulated
	 */
	private IContractFinancingTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IContractFinancing contractFinancing) {
		IContractFinancingTrxValue contractFinancingTrxValue = null;
		if (anICMSTrxValue != null) {
			contractFinancingTrxValue = new OBContractFinancingTrxValue(anICMSTrxValue);
		}
		else {
			contractFinancingTrxValue = new OBContractFinancingTrxValue();
		}
		contractFinancingTrxValue.setStagingContractFinancing(contractFinancing);
		contractFinancingTrxValue = formulateTrxValue(anITrxContext, contractFinancingTrxValue);
		return contractFinancingTrxValue;
	}

	// *********************
	// Abstract Methods
	// *********************
	protected abstract void rollback() throws ContractFinancingException;

	public abstract IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID)
			throws ContractFinancingException;

}
