/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/seccountry/SecurityCountryAction.java,v 1.7 2003/10/28 09:02:07 hltan Exp $
 */
package com.integrosys.cms.ui.seccountry;

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
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/10/28 09:02:07 $ Tag: $Name: $
 */
public class SecurityCountryAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareSecurityCountryCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("country_sec_list".equals(event) || "cancel".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new ListSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("create_template_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new CreateSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("create_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new PrepareAddTemplateItemCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("add_template_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new AddSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("edit_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new PrepareAddTemplateItemCommand();
			// objArray[2] = new EditTemplateItemCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new SubmitSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("process".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadStagingSecurityCountryCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve_template_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveSecurityCountryCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject_template_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectSecurityCountryCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("edit_template_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new EditSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("close_template_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadStagingSecurityCountryCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("edit_staging_template_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new ReadStagingSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("update".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new UpdateSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseSecurityCountryCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("view_template_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new EditSecurityCountryCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("prepare_form".equals(event) || "cancel_list".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new PrepareAddTemplateItemCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("remove".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareLeftFrameSecurityCountryCommand();
			objArray[1] = new RemoveItemSecurityCountryCommand();
		}
		DefaultLogger.debug(this, "event is ==================" + event);
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
		return SecurityCountryFormValidator.validateInput((SecurityCountryForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("add_template_item")) {
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
		if ((resultMap.get("no_template") != null) && ((String) resultMap.get("no_template")).equals("true")) {
			aPage.setPageReference("no_template");
			return aPage;
		}
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
			String forwardName = frameCheck(getReference(event));
			aPage.setPageReference(forwardName);
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("add_template_item".equals(event)) {
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
		if (EVENT_PREPARE.equals(event)) {
			forwardName = "after_prepare";
		}
		if ("country_sec_list".equals(event) || "cancel".equals(event)) {
			forwardName = "after_country_sec_list";
		}
		if ("create_template_item".equals(event) || "add_template_item".equals(event)
				|| "edit_template_item".equals(event) || "edit_staging_template_item".equals(event)
				|| "cancel_list".equals(event) || "remove".equals(event)) {
			forwardName = "after_create_template_item";
		}
		if ("create_item".equals(event) || "edit_item".equals(event) || "prepare_form".equals(event)) {
			forwardName = "after_create_item";
		}
		if ("submit".equals(event) || "update".equals(event)) {
			forwardName = "after_submit";
		}
		if ("process".equals(event)) {
			forwardName = "after_process";
		}
		if ("approve_template_item".equals(event)) {
			forwardName = "after_approve_template_item";
		}
		if ("reject_template_item".equals(event)) {
			forwardName = "after_reject_template_item";
		}
		if ("view_template_item".equals(event)) {
			forwardName = "after_view_template_item";
		}
		if ("close_template_item".equals(event) || "to_track".equals(event)) {
			forwardName = "after_close_template_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}

}