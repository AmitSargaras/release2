/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Home interface to SBExemptedInstBusManagerBean session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBExemptedInstBusManagerHome extends EJBHome
{
    /**
     * Creates SBExemptedInstBusManager session ejb object.
     *
     * @return SBExemptedInstBusManager
     * @throws RemoteException on errors during remote method call
     */
    public SBExemptedInstBusManager create() throws CreateException, RemoteException;
}