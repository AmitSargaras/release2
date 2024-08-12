/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ValuationTrxController.java,v 1.12 2003/07/18 06:08:10 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control valuation related operations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2003/07/18 06:08:10 $ Tag: $Name: $
 */
public class ValuationTrxController extends CMSTrxController {

	private Map nameTrxOperationMap;

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	protected ITrxOperation getTrxOperation(String name) throws TrxParameterException {
		ITrxOperation op = (ITrxOperation) getNameTrxOperationMap().get(name);

		if (op == null) {
			throw new TrxParameterException("trx operation retrieved is null for given name [" + name + "]");
		}

		return op;
	}

	/**
	 * Default Constructor
	 */
	public ValuationTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_VALUATION;
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
		logger.debug("Returning Operation: " + op);
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
		logger.debug("FromState: " + fromState);

		Validate.notNull(fromState, "fromState must not be null.");

		String action = param.getAction();
		logger.debug("Action: " + action);

		Validate.notNull(action, "action must not be null.");

		if (fromState.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MANUAL_FEEDS_CREATE_REVAL)) {
				return getTrxOperation("ManualFeedsCreateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MANUAL_CREATE_REVAL)) {
				return getTrxOperation("ManualCreateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_REVAL)) {
				return getTrxOperation("SystemCreateRevaluationOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_REVAL)) {
				return getTrxOperation("CheckerApproveRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_REVAL)) {
				return getTrxOperation("CheckerRejectRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_REVAL)) {
				return getTrxOperation("SystemCloseRevaluationOperation");
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_REVAL)) {
				return getTrxOperation("CheckerRejectRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_REVAL)) {
				return getTrxOperation("CheckerApproveRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_REVAL)) {
				return getTrxOperation("SystemCloseRevaluationOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MANUAL_UPDATE_REVAL)) {
				return getTrxOperation("ManualUpdateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MANUAL_RESUBMIT_CREATE_REVAL)) {
				return getTrxOperation("ManualResubmitCreateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MANUAL_RESUBMIT_UPDATE_REVAL)) {
				return getTrxOperation("ManualResubmitUpdateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MANUAL_CANCEL_CREATE_REVAL)) {
				return getTrxOperation("ManualCancelCreateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MANUAL_CANCEL_UPDATE_REVAL)) {
				return getTrxOperation("ManualCancelUpdateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_REVAL)) {
				return getTrxOperation("SystemCloseRevaluationOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MANUAL_UPDATE_REVAL)) {
				return getTrxOperation("ManualUpdateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MANUAL_FEEDS_UPDATE_REVAL)) {
				return getTrxOperation("ManualFeedsUpdateRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_REVAL)) {
				return getTrxOperation("SystemCloseRevaluationOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_REVAL)) {
				return getTrxOperation("SystemUpdateRevaluationOperation");
			}
			else {
				throw new TrxParameterException("Unknown action: " + action + " with fromState: " + fromState);
			}
		}
		else {
			throw new TrxParameterException("From State does not match presets! No operations found!");
		}
	}
}