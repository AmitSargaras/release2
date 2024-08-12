/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.transaction.CMSTrxController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This trx controller is to be used in Customer Shareholder. It provides
 * factory for trx operations that are specific to Customer Shareholder.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CustShareholderTrxController extends CMSTrxController
{
    /**
     * Default Constructor
     */
    public CustShareholderTrxController() {
        super();
    }

    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table
     *
     * @return instance of Customer Shareholder
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_SHAREHOLDER;
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

        if (fromState.equals ( ICMSConstant.STATE_ND )) {
            if (action.equals ( ICMSConstant.ACTION_MAKER_CREATE_SHAREHOLDER ) ) {
                return new MakerCreateCustShareholderOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals ( ICMSConstant.STATE_ACTIVE )) {
            if (action.equals ( ICMSConstant.ACTION_MAKER_UPDATE_SHAREHOLDER )) {
                return new MakerUpdateCustShareholderOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }        
        else if (fromState.equals ( ICMSConstant.STATE_PENDING_UPDATE )) {
            if (action.equals ( ICMSConstant.ACTION_CHECKER_APPROVE_SHAREHOLDER )) {
                return new CheckerApproveUpdateCustShareholderOperation();
            }
            else if (action.equals ( ICMSConstant.ACTION_CHECKER_REJECT_SHAREHOLDER )) {
                return new CheckerRejectUpdateCustShareholderOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals ( ICMSConstant.STATE_PENDING_CREATE )) {
            if (action.equals ( ICMSConstant.ACTION_CHECKER_APPROVE_SHAREHOLDER )) {
                return new CheckerApproveUpdateCustShareholderOperation();
            }
            else if (action.equals ( ICMSConstant.ACTION_CHECKER_REJECT_SHAREHOLDER )) {
                return new CheckerRejectUpdateCustShareholderOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }        
        else if (fromState.equals ( ICMSConstant.STATE_REJECTED_CREATE )) {
            if (action.equals ( ICMSConstant.ACTION_MAKER_CREATE_SHAREHOLDER )) {
                return new MakerCreateCustShareholderOperation();
            }
            else if (action.equals ( ICMSConstant.ACTION_MAKER_CLOSE_SHAREHOLDER )) {
                return new MakerCloseCreateCustShareholderOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals ( ICMSConstant.STATE_REJECTED_UPDATE )) {
            if (action.equals ( ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_SHAREHOLDER )) {
                return new MakerCloseUpdateCustShareholderOperation();
            }
            else if (action.equals ( ICMSConstant.ACTION_MAKER_UPDATE_SHAREHOLDER )) {
                return new MakerUpdateCustShareholderOperation();
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