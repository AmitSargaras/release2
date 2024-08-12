/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import javax.ejb.SessionContext;

/**
 * This interface defines the list of actions that should be part of event
 * monitoring..
 * 
 * @author $Author: vishal $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/01/24 07:41:16 $ Tag: $Name: $
 */
public interface IMonitor {

	// public void start(String countryCode) throws EventMonitorException;

	public void start(String countryCode, SessionContext context) throws EventMonitorException;
}
