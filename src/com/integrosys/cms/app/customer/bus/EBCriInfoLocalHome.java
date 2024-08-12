/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBContactLocalHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBContact Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBCriInfoLocalHome extends EJBLocalHome {
	/**
	 * Create a customer Contact information type
	 * 
	 * @param legalID the credit application ID of type long
	 * @param value is the IContact object
	 * @return EBContactLocal
	 * @throws CreateException on error
	 */
	public EBCriInfoLocal create(ICriInfo value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is String value of the contact ID
	 * @return EBContactLocal
	 * @throws FinderException on error
	 */
	public EBCriInfoLocal findByPrimaryKey(Long pk) throws FinderException;
}