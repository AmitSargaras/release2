/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface to EBCustShareholderBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBCustShareholder extends EJBObject
{
    /**
	     * Get the Customer Shareholder business object.
	     *
	     * @return Customer Shareholder object
	     * @throws RemoteException on error during remote method call
	     */
    public ICustShareholder getValue() throws RemoteException;

    /**
	     * Set the Customer Shareholder to this entity.
	     *
	     * @param custShareholder is of type ICustShareholder
	     * @throws VersionMismatchException if the Customer Shareholder is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void setValue (ICustShareholder custShareholder)
        throws VersionMismatchException, RemoteException;

  
    /**
	     * Set the status to deleted for Customer Shareholder.
	     *
	     * @param custShareholder of type ICustShareholder
	     * @throws VersionMismatchException if the Customer Shareholder is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void setStatusDeleted (ICustShareholder custShareholder)
        throws VersionMismatchException, RemoteException;

}