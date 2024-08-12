package com.integrosys.cms.ui.collateral;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

public class CollateralSearchAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_SEARCH.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new CollateralSearchCommand();
			objArray[1] = new RefreshCollateralCommand();
			objArray[2] = new PrepareCollateralSearchCommand();
		}
		if (EVENT_LIST.equals(event) || "list1".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CollateralSearchCommand();
		}
		if ("refresh".equals(event) || "refresh_create".equals(event) || 
				"prepare_form".equals(event) || "prepare_create".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshCollateralCommand();
			objArray[1] = new PrepareCollateralSearchCommand();
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
		return CollateralSearchFormValidator.validateInput((CollateralSearchForm) aForm, locale);
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
		if ("refresh".equals(event)) {
			errorEvent = "search";
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
	 * 
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";

		if ((EVENT_SEARCH.equals(event)) || ("prepare_form".equals(event))) {
			forwardName = "after_search";
		}
		
		if (EVENT_LIST.equals(event)) {
			forwardName = "after_list";
		}

		if ("list1".equals(event)) {
			forwardName = "after_list";
		}

		if ("refresh".equals(event)) {
			forwardName = "after_search";
		}
		
		if ("prepare_create".equals(event)) {
			forwardName = "prepare_create";
		}
		
		if ("refresh_create".equals(event)) {
			forwardName = "refresh_create";
		}
		return forwardName;
	}
}
