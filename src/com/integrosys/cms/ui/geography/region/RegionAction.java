package com.integrosys.cms.ui.geography.region;

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
public class RegionAction extends CommonAction {

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
	public static final String EVENT_REMOVE_REGION = "prepare_remove_region";
	
	public static final String EVENT_LIST_REGION = "view_list_region";
	public static final String EVENT_CHECKER_LIST_REGION = "checker_view_list_region";
	public static final String EVENT_VIEW_REGION_BY_INDEX = "view_region_by_index";
	public static final String EVENT_PREPARE_MAKER_EDIT_REGION = "prepare_maker_edit_region";
	public static final String EVENT_PREPARE_CREATE_REGION = "prepare_create_region";
	public static final String EVENT_TOTRACK_MAKER_REGION = "totrack_maker_region";
	public static final String EVENT_CHECKER_APPROVE_REGION = "checker_approve_region";
	public static final String EVENT_CHECKER_REJECT_REGION = "checker_reject_region";
	public static final String MAKER_CONFIRM_CLOSE_REGION = "maker_confirm_close_region";
	public static final String EVENT_CHECKER_PROCESS_CREATE_REGION = "checker_process_create_region";
	public static final String MAKER_EDIT_REGION_READ = "maker_edit_region_read";
	public static final String EVENT_MAKER_EDIT_REGION = "maker_edit_region";
	public static final String MAKER_DELETE_REGION_READ = "maker_delete_region_read";
	public static final String MAKER_DELETE_REGION = "maker_delete_region";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_REGION = "maker_prepare_activate_region";
	public static final String EVENT_MAKER_ACTIVATE_REGION = "maker_activate_region";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_REGION = "maker_confirm_resubmit_edit_region";
	public static final String EVENT_MAKER_CREATE_REGION = "maker_create_region";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_REGION = "maker_resubmit_create_region";
	public static final String EVENT_MAKER_PREPARE_CLOSE_REGION = "maker_prepare_close_region";
	public static final String EVENT_MAKER_SAVE_REGION = "maker_save_region";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_REGION = "todo_maker_save_create_region";
	public static final String EVENT_MAKER_CREATE_SAVED_REGION = "maker_create_saved_region";
	public static final String EVENT_MAKER_SAVE_EDIT_REGION = "maker_save_edit_region";
	public static final String EVENT_MAKER_EDIT_SAVE_CREATED_REGION = "maker_edit_save_created_region";
	public static final String EVENT_PAGINATE = "paginate";
	public static final String EVENT_CHECKER_PAGINATE = "checker_paginate";
	public static final String COMMON_CLOSE_PAGE = "common_close_page";
	
	public static final String MAKER_PREPARE_UPLOAD_REGION = "maker_prepare_upload_region";
	public static final String MAKER_UPLOAD_REGION = "maker_upload_region";
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
		else if (event.equals(EVENT_LIST_REGION) || event.equals(EVENT_CHECKER_LIST_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListRegionCommand");
		} else if (event.equals(EVENT_REMOVE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RemoveRegionCommand");
		} 

		else if (event.equals(EVENT_VIEW_REGION_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewRegionByIndexCommand");
		} else if (event.equals(MAKER_CONFIRM_CLOSE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseRegionCommand");
		}

		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditRegionCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_REGION)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REGION) || (event
						.equals(EVENT_MAKER_SAVE_EDIT_REGION)))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditRegionCommand");
		}

		else if (event.equals(MAKER_EDIT_REGION_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadRegionCommand");
		} else if (event.equals(MAKER_DELETE_REGION_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteRegionCommand");
		} else if (event.equals(MAKER_DELETE_REGION)
				|| event.equals(EVENT_MAKER_ACTIVATE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteRegionCommand");
		} else if (event.equals(EVENT_TOTRACK_MAKER_REGION)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_REGION)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE_REGION)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_REGION)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_REGION)
				|| event.equals(MAKER_PREPARE_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadRegionCommand");
		}

		else if (event.equals(EVENT_PREPARE_CREATE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateRegionCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_REGION)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_REGION)
				|| event.equals(EVENT_MAKER_SAVE_REGION)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateRegionCommand");
		}

		else if ((event != null) && event.equals(EVENT_CHECKER_APPROVE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveRegionCommmand");
		} else if ((event != null) && event.equals(EVENT_CHECKER_REJECT_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectRegionCommmand");
		}
		else if (EVENT_PAGINATE.equals(event) || EVENT_CHECKER_PAGINATE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateRegionListCommand") };
		}
		
		
		//************* Lines added for File Upload ****************
		
		else if (event.equals(MAKER_UPLOAD_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerUploadRegionCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_REJECTED_DELETE_READ)
						|| event.equals(CHECKER_PROCESS_CREATE_INSERT) || event
						.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListRegionCmd");

		} else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveInsertRegionCmd");
		} else if (event.equals(CHECKER_REJECT_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectInsertRegionCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerInsertCloseRegionCmd");
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
		return RegionValidator.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ( event.equals(EVENT_MAKER_CREATE_REGION)
				|| event.equals(EVENT_MAKER_EDIT_REGION)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REGION)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_REGION)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_REGION)
				|| event.equals(EVENT_MAKER_SAVE_REGION))

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		
		if (EVENT_MAKER_CREATE_REGION.equals(event)) 
			errorEvent = EVENT_PREPARE_CREATE_REGION;
		
		else if (EVENT_MAKER_EDIT_REGION.equals(event))	
			errorEvent = "errorEditEvent";
		
		else if (MAKER_CONFIRM_RESUBMIT_EDIT_REGION.equals(event))	
				errorEvent = "errorReSubmitEvent";
			
		else if (EVENT_MAKER_CREATE_SAVED_REGION.equals(event))	
			errorEvent = "errorSaveEvent";
		
		else if (EVENT_CHECKER_REJECT_REGION.equals(event)) 
			errorEvent = "errorReject";
		
		else if (EVENT_CHECKER_APPROVE_REGION.equals(event))
			errorEvent = "approveError";
		
		else if (CHECKER_REJECT_INSERT.equals(event))
			errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		
		else if (EVENT_MAKER_EDIT_SAVE_CREATED_REGION.equals(event))
			errorEvent = "errorEditEvent";
		
		else if (EVENT_MAKER_SAVE_REGION.equals(event))
			errorEvent = "createError";
		
		else if (EVENT_LIST_REGION.equals(event))
			errorEvent = "listError";
		
		else if (EVENT_CHECKER_LIST_REGION.equals(event))
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
			aPage.setPageReference("maker_fileupload_region_page");
			return aPage;
		}
		else
			aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals(EVENT_LIST_REGION) || event.equals(EVENT_PAGINATE) || event.equals("listError") ) {
			forwardName = EVENT_LIST_REGION;
		} else if (event.equals(EVENT_CHECKER_LIST_REGION) || event.equals(EVENT_CHECKER_PAGINATE) || event.equals("checkerListError") ) {
			forwardName = EVENT_CHECKER_LIST_REGION;
		}else if (event.equals(EVENT_REMOVE_REGION)) {
			forwardName = EVENT_REMOVE_REGION;
		} else if (event.equals(EVENT_VIEW_REGION_BY_INDEX)) {
			forwardName = EVENT_VIEW_REGION_BY_INDEX;
		} else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			forwardName = MAKER_CONFIRM_CLOSE;
		} else if (event.equals("editError")) {
			forwardName = "prepare_maker_edit_region";
		} else if (event.equals(EVENT_MAKER_CREATE_REGION)) {
			forwardName = EVENT_MAKER_CREATE_REGION;
		} else if (event.equals(EVENT_TOTRACK_MAKER_REGION)) {
			forwardName = EVENT_TOTRACK_MAKER_REGION;
		} else if (event.equals(EVENT_PREPARE_CREATE_REGION) || event.equals("createError") ) {
			forwardName = EVENT_PREPARE_CREATE_REGION;
		} else if (event.equals(EVENT_CHECKER_APPROVE_REGION)) {
			forwardName = EVENT_CHECKER_APPROVE_REGION;
		} else if (event.equals(EVENT_CHECKER_REJECT_REGION)) {
			forwardName = EVENT_CHECKER_REJECT_REGION;
		}  else if (event.equals("errorReject")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_REGION;
		} else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_REGION)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_REGION;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_REGION) || event.equals("errorSaveEvent")) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_REGION;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_REGION) || event.equals("errorEditEvent")) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_REGION;
		} else if (event.equals(EVENT_MAKER_EDIT_REGION)) {
			forwardName = EVENT_MAKER_EDIT_REGION;
		} else if (event.equals(EVENT_MAKER_PREPARE_CLOSE_REGION)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE_REGION;
		} else if (MAKER_DELETE_REGION_READ.equals(event)) {
			forwardName = MAKER_DELETE_REGION_READ;
		} else if (MAKER_DELETE_REGION.equals(event)) {
			forwardName = MAKER_DELETE_REGION;
		} else if (EVENT_MAKER_PREPARE_ACTIVATE_REGION.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_REGION;
		} else if (EVENT_MAKER_ACTIVATE_REGION.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_REGION;
		} else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_REGION) || event.equals("errorReSubmitEvent")|| event.equals(MAKER_PREPARE_RESUBMIT)) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE_REGION)) {
			forwardName = MAKER_CONFIRM_CLOSE_REGION;
		}

		else if (event.equals(EVENT_MAKER_CREATE_SAVED_REGION)
			|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_REGION)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		
		else if (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REGION)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		else if (event.equals(EVENT_MAKER_SAVE_REGION)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_REGION)) {
			forwardName = EVENT_MAKER_SAVE_REGION;
		}
		
		else if (MAKER_PREPARE_UPLOAD_REGION.equals(event)) {
			forwardName = MAKER_PREPARE_UPLOAD_REGION;
		}
		else if (MAKER_UPLOAD_REGION.equals(event)) {
			forwardName = COMMON_SUBMIT_PAGE;
		} 
		else if ((event != null) && event.equals(CHECKER_REJECT_INSERT)) 
		{
			forwardName = "common_reject_page";
		} 
		else if (event.equals(CHECKER_PROCESS_CREATE_INSERT)) 
		{
			forwardName = "checker_region_insert_page";
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
		else if(MAKER_UPLOAD_REGION.equals(event)){
			forwardName = MAKER_PREPARE_UPLOAD_REGION;
		}
		else if(CHECKER_APPROVE_INSERT.equals(event)){
			forwardName = COMMON_SUBMIT_PAGE;
		}
		
		else if (event.equals("approveError")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_REGION;
		}
		
		DefaultLogger.debug(this, "Forward name --> " + forwardName);
		return forwardName;
	}
}
