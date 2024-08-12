/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCoBorrowerLimit.java,v 1.2 2006/08/01 12:50:25 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBCoBorrowerLimit entity bean
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.2 $
 * @since $Date: 2006/08/01 12:50:25 $ Tag: $Name: $
 */
public interface EBCoBorrowerLimit extends EJBObject {
	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ICoBorrowerLimit
	 * @throws LimitException on error
	 * @throws RemoteException
	 */
	public ICoBorrowerLimit getValue() throws LimitException, RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws RemoteException
	 */
	public void setValue(ICoBorrowerLimit value) throws ConcurrentUpdateException, RemoteException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @throws ConcurrentUpdateException, LimitException, RemoteException on
	 *         error
	 * 
	 */
	public void createDependants(ICoBorrowerLimit value, long verTime) throws ConcurrentUpdateException,
			LimitException, RemoteException;

}