package com.integrosys.cms.app.bridgingloan.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public class BridgingLoanTrxController extends CMSTrxController {

	/**
	 * Default Constructor
	 */
	public BridgingLoanTrxController() {
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_BRIDGING_LOAN;
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
		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		String toState = value.getToState();
		DefaultLogger.debug(this, "action: " + action);
		DefaultLogger.debug(this, "fromState: " + value.getFromState());
		DefaultLogger.debug(this, "toState: " + value.getToState());

		if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BRIDGING_LOAN)) {
			if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
				return new CheckerApproveCreateBridgingLoanOperation();
			}
			else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
				return new CheckerApproveUpdateBridgingLoanOperation();
			}
		}

		if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BRIDGING_LOAN)) {
			if (toState.equals(ICMSConstant.STATE_PENDING_CREATE) || toState.equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
				return new CheckerRejectBridgingLoanOperation();
			}
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_BRIDGING_LOAN)) {
			return new MakerCloseBridgingLoanOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_BRIDGING_LOAN)) {
			return new MakerCloseDeleteBridgingLoanOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_BRIDGING_LOAN)) {
			return new MakerCloseUpdateBridgingLoanOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_BRIDGING_LOAN)) {
			if (toState == null) { // new - ND
				return new MakerCreateNDBridgingLoanOperation();
			}
			else if (toState.equals(ICMSConstant.STATE_DRAFT) || toState.equals(ICMSConstant.STATE_REJECTED_CREATE)) {
				return new MakerCreateBridgingLoanOperation();
			}
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_BRIDGING_LOAN)) {
			return new MakerDeleteBridgingLoanOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_BRIDGING_LOAN)) {
			if (toState == null) { // new - ND
				return new MakerSaveNDBridgingLoanOperation();
			}
			if (toState.equals(ICMSConstant.STATE_ACTIVE) || toState.equals(ICMSConstant.STATE_DRAFT)
					|| toState.equals(ICMSConstant.STATE_REJECTED)) {
				return new MakerSaveBridgingLoanOperation();
			}
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BRIDGING_LOAN)) {
			return new MakerUpdateBridgingLoanOperation();
		}
		DefaultLogger.debug(this, "No operations found !");
		throw new TrxParameterException("No operations found!");
	}
}