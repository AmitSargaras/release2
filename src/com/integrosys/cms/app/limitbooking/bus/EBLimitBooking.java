/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface to EBLimitBookingBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBLimitBooking extends EJBObject
{
	/**
	    * Get the Limit Booking ID
	    *
	    * @return long
	    */
	public long getLimitBookingID() throws RemoteException;

	/**
	    * Get the version time
	    *
	    * @return long
	    */
    public long getVersionTime() throws RemoteException;
	
	 /**
	    * Method to create child dependants via CMR
	    *
	    * @param value is of type ILimitBooking
	    * @param verTime is the long value of the version time to be compared against.
	    * @param limitBookingID Limit Booking ID
	    * @throws ConcurrentUpdateException, LimitBookingException, RemoteException on error
	    *
	    */
    public void createDependants(ILimitBooking value, long verTime, long limitBookingID) 
		throws ConcurrentUpdateException, LimitBookingException, RemoteException;
	
    /**
	     * Get the Limit Booking business object.
	     *
	     * @return Limit Booking object
	     * @throws RemoteException on error during remote method call
	     */
    public ILimitBooking getValue() throws RemoteException;    

    /**
	     * Set the Limit Booking to this entity.
	     *
	     * @param limitBooking is of type ILimitBooking
	     * @throws LimitBookingException if there is any error during updating of Limit Booking
               * @throws VersionMismatchException if the Limit Booking is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void setValue (ILimitBooking limitBooking)
        throws LimitBookingException, VersionMismatchException, RemoteException;
  
    /**
	     * Set the status to deleted for Limit Booking.
	     *
	     * @param limitBooking of type ILimitBooking
	     * @throws LimitBookingException if there is any error during deleting of Limit Booking
	     * @throws VersionMismatchException if the Limit Booking is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void delete (ILimitBooking limitBooking)
        throws LimitBookingException, VersionMismatchException, RemoteException;
	
	/**
	     * Set the status to successful for Limit Booking.
	     *
	     * @param limitBooking of type ILimitBooking
	     * @throws LimitBookingException if there is any error during updating of status Limit Booking
	     * @throws VersionMismatchException if the Limit Booking is invalid
	     * @throws RemoteException on error during remote method call
	     */
    public void success (ILimitBooking limitBooking)
        throws LimitBookingException, VersionMismatchException, RemoteException;

}