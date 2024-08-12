/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/notification/ViewNotificationCommand.java,v 1.5 2005/11/11 07:16:30 whuang Exp $
 */
package com.integrosys.cms.ui.notification;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.notification.proxy.CMSNotificationFactory;
import com.integrosys.component.notification.bus.INotification;

/**
 * @author $Author $<br>
 * @version $Revision $
 * @since $Date $ Tag: $Name: $
 */

public class ViewNotificationCommand extends AbstractCommand {

	/**
	 * Default Constructor
	 */
	public ViewNotificationCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "notificationID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "status", "java.lang.String", REQUEST_SCOPE },
				{ ICMSConstant.PARAM_NOTIFICATION_START_INDEX, "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "notification", "com.integrosys.component.notification.INotification", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ ICMSConstant.PARAM_NOTIFICATION_START_INDEX, "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		try {
			long notificationID = Long.parseLong((String) map.get("notificationID"));
			String status = (String) map.get("status");
			INotification notification = CMSNotificationFactory.getCMSNotificationProxy().getNotificationByID(
					notificationID);
			result.put("notification", notification);
			result.put("event", map.get("event"));
			result.put(ICMSConstant.PARAM_NOTIFICATION_START_INDEX, map
					.get(ICMSConstant.PARAM_NOTIFICATION_START_INDEX));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}
