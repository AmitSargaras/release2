
package com.integrosys.cms.ui.rbicategory;

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
 * @author $Govind.Sahu$ Action For Rbi Category
 */
public class RbiCategoryAction extends CommonAction implements
		IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";

	// public static final String EVENT_LIST = "maker_list_rbi_category";
	public static final String MAKER_LIST_RBI_CATEGORY = "maker_list_rbi_category";
	public static final String CHECKER_LIST_RBI_CATEGORY = "checker_list_rbi_category";
	public static final String CHECKER_VIEW_RBI_CATEGORY = "checker_view_rbi_category";
	public static final String MAKER_VIEW_RBI_CATEGORY = "maker_view_rbi_category";
	public static final String MAKER_EDIT_RBI_CATEGORY_READ = "maker_edit_rbi_category_read";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_EDIT_RBI_CATEGORY = "maker_edit_rbi_category";
	public static final String MAKER_DELETE_RBI_CATEGORY = "maker_delete_rbi_category";
	public static final String CHECKER_EDIT_READ = "checker_edit_read";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_CREATE="checker_reject_create";
	public static final String CHECKER_REJECT_DELETE="checker_reject_delete";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String REDIRECT_LIST_RBI_CATEGORY = "redirect_list_rbi_category";
	public static final String MAKER_DELETE_RBI_CATEGORY_READ = "maker_delete_rbi_category_read";
	public static final String MAKER_EDIT_REJECT_EDIT = "maker_edit_reject_edit";
	public static final String MAKER_SEARCH_RBI_CATEGORY = "maker_search_rbi_category";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_SEARCH_LIST_RBI_CATEGORY = "maker_search_list_rbi_category";
	public static final String CHECKER_SEARCH_LIST_RBI_CATEGORY = "checker_search_list_rbi_category";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_PREPARE_CREATE_RBI_CATEGORY ="maker_prepare_create_rbi_category";
	public static final String MAKER_CREATE_RBI_CATEGORY ="maker_create_rbi_category";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE="maker_prepare_resubmit_create";

	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		System.out
				.println("In Rbi Category Action with event --"
						+ event);

		ICommand objArray[] = null;
		if ((event.equals(CHECKER_LIST_RBI_CATEGORY))
				|| event.equals(MAKER_LIST_RBI_CATEGORY)||event.equals("redirect_list_rbi_category")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListRbiCategoryCmd");

		} else if ((event.equals(CHECKER_VIEW_RBI_CATEGORY)) || event.equals(MAKER_VIEW_RBI_CATEGORY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadRbiCategoryCmd");

		} else if ((event != null)
				&& event.equals(MAKER_EDIT_RBI_CATEGORY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadRbiCategoryCmd");
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| event.equals(MAKER_EDIT_RBI_CATEGORY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditRbiCategoryCmd");
		} else if ((event != null)
				&& (event.equals(MAKER_DELETE_RBI_CATEGORY_READ))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadRbiCategoryCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DELETE))
				|| event.equals(MAKER_DELETE_RBI_CATEGORY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteRbiCategoryCmd");
		} else if ((event.equals(MAKER_SEARCH_LIST_RBI_CATEGORY))||event.equals(CHECKER_SEARCH_LIST_RBI_CATEGORY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SearchListRbiCategoryCommand");

		} else if ((event.equals(LIST_PAGINATION))||event.equals(CHECKER_LIST_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		} else if ((event.equals(CHECKER_PROCESS_EDIT) || event
				.equals(REJECTED_DELETE_READ))
				
				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadRbiCategoryCmd");
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditRbiCategoryCmd");
		} else if ((event.equals(CHECKER_REJECT_CREATE)) || event.equals(CHECKER_REJECT_EDIT)
				|| event.equals(CHECKER_REJECT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectEditRbiCategoryCmd");
		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT)
				|| event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadRbiCategoryCmd");
		} else if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseRbiCategoryCmd");
		}else if ((event != null)&& event.equals(MAKER_PREPARE_CREATE_RBI_CATEGORY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateRbiCategoryCmd");

		}else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_RBI_CATEGORY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateRbiCategoryCmd");

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
		return RbiCategoryValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_EDIT_RBI_CATEGORY)
				|| event.equals(MAKER_EDIT_REJECT_EDIT)
				|| event.equals(MAKER_SEARCH_RBI_CATEGORY)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_CREATE)
				|| event.equals(MAKER_CREATE_RBI_CATEGORY)
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
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		} else if ((resultMap.get("Error_EmpId") != null)
				&& (resultMap.get("Error_EmpId")).equals("Error_EmpId")) {
			DefaultLogger.debug(this, "Inside Error_EmpId");
			aPage.setPageReference("maker_add_edit_rbi_category_page");
			return aPage;
		} else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
         if ("maker_edit_rbi_category".equals(event) ){
			errorEvent = "maker_edit_rbi_category_read";
		} 
        else if (event.equals("maker_confirm_resubmit_edit")) {
        	errorEvent = "maker_prepare_resubmit_delete";
 			//errorEvent = "maker_prepare_resubmit";
 		}
		else if (event.equals("maker_create_rbi_category")) {
			errorEvent = "maker_prepare_create_rbi_category";
		}
         if(event.equals("checker_reject_edit"))
 		{
 			return  "checker_process_create";
 		}
         if(event.equals("checker_approve_edit"))
  		{
  			return  "checker_process_create";
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
		String forwardName = null;
		
		if ((event.equals("get_list_rbi_category_page"))) {
			forwardName = "get_list_rbi_category_page";
		}
		if ((event.equals("checker_list_rbi_category"))
				|| event.equals("maker_list_rbi_category")) {
			forwardName = "maker_list_rbi_category_page";
		} else if ((event != null)
				&& event.equals("maker_search_rbi_category")) {
			forwardName = "maker_list_rbi_category_page";
		} else if ((event.equals("checker_list_pagination")) || event.equals("list_pagination")) {
			forwardName = "maker_list_rbi_category_page";
		} else if ((event != null)
				&& event.equals("redirect_list_rbi_category")) {
			forwardName = "maker_list_rbi_category_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_add_rbi_category")) {
			forwardName = "maker_add_edit_rbi_category_page";
		} else if ((event != null)
				&& event.equals("maker_add_rbi_category")) {
			forwardName = "common_submit_page";
		} else if ((event.equals("checker_view_rbi_category"))
				|| event.equals("maker_view_rbi_category")) {
			forwardName = "maker_view_page";
		} else if ((event != null)
				&& event.equals("maker_edit_rbi_category_read")) {
			forwardName = "maker_add_edit_rbi_category_page";
		} else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_rbi_category")
				|| event.equals("maker_confirm_resubmit_delete")
				|| event.equals("maker_create_rbi_category")
				|| event.equals("maker_confirm_resubmit_create")) {
			forwardName = "common_submit_page";
		} else if ((event != null)
				&& event.equals("maker_delete_rbi_category_read")) {
			forwardName = "maker_view_delete_page";
		}
		else if ((event != null)
				&& event.equals("maker_delete_rbi_category")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("checker_add_read")) {
			forwardName = "checker_rbi_category_page";
		} else if ((event != null) && event.equals("checker_add_read_error")) {
			forwardName = "checker_rbi_category_page";
		} else if ((event != null) && event.equals("checker_edit_read")) {
			forwardName = "checker_rbi_category_page";
		} else if ((event != null) && event.equals("checker_delete_read")) {
			forwardName = "checker_rbi_category_page";
		} else if ((event != null) && event.equals("checker_approve_add")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_delete")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_reject_add")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_delete")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("rejected_add_read")) {
			forwardName = "maker_add_edit_rbi_category_page";
		} else if ((event != null) && event.equals("rejected_edit_read")) {
			forwardName = "maker_add_edit_rbi_category_page";
		} else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_delete")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_edit_reject_add")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_delete")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		} else if ((event != null)
				&& event.equals("maker_add_edit_rbi_category_error")) {
			forwardName = "maker_add_edit_rbi_category_page";
		} else if ((event != null)
				&& event.equals("maker_create_rbi_category_error")) {
			forwardName = "prepare_create_rbi_category";
		}else if ((event != null) && event.equals("rejected_read")) {
			forwardName = "rejected_read_page";
		} else if ((event != null)
				&& event.equals("checker_approve_edit_error")) {
			forwardName = "checker_rbi_category_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_search_rbi_category")) {
			forwardName = "search_rbi_category_page";
		} else if ((event.equals("checker_search_list_rbi_category"))
				|| event.equals("maker_search_list_rbi_category")) {
			forwardName = "maker_list_rbi_category_page";
		} else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")) {
			forwardName = "checker_rbi_category_page";
		} else if ((event != null) && event.equals("maker_prepare_resubmit")) {
			forwardName = "maker_prepare_resubmit";
		} else if ((event != null) && (event.equals("maker_prepare_close"))) {
			forwardName = "maker_prepare_close";
		} else if ((event != null)
				&& event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		} else if ((event != null) && (event.equals(MAKER_CONFIRM_CLOSE))) {
			forwardName = "common_close_page";
		}else if ((event != null) && event.equals("maker_prepare_create_rbi_category")) {
			forwardName = "prepare_create_rbi_category";
		}
		else if (event.equals("maker_prepare_resubmit_create")) {
			forwardName = "maker_add_edit_rbi_category_page";
		}
		
		return forwardName;
	}

}