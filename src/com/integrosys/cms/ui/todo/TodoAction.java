package com.integrosys.cms.ui.todo;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.cms.ui.workspace.Page;
import com.integrosys.cms.ui.workspace.WorkspaceForm;
import com.integrosys.cms.ui.workspace.WorkspaceFormValidator;

public class TodoAction extends CommonAction {

	public static final String EVENT_NORMAL_TODO = "normaltodo";

	public static final String EVENT_TOTRACK = "totrack";
	
	public static final String EVENT_TOTRACK_DISCREPANCY = "totrack_discrepancy";
	
	public static final String EVENT_PREPARE_DISCREPANCY = "prepare_discrepancy";

	public static final String EVENT_NEW_LIMIT_PROFILE = "newlimitprofile";

	public static final String EVENT_EXISTING_LIMIT_PROFILE = "existinglimitprofile";

	public static final String EVENT_NEW_BORROWER = "newborrower";

	public static final String EVENT_NEW_NONBORROWER = "newnonborrower";

	public static final String EVENT_CUSTOMER_TODO = "customertodo";

	public static final String EVENT_SEARCH_TODO = "searchtodo";
	
	public static final String EVENT_SEARCH_DISCREPANCY_TODO = "search_discrepancy_todo";
	
	public static final String EVENT_DISCREPANCY_TODO_CANCEL = "todo_cancel";

	public static final String EVENT_PENDING_CASES = "pending_cases";

	public static final String EVENT_REFRESH_PENDING_CASES = "refresh_pending_cases";

	public static final String EVENT_REASSIGN_PENDING_CASES = "reassign_pending_cases";

	public static final String EVENT_PENDING_PERFECTION_CREDIT_FOLDER = "pending_perfection_credit_folder";
	
	public static final String EVENT_PREPARE_DISCREPANCY_SORT = "prepare_discrepancy_sort";


	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return this.nameCommandMap;
	}

	protected ICommand getCommand(String name) {
		ICommand command = (ICommand) getNameCommandMap().get(name);
		Validate.notNull(command, "not able to get command given name [" + name + "]");

		return command;
	}

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"inside COMMAND CHAIN");
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event) || EVENT_SEARCH_TODO.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareTodoCommand");
		}
		else if (EVENT_TOTRACK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareToTrackCommand");
		}
		else if (EVENT_NEW_LIMIT_PROFILE.equals(event) || EVENT_EXISTING_LIMIT_PROFILE.equals(event)
				|| EVENT_NEW_BORROWER.equals(event) || EVENT_NEW_NONBORROWER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareNewLimitProfileCommand");
		}
		else if (EVENT_CUSTOMER_TODO.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareCustomerTodoCommand");
		}
		else if (EVENT_PENDING_CASES.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PreparePendingCasesCommand");
		}
		else if (EVENT_REFRESH_PENDING_CASES.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("RefreshPendingCasesCommand");
		}
		else if (EVENT_REASSIGN_PENDING_CASES.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ReassignPendingCasesCommand");
		}
		else if (EVENT_PENDING_PERFECTION_CREDIT_FOLDER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PreparePendingPerfectionCreditFolderCommand");
		}if (EVENT_PREPARE_DISCREPANCY.equals(event)
				||EVENT_SEARCH_DISCREPANCY_TODO.equals(event)
				||EVENT_DISCREPANCY_TODO_CANCEL.equals(event)
				||EVENT_PREPARE_DISCREPANCY_SORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareTodoDiscrepancyCommand");
		}
		else if (EVENT_TOTRACK_DISCREPANCY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareToTrackDiscrepancyCommand");
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
		WorkspaceForm wForm = (WorkspaceForm) aForm;
		if (EVENT_REASSIGN_PENDING_CASES.equals(wForm.getEvent())) {
			return WorkspaceFormValidator.validateReassignInput(wForm, locale);
		}
		else if (EVENT_PENDING_CASES.equals(wForm.getEvent())) {
			return WorkspaceFormValidator.validatePendingCases(wForm, locale);
		}
		return WorkspaceFormValidator.validateInput((WorkspaceForm) aForm, locale);
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

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (EVENT_CUSTOMER_TODO.equals(event)) {
			return EVENT_CUSTOMER_TODO;
		}
		if (EVENT_PENDING_CASES.equals(event)) {
			return EVENT_PENDING_CASES;
		}
		if (EVENT_REFRESH_PENDING_CASES.equals(event)) {
			return EVENT_REFRESH_PENDING_CASES;
		}
		if (EVENT_REASSIGN_PENDING_CASES.equals(event)) {
			return EVENT_REASSIGN_PENDING_CASES;
		}
		if (EVENT_PENDING_PERFECTION_CREDIT_FOLDER.equals(event)) {
			return EVENT_PENDING_PERFECTION_CREDIT_FOLDER;
		}
		if (EVENT_TOTRACK_DISCREPANCY.equals(event)) {
			return EVENT_TOTRACK_DISCREPANCY;
		}
		if (EVENT_PREPARE_DISCREPANCY.equals(event)
				||EVENT_SEARCH_DISCREPANCY_TODO.equals(event)
				||EVENT_DISCREPANCY_TODO_CANCEL.equals(event)
				||EVENT_PREPARE_DISCREPANCY_SORT.equals(event)) {
			return EVENT_PREPARE_DISCREPANCY;
		}
		return EVENT_NORMAL_TODO;
	}

	protected boolean isGatewayEvent(String event) {
		if (EVENT_PREPARE.equals(event)) {
			return true;
		}
		return false;
	}

	/**
	 * Indicates whether validation is required
	 * @param event
	 * @return boolean
	 */
	protected boolean isValidationRequired(String event) {
		return (event.equals(EVENT_CREATE) || event.equals(EVENT_PREPARE) || event.equals(EVENT_EXISTING_LIMIT_PROFILE)
				|| event.equals(EVENT_NEW_LIMIT_PROFILE) || event.equals(EVENT_NEW_NONBORROWER)
				|| event.equals(EVENT_NEW_BORROWER) || event.equals(EVENT_TOTRACK) || event.equals(EVENT_SEARCH_TODO) || event
				.equals(EVENT_REASSIGN_PENDING_CASES)||event.equals(EVENT_SEARCH_DISCREPANCY_TODO));
	}

	/**
	 * Determines what event to be called next on processing errors
	 * @param event
	 * @return String - event name
	 */
	protected String getErrorEvent(String event) {
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_EXISTING_LIMIT_PROFILE)
				|| event.equals(EVENT_NEW_LIMIT_PROFILE) || event.equals(EVENT_NEW_NONBORROWER)
				|| event.equals(EVENT_NEW_BORROWER) || event.equals(EVENT_TOTRACK) || event.equals(EVENT_SEARCH_TODO)) {
			return EVENT_CANCEL;
		}
		else if (event.equals(EVENT_REASSIGN_PENDING_CASES)) {
			return EVENT_PENDING_CASES;
		}
		else if (event.equals(EVENT_PENDING_PERFECTION_CREDIT_FOLDER)) {
			return EVENT_PENDING_PERFECTION_CREDIT_FOLDER;
		}
		else if (event.equals(EVENT_SEARCH_DISCREPANCY_TODO)) {
			return EVENT_PREPARE_DISCREPANCY;
		}
		return getDefaultEvent();
	}
	

}
