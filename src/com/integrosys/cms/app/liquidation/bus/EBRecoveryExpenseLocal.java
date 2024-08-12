/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBRecoveryExpenseBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryExpenseLocal extends EJBLocalObject {
	/**
	 * Get the Expense business object.
	 * 
	 * @return Expense object
	 */
	public IRecoveryExpense getValue();

	/**
	 * Set the Expense to this entity.
	 * 
	 * @param recoveryExpense is of type IRecoveryExpense
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPL Info is invalid
	 */
	public void setValue(IRecoveryExpense recoveryExpense) throws VersionMismatchException;

	/**
	 * Set status of recoveryExpense
	 * @param status
	 */
	public void setStatus(String status);

}