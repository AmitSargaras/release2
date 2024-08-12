/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/LoanAgencyForm.java,v 1.6 2004/06/26 04:26:31 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/26 04:26:31 $ Tag: $Name: $
 */
public class LoanAgencyForm extends CommonForm implements Serializable {
	private String securityID = "";

	private String[] limitID = new String[0];

	private String[] selectedLimitID = new String[0];

	private String adminAgentName = "";

	private String colAgentName = "";

	private String documentAgent = "";

	private String lcIssuingBank = "";

	private String borrower = "";

	private String[] borrowerList = new String[0];

	private String guarantor = "";

	private String[] guarantorList = new String[0];

	private String allowMultipleCurr = "";

	private String[] currency = new String[0];

	private String[] selectedCurrency = new String[0];

	private String globalAmountCcy = "";

	private String globalAmountAmt = "";

	private String facilityType = "";

	private String facCommitmentDate = "";

	private String facEffectiveDate = "";

	private String facMaturityDate = "";

	private String facTerminationDate = "";

	private String lastDateIssueLC = "";

	private String finalLCMaturityDate = "";

	private String transactionType = "";

	private String counselNameAgentBank = "";

	// sub-limit information
	private String subLimitID[] = new String[0];

	private String subLimitCcy[] = new String[0];

	private String subLimitAmt[] = new String[0];

	private String subLimitFacType[] = new String[0];

	private String deleteSubLimit[] = new String[0];

	private String totalSubLimit = "";

	// participant details
	private String[] participantID = new String[0];

	private String[] participant = new String[0];

	private String[] allocatedAmt = new String[0];

	private String[] percentageAmt = new String[0];

	private String[] pricing = new String[0];

	private String[] deleteParticipant = new String[0];

	private String instalmentEqualType = "";

	// term loan amortization schedules - Equal Instalments
	private String equalPrinAmtCcy = "";

	private String equalPrinAmtVal = "";

	private String equalNoInstalments = "";

	private String equalAmtPerInstalments = "";

	private String equalPaymentFreq = "";

	private String equalFirstPaymentDate = "";

	private String[] equalPaymentDate = new String[0];

	private String[] equalPrincipalAmt = new String[0];

	private String[] equalInterestAmt = new String[0];

	private String[] equalTotalPayment = new String[0];

	// term loan amortization schedules - Non-Equal Instalments
	private String nonEqPrinAmtCcy = "";

	private String nonEqPrinAmtVal = "";

	private String nonEqNoInstalments = "";

	private String nonEqPaymentFreq = "";

	private String nonEqFirstPaymentDate = "";

	private String[] nonEqPaymentDate = new String[0];

	private String[] nonEqPrincipalAmt = new String[0];

	private String[] nonEqInterestAmt = new String[0];

	private String[] nonEqTotalPayment = new String[0];

	// term loan amortization schedules - Debt Rate
	private String debtRate = "";

	private String calculateBase = "";

	private String totalAmtInterest = "";

	private String totalPayment = "";

	private String termOutOption = "";

	private String prePaymentOption = "";

	private String prePaymentMinCcy = "";

	private String prePaymentMinAmt = "";

	private String prePaymentPenalty = "";

	private String numDayNotice = "";

	private String governingLaw = "";

	private String interestDuration = "";

	private String interestDurationUnit = "";

	private String maxLoanOutstanding = "";

	private String minDrawdownAllowCcy = "";

	private String minDrawdownAllowAmt = "";

	private String maxDrawdownAllowCcy = "";

	private String maxDrawdownAllowAmt = "";

	private String minNumDrawdownAllow = "";

	private String maxNumDrawdownAllow = "";

	private String minAssignmentFeeCcy = "";

	private String minAssignmentFeeAmt = "";

	private String maxAssignmentFeeCcy = "";

	private String maxAssignmentFeeAmt = "";

	private String borrowerConsent = "";

	private String majorityLendConsent = "";

	private String defaultRate = "";

	// fees
	private String agencyFeeCcy = "";

	private String agencyFeeAmt = "";

	private String closingFeeCcy = "";

	private String closingFeeAmt = "";

	private String commitmentFeeCcy = "";

	private String commitmentFeeAmt = "";

	private String facilityFeeCcy = "";

	private String facilityFeeAmt = "";

	private String upfrontFeeCcy = "";

	private String upfrontFeeAmt = "";

	private String lcFeeCcy = "";

	private String lcFeeAmt = "";

	private String amendmentFeeCcy = "";

	private String amendmentFeeAmt = "";

	private String extensionFeeCcy = "";

	private String extensionFeeAmt = "";

	private String arrangementFeeCcy = "";

	private String arrangementFeeAmt = "";

	private String otherFeeCcy = "";

	private String otherFeeAmt = "";

	public String getSecurityID() {
		return securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String[] getLimitID() {
		return limitID;
	}

	public void setLimitID(String[] limitID) {
		this.limitID = limitID;
	}

	public String[] getSelectedLimitID() {
		return selectedLimitID;
	}

	public void setSelectedLimitID(String[] selectedLimitID) {
		this.selectedLimitID = selectedLimitID;
	}

	public String getAdminAgentName() {
		return adminAgentName;
	}

	public void setAdminAgentName(String adminAgentName) {
		this.adminAgentName = adminAgentName;
	}

	public String getColAgentName() {
		return colAgentName;
	}

	public void setColAgentName(String colAgentName) {
		this.colAgentName = colAgentName;
	}

	public String getDocumentAgent() {
		return documentAgent;
	}

	public void setDocumentAgent(String documentAgent) {
		this.documentAgent = documentAgent;
	}

	public String getLcIssuingBank() {
		return lcIssuingBank;
	}

	public void setLcIssuingBank(String lcIssuingBank) {
		this.lcIssuingBank = lcIssuingBank;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String[] getBorrowerList() {
		return borrowerList;
	}

	public void setBorrowerList(String[] borrowerList) {
		this.borrowerList = borrowerList;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String[] getGuarantorList() {
		return guarantorList;
	}

	public void setGuarantorList(String[] guarantorList) {
		this.guarantorList = guarantorList;
	}

	public String getAllowMultipleCurr() {
		return allowMultipleCurr;
	}

	public void setAllowMultipleCurr(String allowMultipleCurr) {
		this.allowMultipleCurr = allowMultipleCurr;
	}

	public String[] getCurrency() {
		return currency;
	}

	public void setCurrency(String[] currency) {
		this.currency = currency;
	}

	public String[] getSelectedCurrency() {
		return selectedCurrency;
	}

	public void setSelectedCurrency(String[] selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}

	public String getGlobalAmountCcy() {
		return globalAmountCcy;
	}

	public void setGlobalAmountCcy(String globalAmountCcy) {
		this.globalAmountCcy = globalAmountCcy;
	}

	public String getGlobalAmountAmt() {
		return globalAmountAmt;
	}

	public void setGlobalAmountAmt(String globalAmountAmt) {
		this.globalAmountAmt = globalAmountAmt;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getFacCommitmentDate() {
		return facCommitmentDate;
	}

	public void setFacCommitmentDate(String facCommitmentDate) {
		this.facCommitmentDate = facCommitmentDate;
	}

	public String getFacEffectiveDate() {
		return facEffectiveDate;
	}

	public void setFacEffectiveDate(String facEffectiveDate) {
		this.facEffectiveDate = facEffectiveDate;
	}

	public String getFacMaturityDate() {
		return facMaturityDate;
	}

	public void setFacMaturityDate(String facMaturityDate) {
		this.facMaturityDate = facMaturityDate;
	}

	public String getFacTerminationDate() {
		return facTerminationDate;
	}

	public void setFacTerminationDate(String facTerminationDate) {
		this.facTerminationDate = facTerminationDate;
	}

	public String getLastDateIssueLC() {
		return lastDateIssueLC;
	}

	public void setLastDateIssueLC(String lastDateIssueLC) {
		this.lastDateIssueLC = lastDateIssueLC;
	}

	public String getFinalLCMaturityDate() {
		return finalLCMaturityDate;
	}

	public void setFinalLCMaturityDate(String finalLCMaturityDate) {
		this.finalLCMaturityDate = finalLCMaturityDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCounselNameAgentBank() {
		return counselNameAgentBank;
	}

	public void setCounselNameAgentBank(String counselNameAgentBank) {
		this.counselNameAgentBank = counselNameAgentBank;
	}

	public String[] getSubLimitID() {
		return subLimitID;
	}

	public void setSubLimitID(String[] subLimitID) {
		this.subLimitID = subLimitID;
	}

	public String[] getSubLimitCcy() {
		return subLimitCcy;
	}

	public void setSubLimitCcy(String[] subLimitCcy) {
		this.subLimitCcy = subLimitCcy;
	}

	public String[] getSubLimitAmt() {
		return subLimitAmt;
	}

	public void setSubLimitAmt(String[] subLimitAmt) {
		this.subLimitAmt = subLimitAmt;
	}

	public String[] getSubLimitFacType() {
		return subLimitFacType;
	}

	public void setSubLimitFacType(String[] subLimitFacType) {
		this.subLimitFacType = subLimitFacType;
	}

	public String[] getDeleteSubLimit() {
		return deleteSubLimit;
	}

	public void setDeleteSubLimit(String[] deleteSubLimit) {
		this.deleteSubLimit = deleteSubLimit;
	}

	public String getTotalSubLimit() {
		return totalSubLimit;
	}

	public void setTotalSubLimit(String totalSubLimit) {
		this.totalSubLimit = totalSubLimit;
	}

	public String[] getParticipantID() {
		return participantID;
	}

	public void setParticipantID(String[] participantID) {
		this.participantID = participantID;
	}

	public String[] getParticipant() {
		return participant;
	}

	public void setParticipant(String[] participant) {
		this.participant = participant;
	}

	public String[] getAllocatedAmt() {
		return allocatedAmt;
	}

	public void setAllocatedAmt(String[] allocatedAmt) {
		this.allocatedAmt = allocatedAmt;
	}

	public String[] getPercentageAmt() {
		return percentageAmt;
	}

	public void setPercentageAmt(String[] percentageAmt) {
		this.percentageAmt = percentageAmt;
	}

	public String[] getPricing() {
		return pricing;
	}

	public void setPricing(String[] pricing) {
		this.pricing = pricing;
	}

	public String[] getDeleteParticipant() {
		return deleteParticipant;
	}

	public void setDeleteParticipant(String[] deleteParticipant) {
		this.deleteParticipant = deleteParticipant;
	}

	public String getInstalmentEqualType() {
		return instalmentEqualType;
	}

	public void setInstalmentEqualType(String instalmentEqualType) {
		this.instalmentEqualType = instalmentEqualType;
	}

	public String getEqualPrinAmtCcy() {
		return equalPrinAmtCcy;
	}

	public void setEqualPrinAmtCcy(String equalPrinAmtCcy) {
		this.equalPrinAmtCcy = equalPrinAmtCcy;
	}

	public String getEqualPrinAmtVal() {
		return equalPrinAmtVal;
	}

	public void setEqualPrinAmtVal(String equalPrinAmtVal) {
		this.equalPrinAmtVal = equalPrinAmtVal;
	}

	public String getEqualNoInstalments() {
		return equalNoInstalments;
	}

	public void setEqualNoInstalments(String equalNoInstalments) {
		this.equalNoInstalments = equalNoInstalments;
	}

	public String getEqualAmtPerInstalments() {
		return equalAmtPerInstalments;
	}

	public void setEqualAmtPerInstalments(String equalAmtPerInstalments) {
		this.equalAmtPerInstalments = equalAmtPerInstalments;
	}

	public String getEqualPaymentFreq() {
		return equalPaymentFreq;
	}

	public void setEqualPaymentFreq(String equalPaymentFreq) {
		this.equalPaymentFreq = equalPaymentFreq;
	}

	public String getEqualFirstPaymentDate() {
		return equalFirstPaymentDate;
	}

	public void setEqualFirstPaymentDate(String equalFirstPaymentDate) {
		this.equalFirstPaymentDate = equalFirstPaymentDate;
	}

	public String[] getEqualPaymentDate() {
		return equalPaymentDate;
	}

	public void setEqualPaymentDate(String[] equalPaymentDate) {
		this.equalPaymentDate = equalPaymentDate;
	}

	public String[] getEqualPrincipalAmt() {
		return equalPrincipalAmt;
	}

	public void setEqualPrincipalAmt(String[] equalPrincipalAmt) {
		this.equalPrincipalAmt = equalPrincipalAmt;
	}

	public String[] getEqualInterestAmt() {
		return equalInterestAmt;
	}

	public void setEqualInterestAmt(String[] equalInterestAmt) {
		this.equalInterestAmt = equalInterestAmt;
	}

	public String[] getEqualTotalPayment() {
		return equalTotalPayment;
	}

	public void setEqualTotalPayment(String[] equalTotalPayment) {
		this.equalTotalPayment = equalTotalPayment;
	}

	public String getNonEqPrinAmtCcy() {
		return nonEqPrinAmtCcy;
	}

	public void setNonEqPrinAmtCcy(String nonEqPrinAmtCcy) {
		this.nonEqPrinAmtCcy = nonEqPrinAmtCcy;
	}

	public String getNonEqPrinAmtVal() {
		return nonEqPrinAmtVal;
	}

	public void setNonEqPrinAmtVal(String nonEqPrinAmtVal) {
		this.nonEqPrinAmtVal = nonEqPrinAmtVal;
	}

	public String getNonEqNoInstalments() {
		return nonEqNoInstalments;
	}

	public void setNonEqNoInstalments(String nonEqNoInstalments) {
		this.nonEqNoInstalments = nonEqNoInstalments;
	}

	public String getNonEqPaymentFreq() {
		return nonEqPaymentFreq;
	}

	public void setNonEqPaymentFreq(String nonEqPaymentFreq) {
		this.nonEqPaymentFreq = nonEqPaymentFreq;
	}

	public String getNonEqFirstPaymentDate() {
		return nonEqFirstPaymentDate;
	}

	public void setNonEqFirstPaymentDate(String nonEqFirstPaymentDate) {
		this.nonEqFirstPaymentDate = nonEqFirstPaymentDate;
	}

	public String[] getNonEqPaymentDate() {
		return nonEqPaymentDate;
	}

	public void setNonEqPaymentDate(String[] nonEqPaymentDate) {
		this.nonEqPaymentDate = nonEqPaymentDate;
	}

	public String[] getNonEqPrincipalAmt() {
		return nonEqPrincipalAmt;
	}

	public void setNonEqPrincipalAmt(String[] nonEqPrincipalAmt) {
		this.nonEqPrincipalAmt = nonEqPrincipalAmt;
	}

	public String[] getNonEqInterestAmt() {
		return nonEqInterestAmt;
	}

	public void setNonEqInterestAmt(String[] nonEqInterestAmt) {
		this.nonEqInterestAmt = nonEqInterestAmt;
	}

	public String[] getNonEqTotalPayment() {
		return nonEqTotalPayment;
	}

	public void setNonEqTotalPayment(String[] nonEqTotalPayment) {
		this.nonEqTotalPayment = nonEqTotalPayment;
	}

	public String getDebtRate() {
		return debtRate;
	}

	public void setDebtRate(String debtRate) {
		this.debtRate = debtRate;
	}

	public String getCalculateBase() {
		return calculateBase;
	}

	public void setCalculateBase(String calculateBase) {
		this.calculateBase = calculateBase;
	}

	public String getTotalAmtInterest() {
		return totalAmtInterest;
	}

	public void setTotalAmtInterest(String totalAmtInterest) {
		this.totalAmtInterest = totalAmtInterest;
	}

	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getTermOutOption() {
		return termOutOption;
	}

	public void setTermOutOption(String termOutOption) {
		this.termOutOption = termOutOption;
	}

	public String getPrePaymentOption() {
		return prePaymentOption;
	}

	public void setPrePaymentOption(String prePaymentOption) {
		this.prePaymentOption = prePaymentOption;
	}

	public String getPrePaymentMinCcy() {
		return prePaymentMinCcy;
	}

	public void setPrePaymentMinCcy(String prePaymentMinCcy) {
		this.prePaymentMinCcy = prePaymentMinCcy;
	}

	public String getPrePaymentMinAmt() {
		return prePaymentMinAmt;
	}

	public void setPrePaymentMinAmt(String prePaymentMinAmt) {
		this.prePaymentMinAmt = prePaymentMinAmt;
	}

	public String getPrePaymentPenalty() {
		return prePaymentPenalty;
	}

	public void setPrePaymentPenalty(String prePaymentPenalty) {
		this.prePaymentPenalty = prePaymentPenalty;
	}

	public String getNumDayNotice() {
		return numDayNotice;
	}

	public void setNumDayNotice(String numDayNotice) {
		this.numDayNotice = numDayNotice;
	}

	public String getGoverningLaw() {
		return governingLaw;
	}

	public void setGoverningLaw(String governingLaw) {
		this.governingLaw = governingLaw;
	}

	public String getInterestDuration() {
		return interestDuration;
	}

	public void setInterestDuration(String interestDuration) {
		this.interestDuration = interestDuration;
	}

	public String getInterestDurationUnit() {
		return interestDurationUnit;
	}

	public void setInterestDurationUnit(String interestDurationUnit) {
		this.interestDurationUnit = interestDurationUnit;
	}

	public String getMaxLoanOutstanding() {
		return maxLoanOutstanding;
	}

	public void setMaxLoanOutstanding(String maxLoanOutstanding) {
		this.maxLoanOutstanding = maxLoanOutstanding;
	}

	public String getMinDrawdownAllowCcy() {
		return minDrawdownAllowCcy;
	}

	public void setMinDrawdownAllowCcy(String minDrawdownAllowCcy) {
		this.minDrawdownAllowCcy = minDrawdownAllowCcy;
	}

	public String getMinDrawdownAllowAmt() {
		return minDrawdownAllowAmt;
	}

	public void setMinDrawdownAllowAmt(String minDrawdownAllowAmt) {
		this.minDrawdownAllowAmt = minDrawdownAllowAmt;
	}

	public String getMaxDrawdownAllowCcy() {
		return maxDrawdownAllowCcy;
	}

	public void setMaxDrawdownAllowCcy(String maxDrawdownAllowCcy) {
		this.maxDrawdownAllowCcy = maxDrawdownAllowCcy;
	}

	public String getMaxDrawdownAllowAmt() {
		return maxDrawdownAllowAmt;
	}

	public void setMaxDrawdownAllowAmt(String maxDrawdownAllowAmt) {
		this.maxDrawdownAllowAmt = maxDrawdownAllowAmt;
	}

	public String getMinNumDrawdownAllow() {
		return minNumDrawdownAllow;
	}

	public void setMinNumDrawdownAllow(String minNumDrawdownAllow) {
		this.minNumDrawdownAllow = minNumDrawdownAllow;
	}

	public String getMaxNumDrawdownAllow() {
		return maxNumDrawdownAllow;
	}

	public void setMaxNumDrawdownAllow(String maxNumDrawdownAllow) {
		this.maxNumDrawdownAllow = maxNumDrawdownAllow;
	}

	public String getMinAssignmentFeeCcy() {
		return minAssignmentFeeCcy;
	}

	public void setMinAssignmentFeeCcy(String minAssignmentFeeCcy) {
		this.minAssignmentFeeCcy = minAssignmentFeeCcy;
	}

	public String getMinAssignmentFeeAmt() {
		return minAssignmentFeeAmt;
	}

	public void setMinAssignmentFeeAmt(String minAssignmentFeeAmt) {
		this.minAssignmentFeeAmt = minAssignmentFeeAmt;
	}

	public String getMaxAssignmentFeeCcy() {
		return maxAssignmentFeeCcy;
	}

	public void setMaxAssignmentFeeCcy(String maxAssignmentFeeCcy) {
		this.maxAssignmentFeeCcy = maxAssignmentFeeCcy;
	}

	public String getMaxAssignmentFeeAmt() {
		return maxAssignmentFeeAmt;
	}

	public void setMaxAssignmentFeeAmt(String maxAssignmentFeeAmt) {
		this.maxAssignmentFeeAmt = maxAssignmentFeeAmt;
	}

	public String getBorrowerConsent() {
		return borrowerConsent;
	}

	public void setBorrowerConsent(String borrowerConsent) {
		this.borrowerConsent = borrowerConsent;
	}

	public String getMajorityLendConsent() {
		return majorityLendConsent;
	}

	public void setMajorityLendConsent(String majorityLendConsent) {
		this.majorityLendConsent = majorityLendConsent;
	}

	public String getDefaultRate() {
		return defaultRate;
	}

	public void setDefaultRate(String defaultRate) {
		this.defaultRate = defaultRate;
	}

	public String getAgencyFeeCcy() {
		return agencyFeeCcy;
	}

	public void setAgencyFeeCcy(String agencyFeeCcy) {
		this.agencyFeeCcy = agencyFeeCcy;
	}

	public String getAgencyFeeAmt() {
		return agencyFeeAmt;
	}

	public void setAgencyFeeAmt(String agencyFeeAmt) {
		this.agencyFeeAmt = agencyFeeAmt;
	}

	public String getClosingFeeCcy() {
		return closingFeeCcy;
	}

	public void setClosingFeeCcy(String closingFeeCcy) {
		this.closingFeeCcy = closingFeeCcy;
	}

	public String getClosingFeeAmt() {
		return closingFeeAmt;
	}

	public void setClosingFeeAmt(String closingFeeAmt) {
		this.closingFeeAmt = closingFeeAmt;
	}

	public String getCommitmentFeeCcy() {
		return commitmentFeeCcy;
	}

	public void setCommitmentFeeCcy(String commitmentFeeCcy) {
		this.commitmentFeeCcy = commitmentFeeCcy;
	}

	public String getCommitmentFeeAmt() {
		return commitmentFeeAmt;
	}

	public void setCommitmentFeeAmt(String commitmentFeeAmt) {
		this.commitmentFeeAmt = commitmentFeeAmt;
	}

	public String getFacilityFeeCcy() {
		return facilityFeeCcy;
	}

	public void setFacilityFeeCcy(String facilityFeeCcy) {
		this.facilityFeeCcy = facilityFeeCcy;
	}

	public String getFacilityFeeAmt() {
		return facilityFeeAmt;
	}

	public void setFacilityFeeAmt(String facilityFeeAmt) {
		this.facilityFeeAmt = facilityFeeAmt;
	}

	public String getUpfrontFeeCcy() {
		return upfrontFeeCcy;
	}

	public void setUpfrontFeeCcy(String upfrontFeeCcy) {
		this.upfrontFeeCcy = upfrontFeeCcy;
	}

	public String getUpfrontFeeAmt() {
		return upfrontFeeAmt;
	}

	public void setUpfrontFeeAmt(String upfrontFeeAmt) {
		this.upfrontFeeAmt = upfrontFeeAmt;
	}

	public String getLcFeeCcy() {
		return lcFeeCcy;
	}

	public void setLcFeeCcy(String lcFeeCcy) {
		this.lcFeeCcy = lcFeeCcy;
	}

	public String getLcFeeAmt() {
		return lcFeeAmt;
	}

	public void setLcFeeAmt(String lcFeeAmt) {
		this.lcFeeAmt = lcFeeAmt;
	}

	public String getAmendmentFeeCcy() {
		return amendmentFeeCcy;
	}

	public void setAmendmentFeeCcy(String amendmentFeeCcy) {
		this.amendmentFeeCcy = amendmentFeeCcy;
	}

	public String getAmendmentFeeAmt() {
		return amendmentFeeAmt;
	}

	public void setAmendmentFeeAmt(String amendmentFeeAmt) {
		this.amendmentFeeAmt = amendmentFeeAmt;
	}

	public String getExtensionFeeCcy() {
		return extensionFeeCcy;
	}

	public void setExtensionFeeCcy(String extensionFeeCcy) {
		this.extensionFeeCcy = extensionFeeCcy;
	}

	public String getExtensionFeeAmt() {
		return extensionFeeAmt;
	}

	public void setExtensionFeeAmt(String extensionFeeAmt) {
		this.extensionFeeAmt = extensionFeeAmt;
	}

	public String getArrangementFeeCcy() {
		return arrangementFeeCcy;
	}

	public void setArrangementFeeCcy(String arrangementFeeCcy) {
		this.arrangementFeeCcy = arrangementFeeCcy;
	}

	public String getArrangementFeeAmt() {
		return arrangementFeeAmt;
	}

	public void setArrangementFeeAmt(String arrangementFeeAmt) {
		this.arrangementFeeAmt = arrangementFeeAmt;
	}

	public String getOtherFeeCcy() {
		return otherFeeCcy;
	}

	public void setOtherFeeCcy(String otherFeeCcy) {
		this.otherFeeCcy = otherFeeCcy;
	}

	public String getOtherFeeAmt() {
		return otherFeeAmt;
	}

	public void setOtherFeeAmt(String otherFeeAmt) {
		this.otherFeeAmt = otherFeeAmt;
	}

	public String[][] getMapper() {
		String[][] input = { { "loanAgencyObj",
				"com.integrosys.cms.ui.collateral.commodity.loanagency.LoanAgencyMapper" }, };
		return input;
	}
}
