package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * Author: Priya
 * Author: KC Chin
 * Date: Oct 9, 2009
 */
public class ProductLimitParameterTrxController extends CMSTrxController {
	
	private static final long serialVersionUID = 1L;
    private Map nameTrxOperationMap;

    public ProductLimitParameterTrxController() {
        super();
    }

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    public String getInstanceName() {
        return ICMSConstant.INSTANCE_PRODUCT_LIMIT_PARAMETER;
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
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_PRODUCT_LIMIT_PARAMETER) ) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateProductLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_ACTIVE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateProductLimitParameterOperation");
            }
            else if(action.equals(ICMSConstant.ACTION_MAKER_DELETE_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteProductLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if ( fromState.equals(ICMSConstant.STATE_PENDING_CREATE) || 
        		fromState.equals(ICMSConstant.STATE_PENDING_UPDATE) ||
        		fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveProductLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectProductLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_PRODUCT_LIMIT_PARAMETER)) {
                 return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateProductLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_CREATE_PRODUCT_LIMIT_PARAMETER)) {
                 return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseCreateProductLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseUpdateProductLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateProductLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " + action + " with fromState: " + fromState);
            }
        } else if (fromState.equals(ICMSConstant.STATE_REJECTED_DELETE)) {
        	if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDeleteProductLimitParameterOperation");
        	}
        	else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_PRODUCT_LIMIT_PARAMETER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteProductLimitParameterOperation");
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