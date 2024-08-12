/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.poi.report;

import java.util.Arrays;
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
 * @author $Author: Anil Pandey 
 */
public class ReportAction extends CommonAction implements IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";

 
	public static final String EVENT_LIST_REPORT = "list_report";
	public static final String EVENT_GENERATE_REPORT = "generate_report";
	public static final String EVENT_GENERATE_REPORT_SMS_UPLOAD = "generate_report_sms_upload";
	public static final String EVENT_PREPARE_FILTER = "prepare_filter";
	public static final String REFRESH_RM_ID = "refresh_rm_id";
	
	public static final String REFRESH_FACILITY = "refresh_facility";

	public static final String EVENT_GENERATE_REPORT_SPE = "generate_report_spe";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ((event.startsWith(EVENT_GENERATE_REPORT)) ||  (event.startsWith(EVENT_GENERATE_REPORT_SPE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("GenerateReportCmd");
		}else if(event.equals(EVENT_PREPARE_FILTER)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PrepareReportFilterCmd");
			//party-----------------------
		}else if ((event.equals("list"))) {
			objArray = new ICommand[1];
			objArray[0] = new ListCustomerCommand();
		}else if ((event.equals("submit"))) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCustomerCommand();
			//party-----------------------
			//guarantor-----------------------
		}else if ((event.equals("list_guarantor"))) {
			objArray = new ICommand[1];
			objArray[0] = new ListGuarantorCommand();
		}else if ((event.equals("select_guarantor"))) {
			objArray = new ICommand[1];
			objArray[0] = new SelectGuarantorCommand();
			//guarantor-----------------------
			//branch-----------------------
		}else if (event.equals("list_branch")||event.equals("paginate_branch_list")) {
			objArray = new ICommand[1];
			objArray[0] =(ICommand) getNameCommandMap().get("ListSystemBankBranchRPTCmd");;
		}else if ((event.equals("select_branch"))) {
			objArray = new ICommand[1];
			objArray[0] = new SelectBranchForReportCommand();
			//branch-----------------------
			//user-----------------------
		}else if ((event.equals("list_user"))) {
			objArray = new ICommand[1];
			objArray[0] = new ListUserCommand();
		}else if ((event.equals("select_user"))) {
			objArray = new ICommand[1];
			objArray[0] = new SelectUserForReportCommand();
			//user-----------------------
		}else if ((event.equals("downloadReport"))) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadReportCmd();
		}else if ((event.equals("cancleFilter"))) {
			objArray = new ICommand[1];
			objArray[0] = new CancleFilterCmd();
		}else if ((event.startsWith(EVENT_GENERATE_REPORT_SMS_UPLOAD))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("GenerateReportCmd");
		} else if (event.equals(REFRESH_RM_ID)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshRelationshipMgrCommand();
		} else if (event.equals(REFRESH_FACILITY)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshFacility();
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
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return null;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		/*if (event.equals(EVENT_GENERATE_REPORT))
		{	
			result = true;
		}*/
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
		if(event.equalsIgnoreCase(EVENT_GENERATE_REPORT)){
			aPage.setPageReference(getReference("generate_report"));
		}else if(event.equalsIgnoreCase(EVENT_GENERATE_REPORT_SMS_UPLOAD)){
			aPage.setPageReference(getReference("generate_report_sms"));
		}else if(event.equalsIgnoreCase(REFRESH_RM_ID)){
			aPage.setPageReference(getReference(REFRESH_RM_ID));
		}else if(event.equalsIgnoreCase(EVENT_GENERATE_REPORT_SPE)) {
			aPage.setPageReference(getReference("generate_report"));
		}else{
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if ("list".equals(event)||
			"list_guarantor".equals(event)||
			"list_branch".equals(event)||
			"list_user".equals(event)||
			EVENT_GENERATE_REPORT.equals(event)) {
			errorEvent= "cancleFilter";
		}else if(EVENT_GENERATE_REPORT_SPE.equals(event)) {
			errorEvent= "cancleFilter_spe";
		}
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
		if("cancleFilter".equals(event) || "cancleFilter_spe".equals(event)){
			return EVENT_PREPARE_FILTER;
		}
		
		return event;
	}

}