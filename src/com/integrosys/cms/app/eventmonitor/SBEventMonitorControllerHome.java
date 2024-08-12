/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the Event Monitor
 * Controller..
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/23 01:25:14 $ Tag: $Name: $
 */
public interface SBEventMonitorControllerHome extends EJBHome {
	public SBEventMonitorController create() throws CreateException, RemoteException;
}
