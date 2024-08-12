/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/CommodityDealTrxController.java,v 1.11 2005/11/11 07:46:27 czhou Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This trx controller is to be used in Commodity Deal. It provides factory for
 * trx operations that are specific to deal.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/11/11 07:46:27 $ Tag: $Name: $
 */
public class CommodityDealTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CommodityDealTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMODITY_DEAL;
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
			throw new TrxParameterException("Action is null in IAMTrxParameter!");
		}

		if (fromState.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DEAL)) {
				return new MakerCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_DEAL)) {
				return new MakerSaveCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_DEAL)) {
				return new CheckerApproveCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_FORWARD_CREATE_DEAL)) {
				return new CheckerForwardCreateCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE_VERIFY)) {
			if (action.equals(ICMSConstant.ACTION_OFFICER_FORWARD_CREATE_DEAL)) {
				return new OfficerForwardCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_OFFICER_REJECT_CREATE_DEAL)) {
				return new OfficerRejectCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_OFFICER_APPROVE_CREATE_DEAL)) {
				return new OfficerApproveCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_FAM_REJECT_CREATE_DEAL)) {
				return new FAMRejectCreateCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_REJECT)) {
			if (action.equals(ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CREATE_DEAL)) {
				return new FAMConfirmRejectCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_FAM_CONFIRM_REJECT_UPDATE_DEAL)) {
				return new FAMConfirmRejectUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CLOSE_DEAL)) {
				return new FAMConfirmRejectCloseCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DEAL)) {
				return new MakerUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_DEAL)) {
				return new MakerSaveCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DEAL)) {
				return new MakerCloseCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_VALUATE_DEAL)) {
				return new SystemValuateCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CLOSE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CLOSE_DEAL)) {
				return new CheckerApproveCloseCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CLOSE_DEAL)) {
				return new CheckerRejectCloseCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_FORWARD_CLOSE_DEAL)) {
				return new CheckerForwardCloseCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CLOSE_VERIFY)) {
			if (action.equals(ICMSConstant.ACTION_OFFICER_FORWARD_CLOSE_DEAL)) {
				return new OfficerForwardCloseCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_OFFICER_REJECT_CLOSE_DEAL)) {
				return new OfficerRejectCloseCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_OFFICER_APPROVE_CLOSE_DEAL)) {
				return new OfficerApproveCloseCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_FAM_REJECT_CLOSE_DEAL)) {
				return new FAMRejectCloseCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_DEAL)) {
				return new CheckerApproveUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_DEAL)) {
				return new CheckerRejectUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_FORWARD_UPDATE_DEAL)) {
				return new CheckerForwardUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_VALUATE_DEAL)) {
				return new SystemValuateCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE_VERIFY)) {
			if (action.equals(ICMSConstant.ACTION_OFFICER_FORWARD_UPDATE_DEAL)) {
				return new OfficerForwardUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_OFFICER_REJECT_UPDATE_DEAL)) {
				return new OfficerRejectUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_OFFICER_APPROVE_UPDATE_DEAL)) {
				return new OfficerApproveUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_FAM_REJECT_UPDATE_DEAL)) {
				return new FAMRejectUpdateCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DEAL)) {
				return new MakerUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_DEAL)) {
				return new MakerSaveCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_CREATE_DEAL)) {
				return new MakerCancelCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_DEAL)) {
				return new MakerCancelUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DEAL)) {
				return new MakerCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_VALUATE_DEAL)) {
				return new SystemValuateCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action; " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DEAL)) {
				return new MakerUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_DEAL)) {
				return new MakerSaveCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_CREATE_DEAL)) {
				return new MakerCancelCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_DEAL)) {
				return new MakerCancelUpdateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DEAL)) {
				return new MakerCreateCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_VALUATE_DEAL)) {
				return new SystemValuateCommodityDealOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECT_CLOSE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DEAL)) {
				return new MakerCloseCommodityDealOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_CLOSE_DEAL)) {
				return new MakerCancelCloseCommodityDealOperation();
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