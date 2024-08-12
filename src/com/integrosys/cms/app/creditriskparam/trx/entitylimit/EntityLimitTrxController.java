/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.entitylimit;

import com.integrosys.cms.app.transaction.CMSTrxController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.Map;

/**
 * This trx controller is to be used in Entity Limit. It provides
 * factory for trx operations that are specific to Entity Limit.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class EntityLimitTrxController extends CMSTrxController
{

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

	private static final long serialVersionUID = 1L;
	
    /**
     * Default Constructor
     */
    public EntityLimitTrxController() {
        super();
    }

    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table
     *
     * @return instance of Entity Limit
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_ENTITY_LIMIT;
    }

    /**
     * Get transaction operation given the transaction value and parameter.
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @throws TrxParameterException if transaction parameter is invalid
     */
    public ITrxOperation getOperation (ITrxValue value, ITrxParameter param)
        throws TrxParameterException
    {
        ITrxOperation op = factoryOperation (value, param);
        DefaultLogger.debug (this, "Returning Operation: " + op);
        return op;
    }

    /**
     * Helper method to get the operation given the transaction value and
     * transaction parameter.
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return transaction operation
     * @throws TrxParameterException if the transaction parameter is invalid
     */
    private ITrxOperation factoryOperation (ITrxValue value, ITrxParameter param)
        throws TrxParameterException
    {
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
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_ENTITY_LIMIT) ) {
//                return new MakerCreateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateEntityLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_ACTIVE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_ENTITY_LIMIT)) {
//                return new MakerUpdateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateEntityLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }        
        else if (fromState.equals (ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_ENTITY_LIMIT)) {
//                return new CheckerApproveUpdateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateEntityLimitOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_ENTITY_LIMIT)) {
//                return new CheckerRejectUpdateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUpdateEntityLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_ENTITY_LIMIT)) {
//                return new CheckerApproveUpdateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateEntityLimitOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_ENTITY_LIMIT)) {
//                return new CheckerRejectUpdateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUpdateEntityLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }        
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_ENTITY_LIMIT)) {
//                return new MakerCreateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateEntityLimitOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_ENTITY_LIMIT)) {
//                return new MakerCloseCreateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseCreateEntityLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_ENTITY_LIMIT)) {
//                return new MakerCloseUpdateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseUpdateEntityLimitOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_ENTITY_LIMIT)) {
//                return new MakerUpdateEntityLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateEntityLimitOperation");
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