package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:00:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrPaTrxController extends CMSTrxController {

	/**
	 * Default Constructor
	 */
	public PrPaTrxController() {
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
		return ICMSConstant.INSTANCE_AUTO_VAL_PARAM;
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
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_PRPA)) {
				DefaultLogger.debug(this, "coming here create prpa........ ");
				return new MakerCreatePrPaOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PRPA)) {
				DefaultLogger.debug(this, "coming here approve prpa........ ");
				return new CheckerApproveCreatePrPaOperation();
			}

			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_PRPA)) {
				return new CheckerRejectPrPaOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_PRPA)) {
				return new MakerUpdatePrPaOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_PRPA)) {
				return new MakerDeletePrPaOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PRPA)) {
				return new CheckerApproveUpdatePrPaOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_PRPA)) {
				return new CheckerRejectPrPaOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PRPA)) {
				return new CheckerApproveDeletePrPaOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_PRPA)) {
				return new CheckerRejectPrPaOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PRPA)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreatePrPaOperation();
				}
				if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return new MakerEditRejectedDeletePrPaOperation();
				}
				return new MakerEditRejectedUpdatePrPaOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PRPA)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreatePrPaOperation();
				}
				if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return new MakerCloseRejectedDeletePrPaOperation();
				}
				return new MakerCloseRejectedUpdatePrPaOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}

}
