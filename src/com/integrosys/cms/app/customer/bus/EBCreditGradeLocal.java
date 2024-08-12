/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCreditGradeLocal.java,v 1.2 2003/06/24 09:09:16 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBCreditGrade entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/24 09:09:16 $ Tag: $Name: $
 */
public interface EBCreditGradeLocal extends EJBLocalObject {
	/**
	 * Get Credit Grade ID
	 * 
	 * @return long
	 */
	public long getCGID();

	/**
	 * Return an object representation of the Credit Grade information.
	 * 
	 * @return ICreditGrade
	 */
	public ICreditGrade getValue();

	/**
	 * Persist a Credit Grade information
	 * 
	 * @param value is of type ICreditGrade
	 */
	public void setValue(ICreditGrade value);
}