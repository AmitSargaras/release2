package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * An entity represent a rental renewal regards a corporate loan islamic
 * facility.
 * @author Chong Jun Yong
 * 
 */
public class FacilityIslamicRentalRenewal implements Serializable {

	private static final long serialVersionUID = 8385557922435893544L;

	private Long cmsFacilityMasterId;

	private Long renewalTerm;

	private StandardCode renewalTermCode;

	private Double renewalRate;

	private StandardCode primeRateNumber;

	private Double primeVariance;

	private String primeVarianceCode;

	/** CMS internal status to determine whether this to be shown on the screen */
	private String status;

	private String updateStatusIndicator;

	private String changeIndicator;

	public Long getRenewalTerm() {
		return renewalTerm;
	}

	public StandardCode getRenewalTermCode() {
		return renewalTermCode;
	}

	public Double getRenewalRate() {
		return renewalRate;
	}

	public StandardCode getPrimeRateNumber() {
		return primeRateNumber;
	}

	public Double getPrimeVariance() {
		return primeVariance;
	}

	public String getPrimeVarianceCode() {
		return primeVarianceCode;
	}

	public String getStatus() {
		return status;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public Long getCmsFacilityMasterId() {
		return cmsFacilityMasterId;
	}

	public void setRenewalTerm(Long renewalTerm) {
		this.renewalTerm = renewalTerm;
	}

	public void setRenewalTermCode(StandardCode renewalTermCode) {
		this.renewalTermCode = renewalTermCode;
	}

	public void setRenewalRate(Double renewalRate) {
		this.renewalRate = renewalRate;
	}

	public void setPrimeRateNumber(StandardCode primeRateNumber) {
		this.primeRateNumber = primeRateNumber;
	}

	public void setPrimeVariance(Double primeVariance) {
		this.primeVariance = primeVariance;
	}

	public void setPrimeVarianceCode(String primeVarianceCode) {
		this.primeVarianceCode = primeVarianceCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCmsFacilityMasterId(Long cmsFacilityMasterId) {
		this.cmsFacilityMasterId = cmsFacilityMasterId;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("FacilityIslamicRentalRenewal [");
		buffer.append("status=");
		buffer.append(status);
		buffer.append(", primeRateNumber=");
		buffer.append(primeRateNumber);
		buffer.append(", primeVariance=");
		buffer.append(primeVariance);
		buffer.append(", primeVarianceCode=");
		buffer.append(primeVarianceCode);
		buffer.append(", renewalRate=");
		buffer.append(renewalRate);
		buffer.append(", renewalTerm=");
		buffer.append(renewalTerm);
		buffer.append(", renewalTermCode=");
		buffer.append(renewalTermCode);
		buffer.append("]");
		return buffer.toString();
	}
}
