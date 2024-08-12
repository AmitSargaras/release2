/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.systemBank;

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

/**
 * @author $Author: Abhijit R $<br>
 * 
 * Controller for System Bank Master
 */
public class MaintainSystemBankAction extends CommonAction implements IPin {
	
	 private Map nameCommandMap;

	    public Map getNameCommandMap() {
	        return nameCommandMap;
	    }

	    public void setNameCommandMap(Map nameCommandMap) {
	        this.nameCommandMap = nameCommandMap;
	    }

	public static final String FIRST_SORT = "LOGIN_ID";
	public static final String SECOND_SORT = "USER_NAME";
	public static final String MAKER_LIST_SYSTEMBANK = "maker_list_systemBank";
	public static final String CHECKER_LIST_SYSTEMBANK="checker_list_systemBank";
	public static final String CHECKER_VIEW_SYSTEMBANK="checker_view_systemBank";
	public static final String MAKER_VIEW_SYSTEMBANK="maker_view_systemBank";
	public static final String MAKER_EDIT_SYSTEMBANK_READ="maker_edit_systemBank_read";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT="maker_confirm_resubmit_edit";
	public static final String MAKER_EDIT_SYSTEMBANK="maker_edit_systemBank";
	public static final String CHECKER_EDIT_READ="checker_edit_read";
	public static final String REJECTED_DELETE_READ="rejected_delete_read";
	public static final String CHECKER_APPROVE_EDIT="checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT="checker_reject_edit";
	public static final String MAKER_PREPARE_CLOSE="maker_prepare_close";
	public static final String MAKER_PREPARE_RESUBMIT="maker_prepare_resubmit";
	public static final String MAKER_CONFIRM_CLOSE="maker_confirm_close";
	public static final String REDIRECT_LIST_SYSTEMBANK="redirect_list_systemBank";
	public static final String MAKER_DELETE_SYSTEMBANK_READ="maker_delete_systemBank_read";
	public static final String MAKER_EDIT_REJECT_EDIT="maker_edit_reject_edit";
	public static final String MAKER_SEARCH_SYSTEMBANK="maker_search_systemBank";
	public static final String LIST_PAGINATION="list_pagination";
	public static final String MAKER_SEARCH_LIST_SYSTEMBANKBRANCH = "maker_search_list_systemBankBranch";
	public static final String CHECKER_SEARCH_LIST_SYSTEMBANKBRANCH = "checker_search_list_systemBankBranch";
	public static final String MAKER_UPDATE_DRAFT_SYSTEMBANK="maker_update_draft_systemBank";
	public static final String MAKER_UPDATE_SAVE_PROCESS="maker_update_save_process";
	public static final String MAKER_SAVE_UPDATE="maker_save_update";
	public static final String MAKER_DRAFT_CLOSE_PROCESS="maker_draft_close_process";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE="maker_confirm_draft_close";
	public static final String REFRESH_REGION_ID="refresh_region_id";
	public static final String REFRESH_STATE_ID="refresh_state_id";
	public static final String REFRESH_CITY_ID="refresh_city_id";
	public static final String EVENT_PAGINATE = "paginate";
	public static final String EVENT_CHECKER_PAGINATE = "checker_paginate";
	
	
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		 if ((event.equals(CHECKER_LIST_SYSTEMBANK))||event.equals(MAKER_LIST_SYSTEMBANK) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListSystemBankCmd");
		}else if ((event.equals(CHECKER_VIEW_SYSTEMBANK))|| event.equals(MAKER_VIEW_SYSTEMBANK)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadSystemBankCmd");
		}else if ((event != null) && event.equals(MAKER_EDIT_SYSTEMBANK_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadSystemBankCmd");
		}else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)) || event.equals(MAKER_EDIT_SYSTEMBANK)||event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditSystemBankCmd");
		}else if ((event.equals(CHECKER_EDIT_READ)||event.equals(REJECTED_DELETE_READ))||event.equals(CHECKER_VIEW_SYSTEMBANK)||event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadSystemBankCmd");
		}else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditSystemBankCmd");
		}else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditSystemBankCmd");
		}else if ((event.equals(MAKER_PREPARE_CLOSE)) || event.equals(MAKER_PREPARE_RESUBMIT)|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadSystemBankCmd");
		}else if (event.equals(MAKER_CONFIRM_CLOSE)|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseSystemBankCmd");
        }else if (event.equals(MAKER_SEARCH_LIST_SYSTEMBANKBRANCH) || event.equals(CHECKER_SEARCH_LIST_SYSTEMBANKBRANCH)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SearchListSystemBankBranchCommand");

		}else if (event.equals(EVENT_PAGINATE) || event.equals(EVENT_CHECKER_PAGINATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		} 
        
        else if (event.equals(MAKER_UPDATE_DRAFT_SYSTEMBANK)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSaveSystemBankCmd");

		}else if (event.equals(REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshRegionCmd");

		}else if (event.equals(REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshStateCmd");

		}else if (event.equals(REFRESH_CITY_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshCityCmd");

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
		return SystemBankValidator.validateInput( aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ( event.equals("maker_edit_systemBank") 
				|| event.equals(MAKER_EDIT_REJECT_EDIT) 
				|| event.equals(MAKER_SEARCH_SYSTEMBANK)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals("maker_update_draft_systemBank")
				|| event.equals("maker_save_update")
				|| event.equals("maker_search_list_systemBankBranch")
				|| event.equals("checker_search_list_systemBankBranch")
				
		)
		{
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
		DefaultLogger.debug(this, " Exception map error is " + exceptionMap.isEmpty());
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}
		else if ((resultMap.get("Error_EmpId") != null) && (resultMap.get("Error_EmpId")).equals("Error_EmpId")) {
			DefaultLogger.debug(this, "Inside Error_EmpId");
			aPage.setPageReference("maker_add_edit_systemBank_page");
			return aPage;
		}
		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		
		if (MAKER_EDIT_SYSTEMBANK.equals(event)||event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)|| event.equals("maker_update_draft_systemBank")) {
			errorEvent = "maker_add_edit_systemBank_error";
		}
		else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_systemBank_error";
		}
		else if (MAKER_EDIT_REJECT_EDIT.equals(event)) {
			errorEvent = "maker_add_edit_systemBank_error";
		}
		else if (CHECKER_APPROVE_EDIT.equals(event)) {
			errorEvent = "checker_approve_edit_error";
		}
		
		else if (event.equals(MAKER_SEARCH_SYSTEMBANK)) {
			errorEvent = "maker_prepare_search_systemBank";
		}else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}else if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}else if (event.equals("checker_reject_create")) {
			errorEvent = "checker_reject_create_error";
		}else if (event.equals("checker_reject_delete")) {
			errorEvent = "checker_reject_delete_error";
		}else if (event.equals("maker_search_list_systemBankBranch")) {
			errorEvent = "maker_search_list_systemBankBranch_error";
		}else if (event.equals("checker_search_list_systemBankBranch")) {
			errorEvent = "checker_search_list_systemBankBranch_error";
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
		if ((event.equals(CHECKER_LIST_SYSTEMBANK)) || event.equals(MAKER_LIST_SYSTEMBANK)) {
			forwardName = "maker_list_systemBank_page";
		}
		else if ((event != null) && event.equals(MAKER_SEARCH_SYSTEMBANK)) {
			forwardName = "maker_list_systemBank_page";
		}
		else if ((event != null) && event.equals(LIST_PAGINATION)) {
			forwardName = "maker_list_systemBank_page";
		}
		else if ((event != null) && event.equals(REDIRECT_LIST_SYSTEMBANK)) {
			forwardName = "maker_list_systemBank_page";
		}
		else if ((event.equals(MAKER_SEARCH_LIST_SYSTEMBANKBRANCH)) ||event.equals(EVENT_PAGINATE) || event.equals(MAKER_VIEW_SYSTEMBANK) || event.equals("maker_search_list_systemBankBranch_error")) {
			forwardName = "maker_view_delete_page";
		}
		else if ((event != null) && event.equals(MAKER_EDIT_SYSTEMBANK_READ)) {
			forwardName = "maker_add_edit_systemBank_page";
		}
		else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)) || event.equals(MAKER_EDIT_SYSTEMBANK)||event.equals(MAKER_SAVE_UPDATE)) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals(MAKER_DELETE_SYSTEMBANK_READ)) {
			forwardName = "maker_view_delete_page";
		}
		else if ((event != null) && event.equals(CHECKER_EDIT_READ)) {
			forwardName = "checker_systemBank_page";
		}else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			forwardName = "common_approve_page";
		}else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
			forwardName = "checker_reject_edit";
		}else if ((event != null) && event.equals(REJECTED_DELETE_READ)) {
			forwardName = "maker_view_todo_page";
		}else if ((event != null) && event.equals(CHECKER_VIEW_SYSTEMBANK) || event.equals(EVENT_CHECKER_PAGINATE) || event.equals(CHECKER_SEARCH_LIST_SYSTEMBANKBRANCH)|| event.equals("checker_search_list_systemBankBranch_error")) {
			forwardName = "checker_view_delete_page";
		}else if ((event != null) && event.equals(MAKER_PREPARE_RESUBMIT)) {
			forwardName = "maker_prepare_resubmit";
		}else if ((event != null) && event.equals(MAKER_PREPARE_CLOSE)|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			forwardName = "maker_prepare_close";
		}else if ((event != null) && event.equals(MAKER_CONFIRM_CLOSE)|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
			forwardName = "maker_confirm_close";
		}else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			forwardName = "common_close_page";
		}
		else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			forwardName = "common_close_page";
		}
		else if ((event != null) && event.equals("maker_cncl_reject_delete")) {
			forwardName = "common_close_page";
		}else if ((event != null)
				&& (event.equals("maker_save_process")||event.equals("maker_update_save_process"))) {
			forwardName = "maker_view_save_page";
		} 
		else if ((event != null) && event.equals("maker_edit_reject_add")|| event.equals("maker_update_draft_systemBank")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("maker_edit_reject_delete")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		else if ((event != null) && event.equals("maker_add_edit_systemBank_error")) {
			forwardName = "maker_add_edit_systemBank_page";
		}else if ((event != null) && event.equals("maker_save_update_error")) {
			forwardName = "maker_view_save_page";
		}
		else if ((event != null) && event.equals("rejected_read")) {
			forwardName = "rejected_read_page";
		}
		else if ((event != null) && event.equals("checker_approve_edit_error")) {
			forwardName = "checker_systemBank_page";
		}
		else if ((event != null) && event.equals("maker_prepare_search_systemBank")) {
			forwardName = "search_systemBank_page";
		}else if (event.equals("refresh_state_id")) {
			forwardName = "refresh_state_id";
		} 
		else if (event.equals("refresh_region_id")) {
				forwardName = "refresh_region_id";
			}
		else if (event.equals("refresh_city_id")) {
			forwardName = "refresh_city_id";
		}else if ( event.equals("checker_reject_edit_error")
				||event.equals("checker_reject_create_error")
				||event.equals("checker_reject_delete_error")) {
			forwardName = "checker_systemBank_page";
		}

		return forwardName;
	}

	
}