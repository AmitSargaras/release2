/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBLimit.java,v 1.28 2005/10/14 05:11:46 whuang Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;

/**
 * This class represents a Limit record.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.28 $
 * @since $Date: 2005/10/14 05:11:46 $ Tag: $Name: $
 */
public class OBLimit implements ILimit {

	private static final long serialVersionUID = 2191655370803813796L;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private String limitRef = null;

	private String losLimitRef = null;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private String limitProfileReferenceNumber = null;

	private String facilityCode = null;

	private long facilitySequence;
	
	private String acfNo;

	private long outerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private long outerLimitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private String outerLimitRef = null;

	private IBookingLocation bookingLocation = null;

	private String productDesc = null;

	private String limitType = null;

	private Amount approvedLimitAmount = null;

	private Amount activatedLimitAmount = null;

	private Amount operationalLimit = null;

	private String limitSecuredType = null;

	private String limitStatus = null;

	private Date limitExpiryDate = null;

	private Long limitTenor = null;

	private String limitTenorUnit = null;

	private float limitInterest = 0;

	private String limitCMMSN = null;

	private String limitFee = null;

	private String limitConditionText = null;

	private boolean limitAdviseInd = false;

	private boolean limitCommittedInd = false;

	private boolean sharedLimitInd = false;

	private ICollateralAllocation[] collateralAllocations = null;

	private Set collateralAllocationsSet;

	private ICoBorrowerLimit[] coBorrowerLimits = null;

	private Set coBorrowerLimitsSet;

	private long versionTime = 0;

	private String requiredSecurityCoverage = null;  // Shiv 190911

	private float actualSecurityCoverage = ICMSConstant.FLOAT_INVALID_VALUE;

	private Amount actualSecCoverageAmt = null;

	private Amount actualSecCoverageOMVAmt = null;

	private Amount actualSecCoverageFSVAmt = null;

	private ILimitSysXRef[] limitSysXRefs = null;

	private Set limitSysXRefsSet;
	
	private ILimitCovenant[] limitCovenant = null;

	private Set limitCovenantSet;

	private boolean existingInd = false;

	private String hostStatus = null;

	private boolean limitActivatedInd = false;

	private boolean isLimitZerorised;

	private String zerorisedReason;

	private Date zerorisedDate;

	private boolean isDAPError;

	private boolean isChanged;

	private boolean isZerorisedChanged;

	private boolean isZerorisedDateChanged;

	private boolean isZerorisedReasonChanged;

	private String productGroupNum;

	private String productGroup;

	private String productDescNum;

	private String limitTenorUnitNum;

	private Amount drawingLimitAmount = null;

	private Amount outstandingAmount = null;

	private String limitDesc;

	private String limitDescNum;

	private String sourceId;

	private String loanType;

	private String facilityDesc;

	private String facilityDescNum;

	private Date lastUpdatedDate;

	private Double interestRate;

	private String lEReference = null;

	private String accountType;

	private Integer innerLimitOrdering;

	private Long omnibusEnvelopeId;

	private String omnibusEnvelopeName;

    private String disbursedInd;    
  //Shiv
    private String facilityName;
    
    private String facilityType;
    
    private String facilityCat;
    
    private String releasableAmount;
    
    private String adhocLmtAmount;
	
	private String isAdhocToSum;
	
	private String guarantee;
	
	private String subPartyName;
	
	private String subFacilityName;
	
	private String liabilityID;
	
	private String totalReleasedAmount;
	
	private String mainFacilityId;
	
	private char isFromCamonlineReq;
	
	private String syndicateLoan;
	
	private String projectLoan;
	
	private String infaProjectFlag;
	
	private String whlmreupSCOD;
	
	private Date scodDate;
	
	private String remarksSCOD;
	
	private String exstAssetClass;
	
	private Date exstAssClassDate;
	
	private String revisedAssetClass;
	
	private Date revsdAssClassDate;
	
	private Date actualCommOpsDate;
	
	private String lelvelDelaySCOD;
	
	private String principalInterestSchFlag;
	
	private String projectDelayedFlag;
	
	private String reasonLevelOneDelay;
	
	private String chngInRepaySchedule;
	
	private Date escodLevelOneDelayDate;
	
	private Date escodLevelSecondDelayDate;
	
	private String reasonLevelThreeDelay;
	
	private Date escodLevelThreeDelayDate;
	
	private String legalOrArbitrationLevel2Flag;
	
	private String chngOfOwnPrjFlagNonInfraLevel2;
	
	private String reasonBeyondPromoterLevel2Flag;
	
	private String chngOfProjScopeNonInfraLevel2;
	
	private String chngOfOwnPrjFlagInfraLevel2;
	
	private String chngOfProjScopeInfraLevel2;
	
	private String legalOrArbitrationLevel3Flag;
	
	private String chngOfOwnPrjFlagNonInfraLevel3;
	
	private String reasonBeyondPromoterLevel3Flag;
	
	private String chngOfProjScopeNonInfraLevel3;
	
	private String chngOfOwnPrjFlagInfraLevel3;
	
	private String chngOfProjScopeInfraLevel3;
	
	private String leaglOrArbiProceed;
	
	private String  detailsRsnByndPro;
	
	private String recvPriorOfSCOD;
	
	
	private String reasonLevelTwoDelay;
	
	private String promotersCapRunFlag;
	
	private String changeInScopeBeforeSCODFlag;
	
	private String promotersHoldEquityFlag;
	
	private String costOverrunOrg25CostViabilityFlag;
	
	private String hasProjectViabReAssFlag;
	
	private String projectViabReassesedFlag;
	
	private Date revsedESCODStpDate;
	
	private String exstAssetClassL1;
	
	private Date exstAssClassDateL1;
	
	private String exstAssetClassL2;
	
	private Date exstAssClassDateL2;
	
	private String exstAssetClassL3;
	
	private Date exstAssClassDateL3;
	
	private String revisedAssetClassL1;
	
	
	private Date revsdAssClassDateL1;
	
	private String revisedAssetClass_L2;
	private Date revsdAssClassDate_L2;
	private String revisedAssetClass_L3;
	
	private Date revsdAssClassDate_L3;
	
	private String projectDelayedFlagL2;
	
	private String projectDelayedFlagL3;
	
	private String leaglOrArbiProceedLevel3;
	
	private String detailsRsnByndProLevel3;
	
	private String chngInRepayScheduleL2;
	
	private String chngInRepayScheduleL3;
	
	private String promotersCapRunFlagL2;
	
	private String promotersCapRunFlagL3;
	
	private String changeInScopeBeforeSCODFlagL2;
	
	private String changeInScopeBeforeSCODFlagL3;
	
	private String promotersHoldEquityFlagL2;
	
	private String promotersHoldEquityFlagL3;
	
	private String costOverrunOrg25CostViabilityFlagL2;
	
	private String costOverrunOrg25CostViabilityFlagL3;
	
	private String hasProjectViabReAssFlagL2;
	
	private String hasProjectViabReAssFlagL3;
	
	private String projectViabReassesedFlagL2;
	
	private String projectViabReassesedFlagL3;
	
	private Date revsedESCODStpDateL2;
	
	private Date revsedESCODStpDateL3;
	
	private String projectFinance;
	private String bankingArrangement;
	
	private String clauseAsPerPolicy;

	private String isDPRequired;


	
	private List<IFacilityCoBorrowerDetails> coBorrowerDetailsList;
	
	private Long tenor;
	private String tenorUnit;
	private Double margin;
	private String tenorDesc;
	private String putCallOption;
	private Date loanAvailabilityDate;
	private Date optionDate;
	private String riskType;
	
	public String getExstAssetClassL1() {
		return exstAssetClassL1;
	}

	public void setExstAssetClassL1(String exstAssetClassL1) {
		this.exstAssetClassL1 = exstAssetClassL1;
	}

	public Date getExstAssClassDateL1() {
		return exstAssClassDateL1;
	}

	public void setExstAssClassDateL1(Date exstAssClassDateL1) {
		this.exstAssClassDateL1 = exstAssClassDateL1;
	}

	public String getExstAssetClassL2() {
		return exstAssetClassL2;
	}

	public void setExstAssetClassL2(String exstAssetClassL2) {
		this.exstAssetClassL2 = exstAssetClassL2;
	}

	public Date getExstAssClassDateL2() {
		return exstAssClassDateL2;
	}

	public void setExstAssClassDateL2(Date exstAssClassDateL2) {
		this.exstAssClassDateL2 = exstAssClassDateL2;
	}

	public String getExstAssetClassL3() {
		return exstAssetClassL3;
	}

	public void setExstAssetClassL3(String exstAssetClassL3) {
		this.exstAssetClassL3 = exstAssetClassL3;
	}

	public Date getExstAssClassDateL3() {
		return exstAssClassDateL3;
	}

	public void setExstAssClassDateL3(Date exstAssClassDateL3) {
		this.exstAssClassDateL3 = exstAssClassDateL3;
	}

	public String getRevisedAssetClassL1() {
		return revisedAssetClassL1;
	}

	public void setRevisedAssetClassL1(String revisedAssetClassL1) {
		this.revisedAssetClassL1 = revisedAssetClassL1;
	}

	public Date getRevsdAssClassDateL1() {
		return revsdAssClassDateL1;
	}

	public void setRevsdAssClassDateL1(Date revsdAssClassDateL1) {
		this.revsdAssClassDateL1 = revsdAssClassDateL1;
	}

	public String getRevisedAssetClass_L2() {
		return revisedAssetClass_L2;
	}

	public void setRevisedAssetClass_L2(String revisedAssetClass_L2) {
		this.revisedAssetClass_L2 = revisedAssetClass_L2;
	}

	public Date getRevsdAssClassDate_L2() {
		return revsdAssClassDate_L2;
	}

	public void setRevsdAssClassDate_L2(Date revsdAssClassDate_L2) {
		this.revsdAssClassDate_L2 = revsdAssClassDate_L2;
	}

	public String getRevisedAssetClass_L3() {
		return revisedAssetClass_L3;
	}

	public void setRevisedAssetClass_L3(String revisedAssetClass_L3) {
		this.revisedAssetClass_L3 = revisedAssetClass_L3;
	}

	public Date getRevsdAssClassDate_L3() {
		return revsdAssClassDate_L3;
	}

	public void setRevsdAssClassDate_L3(Date revsdAssClassDate_L3) {
		this.revsdAssClassDate_L3 = revsdAssClassDate_L3;
	}

	public String getProjectDelayedFlagL2() {
		return projectDelayedFlagL2;
	}

	public void setProjectDelayedFlagL2(String projectDelayedFlagL2) {
		this.projectDelayedFlagL2 = projectDelayedFlagL2;
	}

	public String getProjectDelayedFlagL3() {
		return projectDelayedFlagL3;
	}

	public void setProjectDelayedFlagL3(String projectDelayedFlagL3) {
		this.projectDelayedFlagL3 = projectDelayedFlagL3;
	}

	public String getLeaglOrArbiProceedLevel3() {
		return leaglOrArbiProceedLevel3;
	}

	public void setLeaglOrArbiProceedLevel3(String leaglOrArbiProceedLevel3) {
		this.leaglOrArbiProceedLevel3 = leaglOrArbiProceedLevel3;
	}

	public String getDetailsRsnByndProLevel3() {
		return detailsRsnByndProLevel3;
	}

	public void setDetailsRsnByndProLevel3(String detailsRsnByndProLevel3) {
		this.detailsRsnByndProLevel3 = detailsRsnByndProLevel3;
	}

	public String getChngInRepayScheduleL2() {
		return chngInRepayScheduleL2;
	}

	public void setChngInRepayScheduleL2(String chngInRepayScheduleL2) {
		this.chngInRepayScheduleL2 = chngInRepayScheduleL2;
	}

	public String getChngInRepayScheduleL3() {
		return chngInRepayScheduleL3;
	}

	public void setChngInRepayScheduleL3(String chngInRepayScheduleL3) {
		this.chngInRepayScheduleL3 = chngInRepayScheduleL3;
	}

	public String getPromotersCapRunFlagL2() {
		return promotersCapRunFlagL2;
	}

	public void setPromotersCapRunFlagL2(String promotersCapRunFlagL2) {
		this.promotersCapRunFlagL2 = promotersCapRunFlagL2;
	}

	public String getPromotersCapRunFlagL3() {
		return promotersCapRunFlagL3;
	}

	public void setPromotersCapRunFlagL3(String promotersCapRunFlagL3) {
		this.promotersCapRunFlagL3 = promotersCapRunFlagL3;
	}

	public String getChangeInScopeBeforeSCODFlagL2() {
		return changeInScopeBeforeSCODFlagL2;
	}

	public void setChangeInScopeBeforeSCODFlagL2(String changeInScopeBeforeSCODFlagL2) {
		this.changeInScopeBeforeSCODFlagL2 = changeInScopeBeforeSCODFlagL2;
	}

	public String getChangeInScopeBeforeSCODFlagL3() {
		return changeInScopeBeforeSCODFlagL3;
	}

	public void setChangeInScopeBeforeSCODFlagL3(String changeInScopeBeforeSCODFlagL3) {
		this.changeInScopeBeforeSCODFlagL3 = changeInScopeBeforeSCODFlagL3;
	}

	public String getPromotersHoldEquityFlagL2() {
		return promotersHoldEquityFlagL2;
	}

	public void setPromotersHoldEquityFlagL2(String promotersHoldEquityFlagL2) {
		this.promotersHoldEquityFlagL2 = promotersHoldEquityFlagL2;
	}

	public String getPromotersHoldEquityFlagL3() {
		return promotersHoldEquityFlagL3;
	}

	public void setPromotersHoldEquityFlagL3(String promotersHoldEquityFlagL3) {
		this.promotersHoldEquityFlagL3 = promotersHoldEquityFlagL3;
	}

	public String getCostOverrunOrg25CostViabilityFlagL2() {
		return costOverrunOrg25CostViabilityFlagL2;
	}

	public void setCostOverrunOrg25CostViabilityFlagL2(String costOverrunOrg25CostViabilityFlagL2) {
		this.costOverrunOrg25CostViabilityFlagL2 = costOverrunOrg25CostViabilityFlagL2;
	}

	public String getCostOverrunOrg25CostViabilityFlagL3() {
		return costOverrunOrg25CostViabilityFlagL3;
	}

	public void setCostOverrunOrg25CostViabilityFlagL3(String costOverrunOrg25CostViabilityFlagL3) {
		this.costOverrunOrg25CostViabilityFlagL3 = costOverrunOrg25CostViabilityFlagL3;
	}

	public String getHasProjectViabReAssFlagL2() {
		return hasProjectViabReAssFlagL2;
	}

	public void setHasProjectViabReAssFlagL2(String hasProjectViabReAssFlagL2) {
		this.hasProjectViabReAssFlagL2 = hasProjectViabReAssFlagL2;
	}

	public String getHasProjectViabReAssFlagL3() {
		return hasProjectViabReAssFlagL3;
	}

	public void setHasProjectViabReAssFlagL3(String hasProjectViabReAssFlagL3) {
		this.hasProjectViabReAssFlagL3 = hasProjectViabReAssFlagL3;
	}

	public String getProjectViabReassesedFlagL2() {
		return projectViabReassesedFlagL2;
	}

	public void setProjectViabReassesedFlagL2(String projectViabReassesedFlagL2) {
		this.projectViabReassesedFlagL2 = projectViabReassesedFlagL2;
	}

	public String getProjectViabReassesedFlagL3() {
		return projectViabReassesedFlagL3;
	}

	public void setProjectViabReassesedFlagL3(String projectViabReassesedFlagL3) {
		this.projectViabReassesedFlagL3 = projectViabReassesedFlagL3;
	}

	public Date getRevsedESCODStpDateL2() {
		return revsedESCODStpDateL2;
	}

	public void setRevsedESCODStpDateL2(Date revsedESCODStpDateL2) {
		this.revsedESCODStpDateL2 = revsedESCODStpDateL2;
	}

	public Date getRevsedESCODStpDateL3() {
		return revsedESCODStpDateL3;
	}

	public void setRevsedESCODStpDateL3(Date revsedESCODStpDateL3) {
		this.revsedESCODStpDateL3 = revsedESCODStpDateL3;
	}

	public String getlEReference() {
		return lEReference;
	}

	public void setlEReference(String lEReference) {
		this.lEReference = lEReference;
	}

	public String getProjectLoan() {
		return projectLoan;
	}

	public void setProjectLoan(String projectLoan) {
		this.projectLoan = projectLoan;
	}

	public String getInfaProjectFlag() {
		return infaProjectFlag;
	}

	public void setInfaProjectFlag(String infaProjectFlag) {
		this.infaProjectFlag = infaProjectFlag;
	}

	public String getWhlmreupSCOD() {
		return whlmreupSCOD;
	}

	public void setWhlmreupSCOD(String whlmreupSCOD) {
		this.whlmreupSCOD = whlmreupSCOD;
	}

	public Date getScodDate() {
		return scodDate;
	}

	public void setScodDate(Date scodDate) {
		this.scodDate = scodDate;
	}

	public String getRemarksSCOD() {
		return remarksSCOD;
	}

	public void setRemarksSCOD(String remarksSCOD) {
		this.remarksSCOD = remarksSCOD;
	}

	public String getExstAssetClass() {
		return exstAssetClass;
	}

	public void setExstAssetClass(String exstAssetClass) {
		this.exstAssetClass = exstAssetClass;
	}

	public Date getExstAssClassDate() {
		return exstAssClassDate;
	}

	public void setExstAssClassDate(Date exstAssClassDate) {
		this.exstAssClassDate = exstAssClassDate;
	}

	public String getRevisedAssetClass() {
		return revisedAssetClass;
	}

	public void setRevisedAssetClass(String revisedAssetClass) {
		this.revisedAssetClass = revisedAssetClass;
	}

	public Date getRevsdAssClassDate() {
		return revsdAssClassDate;
	}

	public void setRevsdAssClassDate(Date revsdAssClassDate) {
		this.revsdAssClassDate = revsdAssClassDate;
	}

	public Date getActualCommOpsDate() {
		return actualCommOpsDate;
	}

	public void setActualCommOpsDate(Date actualCommOpsDate) {
		this.actualCommOpsDate = actualCommOpsDate;
	}

	public String getLelvelDelaySCOD() {
		return lelvelDelaySCOD;
	}

	public void setLelvelDelaySCOD(String lelvelDelaySCOD) {
		this.lelvelDelaySCOD = lelvelDelaySCOD;
	}

	public String getPrincipalInterestSchFlag() {
		return principalInterestSchFlag;
	}

	public void setPrincipalInterestSchFlag(String principalInterestSchFlag) {
		this.principalInterestSchFlag = principalInterestSchFlag;
	}

	public String getProjectDelayedFlag() {
		return projectDelayedFlag;
	}

	public void setProjectDelayedFlag(String projectDelayedFlag) {
		this.projectDelayedFlag = projectDelayedFlag;
	}

	public String getReasonLevelOneDelay() {
		return reasonLevelOneDelay;
	}

	public void setReasonLevelOneDelay(String reasonLevelOneDelay) {
		this.reasonLevelOneDelay = reasonLevelOneDelay;
	}

	public String getChngInRepaySchedule() {
		return chngInRepaySchedule;
	}

	public void setChngInRepaySchedule(String chngInRepaySchedule) {
		this.chngInRepaySchedule = chngInRepaySchedule;
	}

	public Date getEscodLevelOneDelayDate() {
		return escodLevelOneDelayDate;
	}

	public void setEscodLevelOneDelayDate(Date escodLevelOneDelayDate) {
		this.escodLevelOneDelayDate = escodLevelOneDelayDate;
	}

	public Date getEscodLevelSecondDelayDate() {
		return escodLevelSecondDelayDate;
	}

	public void setEscodLevelSecondDelayDate(Date escodLevelSecondDelayDate) {
		this.escodLevelSecondDelayDate = escodLevelSecondDelayDate;
	}

	public String getReasonLevelThreeDelay() {
		return reasonLevelThreeDelay;
	}

	public void setReasonLevelThreeDelay(String reasonLevelThreeDelay) {
		this.reasonLevelThreeDelay = reasonLevelThreeDelay;
	}

	public Date getEscodLevelThreeDelayDate() {
		return escodLevelThreeDelayDate;
	}

	public void setEscodLevelThreeDelayDate(Date escodLevelThreeDelayDate) {
		this.escodLevelThreeDelayDate = escodLevelThreeDelayDate;
	}

	public String getLegalOrArbitrationLevel2Flag() {
		return legalOrArbitrationLevel2Flag;
	}

	public void setLegalOrArbitrationLevel2Flag(String legalOrArbitrationLevel2Flag) {
		this.legalOrArbitrationLevel2Flag = legalOrArbitrationLevel2Flag;
	}

	public String getChngOfOwnPrjFlagNonInfraLevel2() {
		return chngOfOwnPrjFlagNonInfraLevel2;
	}

	public void setChngOfOwnPrjFlagNonInfraLevel2(String chngOfOwnPrjFlagNonInfraLevel2) {
		this.chngOfOwnPrjFlagNonInfraLevel2 = chngOfOwnPrjFlagNonInfraLevel2;
	}

	public String getReasonBeyondPromoterLevel2Flag() {
		return reasonBeyondPromoterLevel2Flag;
	}

	public void setReasonBeyondPromoterLevel2Flag(String reasonBeyondPromoterLevel2Flag) {
		this.reasonBeyondPromoterLevel2Flag = reasonBeyondPromoterLevel2Flag;
	}

	public String getChngOfProjScopeNonInfraLevel2() {
		return chngOfProjScopeNonInfraLevel2;
	}

	public void setChngOfProjScopeNonInfraLevel2(String chngOfProjScopeNonInfraLevel2) {
		this.chngOfProjScopeNonInfraLevel2 = chngOfProjScopeNonInfraLevel2;
	}

	public String getChngOfOwnPrjFlagInfraLevel2() {
		return chngOfOwnPrjFlagInfraLevel2;
	}

	public void setChngOfOwnPrjFlagInfraLevel2(String chngOfOwnPrjFlagInfraLevel2) {
		this.chngOfOwnPrjFlagInfraLevel2 = chngOfOwnPrjFlagInfraLevel2;
	}

	public String getChngOfProjScopeInfraLevel2() {
		return chngOfProjScopeInfraLevel2;
	}

	public void setChngOfProjScopeInfraLevel2(String chngOfProjScopeInfraLevel2) {
		this.chngOfProjScopeInfraLevel2 = chngOfProjScopeInfraLevel2;
	}

	public String getLegalOrArbitrationLevel3Flag() {
		return legalOrArbitrationLevel3Flag;
	}

	public void setLegalOrArbitrationLevel3Flag(String legalOrArbitrationLevel3Flag) {
		this.legalOrArbitrationLevel3Flag = legalOrArbitrationLevel3Flag;
	}

	public String getChngOfOwnPrjFlagNonInfraLevel3() {
		return chngOfOwnPrjFlagNonInfraLevel3;
	}

	public void setChngOfOwnPrjFlagNonInfraLevel3(String chngOfOwnPrjFlagNonInfraLevel3) {
		this.chngOfOwnPrjFlagNonInfraLevel3 = chngOfOwnPrjFlagNonInfraLevel3;
	}

	public String getReasonBeyondPromoterLevel3Flag() {
		return reasonBeyondPromoterLevel3Flag;
	}

	public void setReasonBeyondPromoterLevel3Flag(String reasonBeyondPromoterLevel3Flag) {
		this.reasonBeyondPromoterLevel3Flag = reasonBeyondPromoterLevel3Flag;
	}

	public String getChngOfProjScopeNonInfraLevel3() {
		return chngOfProjScopeNonInfraLevel3;
	}

	public void setChngOfProjScopeNonInfraLevel3(String chngOfProjScopeNonInfraLevel3) {
		this.chngOfProjScopeNonInfraLevel3 = chngOfProjScopeNonInfraLevel3;
	}

	public String getChngOfOwnPrjFlagInfraLevel3() {
		return chngOfOwnPrjFlagInfraLevel3;
	}

	public void setChngOfOwnPrjFlagInfraLevel3(String chngOfOwnPrjFlagInfraLevel3) {
		this.chngOfOwnPrjFlagInfraLevel3 = chngOfOwnPrjFlagInfraLevel3;
	}

	public String getChngOfProjScopeInfraLevel3() {
		return chngOfProjScopeInfraLevel3;
	}

	public void setChngOfProjScopeInfraLevel3(String chngOfProjScopeInfraLevel3) {
		this.chngOfProjScopeInfraLevel3 = chngOfProjScopeInfraLevel3;
	}

	public String getLeaglOrArbiProceed() {
		return leaglOrArbiProceed;
	}

	public void setLeaglOrArbiProceed(String leaglOrArbiProceed) {
		this.leaglOrArbiProceed = leaglOrArbiProceed;
	}

	public String getDetailsRsnByndPro() {
		return detailsRsnByndPro;
	}

	public void setDetailsRsnByndPro(String detailsRsnByndPro) {
		this.detailsRsnByndPro = detailsRsnByndPro;
	}

	public String getRecvPriorOfSCOD() {
		return recvPriorOfSCOD;
	}

	public void setRecvPriorOfSCOD(String recvPriorOfSCOD) {
		this.recvPriorOfSCOD = recvPriorOfSCOD;
	}

	
	public String getReasonLevelTwoDelay() {
		return reasonLevelTwoDelay;
	}

	public void setReasonLevelTwoDelay(String reasonLevelTwoDelay) {
		this.reasonLevelTwoDelay = reasonLevelTwoDelay;
	}

	public String getPromotersCapRunFlag() {
		return promotersCapRunFlag;
	}

	public void setPromotersCapRunFlag(String promotersCapRunFlag) {
		this.promotersCapRunFlag = promotersCapRunFlag;
	}

	public String getChangeInScopeBeforeSCODFlag() {
		return changeInScopeBeforeSCODFlag;
	}

	public void setChangeInScopeBeforeSCODFlag(String changeInScopeBeforeSCODFlag) {
		this.changeInScopeBeforeSCODFlag = changeInScopeBeforeSCODFlag;
	}

	public String getPromotersHoldEquityFlag() {
		return promotersHoldEquityFlag;
	}

	public void setPromotersHoldEquityFlag(String promotersHoldEquityFlag) {
		this.promotersHoldEquityFlag = promotersHoldEquityFlag;
	}

	public String getCostOverrunOrg25CostViabilityFlag() {
		return costOverrunOrg25CostViabilityFlag;
	}

	public void setCostOverrunOrg25CostViabilityFlag(String costOverrunOrg25CostViabilityFlag) {
		this.costOverrunOrg25CostViabilityFlag = costOverrunOrg25CostViabilityFlag;
	}

	public String getHasProjectViabReAssFlag() {
		return hasProjectViabReAssFlag;
	}

	public void setHasProjectViabReAssFlag(String hasProjectViabReAssFlag) {
		this.hasProjectViabReAssFlag = hasProjectViabReAssFlag;
	}

	public String getProjectViabReassesedFlag() {
		return projectViabReassesedFlag;
	}

	public void setProjectViabReassesedFlag(String projectViabReassesedFlag) {
		this.projectViabReassesedFlag = projectViabReassesedFlag;
	}

	public Date getRevsedESCODStpDate() {
		return revsedESCODStpDate;
	}

	public void setRevsedESCODStpDate(Date revsedESCODStpDate) {
		this.revsedESCODStpDate = revsedESCODStpDate;
	}

	public void setLimitZerorised(boolean isLimitZerorised) {
		this.isLimitZerorised = isLimitZerorised;
	}

	public void setDAPError(boolean isDAPError) {
		this.isDAPError = isDAPError;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public void setZerorisedChanged(boolean isZerorisedChanged) {
		this.isZerorisedChanged = isZerorisedChanged;
	}

	public void setZerorisedDateChanged(boolean isZerorisedDateChanged) {
		this.isZerorisedDateChanged = isZerorisedDateChanged;
	}

	public void setZerorisedReasonChanged(boolean isZerorisedReasonChanged) {
		this.isZerorisedReasonChanged = isZerorisedReasonChanged;
	}

	public String getTotalReleasedAmount() {
		return totalReleasedAmount;
	}

	public void setTotalReleasedAmount(String totalReleasedAmount) {
		this.totalReleasedAmount = totalReleasedAmount;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public String getSubPartyName() {
		return subPartyName;
	}

	public void setSubPartyName(String subPartyName) {
		this.subPartyName = subPartyName;
	}

	public String getSubFacilityName() {
		return subFacilityName;
	}

	public void setSubFacilityName(String subFacilityName) {
		this.subFacilityName = subFacilityName;
	}

	public String getLiabilityID() {
		return liabilityID;
	}

	public void setLiabilityID(String liabilityID) {
		this.liabilityID = liabilityID;
	}

    public String getAdhocLmtAmount() {
		return adhocLmtAmount;
	}

	public void setAdhocLmtAmount(String adhocLmtAmount) {
		this.adhocLmtAmount = adhocLmtAmount;
	}

	public String getIsAdhocToSum() {
		return isAdhocToSum;
	}

	public void setIsAdhocToSum(String isAdhocToSum) {
		this.isAdhocToSum = isAdhocToSum;
	}

	public String getReleasableAmount() {
		return releasableAmount;
	}

	public void setReleasableAmount(String releasableAmount) {
		this.releasableAmount = releasableAmount;
	}

	public String getFacilityCat() {
		return facilityCat;
	}

	public void setFacilityCat(String facilityCat) {
		this.facilityCat = facilityCat;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilitySystem() {
		return facilitySystem;
	}

	public void setFacilitySystem(String facilitySystem) {
		this.facilitySystem = facilitySystem;
	}

	public String getFacilitySystemID() {
		return facilitySystemID;
	}

	public void setFacilitySystemID(String facilitySystemID) {
		this.facilitySystemID = facilitySystemID;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getOtherPurpose() {
		return otherPurpose;
	}

	public void setOtherPurpose(String otherPurpose) {
		this.otherPurpose = otherPurpose;
	}

	public String getIsDP() {
		return isDP;
	}

	public void setIsDP(String isDP) {
		this.isDP = isDP;
	}

	public String getRelationShipManager() {
		return relationShipManager;
	}

	public void setRelationShipManager(String relationShipManager) {
		this.relationShipManager = relationShipManager;
	}

	public String getIsReleased() {
		return isReleased;
	}

	public void setIsReleased(String isReleased) {
		this.isReleased = isReleased;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getIsSecured() {
		return isSecured;
	}

	public void setIsSecured(String isSecured) {
		this.isSecured = isSecured;
	}

	public String getIsAdhoc() {
		return isAdhoc;
	}

	public void setIsAdhoc(String isAdhoc) {
		this.isAdhoc = isAdhoc;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	private String facilitySystem;

	private String facilitySystemID;
	
	private String lineNo;
	
	private String purpose;
	
	private String otherPurpose;
	
	private String isDP;
	
	private String relationShipManager;
	
	private String isReleased;
	
	private String grade;
	
	private String isSecured;
	
	private String isAdhoc;
	
	private String currencyCode;
	
	private String purposeBoolean;
	
	private String limitRemarks;
	
	

	/**
	 * Default Constructor
	 */
	public OBLimit() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ILimit
	 */
	public OBLimit(ILimit value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	public String getAccountType() {
		return this.accountType;
	}

	public String getAcfNo() {
		return acfNo;
	}

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getActivatedLimitAmount() {
		return activatedLimitAmount;
	}

	/**
	 * Get the actual security coverage amount
	 * 
	 * @return Amount
	 */
	public Amount getActualSecCoverageAmt() {
		return actualSecCoverageAmt;
	}

	/**
	 * Get the actual security coverage amount based on FSV
	 * 
	 * @return Amount
	 */
	public Amount getActualSecCoverageFSVAmt() {
		return actualSecCoverageFSVAmt;
	}

	/**
	 * Get the actual security coverage amount based on OMV
	 * 
	 * @return Amount
	 */
	public Amount getActualSecCoverageOMVAmt() {
		return actualSecCoverageOMVAmt;
	}

	/**
	 * Get the actual security coverage percentage
	 * 
	 * @return float
	 */
	public float getActualSecurityCoverage() {
		return actualSecurityCoverage;
	}

	/**
	 * Get Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount() {
		return approvedLimitAmount;
	}

	/**
	 * Get Booking Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation() {
		return bookingLocation;
	}

	/**
	 * Get all co-borrower limits.
	 * 
	 * @return ICoBorrowerLimit[]
	 */
	public ICoBorrowerLimit[] getCoBorrowerLimits() {
		return coBorrowerLimits;
	}

	public Set getCoBorrowerLimitsSet() {
		return coBorrowerLimitsSet;
	}

	/**
	 * Get All Collateral Allocations
	 * 
	 * @return ICollateralAllocation[]
	 */
	public ICollateralAllocation[] getCollateralAllocations() {
		return collateralAllocations;
	}

	public Set getCollateralAllocationsSet() {
		return collateralAllocationsSet;
	}

	/**
	 * @return Returns the drawingLimitAmount.
	 */
	public Amount getDrawingLimitAmount() {
		return drawingLimitAmount;
	}

	// Getters

	/**
	 * Get the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @return boolean
	 */
	public boolean getExistingInd() {
		return existingInd;
	}

	/**
	 * @return the facilityCode
	 */
	public String getFacilityCode() {
		return facilityCode;
	}

	/**
	 * Get Facility Description
	 * 
	 * @return String
	 */
	public String getFacilityDesc() {
		return facilityDesc;
	}

	/**
	 * @return Returns the facilityDescNum.
	 */
	public String getFacilityDescNum() {
		return facilityDescNum;
	}

	/**
	 * @return the facilitySequence
	 */
	public long getFacilitySequence() {
		return facilitySequence;
	}

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus() {
		return hostStatus;
	}

	public Integer getInnerLimitOrdering() {
		return innerLimitOrdering;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	/**
	 * @return Returns the _isChanged.
	 */
	public boolean getIsChanged() {
		return isChanged;
	}

	/**
	 * Get if the limit encounter DAP error.
	 * 
	 * @return boolean
	 */
	public boolean getIsDAPError() {
		return isDAPError;
	}

	/**
	 * Check if inner and outer limit are of the same BCA.
	 * 
	 * @return boolean
	 */
	public boolean getIsInnerOuterSameBCA() {
		if (getOuterLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
			return getLimitProfileID() == getOuterLimitProfileID();
		}
		return true;
	}

	/**
	 * Check if the limit is zerorised.
	 * 
	 * @return boolean
	 */
	public boolean getIsLimitZerorised() {
		return isLimitZerorised;
	}

	/**
	 * @return Returns the isZerorisedChanged.
	 */
	public boolean getIsZerorisedChanged() {
		return isZerorisedChanged;
	}

	/**
	 * @return Returns the isZerorisedDateChanged.
	 */
	public boolean getIsZerorisedDateChanged() {
		return isZerorisedDateChanged;
	}

	/**
	 * @return Returns the isZerorisedReasonChanged.
	 */
	public boolean getIsZerorisedReasonChanged() {
		return isZerorisedReasonChanged;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getLEReference() {
		return lEReference;
	}

	/**
	 * Get Limit Activated Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitActivatedInd() {
		return limitActivatedInd;
	}

	/**
	 * Get Limit Advise Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitAdviseInd() {
		return limitAdviseInd;
	}

	/**
	 * Get Limit CMMSN
	 * 
	 * @return String
	 */
	public String getLimitCMMSN() {
		return limitCMMSN;
	}

	/**
	 * Get Limit Committed Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitCommittedInd() {
		return limitCommittedInd;
	}

	/**
	 * Get Limit Condition Text
	 * 
	 * @return String
	 */
	public String getLimitConditionText() {
		return limitConditionText;
	}

	/**
	 * @return Returns the limitDesc.
	 */
	public String getLimitDesc() {
		return limitDesc;
	}

	/**
	 * @return Returns the limitDescNum.
	 */
	public String getLimitDescNum() {
		return limitDescNum;
	}

	/**
	 * Get Limit Expiry Date
	 * 
	 * @return Date
	 */
	public Date getLimitExpiryDate() {
		return limitExpiryDate;
	}

	/**
	 * Get Limit Fee
	 * 
	 * @return String
	 */
	public String getLimitFee() {
		return limitFee;
	}

	/**
	 * Get Limit ID
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * Get Limit Interest
	 * 
	 * @return float
	 */
	public float getLimitInterest() {
		return limitInterest;
	}

	/**
	 * Get the limit profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	public String getLimitProfileReferenceNumber() {
		return limitProfileReferenceNumber;
	}

	/**
	 * Get Limit Reference
	 * 
	 * @return String
	 */
	public String getLimitRef() {
		return limitRef;
	}

	/**
	 * Get Limit Secured Type
	 * 
	 * @return String
	 */
	public String getLimitSecuredType() {
		return limitSecuredType;
	}

	/**
	 * Get Limit Status
	 * 
	 * @return String
	 */
	public String getLimitStatus() {
		return limitStatus;
	}

	/**
	 * Get the limit system x-ref
	 * 
	 * @return ILimitSysXRef[]
	 */
	public ILimitSysXRef[] getLimitSysXRefs() {
		return limitSysXRefs;
	}

	public Set getLimitSysXRefsSet() {
		return limitSysXRefsSet;
	}
	
	/**
	 * Get the limit system Covenant
	 * 
	 * @return ILimitCovenant[]
	 */
	public ILimitCovenant[] getLimitCovenant() {
		return limitCovenant;
	}

	public Set getLimitCovenantSet() {
		return limitCovenantSet;
	}

	/**
	 * Get Limit Tenor
	 * 
	 * @return long
	 */
	public Long getLimitTenor() {
		return limitTenor;
	}

	/**
	 * Get Limit Tenor Unit
	 * 
	 * @return String
	 */
	public String getLimitTenorUnit() {
		return limitTenorUnit;
	}

	/**
	 * @return Returns the limitTenorUnitNum.
	 */
	public String getLimitTenorUnitNum() {
		return limitTenorUnitNum;
	}

	/**
	 * Get Limit Type
	 * 
	 * @return String
	 */
	public String getLimitType() {
		return limitType;
	}

	public String getLoanType() {
		return loanType;
	}

	public String getLosLimitRef() {
		return losLimitRef;
	}

	// Setters

	/**
	 * Get all co-borrower limits whose status is not DELETED.
	 * 
	 * @return ICoBorrowerLimit[]
	 */
	public ICoBorrowerLimit[] getNonDeletedCoBorrowerLimits() {
		if ((null == coBorrowerLimits) || (coBorrowerLimits.length == 0)) {
			return coBorrowerLimits;
		}
		else {
			ArrayList aList = new ArrayList(coBorrowerLimits.length);
			for (int i = 0; i < coBorrowerLimits.length; i++) {
				if (ICMSConstant.STATE_DELETED.equals(coBorrowerLimits[i].getStatus())) {
					// don't include into arraylist
				}
				else {
					aList.add(coBorrowerLimits[i]);
				}
			}
			return (ICoBorrowerLimit[]) aList.toArray(new ICoBorrowerLimit[0]);
		}
	}

    public ICollateralAllocation[] getNonDeletedCollateralAllocations() {
        if (collateralAllocations == null) return null;

        ArrayList list = new ArrayList();

        for (int ii = 0; ii < collateralAllocations.length; ii++) {
            if (!ICMSConstant.HOST_STATUS_DELETE.equals(collateralAllocations[ii].getHostStatus())) {
                list.add(collateralAllocations[ii]);
            }
        }
        return (ICollateralAllocation[]) list.toArray(new ICollateralAllocation[0]);
    }

	public Long getOmnibusEnvelopeId() {
		return omnibusEnvelopeId;
	}

	public String getOmnibusEnvelopeName() {
		return omnibusEnvelopeName;
	}

	/**
	 * Get operational limit if the limit is tied to commodities.
	 * 
	 * @return Amount
	 */
	public Amount getOperationalLimit() {
		return operationalLimit;
	}

	/**
	 * Get Outer Limit ID
	 * 
	 * @return long
	 */
	public long getOuterLimitID() {
		return outerLimitID;
	}

	/**
	 * Get outer limit profile id.
	 * 
	 * @return long
	 */
	public long getOuterLimitProfileID() {
		return outerLimitProfileID;
	}

	/**
	 * Get the Outer Limit Ref
	 * 
	 * @return String
	 */
	public String getOuterLimitRef() {
		return outerLimitRef;
	}

	/**
	 * @return Returns the outstandingAmount.
	 */
	public Amount getOutstandingAmount() {
		return outstandingAmount;
	}

	/**
	 * get the product code <productDesc>|<limit currency>|<accountType>
	 *
	 * @return productCode of type String
	 */
	public String getProductCode() {
		if (PropertiesConstantHelper.isProductDescSpecialHandling()) {
			return getProductDesc() + "|"
					+ (getApprovedLimitAmount() != null ? getApprovedLimitAmount().getCurrencyCode() : "")
					+ (getAccountType() != null ? "|" + getAccountType() : "");
		}

		return getProductDesc();
	}

	/**
	 * Get Product Description
	 * 
	 * @return String
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * @return Returns the productDescNum.
	 */
	public String getProductDescNum() {
		return productDescNum;
	}

	/**
	 * @return Returns the productGroup.
	 */
	public String getProductGroup() {
		return productGroup;
	}

	/**
	 * @return Returns the productGroupNum.
	 */
	public String getProductGroupNum() {
		return productGroupNum;
	}

	/**
	 * Get the required security coverage percentage
	 * 
	 * @return float
	 */
	public String getRequiredSecurityCoverage() {
		return requiredSecurityCoverage;
	}

	/**
	 * Get Shared Limit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getSharedLimitInd() {
		return sharedLimitInd;
	}

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * Get Version Time
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * Get the zerorisation date.
	 * 
	 * @return Date
	 */
	public Date getZerorisedDate() {
		return zerorisedDate;
	}

	/**
	 * Get the reason of zerorisation.
	 * 
	 * @return String
	 */
	public String getZerorisedReason() {
		return zerorisedReason;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setAcfNo(String acfNo) {
		this.acfNo = acfNo;
	}

	/**
	 * Set Activated Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActivatedLimitAmount(Amount value) {
		activatedLimitAmount = value;
	}

	/**
	 * Set the actual security coverage amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActualSecCoverageAmt(Amount value) {
		this.actualSecCoverageAmt = value;
	}

	/**
	 * Set the actual security coverage amount based on FSV
	 * 
	 * @param value is of type Amount
	 */
	public void setActualSecCoverageFSVAmt(Amount value) {
		this.actualSecCoverageFSVAmt = value;
	}

	/**
	 * Set the actual security coverage amount based on OMV
	 * 
	 * @param value is of type Amount
	 */
	public void setActualSecCoverageOMVAmt(Amount value) {
		this.actualSecCoverageOMVAmt = value;
	}

	/**
	 * Set the actual security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setActualSecurityCoverage(float value) {
		actualSecurityCoverage = value;
	}

	/**
	 * Set Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setApprovedLimitAmount(Amount value) {
		approvedLimitAmount = value;
	}

	/**
	 * Set Booking Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation value) {
		bookingLocation = value;
	}

	/**
	 * Set all co-borrower limits.
	 * 
	 * @param value is of type ICoBorrowerLimit[]
	 */
	public void setCoBorrowerLimits(ICoBorrowerLimit[] value) {
		coBorrowerLimits = value;

		this.coBorrowerLimitsSet = (value == null) ? new HashSet(0) : new HashSet(Arrays.asList(value));
	}

	public void setCoBorrowerLimitsSet(Set coBorrowerLimitsSet) {
		this.coBorrowerLimitsSet = coBorrowerLimitsSet;

		this.coBorrowerLimits = (coBorrowerLimitsSet == null) ? null : (ICoBorrowerLimit[]) coBorrowerLimitsSet
				.toArray(new ICoBorrowerLimit[0]);
	}

	/**
	 * Set All Collateral Allocations
	 * 
	 * @param value is of type ICollateralAllocation[]
	 */
	public void setCollateralAllocations(ICollateralAllocation[] value) {
		collateralAllocations = value;

		this.collateralAllocationsSet = (value == null) ? new HashSet(0) : new HashSet(Arrays.asList(value));
	}

	public void setCollateralAllocationsSet(Set collateralAllocationsSet) {
		this.collateralAllocationsSet = collateralAllocationsSet;

		this.collateralAllocations = (collateralAllocationsSet == null) ? null
				: (ICollateralAllocation[]) collateralAllocationsSet.toArray(new ICollateralAllocation[0]);
	}

	/**
	 * @param drawingLimitAmount The drawingLimitAmount to set.
	 */
	public void setDrawingLimitAmount(Amount drawingLimitAmount) {
		this.drawingLimitAmount = drawingLimitAmount;
	}

	/**
	 * Set the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @param value is of type boolean
	 */
	public void setExistingInd(boolean value) {
		existingInd = value;
	}

	/**
	 * @param facilityCode the facilityCode to set
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}

	/**
	 * Set Facility Description
	 * 
	 * @param value is of type String
	 */
	public void setFacilityDesc(String value) {
		facilityDesc = value;
	}

	/**
	 * @param facilityDescNum The facilityDescNum to set.
	 */
	public void setFacilityDescNum(String facilityDescNum) {
		this.facilityDescNum = facilityDescNum;
	}

	/**
	 * @param facilitySequence the facilitySequence to set
	 */
	public void setFacilitySequence(long facilitySequence) {
		this.facilitySequence = facilitySequence;
	}

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value) {
		hostStatus = value;
	}

	public void setInnerLimitOrdering(Integer innerLimitOrdering) {
		this.innerLimitOrdering = innerLimitOrdering;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @param changed The _isChanged to set.
	 */
	public void setIsChanged(boolean changed) {
		isChanged = changed;
	}

	/**
	 * Set if the limit encounters DAP error.
	 * 
	 * @param isDAPError of type boolean
	 */
	public void setIsDAPError(boolean isDAPError) {
		this.isDAPError = isDAPError;
	}

	/**
	 * Set an indicator whether the limit is zerorised.
	 * 
	 * @param isLimitZerorised of type boolean
	 */
	public void setIsLimitZerorised(boolean isLimitZerorised) {
		this.isLimitZerorised = isLimitZerorised;
	}

	/**
	 * @param isZerorisedChanged The isZerorisedChanged to set.
	 */
	public void setIsZerorisedChanged(boolean isZerorisedChanged) {
		this.isZerorisedChanged = isZerorisedChanged;
	}

	/**
	 * @param isZerorisedDateChanged The isZerorisedDateChanged to set.
	 */
	public void setIsZerorisedDateChanged(boolean isZerorisedDateChanged) {
		this.isZerorisedDateChanged = isZerorisedDateChanged;
	}

	/**
	 * @param isZerorisedReasonChanged The isZerorisedReasonChanged to set.
	 */
	public void setIsZerorisedReasonChanged(boolean isZerorisedReasonChanged) {
		this.isZerorisedReasonChanged = isZerorisedReasonChanged;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setLEReference(String lEReference) {
		this.lEReference = lEReference;
	}

	/**
	 * Set Limit Activated Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitActivatedInd(boolean value) {
		limitActivatedInd = value;
	}

	/**
	 * Set Limit Advise Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitAdviseInd(boolean value) {
		limitAdviseInd = value;
	}

	/**
	 * Set Limit CMMSN
	 * 
	 * @param value is of type String
	 */
	public void setLimitCMMSN(String value) {
		limitCMMSN = value;
	}

	/**
	 * Set Limit Committed Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitCommittedInd(boolean value) {
		limitCommittedInd = value;
	}

	/**
	 * Set Limit Condition Text
	 * 
	 * @param value is of type String
	 */
	public void setLimitConditionText(String value) {
		limitConditionText = value;
	}

	/**
	 * @param limitDesc The limitDesc to set.
	 */
	public void setLimitDesc(String limitDesc) {
		this.limitDesc = limitDesc;
	}

	/**
	 * @param limitDescNum The limitDescNum to set.
	 */
	public void setLimitDescNum(String limitDescNum) {
		this.limitDescNum = limitDescNum;
	}

	/**
	 * Set Limit Expiry Date
	 * 
	 * @param value is of type Date
	 */
	public void setLimitExpiryDate(Date value) {
		limitExpiryDate = value;
	}

	/**
	 * Set Limit Fee
	 * 
	 * @param value is of type String
	 */
	public void setLimitFee(String value) {
		limitFee = value;
	}

	/**
	 * Set Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitID(long value) {
		limitID = value;
	}

	/**
	 * Set Limit Interest
	 * 
	 * @param value is of type float
	 */
	public void setLimitInterest(float value) {
		limitInterest = value;
	}

	/**
	 * Set the limit profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value) {
		limitProfileID = value;
	}

	public void setLimitProfileReferenceNumber(String limitProfileReferenceNumber) {
		this.limitProfileReferenceNumber = limitProfileReferenceNumber;
	}

	/**
	 * Set Limit Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitRef(String value) {
		limitRef = value;
	}

	/**
	 * Set Limit Secured Type
	 * 
	 * @param value is of type String
	 */
	public void setLimitSecuredType(String value) {
		limitSecuredType = value;
	}

	/**
	 * Set Limit Status
	 * 
	 * @param value is of type String
	 */
	public void setLimitStatus(String value) {
		limitStatus = value;
	}

	/**
	 * Set the limit system x-ref
	 * 
	 * @param value is of type ILimitSysXRef[]
	 */
	public void setLimitSysXRefs(ILimitSysXRef[] value) {
		limitSysXRefs = value;

		this.limitSysXRefsSet = (value == null) ? new HashSet(0) : new HashSet(Arrays.asList(value));
	}

	public void setLimitSysXRefsSet(Set limitSysXRefsSet) {
		this.limitSysXRefsSet = limitSysXRefsSet;

		this.limitSysXRefs = (limitSysXRefsSet == null) ? null : (ILimitSysXRef[]) limitSysXRefsSet
				.toArray(new ILimitSysXRef[0]);
	}

	/**
	 * Set the limit system covenant
	 * 
	 * @param value is of type ILimitCovenant[]
	 */
	public void setLimitCovenant(ILimitCovenant[] value) {
		limitCovenant = value;

		this.limitCovenantSet = (value == null) ? new HashSet(0) : new HashSet(Arrays.asList(value));
	}

	public void setLimitCovenantSet(Set limitCovenantSet) {
		this.limitCovenantSet = limitCovenantSet;

		this.limitCovenant = (limitCovenantSet == null) ? null : (ILimitCovenant[]) limitCovenantSet
				.toArray(new ILimitCovenant[0]);
	}

	/**
	 * Set Limit Tenor
	 * 
	 * @param value is of type long
	 */
	public void setLimitTenor(Long value) {
		limitTenor = value;
	}

	/**
	 * Set Limit Tenor Unit
	 * 
	 * @param value is of type String
	 */
	public void setLimitTenorUnit(String value) {
		limitTenorUnit = value;
	}

	/**
	 * @param limitTenorUnitNum The limitTenorUnitNum to set.
	 */
	public void setLimitTenorUnitNum(String limitTenorUnitNum) {
		this.limitTenorUnitNum = limitTenorUnitNum;
	}

	/**
	 * Set Limit Type
	 * 
	 * @param value is of type String
	 */
	public void setLimitType(String value) {
		limitType = value;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public void setLosLimitRef(String losLimitRef) {
		this.losLimitRef = losLimitRef;
	}

	public void setOmnibusEnvelopeId(Long omnibusEnvelopeId) {
		this.omnibusEnvelopeId = omnibusEnvelopeId;
	}

	public void setOmnibusEnvelopeName(String omnibusEnvelopeName) {
		this.omnibusEnvelopeName = omnibusEnvelopeName;
	}

	/**
	 * Set operational limit if the limit is tied to commodities.
	 * 
	 * @param operationalLimit of type Amount
	 */
	public void setOperationalLimit(Amount operationalLimit) {
		this.operationalLimit = operationalLimit;
	}

	/**
	 * Set Outer Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setOuterLimitID(long value) {
		outerLimitID = value;
	}

	/**
	 * Set outer limit profile id.
	 * 
	 * @param outerLimitProfileID of type long
	 */
	public void setOuterLimitProfileID(long outerLimitProfileID) {
		this.outerLimitProfileID = outerLimitProfileID;
	}

	/**
	 * Set the Outer Limit Ref
	 * 
	 * @param value is of type String
	 */
	public void setOuterLimitRef(String value) {
		outerLimitRef = value;
	}

	/**
	 * @param outstandingAmount The outstandingAmount to set.
	 */
	public void setOutstandingAmount(Amount outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	/**
	 * Set Product Description
	 * 
	 * @param value is of type String
	 */
	public void setProductDesc(String value) {
		productDesc = value;
	}

	/**
	 * @param productDescNum The productDescNum to set.
	 */
	public void setProductDescNum(String productDescNum) {
		this.productDescNum = productDescNum;
	}

	/**
	 * @param productGroup The productGroup to set.
	 */
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	/**
	 * @param productGroupNum The productGroupNum to set.
	 */
	public void setProductGroupNum(String productGroupNum) {
		this.productGroupNum = productGroupNum;
	}

	/**
	 * Set the required security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setRequiredSecurityCoverage(String value) {
		requiredSecurityCoverage = value;
	}

	/**
	 * Set Shared Limit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setSharedLimitInd(boolean value) {
		sharedLimitInd = value;
	}

	/**
	 * @param sourceId The sourceId to set.
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * Set Version Time
	 * 
	 * @param version is of type long
	 */
	public void setVersionTime(long version) {
		versionTime = version;
	}

	/**
	 * Set the zerorisation date.
	 * 
	 * @param zerorisedDate of type Date
	 */
	public void setZerorisedDate(Date zerorisedDate) {
		this.zerorisedDate = zerorisedDate;
	}

	/**
	 * Set the reason of zerorisation.
	 * 
	 * @param zerorisedReason of type String
	 */
	public void setZerorisedReason(String zerorisedReason) {
		this.zerorisedReason = zerorisedReason;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((limitDesc == null) ? 0 : limitDesc.hashCode());
		result = prime * result + ((limitRef == null) ? 0 : limitRef.hashCode());
		result = prime * result + ((limitStatus == null) ? 0 : limitStatus.hashCode());
		result = prime * result + ((limitType == null) ? 0 : limitType.hashCode());
		result = prime * result + ((productDesc == null) ? 0 : productDesc.hashCode());
		result = prime * result + ((sourceId == null) ? 0 : sourceId.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		OBLimit other = (OBLimit) obj;
		if (limitDesc == null) {
			if (other.limitDesc != null) {
				return false;
			}
		}
		else if (!limitDesc.equals(other.limitDesc)) {
			return false;
		}

		if (limitRef == null) {
			if (other.limitRef != null) {
				return false;
			}
		}
		else if (!limitRef.equals(other.limitRef)) {
			return false;
		}

		if (limitStatus == null) {
			if (other.limitStatus != null) {
				return false;
			}
		}
		else if (!limitStatus.equals(other.limitStatus)) {
			return false;
		}

		if (limitType == null) {
			if (other.limitType != null) {
				return false;
			}
		}
		else if (!limitType.equals(other.limitType)) {
			return false;
		}

		if (productDesc == null) {
			if (other.productDesc != null) {
				return false;
			}
		}
		else if (!productDesc.equals(other.productDesc)) {
			return false;
		}

		if (sourceId == null) {
			if (other.sourceId != null) {
				return false;
			}
		}
		else if (!sourceId.equals(other.sourceId)) {
			return false;
		}

		return true;
	}

	/**
	 * @return the disbursedInd
	 */
	public String getDisbursedInd() {
		return disbursedInd;
	}
	/**
	 * @param disbursedInd the disbursedInd to set
	 */
	public void setDisbursedInd(String disbursedInd) {
		this.disbursedInd = disbursedInd;
	}

	public String getMainFacilityId() {
		return mainFacilityId;
	}

	public void setMainFacilityId(String mainFacilityId) {
		this.mainFacilityId = mainFacilityId;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBLimit@");
		buf.append("Limit Ref [").append(this.limitRef).append("], ");
		buf.append("LOS Limit Id [").append(this.losLimitRef).append("], ");
		buf.append("AA Number [").append(this.limitProfileReferenceNumber).append("], ");
		buf.append("Status [").append(this.limitStatus).append("], ");
		buf.append("Product Type [").append(this.productDesc).append("], ");
		buf.append("Facility Code [").append(this.facilityCode).append("], ");
		buf.append("Approved Amount [").append(this.approvedLimitAmount).append("], ");
		buf.append("Source [").append(sourceId).append("]");

		return buf.toString();
	}

	public char getIsFromCamonlineReq() {
		return isFromCamonlineReq;
	}

	public void setIsFromCamonlineReq(char isFromCamonlineReq) {
		this.isFromCamonlineReq = isFromCamonlineReq;
	}
	
	
	public String getSyndicateLoan() {
		return syndicateLoan;
	}

	public void setSyndicateLoan(String syndicateLoan) {
		this.syndicateLoan = syndicateLoan;
	}

	public String getPurposeBoolean() {
		return purposeBoolean;
	}

	public void setPurposeBoolean(String purposeBoolean) {
		this.purposeBoolean = purposeBoolean;
	}

	public String getLimitRemarks() {
		return limitRemarks;
	}

	public void setLimitRemarks(String limitRemarks) {
		this.limitRemarks = limitRemarks;
	}
	
	

	public String getProjectFinance() {
		return projectFinance;
	}

	public void setProjectFinance(String projectFinance) {
		this.projectFinance = projectFinance;
	}

	public Long getTenor() {
		return tenor;
	}

	public void setTenor(Long tenor) {
		this.tenor = tenor;
	}

	public String getTenorUnit() {
		return tenorUnit;
	}

	public void setTenorUnit(String tenorUnit) {
		this.tenorUnit = tenorUnit;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public String getTenorDesc() {
		return tenorDesc;
	}

	public void setTenorDesc(String tenorDesc) {
		this.tenorDesc = tenorDesc;
	}

	public String getPutCallOption() {
		return putCallOption;
	}

	public void setPutCallOption(String putCallOption) {
		this.putCallOption = putCallOption;
	}

	public Date getLoanAvailabilityDate() {
		return loanAvailabilityDate;
	}

	public void setLoanAvailabilityDate(Date loanAvailabilityDate) {
		this.loanAvailabilityDate = loanAvailabilityDate;
	}

	public Date getOptionDate() {
		return optionDate;
	}

	public void setOptionDate(Date optionDate) {
		this.optionDate = optionDate;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
	public String getBankingArrangement() {
		return bankingArrangement;
	}

	public void setBankingArrangement(String bankingArrangement) {
		this.bankingArrangement = bankingArrangement;
	}

	public String getClauseAsPerPolicy() {
		return clauseAsPerPolicy;
	}

	public void setClauseAsPerPolicy(String clauseAsPerPolicy) {
		this.clauseAsPerPolicy = clauseAsPerPolicy;
	}
	
	//Adhoc facility changes 
		private String adhocFacility;
		private Date adhocLastAvailDate;
		private String multiDrawdownAllow;
		private String adhocTenor;
		private Date adhocFacilityExpDate;
		private String generalPurposeLoan;
		
		
		public String getAdhocFacility() {
			return adhocFacility;
		}

		public void setAdhocFacility(String adhocFacility) {
			this.adhocFacility = adhocFacility;
		}

		public String getMultiDrawdownAllow() {
			return multiDrawdownAllow;
		}

		public void setMultiDrawdownAllow(String multiDrawdownAllow) {
			this.multiDrawdownAllow = multiDrawdownAllow;
		}

		public String getAdhocTenor() {
			return adhocTenor;
		}

		public void setAdhocTenor(String adhocTenor) {
			this.adhocTenor = adhocTenor;
		}

		

		public Date getAdhocLastAvailDate() {
			return adhocLastAvailDate;
		}

		public void setAdhocLastAvailDate(Date adhocLastAvailDate) {
			this.adhocLastAvailDate = adhocLastAvailDate;
		}

		public Date getAdhocFacilityExpDate() {
			return adhocFacilityExpDate;
		}

		public void setAdhocFacilityExpDate(Date adhocFacilityExpDate) {
			this.adhocFacilityExpDate = adhocFacilityExpDate;
		}

		public String getGeneralPurposeLoan() {
			return generalPurposeLoan;
		}
		

		public void setGeneralPurposeLoan(String generalPurposeLoan) {
			this.generalPurposeLoan = generalPurposeLoan;
		}


	@Override
	public List<IFacilityCoBorrowerDetails> getCoBorrowerDetails() {
		return coBorrowerDetailsList;
	}

	@Override
	public void setCoBorrowerDetails(List<IFacilityCoBorrowerDetails> coBorrowerDetailsList) {
		this.coBorrowerDetailsList = coBorrowerDetailsList;
	}

	
	public String getIsDPRequired() {
		return isDPRequired;
	}

	public void setIsDPRequired(String isDPRequired) {
		this.isDPRequired = isDPRequired;
	}
	


}