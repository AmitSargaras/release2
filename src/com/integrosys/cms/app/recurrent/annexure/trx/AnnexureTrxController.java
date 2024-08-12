/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/RecurrentCheckListTrxController.java,v 1.12 2005/04/09 11:01:46 htli Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//ofa
import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Author: htli $
 * @version $Revision: 1.12 $
 * @since $Date: 2005/04/09 11:01:46 $ Tag: $Name: $
 */
public class AnnexureTrxController extends CMSTrxController {

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
	public AnnexureTrxController() {
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
		return ICMSConstant.INSTANCE_ANNEXURE_CHECKLIST;
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

			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateAnnexureOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_COPY_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CopyAnnexureOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateAnnexureOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_ANNEXURE_CHECKLIST)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemCreateAnnexureOperation");
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateAnnexureOperation");
			}
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectAnnexureOperation");
			}
			// ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemCreateAnnexureOperation");
		}
		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			/*if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_ANNEXURE_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedCreateAnnexureOperation");
				}
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerCloseRejectedUpdateAnnexureOperation");
			}*/

			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftSaveAnnexureOperation");
			}

			/*if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_ANNEXURE_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedCreateAnnexureOperation");
				}
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerEditRejectedUpdateAnnexureOperation");
			}*/
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_ANNEXURE_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedUpdateAnnexureReceiptOperation");
				}
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveAnnexureReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_ANNEXURE_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedUpdateAnnexureReceiptOperation");
				}
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateAnnexureOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateAnnexureReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveAnnexureReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveActiveAnnexureOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_DRAFT)) {
			// update receipt
			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveAnnexureReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateAnnexureReceiptOperation");
			}
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerCloseDraftUpdateAnnexureReceiptOperation");
			}

			if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftSaveAnnexureOperation");
			}

			/*if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_ANNEXURE_CHECKLIST)) {
				if (((IAnnexureTrxValue1) value).getCheckList() == null) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseDraftCreateAnnexureOperation");
				}
				else {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseDraftActiveAnnexureOperation");
				}
			}*/

			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_ANNEXURE_CHECKLIST)
					|| action.equals(ICMSConstant.ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST)) {
				if (((IRecurrentCheckListTrxValue) value).getCheckList() == null) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftCreateAnnexureOperation");
				}
				else {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerDraftUpdateAnnexureOperation");
				}
			}

			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			/*if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateAnnexureOperation");
			}*/
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveUpdateAnnexureReceiptOperation");
			}
			/*if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectAnnexureOperation");
			}*/
			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_ANNEXURE_CHECKLIST)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectAnnexureReceiptOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}