/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/SBDocumentLocationBusManagerHome.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the documentation
 * location bus manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public interface SBDocumentLocationBusManagerHome extends EJBHome {
	public SBDocumentLocationBusManager create() throws CreateException, RemoteException;
}
