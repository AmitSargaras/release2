/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/SBCustodianBusManagerHome.java,v 1.1 2003/06/09 11:38:02 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the custodian bus
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/09 11:38:02 $ Tag: $Name: $
 */
public interface SBCustodianBusManagerHome extends EJBHome {
	public SBCustodianBusManager create() throws CreateException, RemoteException;
}
