/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommDealSearchAction.java,v 1.3 2004/06/07 03:34:31 pooja Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

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
 * Description
 * 
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/07 03:34:31 $ Tag: $Name: $
 */
public class CommDealSearchAction extends CommonAction {
	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_SEARCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CommDealSearchCommand();
		}
		if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CommDealSearchCommand();
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
		return CommDealSearchValidator.validateInput((CommDealSearchForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_LIST.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_LIST.equals(event)) {
			errorEvent = EVENT_SEARCH;
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

		if ((EVENT_SEARCH.equals(event))) {
			forwardName = "after_search";
		}
		if (EVENT_LIST.equals(event)) {
			forwardName = "after_list";
		}
		return forwardName;
	}
}
