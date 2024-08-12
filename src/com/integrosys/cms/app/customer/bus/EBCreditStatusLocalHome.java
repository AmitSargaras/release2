/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCreditStatusLocalHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCreditStatus Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBCreditStatusLocalHome extends EJBLocalHome {
	/**
	 * Create a customer Credit Status information type
	 * 
	 * @param legalID is the Legal ID of type long
	 * @param value is the ICreditStatus object
	 * @return EBCreditStatusLocal
	 * @throws CreateException on error
	 */
	public EBCreditStatusLocal create(long legalID, ICreditStatus value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the Credit Status ID
	 * @return EBCreditStatusLocal
	 * @throws FinderException on error
	 */
	public EBCreditStatusLocal findByPrimaryKey(Long pk) throws FinderException;
}