package com.integrosys.cms.app.caseCreationUpdate.trx;

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
 *  CaseCreation Trx controller to manage trx operations
 */
public class CaseCreationTrxController extends CMSTrxController {

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
    public CaseCreationTrxController() {
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
        return ICMSConstant.INSTANCE_CASECREATION;
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
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CASECREATION)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateCaseCreationOperation");

        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASECREATION)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCaseCreationOperation");
        	}
        	
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASECREATION_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCaseCreationBranchOperation");
            }
        }
        }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCaseCreationOperation");
            }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateCaseCreationOperation");
            }
              if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateCaseCreationOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateCaseCreationOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCaseCreationOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCaseCreationOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteCaseCreationOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCaseCreationOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CASECREATION_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCaseCreationBranchOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCaseCreationOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCaseCreationOperation");
            }else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CASECREATION_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCaseCreationBranchOperation");
            } 
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCaseCreationOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CASECREATION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCaseCreationOperation");
            }else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CASECREATION_BRANCH)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCaseCreationBranchOperation");
            } 
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CASECREATION)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateCaseCreationOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateCaseCreationOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteCaseCreationOperation");
                }
            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CASECREATION)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateCaseCreationOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateCaseCreationOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteCaseCreationOperation");
                }
            }else if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CASECREATION_BRANCH)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateCaseCreationOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateCaseCreationOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteCaseCreationOperation");
                }
            } 
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CASECREATION)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftCaseCreationOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
