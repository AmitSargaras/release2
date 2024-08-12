/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/ccmaster/CCMasterAction.java,v 1.7 2003/10/04 13:21:05 hltan Exp $
 */
package com.integrosys.cms.ui.ccmaster;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.ccglobal.CCGlobalForm;
import com.integrosys.cms.ui.ccglobal.CCGlobalFormValidator;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/10/04 13:21:05 $ Tag: $Name: $
 */
public class CCMasterAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("master_cc_list".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListCCMasterCommand();
		}
		else if ("create_cc_master".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateCCMasterCommand();
		}
		else if ("edit_cc_master".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditCCMasterCommand();
		}
		else if ("add_cc_master".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddListCCMasterCommand();
		}
		else if ("add_new_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddCCMasterCommand();
		}
		else if ("submit_cc_master".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCCMasterCommand();
		}
		else if ("view_cc_master".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCCMasterCommand();
		}
		else if ("remove_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveItemCCMasterCommand();
		}
		else if ("process".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCCMasterCommand();
		}
		else if ("approve_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCCMasterCommand();
		}
		else if ("reject_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCCMasterCommand();
		}
		else if ("update_cc_master".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCCMasterCommand();
		}
		else if ("close_template_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCCMasterCommand();
		}
		else if ("edit_staging_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCCMasterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCCMasterCommand();
		}
		DefaultLogger.debug(this, "event is =================="+event);
		return (objArray);
	}

	/**
	 * Method which returns default event
	 * @return default event
	 */
	public String getDefaultEvent() {
		return "master_cc_list";
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
		return CCGlobalFormValidator.validateInput((CCGlobalForm) aForm, locale);
    }

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("create_doc_item") || event.equals("update_doc_item")
				|| (event.equals("update_staging_doc_item"))) {
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
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("create_doc_item".equals(event)) {
			errorEvent = "prepare_create_doc_item";
		}
		if ("update_doc_item".equals(event) || "update_staging_doc_item".equals(event)) {
			errorEvent = "prepare_edit_doc_item";
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
		if ("master_cc_list".equals(event)) {
			forwardName = "after_master_cc_list";
		}
		if ("edit_cc_master".equals(event) || "add_new_item".equals(event) || "remove_item".equals(event)) {
			forwardName = "after_edit_cc_master";
		}
		if ("create_cc_master".equals(event) || "edit_staging_template_item".equals(event)) {
			forwardName = "after_edit_cc_master";
		}
		if ("add_cc_master".equals(event)) {
			forwardName = "after_add_cc_master";
		}
		if ("submit_cc_master".equals(event) || ("update_cc_master").equals(event)) {
			forwardName = "after_submit_cc_master";
		}
		if ("view_cc_master".equals(event) || "close_template_item".equals(event) || "to_track".equals(event)) {
			forwardName = "after_view_cc_master";
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
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		return forwardName;
	}
}