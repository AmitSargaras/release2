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
 * @author $Author: vishal $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/01/24 08:00:05 $ Tag: $Name: $
 */
public interface SBEventMonitorFirstPassHome extends EJBHome {
	public SBEventMonitorFirstPass create() throws CreateException, RemoteException;
}
