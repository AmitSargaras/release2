package com.integrosys.cms.app.recurrent.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 *
 * @author $Author: hshii $
 * @version $Revision: 1.23 $
 * @since $Date: 2005/07/08 11:40:37 $ Tag: $Name: $
 */
public class RecurrentTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public RecurrentTrxControllerFactory() {
		super();
	}

	/**
	 * Returns an ITrxController given the ITrxValue and ITrxParameter objects
	 *
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxController
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException if any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		if (isRecurrentCheckListRelated(param.getAction())) {
			if (isReadOperation(param.getAction())) {
				return new RecurrentCheckListReadController();
			}
			return new RecurrentCheckListTrxController();
		}
		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}


	/**
	 * To check if the action is a checklist related one
	 * @param anAction - String
	 * @return boolean - true if it is checklist related and false otherwise
	 */
	private boolean isRecurrentCheckListRelated(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_MAKER_CREATE_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_COPY_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST_ID))) {
			return true;
		}
		return false;
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST_ID))) {
			return true;
		}
		return false;
	}
}
