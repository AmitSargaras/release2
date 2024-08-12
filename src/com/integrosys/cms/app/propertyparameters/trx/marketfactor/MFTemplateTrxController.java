/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx.marketfactor;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This trx controller is to be used in MF Template. It provides factory for trx
 * operations that are specific to MF Template.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MFTemplateTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public MFTemplateTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return instance of MF Template
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_MF_TEMPLATE;
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
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_MF_TEMPLATE)) {
				return new MakerCreateMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_MF_TEMPLATE)) {
				return new MakerUpdateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_MF_TEMPLATE)) {
				return new MakerDeleteMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_MF_TEMPLATE)) {
				return new MakerUpdateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_MF_TEMPLATE)) {
				return new MakerUpdateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_MF_TEMPLATE)) {
				return new MakerCloseCreateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_MF_TEMPLATE)) {
				return new MakerCloseUpdateMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_MF_TEMPLATE)) {
				return new CheckerApproveUpdateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_MF_TEMPLATE)) {
				return new CheckerRejectUpdateMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_MF_TEMPLATE)) {
				return new CheckerApproveUpdateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_MF_TEMPLATE)) {
				return new CheckerRejectUpdateMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_MF_TEMPLATE)) {
				return new CheckerApproveDeleteMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_MF_TEMPLATE)) {
				return new CheckerRejectUpdateMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_MF_TEMPLATE)) {
				return new MakerCreateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_MF_TEMPLATE)) {
				return new MakerCloseCreateMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_MF_TEMPLATE)) {
				return new MakerCloseUpdateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_MF_TEMPLATE)) {
				return new MakerUpdateMFTemplateOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_MF_TEMPLATE)) {
				return new MakerCloseUpdateMFTemplateOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_MF_TEMPLATE)) {
				return new MakerDeleteMFTemplateOperation();
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