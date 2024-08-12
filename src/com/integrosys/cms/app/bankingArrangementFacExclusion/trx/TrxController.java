package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class TrxController extends CMSTrxController{

	private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    public TrxController() {
    	super();
	}

    public String getInstanceName() {
        return ICMSConstant.INSTANCE_BANKING_ARRANGEMENT_FAC_EXCLUSION;
    }
    
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
        DefaultLogger.debug(this, "Returning Operation: " + op);
        return op;
    }
    
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String action = param.getAction();
		if (action == null) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		if (value == null) {
			throw new TrxParameterException("Value is null in ITrxParameter!");
		}

		DefaultLogger.debug(this, "Action: " + action);

		String toState = value.getToState();
		String fromState = value.getFromState();
		DefaultLogger.debug(this, "toState: " + value.getToState());
		if (toState != null) {
			if (toState.equals(ICMSConstant.STATE_DRAFT)) {
				if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateOperation");
				}
				if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOperation");
				}
			}
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveOperation");
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateOperation");
		}
		if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateOperation");
			}

			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOperation");
			} else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
			} else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteOperation");
				}
			} else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteOperation");
				}
			}
		} else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteOperation");
			} else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
				return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
			}
		} else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_BANKING_ARRANGEMENT_FAC_EXCLUSION)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftOperation");
		}
		throw new TrxParameterException("To State does not match presets! No operations found!");
	}
}
