package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;

public interface IFacilityMaster extends Serializable {
	public String getAcfNo();

	public Amount getActualPledgedLimitAmount();

	public Boolean getAllowIncentive();

	public String getAlternateRate();

	public String getApplicationSourceCategoryCode();

	public String getApplicationSourceEntryCode();

	public Integer getAvailPeriodInDays();

	public Integer getAvailPeriodInMonths();

	public String getCalFirstInstlDate();

	public Date getCgcBnmApprovedDate();

	public String getCreditLineIndicator();

	public Date getDateInstructed();

	public String getDealerNumberOrLppCodeCategoryCode();

	public String getDealerNumberOrLppCodeEntryCode();

	public String getDepartmentCodeCategoryCode();

	public String getDepartmentCodeEntryCode();

	public String getDisbursementManner();

	public Amount getDrawingLimitAmount();

	public Amount getEcofAdministrationCostAmount();

	public Double getEcofRate();

	public Double getEcofVariance();

	public String getEcofVarianceCode();

	public Boolean getEffectiveCostOfFund();

	public Date getFacilityAvailDate();

	public String getFacilityAvailPeriod();

	/**
	 * @return the facilityBnmCodes
	 */
	public IFacilityBnmCodes getFacilityBnmCodes();

	public String getFacilityCurrencyCode();

	public IFacilityFeeCharge getFacilityFeeCharge();

	public IFacilityGeneral getFacilityGeneral();

	public Set getFacilityIncrementals();

	/**
	 * @return the facilityInsuranceSet
	 * @see IFacilityInsurance
	 */
	public Set getFacilityInsuranceSet();

	public IFacilityInterest getFacilityInterest();

	/**
	 * @return the facilityIslamicBbaVariPackage
	 */
	public IFacilityIslamicBbaVariPackage getFacilityIslamicBbaVariPackage();

	/**
	 * @return the facilityIslamicMaster
	 */
	public IFacilityIslamicMaster getFacilityIslamicMaster();

	public OBFacilityIslamicRentalRenewal getFacilityIslamicRentalRenewal();

	public OBFacilityIslamicSecurityDeposit getFacilityIslamicSecurityDeposit();

	public Set getFacilityMessages();

	/**
	 * @return the facilityMultiTierFinancing
	 */
	public Set getFacilityMultiTierFinancingSet();

	/**
	 * @return the facilityOfficerSet
	 * @see IFacilityOfficer
	 */
	public Set getFacilityOfficerSet();

	public IFacilityPayment getFacilityPayment();

	public Set getFacilityReductions();

	/**
	 * @return the facilityRelationshipSet
	 * @see IFacilityRelationship
	 */
	public Set getFacilityRelationshipSet();

	public Amount getFloorPledgedLimitAmount();

	public long getId();

	public BigDecimal getIntInSuspense();

	public String getIsoReferralNumber();

	public Date getLastMaintenanceDate();

	public String getLawyerCodeCategoryCode();

	public String getLawyerCodeEntryCode();

	public Integer getLevel();

	public ILimit getLimit();

	public boolean getMainFacility();

	public String getMainFacilityAaNumber();

	public String getMainFacilityCode();

	public Long getMainFacilitySequenceNumber();

	public Double getOdExcessRateVar();

	public String getOdExcessRateVarCode();

	public String getParValueShares();

	public String getProductPackageCodeCategoryCode();

	public String getProductPackageCodeEntryCode();

	public String getRefinanceFromBankCategoryCode();

	public String getRefinanceFromBankEntryCode();

	public String getRequiredSecurityCoverage();  //Shiv 190911

	public Short getRetentionPeriod();

	public String getRetentionPeriodCode();

	public Amount getRetentionSumAmount();

	public String getRevolvingIndicator();

	public String getRevolvingOnCriteriaIndicator();

	public String getSolicitorName();

	public String getSolicitorReference();

	public BigDecimal getSpecProvision();

	public String getStandbyLine();

	public String getStandByLineFacilityCode();

	public Long getStandByLineFacilityCodeSequence();

	public Boolean isAltSchedule();

	public void setAcfNo(String acfNo);

	public void setActualPledgedLimitAmount(Amount actualPledgedLimitAmount);

	public void setAllowIncentive(Boolean allowIncentive);

	public void setAlternateRate(String alternateRate);

	public void setAltSchedule(Boolean altSchedule);

	public void setApplicationSourceCategoryCode(String applicationSourceCategoryCode);

	public void setApplicationSourceEntryCode(String applicationSourceEntryCode);

	public void setAvailPeriodInDays(Integer availPeriodInDays);

	public void setAvailPeriodInMonths(Integer availPeriodInMonths);

	public void setCalFirstInstlDate(String calFirstInstlDate);

	public void setCgcBnmApprovedDate(Date cgcBnmApprovedDate);

	public void setCreditLineIndicator(String creditLineIndicator);

	public void setDateInstructed(Date dateInstructed);

	public void setDealerNumberOrLppCodeCategoryCode(String dealerNumberOrLppCodeCategoryCode);

	public void setDealerNumberOrLppCodeEntryCode(String dealerNumberOrLppCodeEntryCode);

	public void setDepartmentCodeCategoryCode(String departmentCodeCategoryCode);

	public void setDepartmentCodeEntryCode(String departmentCodeEntryCode);

	public void setDisbursementManner(String disbursementManner);

	public void setDrawingLimitAmount(Amount drawingLimitAmount);

	public void setEcofAdministrationCostAmount(Amount ecofAdministrationCostAmount);

	public void setEcofRate(Double ecofRate);

	public void setEcofVariance(Double ecofVariance);

	public void setEcofVarianceCode(String ecofVarianceCode);

	public void setEffectiveCostOfFund(Boolean effectiveCostOfFund);

	public void setFacilityAvailDate(Date facilityAvailDate);

	public void setFacilityAvailPeriod(String facilityAvailPeriod);

	/**
	 * @param facilityBnmCodes the facilityBnmCodes to set
	 */
	public void setFacilityBnmCodes(IFacilityBnmCodes facilityBnmCodes);

	public void setFacilityCurrencyCode(String facilityCurrencyCode);

	public void setFacilityFeeCharge(IFacilityFeeCharge facilityFeeCharge);

	public void setFacilityGeneral(IFacilityGeneral facilityGeneral);

	public void setFacilityIncrementals(Set facilityIncrementals);

	/**
	 * @param facilityInsuranceSet the facilityInsuranceSet to set
	 */
	public void setFacilityInsuranceSet(Set facilityInsuranceSet);

	public void setFacilityInterest(IFacilityInterest facilityInterest);

	/**
	 * @param facilityIslamicBbaVariPackage the facilityIslamicBbaVariPackage to
	 *        set
	 */
	public void setFacilityIslamicBbaVariPackage(IFacilityIslamicBbaVariPackage facilityIslamicBbaVariPackage);

	/**
	 * @param facilityIslamicMaster the facilityIslamicMaster to set
	 */
	public void setFacilityIslamicMaster(IFacilityIslamicMaster facilityIslamicMaster);

	public void setFacilityIslamicRentalRenewal(OBFacilityIslamicRentalRenewal facilityIslamicRentalRenewal);

	public void setFacilityIslamicSecurityDeposit(OBFacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit);

	public void setFacilityMessages(Set facilityMessages);

	/**
	 * @param facilityIslamicMultiTierFinancingSet the facilityRelationshipSet
	 *        to set
	 */
	public void setFacilityMultiTierFinancingSet(Set facilityMultiTierFinancingSet);

	/**
	 * @param facilityOfficerSet the facilityOfficerSet to set
	 */
	public void setFacilityOfficerSet(Set facilityOfficerSet);

	public void setFacilityPayment(IFacilityPayment facilityPayment);

	public void setFacilityReductions(Set facilityReductions);

	/**
	 * @param facilityRelationshipSet the facilityRelationshipSet to set
	 */
	public void setFacilityRelationshipSet(Set facilityRelationshipSet);

	public void setFloorPledgedLimitAmount(Amount floorPledgedLimitAmount);

	public void setId(long id);

	public void setIntInSuspense(BigDecimal intInSuspense);

	public void setIsoReferralNumber(String isoReferralNumber);

	public void setLastMaintenanceDate(Date lastMaintenanceDate);

	public void setLawyerCodeCategoryCode(String lawyerCodeCategoryCode);

	public void setLawyerCodeEntryCode(String lawyerCodeEntryCode);

	public void setLevel(Integer level);

	public void setLimit(ILimit limit);

	public void setMainFacility(boolean mainFacility);

	public void setMainFacilityAaNumber(String mainFacilityAaNumber);

	public void setMainFacilityCode(String mainFacilityCode);

	public void setMainFacilitySequenceNumber(Long mainFacilitySequenceNumber);

	public void setOdExcessRateVar(Double odExcessRateVar);

	public void setOdExcessRateVarCode(String odExcessRateVarCode);

	public void setParValueShares(String parValueShares);

	public void setProductPackageCodeCategoryCode(String productPackageCodeCategoryCode);

	public void setProductPackageCodeEntryCode(String productPackageCodeEntryCode);

	public void setRefinanceFromBankCategoryCode(String refinanceFromBankCategoryCode);

	public void setRefinanceFromBankEntryCode(String refinanceFromBankEntryCode);

	public void setRequiredSecurityCoverage(String requiredSecurityCoverage);  //Shiv 190911

	public void setRetentionPeriod(Short retentionPeriod);

	public void setRetentionPeriodCode(String retentionPeriodCode);

	public void setRetentionSumAmount(Amount retentionSumAmount);

	public void setRevolvingIndicator(String revolvingIndicator);

	public void setRevolvingOnCriteriaIndicator(String revolvingOnCriteriaIndicator);

	public void setSolicitorName(String solicitorName);

	public void setSolicitorReference(String solicitorReference);

	public void setSpecProvision(BigDecimal specProvision);

	public void setStandbyLine(String standbyLine);

	public void setStandByLineFacilityCode(String standByLineFacilityCode);

	public void setStandByLineFacilityCodeSequence(Long standByLineFacilityCodeSequence);
}
