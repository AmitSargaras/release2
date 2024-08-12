
package com.integrosys.cms.app.creditApproval.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 1.0 $
 * @since $Date: 2011/04/07 02:49:03 $ Tag: $Name: $
 */
public class CreditApprovalTrxController extends CMSTrxController {

	/**
	 * Default Constructor
	 */

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public CreditApprovalTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table. Not
	 * implemented.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CREDIT_APPROVAL;
	}

	/**
	 * Returns an ITrxOperation object
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
	 * 
	 */

	
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		String action = param.getAction();
		String toState = value.getToState();
	    String fromState = value.getFromState();
		DefaultLogger.debug(this, "ToState : " + toState);
		DefaultLogger.debug(this, "FromState : " + fromState);
		if (null == action) {
			throw new CreditApprovalException("Action is null in ITrxParameter!");
		}


		DefaultLogger.debug(this, "Action: " + action);
		
		
		 if(toState!=null){
		        if(toState.equals(ICMSConstant.STATE_DRAFT)){
		        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_CREDIT_APPROVAL)) {
		        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateCreditApprovalOperation");

		        	}
		        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CREDIT_APPROVAL)) {
		        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSubmitOperation");
		        	}
		        }
		  }
		 if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_CREDIT_APPROVAL)) {
		                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveCreditApprovalOperation");
		 }
		 if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CREDIT_APPROVAL)) {
		                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateCreditApprovalOperation");
		 }
		
		
		

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CREDIT_APPROVAL)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateCreditApprovalOperation");
		}
		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_CREDIT_APPROVAL_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREDIT_APPROVAL)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREDIT_APPROVAL)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_CREDIT_APPROVAL)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_CREDIT_APPROVAL)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_CREDIT_APPROVAL)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_CREDIT_APPROVAL)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitRejectedOperation");
		}

		//if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CREDIT_APPROVAL)) {
			//return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftOperation");
		//}	
		if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (param.getAction().equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREDIT_APPROVAL)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateCreditApprovalOperation");
                } else
            	if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                	return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateCreditApprovalOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteCreditApprovalOperation");
                }
            }
		}
		else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_CREDIT_APPROVAL)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDeleteCreditApprovalOperation");
            }
            else   if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CREDIT_APPROVAL)) {
            	return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateSubmitOperation");
            	
            }   
        }
		else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CREDIT_APPROVAL)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
            }
		}
		else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CREDIT_APPROVAL)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
            }
            
            throw new CreditApprovalException("Unknown Action: " + action + " with toState: " + toState);
        }
		else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CREDIT_APPROVAL)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftCreditApprovalOperation");
        }
		
		
		throw new CreditApprovalException("No operations found");
	}
	}

