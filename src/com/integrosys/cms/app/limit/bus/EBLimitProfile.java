/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitProfile.java,v 1.5 2003/09/15 13:19:43 hltan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBLimitProfile entity bean
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/09/15 13:19:43 $ Tag: $Name: $
 */
public interface EBLimitProfile extends EJBObject {
	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Get an object representation from persistance
	 * 
	 * @param loadDependants is a boolean value indicating of child dependants
	 *        should be loaded
	 * @return ILimitProfile
	 * @throws LimitException, RemoteException
	 */
	public ILimitProfile getValue(boolean loadDependants) throws LimitException, RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ILimitProfile
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException, RemoteException on errors
	 */
	public void setValue(ILimitProfile value) throws LimitException, ConcurrentUpdateException, RemoteException;

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ILimitProfile
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @throws ConcurrentUpdateException, LimitException, RemoteException on
	 *         error
	 * 
	 */
	public void createDependants(ILimitProfile value, long verTime) throws ConcurrentUpdateException, LimitException,
			RemoteException;

	/**
	 * Set the status to deleted for limit profile.
	 * 
	 * @param value of type ILimitProfile
	 * @throws ConcurrentUpdateException if another limit profile is updating at
	 *         the same time
	 * @throws RemoteException on error during remote method call
	 */
	public void setStatusDeleted(ILimitProfile value) throws ConcurrentUpdateException, LimitException, RemoteException;

}