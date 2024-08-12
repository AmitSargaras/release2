/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.exemptedinst;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Home interface to the SBExemptedInstProxy session bean
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBExemptedInstProxyHome extends EJBHome
{
    /**
     * Default Create method
     *
     * @throws CreateException on error creating the ejb object
     * @throws RemoteException on error during remote method call
     */
    public SBExemptedInstProxy create() throws CreateException, RemoteException;
}