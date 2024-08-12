/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/proxy/SBDataProtectionHome.java,v 1.2 2003/06/19 13:19:51 jtan Exp $
 */
package com.integrosys.cms.app.dataprotection.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Purpose: Home interface to SessionBean SBDataProtectionHome
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public interface SBDataProtectionHome extends EJBHome {

	/**
	 * @return Remote interface to SBProtectionBean
	 * @throws CreateException - if session bean cannot be created
	 * @throws RemoteException - if remote call encounters error
	 */
	public SBDataProtection create() throws CreateException, RemoteException;
}
