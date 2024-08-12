/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ForexFeedGroupTrxController.java,v 1.10 2005/01/12 06:37:08 hshii Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.10 $
 * @since $Date: 2005/01/12 06:37:08 $ Tag: $Name: $
 */
public class InternalLimitParameterTrxController extends CMSTrxController {

	private static final long serialVersionUID = 1L;

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

	public InternalLimitParameterTrxController() {
		super();
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_INTERNAL_LIMIT;
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param)
			throws TrxParameterException {
		return factoryOperation(value, param);
	}

	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param)
			throws TrxParameterException {
		
		String fromState = value.getStatus();
        DefaultLogger.debug (this, "FromState: " + fromState);

        if (fromState == null) {
            throw new TrxParameterException ("From State is null!");
        }

        String action = param.getAction();
        DefaultLogger.debug (this, "Action: " + action);

        if (action == null) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }

        if (fromState.equals (ICMSConstant.STATE_ND)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateInternalLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_SAVE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveInternalLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_ACTIVE)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateInternalLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_SAVE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveInternalLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_DRAFT)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateInternalLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_SAVE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveInternalLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_CANCEL_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCancelUpdateInternalLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals (ICMSConstant.ACTION_CHECKER_APPROVE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckApproveInternalLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_CHECKER_REJECT_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectInternalLimitParameterOperation");
            }
            else {
                throw new TrxParameterException ("Unknown Action: " +
                            action + " with fromState: " + fromState);
            }
        }
        else if (fromState.equals (ICMSConstant.STATE_REJECTED)) {
            if (action.equals (ICMSConstant.ACTION_MAKER_SAVE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveInternalLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_CANCEL_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCancelUpdateInternalLimitParameterOperation");
            }
            else if (action.equals (ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_LIMIT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateInternalLimitParameterOperation");
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