package com.integrosys.cms.app.discrepency.trx;

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
 * @since 01-06-2011
 */

public class DiscrepencyTrxController extends CMSTrxController{

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_DISCREPENCY;
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
        
        if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_DISCREPENCY)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateDiscrepencyOperation");
        }
        	
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_DISCREPENCY)) 
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateDiscrepencyOperation");
            else if(action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_DISCREPENCY)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateDiscrepencyOperation");
            else
              	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
      
        else if (toState.equals(ICMSConstant.ACTION_MAKER_DRAFT_DISCREPENCY)) {        	
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_DISCREPENCY)) 
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSavedDiscrepencyOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DISCREPENCY)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDiscrepencyOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DISCREPENCY)){
            	if (fromState.equals(ICMSConstant.STATE_PENDING_PERFECTION)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateDiscrepencyOperation");
                else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateDiscrepencyOperation");
                else
                  	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
            }
            else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY))
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDiscrepencyOperation");
            else
              	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateDiscrepencyOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDiscrepencyOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDiscrepencyOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_DELETE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteDiscrepencyOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateDiscrepencyOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDiscrepencyOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteDiscrepencyOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDiscrepencyOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.STATE_DELETED)) {        	
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerActivateDeletedDiscrepencyOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);           
        } else if (toState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveActivateDiscrepencyOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDiscrepencyOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DISCREPENCY)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateDiscrepencyOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateDiscrepencyOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteDiscrepencyOperation");
                }
                //	added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedInActivateDiscrepencyOperation");
                }
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DISCREPENCY)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateDiscrepencyOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateDiscrepencyOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteDiscrepencyOperation");
                }
                //added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateDiscrepencyOperation");
                }
            }  
            //added for InActive
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_INACTIVE_DISCREPENCY)) {
            	if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateDiscrepencyOperation");
            }   
        }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_PENDING_PERFECTION)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateDiscrepencyOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDiscrepencyOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
	
}
