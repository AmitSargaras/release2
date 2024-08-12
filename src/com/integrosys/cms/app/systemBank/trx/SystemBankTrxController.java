package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * @author abhijit.rudrakshawar
 *  System Bank Trx controller to manage trx operations
 */
public class SystemBankTrxController extends CMSTrxController {

    private Map nameTrxOperationMap;

    /**
     * @return &lt;name, ITrxOperation&gt; pair map to be injected, name and
     *         ITrxOperation name will be the same.
     */
    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    /**
     * Default Constructor
     */
    public SystemBankTrxController() {
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
        return ICMSConstant.INSTANCE_SYSTEM_BANK;
    }

    /**
     * Returns an ITrxOperation object
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException
     *          on error
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
        DefaultLogger.debug(this, "Returning Operation: " + op);
        return op;
    }

    /**
     * Helper method to factory the operations
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws TrxParameterException on error
     */
    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        String action = param.getAction();
        if (null == action) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }
        DefaultLogger.debug(this, "Action: " + action);

        String toState = value.getToState();
        String fromState = value.getFromState();
        DefaultLogger.debug(this, "toState: " + value.getToState());
        if(toState!=null){
            if(toState.equals(ICMSConstant.STATE_DRAFT)){
            	
            	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SYSTEM_BANK)) {
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSystemBankOperation");
            	}
            }
            }
                
                if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateSystemBankOperation");
                }
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateSystemBankOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateSystemBankOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectSystemBankOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSystemBankOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteSystemBankOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateSystemBankOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectSystemBankOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveDeleteSystemBankOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectSystemBankOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SYSTEM_BANK)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateSystemBankOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateSystemBankOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteSystemBankOperation");
                }
            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_SYSTEM_BANK)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateSystemBankOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateSystemBankOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDeleteSystemBankOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_SYSTEM_BANK)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftSystemBankOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
