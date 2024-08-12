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
public interface EBRecoveryLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param recovery of type IRecovery
	 * @return local Recovery ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 */
	public EBRecoveryLocal create(IRecovery recovery) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the recoveryID.
	 * 
	 * @param recoveryID ID
	 * @return local ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 */
	public EBRecoveryLocal findByPrimaryKey(Long recoveryID) throws FinderException;

	/**
	 * Find Recovery by Liquidation ID.
	 * 
	 * @param liquidationID
	 * @return a collection of <code>EBRecovery</code>s
	 * @throws javax.ejb.FinderException on error finding the Recovery
	 */
	// public Collection findByLiquidationID (long liquidationID) throws
	// FinderException;
}