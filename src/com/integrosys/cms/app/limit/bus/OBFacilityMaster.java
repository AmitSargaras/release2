package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityMaster implements IFacilityMaster {

	private static final long serialVersionUID = 5820242160116259611L;

	private long id;

	private String requiredSecurityCoverage; // Shiv 190911

	private Amount drawingLimitAmount;

	private ILimit limit;

	private String acfNo;

	private Short retentionPeriod;

	private Date dateInstructed;

	private IFacilityGeneral facilityGeneral;

	private IFacilityInterest facilityInterest;

	private IFacilityFeeCharge facilityFeeCharge;

	private IFacilityPayment facilityPayment;

	private IFacilityBnmCodes facilityBnmCodes;

	private Set facilityOfficerSet;

	private Set facilityRelationshipSet;

	private Set facilityInsuranceSet;

	private Set facilityMultiTierFinancingSet;

	private String applicationSourceCategoryCode;

	private String applicationSourceEntryCode;

	private Double odExcessRateVar;

	private String odExcessRateVarCode;

	private String dealerNumberOrLppCodeCategoryCode;

	private String dealerNumberOrLppCodeEntryCode;

	private String standByLineFacilityCode;

	private Long standByLineFacilityCodeSequence;

	private Date lastMaintenanceDate;

	private boolean mainFacility;

	private String mainFacilityAaNumber;

	private String mainFacilityCode;

	private Long mainFacilitySequenceNumber;

	private String standbyLine;

	private Integer level;

	private BigDecimal specProvision;

	private BigDecimal intInSuspense;

	private String revolvingIndicator;

	private String revolvingOnCriteriaIndicator;

	private Amount floorPledgedLimitAmount;

	private Amount actualPledgedLimitAmount;

	private Boolean altSchedule;

	private Integer availPeriodInMonths;

	private Integer availPeriodInDays;

	private Amount retentionSumAmount;

	private String retentionPeriodCode;

	private String disbursementManner;

	private String calFirstInstlDate;

	private String isoReferralNumber;

	private Boolean allowIncentive;

	private Date cgcBnmApprovedDate;

	private String alternateRate;

	private String creditLineIndicator;

	private String productPackageCodeCategoryCode;

	private String productPackageCodeEntryCode;

	private Boolean effectiveCostOfFund;

	private Amount ecofAdministrationCostAmount;

	private Double ecofRate;

	private Double ecofVariance;

	private String ecofVarianceCode;

	private Date facilityAvailDate;

	private String facilityAvailPeriod;

	private String solicitorReference;

	private String departmentCodeCategoryCode;

	private String departmentCodeEntryCode;

	private String refinanceFromBankCategoryCode;

	private String refinanceFromBankEntryCode;

	private String solicitorName;

	private String lawyerCodeCategoryCode;

	private String lawyerCodeEntryCode;

	private String parValueShares;

	private String facilityCurrencyCode;

	private IFacilityIslamicMaster facilityIslamicMaster;

	private IFacilityIslamicBbaVariPackage facilityIslamicBbaVariPackage;

	private OBFacilityIslamicRentalRenewal facilityIslamicRentalRenewal;

	private OBFacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit;

	/** a set of <tt>OBFacilityMessage</tt> objects */
	private Set facilityMessages;

	/** a set of <tt>OBFacilityIncremental</tt> objects */
	private Set facilityIncrementals;

	/** a set of <tt>OBFacilityReduction</tt> objects */
	private Set facilityReductions;

	private String toBeCancelledInd;

	/**
	 * @return the acfNo
	 */
	public String getAcfNo() {
		return acfNo;
	}

	/**
	 * @return the actualPledgedLimitAmount
	 */
	public Amount getActualPledgedLimitAmount() {
		return actualPledgedLimitAmount;
	}

	/**
	 * @return the allowIncentive
	 */
	public Boolean getAllowIncentive() {
		return allowIncentive;
	}

	/**
	 * @return the alternateRate
	 */
	public String getAlternateRate() {
		return alternateRate;
	}

	/**
	 * @return the applicationSourceCategoryCode
	 */
	public String getApplicationSourceCategoryCode() {
		return applicationSourceCategoryCode;
	}

	/**
	 * @return the applicationSourceEntryCode
	 */
	public String getApplicationSourceEntryCode() {
		return applicationSourceEntryCode;
	}

	/**
	 * @return the availPeriodInDays
	 */
	public Integer getAvailPeriodInDays() {
		return availPeriodInDays;
	}

	/**
	 * @return the availPeriodInMonths
	 */
	public Integer getAvailPeriodInMonths() {
		return availPeriodInMonths;
	}

	/**
	 * @return the calFirstInstlDate
	 */
	public String getCalFirstInstlDate() {
		return calFirstInstlDate;
	}

	/**
	 * @return the cgcBnmApprovedDate
	 */
	public Date getCgcBnmApprovedDate() {
		return cgcBnmApprovedDate;
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
	public Date getDateInstructed() {
		return dateInstructed;
	}

	/**
	 * @return the dealerNumberOrLppCodeCategoryCode
	 */
	public String getDealerNumberOrLppCodeCategoryCode() {
		return dealerNumberOrLppCodeCategoryCode;
	}

	/**
	 * @return the dealerNumberOrLppCodeEntryCode
	 */
	public String getDealerNumberOrLppCodeEntryCode() {
		return dealerNumberOrLppCodeEntryCode;
	}

	/**
	 * @return the departmentCodeCategoryCode
	 */
	public String getDepartmentCodeCategoryCode() {
		return departmentCodeCategoryCode;
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
	public Amount getDrawingLimitAmount() {
		return drawingLimitAmount;
	}

	/**
	 * @return the ecofAdministrationCostAmount
	 */
	public Amount getEcofAdministrationCostAmount() {
		return ecofAdministrationCostAmount;
	}

	/**
	 * @return the ecofRate
	 */
	public Double getEcofRate() {
		return ecofRate;
	}

	/**
	 * @return the ecofVariance
	 */
	public Double getEcofVariance() {
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
	public Boolean getEffectiveCostOfFund() {
		return effectiveCostOfFund;
	}

	/**
	 * @return the facilityAvailDate
	 */
	public Date getFacilityAvailDate() {
		return facilityAvailDate;
	}

	/**
	 * @return the facilityAvailPeriod
	 */
	public String getFacilityAvailPeriod() {
		return facilityAvailPeriod;
	}

	/**
	 * @return the facilityBnmCodes
	 */
	public IFacilityBnmCodes getFacilityBnmCodes() {
		return facilityBnmCodes;
	}

	public String getFacilityCurrencyCode() {
		return facilityCurrencyCode;
	}

	/**
	 * @return the facilityFeeCharge
	 */
	public IFacilityFeeCharge getFacilityFeeCharge() {
		return facilityFeeCharge;
	}

	/**
	 * @return the facilityGeneral
	 */
	public IFacilityGeneral getFacilityGeneral() {
		return facilityGeneral;
	}

	public Set getFacilityIncrementals() {
		return facilityIncrementals;
	}

	/**
	 * @return the facilityInsuranceSet
	 */
	public Set getFacilityInsuranceSet() {
		return facilityInsuranceSet;
	}

	/**
	 * @return the facilityInterest
	 */
	public IFacilityInterest getFacilityInterest() {
		return facilityInterest;
	}

	/**
	 * @return the facilityIslamicBbaVariPackage
	 */
	public IFacilityIslamicBbaVariPackage getFacilityIslamicBbaVariPackage() {
		return facilityIslamicBbaVariPackage;
	}

	/**
	 * @return the facilityIslamicMaster
	 */
	public IFacilityIslamicMaster getFacilityIslamicMaster() {
		return facilityIslamicMaster;
	}

	public OBFacilityIslamicRentalRenewal getFacilityIslamicRentalRenewal() {
		return this.facilityIslamicRentalRenewal;
	}

	public OBFacilityIslamicSecurityDeposit getFacilityIslamicSecurityDeposit() {
		return this.facilityIslamicSecurityDeposit;
	}

	public Set getFacilityMessages() {
		return facilityMessages;
	}

	/**
	 * @return the facilityMultiTierFinancing
	 */
	public Set getFacilityMultiTierFinancingSet() {
		return facilityMultiTierFinancingSet;
	}

	/**
	 * @return the facilityOfficerSet
	 */
	public Set getFacilityOfficerSet() {
		return facilityOfficerSet;
	}

	/**
	 * @return the facilityPayment
	 */
	public IFacilityPayment getFacilityPayment() {
		return facilityPayment;
	}

	public Set getFacilityReductions() {
		return facilityReductions;
	}

	/**
	 * @return the facilityRelationshipSet
	 */
	public Set getFacilityRelationshipSet() {
		return facilityRelationshipSet;
	}

	/**
	 * @return the floorPledgedLimitAmount
	 */
	public Amount getFloorPledgedLimitAmount() {
		return floorPledgedLimitAmount;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the intInSuspense
	 */
	public BigDecimal getIntInSuspense() {
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
	public Date getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	/**
	 * @return the lawyerCodeCategoryCode
	 */
	public String getLawyerCodeCategoryCode() {
		return lawyerCodeCategoryCode;
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
	public Integer getLevel() {
		return level;
	}

	/**
	 * @return the limit
	 */
	public ILimit getLimit() {
		return limit;
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
	public Long getMainFacilitySequenceNumber() {
		return mainFacilitySequenceNumber;
	}

	/**
	 * @return the odExcessRateVar
	 */
	public Double getOdExcessRateVar() {
		return odExcessRateVar;
	}

	/**
	 * @return the odExcessRateVarCode
	 */
	public String getOdExcessRateVarCode() {
		return odExcessRateVarCode;
	}

	/**
	 * @return the parValueShares
	 */
	public String getParValueShares() {
		return parValueShares;
	}

	/**
	 * @return the productPackageCodeCategoryCode
	 */
	public String getProductPackageCodeCategoryCode() {
		return productPackageCodeCategoryCode;
	}

	/**
	 * @return the productPackageCodeEntryCode
	 */
	public String getProductPackageCodeEntryCode() {
		return productPackageCodeEntryCode;
	}

	/**
	 * @return the refinanceFromBankCategoryCode
	 */
	public String getRefinanceFromBankCategoryCode() {
		return refinanceFromBankCategoryCode;
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
	public String getRequiredSecurityCoverage() { // Shiv 190911
		return requiredSecurityCoverage; 
	}

	/**
	 * @return the retentionPeriod
	 */
	public Short getRetentionPeriod() {
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
	public Amount getRetentionSumAmount() {
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
	public BigDecimal getSpecProvision() {
		return specProvision;
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
	public Long getStandByLineFacilityCodeSequence() {
		return standByLineFacilityCodeSequence;
	}

	public String getToBeCancelledInd() {
		return toBeCancelledInd;
	}

	/**
	 * @return the altSchedule
	 */
	public Boolean isAltSchedule() {
		return altSchedule;
	}

	/**
	 * @param acfNo
	 *            the acfNo to set
	 */
	public void setAcfNo(String acfNo) {
		this.acfNo = acfNo;
	}

	/**
	 * @param actualPledgedLimitAmount
	 *            the actualPledgedLimitAmount to set
	 */
	public void setActualPledgedLimitAmount(Amount actualPledgedLimitAmount) {
		this.actualPledgedLimitAmount = actualPledgedLimitAmount;
	}

	/**
	 * @param allowIncentive
	 *            the allowIncentive to set
	 */
	public void setAllowIncentive(Boolean allowIncentive) {
		this.allowIncentive = allowIncentive;
	}

	/**
	 * @param alternateRate
	 *            the alternateRate to set
	 */
	public void setAlternateRate(String alternateRate) {
		this.alternateRate = alternateRate;
	}

	/**
	 * @param altSchedule
	 *            the altSchedule to set
	 */
	public void setAltSchedule(Boolean altSchedule) {
		this.altSchedule = altSchedule;
	}

	/**
	 * @param applicationSourceCategoryCode
	 *            the applicationSourceCategoryCode to set
	 */
	public void setApplicationSourceCategoryCode(
			String applicationSourceCategoryCode) {
		this.applicationSourceCategoryCode = applicationSourceCategoryCode;
	}

	/**
	 * @param applicationSourceEntryCode
	 *            the applicationSourceEntryCode to set
	 */
	public void setApplicationSourceEntryCode(String applicationSourceEntryCode) {
		this.applicationSourceEntryCode = applicationSourceEntryCode;
	}

	/**
	 * @param availPeriodInDays
	 *            the availPeriodInDays to set
	 */
	public void setAvailPeriodInDays(Integer availPeriodInDays) {
		this.availPeriodInDays = availPeriodInDays;
	}

	/**
	 * @param availPeriodInMonths
	 *            the availPeriodInMonths to set
	 */
	public void setAvailPeriodInMonths(Integer availPeriodInMonths) {
		this.availPeriodInMonths = availPeriodInMonths;
	}

	/**
	 * @param calFirstInstlDate
	 *            the calFirstInstlDate to set
	 */
	public void setCalFirstInstlDate(String calFirstInstlDate) {
		this.calFirstInstlDate = calFirstInstlDate;
	}

	/**
	 * @param cgcBnmApprovedDate
	 *            the cgcBnmApprovedDate to set
	 */
	public void setCgcBnmApprovedDate(Date cgcBnmApprovedDate) {
		this.cgcBnmApprovedDate = cgcBnmApprovedDate;
	}

	/**
	 * @param creditLineIndicator
	 *            the creditLineIndicator to set
	 */
	public void setCreditLineIndicator(String creditLineIndicator) {
		this.creditLineIndicator = creditLineIndicator;
	}

	/**
	 * @param dateInstructed
	 *            the dateInstructed to set
	 */
	public void setDateInstructed(Date dateInstructed) {
		this.dateInstructed = dateInstructed;
	}

	/**
	 * @param dealerNumberOrLppCodeCategoryCode
	 *            the dealerNumberOrLppCodeCategoryCode to set
	 */
	public void setDealerNumberOrLppCodeCategoryCode(
			String dealerNumberOrLppCodeCategoryCode) {
		this.dealerNumberOrLppCodeCategoryCode = dealerNumberOrLppCodeCategoryCode;
	}

	/**
	 * @param dealerNumberOrLppCodeEntryCode
	 *            the dealerNumberOrLppCodeEntryCode to set
	 */
	public void setDealerNumberOrLppCodeEntryCode(
			String dealerNumberOrLppCodeEntryCode) {
		this.dealerNumberOrLppCodeEntryCode = dealerNumberOrLppCodeEntryCode;
	}

	/**
	 * @param departmentCodeCategoryCode
	 *            the departmentCodeCategoryCode to set
	 */
	public void setDepartmentCodeCategoryCode(String departmentCodeCategoryCode) {
		this.departmentCodeCategoryCode = departmentCodeCategoryCode;
	}

	/**
	 * @param departmentCodeEntryCode
	 *            the departmentCodeEntryCode to set
	 */
	public void setDepartmentCodeEntryCode(String departmentCodeEntryCode) {
		this.departmentCodeEntryCode = departmentCodeEntryCode;
	}

	/**
	 * @param disbursementManner
	 *            the disbursementManner to set
	 */
	public void setDisbursementManner(String disbursementManner) {
		this.disbursementManner = disbursementManner;
	}

	/**
	 * @param drawingLimitAmount
	 *            the drawingLimitAmount to set
	 */
	public void setDrawingLimitAmount(Amount drawingLimitAmount) {
		this.drawingLimitAmount = drawingLimitAmount;
	}

	/**
	 * @param ecofAdministrationCostAmount
	 *            the ecofAdministrationCostAmount to set
	 */
	public void setEcofAdministrationCostAmount(
			Amount ecofAdministrationCostAmount) {
		this.ecofAdministrationCostAmount = ecofAdministrationCostAmount;
	}

	/**
	 * @param ecofRate
	 *            the ecofRate to set
	 */
	public void setEcofRate(Double ecofRate) {
		this.ecofRate = ecofRate;
	}

	/**
	 * @param ecofVariance
	 *            the ecofVariance to set
	 */
	public void setEcofVariance(Double ecofVariance) {
		this.ecofVariance = ecofVariance;
	}

	/**
	 * @param ecofVarianceCode
	 *            the ecofVarianceCode to set
	 */
	public void setEcofVarianceCode(String ecofVarianceCode) {
		this.ecofVarianceCode = ecofVarianceCode;
	}

	/**
	 * @param effectiveCostOfFund
	 *            the effectiveCostOfFund to set
	 */
	public void setEffectiveCostOfFund(Boolean effectiveCostOfFund) {
		this.effectiveCostOfFund = effectiveCostOfFund;
	}

	/**
	 * @param facilityAvailDate
	 *            the facilityAvailDate to set
	 */
	public void setFacilityAvailDate(Date facilityAvailDate) {
		this.facilityAvailDate = facilityAvailDate;
	}

	/**
	 * @param facilityAvailPeriod
	 *            the facilityAvailPeriod to set
	 */
	public void setFacilityAvailPeriod(String facilityAvailPeriod) {
		this.facilityAvailPeriod = facilityAvailPeriod;
	}

	/**
	 * @param facilityBnmCodes
	 *            the facilityBnmCodes to set
	 */
	public void setFacilityBnmCodes(IFacilityBnmCodes facilityBnmCodes) {
		this.facilityBnmCodes = facilityBnmCodes;
	}

	public void setFacilityCurrencyCode(String facilityCurrencyCode) {
		this.facilityCurrencyCode = facilityCurrencyCode;
	}

	/**
	 * @param facilityFeeCharge
	 *            the facilityFeeCharge to set
	 */
	public void setFacilityFeeCharge(IFacilityFeeCharge facilityFeeCharge) {
		this.facilityFeeCharge = facilityFeeCharge;
	}

	/**
	 * @param facilityGeneral
	 *            the facilityGeneral to set
	 */
	public void setFacilityGeneral(IFacilityGeneral facilityGeneral) {
		this.facilityGeneral = facilityGeneral;
	}

	public void setFacilityIncrementals(Set facilityIncrementals) {
		this.facilityIncrementals = facilityIncrementals;
	}

	/**
	 * @param facilityInsuranceSet
	 *            the facilityInsuranceSet to set
	 */
	public void setFacilityInsuranceSet(Set facilityInsuranceSet) {
		this.facilityInsuranceSet = facilityInsuranceSet;
	}

	/**
	 * @param facilityInterest
	 *            the facilityInterest to set
	 */
	public void setFacilityInterest(IFacilityInterest facilityInterest) {
		this.facilityInterest = facilityInterest;
	}

	/**
	 * @param facilityIslamicBbaVariPackage
	 *            the facilityIslamicBbaVariPackage to set
	 */
	public void setFacilityIslamicBbaVariPackage(
			IFacilityIslamicBbaVariPackage facilityIslamicBbaVariPackage) {
		this.facilityIslamicBbaVariPackage = facilityIslamicBbaVariPackage;
	}

	/**
	 * @param facilityIslamicMaster
	 *            the facilityIslamicMaster to set
	 */
	public void setFacilityIslamicMaster(
			IFacilityIslamicMaster facilityIslamicMaster) {
		this.facilityIslamicMaster = facilityIslamicMaster;
	}

	public void setFacilityIslamicRentalRenewal(
			OBFacilityIslamicRentalRenewal facilityIslamicRentalRenewal) {
		this.facilityIslamicRentalRenewal = facilityIslamicRentalRenewal;
	}

	public void setFacilityIslamicSecurityDeposit(
			OBFacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit) {
		this.facilityIslamicSecurityDeposit = facilityIslamicSecurityDeposit;
	}

	public void setFacilityMessages(Set facilityMessages) {
		this.facilityMessages = facilityMessages;
	}

	/**
	 * @param facilityIslamicMultiTierFinancingSet
	 *            the facilityRelationshipSet to set
	 */
	public void setFacilityMultiTierFinancingSet(
			Set facilityMultiTierFinancingSet) {
		this.facilityMultiTierFinancingSet = facilityMultiTierFinancingSet;
	}

	/**
	 * @param facilityOfficerSet
	 *            the facilityOfficerSet to set
	 */
	public void setFacilityOfficerSet(Set facilityOfficerSet) {
		this.facilityOfficerSet = facilityOfficerSet;
	}

	/**
	 * @param facilityPayment
	 *            the facilityPayment to set
	 */
	public void setFacilityPayment(IFacilityPayment facilityPayment) {
		this.facilityPayment = facilityPayment;
	}

	public void setFacilityReductions(Set facilityReductions) {
		this.facilityReductions = facilityReductions;
	}

	/**
	 * @param facilityRelationshipSet
	 *            the facilityRelationshipSet to set
	 */
	public void setFacilityRelationshipSet(Set facilityRelationshipSet) {
		this.facilityRelationshipSet = facilityRelationshipSet;
	}

	/**
	 * @param floorPledgedLimitAmount
	 *            the floorPledgedLimitAmount to set
	 */
	public void setFloorPledgedLimitAmount(Amount floorPledgedLimitAmount) {
		this.floorPledgedLimitAmount = floorPledgedLimitAmount;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param intInSuspense
	 *            the intInSuspense to set
	 */
	public void setIntInSuspense(BigDecimal intInSuspense) {
		this.intInSuspense = intInSuspense;
	}

	/**
	 * @param isoReferralNumber
	 *            the isoReferralNumber to set
	 */
	public void setIsoReferralNumber(String isoReferralNumber) {
		this.isoReferralNumber = isoReferralNumber;
	}

	/**
	 * @param lastMaintenanceDate
	 *            the lastMaintenanceDate to set
	 */
	public void setLastMaintenanceDate(Date lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}

	/**
	 * @param lawyerCodeCategoryCode
	 *            the lawyerCodeCategoryCode to set
	 */
	public void setLawyerCodeCategoryCode(String lawyerCodeCategoryCode) {
		this.lawyerCodeCategoryCode = lawyerCodeCategoryCode;
	}

	/**
	 * @param lawyerCodeEntryCode
	 *            the lawyerCodeEntryCode to set
	 */
	public void setLawyerCodeEntryCode(String lawyerCodeEntryCode) {
		this.lawyerCodeEntryCode = lawyerCodeEntryCode;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(ILimit limit) {
		this.limit = limit;
	}

	/**
	 * @param mainFacility
	 *            the mainFacility to set
	 */
	public void setMainFacility(boolean mainFacility) {
		this.mainFacility = mainFacility;
	}

	/**
	 * @param mainFacilityAaNumber
	 *            the mainFacilityAaNumber to set
	 */
	public void setMainFacilityAaNumber(String mainFacilityAaNumber) {
		this.mainFacilityAaNumber = mainFacilityAaNumber;
	}

	/**
	 * @param mainFacilityCode
	 *            the mainFacilityCode to set
	 */
	public void setMainFacilityCode(String mainFacilityCode) {
		this.mainFacilityCode = mainFacilityCode;
	}

	/**
	 * @param mainFacilitySequenceNumber
	 *            the mainFacilitySequenceNumber to set
	 */
	public void setMainFacilitySequenceNumber(Long mainFacilitySequenceNumber) {
		this.mainFacilitySequenceNumber = mainFacilitySequenceNumber;
	}

	/**
	 * @param odExcessRateVar
	 *            the odExcessRateVar to set
	 */
	public void setOdExcessRateVar(Double odExcessRateVar) {
		this.odExcessRateVar = odExcessRateVar;
	}

	/**
	 * @param odExcessRateVarCode
	 *            the odExcessRateVarCode to set
	 */
	public void setOdExcessRateVarCode(String odExcessRateVarCode) {
		this.odExcessRateVarCode = odExcessRateVarCode;
	}

	/**
	 * @param parValueShares
	 *            the parValueShares to set
	 */
	public void setParValueShares(String parValueShares) {
		this.parValueShares = parValueShares;
	}

	/**
	 * @param productPackageCodeCategoryCode
	 *            the productPackageCodeCategoryCode to set
	 */
	public void setProductPackageCodeCategoryCode(
			String productPackageCodeCategoryCode) {
		this.productPackageCodeCategoryCode = productPackageCodeCategoryCode;
	}

	/**
	 * @param productPackageCodeEntryCode
	 *            the productPackageCodeEntryCode to set
	 */
	public void setProductPackageCodeEntryCode(
			String productPackageCodeEntryCode) {
		this.productPackageCodeEntryCode = productPackageCodeEntryCode;
	}

	/**
	 * @param refinanceFromBankCategoryCode
	 *            the refinanceFromBankCategoryCode to set
	 */
	public void setRefinanceFromBankCategoryCode(
			String refinanceFromBankCategoryCode) {
		this.refinanceFromBankCategoryCode = refinanceFromBankCategoryCode;
	}

	/**
	 * @param refinanceFromBankEntryCode
	 *            the refinanceFromBankEntryCode to set
	 */
	public void setRefinanceFromBankEntryCode(String refinanceFromBankEntryCode) {
		this.refinanceFromBankEntryCode = refinanceFromBankEntryCode;
	}

	/**
	 * @param requiredSecurityCoverage
	 *            the requiredSecurityCoverage to set
	 */
	public void setRequiredSecurityCoverage(String requiredSecurityCoverage) {
		this.requiredSecurityCoverage = requiredSecurityCoverage;
	}

	/**
	 * @param retentionPeriod
	 *            the retentionPeriod to set
	 */
	public void setRetentionPeriod(Short retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}

	/**
	 * @param retentionPeriodCode
	 *            the retentionPeriodCode to set
	 */
	public void setRetentionPeriodCode(String retentionPeriodCode) {
		this.retentionPeriodCode = retentionPeriodCode;
	}

	/**
	 * @param retentionSumAmount
	 *            the retentionSumAmount to set
	 */
	public void setRetentionSumAmount(Amount retentionSumAmount) {
		this.retentionSumAmount = retentionSumAmount;
	}

	/**
	 * @param revolvingIndicator
	 *            the revolvingIndicator to set
	 */
	public void setRevolvingIndicator(String revolvingIndicator) {
		this.revolvingIndicator = revolvingIndicator;
	}

	/**
	 * @param revolvingOnCriteriaIndicator
	 *            the revolvingOnCriteriaIndicator to set
	 */
	public void setRevolvingOnCriteriaIndicator(
			String revolvingOnCriteriaIndicator) {
		this.revolvingOnCriteriaIndicator = revolvingOnCriteriaIndicator;
	}

	/**
	 * @param solicitorName
	 *            the solicitorName to set
	 */
	public void setSolicitorName(String solicitorName) {
		this.solicitorName = solicitorName;
	}

	/**
	 * @param solicitorReference
	 *            the solicitorReference to set
	 */
	public void setSolicitorReference(String solicitorReference) {
		this.solicitorReference = solicitorReference;
	}

	/**
	 * @param specProvision
	 *            the specProvision to set
	 */
	public void setSpecProvision(BigDecimal specProvision) {
		this.specProvision = specProvision;
	}

	/**
	 * @param standbyLine
	 *            the standbyLine to set
	 */
	public void setStandbyLine(String standbyLine) {
		this.standbyLine = standbyLine;
	}

	/**
	 * @param standByLineFacilityCode
	 *            the standByLineFacilityCode to set
	 */
	public void setStandByLineFacilityCode(String standByLineFacilityCode) {
		this.standByLineFacilityCode = standByLineFacilityCode;
	}

	/**
	 * @param standByLineFacilityCodeSequence
	 *            the standByLineFacilityCodeSequence to set
	 */
	public void setStandByLineFacilityCodeSequence(
			Long standByLineFacilityCodeSequence) {
		this.standByLineFacilityCodeSequence = standByLineFacilityCodeSequence;
	}

	public void setToBeCancelledInd(String toBeCancelledInd) {
		this.toBeCancelledInd = toBeCancelledInd;
	}

}
