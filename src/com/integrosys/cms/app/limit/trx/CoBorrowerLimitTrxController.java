/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/CoBorrowerLimitTrxController.java,v 1.2 2003/09/12 02:38:47 kllee Exp $
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
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 02:38:47 $ Tag: $Name: $
 */
public class CoBorrowerLimitTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CoBorrowerLimitTrxController() {
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
		return ICMSConstant.INSTANCE_COBORROWER_LIMIT;
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
			if (action.equals(ICMSConstant.ACTION_CREATE_CO_LIMIT)) {
				return new CreateCoBorrowerLimitOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CO_LIMIT)) {
				return new MakerUpdateCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CO_LIMIT)) {
				return new SystemCloseCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_CO_LIMIT)) {
				return new HostUpdateCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CO_LIMIT)) {
				return new SystemDeleteCoBorrowerLimitOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CO_LIMIT)) {
				return new CheckerApproveUpdateCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CO_LIMIT)) {
				return new CheckerRejectUpdateCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CO_LIMIT)) {
				return new SystemCloseCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_CO_LIMIT)) {
				return new HostUpdateCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CO_LIMIT)) {
				return new SystemDeleteCoBorrowerLimitOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerCancelUpdateCoBorrowerLimitOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			// else
			// if(action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_CO_LIMIT
			// )) {
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CO_LIMIT)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerResubmitUpdateCoBorrowerLimitOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CO_LIMIT)) {
				return new SystemCloseCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_CO_LIMIT)) {
				return new HostUpdateCoBorrowerLimitOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CO_LIMIT)) {
				return new SystemDeleteCoBorrowerLimitOperation();
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