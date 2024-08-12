/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/proxy/SBSCCertificateProxyManagerHome.java,v 1.1 2003/08/08 12:44:58 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the sc certificate
 * proxy manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:44:58 $ Tag: $Name: $
 */
public interface SBSCCertificateProxyManagerHome extends EJBHome {
	public SBSCCertificateProxyManager create() throws CreateException, RemoteException;
}
