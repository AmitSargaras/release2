/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.manualinput.customer.CoBorrowerDetailsForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtDetailForm extends TrxContextForm implements Serializable {
	
	private String facilityId;

	private String limitProfileID;

	private String customerID;

	private String isCreate;

	private String isDelete;

	private String showSublist;

	private String limitId;

	private String agreementType;

	private String facilityGroup;

	private String facilityGroupDesc;

	private String limitRef;

	private String limitExpiryDate;

	private String outerLmtId;

	private String outerLmtRef;

	private String limitDesc;

	private String origBookingCtry;

	private String origBookingCtryDesc;

	private String origBookingLoc;

	private String origBookingLocDesc;

	private String limitType ="No";

	private String prodType;

	private String prodTypeDesc;

	private String approvedCurrency;

	private String approvedLmt;

	private String requiredSecCov;

	private String lmtTenor;

	private String lmtTenorBasis;

	private String lmtTenorBasisDesc;

	private String lmtAdvisedInd;

	private String loanType;

	private String loanTypeDesc;

	private String facilityType;

	private String facilityTypeDesc;

	private String lmtSecRelationship;

	private String limitExpiryDateClass = "fieldname";

	private String limitDescClass = "fieldname";

	private String origBookingCtryClass = "fieldname";

	private String origBookingLocClass = "fieldname";

	private String outerLmtClass = "fieldname";

	private String limitTypeClass = "fieldname";

	private String facilityGroupClass = "fieldname";

	private String prodTypeClass = "fieldname";

	private String approvedLmtClass = "fieldname";

	private String requiredSecCovClass = "fieldname";

	private String lmtTenorClass = "fieldname";

	private String lmtAdvisedIndClass = "fieldname";

	private String loanTypeClass = "fieldname";

	private String facilityTypeClass = "fieldname";

	private String lmtSecRelationshipClass = "fieldname";

	private List acntRefSummaryList;

	private List lmtSecSummaryList;

	private String[] deletedActRef;

	private String deletedLmtSec;
	
	private String securityIdDel;
	
	//Shiv
	private String facilityName;
	
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
	
	private List facNameList;
	
	private List facDetailList;
	
	private List riskTypeList;
	
	private String facilityCat;
	
	private String facilityCode;
	
	private String releasableAmount;
	
	private String adhocLmtAmount;
	
	private String isAdhocToSum;
	
	private String guarantee;
	
	private String subPartyName;
	
	private String subFacilityName;
	
	private String liabilityID;
	
	private String totalReleasedAmount;
	
	private String fundedAmount;
	
	private String nonFundedAmount;
	
	private String memoExposer;
	
	private String sanctionedLimit;
	
	private String inrValue;
	
	private String [] securityCoverage;
	
	private String [] securityTypeSubType;
	
	private String hiddenSerialNo;
	
	private String edited;

	private String climsFacilityId;
	
	private String mainFacilityId;
	
	private char isFromCamonlineReq;

	//Start: Uma:Phase 3 CR :Limit Calculation Dashboard
	private String fromEvent;

	private String currency;

	private String currencyRate;
	
	private String dashboardLineNo;
	
	private String limitAmount;
	
	private String limitInInr;
	
	private String fdMargin;
	
	private String fdRequired;
	
	
	private String syndicateLoan;
	
	private String sessionCriteria;
	
	private String purposeBoolean;
	
	private String limitRemarks;
	
	private String projectFinance;
	
	private String projectLoan;
	
	private String infaProjectFlag;
	
	private String whlmreupSCOD;
	
	private String scodDate;
	
	private String remarksSCOD;
	
	private String exstAssetClass;
	
	private String exstAssClassDate;
	
	private String revisedAssetClass;
	
	private String revsdAssClassDate;
	
	private String actualCommOpsDate;
	
	private String lelvelDelaySCOD;
	
	private String principalInterestSchFlag;
	
	private String projectDelayedFlag;
	
	private String reasonLevelOneDelay;
	
	private String chngInRepaySchedule;
	
	private String escodLevelOneDelayDate;
	
	private String escodLevelSecondDelayDate;
	
	private String reasonLevelThreeDelay;
	
	private String escodLevelThreeDelayDate;
	
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
	
	private String escodLevelTwoDelay;
	
	private String reasonLevelTwoDelay;
	
	private String promotersCapRunFlag;
	
	private String changeInScopeBeforeSCODFlag;
	
	private String promotersHoldEquityFlag;
	
	private String costOverrunOrg25CostViabilityFlag;
	
	private String hasProjectViabReAssFlag;
	
	private String projectViabReassesedFlag;
	
	private String revsedESCODStpDate;
	
	private String exstAssetClassL1;
	
	private String exstAssClassDateL1;
	
	private String exstAssetClassL2;
	
	private String exstAssClassDateL2;
	
	private String exstAssetClassL3;
	
	private String exstAssClassDateL3;
	
	private String revisedAssetClassL1;
	
	
	private String revsdAssClassDateL1;
	
	private String revisedAssetClass_L2;
	private String revsdAssClassDate_L2;
	private String revisedAssetClass_L3;
	
	private String revsdAssClassDate_L3;
	
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
	
	private String revsedESCODStpDateL2;
	
	private String revsedESCODStpDateL3;
	
	private String actualLevelOfDelay;
	
	private String chngInRepayScheduleL1;
	
	private String status;
	
	private String tenor;
	private String tenorUnit;
	private String margin;
	private String tenorDesc;
	private String putCallOption;
	private String loanAvailabilityDate;
	private String optionDate;
	private String riskType;

	
	private String bankingArrangement;

	private String clauseAsPerPolicy;	
	private String isCamCovenantVerified;
	   
	
	private String partyCoBorrower;
	
	private List<CoBorrowerDetailsForm> coBorrowerList;
	
	private String isDPRequired;

	private String facCoBorrowerInd;
	
	private List<FacCoBorrowerDetailsForm> facCoBorrowerList;
	
	private String facCoBorrowerLiabIds;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChngInRepayScheduleL1() {
		return chngInRepayScheduleL1;
	}

	public void setChngInRepayScheduleL1(String chngInRepayScheduleL1) {
		this.chngInRepayScheduleL1 = chngInRepayScheduleL1;
	}

	public String getExstAssetClassL1() {
		return exstAssetClassL1;
	}

	public void setExstAssetClassL1(String exstAssetClassL1) {
		this.exstAssetClassL1 = exstAssetClassL1;
	}

	public String getExstAssClassDateL1() {
		return exstAssClassDateL1;
	}

	public void setExstAssClassDateL1(String exstAssClassDateL1) {
		this.exstAssClassDateL1 = exstAssClassDateL1;
	}

	public String getExstAssetClassL2() {
		return exstAssetClassL2;
	}

	public void setExstAssetClassL2(String exstAssetClassL2) {
		this.exstAssetClassL2 = exstAssetClassL2;
	}

	public String getExstAssClassDateL2() {
		return exstAssClassDateL2;
	}

	public void setExstAssClassDateL2(String exstAssClassDateL2) {
		this.exstAssClassDateL2 = exstAssClassDateL2;
	}

	public String getExstAssetClassL3() {
		return exstAssetClassL3;
	}

	public void setExstAssetClassL3(String exstAssetClassL3) {
		this.exstAssetClassL3 = exstAssetClassL3;
	}

	public String getExstAssClassDateL3() {
		return exstAssClassDateL3;
	}

	public void setExstAssClassDateL3(String exstAssClassDateL3) {
		this.exstAssClassDateL3 = exstAssClassDateL3;
	}

	public String getRevisedAssetClassL1() {
		return revisedAssetClassL1;
	}

	public void setRevisedAssetClassL1(String revisedAssetClassL1) {
		this.revisedAssetClassL1 = revisedAssetClassL1;
	}

	public String getRevsdAssClassDateL1() {
		return revsdAssClassDateL1;
	}

	public void setRevsdAssClassDateL1(String revsdAssClassDateL1) {
		this.revsdAssClassDateL1 = revsdAssClassDateL1;
	}

	public String getRevisedAssetClass_L2() {
		return revisedAssetClass_L2;
	}

	public void setRevisedAssetClass_L2(String revisedAssetClass_L2) {
		this.revisedAssetClass_L2 = revisedAssetClass_L2;
	}

	public String getRevsdAssClassDate_L2() {
		return revsdAssClassDate_L2;
	}

	public void setRevsdAssClassDate_L2(String revsdAssClassDate_L2) {
		this.revsdAssClassDate_L2 = revsdAssClassDate_L2;
	}

	public String getRevisedAssetClass_L3() {
		return revisedAssetClass_L3;
	}

	public void setRevisedAssetClass_L3(String revisedAssetClass_L3) {
		this.revisedAssetClass_L3 = revisedAssetClass_L3;
	}

	public String getRevsdAssClassDate_L3() {
		return revsdAssClassDate_L3;
	}

	public void setRevsdAssClassDate_L3(String revsdAssClassDate_L3) {
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

	public String getRevsedESCODStpDateL2() {
		return revsedESCODStpDateL2;
	}

	public void setRevsedESCODStpDateL2(String revsedESCODStpDateL2) {
		this.revsedESCODStpDateL2 = revsedESCODStpDateL2;
	}

	public String getRevsedESCODStpDateL3() {
		return revsedESCODStpDateL3;
	}

	public void setRevsedESCODStpDateL3(String revsedESCODStpDateL3) {
		this.revsedESCODStpDateL3 = revsedESCODStpDateL3;
	}

	public String getRevsedESCODStpDate() {
		return revsedESCODStpDate;
	}

	public void setRevsedESCODStpDate(String revsedESCODStpDate) {
		this.revsedESCODStpDate = revsedESCODStpDate;
	}

	public String getProjectFinance() {
		return projectFinance;
	}

	public void setProjectFinance(String projectFinance) {
		this.projectFinance = projectFinance;
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

	public String getScodDate() {
		return scodDate;
	}

	public void setScodDate(String scodDate) {
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

	public String getExstAssClassDate() {
		return exstAssClassDate;
	}

	public void setExstAssClassDate(String exstAssClassDate) {
		this.exstAssClassDate = exstAssClassDate;
	}

	public String getRevisedAssetClass() {
		return revisedAssetClass;
	}

	public void setRevisedAssetClass(String revisedAssetClass) {
		this.revisedAssetClass = revisedAssetClass;
	}

	public String getRevsdAssClassDate() {
		return revsdAssClassDate;
	}

	public void setRevsdAssClassDate(String revsdAssClassDate) {
		this.revsdAssClassDate = revsdAssClassDate;
	}

	public String getActualCommOpsDate() {
		return actualCommOpsDate;
	}

	public void setActualCommOpsDate(String actualCommOpsDate) {
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

	public String getEscodLevelOneDelayDate() {
		return escodLevelOneDelayDate;
	}

	public void setEscodLevelOneDelayDate(String escodLevelOneDelayDate) {
		this.escodLevelOneDelayDate = escodLevelOneDelayDate;
	}

	public String getEscodLevelSecondDelayDate() {
		return escodLevelSecondDelayDate;
	}

	public void setEscodLevelSecondDelayDate(String escodLevelSecondDelayDate) {
		this.escodLevelSecondDelayDate = escodLevelSecondDelayDate;
	}

	public String getReasonLevelThreeDelay() {
		return reasonLevelThreeDelay;
	}

	public void setReasonLevelThreeDelay(String reasonLevelThreeDelay) {
		this.reasonLevelThreeDelay = reasonLevelThreeDelay;
	}

	public String getEscodLevelThreeDelayDate() {
		return escodLevelThreeDelayDate;
	}

	public void setEscodLevelThreeDelayDate(String escodLevelThreeDelayDate) {
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

	public String getEscodLevelTwoDelay() {
		return escodLevelTwoDelay;
	}

	public void setEscodLevelTwoDelay(String escodLevelTwoDelay) {
		this.escodLevelTwoDelay = escodLevelTwoDelay;
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

	public String getSessionCriteria() {
		return sessionCriteria;
	}

	public void setSessionCriteria(String sessionCriteria) {
		this.sessionCriteria = sessionCriteria;
	}
	
	//End: Uma:Phase 3 CR :Limit Calculation Dashboard

	public String[] getSecurityCoverage() {
		return securityCoverage;
	}

	public void setSecurityCoverage(String[] securityCoverage) {
		this.securityCoverage = securityCoverage;
	}

	private List systemID;
	
	public List getSystemID() {
		return systemID;
	}

	public void setSystemID(List systemID) {
		this.systemID = systemID;
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

	public String getIsAdhocToSum() {
		return isAdhocToSum;
	}

	public void setIsAdhocToSum(String isAdhocToSum) {
		this.isAdhocToSum = isAdhocToSum;
	}

	public String getAdhocLmtAmount() {
		return adhocLmtAmount;
	}

	public void setAdhocLmtAmount(String adhocLmtAmount) {
		this.adhocLmtAmount = adhocLmtAmount;
	}

	public String getReleasableAmount() {
		return releasableAmount;
	}

	public void setReleasableAmount(String releasableAmount) {
		this.releasableAmount = releasableAmount;
	}

	public String getFacilityCode() {
		return facilityCode;
	}

	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}

	public String getFacilityCat() {
		return facilityCat;
	}

	public void setFacilityCat(String facilityCat) {
		this.facilityCat = facilityCat;
	}

	public List getFacDetailList() {
		return facDetailList;
	}

	public void setFacDetailList(List facDetailList) {
		this.facDetailList = facDetailList;
	}

	public List getFacNameList() {
		return facNameList;
	}

	public void setFacNameList(List facNameList) {
		this.facNameList = facNameList;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getFacilityName() {
		return facilityName;
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

	/**
	 * @return Returns the limitProfileId.
	 */
	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * @param limitProfileId The limitProfileId to set.
	 */
	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * @return Returns the customerID.
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID The customerID to set.
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return Returns the isCreate.
	 */
	public String getIsCreate() {
		return isCreate;
	}

	/**
	 * @param isCreate The isCreate to set.
	 */
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	/**
	 * @return Returns the isDelete.
	 */
	public String getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete The isDelete to set.
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getShowSublist() {
		return showSublist;
	}

	public void setShowSublist(String showSublist) {
		this.showSublist = showSublist;
	}

	/**
	 * @return Returns the agreementType.
	 */
	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * @param agreementType The agreementType to set.
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * @return Returns the facilityGroup.
	 */
	public String getFacilityGroup() {
		return facilityGroup;
	}

	/**
	 * @param facilityGroup The facilityGroup to set.
	 */
	public void setFacilityGroup(String facilityGroup) {
		this.facilityGroup = facilityGroup;
	}

	/**
	 * @return Returns the approvedCurrency.
	 */
	public String getApprovedCurrency() {
		return approvedCurrency;
	}

	/**
	 * @param approvedCurrency The approvedCurrency to set.
	 */
	public void setApprovedCurrency(String approvedCurrency) {
		this.approvedCurrency = approvedCurrency;
	}

	/**
	 * @return Returns the approvedLmt.
	 */
	public String getApprovedLmt() {
		return approvedLmt;
	}

	/**
	 * @param approvedLmt The approvedLmt to set.
	 */
	public void setApprovedLmt(String approvedLmt) {
		this.approvedLmt = approvedLmt;
	}

	/**
	 * @return Returns the outerLmtId.
	 */
	public String getOuterLmtId() {
		return outerLmtId;
	}

	/**
	 * @param outerLmtId The outerLmtId to set.
	 */
	public void setOuterLmtId(String outerLmtId) {
		this.outerLmtId = outerLmtId;
	}

	/**
	 * @return Returns the outerLmtRef.
	 */
	public String getOuterLmtRef() {
		return outerLmtRef;
	}

	/**
	 * @param outerLmtRef The outerLmtRef to set.
	 */
	public void setOuterLmtRef(String outerLmtRef) {
		this.outerLmtRef = outerLmtRef;
	}

	/**
	 * @return Returns the limitDesc.
	 */
	public String getLimitDesc() {
		return limitDesc;
	}

	/**
	 * @param limitDesc The limitDesc to set.
	 */
	public void setLimitDesc(String limitDesc) {
		this.limitDesc = limitDesc;
	}

	/**
	 * @return Returns the limitExpiryDate.
	 */
	public String getLimitExpiryDate() {
		return limitExpiryDate;
	}

	/**
	 * @param limitExpiryDate The limitExpiryDate to set.
	 */
	public void setLimitExpiryDate(String limitExpiryDate) {
		this.limitExpiryDate = limitExpiryDate;
	}

	/**
	 * @return Returns the limitId.
	 */
	public String getLimitId() {
		return limitId;
	}

	/**
	 * @param limitId The limitId to set.
	 */
	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	/**
	 * @return Returns the limitRef.
	 */
	public String getLimitRef() {
		return limitRef;
	}

	/**
	 * @param limitRef The limitRef to set.
	 */
	public void setLimitRef(String limitRef) {
		this.limitRef = limitRef;
	}

	/**
	 * @return Returns the limitType.
	 */
	public String getLimitType() {
		return limitType;
	}

	/**
	 * @param limitType The limitType to set.
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	/**
	 * @return Returns the requiredSecCov.
	 */
	public String getRequiredSecCov() {
		return requiredSecCov;
	}

	/**
	 * @param requiredSecCov The requiredSecCov to set.
	 */
	public void setRequiredSecCov(String requiredSecCov) {
		this.requiredSecCov = requiredSecCov;
	}

	/**
	 * @return Returns the lmtAdvistedInd.
	 */
	public String getLmtAdvisedInd() {
		return lmtAdvisedInd;
	}

	/**
	 * @param lmtAdvistedInd The lmtAdvistedInd to set.
	 */
	public void setLmtAdvisedInd(String lmtAdvisedInd) {
		this.lmtAdvisedInd = lmtAdvisedInd;
	}

	/**
	 * @return Returns the lmtTenor.
	 */
	public String getLmtTenor() {
		return lmtTenor;
	}

	/**
	 * @param lmtTenor The lmtTenor to set.
	 */
	public void setLmtTenor(String lmtTenor) {
		this.lmtTenor = lmtTenor;
	}

	/**
	 * @return Returns the lmtTenorBasis.
	 */
	public String getLmtTenorBasis() {
		return lmtTenorBasis;
	}

	/**
	 * @param lmtTenorBasis The lmtTenorBasis to set.
	 */
	public void setLmtTenorBasis(String lmtTenorBasis) {
		this.lmtTenorBasis = lmtTenorBasis;
	}

	/**
	 * @return Returns the origBookingCtry.
	 */
	public String getOrigBookingCtry() {
		return origBookingCtry;
	}

	/**
	 * @param origBookingCtry The origBookingCtry to set.
	 */
	public void setOrigBookingCtry(String origBookingCtry) {
		this.origBookingCtry = origBookingCtry;
	}

	/**
	 * @return Returns the origBookingLoc.
	 */
	public String getOrigBookingLoc() {
		return origBookingLoc;
	}

	/**
	 * @param origBookingLoc The origBookingLoc to set.
	 */
	public void setOrigBookingLoc(String origBookingLoc) {
		this.origBookingLoc = origBookingLoc;
	}

	/**
	 * @return Returns the prodType.
	 */
	public String getProdType() {
		return prodType;
	}

	/**
	 * @param prodType The prodType to set.
	 */
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	/**
	 * @return Returns the origBookingCtryDsec.
	 */
	public String getOrigBookingCtryDesc() {
		return origBookingCtryDesc;
	}

	/**
	 * @param origBookingCtryDsec The origBookingCtryDsec to set.
	 */
	public void setOrigBookingCtryDesc(String origBookingCtryDesc) {
		this.origBookingCtryDesc = origBookingCtryDesc;
	}

	/**
	 * @return Returns the origBookingLocDesc.
	 */
	public String getOrigBookingLocDesc() {
		return origBookingLocDesc;
	}

	/**
	 * @param origBookingLocDesc The origBookingLocDesc to set.
	 */
	public void setOrigBookingLocDesc(String origBookingLocDesc) {
		this.origBookingLocDesc = origBookingLocDesc;
	}

	/**
	 * @return Returns the facilityGroupDesc.
	 */
	public String getFacilityGroupDesc() {
		return facilityGroupDesc;
	}

	/**
	 * @param facilityGroupDesc The facilityGroupDesc to set.
	 */
	public void setFacilityGroupDesc(String facilityGroupDesc) {
		this.facilityGroupDesc = facilityGroupDesc;
	}

	/**
	 * @return Returns the prodTypeDes.
	 */
	public String getProdTypeDesc() {
		return prodTypeDesc;
	}

	/**
	 * @param prodTypeDes The prodTypeDes to set.
	 */
	public void setProdTypeDesc(String prodTypeDesc) {
		this.prodTypeDesc = prodTypeDesc;
	}

	/**
	 * @return Returns the lmtTenorBasisDesc.
	 */
	public String getLmtTenorBasisDesc() {
		return lmtTenorBasisDesc;
	}

	/**
	 * @param lmtTenorBasisDesc The lmtTenorBasisDesc to set.
	 */
	public void setLmtTenorBasisDesc(String lmtTenorBasisDesc) {
		this.lmtTenorBasisDesc = lmtTenorBasisDesc;
	}

	/**
	 * @return Returns the loanType.
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param loanType The loanType to set.
	 */
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	/**
	 * @return Returns the loanTypeDesc.
	 */
	public String getLoanTypeDesc() {
		return loanTypeDesc;
	}

	/**
	 * @param loanTypeDesc The loanTypeDesc to set.
	 */
	public void setLoanTypeDesc(String loanTypeDesc) {
		this.loanTypeDesc = loanTypeDesc;
	}

	/**
	 * @return Returns the facilityType.
	 */
	public String getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 * @return Returns the facilityTypeDesc.
	 */
	public String getFacilityTypeDesc() {
		return facilityTypeDesc;
	}

	/**
	 * @param facilityTypeDesc The facilityTypeDesc to set.
	 */
	public void setFacilityTypeDesc(String facilityTypeDesc) {
		this.facilityTypeDesc = facilityTypeDesc;
	}

	/**
	 * @return Returns the approvedLmtClass.
	 */
	public String getApprovedLmtClass() {
		return approvedLmtClass;
	}

	/**
	 * @param approvedLmtClass The approvedLmtClass to set.
	 */
	public void setApprovedLmtClass(String approvedLmtClass) {
		this.approvedLmtClass = approvedLmtClass;
	}

	/**
	 * @return Returns the limitDescClass.
	 */
	public String getLimitDescClass() {
		return limitDescClass;
	}

	/**
	 * @param limitDescClass The limitDescClass to set.
	 */
	public void setLimitDescClass(String limitDescClass) {
		this.limitDescClass = limitDescClass;
	}

	/**
	 * @return Returns the limitExpiryDateClass.
	 */
	public String getLimitExpiryDateClass() {
		return limitExpiryDateClass;
	}

	/**
	 * @param limitExpiryDateClass The limitExpiryDateClass to set.
	 */
	public void setLimitExpiryDateClass(String limitExpiryDateClass) {
		this.limitExpiryDateClass = limitExpiryDateClass;
	}

	/**
	 * @return Returns the limitTypeClass.
	 */
	public String getLimitTypeClass() {
		return limitTypeClass;
	}

	/**
	 * @param limitTypeClass The limitTypeClass to set.
	 */
	public void setLimitTypeClass(String limitTypeClass) {
		this.limitTypeClass = limitTypeClass;
	}

	/**
	 * @return Returns the lmtAdvisedIndClass.
	 */
	public String getLmtAdvisedIndClass() {
		return lmtAdvisedIndClass;
	}

	/**
	 * @param lmtAdvisedIndClass The lmtAdvisedIndClass to set.
	 */
	public void setLmtAdvisedIndClass(String lmtAdvisedIndClass) {
		this.lmtAdvisedIndClass = lmtAdvisedIndClass;
	}

	/**
	 * @return Returns the lmtTenorClass.
	 */
	public String getLmtTenorClass() {
		return lmtTenorClass;
	}

	/**
	 * @param lmtTenorClass The lmtTenorClass to set.
	 */
	public void setLmtTenorClass(String lmtTenorClass) {
		this.lmtTenorClass = lmtTenorClass;
	}

	/**
	 * @return Returns the origBookingCtryClass.
	 */
	public String getOrigBookingCtryClass() {
		return origBookingCtryClass;
	}

	/**
	 * @param origBookingCtryClass The origBookingCtryClass to set.
	 */
	public void setOrigBookingCtryClass(String origBookingCtryClass) {
		this.origBookingCtryClass = origBookingCtryClass;
	}

	/**
	 * @return Returns the origBookingLocClass.
	 */
	public String getOrigBookingLocClass() {
		return origBookingLocClass;
	}

	/**
	 * @param origBookingLocClass The origBookingLocClass to set.
	 */
	public void setOrigBookingLocClass(String origBookingLocClass) {
		this.origBookingLocClass = origBookingLocClass;
	}

	/**
	 * @return Returns the outerLmtClass.
	 */
	public String getOuterLmtClass() {
		return outerLmtClass;
	}

	/**
	 * @param outerLmtClass The outerLmtClass to set.
	 */
	public void setOuterLmtClass(String outerLmtClass) {
		this.outerLmtClass = outerLmtClass;
	}

	/**
	 * @return Returns the facilityGroupClass.
	 */
	public String getFacilityGroupClass() {
		return facilityGroupClass;
	}

	/**
	 * @param facilityGroupClass The facilityGroupClass to set.
	 */
	public void setFacilityGroupClass(String facilityGroupClass) {
		this.facilityGroupClass = facilityGroupClass;
	}

	/**
	 * @return Returns the prodTypeClass.
	 */
	public String getProdTypeClass() {
		return prodTypeClass;
	}

	/**
	 * @param prodTypeClass The prodTypeClass to set.
	 */
	public void setProdTypeClass(String prodTypeClass) {
		this.prodTypeClass = prodTypeClass;
	}

	/**
	 * @return Returns the requiredSecCovClass.
	 */
	public String getRequiredSecCovClass() {
		return requiredSecCovClass;
	}

	/**
	 * @param requiredSecCovClass The requiredSecCovClass to set.
	 */
	public void setRequiredSecCovClass(String requiredSecCovClass) {
		this.requiredSecCovClass = requiredSecCovClass;
	}

	/**
	 * @return Returns the loanTypeClass.
	 */
	public String getLoanTypeClass() {
		return loanTypeClass;
	}

	/**
	 * @param loanTypeClass The loanTypeClass to set.
	 */
	public void setLoanTypeClass(String loanTypeClass) {
		this.loanTypeClass = loanTypeClass;
	}

	/**
	 * @return Returns the facilityTypeClass.
	 */
	public String getFacilityTypeClass() {
		return facilityTypeClass;
	}

	/**
	 * @param facilityTypeClass The facilityTypeClass to set.
	 */
	public void setFacilityTypeClass(String facilityTypeClass) {
		this.facilityTypeClass = facilityTypeClass;
	}

	/**
	 * @return Returns the lmtSecRelationship.
	 */
	public String getLmtSecRelationship() {
		return this.lmtSecRelationship;
	}

	/**
	 * @param value The lmtSecRelationship to set.
	 */
	public void setLmtSecRelationship(String value) {
		this.lmtSecRelationship = value;
	}

	/**
	 * @return Returns the lmtSecRelationshipClass.
	 */
	public String getLmtSecRelationshipClass() {
		return this.lmtSecRelationshipClass;
	}

	/**
	 * @param value The lmtSecRelationshipClass to set.
	 */
	public void setLmtSecRelationshipClass(String value) {
		this.lmtSecRelationshipClass = value;
	}

	/**
	 * @return Returns the acntRefSummaryList.
	 */
	public List getAcntRefSummaryList() {
		return acntRefSummaryList;
	}

	/**
	 * @param acntRefSummaryList The acntRefSummaryList to set.
	 */
	public void setAcntRefSummaryList(List acntRefSummaryList) {
		this.acntRefSummaryList = acntRefSummaryList;
	}

	/**
	 * @return Returns the lmtSecSummaryList.
	 */
	public List getLmtSecSummaryList() {
		return lmtSecSummaryList;
	}

	/**
	 * @param lmtSecSummaryList The lmtSecSummaryList to set.
	 */
	public void setLmtSecSummaryList(List lmtSecSummaryList) {
		this.lmtSecSummaryList = lmtSecSummaryList;
	}

	public String getFundedAmount() {
		return fundedAmount;
	}

	public void setFundedAmount(String fundedAmount) {
		this.fundedAmount = fundedAmount;
	}

	public String getNonFundedAmount() {
		return nonFundedAmount;
	}

	public void setNonFundedAmount(String nonFundedAmount) {
		this.nonFundedAmount = nonFundedAmount;
	}

	public String getMemoExposer() {
		return memoExposer;
	}

	public void setMemoExposer(String memoExposer) {
		this.memoExposer = memoExposer;
	}

	public String getSanctionedLimit() {
		return sanctionedLimit;
	}

	public void setSanctionedLimit(String sanctionedLimit) {
		this.sanctionedLimit = sanctionedLimit;
	}

	public String getInrValue() {
		return inrValue;
	}

	public void setInrValue(String inrValue) {
		this.inrValue = inrValue;
	}

	/**
	 * @return Returns the deletedActRef.
	 */
	public String[] getDeletedActRef() {
		return deletedActRef;
	}

	/**
	 * @param deletedActRef The deletedActRef to set.
	 */
	public void setDeletedActRef(String[] deletedActRef) {
		this.deletedActRef = deletedActRef;
	}

	/**
	 * @return Returns the deletedLmtSec.
	 */
	public String getDeletedLmtSec() {
		return deletedLmtSec;
	}

	/**
	 * @param deletedLmtSec The deletedLmtSec to set.
	 */
	public void setDeletedLmtSec(String deletedLmtSec) {
		this.deletedLmtSec = deletedLmtSec;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.base.uiinfra.common.CommonForm#getMapper()
	 */
	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = { { "lmtDetailForm", "com.integrosys.cms.ui.manualinput.limit.LmtDetailMapper" },
				{ "lmtList", "com.integrosys.cms.ui.manualinput.limit.LmtListMapper" },
				{ "dispFieldMapper", "com.integrosys.cms.ui.manualinput.limit.DispFieldMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
		return input;
	}

	public String[] getSecurityTypeSubType() {
		return securityTypeSubType;
	}

	public void setSecurityTypeSubType(String[] securityTypeSubType) {
		this.securityTypeSubType = securityTypeSubType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.struts.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		deletedActRef = new String[0];
	}

	/**
	 * @param securityIdDel the securityIdDel to set
	 */
	public void setSecurityIdDel(String securityIdDel) {
		this.securityIdDel = securityIdDel;
	}

	/**
	 * @return the securityIdDel
	 */
	public String getSecurityIdDel() {
		return securityIdDel;
	}

	public String getHiddenSerialNo() {
		return hiddenSerialNo;
	}

	public void setHiddenSerialNo(String hiddenSerialNo) {
		this.hiddenSerialNo = hiddenSerialNo;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	public String getFromEvent() {
		return fromEvent;
	}

	public void setFromEvent(String fromEvent) {
		this.fromEvent = fromEvent;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getDashboardLineNo() {
		return dashboardLineNo;
	}

	public void setDashboardLineNo(String dashboardLineNo) {
		this.dashboardLineNo = dashboardLineNo;
	}

	public String getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String getLimitInInr() {
		return limitInInr;
	}

	public void setLimitInInr(String limitInInr) {
		this.limitInInr = limitInInr;
	}

	public String getFdMargin() {
		return fdMargin;
	}

	public void setFdMargin(String fdMargin) {
		this.fdMargin = fdMargin;
	}

	public String getFdRequired() {
		return fdRequired;
	}

	public void setFdRequired(String fdRequired) {
		this.fdRequired = fdRequired;
	}
	
	public String getClimsFacilityId() {
		return climsFacilityId;
	}

	public void setClimsFacilityId(String climsFacilityId) {
		this.climsFacilityId = climsFacilityId;
	}
	

	public String getMainFacilityId() {
		return mainFacilityId;
	}

	public void setMainFacilityId(String mainFacilityId) {
		this.mainFacilityId = mainFacilityId;
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

	public String getActualLevelOfDelay() {
		return actualLevelOfDelay;
	}

	public void setActualLevelOfDelay(String actualLevelOfDelay) {
		this.actualLevelOfDelay = actualLevelOfDelay;
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
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	public void setClauseAsPerPolicy(String clauseAsPerPolicy) {
		this.clauseAsPerPolicy = clauseAsPerPolicy;
	}


	public String getIsCamCovenantVerified() {
		return isCamCovenantVerified;
	}

	public void setIsCamCovenantVerified(String isCamCovenantVerified) {
		this.isCamCovenantVerified = isCamCovenantVerified;
	}

	//Adhoc facility changes
	private String adhocFacility;
	private String adhocLastAvailDate;
	private String multiDrawdownAllow;
	private String adhocTenor;
	private String adhocFacilityExpDate;
	private String generalPurposeLoan;

	public String getAdhocFacility() {
		return adhocFacility;
	}

	public void setAdhocFacility(String adhocFacility) {
		this.adhocFacility = adhocFacility;
	}

	

	public String getAdhocLastAvailDate() {
		return adhocLastAvailDate;
	}

	public void setAdhocLastAvailDate(String adhocLastAvailDate) {
		this.adhocLastAvailDate = adhocLastAvailDate;
	}

	public String getAdhocFacilityExpDate() {
		return adhocFacilityExpDate;
	}

	public void setAdhocFacilityExpDate(String adhocFacilityExpDate) {
		this.adhocFacilityExpDate = adhocFacilityExpDate;
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

	

	public String getGeneralPurposeLoan() {
		return generalPurposeLoan;
	}

	public void setGeneralPurposeLoan(String generalPurposeLoan) {
		this.generalPurposeLoan = generalPurposeLoan;
	}
	public String getPartyCoBorrower() {
		return partyCoBorrower;
	}

	public void setPartyCoBorrower(String partyCoBorrower) {
		this.partyCoBorrower = partyCoBorrower;
	}

	public List<CoBorrowerDetailsForm> getCoBorrowerList() {
		return coBorrowerList;
	}

	public void setCoBorrowerList(List<CoBorrowerDetailsForm> coBorrowerList) {
		this.coBorrowerList = coBorrowerList;
	}

	public String getFacCoBorrowerInd() {
		return facCoBorrowerInd;
	}

	public void setFacCoBorrowerInd(String facCoBorrowerInd) {
		this.facCoBorrowerInd = facCoBorrowerInd;
	}

	public List<FacCoBorrowerDetailsForm> getFacCoBorrowerList() {
		return facCoBorrowerList;
	}

	public void setFacCoBorrowerList(List<FacCoBorrowerDetailsForm> facCoBorrowerList) {
		this.facCoBorrowerList = facCoBorrowerList;
	}

	public String getFacCoBorrowerLiabIds() {
		return facCoBorrowerLiabIds;
	}

	public void setFacCoBorrowerLiabIds(String facCoBorrowerLiabIds) {
		this.facCoBorrowerLiabIds = facCoBorrowerLiabIds;
	}
	
	public String getIsDPRequired() {
		return isDPRequired;
	}

	public void setIsDPRequired(String isDPRequired) {
		this.isDPRequired = isDPRequired;
	}


	private List facCoBorrowerListNew;
	public List getFacCoBorrowerListNew() {
		return facCoBorrowerListNew;
	}

	public void setFacCoBorrowerListNew(List facCoBorrowerListNew) {
		this.facCoBorrowerListNew = facCoBorrowerListNew;
	}

	
	private String coBorrowerLiabId;
	private String coBorrowerName;
	
	public String getCoBorrowerLiabId() {
		return coBorrowerLiabId;
	}

	public void setCoBorrowerLiabId(String coBorrowerLiabId) {
		this.coBorrowerLiabId = coBorrowerLiabId;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}

		public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getTenorUnit() {
		return tenorUnit;
	}

	public void setTenorUnit(String tenorUnit) {
		this.tenorUnit = tenorUnit;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
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

	public String getLoanAvailabilityDate() {
		return loanAvailabilityDate;
	}

	public void setLoanAvailabilityDate(String loanAvailabilityDate) {
		this.loanAvailabilityDate = loanAvailabilityDate;
	}

	public String getOptionDate() {
		return optionDate;
	}

	public void setOptionDate(String optionDate) {
		this.optionDate = optionDate;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public List getRiskTypeList() {
		return riskTypeList;
	}

	public void setRiskTypeList(List riskTypeList) {
		this.riskTypeList = riskTypeList;
	}

	
	
}