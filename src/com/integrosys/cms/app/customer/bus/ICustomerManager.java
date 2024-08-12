/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICustomerManager.java,v 1.1 2003/06/23 10:07:44 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface defines the services that a Customer Manager will perform for
 * the business.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 10:07:44 $ Tag: $Name: $
 */
public interface ICustomerManager extends java.io.Serializable {
	/**
	 * Create a new Customer
	 * 
	 * @param obj is the ICMSCustomer to be created
	 * @return ICMSCustomer containing the newly created customer records
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer createCustomer(ICMSCustomer obj) throws CustomerException;
}