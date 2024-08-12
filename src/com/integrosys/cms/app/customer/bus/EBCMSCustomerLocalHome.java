/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSCustomerLocalHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCMSCustomer Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBCMSCustomerLocalHome extends EJBLocalHome {
	/**
	 * Create a customer Legal Entity information type
	 * 
	 * @param value is the ICMSCustomer object
	 * @return EBCMSCustomerLocal
	 * @throws CreateException on error
	 */
	public EBCMSCustomerLocal create(ICMSCustomer value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the Legal Entity ID
	 * @return EBCMSCustomerLocal
	 * @throws FinderException on error
	 */
	public EBCMSCustomerLocal findByPrimaryKey(Long pk) throws FinderException;
}