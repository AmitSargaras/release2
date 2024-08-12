package com.integrosys.cms.ui.discrepency;

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
import com.integrosys.cms.ui.discrepency.PrepareCommand;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 01/06/2011 06:35:00
 */

public class DiscrepencyAction extends CommonAction{
	
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String MAKER_LIST_DISCREPENCY = "list_discrepency";
	
	public static final String VIEW_DISCREPENCY = "view_discrepency";
	
	public static final String MAKER_CLOSE_BULK_DISCREPANCY ="maker_close_bulk_discrepancy";
	public static final String MAKER_PROCESS_BULK_DISCREPANCY ="maker_process_bulk_discrepancy";
	public static final String PREPARE_MAKER_EDIT_DISCREPENCY_CLOSE = "prepare_maker_edit_discrepency_close";
	public static final String PREPARE_MAKER_EDIT_DISCREPENCY_WAIVE = "prepare_maker_edit_discrepency_waive";
	public static final String PREPARE_MAKER_EDIT_DISCREPENCY_DEFER = "prepare_maker_edit_discrepency_defer";
	
	public static final String MAKER_EDIT_DISCREPENCY_CLOSE = "maker_edit_discrepency_close";
	public static final String MAKER_EDIT_DISCREPENCY_WAIVE = "maker_edit_discrepency_waive";
	public static final String MAKER_EDIT_DISCREPENCY_DEFER = "maker_edit_discrepency_defer";
	
	
	
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";
	public static final String EVENT_REMOVE_DISCREPENCY = "prepare_remove_discrepency";

	public static final String EVENT_LIST_DISCREPENCY = "view_list_discrepency";
	//public static final String EVENT_CHECKER_LIST_DISCREPENCY = "checker_view_list_discrepency";
//	public static final String EVENT_VIEW_DISCREPENCY_BY_INDEX = "view_discrepency_by_index";
	public static final String EVENT_PREPARE_MAKER_EDIT_DISCREPENCY = "prepare_maker_edit_discrepency";
	public static final String EVENT_PREPARE_CREATE_DISCREPENCY = "prepare_create_discrepency";
	public static final String EVENT_PREPARE_CREATE_DISCREPENCY_RETURN ="prepare_create_discrepency_return";
	public static final String EVENT_TOTRACK_MAKER_DISCREPENCY = "totrack_maker_discrepency";
	public static final String EVENT_CHECKER_APPROVE_DISCREPENCY = "checker_approve_discrepency";
	public static final String EVENT_CHECKER_APPROVE_BULK_DISCREPENCY = "checker_approve_bulk_discrepency";
	public static final String EVENT_CHECKER_REJECT_DISCREPENCY = "checker_reject_discrepency";
	public static final String MAKER_CONFIRM_CLOSE_DISCREPENCY = "maker_confirm_close";
	public static final String EVENT_CHECKER_PROCESS_CREATE_DISCREPENCY = "checker_process_create_discrepency";
	public static final String EVENT_CHECKER_PROCESS_UPDATE_DISCREPENCY = "checker_process_update_discrepency";
	public static final String MAKER_EDIT_DISCREPENCY_READ = "maker_edit_discrepency_read";
	public static final String EVENT_MAKER_EDIT_DISCREPENCY = "maker_edit_discrepency";
//	public static final String MAKER_DELETE_DISCREPENCY_READ = "maker_delete_discrepency_read";
//	public static final String MAKER_DELETE_DISCREPENCY = "maker_delete_discrepency";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_DISCREPENCY = "maker_prepare_activate_discrepency";
	public static final String EVENT_MAKER_ACTIVATE_DISCREPENCY = "maker_activate_discrepency";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_DISCREPENCY = "maker_confirm_resubmit_edit_discrepency";
	public static final String EVENT_MAKER_CREATE_DISCREPENCY = "maker_create_discrepency";
	public static final String EVENT_MAKER_CREATE_BULK_DISCREPENCY = "maker_create_bulk_discrepency";
	public static final String EVENT_MAKER_CREATE_DISCREPENCY_TEMP = "maker_create_discrepency_temp";
	public static final String EVENT_MAKER_REMOVE_DISCREPENCY_TEMP = "maker_remove_discrepency_temp";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_DISCREPENCY = "maker_resubmit_create_discrepency";
	public static final String EVENT_MAKER_PREPARE_CLOSE_DISCREPENCY = "maker_prepare_close";
//	public static final String EVENT_MAKER_SAVE_DISCREPENCY = "maker_save_discrepency";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_DISCREPENCY = "todo_maker_save_create_discrepency";
//	public static final String EVENT_MAKER_CREATE_SAVED_DISCREPENCY = "maker_create_saved_discrepency";
//	public static final String EVENT_MAKER_EDIT_SAVE_CREATED_DISCREPENCY = "maker_edit_save_created_discrepency";
	public static final String EVENT_MAKER_SAVE_EDIT_DISCREPENCY = "maker_save_edit_discrepency";
	public static final String EVENT_TODO_MAKER_SAVE_EDITED_DISCREPENCY = "todo_maker_save_edited_discrepency";
	public static final String EVENT_PROCESS_BULK_DISCREPENCY = "process_bulk_discrepancy";
	public static final String EVENT_CHECKER_BULK_REJECT_DISCREPENCY = "checker_bulk_reject_discrepency";
	public static final String SEARCH_DISCREPENCY = "search_discrepency";
	public static final String CHECKER_SEARCH_DISCREPENCY = "checker_search_discrepency";
	public static final String NEXT_LIST_DISCREPENCY = "next_list_discrepency";
	public static final String CHECKER_VEIW_DISCREPENCY = "checker_view_discrepancy";
	public static final String MAKER_VEIW_DISCREPENCY ="maker_view_discrepancy";
	public static final String MAKER_CLOSE_VEIW_DISCREPENCY ="maker_close_view_discrepancy";
	public static final String MAKER_RESUBMIT_BULK_DISCREPENCY ="maker_resubmit_bulk_discrepency";
	public static final String PREPARE_MAKER_EDIT_REJECT_DISCREPENCY ="prepare_maker_edit_reject_discrepency";
	public static final String MAKER_SUBMIT_EDIT_DISCREPENCY ="maker_submit_edit_discrepancy";
	public static final String MAKER_PROCESS_SEARCH_DISCREPENCY ="maker_process_search_discrepency";
	public static final String MAKER_CLOSE_SEARCH_DISCREPENCY ="maker_close_search_discrepency";
	public static final String RETURN_LIST_DISCREPENCY ="return_list_discrepency";
	public static final String CHECKER_NEXT_LIST_DISCREPENCY ="checker_next_list_discrepency";
	public static final String MAKER_NEXT_LIST_DISCREPENCY ="maker_next_list_discrepency";
	public static final String MAKER_CLOSE_NEXT_BULK_DISCREPENCY ="maker_close_next_bulk_discrepancy";
	public static final String CHECKE_LIST_DISCREPENCY ="checker_list_discrepency";
	public static final String MAKER_VIEW_REJECT_DISCREPENCY ="maker_view_reject_discrepency";
	public static final String MAKER_RETURN_PROCESS_LIST_DISCREPENCY ="maker_return_process_list_discrepency";
	public static final String MAKER_SEARCH_SESSION_LIST_DISCREPENCY ="maker_search_session_list_discrepency";
	public static final String PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_DEFER ="prepare_maker_edit_process_discrepency_defer";
	public static final String MAKER_SUBMIT_DEFER_DISCREPENCY ="maker_submit_defer_discrepancy";
	public static final String MAKER_SUBMIT_WAIVE_DISCREPENCY ="maker_submit_waive_discrepancy";
	public static final String MAKER_SUBMIT_CLOSE_DISCREPENCY ="maker_submit_close_discrepancy";
	public static final String MAKER_SUBMIT_UPDATE_DISCREPENCY ="maker_submit_update_discrepancy";
	public static final String PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_CLOSE ="prepare_maker_edit_process_discrepency_close";
	public static final String PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_WAIVE ="prepare_maker_edit_process_discrepency_waive";
	public static final String MAKER_NEXT_CREATE_TEMP_LIST_DISCREPENCY="maker_next_create_temp_list_discrepency";
	
	public static final String MAKER_SEARCH_SESSION_LIST_DISCREPENCY_CLOSE ="maker_search_session_list_discrepency_close";
	public static final String MAKER_UPDATE_TEMP_DISCREPENCY ="maker_update_temp_discrepancy";
	public static final String MAKER_CONFORM_UPDATE_TEMP_DISCREPENCY ="maker_conform_update_temp_discrepency";
	public static final String MAKER_EDIT_TEMP_DISCREPENCY_ERROR ="maker_edit_temp_discrepency_error";
	
	
	

	//public static final String CREATE_LIST_DISCREPENCY ="create_list_discrepency";
	
	
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, " Event : -----> " + event);
		ICommand objArray[] = null;
		
		if (event.equals(MAKER_LIST_DISCREPENCY)||event.equals(NEXT_LIST_DISCREPENCY)
				||event.equals(SEARCH_DISCREPENCY)||event.equals(RETURN_LIST_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListDiscrepencyCommand");
		}
				
		else if (event.equals(MAKER_CONFIRM_CLOSE_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseDiscrepencyCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_DISCREPENCY)
						|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_DISCREPENCY) 
						|| event.equals(EVENT_MAKER_SAVE_EDIT_DISCREPENCY)
						||event.equals(MAKER_EDIT_DISCREPENCY_WAIVE)
						||event.equals(MAKER_EDIT_DISCREPENCY_DEFER)
						||event.equals(MAKER_EDIT_DISCREPENCY_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditDiscrepencyCommand");
		}

		else if (event.equals(PREPARE_MAKER_EDIT_DISCREPENCY_CLOSE)
				||event.equals(PREPARE_MAKER_EDIT_DISCREPENCY_WAIVE)
				||event.equals(PREPARE_MAKER_EDIT_DISCREPENCY_DEFER)
				||event.equals(EVENT_PREPARE_MAKER_EDIT_DISCREPENCY)
				||event.equals(VIEW_DISCREPENCY)
				||event.equals(MAKER_VEIW_DISCREPENCY)
				||event.equals(MAKER_UPDATE_TEMP_DISCREPENCY)
				||event.equals(MAKER_EDIT_TEMP_DISCREPENCY_ERROR)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCommand();
			objArray[1] = (ICommand) getNameCommandMap().get("MakerReadDiscrepencyCommand");
			
		} else if (event.equals(EVENT_TOTRACK_MAKER_DISCREPENCY)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_DISCREPENCY)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE_DISCREPENCY)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_DISCREPENCY)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_DISCREPENCY)
				|| event.equals(EVENT_CHECKER_PROCESS_UPDATE_DISCREPENCY)
				|| event.equals(CHECKER_VEIW_DISCREPENCY)
				|| event.equals(MAKER_CLOSE_VEIW_DISCREPENCY)
				||event.equals(PREPARE_MAKER_EDIT_REJECT_DISCREPENCY)
				||event.equals(MAKER_VIEW_REJECT_DISCREPENCY)
				||event.equals(PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_DEFER)
				||event.equals(PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_CLOSE)
				||event.equals(PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_WAIVE)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCommand();
			objArray[1] = (ICommand) getNameCommandMap().get(
					"CheckerReadDiscrepencyCommand");
		}

		else if (event.equals(EVENT_PREPARE_CREATE_DISCREPENCY)
				||event.equals(EVENT_PREPARE_CREATE_DISCREPENCY_RETURN)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCommand();
			objArray[1] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateDiscrepencyCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_DISCREPENCY)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_DISCREPENCY)
				/*|| event.equals(EVENT_MAKER_CREATE_SAVED_DISCREPENCY)
				|| event.equals(EVENT_MAKER_SAVE_DISCREPENCY)
				|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_DISCREPENCY)*/) {
			
			
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateDiscrepencyCommand");
		}else if (event.equals(EVENT_MAKER_CREATE_DISCREPENCY_TEMP)
				||event.equals(MAKER_NEXT_CREATE_TEMP_LIST_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateTempDiscrepencyCommand");
		}else if (event.equals(EVENT_MAKER_REMOVE_DISCREPENCY_TEMP)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"MakerRemoveDiscrepencyCommand");
		}else if (event.equals(EVENT_MAKER_CREATE_BULK_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCreateBulkDiscrepencyCommand");
		}
		else if ((event != null) && event.equals(EVENT_CHECKER_APPROVE_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveDiscrepencyCommmand");
		} else if ((event != null) && event.equals(EVENT_CHECKER_REJECT_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectDiscrepencyCommmand");
		}else if ((event != null) && event.equals(EVENT_PROCESS_BULK_DISCREPENCY)||event.equals(CHECKER_SEARCH_DISCREPENCY)
				||event.equals(CHECKER_NEXT_LIST_DISCREPENCY)||event.equals(CHECKE_LIST_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadBulkDiscrepencyCommand");
		}else if ((event != null) && event.equals(EVENT_CHECKER_APPROVE_BULK_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerBulkApproveDiscrepencyCommmand");
		}else if ((event != null) && event.equals(EVENT_CHECKER_BULK_REJECT_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerBulkRejectDiscrepencyCommmand");
		}
		else if ((event != null) && event.equals(MAKER_CLOSE_BULK_DISCREPANCY)
				||(event != null) && event.equals(MAKER_CLOSE_SEARCH_DISCREPENCY)
				||(event != null) && event.equals(MAKER_CLOSE_NEXT_BULK_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadBulkDiscrepencyCommand");
		}
		else if ((event != null) && event.equals(MAKER_PROCESS_BULK_DISCREPANCY)
				||(event != null) && event.equals(MAKER_PROCESS_SEARCH_DISCREPENCY)
				||(event != null) && event.equals(MAKER_NEXT_LIST_DISCREPENCY)
				||(event != null)&& event.equals(MAKER_RETURN_PROCESS_LIST_DISCREPENCY)
				||(event != null)&& event.equals(MAKER_SEARCH_SESSION_LIST_DISCREPENCY)
				||(event != null)&& event.equals(MAKER_SEARCH_SESSION_LIST_DISCREPENCY_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadBulkDiscrepencyCommand");
		}
		else if ((event != null) && event.equals(MAKER_RESUBMIT_BULK_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerBulkResubmitDiscrepencyCommmand");
		}
		else if ((event != null) && event.equals(MAKER_SUBMIT_EDIT_DISCREPENCY)
				||(event != null) && event.equals(MAKER_SUBMIT_DEFER_DISCREPENCY)
				||(event != null) && event.equals(MAKER_SUBMIT_WAIVE_DISCREPENCY)
				||(event != null) && event.equals(MAKER_SUBMIT_CLOSE_DISCREPENCY)
				||(event != null) && event.equals(MAKER_SUBMIT_UPDATE_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditResubmitDiscrepencyCommmand");
		}
		else if ((event != null) && event.equals(MAKER_CONFORM_UPDATE_TEMP_DISCREPENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditTempDiscrepencyCommmand");
		}
		return objArray;
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
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return DiscrepencyValidator.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ( event.equals(EVENT_MAKER_CREATE_DISCREPENCY)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_DISCREPENCY)
				||event.equals(MAKER_EDIT_DISCREPENCY_WAIVE)
				||event.equals(MAKER_EDIT_DISCREPENCY_DEFER)
				||event.equals(MAKER_EDIT_DISCREPENCY_CLOSE)
				||event.equals(EVENT_MAKER_EDIT_DISCREPENCY)
				||event.equals(EVENT_MAKER_CREATE_DISCREPENCY_TEMP)
				||event.equals(MAKER_SUBMIT_EDIT_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_DEFER_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_WAIVE_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_CLOSE_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_UPDATE_DISCREPENCY)
				||event.equals(MAKER_CONFORM_UPDATE_TEMP_DISCREPENCY)
				
					
		)

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		
		if (EVENT_MAKER_CREATE_DISCREPENCY.equals(event)||EVENT_MAKER_CREATE_DISCREPENCY_TEMP.equals(event)) {
			errorEvent = EVENT_PREPARE_CREATE_DISCREPENCY;
		}
		else if (MAKER_CONFIRM_RESUBMIT_EDIT_DISCREPENCY.equals(event))	{
				errorEvent = "errorReSubmitEvent";
			
		}
		 else if (EVENT_CHECKER_REJECT_DISCREPENCY.equals(event)) {
			 errorEvent = "checker_reject_discrepency_error";
			 
		 }else if(event.equals(MAKER_EDIT_DISCREPENCY_WAIVE)){
			 errorEvent = PREPARE_MAKER_EDIT_DISCREPENCY_WAIVE;
		 }else if(event.equals(MAKER_EDIT_DISCREPENCY_DEFER)){
			 errorEvent =PREPARE_MAKER_EDIT_DISCREPENCY_DEFER;
		 }else if(event.equals(MAKER_EDIT_DISCREPENCY_CLOSE)){
			 errorEvent = PREPARE_MAKER_EDIT_DISCREPENCY_CLOSE;
		 }else if(event.equals(EVENT_MAKER_EDIT_DISCREPENCY)){
			 errorEvent = EVENT_PREPARE_MAKER_EDIT_DISCREPENCY;
		 }else if (EVENT_CHECKER_BULK_REJECT_DISCREPENCY.equals(event)) {
			 errorEvent = "checker_reject_discrepency_error";
			 
		 }else if (MAKER_SUBMIT_DEFER_DISCREPENCY.equals(event)) {
			 errorEvent = PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_DEFER;
			 
		 }else if (MAKER_SUBMIT_WAIVE_DISCREPENCY.equals(event)) {
			 errorEvent = PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_WAIVE;
			 
		 }else if (MAKER_SUBMIT_CLOSE_DISCREPENCY.equals(event)) {
			 errorEvent = PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_CLOSE;
			 
		 }else if (MAKER_SUBMIT_UPDATE_DISCREPENCY.equals(event)) {
			 errorEvent = PREPARE_MAKER_EDIT_REJECT_DISCREPENCY;
			 
		 }else if (MAKER_CONFORM_UPDATE_TEMP_DISCREPENCY.equals(event)) {
			 errorEvent = MAKER_EDIT_TEMP_DISCREPENCY_ERROR;
			 
		 }
		return errorEvent;
	}
	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		if ((resultMap.get("wip") != null)
				&& ((String) resultMap.get("wip")).equals("wip"))
			aPage.setPageReference("work_in_process");
		else if(MAKER_EDIT_TEMP_DISCREPENCY_ERROR.equals(event)){
			aPage.setPageReference(MAKER_UPDATE_TEMP_DISCREPENCY);
		}
		else
			aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals(MAKER_LIST_DISCREPENCY)||event.equals(SEARCH_DISCREPENCY)
				||event.equals(NEXT_LIST_DISCREPENCY)||event.equals(RETURN_LIST_DISCREPENCY)) {
			forwardName = MAKER_LIST_DISCREPENCY;
		} /*else if (event.equals(EVENT_CHECKER_LIST_DISCREPENCY) || event.equals(EVENT_CHECKER_PAGINATE)) {
			forwardName = EVENT_CHECKER_LIST_DISCREPENCY;
		}*/else if (event.equals(EVENT_REMOVE_DISCREPENCY)) {
			forwardName = EVENT_REMOVE_DISCREPENCY;
		}  else if (event.equals("editError")) {
			forwardName = "prepare_maker_edit_city";
		} else if (event.equals(EVENT_MAKER_CREATE_DISCREPENCY)) {
			forwardName = EVENT_MAKER_CREATE_DISCREPENCY;
		} /*else if (event.equals(EVENT_MAKER_CREATE_SAVED_DISCREPENCY) 
			|| event.equals(EVENT_MAKER_EDIT_SAVE_CREATED_DISCREPENCY)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}*/ else if (event.equals(EVENT_TOTRACK_MAKER_DISCREPENCY)) {
			forwardName = EVENT_TOTRACK_MAKER_DISCREPENCY;
		} else if (event.equals(EVENT_PREPARE_CREATE_DISCREPENCY)||event.equals(MAKER_CONFORM_UPDATE_TEMP_DISCREPENCY)
				||event.equals(EVENT_PREPARE_CREATE_DISCREPENCY_RETURN)) {
			forwardName = EVENT_PREPARE_CREATE_DISCREPENCY;
		} else if (event.equals(EVENT_CHECKER_APPROVE_DISCREPENCY)) {
			forwardName = EVENT_CHECKER_APPROVE_DISCREPENCY;
		} else if (event.equals(EVENT_CHECKER_REJECT_DISCREPENCY)||event.equals(EVENT_CHECKER_BULK_REJECT_DISCREPENCY)) {
			forwardName = EVENT_CHECKER_REJECT_DISCREPENCY;
		}  else if (event.equals("errorReject")) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_DISCREPENCY;
		}else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_DISCREPENCY)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_DISCREPENCY;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_DISCREPENCY) || event.equals("errorSaveEvent")) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_DISCREPENCY;
		}else if (event.equals(EVENT_MAKER_PREPARE_CLOSE_DISCREPENCY)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE_DISCREPENCY;
		} 

		else if (EVENT_MAKER_PREPARE_ACTIVATE_DISCREPENCY.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_DISCREPENCY;
		}

		else if (EVENT_MAKER_ACTIVATE_DISCREPENCY.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_DISCREPENCY;
		}

		else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_DISCREPENCY) || event.equals("errorReSubmitEvent") ) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE_DISCREPENCY)) {
			forwardName = MAKER_CONFIRM_CLOSE_DISCREPENCY;
		}

		else if (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_DISCREPENCY)
				||event.equals(EVENT_MAKER_EDIT_DISCREPENCY)
				||event.equals(MAKER_EDIT_DISCREPENCY_WAIVE)
				||event.equals(MAKER_EDIT_DISCREPENCY_DEFER)
				||event.equals(MAKER_EDIT_DISCREPENCY_CLOSE)
				||event.equals(MAKER_RESUBMIT_BULK_DISCREPENCY)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}
		else if (event.equals(PREPARE_MAKER_EDIT_DISCREPENCY_CLOSE)) {
			forwardName = PREPARE_MAKER_EDIT_DISCREPENCY_CLOSE;
		}
		else if (event.equals(PREPARE_MAKER_EDIT_DISCREPENCY_WAIVE)) {
			forwardName = PREPARE_MAKER_EDIT_DISCREPENCY_WAIVE;
		}
		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_DISCREPENCY)) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_DISCREPENCY;
		}
		else if (event.equals(PREPARE_MAKER_EDIT_DISCREPENCY_DEFER)) {
			forwardName = PREPARE_MAKER_EDIT_DISCREPENCY_DEFER;
		}else if (event.equals(EVENT_CHECKER_PROCESS_UPDATE_DISCREPENCY)) {
			forwardName = EVENT_CHECKER_PROCESS_UPDATE_DISCREPENCY;
		}
		else if (event.equals(VIEW_DISCREPENCY)) {
			forwardName = VIEW_DISCREPENCY;
		}else if (event.equals("checker_reject_discrepency_error")) {
			forwardName = EVENT_PROCESS_BULK_DISCREPENCY;
		}else if (event.equals(EVENT_MAKER_CREATE_DISCREPENCY_TEMP)||event.equals(EVENT_MAKER_REMOVE_DISCREPENCY_TEMP)
				||event.equals(MAKER_NEXT_CREATE_TEMP_LIST_DISCREPENCY)) {
			forwardName = EVENT_MAKER_CREATE_DISCREPENCY_TEMP;
		}else if (event.equals(EVENT_MAKER_CREATE_BULK_DISCREPENCY)) {
			forwardName = EVENT_MAKER_CREATE_BULK_DISCREPENCY;
		}else if (event.equals(EVENT_PROCESS_BULK_DISCREPENCY)||event.equals(CHECKER_SEARCH_DISCREPENCY)
				||event.equals(CHECKER_NEXT_LIST_DISCREPENCY)||event.equals(CHECKE_LIST_DISCREPENCY)) {
			forwardName = EVENT_PROCESS_BULK_DISCREPENCY;
		}else if (event.equals(EVENT_CHECKER_APPROVE_BULK_DISCREPENCY)) {
			forwardName = EVENT_CHECKER_APPROVE_BULK_DISCREPENCY;
		} else if (event.equals(CHECKER_VEIW_DISCREPENCY)||event.equals(MAKER_CLOSE_VEIW_DISCREPENCY)) {
			forwardName = CHECKER_VEIW_DISCREPENCY;
		}else if (event.equals(MAKER_VEIW_DISCREPENCY)) {
			forwardName = MAKER_VEIW_DISCREPENCY;
		}else if (event.equals(MAKER_CLOSE_BULK_DISCREPANCY)||event.equals(MAKER_CLOSE_SEARCH_DISCREPENCY)
				||event.equals(MAKER_CLOSE_NEXT_BULK_DISCREPENCY)|| event.equals(MAKER_SEARCH_SESSION_LIST_DISCREPENCY_CLOSE)) {
			forwardName = MAKER_CLOSE_BULK_DISCREPANCY;
		}else if (event.equals(MAKER_PROCESS_BULK_DISCREPANCY)||event.equals(MAKER_SUBMIT_EDIT_DISCREPENCY)
				||event.equals(MAKER_PROCESS_SEARCH_DISCREPENCY)||event.equals(MAKER_NEXT_LIST_DISCREPENCY)
				||event.equals(MAKER_RETURN_PROCESS_LIST_DISCREPENCY)||event.equals(MAKER_SEARCH_SESSION_LIST_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_DEFER_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_WAIVE_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_CLOSE_DISCREPENCY)
				||event.equals(MAKER_SUBMIT_UPDATE_DISCREPENCY)) {
			forwardName = MAKER_PROCESS_BULK_DISCREPANCY;
		}else if (event.equals(PREPARE_MAKER_EDIT_REJECT_DISCREPENCY)) {
			forwardName = PREPARE_MAKER_EDIT_REJECT_DISCREPENCY;
		}else if (event.equals(MAKER_VIEW_REJECT_DISCREPENCY)) {
			forwardName = MAKER_VIEW_REJECT_DISCREPENCY;
		}else if (event.equals(PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_DEFER)) {
			forwardName =PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_DEFER ;
		}else if (event.equals(PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_CLOSE)) {
			forwardName =PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_CLOSE ;
		}else if (event.equals(PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_WAIVE)) {
			forwardName =PREPARE_MAKER_EDIT_PROCESS_DISCREPENCY_WAIVE ;
		}else if (event.equals(MAKER_UPDATE_TEMP_DISCREPENCY)) {
			forwardName = MAKER_UPDATE_TEMP_DISCREPENCY;
		}else if (event.equals(MAKER_EDIT_TEMP_DISCREPENCY_ERROR)) {
			forwardName = MAKER_UPDATE_TEMP_DISCREPENCY;
		}
		/*else if (event.equals(EVENT_MAKER_SAVE_DISCREPENCY)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_DISCREPENCY)) {
			forwardName = EVENT_MAKER_SAVE_DISCREPENCY;
		}*/
		DefaultLogger.debug(this, "Forward name --> " + forwardName);
		return forwardName;
	}
}
