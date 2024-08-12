package com.integrosys.cms.ui.udf;

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

public class UdfAction extends CommonAction {

	String LIST_UDF = "list_udf";
/*	String PREPARE_ADD_UDF = "prepare_add_udf";
	String PREPARE_ADDERROR_UDF = "prepare_addError_udf";
	String ADD_UDF = "add_udf";
	String DELETE_UDF = "delete_udf";
	String FREEZE_UDF = "freeze_udf"; */
	
	public static final String MAKER_PREPARE_CREATE_UDF= "maker_prepare_create_udf";
	public static final String MAKER_CREATE_UDF = "maker_create_udf";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
//	public static final String MAKER_DRAFT_UDF = "maker_draft_udf";
//	public static final String MAKER_UPDATE_DRAFT_UDF = "maker_update_draft_udf";
	public static final String MAKER_VIEW_UDF = "maker_view_udf";
	public static final String CHECKER_VIEW_UDF = "checker_view_udf";
	public static final String MAKER_EDIT_UDF_READ = "maker_edit_udf_read";
	public static final String MAKER_EDIT_UDF = "maker_edit_udf";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String MAKER_SAVE_CREATE = "maker_save_create";
	private static final Object MAKER_DISABLE_UDF_READ = "maker_disable_udf_read";
	private static final Object MAKER_ENABLE_UDF_READ = "maker_enable_udf_read";
//	private static final Object MAKER_DISABLE_UDF = "maker_disable_udf";
//	public static final String MAKER_ENABLE_UDF = "maker_enable_udf";
	
	public static final String MAKER_DELETE_UDF = "maker_delete_udf";
	public static final String MAKER_ACTIVATE_UDF = "maker_activate_udf";
	public static final String CHECKER_ACTIVATE_UDF_READ = "checker_activate_udf_read";
	public static final String CHECKER_ACTIVATE_UDF = "checker_activate_udf";
	public static final String CHECKER_REJECT_ACTIVATE_UDF = "checker_reject_activate_udf";
	public static final String CHECKER_APPROVE_DELETE = "checker_approve_delete";
	
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}
	
	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	protected ICommand[] getCommandChain(String event) {
		
		DefaultLogger.debug(this,"In UDF Master Action with event --" + event);
		ICommand objArray[] = null;
		if (LIST_UDF.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(LIST_UDF);
		}
/*		else if (PREPARE_ADD_UDF.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(PREPARE_ADD_UDF);
		}
		else if (ADD_UDF.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(ADD_UDF);
		}
		else if (DELETE_UDF.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(DELETE_UDF);
		}
		else if (FREEZE_UDF.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(FREEZE_UDF);
		} */
		
		else if (MAKER_PREPARE_CREATE_UDF.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerPrepareCreateUdfCmd");
		}
		else if (MAKER_CREATE_UDF.equals(event) || MAKER_CONFIRM_RESUBMIT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCreateUdfCmd");
		}  else if ((event.equals(CHECKER_PROCESS_EDIT)) 
				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)
				|| event.equals(CHECKER_ACTIVATE_UDF_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadUdfCmd");
		}else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT) || CHECKER_APPROVE_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditUdfCmd");
		}
//			else if ( event.equals(MAKER_UPDATE_DRAFT_UDF)) {
//			objArray = new ICommand[1];
//			objArray[0] = (ICommand) getNameCommandMap().get("MakerSaveUdfCmd");
//		} 
//		else if ((event.equals(CHECKER_VIEW_UDF))
//				|| event.equals(MAKER_VIEW_UDF)) {
//			objArray = new ICommand[1];
//			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadUdfCmd");
//		}
		else if (((event != null) && event.equals(MAKER_EDIT_UDF_READ)) ||  MAKER_DISABLE_UDF_READ.equals(event) ||  MAKER_ENABLE_UDF_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadUdfCmd");
		}
		else if (((event != null) && event.equals(MAKER_EDIT_UDF)) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditUdfCmd");
		}
		else if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)
				|| "checker_reject_activate_udf".equals(event)
				|| CHECKER_REJECT_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditUdfCmd");
		}
		else if (MAKER_PREPARE_RESUBMIT_DELETE.equals(event)
				||(event.equals(MAKER_PREPARE_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadUdfCmd");
		}
		else if (MAKER_CONFIRM_RESUBMIT_DELETE.equals(event) || MAKER_ACTIVATE_UDF.equals(event)  || MAKER_DELETE_UDF.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteUdfCmd");
		}
		else if ( MAKER_CONFIRM_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseUdfCmd");
		}
		else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}
		else if (event.equals(MAKER_SAVE_CREATE) || event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditUdfCmd");
		}else if (event.equals(CHECKER_ACTIVATE_UDF)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditUdfCmd");
		}
		return objArray;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}	
	
		
	}

	private String getReference(String event) {
		String forwardName = null;
		if (event.equals(LIST_UDF)	) {
			forwardName = LIST_UDF;
		}
		/*else if (event.equals(PREPARE_ADD_UDF) || event.equals(PREPARE_ADDERROR_UDF)) {
			forwardName = PREPARE_ADD_UDF;
		}
		else if (event.equals(ADD_UDF)	) {
			forwardName = ADD_UDF;
		}
		else if (event.equals(DELETE_UDF)	) {
			forwardName = DELETE_UDF;
		}
		else if (event.equals(FREEZE_UDF)	) {
			forwardName = FREEZE_UDF;
		}*/
		
		else if (event.equals("maker_list_udf")) {
			forwardName = "list_udf_page";
		}else if (event.equals("checker_list_udf")) {
			forwardName = "checker_list_udf_page";
		}else if (MAKER_PREPARE_CREATE_UDF.equals(event) || event.equals("errorCreateSaving") ) {
			forwardName = MAKER_PREPARE_CREATE_UDF;
		} else if ((event != null)
				&& event.equals("maker_create_udf_error")) {
			forwardName = "maker_prepare_create_udf";
		}else if ( event.equals("maker_create_udf") || event.equals("maker_save_create")
				|| event.equals("maker_save_update") || "maker_delete_udf".equals(event) || "maker_activate_udf".equals(event)
				) {
			forwardName = "common_submit_page";
		}else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_create")
				|| event.equals("checker_approve_edit_error")) {
			forwardName = "checker_udf_page";
		} else if ((event != null) && event.equals("checker_approve_edit")
				|| "checker_activate_udf".equals(event) || "checker_approve_delete".equals(event)) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_reject_edit") ||  "checker_reject_delete".equals(event)) {
			forwardName = "common_reject_page";
		}else if ( event.equals("checker_reject_edit_error")
				||event.equals("checker_reject_create_error")) {
			forwardName = "checker_udf_page";
		}else if ( "checker_reject_delete_error".equals(event)) {
			forwardName = "checker_process_delete";
		}else if ((event != null) && event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		}else if ((event != null)
				&& (event.equals("maker_create_udf_error"))) {
			forwardName = "maker_prepare_create_udf";
		}
		
		else if ((event.equals(CHECKER_VIEW_UDF))
				|| event.equals(MAKER_VIEW_UDF)) {
			forwardName = "maker_view_udf_page";
		}else if ((event != null) && event.equals("maker_edit_udf_read")
				||event.equals("maker_edit_udf_error")) {
			forwardName = "maker_edit_udf_read_page";
		}
		else if ((event != null) && event.equals("maker_edit_udf")
					|| event.equals("maker_confirm_resubmit_delete")
					|| event.equals("maker_draft_udf")
					|| event.equals("maker_update_draft_udf")
					|| event.equals("maker_disable_udf")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		else if (event.equals("maker_confirm_resubmit_delete_error")) {
				forwardName = "maker_prepare_resubmit_delete";
		}
		else if ((event != null) && (event.equals("maker_prepare_close")
				|| event.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		}
		else if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE)
				)) {
			forwardName = "common_close_page";
		}
		else if (event.equals("list_pagination")) {
			forwardName = "list_udf_page";
		}
		else if (event.equals("checker_list_pagination")) {
			forwardName = "checker_list_udf_page";
		}
		else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process")||event.equals("maker_save_update_error"))) {
			forwardName = "maker_view_save_page";
		}
		else if ((event != null) && event.equals("maker_search_udf")) {
			forwardName = "list_udf_page";
		}
		else if ("maker_disable_udf_read".equals(event) || "maker_enable_udf_read".equals(event)) {
			forwardName = "maker_en_disable_udf";
		}else if (event.equals("checker_reject_activate_udf_error") || event.equals("checker_activate_udf_read")) {
			forwardName = "checker_activate_udf_read";
		}else if (event.equals("checker_reject_activate_udf_error")) {
			forwardName = "checker_activate_udf_read";
		}else if (event.equals("checker_reject_activate_udf")) {
			forwardName = "common_reject_page";

		}else if (event.equals("checker_process_delete")) {
			forwardName = "checker_process_delete";
		}
		return forwardName;
	}
	protected String getErrorEvent(String event) {
		String errorEvent=event;
//		if (ADD_UDF.equals(event)){
//			errorEvent=PREPARE_ADDERROR_UDF;
//		}
		if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}else if (event.equals("checker_reject_delete")) {
			errorEvent = "checker_reject_delete_error";
		}else if (event.equals("checker_reject_create")) {
			errorEvent = "checker_reject_create_error";
		}else if (event.equals("maker_create_udf")|| event.equals("maker_draft_udf")) {
			errorEvent = "maker_create_udf_error";
		}else if (event.equals("maker_edit_udf")) {
			errorEvent = "maker_edit_udf_error";
		}else if (event.equals("maker_update_draft_udf")) {
			errorEvent = "maker_edit_udf_error";
		}else if (event.equals("maker_confirm_resubmit_delete")) {
			errorEvent = "maker_confirm_resubmit_delete_error";
		}else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}else if (event.equals("checker_approve_edit")) {
			errorEvent = "checker_approve_edit_error";
		}else if(event.equals("maker_list_udf")){
			errorEvent="maker_list_udf";
		}else if(event.equals("checker_reject_activate_udf")){
			errorEvent="checker_reject_activate_udf_error";
		}else if (event.equals("checker_approve_edit")) {
			errorEvent = "checker_approve_edit_error";
		}
		return errorEvent;
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return UdfValidator.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
	//	if ( event.equals(ADD_UDF))
			if (event.equals(MAKER_CREATE_UDF)
					
//					||event.equals(CHECKER_APPROVE_EDIT)
					
					||event.equals(MAKER_EDIT_UDF)
					|| event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)
					|| event.equals(MAKER_SAVE_UPDATE)
					) {
			result = true;
			}
		return result;
	}
}
