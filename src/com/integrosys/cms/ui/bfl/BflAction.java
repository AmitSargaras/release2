/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/bfl/BflAction.java,v 1.11 2006/02/20 09:22:00 pratheepa Exp $
 */

package com.integrosys.cms.ui.bfl;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

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
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/02/20 09:22:00 $ Tag: $Name: $
 */
public class BflAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if ("bfl".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if (event.equals("issue_draft_bfl") || event.equals("view_issue_draft_bfl")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if (event.equals("confirm_issue_draft_bfl")) {
			objArray = new ICommand[1];
			objArray[0] = new ConfirmIssueDraftBflCommand();
		}
		else if (event.equals("send_draft_bfl") || event.equals("view_send_draft_bfl")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if ("confirm_send_draft_bfl".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ConfirmSendDraftBflCommand();
		}
		else if (event.equals("recv_draft_bfl_ack") || event.equals("view_recv_draft_bfl_ack")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if ("confirm_recv_draft_bfl_ack".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ConfirmReceiveDraftBflAckCommand();
		}
		else if (event.equals("issue_clean_type_bfl") || event.equals("view_issue_clean_type_bfl")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if ("confirm_issue_clean_type_bfl".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ConfirmIssueCleanTypeCommand();
		}
		else if (event.equals("special_issue_clean_type_bfl") || event.equals("view_special_issue_clean_type_bfl")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if ("confirm_special_issue_clean_type_bfl".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ConfirmSpecialIssueCleanTypeCommand();
		}
		else if (event.equals("issue_final_bfl") || event.equals("view_issue_final_bfl")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if ("confirm_issue_final_bfl".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ConfirmIssueFinalCommand();
		}
		else if (event.equals("customer_accept_bfl") || event.equals("view_customer_accept_bfl")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if ("confirm_customer_accept_bfl".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ConfirmCustomerAcceptBflCommand();
		}
		else if (event.equals("print_bfl_reminder") || event.equals("view_print_bfl_reminder")) {
			objArray = new ICommand[1];
			objArray[0] = new BflCommand();
		}
		else if ("reminder_bfl".equals(event) || event.equals("confirm_print_bfl_reminder")) {
			objArray = new ICommand[1];
			objArray[0] = new PrintReminderCommand();
		}
		// Added by Pratheepa for CR148
		else if (event.equals("view_bfl")) {
			objArray = new ICommand[2];
			objArray[0] = new BflCommand();
			objArray[1] = new PrintReminderCommand();
		}

		return (objArray);
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class" + aForm.getClass());
		return BflFormValidator.validateInput((BflForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("confirm_issue_draft_bfl")) {
			result = true;
		}
		else if (event.equals("confirm_send_draft_bfl")) {
			result = true;
		}
		else if (event.equals("confirm_recv_draft_bfl_ack")) {
			result = true;
		}
		else if (event.equals("confirm_issue_clean_type_bfl")) {
			result = true;
		}
		else if (event.equals("confirm_special_issue_clean_type_bfl")) {
			result = true;
		}
		else if (event.equals("confirm_issue_final_bfl")) {
			result = true;
		}
		else if (event.equals("confirm_customer_accept_bfl")) {
			result = true;
		}
		else if (event.equals("reminder_bfl")) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();

		if (event.equals("reminder_bfl")) {
			errorEvent = "print_bfl_reminder";
		}
		else if ("confirm_issue_draft_bfl".equals(event)) {
			errorEvent = "issue_draft_bfl";
		}
		else if ("confirm_send_draft_bfl".equals(event)) {
			errorEvent = "send_draft_bfl";
		}
		else if ("confirm_recv_draft_bfl_ack".equals(event)) {
			errorEvent = "recv_draft_bfl_ack";
		}
		else if ("confirm_issue_clean_type_bfl".equals(event)) {
			errorEvent = "issue_clean_type_bfl";
		}
		else if ("confirm_special_issue_clean_type_bfl".equals(event)) {
			errorEvent = "special_issue_clean_type_bfl";
		}
		else if ("confirm_issue_final_bfl".equals(event)) {
			errorEvent = "issue_final_bfl";
		}
		else if ("confirm_customer_accept_bfl".equals(event)) {
			errorEvent = "customer_accept_bfl";
		}
		return errorEvent;
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
		// Commented by Pratheepa for CR148.Not a seperate error page.Instead
		// message will be shown.
		/*
		 * if (resultMap.get("reminder") != null &&
		 * (resultMap.get("reminder")).equals("false")) {
		 * DefaultLogger.debug(this, "Inside no reminder");
		 * aPage.setPageReference("noreminder"); return aPage; }
		 */
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (("bfl").equals(event)) {
			forwardName = "after_bfl";
		}
		else if (event.equals("issue_draft_bfl") || event.equals("view_issue_draft_bfl")) {
			forwardName = "after_issue_draft_bfl";
		}
		else if (("confirm_issue_draft_bfl").equals(event)) {
			forwardName = "after_confirm_issue_draft_bfl";
		}
		else if (event.equals("send_draft_bfl") || event.equals("view_send_draft_bfl")) {
			forwardName = "after_send_draft_bfl";
		}
		else if (("confirm_send_draft_bfl").equals(event)) {
			forwardName = "after_confirm_send_draft_bfl";
		}
		else if (event.equals("recv_draft_bfl_ack") || event.equals("view_recv_draft_bfl_ack")) {
			forwardName = "after_recv_draft_bfl_ack";
		}
		else if (("confirm_recv_draft_bfl_ack").equals(event)) {
			forwardName = "after_confirm_recv_draft_bfl_ack";
		}
		else if (event.equals("issue_clean_type_bfl") || event.equals("view_issue_clean_type_bfl")) {
			forwardName = "after_issue_clean_type_bfl";
		}
		else if (("confirm_issue_clean_type_bfl").equals(event)) {
			forwardName = "after_confirm_issue_clean_type_bfl";
		}
		else if (event.equals("special_issue_clean_type_bfl") || event.equals("view_special_issue_clean_type_bfl")) {
			forwardName = "after_special_issue_clean_type_bfl";
		}
		else if (("confirm_special_issue_clean_type_bfl").equals(event)) {
			forwardName = "after_confirm_special_issue_clean_type_bfl";
		}
		else if (event.equals("issue_final_bfl") || event.equals("view_issue_final_bfl")) {
			forwardName = "after_issue_final_bfl";
		}
		else if (("confirm_issue_final_bfl").equals(event)) {
			forwardName = "after_confirm_issue_final_bfl";
		}
		else if (event.equals("customer_accept_bfl") || event.equals("view_customer_accept_bfl")) {
			forwardName = "after_customer_accept_bfl";
		}
		else if (("confirm_customer_accept_bfl").equals(event)) {
			forwardName = "after_confirm_customer_accept_bfl";
		}
		else if (event.equals("print_bfl_reminder") || event.equals("view_print_bfl_reminder")) {
			forwardName = "after_print_bfl_reminder";
		}
		else if (("reminder_bfl").equals(event)) {
			forwardName = "after_reminder_bfl";
		}
		else if (event.equals("confirm_print_bfl_reminder")) {
			forwardName = "after_confirm_print_bfl_reminder";
		}
		// Added by Pratheepa for CR148
		else if (event.equals("view_bfl") || event.equals("checker_view_bfl")) {
			forwardName = "after_view_bfl";
		}
		return forwardName;
	}
}
