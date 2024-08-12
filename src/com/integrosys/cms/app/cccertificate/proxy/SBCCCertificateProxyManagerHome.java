/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/proxy/SBCCCertificateProxyManagerHome.java,v 1.1 2003/08/04 12:55:05 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the certificate
 * proxy manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/04 12:55:05 $ Tag: $Name: $
 */
public interface SBCCCertificateProxyManagerHome extends EJBHome {
	public SBCCCertificateProxyManager create() throws CreateException, RemoteException;
}
