/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/OBCMSNotification.java,v 1.4 2006/03/06 12:41:58 hshii Exp $
 */

package com.integrosys.cms.app.notification.bus;

import org.springframework.beans.BeanUtils;

import com.integrosys.component.notification.bus.INotification;
import com.integrosys.component.notification.bus.OBNotification;

/**
 * CMS Notification OB Purpose: Description:
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/03/06 12:41:58 $ Tag: $Name: $
 */

public class OBCMSNotification extends OBNotification implements ICMSNotification {

	private static final long serialVersionUID = 8459745778622109296L;

	private String leID;

	private String leName;

	private String[] notificationIDs;

	private long userID;

	private String details;

	public OBCMSNotification() {
	}

	public OBCMSNotification(INotification notification) {
		if (notification != null) {
			BeanUtils.copyProperties(notification, this);
		}
	}

	/**
	 * @return Returns the userID.
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * @param userID The userID to set.
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}

	/**
	 * Get the customer's le ID.
	 * 
	 * @returns long
	 */
	public String getLeID() {
		return leID;
	}

	/**
	 * Set the customer's le ID.
	 * 
	 * @param leID - long
	 */
	public void setLeID(String leID) {
		this.leID = leID;
	}

	/**
	 * Get the customer's le name.
	 * 
	 * @returns long
	 */
	public String getLeName() {
		return leName;
	}

	/**
	 * Set the customer's le name.
	 * 
	 * @param leName - String
	 */
	public void setLeName(String leName) {
		this.leName = leName;
	}

	/**
	 * @return Returns the notificationIDs.
	 */
	public String[] getNotificationIDs() {
		return notificationIDs;
	}

	/**
	 * @param notificationIDs The notificationIDs to set.
	 */
	public void setNotificationIDs(String[] notificationIDs) {
		this.notificationIDs = notificationIDs;
	}

	/**
	 * Get the notification details
	 * 
	 * @returns String
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Set the notification details
	 * 
	 * @param details - String
	 */
	public void setDetails(String details) {
		this.details = details;
	}
}
