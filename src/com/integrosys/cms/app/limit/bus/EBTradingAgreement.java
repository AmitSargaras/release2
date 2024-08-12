/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBTradingAgreement entity bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBTradingAgreement extends EJBObject {

	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ITradingAgreement
	 * @throws LimitException, RemoteException
	 */
	public ITradingAgreement getValue() throws LimitException, RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ITradingAgreement
	 * @throws LimitException, ConcurrentUpdateException, RemoteException
	 */
	public void setValue(ITradingAgreement value) throws LimitException, ConcurrentUpdateException, RemoteException;

	/**
	 * Method to create child dependants for trading agreement
	 * 
	 * @param value is of type ITradingAgreement
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @throws ConcurrentUpdateException, LimitException, RemoteException on
	 *         error
	 * 
	 */
	public void createDependants(ITradingAgreement value, long verTime) throws ConcurrentUpdateException,
			LimitException, RemoteException;

}
