/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/SBGenerateRequestBusManagerHome.java,v 1.1 2003/09/11 05:48:56 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

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
 * @since $Date: 2003/09/11 05:48:56 $ Tag: $Name: $
 */
public interface SBGenerateRequestBusManagerHome extends EJBHome {
	public SBGenerateRequestBusManager create() throws CreateException, RemoteException;
}
