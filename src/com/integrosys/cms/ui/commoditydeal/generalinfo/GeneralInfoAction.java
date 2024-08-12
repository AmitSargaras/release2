/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/GeneralInfoAction.java,v 1.12 2004/10/13 06:02:55 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

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
import com.integrosys.cms.ui.commoditydeal.PrepareUserProcessCommand;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2004/10/13 06:02:55 $ Tag: $Name: $
 */

public class GeneralInfoAction extends CommodityDealAction {
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this + " getCommandChain", "event is: " + event);
		ICommand objArray[] = null;
		if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)
				|| EVENT_PREPARE_ADD_DEAL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadGeneralInfoCommand();
			objArray[1] = new PrepareGeneralInfoCommand();
		}
		else if (EVENT_READ.equals(event) || EVENT_PREPARE_CLOSE.equals(event) || EVENT_TRACK.equals(event)
				|| EVENT_PROCESS.equals(event) || EVENT_PREPARE_CLOSE_DEAL.equals(event)
				|| EVENT_USER_TO_TRACK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadGeneralInfoCommand();
		}
		else if (EVENT_USER_PROCESS.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadGeneralInfoCommand();
			objArray[1] = new PrepareUserProcessCommand();
		}
		else if (EVENT_REFRESH_COMMENT.equals(event) || EVENT_PROCESS_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnGeneralInfoCommand();
			objArray[1] = new PrepareUserProcessCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CLOSE_RETURN.equals(event)
				|| EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnGeneralInfoCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnGeneralInfoCommand();
			objArray[1] = new PrepareGeneralInfoCommand();
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB) || event.endsWith(EVENT_PREPARE)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessingGeneralInfoCommand();
		}
		else if (event.endsWith(EVENT_DELETE_ITEM)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteGeneralInfoCommand();
			objArray[1] = new PrepareGeneralInfoCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareGeneralInfoCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshGeneralInfoCommand();
			objArray[1] = new PrepareGeneralInfoCommand();
		}
		else if (EVENT_EDIT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new NavigationCommodityDealcommand();
			objArray[1] = new EditGeneralInfoCommand();
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
		return GeneralInfoValidator.validateInput((GeneralInfoForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		if (EVENT_UPDATE.equals(event) || EVENT_SUBMIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| event.endsWith(EVENT_DELETE_ITEM) || event.endsWith(EVENT_PREPARE) || event.equals(EVENT_EDIT)
				|| EVENT_REFRESH.equals(event)) {
			errorEvent = EVENT_PREPARE_FORM;
		}
		else if (EVENT_APPROVE.equals(event) || EVENT_REJECT.equals(event) || EVENT_FORWARD_DEAL.equals(event)) {
			errorEvent = EVENT_PROCESS_RETURN;
		}

		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_UPDATE.equals(event) || EVENT_SUBMIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| event.endsWith(EVENT_DELETE_ITEM) || event.endsWith(EVENT_PREPARE) || EVENT_APPROVE.equals(event)
				|| EVENT_EDIT.equals(event) || EVENT_REJECT.equals(event) || EVENT_FORWARD_DEAL.equals(event)
				|| EVENT_REFRESH.equals(event)) {
			result = true;
		}
		return result;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if ((resultMap.get("amountEx") != null) && (resultMap.get("amountEx").equals("amountEx"))) {
			aPage.setPageReference("amountEx");
			return aPage;
		}
		if ((resultMap.get("CloseDealErr") != null) && resultMap.get("CloseDealErr").equals("CloseDealErr")) {
			aPage.setPageReference("CloseDealErr");
			return aPage;
		}
		if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			DefaultLogger.debug(this, "event is: " + event);
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
		if (EVENT_PREPARE_FORM.equals(event) || event.endsWith(EVENT_DELETE_ITEM)
				|| EVENT_PREPARE_ADD_DEAL.equals(event) || EVENT_REFRESH.equals(event) || EVENT_EDIT.equals(event)) {
			return EVENT_PREPARE_UPDATE;
		}
		if (EVENT_PREPARE_CLOSE_DEAL.equals(event)) {
			return EVENT_PREPARE_CLOSE;
		}
		if (EVENT_USER_PROCESS.equals(event)
				|| (EVENT_REFRESH_COMMENT.equals(event) && (previous_event != null) && previous_event
						.equals(EVENT_USER_PROCESS))) {
			return EVENT_PROCESS;
		}
		if (EVENT_USER_TO_TRACK.equals(event) || EVENT_REFRESH_COMMENT.equals(event)) {
			return EVENT_TRACK;
		}
		return event;
	}

	private String getAddReference(String event) {
		if (event.startsWith(CommodityDealConstant.CASH_DEPOSIT)) {
			return "add_" + CommodityDealConstant.CASH_DEPOSIT;
		}
		return event;
	}

	private String getUpdateReference(String event) {
		if (event.startsWith(CommodityDealConstant.CASH_DEPOSIT)) {
			return "update_" + CommodityDealConstant.CASH_DEPOSIT;
		}
		return event;
	}

	private String getViewReference(String event) {
		if (event.startsWith(CommodityDealConstant.CASH_DEPOSIT)) {
			return "view_" + CommodityDealConstant.CASH_DEPOSIT;
		}
		return event;
	}

}
