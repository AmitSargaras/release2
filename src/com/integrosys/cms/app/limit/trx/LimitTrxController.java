/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/LimitTrxController.java,v 1.7 2004/09/03 06:56:03 pooja Exp $
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
 * This controller is used to control Limit related operations.
 * 
 * @author $Author: pooja $
 * @version $Revision: 1.7 $
 * @since $Date: 2004/09/03 06:56:03 $ Tag: $Name: $
 */
public class LimitTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public LimitTrxController() {
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
		return ICMSConstant.INSTANCE_LIMIT;
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
		DefaultLogger.debug(this, "Status: " + status);
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
		if (null == fromState) {
			throw new TrxParameterException("From State is null!");
		}

		if (status.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_CREATE_LIMIT)) {
				return new CreateLimitOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_LIMIT)) {
				return new MakerUpdateLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT)) {
				return new SystemCloseLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT)) {
				return new HostUpdateLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT)) {
				return new SystemDeleteLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT)) {
				return new SystemUpdateLimitOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT)) {
				return new CheckerApproveUpdateLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_LIMIT)) {
				return new CheckerRejectUpdateLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_TAT)) {
				return new CheckerRejectUpdateLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT)) {
				return new SystemCloseLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT)) {
				return new HostUpdateLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT)) {
				return new SystemDeleteLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT)) {
				return new SystemUpdateLimitOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerCancelUpdateLimitOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			// else
			// if(action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_LIMIT
			// )) {
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_LIMIT)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerResubmitUpdateLimitOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT)) {
				return new SystemCloseLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_LIMIT)) {
				return new HostUpdateLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT)) {
				return new SystemDeleteLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_LIMIT)) {
				return new SystemUpdateLimitOperation();
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