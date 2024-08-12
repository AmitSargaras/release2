package com.integrosys.cms.app.commoncode.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CommonCodeTypeTrxController() {
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
		return ICMSConstant.INSTANCE_COMMON_CODE_TYPE_LIST;
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
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMON_CODE_TYPE)) {
				return new MakerCreateCommonCodeTypeOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COMMON_CODE_TYPE)) {
				return new CheckerApproveCreateCommonCodeTypeOperation();
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COMMON_CODE_TYPE)) {
				return new CheckerRejectCommonCodeTypeOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COMMON_CODE_TYPE)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreateCommonCodeTypeOperation();
				}
				return new MakerCloseRejectedUpdateCommonCodeTypeOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COMMON_CODE_TYPE)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreateCommonCodeTypeOperation();
				}
				return new MakerEditRejectedUpdateCommonCodeTypeOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMON_CODE_TYPE)) {
				return new MakerUpdateCommonCodeTypeOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COMMON_CODE_TYPE)) {
				return new CheckerApproveUpdateCommonCodeTypeOperation();
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COMMON_CODE_TYPE)) {
				return new CheckerRejectCommonCodeTypeOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("From State does not match presets! No operations found!");
	}
}
