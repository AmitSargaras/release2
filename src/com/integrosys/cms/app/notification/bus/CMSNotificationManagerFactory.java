/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/CMSNotificationManagerFactory.java,v 1.1 2005/10/24 07:25:32 wltan Exp $
 */

package com.integrosys.cms.app.notification.bus;

/**
 * This factory creates INotificationManager
 * @author $Author: wltan $
 * @version $
 * @since $Date: 2005/10/24 07:25:32 $
 */

public class CMSNotificationManagerFactory {

	/**
	 * gets the AbstractNotificationManager.
	 * @return AbstractNotificationManager - an INotificationManager object;
	 */
	public static ICMSNotificationManager getManager() {
		return new CMSNotificationManagerImpl();
	}
}