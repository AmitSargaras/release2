package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

public interface IFacilityInsurance extends Serializable {
	/**
	 * @return the arrangementIndicator
	 */
	public Character getArrangementIndicator();

	/**
	 * @return the coverageTypeCategoryCode
	 */
	public String getCoverageTypeCategoryCode();

	/**
	 * @return the coverageTypeEntryCode
	 */
	public String getCoverageTypeEntryCode();

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode();

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate();

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate();

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId();

	/**
	 * @return the id
	 */
	public long getId();

	/**
	 * @return the order
	 */
	public short getOrder();

	public Long getCmsRefId();

	/**
	 * @return the insuranceCompanyCategoryCode
	 */
	public String getInsuranceCompanyCategoryCode();

	/**
	 * @return the insuranceCompanyEntryCode
	 */
	public String getInsuranceCompanyEntryCode();

	/**
	 * @return the insurancePremiumAmount
	 */
	public Amount getInsurancePremiumAmount();

	/**
	 * @return the insuredAmount
	 */
	public Amount getInsuredAmount();

	/**
	 * @return the issuedDate
	 */
	public Date getIssuedDate();

	/**
	 * @return the policyNumber
	 */
	public String getPolicyNumber();

	/**
	 * @return the propNumberOrCvNote
	 */
	public String getPropNumberOrCvNote();

	/**
	 * @return the remarks
	 */
	public String getRemarks();

	/**
	 * @param order the order to set
	 */
	public void setOrder(short order);

	/**
	 * @param arrangementIndicator the arrangmentIndicator to set
	 */
	public void setArrangementIndicator(Character arrangementIndicator);

	/**
	 * @param coverageTypeCategoryCode the coverageTypeCategoryCode to set
	 */
	public void setCoverageTypeCategoryCode(String coverageTypeCategoryCode);

	/**
	 * @param coverageTypeEntryCode the coverageTypeEntryCode to set
	 */
	public void setCoverageTypeEntryCode(String coverageTypeEntryCode);

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode);

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate);

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate);

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId);

	/**
	 * @param id the id to set
	 */
	public void setId(long id);

	public void setCmsRefId(Long cmsRefId);

	/**
	 * @param insuranceCompanyCategoryCode the insuranceCompanyCategoryCode to
	 *        set
	 */
	public void setInsuranceCompanyCategoryCode(String insuranceCompanyCategoryCode);

	/**
	 * @param insuranceCompanyEntryCode the insuranceCompanyEntryCode to set
	 */
	public void setInsuranceCompanyEntryCode(String insuranceCompanyEntryCode);

	/**
	 * @param insurancePremiumAmount the insurancePremiumAmount to set
	 */
	public void setInsurancePremiumAmount(Amount insurancePremiumAmount);

	/**
	 * @param insuredAmount the insuredAmount to set
	 */
	public void setInsuredAmount(Amount insuredAmount);

	/**
	 * @param issuedDate the issuedDate to set
	 */
	public void setIssuedDate(Date issuedDate);

	/**
	 * @param policyNumber the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber);

	/**
	 * @param propNumberOrCvNote the propNumberOrCvNote to set
	 */
	public void setPropNumberOrCvNote(String propNumberOrCvNote);

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks);
}
