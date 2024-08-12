/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

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
 * Describe this class. Purpose: for GMRA Deal Valaution Description: Action
 * class for GMRA Deal Valaution
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class GMRADealValAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if ("maker_update_valuation".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealPrepareValuationCmd();

		}
		else if ("paginate".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PaginateGMRADealPrepareValuationCmd();

		}
		else if ("maker_update_valuation_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealEditValuationCmd();

		}
		else if ("checker_edit_input_valuation".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new GMRADealCheckerReadValuationCmd();
			objArray[1] = new CompareGMRADealCheckerReadCmd();

		}
		else if ("maker_close_input_valuation".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealCheckerReadValuationCmd();

		}
		else if ("checker_approve_edit_valuation".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealCheckerApproveValuationCmd();

		}
		else if ("checker_reject_edit_valuation".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealCheckerRejectValuationCmd();

		}
		else if ("maker_close_input_valuation_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealMakerCancelValuationCmd();

		}
		else if ("maker_edit_valuation_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealMakerReadRejectedValuationCmd();

		}
		else if (EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealPrepareValuationCmd();

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
		return GMRADealValValidator.validateInput((GMRADealValForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("maker_update_valuation_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)
				|| "paginate".equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_update_valuation_confirm".equals(event)) {
			errorEvent = "maker_update_valuation_confirm_error";
		}
		else if ("maker_edit_reject_confirm".equals(event)) {
			errorEvent = "maker_edit_reject_confirm_error";
		}
		else if ("paginate".equals(event)) {
			errorEvent = "paginate_error";
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
		else if ((resultMap.get("isEmpty") != null) && (resultMap.get("isEmpty")).equals("isEmpty")) {
			aPage.setPageReference(getReference(event + "_error"));
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
		if ("maker_update_valuation".equals(event) || "maker_update_valuation_confirm_error".equals(event)
				|| "maker_edit_reject_confirm_error".equals(event) || "paginate".equals(event)
				|| "paginate_error".equals(event)) {
			forwardName = "maker_update_valuation_page";

		}
		else if ("maker_update_valuation_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)) {
			forwardName = "common_submit_page";

		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";

		}
		else if ("checker_edit_input_valuation".equals(event)) {
			forwardName = "checker_edit_input_valuation_page";

		}
		else if ("checker_approve_edit_valuation".equals(event)) {
			forwardName = "common_approve_page";

		}
		else if ("checker_reject_edit_valuation".equals(event)) {
			forwardName = "common_reject_page";

		}
		else if ("maker_close_input_valuation".equals(event)) {
			forwardName = "maker_close_input_valuation_page";

		}
		else if ("maker_close_input_valuation_confirm".equals(event)) {
			forwardName = "common_close_page";

		}
		else if ("maker_edit_valuation_reject".equals(event)) {
			forwardName = "maker_edit_valuation_reject_page";

		}
		else if (EVENT_VIEW.equals(event)) {
			forwardName = "view";

		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";

		}

		return forwardName;
	}

}
