package com.integrosys.cms.ui.custrelationship.customer;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.customer.BorrowerListCommand;
import com.integrosys.cms.ui.custrelationship.CustRelAction;

public class CRCustomerSearchAction extends CustRelAction {

	public static final String EVENT_CUST_ADD = "customer_add";
	public static final String EVENT_CUST_ADD_FRAME = "customer_add_frame";

	public static final String EVENT_CUST_PREPARE_SEARCH = "customer_search";
	public static final String EVENT_CUST_PREPARE_SEARCH_FRAME = "customer_search_frame";

	public static final String EVENT_CUST_SH_ADD = "cust_shareholder_add";
	public static final String EVENT_CUST_SH_ADD_FRAME = "cust_shareholder_add_frame";

	public static final String EVENT_CUST_SEARCH = "search_customer";

	public static final String EVENT_CUST_FIRST_SEARCH = "first_search";

	public static final String EVENT_CUST_SUBSEQUENT_SEARCH = "subsequent_search";

	public static final String EVENT_CUST_ADD_SEARCH = "add_customer_search";
	public static final String EVENT_CUST_ADD_SEARCH_FRAME = "add_customer_search_frame";

	public static final String EVENT_CUST_ADD_SH_SEARCH = "add_cus_sh_search";
	public static final String EVENT_CUST_ADD_SH_SEARCH_FRAME = "add_cus_sh_search_frame";

	public static final String EVENT_CUST_RESULT = "search_customer_result";

	public static final String EVENT_CUST_ADD_RESULT = "add_customer_search_result";
	public static final String EVENT_CUST_ADD_RESULT_FRAME = "add_customer_search_result_frame";

	// public static final String EVENT_CUST_ADD_SH_RESULT =
	// "add_cust_search_sh_result";

	public static final String EVENT_CUST_ADD_SELECT = "add_customer_search_select";

	public static final String EVENT_CUST_ADD_SH_SELECT = "add_cust_search_sh_select";

	public static final String EVENT_CUST_VIEW_DETAILS = "view_cust_details";

	protected ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this, "CRCustomerSearchAction is " + event);

		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event) 
				|| EVENT_CUST_ADD.equals(event)
				|| EVENT_CUST_SH_ADD.equals(event)
				|| EVENT_CUST_ADD_FRAME.equals(event)
				|| EVENT_CUST_SH_ADD_FRAME.equals(event)
			) {

			objArray = new ICommand[1];
			objArray[0] = new PrepareCustRelSearchCommand();

		/*} else if ("first_search".equals(event)
				|| "subsequent_search".equals(event)) {
			objArray = new ICommand[1];
			//objArray[0] = new CIFSearchCommand();
			objArray[0] = new CRCustSearchCommand();
			*/
		} else if (EVENT_CUST_SEARCH.equals(event) 
			// ||
			// EVENT_CUST_FIRST_SEARCH.equals(event) ||
			// EVENT_CUST_SUBSEQUENT_SEARCH.equals(event) ||
			 ||EVENT_CUST_ADD_SEARCH.equals(event) 
			 ||EVENT_CUST_ADD_SH_SEARCH.equals(event)
			 ||EVENT_CUST_ADD_SEARCH_FRAME.equals(event) 
			 ||EVENT_CUST_ADD_SH_SEARCH_FRAME.equals(event)
			 )
			 {
			//			
			objArray = new ICommand[1];
			objArray[0] = new CRCustSearchCommand();
			
			
			// Reuse List Counter Party Command 
			//objArray[0] = new ListCounterpartyCommand();
			//			
		} else if (EVENT_CUST_VIEW_DETAILS.equals(event)) {

			objArray = new ICommand[2];
			objArray[0] = new ProcessDetailsCustomerCommand();
			objArray[1] = new BorrowerListCommand();

		} else if (EVENT_CUST_ADD_SELECT.equals(event)) {

			objArray = new ICommand[1];
			objArray[0] = new AddNewCustCommand();

		} else if (EVENT_CUST_ADD_SH_SELECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddNewShareHolderCommand();
		}
		else if("return".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = new ReturnCustomerCommand();
		}
		return objArray;
	}

	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {

		// To return correct forward String for AJAX Search
		//if ("first_search".equals(event) || "subsequent_search".equals(event)) {
		//	Page aPage = new Page();
		//	aPage.setPageReference("ajax_search_cif_result");
		//	return aPage;
		//}
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {

		if (EVENT_PREPARE.equals(event) ) {
			return EVENT_CUST_PREPARE_SEARCH;
		
		} else if ( EVENT_CUST_ADD.equals(event)
				|| EVENT_CUST_SH_ADD.equals(event)) {
			
			return EVENT_CUST_PREPARE_SEARCH;
		
		} 
		else if ( EVENT_CUST_ADD_FRAME.equals(event)
				|| EVENT_CUST_SH_ADD_FRAME.equals(event)) {
			
			return EVENT_CUST_PREPARE_SEARCH_FRAME;
		
		} 
		else if (EVENT_CUST_SEARCH.equals(event)
		 || EVENT_CUST_FIRST_SEARCH.equals(event)
		 || EVENT_CUST_SUBSEQUENT_SEARCH.equals(event)
		) {

			return EVENT_CUST_RESULT;
		
		} 
		else if (EVENT_CUST_ADD_SEARCH.equals(event)
				|| EVENT_CUST_ADD_SH_SEARCH.equals(event)				
				) {

			return EVENT_CUST_ADD_RESULT;
			
		}
		else if ( EVENT_CUST_ADD_SEARCH_FRAME.equals(event)
				|| EVENT_CUST_ADD_SH_SEARCH_FRAME.equals(event)
				) {

			return EVENT_CUST_ADD_RESULT_FRAME;
		}
		else if("return".equals(event))
		{
		    return "return_page";
		}
	  else {
			return event;
		}
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

		String event = ((CRCustSearchForm) aForm).getEvent();
		
		if ( EVENT_CUST_SEARCH.equals(event) 
				|| EVENT_CUST_ADD_SELECT.equals(event)
				|| EVENT_CUST_ADD_SH_SELECT.equals(event)
				|| EVENT_CUST_ADD_SEARCH.equals(event)
        		|| EVENT_CUST_ADD_SH_SEARCH.equals(event)
				|| EVENT_CUST_ADD_SEARCH_FRAME.equals(event)
        		|| EVENT_CUST_ADD_SH_SEARCH_FRAME.equals(event)
				)
			return CRCustSearchFormValidator.validateInput(
					(CRCustSearchForm) aForm, locale);
					

		return new ActionErrors();
	}

	/**
	 * This method is to check if validation to form is needed
	 */
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CUST_SEARCH.equals(event)
				|| EVENT_CUST_ADD_SELECT.equals(event)
				|| EVENT_CUST_ADD_SH_SELECT.equals(event)
				|| EVENT_CUST_ADD_SEARCH.equals(event)
				|| EVENT_CUST_ADD_SH_SEARCH.equals(event)
				|| EVENT_CUST_ADD_SEARCH_FRAME.equals(event)
				|| EVENT_CUST_ADD_SH_SEARCH_FRAME.equals(event)
				) {
			result = true;
		}
		return result;
	}

	/**
	 * Getting error event.
	 */
	protected String getErrorEvent(String event) {
	
		String errorEvent = getDefaultEvent();
		if (EVENT_CUST_ADD_SELECT.equals(event)) {
			errorEvent = EVENT_CUST_ADD_SEARCH_FRAME;
		}
		else if (EVENT_CUST_ADD_SH_SELECT.equals(event)) {
			errorEvent = EVENT_CUST_ADD_SH_SEARCH_FRAME;
		} 
		else if (EVENT_CUST_ADD_SEARCH.equals(event)) {
			errorEvent = EVENT_CUST_ADD;
		}
		else if (EVENT_CUST_ADD_SH_SEARCH.equals(event)) {
			errorEvent = EVENT_CUST_SH_ADD;
		}
		else if (EVENT_CUST_ADD_SEARCH_FRAME.equals(event)) {
			errorEvent = EVENT_CUST_ADD_FRAME;
		}
		else if (EVENT_CUST_ADD_SH_SEARCH_FRAME.equals(event)) {
			errorEvent = EVENT_CUST_SH_ADD_FRAME;
		}
		
		
		return errorEvent;
	}

}
