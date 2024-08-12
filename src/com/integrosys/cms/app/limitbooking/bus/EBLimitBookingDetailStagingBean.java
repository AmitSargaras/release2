/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.EntityContext;

/**
 * This entity bean represents the persistence for Limit Booking Detail staging bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public abstract class EBLimitBookingDetailStagingBean extends EBLimitBookingDetailBean {

	/**
	* The Entity Context
	*/
    protected EntityContext _context = null;
		
	/**
	* Default Constructor
	*/
	public EBLimitBookingDetailStagingBean() {}


}