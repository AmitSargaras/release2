package com.integrosys.cms.ui.mfchecklist;

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
import com.integrosys.base.uiinfra.common.Page;

public class MFCheckListAction extends CommonAction {

	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	protected ICommand getCommand(String name) {
		ICommand command = (ICommand) getNameCommandMap().get(name);
		Validate.notNull(command, "not able to get command given name [" + name + "]");

		return command;
	}

	public static final String EVENT_READ = "read";

	public static final String EVENT_MAKER_MFCHECKLIST_SUBMIT = "maker_mfchecklist_confirm";

	public static final String EVENT_MAKER_CLOSE_MFCHECKLIST = "maker_close_mfchecklist";

	public static final String EVENT_CHECKER_APPROVE_MFCHECKLIST = "checker_approve_mfchecklist";

	public static final String EVENT_CHECKER_REJECT_MFCHECKLIST = "checker_reject_mfchecklist";

	public static final String EVENT_MFCHECKLIST_TRACK = "track";

	public static final String EVENT_PROCESS = "process";

	public static final String EVENT_PROCESS_UPDATE = "process_update";

	public static final String EVENT_WIP = "wip";

	public static final String EVENT_CLOSE = "close";

	public static final String EVENT_ERROR_RETURN = "error_return";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this, "********** Event is: " + event);
		ICommand objArray[] = null;
		if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ReadCommand");

		}
		else if (EVENT_MAKER_MFCHECKLIST_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerMFChecklistConfirmCommand");
		}
		else if (EVENT_MAKER_CLOSE_MFCHECKLIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ReadCommand");
		}
		else if (EVENT_CHECKER_APPROVE_MFCHECKLIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerApproveMFCheckListCommand");
		}
		else if (EVENT_CHECKER_REJECT_MFCHECKLIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("CheckerRejectMFCheckListCommand");
		}
		else if (EVENT_MFCHECKLIST_TRACK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ReadCommand");
		}
		else if (EVENT_PROCESS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ProcessMFCheckListCommand");
		}
		else if (EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ReadCommand");
		}
		else if (EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerCloseMFCheckListCommand");
		}
		else if (EVENT_ERROR_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("PrepareMFChecklistCommand");
		}
		return (objArray);
	}

	protected String getErrorEvent(String event) {

		String errorEvent = event;
		if (EVENT_MAKER_MFCHECKLIST_SUBMIT.equals(event)) {
			return EVENT_ERROR_RETURN;
		}
		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {

		if (EVENT_MAKER_MFCHECKLIST_SUBMIT.equals(event)) {
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MFCheckListValidator.validateInput(aForm, locale);
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

		DefaultLogger.debug(this + " method getNextPage", "~~~~~~~~~~~~~~~~~~ event is: " + event);
		Page aPage = new Page();
		if ((resultMap.get(EVENT_WIP) != null) && (resultMap.get(EVENT_WIP)).equals(EVENT_WIP)) {

			aPage.setPageReference(getReference(EVENT_WIP));
			return aPage;

		}
		else {

			aPage.setPageReference(getReference(event));
			return aPage;
		}

	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */

	private String getReference(String event) {
		String forwardName = "submit_fail";

		if (EVENT_READ.equals(event) || EVENT_ERROR_RETURN.equals(event)) {
			forwardName = "read";
		}
		else if (EVENT_MAKER_MFCHECKLIST_SUBMIT.equals(event)) {
			forwardName = "maker_mfchecklist_confirm";
		}
		else if (EVENT_PROCESS.equals(event)) {
			forwardName = "process";
		}
		else if (EVENT_CHECKER_REJECT_MFCHECKLIST.equals(event)) {
			forwardName = "checker_reject_mfchecklist";
		}
		else if (EVENT_PROCESS_UPDATE.equals(event)) {
			forwardName = "process_update";
		}
		else if (EVENT_MAKER_CLOSE_MFCHECKLIST.equals(event)) {
			forwardName = "maker_close_mfchecklist";
		}
		else if (EVENT_CLOSE.equals(event)) {
			forwardName = "close";
		}
		else if (EVENT_CHECKER_APPROVE_MFCHECKLIST.equals(event)) {
			forwardName = "checker_approve_mfchecklist";
		}
		else {
			forwardName = event;
		}

		return forwardName;
	}

}
