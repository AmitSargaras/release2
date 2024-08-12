/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/proxy/SBCMSNotificationProxyManagerHome.java,v 1.1 2005/09/22 02:33:12 whuang Exp $
 */

package com.integrosys.cms.app.notification.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/22 02:33:12 $ Tag: $Name: $
 */

public interface SBCMSNotificationProxyManagerHome extends EJBHome {

	/**
	 * create cms notification proxy manager
	 * @return
	 * @throws CreateException
	 * @throws RemoteException
	 */
	public SBCMSNotificationProxyManager create() throws CreateException, RemoteException;

}
