/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Home interface to SBCustRelationshipBusManagerBean session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBCustRelationshipBusManagerHome extends EJBHome
{
    /**
     * Creates SBCustRelationshipBusManager session ejb object.
     *
     * @return SBCustRelationshipBusManager
     * @throws RemoteException on errors during remote method call
     */
    public SBCustRelationshipBusManager create() throws CreateException, RemoteException;
}