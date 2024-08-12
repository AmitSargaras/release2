/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

/**
 * Factory class that instantiate the IEventMonitorController..
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/23 01:25:14 $ Tag: $Name: $
 */
public class EventMonitorControllerFactory {
	/**
	 * Default Constructor
	 */
	public EventMonitorControllerFactory() {
	}

	/**
	 * Get the Event Monitor Controller Proxy impl..
	 * @return IEventMonitorController - the custodian proxy manager
	 */
	public static IEventMonitorController getEventMonitorController() {
		return new EventMonitorControllerImpl();
	}
}