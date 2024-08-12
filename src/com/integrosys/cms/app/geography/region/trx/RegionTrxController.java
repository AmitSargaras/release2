package com.integrosys.cms.app.geography.region.trx;

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

public class RegionTrxController extends CMSTrxController{

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_REGION;
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
        
        if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_REGION)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateRegionOperation");
        }
        
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_REGION)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateRegionOperation");
            else if(action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_REGION)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateRegionOperation");
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if (toState.equals(ICMSConstant.ACTION_MAKER_DRAFT_REGION)) {        	
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_REGION)) 
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSavedRegionOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_REGION)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRegionOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_REGION)){
            	if (fromState.equals(ICMSConstant.STATE_PENDING_PERFECTION)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateRegionOperation");
                else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateRegionOperation");
                else
                  	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
            }
            else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_REGION))
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRegionOperation");
            else
              	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if ((toState == null) || (toState.equals(ICMSConstant.STATE_PENDING_PERFECTION))) {
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateRegionOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateRegionOperation");
            }

            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRegionOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRegionOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_DELETE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteRegionOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateRegionOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRegionOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteRegionOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRegionOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_DELETED)) {        	
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerActivateDeletedRegionOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);           
        }
        else if (toState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveActivateRegionOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectRegionOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_REGION)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateRegionOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateRegionOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteRegionOperation");
                }
                //	added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedInActivateRegionOperation");
                }
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_REGION)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateRegionOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateRegionOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteRegionOperation");
                }
              //added for InActive 
               else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) { 
            	   return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateRegionOperation");
               } 
            }
            //added for InActive
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_INACTIVE_REGION)) {
            	if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateRegionOperation");
            	}
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_CLOSED)) {
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_REGION)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRegionOperation");
            }else if(action.equals(ICMSConstant.ACTION_MAKER_DELETE_REGION)){
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteRegionOperation");
        	}
        	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
	
}
