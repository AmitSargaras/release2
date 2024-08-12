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
public interface EBRecoveryExpense extends EJBObject {
	/**
	 * Get the RecoveryExpense business object.
	 * 
	 * @return RecoveryExpense object
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public IRecoveryExpense getValue() throws RemoteException;

	/**
	 * Set the RecoveryExpense to this entity.
	 * 
	 * @param recoveryExpense is of type IRecoveryExpense
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPLInfo is invalid
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public void setValue(IRecoveryExpense recoveryExpense) throws VersionMismatchException, RemoteException;

	/**
	 * Set status of recoveryExpense
	 * @param status
	 */
	public void setStatus(String status) throws RemoteException;
}