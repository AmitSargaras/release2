/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * This transaction controller is to be used for reading cash margin.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class CashMarginReadController extends AbstractTrxController implements ITrxOperationFactory {
	/**
	 * Default constructor.
	 */
	public CashMarginReadController() {
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table.
	 * 
	 * @return instance of cash margin
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CASH_MARGIN;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 * 
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction result
	 * @throws TrxParameterException if the transaction value and param is
	 *         invalid
	 * @throws TransactionException on error operating the transaction
	 * @throws TrxControllerException on any other errors encountered
	 */
	public ITrxResult operate(ITrxValue trxVal, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (trxVal == null) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (param == null) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		trxVal = setInstanceName(trxVal);
		DefaultLogger.debug(this, "Instance Name: " + trxVal.getInstanceName());
		ITrxOperation op = getOperation(trxVal, param);
		CMSReadTrxManager mgr = new CMSReadTrxManager();

		try {
			ITrxResult result = mgr.operateTransaction(op, trxVal);
			return result;
		}
		catch (TransactionException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxControllerException("Caught Unknown Exception: " + e.toString());
		}
	}

	/**
	 * Get operation for the transaction given the value and param.
	 * 
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction operation
	 * @throws TrxParameterException if the transaction parameter is invalid
	 */
	public ITrxOperation getOperation(ITrxValue trxVal, ITrxParameter param) throws TrxParameterException {
		if (param == null) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();

		DefaultLogger.debug(this, "Action is " + action);

		if (action == null) {
			throw new TrxParameterException("Action is null!");
		}

		if (action.equals(ICMSConstant.ACTION_READ_CASH_MARGIN)) {
			return new ReadCashMarginOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_READ_CASH_MARGIN_BY_TRXID)) {
			return new ReadCashMarginByTrxIDOperation();
		}
		else {
			throw new TrxParameterException("Unknow Action: " + action + ".");
		}
	}
}
