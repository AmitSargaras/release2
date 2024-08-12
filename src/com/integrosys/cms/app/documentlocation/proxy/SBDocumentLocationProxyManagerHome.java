/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/proxy/SBDocumentLocationProxyManagerHome.java,v 1.1 2004/02/17 02:12:19 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the document
 * location proxy manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:19 $ Tag: $Name: $
 */
public interface SBDocumentLocationProxyManagerHome extends EJBHome {
	public SBDocumentLocationProxyManager create() throws CreateException, RemoteException;
}
