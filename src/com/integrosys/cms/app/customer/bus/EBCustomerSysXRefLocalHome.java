/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCustomerSysXRefLocalHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCustomerSysXRef Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBCustomerSysXRefLocalHome extends EJBLocalHome {
	/**
	 * Create a customer CustomerSysXRef information type
	 * 
	 * @param customerID is the Legal ID of type long
	 * @param value is the ICustomerSysXRef object
	 * @return EBCustomerSysXRefLocal
	 * @throws CreateException on error
	 */
	public EBCustomerSysXRefLocal create(long customerID, ICustomerSysXRef value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the CustomerSysXRef ID
	 * @return EBCustomerSysXRefLocal
	 * @throws FinderException on error
	 */
	public EBCustomerSysXRefLocal findByPrimaryKey(Long pk) throws FinderException;
}