/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/secreceipt/SecurityReceiptAction.java,v 1.38 2006/09/05 03:23:09 czhou Exp $
 */
package com.integrosys.cms.ui.checklist.paripassuupdate;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.camreceipt.FrameFlagSetterCommand;






/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.38 $
 * @since $Date: 2006/09/05 03:23:09 $ Tag: $Name: $
 */
public class UpdatePariPassuCheckListAction extends CommonAction {

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

		if ("after_list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListUpdatePPCheckListCmd();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("after_chk_list".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListUpdatePPCheckListCmd();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("prepare_update".equals(event) || "view".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareUpdatePPCheckListCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("view_chk_update".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareUpdatePPCheckListCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("RECEIVE".equals(event) ||"WAIVER".equals(event)
                || "DEFER".equals(event) || "UPDATE".equals(event)|| "VIEW_DEFERRED".equals(event) || "VIEW_DELETED".equals(event) || "VIEW_EXPIRED".equals(event)
				|| "VIEW_PENDING_COMPLETE".equals(event) || "VIEW_PENDING_DELETE".equals(event)|| "VIEW_COMPLETED".equals(event)
				|| "VIEW_PENDING_RENEWAL".equals(event) || "VIEW_PENDING_WAIVER".equals(event)||"UPDATE_DEFERRED".equals(event)
				|| "VIEW_RECEIVED".equals(event) || "VIEW_REMINDED".equals(event) || "VIEW_RENEWED".equals(event)
				|| "VIEW_WAIVED".equals(event) ||"VIEW_PENDING_UPDATE".equals(event) 
				|| "COMPLETE".equals(event)|| "VIEW_PENDING_DEFER".equals(event)) {  
																								
			objArray = new ICommand[4];
			objArray[0] = new PrepareCommand();
			objArray[1] = new ReadCheckListItemCommand();
            objArray[2] = new MapChecklistCommand();
			objArray[3] = new FrameFlagSetterCommand();
		}
		else if ("view_ok".equals(event) || "view_return".equals(event) || "track_refresh_comment".equals(event)
				|| "maker_refresh_comment".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new FrameFlagSetterCommand();            
        }
		else if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "save_relodge".equals(event) || "save_redeem".equals(event)) {// priya -added
													                            // save_update_renewal for
													                            // CMSSP-619
			objArray = new ICommand[4];
			objArray[0] = new UpdateShareCheckListCommand();
			objArray[1] = new SaveCheckListItemCommand();
            objArray[2] = new FrameFlagSetterCommand();
			objArray[3] = new PrepareCommand();
		}
		else if ("save".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SavePariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitPariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("edit".equals(event) || "close_checklist_item".equals(event) || "cancel_checklist_item".equals(event)
				|| "process".equals(event) || "edit_staging_checklist_item".equals(event) || "to_track".equals(event)) { // OFFICE
			objArray = new ICommand[3];
			objArray[0] = new ReadStagingPariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ClosePariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("cancel".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CancelPariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApprovePariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject_checklist_item".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectPariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("view_receipt".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ViewPariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
			objArray[2] = new PrepareCommand();
		}
		else if ("chk_view".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareCommand();
			objArray[1] = new CheckerViewPariPassuReceiptCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("refresh_comment".equals(event) || "chk_view_return".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshPariPassuReceiptCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
        DefaultLogger.debug(this, "event is ============="+event);
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


	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "save_relodge".equals(event) // priya -added
												// save_update_renewal for
												// CMSSP-619
                || "save_redeem".equals(event)
                || EVENT_SUBMIT.equals(event) || "save".equals(event) || EVENT_REFRESH_SHARE_CHECKLIST.equals(event)||"reject_checklist_item".equals(event)) {

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
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return PariPassuChkLstFormValidator.validateInput((UpdatePariPassuCheckListForm) aForm, locale);
		// return
		// null;//SecurityMasterFormValidator.validateInput((SecurityMasterForm
		// )aForm,locale);
	}
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
		// CR CMS-662 Starts
		if ((resultMap.get("leyes") != null) && ((String) resultMap.get("leyes")).equals("leyes")) {
			aPage.setPageReference(getReference(event + "_prepare"));
			return aPage;
		}
		// CR CMS-662 Ends

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		DefaultLogger.debug(this, ">>>>>>>>>>>> IN getErrorEvent!!!");
		String errorEvent = getDefaultEvent();

		if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_defer_req".equals(event) || "save_waiver".equals(event) || "save_defer".equals(event)
				|| "save_renew".equals(event) || "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "save_relodge".equals(event) || "save_redeem".equals(event)
                || EVENT_REFRESH_SHARE_CHECKLIST.equals(event) ||"reject_checklist_item".equals(event)) { // priya
																									// -
																									// added
																									// save_update_renewal
																									// for
																									// CMSSP
																									// -
																									// 619
			errorEvent = event + "_prepare";
		}
		else if ("save".equals(event)) {
			errorEvent = EVENT_PREPARE;  
		}
        else if (EVENT_SUBMIT.equals(event)) {
            errorEvent = EVENT_PREPARE;
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
		if ("after_list".equals(event)) {
			forwardName = "after_list";
		}
		if ("after_chk_list".equals(event)) {
			forwardName = "after_chk_list";
		}
		if ("prepare_update".equals(event) || "save_remind".equals(event) || "undo".equals(event)
				|| "save_receive".equals(event) || "save_waiver_req".equals(event) || "save_defer_req".equals(event)
				|| "save_waiver".equals(event) || "save_defer".equals(event) || "save_renew".equals(event)
				|| "save_complete".equals(event) || "save_update".equals(event)
				|| "save_update_narration".equals(event) || "save_update_renewal".equals(event)
				|| "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event) || "save_delete".equals(event)
				|| "view_ok".equals(event) || "edit".equals(event)
				|| "maker_refresh_comment".equals(event) || "save_relodge".equals(event) || "save_redeem".equals(event)
                || EVENT_PREPARE.equals(event) || EVENT_EXPRESS_COMPLETION.equals(event)) {                                           
			forwardName = "after_prepare_update";
		}
		if("edit_staging_checklist_item".equals(event))
		{
			forwardName = "after_prepare_update_list";
		}
		if ("RECEIVE".equals(event) || "save_receive_prepare".equals(event)) {
			forwardName = "after_RECEIVE";
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
		if("UPDATE".equals(event))
		{
			forwardName = "after_UPDATE";
		}
		if ("VIEW_RECEIVED".equals(event)) {
			forwardName = "after_VIEW_RECEIVED";
		}
		if("VIEW_WAIVED".equals(event))
		{
			forwardName = "after_VIEW_WAIVED";
		}
		if ("VIEW_DEFERRED".equals(event)) {
			forwardName = "after_VIEW_DEFERRED";
		}
		if ("save".equals(event)) {
			forwardName = "after_save";
		}
		if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		if ("VIEW_PENDING_WAIVER".equals(event)) {
			forwardName = "after_VIEW_PENDING_WAIVER";
		}
		if ("VIEW_PENDING_UPDATE".equals(event)) {
			forwardName = "after_VIEW_PENDING_UPDATE";
		}
		if ("COMPLETE".equals(event) || "save_complete_prepare".equals(event)) {
			forwardName = "after_COMPLETE";
		}
		if ("VIEW_PENDING_DEFER".equals(event)) {
			forwardName = "after_VIEW_PENDING_DEFER";
		}
		if ("process".equals(event) || "refresh_comment".equals(event) || "chk_view_return".equals(event)||"reject_checklist_item_prepare".equals(event)) { // OFFICE
			// Comment
			forwardName = "after_process";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		if ("cancel_checklist_item".equals(event) || "close_checklist_item".equals(event) || "to_track".equals(event)
				|| "track_refresh_comment".equals(event)) {// OFFICE
			forwardName = "after_close_checklist_item";
		}
		if ("cancel".equals(event)) {
			forwardName = "after_close";
		}
		if ("approve_checklist_item".equals(event)) {
			forwardName = "after_approve_checklist_item";
		}	
		if ("reject_checklist_item".equals(event)) {
			forwardName = "after_reject_checklist_item";
		}		
		if ("view_receipt".equals(event)) {
			forwardName = "view_receipt";
		}
		if ("view".equals(event) || "view_return".equals(event)) {
			forwardName = "after_view";
		}
		if ("view_chk_update".equals(event) ) {
			forwardName = "view_chk_update";
		}		
		if ("VIEW_PENDING_COMPLETE".equals(event)) {
			forwardName = "after_VIEW_PENDING_COMPLETE";
		}	
		if ("VIEW_COMPLETED".equals(event)) {
			forwardName = "after_VIEW_COMPLETED";
		}
		if ("chk_view".equals(event)) {
			forwardName = "after_chk_view";
		}		
		DefaultLogger.debug(this, "forwardName is  =============" + forwardName);
		return forwardName;
		
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}