/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/proxy/SBDDNProxyManagerHome.java,v 1.1 2003/08/13 11:27:58 hltan Exp $
 */
package com.integrosys.cms.app.ddn.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the ddn proxy
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:58 $ Tag: $Name: $
 */
public interface SBDDNProxyManagerHome extends EJBHome {
	public SBDDNProxyManager create() throws CreateException, RemoteException;
}
