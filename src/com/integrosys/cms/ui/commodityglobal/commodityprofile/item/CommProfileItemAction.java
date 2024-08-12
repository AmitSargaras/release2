/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/CommProfileItemAction.java,v 1.3 2004/06/24 10:22:17 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CommProfileAction;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.list.CommProfileListAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/24 10:22:17 $ Tag: $Name: $
 */

public class CommProfileItemAction extends CommProfileAction {
	public static final String EVENT_ADD = "add";

	public static final String EVENT_PREPARE_ADD = "prepare_add";

	public static final String EVENT_PREPARE_NOOP = "prepare_noop";

	public static final String EVENT_REFRESH = "refresh";

	public static final String EVENT_READ_RETURN = "read_return";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_ADD)) {
			objArray = new ICommand[1];
			objArray[0] = new AddCommProfileItemCommand();
		}
		else if (event.equals(EVENT_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCommProfileItemCommand();
		}
		else if (event.equals(EVENT_PREPARE)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCommProfileItemCommand();
			objArray[1] = new PrepareCommProfileItemCommand();
		}
		else if (event.equals(EVENT_PREPARE_ADD) || event.equals(EVENT_PREPARE_NOOP)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommProfileItemCommand();
		}
		else if (event.equals(EVENT_REFRESH)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshCommProfileItemCommand();
			objArray[1] = new PrepareCommProfileItemCommand();
		}
		else if (event.equals(EVENT_READ)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadCommProfileItemCommand();
		}
		else if (event.equals(EVENT_READ_RETURN)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCommProfileItemCommand();
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
		return CommProfileItemValidator.validateInput((CommProfileItemForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_ADD) || event.equals(EVENT_UPDATE) || event.equals(EVENT_REFRESH)) {
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
		if (EVENT_READ_RETURN.equals(event)) {
			aPage.setPageReference(getReturnReference((String) resultMap.get("from_event")));
		}
		else if (EVENT_READ.equals(event)) {
			aPage.setPageReference(getReadReference((String) resultMap.get("from_event")));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_ADD)) {
			errorEvent = EVENT_PREPARE_NOOP;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_CANCEL) || event.equals(EVENT_ADD) || event.equals(EVENT_UPDATE)) {
			return CommProfileListAction.EVENT_UPDATE_RETURN;
		}
		if (event.equals(EVENT_PREPARE_NOOP) || event.equals(EVENT_PREPARE_ADD) || event.equals(EVENT_REFRESH)) {
			return EVENT_PREPARE;
		}
		return event;
	}

	private String getReturnReference(String event) {
		if (event.equals(CommProfileListAction.EVENT_PRE_CLOSE)) {
			return CommProfileListAction.EVENT_CLOSE_RETURN;
		}
		if (event.equals(CommProfileListAction.EVENT_TO_TRACK)) {
			return CommProfileListAction.EVENT_TRACK_RETURN;
		}
		if (event.equals(CommProfileListAction.EVENT_PROCESS)) {
			return CommProfileListAction.EVENT_PROCESS_RETURN;
		}
		if (event.equals(CommProfileListAction.EVENT_READ)) {
			return CommProfileListAction.EVENT_READ_RETURN;
		}
		return event;
	}

	private String getReadReference(String from_event) {
		if (from_event.equals(CommProfileListAction.EVENT_PRE_CLOSE)
				|| from_event.equals(CommProfileListAction.EVENT_TO_TRACK)
				|| from_event.equals(CommProfileListAction.EVENT_READ)) {
			return "view_commProfile_item";
		}
		if (from_event.equals(CommProfileListAction.EVENT_PROCESS)) {
			return "process_commProfile_item";
		}
		return from_event;
	}
}
