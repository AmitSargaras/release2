/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/recurrent/RecurrentCheckListAction.java,v 1.14 2005/05/03 10:50:04 hshii Exp $
 */
package com.integrosys.cms.ui.checklist.recurrent;

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
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2005/05/03 10:50:04 $ Tag: $Name: $
 */
public class RecurrentCheckListAction extends CommonAction {

	private Map nameCommandMap;

	private boolean isMaintainChecklistWithoutApproval = false;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setMaintainChecklistWithoutApproval(boolean isMaintainChecklistWithoutApproval) {
		this.isMaintainChecklistWithoutApproval = isMaintainChecklistWithoutApproval;
	}

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("list".equals(event) || EVENT_READ.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("RecurrentAccessControlCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("ListRecurrentCheckListCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("add_covenant".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("AddCovenantCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("remove_covenant".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("RemoveCovenantCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("prepare_add_covenant".equals(event)) {
			objArray = new ICommand[2];// cr 26
			objArray[0] = (ICommand) getNameCommandMap().get("PrepareRecurrentCommand");// cr26
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("prepare_add_recurrent".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("PrepareRecurrentCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("add_recurrent".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("AddRecurrentCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("remove_recurrent".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("RemoveRecurrentCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("view_recurrent_item".equals(event) || "view_recurrent_item_to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadRecurrentCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("edit_recurrent_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadRecurrentCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("PrepareRecurrentCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("prepare_edit_covenant".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("PrepareRecurrentCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("edit_covenant_item".equals(event) || "view_covenant_item".equals(event)
				|| "view_covenant_item_to_track".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadCovenantCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("PrepareRecurrentCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("update_covenant".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("UpdateCovenantCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("update_recurrent".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("UpdateRecurrentCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("SubmitRecurrentCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("process".equals(event) || "edit_staging_checklist_item".equals(event)
				|| "close_checklist_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadStagingRecurrentCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("chk_view_covenant".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerViewCovenantItemCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("chk_view_recurrent".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerViewRecurrentItemCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("approve_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ApproveRecurrentCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("reject_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("RejectRecurrentCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("CloseRecurrentCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("view_ok".equals(event) || "return".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("save".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("SaveRecurrentCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
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
		// return
		// SecurityReceiptFormValidator.validateInput((SecurityReceiptForm
		// )aForm,locale);
		RecurrentCheckListForm form = (RecurrentCheckListForm) aForm;
		if ("add_covenant".equals(form.getEvent()) || "update_covenant".equals(form.getEvent())) {
			return CovenantFormValidator.validateInput((RecurrentCheckListForm) aForm, locale);
		}
		else {
			return RecurrentCheckListFormValidator.validateInput((RecurrentCheckListForm) aForm, locale);
		}
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("add_recurrent".equals(event) || "update_recurrent".equals(event) || "add_covenant".equals(event)
				|| "update_covenant".equals(event) || "submit".equals(event) || "save".equals(event)) {
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
		if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
			String forwardName = frameCheck(getReference(event));
			aPage.setPageReference(forwardName);
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
		if ("add_recurrent".equals(event) || "update_recurrent".equals(event)) {
			errorEvent = "prepare_add_recurrent";
		}
		else if ("add_covenant".equals(event)) {
			errorEvent = "prepare_add_covenant";
		}
		else if ("update_covenant".equals(event)) {
			errorEvent = "prepare_edit_covenant";
		}
		else if ("submit".equals(event) || "save".equals(event)) {
			errorEvent = "list";
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
		if ("list".equals(event) || "add_covenant".equals(event) || "remove_covenant".equals(event)
				|| "add_recurrent".equals(event) || "remove_recurrent".equals(event) || "update_covenant".equals(event)
				|| "edit_staging_checklist_item".equals(event) || "view_ok".equals(event)
				|| "update_recurrent".equals(event)) {
			forwardName = "after_list";
		}
		if ("prepare_add_covenant".equals(event)) {
			forwardName = "after_prepare_add_covenant";
		}
		else if ("prepare_add_recurrent".equals(event) || "edit_recurrent_item".equals(event)) {
			forwardName = "after_prepare_add_recurrent";
		}
		else if ("view_recurrent_item".equals(event) || "view_recurrent_item_to_track".equals(event)) {
			forwardName = "after_view_recurrent_item";
			/*
			 * } else if("edit_recurrent_item".equals(event)) { forwardName =
			 * "after_edit_recurrent_item";
			 */
		}
		else if ("prepare_edit_covenant".equals(event) || "edit_covenant_item".equals(event)) {
			forwardName = "after_edit_covenant_item";
		}
		else if ("view_covenant_item".equals(event) || "view_covenant_item_to_track".equals(event)) {// cr26
			// ,
			// added
			// ...
			// to_track
			forwardName = "after_view_covenant_item";
		}
		else if ("submit".equals(event)) {
			if (isMaintainChecklistWithoutApproval) {
				forwardName = "after_submit_wo_approval";
			}
			else {
				forwardName = "after_submit";
			}
		}
		else if ("save".equals(event)) {
			forwardName = "after_save";
		}
		else if ("process".equals(event)) {
			forwardName = "after_process";
		}
		else if ("chk_view_covenant".equals(event)) {
			forwardName = "after_chk_view_covenant";
		}
		else if ("chk_view_recurrent".equals(event)) {
			forwardName = "after_chk_view_recurrent";
		}
		else if ("approve_checklist_item".equals(event)) {
			forwardName = "after_approve_checklist_item";
		}
		else if ("reject_checklist_item".equals(event)) {
			forwardName = "after_reject_checklist_item";
		}
		else if ("close_checklist_item".equals(event) || "to_track".equals(event) || EVENT_READ.equals(event)
				|| "return".equals(event)) {
			forwardName = "after_close_checklist_item";
		}
		else if ("close".equals(event)) {
			forwardName = "after_close";
		}

		return forwardName;
	}

	private String frameCheck(String forwardName) {

		return forwardName.concat("_WF");
	}
}