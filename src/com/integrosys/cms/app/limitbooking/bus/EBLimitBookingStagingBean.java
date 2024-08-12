/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;

/**
 * Entity bean implementation for staging Limit Booking entity.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public abstract class EBLimitBookingStagingBean extends EBLimitBookingBean
{	
	
	protected String getFindExcludeStatus ()
    {
        return "";
    }
	
	/**
	* Method to get staging EB Local Home for EBLimitBookingDetail
	*
	* @return EBLimitBookingDetailLocalHome
	* @throws LimitBookingException on errors
	*/
	protected EBLimitBookingDetailLocalHome getEBLimitBookingDetailLocalHome() throws LimitBookingException {
		EBLimitBookingDetailLocalHome home = (EBLimitBookingDetailLocalHome)BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_BOOKING_DETAIL_LOCAL_STAGING_JNDI, EBLimitBookingDetailLocalHome.class.getName());

		if(null != home) {
			return home;
		}
		else {
			throw new LimitBookingException ("EBLimitBookingDetailLocalHome for staging is null!");
		}
	}
	
	
   
}