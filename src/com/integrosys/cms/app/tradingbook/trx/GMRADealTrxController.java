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
 * This trx controller is to be used in GMRA deal. It provides factory for trx
 * operations that are specific to GMRA deal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class GMRADealTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public GMRADealTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return instance of GMRA deal
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_GMRA_DEAL;
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
		DefaultLogger.debug(this, "FromState/Status: " + fromState);
		String toState = value.getToState();
		String fromState1 = value.getFromState();
		DefaultLogger.debug(this, "ToState: " + toState);
		DefaultLogger.debug(this, "FromState: " + fromState1);

		if (fromState == null) {
			throw new TrxParameterException("From State is null!");
		}

		String action = param.getAction();
		DefaultLogger.debug(this, "Action: " + action);

		if (action == null) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		if (fromState.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_GMRA_DEAL)) {
				return new MakerCreateGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_GMRA_DEAL)) {
				return new MakerUpdateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_GMRA_DEAL)) {
				return new MakerDeleteGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_GMRA_DEAL)) {
				return new MakerUpdateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_GMRA_DEAL)) {
				return new MakerUpdateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_GMRA_DEAL)) {
				return new MakerCloseCreateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_GMRA_DEAL)) {
				return new MakerCloseUpdateGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GMRA_DEAL)) {
				return new CheckerApproveUpdateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_GMRA_DEAL)) {
				return new CheckerRejectUpdateGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GMRA_DEAL)) {
				return new CheckerApproveUpdateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_GMRA_DEAL)) {
				return new CheckerRejectUpdateGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GMRA_DEAL)) {
				return new CheckerApproveDeleteGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_GMRA_DEAL)) {
				return new CheckerRejectUpdateGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_GMRA_DEAL)) {
				return new MakerCreateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_GMRA_DEAL)) {
				return new MakerCloseCreateGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_GMRA_DEAL)) {
				return new MakerCloseUpdateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_GMRA_DEAL)) {
				return new MakerUpdateGMRADealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_GMRA_DEAL)) {
				return new MakerCloseUpdateGMRADealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_GMRA_DEAL)) {
				return new MakerDeleteGMRADealOperation();
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