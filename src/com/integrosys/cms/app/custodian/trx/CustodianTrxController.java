/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/CustodianTrxController.java,v 1.22 2005/10/14 02:28:45 whuang Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control Custodian related operations.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.22 $
 * @since $Date: 2005/10/14 02:28:45 $ Tag: $Name: $
 */
public class CustodianTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CustodianTrxController() {
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
		return ICMSConstant.INSTANCE_CUSTODIAN;
	}

	/**
	 * Get operation for the given transaction value and transaction parameter.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws TrxParameterException if invalid transaction value and param
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String fromState = value.getStatus();
		String action = param.getAction();
		String previousState = value.getFromState();
		DefaultLogger.debug(this, "FromState: " + fromState);
		DefaultLogger.debug(this, "Action: " + action);
		DefaultLogger.debug(this, "previousState: " + previousState);

		if (fromState == null) {
			throw new TrxParameterException("From State is null!");
		}
		if (action == null) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		if (fromState.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CUSTODIAN_DOC)) {
				return new MakerSaveCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CUSTODIAN_DOC)) {
				return new MakerCreateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_REQUIRED)) {
				return new CreateRequiredCustodianTrxOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTODIAN_DOC)) {
				return new MakerUpdateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CUSTODIAN_DOC)) {
				return new MakerSaveCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_REQUIRED)) { // &&
				// (previousState.equals(ICMSConstant.STATE_PENDING_CREATE)
				// || previousState.equals(ICMSConstant.STATE_PENDING_UPDATE)))
				// {
				return new UpdateRequiredCustodianTrxOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTODIAN_DOC)) {
				return new CheckerApproveCreateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTODIAN_DOC)) {
				return new CheckerRejectCreateCustodianDocTrxOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTODIAN_DOC)) {
				return new CheckerApproveUpdateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTODIAN_DOC)) {
				return new CheckerRejectUpdateCustodianDocTrxOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CUSTODIAN_DOC)) {
				return new MakerSaveCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CREATE_CUSTODIAN_DOC)) {
				return new MakerCloseCreateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CUSTODIAN_DOC)) {
				return new MakerCloseUpdateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTODIAN_DOC)) {
				return new MakerUpdateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CUSTODIAN_DOC)) {
				return new MakerCreateCustodianDocTrxOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action; " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CUSTODIAN_DOC)) {
				return new MakerCreateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTODIAN_DOC)) {
				return new MakerUpdateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CREATE_CUSTODIAN_DOC)) {
				return new MakerCloseCreateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CUSTODIAN_DOC)) {
				return new MakerCloseUpdateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CUSTODIAN_DOC)) {
				return new MakerSaveCustodianDocTrxOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else if (fromState.equals(ICMSConstant.STATE_REQUIRED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CUSTODIAN_DOC)) {
				return new MakerSaveCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CUSTODIAN_DOC)) {
				return new MakerCreateCustodianDocTrxOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTODIAN_DOC)) {
				return new MakerUpdateCustodianDocTrxOperation();
			}
			else {
				throw new TrxParameterException("Unknown Action: " + action + " with fromState: " + fromState);
			}
		}
		else {
			throw new TrxParameterException("From State does not match presets! No operations found!");
		}
	}
}

// old implementation
/*
 * public ITrxOperation getOperation(ITrxValue value, ITrxParameter param)
 * throws TrxParameterException { String status = value.getStatus(); String
 * fromState = value.getFromState(); String action = param.getAction();
 * DefaultLogger.debug(this, "FromState: " + fromState);
 * DefaultLogger.debug(this, "status: " + status); DefaultLogger.debug(this,
 * "Action: " + action);
 * 
 * 
 * if (fromState == null) { throw new
 * TrxParameterException("From State is null!"); } if (action == null) { throw
 * new TrxParameterException("Action is null in ITrxParameter!"); }
 * 
 * if (status.equals(ICMSConstant.STATE_ND)) { if
 * (action.equals(ICMSConstant.ACTION_LODGE_CUSTODIAN_DOC)) { return new
 * LodgeCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_RECEIVED); }
 * 
 * if (status.equals(ICMSConstant.STATE_PENDING_LODGE)) { if
 * (action.equals(ICMSConstant.ACTION_APPROVE_LODGE_CUSTODIAN_DOC)) { return new
 * ApproveLodgeCustodianDocTrxOperation(); } if
 * (action.equals(ICMSConstant.ACTION_REJECT_CUSTODIAN_DOC)) { return new
 * RejectUpdateCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_PENDING_LODGE); }
 * 
 * if (status.equals(ICMSConstant.STATE_LODGED)) { if
 * (action.equals(ICMSConstant.ACTION_TEMP_UPLIFT_CUSTODIAN_DOC)) { return new
 * TempUpliftCustodianDocTrxOperation(); } if
 * (action.equals(ICMSConstant.ACTION_PERM_UPLIFT_CUSTODIAN_DOC)) { return new
 * PermUpliftCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_LODGED); }
 * 
 * if (status.equals(ICMSConstant.STATE_PENDING_TEMP_UPLIFT)) { if
 * (action.equals(ICMSConstant.ACTION_APPROVE_TEMP_UPLIFT_CUSTODIAN_DOC)) {
 * return new ApproveTempUpliftCustodianDocTrxOperation(); } if
 * (action.equals(ICMSConstant.ACTION_REJECT_CUSTODIAN_DOC)) { return new
 * RejectUpdateCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_PENDING_TEMP_UPLIFT); }
 * 
 * if (status.equals(ICMSConstant.STATE_PENDING_PERM_UPLIFT)) { if
 * (action.equals(ICMSConstant.ACTION_APPROVE_PERM_UPLIFT_CUSTODIAN_DOC)) {
 * return new ApprovePermUpliftCustodianDocTrxOperation(); } if
 * (action.equals(ICMSConstant.ACTION_REJECT_CUSTODIAN_DOC)) { return new
 * RejectUpdateCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_PENDING_PERM_UPLIFT); }
 * 
 * if (status.equals(ICMSConstant.STATE_TEMP_UPLIFTED)) { if
 * (action.equals(ICMSConstant.ACTION_LODGE_CUSTODIAN_DOC)) { return new
 * RelodgeCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_TEMP_UPLIFTED); }
 * 
 * if (status.equals(ICMSConstant.STATE_PENDING_RELODGE)) { //bernard - from
 * PENDING_RELODGE to LODGED if
 * (action.equals(ICMSConstant.ACTION_APPROVE_LODGE_CUSTODIAN_DOC)) { return new
 * ApproveRelodgeCustodianDocTrxOperation(); } if
 * (action.equals(ICMSConstant.ACTION_REJECT_CUSTODIAN_DOC)) { return new
 * RejectUpdateCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_PENDING_LODGE); }
 * 
 * if(action.equals(ICMSConstant.ACTION_CNCL_REJECT_CUSTODIAN_DOC)){
 * if(fromState.equals(ICMSConstant.STATE_PENDING_LODGE)){ return new
 * CnclRejectLodgeCustodianDocTrxOperation(); }
 * if(fromState.equals(ICMSConstant.STATE_PENDING_RELODGE)){ return new
 * CnclRejectReLodgeCustodianDocTrxOperation(); }
 * if(fromState.equals(ICMSConstant.STATE_PENDING_TEMP_UPLIFT)){ return new
 * CnclRejectTempUpliftCustodianDocTrxOperation(); }
 * if(fromState.equals(ICMSConstant.STATE_PENDING_PERM_UPLIFT)){ return new
 * CnclRejectPermUpliftCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_REJECTED); }
 * 
 * if(action.equals(ICMSConstant.ACTION_EDIT_REJECT_CUSTODIAN_DOC)){
 * if(fromState.equals(ICMSConstant.STATE_PENDING_LODGE)){ return new
 * EditRejectLodgeCustodianDocTrxOperation(); }
 * if(fromState.equals(ICMSConstant.STATE_PENDING_RELODGE)){ return new
 * EditRejectReLodgeCustodianDocTrxOperation(); }
 * if(fromState.equals(ICMSConstant.STATE_PENDING_TEMP_UPLIFT)){ return new
 * EditRejectTempUpliftCustodianDocTrxOperation(); }
 * if(fromState.equals(ICMSConstant.STATE_PENDING_PERM_UPLIFT)){ return new
 * EditRejectPermUpliftCustodianDocTrxOperation(); } throw new
 * TrxParameterException("Unknow Action: " + action + " with status: " +
 * ICMSConstant.STATE_REJECTED); } throw new
 * TrxParameterException("Status<*> does not match presets! No operations found!"
 * ); }
 */