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
 * MISReportSearchAction Each event handler is implemented using a command
 * object
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/09 07:42:59 $ Tag: $Name: $
 */
public class MISReportSearchAction extends CommonAction {

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

	public static final String EVENT_GEN_DEF_REPORT = "gen_def_report";

	/**
	 * returns a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Entering method getCommandChain");
		ICommand objArray[] = null;

		if (EVENT_PREPARE_SEARCH.equals(event) || "refresh".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareMISReportSearchCommand");
		}
		else if (EVENT_SEARCH_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SearchMISReportCommand");
		}
		else if (EVENT_GET_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("GetMISReportCommand");
		}
		else if (EVENT_GEN_DEF_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MISReportGenDeficiencyRptCmd");
		}
		
		else if ((event.equals("generate_monthly_basel_report"))) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.basel.report.GenerateReportCmd();
		}else if ((event.equals("download_monthly_basel_report"))) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.basel.report.DownloadReportCmd();
		}else if ((event.equals("download_lad_doc"))) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.basel.report.DownloadReportCmd();
		}
		
		else if ((event.equals("generate_daily_basel_open_report"))) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.basel.report.GenerateReportCmd();
		}else if ((event.equals("download_daily_basel_open_report"))) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.basel.report.DownloadReportCmd();
		}
		
		else if ((event.equals("generate_daily_basel_delete_report"))) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.basel.report.GenerateReportCmd();
		}else if ((event.equals("download_daily_basel_delete_report"))) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.basel.report.DownloadReportCmd();
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
		ActionErrors errors = MISReportSearchValidator.validateInput((CommonForm) aForm, locale);
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
		if (EVENT_SEARCH_REPORT.equals(event) || ("refresh".equals(event))) {
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
        else if ("refresh".equals(event)) {
            forwardName = EVENT_PREPARE_SEARCH;
        }
        else if (EVENT_GET_REPORT.equals(event) || EVENT_GEN_DEF_REPORT.equals(event)) {
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
