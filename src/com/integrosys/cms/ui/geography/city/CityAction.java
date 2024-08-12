package com.integrosys.cms.ui.geography.city;

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
public class CityAction extends CommonAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";
	
	public static final String EVENT_PREPARE_LIST_CITY = "prepare_list_city";
	public static final String EVENT_REMOVE_CITY = "prepare_remove_city";

	public static final String EVENT_LIST_CITY = "view_list_city";
	public static final String EVENT_CHECKER_LIST_CITY = "checker_view_list_city";
	public static final String EVENT_VIEW_CITY_BY_INDEX = "view_city_by_index";
	public static final String EVENT_PREPARE_MAKER_EDIT_CITY = "prepare_maker_edit_city";
	public static final String EVENT_PREPARE_CREATE_CITY = "prepare_create_city";
	public static final String EVENT_TOTRACK_MAKER_CITY = "totrack_maker_city";
	public static final String EVENT_CHECKER_APPROVE_CITY = "checker_approve_city";
	public static final String EVENT_CHECKER_REJECT_CITY = "checker_reject_city";
	public static final String MAKER_CONFIRM_CLOSE_CITY = "maker_confirm_close_city";
	public static final String EVENT_CHECKER_PROCESS_CREATE_CITY = "checker_process_create_city";
	public static final String MAKER_EDIT_CITY_READ = "maker_edit_city_read";
	public static final String EVENT_MAKER_EDIT_CITY = "maker_edit_city";
	public static final String MAKER_DELETE_CITY_READ = "maker_delete_city_read";
	public static final String MAKER_DELETE_CITY = "maker_delete_city";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_CITY = "maker_prepare_activate_city";
	public static final String EVENT_MAKER_ACTIVATE_CITY = "maker_activate_city";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_CITY = "maker_confirm_resubmit_edit_city";
	public static final String EVENT_MAKER_CREATE_CITY = "maker_create_city";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_CITY = "maker_resubmit_create_city";
	public static final String EVENT_MAKER_PREPARE_CLOSE_CITY = "maker_prepare_close_city";
	public static final String EVENT_MAKER_SAVE_CITY = "maker_save_city";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_CITY = "todo_maker_save_create_city";
	public static final String EVENT_MAKER_CREATE_SAVED_CITY = "maker_create_saved_city";
	public static final String EVENT_MAKER_EDIT_SAVE_CREATED_CITY = "maker_edit_save_created_city";
	public static final String EVENT_MAKER_SAVE_EDIT_CITY = "maker_save_edit_city";
	public static final String EVENT_TODO_MAKER_SAVE_EDITED_CITY = "todo_maker_save_edited_city";
	public static final String EVENT_REFRESH_REGION_ID = "refresh_region_id";
	public static final String EVENT_REFRESH_STATE_ID = "refresh_state_id";
	public static final String EVENT_PAGINATE = "paginate";
	public static final String EVENT_CHECKER_PAGINATE = "checker_paginate";
	public static final String COMMON_CLOSE_PAGE = "common_close_page";
	
	public static final String MAKER_PREPARE_UPLOAD_CITY = "maker_prepare_upload_city";
	public static final String MAKER_UPLOAD_CITY = "maker_upload_city";
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	
	
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, " Event : -----> " + event);
		ICommand objArray[] = null;
		
		if (event.equals(EVENT_LIST_CITY) || event.equals(EVENT_CHECKER_LIST_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCityCommand");
		}
		else if (event.equals(EVENT_VIEW_CITY_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewCityByIndexCommand");
		} else if (event.equals(MAKER_CONFIRM_CLOSE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseCityCommand");
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditCityCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_CITY)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_CITY) || event
						.equals(EVENT_MAKER_SAVE_EDIT_CITY))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCityCommand");
		}

		else if (event.equals(MAKER_EDIT_CITY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCityCommand");
		} else if (event.equals(MAKER_DELETE_CITY_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteCityCommand");
		} else if (event.equals(MAKER_DELETE_CITY)
				|| event.equals(EVENT_MAKER_ACTIVATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteCityCommand");
		} else if (event.equals(EVENT_TOTRACK_MAKER_CITY)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_CITY)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE_CITY)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_CITY)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadCityCommand");
		}

		else if (event.equals(EVENT_PREPARE_CREATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCityCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_CITY)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_CITY)
				|| event.equals(EVENT_MAKER_SAVE_CITY)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateCityCommand");
		}
		else if ((event != null) && event.equals(EVENT_CHECKER_APPROVE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveCityCommmand");
		} else if ((event != null) && event.equals(EVENT_CHECKER_REJECT_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectCityCommmand");
		}
		else if (event.equals(EVENT_REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCountryCommmand");
		}		
		else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityCommmand");
		}
		else if (EVENT_PAGINATE.equals(event) || EVENT_CHECKER_PAGINATE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateCityListCommand") };
		}
		
//************* Lines added for File Upload ****************
		
		else if (event.equals(MAKER_UPLOAD_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerUploadCityCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_REJECTED_DELETE_READ)
						|| event.equals(CHECKER_PROCESS_CREATE_INSERT) || event
						.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCityCmd");

		} else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveInsertCityCmd");
		} else if (event.equals(CHECKER_REJECT_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectInsertCityCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerInsertCloseCityCmd");
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
		return CityValidator.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ( event.equals(EVENT_MAKER_CREATE_CITY)
				|| event.equals(EVENT_MAKER_EDIT_CITY)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_CITY) 
				|| event.equals(EVENT_MAKER_CREATE_SAVED_CITY)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_CITY)
				|| event.equals(EVENT_MAKER_SAVE_CITY))

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		
		if (EVENT_MAKER_CREATE_CITY.equals(event)) 
			errorEvent = EVENT_PREPARE_CREATE_CITY;
		
		else if (EVENT_MAKER_EDIT_CITY.equals(event))	
			errorEvent = "errorEditEvent";
		
		else if (MAKER_CONFIRM_RESUBMIT_EDIT_CITY.equals(event))	
			errorEvent = "errorReSubmitEvent";
		
		else if (EVENT_MAKER_CREATE_SAVED_CITY.equals(event))	
			errorEvent = "errorSaveEvent";
		
		else if (EVENT_CHECKER_REJECT_CITY.equals(event)) 
			 errorEvent = "errorReject";
		
		else if (EVENT_CHECKER_APPROVE_CITY.equals(event))
			errorEvent = "approveError";
		
		else if (CHECKER_REJECT_INSERT.equals(event))
				errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		
		else if (EVENT_MAKER_EDIT_SAVE_CREATED_CITY.equals(event))
			errorEvent = "errorEditEvent";
		
		else if (EVENT_MAKER_SAVE_CITY.equals(event))
			errorEvent = EVENT_PREPARE_CREATE_CITY;
//			errorEvent = "createError";
		
		else if (EVENT_LIST_CITY.equals(event))
			errorEvent = "listError";
		
		else if (EVENT_CHECKER_LIST_CITY.equals(event))
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
			aPage.setPageReference("maker_fileupload_city_page");
			return aPage;
		}
		else
			aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals(EVENT_PREPARE_LIST_CITY)) {
			forwardName = EVENT_PREPARE_LIST_CITY;
		}else if (event.equals("editError")) {
			forwardName = "prepare_maker_edit_city";
		} else if (event.equals(EVENT_LIST_CITY) || event.equals(EVENT_PAGINATE) || event.equals("listError") ) {
			forwardName = EVENT_LIST_CITY;
		} else if (event.equals(EVENT_CHECKER_LIST_CITY) || event.equals(EVENT_CHECKER_PAGINATE) || event.equals("checkerListError") ) {
			forwardName = EVENT_CHECKER_LIST_CITY;
		}else if (event.equals(EVENT_REMOVE_CITY)) {
			forwardName = EVENT_REMOVE_CITY;
		} else if (event.equals(EVENT_VIEW_CITY_BY_INDEX)) {
			forwardName = EVENT_VIEW_CITY_BY_INDEX;
		} else if (event.equals("editError")) {
			forwardName = "prepare_maker_edit_city";
		} else if (event.equals(EVENT_MAKER_CREATE_CITY)) {
			forwardName = EVENT_MAKER_CREATE_CITY;
		} else if (event.equals(EVENT_MAKER_CREATE_SAVED_CITY) 
			|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_CITY)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} else if (event.equals(EVENT_TOTRACK_MAKER_CITY)) {
			forwardName = EVENT_TOTRACK_MAKER_CITY;
		} else if (event.equals(EVENT_PREPARE_CREATE_CITY) /*|| event.equals("createError")*/ ) {
			forwardName = EVENT_PREPARE_CREATE_CITY;
		} else if (event.equals(EVENT_CHECKER_APPROVE_CITY)) {
			forwardName = EVENT_CHECKER_APPROVE_CITY;
		} else if (event.equals(EVENT_CHECKER_REJECT_CITY)) {
			forwardName = EVENT_CHECKER_REJECT_CITY;
		}  else if (event.equals("errorReject")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_CITY;
		}else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_CITY)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_CITY;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_CITY) || event.equals("errorSaveEvent")) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_CITY;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_CITY) || event.equals("errorEditEvent")) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_CITY;
		} else if (event.equals(EVENT_MAKER_EDIT_CITY)) {
			forwardName = EVENT_MAKER_EDIT_CITY;
		} else if (event.equals(EVENT_MAKER_PREPARE_CLOSE_CITY)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE_CITY;
		} else if (MAKER_DELETE_CITY_READ.equals(event)) {
			forwardName = MAKER_DELETE_CITY_READ;
		} else if (MAKER_DELETE_CITY.equals(event)) {
			forwardName = MAKER_DELETE_CITY;
		}

		else if (EVENT_MAKER_PREPARE_ACTIVATE_CITY.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_CITY;
		}

		else if (EVENT_MAKER_ACTIVATE_CITY.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_CITY;
		}

		else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_CITY) || event.equals("errorReSubmitEvent") ) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE_CITY)) {
			forwardName = MAKER_CONFIRM_CLOSE_CITY;
		}

		else if (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_CITY)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}
		else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			forwardName = EVENT_REFRESH_STATE_ID;
		} 
		else if (event.equals(EVENT_REFRESH_REGION_ID)) {
				forwardName = EVENT_REFRESH_REGION_ID;
			}

		else if (event.equals(EVENT_MAKER_SAVE_CITY)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_CITY)) {
			forwardName = EVENT_MAKER_SAVE_CITY;
		}
		
		
		else if (MAKER_PREPARE_UPLOAD_CITY.equals(event)) {
			forwardName = MAKER_PREPARE_UPLOAD_CITY;
		}
		else if (MAKER_UPLOAD_CITY.equals(event)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} 
		else if ((event != null) && event.equals(CHECKER_REJECT_INSERT)) 
		{
			forwardName = "common_reject_page";
		} 
		else if (event.equals(CHECKER_PROCESS_CREATE_INSERT)) 
		{
			forwardName = "checker_city_insert_page";
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
		else if(MAKER_UPLOAD_CITY.equals(event)){
			forwardName = MAKER_PREPARE_UPLOAD_CITY;
		}
		else if(CHECKER_APPROVE_INSERT.equals(event)){
			forwardName = COMMON_SUBMIT_PAGE;
		}
		else if (event.equals("approveError")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_CITY;
		}
		
		DefaultLogger.debug(this, "Forward name --> " + forwardName);
		return forwardName;
	}
}
