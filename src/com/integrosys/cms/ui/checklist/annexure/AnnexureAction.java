/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/recreceipt/RecurrentReceiptAction.java,v 1.14 2005/05/03 10:48:06 hshii Exp $
 */
package com.integrosys.cms.ui.checklist.annexure;

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
import com.integrosys.cms.ui.checklist.recreceipt.RecurrentCheckListFormValidator;
import com.integrosys.cms.ui.checklist.recreceipt.RecurrentReceiptForm;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2005/05/03 10:48:06 $ Tag: $Name: $
 */
public class AnnexureAction extends CommonAction {

	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}
	
	public static final String EVENT_MAINTAIN_ANNEXURE_LIST = "maintain_annexure_list";
	public static final String EVENT_CHECKER_ANNEXURE_LIST = "checker_annexure_list";
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 *
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("list".equals(event) || EVENT_READ.equals(event) 
				|| EVENT_MAINTAIN_ANNEXURE_LIST.equals(event) || EVENT_CHECKER_ANNEXURE_LIST.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("RecurrentCheckListAction.RecurrentAccessControlCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("ListAnnexureCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("edit_annexure_item".equals(event) || "view_annexure_item".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadAnnexureCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("ReadAnnexureHistoryCommand");
			objArray[2] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("update_annexure_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("UpdateAnnexureCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("SubmitAnnexureCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("process".equals(event) || "edit_staging_checklist_item".equals(event)
				|| "close_checklist_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadStagingAnnexureCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("chk_view_annexure".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerViewAnnexureItemCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("approve_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ApproveAnnexureCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("reject_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("RejectAnnexureCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("CloseAnnexureCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("view_ok".equals(event) || "return".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CCReceiptAction.FrameFlagSetterCommand");
		}
		else if ("prepare_add_annexure".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadAnnexureHistoryCommand");
		}
		else if ("save".equals(event)) { // bernard - added
			// save_annexure_covenant
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("SaveAnnexureCommand");
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
		return RecurrentCheckListFormValidator.validateInput((RecurrentReceiptForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("update_annexure_item".equals(event) || "update_covenant_item".equals(event) ||
				"submit".equals(event) || "save".equals(event)) {
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
		if ("update_annexure_item".equals(event)) {
			errorEvent = "edit_annexure_item";
		}
		if ("update_covenant_item".equals(event)) {
			errorEvent = "edit_covenant";
		}
		if (("save").equals(event) || ("submit").equals(event)) {
			errorEvent = "list";
		}
		if (("reject_checklist_item").equals(event)) {
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
		if ("list".equals(event) || "update_covenant_item".equals(event) || "update_annexure_item".equals(event)
				|| "add_annexure".equals(event) || "remove_annexure".equals(event) || "update_covenant".equals(event)
				|| "edit_staging_checklist_item".equals(event) || "view_ok".equals(event) 
				|| EVENT_MAINTAIN_ANNEXURE_LIST.equals(event) || EVENT_CHECKER_ANNEXURE_LIST.equals(event)) {
			forwardName = "after_list";
		}
		if ("prepare_add_covenant".equals(event)) {
			forwardName = "after_prepare_add_covenant";
		}
		if ("prepare_add_annexure".equals(event)) {
			forwardName = "after_prepare_add_annexure";
		}
		if ("view_annexure_item".equals(event)) {
			forwardName = "after_view_annexure_item";
		}
		if ("view_covenant_item".equals(event)) {
			forwardName = "after_view_covenant_item";
		}
		if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		if ("save".equals(event)) {
			forwardName = "after_save";
		}
		if ("process".equals(event) || "reject_checklist_item_error".equals(event)) {
			forwardName = "after_process";
		}
		if ("chk_view_covenant".equals(event)) {
			forwardName = "after_chk_view_covenant";
		}
		if ("chk_view_annexure".equals(event)) {
			forwardName = "after_chk_view_annexure";
		}
		if ("approve_checklist_item".equals(event)) {
			forwardName = "after_approve_checklist_item";
		}
		if ("reject_checklist_item".equals(event)) {
			forwardName = "after_reject_checklist_item";
		}
		if ("close_checklist_item".equals(event) || "to_track".equals(event) || EVENT_READ.equals(event)
				|| "return".equals(event)) {
			forwardName = "after_close_checklist_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		if ("edit_covenant".equals(event) || "prepare_add_covenant".equals(event)) {
			forwardName = "after_edit_covenant";
		}
		if ("edit_annexure_item".equals(event) || "prepare_add_annexure".equals(event)) {
			forwardName = "after_edit_annexure";
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