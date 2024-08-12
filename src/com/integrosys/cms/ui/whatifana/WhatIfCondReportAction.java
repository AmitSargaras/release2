/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.whatifana;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * The Action for What If Condition Action
 * @author $Author: siew kheat $
 */
public class WhatIfCondReportAction extends CommonAction {

	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	protected ICommand getCommand(String name) {
		ICommand command = (ICommand) getNameCommandMap().get(name);
		Validate.notNull(command, "not able to get command given name [" + name + "]");

		return command;
	}

	/**
	 * to be implemented by child classes
	 * @param event of type String
	 * @return Icommand Array
	 */
	protected ICommand[] getCommandChain(String event) {

		if (EVENT_NEW.equals(event)) {
			return new ICommand[] { getCommand("WhatIfCondReportPrepareCmd") };
		}
		else if (EVENT_GENERATE.equals(event)) {
			return new ICommand[] { getCommand("WhatIfCondReportGenerateNewCmd") };
		}
		else if (EVENT_GENERATE_INVALID.equals(event)) {
			return new ICommand[] { getCommand("WhatIfCondReportPrepareCmd") };
		}
		else if (EVENT_SAMPLE.equals(event)) {
			return new ICommand[] { getCommand("WhatIfCondReportSampleCmd"), getCommand("WhatIfCondReportPrepareCmd") };
		}
		else {
			DefaultLogger.debug(this, "Unhandled event = " + event);
		}

		return null;
	}

	/**
	 * to be implemented by child classes
	 * @param event of type String
	 * @param resultMap of type HashMap
	 * @param exceptionMap of type HashMap
	 * @return IPage
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {

		String forward = null;

		if (EVENT_NEW.equals(event)) {
			forward = FORWARD_NEW;
		}
		else if (EVENT_GENERATE.equals(event)) {
			forward = FORWARD_GENERATE_RESULT;
		}
		else if (EVENT_GENERATE_INVALID.equals(event)) {
			forward = FORWARD_NEW;
		}
		else if (EVENT_SAMPLE.equals(event)) {
			forward = FORWARD_NEW;
		}
		else {
			DefaultLogger.debug(this, "Unhandled event = " + event);
		}

		DefaultLogger.debug(this, "next page (struts forward) = " + forward);

		IPage nextPage = new Page();
		nextPage.setPageReference(forward);
		return nextPage;

	}

	/**
	 * method to return the default event
	 * @return String
	 */
	protected String getDefaultEvent() {
		return EVENT_NEW;
	}

	/**
	 * Method which determines whether a particular event has to be validated or
	 * not
	 * 
	 * @param event of type String
	 * @return boolean
	 */

	protected boolean isValidationRequired(String event) {
		return EVENT_GENERATE.equals(event);
	}

	protected ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return WhatIfCondReportValidator.validateInput((WhatIfCondReportForm) aForm, locale);
	}

	/**
	 * Method which determines which event to be called when error occurs.It is
	 * defaulted to the event returned by getDefaultEvent().The child classes
	 * can override this method to call their own events based on the event
	 * passed as a parameter.
	 * @param event of type String
	 * @return event of type String
	 * 
	 * 
	 */
	protected String getErrorEvent(String event) {
		if (EVENT_GENERATE.equals(event)) {
			return EVENT_GENERATE_INVALID;
		}
		else {
			DefaultLogger.debug(this, "Unhandled event = " + event);
		}

		return null;
	}

	/**
	 * For coming to the input page for entering inputs.
	 */
	public static final String EVENT_NEW = "new";

	/**
	 * For gathering the input and generating.
	 */
	public static final String EVENT_GENERATE = "generate";

	/**
	 * For the case where validation fails when gathering the input for
	 * generation.
	 */
	public static final String EVENT_GENERATE_INVALID = "generateInvalid";

	/**
	 * For viewing sample.
	 */
	public static final String EVENT_SAMPLE = "sample";

	/**
	 * The result page after "generate" button is clicked.
	 */
	public static final String FORWARD_GENERATE_RESULT = "generateResult";

	/**
	 * The input page for user to enter all the inputs.
	 */
	public static final String FORWARD_NEW = "new";
}
