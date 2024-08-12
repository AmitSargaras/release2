/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/SBDDNBusManagerHome.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the ddn bus manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:25 $ Tag: $Name: $
 */
public interface SBDDNBusManagerHome extends EJBHome {
	public SBDDNBusManager create() throws CreateException, RemoteException;
}
