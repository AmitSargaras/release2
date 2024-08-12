package com.integrosys.cms.app.baselmaster.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class BaselTrxController extends CMSTrxController{

	 private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}
	
	public BaselTrxController() {
        super();
    }
	
	public String getInstanceName() {
        return ICMSConstant.INSTANCE_BASEL;
    }
	
	 public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
	        ITrxOperation op = factoryOperation(value, param);
	        DefaultLogger.debug(this, "Returning Operation: " + op);
	        return op;
	    }
	 
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
	        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_BASEL)) {
	        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateBaselOperation");

	        	}
	        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BASEL)) {
	        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateBaselOperation");
	        	}
	        }
	        }
	            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveBaselOperation");
	            }
	            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateBaselOperation");
	            }
	              if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
	            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateBaselOperation");
	            }
	            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
	        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
	            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateBaselOperation");
	            }

	            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectBaselOperation");
	            }
	            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
	        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
	            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateBaselOperation");
	            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteBaselOperation");
	            }else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateBaselOperation");
	            }
	            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
	        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
	            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateBaselOperation");
	            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectBaselOperation");
	            }
	            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
	        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
	            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateBaselOperation");
	            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_BASEL)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectBaselOperation");
	            }
	            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
	        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
	            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_BASEL)) {
	                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateBaselOperation");
	                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateBaselOperation");
	                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteBaselOperation");
	                }
	            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BASEL)) {
	                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateBaselOperation");
	                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateBaselOperation");
	                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteBaselOperation");
	                }
	            }
	            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
	        }else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_BASEL)) {
	        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftBaselOperation");
	        }
	        throw new TrxParameterException("To State does not match presets! No operations found!");
	    }

}
