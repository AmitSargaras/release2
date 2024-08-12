package com.integrosys.cms.ui.geography.country;

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
 * This Action class is used for Geography master which include all commands
 * like, delete view and list.
 * 
 * @author sandiip.shinde
 * 
 */

public class CountryAction extends CommonAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String EVENT_PREPARE_LIST_COUNTRY = "prepare_list_country";

	public static final String EVENT_LIST_COUNTRY = "view_list_country";
	public static final String EVENT_CHECKER_LIST_COUNTRY = "checker_view_list_country";
	public static final String EVENT_VIEW_COUNTRY_BY_INDEX = "view_country_by_index";
	public static final String EVENT_MAKER_PREPARE_CREATE_COUNTRY = "maker_prepare_create_country";
	public static final String EVENT_PREPARE_CREATE_COUNTRY = "prepare_create_country";
	public static final String EVENT_TOTRACK_MAKER_COUNTRY = "totrack_maker_country";
	public static final String EVENT_CHECKER_APPROVE_COUNTRY = "checker_approve_country";
	public static final String EVENT_CHECKER_REJECT_COUNTRY = "checker_reject_country";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String EVENT_CHECKER_PROCESS_CREATE_COUNTRY = "checker_process_create_country";
	public static final String MAKER_EDIT_COUNTRY_READ = "maker_edit_country_read";
	public static final String EVENT_PREPARE_MAKER_EDIT_COUNTRY = "prepare_maker_edit_country";
	public static final String EVENT_MAKER_EDIT_COUNTRY = "maker_edit_country";
	public static final String MAKER_DELETE_COUNTRY_READ = "maker_delete_country_read";
	public static final String MAKER_DELETE_COUNTRY = "maker_delete_country";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY = "maker_prepare_activate_country";
	public static final String EVENT_MAKER_ACTIVATE_COUNTRY = "maker_activate_country";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String EVENT_MAKER_CREATE_COUNTRY = "maker_create_country";
	public static final String EVENT_REMOVE_COUNTRY = "prepare_remove_country";
	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_COUNTRY = "maker_resubmit_create_country";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String EVENT_CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String EVENT_MAKER_PREPARE_CLOSE = "maker_prepare_close";

	public static final String EVENT_MAKER_SAVE_COUNTRY = "maker_save_country";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY = "todo_maker_save_create_country";
	public static final String EVENT_MAKER_CREATE_SAVED_COUNTRY = "maker_create_saved_country";
	public static final String EVENT_MAKER_SAVE_EDIT_COUNTRY = "maker_save_edit_country";
	public static final String EVENT_MAKER_EDIT_SAVE_CREATED_COUNTRY = "maker_edit_save_created_country";
	public static final String EVENT_PAGINATE = "paginate";
	public static final String EVENT_CHECKER_PAGINATE = "checker_paginate";
	public static final String COMMON_CLOSE_PAGE = "common_close_page";
	
	
	public static final String MAKER_PREPARE_UPLOAD_COUNTRY = "maker_prepare_upload_country";
	public static final String MAKER_UPLOAD_COUNTRY = "maker_upload_country";
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, " Event : -----> " + event);
		ICommand objArray[] = null;
		if (event.equals(EVENT_PREPARE_CREATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCountryCommand");
		} else if (event.equals(EVENT_PREPARE_LIST_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareListCountryCommand");
		} else if (event.equals(EVENT_LIST_COUNTRY) || event.equals(EVENT_CHECKER_LIST_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListCountryCommand");
		} else if (event.equals(EVENT_VIEW_COUNTRY_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewCountryByIndexCommand");
		}
		else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseCountryCommand");
		}

		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditCountryCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_COUNTRY)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT) || (event
						.equals(EVENT_MAKER_SAVE_EDIT_COUNTRY)))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCountryCommand");
		}
		else if (event.equals(MAKER_EDIT_COUNTRY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCountryCommand");
		} else if (event.equals(MAKER_DELETE_COUNTRY_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteCountryCommand");
		} else if (event.equals(MAKER_DELETE_COUNTRY)
				|| event.equals(EVENT_MAKER_ACTIVATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteCountryCommand");
		}
		else if (event.equals(EVENT_TOTRACK_MAKER_COUNTRY)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_COUNTRY)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_COUNTRY)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadCountryCommand");
		}

		else if (event.equals(EVENT_MAKER_PREPARE_CREATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCountryCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_COUNTRY)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_COUNTRY)
				|| event.equals(EVENT_MAKER_SAVE_COUNTRY)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateCountryCommand");
		}
		else if ((event != null) && event.equals(EVENT_CHECKER_APPROVE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveCountryCommmand");
		} else if ((event != null)
				&& event.equals(EVENT_CHECKER_REJECT_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectCountryCommmand");
		}

		else if (event.equals(EVENT_REMOVE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RemoveCountryCommand");
		}
		else if (EVENT_PAGINATE.equals(event) || EVENT_CHECKER_PAGINATE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateCountryListCommand") };
		} 
		
		//************* Lines added for File Upload ****************
		
		else if (event.equals(MAKER_UPLOAD_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerUploadCountryCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_REJECTED_DELETE_READ)
						|| event.equals(CHECKER_PROCESS_CREATE_INSERT) || event
						.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCmd");

		} else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveInsertCountryCmd");
		} else if (event.equals(CHECKER_REJECT_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectInsertCountryCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerInsertCloseCountryCmd");
		}
		
		return objArray;
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
		return CountryValidator.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ( event.equals(EVENT_MAKER_CREATE_COUNTRY)
				|| event.equals(EVENT_MAKER_EDIT_COUNTRY)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_COUNTRY )
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_COUNTRY)
				|| event.equals(EVENT_MAKER_SAVE_COUNTRY))

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		
		if (EVENT_MAKER_CREATE_COUNTRY.equals(event)) 
			errorEvent = EVENT_PREPARE_CREATE_COUNTRY;
		 
		else if (EVENT_MAKER_EDIT_COUNTRY.equals(event))
			errorEvent = "errorEditEvent";
		
		else if (MAKER_CONFIRM_RESUBMIT_EDIT.equals(event))	
				errorEvent = "errorReSubmitEvent";
			
		else if (EVENT_MAKER_CREATE_SAVED_COUNTRY.equals(event))	
			errorEvent = "errorSaveEvent";
		
		else if (EVENT_CHECKER_REJECT_COUNTRY.equals(event)) 
			 errorEvent = "errorReject";
		
		else if (EVENT_CHECKER_APPROVE_COUNTRY.equals(event))
			errorEvent = "approveError";
		
		else if (CHECKER_REJECT_INSERT.equals(event))
			errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		
		else if (EVENT_MAKER_EDIT_SAVE_CREATED_COUNTRY.equals(event))
			errorEvent = "errorEditEvent";
		
		else if (EVENT_MAKER_SAVE_COUNTRY.equals(event))
			errorEvent = "createError";
		
		else if (EVENT_LIST_COUNTRY.equals(event))
			errorEvent = "listError";
		
		else if (EVENT_CHECKER_LIST_COUNTRY.equals(event))
			errorEvent = "checkerListError";
		
		return errorEvent;
	}
	
	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		if ((resultMap.get("wip") != null)
				&& ((String) resultMap.get("wip")).equals("wip"))
			aPage.setPageReference("work_in_process");
		else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
			aPage.setPageReference("maker_fileupload_country_page");
			return aPage;
		}else
			aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals(EVENT_PREPARE_LIST_COUNTRY)) {
			forwardName = EVENT_PREPARE_LIST_COUNTRY;
		} else if (event.equals(EVENT_LIST_COUNTRY) || event.equals(EVENT_PAGINATE) || event.equals("listError") ) {
			forwardName = EVENT_LIST_COUNTRY;
		} else if (event.equals(EVENT_CHECKER_LIST_COUNTRY) || event.equals(EVENT_CHECKER_PAGINATE) || event.equals("checkerListError") ) {
			forwardName = EVENT_CHECKER_LIST_COUNTRY;
		} else if (event.equals(EVENT_REMOVE_COUNTRY)) {
			forwardName = EVENT_REMOVE_COUNTRY;
		} else if (event.equals(EVENT_VIEW_COUNTRY_BY_INDEX)) {
			forwardName = EVENT_VIEW_COUNTRY_BY_INDEX;
		} else if (event.equals(MAKER_EDIT_COUNTRY_READ)) {
			forwardName = MAKER_EDIT_COUNTRY_READ;
		} else if (event.equals(EVENT_MAKER_PREPARE_CREATE_COUNTRY) ) {
			forwardName = EVENT_MAKER_PREPARE_CREATE_COUNTRY;
		} else if (event.equals(EVENT_MAKER_CREATE_COUNTRY)) {
			forwardName = EVENT_MAKER_CREATE_COUNTRY;
		} else if (event.equals("editError")) {
			forwardName = "prepare_maker_edit_country";
		} else if (event.equals(EVENT_TOTRACK_MAKER_COUNTRY)) {
			forwardName = EVENT_TOTRACK_MAKER_COUNTRY;
		} else if (event.equals(EVENT_PREPARE_CREATE_COUNTRY) || event.equals("createError") ) {
			forwardName = EVENT_PREPARE_CREATE_COUNTRY;
		} else if (event.equals(EVENT_CHECKER_APPROVE_COUNTRY)) {
			forwardName = EVENT_CHECKER_APPROVE_COUNTRY;
		} else if (event.equals(EVENT_CHECKER_REJECT_COUNTRY)) {
			forwardName = EVENT_CHECKER_REJECT_COUNTRY;
		} else if (event.equals("errorReject")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_COUNTRY;
		} else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_COUNTRY)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_COUNTRY;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY) || event.equals("errorSaveEvent")) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_COUNTRY) || event.equals("errorEditEvent")) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_COUNTRY;
		} else if (event.equals(EVENT_MAKER_EDIT_COUNTRY)) {
			forwardName = EVENT_MAKER_EDIT_COUNTRY;
		} else if (EVENT_MAKER_PREPARE_CLOSE.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE;
		} else if (MAKER_DELETE_COUNTRY_READ.equals(event)) {
			forwardName = MAKER_DELETE_COUNTRY_READ;
		} else if (MAKER_DELETE_COUNTRY.equals(event)) {
			forwardName = MAKER_DELETE_COUNTRY;
		}
		else if (EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY;
		} else if (EVENT_MAKER_ACTIVATE_COUNTRY.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_COUNTRY;
		}
		else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_COUNTRY) || event.equals("errorReSubmitEvent")) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			forwardName = MAKER_CONFIRM_CLOSE;
		}
		else if (event.equals(EVENT_MAKER_CREATE_SAVED_COUNTRY) 
			||  event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_COUNTRY)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}
		else if (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} else if (event.equals(EVENT_MAKER_SAVE_COUNTRY)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_COUNTRY)) {
			forwardName = EVENT_MAKER_SAVE_COUNTRY;
		}
		
		else if (MAKER_PREPARE_UPLOAD_COUNTRY.equals(event)) {
			forwardName = MAKER_PREPARE_UPLOAD_COUNTRY;
		}
		else if (MAKER_UPLOAD_COUNTRY.equals(event)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} 
		else if ((event != null) && event.equals(CHECKER_REJECT_INSERT)) 
		{
			forwardName = "common_reject_page";
		} 
		else if (event.equals(CHECKER_PROCESS_CREATE_INSERT)) 
		{
			forwardName = "checker_country_insert_page";
		} 
		else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			forwardName = COMMON_CLOSE_PAGE;
		}
		else if ((event != null)
				&& (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			forwardName = MAKER_PREPARE_INSERT_CLOSE;
		} 
		else if (event != null && event.equals(MAKER_REJECTED_DELETE_READ)) {
			forwardName = "maker_view_insert_todo_page";
		}
		else if(MAKER_UPLOAD_COUNTRY.equals(event)){
			forwardName = MAKER_PREPARE_UPLOAD_COUNTRY;
		}
		else if(CHECKER_APPROVE_INSERT.equals(event)){
			forwardName = COMMON_SUBMIT_PAGE;
		}
		
		else if (event.equals("approveError")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_COUNTRY;
		}
		DefaultLogger.debug(this, "Forward name --> " + forwardName);
		return forwardName;
	}
}
