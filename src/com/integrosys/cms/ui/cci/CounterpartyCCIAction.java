package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.*;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 9, 2007 Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class CounterpartyCCIAction extends CommonAction implements IPin {

	public static final String EVENT_ADD = "add";

	public static final String EVENT_EDIT = "edit";

	public static final String EVENT_READ = "read";

	public static final String EVENT_ADD_NOOP = "addNoop";

	private static final String REROUTEPAGE = "reroutepage";

	private static final String SUBSTITUTEPAGE = "substitutepage";

	private static final String REASSIGNPAGE = "reassignpage";

	private static final String CHECKER_EDIT_CCI = "checker_edit_cci";

	private static final String CHECKER_APPROVE_EDIT_CCI = "checker_approve_edit_cci";

	private static final String MAKER_CLOSE_CCI = "maker_close_cci";

	public static final String EV_WIP = "wip";

    public static final String EVENT_VIEW_CUSTOMER_DETAILS = "view_customer_details";
    
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("prepare".equals(event) || "prepare_form".equals(event)
				|| "search_customer".equals(event)
				|| "prepare_search_customer".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCounterpartySearchCommand();
		} else if ("prepare_create".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCounterpartyCommand();

        } else if ("customer_list".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new SearchCounterpartyCommand();

        } else if ("prepare_update".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareUpdateCounterpartyCommand();
		} else if ("save".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCounterpartyListCommand();
		} else if ("list".equals(event) || "cc_customer_list".equals(event) ) {
//				|| "customer_list".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListCounterpartyCommand();
		} else if ("first_search".equals(event)
				|| "subsequent_search".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CIFSearchCommand();
		} else if (EVENT_READ.equals(event) || "prepare_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadCounterpartyListCommand();
		} else if (EVENT_EDIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditCounterpartyListCommand();
		} else if ("cancel".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CancelCounterpartyListCommand();
		} else if ("return".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCounterpartyCommand();
			/*
			 * } else if ("search_customer".equals(event)) { objArray = new
			 * ICommand[1]; objArray[0] = new
			 * PrepareCounterpartySearchCommand();
			 */
			/*
			 * } else if ("cc_customer_list".equals(event) ||
			 * "customer_list".equals(event)) { objArray = new ICommand[1];
			 * objArray[0] = new ListCounterpartyCommand();
			 */
			/*
			 * } else if ("customer_list".equals(event)) { objArray = new
			 * ICommand[1]; objArray[0] = new ListCounterpartyCommand();
			 */
		} else if ("add".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddCounterpartyListCommand();
			/*
			 * } else if ("save".equals(event)) { objArray = new ICommand[1];
			 * objArray[0] = new SaveCounterpartyListCommand();
			 */
		} else if ("remove".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveCounterpartyListCommand();
		} else if ("submit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCounterpartyListCommand();
		} else if (CHECKER_EDIT_CCI.equals(event)
				|| MAKER_CLOSE_CCI.equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadCounterpartyCommand();
		} else if (CHECKER_APPROVE_EDIT_CCI.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveEditCounterpartyCommand();
		} else if ("checker_reject_edit_cci".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectEditCounterpartyCommand();
			/*
			 * } else if ("prepare_delete".equals(event)) { objArray = new
			 * ICommand[1]; objArray[0] = new ReadCounterpartyListCommand();
			 */
		} else if ("delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteCounterpartyListCommand();
		} else if ("maker_edit_cci_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadRejectedCounterpartyListCommand();
		} else if ("maker_prepare_update".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditCounterpartyListCommand();
			// objArray[0] = new MakerReadCounterpartyCommand();
		} else if ("maker_close_cci".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadRejectedCounterpartyListCommand();
		} else if ("maker_close_cci_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCancelEditCounterpartyCommand();
		} else if ("viewLimitProfile".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessDetailsCustomerCommand();
		} else if (EVENT_VIEW_CUSTOMER_DETAILS.equals(event)){
            objArray = new ICommand[1];
            objArray[0] = new com.integrosys.cms.ui.customer.viewdetails.ProcessDetailsCustomerCommand();
        }

		return (objArray);
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm
	 *            is of type ActionForm
	 * @param locale
	 *            of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return CounterpartySearchFormValidator.validateInput(
				(CounterpartySearchForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("list") || event.equals("cc_customer_list")
				|| event.equals("customer_list")
                || CHECKER_APPROVE_EDIT_CCI.equals(event)
                || "checker_reject_edit_cci".equals(event)
                || "submit".equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("add".equals(event)) {
			errorEvent = "customer_list";
		}
		if ("customer_list".equals(event)) {
			errorEvent = "prepare_search_customer";
		}

        if (CHECKER_APPROVE_EDIT_CCI.equals(event) || "checker_reject_edit_cci".equals(event)) {
            errorEvent = "checker_edit_cci_error";
        }

        if ("submit".equals(event)) {
			errorEvent = "maker_edit_cci_reject";
		}

        return errorEvent;
	}

	/**
	 * This method is used to determine the next page to be displayed using the
	 * event Result hashmap and exception hashmap.It returns the page object .
	 * 
	 * @param event
	 *            is of type String
	 * @param resultMap
	 *            is of type HashMap
	 * @param exceptionMap
	 *            is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		
		// To return correct forward String for AJAX Search
		if ("first_search".equals(event) || "subsequent_search".equals(event)) {
			Page aPage = new Page();
			aPage.setPageReference("ajax_search_cif_result");
			return aPage;
		}




        Page aPage = new Page();

		String forward = null;


		ICCICounterpartyDetailsTrxValue trxValue = (ICCICounterpartyDetailsTrxValue) resultMap.get("ICCICounterpartyDetailsTrxValue");

        boolean isWip = false;
		if (trxValue != null) {
			String status = trxValue.getStatus();
			//String fromState = trxValue.getFromState();
			// System.out.println("CounterpartyCCIAction status[ " + status +
			// "], fromState [" + fromState +"]");
			isWip = status.equals(ICMSConstant.STATE_DRAFT)
					|| status.equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| status.equals(ICMSConstant.STATE_PENDING_DELETE)
			// || status.equals(ICMSConstant.STATE_REJECTED)
			;
		} else {
			DefaultLogger.debug(this,"trxValue  is null");
		}


        if (EVENT_EDIT.equals(event) || "prepare_update".equals(event)
				|| "prepare_delete".equals(event)) {
			if (isWip) {
				forward = "workInProgress";
			} else {
                if (!isActualCCINoExist(trxValue)){
                  forward = event+"_CREATE";
                }else{
                    forward = event;
                }
            }
		} else if (!exceptionMap.isEmpty()) {
			if (exceptionMap.get("errDuplicate") != null) { //todo
                     forward = "addNoop";
            }else if (exceptionMap.get("isExistCCICustomer") != null) {
               forward = "checker_edit_cci_error" ;
            }
		} else if (EV_WIP.equals(resultMap.get(EV_WIP))) {
			aPage.setPageReference(getReference(EV_WIP));
			return aPage;

        } else if ("cancel".equals(event) || "add".equals(event) ||"remove".equals(event) ) {
            if (!isActualCCINoExist(trxValue)){
                  forward = "after_add_create";
                }else{
                    forward = "after_add";
                }
        } else {
			forward = getReference(event);
		}

        aPage.setPageReference(forward);
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if ("prepare".equals(event) || "prepare_form".equals(event)) {
			forwardName = "after_prepare";
		} else if ("list".equals(event)) {
			forwardName = "after_list";
		}

		else if ("first_search".equals(event)) {
			forwardName = "ajax_search_cif_result";

		} else if ("subsequent_search".equals(event)) {
			forwardName = "ajax_search_cif_result";

		/*}else if ("cancel".equals(event)) {
			forwardName = "after_add";*/
		} else if ("search".equals(event)) {
			forwardName = "after_search";
		} else if (REROUTEPAGE.equals(event) || REASSIGNPAGE.equals(event)
				|| SUBSTITUTEPAGE.equals(event)) {
			forwardName = event;
		} else if (EVENT_EDIT.equals(event)) {
			forwardName = "edit";
		} else if (
		// EVENT_EDIT.equals(event) ||
		"prepare_create".equals(event)) {
			forwardName = "prepare_update";
		} else if ("prepare_search".equals(event)) {
			forwardName = "after_prepare";
		/*} else if ("add".equals(event)) {
			forwardName = "after_add";
		} else if ("remove".equals(event)) {
			forwardName = "after_add";*/
		} else if ("save".equals(event)) {
			forwardName = "submit";
		} else if (CHECKER_APPROVE_EDIT_CCI.equals(event)) {
			forwardName = "common_approve_page";
        } else if ("checker_reject_edit_cci".equals(event)) {
			forwardName = "common_reject_page";
        } else if ("delete".equals(event) || "submit".equals(event)) {
			forwardName = "ack_submit";
		} else if ("maker_edit_cci_reject".equals(event)) {
			forwardName = "maker_edit_cci_reject";
		} else if ("maker_close_cci".equals(event)) {
			forwardName = "maker_close_cci_page";
		} else if ("maker_close_cci_confirm".equals(event)) {
			forwardName = "common_close_page";
		} else if ("to_track".equals(event)) {
			forwardName = "after_to_track";
		} else if ("maker_prepare_update".equals(event)) {
			forwardName = "maker_prepare_update";
		} else {
			forwardName = event;
		}
		return forwardName;
	}

    private boolean isActualCCINoExist(ICCICounterpartyDetailsTrxValue trxValue ) {

     if (trxValue != null){

//        System.out.println("llllllllllllllllllllllllllllllllllllllllllll");
//             System.out.println(trxValue.getFromState());
//            System.out.println(trxValue.getStatus());
//                System.out.println(trxValue.getStagingReferenceID());
//          System.out.println("llllllllllllllllllllllllllllldddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddlllllllllllllll");
//          System.out.println("lllllllllllllllllllllllllllllllccddddddddddddddddddddddddddddddddddddddddddddddddddddddlllllllllllll");
//          System.out.println("llllllllllllllllllllllllllllllllllllllllllll");
//          System.out.println("llllllllllllllllllllllllllllllllllllllllllll");
         
         ICCICounterpartyDetails actDetails = trxValue.getCCICounterpartyDetails();
        if (actDetails != null){
            ICCICounterparty[]   objArray = actDetails.getICCICounterparty() ;
            if (objArray !=null && objArray.length >0)  {
               for (int i=0;i < objArray.length;i++){
                 ICCICounterparty obj= objArray[i]  ;
                 if (obj.getGroupCCINo() !=ICMSConstant.LONG_INVALID_VALUE
                         ||  obj.getGroupCCINo() != 0) {
                     return true;
                 }
               }
            }
        }
     }

        return false;
    }
}
