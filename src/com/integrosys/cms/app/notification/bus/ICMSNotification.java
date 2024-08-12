/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/ICMSNotification.java,v 1.4 2006/03/06 12:41:58 hshii Exp $
 */
package com.integrosys.cms.app.notification.bus;

import com.integrosys.component.notification.bus.INotification;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/03/06 12:41:58 $ Tag: $Name: $
 */
public interface ICMSNotification extends INotification {

	/**
	 * Get the customer's le ID.
	 * 
	 * @returns long
	 */
	public String getLeID();

	/**
	 * Set the customer's le ID.
	 * 
	 * @param leID - long
	 */
	public void setLeID(String leID);

	/**
	 * Get the customer's le name.
	 * 
	 * @returns long
	 */
	public String getLeName();

	/**
	 * Set the customer's le name.
	 * 
	 * @param leName - String
	 */
	public void setLeName(String leName);

	/**
	 * Get the notification details
	 * 
	 * @returns String
	 */
	public String getDetails();

	/**
	 * Set the notification details
	 * 
	 * @param details - String
	 */
	public void setDetails(String details);
}
