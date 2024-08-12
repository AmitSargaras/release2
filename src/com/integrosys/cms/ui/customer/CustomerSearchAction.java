/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/CustomerSearchAction.java,v 1.7 2006/09/30 09:14:27 jzhai Exp $
 */

package com.integrosys.cms.ui.customer;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/30 09:14:27 $ Tag: $Name: $
 */
public class CustomerSearchAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */
	private static final String REROUTEPAGE = "reroutepage";

	private static final String SUBSTITUTEPAGE = "substitutepage";

	private static final String REASSIGNPAGE = "reassignpage";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event) || "prepare_form".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCustomerSearchCommand();
		}
		else if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListCustomerCommand();
		}
		else if ("return".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCustomerCommand();
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
		DefaultLogger.debug(this, "Inside validate Input child class");
		return CustomerSearchFormValidator.validateInput((CustomerSearchForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("list")) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("list".equals(event)) {
			errorEvent = "prepare_form";
		}
		return errorEvent;
	}

	/**
	 * This method is used to determine the next page to be displayed using the
	 * event Result hashmap and exception hashmap.It returns the page object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (EVENT_PREPARE.equals(event) || "prepare_form".equals(event)) {
			forwardName = "after_prepare";
		}
		else if (EVENT_LIST.equals(event)) {
			forwardName = "after_list";
		}
		else if ("return".equals(event)) {
			forwardName = "after_return";
		}
		else if (EVENT_SEARCH.equals(event)) {
			forwardName = "after_search";
		}
		else if (REROUTEPAGE.equals(event) || REASSIGNPAGE.equals(event) || SUBSTITUTEPAGE.equals(event)) {
			forwardName = event;
		}

		return forwardName;
	}
}
