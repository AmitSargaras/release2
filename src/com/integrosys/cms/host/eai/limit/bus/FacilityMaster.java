package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Entity represent a facility master.
 * @author Thurein
 * @author Chong Jun Yong
 * @since 10/Oct/2008
 */
public class FacilityMaster implements Serializable {

	private static final long serialVersionUID = -6828020559512599601L;

	private long id;

	private long limitID;

	private FacilityBNM facilityBnmCodes;

	private IslamicFacilityMaster islamicFacilityMaster;

	private Set facilityMultiTierFinancings;

	private FacilityBBAVariPackage facilityBBAVariPackage;

	private Set officerSet;

	private Set relationshipSet;

	private Set facilityMessages;

	private Set facilityIncrementals;

	private Set facilityReductions;

	private FacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit;

	private FacilityIslamicRentalRenewal facilityIslamicRentalRenewal;

	private long facilityMasterId;

	private Double financedAmt;

	private Double installmentAmt;

	private String aCFNo;

	private StandardCode productPackageCode;

	private StandardCode loanPurposeCode;

	private Date offerAcceptedDate;

	private Date offerDate;

	private StandardCode approvedBy;

	private Date approveDate;

	private StandardCode cancelRejectCode;

	private Date cancelRejectDate;

	private StandardCode officer;

	private StandardCode limitStatus;

	private Double spread;

	private StandardCode rateType;

	private String spreadSign;

	private StandardCode applicationSource;

	private StandardCode dealerLPPCode;

	private Date instructedDate;

	private StandardCode departmentCode;

	private String solicitorName;

	private StandardCode lawyerCode;

	private Double odExcessRateVar;

	private String odExcessRateVarCode;

	private String mainFacilityInd;

	private String mainFacilityAANo;

	private String mainFacilityCode;

	private Long mainFacilitySequenceNumber;

	private Amount drawingAmount;

	private String standbyLine;

	private Double floorPledgeLimit;

	private String updateStatusIndicator;

	private String changeIndicator;

	private String applicationDate;

	private Double interestRate;

	private Double handlingFee;

	private String alternateRate;

	private Boolean altSchedule;

	private Integer availPeriodInDays;

	private Integer availPeriodInMonths;

	private String calFirstInstlDate;

	private String creditLineIndicator;

	private Integer level;

	private String parValueShares;

	private String retentionPeriodCode;

	private String revolvingOnCriteriaIndicator;

	private Long standByLineFacilityCodeSequence;

	private String facilityCurrencyCode;

	private String facilityStatusCategoryCode;

	private String facilityStatusEntryCode;

	/** New fields added to the CA001 */

	private Double finalPaymentAmt;

	private Double primeRateFloor;

	private Double primeRateCeiling;

	private String primeReviewDate;

	private Integer primeReviewTerm;

	private StandardCode primeReviewTermCode;

	private Double commissionFees;

	private Double commissionRate;

	private StandardCode commissionBasis;

	private Double subsidyAmt;

	private Double othersFee;

	private Double maximumCommission;

	private Double minimumCommission;

	private String standbyLineFacCode;

	private Double facCommitmentRate;

	private StandardCode facCommitmentRateNo;

	private Double retentionSum;

	private Integer retentionPeriod;

	private String dateApproveCGCBNM;

	private String effectiveCostOfFund;

	private Double ECOFAdminCost;

	private String facAvalDate;

	private String facAvalPeriod;

	private StandardCode refinanceFromBank;

	private Double ECOFRate;

	private Double ECOFVariance;

	private String ECOFVarianceCode;

	private String revolvingInd;

	private String revOSBalOrgAmt;

	private Integer standbyLineFacCodeSeq;

	private Long facilitySeqNo;

	private String accountNo;

	private String accountType;

	private String nextReviewDate;

	private Integer nextReviewTerm;

	private StandardCode nextReviewTermCode;

	private Double approvedAmount;

	private Long term;

	private StandardCode termCode;

	private String toBeCancelledInd;

	public String getAccountNo() {
		return accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getACFNo() {
		return aCFNo;
	}

	public String getAlternateRate() {
		return alternateRate;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public StandardCode getApplicationSource() {
		return applicationSource;
	}

	public Double getApprovedAmount() {
		return approvedAmount;
	}

	public String getApproveDate() {
		return MessageDate.getInstance().getString(this.approveDate);
	}

	public StandardCode getApprovedBy() {
		return approvedBy;
	}

	public Integer getAvailPeriodInDays() {
		return availPeriodInDays;
	}

	public Integer getAvailPeriodInMonths() {
		return availPeriodInMonths;
	}

	public String getCalFirstInstlDate() {
		return calFirstInstlDate;
	}

	public StandardCode getCancelRejectCode() {
		return cancelRejectCode;
	}

	public String getCancelRejectDate() {
		return MessageDate.getInstance().getString(cancelRejectDate);
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public StandardCode getCommissionBasis() {
		return commissionBasis;
	}

	public Double getCommissionFees() {
		return commissionFees;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public String getCreditLineIndicator() {
		return creditLineIndicator;
	}

	public String getDateApproveCGCBNM() {
		return dateApproveCGCBNM;
	}

	public StandardCode getDealerLPPCode() {
		return dealerLPPCode;
	}

	public StandardCode getDepartmentCode() {
		return departmentCode;
	}

	public Amount getDrawingAmount() {
		return drawingAmount;
	}

	public Double getECOFAdminCost() {
		return ECOFAdminCost;
	}

	public Double getECOFRate() {
		return ECOFRate;
	}

	public Double getECOFVariance() {
		return ECOFVariance;
	}

	public String getECOFVarianceCode() {
		return ECOFVarianceCode;
	}

	public String getEffectiveCostOfFund() {
		return effectiveCostOfFund;
	}

	public String getFacAvalDate() {
		return facAvalDate;
	}

	public String getFacAvalPeriod() {
		return facAvalPeriod;
	}

	public Double getFacCommitmentRate() {
		return facCommitmentRate;
	}

	public StandardCode getFacCommitmentRateNo() {
		return facCommitmentRateNo;
	}

	public FacilityBBAVariPackage getFacilityBBAVariPackage() {
		return facilityBBAVariPackage;
	}

	public FacilityBNM getFacilityBnmCodes() {
		return facilityBnmCodes;
	}

	public String getFacilityCurrencyCode() {
		return facilityCurrencyCode;
	}

	public Set getFacilityIncrementals() {
		return facilityIncrementals;
	}

	public FacilityIslamicRentalRenewal getFacilityIslamicRentalRenewal() {
		return facilityIslamicRentalRenewal;
	}

	public FacilityIslamicSecurityDeposit getFacilityIslamicSecurityDeposit() {
		return facilityIslamicSecurityDeposit;
	}

	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	public Set getFacilityMessages() {
		return facilityMessages;
	}

	public Set getFacilityMultiTierFinancings() {
		return facilityMultiTierFinancings;
	}

	public Set getFacilityReductions() {
		return facilityReductions;
	}

	public Long getFacilitySeqNo() {
		return facilitySeqNo;
	}

	public String getFacilityStatusCategoryCode() {
		return facilityStatusCategoryCode;
	}

	public String getFacilityStatusEntryCode() {
		return facilityStatusEntryCode;
	}

	public Double getFinalPaymentAmt() {
		return finalPaymentAmt;
	}

	public Double getFinancedAmt() {
		return this.financedAmt;
	}

	public Double getFloorPledgeLimit() {
		return floorPledgeLimit;
	}

	public Double getHandlingFee() {
		return handlingFee;
	}

	public long getId() {
		return id;
	}

	public Double getInstallmentAmt() {
		return this.installmentAmt;
	}

	/*
	 * public FacilityGeneral getFacilityGeneral() { return facilityGeneral; }
	 * 
	 * public void setFacilityGeneral(FacilityGeneral facilityGeneral) {
	 * this.facilityGeneral = facilityGeneral; }
	 */

	public String getInstructedDate() {
		return MessageDate.getInstance().getString(instructedDate);
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public IslamicFacilityMaster getIslamicFacilityMaster() {
		return islamicFacilityMaster;
	}

	public Date getJDOApplicationDate() {
		return MessageDate.getInstance().getDate(applicationDate);
	}

	public Date getJDOApproveDate() {
		return approveDate;
	}

	public Date getJDOCancelRejectDate() {
		return cancelRejectDate;
	}

	public Date getJDODateApproveCGCBNM() {
		return MessageDate.getInstance().getDate(dateApproveCGCBNM);
	}

	public Date getJDOFacAvalDate() {
		return MessageDate.getInstance().getDate(facAvalDate);
	}

	public Date getJDOInstructedDate() {
		return instructedDate;
	}

	public Date getJDONextReviewDate() {
		return MessageDate.getInstance().getDate(nextReviewDate);
	}

	public Date getJDOOfferAcceptedDate() {

		return offerAcceptedDate;
	}

	public Date getJDOOfferDate() {
		return offerDate;
	}

	public Date getJDOPrimeReviewDate() {
		return MessageDate.getInstance().getDate(primeReviewDate);
	}

	public StandardCode getLawyerCode() {
		return lawyerCode;
	}

	public Integer getLevel() {
		return level;
	}

	public long getLimitID() {
		return limitID;
	}

	public StandardCode getLimitStatus() {
		return limitStatus;
	}

	public StandardCode getLoanPurposeCode() {
		return loanPurposeCode;
	}

	public String getMainFacilityAANo() {
		return mainFacilityAANo;
	}

	public String getMainFacilityCode() {
		return mainFacilityCode;
	}

	public String getMainFacilityInd() {
		return mainFacilityInd;
	}

	public Long getMainFacilitySequenceNumber() {
		return mainFacilitySequenceNumber;
	}

	public Double getMaximumCommission() {
		return maximumCommission;
	}

	public Double getMinimumCommission() {
		return minimumCommission;
	}

	public String getNextReviewDate() {
		return nextReviewDate;
	}

	public Integer getNextReviewTerm() {
		return nextReviewTerm;
	}

	public StandardCode getNextReviewTermCode() {
		return nextReviewTermCode;
	}

	public Double getOdExcessRateVar() {
		return odExcessRateVar;
	}

	public String getOdExcessRateVarCode() {
		return odExcessRateVarCode;
	}

	public String getOfferAcceptedDate() {
		return MessageDate.getInstance().getString(offerAcceptedDate);
	}

	public String getOfferDate() {
		return MessageDate.getInstance().getString(offerDate);
	}

	public StandardCode getOfficer() {
		return officer;
	}

	public Set getOfficerSet() {
		return officerSet;
	}

	public Double getOthersFee() {
		return othersFee;
	}

	public String getParValueShares() {
		return parValueShares;
	}

	public Double getPrimeRateCeiling() {
		return primeRateCeiling;
	}

	public Double getPrimeRateFloor() {
		return primeRateFloor;
	}

	public String getPrimeReviewDate() {
		return primeReviewDate;
	}

	public Integer getPrimeReviewTerm() {
		return primeReviewTerm;
	}

	public StandardCode getPrimeReviewTermCode() {
		return primeReviewTermCode;
	}

	public StandardCode getProductPackageCode() {
		return productPackageCode;
	}

	public StandardCode getRateType() {
		return rateType;
	}

	public StandardCode getRefinanceFromBank() {
		return refinanceFromBank;
	}

	public Set getRelationshipSet() {
		return relationshipSet;
	}

	public Integer getRetentionPeriod() {
		return retentionPeriod;
	}

	public String getRetentionPeriodCode() {
		return retentionPeriodCode;
	}

	public Double getRetentionSum() {
		return retentionSum;
	}

	public String getRevolvingInd() {
		return revolvingInd;
	}

	public String getRevolvingOnCriteriaIndicator() {
		return revolvingOnCriteriaIndicator;
	}

	public String getRevOSBalOrgAmt() {
		return revOSBalOrgAmt;
	}

	public String getSolicitorName() {
		return solicitorName;
	}

	public Double getSpread() {
		return spread;
	}

	public String getSpreadSign() {
		return spreadSign;
	}

	public String getStandbyLine() {
		return standbyLine;
	}

	public String getStandbyLineFacCode() {
		return standbyLineFacCode;
	}

	public Integer getStandbyLineFacCodeSeq() {
		return standbyLineFacCodeSeq;
	}

	public Long getStandByLineFacilityCodeSequence() {
		return standByLineFacilityCodeSequence;
	}

	public Double getSubsidyAmt() {
		return subsidyAmt;
	}

	public Long getTerm() {
		return term;
	}

	public StandardCode getTermCode() {
		return termCode;
	}

	public String getToBeCancelledInd() {
		return toBeCancelledInd;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public Boolean isAltSchedule() {
		return altSchedule;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setACFNo(String aCFNo) {
		this.aCFNo = aCFNo;
	}

	public void setAlternateRate(String alternateRate) {
		this.alternateRate = alternateRate;
	}

	public void setAltSchedule(Boolean altSchedule) {
		this.altSchedule = altSchedule;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public void setApplicationSource(StandardCode applicationSource) {
		this.applicationSource = applicationSource;
	}

	public void setApprovedAmount(Double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = MessageDate.getInstance().getDate(approveDate);
	}

	public void setApprovedBy(StandardCode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setAvailPeriodInDays(Integer availPeriodInDays) {
		this.availPeriodInDays = availPeriodInDays;
	}

	public void setAvailPeriodInMonths(Integer availPeriodInMonths) {
		this.availPeriodInMonths = availPeriodInMonths;
	}

	public void setCalFirstInstlDate(String calFirstInstlDate) {
		this.calFirstInstlDate = calFirstInstlDate;
	}

	public void setCancelRejectCode(StandardCode cancelRejectCode) {
		this.cancelRejectCode = cancelRejectCode;
	}

	public void setCancelRejectDate(Date cancelRejectDate) {
		this.cancelRejectDate = cancelRejectDate;
	}

	public void setCancelRejectDate(String cancelRejectDate) {
		this.cancelRejectDate = MessageDate.getInstance().getDate(cancelRejectDate);
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCommissionBasis(StandardCode commissionBasis) {
		this.commissionBasis = commissionBasis;
	}

	public void setCommissionFees(Double commissionFees) {
		this.commissionFees = commissionFees;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public void setCreditLineIndicator(String creditLineIndicator) {
		this.creditLineIndicator = creditLineIndicator;
	}

	public void setDateApproveCGCBNM(String dateApproveCGCBNM) {
		this.dateApproveCGCBNM = dateApproveCGCBNM;
	}

	public void setDealerLPPCode(StandardCode dealerLPPCode) {
		this.dealerLPPCode = dealerLPPCode;
	}

	public void setDepartmentCode(StandardCode departmentCode) {
		this.departmentCode = departmentCode;
	}

	public void setDrawingAmount(Amount drawingAmount) {
		this.drawingAmount = drawingAmount;
	}

	public void setECOFAdminCost(Double adminCost) {
		ECOFAdminCost = adminCost;
	}

	public void setECOFRate(Double rate) {
		ECOFRate = rate;
	}

	public void setECOFVariance(Double variance) {
		ECOFVariance = variance;
	}

	public void setECOFVarianceCode(String varianceCode) {
		ECOFVarianceCode = varianceCode;
	}

	public void setEffectiveCostOfFund(String effectiveCostOfFund) {
		this.effectiveCostOfFund = effectiveCostOfFund;
	}

	public void setFacAvalDate(String facAvalDate) {
		this.facAvalDate = facAvalDate;
	}

	public void setFacAvalPeriod(String facAvalPeriod) {
		this.facAvalPeriod = facAvalPeriod;
	}

	public void setFacCommitmentRate(Double facCommitmentRate) {
		this.facCommitmentRate = facCommitmentRate;
	}

	public void setFacCommitmentRateNo(StandardCode facCommitmentRateNo) {
		this.facCommitmentRateNo = facCommitmentRateNo;
	}

	public void setFacilityBBAVariPackage(FacilityBBAVariPackage facilityBBAVariPackage) {
		this.facilityBBAVariPackage = facilityBBAVariPackage;
	}

	public void setFacilityBnmCodes(FacilityBNM facilityBnmCodes) {
		this.facilityBnmCodes = facilityBnmCodes;
	}

	public void setFacilityCurrencyCode(String facilityCurrencyCode) {
		this.facilityCurrencyCode = facilityCurrencyCode;
	}

	public void setFacilityIncrementals(Set facilityIncrementals) {
		this.facilityIncrementals = facilityIncrementals;
	}

	public void setFacilityIslamicRentalRenewal(FacilityIslamicRentalRenewal facilityIslamicRentalRenewal) {
		this.facilityIslamicRentalRenewal = facilityIslamicRentalRenewal;
	}

	public void setFacilityIslamicSecurityDeposit(FacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit) {
		this.facilityIslamicSecurityDeposit = facilityIslamicSecurityDeposit;
	}

	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	public void setFacilityMessages(Set facilityMessages) {
		this.facilityMessages = facilityMessages;
	}

	public void setFacilityMultiTierFinancings(Set facilityMultiTierFinancings) {
		this.facilityMultiTierFinancings = facilityMultiTierFinancings;
	}

	public void setFacilityReductions(Set facilityReductions) {
		this.facilityReductions = facilityReductions;
	}

	public void setFacilitySeqNo(Long facilitySeqNo) {
		this.facilitySeqNo = facilitySeqNo;
	}

	public void setFacilityStatusCategoryCode(String facilityStatusCategoryCode) {
		this.facilityStatusCategoryCode = facilityStatusCategoryCode;
	}

	public void setFacilityStatusEntryCode(String facilityStatusEntryCode) {
		this.facilityStatusEntryCode = facilityStatusEntryCode;
	}

	public void setFinalPaymentAmt(Double finalPaymentAmt) {
		this.finalPaymentAmt = finalPaymentAmt;
	}

	public void setFinancedAmt(Double financedAmt) {
		this.financedAmt = financedAmt;
	}

	public void setFloorPledgeLimit(Double floorPledgeLimit) {
		this.floorPledgeLimit = floorPledgeLimit;
	}

	public void setHandlingFee(Double handlingFee) {
		this.handlingFee = handlingFee;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInstallmentAmt(Double installmentAmt) {
		this.installmentAmt = installmentAmt;
	}

	public void setInstructedDate(Date instructedDate) {
		this.instructedDate = instructedDate;
	}

	public void setInstructedDate(String instructedDate) {
		this.instructedDate = MessageDate.getInstance().getDate(instructedDate);
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public void setIslamicFacilityMaster(IslamicFacilityMaster islamicFacilityMaster) {
		this.islamicFacilityMaster = islamicFacilityMaster;
	}

	public void setJDOApplicationDate(Date applicationDate) {
		this.applicationDate = MessageDate.getInstance().getString(applicationDate);
	}

	public void setJDOApproveDate(Date approveDate) {

		this.approveDate = approveDate;
	}

	public void setJDOCancelRejectDate(Date cancelRejectDate) {
		this.cancelRejectDate = cancelRejectDate;
	}

	public void setJDODateApproveCGCBNM(Date dateApproveCGCBNM) {
		this.dateApproveCGCBNM = MessageDate.getInstance().getString(dateApproveCGCBNM);
	}

	public void setJDOFacAvalDate(Date facAvalDate) {
		this.facAvalDate = MessageDate.getInstance().getString(facAvalDate);
	}

	public void setJDOInstructedDate(Date instructedDate) {
		this.instructedDate = instructedDate;
	}

	public void setJDONextReviewDate(Date nextReviewDate) {
		this.nextReviewDate = MessageDate.getInstance().getString(nextReviewDate);
	}

	public void setJDOOfferAcceptedDate(Date offerAcceptedDate) {
		this.offerAcceptedDate = offerAcceptedDate;
	}

	public void setJDOOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	public void setJDOPrimeReviewDate(Date primeReviewDate) {
		this.primeReviewDate = MessageDate.getInstance().getString(primeReviewDate);
	}

	public void setLawyerCode(StandardCode lawyerCode) {
		this.lawyerCode = lawyerCode;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	public void setLimitStatus(StandardCode limitStatus) {
		this.limitStatus = limitStatus;
	}

	public void setLoanPurposeCode(StandardCode loanPurposeCode) {
		this.loanPurposeCode = loanPurposeCode;
	}

	public void setMainFacilityAANo(String mainFacilityAANo) {
		this.mainFacilityAANo = mainFacilityAANo;
	}

	public void setMainFacilityCode(String mainFacilityCode) {
		this.mainFacilityCode = mainFacilityCode;
	}

	public void setMainFacilityInd(String mainFacilityInd) {
		this.mainFacilityInd = mainFacilityInd;
	}

	public void setMainFacilitySequenceNumber(Long mainFacilitySequenceNumber) {
		this.mainFacilitySequenceNumber = mainFacilitySequenceNumber;
	}

	public void setMaximumCommission(Double maximumCommission) {
		this.maximumCommission = maximumCommission;
	}

	public void setMinimumCommission(Double minimumCommission) {
		this.minimumCommission = minimumCommission;
	}

	public void setNextReviewDate(String nextReviewDate) {
		this.nextReviewDate = nextReviewDate;
	}

	public void setNextReviewTerm(Integer nextReviewTerm) {
		this.nextReviewTerm = nextReviewTerm;
	}

	public void setNextReviewTermCode(StandardCode nextReviewTermCode) {
		this.nextReviewTermCode = nextReviewTermCode;
	}

	public void setOdExcessRateVar(Double odExcessRateVar) {
		this.odExcessRateVar = odExcessRateVar;
	}

	public void setOdExcessRateVarCode(String odExcessRateVarCode) {
		this.odExcessRateVarCode = odExcessRateVarCode;
	}

	public void setOfferAcceptedDate(Date offerAcceptedDate) {
		this.offerAcceptedDate = offerAcceptedDate;
	}

	public void setOfferAcceptedDate(String offerAcceptedDate) {
		this.offerAcceptedDate = MessageDate.getInstance().getDate(offerAcceptedDate);
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	public void setOfferDate(String offerDate) {
		this.offerDate = MessageDate.getInstance().getDate(offerDate);
	}

	public void setOfficer(StandardCode officer) {
		this.officer = officer;
	}

	public void setOfficerSet(Set officerSet) {
		this.officerSet = officerSet;
	}

	public void setOthersFee(Double othersFee) {
		this.othersFee = othersFee;
	}

	public void setParValueShares(String parValueShares) {
		this.parValueShares = parValueShares;
	}

	public void setPrimeRateCeiling(Double primeRateCeiling) {
		this.primeRateCeiling = primeRateCeiling;
	}

	public void setPrimeRateFloor(Double primeRateFloor) {
		this.primeRateFloor = primeRateFloor;
	}

	public void setPrimeReviewDate(String primeReviewDate) {
		this.primeReviewDate = primeReviewDate;
	}

	public void setPrimeReviewTerm(Integer primeReviewTerm) {
		this.primeReviewTerm = primeReviewTerm;
	}

	public void setPrimeReviewTermCode(StandardCode primeReviewTermCode) {
		this.primeReviewTermCode = primeReviewTermCode;
	}

	public void setProductPackageCode(StandardCode productPackageCode) {
		this.productPackageCode = productPackageCode;
	}

	public void setRateType(StandardCode rateType) {
		this.rateType = rateType;
	}

	public void setRefinanceFromBank(StandardCode refinanceFromBank) {
		this.refinanceFromBank = refinanceFromBank;
	}

	public void setRelationshipSet(Set relationshipSet) {
		this.relationshipSet = relationshipSet;
	}

	public void setRetentionPeriod(Integer retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}

	public void setRetentionPeriodCode(String retentionPeriodCode) {
		this.retentionPeriodCode = retentionPeriodCode;
	}

	public void setRetentionSum(Double retentionSum) {
		this.retentionSum = retentionSum;
	}

	public void setRevolvingInd(String revolvingInd) {
		this.revolvingInd = revolvingInd;
	}

	public void setRevolvingOnCriteriaIndicator(String revolvingOnCriteriaIndicator) {
		this.revolvingOnCriteriaIndicator = revolvingOnCriteriaIndicator;
	}

	public void setRevOSBalOrgAmt(String revOSBalOrgAmt) {
		this.revOSBalOrgAmt = revOSBalOrgAmt;
	}

	public void setSolicitorName(String solicitorName) {
		this.solicitorName = solicitorName;
	}

	public void setSpread(Double spread) {
		this.spread = spread;
	}

	public void setSpreadSign(String spreadSign) {
		this.spreadSign = spreadSign;
	}

	public void setStandbyLine(String standbyLine) {
		this.standbyLine = standbyLine;
	}

	public void setStandbyLineFacCode(String standbyLineFacCode) {
		this.standbyLineFacCode = standbyLineFacCode;
	}

	public void setStandbyLineFacCodeSeq(Integer standbyLineFacCodeSeq) {
		this.standbyLineFacCodeSeq = standbyLineFacCodeSeq;
	}

	public void setStandByLineFacilityCodeSequence(Long standByLineFacilityCodeSequence) {
		this.standByLineFacilityCodeSequence = standByLineFacilityCodeSequence;
	}

	public void setSubsidyAmt(Double subsidyAmt) {
		this.subsidyAmt = subsidyAmt;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public void setTermCode(StandardCode termCode) {
		this.termCode = termCode;
	}

	public void setToBeCancelledInd(String toBeCancelledInd) {
		this.toBeCancelledInd = toBeCancelledInd;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("FacilityMaster [");
		buf.append("applicationDate=");
		buf.append(applicationDate);
		buf.append(", approveDate=");
		buf.append(approveDate);
		buf.append(", approvedAmount=");
		buf.append(approvedAmount);
		buf.append(", approvedBy=");
		buf.append(approvedBy);
		buf.append(", facilityCurrencyCode=");
		buf.append(facilityCurrencyCode);
		buf.append(", facilitySeqNo=");
		buf.append(facilitySeqNo);
		buf.append(", facilityStatusCategoryCode=");
		buf.append(facilityStatusCategoryCode);
		buf.append(", facilityStatusEntryCode=");
		buf.append(facilityStatusEntryCode);
		buf.append(", financedAmt=");
		buf.append(financedAmt);
		buf.append(", installmentAmt=");
		buf.append(installmentAmt);
		buf.append(", interestRate=");
		buf.append(interestRate);
		buf.append(", limitID=");
		buf.append(limitID);
		buf.append(", offerAcceptedDate=");
		buf.append(offerAcceptedDate);
		buf.append(", offerDate=");
		buf.append(offerDate);
		buf.append(", officer=");
		buf.append(officer);
		buf.append(", solicitorName=");
		buf.append(solicitorName);
		buf.append(", term=");
		buf.append(term);
		buf.append(", termCode=");
		buf.append(termCode);
		buf.append(", facilityBnmCodes=");
		buf.append(facilityBnmCodes);
		buf.append(", officerSet=");
		buf.append(officerSet);
		buf.append(", relationshipSet=");
		buf.append(relationshipSet);
		buf.append("]");
		return buf.toString();
	}

}
