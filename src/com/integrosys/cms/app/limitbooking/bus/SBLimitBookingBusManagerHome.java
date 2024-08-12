/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Home interface to SBLimitBookingBusManagerBean session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBLimitBookingBusManagerHome extends EJBHome
{
    /**
     * Creates SBLimitBookingBusManager session ejb object.
     *
     * @return SBLimitBookingBusManager
     * @throws RemoteException on errors during remote method call
     */
    public SBLimitBookingBusManager create() throws CreateException, RemoteException;
}