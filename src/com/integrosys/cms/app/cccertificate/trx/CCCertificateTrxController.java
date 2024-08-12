/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/CCCertificateTrxController.java,v 1.5 2003/09/04 13:16:29 kllee Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

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
 * @author $Author: kllee $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/09/04 13:16:29 $ Tag: $Name: $
 */
public class CCCertificateTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CCCertificateTrxController() {
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
		return ICMSConstant.INSTANCE_CCC;
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
			if (action.equals(ICMSConstant.ACTION_MAKER_GENERATE_CCC)) {
				return new MakerCreateCCCOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_GENERATE_CCC)) {
				return new MakerUpdateCCCOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CCC)) {
				return new SystemCloseCCCOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_CCC)) {
				return new CheckerApproveCreateCCCOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_CCC)) {
				return new CheckerRejectCCCOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CCC)) {
				return new SystemCloseCCCOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_CCC)) {
				return new CheckerApproveUpdateCCCOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_CCC)) {
				return new CheckerRejectCCCOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CCC)) {
				return new SystemCloseCCCOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_CCC)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreateCCCOperation();
				}
				return new MakerEditRejectedUpdateCCCOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_CCC)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreateCCCOperation();
				}
				return new MakerCloseRejectedUpdateCCCOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CCC)) {
				return new SystemCloseCCCOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}