package com.integrosys.cms.app.limit.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityInsurance implements IFacilityInsurance {
	private long id;

	private short order;

	private long facilityMasterId;

	private Long cmsRefId;

	private String currencyCode;

	private String coverageTypeCategoryCode;

	private String coverageTypeEntryCode;

	private String insuranceCompanyCategoryCode;

	private String insuranceCompanyEntryCode;

	private String policyNumber;

	private String propNumberOrCvNote;

	private Amount insuredAmount;

	private Amount insurancePremiumAmount;

	private Date issuedDate;

	private Date expiryDate;

	private Date effectiveDate;

	/** Arrangment Indicator, either Bank, Customer, or Developer */
	private Character arrangementIndicator;

	private String remarks;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the order
	 */
	public short getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(short order) {
		this.order = order;
	}

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @return the coverageTypeCategoryCode
	 */
	public String getCoverageTypeCategoryCode() {
		return coverageTypeCategoryCode;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return the coverageTypeEntryCode
	 */
	public String getCoverageTypeEntryCode() {
		return coverageTypeEntryCode;
	}

	/**
	 * @return the insuranceCompanyCategoryCode
	 */
	public String getInsuranceCompanyCategoryCode() {
		return insuranceCompanyCategoryCode;
	}

	/**
	 * @return the insuranceCompanyEntryCode
	 */
	public String getInsuranceCompanyEntryCode() {
		return insuranceCompanyEntryCode;
	}

	/**
	 * @return the policyNumber
	 */
	public String getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @return the propNumberOrCvNote
	 */
	public String getPropNumberOrCvNote() {
		return propNumberOrCvNote;
	}

	/**
	 * @return the insuredAmount
	 */
	public Amount getInsuredAmount() {
		return insuredAmount;
	}

	/**
	 * @return the insurancePremiumAmount
	 */
	public Amount getInsurancePremiumAmount() {
		return insurancePremiumAmount;
	}

	/**
	 * @return the issuedDate
	 */
	public Date getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @return the arrangmentIndicator
	 */
	public Character getArrangementIndicator() {
		return arrangementIndicator;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @param coverageTypeCategoryCode the coverageTypeCategoryCode to set
	 */
	public void setCoverageTypeCategoryCode(String coverageTypeCategoryCode) {
		this.coverageTypeCategoryCode = coverageTypeCategoryCode;
	}

	/**
	 * @param coverageTypeEntryCode the coverageTypeEntryCode to set
	 */
	public void setCoverageTypeEntryCode(String coverageTypeEntryCode) {
		this.coverageTypeEntryCode = coverageTypeEntryCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @param insuranceCompanyCategoryCode the insuranceCompanyCategoryCode to
	 *        set
	 */
	public void setInsuranceCompanyCategoryCode(String insuranceCompanyCategoryCode) {
		this.insuranceCompanyCategoryCode = insuranceCompanyCategoryCode;
	}

	/**
	 * @param insuranceCompanyEntryCode the insuranceCompanyEntryCode to set
	 */
	public void setInsuranceCompanyEntryCode(String insuranceCompanyEntryCode) {
		this.insuranceCompanyEntryCode = insuranceCompanyEntryCode;
	}

	/**
	 * @param policyNumber the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	/**
	 * @param propNumberOrCvNote the propNumberOrCvNote to set
	 */
	public void setPropNumberOrCvNote(String propNumberOrCvNote) {
		this.propNumberOrCvNote = propNumberOrCvNote;
	}

	/**
	 * @param insuredAmount the insuredAmount to set
	 */
	public void setInsuredAmount(Amount insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	/**
	 * @param insurancePremiumAmount the insurancePremiumAmount to set
	 */
	public void setInsurancePremiumAmount(Amount insurancePremiumAmount) {
		this.insurancePremiumAmount = insurancePremiumAmount;
	}

	/**
	 * @param issuedDate the issuedDate to set
	 */
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @param arrangementIndicator the arrangmentIndicator to set
	 */
	public void setArrangementIndicator(Character arrangementIndicator) {
		this.arrangementIndicator = arrangementIndicator;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getCmsRefId() {
		return cmsRefId;
	}

	public void setCmsRefId(Long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((coverageTypeEntryCode == null) ? 0 : coverageTypeEntryCode.hashCode());
		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((insuranceCompanyEntryCode == null) ? 0 : insuranceCompanyEntryCode.hashCode());
		result = prime * result + ((issuedDate == null) ? 0 : issuedDate.hashCode());
		result = prime * result + ((policyNumber == null) ? 0 : policyNumber.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		OBFacilityInsurance other = (OBFacilityInsurance) obj;
		if (!(order == other.order)) {
			return false;
		}

		if (coverageTypeEntryCode == null) {
			if (other.coverageTypeEntryCode != null) {
				return false;
			}
		}
		else if (!coverageTypeEntryCode.equals(other.coverageTypeEntryCode)) {
			return false;
		}

		if (coverageTypeCategoryCode == null) {
			if (other.coverageTypeCategoryCode != null) {
				return false;
			}
		}
		else if (!coverageTypeCategoryCode.equals(other.coverageTypeCategoryCode)) {
			return false;
		}

		if (effectiveDate == null) {
			if (other.effectiveDate != null) {
				return false;
			}
		}
		else if (!effectiveDate.equals(other.effectiveDate)) {
			return false;
		}

		if (insuranceCompanyEntryCode == null) {
			if (other.insuranceCompanyEntryCode != null) {
				return false;
			}
		}
		else if (!insuranceCompanyEntryCode.equals(other.insuranceCompanyEntryCode)) {
			return false;
		}

		if (insuranceCompanyCategoryCode == null) {
			if (other.insuranceCompanyCategoryCode != null) {
				return false;
			}
		}
		else if (!insuranceCompanyCategoryCode.equals(other.insuranceCompanyCategoryCode)) {
			return false;
		}

		if (issuedDate == null) {
			if (other.issuedDate != null) {
				return false;
			}
		}
		else if (!issuedDate.equals(other.issuedDate)) {
			return false;
		}

		if (policyNumber == null) {
			if (other.policyNumber != null) {
				return false;
			}
		}
		else if (!policyNumber.equals(other.policyNumber)) {
			return false;
		}

		return true;
	}

}
