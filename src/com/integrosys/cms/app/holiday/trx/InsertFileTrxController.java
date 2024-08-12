package com.integrosys.cms.app.holiday.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author abhijit.rudrakshawar
 *  Holiday Trx controller to manage trx operations
 */
public class InsertFileTrxController extends CMSTrxController {

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
    public InsertFileTrxController() {
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
        return ICMSConstant.INSTANCE_INSERT_HOLIDAY;
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
       
        }
         if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_FILE_INSERT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerHolidayInsertFileOperation");
            }
            if (action.equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerCreateHolidayOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
             if (action.equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_INSERT)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedInsertMasterOperation");
                }
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }else if (toState.equals(ICMSConstant.STATE_PENDING_INSERT)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveInsertMasterOperation");
            }
            if (action.equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectInsertMasterOperation");
            }
           
           
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } 
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
