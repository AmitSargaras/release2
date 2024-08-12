package com.integrosys.cms.ui.insurancecoveragedtls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;

/**
 * Describe this class
 * Purpose : Insurance Coverage Details Actions
 * Description : Handling Actions of Insurance Coverage Details
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1 $
 * Tag : $Name$
 */

public class InsuranceCoverageDtlsAction extends CommonAction{
	
	public static final String LIST_INSURANCE_COVERAGE_DTLS = "list_insurance_coverage_dtls";
	
	public static final String PREPARE_CREATE_INSURANCE_COVERAGE_DTLS = "prepare_create_insurance_coverage_dtls";
	
	public static final String MAKER_SUBMIT_CREATE_INSURANCE_COVERAGE_DTLS = "maker_submit_create_insurance_coverage_dtls";
	
	public static final String SUBMIT_CREATE_INSURANCE_COVERAGE_DTLS = "maker_submit_create_insurance_coverage_dtls";
	
	public static final String VIEW_INSURANCE_COVERAGE_DTLS_BY_INDEX = "view_insurance_coverage_dtls_by_index";
	
	public static final String PREPARE_EDIT_INSURANCE_COVERAGE_DTLS = "prepare_maker_submit_edit";
	
	public static final String MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE_DTLS = "maker_submit_edit";
	
	public static final String PREPARE_DELETE_INSURANCE_COVERAGE_DTLS = "prepare_maker_submit_remove";
	
	public static final String MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE_DTLS = "maker_submit_remove";
	
	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";
	
	public static final String CHECKER_LIST_INSURANCE_COVERAGE_DTLS="checker_list_insurance_coverage_dtls";
	
	public static final String CHECKER_VIEW_INSURANCE_COVERAGE_DTLS="checker_view_insurance_coverage_dtls";

	public static final String MAKER_CONFIRM_RESUBMIT_EDIT="maker_confirm_resubmit_edit";

	public static final String CHECKER_EDIT_READ="checker_edit_read";

	public static final String REJECTED_DELETE_READ="rejected_delete_read";
	
	public static final String CHECKER_APPROVE_EDIT="checker_approve_edit";
	
	public static final String CHECKER_REJECT_EDIT="checker_reject_edit";
	
	public static final String MAKER_PREPARE_CLOSE="maker_prepare_close";
	
	public static final String MAKER_EDIT_INSURANCE_COVERAGE_DTLS_READ="maker_edit_insurance_coverage_dtls_read";
	
	public static final String MAKER_PREPARE_RESUBMIT="maker_prepare_resubmit";
	
	public static final String MAKER_CONFIRM_CLOSE="maker_confirm_close";
	
	public static final String REDIRECT_LIST_INSURANCE_COVERAGE_DTLS="redirect_list_insurance_coverage_dtls";
	
	public static final String MAKER_DELETE_INSURANCE_COVERAGE_DTLS_READ="maker_delete_insurance_coverage_dtls_read";
	
	public static final String MAKER_EDIT_REJECT_EDIT="maker_edit_reject_edit";
	
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	
	public static final String LIST_PAGINATION="list_pagination";
	
	public static final String MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE_DTLS = "maker_prepare_resubmit_create";
	
	public static final String CHECKER_INSURANCE_COVERAGE_DTLS_PAGE = "checker_insurance_coverage_dtls_page";
	
	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";
	
	public static final String COMMON_CLOSE_PAGE = "common_close_page";
	
	public static final String COMMON_APPROVE_PAGE = "common_approve_page";
	
	public static final String MAKER_VIEW_DELETE_PAGE = "maker_view_delete_page";
	
	public static final String MAKER_VIEW_TODO_PAGE = "maker_view_todo_page";
	
	public static final String CHECKER_VIEW_DELETE_PAGE = "checker_view_delete_page";
	
	public static final String REJECTED_READ_PAGE = "rejected_read_page";
	
	public static final String WORK_IN_PROGRESS_PAGE = "work_in_process_page";
	
	public static final String MAKER_CNCL_REJECT_ADD = "maker_cncl_reject_add";
	
	public static final String MAKER_CNCL_REJECT_EDIT = "maker_cncl_reject_edit";
	
	public static final String MAKER_CNCL_REJECT_DELETE = "maker_cncl_reject_delete";
	
	public static final String MAKER_EDIT_REJECT_ADD = "maker_edit_reject_add";
	
	public static final String MAKER_EDIT_REJECT_DELETE = "maker_edit_reject_delete";
	
	public static final String WORK_IN_PROCESS = "work_in_process";
	
	public static final String REJECTED_READ = "rejected_read";
	
	
	public static final String MAKER_CONFIRM_DRAFT_CLOSE="maker_confirm_draft_close";
	
	public static final String MAKER_DRAFT_CLOSE_INSURANCE_COVERAGE_DTLS = "maker_draft_close_process";
	
	public static final String MAKER_CONFIRM_RESUBMIT_DRAFT = "maker_confirm_resubmit_draft";
	
	public static final String MAKER_SAVE_CREATE_INSURANCE_COVERAGE_DTLS = "maker_save_create_insurance_coverage_dtls";
	
	public static final String MAKER_SAVE_UPDATE_INSURANCE_COVERAGE_DTLS = "maker_save_update_insurance_coverage_dtls";
	
	public static final String MAKER_SAVE_PROCESS = "maker_save_process";
	
	public static final String MAKER_SAVE_UPDATE_PROCESS = "maker_update_save_process";
	
	public static final String MAKER_CONFIRM_RESUBMIT_UPDATE="maker_confirm_resubmit_update";
	
	private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }
    
	protected ICommand[] getCommandChain(String event) 
	{
		ICommand[] objArray = null;
		
			if(event!=null){
			
			if(LIST_INSURANCE_COVERAGE_DTLS.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("ListInsuranceCoverageDtlsCmd"); 
			}
			else if(PREPARE_CREATE_INSURANCE_COVERAGE_DTLS.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("PrepareCreateInsuranceCoverageDtlsCmd");
			}
			else if(MAKER_SUBMIT_CREATE_INSURANCE_COVERAGE_DTLS.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitCreateInsuranceCoverageDtlsCommand");
			}
			else if (VIEW_INSURANCE_COVERAGE_DTLS_BY_INDEX.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("ViewInsuranceCoverageDtlsByIndexCommand");
			}
			else if (PREPARE_DELETE_INSURANCE_COVERAGE_DTLS.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("PrepareDeleteInsuranceCoverageDtlsCommand");
			}
			else if (MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE_DTLS.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitDeleteInsuranceCoverageDtlsCommand");
			}
			else if (PREPARE_EDIT_INSURANCE_COVERAGE_DTLS.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("PrepareEditInsuranceCoverageDtlsCommand");
			}
			else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)) || MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE_DTLS.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitEditInsuranceCoverageDtlsCommand");
			}
			else if ((event.equals(CHECKER_EDIT_READ)||event.equals(REJECTED_DELETE_READ))||event.equals(CHECKER_VIEW_INSURANCE_COVERAGE_DTLS) 
					|| event.equals(CHECKER_PROCESS_CREATE) || event.equals(CHECKER_PROCESS_DELETE) 
					|| event.equals(MAKER_PREPARE_CLOSE) || event.equals(MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE_DTLS) 
					|| event.equals(CHECKER_PROCESS_EDIT)|| event.equals(MAKER_SAVE_UPDATE_PROCESS) || event.equals(MAKER_SAVE_PROCESS)
					|| event.equals(MAKER_DRAFT_CLOSE_INSURANCE_COVERAGE_DTLS)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadInsuranceCoverageDtlsCmd");
			}
			else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditInsuranceCoverageDtlsCmd");
			}
			else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditInsuranceCoverageDtlsCmd");
			}
			else if ((event != null) && event.equals(MAKER_EDIT_INSURANCE_COVERAGE_DTLS_READ) ) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerReadInsuranceCoverageDtlsCmd");
			}
			else if (event.equals(MAKER_CONFIRM_CLOSE)) {
	            objArray = new ICommand[1];
	            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseInsuranceCoverageDtlsCmd");
	        }
			
			else if (event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
	            objArray = new ICommand[1];
	            objArray[0] = (ICommand) getNameCommandMap().get("MakerDraftCloseInsuranceCoverageDtlsCmd");
	        }
			else if (event.equals(MAKER_CONFIRM_RESUBMIT_DRAFT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitDraftInsuranceCoverageDtlsCommand");
			}
			else if(MAKER_SAVE_CREATE_INSURANCE_COVERAGE_DTLS.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("SaveCreateInsuranceCoverageDtlsCommand");
			}
			else if(MAKER_SAVE_UPDATE_INSURANCE_COVERAGE_DTLS.equals(event) || MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitEditInsuranceCoverageDtlsCommand");
			}
		}else{
			throw new InsuranceCoverageDtlsException("Action Event is null");
		}
		return objArray;
	}
	protected IPage getNextPage(String event, HashMap resultMap,HashMap exceptionMap) {
		
		Page page = new Page();
		
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			page.setPageReference(WORK_IN_PROGRESS_PAGE);
			return page;
		}
		
		if(LIST_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = LIST_INSURANCE_COVERAGE_DTLS;
        }
		else if(PREPARE_CREATE_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = PREPARE_CREATE_INSURANCE_COVERAGE_DTLS;
        }
		if(VIEW_INSURANCE_COVERAGE_DTLS_BY_INDEX.equals(event))
        {
        	event = VIEW_INSURANCE_COVERAGE_DTLS_BY_INDEX;
        }
		if(PREPARE_DELETE_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = PREPARE_DELETE_INSURANCE_COVERAGE_DTLS;
        }
		if(MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE_DTLS;
        }
		if(PREPARE_EDIT_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = PREPARE_EDIT_INSURANCE_COVERAGE_DTLS;
        }
		if(MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE_DTLS;
        }
		if(CHECKER_EDIT_READ.equals(event))
        {
        	event = CHECKER_INSURANCE_COVERAGE_DTLS_PAGE;
        }
		if(CHECKER_PROCESS_CREATE.equals(event))
        {
        	event = CHECKER_INSURANCE_COVERAGE_DTLS_PAGE;
        }
		if(CHECKER_PROCESS_DELETE.equals(event))
        {
        	event = CHECKER_INSURANCE_COVERAGE_DTLS_PAGE;
        }
        if(CHECKER_APPROVE_EDIT.equals(event))
        {
        	event = COMMON_APPROVE_PAGE;
        }
        if(REJECTED_DELETE_READ.equals(event))
        {
        	event = MAKER_VIEW_TODO_PAGE;
        }
        if(MAKER_PREPARE_CLOSE.equals(event))
        {
        	event = MAKER_PREPARE_CLOSE;
        }
        if(MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = MAKER_PREPARE_RESUBMIT;
        }
        if(MAKER_CONFIRM_RESUBMIT_EDIT.equals(event))
        {
        	event = COMMON_SUBMIT_PAGE;
        }
        if(CHECKER_PROCESS_EDIT.equals(event))
        {
        	event = CHECKER_INSURANCE_COVERAGE_DTLS_PAGE;
        }
        
		
        if(MAKER_SAVE_PROCESS.equals(event))
        {
        	event = MAKER_SAVE_PROCESS;
        }
        if(MAKER_SAVE_UPDATE_PROCESS.equals(event))
        {
        	event = MAKER_SAVE_UPDATE_PROCESS;
        }
        if(MAKER_DRAFT_CLOSE_INSURANCE_COVERAGE_DTLS.equals(event))
        {
        	event = MAKER_PREPARE_CLOSE;
        }
        if(MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event))
        {
        	event = COMMON_SUBMIT_PAGE;
        }
        if(MAKER_SAVE_UPDATE_INSURANCE_COVERAGE_DTLS.equals(event)){
        	event = MAKER_SAVE_UPDATE_INSURANCE_COVERAGE_DTLS;
        }
        if(MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event))
        {
        	event = COMMON_SUBMIT_PAGE;
        }
        
		page.setPageReference(event);
		return page;
	}
	
	protected boolean isValidationRequired(String event)
    {
		boolean result = false;
		
		if(MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE_DTLS.equals(event) || SUBMIT_CREATE_INSURANCE_COVERAGE_DTLS.equals(event) 
				|| MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event) || MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event))
		{
			result = true;
		}
		return result;
    }
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
    {
    	return InsuranceCoverageDtlsValidator.validateInsuranceCoverageDtlsForm((InsuranceCoverageDtlsForm)aForm, locale); 
    }
    
    protected String getErrorEvent(String event)
    {
    	String errorEvent = "";
        
		if(MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE_DTLS.equals(event) || MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE_DTLS.equals(event))
		{
			errorEvent = PREPARE_EDIT_INSURANCE_COVERAGE_DTLS;
		}
		else if(SUBMIT_CREATE_INSURANCE_COVERAGE_DTLS.equals(event))
		{
			errorEvent = PREPARE_CREATE_INSURANCE_COVERAGE_DTLS;
		}
		else if(MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event))
		{
			errorEvent = MAKER_SAVE_PROCESS;
		}
		else if(MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event))
		{
			errorEvent = MAKER_SAVE_UPDATE_PROCESS;
		}
        return errorEvent;
	}	
}
