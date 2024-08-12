package com.integrosys.cms.ui.component;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;

public class MaintainComponentAction extends CommonAction implements IPin {
	
	
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String MAKER_LIST_COMPONENT= "maker_list_component";
	public static final String CHECKER_LIST_COMPONENT = "checker_list_component";
	public static final String MAKER_ADD_COMPONENT ="maker_prepare_create_component";
	public static final String MAKER_CREATE_COMPONENT ="maker_create_component";
	public static final String MAKER_VIEW_COMPONENT ="maker_view_component";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_APPROVE_CREATE = "checker_approve_edit";
	public static final String CHECKER_VIEW_COMPONENT = "checker_view_component";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_DELETE_COMPONENT = "maker_delete_component";
	public static final String MAKER_DELETE_COMPONENT_READ = "maker_delete_component_read";
	public static final String MAKER_EDIT_COMPONENT_READ ="maker_edit_component_read";
	public static final String MAKER_EDIT_COMPONENT ="maker_edit_component";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String SEARCH_COMPONENT = "search_component";
	public static final String SEARCH_COMPONENT_ERROR = "search_component_error";
	public static final String MAKER_CREATE_COMPONENT_ERROR ="maker_create_component_error";
	public static final String CHECKER_REJECT_EDIT_ERROR ="checker_reject_edit_error";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE_ERROR = "maker_confirm_resubmit_delete_error";
	public static final String MAKER_EDIT_COMPONENT_ERROR = "maker_edit_component_error";
	public static final String RETURN_SEARCH_COMPONENT = "return_search_component";
	public static final String SEARCH_COMPONENT_NEXT = "search_component_next";
	
	
	
	
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In Maintain Component Action with event --" + event);

		ICommand objArray[] = null;
		if ((event.equals(MAKER_LIST_COMPONENT))
				|| event.equals(CHECKER_LIST_COMPONENT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListComponentCmd");

		}else if ((event!=null)&& event.equals(MAKER_ADD_COMPONENT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerPrepareCreateComponentCmd");

		}else if ((event!=null)&& event.equals(MAKER_CREATE_COMPONENT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCreateComponentCmd");

		}else if (event.equals(CHECKER_VIEW_COMPONENT)|| event.equals(MAKER_VIEW_COMPONENT)
				|| event.equals(MAKER_DELETE_COMPONENT_READ)|| event.equals(MAKER_EDIT_COMPONENT_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadComponentCmd");

		}else if (event.equals(MAKER_PREPARE_RESUBMIT_DELETE)|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(REJECTED_DELETE_READ)|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_EDIT)|| event.equals(MAKER_PREPARE_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadComponentCmd");

		}else if ((event!=null)&& event.equals(CHECKER_APPROVE_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditComponentCmd");

		}else if ((event!=null)&& event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditComponentCmd");
		}else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DELETE))
				|| event.equals(MAKER_DELETE_COMPONENT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteComponentCmd");
		}else if ( event.equals(MAKER_EDIT_COMPONENT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditComponentCmd");
		}else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseComponentCmd");
		}else if ((event.equals(SEARCH_COMPONENT))||event.equals(RETURN_SEARCH_COMPONENT)||event.equals(SEARCH_COMPONENT_NEXT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("SearchComponentCmd");
		}
		
		return (objArray);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		
		if (SEARCH_COMPONENT.equals(event)||SEARCH_COMPONENT_NEXT.equals(event)) {
			errorEvent = SEARCH_COMPONENT_ERROR;
		}
		if("maker_create_component".equals(event)){
			errorEvent = MAKER_CREATE_COMPONENT_ERROR;
		}
		if(CHECKER_REJECT_EDIT.equals(event)){
			errorEvent = CHECKER_REJECT_EDIT_ERROR;
		}
		if(MAKER_CONFIRM_RESUBMIT_DELETE.equals(event)){
			errorEvent = MAKER_CONFIRM_RESUBMIT_DELETE_ERROR;
		}
		if(MAKER_EDIT_COMPONENT.equals(event)){
			errorEvent = MAKER_EDIT_COMPONENT_ERROR;
		}
		
		
		return errorEvent;
	}
	
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		
		Page aPage = new Page();
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}else if(event.equalsIgnoreCase("search_component_error")) {
			aPage.setPageReference("maker_list_component_page");
			return aPage;
		}else if(event.equalsIgnoreCase("maker_create_component_error")) {
			aPage.setPageReference("prepare_create_component");
			return aPage;
		}else if(event.equalsIgnoreCase("checker_reject_edit_error")) {
			aPage.setPageReference("checker_component_page");
			return aPage;
		}else if(event.equalsIgnoreCase("maker_confirm_resubmit_delete_error")) {
			aPage.setPageReference("maker_prepare_resubmit_delete");
			return aPage;
		}else if(event.equalsIgnoreCase(MAKER_EDIT_COMPONENT_ERROR)) {
			aPage.setPageReference("maker_add_edit_component_page");
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
	}
		

	}

	private String getReference(String event) {
		String forwardName = null;
		
		if ((event.equals("checker_list_component"))
				|| event.equals("maker_list_component")|| event.equals("search_component")
				||event.equals("return_search_component")||"search_component_next".equals(event)) {
			forwardName = "maker_list_component_page";
		}else if ((event!=null)
				&& event.equals("maker_prepare_create_component")) {
			forwardName = "prepare_create_component";
		} else if (event.equals("maker_create_component")
				||event.equals("maker_delete_component")
				||event.equals("maker_edit_component")) {
			forwardName = "common_submit_page";
		} else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")) {
			forwardName = "checker_component_page";
		}else if ((event!=null)
				&& event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		}else if (event.equals("checker_view_component")
				||event.equals("maker_view_component")) {
			forwardName = "maker_view_page";
		}else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}else if ((event != null)&& event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		}else if ((event != null) && event.equals("maker_delete_component_read")) {
			forwardName = "maker_view_delete_page";
		}else if ((event != null) && event.equals("maker_edit_component_read")) {
			forwardName = "maker_add_edit_component_page";
		} else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}else if ((event != null)
				&& (event.equals("maker_prepare_close"))) {
			forwardName = "maker_prepare_close";
		}else if ((event != null)
				&& (event.equals("maker_confirm_close"))) {
			forwardName = "common_close_page";
		}
		//Start:-------->Abhishek Naik
		else if ((event != null)
				&& (event.equals("maker_confirm_resubmit_delete"))) {
			forwardName = "common_resubmit_page";
		}  
		// End:-------->Abhishek Naik
		return forwardName;
	}
}

