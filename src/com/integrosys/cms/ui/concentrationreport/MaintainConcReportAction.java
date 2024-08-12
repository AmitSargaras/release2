/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/concentrationreport/MaintainConcReportAction.java,v 1.2 2003/09/25 09:18:59 pooja Exp $
 */
package com.integrosys.cms.ui.concentrationreport;

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
 * @author $Author: pooja $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/25 09:18:59 $ Tag: $Name: $
 */
public class MaintainConcReportAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ((event != null) && event.equals("maker_edit_concreport_read")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadConcReportCmd();
		}
		else if ((event != null) && event.equals("maker_add_edit_concreport_error")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadConcReportCmd();
		} /*
		 * else if (event != null &&
		 * event.equals("maker_add_edit_concreport_error")) {
		 * DefaultLogger.debug(this, "Inside error event"); objArray = new
		 * ICommand[1]; objArray[0] = new MakerReadConcReportCmd(); }
		 */
		else if ((event != null) && event.equals("maker_edit_concreport")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditConcReportCmd();
		}
		else if ((event != null) && event.equals("checker_edit_read")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadConcReportCmd();
		}
		else if ((event != null) && event.equals("maker_close_concreport")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadConcReportCmd();
		}
		else if ((event != null) && event.equals("checker_approve_edit")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveEditConcReportCmd();
		}
		else if ((event != null) && event.equals("checker_reject_edit")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectEditConcReportCmd();
		}
		else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			objArray = new ICommand[2];
			objArray[0] = new MakerCnclEditCmd();
			objArray[1] = new MakerReadConcReportCmd();
		}
		else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditConcReportCmd();
		}
		else if ((event != null) && (event.equals("maker_view") || event.equals("checker_view"))) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadConcReportCmd();
		}
		else if ((event != null) && (event.equals("maker_edit_concreport_read_rejected"))) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadRejectedConcReportCmd();
		}
		else if ((event != null) && "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadConcReportCmd();
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
		return MaintainConcReportValidator.validateInput((MaintainConcReportForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		DefaultLogger.debug(this, "%%%Debug:::01:isValidationRequired(): event=" + event);
		if (event.equals("maker_add_concreport") || event.equals("maker_edit_concreport")
				|| event.equals("maker_edit_reject_add") || event.equals("maker_edit_reject_edit")) {
			DefaultLogger.debug(this, "%%%Debug:::02:isValidationRequired(): validationRequired=TRUE");
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
		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_add_concreport".equals(event)) {
			errorEvent = "maker_add_edit_concreport_error";
		}
		else if ("maker_edit_concreport".equals(event)) {
			errorEvent = "maker_add_edit_concreport_error";
		}
		else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_concreport_error";
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_concreport_error";
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
		if (((event != null) && (event.equals("maker_edit_concreport_read") || event
				.equals("maker_add_edit_concreport_error")))
				|| event.equals("maker_edit_concreport_read_rejected")) {
			forwardName = "maker_add_edit_concreport_page";
		}
		else if ((event != null) && event.equals("maker_edit_concreport")) {
			forwardName = "concreport_maker_edit_successful";
		}
		else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		else if ((event != null) && event.equals("checker_edit_read")) {
			forwardName = "checker_concreport_page";
		}
		else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		}
		else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}
		else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			forwardName = "common_close_page";
		}
		else if ((event != null) && event.equals("maker_view")) {
			forwardName = "maker_view_page";
		}
		else if ((event != null) && event.equals("checker_view")) {
			forwardName = "checker_view_page";
		}
		else if ((event != null) && event.equals("maker_close_concreport")) {
			forwardName = "maker_close_concreport_page";
		}
		else if ((event != null) && event.equals("maker_add_edit_concreport_error")) {
			forwardName = "maker_add_edit_concreport_page";
		}
		else if ((event != null) && ("to_track").equals(event)) {
			forwardName = "after_to_track";
		}
		return forwardName;
	}
}