/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.policycap;

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
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PolicyCapAction extends CommonAction {

	public static String EVENT_MAKER_EDIT = "maker_edit";

	public static String EVENT_MAKER_EDIT_ERROR = "maker_edit_error";

	public static String EVENT_MAKER_UPDATE = "maker_update";

	public static String EVENT_CHECKER_PROCESS = "checker_process";

	public static String EVENT_CHECKER_APPROVE = "checker_approve";

	public static String EVENT_CHECKER_REJECT = "checker_reject";

	public static String EVENT_MAKER_CLOSE = "maker_close";

	public static String EVENT_TO_TRACK = "to_track";

	private static String FORWARD_PREFIX = "after_";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new PreparePolicyCapCommand();
			objArray[0] = new PreparePolicyCapGroupCommand();
		}
		else if (EVENT_MAKER_EDIT.equals(event) || (EVENT_TO_TRACK.equals(event))
				|| (EVENT_CHECKER_PROCESS.equals(event)) || (EVENT_READ.equals(event))) {
			objArray = new ICommand[1];
			// objArray[0] = new ReadPolicyCapCommand();
			objArray[0] = new ReadPolicyCapGroupCommand();
		}
		else if (EVENT_MAKER_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new UpdatePolicyCapCommand();
			objArray[0] = new UpdatePolicyCapGroupCommand();
		}
		else if (EVENT_CHECKER_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new ApprovePolicyCapCommand();
			objArray[0] = new ApprovePolicyCapGroupCommand();
		}
		else if (EVENT_CHECKER_REJECT.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new RejectPolicyCapCommand();
			objArray[0] = new RejectPolicyCapGroupCommand();
		}
		else if (EVENT_MAKER_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new ClosePolicyCapCommand();
			objArray[0] = new ClosePolicyCapGroupCommand();
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
		return PolicyCapValidator.validateInput((PolicyCapForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		DefaultLogger.debug(this, "Inside isValidationRequired");
		DefaultLogger.debug(this, "event=" + event);
		if (event.equals(EVENT_MAKER_UPDATE) || event.equals(EVENT_MAKER_EDIT)
				//|| event.equals(EVENT_READ)
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
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();

		if (EVENT_MAKER_UPDATE.equals(event)) {
			errorEvent = EVENT_MAKER_EDIT_ERROR;
		} else if (EVENT_READ.equals(event)) {
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
		String forwardName = FORWARD_PREFIX + event;
		DefaultLogger.debug(this, ">>>>>>>>>>> " + forwardName);
		return forwardName;
	}
}