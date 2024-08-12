package com.integrosys.cms.app.geography.state.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class StateTrxController extends CMSTrxController{

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_STATE;
	}

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
        
        if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_STATE)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateStateOperation");
        }
        
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_STATE))
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateStateOperation");
            else if(action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_STATE)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateStateOperation");
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if (toState.equals(ICMSConstant.ACTION_MAKER_DRAFT_STATE)) {        	
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_STATE)) 
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSavedStateOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_STATE)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateStateOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STATE)){
            	if (fromState.equals(ICMSConstant.STATE_PENDING_PERFECTION)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateStateOperation");
                else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateStateOperation");
                else
                  	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
            }
            else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_STATE))
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectStateOperation");
            else
              	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if ((toState == null) || (toState.equals(ICMSConstant.STATE_PENDING_PERFECTION))) {
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateStateOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateStateOperation");
            }

            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectStateOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateStateOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_DELETE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteStateOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateStateOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectStateOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteStateOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectStateOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_DELETED)) {        	
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerActivateDeletedStateOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);           
        }
        else if (toState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveActivateStateOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectStateOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_STATE)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateStateOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateStateOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteStateOperation");
                }
                //	added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedInActivateStateOperation");
                }
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STATE)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateStateOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateStateOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteStateOperation");
                }
              //added for InActive 
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) { 
             	   return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateStateOperation");
                } 
            }
            //added for InActive
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_INACTIVE_STATE)) {
            	if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateStateOperation");
            	}
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_CLOSED)) {
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_STATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateStateOperation");
            }else if(action.equals(ICMSConstant.ACTION_MAKER_DELETE_STATE)){
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteStateOperation");
        	}
        	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
	
}
