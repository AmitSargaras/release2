/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/proxy/SBGenerateRequestProxyManagerHome.java,v 1.1 2003/09/11 05:49:40 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the generate request
 * proxy manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:40 $ Tag: $Name: $
 */
public interface SBGenerateRequestProxyManagerHome extends EJBHome {
	public SBGenerateRequestProxyManager create() throws CreateException, RemoteException;
}
