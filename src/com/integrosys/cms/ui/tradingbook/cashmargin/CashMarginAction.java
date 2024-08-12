/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.cashmargin;

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
 * Describe this class. Purpose: for cash margin Description: Action class for
 * cash margin
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class CashMarginAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if ("maker_view_cashmargin_isdacsa".equals(event) || "maker_view_cashmargin_gmra".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginPrepareCmd();

		}
		else if ("maker_update_allcashmargin_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)
				|| "maker_update_allcashmargin_editreject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginEditCmd();

		}
		else if ("checker_edit_allcashmargin".equals(event) || "maker_close_allcashmargin".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginCheckerReadCmd();

		}
		else if ("checker_approve_edit_allcashmargin".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginCheckerApproveCmd();

		}
		else if ("checker_reject_edit_allcashmargin".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginCheckerRejectCmd();

		}
		else if ("maker_close_allcashmargin_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginMakerCancelCmd();

		}
		else if ("maker_edit_allcashmargin_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginMakerReadRejectedCmd();

		}
		else if (EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginPrepareCmd();

		}
		else if ("maker_create_cashmargin_confirm".equals(event) || "maker_update_cashmargin_confirm".equals(event)
				|| "maker_delete_cashmargin".equals(event) || "maker_delete_editreject".equals(event)
				|| "maker_create_editreject_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginAfterCreateCmd();

		}
		else if ("maker_update_cashmargin".equals(event) || "maker_update_editreject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashMarginMakerReadSingleCmd();

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
		return CashMarginValidator.validateInput((CashMarginForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("maker_create_cashmargin_confirm".equals(event) || "maker_update_cashmargin_confirm".equals(event)
				|| "maker_create_editreject_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_create_cashmargin_confirm".equals(event) || "maker_create_editreject_confirm".equals(event)) {
			errorEvent = "maker_create_cashmargin_confirm_error";

		}
		else if ("maker_update_cashmargin_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)) {
			errorEvent = "maker_update_cashmargin_confirm_error";
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
		else if ((resultMap.get("cashMarginNull") != null)
				&& (resultMap.get("cashMarginNull")).equals("cashMarginNull")) {
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
		if ("maker_view_cashmargin_isdacsa".equals(event) || "maker_view_cashmargin_gmra".equals(event)
				|| "maker_create_cashmargin_confirm".equals(event) || "maker_delete_cashmargin".equals(event)
				|| "maker_update_cashmargin_confirm".equals(event) || "maker_delete_editreject".equals(event)
				|| "maker_create_editreject_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)
				|| "maker_update_allcashmargin_confirm_error".equals(event)
				|| "maker_edit_reject_confirm_error".equals(event) || "maker_edit_reject_confirm_error".equals(event)) {
			forwardName = "maker_view_cashmargin_page";

		}
		else if ("maker_update_allcashmargin_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)) {
			forwardName = "common_submit_page";

		}
		else if ("checker_edit_allcashmargin".equals(event)) {
			forwardName = "checker_edit_allcashmargin_page";

		}
		else if ("maker_close_allcashmargin".equals(event)) {
			forwardName = "maker_close_allcashmargin_page";

		}
		else if ("checker_approve_edit_allcashmargin".equals(event)) {
			forwardName = "common_approve_page";

		}
		else if ("checker_reject_edit_allcashmargin".equals(event)) {
			forwardName = "common_reject_page";

		}
		else if ("maker_close_allcashmargin_confirm".equals(event)) {
			forwardName = "common_close_page";

		}
		else if ("maker_edit_allcashmargin_reject".equals(event)) {
			forwardName = "maker_edit_allcashmargin_reject_page";

		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";

		}
		else if (EVENT_VIEW.equals(event)) {
			forwardName = "view";

		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";

		}
		else if ("maker_create_cashmargin".equals(event) || "maker_create_cashmargin_confirm_error".equals(event)
				|| "maker_create_editreject".equals(event)) {
			forwardName = "maker_create_cashmargin_page";

		}
		else if ("maker_update_cashmargin".equals(event) || "maker_update_cashmargin_confirm_error".equals(event)
				|| "maker_update_editreject".equals(event)) {
			forwardName = "maker_update_cashmargin_page";

		}

		return forwardName;
	}

}
