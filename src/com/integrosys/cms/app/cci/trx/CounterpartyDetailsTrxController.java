package com.integrosys.cms.app.cci.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;
import com.integrosys.cms.app.predeal.trx.MakerDeleteEarMarkOperation;


/**
 * This controller is used to control document item related operations.
 * for update/ checker approve
 *
 * @author $Author: Jitu<br>
 * @version $Revision: $
 * @since $Date: $   19th Dec 2007
 *        Tag:      $Name: $  Jitendra
 */

public class CounterpartyDetailsTrxController
        extends CMSTrxController {

    /**
     * Default Constructor
     */
    public CounterpartyDetailsTrxController() {
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
        return ICMSConstant.INSTANCE_CCI_COUNTER_PARTY;
    }


    /**
     * Returns an ITrxOperation object
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws TrxParameterException on error
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

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
    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

        String action = param.getAction();
        String toState = value.getToState();
        String fromState = value.getFromState();
        String status = value.getStatus();

        DefaultLogger.debug(this, "Action : " + action);
        DefaultLogger.debug(this, "fromState : " + fromState);
        DefaultLogger.debug(this, "toState : " + toState);
        DefaultLogger.debug(this, "status : " + status);

        if (fromState == null) {
            throw new TrxParameterException("From State is null!");
        }
        if (action == null) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }

         //maker side

        if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY)
                && "PENDING_UPDATE".equals(fromState)) {
            return new MakerUpdateOperation();
        } if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY)
                && "REJECTED".equals(fromState)) {
            return new MakerUpdateOperation();
         } if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY)
                && "PENDING_DELETE".equals(fromState)) {
            return new MakerUpdateOperation();
        }else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY)
                && "PENDING_CREATE".equals(fromState)) {
             return new MakerUpdateOperation();
        } else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY)
                && "ND".equals(fromState)) {
            return new MakerCreateOperation();

        }else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CC_COUNTER_PARTY) &&
                ICMSConstant.STATE_ACTIVE.equals(toState)) {
                return new MakerDeleteOperation();
        }else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL)){
            return new MakerCancelUpdateCounterpartyDetailsOperation();

          //Checker side
        }else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE)){
            if (fromState.equals(ICMSConstant.STATE_ND)) {
                return new CheckerApproveUpdateCounterpartyDetailsOperation();
            }else if  (fromState.equals(ICMSConstant.STATE_ACTIVE) && ICMSConstant.PENDING_UPDATE.equals(toState)) {
                return new CheckerApproveUpdateCounterpartyDetailsOperation();
             }else if  (fromState.equals(ICMSConstant.STATE_REJECTED) && ICMSConstant.PENDING_UPDATE.equals(toState)) {
                return new CheckerApproveUpdateCounterpartyDetailsOperation();
            }else if (ICMSConstant.STATE_PENDING_DELETE.equals(toState)) {
               return new CheckerApproveDeleteCounterpartyDetailsOperation();
          }
         }else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT)){
                  return new CheckerRejectUpdateCounterpartyDetailsOperation();
        }else{
           throw new TrxParameterException("From State does not match presets! No operations found!");
        }
      



        throw new TrxParameterException("No operations found");
    }
}