/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface to EBExemptedInstBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBExemptedInst extends EJBObject
{
    /**
	     * Get the Exempted Institution business object.
	     *
	     * @return Exempted Institution object
	     * @throws RemoteException on error during remote method call
	     */
    public IExemptedInst getValue() throws RemoteException;

    /**
	     * Set the Exempted Institution to this entity.
	     *
	     * @param exemptedInst is of type IExemptedInst
	     * @throws VersionMismatchException if the Exempted Institution is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void setValue (IExemptedInst exemptedInst)
        throws VersionMismatchException, RemoteException;
   
    /**
	     * Set the status to deleted for Exempted Institution.
	     *
	     * @param exemptedInst of type IExemptedInst
	     * @throws VersionMismatchException if the Exempted Institution is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void setStatusDeleted (IExemptedInst exemptedInst)
        throws VersionMismatchException, RemoteException;

}