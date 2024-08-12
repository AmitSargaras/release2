package com.integrosys.cms.ui.limit.facility.master;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class FacilityMasterForm extends FacilityMainForm {

	private static final long serialVersionUID = -8773274594722768398L;

	// Branch Number
	private String organisationCode;

	// Product Type
	private String productDesc;

	// Amount Financed
	private String financedAmount;

	// Date Entered
	private String enteredDate;

	// Application Date
	private String applicationDate;

	// Approved Limit
	private String approvedLimitAmount;

	// Installment Amount
	private String installmentAmount;

	// Drawing Limit
	private String drawingLimitAmount;

	// Final Payment Amount
	private String finalPaymentAmount;

	// Term Code
	private String termCodeEntryCode;

	// Term
	private String term;

	// Required Security Coverage (%)
	private String requiredSecurityCoverage;

	// ACF No.
	private String acfNo;

	// Loan Purpose Code
	private String loanPurposeEntryCode;

	// Product Package Code
	private String productPackageCodeEntryCode;

	// Date Offer Accepted
	private String offerAcceptedDate;

	// Date Of Offer
	private String offerDate;

	// Approved By
	private String personApprovedEntryCode;

	// Date Approved
	private String approvedDate;

	// Canc/Rej Code
	private String cancelOrRejectEntryCode;

	// Canc/Rej Date
	private String cancelOrRejectDate;

	// Car Code
	private String carEntryCode;

	private String officerEntryCodeIndex;

	// Officer
	private String officerEntryCode;

	// Limit Status
	private String limitStatusEntryCode;

	// Facility Status
	private String facilityStatusEntryCode;

	// Interest Rate
	private String interestRate;

	// CAR Code Flag
	private String carCodeFlag;

	// Spread
	private String spread;

	// Rate Type
	private String interestRateTypeEntryCode;

	// Spread Sign
	private String spreadSign;

	// Prime Rate Floor
	private String primeRateFloor;

	// Prime Rate Ceiling
	private String primeRateCeiling;

	// Prime Review Date
	private String primeReviewDate;

	// Prime Review Term
	private String primeReviewTerm;

	// Prime Review Term Code
	private String primeReviewTermCodeEntryCode;

	// Application Source
	private String applicationSourceEntryCode;

	// Limit Currency
	private String facilityCurrencyCode;

	// OD Excess Rate Var Code
	private String odExcessRateVarCode;

	// OD Excess Rate Var
	private String odExcessRateVar;

	// Dealer Number / LPP Code
	private String dealerNumberOrLppCodeEntryCode;

	// Late Charge Type
	private String lateChargeType;

	// Comission Fees
	private String commissionFeesAmount;

	// Handling Fees
	private String handlingFeesAmount;

	// Subsidy Amount
	private String subsidyAmount;

	// Others Fee
	private String othersFeeAmount;

	// Comission Rate
	private String commissionRate;

	// Comission Basis
	private String commissionBasisEntryCode;

	// Maximum Comission
	private String maximumCommissionAmount;

	// Minimum Comission
	private String minimumCommissionAmount;

	// Standby Line Fac Code
	private String standByLineFacilityCode;

	// Standby Line Fac Cod Seq
	private String standByLineFacilityCodeSequence;

	// Expiry Date
	private String limitExpiryDate;

	// Fac Commitment Rate
	private String facilityCommitmentRate;

	// Fac Commitment
	private String facilityCommitmentRateNumberEntryCode;

	// Rate No.
	// Last Maint Date
	private String lastMaintenanceDate;

	// Original Amount
	private String originalAmount;

	// Main Facility
	private boolean mainFacility;

	// Main Facility AA No.
	private String mainFacilityAaNumber;

	// Main Facility Code
	private String mainFacilityCode;

	// Main Facility Seq No.
	private String mainFacilitySequenceNumber;

	// Standby Line
	private String standbyLine;

	// Level
	private String level;

	// Amount Utilised
	private String utilisedAmount;

	// Spec. Provision
	private String specProvision;

	// Int-In-Suspense
	private String intInSuspense;

	// O/S Balance
	private String oustandingBalanceAmount;

	// Payment Code
	private String paymentCodeEntryCode;

	// Payment Frequency
	private String paymentFrequencyEntryCode;

	// Payment Freq Code
	private String paymentFrequencyCodeEntryCode;

	// Int Payment Frequency
	private String interestPaymentFrequencyEntryCode;

	// Int Payment Freq Code
	private String interestPaymentFrequencyCodeEntryCode;

	// Grace Period
	private String gracePeriodEntryCode;

	// Grace Code
	private String gracePeriodCodeEntryCode;

	// Revolving Non-Revolving
	private String revolvingIndicator;

	// Rev On O/S Bal Or ORGAMT
	private String revolvingOnCriteriaIndicator;

	// Floor Pledged Limit
	private String floorPledgedLimitAmount;

	// Actual Pledged Limit
	private String actualPledgedLimitAmount;

	// Alt. Schedule
	private boolean altSchedule;

	// Avail. Period In Mths
	private String availPeriodInMonths;

	// Avail. Period In Days
	private String availPeriodInDays;

	// Retention Sum
	private String retentionSumAmount;

	// Retention Period
	private String retentionPeriod;

	// Retention Period Code
	private String retentionPeriodCode;

	// Disbursement Manner
	private String disbursementManner;

	// Cal 1st Instl Date
	private String calFirstInstlDate;

	// ISO Referral No.
	private String isoReferralNumber;

	// Allow Incentive
	private String allowIncentive = "N";

	// Date approved by CGC/BNM
	private String cgcBnmApprovedDate;

	// Alternate Rate
	private String alternateRate;

	// Credit Line Indicator
	private String creditLineIndicator;

	// private String productPackageCodeEntryCode;//Product Package Code
	// Effective Cost Of Fund
	private String effectiveCostOfFund = "N";

	// ECOF Administration Cost
	private String ecofAdministrationCostAmount;

	// ECOF Rate
	private String ecofRate;

	// ECOF Variance
	private String ecofVariance;

	// ECOF Variance Code
	private String ecofVarianceCode;

	// Facility Aval. Date
	private String facilityAvailDate;

	// Facility Aval. Period
	private String facilityAvailPeriod;

	// Date Instructed
	private String dateInstructed;

	// Solicitor Reference
	private String solicitorReference;

	// Department Code
	private String departmentCodeEntryCode;

	// Refinance From Bank
	private String refinanceFromBankEntryCode;

	// Solicitor Name
	private String solicitorName;

	// Lawyer Code
	private String lawyerCodeEntryCode;

	// PAR Value Shares
	private String parValueShares;

	// Appliation Type
	private String aaType;

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.master.FacilityMasterMapper";

	public String getAaType() {
		return aaType;
	}

	/**
	 * @return the acfNo
	 */
	public String getAcfNo() {
		return acfNo;
	}

	/**
	 * @return the actualPledgedLimitAmount
	 */
	public String getActualPledgedLimitAmount() {
		return actualPledgedLimitAmount;
	}

	/**
	 * @return the allowIncentive
	 */
	public String getAllowIncentive() {
		return allowIncentive;
	}

	/**
	 * @return the alternateRate
	 */
	public String getAlternateRate() {
		return alternateRate;
	}

	/**
	 * @return the applicationDate
	 */
	public String getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @return the applicationSourceEntryCode
	 */
	public String getApplicationSourceEntryCode() {
		return applicationSourceEntryCode;
	}

	/**
	 * @return the approvedDate
	 */
	public String getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @return the approvedLimitAmount
	 */
	public String getApprovedLimitAmount() {
		return approvedLimitAmount;
	}

	/**
	 * @return the availPeriodInDays
	 */
	public String getAvailPeriodInDays() {
		return availPeriodInDays;
	}

	/**
	 * @return the availPeriodInMonths
	 */
	public String getAvailPeriodInMonths() {
		return availPeriodInMonths;
	}

	/**
	 * @return the calFirstInstlDate
	 */
	public String getCalFirstInstlDate() {
		return calFirstInstlDate;
	}

	/**
	 * @return the cancelOrRejectDate
	 */
	public String getCancelOrRejectDate() {
		return cancelOrRejectDate;
	}

	/**
	 * @return the cancelOrRejectEntryCode
	 */
	public String getCancelOrRejectEntryCode() {
		return cancelOrRejectEntryCode;
	}

	/**
	 * @return the carCodeFlag
	 */
	public String getCarCodeFlag() {
		return carCodeFlag;
	}

	/**
	 * @return the carEntryCode
	 */
	public String getCarEntryCode() {
		return carEntryCode;
	}

	/**
	 * @return the cgcBnmApprovedDate
	 */
	public String getCgcBnmApprovedDate() {
		return cgcBnmApprovedDate;
	}

	/**
	 * @return the commissionBasisEntryCode
	 */
	public String getCommissionBasisEntryCode() {
		return commissionBasisEntryCode;
	}

	/**
	 * @return the commissionFeesAmount
	 */
	public String getCommissionFeesAmount() {
		return commissionFeesAmount;
	}

	/**
	 * @return the commissionRate
	 */
	public String getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @return the creditLineIndicator
	 */
	public String getCreditLineIndicator() {
		return creditLineIndicator;
	}

	/**
	 * @return the dateInstructed
	 */
	public String getDateInstructed() {
		return dateInstructed;
	}

	/**
	 * @return the dealerNumberOrLppCodeEntryCode
	 */
	public String getDealerNumberOrLppCodeEntryCode() {
		return dealerNumberOrLppCodeEntryCode;
	}

	/**
	 * @return the departmentCodeEntryCode
	 */
	public String getDepartmentCodeEntryCode() {
		return departmentCodeEntryCode;
	}

	/**
	 * @return the disbursementManner
	 */
	public String getDisbursementManner() {
		return disbursementManner;
	}

	/**
	 * @return the drawingLimitAmount
	 */
	public String getDrawingLimitAmount() {
		return drawingLimitAmount;
	}

	/**
	 * @return the ecofAdministrationCostAmount
	 */
	public String getEcofAdministrationCostAmount() {
		return ecofAdministrationCostAmount;
	}

	/**
	 * @return the ecofRate
	 */
	public String getEcofRate() {
		return ecofRate;
	}

	/**
	 * @return the ecofVariance
	 */
	public String getEcofVariance() {
		return ecofVariance;
	}

	/**
	 * @return the ecofVarianceCode
	 */
	public String getEcofVarianceCode() {
		return ecofVarianceCode;
	}

	/**
	 * @return the effectiveCostOfFund
	 */
	public String getEffectiveCostOfFund() {
		return effectiveCostOfFund;
	}

	/**
	 * @return the enteredDate
	 */
	public String getEnteredDate() {
		return enteredDate;
	}

	/**
	 * @return the facilityAvailDate
	 */
	public String getFacilityAvailDate() {
		return facilityAvailDate;
	}

	/**
	 * @return the facilityAvailPeriod
	 */
	public String getFacilityAvailPeriod() {
		return facilityAvailPeriod;
	}

	/**
	 * @return the facilityCommitmentRate
	 */
	public String getFacilityCommitmentRate() {
		return facilityCommitmentRate;
	}

	/**
	 * @return the facilityCommitmentRateNumberEntryCode
	 */
	public String getFacilityCommitmentRateNumberEntryCode() {
		return facilityCommitmentRateNumberEntryCode;
	}

	/**
	 * @return the facilityCurrencyCode
	 */
	public String getFacilityCurrencyCode() {
		return facilityCurrencyCode;
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
	public String getFinalPaymentAmount() {
		return finalPaymentAmount;
	}

	/**
	 * @return the financedAmount
	 */
	public String getFinancedAmount() {
		return financedAmount;
	}

	/**
	 * @return the floorPledgedLimitAmount
	 */
	public String getFloorPledgedLimitAmount() {
		return floorPledgedLimitAmount;
	}

	/**
	 * @return the gracePeriodCodeEntryCode
	 */
	public String getGracePeriodCodeEntryCode() {
		return gracePeriodCodeEntryCode;
	}

	/**
	 * @return the gracePeriodEntryCode
	 */
	public String getGracePeriodEntryCode() {
		return gracePeriodEntryCode;
	}

	/**
	 * @return the handlingFeesAmount
	 */
	public String getHandlingFeesAmount() {
		return handlingFeesAmount;
	}

	/**
	 * @return the installmentAmount
	 */
	public String getInstallmentAmount() {
		return installmentAmount;
	}

	/**
	 * @return the interestPaymentFrequencyCodeEntryCode
	 */
	public String getInterestPaymentFrequencyCodeEntryCode() {
		return interestPaymentFrequencyCodeEntryCode;
	}

	/**
	 * @return the interestPaymentFrequencyEntryCode
	 */
	public String getInterestPaymentFrequencyEntryCode() {
		return interestPaymentFrequencyEntryCode;
	}

	/**
	 * @return the interestRate
	 */
	public String getInterestRate() {
		return interestRate;
	}

	/**
	 * @return the interestRateTypeEntryCode
	 */
	public String getInterestRateTypeEntryCode() {
		return interestRateTypeEntryCode;
	}

	/**
	 * @return the intInSuspense
	 */
	public String getIntInSuspense() {
		return intInSuspense;
	}

	/**
	 * @return the isoReferralNumber
	 */
	public String getIsoReferralNumber() {
		return isoReferralNumber;
	}

	/**
	 * @return the lastMaintenanceDate
	 */
	public String getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	/**
	 * @return the lateChargeType
	 */
	public String getLateChargeType() {
		return lateChargeType;
	}

	/**
	 * @return the lawyerCodeEntryCode
	 */
	public String getLawyerCodeEntryCode() {
		return lawyerCodeEntryCode;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @return the limitExpiryDate
	 */
	public String getLimitExpiryDate() {
		return limitExpiryDate;
	}

	/**
	 * @return the limitStatusEntryCode
	 */
	public String getLimitStatusEntryCode() {
		return limitStatusEntryCode;
	}

	/**
	 * @return the loanPurposeEntryCode
	 */
	public String getLoanPurposeEntryCode() {
		return loanPurposeEntryCode;
	}

	/**
	 * @return the mainFacility
	 */
	public boolean getMainFacility() {
		return mainFacility;
	}

	/**
	 * @return the mainFacilityAaNumber
	 */
	public String getMainFacilityAaNumber() {
		return mainFacilityAaNumber;
	}

	/**
	 * @return the mainFacilityCode
	 */
	public String getMainFacilityCode() {
		return mainFacilityCode;
	}

	/**
	 * @return the mainFacilitySequenceNumber
	 */
	public String getMainFacilitySequenceNumber() {
		return mainFacilitySequenceNumber;
	}

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	/**
	 * @return the maximumCommissionAmount
	 */
	public String getMaximumCommissionAmount() {
		return maximumCommissionAmount;
	}

	/**
	 * @return the minimumCommissionAmount
	 */
	public String getMinimumCommissionAmount() {
		return minimumCommissionAmount;
	}

	/**
	 * @return the odExcessRateVar
	 */
	public String getOdExcessRateVar() {
		return odExcessRateVar;
	}

	/**
	 * @return the odExcessRateVarCode
	 */
	public String getOdExcessRateVarCode() {
		return odExcessRateVarCode;
	}

	/**
	 * @return the offerAcceptedDate
	 */
	public String getOfferAcceptedDate() {
		return offerAcceptedDate;
	}

	/**
	 * @return the offerDate
	 */
	public String getOfferDate() {
		return offerDate;
	}

	/**
	 * @return the officerEntryCode
	 */
	public String getOfficerEntryCode() {
		return officerEntryCode;
	}

	public String getOfficerEntryCodeIndex() {
		return officerEntryCodeIndex;
	}

	/**
	 * @return the organisationCode
	 */
	public String getOrganisationCode() {
		return organisationCode;
	}

	/**
	 * @return the originalAmount
	 */
	public String getOriginalAmount() {
		return originalAmount;
	}

	/**
	 * @return the othersFeeAmount
	 */
	public String getOthersFeeAmount() {
		return othersFeeAmount;
	}

	/**
	 * @return the oustandingBalanceAmount
	 */
	public String getOustandingBalanceAmount() {
		return oustandingBalanceAmount;
	}

	/**
	 * @return the parValueShares
	 */
	public String getParValueShares() {
		return parValueShares;
	}

	/**
	 * @return the paymentCodeEntryCode
	 */
	public String getPaymentCodeEntryCode() {
		return paymentCodeEntryCode;
	}

	/**
	 * @return the paymentFrequencyCodeEntryCode
	 */
	public String getPaymentFrequencyCodeEntryCode() {
		return paymentFrequencyCodeEntryCode;
	}

	/**
	 * @return the paymentFrequencyEntryCode
	 */
	public String getPaymentFrequencyEntryCode() {
		return paymentFrequencyEntryCode;
	}

	/**
	 * @return the personApprovedEntryCode
	 */
	public String getPersonApprovedEntryCode() {
		return personApprovedEntryCode;
	}

	/**
	 * @return the primeRateCeiling
	 */
	public String getPrimeRateCeiling() {
		return primeRateCeiling;
	}

	/**
	 * @return the primeRateFloor
	 */
	public String getPrimeRateFloor() {
		return primeRateFloor;
	}

	/**
	 * @return the primeReviewDate
	 */
	public String getPrimeReviewDate() {
		return primeReviewDate;
	}

	/**
	 * @return the primeReviewTerm
	 */
	public String getPrimeReviewTerm() {
		return primeReviewTerm;
	}

	/**
	 * @return the primeReviewTermCodeEntryCode
	 */
	public String getPrimeReviewTermCodeEntryCode() {
		return primeReviewTermCodeEntryCode;
	}

	/**
	 * @return the productDesc
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * @return the productPackageCodeEntryCode
	 */
	public String getProductPackageCodeEntryCode() {
		return productPackageCodeEntryCode;
	}

	/**
	 * @return the refinanceFromBankEntryCode
	 */
	public String getRefinanceFromBankEntryCode() {
		return refinanceFromBankEntryCode;
	}

	/**
	 * @return the requiredSecurityCoverage
	 */
	public String getRequiredSecurityCoverage() {
		return requiredSecurityCoverage;
	}

	/**
	 * @return the retentionPeriod
	 */
	public String getRetentionPeriod() {
		return retentionPeriod;
	}

	/**
	 * @return the retentionPeriodCode
	 */
	public String getRetentionPeriodCode() {
		return retentionPeriodCode;
	}

	/**
	 * @return the retentionSumAmount
	 */
	public String getRetentionSumAmount() {
		return retentionSumAmount;
	}

	/**
	 * @return the revolvingIndicator
	 */
	public String getRevolvingIndicator() {
		return revolvingIndicator;
	}

	/**
	 * @return the revolvingOnCriteriaIndicator
	 */
	public String getRevolvingOnCriteriaIndicator() {
		return revolvingOnCriteriaIndicator;
	}

	/**
	 * @return the solicitorName
	 */
	public String getSolicitorName() {
		return solicitorName;
	}

	/**
	 * @return the solicitorReference
	 */
	public String getSolicitorReference() {
		return solicitorReference;
	}

	/**
	 * @return the specProvision
	 */
	public String getSpecProvision() {
		return specProvision;
	}

	/**
	 * @return the spread
	 */
	public String getSpread() {
		return spread;
	}

	/**
	 * @return the spreadSign
	 */
	public String getSpreadSign() {
		return spreadSign;
	}

	/**
	 * @return the standbyLine
	 */
	public String getStandbyLine() {
		return standbyLine;
	}

	/**
	 * @return the standByLineFacilityCode
	 */
	public String getStandByLineFacilityCode() {
		return standByLineFacilityCode;
	}

	/**
	 * @return the standByLineFacilityCodeSequence
	 */
	public String getStandByLineFacilityCodeSequence() {
		return standByLineFacilityCodeSequence;
	}

	/**
	 * @return the subsidyAmount
	 */
	public String getSubsidyAmount() {
		return subsidyAmount;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
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
	public String getUtilisedAmount() {
		return utilisedAmount;
	}

	/**
	 * @return the altSchedule
	 */
	public boolean isAltSchedule() {
		return altSchedule;
	}

	public void setAaType(String aaType) {
		this.aaType = aaType;
	}

	/**
	 * @param acfNo the acfNo to set
	 */
	public void setAcfNo(String acfNo) {
		this.acfNo = acfNo;
	}

	/**
	 * @param actualPledgedLimitAmount the actualPledgedLimitAmount to set
	 */
	public void setActualPledgedLimitAmount(String actualPledgedLimitAmount) {
		this.actualPledgedLimitAmount = actualPledgedLimitAmount;
	}

	/**
	 * @param allowIncentive the allowIncentive to set
	 */
	public void setAllowIncentive(String allowIncentive) {
		this.allowIncentive = allowIncentive;
	}

	/**
	 * @param alternateRate the alternateRate to set
	 */
	public void setAlternateRate(String alternateRate) {
		this.alternateRate = alternateRate;
	}

	/**
	 * @param altSchedule the altSchedule to set
	 */
	public void setAltSchedule(boolean altSchedule) {
		this.altSchedule = altSchedule;
	}

	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	/**
	 * @param applicationSourceEntryCode the applicationSourceEntryCode to set
	 */
	public void setApplicationSourceEntryCode(String applicationSourceEntryCode) {
		this.applicationSourceEntryCode = applicationSourceEntryCode;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @param approvedLimitAmount the approvedLimitAmount to set
	 */
	public void setApprovedLimitAmount(String approvedLimitAmount) {
		this.approvedLimitAmount = approvedLimitAmount;
	}

	/**
	 * @param availPeriodInDays the availPeriodInDays to set
	 */
	public void setAvailPeriodInDays(String availPeriodInDays) {
		this.availPeriodInDays = availPeriodInDays;
	}

	/**
	 * @param availPeriodInMonths the availPeriodInMonths to set
	 */
	public void setAvailPeriodInMonths(String availPeriodInMonths) {
		this.availPeriodInMonths = availPeriodInMonths;
	}

	/**
	 * @param calFirstInstlDate the calFirstInstlDate to set
	 */
	public void setCalFirstInstlDate(String calFirstInstlDate) {
		this.calFirstInstlDate = calFirstInstlDate;
	}

	/**
	 * @param cancelOrRejectDate the cancelOrRejectDate to set
	 */
	public void setCancelOrRejectDate(String cancelOrRejectDate) {
		this.cancelOrRejectDate = cancelOrRejectDate;
	}

	/**
	 * @param cancelOrRejectEntryCode the cancelOrRejectEntryCode to set
	 */
	public void setCancelOrRejectEntryCode(String cancelOrRejectEntryCode) {
		this.cancelOrRejectEntryCode = cancelOrRejectEntryCode;
	}

	/**
	 * @param carCodeFlag the carCodeFlag to set
	 */
	public void setCarCodeFlag(String carCodeFlag) {
		this.carCodeFlag = carCodeFlag;
	}

	/**
	 * @param carEntryCode the carEntryCode to set
	 */
	public void setCarEntryCode(String carEntryCode) {
		this.carEntryCode = carEntryCode;
	}

	/**
	 * @param cgcBnmApprovedDate the cgcBnmApprovedDate to set
	 */
	public void setCgcBnmApprovedDate(String cgcBnmApprovedDate) {
		this.cgcBnmApprovedDate = cgcBnmApprovedDate;
	}

	/**
	 * @param commissionBasisEntryCode the commissionBasisEntryCode to set
	 */
	public void setCommissionBasisEntryCode(String commissionBasisEntryCode) {
		this.commissionBasisEntryCode = commissionBasisEntryCode;
	}

	/**
	 * @param commissionFeesAmount the commissionFeesAmount to set
	 */
	public void setCommissionFeesAmount(String commissionFeesAmount) {
		this.commissionFeesAmount = commissionFeesAmount;
	}

	/**
	 * @param commissionRate the commissionRate to set
	 */
	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * @param creditLineIndicator the creditLineIndicator to set
	 */
	public void setCreditLineIndicator(String creditLineIndicator) {
		this.creditLineIndicator = creditLineIndicator;
	}

	/**
	 * @param dateInstructed the dateInstructed to set
	 */
	public void setDateInstructed(String dateInstructed) {
		this.dateInstructed = dateInstructed;
	}

	/**
	 * @param dealerNumberOrLppCodeEntryCode the dealerNumberOrLppCodeEntryCode
	 *        to set
	 */
	public void setDealerNumberOrLppCodeEntryCode(String dealerNumberOrLppCodeEntryCode) {
		this.dealerNumberOrLppCodeEntryCode = dealerNumberOrLppCodeEntryCode;
	}

	/**
	 * @param departmentCodeEntryCode the departmentCodeEntryCode to set
	 */
	public void setDepartmentCodeEntryCode(String departmentCodeEntryCode) {
		this.departmentCodeEntryCode = departmentCodeEntryCode;
	}

	/**
	 * @param disbursementManner the disbursementManner to set
	 */
	public void setDisbursementManner(String disbursementManner) {
		this.disbursementManner = disbursementManner;
	}

	/**
	 * @param drawingLimitAmount the drawingLimitAmount to set
	 */
	public void setDrawingLimitAmount(String drawingLimitAmount) {
		this.drawingLimitAmount = drawingLimitAmount;
	}

	/**
	 * @param ecofAdministrationCostAmount the ecofAdministrationCostAmount to
	 *        set
	 */
	public void setEcofAdministrationCostAmount(String ecofAdministrationCostAmount) {
		this.ecofAdministrationCostAmount = ecofAdministrationCostAmount;
	}

	/**
	 * @param ecofRate the ecofRate to set
	 */
	public void setEcofRate(String ecofRate) {
		this.ecofRate = ecofRate;
	}

	/**
	 * @param ecofVariance the ecofVariance to set
	 */
	public void setEcofVariance(String ecofVariance) {
		this.ecofVariance = ecofVariance;
	}

	/**
	 * @param ecofVarianceCode the ecofVarianceCode to set
	 */
	public void setEcofVarianceCode(String ecofVarianceCode) {
		this.ecofVarianceCode = ecofVarianceCode;
	}

	/**
	 * @param effectiveCostOfFund the effectiveCostOfFund to set
	 */
	public void setEffectiveCostOfFund(String effectiveCostOfFund) {
		this.effectiveCostOfFund = effectiveCostOfFund;
	}

	/**
	 * @param enteredDate the enteredDate to set
	 */
	public void setEnteredDate(String enteredDate) {
		this.enteredDate = enteredDate;
	}

	/**
	 * @param facilityAvailDate the facilityAvailDate to set
	 */
	public void setFacilityAvailDate(String facilityAvailDate) {
		this.facilityAvailDate = facilityAvailDate;
	}

	/**
	 * @param facilityAvailPeriod the facilityAvailPeriod to set
	 */
	public void setFacilityAvailPeriod(String facilityAvailPeriod) {
		this.facilityAvailPeriod = facilityAvailPeriod;
	}

	/**
	 * @param facilityCommitmentRate the facilityCommitmentRate to set
	 */
	public void setFacilityCommitmentRate(String facilityCommitmentRate) {
		this.facilityCommitmentRate = facilityCommitmentRate;
	}

	/**
	 * @param facilityCommitmentRateNumberEntryCode the
	 *        facilityCommitmentRateNumberEntryCode to set
	 */
	public void setFacilityCommitmentRateNumberEntryCode(String facilityCommitmentRateNumberEntryCode) {
		this.facilityCommitmentRateNumberEntryCode = facilityCommitmentRateNumberEntryCode;
	}

	/**
	 * @param facilityCurrencyCode the facilityCurrencyCode to set
	 */
	public void setFacilityCurrencyCode(String facilityCurrencyCode) {
		this.facilityCurrencyCode = facilityCurrencyCode;
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
	public void setFinalPaymentAmount(String finalPaymentAmount) {
		this.finalPaymentAmount = finalPaymentAmount;
	}

	/**
	 * @param financedAmount the financedAmount to set
	 */
	public void setFinancedAmount(String financedAmount) {
		this.financedAmount = financedAmount;
	}

	/**
	 * @param floorPledgedLimitAmount the floorPledgedLimitAmount to set
	 */
	public void setFloorPledgedLimitAmount(String floorPledgedLimitAmount) {
		this.floorPledgedLimitAmount = floorPledgedLimitAmount;
	}

	/**
	 * @param gracePeriodCodeEntryCode the gracePeriodCodeEntryCode to set
	 */
	public void setGracePeriodCodeEntryCode(String gracePeriodCodeEntryCode) {
		this.gracePeriodCodeEntryCode = gracePeriodCodeEntryCode;
	}

	/**
	 * @param gracePeriodEntryCode the gracePeriodEntryCode to set
	 */
	public void setGracePeriodEntryCode(String gracePeriodEntryCode) {
		this.gracePeriodEntryCode = gracePeriodEntryCode;
	}

	/**
	 * @param handlingFeesAmount the handlingFeesAmount to set
	 */
	public void setHandlingFeesAmount(String handlingFeesAmount) {
		this.handlingFeesAmount = handlingFeesAmount;
	}

	/**
	 * @param installmentAmount the installmentAmount to set
	 */
	public void setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	/**
	 * @param interestPaymentFrequencyCodeEntryCode the
	 *        interestPaymentFrequencyCodeEntryCode to set
	 */
	public void setInterestPaymentFrequencyCodeEntryCode(String interestPaymentFrequencyCodeEntryCode) {
		this.interestPaymentFrequencyCodeEntryCode = interestPaymentFrequencyCodeEntryCode;
	}

	/**
	 * @param interestPaymentFrequencyEntryCode the
	 *        interestPaymentFrequencyEntryCode to set
	 */
	public void setInterestPaymentFrequencyEntryCode(String interestPaymentFrequencyEntryCode) {
		this.interestPaymentFrequencyEntryCode = interestPaymentFrequencyEntryCode;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @param interestRateTypeEntryCode the interestRateTypeEntryCode to set
	 */
	public void setInterestRateTypeEntryCode(String interestRateTypeEntryCode) {
		this.interestRateTypeEntryCode = interestRateTypeEntryCode;
	}

	/**
	 * @param intInSuspense the intInSuspense to set
	 */
	public void setIntInSuspense(String intInSuspense) {
		this.intInSuspense = intInSuspense;
	}

	/**
	 * @param isoReferralNumber the isoReferralNumber to set
	 */
	public void setIsoReferralNumber(String isoReferralNumber) {
		this.isoReferralNumber = isoReferralNumber;
	}

	/**
	 * @param lastMaintenanceDate the lastMaintenanceDate to set
	 */
	public void setLastMaintenanceDate(String lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}

	/**
	 * @param lateChargeType the lateChargeType to set
	 */
	public void setLateChargeType(String lateChargeType) {
		this.lateChargeType = lateChargeType;
	}

	/**
	 * @param lawyerCodeEntryCode the lawyerCodeEntryCode to set
	 */
	public void setLawyerCodeEntryCode(String lawyerCodeEntryCode) {
		this.lawyerCodeEntryCode = lawyerCodeEntryCode;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @param limitExpiryDate the limitExpiryDate to set
	 */
	public void setLimitExpiryDate(String limitExpiryDate) {
		this.limitExpiryDate = limitExpiryDate;
	}

	/**
	 * @param limitStatusEntryCode the limitStatusEntryCode to set
	 */
	public void setLimitStatusEntryCode(String limitStatusEntryCode) {
		this.limitStatusEntryCode = limitStatusEntryCode;
	}

	/**
	 * @param loanPurposeEntryCode the loanPurposeEntryCode to set
	 */
	public void setLoanPurposeEntryCode(String loanPurposeEntryCode) {
		this.loanPurposeEntryCode = loanPurposeEntryCode;
	}

	/**
	 * @param mainFacility the mainFacility to set
	 */
	public void setMainFacility(boolean mainFacility) {
		this.mainFacility = mainFacility;
	}

	/**
	 * @param mainFacilityAaNumber the mainFacilityAaNumber to set
	 */
	public void setMainFacilityAaNumber(String mainFacilityAaNumber) {
		this.mainFacilityAaNumber = mainFacilityAaNumber;
	}

	/**
	 * @param mainFacilityCode the mainFacilityCode to set
	 */
	public void setMainFacilityCode(String mainFacilityCode) {
		this.mainFacilityCode = mainFacilityCode;
	}

	/**
	 * @param mainFacilitySequenceNumber the mainFacilitySequenceNumber to set
	 */
	public void setMainFacilitySequenceNumber(String mainFacilitySequenceNumber) {
		this.mainFacilitySequenceNumber = mainFacilitySequenceNumber;
	}

	/**
	 * @param maximumCommissionAmount the maximumCommissionAmount to set
	 */
	public void setMaximumCommissionAmount(String maximumCommissionAmount) {
		this.maximumCommissionAmount = maximumCommissionAmount;
	}

	/**
	 * @param minimumCommissionAmount the minimumCommissionAmount to set
	 */
	public void setMinimumCommissionAmount(String minimumCommissionAmount) {
		this.minimumCommissionAmount = minimumCommissionAmount;
	}

	/**
	 * @param odExcessRateVar the odExcessRateVar to set
	 */
	public void setOdExcessRateVar(String odExcessRateVar) {
		this.odExcessRateVar = odExcessRateVar;
	}

	/**
	 * @param odExcessRateVarCode the odExcessRateVarCode to set
	 */
	public void setOdExcessRateVarCode(String odExcessRateVarCode) {
		this.odExcessRateVarCode = odExcessRateVarCode;
	}

	/**
	 * @param offerAcceptedDate the offerAcceptedDate to set
	 */
	public void setOfferAcceptedDate(String offerAcceptedDate) {
		this.offerAcceptedDate = offerAcceptedDate;
	}

	/**
	 * @param offerDate the offerDate to set
	 */
	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}

	/**
	 * @param officerEntryCode the officerEntryCode to set
	 */
	public void setOfficerEntryCode(String officerEntryCode) {
		this.officerEntryCode = officerEntryCode;
	}

	public void setOfficerEntryCodeIndex(String officerEntryCodeIndex) {
		this.officerEntryCodeIndex = officerEntryCodeIndex;
	}

	/**
	 * @param organisationCode the organisationCode to set
	 */
	public void setOrganisationCode(String organisationCode) {
		this.organisationCode = organisationCode;
	}

	/**
	 * @param originalAmount the originalAmount to set
	 */
	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}

	/**
	 * @param othersFeeAmount the othersFeeAmount to set
	 */
	public void setOthersFeeAmount(String othersFeeAmount) {
		this.othersFeeAmount = othersFeeAmount;
	}

	/**
	 * @param oustandingBalanceAmount the oustandingBalanceAmount to set
	 */
	public void setOustandingBalanceAmount(String oustandingBalanceAmount) {
		this.oustandingBalanceAmount = oustandingBalanceAmount;
	}

	/**
	 * @param parValueShares the parValueShares to set
	 */
	public void setParValueShares(String parValueShares) {
		this.parValueShares = parValueShares;
	}

	/**
	 * @param paymentCodeEntryCode the paymentCodeEntryCode to set
	 */
	public void setPaymentCodeEntryCode(String paymentCodeEntryCode) {
		this.paymentCodeEntryCode = paymentCodeEntryCode;
	}

	/**
	 * @param paymentFrequencyCodeEntryCode the paymentFrequencyCodeEntryCode to
	 *        set
	 */
	public void setPaymentFrequencyCodeEntryCode(String paymentFrequencyCodeEntryCode) {
		this.paymentFrequencyCodeEntryCode = paymentFrequencyCodeEntryCode;
	}

	/**
	 * @param paymentFrequencyEntryCode the paymentFrequencyEntryCode to set
	 */
	public void setPaymentFrequencyEntryCode(String paymentFrequencyEntryCode) {
		this.paymentFrequencyEntryCode = paymentFrequencyEntryCode;
	}

	/**
	 * @param personApprovedEntryCode the personApprovedEntryCode to set
	 */
	public void setPersonApprovedEntryCode(String personApprovedEntryCode) {
		this.personApprovedEntryCode = personApprovedEntryCode;
	}

	/**
	 * @param primeRateCeiling the primeRateCeiling to set
	 */
	public void setPrimeRateCeiling(String primeRateCeiling) {
		this.primeRateCeiling = primeRateCeiling;
	}

	/**
	 * @param primeRateFloor the primeRateFloor to set
	 */
	public void setPrimeRateFloor(String primeRateFloor) {
		this.primeRateFloor = primeRateFloor;
	}

	/**
	 * @param primeReviewDate the primeReviewDate to set
	 */
	public void setPrimeReviewDate(String primeReviewDate) {
		this.primeReviewDate = primeReviewDate;
	}

	/**
	 * @param primeReviewTerm the primeReviewTerm to set
	 */
	public void setPrimeReviewTerm(String primeReviewTerm) {
		this.primeReviewTerm = primeReviewTerm;
	}

	/**
	 * @param primeReviewTermCodeEntryCode the primeReviewTermCodeEntryCode to
	 *        set
	 */
	public void setPrimeReviewTermCodeEntryCode(String primeReviewTermCodeEntryCode) {
		this.primeReviewTermCodeEntryCode = primeReviewTermCodeEntryCode;
	}

	/**
	 * @param productDesc the productDesc to set
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * @param productPackageCodeEntryCode the productPackageCodeEntryCode to set
	 */
	public void setProductPackageCodeEntryCode(String productPackageCodeEntryCode) {
		this.productPackageCodeEntryCode = productPackageCodeEntryCode;
	}

	/**
	 * @param refinanceFromBankEntryCode the refinanceFromBankEntryCode to set
	 */
	public void setRefinanceFromBankEntryCode(String refinanceFromBankEntryCode) {
		this.refinanceFromBankEntryCode = refinanceFromBankEntryCode;
	}

	/**
	 * @param requiredSecurityCoverage the requiredSecurityCoverage to set
	 */
	public void setRequiredSecurityCoverage(String requiredSecurityCoverage) {
		this.requiredSecurityCoverage = requiredSecurityCoverage;
	}

	/**
	 * @param retentionPeriod the retentionPeriod to set
	 */
	public void setRetentionPeriod(String retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}

	/**
	 * @param retentionPeriodCode the retentionPeriodCode to set
	 */
	public void setRetentionPeriodCode(String retentionPeriodCode) {
		this.retentionPeriodCode = retentionPeriodCode;
	}

	/**
	 * @param retentionSumAmount the retentionSumAmount to set
	 */
	public void setRetentionSumAmount(String retentionSumAmount) {
		this.retentionSumAmount = retentionSumAmount;
	}

	/**
	 * @param revolvingIndicator the revolvingIndicator to set
	 */
	public void setRevolvingIndicator(String revolvingIndicator) {
		this.revolvingIndicator = revolvingIndicator;
	}

	/**
	 * @param revolvingOnCriteriaIndicator the revolvingOnCriteriaIndicator to
	 *        set
	 */
	public void setRevolvingOnCriteriaIndicator(String revolvingOnCriteriaIndicator) {
		this.revolvingOnCriteriaIndicator = revolvingOnCriteriaIndicator;
	}

	/**
	 * @param solicitorName the solicitorName to set
	 */
	public void setSolicitorName(String solicitorName) {
		this.solicitorName = solicitorName;
	}

	/**
	 * @param solicitorReference the solicitorReference to set
	 */
	public void setSolicitorReference(String solicitorReference) {
		this.solicitorReference = solicitorReference;
	}

	/**
	 * @param specProvision the specProvision to set
	 */
	public void setSpecProvision(String specProvision) {
		this.specProvision = specProvision;
	}

	/**
	 * @param spread the spread to set
	 */
	public void setSpread(String spread) {
		this.spread = spread;
	}

	/**
	 * @param spreadSign the spreadSign to set
	 */
	public void setSpreadSign(String spreadSign) {
		this.spreadSign = spreadSign;
	}

	/**
	 * @param standbyLine the standbyLine to set
	 */
	public void setStandbyLine(String standbyLine) {
		this.standbyLine = standbyLine;
	}

	/**
	 * @param standByLineFacilityCode the standByLineFacilityCode to set
	 */
	public void setStandByLineFacilityCode(String standByLineFacilityCode) {
		this.standByLineFacilityCode = standByLineFacilityCode;
	}

	/**
	 * @param standByLineFacilityCodeSequence the
	 *        standByLineFacilityCodeSequence to set
	 */
	public void setStandByLineFacilityCodeSequence(String standByLineFacilityCodeSequence) {
		this.standByLineFacilityCodeSequence = standByLineFacilityCodeSequence;
	}

	/**
	 * @param subsidyAmount the subsidyAmount to set
	 */
	public void setSubsidyAmount(String subsidyAmount) {
		this.subsidyAmount = subsidyAmount;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
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
	public void setUtilisedAmount(String utilisedAmount) {
		this.utilisedAmount = utilisedAmount;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
