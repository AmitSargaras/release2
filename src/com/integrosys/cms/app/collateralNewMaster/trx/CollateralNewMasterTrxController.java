package com.integrosys.cms.app.collateralNewMaster.trx;

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
public class CollateralNewMasterTrxController extends CMSTrxController {

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
    public CollateralNewMasterTrxController() {
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
        return ICMSConstant.INSTANCE_COLLATERAL_NEW_MASTER;
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
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_NEW_MASTER)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateCollateralNewMasterOperation");

        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_NEW_MASTER)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCollateralNewMasterOperation");
        	}
        }
        }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCollateralNewMasterOperation");
            }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateCollateralNewMasterOperation");
            }
              if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateCollateralNewMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateCollateralNewMasterOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCollateralNewMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCollateralNewMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteCollateralNewMasterOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCollateralNewMasterOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCollateralNewMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCollateralNewMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCollateralNewMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_NEW_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCollateralNewMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_NEW_MASTER)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateCollateralNewMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateCollateralNewMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteCollateralNewMasterOperation");
                }
            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_NEW_MASTER)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateCollateralNewMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateCollateralNewMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteCollateralNewMasterOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_COLLATERAL_NEW_MASTER)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftCollateralNewMasterOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
