/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/EBCMSNotificationLocalHome.java,v 1.1 2005/10/24 07:25:32 wltan Exp $
 */

package com.integrosys.cms.app.notification.bus;

import java.util.Collection;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2005/10/24 07:25:32 $ Tag: $Name: $
 */

public interface EBCMSNotificationLocalHome extends javax.ejb.EJBLocalHome {
	public EBCMSNotificationLocal create(ICMSNotification contents) throws javax.ejb.CreateException;

	public EBCMSNotificationLocal findByPrimaryKey(java.lang.Long primaryKey) throws javax.ejb.FinderException;

	public Collection findAll() throws javax.ejb.FinderException;
}