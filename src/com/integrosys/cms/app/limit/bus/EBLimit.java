/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimit.java,v 1.2 2004/06/11 02:37:14 lyng Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBLimit entity bean
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/11 02:37:14 $ Tag: $Name: $
 */
public interface EBLimit extends EJBObject {
	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ILimit
	 * @throws LimitException on error
	 * @throws RemoteException
	 */
	public ILimit getValue() throws LimitException, RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ILimit
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException on error
	 * @throws RemoteException
	 */
	public ILimit setValue(ILimit value) throws ConcurrentUpdateException, LimitException, RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ILimit
	 * @return ILimit
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException on error
	 * @throws RemoteException
	 */
	public ILimit updateOperationalLimit(ILimit value) throws ConcurrentUpdateException, LimitException,
			RemoteException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ILimit
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @throws ConcurrentUpdateException, LimitException, RemoteException on
	 *         error
	 * 
	 */
	public void createDependants(ILimit value, long verTime) throws ConcurrentUpdateException, LimitException,
			RemoteException;
}