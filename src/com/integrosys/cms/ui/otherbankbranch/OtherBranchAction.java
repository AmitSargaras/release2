package com.integrosys.cms.ui.otherbankbranch;

/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://pc495:8443/svn/SmartLender/IndianProjects/HDFC_CLIMS/HDFC_CLIMS/trunk/src/com/integrosys/cms/ui/otherbankbranch/OtherBranchAction.java $
 */

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Describe this class Purpose : Other Bank-Branch Actions Description :
 * Handling Actions of Other Branch
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1 $
 * @since $Date: 2011-02-18 17:12:33 +0800 (Fri, 18 Feb 2011) $ Tag : $Name$
 */
public class OtherBranchAction extends CommonAction {

	public static final String LIST_OTHER_BRANCH = "view_other_branch";

	public static final String PREPARE_CREATE_OTHER_BRANCH = "prepare_create_other_bank_branch";

	public static final String SUBMIT_CREATE_OTHER_BRANCH = "maker_submit_create_other_bank_branch";

	public static final String VIEW_OTHER_BRANCH_BY_INDEX = "view_other_branch_by_index";
	
	public static final String VIEW_OTHER_BRANCH_BY_INDEX_DELETE = "view_other_branch_by_index_delete";

	public static final String PREPARE_EDIT_OTHER_BRANCH = "prepare_edit_branch";

	public static final String MAKER_SUBMIT_EDIT_OTHER_BRANCH = "maker_submit_edit_branch";

	public static final String PREPARE_DELETE_OTHER_BRANCH = "prepare_maker_submit_remove_branch";
	
	public static final String PREPARE_DELETE_OTHER_BRANCH_DELETE = "prepare_maker_submit_remove_branch_delete";

	public static final String MAKER_SUBMIT_DELETE_OTHER_BRANCH = "maker_submit_remove_branch";

	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";

	public static final String CHECKER_LIST_OTHERBRANCH = "checker_list_otherBranch";

	public static final String CHECKER_VIEW_OTHERBRANCH = "checker_view_otherBranch";

	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";

	public static final String CHECKER_EDIT_READ = "checker_edit_read";

	public static final String REJECTED_DELETE_READ = "rejected_delete_read";

	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";

	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";

	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";

	public static final String MAKER_EDIT_OTHERBRANCH_READ = "maker_edit_otherBranch_read";

	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";

	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";

	public static final String REDIRECT_LIST_OTHERBRANCH = "redirect_list_otherBranch";

	public static final String MAKER_DELETE_OTHERBRANCH_READ = "maker_delete_otherBranch_read";

	public static final String MAKER_EDIT_REJECT_EDIT = "maker_edit_reject_edit";

	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";

	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";

	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";

	public static final String LIST_PAGINATION = "list_pagination";

	public static final String MAKER_RESUBMIT_CREATE_OTHER_BRANCH = "maker_prepare_resubmit_create";

	public static final String CHECKER_OTHER_BRANCH_PAGE = "checker_otherBranch_page";

	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";

	public static final String COMMON_CLOSE_PAGE = "common_close_page";

	public static final String COMMON_APPROVE_PAGE = "common_approve_page";

	public static final String MAKER_VIEW_DELETE_PAGE = "maker_view_delete_page";

	public static final String MAKER_VIEW_TODO_PAGE = "maker_view_todo_page";

	public static final String CHECKER_VIEW_DELETE_PAGE = "checker_view_delete_page";

	public static final String REJECTED_READ_PAGE = "rejected_read_page";

	public static final String WORK_IN_PROGRESS_PAGE = "work_in_process_page";

	public static final String MAKER_CNCL_REJECT_ADD = "maker_cncl_reject_add";

	public static final String MAKER_CNCL_REJECT_EDIT = "maker_cncl_reject_edit";

	public static final String MAKER_CNCL_REJECT_DELETE = "maker_cncl_reject_delete";

	public static final String MAKER_EDIT_REJECT_ADD = "maker_edit_reject_add";

	public static final String MAKER_EDIT_REJECT_DELETE = "maker_edit_reject_delete";

	public static final String WORK_IN_PROCESS = "work_in_process";

	public static final String REJECTED_READ = "rejected_read";

	public static final String CHECKER_VIEW_OTHER_BRANCH = "checker_view_other_branch";

	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";

	public static final String MAKER_DRAFT_CLOSE_OTHER_BANK_BRANCH = "maker_draft_close_process";

	public static final String MAKER_CONFIRM_RESUBMIT_DRAFT = "maker_confirm_resubmit_draft";

	public static final String MAKER_SAVE_CREATE_OTHER_BANK_BRANCH = "maker_save_create_other_bank_branch";

	public static final String MAKER_SAVE_UPDATE_OTHER_BANK_BRANCH = "maker_save_update_other_bank_branch";

	public static final String MAKER_SAVE_PROCESS = "maker_save_process";

	public static final String MAKER_SAVE_UPDATE_PROCESS = "maker_update_save_process";

	public static final String MAKER_CONFIRM_RESUBMIT_UPDATE = "maker_confirm_resubmit_update";

	// **************************************UPLOAD**********************************

	public static final String MAKER_PREPARE_UPLOAD_OTHER_BANKBRANCH = "maker_prepare_upload_other_bankbranch";

	public static final String MAKER_EVENT_UPLOAD_OTHER_BANKBRANCH = "maker_event_upload_other_bankbranch";

	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";

	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert_bankbranch";

	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert_bankbranch";

	public static final String EVENT_MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";

	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";

	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	
	public static final String EVENT_REFRESH_REGION_ID = "refresh_region_id";
	
	public static final String EVENT_REFRESH_STATE_ID = "refresh_state_id";
	
	public static final String EVENT_REFRESH_CITY_ID = "refresh_city_id";

	// **********************UPLOAD********************************

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	protected ICommand[] getCommandChain(String event) {

		ICommand[] objArray = null;

		if (LIST_OTHER_BRANCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListOtherBranchCommand");
		} else if (PREPARE_CREATE_OTHER_BRANCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareCreateOtherBranchCommand");
		} else if (SUBMIT_CREATE_OTHER_BRANCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SubmitCreateOtherBranchCommand");
		} else if (VIEW_OTHER_BRANCH_BY_INDEX.equals(event)
				|| CHECKER_VIEW_OTHER_BRANCH.equals(event)
				|| VIEW_OTHER_BRANCH_BY_INDEX_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewOtherBranchByIndexCommand");
		} else if (PREPARE_EDIT_OTHER_BRANCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareEditOtherBranchCommand");
		} else if (PREPARE_DELETE_OTHER_BRANCH.equals(event)
				|| PREPARE_DELETE_OTHER_BRANCH_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareDeleteOtherBranchCommand");
		} else if (MAKER_SUBMIT_DELETE_OTHER_BRANCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DeleteOtherBranchCommand");
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| MAKER_SUBMIT_EDIT_OTHER_BRANCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"EditOtherBranchCommand");
		} else if ((event.equals(CHECKER_EDIT_READ) || event
				.equals(REJECTED_DELETE_READ))
				|| event.equals(CHECKER_VIEW_OTHERBRANCH)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(MAKER_PREPARE_CLOSE)
				|| event.equals(MAKER_RESUBMIT_CREATE_OTHER_BRANCH)
				|| event.equals(CHECKER_PROCESS_EDIT)
				|| event.equals(MAKER_SAVE_UPDATE_PROCESS)
				|| event.equals(MAKER_SAVE_PROCESS)
				|| event.equals(MAKER_DRAFT_CLOSE_OTHER_BANK_BRANCH)
				|| event.equals(MAKER_PREPARE_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadOtherBranchCmd");
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditOtherBranchCmd");
		} else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectEditOtherBranchCmd");
		} else if ((event != null) && event.equals(MAKER_EDIT_OTHERBRANCH_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadOtherBranchCmd");
		} else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseOtherBranchCmd");
		}

		else if (event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDraftCloseOtherBankBranchCmd");
		} else if (event.equals(MAKER_CONFIRM_RESUBMIT_DRAFT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSubmitDraftOtherBankBranchCommand");
		} else if (MAKER_SAVE_CREATE_OTHER_BANK_BRANCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveCreateOtherBankBranchCommand");
		} else if (MAKER_SAVE_UPDATE_OTHER_BANK_BRANCH.equals(event)
				|| MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"EditOtherBranchCommand");
		}

		// **********************UPLOAD********************************

		else if (event.equals(MAKER_PREPARE_UPLOAD_OTHER_BANKBRANCH)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareUploadOtherBankBranchCmd");

		}

		else if (event.equals(MAKER_EVENT_UPLOAD_OTHER_BANKBRANCH)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerUploadOtherBankBranchCmd");

		}

		else if ((event != null) && event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"OtherBankBranchCheckerReadFileInsertListCmd");
		}

		else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveInsertOtherBankBranchCmd");
		}

		else if (event.equals(CHECKER_REJECT_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectInsertOtherBankBranchCmd");
		}

		else if (event.equals(EVENT_MAKER_REJECTED_DELETE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"OtherBankBranchCheckerReadFileInsertListCmd");

		} else if ((event != null) && event.equals(MAKER_PREPARE_INSERT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"OtherBankBranchCheckerReadFileInsertListCmd");
		}

		else if ((event != null) && (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerInsertCloseOtherBankBranchCmd");
		}else if (event.equals(EVENT_REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCountryCommmand");
		}else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityCommmand");
		}else if (event.equals(EVENT_REFRESH_CITY_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityTownCommand");
		}
		return objArray;
	}

	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page page = new Page();

		if ((resultMap.get("wip") != null)
				&& ((String) resultMap.get("wip")).equals("wip")) {
			page.setPageReference(WORK_IN_PROGRESS_PAGE);
			return page;
		}

		else if (LIST_OTHER_BRANCH.equals(event)) {
			event = LIST_OTHER_BRANCH;
		} else if (VIEW_OTHER_BRANCH_BY_INDEX.equals(event)
				|| CHECKER_VIEW_OTHER_BRANCH.equals(event)
				|| VIEW_OTHER_BRANCH_BY_INDEX_DELETE.equals(event)) {
			event = VIEW_OTHER_BRANCH_BY_INDEX;
		} else if (PREPARE_EDIT_OTHER_BRANCH.equals(event) || event.equals("errorReSubmitEvent")) {
			event = PREPARE_EDIT_OTHER_BRANCH;
		} else if (PREPARE_DELETE_OTHER_BRANCH.equals(event)||event.equals(PREPARE_DELETE_OTHER_BRANCH_DELETE)) {
			event = PREPARE_DELETE_OTHER_BRANCH;
		} else if (PREPARE_CREATE_OTHER_BRANCH.equals(event)) {
			event = PREPARE_CREATE_OTHER_BRANCH;
		} else if (CHECKER_EDIT_READ.equals(event)) {
			event = CHECKER_OTHER_BRANCH_PAGE;
		} else if (CHECKER_PROCESS_CREATE.equals(event)) {
			event = CHECKER_OTHER_BRANCH_PAGE;
		} else if (CHECKER_PROCESS_DELETE.equals(event)) {
			event = CHECKER_OTHER_BRANCH_PAGE;
		} else if (CHECKER_APPROVE_EDIT.equals(event)) {
			event = COMMON_APPROVE_PAGE;
		} else if (REJECTED_DELETE_READ.equals(event)) {
			event = MAKER_VIEW_TODO_PAGE;
		} else if (MAKER_PREPARE_CLOSE.equals(event)) {
			event = MAKER_PREPARE_CLOSE;
		} else if (MAKER_RESUBMIT_CREATE_OTHER_BRANCH.equals(event) || event.equals("errorMakerReSubmitEvent")) {
			event = MAKER_PREPARE_RESUBMIT;
		} else if (MAKER_CONFIRM_RESUBMIT_EDIT.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		} else if (CHECKER_PROCESS_EDIT.equals(event)) {
			event = CHECKER_OTHER_BRANCH_PAGE;
		}

		else if (MAKER_SAVE_PROCESS.equals(event)) {
			event = MAKER_SAVE_PROCESS;
		} else if (MAKER_SAVE_UPDATE_PROCESS.equals(event)) {
			event = MAKER_SAVE_UPDATE_PROCESS;
		} else if (MAKER_DRAFT_CLOSE_OTHER_BANK_BRANCH.equals(event)) {
			event = MAKER_PREPARE_CLOSE;
		} else if (MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		} else if (MAKER_SAVE_UPDATE_OTHER_BANK_BRANCH.equals(event)) {
			event = MAKER_SAVE_UPDATE_OTHER_BANK_BRANCH;
		} else if (MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		}
		// *****************************UPLOAD************************************

		else if (event.equals("maker_prepare_upload_other_bankbranch")) {
			event = "maker_prepare_upload_other_bankbranch_page";
		}

		else if ((event.equals("maker_event_upload_other_bankbranch"))) {
			event = COMMON_SUBMIT_PAGE;
		}

		else if (event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			event = "checker_process_create_insert_bankbranch_page";
		}

		else if (event.equals("checker_approve_insert_bankbranch")) {
			event = COMMON_SUBMIT_PAGE;
		}

		else if (event.equals("checker_reject_insert_bankbranch")) {
			event = COMMON_SUBMIT_PAGE;
		}

		else if (event.equals("maker_rejected_delete_read")) {
			event = "maker_rejected_delete_read_bankbranch";
		}

		else if ((event.equals("maker_prepare_insert_close"))) {
			event = "maker_prepare_insert_close_bankbranch";
		} else if ((event.equals("maker_confirm_insert_close"))) {
			event = COMMON_SUBMIT_PAGE;
		}

		else if ((event.equals("checker_reject_insert_error_bankbranch"))) {
			event = "checker_reject_insert_bankbranch_error_page";
		}

		else if ((event.equals("maker_event_upload_valuation_agency_error"))) {
			event = "maker_event_upload_valuation_agency_error_page";
		}else if (event.equals(EVENT_REFRESH_REGION_ID)) {
        	event = EVENT_REFRESH_REGION_ID;
		}else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			event = EVENT_REFRESH_STATE_ID;
		}else if (event.equals(EVENT_REFRESH_CITY_ID)) {
			event = "refresh_city_id";
		}

		if ((resultMap.get("errorEveList") != null)
				&& ((String) resultMap.get("errorEveList"))
						.equals("errorEveList")) {
			page.setPageReference("maker_fileupload_other_BankBranch_page");
			return page;
		}

		page.setPageReference(event);
		return page;
	}

	/**
	 * 
	 */
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (MAKER_SUBMIT_EDIT_OTHER_BRANCH.equals(event)
				|| SUBMIT_CREATE_OTHER_BRANCH.equals(event)
				|| MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event)
				|| MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)
				|| MAKER_SAVE_CREATE_OTHER_BANK_BRANCH.equals(event)
				|| MAKER_SAVE_UPDATE_OTHER_BANK_BRANCH.equals(event)
				|| MAKER_CONFIRM_RESUBMIT_EDIT.equals(event)) {
			result = true;
		}
		return result;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	private String getPageRefernce(String event) {
		String forwardName = null;
		if ((event.equals(CHECKER_LIST_OTHERBRANCH))) {
			forwardName = LIST_OTHER_BRANCH;
		} else if ((event != null) && event.equals(LIST_PAGINATION)) {
			forwardName = LIST_OTHER_BRANCH;
		} else if ((event != null) && event.equals(REDIRECT_LIST_OTHERBRANCH)) {
			forwardName = LIST_OTHER_BRANCH;
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))) {
			forwardName = COMMON_SUBMIT_PAGE;
		} else if ((event != null)
				&& event.equals(MAKER_DELETE_OTHERBRANCH_READ)) {
			forwardName = MAKER_VIEW_DELETE_PAGE;
		} else if ((event != null) && event.equals(CHECKER_EDIT_READ)) {
			forwardName = CHECKER_OTHER_BRANCH_PAGE;
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			forwardName = COMMON_APPROVE_PAGE;
		} else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
			forwardName = CHECKER_REJECT_EDIT;
		} else if ((event != null) && event.equals(REJECTED_DELETE_READ)) {
			forwardName = MAKER_VIEW_TODO_PAGE;
		} else if ((event != null) && event.equals(CHECKER_VIEW_OTHERBRANCH)) {
			forwardName = CHECKER_VIEW_DELETE_PAGE;
		} else if ((event != null) && event.equals(MAKER_PREPARE_RESUBMIT)) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if ((event != null) && event.equals(MAKER_PREPARE_CLOSE)) {
			forwardName = MAKER_PREPARE_CLOSE;
		} else if ((event != null) && event.equals(MAKER_CONFIRM_CLOSE)) {
			forwardName = MAKER_CONFIRM_CLOSE;
		} else if ((event != null) && event.equals(MAKER_CNCL_REJECT_ADD)) {
			forwardName = COMMON_CLOSE_PAGE;
		} else if ((event != null) && event.equals(MAKER_CNCL_REJECT_EDIT)) {
			forwardName = COMMON_CLOSE_PAGE;
		} else if ((event != null) && event.equals(MAKER_CNCL_REJECT_DELETE)) {
			forwardName = COMMON_CLOSE_PAGE;
		} else if ((event != null) && event.equals(MAKER_EDIT_REJECT_ADD)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} else if ((event != null) && event.equals(MAKER_EDIT_REJECT_EDIT)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} else if ((event != null) && event.equals(MAKER_EDIT_REJECT_DELETE)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} else if ((event != null) && event.equals(WORK_IN_PROCESS)) {
			forwardName = WORK_IN_PROGRESS_PAGE;
		} else if ((event != null) && event.equals(REJECTED_READ)) {
			forwardName = REJECTED_READ_PAGE;
		} else if ((event != null)
				&& event.equals("checker_approve_edit_error")) {
			forwardName = CHECKER_OTHER_BRANCH_PAGE;
		} else if ((event != null) && event.equals(CHECKER_PROCESS_CREATE)) {
			forwardName = CHECKER_OTHER_BRANCH_PAGE;
		} else if ((event != null) && event.equals(CHECKER_PROCESS_DELETE)) {
			forwardName = CHECKER_OTHER_BRANCH_PAGE;
		} else if ((event != null) && event.equals(MAKER_PREPARE_CLOSE)) {
			forwardName = MAKER_PREPARE_CLOSE;
		} else if (event.equals(PREPARE_CREATE_OTHER_BRANCH)) {
			forwardName = PREPARE_CREATE_OTHER_BRANCH;
		}

		// **********************UPLOAD********************************

		else if ((event != null)
				&& event.equals("maker_prepare_upload_other_bankbranch")) {
			forwardName = "maker_prepare_upload_other_bankbranch_page";
		}

		else if (event.equals("checker_process_create_insert")) {
			forwardName = "checker_process_create_insert_bankbranch_page";
		}

		else if ((event != null)
				&& event.equals("checker_approve_insert_bankbranch")) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		else if ((event != null)
				&& event.equals("checker_reject_insert_bankbranch")) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		else if ((event != null) && event.equals("maker_rejected_delete_read")) {
			forwardName = "maker_rejected_delete_read_bankbranch";
		}

		else if ((event != null) && (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			forwardName = "maker_prepare_insert_close_bankbranch";
		} else if ((event != null)
				&& (event.equals("maker_confirm_insert_close"))) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		else if ((event != null)
				&& (event.equals("checker_reject_insert_error_bankbranch"))) {
			forwardName = "checker_reject_insert_bankbranch_error_page";
		}

		else if ((event != null)
				&& (event.equals("maker_event_upload_other_bankbranch"))) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		else if ((event != null)
				&& (event.equals("maker_event_upload_valuation_agency_error"))) {
			forwardName = "maker_event_upload_valuation_agency_error_page";
		}else if (event.equals(EVENT_REFRESH_REGION_ID)) {
			forwardName = EVENT_REFRESH_REGION_ID;
		}else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			forwardName = EVENT_REFRESH_STATE_ID;
		}else if (event.equals(EVENT_REFRESH_CITY_ID)) {
			forwardName = EVENT_REFRESH_CITY_ID;
		}

		return forwardName;
	}

	/**
	 * 
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return OtherBranchValidator.validateOtherBranchForm(
				(OtherBranchForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = "";

		if (MAKER_SUBMIT_EDIT_OTHER_BRANCH.equals(event)
				|| MAKER_RESUBMIT_CREATE_OTHER_BRANCH.equals(event)) {
			errorEvent = "errorReSubmitEvent";
		} else if (SUBMIT_CREATE_OTHER_BRANCH.equals(event)) {
			errorEvent = PREPARE_CREATE_OTHER_BRANCH;
		} else if (MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event)) {
			errorEvent = MAKER_SAVE_PROCESS;
		} else if (MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)) {
			errorEvent = MAKER_SAVE_UPDATE_PROCESS;
		} else if (CHECKER_REJECT_EDIT.equals(event)) {
			errorEvent = CHECKER_PROCESS_CREATE;
		}else if(CHECKER_APPROVE_EDIT.equals(event)){
			errorEvent = CHECKER_PROCESS_CREATE;
		}else if(MAKER_SAVE_CREATE_OTHER_BANK_BRANCH.equals(event)){
			errorEvent = PREPARE_CREATE_OTHER_BRANCH;
		}else if(MAKER_SAVE_UPDATE_OTHER_BANK_BRANCH.equals(event)){
			errorEvent = PREPARE_EDIT_OTHER_BRANCH;
		}
		else if(MAKER_CONFIRM_RESUBMIT_EDIT.equals(event)){
			errorEvent = "errorMakerReSubmitEvent";
		}
		// **********************UPLOAD********************************
		else if (event.equals(CHECKER_REJECT_INSERT)) {
			errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		}
		return errorEvent;
	}
}
