/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/cccoltask/CCColTaskAction.java,v 1.9 2006/04/13 06:51:57 jzhai Exp $
 */
package com.integrosys.cms.ui.cccoltask;

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
 * @version $Revision: 1.9 $
 * @since $Date: 2006/04/13 06:51:57 $ Tag: $Name: $
 */
public class CCColTaskAction extends CommonAction {

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
			objArray[0] = new ListCCColCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("read_task".equals(event) || "view_task".equals(event) || "read_task_update".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadCCColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCCColTaskCommand();
		}
		else if ("prepare_form".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new FrameFlagSetterCommand();
			objArray[1] = new PrepareCCColTaskCommand();
		}
		else if ("maker_pre_reject".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadCCColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCCColTaskCommand();
		}
		else if ("maker_reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerRejectCCColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitCCColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveCCColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectCCColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("process".equals(event) || "edit_staging".equals(event) || "close_item".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadStagingCCColTaskCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCCColTaskCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseCCColTaskCommand();
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
		return CCColTaskFormValidator.validateInput((CCColTaskForm) aForm, locale);
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
		DefaultLogger.debug(this, "<<<<<< event: " + event);
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
		else if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		else if ("maker_pre_reject".equals(event)) {
			forwardName = "after_read_task";
		}
		else if ("maker_reject".equals(event)) {
			forwardName = "after_submit";
		}
		else if ("read_task".equals(event) || "prepare_form".equals(event) || "read_task_update".equals(event)) {
			forwardName = "after_read_task";
		}
		else if ("process".equals(event)) {
			forwardName = "after_process";
		}
		else if ("edit_staging".equals(event)) {
			forwardName = "after_edit_staging";
		}
		else if ("chk_view_recurrent".equals(event)) {
			forwardName = "after_chk_view_recurrent";
		}
		else if ("approve".equals(event)) {
			forwardName = "after_approve";
		}
		else if ("reject".equals(event)) {
			forwardName = "after_reject";
		}
		else if ("close_item".equals(event) || "view_task".equals(event) || "to_track".equals(event)) {
			forwardName = "after_close_item";
		}
		else if ("close".equals(event)) {
			forwardName = "after_close";
		}
		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}