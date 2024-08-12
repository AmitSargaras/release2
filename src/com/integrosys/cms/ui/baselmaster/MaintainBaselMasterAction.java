package com.integrosys.cms.ui.baselmaster;

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
import com.integrosys.cms.ui.newtatmaster.TatMasterValidator;

public class MaintainBaselMasterAction extends CommonAction implements IPin {

	private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String MAKER_LIST_BASEL= "maker_list_basel_master";
	public static final String CHECKER_LIST_BASEL = "checker_list_basel_master";
	public static final String MAKER_ADD_BASEL = "maker_prepare_create_basel";
	public static final String MAKER_CONFIRM_ADD_BASEL = "maker_create_basel";
	public static final String MAKER_CONFIRM_ADD_BASEL_ERROR = "maker_create_basel_error";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_APPROVE_CREATE = "checker_approve_edit";
	public static final String MAKER_VIEW_BASEL ="maker_view_basel";
	public static final String CHECKER_VIEW_BASEL = "checker_view_basel";
	public static final String MAKER_EDIT_BASEL_READ = "maker_edit_basel_read";
	public static final String MAKER_DELETE_BASEL_READ = "maker_delete_basel_read";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_EDIT_ERROR ="checker_reject_edit_error";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE_ERROR = "maker_confirm_resubmit_delete_error";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String MAKER_EDIT_BASEL ="maker_edit_basel";
	public static final String MAKER_EDIT_BASEL_ERROR ="maker_edit_basel_error";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String MAKER_DELETE_BASEL = "maker_delete_basel";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String SEARCH_BASEL = "search_basel";
	public static final String RETURN_SEARCH_BASEL = "return_search_basel";
	public static final String SEARCH_BASEL_NEXT = "search_basel_next";
	
	
	
	
	
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In Maintain Basel Action with event --" + event);

		ICommand objArray[] = null;
		if (event.equals(MAKER_LIST_BASEL)|| event.equals(CHECKER_LIST_BASEL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListBaselMasterCmd");

		}else if(event.equals(MAKER_ADD_BASEL)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerPrepareCreateBaselCmd");
			
		}else if(event.equals(MAKER_CONFIRM_ADD_BASEL)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCreateBaselCmd");
		}else if (event.equals(CHECKER_PROCESS_CREATE)||event.equals(MAKER_PREPARE_RESUBMIT_DELETE)||event.equals(REJECTED_DELETE_READ)
				|| event.equals(MAKER_PREPARE_CLOSE)||event.equals(CHECKER_PROCESS_EDIT)|| event.equals(CHECKER_PROCESS_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadBaselCmd");
		}else if ((event!=null)&& event.equals(CHECKER_APPROVE_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditBaselCmd");
		}else if (event.equals(MAKER_VIEW_BASEL)||event.equals(CHECKER_VIEW_BASEL)
				||event.equals(MAKER_EDIT_BASEL_READ)||event.equals(MAKER_DELETE_BASEL_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadBaselCmd");
		}else if ((event!=null)&& event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditBaselCmd");
		}else if (event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerResubmitBaselCmd");
		}else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseBaselCmd");
		}else if ( event.equals(MAKER_EDIT_BASEL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditBaselCmd");
		}else if ( event.equals(MAKER_DELETE_BASEL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteBaselCmd");
		}else if (event.equals(SEARCH_BASEL)||event.equals(RETURN_SEARCH_BASEL)||event.equals(SEARCH_BASEL_NEXT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("SearchBaselCmd");
		}
		return objArray;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return BaselValidator.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ( event.equals(MAKER_CONFIRM_ADD_BASEL)||event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)||event.equals(MAKER_EDIT_BASEL))

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (MAKER_CONFIRM_ADD_BASEL.equals(event)) {
			errorEvent = MAKER_CONFIRM_ADD_BASEL_ERROR;
		}if(CHECKER_REJECT_EDIT.equals(event)){
			errorEvent = CHECKER_REJECT_EDIT_ERROR;
		}if(MAKER_CONFIRM_RESUBMIT_DELETE.equals(event)){
			errorEvent = MAKER_CONFIRM_RESUBMIT_DELETE_ERROR;
		}if(MAKER_EDIT_BASEL.equals(event)){
			errorEvent = MAKER_EDIT_BASEL_ERROR;
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
		}else if(event.equalsIgnoreCase("maker_confirm_resubmit_delete_error")) {
			aPage.setPageReference("maker_prepare_resubmit_delete");
			return aPage;
		}
		else if(event.equalsIgnoreCase("checker_reject_edit_error")) {
			aPage.setPageReference("checker_basel_page");
			return aPage;
		}else if(event.equalsIgnoreCase(MAKER_EDIT_BASEL_ERROR)) {
			aPage.setPageReference("maker_veiw_edit_page");
			return aPage;
		}
		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
		
		
	}
	
	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals("maker_list_basel_master")||event.equals("checker_list_basel_master")||event.equals("search_basel")
				||event.equals("return_search_basel")||event.equals("search_basel_next")) {
			forwardName = "list_basel_master_page";
		}else if (event.equals("maker_prepare_create_basel")||MAKER_CONFIRM_ADD_BASEL_ERROR.equals(event)) {
			forwardName = "prepare_create_basel";
		}else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}else if ( event.equals("maker_create_basel")||event.equals("maker_edit_basel")||event.equals("maker_delete_basel")) {
			forwardName = "common_submit_page";
		}else if (event.equals("checker_process_create")||event.equals(CHECKER_PROCESS_EDIT)||event.equals("checker_process_delete")) {
			forwardName = "checker_basel_page";
		}else if ((event!=null)
				&& event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		}else if (event.equals("checker_view_basel")
				||event.equals("maker_view_basel")) {
			forwardName = "maker_view_page";
		}else if ((event != null) && event.equals("maker_delete_basel_read")) {
			forwardName = "maker_view_delete_page";
		}else if ((event != null) && event.equals("maker_edit_basel_read")) {
			forwardName = "maker_veiw_edit_page";
		}else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}else if ((event != null)&& event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		}else if ((event != null)&& (event.equals("maker_prepare_close"))) {
			forwardName = "maker_prepare_close";
		}else if ((event != null)
				&& event.equals("maker_confirm_close")) {
			forwardName = "common_close_page";
		}else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		}else if ((event != null)
				&& event.equals("maker_confirm_resubmit_delete")) {
			forwardName = "common_resubmit_page";
		}
		
		return forwardName;
	}

}
