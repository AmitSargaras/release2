package com.integrosys.cms.app.geography.city.trx;

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

public class CityTrxController extends CMSTrxController{

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CITY;
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
        
        if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_CITY)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateCityOperation");
        }
        	
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_CITY)) 
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateCityOperation");
            else if(action!= null && action.equals(ICMSConstant.ACTION_MAKER_SAVE_CITY)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreateCityOperation");
            else
              	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
      
        else if (toState.equals(ICMSConstant.ACTION_MAKER_DRAFT_CITY)) {        	
        	if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CREATE_CITY)) 
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSavedCityOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CITY)) 
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCityOperation");
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CITY)){
            	if (fromState.equals(ICMSConstant.STATE_PENDING_PERFECTION)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateCityOperation");
                else if (fromState.equals(ICMSConstant.STATE_ACTIVE)) 
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateCityOperation");
                else
                  	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
            }
            else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CITY))
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCityOperation");
            else
              	throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateCityOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCityOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_DELETE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteCityOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCityOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteCityOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.STATE_DELETED)) {        	
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerActivateDeletedCityOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);           
        } else if (toState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveActivateCityOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        
        else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CITY)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateCityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateCityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteCityOperation");
                }
                //	added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedInActivateCityOperation");
                }
            } else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CITY)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateCityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateCityOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteCityOperation");
                }
                //added for InActive
                else if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateCityOperation");
                }
            }  
            //added for InActive
            else if (action!= null && action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_INACTIVE_CITY)) {
            	if (fromState.equals(ICMSConstant.ACTION_PENDING_ACTIVATE)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInActivateCityOperation");
            }   
        }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_PENDING_PERFECTION)) {
            if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateCityOperation");
            } else if (action!= null && action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectCityOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
	
}
