/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the local remote interface to the EBTradingAgreement entity bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBTradingAgreementLocal extends EJBLocalObject {

	/**
	 * Get the version time
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Get an object representation from persistance
	 * 
	 * @return ITradingAgreement
	 * @throws LimitException
	 */
	public ITradingAgreement getValue() throws LimitException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ITradingAgreement
	 * @throws LimitException, ConcurrentUpdateException
	 */
	public void setValue(ITradingAgreement value) throws LimitException, ConcurrentUpdateException;

	/**
	 * Method to create child dependants for trading agreement
	 * 
	 * @param value is of type ITradingAgreement
	 * @param verTime is the long value of the version time to be compared
	 *        against.
	 * @throws ConcurrentUpdateException, LimitException on error
	 * 
	 */
	public void createDependants(ITradingAgreement value, long verTime) throws ConcurrentUpdateException,
			LimitException;

	/**
	 * Set the status to deleted for trading agreement.
	 * 
	 * @param value of type ITradingAgreement
	 * @throws ConcurrentUpdateException if another trading agreement is
	 *         updating at the same time
	 * @throws RemoteException on error during remote method call
	 */
	public void setStatusDeleted(ITradingAgreement value) throws LimitException, ConcurrentUpdateException;

}