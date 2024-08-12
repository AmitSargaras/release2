package com.integrosys.cms.app.systemBankBranch.bus;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author abhijit.rudrakshawar
 *  System Bank Trx controller to manage trx operations
 */
public class SystemBankBranchTrxController extends CMSTrxController {

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
    public SystemBankBranchTrxController() {
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
        return ICMSConstant.INSTANCE_SYSTEM_BANK_BRANCH;
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
        if (action==null) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }
        if (value==null) {
            throw new TrxParameterException("Value is null in ITrxParameter!");
        }
        
        DefaultLogger.debug(this, "Action: " + action);

        String toState = value.getToState();
        String fromState = value.getFromState();
        DefaultLogger.debug(this, "toState: " + value.getToState());
        
        if(toState!=null){
        if(toState.equals(ICMSConstant.STATE_DRAFT)){
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_SYSTEM_BANK_BRANCH)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateSystemBankBranchOperation");

        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SYSTEM_BANK_BRANCH)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSystemBankBranchOperation");
        	}
        }
        }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveSystemBankBranchOperation");
            }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateSystemBankBranchOperation");
            }
              if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSystemBankBranchOperation");
            }
            if (action.equals(ICMSConstant.ACTION_MAKER_FILE_INSERT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSystemBankBranchInsertFileOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateSystemBankBranchOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectSystemBankBranchOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSystemBankBranchOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteSystemBankBranchOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSystemBankBranchOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateSystemBankBranchOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectSystemBankBranchOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateSystemBankBranchOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectSystemBankBranchOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SYSTEM_BANK_BRANCH)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateSystemBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateSystemBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteSystemBankBranchOperation");
                }
            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_SYSTEM_BANK_BRANCH)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateSystemBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateSystemBankBranchOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteSystemBankBranchOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_SYSTEM_BANK_BRANCH)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftSystemBankBranchOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
