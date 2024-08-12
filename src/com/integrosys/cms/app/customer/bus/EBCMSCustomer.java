/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSCustomer.java,v 1.5 2003/07/03 07:51:47 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBCMSCustomer entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/07/03 07:51:47 $ Tag: $Name: $
 */
public interface EBCMSCustomer extends EJBObject {
	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ICMSCustomer
	 * @throws CustomerException on error
	 * @throws RemoteException
	 */
	public ICMSCustomer getValue() throws CustomerException, RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ICMSCustomer
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws CustomerException on error
	 * @throws RemoteException
	 */
	public void setValue(ICMSCustomer value) throws ConcurrentUpdateException, CustomerException, RemoteException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ICMSCustomer
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @throws ConcurrentUpdateException, CustomerException, RemoteException on
	 *         error
	 * 
	 */
	public void createDependants(ICMSCustomer value, long verTime) throws ConcurrentUpdateException, CustomerException,
			RemoteException;

	/**
	 * Set the non-borrower indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setNonBorrowerInd(boolean value) throws RemoteException;

}