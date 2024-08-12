package com.integrosys.cms.ui.creditApproval;

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
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;

/**
 * govind.sahu
 * This action implements ...
 */
public class CreditApprovalAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	private String evt;
	
	private Map nameCommandMap;

	protected Map getNameCommandMap() {
		return this.nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this, "*******" + event + "================");
		

		 if (EVENT_PREPARE_LIST.equals(event)||EVENT_CHECKER_PREPARE_LIST.equals(event)) {
				return new ICommand[] { (ICommand) getNameCommandMap().get("ReadCreditApprovalListCommand") };
			}
		 else if (EVENT_PREPARE.equals(event)) {
			return new ICommand[] {(ICommand) getNameCommandMap().get("PrepareCreditApprovalCommand")};
		}
		else if (EVENT_PREPARE_SUBMIT.equals(event)) {
		return new ICommand[] {(ICommand) getNameCommandMap().get("MakerSubmitCreditApprovalCommand")};
    	}
		else if (MAKER_VIEW_CREDIT_APPROVAL.equals(event)||CHECKER_VIEW_CREDIT_APPROVAL.equals(event)||CHECKER_VIEW_APPROVED.equals(event)||CHECKER_VIEW_REJECTED.equals(event)||EVENT_CLOSE_VIEW.equals(event)||
				CHECKER_VIEW_UPDATE_CREDIT_APPROVAL.equals(event)) {
    		return new ICommand[] {(ICommand) getNameCommandMap().get("MakerReadCreditApprovalCommand")};
    	}
		else if (event.equals(CHECKER_CONFIRM_APPROVE_CREATE)||event.equals(CHECKER_CONFIRM_APPROVE_UPDATE)) {
			return new ICommand[] {(ICommand) getNameCommandMap().get("CheckerApproveCreateCreditApprovalCommand")};
        }
		else if ("event_reject_create".equals(event)||"event_reject_update".equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("RejectCreditApprovalCommand") };
		}
		else if (EVENT_CLOSE.equals(event)||event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CloseCreditApprovalCommand") };
		}
		else if ((event != null) && event.equals(MAKER_VIEW_CREDIT_APPROVAL_READ)
				||event.equals(CHECKER_VIEW_CREDIT_APPROVAL_READ)
				||event.equals(PREPARE_MAKER_SUBMIT_EDIT)
				||event.equals(EVENT_RESUBMIT_EDIT_VIEW)
				||event.equals(MAKER_DELETE_READ)
				||event.equals(MAKER_DRAFT_CLOSE_PROCESS)
				||event.equals(MAKER_UPDATE_SAVE_PROCESS)			
				) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerReadCreditApprovalByIdCommand") };
			
		}
		else if ((event != null) && event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				||event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REJECTED)
				||event.equals(CONFIRM_MAKER_SUBMIT_REMOVE)
				||event.equals(MAKER_SAVE_UPDATE)
				||event.equals(MAKER_SAVE_CREATE)		
		) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerEditCreditApprovalCommand") };
			
		}
		else if (event.equals(MAKER_DRAFT_CREDIT_APPROVAL)||event.equals(MAKER_UPDATE_DRAFT_CREDIT_APPROVAL)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerSaveCreditApprovalCommand") };
		}
		else if (event.equals(MAKER_SEARCH_LIST_CREDIT_APPROVAL)||event.equals(CHECKER_SEARCH_LIST_CREDIT_APPROVAL)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SearchListCreditApprovalCommand") };
		}
		 
		 /**********File Upload****************/
		else if (event.equals(MAKER_PREPARE_UPLOAD_CREDITAPPROVAL)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerPrepareUploadCreditApprovalCommand") };
		}
		else if (event.equals(MAKER_EVENT_UPLOAD_CREDITAPPROVAL)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerUploadCreditApprovalCommand") };
		}
        else if (event.equals(MAKER_REJECTED_DELETE_READ)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerApproveInsertCreditApprovalCommand") };
		}else if ( event.equals(CHECKER_REJECT_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerRejectInsertCreditApprovalCommand") };
		}else if ((event != null) && event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && event.equals(MAKER_PREPARE_INSERT_CLOSE)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CheckerReadFileInsertListCommand") };
		}else if ((event != null) && (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerInsertCloseCreditApprovalCommand") };
		}
		 /************************/

		else if ( EVENT_PAGINATE.equals(event) || event.equals(EVENT_PAGINATE_CHECKER) ) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateCreditApprovalListCommand") };
		}
		 
		if(event==null)
		{
			throw new CreditApprovalException("Event is null or blank");
		}
		return null;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input  class" + ((CreditApprovalForm) aForm));

		return CreditApprovalFormValidator.validateInput((CreditApprovalForm) aForm, locale ,evt);
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {

		String forward = null;
		Page page = new Page();
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			page.setPageReference("wip");
			return page;
		}
		if (EVENT_PREPARE_LIST.equals(event)||EVENT_CHECKER_PREPARE_LIST.equals(event) || event.equals(EVENT_PAGINATE) || event.equals(EVENT_PAGINATE_CHECKER) ) {
			forward = "list";
		}
		else if (EVENT_PREPARE.equals(event)) {
			forward = "prepare";
		}else if (EVENT_PREPARE_SUBMIT.equals(event)) {
		
			forward = "maker_prepare_submit";
		}
		else if (EVENT_CANCEL.equals(event)) {
			forward = "save";
		}
		else if (MAKER_VIEW_CREDIT_APPROVAL.equals(event)) {
			forward =  MAKER_VIEW_CREDIT_APPROVAL;
		}
		else if (CHECKER_VIEW_CREDIT_APPROVAL.equals(event) || event.equals("approvalError") ) {
			forward =  CHECKER_VIEW_CREDIT_APPROVAL;
		}
		else if (CHECKER_VIEW_UPDATE_CREDIT_APPROVAL.equals(event)) {
			forward =  CHECKER_VIEW_UPDATE_CREDIT_APPROVAL;
		}
		else if (event.equals(CHECKER_CONFIRM_APPROVE_CREATE)
				||event.equals(CONFIRM_MAKER_SUBMIT_REMOVE)
				||event.equals(CHECKER_CONFIRM_APPROVE_UPDATE)) {
			forward =  CHECKER_CONFIRM_APPROVE_CREATE;
	    }
		else if (event.equals(CHECKER_VIEW_APPROVED)) {
			forward =  CHECKER_VIEW_APPROVED;
	    }
		else if ("event_reject_create".equals(event)) {
			forward = "event_reject_create";
		}
		else if ("event_reject_update".equals(event)) {
			forward = "event_reject_update";
		}
		else if (CHECKER_VIEW_REJECTED.equals(event)) {
			forward = CHECKER_VIEW_REJECTED;
		}
		else if (EVENT_CLOSE.equals(event)) {
			forward = EVENT_CLOSE;
		}
		else if (EVENT_CLOSE_VIEW.equals(event)) {
			forward = EVENT_CLOSE_VIEW;
		}
		else if ((event != null) && event.equals(MAKER_VIEW_CREDIT_APPROVAL_READ)) {
			forward = MAKER_VIEW_CREDIT_APPROVAL_READ;
		}
		else if ((event != null) && event.equals(CHECKER_VIEW_CREDIT_APPROVAL_READ)) {
			forward = CHECKER_VIEW_CREDIT_APPROVAL_READ;
		}
		
		else if ((event != null) && event.equals(PREPARE_MAKER_SUBMIT_EDIT)) {
			forward = PREPARE_MAKER_SUBMIT_EDIT;
		}
		else if ((event != null) && event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)) {
			forward = MAKER_CONFIRM_RESUBMIT_EDIT;
		}
		else if ((event != null) && event.equals(EVENT_RESUBMIT_EDIT_VIEW))
		{
			forward = EVENT_RESUBMIT_EDIT_VIEW;
		}
		else if ((event != null) && event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REJECTED))
		{
			forward = MAKER_CONFIRM_RESUBMIT_EDIT_REJECTED;
		}
		else if ((event != null) && event.equals(MAKER_DELETE_READ))
		{
			forward = MAKER_DELETE_READ;
		}
		else if ((event != null) && event.equals(MAKER_DRAFT_CREDIT_APPROVAL)
				|| event.equals(MAKER_UPDATE_DRAFT_CREDIT_APPROVAL)
				|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE)
				|| event.equals(MAKER_UPDATE_DRAFT_CREDIT_APPROVAL)
				|| event.equals(MAKER_SAVE_UPDATE)
			    || event.equals(MAKER_SAVE_CREATE))
		{
			forward = "common_submit_page";
		}
		else if ((event != null) && event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			forward = MAKER_PREPARE_CLOSE ;
		}
		else if ((event != null) && event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			forward = EVENT_RESUBMIT_EDIT_VIEW ;
		}
		 else if (event.equals(MAKER_SEARCH_LIST_CREDIT_APPROVAL)||event.equals(CHECKER_SEARCH_LIST_CREDIT_APPROVAL)) {
			 forward = "list";
		}
		
		 else if (event.equals("maker_view_approve_reject_credit_error")) {
			 forward = "maker_view_approve_reject_credit";
		}
		 else if (event.equals("maker_view_approve_reject_update_error")) {
			 forward = "checker_confirm_approve_update";
		}

		 else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
				forward = "maker_fileupload_creditApproval_page";
			}
		 else if (event.equals("maker_event_upload_creditApproval")) {
			 forward = "common_submit_page";
	    }
		
		
		 else if ((event != null) && event.equals("maker_prepare_upload_creditApproval")) {
			 forward = "prepare_upload_creditApproval_page";
			}else if ((event != null) && event.equals("maker_rejected_delete_read")) {
				forward = "maker_view_insert_todo_page";
			}else if ((event != null) && event.equals("checker_approve_insert")) {
				forward = "common_approve_page";
			}else if ((event != null) && event.equals("checker_reject_insert")) {
				forward = "common_reject_page";
			}else if (event.equals("checker_process_create_insert")) {
				forward = "checker_creditApproval_insert_page";
			}else if ((event != null)
					&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
				forward = "common_close_page";
			} else if ((event != null)
					&& (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
				forward = "maker_prepare_insert_close";
			} 		
		
		page.setPageReference(forward);
		return page;
	}

	protected boolean isValidationRequired(String event) {
		evt = event;
		if(EVENT_PREPARE_SUBMIT.equals(event)
				||event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				||event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REJECTED)
				||event.equals("maker_confirm_resubmit_edit")
				||event.equals(MAKER_DRAFT_CREDIT_APPROVAL)
				||event.equals(MAKER_SAVE_CREATE)
				)
			
		{
			return true;
		}
		return false;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = "";
		
		if (EVENT_PREPARE_SUBMIT.equals(event)
				||MAKER_DRAFT_CREDIT_APPROVAL.equals(event)) {
			return EVENT_PREPARE;
		}
		else if (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)) {
			return  PREPARE_MAKER_SUBMIT_EDIT;
		}
		else if (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REJECTED)) {
			return  EVENT_RESUBMIT_EDIT_VIEW;
		}
		else if(event.equals(MAKER_SAVE_CREATE))
		{
			return  "maker_update_save_process";
		}
		else if(event.equals("prepare_maker_submit_edit"))
		{
			return  "maker_update_draft_creditApproval";
		}
		else if(event.equals("event_reject_create"))
		{
			return  "maker_view_approve_reject_credit";
		}
		else if(event.equals("event_reject_update"))
		{
			return  "maker_view_approve_update_credit";
		}
		else if(event.equals("maker_search_list_creditApproval"))
		{
			return  EVENT_PREPARE_LIST;
		}
		else if(event.equals("checker_confirm_approve_create") || event.equals("checker_confirm_approve_update"))
		{
			errorEvent =  "approvalError";
		}else if(event.equals("checker_search_list_creditApproval"))
		{
			errorEvent =  "checker_prepare_list";
		}
		
		return errorEvent;
	}

	
	public static final String EVENT_PREPARE_LIST = "prepare_list";
	
	public static final String EVENT_CHECKER_PREPARE_LIST = "checker_prepare_list";
	
	public static final String EVENT_PREPARE = "prepare";

	public static final String EVENT_SAVE = "save";

	public static final String EVENT_CANCEL = "cancel";
	
	public static final String EVENT_PREPARE_SUBMIT = "maker_prepare_submit";
	
	public static final String MAKER_VIEW_CREDIT_APPROVAL = "maker_view_credit";
	
	public static final String CHECKER_VIEW_CREDIT_APPROVAL = "maker_view_approve_reject_credit";
	
	public static final String CHECKER_VIEW_UPDATE_CREDIT_APPROVAL = "maker_view_approve_update_credit";
	
	public static final String CHECKER_CONFIRM_APPROVE_CREATE="checker_confirm_approve_create";
	
	public static final String CHECKER_CONFIRM_APPROVE_UPDATE="checker_confirm_approve_update";
	
	public static final String CHECKER_VIEW_APPROVED="view";
	
	public static final String CHECKER_VIEW_REJECTED="view_reject";
	
	public static final String EVENT_REJECT = "reject";
	
	public static final String EVENT_CLOSE_VIEW = "view_reject_close";
	
	public static final String EVENT_RESUBMIT_EDIT_VIEW = "view_edit_reject_resubmit";
	
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_REJECTED="maker_confirm_resubmit_edit_rejected";
	
	public static final String MAKER_DELETE_READ="prepare_maker_submit_remove";
	
	public static final String CONFIRM_MAKER_SUBMIT_REMOVE="confirm_maker_submit_remove";
	
	public static final String EVENT_CLOSE = "maker_confirm_close";
	
	public static final String MAKER_VIEW_CREDIT_APPROVAL_READ = "maker_view_creditApproval_read";
	
	public static final String CHECKER_VIEW_CREDIT_APPROVAL_READ = "checker_view_creditApproval_read";
	
	public static final String PREPARE_MAKER_SUBMIT_EDIT="prepare_maker_submit_edit";
	
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT="maker_confirm_resubmit_edit";
	
	public static final String MAKER_SEARCH_LIST_CREDIT_APPROVAL = "maker_search_list_creditApproval";
	
	public static final String CHECKER_SEARCH_LIST_CREDIT_APPROVAL = "checker_search_list_creditApproval";

	public static final String MAKER_DRAFT_CREDIT_APPROVAL ="maker_draft_creditApproval";
	
	public static final String MAKER_SAVE_PROCESS="maker_save_process";
	
	public static final String MAKER_SAVE_CREATE="maker_save_create";
	
	public static final String MAKER_PREPARE_RESUBMIT_CREATE="maker_prepare_resubmit_create";

	public static final String MAKER_UPDATE_DRAFT_CREDIT_APPROVAL="maker_update_draft_creditApproval";
	
	public static final String MAKER_UPDATE_SAVE_PROCESS="maker_update_save_process";
	
	public static final String MAKER_SAVE_UPDATE="maker_save_update";
	
	public static final String MAKER_DRAFT_CLOSE_PROCESS="maker_draft_close_process";
	
	public static final String MAKER_CONFIRM_DRAFT_CLOSE="maker_confirm_draft_close";
	
	public static final String MAKER_PREPARE_CLOSE   = "maker_prepare_close";
	
	public static final String MAKER_PREPARE_UPLOAD_CREDITAPPROVAL = "maker_prepare_upload_creditApproval";
	
	public static final String MAKER_EVENT_UPLOAD_CREDITAPPROVAL = "maker_event_upload_creditApproval";
	

	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_PREPARE_UPLOAD_CREDIT_APPROVAL = "maker_prepare_upload_creditApproval";
	public static final String MAKER_EVENT_UPLOAD_CREDIT_APPROVAL = "maker_event_upload_creditApproval";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	
	public static final String EVENT_PAGINATE = "paginate";
	public static final String EVENT_PAGINATE_CHECKER = "paginateChecker";	
	
}
