/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/CustomerTrxController.java,v 1.6 2004/02/10 10:36:56 lyng Exp $
 */
package com.integrosys.cms.app.customer.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control Customer related operations.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/02/10 10:36:56 $ Tag: $Name: $
 */
public class CustomerTrxController extends CMSTrxController {
	/**
	 * Default Constructor
	 */
	public CustomerTrxController() {
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
		return ICMSConstant.INSTANCE_CUSTOMER;
	}

	/**
	 * Returns an ITrxOperation object
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws TrxParameterException on error
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	// helper method
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String status = value.getStatus();
		DefaultLogger.debug(this, "Status: " + status);
		if (null == status) {
			throw new TrxParameterException("Status is null!");
		}
		String action = param.getAction();
		DefaultLogger.debug(this, "Action: " + action);
		if (null == action) {
			throw new TrxParameterException("Action is null in IAMTrxParameter!");
		}
		String fromState = value.getFromState();
		DefaultLogger.debug(this, "From State: " + fromState);
		if (null == fromState) {
			throw new TrxParameterException("From State is null!");
		}

        String toState = value.getToState();
        DefaultLogger.debug(this, "toState: " + value.getToState());

        if(toState!=null){
        if(toState.equals(ICMSConstant.STATE_DRAFT)){
        	//Below cond Temp for 317 issue
        	if(action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER)  )
        	{
        		action="ACTION_MAKER_UPDATE_DRAFT_CUSTOMER";
        	}
        	
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DRAFT_CUSTOMER) || action.equals(ICMSConstant.ACTION_MAKER_CREATE_DRAFT_CUSTOMER_PARTY)) {
        		return new CreateDraftCustomerOperation();

        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DRAFT_CUSTOMER_BRMAKER)) {
        		return new CreateDraftCustomerOperation();

        	}
//        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DRAFT_CUSTOMER)  || action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DRAFT_CUSTOMER_PARTY)) {
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DRAFT_CUSTOMER)){
        		return new MakerUpdateDraftCustomerOperation();
        	}
        	
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DRAFT_CUSTOMER_BRMAKER)) {
        		return new MakerUpdateDraftCustomerBrmakerOperation();
        	}
        	
            //Start:Uma Khot:Added for Valid Rating CR
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DRAFT_CUSTOMER_PARTY)) {
                return new MakerUpdateDraftCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
            
//        	if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CUSTOMER) || action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CUSTOMER_PARTY)) {
        	if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CUSTOMER) ) {
        		return new MakerCloseDraftCustomerOperation();
        	}
        	if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CUSTOMER_BRMAKER)) {
        		return new MakerCloseDraftCustomerBrmakerOperation();
        	}
            //Start:Uma Khot:Added for Valid Rating CR
            if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CUSTOMER_PARTY)) {
                return new MakerCloseDraftCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
        }
        }
//            if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER)  || action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_PARTY)) {
        if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER)){
                return new SaveCustomerOperation();
            }
            if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_BRMAKER)) {
                return new SaveCustomerBrmakerOperation();
            }
          //Start:Uma Khot:Added for Valid Rating CR
            if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_PARTY)) {
                return new SaveCustomerPartymakerOperation();
            }
            
        //    if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT) || action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT_PARTY)) {
            if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT) ) {
                return new SaveEditCustomerOperation();
            }
            if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT_BRMAKER)) {
                return new SaveEditCustomerBrmakerOperation();
            }
            
          //Start:Uma Khot:Added for Valid Rating CR
            if (action.equals(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT_PARTY)) {
                return new SaveEditCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
		if (status.equals(ICMSConstant.STATE_ND)) {
//			if (action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER_PARTY)) {
			if (action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER)){
				return new CreateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER_BRMAKER)) {//Sandeep Shinde
				return new CreateCustomerBrmakerOperation();
			}
	          //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER_PARTY)) {
                return new CreateCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
            
//			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_PARTY)) {//Sandeep Shinde
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER)){
				return new MakerUpdateCustomerOperation();
			}
	          //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_PARTY)) {
                return new MakerUpdateCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		/* 	else if for Status PENDING_CREATE Added by Sandeep Shinde on 29-11-2011	*/
		else if (status.equals(ICMSConstant.PENDING_CREATE)) {			
//			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER_PARTY)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER) ){
				return new CheckerApproveCreateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER_BRCHECKER)) {
				return new CheckerApproveCreateCustomerBrcheckerOperation();
			}
	          //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER_PARTY)) {
                return new CheckerApproveCreateCustomerPartycheckerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
			
//			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER)  || action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER_PARTY)) {
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER) ){
				return new CheckerRejectCreateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER)) {
				return new CheckerRejectCreateCustomerBrcheckerOperation();
			}
	          //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER_PARTY)) {
                return new CheckerRejectCreateCustomerPartycheckerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		
		/* 	else if for Status STATE_PENDING_DELETE Added by Sandeep Shinde on 05-04-2011	*/
		else if (status.equals(ICMSConstant.STATE_PENDING_DELETE)) {			
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_CUSTOMER) || action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_CUSTOMER_PARTY)) {
				return new CheckerApproveDeleteCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DELETE_CUSTOMER)) {
				return new CheckerRejectDeleteCustomerOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_ACTIVE)) {
//			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER)  || action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_PARTY)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER)){
				return new MakerUpdateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_BRMAKER)) {
				return new MakerUpdateCustomerBrmakerOperation();
			}
	          //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_PARTY)) {
                return new MakerUpdateCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
			
			else if (action.equals(ICMSConstant.ACTION_HOST_UPDATE_CUSTOMER)) {
				return new HostUpdateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_DELETE_CUSTOMER)) {
				return new HostDeleteCustomerOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
//			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTY)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER)){
				return new CheckerApproveUpdateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_BRMAKER)) {
				return new CheckerApproveUpdateCustomerBrmakerOperation();
			}
			
			//Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTY)) {
                return new CheckerApproveUpdateCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
			
//			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER_PARTY)) {
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER)){
				return new CheckerRejectUpdateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER)) {
				return new CheckerRejectUpdateCustomerOperation();
			}
		
		
			//Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER_PARTY)) {
            return new CheckerRejectUpdateCustomerPartymakerOperation();
			}
			//End:Uma Khot:Added for Valid Rating CR
		
			else if (action.equals(ICMSConstant.ACTION_HOST_DELETE_CUSTOMER)) {
				return new HostDeleteCustomerOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		
		/*	else if for Status : STATE_CLOSED added by Sandeep Shinde on 07-04-2011 Start*/		
		else if (status.equals(ICMSConstant.STATE_CLOSED)) {
//			if (action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER_PARTY)) {
			if (action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER) ){
				return new CreateCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER)) {
				return new MakerUpdateCloseCustomerOperation();
			}
        //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_CREATE_CUSTOMER_PARTY)) {
                return new CreateCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_HOST_DELETE_CUSTOMER)) {
				return new HostDeleteCustomerOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		/*	else if added by Sandeep Shinde on 07-04-2011 End*/
		
		else if (status.equals(ICMSConstant.STATE_REJECTED)) {
//			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE) || action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_PARTY)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE)){
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerCancelUpdateCustomerOperation();
				}				
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
	          //Start:Uma Khot:Added for Valid Rating CR
			if (action.equals(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_PARTY)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerCancelUpdateCustomerPartymakerOperation();
				}				
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
	          //End:Uma Khot:Added for Valid Rating CR
			
//			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_PARTY)) {//Sandeep Shinde
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER)){
				return new MakerUpdateRejectCustomerOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_BRMAKER)) {//Sandeep Shinde
				return new MakerUpdateRejectCustomerBrmakerOperation();
			}
			
	          //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_PARTY)) {
                return new MakerUpdateRejectCustomerPartymakerOperation();
            }
          //End:Uma Khot:Added for Valid Rating CR
			/*	else if added by Sandeep Shinde on 06-04-2011 Start*/			
//			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CUSTOMER) || action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CUSTOMER_PARTY)) {
			
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CUSTOMER)){
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCustomerOperation();
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)){
					return new MakerCloseRejectedCustomerOperation();
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)){
					return new MakerCloseRejectedCustomerOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}	else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CUSTOMER_BRMAKER)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCustomerBrmakerOperation();
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)){
					return new MakerCloseRejectedCustomerOperation();
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)){
					return new MakerCloseRejectedCustomerOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}

	          //Start:Uma Khot:Added for Valid Rating CR
			else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_CUSTOMER_PARTY)){
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerCloseRejectedCustomerPartymakerOperation();
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)){
					return new MakerCloseRejectedCustomerPartymakerOperation();
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)){
					return new MakerCloseRejectedCustomerPartymakerOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}           //End:Uma Khot:Added for Valid Rating CR
			
			/*	else if added by Sandeep Shinde on 06-04-2011 End*/
//			else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTY)) {
			else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_CUSTOMER)){
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return new MakerResubmitUpdateCustomerOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
//			else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER) || action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTY)) {
			else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER) ){
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerResubmitCreateCustomerOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return new MakerResubmitCreateCustomerBrmakerOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
	          //Start:Uma Khot:Added for Valid Rating CR
			  else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTY)) {
					if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
						return new MakerResubmitUpdateCustomerPartymakerOperation();
					}
					else {
						throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
								+ " and fromState: " + fromState);
					}
				}
				else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTY)) {
					if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
						return new MakerResubmitCreateCustomerPartymakerOperation();
					}
					else {
						throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
								+ " and fromState: " + fromState);
					}
				}
	        //End:Uma Khot:Added for Valid Rating CR
			
			else if (action.equals(ICMSConstant.ACTION_MAKER_RESUBMIT_DELETE_CUSTOMER)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return new MakerResubmitDeleteCustomerOperation();
				}
				else {
					throw new TrxParameterException("Unknow Action: " + action + " with status: " + status
							+ " and fromState: " + fromState);
				}
			}
			else if (action.equals(ICMSConstant.ACTION_HOST_DELETE_CUSTOMER)) {
				return new HostDeleteCustomerOperation();
			}
			else {
				throw new TrxParameterException("Unknow Action: " + action + " with status: " + status);
			}
		}
		else {
			throw new TrxParameterException("Status does not match presets! No operations found!");
		}
	}
}