package com.integrosys.cms.ui.segmentWiseEmail;

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
import com.integrosys.cms.ui.productMaster.ProductMasterValidator;

public class SegmentWiseEmailAction extends CommonAction implements IPin{

	private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String MAKER_LIST_SEGMENT_WISE_EMAIL= "maker_list_segment_wise_email";
	public static final String CHECKER_LIST_SEGMENT_WISE_EMAIL= "checker_list_segment_wise_email";
	public static final String MAKER_VIEW_SEGMENT_WISE_EMAIL = "maker_view_segment_wise_email";
	public static final String CHECKER_VIEW_SEGMENT_WISE_EMAIL = "checker_view_segment_wise_email";
	public static final String MAKER_PREPARE_CREATE_SEGMENT_WISE_EMAIL= "maker_prepare_create_segment_wise_email";
	public static final String MAKER_CREATE_SEGMENT_WISE_EMAIL = "maker_create_segment_wise_email";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String CHECKER_APPROVE_CREATE = "checker_approve_create";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE = "maker_prepare_resubmit_create";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String MAKER_DELETE_SEGMENT_WISE_EMAIL_READ = "maker_delete_segment_wise_email_read";
	public static final String MAKER_DELETE_SEGMENT_WISE_EMAIL = "maker_delete_segment_wise_email";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_EDIT_SEGMENT_WISE_EMAIL_READ = "maker_edit_segment_wise_email_read";
	public static final String MAKER_EDIT_SEGMENT_WISE_EMAIL = "maker_edit_segment_wise_email";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String MAKER_PREPARE_RESUBMIT_EDIT = "maker_prepare_resubmit_edit";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	

	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In Segement wise Email Action with event --" + event);

		ICommand objArray[] = null;
		if (event.equals(MAKER_LIST_SEGMENT_WISE_EMAIL)|| event.equals(CHECKER_LIST_SEGMENT_WISE_EMAIL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListSegmentWiseEmailCmd");
		}
		else if ((event.equals(CHECKER_VIEW_SEGMENT_WISE_EMAIL))
				|| event.equals(MAKER_VIEW_SEGMENT_WISE_EMAIL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadSegmentWiseEmailCmd");
		}
		else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_SEGMENT_WISE_EMAIL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateSegmentWiseEmailCmd");
		}
		else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_SEGMENT_WISE_EMAIL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateSegmentWiseEmailCmd");
		}  
		else if (event.equals(CHECKER_PROCESS_DELETE)
				||(event.equals(CHECKER_PROCESS_EDIT)) 
				|| event.equals(CHECKER_PROCESS_CREATE)
				||event.equals(MAKER_PREPARE_RESUBMIT_CREATE)
				||event.equals(MAKER_PREPARE_RESUBMIT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadSegmentWiseEmailCmd");
		}
		else if ((event != null) && event.equals(CHECKER_APPROVE_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveCreateSegmentWiseEmailCmd");
		}
		else if ((event.equals(CHECKER_REJECT_CREATE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectCreateSegmentWiseEmailCmd");
		}
		else if (event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				||event.equals(MAKER_PREPARE_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadSegmentWiseEmailCmd");
		}
		else if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseSegmentWiseEmailCmd");
		}
		else if (event.equals(MAKER_DELETE_SEGMENT_WISE_EMAIL_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadSegmentWiseEmailCmd");
		}
		else if (event.equals(MAKER_DELETE_SEGMENT_WISE_EMAIL)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteSegmentWiseEmailCmd");
		}
		else if ((event != null) && event.equals(MAKER_EDIT_SEGMENT_WISE_EMAIL_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadSegmentWiseEmailCmd");
		}
		else if ((event != null) && (event.equals(MAKER_EDIT_SEGMENT_WISE_EMAIL)||
				event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerEditSegmentWiseEmailCmd");
		}
		else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditSegmentWiseEmailCmd");
		}
		else if (event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditSegmentWiseEmailCmd");
		}
		else if (event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerDeleteSegmentWiseEmailCmd");
		}
		
		else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
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
		return ProductMasterValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
	/*	if (event.equals(MAKER_CREATE_PRODUCT_MASTER)
				||event.equals(MAKER_UPDATE_DRAFT_PRODUCT_MASTER)
				||event.equals(CHECKER_APPROVE_EDIT)
				||event.equals(MAKER_DRAFT_PRODUCT_MASTER)
				||event.equals(MAKER_EDIT_PRODUCT_MASTER)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)
				|| event.equals(MAKER_SAVE_UPDATE)
				|| event.equals(MAKER_LIST_PRODUCT_MASTER))
		{
			result = true;
		}*/
		return result;
	}
	protected String getErrorEvent(String event) {
		
		String errorEvent = getDefaultEvent();
		if (event.equals("maker_create_segment_wise_email")|| event.equals("maker_draft_segment_wise_email")) {
			errorEvent = "maker_create_segment_wise_email_error";
		}
		else if (event.equals("checker_reject_create")) {
			errorEvent = "checker_reject_create_error";
		}
		else if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}
		else if (event.equals("checker_approve_edit")) {
			errorEvent = "checker_approve_edit_error";
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
			aPage.setPageReference("checker_segment_wise_email_page");
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}	
	}
	private String getReference(String event) {
		String forwardName = null;
		if (event.equals(MAKER_LIST_SEGMENT_WISE_EMAIL)) {
			forwardName = "list_segment_wise_email_page";
		}
		else if (event.equals(CHECKER_LIST_SEGMENT_WISE_EMAIL)) {
			forwardName = "checker_list_segment_wise_email_page";
		}
		else if (MAKER_PREPARE_CREATE_SEGMENT_WISE_EMAIL.equals(event) 	
				|| event.equals("errorCreateSaving") ) {
			forwardName = MAKER_PREPARE_CREATE_SEGMENT_WISE_EMAIL;
		}
		else if (MAKER_PREPARE_RESUBMIT_CREATE.equals(event)) {
			forwardName = MAKER_PREPARE_RESUBMIT_CREATE;
		}
		else if ((event != null)
				&& event.equals("maker_create_segment_wise_email_error")) {
			forwardName = MAKER_PREPARE_CREATE_SEGMENT_WISE_EMAIL;
		}
		else if (event.equals("maker_create_segment_wise_email")
				||event.equals("maker_confirm_resubmit_create")
				||event.equals("maker_edit_segment_wise_email")
				||event.equals("maker_confirm_resubmit_edit")) {
			forwardName = "common_submit_page";
		}
		else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")
				|| event.equals("checker_approve_edit_error")) {
			forwardName = "checker_segment_wise_email_page";
		}
		else if ((event != null) && (event.equals("checker_approve_create")
				||event.equals("checker_approve_edit"))) {
				forwardName = "common_approve_page";
		} 
		else if ((event != null) && event.equals("checker_reject_create")) {
				forwardName = "common_reject_page";
		}
		else if (event.equals("checker_reject_create_error")) {
			forwardName = "checker_segment_wise_email_page";
		}
		else if ((event.equals(CHECKER_VIEW_SEGMENT_WISE_EMAIL))
				|| event.equals(MAKER_VIEW_SEGMENT_WISE_EMAIL)) {
			forwardName = "maker_view_segment_wise_email_page";
		}
		else if ((event != null) && (event.equals(MAKER_PREPARE_CLOSE))) {
			forwardName = "maker_prepare_close";
		}
		else if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE))) {
			forwardName = "common_close_page";
		}
		else if (event.equals(MAKER_DELETE_SEGMENT_WISE_EMAIL_READ)) {
			forwardName = "maker_delete_segment_wise_email_page";
		}
		else if (event.equals(MAKER_DELETE_SEGMENT_WISE_EMAIL)
				||event.equals("maker_confirm_resubmit_delete")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && (event.equals("maker_edit_segment_wise_email_read")
				||event.equals("maker_edit_segment_wise_email_error"))) {
			forwardName = "maker_edit_segment_wise_email_read_page";
		}
		else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}
		else if (event.equals(MAKER_PREPARE_RESUBMIT_EDIT)) {
			forwardName = "maker_prepare_resubmit_edit_segment_wise_email_page";
		}
		
		
		else if ((event != null) && event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		}
		
		else if ((event != null) && event.equals("maker_confirm_resubmit_delete")) {
			forwardName = "common_submit_page";
		}
		
		else if (event.equals("maker_confirm_resubmit_delete_error")) {
				forwardName = "maker_prepare_resubmit_delete";
		}
		else if (event.equals("list_pagination")) {
			forwardName = "list_product_master_page";
		}
		else if (event.equals("checker_list_pagination")) {
			forwardName = "checker_list_product_master_page";
		}
		
		return forwardName;
	}
}
