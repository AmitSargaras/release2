/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.techinfra.beanloader.BeanController;

/**
 * This factory creates actual and staging SBLimitBookingBusManager.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class LimitBookingBusManagerFactory
{
    /**
     * Default Constructor
     */
    public LimitBookingBusManagerFactory()
    {}
	
	 /**
	    * Get the SB for the actual storage of LimitBooking
	    *
	    * @return SBLimitBookingManager
	    * @throws LimitBookingException on errors
	    */
    public static SBLimitBookingBusManager getActualLimitBookingBusManager() throws LimitBookingException {
        SBLimitBookingBusManager home = (SBLimitBookingBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_LIMIT_BOOKING_MGR_JNDI, SBLimitBookingBusManagerHome.class.getName());
                
	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new LimitBookingException("SBLimitBookingBusManager for Actual is null!");
	    }
    }
	
    /**
	    * Get the SB for the staging storage of LimitBooking
	    *
	    * @return SBLimitBookingManager
	    * @throws LimitBookingException on errors
	    */
    public static SBLimitBookingBusManager getStagingLimitBookingBusManager() throws LimitBookingException {
        SBLimitBookingBusManager home = (SBLimitBookingBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_LIMIT_BOOKING_MGR_STAGING_JNDI, SBLimitBookingBusManagerHome.class.getName());
                
	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new LimitBookingException("SBLimitBookingBusManager for Staging is null!");
	    }
    }

}