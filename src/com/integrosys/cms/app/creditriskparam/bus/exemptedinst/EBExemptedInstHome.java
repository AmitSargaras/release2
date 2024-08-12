/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Remote home interface for EBExemptedInst.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBExemptedInstHome extends EJBHome
{
   /**
	    * Called by the client to create a Exempted Institution ejb object.
	    *
	    * @param exemptedInst of type IExemptedInst
	    * @return EBExemptedInst
	    * @throws CreateException on error while creating the ejb
	    * @throws RemoteException on error during remote method call
	    */
    public EBExemptedInst create (IExemptedInst exemptedInst)
        throws CreateException, RemoteException;

    /**
	     * Find the Exempted Institution ejb object by primary key, the Exempted Institution ID.
	     *
	     * @param exemptedInstIDPK Exempted Institution ID of type Long
	     * @return EBExemptedInst
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public EBExemptedInst findByPrimaryKey (Long exemptedInstIDPK)
        throws FinderException, RemoteException;
    
	/**
	     * Find the Exempted Institution ejb object by group ID.
	     *
	     * @param groupID group ID of type long
	     * @return a collection of <code>EBExemptedInst</code>s
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public Collection findByGroupID (long groupID)
        throws FinderException, RemoteException;

	/**
	     * Find theExempted Institution ejb object by sub profile ID.
	     *
	     * @param subProfileID sub profile ID of type long
	     * @return a <code>EBExemptedInst</code>s
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public EBExemptedInst findBySubProfileID (long subProfileID)
        throws FinderException, RemoteException;
		
    /**
	     * Find all Exempted Institution.
	     *
	     * @return a collection of <code>EBExemptedInst</code>s
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public Collection findAll() throws FinderException, RemoteException;

}