/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/ProfileTrxController.java,v 1.6 2005/01/13 08:31:26 whuang Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.6 $
 * @since $Date: 2005/01/13 08:31:26 $ Tag: $Name: $
 */
public class ProfileTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public ProfileTrxController() {
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
		return ICMSConstant.INSTANCE_COMMODITY_MAIN_PROFILE;
	}

	/**
	 * Returns an ITrxOperation object
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException on
	 *         error
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
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException on
	 *         error
	 */
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		DefaultLogger.debug(this, " >>>>>>:1 ITrxValue = " + AccessorUtil.printMethodValue(value));
		String action = param.getAction();
		DefaultLogger.debug(this, "Action: " + action);

		String status = value.getStatus();
		DefaultLogger.debug(this, "Status: " + status);

		if (action == null) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		if (status == null) {
			throw new TrxParameterException("From State is null!");
		}

		if (status.equals(ICMSConstant.STATE_ND)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
				return new MakerCreateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}

		else if (status.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
				return new MakerUpdateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN)) {
				return new MakerDeleteProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveCreateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectCreateProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}

		else if (status.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveUpdateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectUpdateProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN)) {
				return new CheckerApproveDeleteProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN)) {
				return new CheckerRejectDeleteProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedCreateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedCreateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedUpdateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedUpdateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN)) {
				return new MakerResubmitRejectedDeleteProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseRejectedDeleteProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN)) {
				return new MakerUpdateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN)) {
				return new MakerCreateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN)) {
				return new MakerDeleteProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN)) {
				return new MakerSaveProfileOperation();
			}
			// add CLOSE DRAFT functions by Huang Wei
			else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseDraftCreateProfileOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN)) {
				return new MakerCloseDraftUpdateProfileOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}

		else {
			throw new TrxParameterException(" -- No operations found! with  FROM_STATE : '" + status
					+ "' and Operation: '" + action + "'. "
					+ "\n Please check Transaction Matrix talbe for supported combinations. ");
		}

	}
}