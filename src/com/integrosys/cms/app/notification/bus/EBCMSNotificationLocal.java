/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/EBCMSNotificationLocal.java,v 1.1 2005/10/24 07:25:32 wltan Exp $
 */

package com.integrosys.cms.app.notification.bus;

import com.integrosys.component.notification.bus.VersionMismatchException;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2005/10/24 07:25:32 $ Tag: $Name: $
 */

public interface EBCMSNotificationLocal extends javax.ejb.EJBLocalObject {

	public ICMSNotification getCMSNotification();

	public void setCMSNotification(ICMSNotification contents) throws VersionMismatchException;

	public void setChilds(ICMSNotification contents);

}