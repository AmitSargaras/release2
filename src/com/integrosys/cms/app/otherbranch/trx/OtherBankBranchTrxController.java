package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * @author dattatray.thorat
 *  Other Bank Branch Trx controller to manage trx operations
 */
public class OtherBankBranchTrxController extends CMSTrxController {

    private Map nameTrxOperationMap;

    /**
     * @return &lt;name, ITrxOperation&gt; pair map to be injected, name and
     *         ITrxOperation name will be the same.
     */
    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    /**
     * Default Constructor
     */
    public OtherBankBranchTrxController() {
        super();
    }

    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table.
     * Not implemented.
     *
     * @return String
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_OTHER_BANK_BRANCH;
    }

    /**
     * Returns an ITrxOperation object
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException
     *          on error
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
        DefaultLogger.debug(this, "Returning Operation: " + op);
        return op;
    }

    /**
     * Helper method to factory the operations
     *
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
        
        if (toState != null) {
			if (toState.equals(ICMSConstant.STATE_DRAFT)) {
				if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_OTHER_BANK_BRANCH)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitSaveCreateOtherBankBranchOperation");
				}
				if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_OTHER_BANK_BRANCH)) {
					return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitSaveUpdateOtherBankBranchOperation");
				}
			}
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_OTHER_BANK_BRANCH)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateOtherBankBranchOperation");
        }

        if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_OTHER_BANK_BRANCH)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftCreateOtherBankBranchOperation");
        }
        
        if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_OTHER_BANK_BRANCH)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOtherBankBranchOperation");
        }
        
        
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateOtherBankBranchOperation");
            }
            
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_CREATE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateOtherBankBranchOperation");
            }
            
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateOtherBankBranchOperation");
            }

            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOtherBankBranchOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOtherBankBranchOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_DELETE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteOtherBankBranchOperation");
            }
            
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateOtherBankBranchOperation");
            }
            
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOtherBankBranchOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOtherBankBranchOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteOtherBankBranchOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOtherBankBranchOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_OTHER_BANK_BRANCH)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateOtherBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateOtherBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteOtherBankBranchOperation");
                }
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_OTHER_BANK_BRANCH)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateOtherBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateOtherBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteOtherBankBranchOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_CLOSED)) {
        	if(action!= null && action.equals(ICMSConstant.ACTION_MAKER_DELETE_OTHER_BANK_BRANCH)){
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteOtherBankBranchOperation");
        	} else  if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_OTHER_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOtherBankBranchOperation");
            } 
        	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
