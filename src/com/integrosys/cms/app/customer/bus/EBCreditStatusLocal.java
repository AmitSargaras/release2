/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCreditStatusLocal.java,v 1.2 2003/06/24 09:09:16 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBCreditStatus entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/24 09:09:16 $ Tag: $Name: $
 */
public interface EBCreditStatusLocal extends EJBLocalObject {
	/**
	 * Get credit status ID
	 * 
	 * @return long
	 */
	public long getCSID();

	/**
	 * Return an object representation of the Credit Status information.
	 * 
	 * @return ICreditStatus
	 */
	public ICreditStatus getValue();

	/**
	 * Persist a Credit Status information
	 * 
	 * @param value is of type ICreditStatus
	 */
	public void setValue(ICreditStatus value);
}