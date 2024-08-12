/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBLoanAgency.java,v 1.31 2004/08/18 08:00:51 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: May 3, 2004 Time: 1:37:05
 * PM To change this template use File | Settings | File Templates.
 */
public class OBLoanAgency implements ILoanAgency {

	private long loanAgencyID = ICMSConstant.LONG_INVALID_VALUE;

	private ILoanLimit[] limitIDs;

	private String administrativeAgentName;

	private String collateralAgentName;

	private String documentAgent;

	private String LCIssuingBank;

	private IBorrower[] borrowers;

	private IGuarantor[] guarantors;

	private ISubLimit[] sublimits;

	private ILoanSchedule[] loanSchedules;

	private boolean isMultipleCurrencies;

	private String[] globalCurrencies;

	private Amount globalAmount;

	private Date facilityCommitmentDate;

	private Date facilityEffectiveDate;

	private Date facilityMaturityDate;

	private Date facilityTerminationDate;

	private Date lastDateToIssueLC;

	private Date finalLCMaturityDate;

	private String typeOfTransaction; // commoncode - list

	private String agentBankCounselName;

	private IParticipant[] participants;

	// Term Loan Amortization schedules
	private boolean isEqualInstalments;

	private Amount principleAmount;

	private int numberOfInstalments; // for use by UI to do calculation for loan
										// schedule

	private String frequencyOfPayment;

	private Date dateOfPayment; // for use by UI to do calculation for loan
								// schedule

	private double debtRate = ICMSConstant.DOUBLE_INVALID_VALUE;

	private int calculationBaseNumberOfDays;

	private boolean isTermOutOption;

	private boolean isPrepaymentOption;

	private Amount prepaymentMinAmount;

	private boolean isPrepaymentPenalty;

	private int numberOfNoticeDays;

	private String governingLaw;

	private int interestPeriodDuration;

	private int maxNumberOfLoanOutstanding;

	private Amount minDrawdownAmountAllowed;

	private int minNumberOfDrawdownsAllowed = ICMSConstant.INT_INVALID_VALUE;

	private Amount minAssignmentFees;

	private Amount maxDrawdownAmountAllowed;

	private int maxNumberOfDrawdownsAllowed = ICMSConstant.INT_INVALID_VALUE;

	private Amount maxAssignmentFees;

	private boolean isConsentFromBorrower;

	private double majorityLendersConsent;

	private double defaultRate;

	// Fees
	private Amount agencyFees;

	private Amount closingFees;

	private Amount commitmentFees;

	private Amount facilityFees;

	private Amount upfrontFees;

	private Amount LCFees;

	private Amount amendmentFees;

	private Amount extensionFees;

	private Amount arrangementFees;

	private Amount otherFees;

	private String interestPeriodDurationUnit; // commoncode - list

	private String facilityType;

	private String status;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	public ILoanLimit[] getLimitIDs() {
		return limitIDs;
	}

	public void setLimitIDs(ILoanLimit[] limitIDs) {
		this.limitIDs = limitIDs;
	}

	public String getAdministrativeAgentName() {
		return administrativeAgentName;
	}

	public void setAdministrativeAgentName(String administrativeAgentName) {
		this.administrativeAgentName = administrativeAgentName;
	}

	public Amount getAgencyFees() {
		return agencyFees;
	}

	public void setAgencyFees(Amount agencyFees) {
		this.agencyFees = agencyFees;
	}

	public String getAgentBankCounselName() {
		return agentBankCounselName;
	}

	public void setAgentBankCounselName(String agentBankCounselName) {
		this.agentBankCounselName = agentBankCounselName;
	}

	public Amount getAmendmentFees() {
		return amendmentFees;
	}

	public void setAmendmentFees(Amount amendmentFees) {
		this.amendmentFees = amendmentFees;
	}

	public Amount getArrangementFees() {
		return arrangementFees;
	}

	public void setArrangementFees(Amount arrangementFees) {
		this.arrangementFees = arrangementFees;
	}

	public IBorrower[] getBorrowers() {
		return borrowers;
	}

	public void setBorrowers(IBorrower[] borrowers) {
		this.borrowers = borrowers;
	}

	public Amount getClosingFees() {
		return closingFees;
	}

	public void setClosingFees(Amount closingFees) {
		this.closingFees = closingFees;
	}

	public String getCollateralAgentName() {
		return collateralAgentName;
	}

	public void setCollateralAgentName(String collateralAgentName) {
		this.collateralAgentName = collateralAgentName;
	}

	public Amount getCommitmentFees() {
		return commitmentFees;
	}

	public void setCommitmentFees(Amount commitmentFees) {
		this.commitmentFees = commitmentFees;
	}

	public boolean getIsConsentFromBorrower() {
		return isConsentFromBorrower;
	}

	public void setIsConsentFromBorrower(boolean consentFromBorrower) {
		isConsentFromBorrower = consentFromBorrower;
	}

	public double getDefaultRate() {
		return defaultRate;
	}

	public void setDefaultRate(double defaultRate) {
		this.defaultRate = defaultRate;
	}

	public String getDocumentAgent() {
		return documentAgent;
	}

	public void setDocumentAgent(String documentAgent) {
		this.documentAgent = documentAgent;
	}

	public Amount getExtensionFees() {
		return extensionFees;
	}

	public void setExtensionFees(Amount extensionFees) {
		this.extensionFees = extensionFees;
	}

	public Date getFacilityCommitmentDate() {
		return facilityCommitmentDate;
	}

	public void setFacilityCommitmentDate(Date facilityCommitmentDate) {
		this.facilityCommitmentDate = facilityCommitmentDate;
	}

	public Date getFacilityEffectiveDate() {
		return facilityEffectiveDate;
	}

	public void setFacilityEffectiveDate(Date facilityEffectiveDate) {
		this.facilityEffectiveDate = facilityEffectiveDate;
	}

	public Amount getFacilityFees() {
		return facilityFees;
	}

	public void setFacilityFees(Amount facilityFees) {
		this.facilityFees = facilityFees;
	}

	public Date getFacilityMaturityDate() {
		return facilityMaturityDate;
	}

	public void setFacilityMaturityDate(Date facilityMaturityDate) {
		this.facilityMaturityDate = facilityMaturityDate;
	}

	public Date getFacilityTerminationDate() {
		return facilityTerminationDate;
	}

	public void setFacilityTerminationDate(Date facilityTerminationDate) {
		this.facilityTerminationDate = facilityTerminationDate;
	}

	public Date getFinalLCMaturityDate() {
		return finalLCMaturityDate;
	}

	public void setFinalLCMaturityDate(Date finalLCMaturityDate) {
		this.finalLCMaturityDate = finalLCMaturityDate;
	}

	public Amount getGlobalAmount() {
		return globalAmount;
	}

	public void setGlobalAmount(Amount globalAmount) {
		this.globalAmount = globalAmount;
	}

	public boolean getIsMultipleCurrencies() {
		return isMultipleCurrencies;
	}

	public void setIsMultipleCurrencies(boolean isMultipleCurrencies) {
		this.isMultipleCurrencies = isMultipleCurrencies;
	}

	public String[] getGlobalCurrencies() {
		return globalCurrencies;
	}

	public void setGlobalCurrencies(String[] globalCurrencies) {
		this.globalCurrencies = globalCurrencies;
	}

	public String getGoverningLaw() {
		return governingLaw;
	}

	public void setGoverningLaw(String governingLaw) {
		this.governingLaw = governingLaw;
	}

	public IGuarantor[] getGuarantors() {
		return guarantors;
	}

	public void setGuarantors(IGuarantor[] guarantors) {
		this.guarantors = guarantors;
	}

	public ISubLimit[] getSubLimits() {
		return sublimits;
	}

	public void setSubLimits(ISubLimit[] sublimits) {
		this.sublimits = sublimits;
	}

	public ILoanSchedule[] getLoanSchedules() {
		return loanSchedules;
	}

	public void setLoanSchedules(ILoanSchedule[] loanSchedules) {
		this.loanSchedules = loanSchedules;
		if ((loanSchedules != null) && (loanSchedules.length > 0)) {
			setDateOfPayment(loanSchedules[0].getPaymentDate());
			setNumberOfInstalments(loanSchedules.length);
		}
	}

	public int getInterestPeriodDuration() {
		return interestPeriodDuration;
	}

	public void setInterestPeriodDuration(int interestPeriodDuration) {
		this.interestPeriodDuration = interestPeriodDuration;
	}

	public String getInterestPeriodDurationUnit() {
		return interestPeriodDurationUnit;
	}

	public void setInterestPeriodDurationUnit(String interestPeriodDurationUnit) {
		this.interestPeriodDurationUnit = interestPeriodDurationUnit;
	}

	public Date getLastDateToIssueLC() {
		return lastDateToIssueLC;
	}

	public void setLastDateToIssueLC(Date lastDateToIssueLC) {
		this.lastDateToIssueLC = lastDateToIssueLC;
	}

	public Amount getLCFees() {
		return LCFees;
	}

	public void setLCFees(Amount LCFees) {
		this.LCFees = LCFees;
	}

	public String getLCIssuingBank() {
		return LCIssuingBank;
	}

	public void setLCIssuingBank(String lCIssuingBank) {
		this.LCIssuingBank = lCIssuingBank;
	}

	public long getLoanAgencyID() {
		return loanAgencyID;
	}

	public void setLoanAgencyID(long loanAgencyID) {
		this.loanAgencyID = loanAgencyID;
	}

	public double getMajorityLendersConsent() {
		return majorityLendersConsent;
	}

	public void setMajorityLendersConsent(double majorityLendersConsent) {
		this.majorityLendersConsent = majorityLendersConsent;
	}

	public Amount getMaxAssignmentFees() {
		return maxAssignmentFees;
	}

	public void setMaxAssignmentFees(Amount maxAssignmentFees) {
		this.maxAssignmentFees = maxAssignmentFees;
	}

	public Amount getMaxDrawdownAmountAllowed() {
		return maxDrawdownAmountAllowed;
	}

	public void setMaxDrawdownAmountAllowed(Amount maxDrawdownAmountAllowed) {
		this.maxDrawdownAmountAllowed = maxDrawdownAmountAllowed;
	}

	public int getMaxNumberOfDrawdownsAllowed() {
		return maxNumberOfDrawdownsAllowed;
	}

	public void setMaxNumberOfDrawdownsAllowed(int maxNumberOfDrawdownsAllowed) {
		this.maxNumberOfDrawdownsAllowed = maxNumberOfDrawdownsAllowed;
	}

	public int getMaxNumberOfLoanOutstanding() {
		return maxNumberOfLoanOutstanding;
	}

	public void setMaxNumberOfLoanOutstanding(int maxNumberOfLoanOutstanding) {
		this.maxNumberOfLoanOutstanding = maxNumberOfLoanOutstanding;
	}

	public Amount getMinAssignmentFees() {
		return minAssignmentFees;
	}

	public void setMinAssignmentFees(Amount minAssignmentFees) {
		this.minAssignmentFees = minAssignmentFees;
	}

	public Amount getMinDrawdownAmountAllowed() {
		return minDrawdownAmountAllowed;
	}

	public void setMinDrawdownAmountAllowed(Amount minDrawdownAmountAllowed) {
		this.minDrawdownAmountAllowed = minDrawdownAmountAllowed;
	}

	public int getMinNumberOfDrawdownsAllowed() {
		return minNumberOfDrawdownsAllowed;
	}

	public void setMinNumberOfDrawdownsAllowed(int minNumberOfDrawdownsAllowed) {
		this.minNumberOfDrawdownsAllowed = minNumberOfDrawdownsAllowed;
	}

	public int getNumberOfNoticeDays() {
		return numberOfNoticeDays;
	}

	public void setNumberOfNoticeDays(int numberOfNoticeDays) {
		this.numberOfNoticeDays = numberOfNoticeDays;
	}

	public Amount getOtherFees() {
		return otherFees;
	}

	public void setOtherFees(Amount otherFees) {
		this.otherFees = otherFees;
	}

	public IParticipant[] getParticipants() {
		return participants;
	}

	public void setParticipants(IParticipant[] participants) {
		this.participants = participants;
	}

	public Amount getPrepaymentMinAmount() {
		return prepaymentMinAmount;
	}

	public void setPrepaymentMinAmount(Amount prepaymentMinAmount) {
		this.prepaymentMinAmount = prepaymentMinAmount;
	}

	public boolean getIsPrepaymentOption() {
		return isPrepaymentOption;
	}

	public void setIsPrepaymentOption(boolean isPrepaymentOption) {
		this.isPrepaymentOption = isPrepaymentOption;
	}

	public boolean getIsPrepaymentPenalty() {
		return isPrepaymentPenalty;
	}

	public void setIsPrepaymentPenalty(boolean isPrepaymentPenalty) {
		this.isPrepaymentPenalty = isPrepaymentPenalty;
	}

	public String getTypeOfTransaction() {
		return typeOfTransaction;
	}

	public void setTypeOfTransaction(String typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}

	public boolean getIsEqualInstalments() {
		return isEqualInstalments;
	}

	public void setIsEqualInstalments(boolean isEqualInstalments) {
		this.isEqualInstalments = isEqualInstalments;
	}

	public Date getDateOfPayment() {
		return (dateOfPayment == null) ? (((loanSchedules == null) || (loanSchedules.length == 0) || (loanSchedules[0] == null)) ? null
				: loanSchedules[0].getPaymentDate())
				: dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public String getFrequencyOfPayment() {
		return frequencyOfPayment;
	}

	public void setFrequencyOfPayment(String frequencyOfPayment) {
		this.frequencyOfPayment = frequencyOfPayment;
	}

	public int getNumberOfInstalments() {
		return (numberOfInstalments < 0) ? (((loanSchedules == null) || (loanSchedules.length == 0)) ? -1
				: loanSchedules.length) : numberOfInstalments;
	}

	public void setNumberOfInstalments(int numberOfInstalments) {
		this.numberOfInstalments = numberOfInstalments;
	}

	public Amount getPrincipleAmount() {
		return principleAmount;
	}

	public void setPrincipleAmount(Amount principleAmount) {
		this.principleAmount = principleAmount;
	}

	public int getCalculationBaseNumberOfDays() {
		return calculationBaseNumberOfDays;
	}

	public void setCalculationBaseNumberOfDays(int calculationBaseNumberOfDays) {
		this.calculationBaseNumberOfDays = calculationBaseNumberOfDays;
	}

	public double getDebtRate() {
		return debtRate;
	}

	public void setDebtRate(double debtRate) {
		this.debtRate = debtRate;
	}

	public boolean getIsTermOutOption() {
		return isTermOutOption;
	}

	public void setIsTermOutOption(boolean isTermOutOption) {
		this.isTermOutOption = isTermOutOption;
	}

	public Amount getUpfrontFees() {
		return upfrontFees;
	}

	public void setUpfrontFees(Amount upfrontFees) {
		this.upfrontFees = upfrontFees;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get total sub limit amount.
	 * 
	 * @return Amount
	 */
	public Amount getTotalSubLimitAmount() {
		try {
			if ((getGlobalAmount() == null) || (getGlobalAmount().getCurrencyCode() == null)) {
				return null;
			}
			String ccy = getGlobalAmount().getCurrencyCode();
			int size = 0;

			if ((sublimits == null) || ((size = sublimits.length) == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < size; i++) {
				ISubLimit sublimit = sublimits[i];
				if (!isEmptySubLimit(sublimit)) {
					Amount amt = AmountConversion.getConversionAmount(sublimit.getAmount(), ccy);
					if (totalAmt == null) {
						totalAmt = new Amount(amt.getAmountAsBigDecimal(), amt.getCurrencyCodeAsObject());
					}
					else {
						totalAmt.addToThis(amt);
					}
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total sublimitt amount! " + e.toString());
		}
	}

	/**
	 * Get total amount allocated to participants.
	 * 
	 * @return Amount
	 */
	public Amount getTotalAllocatedAmount() {
		try {
			if ((getGlobalAmount() == null) || (getGlobalAmount().getCurrencyCode() == null)) {
				return null;
			}
			String ccy = getGlobalAmount().getCurrencyCode();
			int size = 0;

			if ((participants == null) || ((size = participants.length) == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < size; i++) {
				IParticipant participant = participants[i];
				if (isParticipantAllocatedLoan(participant)) {
					Amount amt = (!ccy.equals(participant.getAllocatedAmount().getCurrencyCode())) ? AmountConversion
							.getConversionAmount(participant.getAllocatedAmount(), ccy) : participant
							.getAllocatedAmount();
					if (totalAmt == null) {
						totalAmt = new Amount(amt.getAmountAsBigDecimal(), amt.getCurrencyCodeAsObject());
					}
					else {
						totalAmt.addToThis(amt);
					}
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total amount allocated to participants! " + e.toString());
		}
	}

	/**
	 * Get total interest amount from the list of loan schedules.
	 * 
	 * @return Amount
	 */
	public Amount getTotalInterestAmount() {
		try {
			if ((getPrincipleAmount() == null) || (getPrincipleAmount().getCurrencyCode() == null)) {
				return null;
			}
			String ccy = getPrincipleAmount().getCurrencyCode();

			int size = 0;
			if ((loanSchedules == null) || ((loanSchedules.length) == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < size; i++) {
				Amount interest = loanSchedules[i].getInterestDueAmount();
				Amount amt = (!ccy.equals(interest.getCurrencyCode())) ? AmountConversion.getConversionAmount(interest,
						ccy) : interest;
				if (totalAmt == null) {
					totalAmt = new Amount(amt.getAmountAsBigDecimal(), amt.getCurrencyCodeAsObject());
				}
				else {
					totalAmt.addToThis(amt);
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total loan interest amount! " + e.toString());
		}
	}

	/**
	 * Get total payment amount from the list of loan schedules.
	 * 
	 * @return Amount
	 */
	public Amount getTotalPaymentAmount() {
		try {
			if ((getPrincipleAmount() == null) || (getPrincipleAmount().getCurrencyCode() == null)) {
				return null;
			}
			String ccy = getPrincipleAmount().getCurrencyCode();

			int size = 0;
			if ((loanSchedules == null) || ((loanSchedules.length) == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < size; i++) {
				Amount payment = loanSchedules[i].getPrincipalDueAmount();
				Amount amt = (!ccy.equals(payment.getCurrencyCode())) ? AmountConversion.getConversionAmount(payment,
						ccy) : payment;
				if (totalAmt == null) {
					totalAmt = new Amount(amt.getAmountAsBigDecimal(), amt.getCurrencyCodeAsObject());
				}
				else {
					totalAmt.addToThis(amt);
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total loan interest amount! " + e.toString());
		}
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public boolean equals(Object o) {
		return ((o != null) && this.toString().equals(o.toString())) && (o.getClass().equals(this.getClass()));
	}

	private boolean isEmptySubLimit(ISubLimit sublimit) {
		if ((sublimit == null) || (sublimit.getAmount() == null) || (sublimit.getAmount().getCurrencyCode() == null)
				|| (sublimit.getAmount().getCurrencyCode().length() == 0)) {
			return true;
		}
		return false;
	}

	private boolean isParticipantAllocatedLoan(IParticipant participant) {
		if ((participant == null) || (participant.getAllocatedAmount() == null)
				|| (participant.getAllocatedAmount().getCurrencyCode() == null)
				|| (participant.getAllocatedAmount().getCurrencyCode().length() == 0)) {
			return false;
		}
		return true;
	}

}