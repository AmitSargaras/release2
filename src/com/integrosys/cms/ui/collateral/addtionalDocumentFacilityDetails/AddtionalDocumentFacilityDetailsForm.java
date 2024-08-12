/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/InsurancePolicyForm.java,v 1.2 2006/04/11 09:03:49 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/04/11 09:03:49 $ Tag: $Name: $
 */

public class AddtionalDocumentFacilityDetailsForm extends CommonForm implements Serializable {

	private static final long serialVersionUID = -7491487972026951538L;

	private String refID = "";
	
	private String docFacilityAmount = "";
	
	private String docFacilityCategory = "";
	
	private String docFacilityType = "";
	
	private String docFacilityTotalAmount = "";

//	private String insPolicyNum = "";

//	private String insurerName = "";

//	private String insuranceType = "";

	private String expiryDateIns = "";

//	private String insPolicyCurrency = "";

//	private String insurableAmt = "";

//	private String insuredAmt = "";

//	protected String conversionCurrency = "";

//	protected String insuredAmtConvert = "";

//	private String effectiveDateIns = "";

//	private String insuredAgainst = "";

//	private String insuredAddress = "";

	private String documentNo = "";

	private String lmtProfileId = "";

	private String coverNoteNumber = "";

//	private String insIssueDate = "";

//	private String insuranceExchangeRate = "";

//	private String insuranceCompanyName = "";

	private String debitingACNo = "";

//	private String acType = "Loan";

	//private String nonSchemeScheme = "N";
	private String nonSchemeScheme;
//	private String insurancePremium = "";
//
//	private String autoDebit = "N";

	private String takafulCommission = "";

	private String endorsementDate = "";

	private String buildingOccpation = "";

	private String natureOfWork = "";

	private String typeOfBuilding = "";

	private String numberOfStorey = "";

	private String wall = "";

	private String extensionWalls = "";

	private String roof = "";

	private String extensionRoof = "";

	private String endorsementCode = "";

	private String policyCustodian = "";

//	private String insuranceClaimDate = "";

	private String typeOfFloor = "";

	private String typeOfPerils1 = "";

	private String typeOfPerils2 = "";

	private String typeOfPerils3 = "";

	private String typeOfPerils4 = "";

	private String typeOfPerils5 = "";

	private String remark1 = "";

	private String remark2 = "";

	private String remark3 = "";

	private String bankCustomerArrange = "";

	private String nettPermByBorrower = "";

	private String nettPermToInsCo = "";

	private String commissionEarned = "";

	private String stampDuty = "";

	private String newAmountInsured = "";

	private String grossPremium = "";

	private String rebateAmount = "";

	private String serviceTaxAmount = "";

	private String serviceTaxPercentage = "";
	
	private String receivedDate = "";
	
	
	private List currencyList;
	
	// private String newAmtInsured="";
	
	

	public String getReceivedDate() {
		return receivedDate;
	}

	public String getDocFacilityAmount() {
		return docFacilityAmount;
	}

	public void setDocFacilityAmount(String docFacilityAmount) {
		this.docFacilityAmount = docFacilityAmount;
	}

	public String getDocFacilityCategory() {
		return docFacilityCategory;
	}

	public void setDocFacilityCategory(String docFacilityCategory) {
		this.docFacilityCategory = docFacilityCategory;
	}

	public String getDocFacilityType() {
		return docFacilityType;
	}

	public void setDocFacilityType(String docFacilityType) {
		this.docFacilityType = docFacilityType;
	}

	public String getDocFacilityTotalAmount() {
		return docFacilityTotalAmount;
	}

	public void setDocFacilityTotalAmount(String docFacilityTotalAmount) {
		this.docFacilityTotalAmount = docFacilityTotalAmount;
	}

	public List getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List currencyList) {
		this.currencyList = currencyList;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

//	public String getAcType() {
//		return acType;
//	}
//
//	public String getAutoDebit() {
//		return autoDebit;
//	}

	public String getBankCustomerArrange() {
		return bankCustomerArrange;
	}

	public String getBuildingOccpation() {
		return buildingOccpation;
	}

	public String getCommissionEarned() {
		return commissionEarned;
	}

//	public String getConversionCurrency() {
//		return this.conversionCurrency;
//	}

	public String getCoverNoteNumber() {
		return coverNoteNumber;
	}

	public String getDebitingACNo() {
		return debitingACNo;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

//	public String getEffectiveDateIns() {
//		return this.effectiveDateIns;
//	}

	public String getEndorsementCode() {
		return endorsementCode;
	}

	public String getEndorsementDate() {
		return endorsementDate;
	}

	public String getExpiryDateIns() {
		return this.expiryDateIns;
	}

	public String getExtensionRoof() {
		return extensionRoof;
	}

	public String getExtensionWalls() {
		return extensionWalls;
	}

	public String getGrossPremium() {
		return grossPremium;
	}

	/*public String getInsIssueDate() {
		return insIssueDate;
	}

	public String getInsPolicyCurrency() {
		return this.insPolicyCurrency;
	}

	public String getInsPolicyNum() {
		return this.insPolicyNum;
	}

	public String getInsurableAmt() {
		return this.insurableAmt;
	}

	public String getInsuranceClaimDate() {
		return insuranceClaimDate;
	}

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public String getInsuranceExchangeRate() {
		return insuranceExchangeRate;
	}

	public String getInsurancePremium() {
		return insurancePremium;
	}

	public String getInsuranceType() {
		return this.insuranceType;
	}

	public String getInsuredAddress() {
		return this.insuredAddress;
	}

	public String getInsuredAgainst() {
		return this.insuredAgainst;
	}

	public String getInsuredAmt() {
		return this.insuredAmt;
	}

	public String getInsuredAmtConvert() {
		return this.insuredAmtConvert;
	}

	public String getInsurerName() {
		return this.insurerName;
	}*/

	public String getLmtProfileId() {
		return this.lmtProfileId;
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public String getNettPermByBorrower() {
		return nettPermByBorrower;
	}

	public String getNettPermToInsCo() {
		return nettPermToInsCo;
	}

	public String getNewAmountInsured() {
		return newAmountInsured;
	}

	public String getNonSchemeScheme() {
		return nonSchemeScheme;
	}

	public String getNumberOfStorey() {
		return numberOfStorey;
	}

	public String getPolicyCustodian() {
		return policyCustodian;
	}

	public String getRebateAmount() {
		return rebateAmount;
	}

	public String getRefID() {
		return this.refID;
	}

	public String getRemark1() {
		return remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public String getRoof() {
		return roof;
	}

	public String getServiceTaxAmount() {
		return serviceTaxAmount;
	}

	public String getServiceTaxPercentage() {
		return serviceTaxPercentage;
	}

	public String getStampDuty() {
		return stampDuty;
	}

	public String getTakafulCommission() {
		return takafulCommission;
	}

	public String getTypeOfBuilding() {
		return typeOfBuilding;
	}

	public String getTypeOfFloor() {
		return typeOfFloor;
	}

	public String getTypeOfPerils1() {
		return typeOfPerils1;
	}

	public String getTypeOfPerils2() {
		return typeOfPerils2;
	}

	public String getTypeOfPerils3() {
		return typeOfPerils3;
	}

	public String getTypeOfPerils4() {
		return typeOfPerils4;
	}

	public String getTypeOfPerils5() {
		return typeOfPerils5;
	}

	public String getWall() {
		return wall;
	}

//	public void setAcType(String acType) {
//		this.acType = acType;
//	}
//
//	public void setAutoDebit(String autoDebit) {
//		this.autoDebit = autoDebit;
//	}

	public void setBankCustomerArrange(String bankCustomerArrange) {
		this.bankCustomerArrange = bankCustomerArrange;
	}

	public void setBuildingOccpation(String buildingOccpation) {
		this.buildingOccpation = buildingOccpation;
	}

	public void setCommissionEarned(String commissionEarned) {
		this.commissionEarned = commissionEarned;
	}

//	public void setConversionCurrency(String conversionCurrency) {
//		this.conversionCurrency = conversionCurrency;
//	}

	public void setCoverNoteNumber(String coverNoteNumber) {
		this.coverNoteNumber = coverNoteNumber;
	}

	public void setDebitingACNo(String debitingACNo) {
		this.debitingACNo = debitingACNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

//	public void setEffectiveDateIns(String effectiveDateIns) {
//		this.effectiveDateIns = effectiveDateIns;
//	}

	public void setEndorsementCode(String endorsementCode) {
		this.endorsementCode = endorsementCode;
	}

	public void setEndorsementDate(String endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	public void setExpiryDateIns(String expiryDateIns) {
		this.expiryDateIns = expiryDateIns;
	}

	public void setExtensionRoof(String extensionRoof) {
		this.extensionRoof = extensionRoof;
	}

	public void setExtensionWalls(String extensionWalls) {
		this.extensionWalls = extensionWalls;
	}

	public void setGrossPremium(String grossPremium) {
		this.grossPremium = grossPremium;
	}

	/*public void setInsIssueDate(String insIssueDate) {
		this.insIssueDate = insIssueDate;
	}

	public void setInsPolicyCurrency(String insPolicyCurrency) {
		this.insPolicyCurrency = insPolicyCurrency;
	}

	public void setInsPolicyNum(String insPolicyNum) {
		this.insPolicyNum = insPolicyNum;
	}

	public void setInsurableAmt(String insurableAmt) {
		this.insurableAmt = insurableAmt;
	}

	public void setInsuranceClaimDate(String insuranceClaimDate) {
		this.insuranceClaimDate = insuranceClaimDate;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public void setInsuranceExchangeRate(String insuranceExchangeRate) {
		this.insuranceExchangeRate = insuranceExchangeRate;
	}

	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}

	public void setInsuredAgainst(String insuredAgainst) {
		this.insuredAgainst = insuredAgainst;
	}

	public void setInsuredAmt(String insuredAmt) {
		this.insuredAmt = insuredAmt;
	}

	public void setInsuredAmtConvert(String insuredAmtConvert) {
		this.insuredAmtConvert = insuredAmtConvert;
	}

	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}*/

	public void setLmtProfileId(String lmtProfileId) {
		this.lmtProfileId = lmtProfileId;
	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public void setNettPermByBorrower(String nettPermByBorrower) {
		this.nettPermByBorrower = nettPermByBorrower;
	}

	public void setNettPermToInsCo(String nettPermToInsCo) {
		this.nettPermToInsCo = nettPermToInsCo;
	}

	public void setNewAmountInsured(String newAmountInsured) {
		this.newAmountInsured = newAmountInsured;
	}

	public void setNonSchemeScheme(String nonSchemeScheme) {
		this.nonSchemeScheme = nonSchemeScheme;
	}

	public void setNumberOfStorey(String numberOfStorey) {
		this.numberOfStorey = numberOfStorey;
	}

	public void setPolicyCustodian(String policyCustodian) {
		this.policyCustodian = policyCustodian;
	}

	public void setRebateAmount(String rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public void setRoof(String roof) {
		this.roof = roof;
	}

	public void setServiceTaxAmount(String serviceTaxAmount) {
		this.serviceTaxAmount = serviceTaxAmount;
	}

	public void setServiceTaxPercentage(String serviceTaxPercentage) {
		this.serviceTaxPercentage = serviceTaxPercentage;
	}

	public void setStampDuty(String stampDuty) {
		this.stampDuty = stampDuty;
	}

	public void setTakafulCommission(String takafulCommission) {
		this.takafulCommission = takafulCommission;
	}

	public void setTypeOfBuilding(String typeOfBuilding) {
		this.typeOfBuilding = typeOfBuilding;
	}

	public void setTypeOfFloor(String typeOfFloor) {
		this.typeOfFloor = typeOfFloor;
	}

	public void setTypeOfPerils1(String typeOfPerils1) {
		this.typeOfPerils1 = typeOfPerils1;
	}

	public void setTypeOfPerils2(String typeOfPerils2) {
		this.typeOfPerils2 = typeOfPerils2;
	}

	public void setTypeOfPerils3(String typeOfPerils3) {
		this.typeOfPerils3 = typeOfPerils3;
	}

	public void setTypeOfPerils4(String typeOfPerils4) {
		this.typeOfPerils4 = typeOfPerils4;
	}

	public void setTypeOfPerils5(String typeOfPerils5) {
		this.typeOfPerils5 = typeOfPerils5;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	/*
	 * public String getNewAmtInsured() { return newAmtInsured; }
	 * 
	 * public void setNewAmtInsured(String newAmtInsured) { this.newAmtInsured =
	 * newAmtInsured; }
	 */
	public String[][] getMapper() {
		String[][] input = { { "addtionalDocumentFacilityDetailsObj",
				"com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails.AddtionalDocumentFacilityDetailsMapper" }, };
		return input;
	}
	
	private String lastUpdatedBy;
	private String lastApproveBy;
	private String lastUpdatedOn;
	private String lastApproveOn;
	
	private String addFacDocStatus;
	private String originalTargetDate;
	private String nextDueDate;
	private String dateDeferred;
	private String creditApprover;
	private String waivedDate;
	private String insuranceStatusRadio;

	

	public String getAddFacDocStatus() {
		return addFacDocStatus;
	}

	public void setAddFacDocStatus(String addFacDocStatus) {
		this.addFacDocStatus = addFacDocStatus;
	}

	public String getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(String originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getDateDeferred() {
		return dateDeferred;
	}

	public void setDateDeferred(String dateDeferred) {
		this.dateDeferred = dateDeferred;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}

	public String getWaivedDate() {
		return waivedDate;
	}

	public void setWaivedDate(String waivedDate) {
		this.waivedDate = waivedDate;
	}

	public String getInsuranceStatusRadio() {
		return insuranceStatusRadio;
	}

	public void setInsuranceStatusRadio(String insuranceStatusRadio) {
		this.insuranceStatusRadio = insuranceStatusRadio;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastApproveBy() {
		return lastApproveBy;
	}

	public void setLastApproveBy(String lastApproveBy) {
		this.lastApproveBy = lastApproveBy;
	}

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastApproveOn() {
		return lastApproveOn;
	}

	public void setLastApproveOn(String lastApproveOn) {
		this.lastApproveOn = lastApproveOn;
	}

	
}
