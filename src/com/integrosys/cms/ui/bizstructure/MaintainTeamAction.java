/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.bizstructure;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/11/27 07:38:01 $ Tag: $Name: $
 */
public class MaintainTeamAction extends BizStructureIPinAction {
    //Andy Wong, 2 Jan 2008: define first sorting criteria when displaying team member
    public static final String FIRST_SORT = "ABBREVIATION";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ((event != null) && event.equals("start")) {
			objArray = new ICommand[2];
			objArray[0] = getCommand("ListTeamTypesCmd");
			objArray[1] = getCommand("DefaultTeamCmd");
		}
		else if ("redirect_list_team".equals(event) || "maker_list_team".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerListTeamCmd");
		}
		else if ("maker_prepare_add_team".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = getCommand("PrepareMakerAddTeamCmd");
			objArray[1] = getCommand("GetOrgGroupByCtryCmd");
			objArray[2] = getCommand("GetOrgByCtryCmd");
			objArray[3] = getCommand("GetSegmentByCtryCmd");
		}
		else if ("maker_edit_reject_add".equals(event) || "maker_add_team".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerAddTeamCmd");
		}
		else if ("maker_edit_team_read".equals(event) || "maker_delete_team_read".equals(event)
				|| "maker_view_team".equals(event) || "maker_view_team_wf".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = getCommand("MakerReadTeamCmd");
			objArray[1] = getCommand("GetOrgGroupByCtryCmd");
			objArray[2] = getCommand("GetOrgByCtryCmd");
			objArray[3] = getCommand("GetSegmentByCtryCmd");
		}
		else if ("maker_edit_reject_edit".equals(event) || "maker_edit_team".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerEditTeamCmd");
		}
		else if ("maker_edit_reject_delete".equals(event) || "maker_delete_team".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerDeleteTeamCmd");
		}
		else if ((event != null) && event.equals("checker_approve_add")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerApproveAddTeamCmd");
		}
		else if ((event != null) && event.equals("checker_approve_edit")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerApproveEditTeamCmd");
		}
		else if ((event != null) && event.equals("checker_approve_delete")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerApproveDeleteTeamCmd");
		}
		else if ((event != null) && event.equals("checker_reject_add")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerRejectAddTeamCmd");
		}
		else if ((event != null) && event.equals("checker_reject_edit")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerRejectEditTeamCmd");
		}
		else if ((event != null) && event.equals("checker_reject_delete")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerRejectDeleteTeamCmd");
		}
		else if ("rejected_add_read".equals(event) || "rejected_edit_read".equals(event)
				|| "rejected_delete_read".equals(event) || "checker_add_read".equals(event)
				|| "checker_edit_read".equals(event) || "checker_delete_read".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = getCommand("CheckerReadTeamCmd");
			objArray[1] = getCommand("GetOrgGroupByCtryCmd");
			objArray[2] = getCommand("GetOrgByCtryCmd");
			objArray[3] = getCommand("GetSegmentByCtryCmd");
		}
		else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerCnclAddTeamCmd");
		}
		else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerCnclEditTeamCmd");
		}
		else if ((event != null) && event.equals("maker_cncl_reject_delete")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerCnclDeleteTeamCmd");
		}
		else if ("search_team_user_for_edit".equals(event) || "search_team_user_for_add_reject".equals(event)
				|| "search_team_user_for_edit_reject".equals(event) || "search_team_user_for_add".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("UserSearchCmd");
		}
		else if ("add_team_users_for_edit".equals(event) || "add_team_users_for_add_reject".equals(event)
				|| "add_team_users_for_edit_reject".equals(event) || "add_team_users_for_add".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = getCommand("AddUserCmd");
			objArray[1] = getCommand("GetOrgGroupByCtryCmd");
			objArray[2] = getCommand("GetOrgByCtryCmd");
			objArray[3] = getCommand("GetSegmentByCtryCmd");
		}
		else if ("remove_team_users_for_add".equals(event) || "remove_team_user_for_add_reject".equals(event)
				|| "remove_team_users_for_edit".equals(event) || "remove_team_user_for_edit_reject".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = getCommand("RemoveTeamUserCmd");
			objArray[1] = getCommand("GetOrgGroupByCtryCmd");
			objArray[2] = getCommand("GetOrgByCtryCmd");
			objArray[3] = getCommand("GetSegmentByCtryCmd");
		}
		else if ((event != null) && event.equals("cncl_team_users_for_add")) {
			objArray = new ICommand[1];
			// objArray[0] = getCommand("MakerDeleteTeamCmd");//todo integrate
			// by req..
		}
		else if ((event != null) && event.equals("rejected_read")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerReadTeamCmd");
		}
		else if ("maker_add_edit_team_error".equals(event) || "maker_add_edit_team_error_reject".equals(event)
				|| "checker_approve_add_error".equals(event) || "checker_approve_edit_error".equals(event)
				|| "add_ctry_add_team".equals(event) || "add_ctry_add_team_reject".equals(event)
				|| "add_ctry_edit_team".equals(event) || "add_ctry_edit_team_reject".equals(event)
				|| "del_ctry_add_team".equals(event) || "del_ctry_add_team_reject".equals(event)
				|| "del_ctry_edit_team".equals(event) || "del_ctry_edit_team_reject".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = getCommand("GetOrgGroupByCtryCmd");
			objArray[1] = getCommand("GetOrgByCtryCmd");
			objArray[2] = getCommand("GetSegmentByCtryCmd");
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
		return MaintainTeamValidator.validateInput((MaintainTeamForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_add_team") || event.equals("maker_edit_team") || event.equals("maker_edit_reject_add")
				|| event.equals("maker_edit_reject_edit")) {
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
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}
		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_add_team".equals(event)) {
			errorEvent = "maker_add_edit_team_error";
		}
		else if ("maker_edit_team".equals(event)) {
			errorEvent = "maker_add_edit_team_error";
		}
		else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_team_error_reject";
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_team_error_reject";
		}
		else if ("checker_approve_add".equals(event)) {
			errorEvent = "checker_approve_add_error";
		}
		else if ("checker_approve_edit".equals(event)) {
			errorEvent = "checker_approve_edit_error";
			// errorEvent = "checker_edit_read";

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
		if ((event != null) && event.equals("start")) {
			forwardName = "after_start";
		}
		else if ((event != null) && event.equals("maker_list_team")) {
			forwardName = "maker_list_team_page";
		}
		else if ((event != null) && event.equals("redirect_list_team")) {
			forwardName = "common_submit_frame_page";
		}
		else if ((event != null) && event.equals("maker_prepare_add_team")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("add_ctry_add_team")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("add_ctry_add_team_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("add_ctry_edit_team")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("add_ctry_edit_team_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("del_ctry_add_team")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("del_ctry_add_team_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("del_ctry_edit_team")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("del_ctry_edit_team_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("maker_add_team")) {
			forwardName = "get_list_team_page";
		}
		else if ((event != null) && event.equals("maker_view_team")) {
			forwardName = "maker_view_delete_page";
		}
		else if ((event != null) && event.equals("maker_edit_team_read")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("maker_edit_team")) {
			forwardName = "get_list_team_page";
		}
		else if ((event != null) && event.equals("maker_delete_team_read")) {
			forwardName = "maker_view_delete_page";
		}
		else if ((event != null) && event.equals("maker_delete_team")) {
			forwardName = "get_list_team_page";
		}
		else if ((event != null) && event.equals("checker_add_read")) {
			forwardName = "checker_team_page";
		}
		else if ((event != null) && event.equals("checker_edit_read")) {
			forwardName = "checker_team_page";
		}
		else if ((event != null) && event.equals("checker_delete_read")) {
			forwardName = "checker_team_page";
		}
		else if ((event != null) && event.equals("checker_approve_add")) {
			forwardName = "common_approve_page";
		}
		else if ((event != null) && event.equals("checker_approve_add_error")) {
			forwardName = "checker_team_page";
		}
		else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		}
		else if ((event != null) && event.equals("checker_approve_edit_error")) {
			forwardName = "checker_team_page";
		}
		else if ((event != null) && event.equals("checker_approve_delete")) {
			forwardName = "common_approve_page";
		}
		else if ((event != null) && event.equals("checker_reject_add")) {
			forwardName = "common_reject_page";
		}
		else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		}
		else if ((event != null) && event.equals("checker_reject_delete")) {
			forwardName = "common_reject_page";
		}
		else if ((event != null) && event.equals("rejected_add_read")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("rejected_edit_read")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_delete_rejected_page";
		}
		else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			forwardName = "common_close_page";
		}
		else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			forwardName = "common_close_page";
		}
		else if ((event != null) && event.equals("maker_cncl_reject_delete")) {
			forwardName = "common_close_page";
		}
		else if ((event != null) && event.equals("maker_edit_reject_add")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("maker_edit_reject_delete")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}
		else if ((event != null) && event.equals("maker_add_edit_team_error")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("remove_team_users_for_add")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("remove_team_users_for_edit")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("remove_team_user_for_add_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("remove_team_user_for_edit_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("maker_add_edit_team_error_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("search_team_user_for_add")) {
			forwardName = "search_team_user_for_add";
		}
		else if ((event != null) && event.equals("add_team_users_for_add")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("search_team_user_for_add_reject")) {
			forwardName = "search_team_user_for_add_reject";
		}
		else if ((event != null) && event.equals("add_team_users_for_add_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("search_team_user_for_edit")) {
			forwardName = "search_team_user_for_edit";
		}
		else if ((event != null) && event.equals("add_team_users_for_edit")) {
			forwardName = "maker_add_edit_team_page";
		}
		else if ((event != null) && event.equals("search_team_user_for_edit_reject")) {
			forwardName = "search_team_user_for_edit_reject";
		}
		else if ((event != null) && event.equals("add_team_users_for_edit_reject")) {
			forwardName = "maker_add_edit_team_page_rejected";
		}
		else if ((event != null) && event.equals("rejected_read")) {
			forwardName = "rejected_read_page";
		}
		else if ((event != null) && event.equals("maker_view_team_wf")) {
			forwardName = "maker_view_team_wf";
		}
		return forwardName;
	}
}