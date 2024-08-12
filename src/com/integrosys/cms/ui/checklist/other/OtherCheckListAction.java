/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/security/SecurityCheckListAction.java,v 1.12 2006/04/13 07:35:37 jzhai Exp $
 */
package com.integrosys.cms.ui.checklist.other;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/04/13 07:35:37 $ Tag: $Name: $
 */
public class OtherCheckListAction extends CommonAction {

	private Map nameCommandMap;

	private boolean isMaintainChecklistWithoutApproval = false;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public void setMaintainChecklistWithoutApproval(boolean isMaintainChecklistWithoutApproval) {
		this.isMaintainChecklistWithoutApproval = isMaintainChecklistWithoutApproval;
	}

	private ICommand getCommandByName(String name) {
		return (ICommand) this.nameCommandMap.get(name);
	}

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
			objArray[0] = getCommandByName("ListOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("disable_other_col_task".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("DisableOtherColTaskCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("maintain".equals(event) || "view".equals(event) || "delete".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("MaintainOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("add_template_list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("AddCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("add_new_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("AddOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("remove_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("RemoveOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("SubmitOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("submit_delete".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("SubmitDeleteOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("process".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("ReadStagingOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("approve_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("ApproveOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("reject_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("RejectOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("update".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("UpdateOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("update_delete".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("UpdateDeleteOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("close_checklist_item".equals(event) || "edit_staging_checklist_item".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("ReadStagingOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("CloseOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("copy_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("CopyItemCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("link_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("LinkItemCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("replace_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("ReplaceItemCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("copy".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("CopyOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("link".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("LinkOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("replace".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommandByName("ReplaceOtherCheckListCommand");
			objArray[1] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("remove_error".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommandByName("FrameFlagSetterCommand");
		}
		else if ("back".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommandByName("FrameFlagSetterCommand");
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
		return OtherCheckListFormValidator.validateInput((OtherCheckListForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("add_template_item") || "copy".equals(event) || "replace".equals(event)) {
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
			String forwardName = null;
			if ((resultMap.get("deleteInd") != null) && ("true").equals((String) resultMap.get("deleteInd"))
					&& "edit_staging_checklist_item".equals(event)) {
				forwardName = frameCheck(getReference("delete"));
			}
			else {
				forwardName = frameCheck(getReference(event));
			}
			aPage.setPageReference(forwardName);
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("copy".equals(event)) {
			errorEvent = "prepare_form";
		}
		else if ("remove_item".equals(event)) {
			errorEvent = "remove_error";
		}
		else if ("replace".equals(event)) {
			errorEvent = "replace_item";
		}else if("reject_checklist_item".equals(event)){
			errorEvent = "reject_checklist_item_error";
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
		else if ("disable_other_col_task".equals(event)) {
			forwardName = "disable_other_task";
		}
		else if ("maintain".equals(event) || "add_new_item".equals(event) || "remove_item".equals(event)
				|| "edit_staging_checklist_item".equals(event) || "copy".equals(event) || "link".equals(event)
				|| "replace".equals(event) || "back".equals(event) || "remove_error".equals(event)) {
			forwardName = "after_maintain";
		}
		else if ("add_template_list".equals(event)) {
			forwardName = "after_add_template_list";
		}
		else if ("update".equals(event) || "submit_delete".equals(event) || "update_delete".equals(event)) {
			forwardName = "after_submit";
		}
		else if ("submit".equals(event)) {
			if (this.isMaintainChecklistWithoutApproval) {
				forwardName = "after_submit_wo_approval";
			}
			else {
				forwardName = "after_submit";
			}
		}
		else if ("process".equals(event) || "reject_checklist_item_error".equals(event)) {
			forwardName = "after_process";
		}
		else if ("approve_checklist_item".equals(event)) {
			forwardName = "after_approve_checklist_item";
		}
		else if ("reject_checklist_item".equals(event)) {
			forwardName = "after_reject_checklist_item";
		}
		else if ("view_checklist_item".equals(event) || "close_checklist_item".equals(event) || "view".equals(event)
				|| "to_track".equals(event)) {
			forwardName = "after_view_checklist_item";
		}
		else if ("delete".equals(event)) {
			forwardName = "after_delete_checklist";
		}
		else if ("close".equals(event)) {
			forwardName = "after_close";
		}
		else if ("copy_item".equals(event) || "prepare_form".equals(event)) {
			forwardName = "after_copy_item";
		}
		else if ("link_item".equals(event)) {
			forwardName = "after_link_item";
		}
		else if ("replace_item".equals(event)) {
			forwardName = "after_replace_item";
		}

		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}

}