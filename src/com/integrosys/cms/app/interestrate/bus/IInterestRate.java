/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface represents interest rate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IInterestRate extends Serializable {
	/**
	 * Get interest rate id.
	 * 
	 * @return Long
	 */
	public Long getIntRateID();

	/**
	 * Set interest rate id.
	 * 
	 * @param intRateID of type Long
	 */
	public void setIntRateID(Long intRateID);

	/**
	 * Get interest rate type code.
	 * 
	 * @return String
	 */
	public String getIntRateType();

	/**
	 * Set interest rate type code.
	 * 
	 * @param intRateType of type String
	 */
	public void setIntRateType(String intRateType);

	/**
	 * Get interest rate date.
	 * 
	 * @return Date
	 */
	public Date getIntRateDate();

	/**
	 * Set interest rate date.
	 * 
	 * @param intRateDate of type Date
	 */
	public void setIntRateDate(Date intRateDate);

	/**
	 * Get interest rate percentage.
	 * 
	 * @return Double
	 */
	public Double getIntRatePercent();

	/**
	 * Set interest rate percentage.
	 * 
	 * @param intRatePercent of type Double
	 */
	public void setIntRatePercent(Double intRatePercent);

	/**
	 * Get interest rate group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set interest rate group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get the version of the interest rate.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version of the interest rate.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);
}
