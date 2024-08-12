/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.cms.app.transaction.CMSTrxController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.Map;

/**
 * This trx controller is to be used in Country Limit. It provides
 * factory for trx operations that are specific to Country Limit.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CountryLimitTrxController extends CMSTrxController
{

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    /**
     * Default Constructor
     */
    public CountryLimitTrxController() {
        super();
    }

    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table
     *
     * @return instance of Country Limit
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_COUNTRY_LIMIT;
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
       
		if (fromState.equals (ICMSConstant.STATE_ACTIVE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY_LIMIT)) {
//                return new MakerUpdateCountryLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCountryLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }        
        else if (fromState.equals (ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY_LIMIT)) {
//                return new CheckerApproveUpdateCountryLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCountryLimitOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY_LIMIT)) {
//                return new CheckerRejectUpdateCountryLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUpdateCountryLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY_LIMIT)) {
//                return new CheckerApproveUpdateCountryLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateCountryLimitOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY_LIMIT)) {
//                return new CheckerRejectUpdateCountryLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUpdateCountryLimitOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }       
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_COUNTRY_LIMIT)) {
//                return new MakerCloseUpdateCountryLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseUpdateCountryLimitOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY_LIMIT)) {
//                return new MakerUpdateCountryLimitOperation();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateCountryLimitOperation");
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