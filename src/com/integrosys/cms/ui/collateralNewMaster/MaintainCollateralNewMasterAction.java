/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.collateralNewMaster;

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
 * @author $Author: Abhijit R$ Action For CollateralNewMaster
 */
public class MaintainCollateralNewMasterAction extends CommonAction implements IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";

	// public static final String EVENT_LIST = "maker_list_collateralNewMaster";
	public static final String MAKER_LIST_COLLATERAL_NEW_MASTER = "maker_list_collateralNewMaster";
	public static final String CHECKER_LIST_COLLATERAL_NEW_MASTER = "checker_list_collateralNewMaster";
	public static final String CHECKER_VIEW_COLLATERAL_NEW_MASTER = "checker_view_collateralNewMaster";
	public static final String MAKER_VIEW_COLLATERAL_NEW_MASTER = "maker_view_collateralNewMaster";
	public static final String MAKER_EDIT_COLLATERAL_NEW_MASTER_READ = "maker_edit_collateralNewMaster_read";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_EDIT_COLLATERAL_NEW_MASTER = "maker_edit_collateralNewMaster";
	public static final String MAKER_DELETE_COLLATERAL_NEW_MASTER = "maker_delete_collateralNewMaster";
	public static final String CHECKER_EDIT_READ = "checker_edit_read";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String REDIRECT_LIST_COLLATERAL_NEW_MASTER = "redirect_list_collateralNewMaster";
	public static final String MAKER_DELETE_COLLATERAL_NEW_MASTER_READ = "maker_delete_collateralNewMaster_read";
	public static final String MAKER_EDIT_REJECT_EDIT = "maker_edit_reject_edit";
	public static final String MAKER_SEARCH_COLLATERAL_NEW_MASTER = "maker_search_collateralNewMaster";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_SEARCH_LIST_COLLATERAL_NEW_MASTER = "maker_search_list_collateralNewMaster";
	public static final String CHECKER_SEARCH_LIST_COLLATERAL_NEW_MASTER = "checker_search_list_collateralNewMaster";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_PREPARE_CREATE_COLLATERAL_NEW_MASTER = "maker_prepare_create_collateralNewMaster";
	public static final String MAKER_CREATE_COLLATERAL_NEW_MASTER = "maker_create_collateralNewMaster";
	public static final String MAKER_DRAFT_COLLATERAL_NEW_MASTER = "maker_draft_collateralNewMaster";
	public static final String MAKER_SAVE_PROCESS = "maker_save_process";
	public static final String MAKER_SAVE_CREATE = "maker_save_create";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE = "maker_prepare_resubmit_create";
	public static final String MAKER_UPDATE_DRAFT_COLLATERAL_NEW_MASTER = "maker_update_draft_collateralNewMaster";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";

	/**
	 * Event Added by 
	 * @author sandiip.shinde
	 * for Pagination purpose
	 */
	
	public static final String EVENT_PAGINATE = "paginate";
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this,"In Maintain CollateralNewMaster Action with event --" + event);

		ICommand objArray[] = null;
		if ((event.equals(CHECKER_LIST_COLLATERAL_NEW_MASTER))
				|| event.equals(MAKER_LIST_COLLATERAL_NEW_MASTER) 
				|| EVENT_PAGINATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCollateralNewMasterCmd");

		} else if ((event.equals(CHECKER_VIEW_COLLATERAL_NEW_MASTER))
				|| event.equals(MAKER_VIEW_COLLATERAL_NEW_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCollateralNewMasterCmd");

		} else if ((event != null) && event.equals(MAKER_EDIT_COLLATERAL_NEW_MASTER_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCollateralNewMasterCmd");
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| event.equals(MAKER_EDIT_COLLATERAL_NEW_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCollateralNewMasterCmd");
		} else if ((event != null) && (event.equals(MAKER_DELETE_COLLATERAL_NEW_MASTER_READ))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCollateralNewMasterCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DELETE))
				|| event.equals(MAKER_DELETE_COLLATERAL_NEW_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteCollateralNewMasterCmd");
		} else if ((event.equals(MAKER_SEARCH_LIST_COLLATERAL_NEW_MASTER))
				|| event.equals(CHECKER_SEARCH_LIST_COLLATERAL_NEW_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SearchListCollateralNewMasterCommand");

		} else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		} else if ((event.equals(CHECKER_PROCESS_EDIT) || event
				.equals(REJECTED_DELETE_READ))

				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(MAKER_SAVE_PROCESS)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadCollateralNewMasterCmd");
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditCollateralNewMasterCmd");
		} else if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)
				|| event.equals(CHECKER_REJECT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectEditCollateralNewMasterCmd");
		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT)
				|| event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_CREATE)
				|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadCollateralNewMasterCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseCollateralNewMasterCmd");
		} else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_COLLATERAL_NEW_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCollateralNewMasterCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_COLLATERAL_NEW_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateCollateralNewMasterCmd");

		} else if (event.equals(MAKER_DRAFT_COLLATERAL_NEW_MASTER)
				|| event.equals(MAKER_UPDATE_DRAFT_COLLATERAL_NEW_MASTER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSaveCollateralNewMasterCmd");

		} else if (event.equals(MAKER_SAVE_CREATE)
				|| event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCollateralNewMasterCmd");

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
		return CollateralNewMasterValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		
			
		if (event.equals(MAKER_EDIT_COLLATERAL_NEW_MASTER)
				|| event.equals(MAKER_EDIT_REJECT_EDIT)
				|| event.equals(MAKER_SEARCH_COLLATERAL_NEW_MASTER)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_CREATE)
				|| event.equals(MAKER_CREATE_COLLATERAL_NEW_MASTER)
				|| event.equals(MAKER_DRAFT_COLLATERAL_NEW_MASTER)
				|| event.equals("maker_update_draft_collateralNewMaster")
				|| event.equals(MAKER_SAVE_UPDATE)
				||event.equalsIgnoreCase("maker_confirm_resubmit_delete")
				||event.equalsIgnoreCase("maker_list_collateralNewMaster")
				||event.equalsIgnoreCase("checker_list_collateralNewMaster")
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
			aPage.setPageReference("maker_add_edit_collateralNewMaster_page");
			return aPage;
		} else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_add_collateralNewMaster".equals(event)) {
			errorEvent = "maker_add_edit_collateralNewMaster_error";
		} else if ("maker_edit_collateralNewMaster".equals(event)
				|| event.equals("maker_confirm_resubmit_edit")) {
			errorEvent = "maker_add_edit_collateralNewMaster_error";
		} else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_collateralNewMaster_error";
		} else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_collateralNewMaster_error";
		} else if ("checker_approve_edit".equals(event)) {
			errorEvent = "checker_approve_edit_error";
		} else if ("checker_approve_add".equals(event)) {
			errorEvent = "checker_add_read_error";
		} else if (event.equals("maker_search_collateralNewMaster")) {
			errorEvent = "maker_prepare_search_collateralNewMaster";
		}else if (event.equals("maker_create_collateralNewMaster")|| event.equals("maker_draft_collateralNewMaster")) {
			errorEvent = "maker_create_collateralNewMaster_error";
		}else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}else if (event.equals("maker_confirm_resubmit_delete")) {
			errorEvent = "maker_confirm_resubmit_delete_error";
		}else if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}else if (event.equals("checker_reject_create")) {
			errorEvent = "checker_reject_create_error";
		}else if (event.equals("checker_reject_delete")) {
			errorEvent = "checker_reject_delete_error";
		}else if (event.equals("maker_list_collateralNewMaster")) {
			errorEvent = "maker_list_collateralNewMaster";
		}
		else if (event.equals("checker_list_collateralNewMaster")) {
			errorEvent = "checker_list_collateralNewMaster";
		}
		
		/**
		 * Event Added by Sandeep Shinde
		 */
		else if (event.equals("maker_update_draft_collateralNewMaster")) {
			errorEvent = "maker_edit_collateralNewMaster_read_error";
		}
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
		if (event.equals("maker_list_collateralNewMaster")
				|| event.equals(EVENT_PAGINATE)) {
			forwardName = "maker_list_collateralNewMaster_page";
		}else if (event.equals("checker_list_collateralNewMaster")){
			forwardName = "checker_list_collateralNewMaster_page";
		}
		else if (event.equals("checker_list_collateralNewMaster")){
			forwardName = "checker_list_collateralNewMaster_page";
		}
		else if ((event != null) && event.equals("checker_list_collateralNewMaster_page")) {
			forwardName = "checker_list_collateralNewMaster_page";
		} else if ((event != null) && event.equals("maker_search_collateralNewMaster")) {
	forwardName = "maker_list_collateralNewMaster_page";	
		} else if ((event.equals("list_pagination"))) {
			forwardName = "maker_list_collateralNewMaster_page";
		} else if((event.equals("checker_list_pagination"))){
			forwardName = "checker_list_collateralNewMaster_page";
		}
		else if ((event != null) && event.equals("redirect_list_collateralNewMaster")) {
			forwardName = "maker_list_collateralNewMaster_page";
		} else if ((event != null) && event.equals("maker_prepare_add_collateralNewMaster")) {
			forwardName = "maker_add_edit_collateralNewMaster_page";
		} else if ((event != null) && event.equals("maker_add_collateralNewMaster")) {
			forwardName = "common_submit_page";
		} else if ((event.equals("checker_view_collateralNewMaster"))
				|| event.equals("maker_view_collateralNewMaster")) {
			forwardName = "maker_view_page";
			} else if ((event.equals("checker_view_collateralNewMaster"))
			|| event.equals("checker_view_collateralNewMaster")) {
		forwardName = "checker_view_page";
		} else if ( ((event != null) && event.equals("maker_edit_collateralNewMaster_read")) 
				/* 	Following or event added by Sandeep Shinde for validation purpose*/
				|| event.equals("maker_edit_collateralNewMaster_read_error")) {
			forwardName = "maker_add_edit_collateralNewMaster_page";
		} else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_collateralNewMaster")
				|| event.equals("maker_confirm_resubmit_delete")
				|| event.equals("maker_create_collateralNewMaster")
				|| event.equals("maker_draft_collateralNewMaster")
				|| event.equals("maker_confirm_resubmit_create")
				|| event.equals("maker_save_create")
				|| event.equals("maker_save_update")
				|| event.equals("maker_update_draft_collateralNewMaster")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_delete_collateralNewMaster_read")) {
			forwardName = "maker_view_delete_page";
		} else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process"))) {
			forwardName = "maker_view_save_page";
		} else if ((event != null) && event.equals("maker_delete_collateralNewMaster")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("checker_add_read")) {
			forwardName = "checker_collateralNewMaster_page";
		} else if ((event != null) && event.equals("checker_add_read_error")) {
			forwardName = "checker_collateralNewMaster_page";
		} else if ((event != null) && event.equals("checker_edit_read")) {
			forwardName = "checker_collateralNewMaster_page";
		} else if ((event != null) && event.equals("checker_delete_read")) {
			forwardName = "checker_collateralNewMaster_page";
		} else if ((event != null) && event.equals("checker_approve_add")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_delete")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_reject_add")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_delete")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("rejected_add_read")) {
			forwardName = "maker_add_edit_collateralNewMaster_page";
		} else if ((event != null) && event.equals("rejected_edit_read")) {
			forwardName = "maker_add_edit_collateralNewMaster_page";
		} else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_delete")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_edit_reject_add")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_delete")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		} else if ((event != null)
				&& event.equals("maker_add_edit_collateralNewMaster_error")) {
			forwardName = "maker_add_edit_collateralNewMaster_page";
		} else if ((event != null) && event.equals("rejected_read")) {
			forwardName = "rejected_read_page";
		} else if ((event != null)
				&& event.equals("checker_approve_edit_error")) {
			forwardName = "checker_collateralNewMaster_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_search_collateralNewMaster")) {
			forwardName = "search_collateralNewMaster_page";
		} else if ((event.equals("checker_search_list_collateralNewMaster"))
				|| event.equals("maker_search_list_collateralNewMaster")) {
			forwardName = "maker_list_collateralNewMaster_page";
		} else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")) {
			forwardName = "checker_collateralNewMaster_page";
		} else if ((event != null) && event.equals("maker_prepare_resubmit")) {
			forwardName = "maker_prepare_resubmit";
		} else if ((event != null)
				&& (event.equals("maker_prepare_close") || event
						.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		} else if ((event != null)
				&& event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			forwardName = "common_close_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_create_collateralNewMaster")) {
			forwardName = "prepare_create_collateralNewMaster";
		} else if (event.equals("maker_prepare_resubmit_create")) {
			forwardName = "maker_prepare_resubmit";
		}else if ((event != null)
				&& (event.equals("maker_create_collateralNewMaster_error"))) {
			forwardName = "prepare_create_collateralNewMaster";
		}else if (event.equals("maker_confirm_resubmit_delete_error")) {
			forwardName = "maker_prepare_resubmit_delete";
		} else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process")||event.equals("maker_save_update_error"))) {
			forwardName = "maker_view_save_page";
		}else if ( event.equals("checker_reject_edit_error")
				||event.equals("checker_reject_create_error")
				||event.equals("checker_reject_delete_error")) {
			forwardName = "checker_collateralNewMaster_page";
		}

		return forwardName;
	}

}