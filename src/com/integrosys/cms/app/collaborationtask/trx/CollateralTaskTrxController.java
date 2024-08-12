/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/CollateralTaskTrxController.java,v 1.4 2006/04/13 06:20:41 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control collateral task related operations.
 * 
 * @author $Author: jzhai $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/04/13 06:20:41 $ Tag: $Name: $
 */
public class CollateralTaskTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CollateralTaskTrxController() {
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
		return ICMSConstant.INSTANCE_COLLATERAL_TASK;
	}

	/**
	 * Returns an ITrxOperation object
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws TrxParameterException on error
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
	 * @throws TrxParameterException on error
	 */
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		DefaultLogger.debug(this, "Action: " + action);

		String toState = value.getToState();
		String fromState = value.getFromState();
		DefaultLogger.debug(this, "toState: " + value.getToState());
		if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_TASK)) {
				return new MakerCreateCollateralTaskOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_COLLATERAL_TASK)) {
			return new SystemCloseCollateralTaskOperation();
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_TASK)) {
				return new CheckerApproveCreateCollateralTaskOperation();
			}

			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_TASK)) {
				return new CheckerRejectCollateralTaskOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_TASK)) {
				return new MakerUpdateCollateralTaskOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_REJECT_COLLATERAL_TASK)) {
				return new MakerRejectCollateralTaskOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_REJECT)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_TASK)) {
				return new CheckerApproveRejectedCollateralTaskOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_TASK)) {
				return new CheckerRejectRejectedCollateralTaskOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_REJECT_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_TASK)) {
				return new MakerEditRejectRejectedUpdateCollateralTaskOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_TASK)) {
				return new MakerCloseRejectRejectedUpdateCollateralTaskOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_TASK)) {
				return new CheckerApproveUpdateCollateralTaskOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_TASK)) {
				return new CheckerRejectCollateralTaskOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_TASK)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreateCollateralTaskOperation();
				}
				return new MakerEditRejectedUpdateCollateralTaskOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_TASK)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreateCollateralTaskOperation();
				}
				return new MakerCloseRejectedUpdateCollateralTaskOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}