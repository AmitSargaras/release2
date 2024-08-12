/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/proxy/CustomerProxyFactory.java,v 1.1 2003/07/03 08:54:56 kllee Exp $
 */
package com.integrosys.cms.app.customer.proxy;

/**
 * This factory class will load ICustomerProxy implemenations.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/03 08:54:56 $ Tag: $Name: $
 */
public class CustomerProxyFactory {
	/**
	 * Create a default customer proxy implementation
	 * 
	 * @return ICustomerProxy
	 */
	public static ICustomerProxy getProxy() {
		return new CustomerProxyImpl();
	}
}