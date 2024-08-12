/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/documentexpiry/SBDocExpiryHome.java,v 1.1 2005/11/18 10:25:50 lini Exp $
 */
package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session Bean for Doc Expiry
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/11/18 10:25:50 $ Tag: $Name: $
 */
public interface SBDocExpiryHome extends EJBHome {

	public SBDocExpiry create() throws CreateException, RemoteException;
}
