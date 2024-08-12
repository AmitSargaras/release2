/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/bfltatparameters/BflTatParametersAction.java,v 1.2 2005/09/19 08:17:01 czhou Exp $
 */

package com.integrosys.cms.ui.bfltatparameters;

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
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/19 08:17:01 $ Tag: $Name: $
 */
public class BflTatParametersAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareBflTatParametersCommand();
		}
		else if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListBflTatParametersCommand();
		}

		return (objArray);
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class" + aForm.getClass());
		return BflTatParametersFormValidator.validateInput((BflTatParametersForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		// if (EVENT_PREPARE.equals(event)) {
		if (EVENT_LIST.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();

		if (EVENT_PREPARE.equals(event)) {
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
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = event;
		return forwardName;
	}
}
