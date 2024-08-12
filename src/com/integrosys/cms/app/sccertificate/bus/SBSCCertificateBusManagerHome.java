/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SBSCCertificateBusManagerHome.java,v 1.1 2003/08/08 12:44:14 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the certificate bus
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:44:14 $ Tag: $Name: $
 */
public interface SBSCCertificateBusManagerHome extends EJBHome {
	public SBSCCertificateBusManager create() throws CreateException, RemoteException;
}
