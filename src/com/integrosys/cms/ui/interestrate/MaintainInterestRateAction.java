/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/MaintainInterestRateAction.java,v 1 2007/02/08 Jerlin Exp $
 */
package com.integrosys.cms.ui.interestrate;

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
 * Describe this class. Purpose: for Interest Rate Description: Action class for
 * Interest Rate
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class MaintainInterestRateAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {

		}
		else if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateListCommand();

		}
		else if ("maker_edit_interestrate_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateMakerEditCmd();

		}
		else if ("checker_edit_interestrate".equals(event) || "maker_close_interestrate".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateCheckerReadCmd();

		}
		else if ("checker_approve_edit_interestrate".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateCheckerApproveEditCmd();

		}
		else if ("checker_reject_edit_interestrate".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateCheckerRejectEditCmd();

		}
		else if ("maker_close_interestrate_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateMakerCancelEditCmd();

		}
		else if ("maker_edit_interestrate_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateMakerReadRejectedCmd();

		}
		else if (EVENT_VIEW.equals(event) || "checker_view".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateListCommand();

		}
		else if ("to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new InterestRateCheckerReadCmd();

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
		return MaintainInterestRateValidator.validateInput((MaintainInterestRateForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_edit_interestrate_confirm") || event.equals(EVENT_LIST) || event.equals("checker_view")) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_edit_interestrate_confirm".equals(event)) {
			errorEvent = "maker_edit_interestrate_confirm_error";
		}
		else if (EVENT_LIST.equals(event) || "checker_view".equals(event)) {
			errorEvent = "interestrate_list_error";
		}

		return errorEvent;
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
			//System.out.println("************** getNextPage() wip *************"
			// );

			aPage.setPageReference(getReference("work_in_process"));
			return aPage;

		}
		else {
			// System.out.println(
			// "************** getNextPage() event *************");

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
		if (EVENT_PREPARE.equals(event) || "interestrate_list_error".equals(event)) {
			forwardName = "after_prepare";

		}
		else if (EVENT_LIST.equals(event) || "maker_edit_interestrate_confirm_error".equals(event)) {
			forwardName = "after_list";

		}
		else if (EVENT_SEARCH.equals(event)) {
			forwardName = "after_search";

		}
		else if ("maker_edit_interestrate_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)) {
			forwardName = "maker_edit_interestrate_confirm";

		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";

		}
		else if ("checker_edit_interestrate".equals(event)) {
			forwardName = "checker_edit_interestrate_page";

		}
		else if ("checker_approve_edit_interestrate".equals(event)) {
			forwardName = "common_approve_page";

		}
		else if ("checker_reject_edit_interestrate".equals(event)) {
			forwardName = "common_reject_page";

		}
		else if ("maker_close_interestrate".equals(event)) {
			forwardName = "maker_close_interestrate_page";

		}
		else if ("maker_close_interestrate_confirm".equals(event)) {
			forwardName = "common_close_page";

		}
		else if ("maker_edit_interestrate_reject".equals(event)) {
			forwardName = "maker_edit_interestrate_page";

		}
		else if (EVENT_VIEW.equals(event) || "checker_view".equals(event)) {
			forwardName = "view";

		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";
		}

		return forwardName;
	}

}
