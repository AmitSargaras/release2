/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Describe this class. Purpose: for Threshold Rating Description: Action class
 * for Threshold Rating
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class ThresholdRatingAction extends TradingAgreementAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if ("prepare_add_threshold".equals(event) || "prepare_update_threshold".equals(event)
				|| "view_threshold".equals(event) || EVENT_VIEW.equals(event) || "checker_view_threshold".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ThresholdRatingPrepareCmd();

		}
		else if ("refresh_prepare_add_threshold_new".equals(event)
				|| "refresh_prepare_update_threshold_new".equals(event)
				|| "refresh_prepare_add_threshold_update".equals(event)
				|| "refresh_prepare_update_threshold_update".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ThresholdRatingPrepareAddRefreshCmd();

		}
		else if ("prepare_add_threshold_new_confirm".equals(event)
				|| "prepare_update_threshold_new_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ThresholdRatingSaveSingleDetailCmd();

		}
		else if ("prepare_add_threshold_update".equals(event) || "prepare_update_threshold_update".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ThresholdRatingReadSingleDetailCmd();

		}
		else if ("prepare_add_threshold_update_confirm".equals(event)
				|| "prepare_update_threshold_update_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ThresholdRatingSaveUpdateDetailCmd();

		}
		else if ("prepare_add_threshold_delete".equals(event) || "prepare_update_threshold_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ThresholdRatingAfterDeleteCmd();

		}
		else if ("prepare_add_threshold_confirm".equals(event) || "prepare_add_editreject_confirm".equals(event)
				|| "prepare_update_threshold_confirm".equals(event)
				|| "prepare_update_editreject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ThresholdRatingSaveDetailCmd();

		}
		else if ("return_prepare_add_threshold".equals(event) || "return_prepare_update_threshold".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnAgreementDetailCmd();

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
		DefaultLogger.debug(this, "Inside validate input child class");
		return ThresholdRatingValidator.validateInput((ThresholdRatingForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("prepare_add_threshold_new_confirm".equals(event) || "prepare_update_threshold_new_confirm".equals(event)
				|| "prepare_add_threshold_update_confirm".equals(event)
				|| "prepare_update_threshold_update_confirm".equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("prepare_add_threshold_new_confirm".equals(event)) {
			errorEvent = "prepare_add_threshold_new_confirm_error";
		}
		else if ("prepare_update_threshold_new_confirm".equals(event)) {
			errorEvent = "prepare_update_threshold_new_confirm_error";
		}
		else if ("prepare_add_threshold_update_confirm".equals(event)) {
			errorEvent = "prepare_add_threshold_update_confirm_error";
		}
		else if ("prepare_update_threshold_update_confirm".equals(event)) {
			errorEvent = "prepare_update_threshold_update_confirm_error";
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
		String preEvent = (String) resultMap.get("preEvent");
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			//System.out.println("************** getNextPage() wip *************"
			// );

			aPage.setPageReference(getReference("work_in_process"));
			return aPage;

		}
		else if ((resultMap.get("alreadyExist") != null) && (resultMap.get("alreadyExist")).equals("alreadyExist")) {
//			System.out.println("************** getNextPage() event.substring(0,event.length()-8) *************"+ event.substring(0, event.length() - 8));
			aPage.setPageReference(getReference(event.substring(0, event.length() - 8)));
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
		if ("prepare_add_threshold".equals(event) || "prepare_update_threshold".equals(event)
				|| EVENT_VIEW.equals(event) || "prepare_add_threshold_new_confirm".equals(event)
				|| "prepare_update_threshold_new_confirm".equals(event)
				|| "prepare_add_threshold_update_confirm".equals(event)
				|| "prepare_update_threshold_update_confirm".equals(event)
				|| "prepare_add_threshold_delete".equals(event) || "prepare_update_threshold_delete".equals(event)) {
			forwardName = "prepare_threshold_list";

		}
		else if ("prepare_add_threshold_new".equals(event) || "prepare_update_threshold_new".equals(event)
				|| "prepare_add_threshold_new_confirm_error".equals(event)
				|| "prepare_update_threshold_new_confirm_error".equals(event)
				|| "refresh_prepare_add_threshold_new".equals(event)
				|| "refresh_prepare_update_threshold_new".equals(event)) {
			forwardName = "maker_add_page";

		}
		else if ("prepare_add_threshold_update".equals(event) || "prepare_update_threshold_update".equals(event)
				|| "prepare_add_threshold_update_confirm_error".equals(event)
				|| "prepare_update_threshold_update_confirm_error".equals(event)
				|| "refresh_prepare_add_threshold_update".equals(event)
				|| "refresh_prepare_update_threshold_update".equals(event)) {
			forwardName = "maker_update_page";

		}
		else if ("prepare_add_threshold_confirm".equals(event) || "prepare_add_editreject_confirm".equals(event)
				|| "return_prepare_add_threshold".equals(event)) {
			forwardName = "return_prepare_add_threshold";

		}
		else if ("prepare_update_threshold_confirm".equals(event) || "prepare_update_editreject_confirm".equals(event)
				|| "return_prepare_update_threshold".equals(event)) {
			forwardName = "return_prepare_update_threshold";

		}
		else if ("return_view_threshold".equals(event)) {
			forwardName = "return_view_threshold";

		}
		else if ("view_threshold".equals(event)) {
			forwardName = "view_threshold";

		}
		else if ("checker_view_threshold".equals(event)) {
			forwardName = "checker_view_threshold";

		}

		return forwardName;
	}

}
