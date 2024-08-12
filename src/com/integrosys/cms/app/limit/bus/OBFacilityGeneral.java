package com.integrosys.cms.app.limit.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityGeneral implements IFacilityGeneral {
	private static final long serialVersionUID = 3440918706845904969L;

	private long facilityMasterId;

	private String currencyCode;

	private Amount financedAmount;

	private Date enteredDate;

	private Date applicationDate;

	private Amount originalAmount;

	private Amount installmentAmount;

	private Amount finalPaymentAmount;

	private Amount utilisedAmount;

	private Amount oustandingBalanceAmount;

	private Amount approvedAmount;

	private String termCodeCategoryCode;

	private String termCodeEntryCode;

	private Integer term;

	private String loanPurposeCategoryCode;

	private String loanPurposeEntryCode;

	private Date offerAcceptedDate;

	private Date offerDate;

	private String personApprovedCategoryCode;

	private String personApprovedEntryCode;

	private Date approvedDate;

	private String cancelOrRejectCategoryCode;

	private String cancelOrRejectEntryCode;

	private Date cancelOrRejectDate;

	private String carCategoryCode;

	private String carEntryCode;

	private Boolean carCodeFlag;

	private String officerCategoryCode;

	private String officerEntryCode;

	private String limitStatusCategoryCode;

	private String limitStatusEntryCode;

	private String facilityStatusCategoryCode;

	private String facilityStatusEntryCode;

	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}

	public Amount getApprovedAmount() {
		return approvedAmount;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @return the cancelOrRejectCategoryCode
	 */
	public String getCancelOrRejectCategoryCode() {
		return cancelOrRejectCategoryCode;
	}

	/**
	 * @return the cancelOrRejectDate
	 */
	public Date getCancelOrRejectDate() {
		return cancelOrRejectDate;
	}

	/**
	 * @return the cancelOrRejectEntryCode
	 */
	public String getCancelOrRejectEntryCode() {
		return cancelOrRejectEntryCode;
	}

	/**
	 * @return the carCategoryCode
	 */
	public String getCarCategoryCode() {
		return carCategoryCode;
	}

	/**
	 * @return the carCodeFlag
	 */
	public Boolean getCarCodeFlag() {
		return carCodeFlag;
	}

	/**
	 * @return the carEntryCode
	 */
	public String getCarEntryCode() {
		return carEntryCode;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return the enteredDate
	 */
	public Date getEnteredDate() {
		return enteredDate;
	}

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @return the facilityStatusCategoryCode
	 */
	public String getFacilityStatusCategoryCode() {
		return facilityStatusCategoryCode;
	}

	/**
	 * @return the facilityStatusEntryCode
	 */
	public String getFacilityStatusEntryCode() {
		return facilityStatusEntryCode;
	}

	/**
	 * @return the finalPaymentAmount
	 */
	public Amount getFinalPaymentAmount() {
		return finalPaymentAmount;
	}

	/**
	 * @return the financedAmount
	 */
	public Amount getFinancedAmount() {
		return financedAmount;
	}

	/**
	 * @return the installmentAmount
	 */
	public Amount getInstallmentAmount() {
		return installmentAmount;
	}

	/**
	 * @return the limitStatusCategoryCode
	 */
	public String getLimitStatusCategoryCode() {
		return limitStatusCategoryCode;
	}

	/**
	 * @return the limitStatusEntryCode
	 */
	public String getLimitStatusEntryCode() {
		return limitStatusEntryCode;
	}

	/**
	 * @return the loanPurposeCategoryCode
	 */
	public String getLoanPurposeCategoryCode() {
		return loanPurposeCategoryCode;
	}

	/**
	 * @return the loanPurposeEntryCode
	 */
	public String getLoanPurposeEntryCode() {
		return loanPurposeEntryCode;
	}

	/**
	 * @return the offerAcceptedDate
	 */
	public Date getOfferAcceptedDate() {
		return offerAcceptedDate;
	}

	/**
	 * @return the offerDate
	 */
	public Date getOfferDate() {
		return offerDate;
	}

	/**
	 * @return the officerCategoryCode
	 */
	public String getOfficerCategoryCode() {
		return officerCategoryCode;
	}

	/**
	 * @return the officerEntryCode
	 */
	public String getOfficerEntryCode() {
		return officerEntryCode;
	}

	/**
	 * @return the originalAmount
	 */
	public Amount getOriginalAmount() {
		return originalAmount;
	}

	/**
	 * @return the oustandingBalanceAmount
	 */
	public Amount getOustandingBalanceAmount() {
		return oustandingBalanceAmount;
	}

	/**
	 * @return the personApprovedCategoryCode
	 */
	public String getPersonApprovedCategoryCode() {
		return personApprovedCategoryCode;
	}

	/**
	 * @return the personApprovedEntryCode
	 */
	public String getPersonApprovedEntryCode() {
		return personApprovedEntryCode;
	}

	/**
	 * @return the term
	 */
	public Integer getTerm() {
		return term;
	}

	/**
	 * @return the termCodeCategoryCode
	 */
	public String getTermCodeCategoryCode() {
		return termCodeCategoryCode;
	}

	/**
	 * @return the termCodeEntryCode
	 */
	public String getTermCodeEntryCode() {
		return termCodeEntryCode;
	}

	/**
	 * @return the utilisedAmount
	 */
	public Amount getUtilisedAmount() {
		return utilisedAmount;
	}

	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public void setApprovedAmount(Amount approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @param cancelOrRejectCategoryCode the cancelOrRejectCategoryCode to set
	 */
	public void setCancelOrRejectCategoryCode(String cancelOrRejectCategoryCode) {
		this.cancelOrRejectCategoryCode = cancelOrRejectCategoryCode;
	}

	/**
	 * @param cancelOrRejectDate the cancelOrRejectDate to set
	 */
	public void setCancelOrRejectDate(Date cancelOrRejectDate) {
		this.cancelOrRejectDate = cancelOrRejectDate;
	}

	/**
	 * @param cancelOrRejectEntryCode the cancelOrRejectEntryCode to set
	 */
	public void setCancelOrRejectEntryCode(String cancelOrRejectEntryCode) {
		this.cancelOrRejectEntryCode = cancelOrRejectEntryCode;
	}

	/**
	 * @param carCategoryCode the carCategoryCode to set
	 */
	public void setCarCategoryCode(String carCategoryCode) {
		this.carCategoryCode = carCategoryCode;
	}

	/**
	 * @param carCodeFlag the carCodeFlag to set
	 */
	public void setCarCodeFlag(Boolean carCodeFlag) {
		this.carCodeFlag = carCodeFlag;
	}

	/**
	 * @param carEntryCode the carEntryCode to set
	 */
	public void setCarEntryCode(String carEntryCode) {
		this.carEntryCode = carEntryCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @param enteredDate the enteredDate to set
	 */
	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @param facilityStatusCategoryCode the facilityStatusCategoryCode to set
	 */
	public void setFacilityStatusCategoryCode(String facilityStatusCategoryCode) {
		this.facilityStatusCategoryCode = facilityStatusCategoryCode;
	}

	/**
	 * @param facilityStatusEntryCode the facilityStatusEntryCode to set
	 */
	public void setFacilityStatusEntryCode(String facilityStatusEntryCode) {
		this.facilityStatusEntryCode = facilityStatusEntryCode;
	}

	/**
	 * @param finalPaymentAmount the finalPaymentAmount to set
	 */
	public void setFinalPaymentAmount(Amount finalPaymentAmount) {
		this.finalPaymentAmount = finalPaymentAmount;
	}

	/**
	 * @param financedAmount the financedAmount to set
	 */
	public void setFinancedAmount(Amount financedAmount) {
		this.financedAmount = financedAmount;
	}

	/**
	 * @param installmentAmount the installmentAmount to set
	 */
	public void setInstallmentAmount(Amount installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	/**
	 * @param limitStatusCategoryCode the limitStatusCategoryCode to set
	 */
	public void setLimitStatusCategoryCode(String limitStatusCategoryCode) {
		this.limitStatusCategoryCode = limitStatusCategoryCode;
	}

	/**
	 * @param limitStatusEntryCode the limitStatusEntryCode to set
	 */
	public void setLimitStatusEntryCode(String limitStatusEntryCode) {
		this.limitStatusEntryCode = limitStatusEntryCode;
	}

	/**
	 * @param loanPurposeCategoryCode the loanPurposeCategoryCode to set
	 */
	public void setLoanPurposeCategoryCode(String loanPurposeCategoryCode) {
		this.loanPurposeCategoryCode = loanPurposeCategoryCode;
	}

	/**
	 * @param loanPurposeEntryCode the loanPurposeEntryCode to set
	 */
	public void setLoanPurposeEntryCode(String loanPurposeEntryCode) {
		this.loanPurposeEntryCode = loanPurposeEntryCode;
	}

	/**
	 * @param offerAcceptedDate the offerAcceptedDate to set
	 */
	public void setOfferAcceptedDate(Date offerAcceptedDate) {
		this.offerAcceptedDate = offerAcceptedDate;
	}

	/**
	 * @param offerDate the offerDate to set
	 */
	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	/**
	 * @param officerCategoryCode the officerCategoryCode to set
	 */
	public void setOfficerCategoryCode(String officerCategoryCode) {
		this.officerCategoryCode = officerCategoryCode;
	}

	/**
	 * @param officerEntryCode the officerEntryCode to set
	 */
	public void setOfficerEntryCode(String officerEntryCode) {
		this.officerEntryCode = officerEntryCode;
	}

	/**
	 * @param originalAmount the originalAmount to set
	 */
	public void setOriginalAmount(Amount originalAmount) {
		this.originalAmount = originalAmount;
	}

	/**
	 * @param oustandingBalanceAmount the oustandingBalanceAmount to set
	 */
	public void setOustandingBalanceAmount(Amount oustandingBalanceAmount) {
		this.oustandingBalanceAmount = oustandingBalanceAmount;
	}

	/**
	 * @param personApprovedCategoryCode the personApprovedCategoryCode to set
	 */
	public void setPersonApprovedCategoryCode(String personApprovedCategoryCode) {
		this.personApprovedCategoryCode = personApprovedCategoryCode;
	}

	/**
	 * @param personApprovedEntryCode the personApprovedEntryCode to set
	 */
	public void setPersonApprovedEntryCode(String personApprovedEntryCode) {
		this.personApprovedEntryCode = personApprovedEntryCode;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(Integer term) {
		this.term = term;
	}

	/**
	 * @param termCodeCategoryCode the termCodeCategoryCode to set
	 */
	public void setTermCodeCategoryCode(String termCodeCategoryCode) {
		this.termCodeCategoryCode = termCodeCategoryCode;
	}

	/**
	 * @param termCodeEntryCode the termCodeEntryCode to set
	 */
	public void setTermCodeEntryCode(String termCodeEntryCode) {
		this.termCodeEntryCode = termCodeEntryCode;
	}

	/**
	 * @param utilisedAmount the utilisedAmount to set
	 */
	public void setUtilisedAmount(Amount utilisedAmount) {
		this.utilisedAmount = utilisedAmount;
	}

}
