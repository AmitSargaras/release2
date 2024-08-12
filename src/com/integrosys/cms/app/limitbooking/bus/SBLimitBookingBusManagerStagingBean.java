/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean acts as the facade to the Entity Beans for Limit Booking staging data.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class SBLimitBookingBusManagerStagingBean extends SBLimitBookingBusManagerBean
{
    /**
     * Default Constructor
     */
    public SBLimitBookingBusManagerStagingBean()
    {}
    
    /**
	     * Get staging home interface of EBLimitBooking.
	     *
	     * @return EBLimitBookingHome
	     * @throws LimitBookingException on errors encountered
	     */
	protected EBLimitBookingHome getEBLimitBookingHome()
        throws LimitBookingException
    {
        EBLimitBookingHome ejbHome = (EBLimitBookingHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_LIMIT_BOOKING_STAGING_JNDI, EBLimitBookingHome.class.getName());

        if (ejbHome == null)
            throw new LimitBookingException("EBLimitBookingHome for staging is null!");

        return ejbHome;
    }
	
	

	 
}