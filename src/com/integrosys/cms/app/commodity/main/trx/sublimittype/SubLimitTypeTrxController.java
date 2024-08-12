/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/SubLimitTypeTrxController.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

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
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      SubLimitTypeTrxController.java
 */
public class SubLimitTypeTrxController extends CMSTrxController {

	/*
	 * @see
	 * com.integrosys.cms.app.transaction.CMSTrxController#getOperation(com.
	 * integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		DefaultLogger.debug(this, "getOperation");
		validParm(value, param);
		String action = param.getAction();
		String status = value.getStatus();
		DefaultLogger.debug(this, "Action : " + action);
		DefaultLogger.debug(this, "Status : " + status);
		return getOperation(status, action);
	}

	/*
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxController#
	 * getInstanceName()
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMODITY_MAIN_SUBLIMITTYPE;
	}

	private void validParm(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (value == null) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		String status = value.getStatus();
		if (status == null) {
			throw new TrxParameterException("From State is null!");
		}
		if (param == null) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();
		if (action == null) {
			throw new TrxParameterException("Action para is null!");
		}
	}

	private ITrxOperation getOperation(String status, String action) throws TrxParameterException {
		if (status.equals(ICMSConstant.STATE_ND)) {
			return getNDStateOperation(action);
		}
		if (status.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			return getPendingCreateStateOperation(action);
		}
		if (status.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			return getPendingUpdateStateOperation(action);
		}
		if (status.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			return getPendingDeleteStateOperation(action);
		}
		if (status.equals(ICMSConstant.STATE_REJECTED)) {
			return getRejectStateOperation(action);
		}
		if (status.equals(ICMSConstant.STATE_ACTIVE)) {
			return getActiveStateOperation(action);
		}
		if (status.equals(ICMSConstant.STATE_DRAFT)) {
			return getDraftStateOperation(action);
		}
		throw new TrxParameterException(" -- No operations found! with  FROM_STATE : '" + status + "' and Operation: '"
				+ action + "'. " + "\n Please check Transaction Matrix talbe for supported combinations. ");

	}

	private ITrxOperation getNDStateOperation(String action) throws TrxParameterException {
		if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
			return new MakerCreateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
			return new MakerSaveSLTOperation();
		}
		throw new TrxParameterException("Unknow Action: " + action + " with status: ND");
	}

	private ITrxOperation getPendingCreateStateOperation(String action) throws TrxParameterException {
		if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN)) {
			return new CheckerApproveCreateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN)) {
			return new CheckerRejectCreateSLTOperation();
		}
		throw new TrxParameterException("Unknow Action: " + action + " with status: PENDINGCREATE");
	}

	private ITrxOperation getPendingUpdateStateOperation(String action) throws TrxParameterException {
		if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN)) {
			return new CheckerApproveUpdateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN)) {
			return new CheckerRejectUpdateSLTOperation();
		}
		throw new TrxParameterException("Unknow Action: " + action + " with status: PENGDINGUPDATE");
	}

	private ITrxOperation getPendingDeleteStateOperation(String action) throws TrxParameterException {
		if (action.equals(ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN)) {
			return new CheckerApproveDeleteSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN)) {
			return new CheckerRejectDeleteSLTOperation();
		}
		throw new TrxParameterException("Unknow Action: " + action + " with status: PENDINGDELETE.");
	}

	private ITrxOperation getRejectStateOperation(String action) throws TrxParameterException {
		if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN)) {
			return new MakerResubmitRejectedCreateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
			return new MakerCloseRejectedCreateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN)) {
			return new MakerResubmitRejectedUpdateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
			return new MakerCloseRejectedUpdateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN)) {
			return new MakerResubmitRejectedDeleteSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN)) {
			return new MakerCloseRejectedDeleteSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
			return new MakerSaveSLTOperation();
		}
		throw new TrxParameterException("Unknow Action: " + action + " with status: REJECT.");

	}

	private ITrxOperation getActiveStateOperation(String action) throws TrxParameterException {
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
			return new MakerSaveSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
			return new MakerUpdateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN)) {
			return new MakerDeleteSLTOperation();
		}
		throw new TrxParameterException("Unknow Action: " + action + " with status: ACTIVE.");

	}

	private ITrxOperation getDraftStateOperation(String action) throws TrxParameterException {
		if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
			return new MakerCreateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
			return new MakerUpdateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
			return new MakerSaveSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
			return new MakerCloseDraftCreateSLTOperation();
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
			return new MakerCloseDraftUpdateSLTOperation();
		}
		// if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN)) {
		// return new MakerDeleteSLTOperation();
		// }
		throw new TrxParameterException("Unknow Action: " + action + " with status: DRAFT.");
	}
}
