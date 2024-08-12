package com.integrosys.cms.app.geography.country.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class CountryTrxController extends CMSTrxController{

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COUNTRY;
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
        
        if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_COUNTRY)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateCountryOperation");
        }
        
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_COUNTRY)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateCountryOperation");
            else if(action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_COUNTRY)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateCountryOperation");
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if (toState.equals(ICMSConstant.ACTION_MAKER_DRAFT_COUNTRY)) {        	
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_COUNTRY)) 
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSavedCountryOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCountryOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COUNTRY)){
            	if (fromState.equals(ICMSConstant.STATE_PENDING_PERFECTION)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateCountryOperation");
                else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateCountryOperation");
                else
                  	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
            }
            else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY))
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCountryOperation");
            else
              	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if ((toState == null) || (toState.equals(ICMSConstant.STATE_PENDING_PERFECTION))) {
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateCountryOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateCountryOperation");
            }
            else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCountryOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCountryOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_DELETE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteCountryOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCountryOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCountryOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteCountryOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCountryOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_DELETED)) {        	
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerActivateDeletedCountryOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);           
        } 
        
        else if (toState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveActivateCountryOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCountryOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COUNTRY)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateCountryOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateCountryOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteCountryOperation");
                }
                //added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedInActivateCountryOperation");
                }
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COUNTRY)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateCountryOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateCountryOperation");
                } 
                else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteCountryOperation");
                }
              //added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateCountryOperation");
                }
            }  
            
          //added for InActive
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_INACTIVE_COUNTRY)) {
                	if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateCountryOperation");
                }      
            }
            
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_CLOSED)) {
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCountryOperation");
            }else if(action.equals(ICMSConstant.ACTION_MAKER_DELETE_COUNTRY)){
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteCountryOperation");
        	}
            // added 
            else if(action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_COUNTRY)){
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerActivateDeletedCountryOperation");
        	}
        	
        	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_COUNTRY)) {        	
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerActivateDeletedCountryOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);           
        } 
        
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
	
}
