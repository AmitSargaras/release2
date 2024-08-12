/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/WarehouseTrxController.java,v 1.4 2004/08/13 07:31:29 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/13 07:31:29 $ Tag: $Name: $
 */
public class WarehouseTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public WarehouseTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table. Not
	 * implemented.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMODITY_MAIN_WAREHOUSE;
	}

	/**
	 * Returns an ITrxOperation object
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException on
	 *         error
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	/**
	 * Helper method to factory the operations
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException on
	 *         error
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
				return new MakerSaveWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
				return new MakerCreateWarehouseOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
				return new MakerUpdateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN)) {
				return new MakerDeleteWarehouseOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveCreateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectCreateWarehouseOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}

		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveUpdateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectUpdateWarehouseOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveDeleteWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectDeleteWarehouseOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseCreateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseUpdateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
				return new MakerUpdateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
				return new MakerCreateWarehouseOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action; " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedCreateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedCreateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedUpdateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedUpdateWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedDeleteWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedDeleteWarehouseOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveWarehouseOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else {
			throw new TrxParameterException(" -- No operations found! with  FROM_STATE : '" + fromState
					+ "' and Operation: '" + action + "'. "
					+ "\n Please check Transaction Matrix talbe for supported combinations. ");
		}

	}
}