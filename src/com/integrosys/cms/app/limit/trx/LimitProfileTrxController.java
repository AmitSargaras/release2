/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/LimitProfileTrxController.java,v 1.11 2004/04/01 11:12:27 lyng Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control LimitProfile related operations.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.11 $
 * @since $Date: 2004/04/01 11:12:27 $ Tag: $Name: $
 */
public class LimitProfileTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public LimitProfileTrxController() {
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
		return ICMSConstant.INSTANCE_LIMIT_PROFILE;
	}

	/**
	 * Returns an ITrxOperation object
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws TrxParameterException on error
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	// helper method
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String status = value.getStatus();
		DefaultLogger.debug(this, "status: " + status);
		if (null == status) {
			throw new TrxParameterException("Status is null!");
		}
		String action = param.getAction();
		DefaultLogger.debug(this, "Action: " + action);
		if (null == action) {
			throw new TrxParameterException("Action is null in IAMTrxParameter!");
		}
		String fromState = value.getFromState();
		DefaultLogger.debug(this, "From State: " + fromState);
		// if(null == fromState) {
		// throw new TrxParameterException("From State is null!");
		// }

		if (status.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_CREATE_LIMIT_PROFILE)) {
				return new CreateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_LIMIT_PROFILE)) {
				return new MakerCreateLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_NEW)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_TAT)) {
				return new MakerCreateTATOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT_PROFILE)) {
				return new SystemCloseLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT_PROFILE)) {
				return new HostUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_RENEW_LIMIT_PROFILE)) {
				return new RenewLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT_PROFILE)) {
				return new SystemDeleteLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT_PROFILE)) {
				return new SystemUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DIRECT_UPDATE_LIMIT_PROFILE)) {
				return new MakerDirectUpdateLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_TAT)) {
				return new CheckerApproveCreateTATOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_TAT)) {
				return new CheckerRejectCreateTATOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT_PROFILE)) {
				return new SystemCloseLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT_PROFILE)) {
				return new HostUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_RENEW_LIMIT_PROFILE)) {
				return new RenewLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT_PROFILE)) {
				return new SystemDeleteLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT_PROFILE)) {
				return new SystemUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT_PROFILE)) {
				return new CheckerApproveUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_LIMIT_PROFILE)) {
				return new CheckerRejectUpdateLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_LIMIT_PROFILE)) {
				return new MakerUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT_PROFILE)) {
				return new SystemCloseLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT_PROFILE)) {
				return new HostUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_UPDATE_TAT)) {
				return new UpdateTATOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT_PROFILE)) {
				return new SystemUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_RENEW_LIMIT_PROFILE)) {
				return new RenewLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT_PROFILE)) {
				return new SystemDeleteLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_LIMIT_PROFILE)) {
				return new MakerDeleteLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DIRECT_UPDATE_LIMIT_PROFILE)) {
				return new MakerDirectUpdateLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT_PROFILE)) {
				return new CheckerApproveUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_LIMIT_PROFILE)) {
				return new CheckerRejectUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT_PROFILE)) {
				return new SystemCloseLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT_PROFILE)) {
				return new HostUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_UPDATE_TAT)) {
				return new UpdateTATOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT_PROFILE)) {
				return new SystemUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_RENEW_LIMIT_PROFILE)) {
				return new RenewLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT_PROFILE)) {
				return new SystemDeleteLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT_PROFILE)) {
				return new CheckerApproveDeleteLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_LIMIT_PROFILE)) {
				return new CheckerRejectUpdateLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_LIMIT_PROFILE)) {
				return new MakerCreateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_CREATE_LIMIT_PROFILE)) {
				return new MakerCancelCreateLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_REJECTED_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_LIMIT_PROFILE)) {
				return new MakerCancelUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_LIMIT_PROFILE)) {
				return new MakerDeleteLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerCancelUpdateLimitProfileOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			// else if(action.equals(ICMSConstant.
			// ACTION_MAKER_RESUBMIT_UPDATE_LIMIT_PROFILE)) {
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_LIMIT_PROFILE)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerResubmitUpdateLimitProfileOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_TAT)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerResubmitCreateTATOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_CREATE_TAT)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCancelCreateTATOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			else if (action.equals(ICMSConstant.ACTION_UPDATE_TAT)) {
				return new RejectUpdateTATOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT_PROFILE)) {
				return new SystemCloseLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT_PROFILE)) {
				return new HostUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT_PROFILE)) {
				return new SystemUpdateLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_RENEW_LIMIT_PROFILE)) {
				return new RenewLimitProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT_PROFILE)) {
				return new SystemDeleteLimitProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else {
			throw new TrxParameterException("From State does not match presets! No operations found!");
		}
	}
}