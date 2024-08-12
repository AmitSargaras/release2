/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCustomerSysXRefLocal.java,v 1.1 2003/06/23 10:07:44 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the local interface to the EBCustomerSysXRef entity bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 10:07:44 $ Tag: $Name: $
 */
public interface EBCustomerSysXRefLocal extends EJBLocalObject {
	/**
	 * Get X-Ref primary key
	 * 
	 * @return long
	 */
	public long getXRefID();

	/**
	 * Return an object representation of the CustomerSysXRef information.
	 * 
	 * @return ICustomerSysXRef
	 */
	public ICustomerSysXRef getValue() throws CustomerException;;

	/**
	 * Persist a CustomerSysXRef information
	 * 
	 * @param value is of type ICustomerSysXRef
	 */
	public void setValue(ICustomerSysXRef value)throws CustomerException;
	
	public void createDependants(long customerID,ICustomerSysXRef value) throws CustomerException;
}