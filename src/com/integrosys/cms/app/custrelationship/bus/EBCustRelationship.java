/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface to EBCustRelationshipBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBCustRelationship extends EJBObject
{
    /**
	     * Get the Customer Relationship business object.
	     *
	     * @return Customer Relationship object
	     * @throws RemoteException on error during remote method call
	     */
    public ICustRelationship getValue() throws RemoteException;

    /**
	     * Set the Customer Relationship to this entity.
	     *
	     * @param custRelnship is of type ICustRelationship
	     * @throws VersionMismatchException if the Customer Relationship is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void setValue (ICustRelationship custRelnship)
        throws VersionMismatchException, RemoteException;

   
    /**
	     * Set the status to deleted for Customer Relationship.
	     *
	     * @param custRelnship of type ICustRelationship
	     * @throws VersionMismatchException if the Customer Relationship is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void setStatusDeleted (ICustRelationship custRelnship)
        throws VersionMismatchException, RemoteException;

}