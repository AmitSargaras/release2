/*
 * Created on Feb 14, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MILmtTransactionController extends CMSTrxController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperationFactory#getOperation
	 * (com.integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		// TODO Auto-generated method stub
		ITrxOperation op = factoryOperation(value, param);
		return op;
	}

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

		// this is ND actually, but idiot framework will set status to ACTIVE
		if (ICMSConstant.STATE_ND.equals(status)) {
			if (TransactionActionConst.ACTION_MANUAL_CREATE_LMT.equals(action)) {
				return new MIMakerCreateLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_SAVE_LMT.equals(action)) {
				return new MIMakerSaveNewLmtOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (ICMSConstant.STATE_PENDING_UPDATE.equals(status)||ICMSConstant.STATE_PENDING_CREATE.equals(status)) {
			if (TransactionActionConst.ACTION_MANUAL_APPROVE_LMT.equals(action)) {
				return new MICheckerApproveLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_REJECT_LMT.equals(action)) {
				return new MICheckerRejectLmtOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (ICMSConstant.STATE_DRAFT.equals(status)) {
			if (TransactionActionConst.ACTION_MANUAL_CLOSE_LMT.equals(action)) {
				return new MIMakerCancelLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_UPDATE_LMT.equals(action)) {
				return new MIMakerUpdateLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_SAVE_LMT.equals(action)) {
				return new MIMakerSaveExistingLmtOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (ICMSConstant.STATE_REJECTED.equals(status)) {
			if (TransactionActionConst.ACTION_MANUAL_CLOSE_LMT.equals(action)) {
				return new MIMakerCancelLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_UPDATE_LMT.equals(action)) {
				return new MIMakerUpdateLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_SAVE_LMT.equals(action)) {
				return new MIMakerSaveExistingLmtOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (ICMSConstant.STATE_ACTIVE.equals(status)) {
			if (TransactionActionConst.ACTION_MANUAL_UPDATE_LMT.equals(action)) {
				return new MIMakerUpdateLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_SAVE_LMT.equals(action)) {
				return new MIMakerSaveExistingLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_DELETE_LMT.equals(action)) {
				return new MIMakerDeleteLmtOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (ICMSConstant.STATE_PENDING_DELETE.equals(status)) {
			if (TransactionActionConst.ACTION_MANUAL_APPROVE_LMT.equals(action)) {
				return new MICheckerApproveLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_REJECT_LMT.equals(action)) {
				return new MICheckerRejectLmtOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (ICMSConstant.STATE_REJECTED_DELETE.equals(status)) {
			if (TransactionActionConst.ACTION_MANUAL_CLOSE_LMT.equals(action)) {
				return new MIMakerCancelLmtOperation();
			}
			else if (TransactionActionConst.ACTION_MANUAL_DELETE_LMT.equals(action)) {
				return new MIMakerDeleteLmtOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else {
			throw new TrxParameterException("From State does not match presets! No operations found!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxController#getInstanceName()
	 */
	public String getInstanceName() {
		// TODO Auto-generated method stub
		return ICMSConstant.INSTANCE_LIMIT;
	}

}
