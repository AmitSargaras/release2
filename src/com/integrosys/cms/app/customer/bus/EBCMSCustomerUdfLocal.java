/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSCustomerLocal.java,v 1.2 2003/07/03 07:51:47 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the local interface to the EBCMSCustomer entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:51:47 $ Tag: $Name: $
 */
public interface EBCMSCustomerUdfLocal extends EJBLocalObject {
	/**
	 * Get the contact ID primary key
	 * 
	 * @return long
	 */
	public long getId();
	
	/**
	 * Return an object representation of the Legal Entity information.
	 * 
	 * @return ICMSCustomer
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerUdf getValue();

	/**
	 * Persist a Legal Entity information
	 * 
	 * @param value is of type ICMSCustomer
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws CustomerException on error
	 */
	public void setValue(ICMSCustomerUdf value);
}