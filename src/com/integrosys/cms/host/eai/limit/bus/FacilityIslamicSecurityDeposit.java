package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

/**
 * An entity represent a security deposits regards a corporate loan islamic
 * facility.
 * @author Chong Jun Yong
 * 
 */
public class FacilityIslamicSecurityDeposit implements Serializable {

	private static final long serialVersionUID = 8820183054096396985L;

	private Long cmsFacilityMasterId;

	/** CMS internal status to determine whether this to be shown on the screen */
	private String status;

	private Integer numberOfMonth;

	private Double securityDeposit;

	private Double fixSecurityDepositAmount;

	private Double originalSecurityDepositAmount;

	private String updateStatusIndicator;

	private String changeIndicator;

	public Long getCmsFacilityMasterId() {
		return cmsFacilityMasterId;
	}

	public String getStatus() {
		return status;
	}

	public Integer getNumberOfMonth() {
		return numberOfMonth;
	}

	public Double getSecurityDeposit() {
		return securityDeposit;
	}

	public Double getFixSecurityDepositAmount() {
		return fixSecurityDepositAmount;
	}

	public Double getOriginalSecurityDepositAmount() {
		return originalSecurityDepositAmount;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public void setCmsFacilityMasterId(Long cmsFacilityMasterId) {
		this.cmsFacilityMasterId = cmsFacilityMasterId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setNumberOfMonth(Integer numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}

	public void setSecurityDeposit(Double securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public void setFixSecurityDepositAmount(Double fixSecurityDepositAmount) {
		this.fixSecurityDepositAmount = fixSecurityDepositAmount;
	}

	public void setOriginalSecurityDepositAmount(Double originalSecurityDepositAmount) {
		this.originalSecurityDepositAmount = originalSecurityDepositAmount;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("FacilityIslamicSecurityDeposit [");
		buffer.append("status=");
		buffer.append(status);
		buffer.append(", numberOfMonth=");
		buffer.append(numberOfMonth);
		buffer.append(", securityDeposit=");
		buffer.append(securityDeposit);
		buffer.append(", fixSecurityDepositAmount=");
		buffer.append(fixSecurityDepositAmount);
		buffer.append(", originalSecurityDepositAmount=");
		buffer.append(originalSecurityDepositAmount);
		buffer.append("]");
		return buffer.toString();
	}

}
