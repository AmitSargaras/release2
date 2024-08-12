package com.integrosys.cms.app.limitbooking.proxy;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
* Home interface to the SBLimitProxy session bean
* 
* @author $Author$
* @version $Revision$
* @since $Date$
* Tag: $Name$
*/
public interface SBLimitBookingProxyHome extends EJBHome {
    /**
    * Default Create method
    *
    * @throws CreateException on error
    * @throws RemoteException
    */
    public SBLimitBookingProxy create() throws CreateException, RemoteException;
}       