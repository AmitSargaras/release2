/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBContactLocal.java,v 1.1 2003/06/23 10:07:44 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBContact entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 10:07:44 $ Tag: $Name: $
 */
public interface EBSublineLocal extends EJBLocalObject {
	/**
	 * Get the contact ID primary key
	 * 
	 * @return long
	 */
	public long getSublineID();

	/**
	 * Return an object representation of the contact information.
	 * 
	 * @return IContact
	 */
	public ISubline getValue();

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(ISubline value);
}