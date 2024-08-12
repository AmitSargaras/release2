/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Remote home interface for EBCustRelationship.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBCustRelationshipHome extends EJBHome
{
   /**
	    * Called by the client to create a Customer Relationship ejb object.
	    *
	    * @param parentSubProfileID parent sub profile ID
	    * @param custRelnship of type ICustRelationship
	    * @return EBCustRelationship
	    * @throws CreateException on error while creating the ejb
	    * @throws RemoteException on error during remote method call
	    */
    public EBCustRelationship create (long parentSubProfileID, ICustRelationship custRelnship)
        throws CreateException, RemoteException;

    /**
	     * Find the Customer Relationship ejb object by primary key, the Customer Relationship ID.
	     *
	     * @param custRelnshipIDPK Customer Relationship ID of type Long
	     * @return EBCustRelationship
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public EBCustRelationship findByPrimaryKey (Long custRelnshipIDPK)
        throws FinderException, RemoteException;

    /**
	     * Find the Customer Relationship ejb object by group ID.
	     *
	     * @param groupID group ID of type long
	     * @return a collection of <code>EBCustRelationship</code>s
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public Collection findByGroupID (long groupID)
        throws FinderException, RemoteException;

    /**
	     * Find all Customer Relationship.
	     *
	     * @return a collection of <code>EBCustRelationship</code>s
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public Collection findAll() throws FinderException, RemoteException;

}