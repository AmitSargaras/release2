/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCustomerSysXRef.java,v 1.3 2003/07/25 07:49:32 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * This is the remote interface to the EBCustomerSysXRef entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/25 07:49:32 $ Tag: $Name: $
 */
public interface EBCustomerSysXRef extends EJBObject {
	/**
	 * Get an object representation from persistance
	 * 
	 * @return ICustomerSysXRef
	 * @throws RemoteException
	 */
	public ICustomerSysXRef getValue() throws RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ICustomerSysXRef
	 * @throws RemoteException
	 */
	public void setValue(ICustomerSysXRef value) throws RemoteException;
	
	public void createDependants(long customerID,ICustomerSysXRef value) throws CustomerException,
	RemoteException;
}