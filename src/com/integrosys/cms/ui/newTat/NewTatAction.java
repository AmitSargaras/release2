/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.newTat;

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


/**
 * @author $Author: Abhijit Rudrakshawar$ Action For New Tat
 * Dated : 19-Sep-2013
 */
public class NewTatAction extends CommonAction implements IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String LIST_NEW_TAT = "list";
	public static final String LIST_NEW_TAT_OK = "list_ok";
	public static final String MAKER_LIST_TAT = "maker_list_tat";
	public static final String PAGINATE = "pagination";
	public static final String INITIATE_NEW_TAT = "initiate_new_tat";
	public static final String LIST_CUSTOMER = "list_customer";
	public static final String SUBMIT_DOCUMENTS = "submit_documents";
	public static final String PREPARE_SUBMIT_DOCUMENTS = "prepare_submit_documents";
	public static final String CONFIRM_SUBMIT_DOCUMENTS = "confirm_submit_documents";
	public static final String PREPARE_DEFERRAL_APPROVE = "prepare_deferral_approve";
	public static final String PREPARE_DEFERRAL_CLEARANCE = "prepare_deferral_clearance";
	public static final String DEFERRAL_APPROVE = "deferral_approve";
    public static final String DEFERRAL_CLEARANCE = "deferral_clearance";
    public static final String PREPARE_DOCUMENT_RECEIVE = "prepare_document_receive";
    public static final String PREPARE_DOCUMENT_SCAN = "prepare_document_scan";
    public static final String SHOW_AUDIT_TRAIL = "show_audit_trail";
    public static final String SHOW_CASE_RM = "show_case_rm";
    public static final String SHOW_CASE_BRANCH = "show_case_branch";
    public static final String SHOW_CASE_CPU = "show_case_cpu";
    public static final String REFRESH_FACILITY_NAME = "refresh_facility_Name";
	 public static final String CONFIRM_DOCUMENT_RECEIVE = "confirm_document_receive";
    public static final String CONFIRM_DOCUMENT_SCAN = "confirm_document_scan";
    public static final String PREPARE_LIMIT_RELEASE = "prepare_limit_release";
    public static final String CONFIRM_LIMIT_RELEASE = "confirm_limit_release";
    public static final String CONFIRM_LIMIT_RELEASE_ERROR = "confirm_limit_release_error";
    public static final String PREPARE_DEFERRAL_RAISED = "prepare_deferral_raised";
    public static final String CONFIRM_DEFERRAL_RAISED = "confirm_deferral_raised";
    public static final String PREPARE_CLIMS_UPDATED = "prepare_clims_updated";
    public static final String CONFIRM_CLIMS_UPDATED = "confirm_clims_updated";
    public static final String REFRESH_FACILITY_DETAILS = "refresh_facility_details";
    public static final String CLOSE_CASE = "close_case";
	
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this,"In Maintain TAT Action with event --" + event);

		ICommand objArray[] = null;
		if (LIST_NEW_TAT.equals(event) || SUBMIT_DOCUMENTS.equals(event) || MAKER_LIST_TAT.equals(event) || PAGINATE.equals(event) || "list_cancel".equals(event) || "list_ok".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListNewTatCmd();
			objArray[1] = new PrepareValuesCommand();

		} else if (LIST_CUSTOMER.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new NewTatListCustomerCommand();
			objArray[1] = new PrepareValuesCommand();
		} 
	 else if (SHOW_CASE_RM.equals(event) || SHOW_CASE_BRANCH.equals(event) || SHOW_CASE_CPU.equals(event)) {
		objArray = new ICommand[1];
		objArray[0] = new ShowCaseDetailsCommand();
	} 
		else if (PREPARE_SUBMIT_DOCUMENTS.equals(event)
				|| PREPARE_DEFERRAL_APPROVE.equals(event)
				|| PREPARE_DEFERRAL_CLEARANCE.equals(event)
				|| PREPARE_DOCUMENT_RECEIVE.equals(event)
				|| PREPARE_DOCUMENT_SCAN.equals(event)
				|| PREPARE_CLIMS_UPDATED.equals(event)
				|| PREPARE_DEFERRAL_RAISED.equals(event)
				|| PREPARE_LIMIT_RELEASE.equals(event)
		) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareValuesCommand();
		} 
		else if (CONFIRM_SUBMIT_DOCUMENTS.equals(event) 
				|| DEFERRAL_APPROVE.equals(event) 
				|| DEFERRAL_CLEARANCE.equals(event)
				|| CONFIRM_DOCUMENT_RECEIVE.equals(event)
				|| CONFIRM_DOCUMENT_SCAN.equals(event)
				|| CONFIRM_CLIMS_UPDATED.equals(event)
				|| CONFIRM_DEFERRAL_RAISED.equals(event)
				|| CONFIRM_LIMIT_RELEASE.equals(event)
				|| CLOSE_CASE.equals(event)
		) {
			objArray = new ICommand[1];
		objArray[0] = new SubmitDocumentsCommand();
		} 
		else if (SHOW_AUDIT_TRAIL.equals(event) ) {
			objArray = new ICommand[1];
		objArray[0] = new ShowAuditTrailDetailsCommand();
		} 
		else if (REFRESH_FACILITY_NAME.equals(event)) {
			objArray = new ICommand[1];
		objArray[0] = new RefreshFacilityNameCommmand();
		}else if (REFRESH_FACILITY_DETAILS.equals(event)){
			objArray = new ICommand[1];
			objArray[0]	= new RefreshLmtFacDetailCmd();
		}else if (CONFIRM_LIMIT_RELEASE_ERROR.equals(event)
		){
			objArray = new ICommand[1];
			objArray[0]	= new RefreshLmtFacErrDetailCmd();
		}
		return (objArray);
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
	public ActionErrors validateInput(ActionForm aForm, Locale locale)  {
		return NewTatValidator.validateInput(aForm, locale);
		
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (CONFIRM_SUBMIT_DOCUMENTS.equals(event) 
				|| DEFERRAL_APPROVE.equals(event) 
				|| DEFERRAL_CLEARANCE.equals(event)
				|| CONFIRM_DOCUMENT_RECEIVE.equals(event)
				|| CONFIRM_DOCUMENT_SCAN.equals(event)
				|| CONFIRM_CLIMS_UPDATED.equals(event)
				|| CONFIRM_DEFERRAL_RAISED.equals(event)
				|| CONFIRM_LIMIT_RELEASE.equals(event)
					//|| CLOSE_CASE.equals(event)
		)

		{
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event
	 *            is of type String
	 * @param resultMap
	 *            is of type HashMap
	 * @param exceptionMap
	 *            is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());
		
		if ((resultMap.get("party_closed") != null)
				&& (resultMap.get("party_closed")).equals("party_closed")) {
			aPage.setPageReference("party_closed");
			return aPage;
		}else
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		} else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
		
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (CONFIRM_SUBMIT_DOCUMENTS.equals(event)) {
			errorEvent = "confirm_submit_documents_error";
		}else if (CONFIRM_LIMIT_RELEASE.equals(event)) {
			errorEvent = "confirm_limit_release_error";
		}
		else if (DEFERRAL_APPROVE.equals(event)) {
			errorEvent = PREPARE_DEFERRAL_APPROVE;
		}else if (DEFERRAL_CLEARANCE.equals(event)) {
			errorEvent = PREPARE_DEFERRAL_CLEARANCE;
		}else if (CONFIRM_DOCUMENT_RECEIVE.equals(event)) {
			errorEvent = PREPARE_DOCUMENT_RECEIVE;
		}else if (CONFIRM_DOCUMENT_SCAN.equals(event)) {
			errorEvent = PREPARE_DOCUMENT_SCAN;
		}else if (CONFIRM_CLIMS_UPDATED.equals(event)) {
			errorEvent = PREPARE_CLIMS_UPDATED;
		}else if (CONFIRM_DEFERRAL_RAISED.equals(event)) {
			errorEvent = PREPARE_DEFERRAL_RAISED;
		}
		//else if (CLOSE_CASE.equals(event)) {
		//	errorEvent = PREPARE_DOCUMENT_RECEIVE;
		//}
		
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		forwardName=event;
		return forwardName;
	}

}