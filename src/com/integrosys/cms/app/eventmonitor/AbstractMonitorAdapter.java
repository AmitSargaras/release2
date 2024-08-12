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
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/05/03 02:04:23 $ Tag: $Name: $
 */
public abstract class AbstractMonitorAdapter implements IMonitor {

	public void start(String countryCode, SessionContext context) throws EventMonitorException {
	}

	public void start(String[] params, SessionContext context) throws EventMonitorException {
	}
}
