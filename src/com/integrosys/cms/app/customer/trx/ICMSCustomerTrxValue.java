/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/ICMSCustomerTrxValue.java,v 1.1 2003/06/25 12:44:42 kllee Exp $
 */
package com.integrosys.cms.app.customer.trx;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CMS Customer trx value.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/25 12:44:42 $ Tag: $Name: $
 */
public interface ICMSCustomerTrxValue extends ICMSTrxValue {
	/**
	 * Get the customer busines entity
	 * 
	 * @return ICMSCustomer
	 */
	public ICMSCustomer getCustomer();

	/**
	 * Get the staging customer business entity
	 * 
	 * @return ICMSCustomer
	 */
	public ICMSCustomer getStagingCustomer();

	/**
	 * Set the customer busines entity
	 * 
	 * @param value is of type ICMSCustomer
	 */
	public void setCustomer(ICMSCustomer value);

	/**
	 * Set the staging customer business entity
	 * 
	 * @param value is of type ICMSCustomer
	 */
	public void setStagingCustomer(ICMSCustomer value);
}