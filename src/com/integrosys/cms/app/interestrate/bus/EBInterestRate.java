/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBInterestRateBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBInterestRate extends EJBObject {
	/**
	 * Get the interest rate business object.
	 * 
	 * @return interest rate object
	 * @throws RemoteException on error during remote method call
	 */
	public IInterestRate getValue() throws RemoteException;

	/**
	 * Set the interest rate to this entity.
	 * 
	 * @param intRate is of type IInterestRate
	 * @throws VersionMismatchException if the interestrate is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(IInterestRate intRate) throws VersionMismatchException, RemoteException;

	/**
	 * Set the interest rate percentage for interest rate.
	 * 
	 * @param intRate of type IInterestRate
	 * @throws VersionMismatchException if the is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setIntRateValue(IInterestRate intRate) throws VersionMismatchException, RemoteException;
}