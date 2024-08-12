/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalAction.java,v 1.4 2005/09/08 08:56:22 hshii Exp $
 */
package com.integrosys.cms.ui.srp.global;

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
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/08 08:56:22 $ Tag: $Name: $
 */
public class SRPGlobalAction extends CommonAction {

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
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("maker_prepare_srpglobal".equals(event) || "view_prepare_srpglobal".equals(event)
				|| "prepare_form".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalMakerPrepareCommand");
		}
		else if ("maker_edit_srpglobal_read".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalMakerReadCmd");
		}
		else if ("maker_edit_srpglobal_read_rejected".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalMakerReadRejectedCmd");
		}
		else if ("maker_edit_srpglobal".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalMakerEditCmd");
		}
		else if ("checker_edit_read".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalCheckerReadCmd");
		}
		else if ("srpglobal_maker_close".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalCheckerReadCmd");
		}
		else if ("checker_approve_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalCheckerApproveEditCmd");
		}
		else if ("checker_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalCheckerRejectEditCmd");
		}
		else if ("maker_cncl_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalMakerCancelEditCmd");
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalMakerEditCmd");
		}
		else if (EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalMakerReadCmd");
		}
		else if ("to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("SRPGlobalCheckerReadCmd");
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
		return SRPGlobalValidator.validateInput((SRPGlobalForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_add_srpglobal") || event.equals("maker_edit_srpglobal")
				|| event.equals("maker_edit_reject_add") || event.equals("maker_edit_reject_edit")
				|| "maker_edit_srpglobal_read".equals(event) || EVENT_VIEW.equals(event)) {
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
		if ("maker_add_srpglobal".equals(event)) {
			errorEvent = "maker_add_edit_srpglobal_error";
		}
		else if ("maker_edit_srpglobal".equals(event)) {
			errorEvent = "maker_add_edit_srpglobal_error";
		}
		else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_srpglobal_error";
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_srpglobal_error";
		}
		else if ("maker_edit_srpglobal_read".equals(event) || EVENT_VIEW.equals(event)) {
			errorEvent = "prepare_form";
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
		if ("view_prepare_srpglobal".equals(event) || "maker_prepare_srpglobal".equals(event)
				|| "prepare_form".equals(event)) {
			forwardName = "maker_prepare_srpglobal_page";
		}
		else if ("maker_edit_srpglobal_read".equals(event) || "maker_add_edit_srpglobal_error".equals(event)
				|| "maker_edit_srpglobal_read_rejected".equals(event)) {
			forwardName = "maker_add_edit_srpglobal_page";
		}
		else if ("maker_edit_srpglobal".equals(event)) {
			forwardName = "srpglobal_maker_edit_successful";
		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";
		}
		else if ("checker_edit_read".equals(event)) {
			forwardName = "checker_srpglobal_page";
		}
		else if ("checker_approve_edit".equals(event)) {
			forwardName = "common_approve_page";
		}
		else if ("srpglobal_maker_close".equals(event)) {
			forwardName = "srpglobal_maker_close_page";
		}
		else if ("checker_reject_edit".equals(event)) {
			forwardName = "common_reject_page";
		}
		else if ("maker_cncl_reject_edit".equals(event)) {
			forwardName = "common_close_page";
		}
		else if (EVENT_VIEW.equals(event)) {
			forwardName = EVENT_VIEW;
		}
		else if ("maker_add_edit_srpglobal_error".equals(event)) {
			forwardName = "maker_add_edit_srpglobal_page";
		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";
		}
		return forwardName;
	}
}