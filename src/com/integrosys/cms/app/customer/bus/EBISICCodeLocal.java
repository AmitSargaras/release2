/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBISICCodeLocal.java,v 1.2 2003/06/24 09:09:16 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBISICCode entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/24 09:09:16 $ Tag: $Name: $
 */
public interface EBISICCodeLocal extends EJBLocalObject {
	/**
	 * Get the ISIC ID
	 * 
	 * @return long
	 */
	public long getISICID();

	/**
	 * Return an object representation of the ISIC Code information.
	 * 
	 * @return IISICCode
	 */
	public IISICCode getValue();

	/**
	 * Persist a ISIC Code information
	 * 
	 * @param value is of type IISICCode
	 */
	public void setValue(IISICCode value);
}