/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/SBCCCertificateBusManagerHome.java,v 1.1 2003/08/04 12:53:48 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

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
 * @since $Date: 2003/08/04 12:53:48 $ Tag: $Name: $
 */
public interface SBCCCertificateBusManagerHome extends EJBHome {
	public SBCCCertificateBusManager create() throws CreateException, RemoteException;
}
