/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralParameter.java,v 1.4 2003/08/15 10:16:49 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * This interface represents security parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/15 10:16:49 $ Tag: $Name: $
 */
public interface ICollateralParameter extends ICollateralSubType {
	/**
	 * Gets the id which is the primary key to identify the parameter.
	 * 
	 * @return collateral parameter id
	 */
	public long getId();

	/**
	 * Sets the id.
	 * 
	 * @param id collateral parameter id
	 */
	public void setId(long id);

	/**
	 * Get group id.
	 * 
	 * @return long
	 */
	public long getGroupId();

	/**
	 * Set group id.
	 * 
	 * @param groupId of type long
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the country ISO code for this parameter.
	 * 
	 * @return country ISO code.
	 */
	public String getCountryIsoCode();

	/**
	 * Sets the country ISO code for this parameter.
	 * 
	 * @param countryIsoCode ISO country code.
	 */
	public void setCountryIsoCode(String countryIsoCode);

	/**
	 * Gets the security subtype id to identify the subtype for which this
	 * parameter is applicable.
	 * 
	 * @return security subtype id
	 */
	public String getSecuritySubTypeId();

	/**
	 * Sets the security subtype id that identifies the subtype for which this
	 * parameter is applicable.
	 * 
	 * @param securitySubTypeId security subtype id
	 */
	public void setSecuritySubTypeId(String securitySubTypeId);

	/**
	 * Gets the input threshold percentage for this parameter.
	 * 
	 * @return the threshold percentage
	 */
	public double getThresholdPercent();

	/**
	 * Sets the input threshold percentage for this parameter.
	 * 
	 * @param thresholdPercent the threshold percentage
	 */
	public void setThresholdPercent(double thresholdPercent);

	/**
	 * Gets the valuation frequency unit for this parameter.
	 * 
	 * @return the valuation frequency unit
	 */
	public String getValuationFrequencyUnit();

	/**
	 * Sets the valuation frequency unit for this parameter.
	 * 
	 * @param valuationUnit the valuation frequency unit
	 */
	public void setValuationFrequencyUnit(String valuationUnit);

	/**
	 * Gets the valuation frequency for this parameter.
	 * 
	 * @return the valuation frequency
	 */
	public int getValuationFrequency();

	/**
	 * Sets the valuation frequency for this parameter.
	 * 
	 * @param valuationFrequency the valuation frequency
	 */
	public void setValuationFrequency(int valuationFrequency);

	/**
	 * Get version time.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set version time.
	 * 
	 * @param versionTime of type long
	 */
	public void setVersionTime(long versionTime);

	/**
	 * Get status of this security parameter.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set status of this security parameter.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}
