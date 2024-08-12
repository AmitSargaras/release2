package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Session bean home interface for the services provided by the checklist bus
 * manager
 *
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */
public interface SBRecurrentBusManagerHome extends EJBHome {
	public SBRecurrentBusManager create() throws CreateException, RemoteException;
}
