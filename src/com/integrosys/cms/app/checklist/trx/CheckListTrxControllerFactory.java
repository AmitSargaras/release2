/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckListTrxControllerFactory.java,v 1.23 2005/07/08 11:40:37 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.23 $
 * @since $Date: 2005/07/08 11:40:37 $ Tag: $Name: $
 */
public class CheckListTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public CheckListTrxControllerFactory() {
		super();
	}

	/**
	 * Returns an ITrxController given the ITrxValue and ITrxParameter objects
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxController
	 * @throws TrxParameterException if any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		if (isCheckListRelated(param.getAction())) {
			if (isReadOperation(param.getAction())) {
				return new CheckListReadController();
			}
			return new CheckListTrxController();
		}
		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

    /**
	 * To check if the action is a checklist related one
	 * @param anAction - String
	 * @return boolean - true if it is checklist related and false otherwise
	 */
	private boolean isCheckListRelated(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_MAKER_CREATE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_COPY_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_UPDATE_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_UPDATE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_UPDATE_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_SAVE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_MAKER_CANCEL_SAVE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_CREATE_DOCUMENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_DIRECT_UPDATE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_GENERATE_CHECKLIST_WAIVER))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_WAIVER))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_WAIVER))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_GENERATE_CHECKLIST_DEFERRAL))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_DEFERRAL))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_DEFERRAL))
				|| (anAction.equals(ICMSConstant.ACTION_SYSTEM_OBSOLETE_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_CHECKER_VERIFY_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MANAGER_VERIFY_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_MANAGER_REJECT_CHECKLIST_RECEIPT))
				|| (anAction.equals(ICMSConstant.ACTION_READ_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_READ_CHECKLIST_ID))) {
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
		if ((anAction.equals(ICMSConstant.ACTION_READ_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_READ_CHECKLIST_ID))
				|| (anAction.equals(ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST))
				|| (anAction.equals(ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST_ID))) {
			return true;
		}
		return false;
	}
}