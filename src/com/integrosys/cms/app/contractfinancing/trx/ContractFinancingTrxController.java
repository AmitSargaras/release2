package com.integrosys.cms.app.contractfinancing.trx;

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
public class ContractFinancingTrxController extends CMSTrxController {

	/**
	 * Default Constructor
	 */
	public ContractFinancingTrxController() {
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CONTRACT_FINANCING;
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

		if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CONTRACT_FINANCING)) {
			if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
				return new CheckerApproveCreateContractFinancingOperation();
			}
			else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
				return new CheckerApproveUpdateContractFinancingOperation();
			}
		}

		if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CONTRACT_FINANCING)) {
			return new CheckerRejectContractFinancingOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CONTRACT_FINANCING)) {
			return new MakerCloseContractFinancingOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_CONTRACT_FINANCING)) {
			return new MakerCloseDeleteContractFinancingOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CONTRACT_FINANCING)) {
			return new MakerCloseUpdateContractFinancingOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CONTRACT_FINANCING)) {
			if (toState == null) { // new - ND
				return new MakerCreateNDContractFinancingOperation();
			}
			else if (toState.equals(ICMSConstant.STATE_DRAFT) || toState.equals(ICMSConstant.STATE_REJECTED)) {
				return new MakerCreateContractFinancingOperation();
			}
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CONTRACT_FINANCING)) {
			return new MakerDeleteContractFinancingOperation();
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CONTRACT_FINANCING)) {
			if (toState == null) { // new - ND
				return new MakerSaveNDContractFinancingOperation();
			}
			if (toState.equals(ICMSConstant.STATE_ACTIVE) || toState.equals(ICMSConstant.STATE_DRAFT)
					|| toState.equals(ICMSConstant.STATE_REJECTED)) {
				return new MakerSaveContractFinancingOperation();
			}
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CONTRACT_FINANCING)) {
			return new MakerUpdateContractFinancingOperation();
		}

		DefaultLogger.debug(this, "No operations found !");

		throw new TrxParameterException("No operations found!");
	}

}
