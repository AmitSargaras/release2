/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.app.limitbooking.bus;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * This is the Local Home interface for the EBLimitBookingDetail Entity Bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
*/
public interface EBLimitBookingDetailLocalHome extends EJBLocalHome {

    /**
	* Create a local Limit Booking Detail Entity Bean
	*
	* @param value is the ILimitBookingDetail object
	* @return EBLimitBookingDetailLocal
	* @throws CreateException on error
	*/
	public EBLimitBookingDetailLocal create(ILimitBookingDetail value, long limitBookingIDPK) throws CreateException;
		
    /**
	    * Find by Primary Key.
	    *
	    * @param pk is Long value of the primary key
	    * @return EBLimitBookingDetailLocal
	    * @throws FinderException on error
	    */
    public EBLimitBookingDetailLocal findByPrimaryKey(Long pk) throws FinderException;

    /**
	    * Find by booking category type and limitBooking ID
	    *
	    * @param bkgCatType is the limit booking category type
	    * @param limitBookingID is the Long value of the limitBooking ID
	    * @param status the status to be excluded in this find
	    * @return Collection of EBLimitBookingDetailLocal
	    * @throws FinderException on error
	    */
    public Collection findByLimitBookingIDAndType(String bkgCatType, long limitBookingID, String status) throws FinderException;
	
	 /**
	    * Find by limitBooking ID
	    *
	    * @param limitBookingID is the Long value of the limitBooking ID
	    * @param status the status to be excluded in this find
	    * @return Collection of EBLimitBookingDetailLocal
	    * @throws FinderException on error
	    */
    public Collection findByLimitBookingID(long limitBookingID, String status) throws FinderException;
}