package com.integrosys.cms.ui.limit.facility.insurance;

import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class InsuranceForm extends FacilityMainForm {

	private String[] coverageTypeEntryCode;// Type Of Coverage

	private String[] insuranceCompanyEntryCode;// Insurance Company

	private String[] policyNumber;// Policy No

	private String[] propNumberOrCvNote;// Prop No/Cv Note

	private String[] insuredAmount;// Amount Insured

	private String[] insuredAmountCurrency;

	private String[] insurancePremiumAmount;// Insurance premium

	private String[] insurancePremiumAmountCurrency;

	private String[] issuedDate;// Issued Date

	private String[] expiryDate;// Expiry Date

	private String[] effectiveDate;// Effective Date

	private String[] arrangementIndicator = new String[] { "B", "B" };// Bank/Cust/Dev

	// Arr
	// In

	private String[] remarksInsurance;// Remarks

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.insurance.InsuranceMapper";

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	/**
	 * @return the coverageTypeEntryCode
	 */
	public String[] getCoverageTypeEntryCode() {
		return coverageTypeEntryCode;
	}

	/**
	 * @param coverageTypeEntryCode the coverageTypeEntryCode to set
	 */
	public void setCoverageTypeEntryCode(String[] coverageTypeEntryCode) {
		this.coverageTypeEntryCode = coverageTypeEntryCode;
	}

	/**
	 * @return the insuranceCompanyEntryCode
	 */
	public String[] getInsuranceCompanyEntryCode() {
		return insuranceCompanyEntryCode;
	}

	/**
	 * @param insuranceCompanyEntryCode the insuranceCompanyEntryCode to set
	 */
	public void setInsuranceCompanyEntryCode(String[] insuranceCompanyEntryCode) {
		this.insuranceCompanyEntryCode = insuranceCompanyEntryCode;
	}

	/**
	 * @return the policyNumber
	 */
	public String[] getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @param policyNumber the policyNumber to set
	 */
	public void setPolicyNumber(String[] policyNumber) {
		this.policyNumber = policyNumber;
	}

	/**
	 * @return the propNumberOrCvNote
	 */
	public String[] getPropNumberOrCvNote() {
		return propNumberOrCvNote;
	}

	/**
	 * @param propNumberOrCvNote the propNumberOrCvNote to set
	 */
	public void setPropNumberOrCvNote(String[] propNumberOrCvNote) {
		this.propNumberOrCvNote = propNumberOrCvNote;
	}

	/**
	 * @return the insuredAmount
	 */
	public String[] getInsuredAmount() {
		return insuredAmount;
	}

	/**
	 * @param insuredAmount the insuredAmount to set
	 */
	public void setInsuredAmount(String[] insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	/**
	 * @return the insuredAmountCurrency
	 */
	public String[] getInsuredAmountCurrency() {
		return insuredAmountCurrency;
	}

	/**
	 * @param insuredAmountCurrency the insuredAmountCurrency to set
	 */
	public void setInsuredAmountCurrency(String[] insuredAmountCurrency) {
		this.insuredAmountCurrency = insuredAmountCurrency;
	}

	/**
	 * @return the insurancePremiumAmount
	 */
	public String[] getInsurancePremiumAmount() {
		return insurancePremiumAmount;
	}

	/**
	 * @param insurancePremiumAmount the insurancePremiumAmount to set
	 */
	public void setInsurancePremiumAmount(String[] insurancePremiumAmount) {
		this.insurancePremiumAmount = insurancePremiumAmount;
	}

	/**
	 * @return the insurancePremiumAmountCurrency
	 */
	public String[] getInsurancePremiumAmountCurrency() {
		return insurancePremiumAmountCurrency;
	}

	/**
	 * @param insurancePremiumAmountCurrency the insurancePremiumAmountCurrency
	 *        to set
	 */
	public void setInsurancePremiumAmountCurrency(String[] insurancePremiumAmountCurrency) {
		this.insurancePremiumAmountCurrency = insurancePremiumAmountCurrency;
	}

	/**
	 * @return the issuedDate
	 */
	public String[] getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @param issuedDate the issuedDate to set
	 */
	public void setIssuedDate(String[] issuedDate) {
		this.issuedDate = issuedDate;
	}

	/**
	 * @return the expiryDate
	 */
	public String[] getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(String[] expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the effectiveDate
	 */
	public String[] getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String[] effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the arrangementIndicator
	 */
	public String[] getArrangementIndicator() {
		return arrangementIndicator;
	}

	/**
	 * @param arrangementIndicator the arrangementIndicator to set
	 */
	public void setArrangementIndicator(String[] arrangementIndicator) {
		this.arrangementIndicator = arrangementIndicator;
	}

	/**
	 * @return the remarksInsurance
	 */
	public String[] getRemarksInsurance() {
		return remarksInsurance;
	}

	/**
	 * @param remarksInsurance the remarksInsurance to set
	 */
	public void setRemarksInsurance(String[] remarksInsurance) {
		this.remarksInsurance = remarksInsurance;
	}

}
