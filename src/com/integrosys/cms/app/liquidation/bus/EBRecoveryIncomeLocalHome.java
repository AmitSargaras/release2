/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBRecoveryIncomeBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryIncomeLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param recoveryIncome of type IRecoveryIncome
	 * @return local RecoveryIncome ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 */
	public EBRecoveryIncomeLocal create(IRecoveryIncome recoveryIncome) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the recoveryIncomeID.
	 * 
	 * @param recoveryIncomeID NPL Info ID
	 * @return local NPL Info ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 */
	public EBRecoveryIncomeLocal findByPrimaryKey(Long recoveryIncomeID) throws FinderException;

	/**
	 * Find Recovery Income by Liquidation ID.
	 * 
	 * @param liquidationID
	 * @return a collection of <code>EBRecoveryIncome</code>s
	 * @throws javax.ejb.FinderException on error finding the RecoveryIncome
	 */
	// public Collection findByRecoveryID (long liquidationID) throws
	// FinderException;
}