/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

/**
 * Session bean remote interface for the services provided by the Event Monitor
 * Controller
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/25 05:54:25 $ Tag: $Name: $
 */
public interface SBEventManager extends EJBObject {
	public void fireEvent(String eventID, List params) throws RemoteException;

}
