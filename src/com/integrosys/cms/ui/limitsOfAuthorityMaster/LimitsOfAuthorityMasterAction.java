package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.cms.ui.profile.Page;

public class LimitsOfAuthorityMasterAction extends CommonAction implements IPin{

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String EVENT_MAKER_LIST_LIMITS_OF_AUTHORITY = "maker_list_limits_of_authority";
	public static final String EVENT_CHECKER_LIST_LIMITS_OF_AUTHORITY = "checker_list_limits_of_authority";
	public static final String EVENT_MAKER_PREPARE_CREATE_LIMITS_OF_AUTHORITY = "maker_prepare_create_limits_of_authority";
	
	public static final String EVENT_MAKER_CREATE_LIMITS_OF_AUTHORITY = "maker_create_limits_of_authority";
	public static final String EVENT_MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String EVENT_CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String EVENT_CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String EVENT_CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String EVENT_MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String EVENT_CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String EVENT_MAKER_DRAFT_LIMITS_OF_AUTHORITY = "maker_draft_limits_of_authority";
	public static final String EVENT_MAKER_UPDATE_DRAFT_LIMITS_OF_AUTHORITY = "maker_update_draft_limits_of_authority";
	public static final String EVENT_MAKER_VIEW_LIMITS_OF_AUTHORITY = "maker_view_limits_of_authority";
	public static final String EVENT_CHECKER_VIEW_LIMITS_OF_AUTHORITY = "checker_view_limits_of_authority";
	public static final String EVENT_MAKER_EDIT_READ_LIMITS_OF_AUTHORITY = "maker_edit_read_limits_of_authority";
	public static final String EVENT_MAKER_EDIT_LIMITS_OF_AUTHORITY = "maker_edit_limits_of_authority";
	public static final String EVENT_MAKER_EDIT_UPDATE_LIMITS_OF_AUTHORITY = "maker_edit_update_limits_of_authority";
	public static final String EVENT_CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String EVENT_CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String EVENT_MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String EVENT_MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String EVENT_MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String EVENT_MAKER_PREPARE_RESUBMIT_CREATE = "maker_prepare_resubmit_create";
	public static final String EVENT_MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String EVENT_MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String EVENT_MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";
	public static final String EVENT_LIST_PAGINATION = "list_pagination";
	public static final String EVENT_CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String EVENT_MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String EVENT_MAKER_SAVE_CREATE = "maker_save_create";
	public static final String EVENT_MAKER_DELETE_READ = "maker_delete_read_limits_of_authority";
	public static final String EVENT_MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String MAKER_SAVE_UPDATE_ERROR = "maker_save_update_error";
	public static final String CHECKER_REJECT_EDIT_ERROR = "checker_reject_edit_error";
	public static final String CHECKER_APPROVE_EDIT_ERROR = "checker_approve_edit_error";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_ERROR = "maker_confirm_resubmit_edit_error";
	public static final String MAKER_EDIT_ERROR = "maker_edit_error";
	public static final String MAKER_EDIT_UPDATE_ERROR = "maker_edit_update_error";
	
	public static final String FWD_WIP = "work_in_process_page";
	public static final String FWD_MAKER_VIEW_ADD_PAGE = "maker_view_add_page";
	public static final String FWD_COMMON_SUBMIT_PAGE = "common_submit_page";
	public static final String FWD_COMMON_CLOSE_PAGE = "common_close_page";
	public static final String FWD_COMMON_REJECT_PAGE = "common_reject_page"; 
	public static final String FWD_COMMON_APPROVE_PAGE = "common_approve_page";
	public static final String FWD_CHECKER_PAGE = "checker_page";
	public static final String FWD_MAKER_VIEW_UPDATE_PAGE = "maker_view_update_page";
	
	protected ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if(event.equals(EVENT_MAKER_LIST_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_CHECKER_LIST_LIMITS_OF_AUTHORITY)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListLimitsOfAuthorityCmd");
		}else if(event.equals(EVENT_MAKER_PREPARE_CREATE_LIMITS_OF_AUTHORITY)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerPrepareCreateLimitsOfAuthorityCmd");
		}else if(event.equals(EVENT_MAKER_CREATE_LIMITS_OF_AUTHORITY) || 
				event.equals(EVENT_MAKER_CONFIRM_RESUBMIT_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCreateLimitsOfAuthorityCmd");
		}  else if (event.equals(EVENT_CHECKER_PROCESS_EDIT) 
				|| event.equals(EVENT_CHECKER_PROCESS_DELETE)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE)
				|| event.equals(EVENT_MAKER_UPDATE_SAVE_PROCESS)
				|| event.equals(EVENT_MAKER_PREPARE_RESUBMIT_DELETE)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE)
				|| event.equals(EVENT_MAKER_DRAFT_CLOSE_PROCESS) 
				||event.equals(EVENT_MAKER_PREPARE_RESUBMIT_CREATE)
				) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadLimitsOfAuthorityCmd");
		}else if (event.equals(EVENT_CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditLimitsOfAuthorityCmd");
		}else if (event.equals(EVENT_MAKER_DRAFT_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_UPDATE_DRAFT_LIMITS_OF_AUTHORITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerSaveLimitsOfAuthorityCmd");
		} 
		else if (event.equals(EVENT_MAKER_VIEW_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_CHECKER_VIEW_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_EDIT_READ_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_DELETE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadLimitsOfAuthorityCmd");
		}
		else if (event.equals(EVENT_MAKER_EDIT_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_EDIT_UPDATE_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_SAVE_CREATE)
				|| event.equals(EVENT_MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditLimitsOfAuthorityCmd");
		}
		else if (event.equals(EVENT_CHECKER_REJECT_CREATE)
				|| event.equals(EVENT_CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditLimitsOfAuthorityCmd");
		}
		else if (event.equals(EVENT_MAKER_CONFIRM_RESUBMIT_DELETE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteLimitsOfAuthorityCmd");
		}
		else if (event.equals(EVENT_MAKER_CONFIRM_CLOSE)
				|| event.equals(EVENT_MAKER_CONFIRM_DRAFT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseLimitsOfAuthorityCmd");
		}
		else if (event.equals(EVENT_LIST_PAGINATION)
				|| event.equals(EVENT_CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}else if (event.equals(EVENT_MAKER_CONFIRM_RESUBMIT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitEditLimitsOfAuthorityCmd");
		}
		
		return objArray;
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_MAKER_CREATE_LIMITS_OF_AUTHORITY)
				||event.equals(EVENT_MAKER_DRAFT_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_SAVE_UPDATE)
				|| event.equals(EVENT_MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(EVENT_MAKER_UPDATE_DRAFT_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_EDIT_LIMITS_OF_AUTHORITY)
				|| event.equals(EVENT_MAKER_EDIT_UPDATE_LIMITS_OF_AUTHORITY))
		{
			result = true;
		}
		return result;
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return LimitsOfAuthorityMasterValidator.validateInput((LimitsOfAuthorityMasterForm) aForm, locale);
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		String fwd = null;
		if ("wip".equals(resultMap.get("wip"))) {
			fwd = FWD_WIP;
		}
		else {
			fwd = getReference(event);
		}
		aPage.setPageReference(fwd);
		return aPage;
	}
	
	@Override
	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (EVENT_MAKER_CREATE_LIMITS_OF_AUTHORITY.equals(event) 
				|| EVENT_MAKER_DRAFT_LIMITS_OF_AUTHORITY.equals(event)) {
			errorEvent = EVENT_MAKER_PREPARE_CREATE_LIMITS_OF_AUTHORITY;
		}
		else if(EVENT_MAKER_SAVE_UPDATE.equals(event)) {
			errorEvent = MAKER_SAVE_UPDATE_ERROR;
		}
		else if (event.equals(EVENT_CHECKER_REJECT_EDIT)) {
			errorEvent = CHECKER_REJECT_EDIT_ERROR;
		}
		else if(event.equals(EVENT_CHECKER_APPROVE_EDIT)) {
			errorEvent = CHECKER_APPROVE_EDIT_ERROR;
		}
		else if(event.equals(EVENT_MAKER_CONFIRM_RESUBMIT_EDIT)){
			errorEvent = MAKER_CONFIRM_RESUBMIT_EDIT_ERROR;
		}
		else if (event.equals(EVENT_MAKER_EDIT_LIMITS_OF_AUTHORITY) || 
				event.equals(EVENT_MAKER_UPDATE_DRAFT_LIMITS_OF_AUTHORITY)) {
			errorEvent = MAKER_EDIT_ERROR;
		}
		else if(event.equals(EVENT_MAKER_EDIT_UPDATE_LIMITS_OF_AUTHORITY)){
			errorEvent = MAKER_EDIT_UPDATE_ERROR;
		}
		return errorEvent;
	}
	
	private String getReference(String event) {
		String forwardName = null;
		if(event.equals(EVENT_MAKER_CREATE_LIMITS_OF_AUTHORITY) ||
			event.equals(EVENT_MAKER_DRAFT_LIMITS_OF_AUTHORITY) ||
			event.equals(EVENT_MAKER_SAVE_UPDATE)||
			event.equals(EVENT_MAKER_CONFIRM_RESUBMIT_EDIT)||
			event.equals(EVENT_MAKER_EDIT_LIMITS_OF_AUTHORITY)||
			event.equals(EVENT_MAKER_EDIT_UPDATE_LIMITS_OF_AUTHORITY)||
			event.equals(EVENT_MAKER_UPDATE_DRAFT_LIMITS_OF_AUTHORITY)||
			event.equals(EVENT_MAKER_CONFIRM_RESUBMIT_DELETE)) {
			forwardName = FWD_COMMON_SUBMIT_PAGE;
		}else if (event.equals(EVENT_MAKER_UPDATE_SAVE_PROCESS)) {
			forwardName = FWD_MAKER_VIEW_UPDATE_PAGE;
		}else if(event.equals(MAKER_SAVE_UPDATE_ERROR)) {
			forwardName = FWD_MAKER_VIEW_ADD_PAGE;
		}else if (event.equals(EVENT_MAKER_DRAFT_CLOSE_PROCESS)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE;
		}else if (event.equals(EVENT_MAKER_CONFIRM_CLOSE)
					|| event.equals(EVENT_MAKER_CONFIRM_DRAFT_CLOSE)) {
			forwardName = FWD_COMMON_CLOSE_PAGE;
		}else if(event.equals(EVENT_CHECKER_PROCESS_CREATE)
				|| event.equals(CHECKER_REJECT_EDIT_ERROR)
				|| event.equals(EVENT_CHECKER_PROCESS_EDIT)
				|| event.equals(EVENT_CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_APPROVE_EDIT_ERROR)) {
			forwardName = FWD_CHECKER_PAGE;
		}else if(event.equals(EVENT_CHECKER_REJECT_EDIT)) {
			forwardName = FWD_COMMON_REJECT_PAGE;
		}else if(event.equals(EVENT_CHECKER_APPROVE_EDIT)) {
			forwardName = FWD_COMMON_APPROVE_PAGE;
		}else if(event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_ERROR)) {
			forwardName = EVENT_MAKER_PREPARE_RESUBMIT_CREATE;
		}else if(event.equals(MAKER_EDIT_ERROR)) {
			forwardName = EVENT_MAKER_EDIT_READ_LIMITS_OF_AUTHORITY;
		}
		else if(event.equals(MAKER_EDIT_UPDATE_ERROR)) {
			forwardName = FWD_MAKER_VIEW_UPDATE_PAGE;
		}
		else {
			forwardName = event;
		}
		return forwardName;
	}

}