/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBRecoveryExpenseBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryExpenseLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param recoveryExpense of type IRecoveryExpense
	 * @return local RecoveryExpense ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 */
	public EBRecoveryExpenseLocal create(IRecoveryExpense recoveryExpense) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the recoveryExpenseID.
	 * 
	 * @param recoveryExpenseID NPL Info ID
	 * @return local NPL Info ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 */
	public EBRecoveryExpenseLocal findByPrimaryKey(Long recoveryExpenseID) throws FinderException;

	/**
	 * Find Recovery Expense by Collateral ID.
	 * 
	 * @param liquidationID
	 * @return a collection of <code>EBRecoveryExpense</code>s
	 * @throws javax.ejb.FinderException on error finding the RecoveryExpense
	 */
	// public Collection findByLiquidationID (long liquidationID) throws
	// FinderException;
}