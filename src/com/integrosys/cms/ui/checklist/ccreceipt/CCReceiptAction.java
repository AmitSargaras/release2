/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/ccreceipt/CCReceiptAction.java,v 1.38 2006/09/05 03:23:09 czhou Exp $
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.secreceipt.*;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.38 $
 * @since $Date: 2006/09/05 03:23:09 $ Tag: $Name: $
 */
public class CCReceiptAction extends CommonAction {

	public final static String EVENT_CHECKER_UPDATE = "checker_update";

	// public final static String EVENT_ADD_SHARE_CHECKLIST =
	// "add_SHARE_CHECKLIST"; //consider changing to complete_share_checklist
	// public final static String EVENT_UPDATE_SHARE_CHECKLIST =
	// "update_SHARE_CHECKLIST";
	public final static String EVENT_REFRESH_SHARE_CHECKLIST = "refresh_share_checklist";
    public final static String EVENT_EXPRESS_COMPLETION = "express_complete";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		// Start for CR-17
		if (// EVENT_ADD_SHARE_CHECKLIST.equals(event) ||
			// EVENT_UPDATE_SHARE_CHECKLIST.equals(event) ||
		EVENT_REFRESH_SHARE_CHECKLIST.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareCommand();
			objArray[1] = new UpdateShareCheckListCommand();
			// objArray[1] = new AddShareCheckListCommand();
			objArray[2] = new FrameFlagSetterCommand();
			// } else if (EVENT_UPDATE_SHARE_CHECKLIST.equals(event)) {
			// objArray = new ICommand[3];
			// objArray[0] = new PrepareCommand();
			// objArray[1] = new UpdateShareCheckListCommand();
			// objArray[2] = new FrameFlagSetterCommand();
			// End for CR-17
		}
		else if ("list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("cust_list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListCustodianCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("prepare_update".equals(event) || "view".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareUpdateCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("REMIND".equals(event) || "RECEIVE".equals(event) || "WAIVER_REQ".equals(event)
				|| "DEFER_REQ".equals(event) || "DELETE".equals(event) || "VIEW_COMPLETED".equals(event)
                || "REDEEM".equals(event) || "VIEW_REDEEMED".equals(event) || "VIEW_PENDING_REDEEM".equals(event)                
				|| "VIEW_DEFERRED".equals(event) || "VIEW_DELETED".equals(event) || "VIEW_EXPIRED".equals(event)
				|| "VIEW_PENDING_COMPLETE".equals(event) || "VIEW_PENDING_DELETE".equals(event)
				|| "VIEW_PENDING_RENEWAL".equals(event) || "VIEW_PENDING_WAIVER".equals(event)
				|| "VIEW_RECEIVED".equals(event) || "VIEW_REMINDED".equals(event) || "VIEW_RENEWED".equals(event)
				|| "VIEW_WAIVED".equals(event) || "VIEW_PENDING_DEFER".equals(event)
				|| "VIEW_PENDING_DEFER_REQ".equals(event) || "VIEW_PENDING_WAIVER_REQ".equals(event)
				|| "VIEW_DEFER_REQ".equals(event) || "VIEW_WAIVER_REQ".equals(event) || "WAIVER".equals(event)
				|| "DEFER".equals(event) || "UPDATE_DEFERRED".equals(event)
                || "UPDATE_NARRATION".equals(event) || "UPDATE_RENEWAL".equals(event)
				|| "RENEW".equals(event) || "COMPLETE".equals(event) || "UPDATE".equals(event)
				|| "VIEW_PENDING_UPDATE".equals(event) || "ALLOW_PERM_UPLIFT".equals(event)
				|| "ALLOW_TEMP_UPLIFT".equals(event) || "VIEW_WAIVER_REQ_GENERATED".equals(event)
				|| "VIEW_DEFER_REQ_GENERATED".equals(event) || "ALLOW_RELODGE".equals(event)
                || "LODGE".equals(event) || "UPDATE_LODGED".equals(event) || "VIEW_LODGED".equals(event) 
                || "TEMP_UPLIFT".equals(event) || "VIEW_TEMP_UPLIFTED".equals(event)
                || "PERM_UPLIFT".equals(event) || "VIEW_PERM_UPLIFTED".equals(event)
                || "VIEW_PENDING_TEMP_UPLIFT".equals(event) || "VIEW_PENDING_PERM_UPLIFT".equals(event)
                || "VIEW_AWAITING".equals(event) || "VIEW_PENDING_LODGE".equals(event)
                || "VIEW_PENDING_RELODGE".equals(event))  { // bernard
																								// -
																								// added
																								// ALLOW_RELODGE
																								// ,
																								// priya
																								// -
																								// added
																								// UPDATE_RENEWAL
																								// for
																								// CMSSP
																								// -
																								// 619
			objArray = new ICommand[3];
			objArray[0] = new PrepareCommand();
			objArray[1] = new ReadCheckListItemCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "save_relodge".equals(event) || "save_redeem".equals(event)) { // bernard - added
													// save_relodge , priya -
													// added save_update_renewal
													// for CMSSP-619
			objArray = new ICommand[4];
			objArray[0] = new UpdateShareCheckListCommand();
			objArray[1] = new SaveCheckListItemCommand();
			objArray[2] = new FrameFlagSetterCommand();
			objArray[3] = new PrepareCommand();
		}
		else if ("undo".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new UndoCheckListItemCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("save".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SaveCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("edit".equals(event) || "close_checklist_item".equals(event) || "cancel_checklist_item".equals(event)
				|| "process".equals(event) || "edit_staging_checklist_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadStagingCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("cancel".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CancelCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if (EVENT_CHECKER_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerUpdateCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("forward_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ForwardCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("view_ok".equals(event) || "view_return".equals(event) || "chk_view_return".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareFormCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("maker_refresh_comment".equals(event) || EVENT_PREPARE.equals(event)
				|| "track_refresh_comment".equals(event) || "refresh_comment".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new FrameFlagSetterCommand();
		}
		else if ("save_remind_prepare".equals(event) || "save_receive_prepare".equals(event)
				|| "save_waiver_req_prepare".equals(event) || "save_defer_req_prepare".equals(event)
				|| "save_waiver_prepare".equals(event) || "save_defer_prepare".equals(event)
				|| "save_renew_prepare".equals(event) || "save_complete_prepare".equals(event)
				|| "save_update_prepare".equals(event) || "save_update_renewal_prepare".equals(event)
				|| "save_temp_uplift_prepare".equals(event) || "save_perm_uplift_prepare".equals(event)
				|| "save_delete_prepare".equals(event) || "save_relodge_prepare".equals(event) // bernard
																								// -
																								// added
																								// save_relodge_prepare
				|| "refresh_share_checklist_prepare".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("chk_view".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerViewCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("view_receipt".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ViewCCReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
        else if(EVENT_EXPRESS_COMPLETION.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new ExpressCompleteCommand();
            objArray[1] = new FrameFlagSetterCommand();
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
		return CCReceiptFormValidator.validateInput((CCReceiptForm) aForm, locale);
		// return
		// null;//SecurityMasterFormValidator.validateInput((SecurityMasterForm
		// )aForm,locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_renewal".equals(event) || "save_temp_uplift".equals(event)
				|| "save_perm_uplift".equals(event) || "save_delete".equals(event) || "save_relodge".equals(event)
                || "save_redeem".equals(event)
                || EVENT_SUBMIT.equals(event) || "save".equals(event)
				// || EVENT_ADD_SHARE_CHECKLIST.equals(event) ||
				// EVENT_UPDATE_SHARE_CHECKLIST.equals(event)
				|| EVENT_REFRESH_SHARE_CHECKLIST.equals(event)) {
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
		if ((resultMap.get("no_template") != null) && ((String) resultMap.get("no_template")).equals("true")) {
			aPage.setPageReference("no_template");
			return aPage;
		}
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if ((resultMap.get("wip_doc_loc") != null) && ((String) resultMap.get("wip_doc_loc")).equals("wip_doc_loc")) {
			aPage.setPageReference("wip_doc_loc");
			return aPage;
		}

		// R1.5 CR17 Starts
		DefaultLogger.debug(this, ">>>>>>>>>>> EVENT (getNextPage): " + event);
		DefaultLogger.debug(this, ">>>>>>>>>>> PREV EVENT (getNextPage): " + resultMap.get("prev_event"));

		if (resultMap.get("prev_event") != null) {
			String forwardName;
			if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
				forwardName = frameCheck(getReference((String) resultMap.get("prev_event")));
			}
			else {
				forwardName = getReference((String) resultMap.get("prev_event"));
			}

			DefaultLogger.debug(this, ">>>>>>>>>>>>> frame: " + resultMap.get("frame"));
			DefaultLogger.debug(this, ">>>>>>>>>>>>> forward name: " + forwardName);
			aPage.setPageReference(forwardName);
			return aPage;
		}
		// R1.5 CR17 Ends

		if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
			String forwardName = frameCheck(getReference(event));
			aPage.setPageReference(forwardName);
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_renewal".equals(event) || "save_temp_uplift".equals(event)
				|| "save_perm_uplift".equals(event) || "save_delete".equals(event) || "save_relodge".equals(event)
                || "save_redeem".equals(event)
                || EVENT_REFRESH_SHARE_CHECKLIST.equals(event)) {
			errorEvent = event + "_prepare";
		}
		else if ("save".equals(event) || EVENT_SUBMIT.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}

		DefaultLogger.debug(this, ">>>>>>>>>>>>> ERROR EVENT: " + errorEvent);
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ("list".equals(event)) {
			forwardName = "after_list";
		}
		if ("cust_list".equals(event)) {
			forwardName = "after_cust_list";
		}
		if ("prepare_update".equals(event) || "save_remind".equals(event) || "undo".equals(event)
				|| "save_receive".equals(event) || "save_waiver_req".equals(event) || "save_defer_req".equals(event)
				|| "save_waiver".equals(event) || "save_defer".equals(event) || "save_renew".equals(event)
				|| "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "view_ok".equals(event) || "edit".equals(event) || "edit_staging_checklist_item".equals(event)
                || "save_redeem".equals(event)  || EVENT_EXPRESS_COMPLETION.equals(event)
                || "save_relodge".equals(event) || EVENT_PREPARE.equals(event) ) { // priya
																					// -
																					// added
																					// save_update_renewal
																					// for
																					// CMSSP
																					// -
																					// 619
			forwardName = "after_prepare_update";
		}
		if ("maker_refresh_comment".equals(event))// MAKER Refresh
		{
			forwardName = "after_prepare_update_NF";
		}
		if ("REMIND".equals(event) || "save_remind_prepare".equals(event)) {
			forwardName = "after_REMIND";
		}
		if ("RECEIVE".equals(event) || "save_receive_prepare".equals(event)) {
			forwardName = "after_RECEIVE";
		}
		if ("WAIVER_REQ".equals(event) || "save_waiver_req_prepare".equals(event)) {
			forwardName = "after_WAIVER_REQ";
		}
		if ("DEFER_REQ".equals(event) || "save_defer_req_prepare".equals(event)) {
			forwardName = "after_DEFER_REQ";
		}
		if ("WAIVER".equals(event) || "save_waiver_prepare".equals(event)) {
			forwardName = "after_WAIVER";
		}
		if ("DEFER".equals(event) || "save_defer_prepare".equals(event) || "UPDATE_DEFERRED".equals(event)) {
			forwardName = "after_DEFER";
		}
		if ("RENEW".equals(event) || "save_renew_prepare".equals(event)) {
			forwardName = "after_RENEW";
		}
		if ("COMPLETE".equals(event) || "save_complete_prepare".equals(event)) {
			forwardName = "after_COMPLETE";
		}
		if ("UPDATE".equals(event) || "save_update_prepare".equals(event)) {
			forwardName = "after_UPDATE";
		}
		if ("UPDATE_NARRATION".equals(event)) {
			forwardName = "after_UPDATE_NARRATION";
		}
		// CMSSP-619
		if ("UPDATE_RENEWAL".equals(event) || "save_update_renewal_prepare".equals(event)) {
			forwardName = "after_UPDATE_RENEWAL";
		}
		if ("ALLOW_PERM_UPLIFT".equals(event) || "save_perm_uplift_prepare".equals(event)
               || "PERM_UPLIFT".equals(event) || "VIEW_PERM_UPLIFTED".equals(event)
               || "VIEW_PENDING_PERM_UPLIFT".equals(event)) {
			forwardName = "after_ALLOW_PERM_UPLIFT";
		}
		if ("ALLOW_TEMP_UPLIFT".equals(event) || "save_temp_uplift_prepare".equals(event)
               || "TEMP_UPLIFT".equals(event) || "VIEW_TEMP_UPLIFTED".equals(event)
               || "VIEW_PENDING_TEMP_UPLIFT".equals(event)) {
			forwardName = "after_ALLOW_TEMP_UPLIFT";
		}
		if ("ALLOW_RELODGE".equals(event) || "save_relodge_prepare".equals(event) ||
            "LODGE".equals(event) || "UPDATE_LODGED".equals(event) || "VIEW_LODGED".equals(event) ||
            "VIEW_PENDING_LODGE".equals(event) || "VIEW_PENDING_RELODGE".equals(event)) { // bernard
																						// -
																						// added
																						// to
																						// support
																						// allow
																						// relodge
            forwardName = "after_ALLOW_RELODGE";
		}
		if ("DELETE".equals(event) || "save_delete_prepare".equals(event)) {
			forwardName = "after_DELETE";
		}
        if ("REDEEM".equals(event) || "save_redeem_prepare".equals(event)) {
            forwardName = "after_REDEEM";
        }
        if ("VIEW_COMPLETED".equals(event)) {
			forwardName = "after_VIEW_COMPLETED";
		}
		if ("VIEW_DEFERRED".equals(event) || "VIEW_DEFER_REQ_GENERATED".equals(event) || "VIEW_DEFER_REQ".equals(event)) {
			forwardName = "after_VIEW_DEFERRED";
		}
		if ("VIEW_DELETED".equals(event)) {
			forwardName = "after_VIEW_DELETED";
		}
		if ("VIEW_EXPIRED".equals(event)) {
			forwardName = "after_VIEW_EXPIRED";
		}
		if ("VIEW_PENDING_COMPLETE".equals(event)) {
			forwardName = "after_VIEW_PENDING_COMPLETE";
		}
		if ("VIEW_PENDING_UPDATE".equals(event)) {
			forwardName = "after_VIEW_PENDING_UPDATE";
		}
		if ("VIEW_PENDING_DEFER".equals(event)) {
			forwardName = "after_VIEW_PENDING_DEFER";
		}
		if ("VIEW_PENDING_DEFER_REQ".equals(event)) {
			forwardName = "after_VIEW_PENDING_DEFER_REQ";
		}
		if ("VIEW_PENDING_DELETE".equals(event)) {
			forwardName = "after_VIEW_PENDING_DELETE";
		}
		if ("VIEW_PENDING_RENEWAL".equals(event)) {
			forwardName = "after_VIEW_PENDING_RENEWAL";
		}
		if ("VIEW_PENDING_WAIVER".equals(event)) {
			forwardName = "after_VIEW_PENDING_WAIVER";
		}
		if ("VIEW_PENDING_WAIVER_REQ".equals(event)) {
			forwardName = "after_VIEW_PENDING_WAIVER_REQ";
		}
        if ("VIEW_PENDING_REDEEM".equals(event)) {
            forwardName = "after_VIEW_PENDING_REDEEM";
        }
        if ("VIEW_RECEIVED".equals(event) ) {
			forwardName = "after_VIEW_RECEIVED";
		}
		if ("VIEW_AWAITING".equals(event)) { 
			forwardName = "after_VIEW_AWAITING";
		}
		if ("VIEW_REMINDED".equals(event)) {
			forwardName = "after_VIEW_REMINDED";
		}
		if ("VIEW_RENEWED".equals(event)) {
			forwardName = "after_VIEW_RENEWED";
		}
		if ("VIEW_WAIVED".equals(event) || "VIEW_WAIVER_REQ_GENERATED".equals(event) || "VIEW_WAIVER_REQ".equals(event)) {
			forwardName = "after_VIEW_WAIVED";
		}
        if ("VIEW_REDEEMED".equals(event)) {
			forwardName = "after_VIEW_REDEEMED";
		}
		if ("save".equals(event)) {
			forwardName = "after_save";
		}
		if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		if ("process".equals(event) || "refresh_comment".equals(event) || "chk_view_return".equals(event)) { // OFFICE
			forwardName = "after_process";
		}
		if ("approve_checklist_item".equals(event)) {
			forwardName = "after_approve_checklist_item";
		}
		if ("reject_checklist_item".equals(event)) {
			forwardName = "after_reject_checklist_item";
		}
		// <+Begin OFFICE
		if ("forward_checklist_item".equals(event)) {
			forwardName = "after_forward_checklist_item";
		}
		// +Begin OFFICE>
		if ("cancel_checklist_item".equals(event) || "close_checklist_item".equals(event) || "to_track".equals(event)
				|| "track_refresh_comment".equals(event)) {// OFFICE
			forwardName = "after_close_checklist_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		if ("cancel".equals(event)) {
			forwardName = "after_close";
		}
		if ("view".equals(event) || "view_return".equals(event)) {
			forwardName = "after_view";
		}
		if ("chk_view".equals(event)) {
			forwardName = "after_chk_view";
		}
		if ("view_receipt".equals(event)) {
			forwardName = "view_receipt";
		}
		if (EVENT_CHECKER_UPDATE.equals(event)) {
			forwardName = EVENT_CHECKER_UPDATE;
		}
        DefaultLogger.debug(this, "event is ============="+event);
		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}