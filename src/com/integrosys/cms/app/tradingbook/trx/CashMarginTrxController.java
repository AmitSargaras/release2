/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This trx controller is to be used in cash margin. It provides factory for trx
 * operations that are specific to cash margin.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class CashMarginTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CashMarginTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return instance of cash margin
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CASH_MARGIN;
	}

	/**
	 * Get transaction operation given the transaction value and parameter.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws TrxParameterException if transaction parameter is invalid
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	/**
	 * Helper method to get the operation given the transaction value and
	 * transaction parameter.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction operation
	 * @throws TrxParameterException if the transaction parameter is invalid
	 */
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String fromState = value.getStatus();
		DefaultLogger.debug(this, "FromState: " + fromState);

		if (fromState == null) {
			throw new TrxParameterException("From State is null!");
		}

		String action = param.getAction();
		DefaultLogger.debug(this, "Action: " + action);

		if (action == null) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		if (fromState.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASH_MARGIN)) {
				return new MakerUpdateCashMarginOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CASH_MARGIN)) {
				return new MakerSaveCashMarginOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASH_MARGIN)) {
				return new MakerUpdateCashMarginOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CASH_MARGIN)) {
				return new MakerSaveCashMarginOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASH_MARGIN)) {
				return new MakerUpdateCashMarginOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CASH_MARGIN)) {
				return new MakerSaveCashMarginOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_CASH_MARGIN)) {
				return new MakerCancelUpdateCashMarginOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CASH_MARGIN)) {
				return new CheckerApproveUpdateCashMarginOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CASH_MARGIN)) {
				return new CheckerRejectUpdateCashMarginOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CASH_MARGIN)) {
				return new MakerSaveCashMarginOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_CASH_MARGIN)) {
				return new MakerCancelUpdateCashMarginOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASH_MARGIN)) {
				return new MakerUpdateCashMarginOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else {
			throw new TrxParameterException("From State does not match presets! No operations found!");
		}
	}
}