package com.integrosys.cms.ui.excludedfacility;

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

public class ExcludedFacilityAction extends CommonAction implements IPin{

private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String MAKER_LIST_EXCLUDED_FACILITY= "maker_list_excluded_facility_category";
	public static final String CHECKER_LIST_EXCLUDED_FACILITY= "checker_excluded_facility_category";
	public static final String CHECKER_VIEW_EXCLUDED_FACILITY = "checker_view_excluded_facility_category";
	public static final String MAKER_VIEW_EXCLUDED_FACILITY = "maker_view_excluded_facility_category";
	public static final String MAKER_PREPARE_CREATE_EXCLUDED_FACILITY= "maker_prepare_create_excluded_facility_category";
	public static final String MAKER_CREATE_EXCLUDED_FACILITY= "maker_create_excluded_facility_category";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String MAKER_DRAFT_EXCLUDED_FACILITY = "maker_draft_excluded_facility_category";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_SAVE_CREATE = "maker_save_create";
	public static final String MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String MAKER_EDIT_EXCLUDED_FACILITY_READ = "maker_edit_excluded_facility_category_read";
	public static final String MAKER_EDIT_EXCLUDED_FACILITY = "maker_edit_excluded_facility_category";
	public static final String MAKER_UPDATE_DRAFT_EXCLUDED_FACILITY = "maker_update_draft_excluded_facility_category";
	public static final String MAKER_DELETE_EXCLUDED_FACILITY_READ = "maker_delete_excluded_facility_category_read";
	public static final String MAKER_DELETE_EXCLUDED_FACILITY = "maker_delete_excluded_facility_category";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In Exculded Facility Action with event --" + event);

		ICommand objArray[] = null;
		if (event.equals(MAKER_LIST_EXCLUDED_FACILITY)|| event.equals(CHECKER_LIST_EXCLUDED_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListExcludedFacilityCmd");

		} else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_EXCLUDED_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateExcludedFacilityCmd");

		} else if ((event.equals(CHECKER_VIEW_EXCLUDED_FACILITY))
				|| event.equals(MAKER_VIEW_EXCLUDED_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadExcludedFacilityCmd");

		}else if (/*(event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| */event.equals(MAKER_CREATE_EXCLUDED_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateExcludedFacilityCmd");

		} else if ((event.equals(CHECKER_PROCESS_EDIT)) ||/* event
				.equals(REJECTED_DELETE_READ))

				||*/ event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				/*|| event.equals(MAKER_SAVE_PROCESS)*/
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadExcludedFacilityCmd");
		}else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditExcludedFacilityCmd");
		} else if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)
				|| event.equals(CHECKER_REJECT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectEditExcludedFacilityCmd");
		} else if (event.equals(MAKER_DRAFT_EXCLUDED_FACILITY)
				|| event.equals(MAKER_UPDATE_DRAFT_EXCLUDED_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSaveExcludedFacilityCmd");

		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				/*|| event.equals(MAKER_PREPARE_RESUBMIT)*/
				|| event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				/*|| event.equals(MAKER_PREPARE_RESUBMIT_CREATE)*/
				|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadExcludedFacilityCmd");
		}else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DELETE))
				|| event.equals(MAKER_DELETE_EXCLUDED_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteExcludedFacilityCmd");
		}else if (event.equals(MAKER_SAVE_CREATE)
				|| event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditExcludedFacilityCmd");

		} else if ((event != null) && event.equals(MAKER_EDIT_EXCLUDED_FACILITY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadExcludedFacilityCmd");
		}  else if (/*(event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				||*/ event.equals(MAKER_EDIT_EXCLUDED_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditExcludedFacilityCmd");
		}else if ((event != null) && (event.equals(MAKER_DELETE_EXCLUDED_FACILITY_READ))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadExcludedFacilityCmd");

		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseExcludedFacilityCmd");
		} else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

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
		return ExcludedFacilityValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_CREATE_EXCLUDED_FACILITY)
				||event.equals(MAKER_EDIT_EXCLUDED_FACILITY)
				||event.equals(MAKER_UPDATE_DRAFT_EXCLUDED_FACILITY)
				||event.equals(MAKER_DELETE_EXCLUDED_FACILITY)
				||event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)
				||event.equals(MAKER_SAVE_UPDATE)
				||event.equals(CHECKER_APPROVE_EDIT)
				||event.equals(MAKER_DRAFT_EXCLUDED_FACILITY))
		{
			result = true;
		}
		return result;
	}
	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}else if (event.equals("maker_create_excluded_facility_category")|| event.equals("maker_draft_excluded_facility_category")) {
			errorEvent = "maker_create_excluded_facility_category_error";
		}else if (event.equals("maker_edit_excluded_facility_category")) {
			errorEvent = "maker_edit_excluded_facility_category_error";
		}else if (event.equals("maker_update_draft_excluded_facility_category")) {
			errorEvent = "maker_update_draft_excluded_facility_category_error";
		}else if (event.equals("maker_delete_excluded_facility_category")) {
			errorEvent = "maker_delete_excluded_facility_category_error";
		}else if (event.equals("maker_confirm_resubmit_delete")) {
			errorEvent = "maker_confirm_resubmit_delete_error";
		}else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}else if (event.equals("checker_approve_edit")) {
			errorEvent = "checker_approve_edit_error";
		}
		return errorEvent;
	}
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}	
	}
	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals("maker_list_excluded_facility_category")) {
			forwardName = "list_excluded_facility_page";
		}else if (event.equals("checker_excluded_facility_category") ) {
			forwardName = "checker_list_excluded_facility_page";
		}else if (MAKER_PREPARE_CREATE_EXCLUDED_FACILITY.equals(event) || event.equals("errorCreateSaving") ) {
			forwardName = MAKER_PREPARE_CREATE_EXCLUDED_FACILITY;
		} else if ((event != null)
				&& event.equals("maker_create_excluded_facility_category_error")) {
			forwardName = "maker_prepare_create_excluded_facility_category";
		}else if (/*(event.equals("maker_confirm_resubmit_edit"))
				||*/ event.equals("maker_edit_excluded_facility_category")
				|| event.equals("maker_confirm_resubmit_delete")
				|| event.equals("maker_create_excluded_facility_category")
				|| event.equals("maker_draft_excluded_facility_category")
				/*|| event.equals("maker_confirm_resubmit_create")
				|| event.equals("maker_save_create")*/
				|| event.equals("maker_save_update")
				|| event.equals("maker_update_draft_excluded_facility_category")) {
			forwardName = "common_submit_page";
		}else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")
				|| event.equals("checker_approve_edit_error")) {
			forwardName = "checker_excluded_facility_category_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}else if ( event.equals("checker_reject_edit_error")
				||event.equals("checker_reject_create_error")
				||event.equals("checker_reject_delete_error")) {
			forwardName = "checker_excluded_facility_category_page";
		}else if ((event != null)
				&& event.equals("maker_prepare_resubmit_delete")|| event.equals("maker_confirm_resubmit_delete_error")) {
			forwardName = "maker_prepare_resubmit_delete";
		} else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process")||event.equals("maker_save_update_error"))) {
			forwardName = "maker_view_save_page";
		}else if ((event != null) && ( event.equals("maker_edit_excluded_facility_category_read") || event.equals("maker_update_draft_excluded_facility_category_error"))
				 || event.equals("maker_edit_excluded_facility_category_error")) {
			forwardName = "maker_add_edit_excluded_facility_category_page";
		} else if ((event != null) && event.equals("maker_delete_excluded_facility_category_read")|| event.equals("maker_delete_excluded_facility_category_error")) {
			forwardName = "maker_view_delete_page";
		} else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}else if ((event != null) && event.equals("maker_delete_excluded_facility_category")) {
			forwardName = "common_submit_page";
		}else if ((event != null) && event.equals("checker_reject_delete")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_approve_delete")) {
			forwardName = "common_approve_page";
		} else if ((event != null)
				&& (event.equals("maker_prepare_close") || event
						.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		}else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			forwardName = "common_close_page";
		}else if ((event.equals("checker_view_excluded_facility_category"))
				|| event.equals("maker_view_excluded_facility_category")) {
			forwardName = "maker_view_page";
		}else if ((event != null)
				&& (event.equals("maker_create_excluded_facility_category_error"))) {
			forwardName = "maker_prepare_create_excluded_facility_category";
		} else if ((event != null) && event.equals("checker_list_pagination")) {
			forwardName = "checker_list_excluded_facility_page";
		} else if (event.equals("list_pagination")) {
			forwardName = "list_excluded_facility_page";
		}
		
		return forwardName;
	}
}
