/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.custodian;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/14 02:42:08 $ Tag: $Name: $
 */
public class CPCCustodianAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		// todo map command chains to event properly... and remove the unused
		// events...
		ICommand objArray[] = null;
		if ((event != null) && event.equals("cc_doc_list")) {
			objArray = new ICommand[1];
			objArray[0] = new ListDocByDocTypeCmd();
		}
		else if ((event != null) && event.equals("security_doc_list")) {
			objArray = new ICommand[1];
			objArray[0] = new ListDocByDocTypeCmd();
		}
		else if ((event != null) && event.equals("custodian_doc_list")) {
			objArray = new ICommand[1];
			objArray[0] = new ListDocByDocTypeCmd();
		}
		else if ((event != null) && event.equals("cpc_custodian_doc_list")) {
			objArray = new ICommand[1];
			objArray[0] = new ListDocByDocTypeCmd();
		}
		else if ((event != null) && event.equals("create_custodian_doc_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new CreateCustodianDocMakerCmd();
		}
		else if ((event != null) && event.equals("read_custodian_doc_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("prepare_authz_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("prepare_authz_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("authz_tempuplift_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new AuthzTempUpliftDocMakerCmd();
		}
		else if ((event != null) && event.equals("authz_tempuplift_approve_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new AuthzTempUpliftDocCheckerCmd();
		}
		else if ((event != null) && event.equals("authz_permuplift_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new AuthzPermUpliftDocMakerCmd();
		}
		else if ((event != null) && event.equals("authz_permuplift_approve_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new AuthzPermUpliftDocCheckerCmd();
		}
		else if ((event != null) && event.equals("reject_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCustodianCmd();
		}
		else if ((event != null) && event.equals("edit_reject_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new EditRejectCustodianCmd();
		}
		else if ((event != null) && event.equals("cncl_reject_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new CnclRejectCustodianCmd();
		}
		return (objArray);
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
		DefaultLogger.debug(this, "Inside validate Input child class");
		return null;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CREATE) || event.equals("update_team")
				|| event.equals("add_team") || event.equals("update_attribute")) {
			result = true;
		}
		return result;
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
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("cc_doc_list".equals(event)) {
			errorEvent = "cc_doc_list";
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ((event != null) && "cc_doc_list".equals(event)) {
			forwardName = "cc_doc_list";
		}
		else if ((event != null) && "security_doc_list".equals(event)) {
			forwardName = "security_doc_list";
		}
		else if ((event != null) && "custodian_doc_list".equals(event)) {
			forwardName = "custodian_doc_list";
		}
		else if ((event != null) && "cpc_custodian_doc_list".equals(event)) {
			forwardName = "cpc_custodian_doc_list";
		}
		else if ((event != null) && "prepare_create_custodian_doc_maker".equals(event)) {
			forwardName = "prepare_create_custodian_doc_maker";
		}
		else if ((event != null) && "create_custodian_doc_maker".equals(event)) {
			forwardName = "create_custodian_doc_maker";
		}
		else if ((event != null) && "read_custodian_doc_checker".equals(event)) {
			forwardName = "read_custodian_doc_checker";
		}
		else if ((event != null) && "prepare_authz_custodian_maker".equals(event)) {
			forwardName = "prepare_authz_custodian_maker";
		}
		else if ((event != null) && "prepare_authz_custodian_checker".equals(event)) {
			forwardName = "prepare_authz_custodian_checker";
		}
		else if ((event != null) && "authz_tempuplift_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "authz_tempuplift_approve_custodian_checker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "authz_permuplift_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "authz_tempuplift_approve_custodian_checker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "reject_custodian_checker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "edit_reject_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "cncl_reject_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		return forwardName;
	}
}