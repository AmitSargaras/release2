/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBKYCLocal.java,v 1.2 2003/06/24 09:09:16 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBKYC entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/24 09:09:16 $ Tag: $Name: $
 */
public interface EBKYCLocal extends EJBLocalObject {
	/**
	 * Get the KYC ID
	 * 
	 * @return long
	 */
	public long getKYCID();

	/**
	 * Return an object representation of the KYC information.
	 * 
	 * @return IKYC
	 */
	public IKYC getValue();

	/**
	 * Persist a KYC information
	 * 
	 * @param value is of type IKYC
	 */
	public void setValue(IKYC value);
}