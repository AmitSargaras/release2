/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/notification/DeleteNotificationCommand.java,v 1.2 2005/11/11 07:16:30 whuang Exp $
 */
package com.integrosys.cms.ui.notification;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.notification.bus.OBCMSNotification;
import com.integrosys.cms.app.notification.proxy.CMSNotificationFactory;
import com.integrosys.cms.app.notification.proxy.ICMSNotificationProxy;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/11/11 07:16:30 $ Tag: $Name: $
 */
public class DeleteNotificationCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public DeleteNotificationCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "notification", "com.integrosys.cms.app.notification.bus.OBCMSNotification",
				FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "notificationList", "java.util.ArrayList", SERVICE_SCOPE } });
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
		OBCMSNotification notification = (OBCMSNotification) map.get("notification");
		try {
			String[] notificationIDs = notification.getNotificationIDs();
			if (notificationIDs != null) {
				ICMSNotificationProxy proxy = CMSNotificationFactory.getCMSNotificationProxy();
				// proxy.updateNotificationStatus(notificationIDs,ICMSConstant.
				// STATE_NOTIFICATION_DELETED);
				proxy.deleteNotification(notification);
			}
			result.put("notificationList", null);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException();
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}
