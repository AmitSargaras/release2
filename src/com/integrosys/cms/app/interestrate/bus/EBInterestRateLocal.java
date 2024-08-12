/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBInterestRateBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBInterestRateLocal extends EJBLocalObject {
	/**
	 * Get the interest rate business object.
	 * 
	 * @return interest rate object
	 */
	public IInterestRate getValue();

	/**
	 * Set the interest rate to this entity.
	 * 
	 * @param intRate is of type IInterestRate
	 * @throws VersionMismatchException if the interest rate is invalid
	 */
	public void setValue(IInterestRate intRate) throws VersionMismatchException;

	/**
	 * Set the interest rate percentage for interest rate.
	 * 
	 * @param intRate of type IInterestRate
	 * @throws VersionMismatchException if the interest rate is invalid
	 */
	public void setIntRateValue(IInterestRate intRate) throws VersionMismatchException;
}