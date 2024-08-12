/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/LimitsAction.java,v 1.9 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit.limitaccount;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.limit.LimitsAction;

public class LimitAccountAction extends LimitsAction {

	public static final String EVENT_REFRESH = "refresh";

	public static final String EVENT_RETURN = "return";

	public ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddLimitAccountCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateLimitAccountCommand();
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLimitAccountCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadLimitAccountCommand();
			objArray[1] = new PrepareLimitAccountCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLimitAccountCommand();
		}

		return objArray;
	}

	/**
	 * Method which determines whether a particular event has to be validated or
	 * not
	 * 
	 * @param event of type String
	 * @return boolean
	 */
	public boolean isValidationRequired(String event) {
		boolean validationRequired = false;
		if (EVENT_UPDATE.equals(event) || EVENT_CREATE.equals(event)) {
			validationRequired = true;
		}
		return validationRequired;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param cForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm cForm, Locale locale) {
		ActionErrors errors = null;
		errors = LimitAccountValidator.validateInput((LimitAccountForm) cForm, locale);
		return errors;
	}

	/**
	 * Method which determines which event to be called when error occurs.It is
	 * defaulted to the event returned by getDefaultEvent().The child classes
	 * can override this method to call their own events based on the event
	 * passed as a parameter.
	 * @param event of type String
	 * @return event of type String
	 */
	protected String getErrorEvent(String event) {
		String errorEvent = "";
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event)) {
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

		if (EVENT_PREPARE_UPDATE.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE);
		}
		else if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event)) {
			aPage.setPageReference(EVENT_RETURN);
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}
}