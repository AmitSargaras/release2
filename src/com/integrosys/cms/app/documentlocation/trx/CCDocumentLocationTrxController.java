/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/CCDocumentLocationTrxController.java,v 1.1 2004/02/17 02:12:37 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control CC document location related operations.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:37 $ Tag: $Name: $
 */
public class CCDocumentLocationTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CCDocumentLocationTrxController() {
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
		return ICMSConstant.INSTANCE_CC_DOC_LOC;
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
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CC_DOC_LOC)) {
				return new MakerCreateCCDocumentLocationOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CC_DOC_LOC)) {
			return new SystemCloseCCDocumentLocationOperation();
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CC_DOC_LOC)) {
				return new CheckerApproveCreateCCDocumentLocationOperation();
			}

			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CC_DOC_LOC)) {
				return new CheckerRejectCCDocumentLocationOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CC_DOC_LOC)) {
				return new MakerUpdateCCDocumentLocationOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CC_DOC_LOC)) {
				return new CheckerApproveUpdateCCDocumentLocationOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CC_DOC_LOC)) {
				return new CheckerRejectCCDocumentLocationOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CC_DOC_LOC)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreateCCDocumentLocationOperation();
				}
				return new MakerEditRejectedUpdateCCDocumentLocationOperation();
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CC_DOC_LOC)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreateCCDocumentLocationOperation();
				}
				return new MakerCloseRejectedUpdateCCDocumentLocationOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}