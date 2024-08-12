package com.integrosys.cms.app.newtatmaster.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class TatMasterTrxController extends CMSTrxController {
	
	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_TAT_MASTER;
	}

	
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param)
			throws TrxParameterException {
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
		  
		  if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
	            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_TAT_MASTER)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateTatMasterOperation");
	            }
	            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
	        }  else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
	            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_TAT_MASTER)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateTatMasterOperation");
	            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_TAT_MASTER)) {
	                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectTatMasterOperation");
	            }
	            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
	        }else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
	            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_TAT_MASTER)) {
	                if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateTatMasterOperation");
	                } 
	            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_TAT_MASTER)) {
	                 if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
	                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateTatMasterOperation");
	                } 
	            }
	            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
	        }
	  }
	        throw new TrxParameterException("To State does not match presets! No operations found!");
	  }

}
