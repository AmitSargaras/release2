/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface to the Limit Booking business manager session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBLimitBookingBusManager extends EJBObject
{    
  /**
	     * Gets the Limit Booking by Limit Booking ID.
	     *
	     * @param limitBookingID Limit Booking ID
	     * @return ILimitBooking
	     * @throws RemoteException, LimitBookingException on errors encountered
	     */
    public ILimitBooking getLimitBooking (long limitBookingID)
        throws RemoteException, LimitBookingException;
   
	/**
	 * Creates Limit Booking.
	 *
	 * @param value the Limit Booking of type ILimitBooking
	 * @return ILimitBooking
	 * @throws RemoteException, LimitBookingException on erros creating the Limit Booking
	 */
    public ILimitBooking createLimitBooking (ILimitBooking value)
        throws RemoteException, LimitBookingException;

	/**
	 * Updates the input of Limit Booking.
	 *
	 * @param value the Limit Booking of type ILimitBooking
	 * @return ILimitBooking
	 * @throws RemoteException, LimitBookingException on error updating the Limit Booking
	 */
    public ILimitBooking updateLimitBooking (ILimitBooking value)
        throws RemoteException, LimitBookingException;

	/**
	 * Delete the input of Limit Booking.
	 *
	 * @param value the Limit Booking of type ILimitBooking
	 * @return ILimitBooking
	 * @throws RemoteException, LimitBookingException on error deleting the Limit Booking
	 */
    public ILimitBooking deleteLimitBooking (ILimitBooking value)
        throws RemoteException, LimitBookingException;
	
	/**
	 * Update status to successful of the input of Limit Booking.
	 *
	 * @param value the Limit Booking of type ILimitBooking
	 * @return ILimitBooking
	 * @throws RemoteException, LimitBookingException on error updating the Limit Booking
	 */
    public ILimitBooking successLimitBooking (ILimitBooking value)
        throws RemoteException, LimitBookingException;
			
		
}
