/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.directorMaster;

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
 * Purpose : Action Class for all mappings. 
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class MaintainDirectorMasterAction extends CommonAction implements
		IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String FIRST_SORT = "LOGIN_ID";
	
	public static final String SECOND_SORT = "USER_NAME";

	public static final String MAKER_LIST_DIRECTORMASTER = "maker_list_directorMaster";
	public static final String CHECKER_LIST_DIRECTORMASTER = "checker_list_directorMaster";
	public static final String CHECKER_DIRECTORMASTER = "checker_directorMaster";
	public static final String CHECKER_VIEW_DIRECTORMASTER = "checker_view_directorMaster";
	public static final String MAKER_VIEW_DIRECTORMASTER = "maker_view_directorMaster";
	public static final String MAKER_EDIT_DIRECTORMASTER_READ = "maker_edit_directorMaster_read";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String MAKER_CONFIRM_RESUBMIT_DISABLE = "maker_confirm_resubmit_disable";
	public static final String MAKER_CONFIRM_RESUBMIT_ENABLE = "maker_confirm_resubmit_enable";
	public static final String MAKER_EDIT_DIRECTORMASTER = "maker_edit_directorMaster";
	public static final String MAKER_DISABLE_DIRECTORMASTER= "maker_disable_directorMaster";
	public static final String MAKER_ENABLE_DIRECTORMASTER= "maker_enable_directorMaster";
	public static final String CHECKER_EDIT_READ = "checker_edit_read";
	public static final String REJECTED_DISABLE_READ = "rejected_disable_read";
	public static final String REJECTED_ENABLE_READ = "rejected_enable_read";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_CREATE="checker_reject_create";
	public static final String CHECKER_REJECT_DISABLE="checker_reject_disable";
	public static final String CHECKER_REJECT_ENABLE="checker_reject_enable";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String REDIRECT_LIST_DIRECTORMASTER = "redirect_list_directorMaster";
	public static final String MAKER_DISABLE_DIRECTORMASTER_READ = "maker_disable_directorMaster_read";
	public static final String MAKER_ENABLE_DIRECTORMASTER_READ = "maker_enable_directorMaster_read";
	public static final String MAKER_EDIT_REJECT_EDIT = "maker_edit_reject_edit";
	public static final String MAKER_SEARCH_DIRECTORMASTER = "maker_search_directorMaster";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_SEARCH_LIST_DIRECTORMASTER = "maker_search_list_directorMaster";
	public static final String CHECKER_SEARCH_LIST_DIRECTORMASTER = "checker_search_list_directorMaster";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_PROCESS_DISABLE = "checker_process_disable";
	public static final String CHECKER_PROCESS_ENABLE = "checker_process_enable";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String MAKER_PREPARE_RESUBMIT_DISABLE = "maker_prepare_resubmit_disable";
	public static final String MAKER_PREPARE_RESUBMIT_ENABLE = "maker_prepare_resubmit_enable";
	public static final String MAKER_PREPARE_CREATE_DIRECTORMASTER ="maker_prepare_create_directorMaster";
	public static final String MAKER_CREATE_DIRECTOR_MASTER ="maker_create_directorMaster";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE="maker_prepare_resubmit_create";
	public static final String MAKER_SAVE_PROCESS="maker_save_process";
	public static final String MAKER_SAVE_CREATE="maker_save_create";
	public static final String MAKER_UPDATE_SAVE_PROCESS="maker_update_save_process";
	public static final String MAKER_SAVE_UPDATE="maker_save_update";
	public static final String MAKER_DRAFT_CLOSE_PROCESS="maker_draft_close_process";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE="maker_confirm_draft_close";
	public static final String MAKER_UPDATE_DRAFT_DIRECTORMASTER="maker_update_draft_directorMaster";
	public static final String MAKER_DRAFT_DIRECTORMASTER ="maker_draft_directorMaster";
	

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this,"In Maintain Director Master Action with event --"+ event);

		ICommand objArray[] = null;
		if ((event.equals(CHECKER_DIRECTORMASTER))
				|| event.equals(MAKER_LIST_DIRECTORMASTER)|| event.equals(CHECKER_LIST_DIRECTORMASTER) 
				|| event.equals(MAKER_SEARCH_LIST_DIRECTORMASTER) || event.equals(CHECKER_SEARCH_LIST_DIRECTORMASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListDirectorMasterCmd");

		} else if ((event.equals(CHECKER_VIEW_DIRECTORMASTER)) || event.equals(MAKER_VIEW_DIRECTORMASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadDirectorMasterCmd");

		} else if ((event != null)
				&& event.equals(MAKER_EDIT_DIRECTORMASTER_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadDirectorMasterCmd");
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| event.equals(MAKER_EDIT_DIRECTORMASTER))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditDirectorMasterCmd");
		} else if ((event.equals(MAKER_ENABLE_DIRECTORMASTER_READ))
				|| event.equals(MAKER_DISABLE_DIRECTORMASTER_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadDirectorMasterCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DISABLE))
				|| event.equals(MAKER_DISABLE_DIRECTORMASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDisableDirectorMasterCmd");
			
		//
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_ENABLE))
				|| event.equals(MAKER_ENABLE_DIRECTORMASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEnableDirectorMasterCmd");
		//end
		} 
		
		/** Commented By Sandeep Shinde to avoid Multiple Command classes 
		 *  while searching which is causing error while using ASST Validator 
		 */
		
		/*else if ((event.equals(MAKER_SEARCH_LIST_DIRECTORMASTER))||event.equals(CHECKER_SEARCH_LIST_DIRECTORMASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SearchListDirectorMasterCommand");

		}*/ else if ((event.equals(LIST_PAGINATION))||event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		} else if ((event.equals(CHECKER_PROCESS_EDIT) || event
				.equals(REJECTED_DISABLE_READ))||event.equals(REJECTED_DELETE_READ)||event.equals(REJECTED_ENABLE_READ)
				
				|| event.equals(CHECKER_PROCESS_DISABLE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				||event.equals(CHECKER_PROCESS_ENABLE)
				|| event.equals(MAKER_SAVE_PROCESS)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)
				) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadDirectorMasterCmd");
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditDirectorMasterCmd");
		} else if ((event.equals(CHECKER_REJECT_CREATE)) || event.equals(CHECKER_REJECT_EDIT)
				|| event.equals(CHECKER_REJECT_DISABLE)|| event.equals(CHECKER_REJECT_ENABLE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectEditDirectorMasterCmd");
		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT)
				|| event.equals(MAKER_PREPARE_RESUBMIT_DISABLE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_ENABLE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_CREATE)
				||event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadDirectorMasterCmd");
		} else if ((event != null) && event.equals(MAKER_CONFIRM_CLOSE)|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseDirectorMasterCmd");
		}else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_DIRECTORMASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateDirectorMasterCmd");

		}else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_DIRECTOR_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateDirectorMasterCmd");

		}else if (event.equals(MAKER_DRAFT_DIRECTORMASTER)||event.equals(MAKER_UPDATE_DRAFT_DIRECTORMASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSaveDirectorMasterCmd");

		}else if (event.equals(MAKER_SAVE_CREATE)||event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditDirectorMasterCmd");

		}/*else{
			throw new DirectorMasterException("Improper Event");
		}*/

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
//		System.out.println("################ = "+DirectorMasterValidator.validateInput(aForm, locale));
		return DirectorMasterValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_EDIT_DIRECTORMASTER)
				|| event.equals(MAKER_EDIT_REJECT_EDIT)
				|| event.equals(MAKER_SEARCH_DIRECTORMASTER)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_CREATE)
				|| event.equals(MAKER_CREATE_DIRECTOR_MASTER)
				|| event.equals(MAKER_SAVE_UPDATE)
				|| event.equals(MAKER_DRAFT_DIRECTORMASTER)
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
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		} else if ((resultMap.get("Error_EmpId") != null)
				&& (resultMap.get("Error_EmpId")).equals("Error_EmpId")) {
			DefaultLogger.debug(this, "Inside Error_EmpId");
			aPage.setPageReference("maker_add_edit_directorMaster_page");
			return aPage;
		} else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_add_directorMaster".equals(event)) {
			errorEvent = "maker_add_edit_directorMaster_error";
		} else if ("maker_edit_directorMaster".equals(event)/*
				|| event.equals("maker_confirm_resubmit_edit")*/) {
			errorEvent = "maker_add_edit_directorMaster_error";
		} 
		/*	Changed because resubmit page not redirected properly. */
		else if ("maker_confirm_resubmit_edit".equals(event)) {
			errorEvent = "maker_prepare_resubmit_create_error";
		}
		else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_directorMaster_error";
		} else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_directorMaster_error";
		} else if ("checker_approve_edit".equals(event)) {
			errorEvent = "checker_approve_edit_error";
		} else if ("checker_approve_add".equals(event)) {
			errorEvent = "checker_add_read_error";
		} else if (event.equals("maker_search_directorMaster")) {
			errorEvent = "maker_prepare_search_directorMaster";
		}
		else if (event.equals("maker_create_directorMaster")) {
			errorEvent = "maker_prepare_create_directorMaster";
		} else if(event.equals(CHECKER_REJECT_DISABLE)){
			errorEvent = "errorReject";
		}else if(event.equals(CHECKER_REJECT_ENABLE)){
			errorEvent = "errorRejectEnable"; 
		}else if (CHECKER_REJECT_EDIT.equals(event)) 
			 errorEvent = "errorReject";		
		else if (MAKER_DRAFT_DIRECTORMASTER.equals(event)) 
			 errorEvent = "errorDraft";
		
		else if (event.equals("maker_save_update"))
			errorEvent = "errorSaveUpdate";
		
		else if( event.equals("maker_update_draft_directorMaster"))
				errorEvent = "errorUpdateDraft";
		
		else if( event.equals(MAKER_SEARCH_LIST_DIRECTORMASTER) )
			errorEvent = "errorMakerList";
		
		else if( event.equals(CHECKER_SEARCH_LIST_DIRECTORMASTER))
			errorEvent = "errorCheckerList";
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ((event!= null)
				&& ( event.equals("maker_list_directorMaster") || event.equals("errorMakerList") )) {
			forwardName = "maker_list_directorMaster_page";
		}else if((event != null)&& ( event.equals("checker_list_directorMaster") || event.equals("errorCheckerList") || (event.equals("checker_list_pagination"))))
				{
			forwardName = "checker_list_directorMaster_page";
		}
		else if ((event != null)
				&& event.equals("maker_search_directorMaster")) {
			forwardName = "maker_list_directorMaster_page";
		} else if (event.equals("list_pagination")) {
			forwardName = "maker_list_directorMaster_page";
		} else if ((event != null)
				&& event.equals("redirect_list_directorMaster")) {
			forwardName = "maker_list_directorMaster_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_add_directorMaster")) {
			forwardName = "maker_add_edit_directorMaster_page";
		} else if ((event != null)
				&& event.equals("maker_add_directorMaster")) {
			forwardName = "common_submit_page";
		} else if ((event.equals("checker_view_directorMaster"))
				|| event.equals("maker_view_directorMaster")) {
			forwardName = "maker_view_page";
		} else if ((event != null)
				&& ( event.equals("maker_edit_directorMaster_read") || event.equals("errorUpdateDraft")) ) {
			forwardName = "maker_add_edit_directorMaster_page";
		} else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_directorMaster")
				|| event.equals("maker_confirm_resubmit_disable")
				|| event.equals("maker_create_directorMaster")
				|| event.equals("maker_confirm_resubmit_create")
		        ||event.equals("maker_draft_directorMaster")
				|| event.equals("maker_update_draft_directorMaster")		
		
		) {
			forwardName = "common_submit_page";
		}else if (event.equals("maker_save_create")
				|| event.equals("maker_save_update"))
		{
			forwardName = "common_submit_page_save";
		}
				
		else if ((event != null)
				&& event.equals("maker_disable_directorMaster_read")) {
			forwardName = "maker_view_disable_page";
		}
		//
		else if ((event != null)
				&& event.equals("maker_enable_directorMaster_read")) {
			forwardName = "maker_view_enable_page";
		}
		else if ((event != null)
				&& event.equals("maker_disable_directorMaster")) {
			forwardName = "common_submit_page";
		}
		//
		else if ((event != null)
				&& event.equals("maker_enable_directorMaster")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null)
				&& (event.equals("maker_save_process") || event.equals("maker_update_save_process") ||event.equals("errorSaveUpdate"))) {
			forwardName = "maker_view_save_page";
		} 
		else if ((event != null) && event.equals("checker_add_read")) {
			forwardName = "checker_directorMaster_page";
		} else if ((event != null) && event.equals("checker_add_read_error")) {
			forwardName = "checker_directorMaster_page";
		} else if ((event != null) && event.equals("checker_edit_read")) {
			forwardName = "checker_directorMaster_page";
		} else if ((event != null) && event.equals("checker_disable_read")) {
			forwardName = "checker_directorMaster_page";
		} else if ((event != null) && event.equals("checker_approve_add")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_disable")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_reject_add")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_disable")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_enable")) {
			forwardName = "common_reject_page";
		}else if ((event != null) && event.equals("checker_reject_able")) {
			forwardName = "common_reject_page";
		}
		else if ((event != null) && event.equals("rejected_add_read")) {
			forwardName = "maker_rejected_edit_directorMaster_page";
		} else if ((event != null) && event.equals("rejected_edit_read")) {
			forwardName = "maker_add_edit_directorMaster_page";
		} else if ((event != null) && event.equals("rejected_disable_read")) {
			forwardName = "maker_view_todo_page";
		}
		//
			 else if ((event != null) && event.equals("rejected_enable_read")) {
					forwardName = "maker_view_todo_page";
		//end
		} else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		}else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_disable")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_edit_reject_add")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_disable")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		} else if ((event != null)
				&& event.equals("maker_add_edit_directorMaster_error")) {
			forwardName = "maker_add_edit_directorMaster_page";
		} else if ((event != null) && event.equals("rejected_read")) {
			forwardName = "rejected_read_page";
		} else if ((event != null)
				&& event.equals("checker_approve_edit_error")) {
			forwardName = "checker_directorMaster_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_search_directorMaster")) {
			forwardName = "search_directorMaster_page";
		} else if (event.equals("checker_search_list_directorMaster")) {
			forwardName = "checker_list_directorMaster_page";
		}else if ( event.equals("maker_search_list_directorMaster") ) {
			forwardName = "maker_list_directorMaster_page";
		}
		else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_create")
			  	) {
			forwardName = "checker_directorMaster_page";
		}else if ((event != null)&&(event.equals("checker_process_disable") || event.equals("errorReject")))
				{
			forwardName = "checker_disable_directorMaster_page";
		}else if ((event != null)&&(event.equals("checker_process_enable")  || event.equals("errorRejectEnable") ))
				{
			forwardName = "checker_enable_directorMaster_page";
		}else if ((event != null) && event.equals("maker_prepare_resubmit")) {
			forwardName = "maker_prepare_resubmit";
		} else if ((event != null) && event.equals("maker_prepare_close")|| event.equals("maker_draft_close_process")) {
			forwardName = "maker_prepare_close";
		} else if ((event != null)
				&& event.equals("maker_prepare_resubmit_disable")) {
			forwardName = "maker_prepare_resubmit_disable";
		}else if ((event != null)
				&& event.equals("maker_prepare_resubmit_enable")) {
			forwardName = "maker_prepare_resubmit_enable";
		} 
		else if ((event != null) && event.equals(MAKER_CONFIRM_CLOSE) || event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
			forwardName = "common_close_page";
		}else if ((event != null) && ( event.equals("maker_prepare_create_directorMaster") || event.equals("errorDraft"))) {
			forwardName = "prepare_create_directorMaster";
		}
		else if (event.equals("maker_prepare_resubmit_create")
				|| event.equals("maker_prepare_resubmit_create_error")) {	/*	OR Condition added as resubmit page not redirected properly*/
			forwardName = "maker_prepare_resubmit";
		}
		else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		
		else if (event.equals("errorReject")) {
			forwardName = "checker_directorMaster_page";
		}
		return forwardName;
	}

}