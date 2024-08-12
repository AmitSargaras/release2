/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/documentexpiry/SBDocExpiry.java,v 1.1 2005/11/18 10:25:50 lini Exp $
 */
package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * Session Bean for Doc Expiry
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/11/18 10:25:50 $ Tag: $Name: $
 */
public interface SBDocExpiry extends EJBObject {

	public void processEvent(String code, Object o, IRuleParam ruleParam, String eventName)
			throws EventMonitorException, RemoteException;

}
