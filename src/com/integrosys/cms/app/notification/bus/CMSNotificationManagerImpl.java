/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/CMSNotificationManagerImpl.java,v 1.1 2005/10/24 07:25:32 wltan Exp $
 */
package com.integrosys.cms.app.notification.bus;

/**
 * NotificationManagerImpl is a delegator class for NotificationTypeManger component.
 * This class simply delegates all calls to SBNotificationManager.
 * Factory class use creates an instance of NotificationManagerImpl as an object of
 * INotificationManager to the client.
 * @author $Author: wltan $<br>
 * @version  $
 * @since $Date: 2005/10/24 07:25:32 $
 * Tag: $Name:  $
 */

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.component.notification.JNDIConstants;
import com.integrosys.component.notification.bus.INotification;
import com.integrosys.component.notification.bus.NotificationCreateException;
import com.integrosys.component.notification.bus.NotificationMessageGeneratorException;
import com.integrosys.component.notification.bus.SBNotificationManager;

public class CMSNotificationManagerImpl implements ICMSNotificationManager {
	/**
	 * creates a new notification.
	 * @param notification the notification to be created. - notification must
	 *        contain its notificationTypeData (not null). Otherwise method
	 *        throws an exception.
	 *        <ol>
	 *        - The notificationTypeData can be of two types
	 *        <li>completely default settings defined for one of the pre-defined
	 *        NotificationTypes.</li>
	 *        <li>partially modified (overriden) data from one of the existing
	 *        notificationTypeData. in which case the
	 *        notificationTypeData.getNotificatoinTypeID() shoud be the ID of an
	 *        existing notificationType that is being overriden.</li>
	 *        <ul>
	 *        Overriding Notificatoin Data :
	 *        <li>Notification Data must not be null
	 *        <li>any field that is set to 'null' is considered to use its
	 *        default value.
	 *        <li>Collection fields such as getNotificationRecipients() are
	 *        considered as an atomic field that means, we can't override few
	 *        elements of the Collection fields.
	 *        </ul>
	 *        </ol>
	 * @return the created notification.
	 * @throws NotificationCreateException is a wrapper exception. Thrown when
	 *         any exception occured while creating the notification.
	 */
	public INotification createNotification(INotification notification) throws NotificationCreateException,
			NotificationMessageGeneratorException {
		try {
			return getManager().createNotification(notification);
		}
		catch (RemoteException e) {
			throw new NotificationCreateException(e);
		}
	}

	public SBNotificationManager getManager() {
		if (_manager == null) {
			initManager();
		}
		return _manager;
	}

	private SBNotificationManager _manager;

	private void initManager() {
		_manager = (SBNotificationManager) BeanController.getEJB(JNDIConstants.SB_NOTIFICATION_MANAGER_HOME,
				JNDIConstants.SB_NOTIFICATION_MANAGER_HOME_PATH);
	}
}
