package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * An entity represent a security deposits regards a corporate loan islamic
 * facility.
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityIslamicSecurityDeposit implements Serializable {

	private static final long serialVersionUID = -5758260254624064774L;

	private Long cmsFacilityMasterId;

	/** CMS internal status to determine whether this to be shown on the screen */
	private String status;

	private Integer numberOfMonth;

	private BigDecimal securityDeposit;

	private BigDecimal fixSecurityDepositAmount;

	private BigDecimal originalSecurityDepositAmount;

	private Boolean isRecalculateUponReview;

	private Integer monthBeforePrintRenewalReport;

	private String remarks;

	public Long getCmsFacilityMasterId() {
		return cmsFacilityMasterId;
	}

	public BigDecimal getFixSecurityDepositAmount() {
		return fixSecurityDepositAmount;
	}

	public Boolean getIsRecalculateUponReview() {
		return isRecalculateUponReview;
	}

	public Integer getMonthBeforePrintRenewalReport() {
		return monthBeforePrintRenewalReport;
	}

	public Integer getNumberOfMonth() {
		return numberOfMonth;
	}

	public BigDecimal getOriginalSecurityDepositAmount() {
		return originalSecurityDepositAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public BigDecimal getSecurityDeposit() {
		return securityDeposit;
	}

	public String getStatus() {
		return status;
	}

	public void setCmsFacilityMasterId(Long cmsFacilityMasterId) {
		this.cmsFacilityMasterId = cmsFacilityMasterId;
	}

	public void setFixSecurityDepositAmount(BigDecimal fixSecurityDepositAmount) {
		this.fixSecurityDepositAmount = fixSecurityDepositAmount;
	}

	public void setIsRecalculateUponReview(Boolean isRecalculateUponReview) {
		this.isRecalculateUponReview = isRecalculateUponReview;
	}

	public void setMonthBeforePrintRenewalReport(Integer monthBeforePrintRenewalReport) {
		this.monthBeforePrintRenewalReport = monthBeforePrintRenewalReport;
	}

	public void setNumberOfMonth(Integer numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}

	public void setOriginalSecurityDepositAmount(BigDecimal originalSecurityDepositAmount) {
		this.originalSecurityDepositAmount = originalSecurityDepositAmount;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setSecurityDeposit(BigDecimal securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBFacilityIslamicSecurityDeposit [");
		buf.append("cmsFacilityMasterId=");
		buf.append(cmsFacilityMasterId);
		buf.append(", fixSecurityDepositAmount=");
		buf.append(fixSecurityDepositAmount);
		buf.append(", numberOfMonth=");
		buf.append(numberOfMonth);
		buf.append(", originalSecurityDepositAmount=");
		buf.append(originalSecurityDepositAmount);
		buf.append(", securityDeposit=");
		buf.append(securityDeposit);
		buf.append(", status=");
		buf.append(status);
		buf.append(", isRecalculateUponReview=");
		buf.append(isRecalculateUponReview);
		buf.append(", monthBeforePrintRenewalReport=");
		buf.append(monthBeforePrintRenewalReport);
		buf.append(", remarks=");
		buf.append(remarks);
		buf.append("]");
		return buf.toString();
	}

}