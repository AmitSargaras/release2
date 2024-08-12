/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Session bean remote interface for the services provided by the Event Monitor
 * Controller
 * 
 * @author $Author: vishal $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/01/24 08:00:05 $ Tag: $Name: $
 */
public interface SBEventMonitorFirstPass extends EJBObject {
	// public void monitorDocumentExpiry() throws RemoteException;

	public void startMonitorJob(String monitorJobClassName, String countryCode) throws RemoteException;

	public void startCollateralThresholdJob(Long[] collateralIDList) throws RemoteException;

}
