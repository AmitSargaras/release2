/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ExemptFacilityGroupTrxController.java,v 1.10 2005/01/12 06:37:08 hshii Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 *
 * @author $Author: hshii $
 * @version $Revision: 1.10 $
 * @since $Date: 2005/01/12 06:37:08 $
 * Tag: $Name:  $
 */
public class ExemptFacilityGroupTrxController extends CMSTrxController {

    /**
     * Default Constructor
     */
    public ExemptFacilityGroupTrxController() {
        super();
    }


    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table.
     * Not implemented.
     *
     * @return String
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_EXEMPT_FACILITY_GROUP;
    }


    /**
     * Returns an ITrxOperation object
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException on error
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param)
            throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
        DefaultLogger.debug(this, "Returning Operation: " + op);
        return op;
    }


    /**
     * Helper method to factory the operations
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException on error
     */
    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException
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
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_EXEMPT_FACILITY) ) {
                return new MakerCreateOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_ACTIVE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_EXEMPT_FACILITY)) {
                return new MakerUpdateOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_EXEMPT_FACILITY)) {
                return new CheckerApproveUpdateOperation();
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_EXEMPT_FACILITY)) {
                return new CheckerRejectUpdateOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_EXEMPT_FACILITY)) {
                return new CheckerApproveUpdateOperation();
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_EXEMPT_FACILITY)) {
                return new CheckerRejectUpdateOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_CREATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CREATE_EXEMPT_FACILITY)) {
                return new MakerCreateOperation();
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_EXEMPT_FACILITY)) {
                return new MakerCloseCreateOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_EXEMPT_FACILITY)) {
                return new MakerCloseUpdateOperation();
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_EXEMPT_FACILITY)) {
                return new MakerUpdateOperation();
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        } else if (fromState.equals (ICMSConstant.STATE_CLOSED)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_EXEMPT_FACILITY) ) {
                return new MakerCreateOperation();
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