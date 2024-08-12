/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateralParameter.java,v 1.4 2003/08/15 10:16:49 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents security parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/15 10:16:49 $ Tag: $Name: $
 */
public class OBCollateralParameter extends OBCollateralSubType implements ICollateralParameter {
	private long id = ICMSConstant.LONG_MIN_VALUE;

	private long groupId = ICMSConstant.LONG_MIN_VALUE;

	private String countryIsoCode;

	private String securitySubTypeId;

	private double thresholdPercent;

	private String valuationFrequencyUnit;

	private int valuationFrequency;

	private long versionTime;

	private String status;

	/**
	 * Default constructor.
	 */
	public OBCollateralParameter() {
		super();
	}

	/**
	 * Constucts using a ICollateralParameter instance.
	 * 
	 * @param colParam of type ICollateralParameter
	 */
	public OBCollateralParameter(ICollateralParameter colParam) {
		this();
		AccessorUtil.copyValue(colParam, this);
	}

	/**
	 * Gets the id which is the primary key to identify the parameter.
	 * 
	 * @return collateral parameter id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id collateral parameter id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get group id.
	 * 
	 * @return long
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * Set group id.
	 * 
	 * @param groupId of type long
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * Gets the country ISO code for this parameter.
	 * 
	 * @return country ISO code.
	 */
	public String getCountryIsoCode() {
		return countryIsoCode;
	}

	/**
	 * Sets the country ISO code for this parameter.
	 * 
	 * @param countryIsoCode ISO country code.
	 */
	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	/**
	 * Gets the security subtype id to identify the subtype for which this
	 * parameter is applicable.
	 * 
	 * @return security subtype id
	 */
	public String getSecuritySubTypeId() {
		return securitySubTypeId;
	}

	/**
	 * Sets the security subtype id that identifies the subtype for which this
	 * parameter is applicable.
	 * 
	 * @param securitySubTypeId security subtype id
	 */
	public void setSecuritySubTypeId(String securitySubTypeId) {
		this.securitySubTypeId = securitySubTypeId;
	}

	/**
	 * Gets the input threshold percentage for this parameter.
	 * 
	 * @return the threshold percentage
	 */
	public double getThresholdPercent() {
		return thresholdPercent;
	}

	/**
	 * Sets the input threshold percentage for this parameter.
	 * 
	 * @param thresholdPercent the threshold percentage
	 */
	public void setThresholdPercent(double thresholdPercent) {
		this.thresholdPercent = thresholdPercent;
	}

	/**
	 * Gets the valuation frequency unit for this parameter.
	 * 
	 * @return the valuation frequency unit
	 */
	public String getValuationFrequencyUnit() {
		return valuationFrequencyUnit;
	}

	/**
	 * Sets the valuation frequency unit for this parameter.
	 * 
	 * @param valuationUnit the valuation frequency unit
	 */
	public void setValuationFrequencyUnit(String valuationUnit) {
		this.valuationFrequencyUnit = valuationUnit;
	}

	/**
	 * Gets the valuation frequency for this parameter.
	 * 
	 * @return the valuation frequency
	 */
	public int getValuationFrequency() {
		return valuationFrequency;
	}

	/**
	 * Sets the valuation frequency for this parameter.
	 * 
	 * @param valuationFrequency the valuation frequency
	 */
	public void setValuationFrequency(int valuationFrequency) {
		this.valuationFrequency = valuationFrequency;
	}

	/**
	 * Get version time.
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * Set version time.
	 * 
	 * @param versionTime of type long
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Get status of this security parameter.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status of this security parameter.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
