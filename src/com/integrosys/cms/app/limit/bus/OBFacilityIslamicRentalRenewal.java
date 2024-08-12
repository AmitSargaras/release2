package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * An entity represent a rental renewal regards a corporate loan islamic
 * facility.
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityIslamicRentalRenewal implements Serializable {

	private static final long serialVersionUID = 3150337629206575099L;

	private Long cmsFacilityMasterId;

	private Long renewalTerm;

	private String renewalTermCodeCategoryCode;

	private String renewalTermCodeEntryCode;

	private BigDecimal renewalRate;

	private String primeRateNumberCategoryCode;

	private String primeRateNumberEntryCode;

	private BigDecimal primeVariance;

	private String primeVarianceCode;

	/** CMS internal status to determine whether this to be shown on the screen */
	private String status;

	public Long getCmsFacilityMasterId() {
		return cmsFacilityMasterId;
	}

	public String getPrimeRateNumberCategoryCode() {
		return primeRateNumberCategoryCode;
	}

	public String getPrimeRateNumberEntryCode() {
		return primeRateNumberEntryCode;
	}

	public BigDecimal getPrimeVariance() {
		return primeVariance;
	}

	public String getPrimeVarianceCode() {
		return primeVarianceCode;
	}

	public BigDecimal getRenewalRate() {
		return renewalRate;
	}

	public Long getRenewalTerm() {
		return renewalTerm;
	}

	public String getRenewalTermCodeCategoryCode() {
		return renewalTermCodeCategoryCode;
	}

	public String getRenewalTermCodeEntryCode() {
		return renewalTermCodeEntryCode;
	}

	public String getStatus() {
		return status;
	}

	public void setCmsFacilityMasterId(Long cmsFacilityMasterId) {
		this.cmsFacilityMasterId = cmsFacilityMasterId;
	}

	public void setPrimeRateNumberCategoryCode(String primeRateNumberCategoryCode) {
		this.primeRateNumberCategoryCode = primeRateNumberCategoryCode;
	}

	public void setPrimeRateNumberEntryCode(String primeRateNumberEntryCode) {
		this.primeRateNumberEntryCode = primeRateNumberEntryCode;
	}

	public void setPrimeVariance(BigDecimal primeVariance) {
		this.primeVariance = primeVariance;
	}

	public void setPrimeVarianceCode(String primeVarianceCode) {
		this.primeVarianceCode = primeVarianceCode;
	}

	public void setRenewalRate(BigDecimal renewalRate) {
		this.renewalRate = renewalRate;
	}

	public void setRenewalTerm(Long renewalTerm) {
		this.renewalTerm = renewalTerm;
	}

	public void setRenewalTermCodeCategoryCode(String renewalTermCodeCategoryCode) {
		this.renewalTermCodeCategoryCode = renewalTermCodeCategoryCode;
	}

	public void setRenewalTermCodeEntryCode(String renewalTermCodeEntryCode) {
		this.renewalTermCodeEntryCode = renewalTermCodeEntryCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBFacilityIslamicRentalRenewal [");
		buf.append("cmsFacilityMasterId=");
		buf.append(cmsFacilityMasterId);
		buf.append(", primeRateNumberCategoryCode=");
		buf.append(primeRateNumberCategoryCode);
		buf.append(", primeRateNumberEntryCode=");
		buf.append(primeRateNumberEntryCode);
		buf.append(", primeVariance=");
		buf.append(primeVariance);
		buf.append(", primeVarianceCode=");
		buf.append(primeVarianceCode);
		buf.append(", renewalRate=");
		buf.append(renewalRate);
		buf.append(", renewalTerm=");
		buf.append(renewalTerm);
		buf.append(", renewalTermCodeCategoryCode=");
		buf.append(renewalTermCodeCategoryCode);
		buf.append(", renewalTermCodeEntryCode=");
		buf.append(renewalTermCodeEntryCode);
		buf.append(", status=");
		buf.append(status);
		buf.append("]");
		return buf.toString();
	}

}