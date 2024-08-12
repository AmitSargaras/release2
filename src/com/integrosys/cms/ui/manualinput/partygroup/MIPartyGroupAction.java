/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.manualinput.partygroup;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit.CashDepositForm;
import com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit.CashDepositValidator;

/**
 * Manual input customer action.
 * 
 * @author $Author: Jerlin, Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MIPartyGroupAction extends CommonAction implements IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String EVENT_LIST_PARTY_GROUP = "maker_list_party_group";
	public static final String CHECKER_LIST_PARTY_GROUP = "checker_list_party_group";
	public static final String CHECKER_VIEW_PARTY_GROUP = "checker_view_party_group";
	public static final String MAKER_VIEW_PARTY_GROUP = "maker_view_party_group";
	public static final String MAKER_EDIT_PARTY_GROUP_READ = "maker_edit_party_group_read";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String MAKER_EDIT_PARTY_GROUP = "maker_edit_party_group";
	public static final String CHECKER_EDIT_READ = "checker_edit_read";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_ACTIVATE_PARTY_GROUP_READ = "checker_activate_party_group_read";
	public static final String CHECKER_ACTIVATE_PARTY_GROUP = "checker_activate_party_group";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String REDIRECT_LIST_PARTY_GROUP = "redirect_list_party_group";
	public static final String MAKER_DELETE_PARTY_GROUP_READ = "maker_delete_party_group_read";
	public static final String MAKER_EDIT_REJECT_EDIT = "maker_edit_reject_edit";
	public static final String MAKER_SEARCH_PARTY_GROUP = "maker_search_party_group";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String MAKER_SAVE_PROCESS = "maker_save_process";
	public static final String MAKER_SEARCH_LIST_PARTY_GROUPBRANCH = "maker_search_list_party_groupBranch";
	public static final String MAKER_UPDATE_DRAFT_PARTY_GROUP = "maker_update_draft_party_group";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String CHECKER_REJECT_ACTIVATE_PARTY_GROUP = "checker_reject_activate_party_group";
	public static final String MAKER_SAVE_CREATE = "maker_save_create";
	public static final String MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";
	public static final String MAKER_PREPARE_CREATE_PARTY_GROUP = "maker_prepare_create_party_group";
	public static final String MAKER_CREATE_PARTY_GROUP = "maker_create_party_group";
	public static final String MAKER_CREATE_DRAFT_PARTY_GROUP = "maker_create_draft_party_group";
	public static final String MAKER_EDIT_DRAFT_PARTY_GROUP = "maker_edit_draft_party_group";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String CHECKER_APPROVE_CREATE = "checker_approve_create";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE = "maker_prepare_resubmit_create";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_ENABLE_PARTY_GROUP = "maker_enable_party_group";
	public static final String MAKER_DISABLE_PARTY_GROUP = "maker_disable_party_group";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_DELETE_PARTY_GROUP = "maker_delete_party_group";
	public static final String MAKER_ACTIVATE_PARTY_GROUP = "maker_activate_party_group";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		System.out
				.println("-------------------------------In MIPartyGroupAction with event ------"
						+ event);

		ICommand objArray[] = null;
		if (EVENT_LIST_PARTY_GROUP.equals(event) || CHECKER_LIST_PARTY_GROUP.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListPartyGroupCmd");
			;

		} else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreatePartyGroupCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreatePartyGroupCmd");

		} else if (event.equals(MAKER_CREATE_DRAFT_PARTY_GROUP)
				|| event.equals(MAKER_UPDATE_DRAFT_PARTY_GROUP)
				|| event.equals(MAKER_EDIT_DRAFT_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSavePartyGroupCmd");

		} else if ((event.equals(CHECKER_VIEW_PARTY_GROUP))
				|| event.equals(MAKER_VIEW_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadPartyGroupCmd");
		} else if ((event.equals(CHECKER_PROCESS_EDIT) || event
				.equals(REJECTED_DELETE_READ))

				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(MAKER_SAVE_PROCESS)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadPartyGroupCmd");
		} else if ((event != null) && event.equals(CHECKER_APPROVE_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveCreatePartyGroupCmd");
		} else if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)
				|| event.equals(CHECKER_REJECT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectCreatePartyGroupCmd");
		} else if ((event != null) && event.equals(MAKER_EDIT_PARTY_GROUP_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditPartyGroupCmd");
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| event.equals(MAKER_EDIT_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditPartyGroupCmd");
		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT)
				|| event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_CREATE)
				|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadPartyGroupCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerClosePartyGroupCmd");
		} else if (event.equals(MAKER_ENABLE_PARTY_GROUP)
				|| event.equals(MAKER_DISABLE_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDisableEnablePartyGroupCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DELETE))
				|| event.equals(MAKER_DELETE_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeletePartyGroupCmd");
		} else if ((event.equals(MAKER_ACTIVATE_PARTY_GROUP))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeletePartyGroupCmd");
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditPartyGroupCmd");
		} else if ((event != null)
				&& event.equals(CHECKER_ACTIVATE_PARTY_GROUP_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadPartyGroupCmd");
		} else if ((event != null)
				&& event.equals(CHECKER_ACTIVATE_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerActivatePartyGroupCmd");
		} else if (event.equals(MAKER_SAVE_CREATE)
				|| event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditPartyGroupCmd");

		} else if (event.equals(CHECKER_REJECT_ACTIVATE_PARTY_GROUP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectActivatePartyGroupCmd");
		}else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		}

		return (objArray);
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm
	 *            is of type ActionForm
	 * @param locale
	 *            of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return PartyGroupValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_EDIT_PARTY_GROUP)
				|| event.equals(MAKER_SAVE_UPDATE)
				|| event.equals(MAKER_SAVE_CREATE)
				|| event.equals(MAKER_CREATE_PARTY_GROUP)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_CREATE)
				|| event.equals(MAKER_CREATE_DRAFT_PARTY_GROUP)
				|| event.equals(MAKER_EDIT_DRAFT_PARTY_GROUP))

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (MAKER_CREATE_PARTY_GROUP.equals(event)) {
			errorEvent = "maker_create_party_group_error";
		} else if (MAKER_EDIT_PARTY_GROUP.equals(event))	
			{
			errorEvent = "maker_edit_party_group_error";
		} else if ("maker_save_update".equals(event)) {
			errorEvent = "maker_save_update_error";
		} else if ("maker_save_create".equals(event)) {
			errorEvent = "maker_save_create_error";
		} else if ("maker_confirm_resubmit_edit".equals(event)) {
			errorEvent = "maker_confirm_resubmit_edit_error";
		} else if ("checker_reject_activate_party_group".equals(event)) {
			errorEvent = "checker_reject_activate_party_group_error";
		} else if (CHECKER_REJECT_EDIT.equals(event)) {
			errorEvent = "checker_reject_edit_party_group_error";
		} else if (CHECKER_REJECT_CREATE.equals(event)) {
			errorEvent = "checker_reject_create_party_group_error";
		} else if (CHECKER_REJECT_DELETE.equals(event)) {
			errorEvent = "checker_reject_delete_party_group_error";
		} 
		else if (MAKER_CREATE_DRAFT_PARTY_GROUP.equals(event)) {
			errorEvent = "errorCreateSaving";
		}
		else if (MAKER_EDIT_DRAFT_PARTY_GROUP.equals(event)) {
			errorEvent = "errorEditSaving";
		}
		
		return errorEvent;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event
	 *            is of type String
	 * @param resultMap
	 *            is of type HashMap
	 * @param exceptionMap
	 *            is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("work_in_process");
			return aPage;
		} else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (EVENT_LIST_PARTY_GROUP.equals(event)) {
			forwardName = EVENT_LIST_PARTY_GROUP;

		} else if (MAKER_PREPARE_CREATE_PARTY_GROUP.equals(event) || event.equals("errorCreateSaving") ) {
			forwardName = MAKER_PREPARE_CREATE_PARTY_GROUP;

		} else if (MAKER_CREATE_PARTY_GROUP.equals(event)) {
			forwardName = "common_submit_page";

		} else if ((event.equals(CHECKER_PROCESS_DELETE))
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(CHECKER_PROCESS_EDIT)) {
			forwardName = "checker_party_group_page";
		} else if ((event != null) && event.equals("checker_approve_create")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		} else if ((event.equals("checker_view_party_group"))
				|| event.equals("maker_view_party_group")) {
			forwardName = "maker_view_page";
		} else if ((event.equals("checker_view_party_group"))
				|| event.equals("maker_view_party_group")) {
			forwardName = "checker_view_page";
		} else if ((event != null)
				&& ( event.equals("maker_edit_party_group_read") || event.equals("errorEditSaving") ) ) {
			forwardName = "maker_edit_party_group_page";
		} else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_party_group")
				|| event.equals("maker_confirm_resubmit_delete")
				|| event.equals("maker_create_party_group")
				|| event.equals("maker_edit_draft_party_group")
				|| event.equals("maker_confirm_resubmit_create")
				|| event.equals("maker_save_create")
				|| event.equals("maker_update_draft_party_group")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("checker_reject_create")) {
			forwardName = "common_reject_page";

		} else if ((event != null)
				&& event.equals("maker_create_draft_party_group")) {
			forwardName = "common_save_page";

		} else if ((event != null)
				&& event.equals("checker_reject_activate_party_group")) {
			forwardName = "common_reject_page";

		} else if (event.equals("maker_prepare_resubmit_create")) {
			forwardName = "maker_prepare_resubmit";
		} else if (event.equals("checker_list_party_group")) {
			forwardName = "checker_list_party_group";
		}
		else if (event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit";
		} else if ((event != null)
				&& (event.equals("maker_prepare_close") || event
						.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			forwardName = "common_close_page";
		} else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process"))) {
			forwardName = "maker_view_save_page";
		} else if ((event != null)
				&& (event.equals(MAKER_ENABLE_PARTY_GROUP) || event
						.equals(MAKER_DISABLE_PARTY_GROUP))) {
			forwardName = "partygroup_enable_disable_page";
		} else if ((event != null) && event.equals("maker_delete_party_group")) {
			forwardName = "common_submit_page";
		} else if ((event != null)
				&& event.equals("maker_activate_party_group")) {
			forwardName = "common_submit_page";
		} else if ((event != null)
				&& event.equals("checker_activate_party_group_read")) {
			forwardName = "checker_activate_party_group_read";
		} else if ((event != null)
				&& event.equals("checker_activate_party_group")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("maker_save_update")) {
			forwardName = "common_save_page";
		} else if ((event != null)
				&& event.equals("maker_create_party_group_error")) {
			forwardName = "maker_prepare_create_party_group";
		}  else if ((event != null)
				&& event.equals("maker_edit_party_group_error")) {
			forwardName = "maker_edit_party_group_page";
		}  else if ((event != null)
				&& event.equals("maker_save_create_error")) {
			forwardName = "maker_view_save_page";
		}  else if ((event != null)
				&& event.equals("maker_save_update_error")) {
			forwardName = "maker_view_save_page";
		}  else if ((event != null)
				&& event.equals("maker_confirm_resubmit_edit_error")) {
			forwardName = "maker_prepare_resubmit";
		} else if ((event != null)
				&& event.equals("checker_reject_activate_party_group_error")) {
			forwardName = "checker_activate_party_group_read";
		}else if ((event != null)
				&& event.equals("checker_reject_edit_party_group_error")) {
			forwardName = "checker_party_group_page";
		}else if ((event != null)
				&& event.equals("checker_reject_create_party_group_error")) {
			forwardName = "checker_party_group_page";
		}else if ((event != null)
				&& event.equals("checker_reject_delete_party_group_error")) {
			forwardName = "checker_party_group_page";
		} else if (event.equals("list_pagination")) {
			forwardName = "maker_list_party_group";
		} else if (event.equals("checker_list_pagination")) {
			forwardName = "checker_list_party_group";
		}  

		System.out
				.println("----------------------------- forwardName--------------------"
						+ forwardName);
		return forwardName;
	}

}
