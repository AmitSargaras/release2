package com.integrosys.cms.ui.report;

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
 * $Header:
 */

/**
 * Description: This action class returns an array of event handlers for
 * SystemReportSearchAction Each event handler is implemented using a command
 * object
 * 
 * @author $Author: rohaidah $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/12 12:06:41 $ Tag: $Name: $
 */
public class SystemReportSearchAction extends CommonAction {

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

	/**
	 * returns a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Entering method getCommandChain");
		ICommand objArray[] = null;

		if (EVENT_PREPARE_SEARCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareSystemReportSearchCommand");
		}
		else if (EVENT_SEARCH_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SearchSystemReportCommand");
		}
		else if (EVENT_GET_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("GetSystemReportCommand");
		}
		DefaultLogger.debug(this, "*******" + event + "================" + objArray);
		return (objArray);
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
		ActionErrors errors = SystemReportSearchValidator.validateInput((CommonForm) aForm, locale);
		DefaultLogger.debug(this, "validation errors ********" + errors + "********");
		return errors;
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

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		String nextPage = getReference(event);
		aPage.setPageReference(nextPage);
		return aPage;
	}

	/**
	 * returns the event that encountered errors
	 * @param event
	 * @return
	 */
	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (EVENT_SEARCH_REPORT.equals(event)) {
			errorEvent = EVENT_PREPARE_SEARCH;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		DefaultLogger.info(this, "Entering method getReference");

		String forwardName = EVENT_PREPARE_SEARCH;
		if ((EVENT_PREPARE_SEARCH).equals(event)) {
			forwardName = EVENT_PREPARE_SEARCH;
		}
		else if (EVENT_SEARCH_REPORT.equals(event)) {
			forwardName = EVENT_SEARCH_REPORT;
		}
		else if (EVENT_GET_REPORT.equals(event)) {
			forwardName = EVENT_GET_REPORT;
		}
		else {
			forwardName = event;
		}
		DefaultLogger.debug(this, "forwardName ******** " + forwardName + "********");
		return forwardName;
	}

	/**
	 * returns the default event when none is specified
	 * @return
	 */
	protected String getDefaultEvent() {
		return EVENT_PREPARE_SEARCH;
	}

}
