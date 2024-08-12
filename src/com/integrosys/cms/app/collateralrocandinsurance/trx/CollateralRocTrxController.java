package com.integrosys.cms.app.collateralrocandinsurance.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class CollateralRocTrxController extends CMSTrxController{

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
    public CollateralRocTrxController() {
	}
    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table.
     * Not implemented.
     *
     * @return String
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_COLLATERAL_ROC;
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
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_ROC)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateCollateralRocOperation");

        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_ROC)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCollateralRocOperation");
        	}
        	}
        }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCollateralRocOperation");
            }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateCollateralRocOperation");
            }
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateCollateralRocOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateCollateralRocOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCollateralRocOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCollateralRocOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteCollateralRocOperation");
            }/*else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_FACILITY_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateFacilityNewMasterOperation");
            }*/
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCollateralRocOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCollateralRocOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCollateralRocOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_ROC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCollateralRocOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_ROC)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateCollateralRocOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateCollateralRocOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteCollateralRocOperation");
                }
            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_ROC)) {
               if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateCollateralRocOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateCollateralRocOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteCollateralRocOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_COLLATERAL_ROC)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftCollateralRocOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
}
