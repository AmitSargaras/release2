/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CollateralParameterTrxController.java,v 1.7 2003/08/14 04:35:10 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This trx controller is to be used in Collateral Parameter. It provides
 * factory for trx operations that are specific to collateral parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/14 04:35:10 $ Tag: $Name: $
 */
public class CollateralParameterTrxController extends CMSTrxController {

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
	public CollateralParameterTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COL_PARAMETER;
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

		Validate.notNull(fromState, "from state must not be null.");

		String action = param.getAction();
		logger.debug("Action: " + action);

		Validate.notNull(action, "action must not be null.");

		if (fromState.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLPARAM)) {
				return getTrxOperation("MakerUpdateCollateralParameterOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COLPARAM)) {
				return getTrxOperation("MakerSaveCollateralParameterOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLPARAM)) {
				return getTrxOperation("MakerUpdateCollateralParameterOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COLPARAM)) {
				return getTrxOperation("MakerSaveCollateralParameterOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLPARAM)) {
				return getTrxOperation("MakerUpdateCollateralParameterOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COLPARAM)) {
				return getTrxOperation("MakerSaveCollateralParameterOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_COLPARAM)) {
				return getTrxOperation("MakerCancelUpdateCollateralParameterOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLPARAM)) {
				return getTrxOperation("CheckerApproveUpdateCollateralParameterOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLPARAM)) {
				return getTrxOperation("CheckerRejectUpdateCollateralParameterOperation");
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_COLPARAM)) {
				return getTrxOperation("MakerCancelUpdateCollateralParameterOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLPARAM)) {
				return getTrxOperation("MakerUpdateCollateralParameterOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COLPARAM)) {
				return getTrxOperation("MakerSaveCollateralParameterOperation");
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
