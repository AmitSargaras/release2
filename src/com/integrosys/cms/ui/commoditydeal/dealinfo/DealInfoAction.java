/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/DealInfoAction.java,v 1.7 2006/03/23 08:39:08 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;
import com.integrosys.cms.ui.commoditydeal.NavigationCommodityDealcommand;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/03/23 08:39:08 $ Tag: $Name: $
 */

public class DealInfoAction extends CommodityDealAction {

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
			objArray[0] = new ReadDealInfoCommand();
			objArray[1] = new PrepareDealInfoCommand();
		}
		else if (EVENT_READ.equals(event) || EVENT_PREPARE_CLOSE.equals(event) || EVENT_TRACK.equals(event)
				|| EVENT_PROCESS.equals(event) || EVENT_PREPARE_CLOSE_DEAL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDealInfoCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareDealInfoCommand();
		}
		else if (EVENT_EDIT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new NavigationCommodityDealcommand();
			objArray[1] = new EditDealInfoCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshDealInfoCommand();
			objArray[1] = new PrepareDealInfoCommand();
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
		return DealInfoValidator.validateInput((DealInfoForm) aForm, locale);
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
		aPage.setPageReference(getReference(event, (String) resultMap.get("previous_event")));
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event, String previous_event) {
		if (EVENT_PREPARE_FORM.equals(event) || EVENT_REFRESH.equals(event) || EVENT_PREPARE_ADD_DEAL.equals(event)) {
			return EVENT_PREPARE_UPDATE;
		}
		if (EVENT_PREPARE_CLOSE_DEAL.equals(event)) {
			return EVENT_PREPARE_CLOSE;
		}
		if (EVENT_READ.equals(event) && (previous_event != null) && previous_event.equals(EVENT_USER_PROCESS)) {
			return EVENT_USER_PROCESS;
		}
		return event;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		if (EVENT_EDIT.equals(event) || EVENT_REFRESH.equals(event)) {
			errorEvent = EVENT_PREPARE_FORM;
		}

		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_EDIT.equals(event)) {
			result = true;
		}
		return result;
	}
}
