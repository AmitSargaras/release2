/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprice/CommodityPriceAction.java,v 1.4 2005/08/05 10:09:11 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprice;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/05 10:09:11 $ Tag: $Name: $
 */

public class CommodityPriceAction extends CommonAction {
	public final static String EVENT_SELECT = "select";

	public final static String EVENT_PRE_CLOSE = "prepare_close";

	public final static String EVENT_TO_TRACK = "to_track";

	public final static String EVENT_PROCESS_UPDATE = "process_update";

	public final static String EVENT_CLOSE = "close";

	public final static String EVENT_REFRESH = "refresh";

	public final static String EVENT_NOOP = "no_operation";

	public final static String EVENT_PROCESS_NOOP = "process_noop";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_PROCESS) || event.equals(EVENT_PROCESS_UPDATE)
				|| event.equals(EVENT_TO_TRACK) || event.equals(EVENT_PRE_CLOSE) || EVENT_READ.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCommodityPriceCommand();
			objArray[1] = new PrepareCommodityPriceCommand();
		}
		else if (event.equals(EVENT_SUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCommodityPriceCommand();
		}
		else if (event.equals(EVENT_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCommodityPriceCommand();
		}
		else if (event.equals(EVENT_SELECT) || event.equals(EVENT_REFRESH)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommPriceSelectCommand();
		}
		else if (event.equals(EVENT_APPROVE)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCommodityPriceCommand();
		}
		else if (event.equals(EVENT_REJECT)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCommodityPriceCommand();
		}
		else if (event.equals(EVENT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCommodityPriceCommand();
		}
		else if (event.equals(EVENT_NOOP) || event.equals(EVENT_PROCESS_NOOP)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommodityPriceCommand();
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
		return CommodityPriceValidator.validateInput((CommodityPriceForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_REJECT) || event.equals(EVENT_SUBMIT)
				|| event.equals(EVENT_APPROVE) || event.equals(EVENT_UPDATE) || event.equals(EVENT_READ)) {
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
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EVENT_SUBMIT) || event.equals(EVENT_UPDATE)) {
			errorEvent = EVENT_NOOP;
		}
		else if (event.equals(EVENT_PREPARE) || EVENT_READ.equals(event)) {
			errorEvent = EVENT_SELECT;
		}
		else if (event.equals(EVENT_APPROVE) || event.equals(EVENT_REJECT)) {
			errorEvent = EVENT_PROCESS_NOOP;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_PROCESS_UPDATE) || event.equals(EVENT_NOOP)) {
			return EVENT_PREPARE;
		}
		if (event.equals(EVENT_REFRESH)) {
			return EVENT_SELECT;
		}
		if (event.equals(EVENT_PROCESS_NOOP)) {
			return EVENT_PROCESS;
		}
		return event;
	}
}
