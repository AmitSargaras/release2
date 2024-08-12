/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/UnitofMeasureTrxController.java,v 1.3 2004/08/13 07:31:05 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/13 07:31:05 $ Tag: $Name: $
 */
public class UnitofMeasureTrxController extends CMSTrxController {
	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMODITY_MAIN_UOM;
	}

	/**
	 * Get transaction operation given the transaction value and parameter.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException if
	 *         transaction parameter is invalid
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
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
				return new MakerCreateUOMOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
				return new MakerUpdateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveUOMOperation();
			} /*
			 * else if (action.equals
			 * (ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN)) { return new
			 * MakerDeleteUOMOperation(); }
			 */
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveCreateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectCreateUOMOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveUpdateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectUpdateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectCreateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveCreateUOMOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
			/*
			 * } else if (fromState.equals (ICMSConstant.STATE_PENDING_DELETE))
			 * { if (action.equals
			 * (ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN)) {
			 * return new CheckerApproveDeleteUOMOperation(); } else if
			 * (action.equals
			 * (ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN)) {
			 * return new CheckerRejectDeleteUOMOperation(); } else { throw new
			 * TrxParameterException ("Unknown Action: " + action +
			 * " with fromState: " + fromState); }
			 */
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseCreateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseUpdateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
				return new MakerUpdateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
				return new MakerCreateUOMOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action; " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedCreateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedCreateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedUpdateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedUpdateUOMOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveUOMOperation();
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
