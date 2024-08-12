/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCreditGradeLocalHome.java,v 1.2 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCreditGrade Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBCreditGradeLocalHome extends EJBLocalHome {
	/**
	 * Create a customer Credit Grade information type
	 * 
	 * @param legalID is the Legal ID of type long
	 * @param value is the ICreditGrade object
	 * @return EBCreditGradeLocal
	 * @throws CreateException on error
	 */
	public EBCreditGradeLocal create(long legalID, ICreditGrade value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the Credit Grade ID
	 * @return EBCreditGradeLocal
	 * @throws FinderException on error
	 */
	public EBCreditGradeLocal findByPrimaryKey(Long pk) throws FinderException;
}