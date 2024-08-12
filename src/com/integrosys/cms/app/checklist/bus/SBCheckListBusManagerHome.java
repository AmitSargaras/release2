/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/SBCheckListBusManagerHome.java,v 1.1 2003/06/24 11:36:00 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the checklist bus
 * manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */
public interface SBCheckListBusManagerHome extends EJBHome {
	public SBCheckListBusManager create() throws CreateException, RemoteException;
}
