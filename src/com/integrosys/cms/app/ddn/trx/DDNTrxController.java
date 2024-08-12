/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/DDNTrxController.java,v 1.5 2005/06/08 06:33:54 htli Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control ddn related operations.
 * 
 * @author $Author: htli $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/06/08 06:33:54 $ Tag: $Name: $
 */
public class DDNTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public DDNTrxController() {
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
		return ICMSConstant.INSTANCE_DDN;
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
			if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_DDN)) {
				return new SystemCreateDDNOperation();
			}
			// ck test
			else if (action.equals(ICMSConstant.ACTION_MAKER_GENERATE_DDN)) {
				return new MakerCreateDDNOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_NEW)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_GENERATE_DDN)) {
				return new MakerUpdateDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_DDN)) {
				return new SystemCloseDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_GENERATE_NEW_BCA_DDN)) {

				// return new MakerUpdateDDNOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_GENERATE_DDN)) {
				return new MakerUpdateDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_DDN)) {
				return new SystemCloseDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_GENERATE_NEW_BCA_DDN)) {
				// return new MakerUpdateDDNOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN)) {
				return new CheckerApproveUpdateDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_DDN)) {
				return new CheckerRejectDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_DDN)) {
				return new SystemCloseDDNOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN)) {
				return new CheckerApproveUpdateDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_DDN)) {
				return new CheckerRejectDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_DDN)) {
				return new SystemCloseDDNOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_DDN)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreateDDNOperation();
				}
				return new MakerEditRejectedUpdateDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_DDN)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreateDDNOperation();
				}
				return new MakerCloseRejectedUpdateDDNOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_DDN)) {
				return new SystemCloseDDNOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}