/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/MaintainSystemParametersAction.java,v 1.11 2005/09/09 11:56:07 hshii Exp $
 */
package com.integrosys.cms.ui.systemparameters;

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
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/09/09 11:56:07 $ Tag: $Name: $
 */
public class MaintainSystemParametersAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("maker_edit_sysparams_read".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadSystemParametersCmd();
		}
		else if ("maker_edit_sysparams".equals(event) || "maker_edit_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditSystemParametersCmd();
		}
		else if ("checker_edit_redirect_read".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RedirectCheckerCommand();
		}
		else if ("checker_edit_read".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadSystemParametersCmd();
		}
		else if ("maker_close_sysparams".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadSystemParametersCmd();
		}
		else if ("maker_close_redirect_sysparams".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RedirectMakerCommand();
		}
		else if ("checker_approve_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveEditSystemParametersCmd();
		}
		else if ("checker_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectEditSystemParametersCmd();
		}
		else if ("maker_cncl_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCnclEditCmd();
			// objArray[1] = new MakerReadSystemParametersCmd();
		}
		else if (EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadSystemParametersCmd();
		}
		else if ("maker_edit_sysparams_read_rejected".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadRejectedSystemParametersCmd();
		}
		else if ("maker_edit_sysparams_redirect_read_rejected".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RedirectMakerCommand();
		}
		else if ("to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadSystemParametersCmd();
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
		return MaintainSystemParametersValidator.validateInput((MaintainSystemParametersForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_add_sysparams") || event.equals("maker_edit_sysparams")
				|| event.equals("maker_edit_reject_add") || event.equals("maker_edit_reject_edit")) {
			result = true;
		}
		return result;
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
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}
		if ((resultMap.get("redirectChecker") != null) && (resultMap.get("redirectChecker")).equals("SP")) {
			DefaultLogger.debug(this, "Inside SP redirectChecker");
			aPage.setPageReference("checker_general_parameter");
			return aPage;
		}
		if ((resultMap.get("redirectChecker") != null) && (resultMap.get("redirectChecker")).equals("CR")) {
			DefaultLogger.debug(this, "Inside CR redirectChecker");
			aPage.setPageReference("checker_conc_report");
			return aPage;
		}
		if ((resultMap.get("redirectMaker") != null) && (resultMap.get("redirectMaker")).equals("SP")) {
			DefaultLogger.debug(this, "Inside SP redirectMaker");
			aPage.setPageReference("maker_general_parameter");
			return aPage;
		}
		if ((resultMap.get("redirectMaker") != null) && (resultMap.get("redirectMaker")).equals("CR")) {
			DefaultLogger.debug(this, "Inside CR redirectMaker");
			aPage.setPageReference("maker_conc_report");
			return aPage;
		}
		if ((resultMap.get("redirectMakerClose") != null) && (resultMap.get("redirectMakerClose")).equals("SP")) {
			DefaultLogger.debug(this, "Inside SP Close");
			aPage.setPageReference("maker_close_general_parameter");
			return aPage;
		}
		if ((resultMap.get("redirectMakerClose") != null) && (resultMap.get("redirectMakerClose")).equals("CR")) {
			DefaultLogger.debug(this, "Inside CR Close");
			aPage.setPageReference("maker_close_conc_report");
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;

	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_add_sysparams".equals(event)) {
			errorEvent = "maker_add_edit_sysparams_error";
		}
		else if ("maker_edit_sysparams".equals(event)) {
			errorEvent = "maker_add_edit_sysparams_error";
		}
		else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_sysparams_error";
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_sysparams_error";
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ("maker_edit_sysparams_read".equals(event) || "maker_add_edit_sysparams_error".equals(event)
				|| "maker_edit_sysparams_read_rejected".equals(event)) {
			forwardName = "maker_add_edit_sysparams_page";
		}
		else if ("maker_edit_sysparams".equals(event)) {
			forwardName = "sysparams_maker_edit_successful";
		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";
		}
		else if ("checker_edit_read".equals(event)) {
			forwardName = "checker_sysparams_page";
		}
		else if ("checker_approve_edit".equals(event)) {
			forwardName = "common_approve_page";
		}
		else if ("checker_reject_edit".equals(event)) {
			forwardName = "common_reject_page";
		}
		else if ("maker_cncl_reject_edit".equals(event)) {
			forwardName = "common_close_page";
		}
		else if ("maker_close_sysparams".equals(event)) {
			forwardName = "maker_close_sysparams_page";
		}
		else if ("maker_add_edit_sysparams_error".equals(event)) {
			forwardName = "maker_add_edit_sysparams_page";
		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";
		}
		else if (EVENT_VIEW.equals(event)) {
			forwardName = EVENT_VIEW;
		}
		return forwardName;
	}
}