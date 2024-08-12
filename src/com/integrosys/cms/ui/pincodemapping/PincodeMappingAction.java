package com.integrosys.cms.ui.pincodemapping;

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

public class PincodeMappingAction  extends CommonAction implements IPin{

private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String MAKER_LIST_PINCODE= "maker_list_state_pincode_mapping";
	public static final String CHECKER_LIST_PINCODE = "checker_list_state_pincode_mapping";
	public static final String CHECKER_VIEW_PINCODE_MAPPING = "checker_view_state_pincode_mapping";
	public static final String MAKER_VIEW_PINCODE_MAPPING = "maker_view_state_pincode_mapping";
	public static final String MAKER_PREPARE_CREATE_PINCODE_MAPPING= "maker_prepare_create_state_pincode_mapping";
	public static final String MAKER_CREATE_PINCODE_MAPPING = "maker_create_state_pincode_mapping";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_APPROVE_CREATE = "checker_approve_create";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";
	public static final String MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_CREATE_DRAFT_PINCODE_MAPPING = "maker_create_draft_state_pincode_mapping";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE = "maker_prepare_resubmit_create";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_UPDATE_DRAFT_PINCODE_MAPPING = "maker_update_draft_state_pincode_mapping";
	public static final String MAKER_EDIT_DRAFT_PINCODE_MAPPING = "maker_edit_draft_state_pincode_mapping";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String MAKER_EDIT_PINCODE_MAPPING = "maker_edit_state_pincode_mapping";
	public static final String MAKER_DELETE_PINCODE_MAPPING_READ = "maker_delete_state_pincode_mapping_read";
	public static final String MAKER_EDIT_PINCODE_MAPPING_READ = "maker_edit_state_pincode_mapping_read";
	public static final String MAKER_PREPARE_DELETE_PINCODE_MAPPING = "maker_prepare_delete_state_pincode_mapping";
	public static final String MAKER_ENABLE_PINCODE_MAPPING = "maker_enable_state_pincode_mapping";
	public static final String MAKER_DELETE_PINCODE_MAPPING = "maker_delete_state_pincode_mapping";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In State Pincode Mapping Action with event --" + event);

		ICommand objArray[] = null;
		if (MAKER_LIST_PINCODE.equals(event)|| CHECKER_LIST_PINCODE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListPincodeCmd");	
		}else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_PINCODE_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreatePincodeCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_PINCODE_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreatePincodeCmd");

		}else if (event.equals(MAKER_CREATE_DRAFT_PINCODE_MAPPING)
				|| event.equals(MAKER_UPDATE_DRAFT_PINCODE_MAPPING)
				|| event.equals(MAKER_EDIT_DRAFT_PINCODE_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSavePincodeMappingCmd");

		} else if ((event.equals(CHECKER_VIEW_PINCODE_MAPPING))
				|| event.equals(MAKER_VIEW_PINCODE_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadPincodeMappingCmd");
		} else if ((event.equals(CHECKER_PROCESS_EDIT)
				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadPincodeMappingCmd");
		}else if ((event != null) && event.equals(CHECKER_APPROVE_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveCreatePincodeMappingCmd");
		}else if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)
				|| event.equals(CHECKER_REJECT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectCreatePincodeMappingCmd");
		}else if ((event != null) && event.equals(MAKER_EDIT_PINCODE_MAPPING_READ)
				|| event.equals("maker_edit_state_pincode_mapping_error")
				|| event.equals("maker_edit_draft_state_pincode_mapping_error")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditPincodeMappingCmd");
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| event.equals(MAKER_EDIT_PINCODE_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditPincodeMappingCmd");
		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_CREATE)
				|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)
				|| event.equals("maker_confirm_resubmit_edit_error")
				|| event.equals("maker_save_update_error")
				/*|| event.equals("checker_approve_create_error")*/) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadPincodeMappingCmd");
		}else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerClosePincodeMappingCmd");
		} else if (event.equals(MAKER_ENABLE_PINCODE_MAPPING)
				|| event.equals(MAKER_PREPARE_DELETE_PINCODE_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeletePincodeMappingCmd");

		} else if (event.equals(MAKER_DELETE_PINCODE_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeletePincodeMappingCmd");
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditPincodeMappingCmd");
		}else if (event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditPincodeMappingCmd");

		}else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		}

		
		return objArray;
	}
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("work_in_process");
			return aPage;
		} else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return PincodeValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_CREATE_PINCODE_MAPPING)
				|| event.equals(MAKER_CREATE_DRAFT_PINCODE_MAPPING)
				|| event.equals(MAKER_SAVE_UPDATE)
				|| event.equals(MAKER_EDIT_PINCODE_MAPPING)
				|| event.equals(MAKER_EDIT_DRAFT_PINCODE_MAPPING)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (MAKER_CREATE_PINCODE_MAPPING.equals(event)) {
			DefaultLogger.debug(this, "Error event...");
			errorEvent = "maker_prepare_create_state_pincode_mapping";
		}  else if (CHECKER_REJECT_CREATE.equals(event)) {
			errorEvent = "checker_reject_create_state_pincode_mapping_error";
		}else if (MAKER_CREATE_DRAFT_PINCODE_MAPPING.equals(event)) {
			errorEvent = "maker_prepare_create_state_pincode_mapping";
		} else if ("maker_save_update".equals(event)) {
			errorEvent = "maker_save_update_error";
		} else if (MAKER_EDIT_PINCODE_MAPPING.equals(event))	
		{
			errorEvent = "maker_edit_state_pincode_mapping_error";
		}else if (MAKER_EDIT_DRAFT_PINCODE_MAPPING.equals(event)) {
			errorEvent = "maker_edit_draft_state_pincode_mapping_error";
		}else if (MAKER_LIST_PINCODE.equals(event)){
			errorEvent = "listError";
		}else if ("maker_confirm_resubmit_edit".equals(event)) {
			errorEvent = "maker_confirm_resubmit_edit_error";
		}else if (CHECKER_LIST_PINCODE.equals(event)){
			errorEvent = "checkerListError";
		}else if (CHECKER_APPROVE_CREATE.equals(event)){
			errorEvent = "checker_approve_create_error";
		}
		
		return errorEvent;
	}
	
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (MAKER_LIST_PINCODE.equals(event) || event.equals("listError")) {
			forwardName = MAKER_LIST_PINCODE;
			
		}  else if (MAKER_PREPARE_CREATE_PINCODE_MAPPING.equals(event)  ) {
			forwardName = MAKER_PREPARE_CREATE_PINCODE_MAPPING;

		}  else if (MAKER_CREATE_PINCODE_MAPPING.equals(event)) {
			forwardName = "common_submit_page";

		}else if ((event != null) && event.equals("checker_approve_create")) {
			forwardName = "common_approve_page";		
		}else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_state_pincode_mapping")
				|| event.equals("maker_create_state__pincode_mapping")
				|| event.equals("maker_edit_draft_state_pincode_mapping"))
			{
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("checker_reject_create")) {
			forwardName = "common_reject_page";

		}else if ((event != null)
				&& event.equals("maker_create_draft_state_pincode_mapping")) {
			forwardName = "common_save_page";

		}else if ((event.equals(CHECKER_PROCESS_DELETE))
				||event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(CHECKER_PROCESS_EDIT)) {
			forwardName = "checker_state_pincode_mapping_page";
		}/* else if ((event != null)
			&& event.equals("maker_create_state_pincode_mapping_error")) {
			forwardName = "maker_prepare_create_state_pincode_mapping";
		}*/else if (event.equals("checker_list_state_pincode_mapping")) {
			forwardName = "checker_list_state_pincode_mapping";
		} else if (event.equals("checker_list_pagination")) {
			forwardName = "checker_list_state_pincode_mapping";
		} else if ((event != null)
				&& event.equals("checker_reject_create_state_pincode_mapping_error")) {
			forwardName = "checker_state_pincode_mapping_page";
		} else if (event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit";
		}else if ((event != null)
				&& (event.equals("maker_prepare_close") || event
						.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			forwardName = "common_close_page";
		}else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process"))) {
			forwardName = "maker_view_save_page";
		} else if ((event != null) && event.equals("maker_save_update")) {
			forwardName = "common_save_page";
		} else if ((event != null)
				&& event.equals("maker_save_update_error")) {
			forwardName = "maker_view_save_page";
		} else if ((event.equals("checker_view_state_pincode_mapping"))
				|| event.equals("maker_view_state_pincode_mapping")) {
			forwardName = "maker_view_page";
		}else if ((event.equals("checker_view_state_pincode_mapping"))
				|| event.equals("maker_view_state_pincode_mapping")) {
			forwardName = "checker_view_page";
		} else if ((event != null)
				&& ( event.equals("maker_edit_state_pincode_mapping_read") || event.equals("maker_edit_state_pincode_mapping_error")|| event.equals("maker_edit_draft_state_pincode_mapping_error") ) ) {
			forwardName = "maker_edit_state_pincode_mapping_page";
		} else if ((event != null)
				&& event.equals("maker_edit_state_pincode_mapping_page")) {
			forwardName = "maker_edit_state_pincode_mapping_page";
		} else if ((event != null)
				&& (event.equals(MAKER_ENABLE_PINCODE_MAPPING) || event
						.equals(MAKER_PREPARE_DELETE_PINCODE_MAPPING))) {
			forwardName = "state_pincode_mapping_prepare_delete_page";
		}  else if ((event != null) && event.equals("maker_delete_state_pincode_mapping")) {
			forwardName = "common_submit_page";
		} else if (event.equals("list_pagination")) {
			forwardName = "maker_list_state_pincode_mapping";
		} else if (event.equals("checker_list_pagination") || event.equals("checkerListError")) {
			forwardName = "checker_list_state_pincode_mapping";
		}/*else if (event.equals("errorCreateSaving")) {
			forwardName = "maker_create_draft_state_pincode_mapping";
		}*/ else if ((event != null)
				&& event.equals("maker_confirm_resubmit_edit_error")) {
			forwardName = "maker_prepare_resubmit";
		}  else if ((event != null)
				&& event.equals("checker_approve_create_error")) {
			forwardName = "checker_process_create";
		} 


		System.out
				.println("----------------------------- forwardName--------------------"
						+ forwardName);
		return forwardName;
	}


}
