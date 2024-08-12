/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface to the custrelationship business manager session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBCustRelationshipBusManager extends EJBObject
{    
   /**
	     * Gets list of Customer Relationship by group ID.
	     *
	     * @param groupID group ID
	     * @return array of ICustRelationship
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationship[] getCustRelationshipByGroupID (long groupID)
        throws CustRelationshipException, RemoteException;

    /**
	     * Creates the input list of Customer Relationship.
	     *
	     * @param parentSubProfileID parent sub profile ID
	     * @param value array of ICustRelationship to be created
	     * @return array of ICustRelationship
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationship[] createCustRelationship (long parentSubProfileID, ICustRelationship[] value)
        throws CustRelationshipException, RemoteException;

   /**
	     * Updates the input list of Customer Relationship.
	     *
	     * @param parentSubProfileID parent sub profile ID
	     * @param value array of ICustRelationship to be updated
	     * @return array of ICustRelationship
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationship[] updateCustRelationship (long parentSubProfileID, ICustRelationship[] value)
        throws CustRelationshipException, RemoteException;

	/**
	     * Gets list of Customer Shareholder by group ID.
	     *
	     * @param groupID group ID
	     * @return array of ICustShareholder
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholder[] getCustShareholderByGroupID (long groupID)
        throws CustRelationshipException, RemoteException;

    /**
	     * Creates the input list of Customer Shareholder.
	     *
	     * @param parentSubProfileID parent sub profile ID
	     * @param value array of ICustShareholder to be created
	     * @return array of ICustShareholder
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholder[] createCustShareholder (long parentSubProfileID, ICustShareholder[] value)
        throws CustRelationshipException, RemoteException;

   /**
	     * Updates the input list of Customer Shareholder.
	     *
	     * @param parentSubProfileID parent sub profile ID
	     * @param value array of ICustShareholder to be updated
	     * @return array of ICustShareholder
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholder[] updateCustShareholder (long parentSubProfileID, ICustShareholder[] value)
        throws CustRelationshipException, RemoteException;
		
}
