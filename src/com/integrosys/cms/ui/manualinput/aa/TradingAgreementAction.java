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
 * Describe this class. Purpose: for Trading Agreement Description: Action class
 * for Trading Agreement
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class TradingAgreementAction extends AADetailAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		DefaultLogger.debug(this,"**************getCommandChain event : " + event);
		if ("maker_view_agreement".equals(event) || EVENT_VIEW.equals(event) || "checker_view_agreement".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TradingAgreementPrepareCmd();

		}
		else if ("maker_add_agreement_confirm".equals(event) || "maker_add_editreject_confirm".equals(event)
				|| "maker_update_agreement_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TradingAgreementSaveDetailCmd();

		}
		else if ("maker_add_agreement".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TradingAgreementPrepareCmd();

		}
		else if ("maker_update_agreement".equals(event) || "maker_update_editreject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TradingAgreementReadDetailCmd();

		}
		else if ("cancel".equals(event) || "read_return".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnAADetailCmd();

		}
		else if ("refresh_maker_add_agreement".equals(event) || "refresh_maker_update_agreement".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TradingAgreementPrepareRefreshCmd();

		}
		else if ("prepare_add_threshold".equals(event) || "prepare_update_threshold".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCurTradingAgreementCmd();
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
		return TradingAgreementValidator.validateInput((TradingAgreementForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
//		System.out.println("**************isValidationRequired event : " + event);
		boolean result = false;
		if ("maker_add_agreement_confirm".equals(event) || "maker_add_editreject_confirm".equals(event)
				|| "maker_update_agreement_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
//		System.out.println("**************getErrorEvent event : " + event);
		String errorEvent = getDefaultEvent();
		if ("maker_add_agreement_confirm".equals(event) || "maker_add_editreject_confirm".equals(event)) {
			errorEvent = "maker_add_agreement_confirm_error";
		}
		else if ("maker_update_agreement_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)) {
			errorEvent = "maker_update_agreement_confirm_error";
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
//		System.out.println("**************getNextPage event : " + event);
		Page aPage = new Page();
		String preEvent = (String) resultMap.get("preEvent");
//		System.out.println("************** preEvent : " + preEvent);
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			//System.out.println("************** getNextPage() wip *************"
			// );

			aPage.setPageReference(getReference("work_in_process"));
			return aPage;

		}
		else if ("cancel".equals(event) || "read_return".equals(event)) {

			aPage.setPageReference(getReference("return_" + preEvent));
			return aPage;

		}
		else if ("maker_add_agreement_confirm".equals(event) || "maker_add_editreject_confirm".equals(event)
				|| "maker_update_agreement_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)
				|| "maker_delete_agreement".equals(event) || "maker_delete_editreject".equals(event)) {

			aPage.setPageReference(getReference("return_" + preEvent));
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
//		System.out.println("**************getReference event : " + event);
		String forwardName = "submit_fail";

		if ("maker_view_agreement".equals(event) || EVENT_VIEW.equals(event)) {
			forwardName = "view";

		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";

		}
		else if ("maker_add_agreement".equals(event) || "maker_add_agreement_confirm_error".equals(event)
				|| "maker_add_editreject".equals(event) || "refresh_maker_add_agreement".equals(event)) {
			forwardName = "maker_add_page";

		}
		else if ("maker_update_agreement".equals(event) || "maker_update_agreement_confirm_error".equals(event)
				|| "maker_update_editreject".equals(event) || "refresh_maker_update_agreement".equals(event)) {
			forwardName = "maker_update_page";

		}
		else if ("prepare_add_threshold".equals(event)) {
			forwardName = "prepare_add_threshold";

		}
		else if ("prepare_update_threshold".equals(event)) {
			forwardName = "prepare_update_threshold";

		}
		else if ("view_threshold".equals(event)) {
			forwardName = "view_threshold";

		}
		else if ("return_maker_edit_aadetail".equals(event) || "return_maker_add_aadetail".equals(event)
				|| "return_maker_edit_aadetail_reject".equals(event)) {
			forwardName = event;

		}
		else if ("checker_view_agreement".equals(event)) {
			forwardName = event;

		}

		return forwardName;
	}

}
