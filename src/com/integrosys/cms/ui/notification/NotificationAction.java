/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/notification/NotificationAction.java,v 1.5 2005/10/20 09:06:50 hshii Exp $
 */
package com.integrosys.cms.ui.notification;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author $<br>
 * @version $Revision $
 * @since $Date $ Tag: $Name: $
 */

public class NotificationAction extends CommonAction {

	public static final String EVENT_SEARCH_NOTIFICATION = "search";

	public static final String EVENT_VIEW_NOTIFICATION = "view";

	public static final String EVENT_VIEW_DELETED_NOTIFICATION = "view_deleted";

	public static final String EVENT_DELETE_NOTIFICATION = "delete";

	public static final String EVENT_LIST_DELETED_NOTIFICATION = "list_deleted_notification";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return ICommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_SEARCH_NOTIFICATION.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SearchNotificationsByTeamIDCommand();
		}
		else if (EVENT_VIEW_NOTIFICATION.equals(event) || EVENT_VIEW_DELETED_NOTIFICATION.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewNotificationCommand();
		}
		else if (EVENT_DELETE_NOTIFICATION.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteNotificationCommand();
		}
		else if (EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareNotificationCommand();
		}
		return (objArray);
	}

	/**
	 * method to return the default event
	 * @return String
	 */
	protected String getDefaultEvent() {
		return EVENT_PREPARE;
	}

	public boolean isValidationRequired(String event) {
		boolean validationRequired = false;
		if (event.equals(EVENT_DELETE_NOTIFICATION) || event.equals(EVENT_SEARCH_NOTIFICATION)) {
			validationRequired = true;
		}
		return validationRequired;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		ActionErrors errors = NotificationValidator.validateInput((NotificationForm) aForm, locale);
		return errors;
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
		String status = (String) resultMap.get("status");
		Page aPage = new Page();
		if ((status != null) && status.equals(ICMSConstant.STATE_NOTIFICATION_DELETED)) {
			aPage.setPageReference(EVENT_LIST_DELETED_NOTIFICATION);
		}
		else {
			String nextPage = getReference(event);
			aPage.setPageReference(nextPage);
		}
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = "";
		if (EVENT_DELETE_NOTIFICATION.equals(event)) {
			errorEvent = EVENT_SEARCH_NOTIFICATION;
		}
		else if (EVENT_SEARCH_NOTIFICATION.equals(event)) {
			errorEvent = EVENT_CANCEL;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_CANCEL)) {
			return EVENT_SEARCH_NOTIFICATION;
		}
		else {
			return event;
		}
	}

}
