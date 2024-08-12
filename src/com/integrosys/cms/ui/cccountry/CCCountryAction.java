/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/cccountry/CCCountryAction.java,v 1.6 2003/08/23 07:30:38 elango Exp $
 */
package com.integrosys.cms.ui.cccountry;

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
 * @author $Author: elango $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/23 07:30:38 $ Tag: $Name: $
 */
public class CCCountryAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("prepare".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCCCountryCommand();
		}
		else if ("country_law_list".equals(event) || "cancel".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListCCCountryCommand();
		}
		else if ("create_template_list".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateCCCountryCommand();
		}
		else if ("add_template_list".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddTemplateListCommand();
		}
		else if ("add_new_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCCCountryCommand();
		}
		else if ("add_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddTemplateCommand();
		}
		else if ("edit_item".equals(event)||"prepare_form".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditCCCountryTemplateCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCCCountryCommand();
		}
		else if ("process".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCCCountryCommand();
		}
		else if ("approve_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCCCountryCommand();
		}
		else if ("reject_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCCCountryCommand();
		}
		else if ("edit_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditCCCountryCommand();
		}
		else if ("close_template_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCCCountryCommand();
		}
		else if ("edit_staging_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCCCountryCommand();
		}
		else if ("update".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCCCountryCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCCCountryCommand();
		}
		else if ("view_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCCCountryCommand();
		}
		else if ("remove".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveItemCCCountryCommand();
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
		return CCCountryFormValidator.validateInput((CCCountryForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("add_item")) {
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
        if ((resultMap.get("template_error") != null) && ((String) resultMap.get("template_error")).equals("true")) {
            aPage.setPageReference("template_error");
            return aPage;
        }
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("add_item".equals(event)) {
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
		if ("country_law_list".equals(event) || "cancel".equals(event)) {
			forwardName = "after_country_law_list";
		}
		if ("create_template_list".equals(event) || "add_new_item".equals(event) || "add_item".equals(event)
				|| "edit_template_item".equals(event) || "edit_staging_template_item".equals(event)
				|| "cancel_list".equals(event) || "remove".equals(event)) {
			forwardName = "after_create_template_list";
		}
		if ("add_template_list".equals(event)) {
			forwardName = "after_add_template_list";
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
		if ("view_template_item".equals(event) || "close_template_item".equals(event) || "to_track".equals(event)) {
			forwardName = "after_view_template_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		return forwardName;
	}
}