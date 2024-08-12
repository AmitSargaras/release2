/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/SBCMSNotificationManagerBean.java,v 1.3 2006/11/08 09:03:15 hmbao Exp $
 */
package com.integrosys.cms.app.notification.bus;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.component.notification.bus.INotification;
import com.integrosys.component.notification.bus.NotificationCreateException;
import com.integrosys.component.notification.bus.OBNotificationRecipient;
import com.integrosys.component.notification.bus.SBNotificationManagerBean;

/**
 * @author $Author: hmbao $
 * @version $
 * @since $Date: 2006/11/08 09:03:15 $ Tag: $Name: $
 */
public class SBCMSNotificationManagerBean extends SBNotificationManagerBean implements SessionBean {

	// persist methods

	/**
	 * creates a new notification.
	 * @param notification the notification to be created. by default,
	 *        notification is considered to use the default notification type
	 *        settings, but in case new notification type is provided, the
	 *        default 'notification type' data is overrriden. note:
	 *        notification.getNotificationTypeData() returns the 'notification
	 *        type' data.
	 * @return the created notification.
	 * @throws NotificationCreateException is a wrapper exception. Thrown when
	 *         any exception occured while creating the notification.
	 */
	public INotification createNotificationPersist(INotification notification) throws NotificationCreateException {
		try {
			OBCMSNotification ob = new OBCMSNotification(notification);
			EBCMSNotificationLocal localNotification = getCMSNotificationLocalHome().create(ob);
			ob.setNotificationID(localNotification.getCMSNotification().getNotificationID());

			new CMSNotificationDAO().updateNotification(ob);
			// _fillForeignKeysIntoChilds(ob);

			localNotification.setChilds(ob);
			ICMSNotification notif = localNotification.getCMSNotification();
			Collection nReci = notif.getNotificationRecipients();

			if (nReci == null) {
				return notif;
			}

			for (Iterator iter = nReci.iterator(); iter.hasNext();) {
				OBNotificationRecipient reci = (OBNotificationRecipient) iter.next();
				reci.setNotificationID(notification.getNotificationID());
			}

			return notif;

			// return localNotification.getNotification();

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new NotificationCreateException(e);
		}
	}

	private EBCMSNotificationLocalHome getCMSNotificationLocalHome() throws NotificationCreateException {
		EBCMSNotificationLocalHome ejbHome = (EBCMSNotificationLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CMS_NOTIFICATION_LOCAL_JNDI, EBCMSNotificationLocalHome.class.getName());

		if (ejbHome == null) {
			throw new NotificationCreateException("EBCMSNotificationLocalHome is null!");
		}

		return ejbHome;
	}
}
