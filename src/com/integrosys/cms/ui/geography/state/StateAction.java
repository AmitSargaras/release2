package com.integrosys.cms.ui.geography.state;

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
public class StateAction extends CommonAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	
	public static final String EVENT_PREPARE_LIST_STATE = "prepare_list_state";
	public static final String EVENT_REMOVE_STATE = "prepare_remove_state";

	public static final String EVENT_LIST_STATE = "view_list_state";
	public static final String EVENT_CHECKER_LIST_STATE = "checker_view_list_state";
	public static final String EVENT_VIEW_STATE_BY_INDEX = "view_state_by_index";
	public static final String EVENT_PREPARE_MAKER_EDIT_STATE = "prepare_maker_edit_state";
	public static final String EVENT_PREPARE_CREATE_STATE = "prepare_create_state";
	public static final String EVENT_TOTRACK_MAKER_STATE = "totrack_maker_state";
	public static final String EVENT_CHECKER_APPROVE_STATE = "checker_approve_state";
	public static final String EVENT_CHECKER_REJECT_STATE = "checker_reject_state";
	public static final String MAKER_CONFIRM_CLOSE_STATE = "maker_confirm_close_state";
	public static final String EVENT_CHECKER_PROCESS_CREATE_STATE = "checker_process_create_state";
	public static final String MAKER_EDIT_STATE_READ = "maker_edit_state_read";
	public static final String EVENT_MAKER_EDIT_STATE = "maker_edit_state";
	public static final String MAKER_DELETE_STATE_READ = "maker_delete_state_read";
	public static final String MAKER_DELETE_STATE = "maker_delete_state";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_STATE = "maker_prepare_activate_state";
	public static final String EVENT_MAKER_ACTIVATE_STATE = "maker_activate_state";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_STATE = "maker_confirm_resubmit_edit_state";
	public static final String EVENT_MAKER_CREATE_STATE = "maker_create_state";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_STATE = "maker_resubmit_create_state";
	public static final String EVENT_MAKER_PREPARE_CLOSE_STATE = "maker_prepare_close_state";
	public static final String EVENT_REFRESH_REGION_ID = "refresh_region_id";
	public static final String EVENT_REFRESH_STATE_ID = "refresh_state_id";
	public static final String EVENT_REFRESH_CITY_LIST = "refresh_city_list";
	public static final String EVENT_MAKER_SAVE_STATE = "maker_save_state";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_STATE = "todo_maker_save_create_state";
	public static final String EVENT_MAKER_CREATE_SAVED_STATE = "maker_create_saved_state";
	public static final String EVENT_MAKER_SAVE_EDIT_STATE = "maker_save_edit_state";
	public static final String EVENT_MAKER_EDIT_SAVE_CREATED_STATE = "maker_edit_save_created_state";
	/** For saving into session and then going to list page. */
	public static final String EVENT_PAGINATE = "paginate";
	public static final String EVENT_CHECKER_PAGINATE = "checker_paginate";
	public static final String COMMON_CLOSE_PAGE = "common_close_page";
	
	public static final String MAKER_PREPARE_UPLOAD_STATE = "maker_prepare_upload_state";
	public static final String MAKER_UPLOAD_STATE = "maker_upload_state";
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, " Event : -----> " + event);
		ICommand objArray[] = null;
		
		if (event.equals(MAKER_CONFIRM_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseCountryCommand");
		}
		else if (event.equals(EVENT_PREPARE_LIST_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareListStateCommand");
		} else if (event.equals(EVENT_LIST_STATE) || event.equals(EVENT_CHECKER_LIST_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap()
					.get("ListStateCommand");
		} else if (event.equals(EVENT_REMOVE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RemoveStateCommand");
		}
		else if (event.equals(EVENT_VIEW_STATE_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewStateByIndexCommand");
		} else if (event.equals(MAKER_CONFIRM_CLOSE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseStateCommand");
		}

		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditStateCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_STATE)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_STATE) || (event
						.equals(EVENT_MAKER_SAVE_EDIT_STATE)))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditStateCommand");
		}

		else if (event.equals(MAKER_EDIT_STATE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadStateCommand");
		} else if (event.equals(MAKER_DELETE_STATE_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteStateCommand");
		} else if (event.equals(MAKER_DELETE_STATE)
				|| event.equals(EVENT_MAKER_ACTIVATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteStateCommand");
		} else if (event.equals(EVENT_TOTRACK_MAKER_STATE)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_STATE)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE_STATE)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_STATE)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadStateCommand");
		}

		else if (event.equals(EVENT_PREPARE_CREATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateStateCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_STATE)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_STATE)
				|| event.equals(EVENT_MAKER_SAVE_STATE)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateStateCommand");
		}
		else if ((event != null) && event.equals(EVENT_CHECKER_APPROVE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveStateCommmand");
		} else if ((event != null) && event.equals(EVENT_CHECKER_REJECT_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectStateCommmand");
		}
		else if (event.equals(EVENT_REFRESH_CITY_LIST)
				|| event.equals(EVENT_REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshStateCommmand");
		}
		else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityCommmand");
		}
		else if (EVENT_PAGINATE.equals(event) || EVENT_CHECKER_PAGINATE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateStateListCommand") };
		}
		
		//************* Lines added for File Upload ****************
		
		else if (event.equals(MAKER_UPLOAD_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerUploadStateCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_REJECTED_DELETE_READ)
						|| event.equals(CHECKER_PROCESS_CREATE_INSERT) || event
						.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListStateCmd");

		} else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveInsertStateCmd");
		} else if (event.equals(CHECKER_REJECT_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectInsertStateCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerInsertCloseStateCmd");
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
		return StateValidator.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ( event.equals(EVENT_MAKER_CREATE_STATE)
				|| event.equals(EVENT_MAKER_EDIT_STATE)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_STATE)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_STATE)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_STATE)
				|| event.equals(EVENT_MAKER_SAVE_STATE))

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		
		if (EVENT_MAKER_CREATE_STATE.equals(event)) 
			errorEvent = EVENT_PREPARE_CREATE_STATE;
		
		else if (EVENT_MAKER_EDIT_STATE.equals(event))	
			errorEvent = "errorEditEvent";
		
		else if (MAKER_CONFIRM_RESUBMIT_EDIT_STATE.equals(event))	
			errorEvent = "errorReSubmitEvent";
		
		else if (EVENT_MAKER_CREATE_SAVED_STATE.equals(event))	
			errorEvent = "errorSaveEvent";
		
		 else if (EVENT_CHECKER_REJECT_STATE.equals(event)) 
			 errorEvent = "errorReject";
		
		 else if (EVENT_CHECKER_APPROVE_STATE.equals(event))
				errorEvent = "approveError";
		
		 else if (CHECKER_REJECT_INSERT.equals(event))
				errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		
		 else if (EVENT_MAKER_EDIT_SAVE_CREATED_STATE.equals(event))
				errorEvent = "errorEditEvent";
			
		else if (EVENT_MAKER_SAVE_STATE.equals(event))
			errorEvent = EVENT_PREPARE_CREATE_STATE;
//			errorEvent = "createError";
		
		else if (EVENT_LIST_STATE.equals(event))
			errorEvent = "listError";
		
		else if (EVENT_CHECKER_LIST_STATE.equals(event))
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
			aPage.setPageReference("maker_fileupload_state_page");
			return aPage;
		}
		else
			aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = null;
		
		 if (event.equals(EVENT_PREPARE_LIST_STATE)) {
			forwardName = EVENT_PREPARE_LIST_STATE;
		} else if (event.equals(EVENT_LIST_STATE) || event.equals(EVENT_PAGINATE) || event.equals("listError") ) {
			forwardName = EVENT_LIST_STATE;
		} else if (event.equals(EVENT_CHECKER_LIST_STATE) || event.equals(EVENT_CHECKER_PAGINATE) || event.equals("checkerListError") ) {
			forwardName = EVENT_CHECKER_LIST_STATE;
		} else if (event.equals(EVENT_REMOVE_STATE)) {
			forwardName = EVENT_REMOVE_STATE;
		} else if (event.equals(EVENT_VIEW_STATE_BY_INDEX)) {
			forwardName = EVENT_VIEW_STATE_BY_INDEX;
		} else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			forwardName = MAKER_CONFIRM_CLOSE;
		} else if (event.equals("editError")) {
			forwardName = "prepare_maker_edit_state";
		} else if (event.equals(EVENT_MAKER_CREATE_STATE)) {
			forwardName = EVENT_MAKER_CREATE_STATE;
		} else if (event.equals(EVENT_TOTRACK_MAKER_STATE)) {
			forwardName = EVENT_TOTRACK_MAKER_STATE;
		} else if (event.equals(EVENT_PREPARE_CREATE_STATE) /*|| event.equals("createError")*/ ) {
			forwardName = EVENT_PREPARE_CREATE_STATE;
		} else if (event.equals(EVENT_CHECKER_APPROVE_STATE)) {
			forwardName = EVENT_CHECKER_APPROVE_STATE;
		} else if (event.equals(EVENT_CHECKER_REJECT_STATE)) {
			forwardName = EVENT_CHECKER_REJECT_STATE;
		}  else if (event.equals("errorReject")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_STATE;
		}else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_STATE)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_STATE;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_STATE) || event.equals("errorSaveEvent")) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_STATE;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_STATE) || event.equals("errorEditEvent")) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_STATE;
		} else if (event.equals(EVENT_MAKER_EDIT_STATE)) {
			forwardName = EVENT_MAKER_EDIT_STATE;
		} else if (event.equals(EVENT_MAKER_PREPARE_CLOSE_STATE)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE_STATE;
		} else if (MAKER_DELETE_STATE_READ.equals(event)) {
			forwardName = MAKER_DELETE_STATE_READ;
		} else if (MAKER_DELETE_STATE.equals(event)) {
			forwardName = MAKER_DELETE_STATE;
		} else if (EVENT_MAKER_PREPARE_ACTIVATE_STATE.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_STATE;
		} else if (EVENT_MAKER_ACTIVATE_STATE.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_STATE;
		} else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_STATE) || event.equals("errorReSubmitEvent")) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE_STATE)) {
			forwardName = MAKER_CONFIRM_CLOSE_STATE;
		} else if (event.equals(EVENT_REFRESH_REGION_ID)) {
			forwardName = EVENT_REFRESH_REGION_ID;
		} else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			forwardName = EVENT_REFRESH_STATE_ID;
		} else if (event.equals(EVENT_REFRESH_CITY_LIST)) {
			forwardName = EVENT_REFRESH_CITY_LIST;
		}
		else if ( event.equals(EVENT_MAKER_CREATE_SAVED_STATE)
			|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_STATE)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}
		else if ( event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_STATE)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}
		else if (event.equals(EVENT_MAKER_SAVE_STATE)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_STATE)) {
			forwardName = EVENT_MAKER_SAVE_STATE;
		}
		 
		else if (MAKER_PREPARE_UPLOAD_STATE.equals(event)) {
			forwardName = MAKER_PREPARE_UPLOAD_STATE;
		}
		else if (MAKER_UPLOAD_STATE.equals(event)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} 
		else if ((event != null) && event.equals(CHECKER_REJECT_INSERT)) 
		{
			forwardName = "common_reject_page";
		} 
		else if (event.equals(CHECKER_PROCESS_CREATE_INSERT)) 
		{
			forwardName = "checker_state_insert_page";
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
		else if(MAKER_UPLOAD_STATE.equals(event)){
			forwardName = MAKER_PREPARE_UPLOAD_STATE;
		}
		else if(CHECKER_APPROVE_INSERT.equals(event)){
			forwardName = COMMON_SUBMIT_PAGE;
		}
		else if (event.equals("approveError")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_STATE;
		}
		 
		DefaultLogger.debug(this, "Forward name --> " + forwardName);
		return forwardName;
	}
}
