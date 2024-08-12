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
 * Describe this class. Purpose: for GMRA Deal Description: Action class for
 * GMRA Deal
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class GMRADealAction extends CommonAction {

	protected String getDefaultEvent() {
		return "view_gmra_deal_detail";
	}

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("view_gmra_deal_detail".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealPrepareCmd();

		}
		else if ("maker_add_deal".equals(event) || "refresh_maker_add_deal".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealPrepareReadCmd();

		}
		else if ("process_gmra_dealdetails".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealPrepareReadDetailCmd();

		}
		else if ("edit_gmra_dealdetails".equals(event) || "refresh_edit_gmra_dealdetails".equals(event)
				|| "delete_gmra_dealdetails".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealReadDetailCmd();

		}
		else if ("maker_update_dealdetails_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealEditDetailCmd();

		}
		else if ("checker_edit_dealdetails".equals(event) || "maker_close_dealdetails".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealCheckerReadDetailCmd();

		}
		else if ("checker_approve_edit_dealdetails".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealCheckerApproveDetailCmd();

		}
		else if ("checker_reject_edit_dealdetails".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealCheckerRejectDetailCmd();

		}
		else if ("maker_close_dealdetails_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealMakerCancelDetailCmd();

		}
		else if ("maker_edit_dealdetails_reject".equals(event) || "maker_delete_dealdetails_reject".equals(event)
				|| "refresh_maker_edit_dealdetails_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealMakerReadRejectedDetailCmd();

		}
		else if ("maker_add_deal_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealAddNewDetailCmd();

		}
		else if ("delete_gmra_dealdetails_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealDeleteDetailCmd();

		}
		else if ("paginate".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PaginateGMRADealPrepareCmd();

		}
		else if (EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new GMRADealPrepareCmd();

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
		return GMRADealValidator.validateInput((GMRADealForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		System.out.println("//////////////////////event : " + event);
		boolean result = false;
		if ("maker_update_dealdetails_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)
				|| "maker_add_deal_confirm".equals(event)) {
			System.out.println("////////////////////// inside result : " + result);
			result = true;
		}
		System.out.println("//////////////////////result : " + result);
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_update_dealdetails_confirm".equals(event)) {
			errorEvent = "maker_update_dealdetails_confirm_error";
		}
		else if ("maker_edit_reject_confirm".equals(event)) {
			errorEvent = "maker_edit_reject_confirm_error";
		}
		else if ("maker_add_deal_confirm".equals(event)) {
			errorEvent = "maker_add_deal_confirm_error";
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
		else if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("errorMsg")) {
			System.out.println("************** getNextPage() errorMsg *************");
			aPage.setPageReference(getReference("error_message"));
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
		if ("view_gmra_deal_detail".equals(event) || "paginate".equals(event)) {
			forwardName = "view_gmra_deal_detail_page";

		}
		else if ("process_gmra_dealdetails".equals(event)) {
			forwardName = "maker_view_dealdetails_page";

		}
		else if ("edit_gmra_dealdetails".equals(event) || "refresh_edit_gmra_dealdetails".equals(event)
				|| "maker_update_dealdetails_confirm_error".equals(event)) {
			forwardName = "maker_edit_dealdetails_page";

		}
		else if ("maker_add_deal".equals(event) || "refresh_maker_add_deal".equals(event)
				|| "maker_add_deal_confirm_error".equals(event)) {
			forwardName = "maker_add_deal_page";

		}
		else if ("checker_edit_dealdetails".equals(event)) {
			forwardName = "checker_edit_dealdetails_page";

		}
		else if ("maker_update_dealdetails_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)
				|| "maker_add_deal_confirm".equals(event) || "delete_gmra_dealdetails_confirm".equals(event)) {
			forwardName = "common_submit_page";

		}
		else if ("checker_approve_edit_dealdetails".equals(event)) {
			forwardName = "common_approve_page";

		}
		else if ("checker_reject_edit_dealdetails".equals(event)) {
			forwardName = "common_reject_page";

		}
		else if ("maker_close_dealdetails".equals(event) || "delete_gmra_dealdetails".equals(event)
				|| "maker_delete_dealdetails_reject".equals(event)) {
			forwardName = "maker_close_dealdetails_page";

		}
		else if ("maker_close_dealdetails_confirm".equals(event)) {
			forwardName = "common_close_page";

		}
		else if ("maker_edit_dealdetails_reject".equals(event) || "maker_edit_reject_confirm_error".equals(event)
				|| "refresh_maker_edit_dealdetails_reject".equals(event)) {
			forwardName = "maker_edit_dealdetails_reject_page";

		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";

		}
		else if ("error_message".equals(event)) {
			forwardName = "error_message_page";

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
