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
 * Remote home interface for EBRecoveryIncome.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryIncomeHome extends EJBHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param recoveryIncome of type IRecoveryIncome
	 * @return EBRecoveryIncome ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBRecoveryIncome create(IRecoveryIncome recoveryIncome) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the recoveryIncomeID.
	 * 
	 * @param recoveryIncomeID
	 * @return local RecoveryIncome subtype ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBRecoveryIncome findByPrimaryKey(Long recoveryIncomeID) throws FinderException, RemoteException;

	/**
	 * Find Recovery Income by liquidationID
	 * 
	 * @param liquidationID
	 * @return a collection of <code>EBRecoveryIncome</code>s
	 * @throws javax.ejb.FinderException on error finding the RecoveryIncome
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	// public Collection findByRecoveryID (long liquidationID) throws
	// FinderException, RemoteException;
}