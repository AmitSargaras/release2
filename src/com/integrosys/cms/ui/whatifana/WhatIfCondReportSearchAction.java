package com.integrosys.cms.ui.whatifana;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/*
 * Copyright Integro Technologies Pte Ltd
 */

/**
 * Description: This action class returns an array of event handlers for
 * WhatIfCondReportSearchAction Each event handler is implemented using a
 * command object
 * 
 * @author $Author: Siew Kheat 
 */
public class WhatIfCondReportSearchAction extends CommonAction {

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

	public static final String EVENT_PREPARE_SEARCH = "prepare_search";

	public static final String EVENT_SEARCH_REPORT = "search_reports";

	public static final String EVENT_GET_REPORT = "get_report";

	public static final String EVENT_PREPARE_SEARCH_NOOP = "prepare_search_noop";

	/**
	 * gets the next page reference
	 * @param event
	 * @param resultMap
	 * @param exceptionMap
	 * @return
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		DefaultLogger.info(this, "Entering method getNextPage");
		Page aPage = new Page();
		String nextPage = getReference(event);
		aPage.setPageReference(nextPage);
		return aPage;
	}

	/**
	 * determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		DefaultLogger.info(this, "Entering method getReference");

		String forwardName = null;
		if (EVENT_SEARCH_REPORT.equals(event)) {
			forwardName = EVENT_SEARCH_REPORT;
		}
		else if (EVENT_GET_REPORT.equals(event)) {
			forwardName = EVENT_GET_REPORT;
		}
		else {
			forwardName = EVENT_PREPARE_SEARCH;
		}
		DefaultLogger.debug(this, "forwardName ******** " + forwardName + "********");
		return forwardName;
	}

	/**
	 * validates the HTML input data captured in the Action form and return the
	 * ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.info(this, "Entering method validateInput");
		ActionErrors errors = WhatIfCondReportSearchValidator.validateInput((CommonForm) aForm, locale);
		DefaultLogger.debug(this, "validation errors ********" + errors + "********");
		return errors;
	}

	/**
	 * returns the event that encountered errors
	 * @param event
	 * @return
	 */
	protected String getErrorEvent(String event) {
		DefaultLogger.info(this, "Entering method getErrorEvent");
		String errorEvent = getDefaultEvent();
		if (EVENT_SEARCH_REPORT.equals(event)) {
			errorEvent = EVENT_PREPARE_SEARCH;
		}
		return errorEvent;
	}

	/**
	 * decides whether the event require validation
	 * @param event
	 * @return
	 */
	protected boolean isValidationRequired(String event) {
		DefaultLogger.info(this, "Entering method isValidation");
		return EVENT_SEARCH_REPORT.equals(event);
	}

	/**
	 * returns an array of command objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Entering method getCommandChain");
		ICommand objArray[] = null;

		if (EVENT_SEARCH_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SearchWhatIfCondReportCommand");
		}
		else if (EVENT_GET_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("GetWhatIfCondReportCommand");
		}

		DefaultLogger.debug(this, "*******" + event + "================" + objArray);
		return (objArray);
	}

	/**
	 * returns the default event when none is specified
	 * @return
	 */
	protected String getDefaultEvent() {
		return EVENT_PREPARE_SEARCH;
	}

}
