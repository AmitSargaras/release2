package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.bridgingloan.BridgingLoanAction;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class AdvsPaymentAction extends BridgingLoanAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if (EVENT_LIST_SUMMARY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListAdvsPaymentSummaryCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}

		return (objArray);
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		// DefaultLogger.debug(this, "Inside validate Input child class");
		// return FDRValidator.validateInput((FDRForm)aForm,locale);
		return null;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		/*
		 * DefaultLogger.debug(this, "Inside isValidationRequired");
		 * DefaultLogger.debug(this, "event="+event); if
		 * (EVENT_SUBMIT.equals(event) || EVENT_SAVE.equals(event)) { result =
		 * true; }
		 */
		return result;
	}

	protected String getErrorEvent(String event) {
		/*
		 * String errorEvent = getDefaultEvent(); if(EVENT_SUBMIT.equals(event)
		 * || EVENT_SAVE.equals(event)){ errorEvent = EVENT_PREPARE_FORM; }
		 * return errorEvent;
		 */
		return null;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, "\n>>>>>>>>>>> getNextPage");
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
		}
		else if (resultMap.get("event") != null) {
			aPage.setPageReference(getReference((String) resultMap.get("event")));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = FORWARD_PREFIX + event;
		DefaultLogger.debug(this, ">>>>>>>>>>> " + forwardName);
		return forwardName;
	}
}