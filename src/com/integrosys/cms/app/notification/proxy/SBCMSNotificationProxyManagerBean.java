/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/proxy/SBCMSNotificationProxyManagerBean.java,v 1.5 2006/11/02 08:08:16 jzhan Exp $
 */

package com.integrosys.cms.app.notification.proxy;

import java.util.ArrayList;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationResult;
import com.integrosys.cms.app.notification.bus.CMSNotificationDAO;
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

public class SBCMSNotificationProxyManagerBean implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCMSNotificationProxyManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

	/**
	 * get number of notification by team id
	 * @param teamID
	 * @return
	 * @throws CMSNotificationException
	 */
	public int countNotifications(long teamID, long userID) throws CMSNotificationException {
		CMSNotificationDAO dao = new CMSNotificationDAO();
		return dao.countNotifications(teamID, userID);
	}

	/**
	 * get notifications by team id
	 * @param teamID
	 * @return
	 * @throws CMSNotificationException
	 */
	// CR-120 Search Notification
	public ArrayList getNotificationsByTeamID(long userID, long teamID, String status, String legalName, long leID)
			throws CMSNotificationException {
		CMSNotificationDAO dao = new CMSNotificationDAO();
		return dao.getNotificationsByTeamID(userID, teamID, status, legalName, leID);
	}

	public PaginationResult getNotificationsByTeamID(long userID, long teamID, String status, String legalName,
			long leID, PaginationBean pgBean) throws CMSNotificationException {
		CMSNotificationDAO dao = new CMSNotificationDAO();
		return dao.getNotificationsByTeamID(userID, teamID, status, legalName, leID, pgBean);
	}

	/**
	 * get notification by id
	 * @param notificationID
	 * @return
	 * @throws CMSNotificationException
	 */
	public INotification getNotificationByID(long notificationID) throws CMSNotificationException {
		CMSNotificationDAO dao = new CMSNotificationDAO();
		return dao.getNotificationByID(notificationID);
	}

	/**
	 * update notification status
	 * @param notifyIDList
	 * @param status
	 * @throws CMSNotificationException
	 */
	public void updateNotificationStatus(String[] notifyIDList, String status) throws CMSNotificationException {
		String notifyIDArray = "";
		for (int i = 0; i < notifyIDList.length; i++) {
			String notifyID = notifyIDList[i];
			if (i != notifyIDList.length - 1) {
				notifyIDArray += notifyID + ",";
			}
			else {
				notifyIDArray += notifyID;
			}
		}
		CMSNotificationDAO dao = new CMSNotificationDAO();
		dao.updateNotificationStatus(notifyIDArray, status);
	}

	/**
	 * delete notification by batch job
	 * @throws CMSNotificationException
	 */
	public void deleteNotification() throws CMSNotificationException {
		CMSNotificationDAO dao = new CMSNotificationDAO();
		dao.deleteNotification();
	}

	/**
	 * put notification into delete list
	 * @param notification
	 * @throws CMSNotificationException
	 */
	public void deleteNotification(OBCMSNotification notification) throws CMSNotificationException {
		CMSNotificationDAO dao = new CMSNotificationDAO();
		dao.deleteNotification(notification);
	}

	/**
	 * get notification receipients team id list
	 * @param teamTypeIDList long[]
	 * @param countryList String[]
	 * @param segment String
	 * @return teamIDList Long[]
	 * @throws CMSNotificationException
	 */
	public Long[] getReceipientsTeamIDList(String[] teamTypeIDList, String[] countryList, String segment)
			throws CMSNotificationException {
		CMSNotificationDAO dao = new CMSNotificationDAO();
		return dao.getReceipientsTeamIDList(teamTypeIDList, countryList, segment);
	}
}
