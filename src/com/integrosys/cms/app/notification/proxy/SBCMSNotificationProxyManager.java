/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/proxy/SBCMSNotificationProxyManager.java,v 1.5 2006/11/02 08:08:16 jzhan Exp $
 */
package com.integrosys.cms.app.notification.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationResult;
import com.integrosys.cms.app.notification.bus.CMSNotificationException;
import com.integrosys.cms.app.notification.bus.OBCMSNotification;
import com.integrosys.component.notification.bus.INotification;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/11/02 08:08:16 $ Tag: $Name: $
 */

public interface SBCMSNotificationProxyManager extends EJBObject {

	/**
	 * get number of notification by team id
	 * @param teamID
	 * @return
	 * @throws CMSNotificationException
	 * @throws RemoteException
	 */
	public int countNotifications(long teamID, long userID) throws CMSNotificationException, RemoteException;

	/**
	 * get notification by team id
	 * @param teamID
	 * @return
	 * @throws CMSNotificationException
	 * @throws RemoteException
	 */
	// CR-120 Search Notification
	public ArrayList getNotificationsByTeamID(long userID, long teamID, String status, String legalName, long leID)
			throws CMSNotificationException, RemoteException;

	public PaginationResult getNotificationsByTeamID(long userID, long teamID, String status, String legalName,
			long leID, PaginationBean pgBean) throws CMSNotificationException, RemoteException;

	/**
	 * get notification by id
	 * @param notificationID
	 * @return
	 * @throws CMSNotificationException
	 * @throws RemoteException
	 */
	public INotification getNotificationByID(long notificationID) throws CMSNotificationException, RemoteException;

	/**
	 * update notification status
	 * @param notifyIDArray
	 * @param status
	 * @throws CMSNotificationException
	 * @throws RemoteException
	 */
	public void updateNotificationStatus(String[] notifyIDArray, String status) throws CMSNotificationException,
			RemoteException;

	/**
	 * delete notification by batch job
	 * @throws CMSNotificationException
	 * @throws RemoteException
	 */
	public void deleteNotification() throws CMSNotificationException, RemoteException;

	/**
	 * put notification into delete list
	 * @param notification
	 * @throws CMSNotificationException
	 * @throws RemoteException
	 */
	public void deleteNotification(OBCMSNotification notification) throws CMSNotificationException, RemoteException;

	/**
	 * get notification receipients team id list
	 * @param teamTypeIDList long[]
	 * @param countryList String[]
	 * @param segment String
	 * @return teamIDList Long[]
	 * @throws CMSNotificationException
	 */
	public Long[] getReceipientsTeamIDList(String[] teamTypeIDList, String[] countryList, String segment)
			throws CMSNotificationException, RemoteException;
}
