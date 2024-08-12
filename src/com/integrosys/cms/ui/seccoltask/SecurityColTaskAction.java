/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/seccoltask/SecurityColTaskAction.java,v 1.7 2006/04/13 07:41:50 jzhai Exp $
 */
package com.integrosys.cms.ui.seccoltask;

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
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/04/13 07:41:50 $ Tag: $Name: $
 */
public class SecurityColTaskAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListSecurityColCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("read_task".equals(event) || "view_task".equals(event) || "read_task_update".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("maker_pre_reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("maker_reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerRejectSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("process".equals(event) || "edit_staging".equals(event) || "close_item".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadStagingSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseSecurityColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("refresh".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new FrameFlagSetterCommand();
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
		return SecurityColTaskFormValidator.validateInput((SecurityColTaskForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("submit")) {
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
		if (resultMap.get("error") != null) {
			aPage.setPageReference("info");
			return aPage;
		}
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
			if ("refresh".equals(event)) {
				DefaultLogger.debug(this, "<<<<<<<<< originEvent: " + (String) resultMap.get("originEvent"));
				String forwardName = frameCheck(getReference((String) resultMap.get("originEvent")));
				aPage.setPageReference(forwardName);
				return aPage;
			}

			String forwardName = frameCheck(getReference(event));
			aPage.setPageReference(forwardName);
			return aPage;
		}

		if ("refresh".equals(event)) {
			DefaultLogger.debug(this, "<<<<<<<<< originEvent: " + (String) resultMap.get("originEvent"));
			aPage.setPageReference(getReference((String) resultMap.get("originEvent")));
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("total".equals(event) || "submit".equals(event)) {
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
		if ("list".equals(event)) {
			forwardName = "after_list";
		}
		if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		if ("maker_pre_reject".equals(event)) {
			forwardName = "after_read_task";
		}
		if ("maker_reject".equals(event)) {
			forwardName = "after_submit";
		}
		if ("read_task".equals(event) || "prepare_form".equals(event) || "read_task_update".equals(event)) {
			forwardName = "after_read_task";
		}
		if ("process".equals(event)) {
			forwardName = "after_process";
		}
		if ("edit_staging".equals(event)) {
			forwardName = "after_edit_staging";
		}
		if ("chk_view_recurrent".equals(event)) {
			forwardName = "after_chk_view_recurrent";
		}
		if ("approve".equals(event)) {
			forwardName = "after_approve";
		}
		if ("reject".equals(event)) {
			forwardName = "after_reject";
		}
		if ("close_item".equals(event) || "view_task".equals(event) || "to_track".equals(event)) {
			forwardName = "after_close_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		if ("edit_covenant".equals(event)) {
			forwardName = "after_edit_covenant";
		}
		if ("edit_recurrent".equals(event)) {
			forwardName = "after_edit_recurrent";
		}
		if ("print_doc".equals(event)) {
			forwardName = "after_print_doc";
		}
		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}