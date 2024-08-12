package com.integrosys.cms.ui.cersaiMapping;

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
import com.integrosys.cms.ui.cersaiMapping.CersaiMappingValidator;

public class CersaiMappingAction extends CommonAction implements IPin{

	private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String MAKER_LIST_CERSAI_MAPPING= "maker_list_cersai_mapping";
	
	public static final String CHECKER_LIST_CERSAI_MAPPING= "checker_list_cersai_mapping";
	
	public static final String MAKER_LIST_CERSAI_MAPPING_ON_SELECT_MASTER="maker_list_cersai_mapping_on_select_master";
	
	//public static final String CHECKER_LIST_CERSAI_MAPPING_ON_SELECT_MASTER="checker_list_cersai_mapping_on_select_master";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_CREATE_CERSAI_MAPPING = "maker_create_cersai_mapping";
	public static final String MAKER_PREPARE_CREATE_CERSAI_MAPPING= "maker_prepare_create_cersai_mapping";
	public static final String CHECKER_VIEW_CERSAI_MAPPING = "checker_view_cersai_mapping";
	public static final String MAKER_VIEW_CERSAI_MAPPING = "maker_view_cersai_mapping";
	public static final String MAKER_EDIT_CERSAI_MAPPING_READ = "maker_edit_cersai_mapping_read";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String PROCESS_LIST_PAGINATION = "process_list_pagination";
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In Cersai Mapping Action with event --" + event);

		ICommand objArray[] = null;
		if (event.equals(MAKER_LIST_CERSAI_MAPPING) || event.equals(CHECKER_LIST_CERSAI_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCersaiMappingCmd");

		}
		if (event.equals(MAKER_LIST_CERSAI_MAPPING_ON_SELECT_MASTER) || event.equals("checker_list_cersai_mapping_on_select_master")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCersaiMappingOnSelectMasterCmd");
		} 
		
		if (event.equals(CHECKER_PROCESS_EDIT) 
				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadCersaiMappingCmd");
		}
		if ((event.equals(MAKER_PREPARE_RESUBMIT_DELETE))
				||(event.equals(MAKER_PREPARE_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadCersaiMappingCmd");
		}
		
		if ((event.equals(CHECKER_VIEW_CERSAI_MAPPING))
				|| event.equals(MAKER_VIEW_CERSAI_MAPPING)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadCersaiMappingCmd");
		}
		
		if ((event != null) && event.equals(MAKER_EDIT_CERSAI_MAPPING_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadCersaiMappingCmd");
		}
		
		if (event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteCersaiMappingCmd");
		}
		
		
		
//		if ((event != null)
//				&& event.equals(MAKER_PREPARE_CREATE_CERSAI_MAPPING)) {
//			objArray = new ICommand[1];
//			objArray[0] = (ICommand) getNameCommandMap().get(
//					"MakerPrepareCreateCersaiMappingCmd");
//		}
		
		if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditCersaiMappingCmd");
		}
		
		if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseCersaiMappingCmd");
		}
		
		if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION) || event.equals(PROCESS_LIST_PAGINATION) || event.equals("checker_list_pagination_view")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}
		if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditCersaiMappingCmd");
		}
		
//		if (event.equals(MAKER_CREATE_CERSAI_MAPPING)) {
//			objArray = new ICommand[1];
//			objArray[0] = (ICommand) getNameCommandMap().get(
//					"MakerCreateCersaiMappingCmd");
//
//		}
		
		if (EVENT_SUBMIT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SubmitCersaiMappingCommand") };
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
		return CersaiMappingValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_LIST_CERSAI_MAPPING)
				||event.equals(CHECKER_APPROVE_EDIT)
				|| event.equals(MAKER_CREATE_CERSAI_MAPPING)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)
				|| event.equals(EVENT_SUBMIT))
		{
			result = true;
		}
		return result;
	}
	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		 if(event.equals("maker_list_cersai_mapping")){
			errorEvent="maker_list_cersai_mapping";
		}
		 if(event.equals("submit")){
				errorEvent="maker_list_cersai_mapping";
			}
		 if(event.equals("checker_reject_edit")) {
				errorEvent = "checker_reject_edit_error";
			}
		 if (event.equals("checker_approve_edit")) {
				errorEvent = "checker_approve_edit_error";
			}
		 if (event.equals("checker_reject_create")) {
				errorEvent = "checker_reject_create_error";
			}
		 if (event.equals("maker_confirm_resubmit_delete")) {
				errorEvent = "maker_confirm_resubmit_delete_error";
			}
//		 if (event.equals("maker_create_cersai_mapping")) {
//				errorEvent = "maker_create_cersai_mapping_error";
//		 }
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
		}else if(event.equalsIgnoreCase("checker_reject_edit_error")) {
			aPage.setPageReference("checker_list_cersai_mapping_page");
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}	
	}
	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals("maker_list_cersai_mapping") || event.equals(MAKER_LIST_CERSAI_MAPPING_ON_SELECT_MASTER)) {
			forwardName = "maker_list_cersai_mapping_page";
		}
		
		if (event.equals("checker_list_cersai_mapping_on_select_master")) {
			forwardName = "checker_list_cersai_mapping_view";
		}
//		if (MAKER_PREPARE_CREATE_CERSAI_MAPPING.equals(event) || event.equals("errorCreateSaving") ) {
//			forwardName = MAKER_PREPARE_CREATE_CERSAI_MAPPING;
//		}
//		
		if (event.equals("checker_list_cersai_mapping")) {
			forwardName = "checker_list_cersai_mapping_view";
		}
		
//		if ((event.equals(CHECKER_VIEW_CERSAI_MAPPING))
//				|| event.equals(MAKER_VIEW_CERSAI_MAPPING)) {
//			forwardName = "maker_view_cersai_mapping_page";
//		}
		
		if ((event != null) && event.equals("maker_edit_cersai_mapping_read")
				||event.equals("maker_edit_cersai_mapping_error")) {
			forwardName = "maker_edit_cersai_mapping_read_page";
		}
		
		if ((event != null) && (event.equals("maker_prepare_close"))) {
			forwardName = "maker_prepare_close";
		}
		
		if ((event != null) && event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		}
		if (event.equals("maker_confirm_resubmit_delete_error")) {
			forwardName = "maker_prepare_resubmit_delete";
		}
		
		if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")
				|| event.equals("checker_approve_edit_error")) {
			forwardName = "checker_list_cersai_mapping_page";
		}
		
		if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE))) {
			forwardName = "common_close_page";
		}
		
		if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		}
		
		if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}
		
		if ( event.equals("checker_reject_edit_error")
				||event.equals("checker_reject_create_error")) {
			forwardName = "checker_list_cersai_mapping_page";
		}
		
		if (event.equals("EVENT_SUBMIT") || event.equals(EVENT_SUBMIT) || event.equals("maker_create_cersai_mapping")
				|| event.equals("maker_confirm_resubmit_delete")) {
			forwardName = "common_submit_page";
		}
		
		if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		
		if (event.equals("list_pagination")) {
			forwardName = "maker_list_cersai_mapping_page";
		}
		
		if (event.equals("checker_list_pagination")) {
			forwardName = "checker_list_cersai_mapping_page";
		}
		
		if (event.equals("checker_list_pagination_view")) {
			forwardName = "checker_list_cersai_mapping_view";
		}
		if (event.equals("process_list_pagination")) {
			forwardName = "maker_prepare_resubmit_delete";
		}
//		if ((event != null)
//				&& event.equals("maker_create_cersai_mapping_error")) {
//			forwardName = "maker_prepare_create_cersai_mapping";
//		}
		
		return forwardName;
	}
}
