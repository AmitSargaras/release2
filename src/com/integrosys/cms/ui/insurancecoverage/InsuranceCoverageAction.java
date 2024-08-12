package com.integrosys.cms.ui.insurancecoverage;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;

/**
 * Describe this class
 * Purpose : Insurance Coverage Actions
 * Description : Handling Actions of Insurance Coverage
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1 $
 * Tag : $Name$
 */

public class InsuranceCoverageAction extends CommonAction{
	
	public static final String LIST_INSURANCE_COVERAGE = "list_insurance_coverage";
	
	public static final String PREPARE_CREATE_INSURANCE_COVERAGE = "prepare_create_insurance_coverage";
	
	public static final String MAKER_SUBMIT_CREATE_INSURANCE_COVERAGE = "maker_submit_create_insurance_coverage";
	
	public static final String SUBMIT_CREATE_INSURANCE_COVERAGE = "maker_submit_create_insurance_coverage";
	
	public static final String VIEW_INSURANCE_COVERAGE_BY_INDEX = "view_insurance_coverage_by_index";
	
	public static final String PREPARE_EDIT_INSURANCE_COVERAGE = "prepare_maker_submit_edit";
	
	public static final String MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE = "maker_submit_edit";
	
	public static final String PREPARE_DELETE_INSURANCE_COVERAGE = "prepare_maker_submit_remove";
	
	public static final String MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE = "maker_submit_remove";
	
	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";
	
	public static final String CHECKER_LIST_INSURANCE_COVERAGE="checker_list_insurance_coverage";
	
	public static final String CHECKER_VIEW_INSURANCE_COVERAGE="checker_view_insurance_coverage";

	public static final String MAKER_CONFIRM_RESUBMIT_EDIT="maker_confirm_resubmit_edit";

	public static final String CHECKER_EDIT_READ="checker_edit_read";

	public static final String REJECTED_DELETE_READ="rejected_delete_read";
	
	public static final String CHECKER_APPROVE_EDIT="checker_approve_edit";
	
	public static final String CHECKER_REJECT_EDIT="checker_reject_edit";
	
	public static final String MAKER_PREPARE_CLOSE="maker_prepare_close";
	
	public static final String MAKER_EDIT_INSURANCE_COVERAGE_READ="maker_edit_insurance_coverage_read";
	
	public static final String MAKER_PREPARE_RESUBMIT="maker_prepare_resubmit";
	
	public static final String MAKER_CONFIRM_CLOSE="maker_confirm_close";
	
	public static final String REDIRECT_LIST_INSURANCE_COVERAGE="redirect_list_insurance_coverage";
	
	public static final String MAKER_DELETE_INSURANCE_COVERAGE_READ="maker_delete_insurance_coverage_read";
	
	public static final String MAKER_EDIT_REJECT_EDIT="maker_edit_reject_edit";
	
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	
	public static final String LIST_PAGINATION="list_pagination";
	
	public static final String MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE = "maker_prepare_resubmit_create";
	
	public static final String CHECKER_INSURANCE_COVERAGE_PAGE = "checker_insurance_coverage_page";
	
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
	
	public static final String MAKER_DRAFT_CLOSE_INSURANCE_COVERAGE = "maker_draft_close_process";
	
	public static final String MAKER_CONFIRM_RESUBMIT_DRAFT = "maker_confirm_resubmit_draft";
	
	public static final String MAKER_SAVE_CREATE_INSURANCE_COVERAGE = "maker_save_create_insurance_coverage";
	
	public static final String MAKER_SAVE_UPDATE_INSURANCE_COVERAGE = "maker_save_update_insurance_coverage";
	
	public static final String MAKER_SAVE_PROCESS = "maker_save_process";
	
	public static final String MAKER_SAVE_UPDATE_PROCESS = "maker_update_save_process";
	
	public static final String MAKER_CONFIRM_RESUBMIT_UPDATE="maker_confirm_resubmit_update";
	
	
	public static final String MAKER_PREPARE_UPLOAD_INSURANCE_COVERAGE = "maker_prepare_upload_insurance_coverage";

	public static final String MAKER_UPLOAD_INSURANCE_COVERAGE = "maker_upload_insurance_coverage";
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	
	public static final String EVENT_PAGINATE = "paginate";
	
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
			
			if(LIST_INSURANCE_COVERAGE.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("ListInsuranceCoverageCmd"); 
			}
			else if(PREPARE_CREATE_INSURANCE_COVERAGE.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("PrepareCreateInsuranceCoverageCmd");
			}
			else if(MAKER_SUBMIT_CREATE_INSURANCE_COVERAGE.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitCreateInsuranceCoverageCommand");
			}
			else if (VIEW_INSURANCE_COVERAGE_BY_INDEX.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("ViewInsuranceCoverageByIndexCommand");
			}
			else if (PREPARE_DELETE_INSURANCE_COVERAGE.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("PrepareDeleteInsuranceCoverageCommand");
			}
			else if (MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitDeleteInsuranceCoverageCommand");
			}
			else if (PREPARE_EDIT_INSURANCE_COVERAGE.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("PrepareEditInsuranceCoverageCommand");
			}
			else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)) || MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitEditInsuranceCoverageCommand");
			}
			else if ((event.equals(CHECKER_EDIT_READ)||event.equals(REJECTED_DELETE_READ))||event.equals(CHECKER_VIEW_INSURANCE_COVERAGE) 
					|| event.equals(CHECKER_PROCESS_CREATE) || event.equals(CHECKER_PROCESS_DELETE) 
					|| event.equals(MAKER_PREPARE_CLOSE) || event.equals(MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE) 
					|| event.equals(CHECKER_PROCESS_EDIT ) || event.equals(MAKER_SAVE_UPDATE_PROCESS) 
					|| event.equals(MAKER_SAVE_PROCESS) || event.equals(MAKER_DRAFT_CLOSE_INSURANCE_COVERAGE)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadInsuranceCoverageCmd");
			}
			else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditInsuranceCoverageCmd");
			}
			else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditInsuranceCoverageCmd");
			}
			else if ((event != null) && event.equals(MAKER_EDIT_INSURANCE_COVERAGE_READ) ) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerReadInsuranceCoverageCmd");
			}
			else if (event.equals(MAKER_CONFIRM_CLOSE)) {
	            objArray = new ICommand[1];
	            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseInsuranceCoverageCmd");
	        }
			
			else if (event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
	            objArray = new ICommand[1];
	            objArray[0] = (ICommand) getNameCommandMap().get("MakerDraftCloseInsuranceCoverageCmd");
	        }
			else if (event.equals(MAKER_CONFIRM_RESUBMIT_DRAFT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitDraftInsuranceCoverageCommand");
			}
			else if(MAKER_SAVE_CREATE_INSURANCE_COVERAGE.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("SaveCreateInsuranceCoverageCommand");
			}
			else if(MAKER_SAVE_UPDATE_INSURANCE_COVERAGE.equals(event) || MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)){
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("MakerSubmitEditInsuranceCoverageCommand");
			}
			
			else if (event.equals(MAKER_UPLOAD_INSURANCE_COVERAGE)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerUploadInsuranceCoverageCmd");
			} else if ((event != null)
					&& (event.equals(MAKER_REJECTED_DELETE_READ)
							|| event.equals(CHECKER_PROCESS_CREATE_INSERT) || event
							.equals(MAKER_PREPARE_INSERT_CLOSE))) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerReadFileInsertListCmd");

			} else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerApproveInsertInsuranceCoverageCmd");
			} else if (event.equals(CHECKER_REJECT_INSERT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerRejectInsertInsuranceCoverageCmd");
			} else if ((event != null)
					&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerInsertCloseInsuranceCoverageCmd");
			}
			
			else if ( EVENT_PAGINATE.equals(event) ) {
				return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateInsuranceCoverageListCommand") };
			}
			
		}else{
			throw new InsuranceCoverageException("Action Event is null");
		}
		return objArray;
	}
	protected IPage getNextPage(String event, HashMap resultMap,HashMap exceptionMap) {
		
		Page page = new Page();
		
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			page.setPageReference(WORK_IN_PROGRESS_PAGE);
			return page;
		}else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
			page.setPageReference("maker_fileupload_insurance_coverage_page");
			return page;
		}else
		
		if(LIST_INSURANCE_COVERAGE.equals(event) || event.equals("listError") || event.equals(EVENT_PAGINATE) )
        {
        	event = LIST_INSURANCE_COVERAGE;
        }
		else if(PREPARE_CREATE_INSURANCE_COVERAGE.equals(event))
        {
        	event = PREPARE_CREATE_INSURANCE_COVERAGE;
        }
		if(VIEW_INSURANCE_COVERAGE_BY_INDEX.equals(event))
        {
        	event = VIEW_INSURANCE_COVERAGE_BY_INDEX;
        }
		if(PREPARE_DELETE_INSURANCE_COVERAGE.equals(event))
        {
        	event = PREPARE_DELETE_INSURANCE_COVERAGE;
        }
		if(MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE.equals(event))
        {
        	event = MAKER_SUBMIT_DELETE_INSURANCE_COVERAGE;
        }
		if(PREPARE_EDIT_INSURANCE_COVERAGE.equals(event) || event.equals("editError"))
        {
        	event = PREPARE_EDIT_INSURANCE_COVERAGE;
        }
		if(MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE.equals(event))
        {
        	event = MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE;
        }
		if(CHECKER_EDIT_READ.equals(event))
        {
        	event = CHECKER_INSURANCE_COVERAGE_PAGE;
        }
		if(CHECKER_PROCESS_CREATE.equals(event))
        {
        	event = CHECKER_PROCESS_CREATE;
        }
		if(CHECKER_PROCESS_DELETE.equals(event))
        {
        	event = CHECKER_INSURANCE_COVERAGE_PAGE;
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
        if(MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE.equals(event) || event.equals("resubmitError"))
        {
        	event = MAKER_PREPARE_RESUBMIT;
        }
        if(MAKER_CONFIRM_RESUBMIT_EDIT.equals(event))
        {
        	event = COMMON_SUBMIT_PAGE;
        }
        if(CHECKER_PROCESS_EDIT.equals(event))
        {
        	event = CHECKER_INSURANCE_COVERAGE_PAGE;
        }
        if(MAKER_SAVE_PROCESS.equals(event) || event.equals("draftError") )
        {
        	event = MAKER_SAVE_PROCESS;
        }
        if(MAKER_SAVE_UPDATE_PROCESS.equals(event))
        {
        	event = MAKER_SAVE_UPDATE_PROCESS;
        }
        if(MAKER_DRAFT_CLOSE_INSURANCE_COVERAGE.equals(event))
        {
        	event = MAKER_PREPARE_CLOSE;
        }
        if(MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event))
        {
        	event = COMMON_SUBMIT_PAGE;
        }
        if(MAKER_SAVE_UPDATE_INSURANCE_COVERAGE.equals(event)){
        	event = MAKER_SAVE_UPDATE_INSURANCE_COVERAGE;
        }
        if(MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event))
        {
        	event = COMMON_SUBMIT_PAGE;
        }
        
        if (MAKER_PREPARE_UPLOAD_INSURANCE_COVERAGE.equals(event)) {
			event = MAKER_PREPARE_UPLOAD_INSURANCE_COVERAGE;
		}
		if (MAKER_UPLOAD_INSURANCE_COVERAGE.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		} else if ((event != null) && event.equals(CHECKER_REJECT_INSERT)) {
			event = "common_reject_page";
		} else if (event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			event = "checker_insurance_coverage_insert_page";
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			event = COMMON_CLOSE_PAGE;
		} else if ((event != null)
				&& (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			event = MAKER_PREPARE_INSERT_CLOSE;
		} 
		else if (event != null && event.equals(MAKER_REJECTED_DELETE_READ)) {
			event = "maker_view_insert_todo_page";
		}
		else if(MAKER_UPLOAD_INSURANCE_COVERAGE.equals(event)){
			event = MAKER_PREPARE_UPLOAD_INSURANCE_COVERAGE;
		}
		else if(CHECKER_APPROVE_INSERT.equals(event)){
			event = COMMON_SUBMIT_PAGE;
		}
		
		else if(PREPARE_EDIT_INSURANCE_COVERAGE.equals(event) || event.equals("editSaveError") ){
			event = PREPARE_EDIT_INSURANCE_COVERAGE;
		}
		page.setPageReference(event);
		return page;
	}
	
	protected boolean isValidationRequired(String event)
    {
		boolean result = false;
		
		if(MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE.equals(event) || SUBMIT_CREATE_INSURANCE_COVERAGE.equals(event) 
				|| MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event) || MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)
				|| MAKER_SAVE_CREATE_INSURANCE_COVERAGE.equals(event))
		{
			result = true;
		}
		return result;
    }
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
    {
    	return InsuranceCoverageValidator.validateInsuranceCoverageForm((InsuranceCoverageForm)aForm, locale); 
    }
    
    protected String getErrorEvent(String event)
    {
    	String errorEvent = "";
        
		if(MAKER_SUBMIT_EDIT_INSURANCE_COVERAGE.equals(event) || MAKER_RESUBMIT_CREATE_INSURANCE_COVERAGE.equals(event))
		{
//			errorEvent = PREPARE_EDIT_INSURANCE_COVERAGE;
			errorEvent = "editError";
		}
		else if(SUBMIT_CREATE_INSURANCE_COVERAGE.equals(event) || MAKER_SAVE_CREATE_INSURANCE_COVERAGE.equals(event))
		{
			errorEvent = PREPARE_CREATE_INSURANCE_COVERAGE;
		}
		else if(MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event))
		{
			errorEvent = "draftError";
		}
		else if(MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event))
		{
			errorEvent = MAKER_SAVE_UPDATE_PROCESS;
		}
		else if(CHECKER_REJECT_EDIT.equals(event))
		{
			errorEvent = CHECKER_PROCESS_CREATE;
		}
		else if(CHECKER_REJECT_INSERT.equals(event))
		{
			errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		}
		else if (LIST_INSURANCE_COVERAGE.equals(event))
			errorEvent = "listError";
		
		else if (CHECKER_LIST_INSURANCE_COVERAGE.equals(event))
			errorEvent = "checkerListError";
		
		else if (MAKER_CONFIRM_RESUBMIT_EDIT.equals(event))
			errorEvent = "resubmitError";
		
		else if (MAKER_SAVE_UPDATE_INSURANCE_COVERAGE.equals(event))
			errorEvent = "editSaveError";
		
        return errorEvent;
	}	
}
