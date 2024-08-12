package com.integrosys.cms.ui.riskType;

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

public class RiskTypeAction extends CommonAction implements IPin{

	private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String MAKER_LIST_RISK_TYPE= "maker_list_risk_type";
	public static final String CHECKER_LIST_RISK_TYPE= "checker_list_risk_type";
	public static final String MAKER_PREPARE_CREATE_RISK_TYPE= "maker_prepare_create_risk_type";
	public static final String MAKER_CREATE_RISK_TYPE = "maker_create_risk_type";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String MAKER_DRAFT_RISK_TYPE = "maker_draft_risk_type";
	public static final String MAKER_UPDATE_DRAFT_RISK_TYPE = "maker_update_draft_risk_type";

	public static final String MAKER_VIEW_RISK_TYPE = "maker_view_risk_type";
	public static final String CHECKER_VIEW_RISK_TYPE = "checker_view_risk_type";
	public static final String MAKER_EDIT_RISK_TYPE_READ = "maker_edit_risk_type_read";
	public static final String MAKER_EDIT_RISK_TYPE = "maker_edit_risk_type";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE = "maker_prepare_resubmit_create";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String MAKER_SAVE_CREATE = "maker_save_create";
	public static final String MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In Risk Types Action with event --" + event);

		ICommand objArray[] = null;
		if (event.equals(MAKER_LIST_RISK_TYPE)|| event.equals(CHECKER_LIST_RISK_TYPE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListRiskTypeCmd");

		} else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_RISK_TYPE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateRiskTypeCmd");

		}else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_RISK_TYPE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateRiskTypeCmd");

		}  else if ((event.equals(CHECKER_PROCESS_EDIT)) 
				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadRiskTypeCmd");
		}else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditRiskTypeCmd");
		}else if (event.equals(MAKER_DRAFT_RISK_TYPE)
				|| event.equals(MAKER_UPDATE_DRAFT_RISK_TYPE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerSaveRiskTypeCmd");
		} 
		//Added by santosh - CR UBS limit
		else if ((event.equals(CHECKER_VIEW_RISK_TYPE))
				|| event.equals(MAKER_VIEW_RISK_TYPE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadRiskTypeCmd");
		}
		else if ((event != null) && event.equals(MAKER_EDIT_RISK_TYPE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadRiskTypeCmd");
		}
		else if ((event != null) && event.equals(MAKER_EDIT_RISK_TYPE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditRiskTypeCmd");
		}
		else if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditRiskTypeCmd");
		}
		else if (event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				||(event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_DRAFT_CLOSE_PROCESS) ||event.equals(MAKER_PREPARE_RESUBMIT_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadRiskTypeCmd");
		}
		else if (event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteRiskTypeCmd");
		}
		else if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE)
				|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseRiskTypeCmd");
		}
		else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}
		else if (event.equals(MAKER_SAVE_CREATE)
				|| event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditRiskTypeCmd");
	/*	}else if (event.equals("list") || event.equals("listEdit") || event.equals("listDraft") || event.equals("listRejected") || event.equals("listDeleteRejected")) {
			objArray = new ICommand[1];
			objArray[0] = new ListCustomerCommand();
*/		}else if (event.equals("cancle") || event.equals("cancleEdit") || event.equals("cancleDraft") || event.equals("cancleRejected") || event.equals("cancleDeleteRejected")) {
			objArray = new ICommand[1];
			objArray[0] = new CancleFilterCmd();
		}else if (event.equals("submitCustomer") || event.equals("submitCustomerEdit") || event.equals("submitCustomerDraft")
				|| event.equals("submitCustomerRejected") || event.equals("submitCustomerDeleteRejected")) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCustomerCommand();
		}else if ((event != null) && event.equals("maker_delete_risk_type_read")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadRiskTypeCmd");
		}else if ((event != null) && event.equals("maker_confirm_resubmit_edit")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitEditRiskTypeCommand");
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
		return RiskTypeValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_CREATE_RISK_TYPE)
				||event.equals(MAKER_UPDATE_DRAFT_RISK_TYPE)
				||event.equals(CHECKER_APPROVE_EDIT)
				||event.equals(MAKER_DRAFT_RISK_TYPE)
				||event.equals(MAKER_EDIT_RISK_TYPE)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)
				|| event.equals(MAKER_SAVE_UPDATE)
				|| event.equals(MAKER_LIST_RISK_TYPE)
				|| event.equals("maker_confirm_resubmit_edit"))
		{
			result = true;
		}
		return result;
	}
	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}else if (event.equals("checker_reject_create")) {
			errorEvent = "checker_reject_create_error";
		}else if (event.equals("maker_create_risk_type")|| event.equals("maker_draft_risk_type")) {
			errorEvent = "maker_create_risk_type_error";
		}else if (event.equals("maker_edit_risk_type")) {
			errorEvent = "maker_edit_risk_type_error";
		}else if (event.equals("maker_update_draft_risk_type")) {
			errorEvent = "maker_edit_risk_type_error";
		}else if (event.equals("maker_delete_risk_type")) {
			errorEvent = "maker_delete_excluded_facility_category_error";
		}else if (event.equals("maker_confirm_resubmit_delete")) {
			errorEvent = "maker_confirm_resubmit_delete_error";
		}else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}else if (event.equals("checker_approve_edit")) {
			errorEvent = "checker_approve_edit_error";
		}else if(event.equals("maker_list_risk_type")){
			errorEvent="maker_list_risk_type";
		}else if ("list".equals(event)) {
				errorEvent= "cancleFilter";
		}else if ("listEdit".equals(event)) {
			errorEvent= "cancleFilterEdit";
		}else if ("listDraft".equals(event)) {
			errorEvent= "cancleFilterDraft";
		}else if ("listDeleteRejected".equals(event)) {
			errorEvent= "cancleFilterDeleteRejected";
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
		}else if(event.equalsIgnoreCase("checker_reject_edit_error")) {
			aPage.setPageReference("checker_risk_type_page");
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}	
	}
	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals("maker_list_risk_type")) {
			forwardName = "list_risk_type_page";
		}else if (event.equals("checker_list_risk_type")) {
			forwardName = "checker_list_risk_type_page";
		}else if (MAKER_PREPARE_CREATE_RISK_TYPE.equals(event) || event.equals("errorCreateSaving") ) {
			forwardName = MAKER_PREPARE_CREATE_RISK_TYPE;
		} else if ((event != null)
				&& event.equals("maker_create_risk_type_error")) {
			forwardName = "maker_prepare_create_risk_type";
		}else if (/*(event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_excluded_facility_category")
				|| event.equals("maker_confirm_resubmit_delete")
				||*/ event.equals("maker_create_risk_type")
				/*|| event.equals("maker_draft_excluded_facility_category")
				|| event.equals("maker_confirm_resubmit_create")*/
				|| event.equals("maker_save_create")
				|| event.equals("maker_save_update")
				/*|| event.equals("maker_update_draft_excluded_facility_category")*/
				) {
			forwardName = "common_submit_page";
		}else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")
				|| event.equals("checker_approve_edit_error")) {
			forwardName = "checker_risk_type_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}else if ( event.equals("checker_reject_edit_error")
				||event.equals("checker_reject_create_error")) {
			forwardName = "checker_risk_type_page";
		}else if ((event != null) && event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		}else if ((event != null) && event.equals("maker_prepare_resubmit_create")) {
			forwardName = "maker_prepare_resubmit_create";
		}
		/*else if ((event != null)
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
		}*/
		else if ((event != null)
				&& (event.equals("maker_create_risk_type_error"))) {
			forwardName = "maker_prepare_create_risk_type";
		}
		//added by santosh
		else if ((event.equals(CHECKER_VIEW_RISK_TYPE))
				|| event.equals(MAKER_VIEW_RISK_TYPE)) {
			forwardName = "maker_view_risk_type_page";
		}else if ((event != null) && event.equals("maker_edit_risk_type_read")
				||event.equals("maker_edit_risk_type_error")) {
			forwardName = "maker_edit_risk_type_read_page";
		}
		else if ((event != null) && event.equals("maker_edit_risk_type")
					|| event.equals("maker_confirm_resubmit_delete")
					|| event.equals("maker_draft_risk_type")
					|| event.equals("maker_update_draft_risk_type") || event.equals("maker_confirm_resubmit_edit")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		else if (event.equals("maker_confirm_resubmit_delete_error")) {
				forwardName = "maker_prepare_resubmit_delete";
		}
		else if (event.equals("maker_confirm_resubmit_create_error")) {
			forwardName = "maker_prepare_resubmit_create";
		}
		else if ((event != null) && (event.equals("maker_prepare_close")
				|| event.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		}
		else if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE)
				|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			forwardName = "common_close_page";
		}
		else if (event.equals("list_pagination")) {
			forwardName = "list_risk_type_page";
		}
		else if (event.equals("checker_list_pagination")) {
			forwardName = "checker_list_risk_type_page";
		}
		else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process")||event.equals("maker_save_update_error"))) {
			forwardName = "maker_view_save_page";
		}
		else if ((event != null) && event.equals("maker_search_risk_type")) {
			forwardName = "list_risk_type_page";
		}
		else if ((event.equals("checker_search_list_risk_type"))
				|| event.equals("maker_search_list_risk_type")) {
			forwardName = "list_fccBranch_page";
		}else if("list".equals(event)) {
			forwardName = "list";
		}else if("listEdit".equals(event)) {
			forwardName = "listEdit";
		}else if("listDraft".equals(event)) {
			forwardName = "listDraft";
		}else if("listRejected".equals(event)) {
			forwardName = "listRejected";
		}else if("listDeleteRejected".equals(event)) {
			forwardName = "listDeleteRejected";
		}else if("cancleFilter".equals(event)) {
			forwardName = "cancleFilter";
		}
		else if("cancle".equals(event)) {
			forwardName = "cancleFilter";
		}else if("submitCustomer".equals(event)) {
			forwardName = "submitCustomer";
		}else if("submitCustomerEdit".equals(event)) {
			forwardName = "submitCustomerEdit";
		}else if("submitCustomerDraft".equals(event)) {
			forwardName = "submitCustomerDraft";
		}else if("submitCustomerRejected".equals(event)) {
			forwardName = "submitCustomerRejected";
		}else if("submitCustomerDeleteRejected".equals(event)) {
			forwardName = "submitCustomerDeleteRejected";
		}else if("cancleFilterEdit".equals(event)) {
			forwardName = "cancleFilterEdit";
		}else if("cancleEdit".equals(event)) {
			forwardName = "cancleFilterEdit";
		}else if("cancleDraft".equals(event)) {
			forwardName = "cancleFilterDraft";
		}else if("cancleRejected".equals(event)) {
			forwardName = "cancleFilterRejected";
		}else if("cancleFilterDraft".equals(event)) {
			forwardName = "cancleFilterDraft";
		}else if("cancleDeleteRejected".equals(event)) {
			forwardName = "cancleFilterDeleteRejected";
		}else if("cancleFilterDeleteRejected".equals(event)) {
			forwardName = "cancleFilterDeleteRejected";
		}else if("maker_delete_risk_type_read".equals(event)) {
			forwardName = "maker_delete_risk_type_page";
		}
		return forwardName;
	}
}
