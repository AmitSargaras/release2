package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

public interface IFacilityGeneral extends Serializable {
	public Date getApplicationDate();

	public Amount getApprovedAmount();

	public Date getApprovedDate();

	public String getCancelOrRejectCategoryCode();

	public Date getCancelOrRejectDate();

	public String getCancelOrRejectEntryCode();

	public String getCarCategoryCode();

	public Boolean getCarCodeFlag();

	public String getCarEntryCode();

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode();

	public Date getEnteredDate();

	public long getFacilityMasterId();

	public String getFacilityStatusCategoryCode();

	public String getFacilityStatusEntryCode();

	public Amount getFinalPaymentAmount();

	public Amount getFinancedAmount();

	public Amount getInstallmentAmount();

	public String getLimitStatusCategoryCode();

	public String getLimitStatusEntryCode();

	public String getLoanPurposeCategoryCode();

	public String getLoanPurposeEntryCode();

	public Date getOfferAcceptedDate();

	public Date getOfferDate();

	public String getOfficerCategoryCode();

	public String getOfficerEntryCode();

	public Amount getOriginalAmount();

	public Amount getOustandingBalanceAmount();

	public String getPersonApprovedCategoryCode();

	public String getPersonApprovedEntryCode();

	public Integer getTerm();

	public String getTermCodeCategoryCode();

	public String getTermCodeEntryCode();

	public Amount getUtilisedAmount();

	public void setApplicationDate(Date applicationDate);

	public void setApprovedAmount(Amount approvedAmount);

	public void setApprovedDate(Date approvedDate);

	public void setCancelOrRejectCategoryCode(String cancelOrRejectCategoryCode);

	public void setCancelOrRejectDate(Date cancelOrRejectDate);

	public void setCancelOrRejectEntryCode(String cancelOrRejectEntryCode);

	public void setCarCategoryCode(String carCategoryCode);

	public void setCarCodeFlag(Boolean carCodeFlag);

	public void setCarEntryCode(String carEntryCode);

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode);;

	public void setEnteredDate(Date enteredDate);

	public void setFacilityMasterId(long facilityMasterId);

	public void setFacilityStatusCategoryCode(String facilityStatusCategoryCode);

	public void setFacilityStatusEntryCode(String facilityStatusEntryCode);

	public void setFinalPaymentAmount(Amount finalPaymentAmount);

	public void setFinancedAmount(Amount financedAmount);

	public void setInstallmentAmount(Amount installmentAmount);

	public void setLimitStatusCategoryCode(String limitStatusCategoryCode);

	public void setLimitStatusEntryCode(String limitStatusEntryCode);

	public void setLoanPurposeCategoryCode(String loanPurposeCategoryCode);

	public void setLoanPurposeEntryCode(String loanPurposeEntryCode);

	public void setOfferAcceptedDate(Date offerAcceptedDate);

	public void setOfferDate(Date offerDate);

	public void setOfficerCategoryCode(String officerCategoryCode);

	public void setOfficerEntryCode(String officerEntryCode);

	public void setOriginalAmount(Amount originalAmount);

	public void setOustandingBalanceAmount(Amount oustandingBalanceAmount);

	public void setPersonApprovedCategoryCode(String personApprovedCategoryCode);

	public void setPersonApprovedEntryCode(String personApprovedEntryCode);

	public void setTerm(Integer term);

	public void setTermCodeCategoryCode(String termCodeCategoryCode);

	public void setTermCodeEntryCode(String termCodeEntryCode);

	public void setUtilisedAmount(Amount utilisedAmount);
}
