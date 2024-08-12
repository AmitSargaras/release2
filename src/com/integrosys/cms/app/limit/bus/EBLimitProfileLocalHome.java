/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitProfileLocalHome.java,v 1.6 2003/07/10 06:28:03 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBLimitProfile Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/07/10 06:28:03 $ Tag: $Name: $
 */
public interface EBLimitProfileLocalHome extends EJBLocalHome {
	/**
	 * Create a limit profile. Does not create the limits.
	 * 
	 * @param value is the ILimitProfile object
	 * @return EBLimitProfileLocal
	 * @throws CreateException on error
	 */
	public EBLimitProfileLocal create(ILimitProfile value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBLimitProfileLocal
	 * @throws FinderException on error
	 */
	public EBLimitProfileLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by Customer ID and BCA Status
	 * 
	 * @param customerID is of type Long
	 * @param status is of type String
	 * @return Collection of EBLimit
	 * @throws FinderException on error
	 */
	public Collection findByCustomerBCAStatus(Long customerID, String status) throws FinderException;
}