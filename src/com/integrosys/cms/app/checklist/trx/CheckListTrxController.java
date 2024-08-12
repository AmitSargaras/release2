/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckListTrxController.java,v 1.18 2005/07/08 11:40:37 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
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
 * @author $Author: hshii $
 * @version $Revision: 1.18 $
 * @since $Date: 2005/07/08 11:40:37 $ Tag: $Name: $
 */
public class CheckListTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CheckListTrxController() {
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
		return ICMSConstant.INSTANCE_CHECKLIST;
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
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CHECKLIST)) {
				return new MakerCreateCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_COPY_CHECKLIST)) {
				return new CopyCheckListOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (action.equals(ICMSConstant.ACTION_SYSTEM_CLOSE_CHECKLIST)) {
			return new SystemCloseCheckListOperation();
		}

        if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST)) {
           return new SystemCreateCheckListOperation();
        }

        if (action.equals(ICMSConstant.ACTION_SYSTEM_CREATE_DOCUMENT_CHECKLIST)) {
            return new SystemCreateDocumentCheckListOperation();
        }

		if (action.equals(ICMSConstant.ACTION_SYSTEM_UPDATE_CHECKLIST)) {
			return new SystemUpdateCheckListOperation();
		}

		if (action.equals(ICMSConstant.ACTION_DIRECT_UPDATE_CHECKLIST)) {
			return new DirectUpdateCheckListOperation();
		}

		if (action.equals(ICMSConstant.ACTION_COPY_CHECKLIST)) {
			return new CopyCheckListOperation();
		}

		if (action.equals(ICMSConstant.ACTION_SYSTEM_OBSOLETE_CHECKLIST)) {
			return new SystemObsoleteCheckListOperation();
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST)) {
				return new CheckerApproveCreateCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST)) {
				return new CheckerRejectCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST)) {
				return new SystemDeleteCheckListOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_REJECTED) || toState.equals(ICMSConstant.STATE_OFFICER_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCreateCheckListOperation();
				}
				return new MakerCloseRejectedUpdateCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CHECKLIST_RECEIPT)) {
				return new MakerCloseRejectedUpdateCheckListReceiptOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CHECKLIST)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerEditRejectedCreateCheckListOperation();
				}
				return new MakerEditRejectedUpdateCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CHECKLIST_RECEIPT)) {
				return new MakerEditRejectedUpdateCheckListReceiptOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CHECKLIST)) {
				return new MakerSaveCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST)) {
				return new SystemDeleteCheckListOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}
		if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CHECKLIST)) {
				return new MakerUpdateCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CHECKLIST_RECEIPT)) {
				return new MakerUpdateCheckListReceiptOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CHECKLIST)) {
				return new MakerSaveCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST)) {
				return new SystemDeleteCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_GENERATE_CHECKLIST_WAIVER)) {
				return new SystemGenerateWaiverRequestCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_GENERATE_CHECKLIST_DEFERRAL)) {
				return new SystemGenerateDeferralRequestCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_UPDATE_CHECKLIST_RECEIPT)) {
				return new CheckerUpdateCheckListReceiptOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_DRAFT)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CHECKLIST_RECEIPT)) {
				return new MakerUpdateCheckListReceiptOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CHECKLIST)) {
				return new MakerSaveCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_SAVE_CHECKLIST)) {
				return new MakerCancelSaveCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST)) {
				return new SystemDeleteCheckListOperation();
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST)) {
				return new CheckerApproveUpdateCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST_RECEIPT)) {
				return new CheckerApproveUpdateCheckListReceiptOperation();
			}

			else if (action.equals(ICMSConstant.ACTION_CHECKER_VERIFY_CHECKLIST_RECEIPT)) {
				return new CheckerVerifyUpdateCheckListReceiptOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST)) {
				return new CheckerRejectCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST_RECEIPT)) {
				return new CheckerRejectCheckListReceiptOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST)) {
				return new SystemDeleteCheckListOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ)) {
			if (action.equals(ICMSConstant.ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_WAIVER)) {
				return new SystemApproveGenerateWaiverRequestCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_WAIVER)) {
				return new SystemRejectGenerateWaiverRequestCheckListOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ)) {
			if (action.equals(ICMSConstant.ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_DEFERRAL)) {
				return new SystemApproveGenerateDeferralRequestCheckListOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_DEFERRAL)) {
				return new SystemRejectGenerateDeferralRequestCheckListOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}

		if (toState.equals(ICMSConstant.STATE_PENDING_MGR_VERIFY)) {
			if (action.equals(ICMSConstant.ACTION_MANAGER_VERIFY_CHECKLIST_RECEIPT)) {
				return new ManagerApproveUpdateCheckListReceiptOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MANAGER_REJECT_CHECKLIST_RECEIPT)) {
				return new ManagerRejectCheckListReceiptOperation();
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}