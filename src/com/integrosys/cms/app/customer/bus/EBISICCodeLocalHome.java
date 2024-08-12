/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBISICCodeLocalHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBISICCode Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBISICCodeLocalHome extends EJBLocalHome {
	/**
	 * Create a customer ISIC Code information type
	 * 
	 * @param legalID is the Legal ID of type long
	 * @param value is the IISICCode object
	 * @return EBISICCodeLocal
	 * @throws CreateException on error
	 */
	public EBISICCodeLocal create(long legalID, IISICCode value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the ISIC Code ID
	 * @return EBISICCodeLocal
	 * @throws FinderException on error
	 */
	public EBISICCodeLocal findByPrimaryKey(Long pk) throws FinderException;
}