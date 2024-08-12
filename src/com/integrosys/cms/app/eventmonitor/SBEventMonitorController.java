/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.EJBObject;

/**
 * Session bean remote interface for the services provided by the Event Monitor
 * Controller
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/05/03 02:04:23 $ Tag: $Name: $
 */
public interface SBEventMonitorController extends EJBObject {
	// public void monitorDocumentExpiry() throws RemoteException;
	public void startMonitorJob(String[] args) throws RemoteException;

	public void startMonitorJob(String monitorJobClassName, String countryCode) throws RemoteException;

	//public void startReportJob(String scope, String country, String centre, String date, String reportMasterID) throws RemoteException;
    public void startReportJob(HashMap map) throws RemoteException;

    public void startAdhocReport() throws RemoteException;

	public void startReportSQLSearcher(String[] args) throws RemoteException;
}
