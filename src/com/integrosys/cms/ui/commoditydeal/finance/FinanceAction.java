/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/FinanceAction.java,v 1.6 2004/09/08 01:50:47 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.commoditydeal.NavigationCommodityDealcommand;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/09/08 01:50:47 $ Tag: $Name: $
 */

public class FinanceAction extends CommodityDealAction {
	public static final String EVENT_PRINT_APPROVAL_SHEET = "print_approval_sheet";

	public static final String EVENT_PRINT_AUTHORISATION_RELEASE = "print_authorisation_release";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this + " getCommandChain", "event is: " + event);
		ICommand objArray[] = null;
		if (EVENT_PROCESS_RETURN.equals(event) || EVENT_READ_RETURN.equals(event) || EVENT_CLOSE_RETURN.equals(event)
				|| EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadFinanceCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadFinanceCommand();
			objArray[1] = new PrepareFinanceCommand();
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB) || event.endsWith(EVENT_PREPARE)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessingFinanceCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareFinanceCommand();
		}
		else if (EVENT_EDIT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditFinanceCommand();
			objArray[1] = new NavigationCommodityDealcommand();
		}
		else if (EVENT_PRINT_APPROVAL_SHEET.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrintApprovalSheetCommand();
		}
		else if (EVENT_PRINT_AUTHORISATION_RELEASE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrintAuthorisationReleaseCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}

		return objArray;
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
		return FinanceValidator.validateInput((FinanceForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		if (EVENT_EDIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB) || event.endsWith(EVENT_PREPARE)) {
			errorEvent = EVENT_PREPARE_FORM;
		}
		else if (EVENT_APPROVE.equals(event) || EVENT_REJECT.equals(event)) {
			errorEvent = EVENT_PROCESS_RETURN;
		}

		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_EDIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB) || event.endsWith(EVENT_PREPARE)) {
			result = true;
		}
		return result;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
		if (event.endsWith(EVENT_PREPARE)) {
			aPage.setPageReference(getAddReference(event));
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB)) {
			aPage.setPageReference(getUpdateReference(event));
		}
		else if (event.endsWith(EVENT_VIEW)) {
			aPage.setPageReference(getViewReference(event));
		}
		else {
			aPage.setPageReference(getReference(event, (String) resultMap.get("previous_event")));
		}
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event, String previous_event) {
		if (EVENT_PREPARE_FORM.equals(event)) {
			return EVENT_PREPARE_UPDATE;
		}
		if (EVENT_PREPARE_CLOSE_DEAL.equals(event)) {
			return EVENT_PREPARE_CLOSE;
		}
		if (EVENT_READ_RETURN.equals(event) && (previous_event != null) && previous_event.equals(EVENT_USER_PROCESS)) {
			return EVENT_USER_PROCESS;
		}
		return event;
	}

	private String getAddReference(String event) {
		if (event.startsWith(CommodityDealConstant.SETTLEMENT)) {
			return "add_" + CommodityDealConstant.SETTLEMENT;
		}
		if (event.startsWith(CommodityDealConstant.RELEASE)) {
			return "add_" + CommodityDealConstant.RELEASE;
		}
		return event;
	}

	private String getUpdateReference(String event) {
		if (event.startsWith(CommodityDealConstant.PURCHASE_SALE)) {
			return "update_" + CommodityDealConstant.PURCHASE_SALE;
		}
		if (event.startsWith(CommodityDealConstant.HEDGING)) {
			return "update_" + CommodityDealConstant.HEDGING;
		}
		if (event.startsWith(CommodityDealConstant.SETTLEMENT)) {
			return "update_" + CommodityDealConstant.SETTLEMENT;
		}
		if (event.startsWith(CommodityDealConstant.RELEASE)) {
			return "update_" + CommodityDealConstant.RELEASE;
		}
		return event;
	}

	private String getViewReference(String event) {
		if (event.startsWith(CommodityDealConstant.PURCHASE_SALE)) {
			return "view_" + CommodityDealConstant.PURCHASE_SALE;
		}
		if (event.startsWith(CommodityDealConstant.HEDGING)) {
			return "view_" + CommodityDealConstant.HEDGING;
		}
		if (event.startsWith(CommodityDealConstant.SETTLEMENT)) {
			return "view_" + CommodityDealConstant.SETTLEMENT;
		}
		if (event.startsWith(CommodityDealConstant.RELEASE)) {
			return "view_" + CommodityDealConstant.RELEASE;
		}
		return event;
	}
}
