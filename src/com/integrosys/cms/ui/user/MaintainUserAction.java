/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.user;

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

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/07/12 07:51:40 $ Tag: $Name: $
 */
public class MaintainUserAction extends CommonAction {
	
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}


	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";
	
	public static final String REFRESH_REGION_ID="refresh_region_id";
	
	public static final String REFRESH_STATE_ID="refresh_state_id";
	
	public static final String REFRESH_CITY_ID="refresh_city_id";
	
	public static final String USER_DETAIL_LIST = "user_detail_list";
	
	public static final String KILL_USER_LOGIN_LIST  = "kill_user_login_list";
	
	public static final String BROADCAST_MESSAGE  = "broadcast_message";
	
	public static final String USER_HAND_OFF = "user_hand_off";
	
	public static final String KILL_USER_HAND_OFF_LIST  = "kill_user_hand_off_list";
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ((event != null) && event.equals("maker_list_user")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerListUserCmd();
		}
		else if ((event != null) && event.equals("list_pagination")) {
			objArray = new ICommand[1];
			objArray[0] = new PaginationCmd();
		}
		else if ((event != null) && event.equals("maker_search_user")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerSearchUserCmd();
		}
		else if ((event != null) && event.equals("redirect_list_user")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerListUserCmd();
		}
		else if ((event != null) && event.equals("maker_add_user")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerAddUserCmd();
		}
		else if ((event != null) && event.equals("user_detail_list")) {
			objArray = new ICommand[1];
			objArray[0] = new LoginUserDetailCmd();
		}else if ((event != null) && event.equals("user_hand_off")) {
			objArray = new ICommand[1];
			//objArray[0] = new LoginUserDetailCmd();
			objArray[0] = new LoginUserHandOffCmd();
		}else if ((event != null) && event.equals("kill_user_login_list")) {
			objArray = new ICommand[1];
			objArray[0] = new KillLoginUserDetailCmd();
		}else if ((event != null) && event.equals("kill_user_hand_off_list")) {
			objArray = new ICommand[1];
			objArray[0] = new KillLoginUserDetailCmd();
		}else if ((event != null) && event.equals("broadcast_message")) {
			objArray = new ICommand[1];
			objArray[0] = new BroadCastMessageCmd();
		}		
		else if ((event != null) && event.equals("maker_view_user")) {
			objArray = new ICommand[2];
			objArray[0] = new MakerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}
		else if ((event != null) && event.equals("maker_edit_user_read")) {
			objArray = new ICommand[2];
			objArray[0] = new MakerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}
		else if ((event != null) && event.equals("maker_edit_user")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditUserCmd();
		}
		else if ((event != null) && event.equals("maker_delete_user_read")) {
			objArray = new ICommand[2];
			objArray[0] = new MakerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}
		else if ((event != null) && event.equals("maker_delete_user")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerDeleteUserCmd();
		}
		else if ((event != null) && event.equals("checker_add_read")) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}
		else if ((event != null) && event.equals("checker_edit_read")) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}
		else if ((event != null) && event.equals("checker_delete_read")) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}
		else if ((event != null) && event.equals("checker_approve_add")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveAddUserCmd();
		}
		else if ((event != null) && event.equals("checker_approve_edit")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveEditUserCmd();
		}
		else if ((event != null) && event.equals("checker_approve_delete")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveDeleteUserCmd();
		}
		else if ((event != null) && event.equals("checker_reject_add")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectAddUserCmd();
		}
		else if ((event != null) && event.equals("checker_reject_edit")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectEditUserCmd();
		}
		else if ((event != null) && event.equals("checker_reject_delete")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectDeleteUserCmd();
		}
		else if ((event != null) && event.equals("rejected_add_read")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadUserCmd();
		}
		else if ((event != null) && event.equals("rejected_edit_read")) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadUserCmd();
		}
		else if ((event != null) && event.equals("rejected_delete_read")) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}
		else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCnclAddCmd();
		}
		else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCnclEditCmd();
		}
		else if ((event != null) && event.equals("maker_cncl_reject_delete")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCnclDeleteCmd();
		}
		else if ((event != null) && event.equals("maker_edit_reject_add")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerAddUserCmd();
		}
		else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditUserCmd();
		}
		else if ((event != null) && event.equals("maker_edit_reject_delete")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerDeleteUserCmd();
		}
		else if ((event != null) && event.equals("rejected_read")) {
			objArray = new ICommand[2];
			objArray[0] = new CheckerReadUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"UpdateListOptionsCmd");
		}/*Added by Abhijit R start*/
		else if ((event != null) && event.equals("maker_prepare_add_user")) {
			objArray = new ICommand[2];
			objArray[0] = new MakerPrepareAddUserCmd();
			objArray[1] = (ICommand) getNameCommandMap().get(
			"ListOptionsCmd");
		}else if (event.equals(REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshRegionCmd");

		}else if (event.equals(REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshStateCmd");

		}else if (event.equals(REFRESH_CITY_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshCityCmd");

		}else if ((event != null) && event.equals("maker_prepare_search_user")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"ListOptionsCmd");
		}else if ((event != null) && event.equals("refresh_branch_detail")) {
			objArray = new ICommand[1];
			objArray[0] =  new RefreshBranchDetailsCmd();
		}
		/*Added by Abhijit R end*/
		/*Added by Archana - Start*/
		else if((event != null) && event.equals("maker_event_upload_user")){
			objArray = new ICommand[1];
			objArray[0] = new MakerUploadUserCmd();
		}
		/*Added by Archana - End*/
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
		return MaintainUserValidator.validateInput((MaintainUserForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_add_user") || event.equals("maker_edit_user") || event.equals("maker_edit_reject_add")
				|| event.equals("maker_edit_reject_edit") || event.equals("maker_search_user"))

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
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, " Exception map error is " + exceptionMap.isEmpty());
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}else if ((resultMap.get("wipInvalid") != null) && (resultMap.get("wipInvalid")).equals("wipInvalid")) {
			aPage.setPageReference(getReference("work_in_process_invalid_user"));
			return aPage;
		}
		else if ((resultMap.get("Error_EmpId") != null) && (resultMap.get("Error_EmpId")).equals("Error_EmpId")) {
			DefaultLogger.debug(this, "Inside Error_EmpId");
			aPage.setPageReference("maker_add_edit_user_page");
			return aPage;
		}else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
			aPage.setPageReference("maker_fileupload_user_error_page");
			return aPage;
		}
		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_add_user".equals(event)) {
			errorEvent = "maker_add_edit_user_error";
		}
		else if ("maker_edit_user".equals(event)) {
			errorEvent = "maker_add_edit_user_error";
		}
		else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_user_error";
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_user_error";
		}
		else if ("checker_approve_edit".equals(event)) {
			errorEvent = "checker_approve_edit_error";
		}
		else if ("checker_approve_add".equals(event)) {
			errorEvent = "checker_add_read_error";
		}
		else if (event.equals("maker_search_user")) {
			errorEvent = "maker_prepare_search_user";
		}else if ("checker_reject_add".equals(event)) {
			errorEvent = "checker_reject_add_error";
		}
		else if ("checker_reject_edit".equals(event)) {
			errorEvent = "checker_reject_edit_error";
		}
		else if ("checker_reject_delete".equals(event)) {
			errorEvent = "checker_reject_delete_error";
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
		if ((event != null) && event.equals("maker_list_user")) {
			forwardName = "maker_list_user_page";
		}
		else if ((event != null) && event.equals("maker_search_user")) {
			forwardName = "maker_list_user_page";
		}
		else if ((event != null) && event.equals("list_pagination")) {
			forwardName = "maker_list_user_page";
		}
		else if ((event != null) && event.equals("redirect_list_user")) {
			forwardName = "maker_list_user_page";
		}
		else if ((event != null) && event.equals("maker_prepare_add_user")) {
			forwardName = "maker_add_edit_user_page";
		}
		else if ((event != null) && event.equals("maker_add_user")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("maker_view_user")) {
			forwardName = "maker_view_delete_page";
		}
		else if ((event != null) && event.equals("maker_edit_user_read")) {
			forwardName = "maker_add_edit_user_page";
		}
		else if ((event != null) && event.equals("maker_edit_user")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("maker_delete_user_read")) {
			forwardName = "maker_view_delete_page";
		}
		else if ((event != null) && event.equals("maker_delete_user")) {
			forwardName = "common_submit_page";
		}
		else if ((event != null) && event.equals("checker_add_read")) {
			forwardName = "checker_user_page";
		}
		else if ((event != null) && event.equals("checker_add_read_error")) {
			forwardName = "checker_user_page";
		}
		else if ((event != null) && event.equals("checker_edit_read")) {
			forwardName = "checker_user_page";
		}
		else if ((event != null) && event.equals("checker_delete_read")) {
			forwardName = "checker_user_page";
		}
		else if ((event != null) && event.equals("checker_approve_add")) {
			forwardName = "common_approve_page";
		}
		else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
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
			forwardName = "maker_add_edit_user_page";
		}
		else if ((event != null) && event.equals("rejected_edit_read")) {
			forwardName = "maker_add_edit_user_page";
		}
		else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_delete_page";
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
		}else if ((event != null) && event.equals("work_in_process_invalid_user")) {
			forwardName = "work_in_process_invalid_user_page";
		}
		else if ((event != null) && event.equals("maker_add_edit_user_error")) {
			forwardName = "maker_add_edit_user_page";
		}
		else if ((event != null) && event.equals("rejected_read")) {
			forwardName = "rejected_read_page";
		}
		else if ((event != null) && event.equals("checker_approve_edit_error")) {
			forwardName = "checker_user_page";
		}
		else if ((event != null) && event.equals("maker_prepare_search_user")) {
			forwardName = "search_user_page";
		}else if (event.equals("refresh_state_id")) {
			forwardName = "refresh_state_id";
		} 
		else if (event.equals("refresh_region_id")) {
				forwardName = "refresh_region_id";
			}
		else if (event.equals("refresh_city_id")) {
			forwardName = "refresh_city_id";
		}	/*Added by archana - start*/
		else if ((event != null) && event.equals("maker_prepare_upload_user")) {
			forwardName = "maker_prepare_upload_user";
		}else if ((event != null) && event.equals("maker_event_upload_user")) {
			forwardName = "common_submit_page";
		}
		/*Added by archana - End*/
		else if (event.equals("refresh_branch_detail")) {
			forwardName = "refresh_branch_detail";
		}
		else if (event.equals("checker_reject_add_error")) {
			forwardName = "checker_user_page";
		}
		else if (event.equals("checker_reject_edit_error")) {
			forwardName = "checker_user_page";
		}
		else if (event.equals("checker_reject_delete_error")) {
			forwardName = "checker_user_page";
		}else if ((event != null) && event.equals("user_detail_list")) {
			forwardName = "user_detail_list";
		}else if ((event != null) && event.equals("kill_user_login_list")) {
			forwardName = "kill_user_login_list";
		}else if ((event != null) && event.equals("broadcast_message")) {
			forwardName = "broadcast_message";
		}else if ((event != null) && event.equals("user_hand_off")) {
			forwardName = "user_hand_off";	
		}else if ((event != null) && event.equals("kill_user_hand_off_list")) {
			forwardName = "kill_user_hand_off_list";
		}
		return forwardName;
	}
}