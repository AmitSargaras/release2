/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/secmaster/FacilityMasterAction.java,v 1.6 2003/10/28 08:58:58 hltan Exp $
 */
package com.integrosys.cms.ui.facmaster;

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
 * @version $Revision: 1.6 $
 * @since $Date: 2003/10/28 08:58:58 $ Tag: $Name: $
 */
public class FacilityMasterAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("prepare_sectype".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareFacilityMasterCommand();
		}
		else if ("show_subtype_list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new FacilityListCommand();
			objArray[1] = new PrepareFacilityMasterCommand();
		}
		else if ("checker_prepare_sectype".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new FacilityListCommand();
			objArray[1] = new PrepareFacilityMasterCommand();
		}
		else if ("create_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateFacilityMasterCommand();
		}
		else if ("edit_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditFacilityMasterCommand();
		}
		else if ("add_template_item".equals(event)||"add_template_item_resubmit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddFacilityMasterCommand();
		}
		else if ("edit_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditTemplateItemCommand();
		}
		else if ("submit_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitFacilityMasterCommand();
		}
		else if ("approve_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveFacilityMasterCommand();
		}
		else if ("reject_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectFacilityMasterCommand();
		}
		else if ("process".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingFacilityMasterCommand();
		}
		else if ("edit_staging_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingFacilityMasterCommand();
		}
		else if ("update_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateFacilityMasterCommand();
		}
		else if ("close_template_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingFacilityMasterCommand();
		}
		else if ("view_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingFacilityMasterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseFacilityMasterCommand();
		}
		else if ("prepare_add_template_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAddTemplateItemCommand();
		}
		else if ("add_facility_master".equals(event)||"add_facility_master_resubmit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddListFacilityMasterCommand();
		}
		else if ("remove_item".equals(event)||"remove_item_resubmit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveItemFacilityMasterCommand();
		}
		
		DefaultLogger.debug(this, "event is =================="+event);
		return (objArray);
	}

	/**
	 * Method which returns default event
	 * @return default event
	 */
	public String getDefaultEvent() {
		return "prepare_sectype";
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
		return FacilityMasterFormValidator.validateInput((FacilityMasterForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("add_template_item")) {
			result = false;
		}
		if(event.equals("view_template_item"))
			result=true;
		if(event.equals("edit_template_item"))
		    result=true;
		if(event.equals("submit_template_item"))
		    result=true;
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
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("add_template_item".equals(event)) {
			//errorEvent = "prepare_add_template_item";
		}
		if ("reject_template_item".equals(event)) {
			errorEvent = "reject_template_item_error";
		}
		if ("view_template_item".equals(event)) {
			errorEvent = "after_view_template_item_error";
		}
		if ("edit_template_item".equals(event)) {
		errorEvent = "after_create_template_item_error";
	}
		if ("submit_template_item".equals(event)) {
			errorEvent = "after_submit_template_item_error";
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
		if ("prepare_sectype".equals(event)) {
			forwardName = "after_prepare_sectype";
		}
		if("after_view_template_item_error".equalsIgnoreCase(event)){
			forwardName = "after_view_template_item";
		}
		if("after_create_template_item_error".equalsIgnoreCase(event)){
			forwardName = "after_create_template_item";
		}
		if ("show_subtype_list".equals(event)) {
			forwardName = "after_show_subtype_list";
		}
		if ("checker_prepare_sectype".equals(event)) {
			forwardName = "after_show_subtype_list";
		}
		if ("create_template_item".equals(event) || "edit_template_item".equals(event)
				|| "add_template_item".equals(event)|| "back".equals(event) || "remove_item".equals(event)) {
			forwardName = "after_create_template_item";
		}
		if ("edit_staging_template_item".equals(event)|| "add_template_item_resubmit".equals(event)) {
			forwardName = "after_resubmit_template_item";
		}
		if ("prepare_add_template_item".equals(event) || "edit_item".equals(event)) {
			forwardName = "after_prepare_add_template_item";
		}
		if ("submit_template_item".equals(event) || "update_template_item".equals(event)) {
			forwardName = "after_submit_template_item";
		}
		if ("after_submit_template_item_error".equals(event)) {
			forwardName = "after_submit_template_item_error";
		}
		if ("process".equals(event)) {
			forwardName = "after_process";
		}
		if ("add_facility_master".equals(event)||"add_facility_master_resubmit".equals(event)) {
			forwardName = "after_add_facility_master";
		}
		if ("approve_template_item".equals(event)) {
			forwardName = "after_approve_template_item";
		}
		if ("reject_template_item".equals(event)) {
			forwardName = "after_reject_template_item";
		}
		if ("close_template_item".equals(event) || "view_template_item".equals(event) || "to_track".equals(event)) {
			forwardName = "after_view_template_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		if ("reject_template_item_error".equals(event)) {
			forwardName = "after_process";
		}
		return forwardName;
	}
}