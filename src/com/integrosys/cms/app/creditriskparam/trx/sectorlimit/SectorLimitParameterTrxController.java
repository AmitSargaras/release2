package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * Author: Syukri
 * Date: Jun 4, 2008
 */
public class SectorLimitParameterTrxController extends CMSTrxController {
	
	private static final long serialVersionUID = 1L;

    private Map nameTrxOperationMap;

    public SectorLimitParameterTrxController() {
        super();
    }

    public String getInstanceName() {
        return ICMSConstant.INSTANCE_SECTOR_LIMIT_PARAMETER;
    }

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug (this, "Returning Operation: " + op);
        return op;
    }

    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        if (param == null)
            throw new TrxParameterException("ITrxParameter is NULL");

        String fromState = value.getStatus();
        DefaultLogger.debug (this, "FromState/Status: " + fromState);
		String toState = value.getToState();
		String fromState1 = value.getFromState();
		DefaultLogger.debug(this, "ToState: " + toState);
		DefaultLogger.debug(this, "FromState: " + fromState1);

        if (fromState == null) {
            throw new TrxParameterException ("From State is null!");
        }

        String action = param.getAction();
        DefaultLogger.debug (this, "Action: " + action);

        if (action == null) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }

        if (fromState.equals (ICMSConstant.STATE_ND)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_SECTOR_LIMIT_PARAMETER) ) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSectorLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_ACTIVE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSectorLimitParameterOperation");
            }
            else if(action.equals(ICMSConstant.ACTION_MAKER_DELETE_SECTOR_LIMIT_PARAMETER))  {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteSectorLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if ( fromState.equals(ICMSConstant.STATE_PENDING_CREATE) || 
        		fromState.equals(ICMSConstant.STATE_PENDING_UPDATE) ||
        		fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveSectorLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectSectorLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSectorLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_CREATE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseCreateSectorLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseUpdateSectorLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSectorLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        } else if (fromState.equals(ICMSConstant.STATE_REJECTED_DELETE)) {
        	if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDeleteSectorLimitParameterOperation");
        	}
        	else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_SECTOR_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteSectorLimitParameterOperation");
        	} 
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }        	
        }
        else {
			throw new TrxParameterException ("From State does not match presets! No operations found!");
		}
    }
}