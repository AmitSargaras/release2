/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;

import javax.ejb.EJBLocalObject;

/**
 * This is the local remote interface to the EBLimitBookingDetail entity bean
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
*/
public interface EBLimitBookingDetailLocal extends EJBLocalObject {

    /**
	    * Get an Limit Booking Detail object representation from persistance
	    *
	    * @return ILimitBookingDetail
	    */
    public ILimitBookingDetail getValue();
	
	
    /**
	    * Set an Limit Booking Detail object representation into persistance
	    *
	    * @param value is of type ILimitBookingDetail
	    * @throws LimitBookingException on errors
	    */
    public void setValue(ILimitBookingDetail value) throws LimitBookingException;
		
	 /**
	     * Delete this Limit Booking Detail.
	     */
    public void delete();
	
	/**
	     * Update status to successful for this Limit Booking Detail.
	     */
    public void success();
	
	/**
	    * Get the Limit Booking Detail Reference ID
	    *
	    * @return long
	    */	
	 public long getCmsRef();

}