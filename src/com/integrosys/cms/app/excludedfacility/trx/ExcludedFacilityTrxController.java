package com.integrosys.cms.app.excludedfacility.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class ExcludedFacilityTrxController extends CMSTrxController {

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
    public ExcludedFacilityTrxController() {
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
        return ICMSConstant.INSTANCE_EXCLUDED_FACILITY;
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
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_EXCLUDED_FACILITY)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateExcludedFacilityOperation");

        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_EXCLUDED_FACILITY)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateExcludedFacilityOperation");
        	}
        	}
        }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveExcludedFacilityOperation");
            }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateExcludedFacilityOperation");
            }
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateExcludedFacilityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateExcludedFacilityOperation");
            }

             if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectExcludedFacilityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateExcludedFacilityOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteExcludedFacilityOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_FACILITY_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateFacilityNewMasterOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateExcludedFacilityOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectExcludedFacilityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateExcludedFacilityOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_EXCLUDED_FACILITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectExcludedFacilityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_EXCLUDED_FACILITY)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateExcludedFacilityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateExcludedFacilityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteExcludedFacilityOperation");
                }
            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_EXCLUDED_FACILITY)) {
               if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateExcludedFacilityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateExcludedFacilityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteExcludedFacilityOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_EXCLUDED_FACILITY)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftExcludedFacilityOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
}
