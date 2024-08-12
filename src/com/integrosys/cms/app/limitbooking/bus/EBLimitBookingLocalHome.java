/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Local home interface for EBLimitBookingBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBLimitBookingLocalHome extends EJBLocalHome
{

    /**
	     * Find the local ejb object by primary key, the Limit Booking ID.
	     *
	     * @param limitBookingIDPK Limit Booking ID
	     * @return local Limit Booking ejb object
	     * @throws FinderException on error while finding the ejb
	     */
    public EBLimitBookingLocal findByPrimaryKey (Long limitBookingIDPK)
        throws FinderException;

}