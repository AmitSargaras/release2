/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/RecurrentCheckListTrxController.java,v 1.12 2005/04/09 11:01:46 htli Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//ofa
import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Author: htli $
 * @version $Revision: 1.12 $
 * @since $Date: 2005/04/09 11:01:46 $ Tag: $Name: $
 */
public class RecurrentCheckListTrxController extends CMSTrxController {

	private static final long serialVersionUID = 8222663898353501227L;

	private Map nameTrxOperationMap;

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	/**
	 * Default Constructor
	 */
	public RecurrentCheckListTrxController() {
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
		return ICMSConstant.INSTANCE_RECURRENT_CHECKLIST;
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

			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateRecurrentCheckListOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_COPY_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CopyRecurrentCheckListOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateRecurrentCheckListOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_RECURRENT_CHECKLIST)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemCreateRecurrentCheckListOperation");
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateRecurrentCheckListOperation");
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRecurrentCheckListOperation");
			}
			// ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST_RECEIPT
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemCreateRecurrentCheckListOperation");
		}
		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedCreateRecurrentCheckListOperation");
				}
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerCloseRejectedUpdateRecurrentCheckListOperation");
			}

			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftSaveRecurrentCheckListOperation");
			}

			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedCreateRecurrentCheckListOperation");
				}
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerEditRejectedUpdateRecurrentCheckListOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST_RECEIPT)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedUpdateRecurrentCheckListReceiptOperation");
				}
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveRecurrentCheckListReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST_RECEIPT)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedUpdateRecurrentCheckListReceiptOperation");
				}
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRecurrentCheckListOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRecurrentCheckListReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveRecurrentCheckListReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveActiveRecurrentCheckListOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_DRAFT)) {
			// update receipt
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveRecurrentCheckListReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRecurrentCheckListReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerCloseDraftUpdateRecurrentCheckListReceiptOperation");
			}

			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftSaveRecurrentCheckListOperation");
			}

			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST)) {
				if (((IRecurrentCheckListTrxValue) value).getCheckList() == null) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseDraftCreateRecurrentCheckListOperation");
				}
				else {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseDraftActiveRecurrentCheckListOperation");
				}
			}

			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST)
					|| action.equals(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST)) {
				if (((IRecurrentCheckListTrxValue) value).getCheckList() == null) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftCreateRecurrentCheckListOperation");
				}
				else {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftUpdateRecurrentCheckListOperation");
				}
			}

			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateRecurrentCheckListOperation");
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveUpdateRecurrentCheckListReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRecurrentCheckListOperation");
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST_RECEIPT)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRecurrentCheckListReceiptOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}