/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBRecoveryIncomeBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryIncome extends EJBObject {
	/**
	 * Get the RecoveryIncome business object.
	 * 
	 * @return RecoveryIncome object
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public IRecoveryIncome getValue() throws RemoteException;

	/**
	 * Set the RecoveryIncome to this entity.
	 * 
	 * @param recoveryIncome is of type IRecoveryIncome
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPLInfo is invalid
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public void setValue(IRecoveryIncome recoveryIncome) throws VersionMismatchException, RemoteException;

	/**
	 * Set status of the business object.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) throws RemoteException;

}