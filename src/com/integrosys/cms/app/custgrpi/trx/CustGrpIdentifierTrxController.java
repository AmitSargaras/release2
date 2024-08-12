package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;


/**
 * This controller is used to control document item related operations.
 * for update/ checker approve
 *
 * @author $Author: Jitu<br>
 * @version $Revision: $
 * @since $Date: $   19th Dec 2007
 *        Tag:      $Name: $  Jitendra
 */

public class CustGrpIdentifierTrxController  extends CMSTrxController {

    /**
     * Default Constructor
     */
    public CustGrpIdentifierTrxController() {
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
        return ICMSConstant.INSTANCE_CUST_GRP_IDENTIFIER;
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
     *
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

        //maker2 side
         if (action.equals(ICMSConstant.ACTION_MAKER2_SAVE_CUST_GRP_IDENTIFIER) ) {
                return new Maker2SaveOperation();
        } else  if (action.equals(ICMSConstant.ACTION_MAKER2_UPDATE_CUST_GRP_IDENTIFIER)
                && ( ICMSConstant.STATE_PENDING_CREATE.equals(fromState)
                    || ICMSConstant.PENDING_UPDATE.equals(fromState)
                    || ICMSConstant.STATE_PENDING_DELETE.equals(fromState)
                    || ICMSConstant.STATE_ACTIVE.equals(fromState)
                    || ICMSConstant.STATE_REJECTED.equals(fromState)
                    || ICMSConstant.STATE_ND.equals(fromState)
                    || ICMSConstant.STATE_DRAFT.equals(fromState)
                    || ICMSConstant.STATE_REJECTED_DELETE.equals(fromState)
                  )) {
                return new Maker2UpdateOperation();
        } else if (action.equals(ICMSConstant.ACTION_MAKER2_CANCEL)) {
                return new Maker2CancelUpdateCustGrpIdentifierOperation();

          //Checker2 side
        } else if (action.equals(ICMSConstant.ACTION_CHECKER2_APPROVE)) {
            if (fromState.equals(ICMSConstant.STATE_DRAFT) && ICMSConstant.PENDING_UPDATE.equals(toState) ) {
                return new Checker2ApproveUpdateCustGrpIdentifierOperation();
            } else if (fromState.equals(ICMSConstant.STATE_ACTIVE) && ICMSConstant.PENDING_UPDATE.equals(toState)) {
                return new Checker2ApproveUpdateCustGrpIdentifierOperation();
             }else if  (fromState.equals(ICMSConstant.STATE_REJECTED) && ICMSConstant.PENDING_UPDATE.equals(toState)) {
                return new Checker2ApproveUpdateCustGrpIdentifierOperation();
            } else if (ICMSConstant.STATE_PENDING_DELETE.equals(toState)) {
                return new Checker2ApproveUpdateCustGrpIdentifierOperation();
            }
          } else if (action.equals(ICMSConstant.ACTION_CHECKER2_REJECT)) {
                return new Checker2RejectUpdateCustGrpIdentifierOperation();


          //maker side
        } else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CUST_GRP_IDENTIFIER) ) {
                return new MakerSaveOperation();
        } else  if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUST_GRP_IDENTIFIER)){
               if( ICMSConstant.PENDING_UPDATE.equals(fromState)
                    || ICMSConstant.STATE_PENDING_DELETE.equals(fromState)
                    || ICMSConstant.STATE_PENDING_CREATE.equals(fromState)
                    || ICMSConstant.STATE_REJECTED.equals(fromState)
                    || ICMSConstant.STATE_REJECTED_DELETE.equals(fromState)
                   ) {
                return new MakerUpdateOperation();
               } else if ( ICMSConstant.STATE_ACTIVE.equals(fromState)
                       || ICMSConstant.STATE_DRAFT.equals(toState)
                     ){
                return new MakerUpdateOperation();
               } else if ( ICMSConstant.STATE_ND.equals(fromState)
                       || ICMSConstant.STATE_DRAFT.equals(fromState)
                     ){
                return new MakerCreateOperation();
               }

        } else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CUST_GRP_IDENTIFIER) &&
                (ICMSConstant.STATE_ACTIVE.equals(toState) || ICMSConstant.STATE_REJECTED_DELETE.equals(toState)) ) {
            return new MakerDeleteOperation();
        } else if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL)) {
            return new MakerCancelUpdateCustGrpIdentifierOperation();

        //Checker side
        } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT)) {
            return new CheckerRejectUpdateCustGrpIdentifierOperation();
        } else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE)) {
            if (fromState.equals(ICMSConstant.STATE_ND)) {
                return new CheckerApproveUpdateCustGrpIdentifierOperation();
            } else if (fromState.equals(ICMSConstant.STATE_DRAFT) && ICMSConstant.PENDING_UPDATE.equals(toState) ) {
                  return new CheckerApproveUpdateCustGrpIdentifierOperation();
            } else if (fromState.equals(ICMSConstant.STATE_ACTIVE) && ICMSConstant.PENDING_UPDATE.equals(toState)) {
                return new CheckerApproveUpdateCustGrpIdentifierOperation();
             }else if  (fromState.equals(ICMSConstant.STATE_REJECTED) && ICMSConstant.PENDING_UPDATE.equals(toState)) {
                return new CheckerApproveUpdateCustGrpIdentifierOperation();
            } else if (ICMSConstant.STATE_PENDING_DELETE.equals(toState)) {
                return new CheckerApproveDeleteCustGrpIdentifierOperation();
            }
        } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT)) {
            return new CheckerRejectUpdateCustGrpIdentifierOperation();
        } else {
            throw new TrxParameterException("From State does not match presets! No operations found!");
        }

        throw new TrxParameterException("No operations found");
    }
}