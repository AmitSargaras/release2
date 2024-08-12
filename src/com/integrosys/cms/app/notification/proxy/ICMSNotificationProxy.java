/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/proxy/ICMSNotificationProxy.java,v 1.8 2006/11/02 08:08:16 jzhan Exp $
 */

package com.integrosys.cms.app.notification.proxy;

import java.util.ArrayList;

import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationResult;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.notification.bus.CMSNotificationException;
import com.integrosys.cms.app.notification.bus.OBCMSNotification;
import com.integrosys.component.notification.bus.INotification;
import com.integrosys.component.notification.bus.NotificationCreateException;
import com.integrosys.component.notification.bus.NotificationMessageGeneratorException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/11/02 08:08:16 $ Tag: $Name: $
 */

public interface ICMSNotificationProxy {
	/*
      * 
      */
	void sendEventNotification(OBEventInfo info, java.util.ArrayList receipientList, String[] countryList,
			String segment, String velocityTemplate) throws NotificationCreateException,
			NotificationMessageGeneratorException;

	void sendEventNotification(OBEventInfo info, String[] eventReceipientGroup, String[] countryList, String segment,
			String velocityTemplate) throws NotificationCreateException, NotificationMessageGeneratorException;

	/**
	 * get number of notification by team id
	 * @param teamID
	 * @return int
	 */
	public int countNotifications(long teamID, long userID) throws CMSNotificationException;

	/**
	 * get notification by team id
	 * @param teamID
	 * @return
	 * @throws CMSNotificationException
	 */
	// CR-120 Search Notification
	public ArrayList getNotificationsByTeamID(long userID, long teamID, String status, String legalName, long leID)
			throws CMSNotificationException;

	public PaginationResult getNotificationsByTeamID(long userID, long teamID, String status, String legalName,
			long leID, PaginationBean pgBean) throws CMSNotificationException;

	/**
	 * get notification by id
	 * @param notificationID
	 * @return
	 * @throws CMSNotificationException
	 */
	public INotification getNotificationByID(long notificationID) throws CMSNotificationException;

	/**
	 * update notification list
	 * @param notifyIDArray
	 * @param status
	 * @throws CMSNotificationException
	 */
	public void updateNotificationStatus(String[] notifyIDArray, String status) throws CMSNotificationException;

	/**
	 * delete notification by batch job
	 * @throws CMSNotificationException
	 */
	public void deleteNotification() throws CMSNotificationException;

	/**
	 * put notification into delete list
	 * @param notification
	 * @throws CMSNotificationException
	 */
	public void deleteNotification(OBCMSNotification notification) throws CMSNotificationException;

}
