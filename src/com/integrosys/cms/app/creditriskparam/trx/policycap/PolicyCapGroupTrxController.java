/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author user
 * 
 */
public class PolicyCapGroupTrxController extends CMSTrxController {

	private static final long serialVersionUID = 4123857157929003877L;

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
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_POLICY_CAP_GROUP;
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
		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		DefaultLogger.debug(this, "Action: " + action);

		String toState = value.getToState();
		DefaultLogger.debug(this, "toState: " + value.getToState());
		DefaultLogger.debug(this, "fromState: " + value.getFromState());

		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_POLICY_CAP_GROUP)) {
				return new MakerUpdatePolicyCapGroupOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
			}

		}
		else if (toState.equals(ICMSConstant.PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_POLICY_CAP_GROUP)) {
				return new CheckerApprovePolicyCapGroupOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_POLICY_CAP_GROUP)) {
				return new CheckerRejectPolicyCapGroupOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
			}

		}
		else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_POLICY_CAP_GROUP)) {
				return new MakerUpdatePolicyCapGroupOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_POLICY_CAP_GROUP)) {
				return new MakerCloseRejectedPolicyCapGroupOperation();
			}
		}

		return null;
	}

}
