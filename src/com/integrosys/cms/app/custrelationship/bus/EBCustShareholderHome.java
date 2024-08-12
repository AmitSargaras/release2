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
 * Remote home interface for EBCustShareholder.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBCustShareholderHome extends EJBHome
{
   /**
	    * Called by the client to create a Customer Shareholder ejb object.
	    *
	    * @param parentSubProfileID parent sub profile ID
	    * @param custShareholder of type ICustShareholder
	    * @return EBCustShareholder
	    * @throws CreateException on error while creating the ejb
	    * @throws RemoteException on error during remote method call
	    */
    public EBCustShareholder create (long parentSubProfileID, ICustShareholder custShareholder)
        throws CreateException, RemoteException;

    /**
	     * Find the Customer Shareholder ejb object by primary key, the Customer Relationship ID.
	     *
	     * @param custRelnshipIDPK Customer Relationship ID of type Long
	     * @return EBCustShareholder
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public EBCustShareholder findByPrimaryKey (Long custRelnshipIDPK)
        throws FinderException, RemoteException;

    /**
	     * Find the Customer Shareholder ejb object by group ID.
	     *
	     * @param groupID group ID of type long
	     * @return a collection of <code>EBCustShareholder</code>s
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public Collection findByGroupID (long groupID)
        throws FinderException, RemoteException;

    /**
	     * Find all Customer Shareholder.
	     *
	     * @return a collection of <code>EBCustShareholder</code>s
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public Collection findAll() throws FinderException, RemoteException;

}