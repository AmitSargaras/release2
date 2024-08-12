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
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/09/06 10:37:15 $ Tag: $Name: $
 */
public class CustodianAction extends CommonAction {

	public static String EVENT_APPROVE_DOC_CHECKER = "approve_doc_checker";

	public static String EVENT_REJECT_DOC_CHECKER = "reject_doc_checker";

	public static String EVENT_CUST_DOC_LIST_CHECKER = "custodian_doc_checker";

	public static String EVENT_SUBMIT_CUST_DOC_MAKER = "submit_custodian_doc";

	public static String EVENT_SAVE_CUST_DOC_MAKER = "save_custodian_doc";

	public static String EVENT_SUBMIT_TODO_CUST_DOC_MAKER = "submit_todo_custodian_doc";

	public static String EVENT_SAVE_TODO_CUST_DOC_MAKER = "save_todo_custodian_doc";

	public static String EVENT_CUST_DOC_LIST_MAKER = "custodian_doc_list";

	public static String EVENT_VIEW_CUST_ITEM_TODO = "view_item_todo";

	public static String EVENT_CLOSE_CUST_DOC_MAKER = "close_custodian_doc_maker";

	public static String EVENT_VIEW_CUST_SEC_ITEM_TODO = "view_sec_item_todo";

	public static String EVENT_CUST_SEC_DOC_LIST_MAKER = "cust_sec_doc_list";

	public static String EVENT_CUST_FORWARD_TODO = "cust_forward_todo";

	public static String EVENT_CUST_DOC_UNDO_TODO = "custodian_doc_undo_todo";

	public static String EVENT_CUST_DOC_UNDO = "custodian_doc_undo";

	public static String EVENT_REVERSE_CUST_MAKER_TODO = "reverse_custodian_maker_todo";

	public static String EVENT_REVERSE_CUST_MAKER = "reverse_custodian_maker";

    public static String EVENT_LIST_REVERSAL = "list_reversal";
    public static String EVENT_PRINT_REVERSAL_MEMO = "print_reversal_memo";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		// todo map command chains to event properly...
		ICommand objArray[] = null;
		if ((event != null) && event.equals("cc_doc_list")) {
			objArray = new ICommand[1];
			objArray[0] = new ListDocByDocTypeCmd();
		}
		else if ((event != null) && event.equals("security_doc_list")) {
			objArray = new ICommand[1];
			objArray[0] = new ListDocByDocTypeCmd();
		}
		else if ((event != null) && event.equals("read_custodian_doc_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("prepare_custodian_maker")) {
			objArray = new ICommand[3];
			objArray[0] = new ReadDocByTrxIdCmd();
			objArray[1] = new FrameFlagSetterCommand();
            objArray[2] = new ReadSecEnvelopeLocCmd();
		}
		else if ((event != null) && event.equals("prepare_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("lodge_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new LodgeDocMakerCmd();
		}
		else if ((event != null) && event.equals("lodge_approve_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new LodgeDocCheckerCmd();
		}
		else if ((event != null) && event.equals("relodge_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new RelodgeDocMakerCmd();
		}
		else if ((event != null) && event.equals("relodge_approve_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new RelodgeDocCheckerCmd();
		}
        else if ((event != null) && event.equals("lodgereversal_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new LodgeReversalDocMakerCmd();
		}
		else if ((event != null) && event.equals("tempuplift_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new TempUpliftDocMakerCmd();
		}
		else if ((event != null) && event.equals("tempuplift_approve_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new TempUpliftDocCheckerCmd();
		}
		else if ((event != null) && event.equals("permuplift_custodian_maker")) {
			objArray = new ICommand[1];
			objArray[0] = new PermUpliftDocMakerCmd();
		}
		else if ((event != null) && event.equals("permuplift_approve_custodian_checker")) {
			objArray = new ICommand[1];
			objArray[0] = new PermUpliftDocCheckerCmd();
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
		else if ((event != null) && event.equals("list_lodgement")) {
			objArray = new ICommand[1];
			objArray[0] = new ListLodgementMemoCmd();
		}
		else if ((event != null) && event.equals("list_withdrawal")) {
			objArray = new ICommand[1];
			objArray[0] = new ListWithdrawalMemoCmd();
		}
		else if ((event != null) && event.equals("print_lodgement_memo")) {
			objArray = new ICommand[1];
			objArray[0] = new PrintMemoCmd();
		}
		else if ((event != null) && event.equals("print_withdrawl_memo")) {
			objArray = new ICommand[1];
			objArray[0] = new PrintMemoCmd();
		}
		else if ((event != null) && event.equals("read_reject_doc")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("prepaare_edit_reject_doc")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("prepare_close_reject_doc")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && event.equals("save_doc")) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocByTrxIdCmd();
		}
		else if ((event != null) && (event.equals("session_cc_doc_list") || event.equals("session_security_doc_list"))) {
			objArray = new ICommand[2];
			objArray[0] = new ListDocSessionCmd();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ((event != null)
				&& (event.equals(EVENT_CUST_DOC_LIST_CHECKER) || event.equals(EVENT_CUST_DOC_LIST_MAKER)
						|| event.equals(EVENT_CUST_SEC_DOC_LIST_MAKER) || event.equals(EVENT_VIEW_CUST_SEC_ITEM_TODO)
						|| event.equals(EVENT_CUST_FORWARD_TODO) || event.equals(EVENT_VIEW_CUST_ITEM_TODO))) {
			objArray = new ICommand[3];
			objArray[0] = new ReadDocByTrxIdCmd();
			objArray[1] = new FrameFlagSetterCommand();
            objArray[2] = new ReadSecEnvelopeLocCmd();
		}
		else if ((event != null) && (event.equals(EVENT_APPROVE_DOC_CHECKER) || event.equals(EVENT_REJECT_DOC_CHECKER))) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitDocCheckerCmd();
		}
		else if ((event != null)
				&& (event.equals(EVENT_SUBMIT_CUST_DOC_MAKER) || event.equals(EVENT_SUBMIT_TODO_CUST_DOC_MAKER)
						|| event.equals(EVENT_SAVE_CUST_DOC_MAKER) || event.equals(EVENT_SAVE_TODO_CUST_DOC_MAKER) || event
						.equals(EVENT_CLOSE_CUST_DOC_MAKER))) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitDocMakerCmd();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ((event != null) && (event.equals(EVENT_CUST_DOC_UNDO_TODO) || event.equals(EVENT_CUST_DOC_UNDO))) {
			objArray = new ICommand[1];
			objArray[0] = new UndoCustDocItemCmd();
		}
		else if (((event != null) && event.equals(EVENT_REVERSE_CUST_MAKER))
				|| event.equals(EVENT_REVERSE_CUST_MAKER_TODO)) {
			objArray = new ICommand[1];
			objArray[0] = new ReverseCustDocItemCmd();
		}		
        else if ((event != null) && event.equals(EVENT_LIST_REVERSAL))
	    {
            objArray = new ICommand[1];
			objArray[0] = new ListReversalMemoCmd();
		}
        else if ((event != null) && event.equals(EVENT_PRINT_REVERSAL_MEMO)) {
			objArray = new ICommand[1];
			objArray[0] = new PrintMemoCmd();
		}
        else if(event.equals("doc_error")){
            objArray = new ICommand[1];
			objArray[0] = new ReadSecEnvelopeLocCmd();    
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
		return CustodianValidator.validateInput((CustodianForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("lodge_custodian_maker") || event.equals("relodge_custodian_maker")
				|| event.equals("tempuplift_custodian_maker") || event.equals("permuplift_custodian_maker")
				|| event.equals("edit_reject_custodian_maker") || event.equals("print_lodgement_memo")
				|| event.equals("print_withdrawl_memo") || event.equals("print_reversal_memo") || event.equals("lodgereversal_custodian_maker")
		) {
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
		// CR-34 modification
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		String forId = (String) resultMap.get("forId");

		if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
			String forwardName = getReference(event);

			if (EVENT_CUST_DOC_LIST_MAKER.equals(forwardName)) {
				if ("security".equals(forId) || "S".equalsIgnoreCase(forId)) {
					forwardName = "session_security_doc_list";
				}
				else {
					forwardName = "session_cc_doc_list";
				}
			}

			forwardName = frameCheck(forwardName);
			aPage.setPageReference(forwardName);
			return aPage;
		}

		if ("lodge_custodian_maker".equals(event) || "relodge_custodian_maker".equals(event)
				|| "tempuplift_custodian_maker".equals(event) || "permuplift_custodian_maker".equals(event)
				|| "edit_reject_custodian_maker".equals(event) || EVENT_CUST_DOC_UNDO.equals(event)
				|| EVENT_REVERSE_CUST_MAKER.equals(event)   || "lodgereversal_custodian_maker".equals(event)) {
			DefaultLogger.debug(this, "forId in CustodianAction = " + forId);
			if (((forId != null) && ("security".equals(forId))) || "S".equalsIgnoreCase(forId)) {
				aPage.setPageReference("back_security_doc_list");
			}
			else {
				aPage.setPageReference("back_cc_doc_list");
			}
		}
		else if (EVENT_CUST_DOC_UNDO_TODO.equals(event) || EVENT_REVERSE_CUST_MAKER_TODO.equals(event)) {
			aPage.setPageReference("back_doc_list_todo");
		}
		else {
			// end CR-34 modification
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("lodge_custodian_maker".equals(event) || "relodge_custodian_maker".equals(event)
				|| "tempuplift_custodian_maker".equals(event) || "permuplift_custodian_maker".equals(event)
				|| "edit_reject_custodian_maker".equals(event) || "lodgereversal_custodian_maker".equals(event)

		) {
			errorEvent = "doc_error";
		}
		else if ("print_lodgement_memo".equals(event)) {
			errorEvent = "lodgement_error";
		}
		else if ("print_withdrawl_memo".equals(event)) {
			errorEvent = "withdrawl_error";
		}
        else if ("print_reversal_memo".equals(event)) {
			errorEvent = "reversal_error";
		}
        else if(EVENT_APPROVE_DOC_CHECKER.equals(event)){
            errorEvent = EVENT_CUST_DOC_LIST_CHECKER;    
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
		else if ((event != null) && "prepare_create_custodian_doc_maker".equals(event)) {
			forwardName = "prepare_create_custodian_doc_maker";
		}
		else if ((event != null) && "doc_error".equals(event)) {
			forwardName = "doc_error";
		}
		else if ((event != null) && "create_custodian_doc_maker".equals(event)) {
			forwardName = "create_custodian_doc_maker";
		}
		else if ((event != null) && "read_custodian_doc_checker".equals(event)) {
			forwardName = "read_custodian_doc_checker";
		}
		else if ((event != null) && "prepare_custodian_maker".equals(event)) {
			forwardName = "prepare_custodian_maker";
		}
		else if ((event != null) && "prepare_custodian_checker".equals(event)) {
			forwardName = "prepare_custodian_checker";
		}
		else if ((event != null) && "lodge_custodian_maker".equals(event)) {
			// CR-34 modification
			forwardName = "confirm_submitted";
			// forwardName = "cc_doc_list";
			// end CR-34 modification
		}
		else if ((event != null) && "lodge_approve_custodian_checker".equals(event)) {
			forwardName = "confirm_approved";
		}
		else if ((event != null) && "relodge_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "relodge_approve_custodian_checker".equals(event)) {
			forwardName = "confirm_approved";
		}
        else if ((event != null) && "lodgereversal_custodian_maker".equals(event)) {
            forwardName = "confirm_submitted";
        }
		else if ((event != null) && "tempuplift_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "tempuplift_approve_custodian_checker".equals(event)) {
			forwardName = "confirm_approved";
		}
		else if ((event != null) && "permuplift_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted";
		}
		else if ((event != null) && "permuplift_approve_custodian_checker".equals(event)) {
			forwardName = "confirm_approved";
		}
		else if ((event != null) && "reject_custodian_checker".equals(event)) {
			forwardName = "confirm_rejected";
		}
		else if ((event != null) && "edit_reject_custodian_maker".equals(event)) {
			forwardName = "confirm_submitted_noframe";
		}
		else if ((event != null) && "cncl_reject_custodian_maker".equals(event)) {
			forwardName = "confirm_closed_noframe";
		}
		else if ((event != null) && "list_lodgement".equals(event)) {
			forwardName = "list_lodgement";
		}
		else if ((event != null) && "lodgement_error".equals(event)) {
			forwardName = "list_lodgement";
		}
		else if ((event != null) && "list_withdrawal".equals(event)) {
			forwardName = "list_withdrawal";
		}
		else if ((event != null) && "withdrawl_error".equals(event)) {
			forwardName = "list_withdrawal";
		}
		else if ((event != null) && "print_lodgement_memo".equals(event)) {
			forwardName = "list_lodgement";
		}
		else if ((event != null) && "print_withdrawl_memo".equals(event)) {
			forwardName = "list_withdrawal";
		}
		else if ((event != null) && "read_reject_doc".equals(event)) {
			forwardName = "forward";
		}
		else if ((event != null) && "prepaare_edit_reject_doc".equals(event)) {
			forwardName = "edit_reject_page";
		}
		else if ((event != null) && "prepare_close_reject_doc".equals(event)) {
			forwardName = "cncl_reject_page";
		}
		else if ((event != null) && EVENT_CUST_FORWARD_TODO.equals(event)) {
			forwardName = "forward";
		}
        else if ((event != null) && EVENT_PRINT_REVERSAL_MEMO.equals(event)) {
			forwardName = EVENT_LIST_REVERSAL;
		}
        else if ((event != null) && "reversal_error".equals(event)) {
			forwardName = EVENT_LIST_REVERSAL;
		}
        else if ((event != null) && EVENT_CUST_DOC_LIST_CHECKER.equals(event)){
            forwardName = EVENT_CUST_DOC_LIST_CHECKER;    
        }
		else {
			forwardName = event;
		}

		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}