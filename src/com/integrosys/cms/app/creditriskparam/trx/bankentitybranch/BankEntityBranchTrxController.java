package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * Title: CLIMS 
 * Description: Bank entity branch transaction controller
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: June 1, 2008
 */
public class BankEntityBranchTrxController extends CMSTrxController {

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
    public BankEntityBranchTrxController() {
        super();
    }
    /**
    * Return the instance name associated to this ITrxController.
    * The instance name refers to the instance of the state transition table.
    * Not implemented.
    *
    * @return String
    */
    public String getInstanceName()
    {
        return ICMSConstant.INSTANCE_BANK_ENTITY_BRANCH_PARAM;
    }
    /**
    * Returns an ITrxOperation object
    * @param value - ITrxValue
    * @param param - ITrxParameter
    * @return ITrxOperation - the trx operation
    * @throws com.integrosys.base.businfra.transaction.TrxParameterException on error
    */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException
    {
        ITrxOperation op = factoryOperation(value, param);
        DefaultLogger.debug(this, "Returning Operation: " + op);
        return op;
    }

    /**
    * Helper method to factory the operations
    * @param value - ITrxValue
    * @param param - ITrxParameter
    * @return ITrxOperation - the trx operation
    * @throws TrxParameterException on error
    */
    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException
    {
        String action = param.getAction();
        if(null == action)
        {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }
        DefaultLogger.debug(this, "Action: " + action);

        String toState = value.getToState();
        String fromState = value.getFromState();
        DefaultLogger.debug(this, "toState: " + value.getToState());
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND)))
        {
            if(action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BANK_ENTITY_BRANCH))
            {
//                return new MakerUpdateBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateBankEntityBranchOp");
            }
            
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if(toState.equals(ICMSConstant.STATE_PENDING_UPDATE))
        {
            if(action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BANK_ENTITY_BRANCH))
            {
//                return new CheckerApproveBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveBankEntityBranchOp");
            }

            if(action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BANK_ENTITY_BRANCH))
            {
//                return new CheckerRejectBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectBankEntityBranchOp");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if(toState.equals(ICMSConstant.STATE_ACTIVE))
        {
            if(action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BANK_ENTITY_BRANCH))
            {
//                return new MakerUpdateBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateBankEntityBranchOp");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        }
        else if(toState.equals(ICMSConstant.STATE_PENDING_UPDATE))
        {
            if(action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_BANK_ENTITY_BRANCH))
            {
//                return new CheckerApproveBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveBankEntityBranchOp");
            }
            else if(action.equals(ICMSConstant.ACTION_CHECKER_REJECT_BANK_ENTITY_BRANCH))
            {
//                return new CheckerRejectBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectBankEntityBranchOp");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if(toState.equals(ICMSConstant.STATE_REJECTED))
        {
            if(action.equals(ICMSConstant.ACTION_MAKER_UPDATE_BANK_ENTITY_BRANCH))
            {
//                return new MakerUpdateBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateBankEntityBranchOp");
            }
            else if(action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BANK_ENTITY_BRANCH))
            {
//                return new MakerCloseRejectedBankEntityBranchOp();
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedBankEntityBranchOp");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
}
