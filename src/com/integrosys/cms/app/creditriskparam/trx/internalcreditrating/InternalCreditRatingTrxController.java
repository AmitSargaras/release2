/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * @author priya
 *
 */
public class InternalCreditRatingTrxController extends CMSTrxController {

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    private static final long serialVersionUID = 1L;
	
	public String getInstanceName() {
        return ICMSConstant.INSTANCE_INTERNAL_CREDIT_RATING;
    }

    public ITrxOperation getOperation (ITrxValue value, ITrxParameter param) throws TrxParameterException {
    	
        ITrxOperation op = factoryOperation (value, param);
        DefaultLogger.debug (this, "Returning Operation: " + op);
        return op;
        
    }

    private ITrxOperation factoryOperation (ITrxValue value, ITrxParameter param) throws TrxParameterException {
    	
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
        	if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_INTERNAL_CREDIT_RATING) ) {
//                return new MakerCreateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateInternalCreditRatingOperation");                
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_ACTIVE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_CREDIT_RATING)) {
//                return new MakerUpdateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateInternalCreditRatingOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }        
        else if (fromState.equals (ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_INTERNAL_CREDIT_RATING)) {
//                return new CheckerApproveUpdateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateInternalCreditRatingOperation");                
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_INTERNAL_CREDIT_RATING)) {
//                return new CheckerRejectUpdateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUpdateInternalCreditRatingOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_INTERNAL_CREDIT_RATING)) {
//                return new CheckerApproveUpdateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateInternalCreditRatingOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_INTERNAL_CREDIT_RATING)) {
//                return new CheckerRejectUpdateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUpdateInternalCreditRatingOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }     
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_INTERNAL_CREDIT_RATING)) {
//                return new MakerCreateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateInternalCreditRatingOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_INTERNAL_CREDIT_RATING)) {
//                return new MakerCloseCreateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseCreateInternalCreditRatingOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_INTERNAL_CREDIT_RATING)) {
//                return new MakerCloseUpdateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseUpdateInternalCreditRatingOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_CREDIT_RATING)) {
//                return new MakerUpdateInternalCreditRatingOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateInternalCreditRatingOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }        
        else {
			throw new TrxParameterException ("From State does not match presets! No operations found!");
		}
    }

}
