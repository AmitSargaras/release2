/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBRecoveryIncomeBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryIncomeLocal extends EJBLocalObject {
	/**
	 * Get the Income business object.
	 * 
	 * @return Income object
	 */
	public IRecoveryIncome getValue();

	/**
	 * Set the Income to this entity.
	 * 
	 * @param recoveryIncome is of type IRecoveryIncome
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPL Info is invalid
	 */
	public void setValue(IRecoveryIncome recoveryIncome) throws VersionMismatchException;

	/**
	 * Set status of the business object.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}