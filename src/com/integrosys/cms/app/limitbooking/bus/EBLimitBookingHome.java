/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Remote home interface for EBLimitBooking.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBLimitBookingHome extends EJBHome
{
   /**
	    * Called by the client to create a Limit Booking ejb object.
	    *
	    * @param limitBooking of type ILimitBooking
	    * @return EBLimitBooking
	    * @throws CreateException on error while creating the ejb
	    * @throws RemoteException on error during remote method call
	    */
    public EBLimitBooking create (ILimitBooking limitBooking)
        throws CreateException, RemoteException;

    /**
	     * Find the Limit Booking ejb object by primary key, the Limit Booking ID.
	     *
	     * @param limitBookingIDPK Limit Booking ID of type Long
	     * @return EBLimitBooking
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
    public EBLimitBooking findByPrimaryKey (Long limitBookingIDPK)
        throws FinderException, RemoteException;
	
	/**
	     * Find all the Limit Booking ejb object excluded the specified status.
	     *
	     * @param status the status excluded
	     * @return Collection of EBLimitBooking
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */	
	public Collection findAll( String status ) throws FinderException, RemoteException;

}