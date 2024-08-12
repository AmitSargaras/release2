/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBKYCLocalHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBKYC Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBKYCLocalHome extends EJBLocalHome {
	/**
	 * Create a customer KYC information type
	 * 
	 * @param legalID is the Legal ID of type long
	 * @param value is the IKYC object
	 * @return EBKYCLocal
	 * @throws CreateException on error
	 */
	public EBKYCLocal create(long legalID, IKYC value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the KYC ID
	 * @return EBKYCLocal
	 * @throws FinderException on error
	 */
	public EBKYCLocal findByPrimaryKey(Long pk) throws FinderException;
}