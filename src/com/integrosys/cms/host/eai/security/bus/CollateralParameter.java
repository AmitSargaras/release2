package com.integrosys.cms.host.eai.security.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CollateralParameter {

	private long id = ICMSConstant.LONG_MIN_VALUE;

	private Long groupId;

	private String countryIsoCode;

	private String securitySubTypeId;

	private Double thresholdPercent;

	private String valuationFrequencyUnit;

	private Integer valuationFrequency;

	private Long versionTime;

	private String status;

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
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * Set group id.
	 * 
	 * @param groupId of type long
	 */
	public void setGroupId(Long groupId) {
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
	public Double getThresholdPercent() {
		return thresholdPercent;
	}

	/**
	 * Sets the input threshold percentage for this parameter.
	 * 
	 * @param thresholdPercent the threshold percentage
	 */
	public void setThresholdPercent(Double thresholdPercent) {
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
	public Integer getValuationFrequency() {
		return valuationFrequency;
	}

	/**
	 * Sets the valuation frequency for this parameter.
	 * 
	 * @param valuationFrequency the valuation frequency
	 */
	public void setValuationFrequency(Integer valuationFrequency) {
		this.valuationFrequency = valuationFrequency;
	}

	/**
	 * Get version time.
	 * 
	 * @return long
	 */
	public Long getVersionTime() {
		return versionTime;
	}

	/**
	 * Set version time.
	 * 
	 * @param versionTime of type long
	 */
	public void setVersionTime(Long versionTime) {
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
}
