/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.marketfactor;

import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This trx controller is to be used in MF Checklist. It provides factory for
 * trx operations that are specific to MF Checklist.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MFChecklistTrxController extends CMSTrxController {

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
	public MFChecklistTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return instance of MF Checklist
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_MF_CHECKLIST;
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
		logger.debug("FromState/Status: " + fromState);

		Validate.notNull(fromState, "fromState must not be null.");

		String action = param.getAction();
		logger.debug("Action: " + action);

		Validate.notNull(action, "action must not be null.");

		if (fromState.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerCreateMFChecklistOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerUpdateMFChecklistOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerUpdateMFChecklistOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerUpdateMFChecklistOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_MF_CHECKLIST)) {
				return getTrxOperation("MakerCloseCreateMFChecklistOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerCloseUpdateMFChecklistOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_MF_CHECKLIST)) {
				return getTrxOperation("CheckerApproveUpdateMFChecklistOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_MF_CHECKLIST)) {
				return getTrxOperation("CheckerRejectUpdateMFChecklistOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_MF_CHECKLIST)) {
				return getTrxOperation("CheckerApproveUpdateMFChecklistOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_MF_CHECKLIST)) {
				return getTrxOperation("CheckerRejectUpdateMFChecklistOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerCreateMFChecklistOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_MF_CHECKLIST)) {
				return getTrxOperation("MakerCloseCreateMFChecklistOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerCloseUpdateMFChecklistOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_MF_CHECKLIST)) {
				return getTrxOperation("MakerUpdateMFChecklistOperation");
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