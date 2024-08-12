/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/reassignment/ReassignmentAction.java,v 1.1 2004/09/21 09:16:45 hshii Exp $
 */
package com.integrosys.cms.ui.reassignment;

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
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/21 09:16:45 $ Tag: $Name: $
 */
public class ReassignmentAction extends CommonAction {

	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this + " getCommandChain", "event is: " + event);
		ICommand objArray[] = null;

		if (EVENT_SEARCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SearchReassignmentCommand();
		}
		else if (EVENT_FORWARD.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ForwardReassignmentCommand();
		}

		return objArray;
	}

	/**
	 * method to return the default event
	 * @return String
	 */
	protected String getDefaultEvent() {
		return EVENT_PREPARE;
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
		DefaultLogger.debug(this, "Inside validate Input class");
		return ReassignmentValidator.validateInput((ReassignmentForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_SEARCH.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;

		if (EVENT_SEARCH.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}
		return errorEvent;
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
		if (EVENT_SEARCH.equals(event)) {
			aPage.setPageReference((String) resultMap.get("reassignmentType"));
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}
}
