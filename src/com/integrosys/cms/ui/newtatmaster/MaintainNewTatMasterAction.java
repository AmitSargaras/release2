package com.integrosys.cms.ui.newtatmaster;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.discrepency.DiscrepencyValidator;

public class MaintainNewTatMasterAction extends CommonAction {
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String MAKER_LIST_TAT_MASTER="maker_list_tat_master";
	
	public static final String CHECKER_LIST_TAT_MASTER="checker_list_tat_master";
	
	public static final String MAKER_VIEW_TAT_EVENT="maker_view_tat_event";
	
	public static final String CHECKER_VIEW_TAT_EVENT="checker_view_tat_event";
	
	public static final String MAKER_EDIT_TAT_EVENT="maker_edit_tat_event";
	
	public static final String MAKER_CONFORM_EDIT_TAT_EVENT="maker_conform_edit_tat_event";
	
	public static final String MAKER_CONFORM_EDIT_TAT_EVENT_ERROR="maker_conform_edit_tat_event_error";
	
	public static final String CHECKER_PROCESS_EDIT="checker_process_edit";
	
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	
	public static final String MAKER_RESUBMIT_TAT_EVENT = "maker_resubmit_tat_event";
	
	public static final String MAKER_RESUBMIT_TAT_EVENT_ERROR = "maker_resubmit_tat_event_error";
	
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	
	public static final String CHECKER_REJECT_EDIT_ERROR ="checker_reject_edit_error";


protected ICommand[] getCommandChain(String event) {
	DefaultLogger.debug(this, " Event : -----> " + event);
	ICommand objArray[] = null;
	if (event.equals(MAKER_LIST_TAT_MASTER)||event.equals(CHECKER_LIST_TAT_MASTER)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("ViewTatMasterEvent");
	}else if (event.equals(MAKER_VIEW_TAT_EVENT)|| event.equals(CHECKER_VIEW_TAT_EVENT)
			||event.equals(MAKER_EDIT_TAT_EVENT)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("ReadTatMasterEvent");
	}else if (event.equals(MAKER_CONFORM_EDIT_TAT_EVENT)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("EditTatMasterEvent");
	}else if (event.equals(CHECKER_PROCESS_EDIT)||event.equals(MAKER_PREPARE_RESUBMIT)
			||event.equals(MAKER_PREPARE_CLOSE)||event.endsWith("rejected_delete_read")) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadTatMaster");
	}else if (event.equals(CHECKER_APPROVE_EDIT)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditTatMasterCmd");
	}else if (event.equals(CHECKER_REJECT_EDIT)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditTatMasterCmd");
	}else if (event.equals(MAKER_RESUBMIT_TAT_EVENT)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("MakerResubmitTatMaster");
	}else if (event.equals(MAKER_CONFIRM_CLOSE)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseTatMaster");
	}
	
	return objArray;
}


public ActionErrors validateInput(ActionForm aForm, Locale locale) {
	DefaultLogger.debug(this, "VALIDATION REQUIRED...");
	return TatMasterValidator.validateInput(aForm, locale);
}



protected boolean isValidationRequired(String event) {
	boolean result = false;
	if ( event.equals(MAKER_CONFORM_EDIT_TAT_EVENT)	
			||(event.equals(MAKER_RESUBMIT_TAT_EVENT))
	)

	{
		result = true;
	}
	return result;
}

protected String getErrorEvent(String event) {
	String errorEvent = getDefaultEvent();
	if (MAKER_CONFORM_EDIT_TAT_EVENT.equals(event)) {
		errorEvent = MAKER_CONFORM_EDIT_TAT_EVENT_ERROR;
	}
	if (MAKER_RESUBMIT_TAT_EVENT.equals(event)) {
		errorEvent = MAKER_RESUBMIT_TAT_EVENT_ERROR;
	}
	if (CHECKER_REJECT_EDIT.equals(event)) {
		errorEvent = CHECKER_REJECT_EDIT_ERROR;
	}
	return errorEvent;
	
}


protected IPage getNextPage(String event, HashMap resultMap,
		HashMap exceptionMap) {
	Page aPage = new Page();
	if ((resultMap.get("wip") != null)
			&& ((String) resultMap.get("wip")).equals("wip"))
		aPage.setPageReference("work_in_process");
	else
		aPage.setPageReference(getReference(event));
	return aPage;
}

private String getReference(String event) {
	String forwardName = null;
	if (event.equals(MAKER_LIST_TAT_MASTER)||event.equals(CHECKER_LIST_TAT_MASTER)) {
		forwardName = MAKER_LIST_TAT_MASTER;
	}else if (event.equals(MAKER_VIEW_TAT_EVENT)|| event.equals(CHECKER_VIEW_TAT_EVENT)) {
		forwardName = MAKER_VIEW_TAT_EVENT;
	}else if (event.equals(MAKER_EDIT_TAT_EVENT)||event.equals(MAKER_CONFORM_EDIT_TAT_EVENT_ERROR)) {
		forwardName = MAKER_EDIT_TAT_EVENT;
	} else if (event.equals(MAKER_CONFORM_EDIT_TAT_EVENT)) {
		forwardName = "common_submit_page";
	}	else if ((event != null) && event.equals("work_in_process")) {
		forwardName = "work_in_process_page";
	}else if(event.equals(CHECKER_PROCESS_EDIT)|| event.equals(CHECKER_REJECT_EDIT_ERROR)){
		forwardName = "checker_process_edit";
	}else if ((event!=null)&& event.equals("checker_approve_edit")) {
		forwardName = "common_approve_page";
	} else if ((event != null) && event.equals("checker_reject_edit")) {
		forwardName = "common_reject_page";
	}else if (event.equals("maker_prepare_resubmit")||event.equals(MAKER_RESUBMIT_TAT_EVENT_ERROR)) {
		forwardName = "maker_prepare_resubmit";
	}else if ((event != null)&& (event.equals("maker_prepare_close"))) {
		forwardName = "maker_prepare_close";
	}else if ((event != null) && event.equals("rejected_delete_read")) {
		forwardName = "chk_view_to_track_page";
	}
	else if ((event != null)
			&& (event.equals("maker_confirm_close"))) {
		forwardName = "common_close_page";
	}else if ((event != null)
			&& (event.equals("maker_resubmit_tat_event"))) {
		forwardName = "common_resubmit_page";
	} 
	
	
	DefaultLogger.debug(this, "Forward name --> " + forwardName);
	return forwardName;
}
}
