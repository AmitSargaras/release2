/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBRecoveryExpense.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryExpenseHome extends EJBHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param recoveryExpense of type IRecoveryExpense
	 * @return EBRecoveryExpense ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBRecoveryExpense create(IRecoveryExpense recoveryExpense) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the recoveryExpenseID.
	 * 
	 * @param recoveryExpenseID
	 * @return local RecoveryExpense subtype ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBRecoveryExpense findByPrimaryKey(Long recoveryExpenseID) throws FinderException, RemoteException;

	/**
	 * Find Recovery Expense by LiquidationID
	 * 
	 * @param liquidationID
	 * @return a collection of <code>EBRecoveryExpense</code>s
	 * @throws javax.ejb.FinderException on error finding the RecoveryExpense
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	// public Collection findByLiquidationID (long liquidationID) throws
	// FinderException, RemoteException;
}