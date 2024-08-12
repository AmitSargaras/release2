/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import java.util.List;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;


/**
 * Describe this class. Purpose: To set getter and setter method for the value needed
 * by Party Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Ankit
 * @version $Revision:$
 * @since $Date:05-AUG-2014 $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PartyDetails")

public class PartyDetailsRequestDTO extends RequestDTO {
	
	private static final long serialVersionUID = -114309476199266725L;
	
	//CMSCustomer Info
	
	@XmlElement(name = "businessGroup",required=true)
	private String businessGroup;
	@XmlElement(name = "mainBranch",required=true)
	private String mainBranch;
	@XmlElement(name = "relationshipManager",required=true)
	private String relationshipManager;
	/*As per the discussion made on 03-SEPT-2014, 
	 * RMRegion and businessGroupExposureLimit Fields need to be commented. 
	 * 
	 * RMRegion value will be fetched from Relationship manager Field value.Relationship manager  
	 * table contains Region value and based on that region ID will be set to RMRegion value.
	 * 
	 * businessGroupExposureLimit field value will be set according to value set with field 
	 * 'GROUP_EXP_LIMIT' column of CMS_PARTY_GROUP table.
	 */
	/*@XmlElement(name = "RMRegion",required=true)
	private String RMRegion;*/
	@XmlElement(name = "entity",required=true)
	private String entity;
	@XmlElement(name = "PAN",required=true)
	private String PAN;
	@XmlElement(name = "RBIIndustryCode",required=true)
	private String RBIIndustryCode;
	@XmlElement(name = "industryName",required=true)
	private String industryName;

	@XmlElement(name = "bankingMethod",required=true) //For ON/OFF feature of New CAM Interface CR
	private String bankingMethod;
	
//	@XmlElement(name = "bankingMethodComboBoxList",required=true)
	@XmlTransient
	private List<BankingMethodRequestDTO> bankingMethodComboBoxList;
	@XmlElement(name = "totalFundedLimit",required=true)
	private String totalFundedLimit;
	@XmlElement(name = "totalNonFundedLimit",required=true)
	private String totalNonFundedLimit;
	@XmlElement(name = "fundedSharePercent",required=true)
	private String fundedSharePercent;
	/*@XmlElement(name = "nonFundedSharePercent",required=true)
	private String nonFundedSharePercent;*/
	@XmlElement(name = "memoExposure",required=true)
	private String memoExposure;
	@XmlElement(name = "mpbf",required=true)
	private String mpbf;
	@XmlElement(name = "partyName",required=true)
	private String partyName;
	@XmlElement(name = "segment",required=true)
	private String segment;
	@XmlElement(name = "relationshipStartDate",required=true)
	private String relationshipStartDate;
	
	//CRI Info
	
	@XmlElement(name = "customerRAMId",required=true)
	private String customerRAMId;
	@XmlElement(name = "customerAPRCode",required=true)
	private String customerAPRCode;
	@XmlElement(name = "customerExtRating",required=true)
	private String customerExtRating;
	@XmlElement(name = "nbfcFlag",required=true)
	private String nbfcFlag;
	@XmlElement(name = "nbfcA",required=true)
	private String nbfcA;
	@XmlElement(name = "nbfcB",required=true)
	private String nbfcB;
	@XmlElement(name = "prioritySector",required=true)
	private String prioritySector;
	@XmlElement(name = "msmeClassification",required=true)
	private String msmeClassification;
	@XmlElement(name = "permSSICert",required=true)
	private String permSSICert;
	@XmlElement(name = "weakerSection",required=true)
	private String weakerSection;
	@XmlElement(name = "weakerSectionType",required=true)
	private String weakerSectionType;
	/*@XmlElement(name = "weakerSectionValue",required=true)
	private String weakerSectionValue;*/
	@XmlElement(name = "kisanCreditCard",required=true)
	private String kisanCreditCard;
	@XmlElement(name = "minorityCommunity",required=true)
	private String minorityCommunity;
	@XmlElement(name = "minorityCommunityType",required=true)
	private String minorityCommunityType;
	@XmlElement(name = "capitalMarketExposure",required=true)
	private String capitalMarketExposure;
	@XmlElement(name = "realEstateExposure",required=true)
	private String realEstateExposure;
	@XmlElement(name = "commodityExposure",required=true)
	private String commodityExposure;
	@XmlElement(name = "sensitive",required=true)
	private String sensitive;
	@XmlElement(name = "commodityName",required=true)
	private String commodityName;
	@XmlElement(name = "grossInvestmentPM",required=true)
	private String grossInvestmentPM;
	@XmlElement(name = "grossInvestmentEquip",required=true)
	private String grossInvestmentEquip;
	@XmlElement(name = "psu",required=true)
	private String psu;
	@XmlElement(name = "percentageOfShareholding",required=true)
	private String percentageOfShareholding;
	@XmlElement(name = "govtUndertaking",required=true)
	private String govtUndertaking;
	@XmlElement(name = "projectFinance",required=true)
	private String projectFinance;
	@XmlElement(name = "infraFinance",required=true)
	private String infraFinance;
	@XmlElement(name = "infraFinanceType",required=true)
	private String infraFinanceType;
	@XmlElement(name = "SEMSGuideApplicable",required=true)
	private String SEMSGuideApplicable;
	@XmlElement(name = "failsUnderIFCExclusion",required=true)
	private String failsUnderIFCExclusion;
	@XmlElement(name = "tufs",required=true)
	private String tufs;
	@XmlElement(name = "RBIDefaulterList",required=true)
	private String RBIDefaulterList;
	
	//Change for RBI defaulter Type i.e Company ,Director and Group Companies
	@XmlElement(name = "rbiDefaulterListTypeCompany",required=true)
	private String rbiDefaulterListTypeCompany;
	@XmlElement(name = "rbiDefaulterListTypeDirectors",required=true)
	private String rbiDefaulterListTypeDirectors;
	@XmlElement(name = "rbiDefaulterListTypeGroupCompanies",required=true)
	private String rbiDefaulterListTypeGroupCompanies;
	
	@XmlElement(name = "litigationPending",required=true)
	private String litigationPending;
	@XmlElement(name = "litigationPendingBy",required=true)
	private String litigationPendingBy;
	@XmlElement(name = "interestOfDirectors",required=true)
	private String interestOfDirectors;
	@XmlElement(name = "interestOfDirectorsType",required=true)
	private String interestOfDirectorsType;
	@XmlElement(name = "adverseRemark",required=true)
	private String adverseRemark;
	@XmlElement(name = "adverseRemarkValue",required=true)
	private String adverseRemarkValue;
	@XmlElement(name = "audit",required=true)
	private String audit;
	@XmlElement(name = "avgAnnualTurnover",required=true)
	private String avgAnnualTurnover;
	/*@XmlElement(name = "businessGroupExposureLimit",required=true)
	private String businessGroupExposureLimit;*/
	@XmlElement(name = "isBorrowerDirector",required=true)
	private String isBorrowerDirector;
	@XmlElement(name = "borrowerDirectorValue",required=true)
	private String borrowerDirectorValue;
	@XmlElement(name = "isBorrowerPartner",required=true)
	private String isBorrowerPartner;
	@XmlElement(name = "borrowerPartnerValue",required=true)
	private String borrowerPartnerValue;
	@XmlElement(name = "isDirectorOfOtherBank",required=true)
	private String isDirectorOfOtherBank;
	@XmlElement(name = "directorOfOtherBankValue",required=true)
	private String directorOfOtherBankValue;
	@XmlElement(name = "isRelativeOfHDFCBank",required=true)
	private String isRelativeOfHDFCBank;
	@XmlElement(name = "relativeOfHDFCBankValue",required=true)
	private String relativeOfHDFCBankValue;
	@XmlElement(name = "isRelativeOfChairman",required=true)
	private String isRelativeOfChairman;
	@XmlElement(name = "relativeOfChairmanValue",required=true)
	private String relativeOfChairmanValue;
	@XmlElement(name = "isPartnerRelativeOfBanks",required=true)
	private String isPartnerRelativeOfBanks;
	@XmlElement(name = "partnerRelativeOfBanksValue",required=true)
	private String partnerRelativeOfBanksValue;
	@XmlElement(name = "isShareholderRelativeOfBank",required=true)
	private String isShareholderRelativeOfBank;
	@XmlElement(name = "shareholderRelativeOfBankValue",required=true)
	private String shareholderRelativeOfBankValue;
	@XmlElement(name = "firstYear",required=true)
	private String firstYear;
	@XmlElement(name = "firstYearTurnover",required=true)
	private String firstYearTurnover;
	@XmlElement(name = "turnoverCurrency",required=true)
	private String turnoverCurrency;
	@XmlElement(name = "industryExposurePercent",required=true)
	private String industryExposurePercent;
	@XmlElement(name = "isShareholderRelativeOfDirector",required=true)
	private String isShareholderRelativeOfDirector;
	@XmlElement(name = "climsPartyId",required=true)
	private String climsPartyId;
	
	@XmlTransient
	private String event;
	
	@XmlTransient
	private ActionErrors errors;

	//System Info - Multiple
	
	@XmlElement(name = "systemDetReqList",required=true)
	private List<PartySystemDetailsRequestDTO> systemDetReqList;

	//Banking Method - Multiple 
	
	@XmlElement(name = "bankingMethodDetailList",required=true)
	private List<PartyBankingMethodDetailsRequestDTO> bankingMethodDetailList;

	// Directer Info - Multiple
	
	@XmlElement(name = "directorDetailList",required=true)
	private List<PartyDirectorDetailsRequestDTO> directorDetailList;
	
	// Contact Fields
	
	/*@XmlElement(name = "ContactType",required=true)
	private String contactType;*/
	
	
	@XmlElement(name = "address1",required=true)
	private String address1;
	@XmlElement(name = "address2",required=true)
	private String address2;
	@XmlElement(name = "address3",required=true)
	private String address3;
	@XmlElement(name = "city",required=true)
	private String city;
	@XmlElement(name = "state",required=true)
	private String state;
	@XmlElement(name = "pincode",required=true)
	private String pincode;
	@XmlElement(name = "region",required=true)
	private String region;
	@XmlElement(name = "emailId",required=true)
	private String emailId;
	@XmlElement(name = "faxStdCode",required=true)
	private String faxStdCode;
	@XmlElement(name = "faxNumber",required=true)
	private String faxNumber;
	@XmlElement(name = "telephoneStdCode",required=true)
	private String telephoneStdCode;
	@XmlElement(name = "telephoneNo",required=true)
	private String telephoneNo;
	
	@XmlElement(name = "registeredAddr1",required=true)
	private String registeredAddr1;
	@XmlElement(name = "registeredAddr2",required=true)
	private String registeredAddr2;
	@XmlElement(name = "registeredAddr3",required=true)
	private String registeredAddr3;
	@XmlElement(name = "registeredCity",required=true)
	private String registeredCity;
	@XmlElement(name = "registeredState",required=true)
	private String registeredState;
	@XmlElement(name = "registeredPincode",required=true)
	private String registeredPincode;
	@XmlElement(name = "registeredRegion",required=true)
	private String registeredRegion;
	@XmlElement(name = "registeredCountry",required=true)
	private String registeredCountry;
	@XmlElement(name = "registeredFaxStdCode",required=true)
	private String registeredFaxStdCode;
	@XmlElement(name = "registeredFaxNumber",required=true)
	private String registeredFaxNumber;
	@XmlElement(name = "registeredTelephoneStdCode",required=true)
	private String registeredTelephoneStdCode;
	@XmlElement(name = "registeredTelNo",required=true)
	private String registeredTelNo;
	
	private String relationshipMgrCode;
	


	public String getRelationshipMgrCode() {
		return relationshipMgrCode;
	}

	public void setRelationshipMgrCode(String relationshipMgrCode) {
		this.relationshipMgrCode = relationshipMgrCode;
	}


	@XmlElement(name = "coBorrowerDetailsInd",required=true)
	private String coBorrowerDetailsInd;
	
	@XmlElement(name = "coBorrowerDetailsList",required=true)
	private PartyCoBorrowerDetailsRequestDTO coBorrowerDetailsList;
	

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getRegisteredAddr1() {
		return registeredAddr1;
	}

	public void setRegisteredAddr1(String registeredAddr1) {
		this.registeredAddr1 = registeredAddr1;
	}

	public String getRegisteredAddr2() {
		return registeredAddr2;
	}

	public void setRegisteredAddr2(String registeredAddr2) {
		this.registeredAddr2 = registeredAddr2;
	}

	public String getRegisteredAddr3() {
		return registeredAddr3;
	}

	public void setRegisteredAddr3(String registeredAddr3) {
		this.registeredAddr3 = registeredAddr3;
	}

	public String getRegisteredCity() {
		return registeredCity;
	}

	public void setRegisteredCity(String registeredCity) {
		this.registeredCity = registeredCity;
	}

	public String getRegisteredState() {
		return registeredState;
	}

	public void setRegisteredState(String registeredState) {
		this.registeredState = registeredState;
	}

	public String getRegisteredPincode() {
		return registeredPincode;
	}

	public void setRegisteredPincode(String registeredPincode) {
		this.registeredPincode = registeredPincode;
	}

	public String getRegisteredRegion() {
		return registeredRegion;
	}

	public void setRegisteredRegion(String registeredRegion) {
		this.registeredRegion = registeredRegion;
	}

	public String getRegisteredCountry() {
		return registeredCountry;
	}

	public void setRegisteredCountry(String registeredCountry) {
		this.registeredCountry = registeredCountry;
	}

	public String getRegisteredTelNo() {
		return registeredTelNo;
	}

	public void setRegisteredTelNo(String registeredTelNo) {
		this.registeredTelNo = registeredTelNo;
	}

	public String getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}

	public String getMainBranch() {
		return mainBranch;
	}

	public void setMainBranch(String mainBranch) {
		this.mainBranch = mainBranch;
	}

	public String getRelationshipManager() {
		return relationshipManager;
	}

	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getRBIIndustryCode() {
		return RBIIndustryCode;
	}

	public void setRBIIndustryCode(String industryCode) {
		RBIIndustryCode = industryCode;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getTotalFundedLimit() {
		return totalFundedLimit;
	}

	public void setTotalFundedLimit(String totalFundedLimit) {
		this.totalFundedLimit = totalFundedLimit;
	}

	public String getTotalNonFundedLimit() {
		return totalNonFundedLimit;
	}

	public void setTotalNonFundedLimit(String totalNonFundedLimit) {
		this.totalNonFundedLimit = totalNonFundedLimit;
	}

	public String getFundedSharePercent() {
		return fundedSharePercent;
	}

	public void setFundedSharePercent(String fundedSharePercent) {
		this.fundedSharePercent = fundedSharePercent;
	}

	public String getMemoExposure() {
		return memoExposure;
	}

	public void setMemoExposure(String memoExposure) {
		this.memoExposure = memoExposure;
	}

	public String getMpbf() {
		return mpbf;
	}

	public void setMpbf(String mpbf) {
		this.mpbf = mpbf;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getRelationshipStartDate() {
		return relationshipStartDate;
	}

	public void setRelationshipStartDate(String relationshipStartDate) {
		this.relationshipStartDate = relationshipStartDate;
	}

	public String getCustomerAPRCode() {
		return customerAPRCode;
	}

	public void setCustomerAPRCode(String customerAPRCode) {
		this.customerAPRCode = customerAPRCode;
	}

	public String getCustomerExtRating() {
		return customerExtRating;
	}

	public void setCustomerExtRating(String customerExtRating) {
		this.customerExtRating = customerExtRating;
	}

	public String getNbfcFlag() {
		return nbfcFlag;
	}

	public void setNbfcFlag(String nbfcFlag) {
		this.nbfcFlag = nbfcFlag;
	}

	public String getPrioritySector() {
		return prioritySector;
	}

	public void setPrioritySector(String prioritySector) {
		this.prioritySector = prioritySector;
	}

	public String getMsmeClassification() {
		return msmeClassification;
	}

	public void setMsmeClassification(String msmeClassification) {
		this.msmeClassification = msmeClassification;
	}

	public String getPermSSICert() {
		return permSSICert;
	}

	public void setPermSSICert(String permSSICert) {
		this.permSSICert = permSSICert;
	}

	public String getWeakerSection() {
		return weakerSection;
	}

	public void setWeakerSection(String weakerSection) {
		this.weakerSection = weakerSection;
	}

	public String getKisanCreditCard() {
		return kisanCreditCard;
	}

	public void setKisanCreditCard(String kisanCreditCard) {
		this.kisanCreditCard = kisanCreditCard;
	}

	public String getMinorityCommunity() {
		return minorityCommunity;
	}

	public void setMinorityCommunity(String minorityCommunity) {
		this.minorityCommunity = minorityCommunity;
	}

	public String getCapitalMarketExposure() {
		return capitalMarketExposure;
	}

	public void setCapitalMarketExposure(String capitalMarketExposure) {
		this.capitalMarketExposure = capitalMarketExposure;
	}

	public String getRealEstateExposure() {
		return realEstateExposure;
	}

	public void setRealEstateExposure(String realEstateExposure) {
		this.realEstateExposure = realEstateExposure;
	}

	public String getCommodityExposure() {
		return commodityExposure;
	}

	public void setCommodityExposure(String commodityExposure) {
		this.commodityExposure = commodityExposure;
	}

	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getGrossInvestmentPM() {
		return grossInvestmentPM;
	}

	public void setGrossInvestmentPM(String grossInvestmentPM) {
		this.grossInvestmentPM = grossInvestmentPM;
	}

	public String getGrossInvestmentEquip() {
		return grossInvestmentEquip;
	}

	public void setGrossInvestmentEquip(String grossInvestmentEquip) {
		this.grossInvestmentEquip = grossInvestmentEquip;
	}

	public String getPsu() {
		return psu;
	}

	public void setPsu(String psu) {
		this.psu = psu;
	}

	public String getPercentageOfShareholding() {
		return percentageOfShareholding;
	}

	public void setPercentageOfShareholding(String percentageOfShareholding) {
		this.percentageOfShareholding = percentageOfShareholding;
	}

	public String getGovtUndertaking() {
		return govtUndertaking;
	}

	public void setGovtUndertaking(String govtUndertaking) {
		this.govtUndertaking = govtUndertaking;
	}

	public String getProjectFinance() {
		return projectFinance;
	}

	public void setProjectFinance(String projectFinance) {
		this.projectFinance = projectFinance;
	}

	public String getInfraFinance() {
		return infraFinance;
	}

	public void setInfraFinance(String infraFinance) {
		this.infraFinance = infraFinance;
	}

	public String getInfraFinanceType() {
		return infraFinanceType;
	}

	public void setInfraFinanceType(String infraFinanceType) {
		this.infraFinanceType = infraFinanceType;
	}

	public String getSEMSGuideApplicable() {
		return SEMSGuideApplicable;
	}

	public void setSEMSGuideApplicable(String guideApplicable) {
		SEMSGuideApplicable = guideApplicable;
	}

	public String getFailsUnderIFCExclusion() {
		return failsUnderIFCExclusion;
	}

	public void setFailsUnderIFCExclusion(String failsUnderIFCExclusion) {
		this.failsUnderIFCExclusion = failsUnderIFCExclusion;
	}

	public String getTufs() {
		return tufs;
	}

	public void setTufs(String tufs) {
		this.tufs = tufs;
	}

	public String getRBIDefaulterList() {
		return RBIDefaulterList;
	}

	public void setRBIDefaulterList(String defaulterList) {
		RBIDefaulterList = defaulterList;
	}

	public String getLitigationPending() {
		return litigationPending;
	}

	public void setLitigationPending(String litigationPending) {
		this.litigationPending = litigationPending;
	}

	public String getInterestOfDirectors() {
		return interestOfDirectors;
	}

	public void setInterestOfDirectors(String interestOfDirectors) {
		this.interestOfDirectors = interestOfDirectors;
	}

	public String getAdverseRemark() {
		return adverseRemark;
	}

	public void setAdverseRemark(String adverseRemark) {
		this.adverseRemark = adverseRemark;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getAvgAnnualTurnover() {
		return avgAnnualTurnover;
	}

	public void setAvgAnnualTurnover(String avgAnnualTurnover) {
		this.avgAnnualTurnover = avgAnnualTurnover;
	}

	/*public String getBusinessGroupExposureLimit() {
		return businessGroupExposureLimit;
	}

	public void setBusinessGroupExposureLimit(String businessGroupExposureLimit) {
		this.businessGroupExposureLimit = businessGroupExposureLimit;
	}*/

	public String getIsBorrowerDirector() {
		return isBorrowerDirector;
	}

	public void setIsBorrowerDirector(String isBorrowerDirector) {
		this.isBorrowerDirector = isBorrowerDirector;
	}

	public String getIsBorrowerPartner() {
		return isBorrowerPartner;
	}

	public void setIsBorrowerPartner(String isBorrowerPartner) {
		this.isBorrowerPartner = isBorrowerPartner;
	}

	public String getIsDirectorOfOtherBank() {
		return isDirectorOfOtherBank;
	}

	public void setIsDirectorOfOtherBank(String isDirectorOfOtherBank) {
		this.isDirectorOfOtherBank = isDirectorOfOtherBank;
	}

	public String getIsRelativeOfHDFCBank() {
		return isRelativeOfHDFCBank;
	}

	public void setIsRelativeOfHDFCBank(String isRelativeOfHDFCBank) {
		this.isRelativeOfHDFCBank = isRelativeOfHDFCBank;
	}

	public String getIsRelativeOfChairman() {
		return isRelativeOfChairman;
	}

	public void setIsRelativeOfChairman(String isRelativeOfChairman) {
		this.isRelativeOfChairman = isRelativeOfChairman;
	}

	public String getIsPartnerRelativeOfBanks() {
		return isPartnerRelativeOfBanks;
	}

	public void setIsPartnerRelativeOfBanks(String isPartnerRelativeOfBanks) {
		this.isPartnerRelativeOfBanks = isPartnerRelativeOfBanks;
	}

	public String getIsShareholderRelativeOfBank() {
		return isShareholderRelativeOfBank;
	}

	public void setIsShareholderRelativeOfBank(String isShareholderRelativeOfBank) {
		this.isShareholderRelativeOfBank = isShareholderRelativeOfBank;
	}

	public String getFirstYear() {
		return firstYear;
	}

	public void setFirstYear(String firstYear) {
		this.firstYear = firstYear;
	}

	public String getFirstYearTurnover() {
		return firstYearTurnover;
	}

	public void setFirstYearTurnover(String firstYearTurnover) {
		this.firstYearTurnover = firstYearTurnover;
	}

	public String getTurnoverCurrency() {
		return turnoverCurrency;
	}

	public void setTurnoverCurrency(String turnoverCurrency) {
		this.turnoverCurrency = turnoverCurrency;
	}

	public List<PartySystemDetailsRequestDTO> getSystemDetReqList() {
		return systemDetReqList;
	}

	public void setSystemDetReqList(
			List<PartySystemDetailsRequestDTO> systemDetReqList) {
		this.systemDetReqList = systemDetReqList;
	}

	/*public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}*/

	/*public String getNonFundedSharePercent() {
		return nonFundedSharePercent;
	}

	public void setNonFundedSharePercent(String nonFundedSharePercent) {
		this.nonFundedSharePercent = nonFundedSharePercent;
	}*/

	public String getIndustryExposurePercent() {
		return industryExposurePercent;
	}

	public void setIndustryExposurePercent(String industryExposurePercent) {
		this.industryExposurePercent = industryExposurePercent;
	}

	public String getIsShareholderRelativeOfDirector() {
		return isShareholderRelativeOfDirector;
	}

	public void setIsShareholderRelativeOfDirector(
			String isShareholderRelativeOfDirector) {
		this.isShareholderRelativeOfDirector = isShareholderRelativeOfDirector;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	/*public String getRMRegion() {
		return RMRegion;
	}

	public void setRMRegion(String region) {
		RMRegion = region;
	}*/

	public List<PartyBankingMethodDetailsRequestDTO> getBankingMethodDetailList() {
		return bankingMethodDetailList;
	}

	public void setBankingMethodDetailList(
			List<PartyBankingMethodDetailsRequestDTO> bankingMethodDetailList) {
		this.bankingMethodDetailList = bankingMethodDetailList;
	}

	public List<PartyDirectorDetailsRequestDTO> getDirectorDetailList() {
		return directorDetailList;
	}

	public void setDirectorDetailList(
			List<PartyDirectorDetailsRequestDTO> directorDetailList) {
		this.directorDetailList = directorDetailList;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String pan) {
		PAN = pan;
	}

	public String getTelephoneStdCode() {
		return telephoneStdCode;
	}

	public void setTelephoneStdCode(String telephoneStdCode) {
		this.telephoneStdCode = telephoneStdCode;
	}

	public String getFaxStdCode() {
		return faxStdCode;
	}

	public void setFaxStdCode(String faxStdCode) {
		this.faxStdCode = faxStdCode;
	}

	public String getRegisteredFaxNumber() {
		return registeredFaxNumber;
	}

	public void setRegisteredFaxNumber(String registeredFaxNumber) {
		this.registeredFaxNumber = registeredFaxNumber;
	}

	public String getRegisteredTelephoneStdCode() {
		return registeredTelephoneStdCode;
	}

	public void setRegisteredTelephoneStdCode(String registeredTelephoneStdCode) {
		this.registeredTelephoneStdCode = registeredTelephoneStdCode;
	}

	public String getRegisteredFaxStdCode() {
		return registeredFaxStdCode;
	}

	public void setRegisteredFaxStdCode(String registeredFaxStdCode) {
		this.registeredFaxStdCode = registeredFaxStdCode;
	}

	public String getClimsPartyId() {
		return climsPartyId;
	}

	public void setClimsPartyId(String climsPartyId) {
		this.climsPartyId = climsPartyId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getNbfcA() {
		return nbfcA;
	}

	public void setNbfcA(String nbfcA) {
		this.nbfcA = nbfcA;
	}

	public String getNbfcB() {
		return nbfcB;
	}

	public void setNbfcB(String nbfcB) {
		this.nbfcB = nbfcB;
	}

	public String getWeakerSectionType() {
		return weakerSectionType;
	}

	public void setWeakerSectionType(String weakerSectionType) {
		this.weakerSectionType = weakerSectionType;
	}

/*	public String getWeakerSectionValue() {
		return weakerSectionValue;
	}

	public void setWeakerSectionValue(String weakerSectionValue) {
		this.weakerSectionValue = weakerSectionValue;
	}*/

	public String getMinorityCommunityType() {
		return minorityCommunityType;
	}

	public void setMinorityCommunityType(String minorityCommunityType) {
		this.minorityCommunityType = minorityCommunityType;
	}

	//Change for RBI defaulter Type i.e Company ,Director and Group Companies
	public String getRbiDefaulterListTypeCompany() {
		return rbiDefaulterListTypeCompany;
	}

	public void setRbiDefaulterListTypeCompany(String rbiDefaulterListTypeCompany) {
		this.rbiDefaulterListTypeCompany = rbiDefaulterListTypeCompany;
	}

	public String getRbiDefaulterListTypeDirectors() {
		return rbiDefaulterListTypeDirectors;
	}

	public void setRbiDefaulterListTypeDirectors(String rbiDefaulterListTypeDirectors) {
		this.rbiDefaulterListTypeDirectors = rbiDefaulterListTypeDirectors;
	}
	public String getRbiDefaulterListTypeGroupCompanies() {
		return rbiDefaulterListTypeGroupCompanies;
	}

	public void setRbiDefaulterListTypeGroupCompanies(String rbiDefaulterListTypeGroupCompanies) {
		this.rbiDefaulterListTypeGroupCompanies = rbiDefaulterListTypeGroupCompanies;
	}
	
	public String getLitigationPendingBy() {
		return litigationPendingBy;
	}

	public void setLitigationPendingBy(String litigationPendingBy) {
		this.litigationPendingBy = litigationPendingBy;
	}

	public String getInterestOfDirectorsType() {
		return interestOfDirectorsType;
	}

	public void setInterestOfDirectorsType(String interestOfDirectorsType) {
		this.interestOfDirectorsType = interestOfDirectorsType;
	}

	public String getAdverseRemarkValue() {
		return adverseRemarkValue;
	}

	public void setAdverseRemarkValue(String adverseRemarkValue) {
		this.adverseRemarkValue = adverseRemarkValue;
	}

	public String getBorrowerDirectorValue() {
		return borrowerDirectorValue;
	}

	public void setBorrowerDirectorValue(String borrowerDirectorValue) {
		this.borrowerDirectorValue = borrowerDirectorValue;
	}

	public String getBorrowerPartnerValue() {
		return borrowerPartnerValue;
	}

	public void setBorrowerPartnerValue(String borrowerPartnerValue) {
		this.borrowerPartnerValue = borrowerPartnerValue;
	}

	public String getDirectorOfOtherBankValue() {
		return directorOfOtherBankValue;
	}

	public void setDirectorOfOtherBankValue(String directorOfOtherBankValue) {
		this.directorOfOtherBankValue = directorOfOtherBankValue;
	}

	public String getRelativeOfHDFCBankValue() {
		return relativeOfHDFCBankValue;
	}

	public void setRelativeOfHDFCBankValue(String relativeOfHDFCBankValue) {
		this.relativeOfHDFCBankValue = relativeOfHDFCBankValue;
	}

	public String getRelativeOfChairmanValue() {
		return relativeOfChairmanValue;
	}

	public void setRelativeOfChairmanValue(String relativeOfChairmanValue) {
		this.relativeOfChairmanValue = relativeOfChairmanValue;
	}

	public String getPartnerRelativeOfBanksValue() {
		return partnerRelativeOfBanksValue;
	}

	public void setPartnerRelativeOfBanksValue(String partnerRelativeOfBanksValue) {
		this.partnerRelativeOfBanksValue = partnerRelativeOfBanksValue;
	}

	public String getShareholderRelativeOfBankValue() {
		return shareholderRelativeOfBankValue;
	}

	public void setShareholderRelativeOfBankValue(
			String shareholderRelativeOfBankValue) {
		this.shareholderRelativeOfBankValue = shareholderRelativeOfBankValue;
	}

	public String getCustomerRAMId() {
		return customerRAMId;
	}

	public void setCustomerRAMId(String customerRAMId) {
		this.customerRAMId = customerRAMId;
	}
	
	//LEI CR
	@XmlElement(name = "leiCode",required=true)
	private String leiCode;
	@XmlElement(name = "leiExpDate",required=true)
	private String leiExpDate;
	
	public String getLeiCode() {
		return leiCode;
	}

	public void setLeiCode(String leiCode) {
		this.leiCode = leiCode;
	}

	public String getLeiExpDate() {
		return leiExpDate;
	}

	public void setLeiExpDate(String leiExpDate) {
		this.leiExpDate = leiExpDate;
	}
	//End LEI CR
	
	//IFSC cr
	@XmlElement(name = "ifscBankingMethodDetailList",required=true)
	private List<PartyIFSCBankingMethodDetailsRequestDTO> ifscBankingMethodDetailList;
	
	public List<PartyIFSCBankingMethodDetailsRequestDTO> getIfscBankingMethodDetailList() {
		return ifscBankingMethodDetailList;
	}

	public void setIfscBankingMethodDetailList(List<PartyIFSCBankingMethodDetailsRequestDTO> ifscBankingMethodDetailList) {
		this.ifscBankingMethodDetailList = ifscBankingMethodDetailList;
	}
	//End IFSC cr


	public void setCoBorrowerDetailsInd(String coBorrowerDetailsInd) {
		this.coBorrowerDetailsInd = coBorrowerDetailsInd;
	}

	public void setCoBorrowerDetailsList(PartyCoBorrowerDetailsRequestDTO coBorrowerDetailsList) {
		this.coBorrowerDetailsList = coBorrowerDetailsList;
	}
	
	public PartyCoBorrowerDetailsRequestDTO getCoBorrowerDetailsList() {
		return coBorrowerDetailsList;
	}

	public String getCoBorrowerDetailsInd() {
		return coBorrowerDetailsInd;
	}
	@XmlElement(name = "isSPVFunding",required=true)
	private String isSPVFunding;
	@XmlElement(name = "indirectCountryRiskExposure",required=true)
	private String indirectCountryRiskExposure;
	@XmlElement(name = "criCountryName",required=true)
	private String criCountryName;
	@XmlElement(name = "salesPercentage",required=true)
	private String salesPercentage;
	@XmlElement(name = "isIPRE",required=true)
	private String isIPRE;
	@XmlElement(name = "financeForAccquisition",required=true)
	private String financeForAccquisition;
	@XmlElement(name = "facilityApproved",required=true)
	private String facilityApproved;
	@XmlElement(name = "facilityAmount",required=true)
	private String facilityAmount;
	@XmlElement(name = "securityName",required=true)
	private String securityName;
	@XmlElement(name = "securityType",required=true)
	private String securityType;
	@XmlElement(name = "securityValue",required=true)
	private String securityValue;
	@XmlElement(name = "companyType",required=true)
	private String companyType;
	@XmlElement(name = "nameOfHoldingCompany",required=true)
	private String nameOfHoldingCompany;
	@XmlElement(name = "type",required=true)
	private String type;
	@XmlElement(name = "categoryOfFarmer",required=true)
	private String categoryOfFarmer;
	@XmlElement(name = "landHolding",required=true)
	private String landHolding;
	@XmlElement(name = "countryOfGuarantor",required=true)
	private String countryOfGuarantor;
	@XmlElement(name = "isAffordableHousingFinance",required=true)
	private String isAffordableHousingFinance;
	@XmlElement(name = "isInRestrictedList",required=true)
	private String isInRestrictedList;
	@XmlElement(name = "restrictedFinancing",required=true)
	private String restrictedFinancing;
	@XmlElement(name = "restrictedIndustries",required=true)
	private String restrictedIndustries;
	@XmlElement(name = "restrictedListIndustries",required=true)
	private String restrictedListIndustries;
	@XmlElement(name = "isBorrowerInRejectDatabase",required=true)
	private String isBorrowerInRejectDatabase;
	@XmlElement(name = "rejectHistoryReason",required=true)
	private String rejectHistoryReason;
	@XmlElement(name = "capitalForCommodityAndExchange",required=true)
	private String capitalForCommodityAndExchange;
	@XmlElement(name = "isBrokerTypeShare",required=true)
	private String isBrokerTypeShare;
	@XmlElement(name = "isBrokerTypeCommodity",required=true)
	private String isBrokerTypeCommodity;
//	@XmlElement(name = "brokerType",required=true)
//	private String brokerType;
	@XmlElement(name = "objectFinance",required=true)
	private String objectFinance;
	@XmlElement(name = "isCommodityFinanceCustomer",required=true)
	private String isCommodityFinanceCustomer;
	@XmlElement(name = "restructuedBorrowerOrFacility",required=true)
	private String restructuedBorrowerOrFacility;
	@XmlElement(name = "facility",required=true)
	private String facility;
	@XmlElement(name = "limitAmount",required=true)
	private String limitAmount;
	@XmlElement(name = "subsidyFlag",required=true)
	private String subsidyFlag;
	@XmlElement(name = "holdingCompnay",required=true)
	private String holdingCompnay;
	@XmlElement(name = "cautionList",required=true)
	private String cautionList;
	@XmlElement(name = "dateOfCautionList",required=true)
	private String dateOfCautionList;
	@XmlElement(name = "company",required=true)
	private String company;
	@XmlElement(name = "directors",required=true)
	private String directors;
	@XmlElement(name = "groupCompanies",required=true)
	private String groupCompanies;
	@XmlElement(name = "defaultersList",required=true)
	private String defaultersList;
	@XmlElement(name = "rbiDateOfCautionList",required=true)
	private String rbiDateOfCautionList;
	@XmlElement(name = "rbiCompany",required=true)
	private String rbiCompany;
	@XmlElement(name = "rbiDirectors",required=true)
	private String rbiDirectors;
	@XmlElement(name = "rbiGroupCompanies",required=true)
	private String rbiGroupCompanies;
	@XmlElement(name = "commericialRealEstate",required=true)
	private String commericialRealEstate;
	@XmlElement(name = "commericialRealEstateValue",required=true)
	private String commericialRealEstateValue;
	@XmlElement(name = "commericialRealEstateResidentialHousing",required=true)
	private String commericialRealEstateResidentialHousing;
	@XmlElement(name = "residentialRealEstate",required=true)
	private String residentialRealEstate;
	@XmlElement(name = "indirectRealEstate",required=true)
	private String indirectRealEstate;
	@XmlElement(name = "isBackedByGovt",required=true)
	private String isBackedByGovt; 
	@XmlElement(name = "conductOfAccountWithOtherBanks",required=true)
	private String conductOfAccountWithOtherBanks;
	@XmlElement(name = "crilicStatus",required=true)
	private String crilicStatus;
//	@XmlElement(name = "isQualifyingNotesPresent",required=true)
//	private String isQualifyingNotesPresent;
//	@XmlElement(name = "stateImplications",required=true)
//	private String stateImplications;
	@XmlElement(name = "comment",required=true)
	private String comment;

	@XmlElement(name = "isRBIWilfulDefaultersList",required=true)
	private String isRBIWilfulDefaultersList;
	@XmlElement(name = "nameOfBank",required=true)
	private String nameOfBank;
	@XmlElement(name = "isDirectorMoreThanOne",required=true)
	private String isDirectorMoreThanOne;
	@XmlElement(name = "nameOfDirectorsAndCompany",required=true)
	private String nameOfDirectorsAndCompany;
	@XmlElement(name = "isCibilStatusClean",required=true)
	private String isCibilStatusClean;
	@XmlElement(name = "detailsOfCleanCibil",required=true)
	private String detailsOfCleanCibil;
	
	
	public String getIsSPVFunding() {
		return isSPVFunding;
	}

	public void setIsSPVFunding(String isSPVFunding) {
		this.isSPVFunding = isSPVFunding;
	}

	public String getIndirectCountryRiskExposure() {
		return indirectCountryRiskExposure;
	}

	public void setIndirectCountryRiskExposure(String indirectCountryRiskExposure) {
		this.indirectCountryRiskExposure = indirectCountryRiskExposure;
	}

	public String getCriCountryName() {
		return criCountryName;
	}

	public void setCriCountryName(String criCountryName) {
		this.criCountryName = criCountryName;
	}

	public String getSalesPercentage() {
		return salesPercentage;
	}

	public void setSalesPercentage(String salesPercentage) {
		this.salesPercentage = salesPercentage;
	}

	public String getIsIPRE() {
		return isIPRE;
	}

	public void setIsIPRE(String isIPRE) {
		this.isIPRE = isIPRE;
	}

	public String getFinanceForAccquisition() {
		return financeForAccquisition;
	}

	public void setFinanceForAccquisition(String financeForAccquisition) {
		this.financeForAccquisition = financeForAccquisition;
	}

	public String getFacilityApproved() {
		return facilityApproved;
	}

	public void setFacilityApproved(String facilityApproved) {
		this.facilityApproved = facilityApproved;
	}

	public String getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecurityValue() {
		return securityValue;
	}

	public void setSecurityValue(String securityValue) {
		this.securityValue = securityValue;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getNameOfHoldingCompany() {
		return nameOfHoldingCompany;
	}

	public void setNameOfHoldingCompany(String nameOfHoldingCompany) {
		this.nameOfHoldingCompany = nameOfHoldingCompany;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategoryOfFarmer() {
		return categoryOfFarmer;
	}

	public void setCategoryOfFarmer(String categoryOfFarmer) {
		this.categoryOfFarmer = categoryOfFarmer;
	}

	public String getLandHolding() {
		return landHolding;
	}

	public void setLandHolding(String landHolding) {
		this.landHolding = landHolding;
	}

	public String getCountryOfGuarantor() {
		return countryOfGuarantor;
	}

	public void setCountryOfGuarantor(String countryOfGuarantor) {
		this.countryOfGuarantor = countryOfGuarantor;
	}

	public String getIsAffordableHousingFinance() {
		return isAffordableHousingFinance;
	}

	public void setIsAffordableHousingFinance(String isAffordableHousingFinance) {
		this.isAffordableHousingFinance = isAffordableHousingFinance;
	}

	public String getIsInRestrictedList() {
		return isInRestrictedList;
	}

	public void setIsInRestrictedList(String isInRestrictedList) {
		this.isInRestrictedList = isInRestrictedList;
	}

	public String getRestrictedFinancing() {
		return restrictedFinancing;
	}

	public void setRestrictedFinancing(String restrictedFinancing) {
		this.restrictedFinancing = restrictedFinancing;
	}

	public String getRestrictedIndustries() {
		return restrictedIndustries;
	}

	public void setRestrictedIndustries(String restrictedIndustries) {
		this.restrictedIndustries = restrictedIndustries;
	}

	public String getRestrictedListIndustries() {
		return restrictedListIndustries;
	}

	public void setRestrictedListIndustries(String restrictedListIndustries) {
		this.restrictedListIndustries = restrictedListIndustries;
	}

	public String getIsBorrowerInRejectDatabase() {
		return isBorrowerInRejectDatabase;
	}

	public void setIsBorrowerInRejectDatabase(String isBorrowerInRejectDatabase) {
		this.isBorrowerInRejectDatabase = isBorrowerInRejectDatabase;
	}

	public String getRejectHistoryReason() {
		return rejectHistoryReason;
	}

	public void setRejectHistoryReason(String rejectHistoryReason) {
		this.rejectHistoryReason = rejectHistoryReason;
	}

	public String getCapitalForCommodityAndExchange() {
		return capitalForCommodityAndExchange;
	}

	public void setCapitalForCommodityAndExchange(String capitalForCommodityAndExchange) {
		this.capitalForCommodityAndExchange = capitalForCommodityAndExchange;
	}

//	public String getBrokerType() {
//		return brokerType;
//	}
//
//	public void setBrokerType(String brokerType) {
//		this.brokerType = brokerType;
//	}

	public String getObjectFinance() {
		return objectFinance;
	}

	public void setObjectFinance(String objectFinance) {
		this.objectFinance = objectFinance;
	}

	public String getIsCommodityFinanceCustomer() {
		return isCommodityFinanceCustomer;
	}

	public void setIsCommodityFinanceCustomer(String isCommodityFinanceCustomer) {
		this.isCommodityFinanceCustomer = isCommodityFinanceCustomer;
	}

	public String getRestructuedBorrowerOrFacility() {
		return restructuedBorrowerOrFacility;
	}

	public void setRestructuedBorrowerOrFacility(String restructuedBorrowerOrFacility) {
		this.restructuedBorrowerOrFacility = restructuedBorrowerOrFacility;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String getSubsidyFlag() {
		return subsidyFlag;
	}

	public void setSubsidyFlag(String subsidyFlag) {
		this.subsidyFlag = subsidyFlag;
	}

	public String getHoldingCompnay() {
		return holdingCompnay;
	}

	public void setHoldingCompnay(String holdingCompnay) {
		this.holdingCompnay = holdingCompnay;
	}

	public String getCautionList() {
		return cautionList;
	}

	public void setCautionList(String cautionList) {
		this.cautionList = cautionList;
	}

	public String getDateOfCautionList() {
		return dateOfCautionList;
	}

	public void setDateOfCautionList(String dateOfCautionList) {
		this.dateOfCautionList = dateOfCautionList;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDirectors() {
		return directors;
	}

	public void setDirectors(String directors) {
		this.directors = directors;
	}

	public String getGroupCompanies() {
		return groupCompanies;
	}

	public void setGroupCompanies(String groupCompanies) {
		this.groupCompanies = groupCompanies;
	}

	public String getDefaultersList() {
		return defaultersList;
	}

	public void setDefaultersList(String defaultersList) {
		this.defaultersList = defaultersList;
	}

	public String getRbiDateOfCautionList() {
		return rbiDateOfCautionList;
	}

	public void setRbiDateOfCautionList(String rbiDateOfCautionList) {
		this.rbiDateOfCautionList = rbiDateOfCautionList;
	}

	public String getRbiCompany() {
		return rbiCompany;
	}

	public void setRbiCompany(String rbiCompany) {
		this.rbiCompany = rbiCompany;
	}

	public String getRbiDirectors() {
		return rbiDirectors;
	}

	public void setRbiDirectors(String rbiDirectors) {
		this.rbiDirectors = rbiDirectors;
	}

	public String getRbiGroupCompanies() {
		return rbiGroupCompanies;
	}

	public void setRbiGroupCompanies(String rbiGroupCompanies) {
		this.rbiGroupCompanies = rbiGroupCompanies;
	}

	public String getCommericialRealEstate() {
		return commericialRealEstate;
	}

	public void setCommericialRealEstate(String commericialRealEstate) {
		this.commericialRealEstate = commericialRealEstate;
	}

	public String getCommericialRealEstateValue() {
		return commericialRealEstateValue;
	}

	public void setCommericialRealEstateValue(String commericialRealEstateValue) {
		this.commericialRealEstateValue = commericialRealEstateValue;
	}

	public String getCommericialRealEstateResidentialHousing() {
		return commericialRealEstateResidentialHousing;
	}

	public void setCommericialRealEstateResidentialHousing(String commericialRealEstateResidentialHousing) {
		this.commericialRealEstateResidentialHousing = commericialRealEstateResidentialHousing;
	}

	public String getResidentialRealEstate() {
		return residentialRealEstate;
	}

	public void setResidentialRealEstate(String residentialRealEstate) {
		this.residentialRealEstate = residentialRealEstate;
	}

	public String getConductOfAccountWithOtherBanks() {
		return conductOfAccountWithOtherBanks;
	}

	public void setConductOfAccountWithOtherBanks(String conductOfAccountWithOtherBanks) {
		this.conductOfAccountWithOtherBanks = conductOfAccountWithOtherBanks;
	}

	public String getIndirectRealEstate() {
		return indirectRealEstate;
	}

	public void setIndirectRealEstate(String indirectRealEstate) {
		this.indirectRealEstate = indirectRealEstate;
	}

	public String getIsBackedByGovt() {
		return isBackedByGovt;
	}

	public void setIsBackedByGovt(String isBackedByGovt) {
		this.isBackedByGovt = isBackedByGovt;
	}

	public String getCrilicStatus() {
		return crilicStatus;
	}

	public void setCrilicStatus(String crilicStatus) {
		this.crilicStatus = crilicStatus;
	}

//	public String getIsQualifyingNotesPresent() {
//		return isQualifyingNotesPresent;
//	}
//
//	public void setIsQualifyingNotesPresent(String isQualifyingNotesPresent) {
//		this.isQualifyingNotesPresent = isQualifyingNotesPresent;
//	}
//
//	public String getStateImplications() {
//		return stateImplications;
//	}
//
//	public void setStateImplications(String stateImplications) {
//		this.stateImplications = stateImplications;
//	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIsRBIWilfulDefaultersList() {
		return isRBIWilfulDefaultersList;
	}

	public void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaultersList) {
		this.isRBIWilfulDefaultersList = isRBIWilfulDefaultersList;
	}

	public String getNameOfBank() {
		return nameOfBank;
	}

	public void setNameOfBank(String nameOfBank) {
		this.nameOfBank = nameOfBank;
	}

	public String getIsDirectorMoreThanOne() {
		return isDirectorMoreThanOne;
	}

	public void setIsDirectorMoreThanOne(String isDirectorMoreThanOne) {
		this.isDirectorMoreThanOne = isDirectorMoreThanOne;
	}

	public String getNameOfDirectorsAndCompany() {
		return nameOfDirectorsAndCompany;
	}

	public void setNameOfDirectorsAndCompany(String nameOfDirectorsAndCompany) {
		this.nameOfDirectorsAndCompany = nameOfDirectorsAndCompany;
	}

	public String getIsCibilStatusClean() {
		return isCibilStatusClean;
	}

	public void setIsCibilStatusClean(String isCibilStatusClean) {
		this.isCibilStatusClean = isCibilStatusClean;
	}

	public String getDetailsOfCleanCibil() {
		return detailsOfCleanCibil;
	}

	public void setDetailsOfCleanCibil(String detailsOfCleanCibil) {
		this.detailsOfCleanCibil = detailsOfCleanCibil;
	}
	
	@XmlElement(name = "udfMethodDetailList",required=true)
	private List<PartyUdfMethodDetailsRequestDTO> udfMethodDetailList;


	public List<PartyUdfMethodDetailsRequestDTO> getUdfMethodDetailList() {
		return udfMethodDetailList;
	}

	public void setUdfMethodDetailList(List<PartyUdfMethodDetailsRequestDTO> udfMethodDetailList) {
		this.udfMethodDetailList = udfMethodDetailList;
	}


	public String getIsBrokerTypeShare() {
		return isBrokerTypeShare;
	}

	public void setIsBrokerTypeShare(String isBrokerTypeShare) {
		this.isBrokerTypeShare = isBrokerTypeShare;
	}

	public String getIsBrokerTypeCommodity() {
		return isBrokerTypeCommodity;
	}

	public void setIsBrokerTypeCommodity(String isBrokerTypeCommodity) {
		this.isBrokerTypeCommodity = isBrokerTypeCommodity;
	}
	
	//New CR 
	//NEW CAM UI FORMAT START
	@XmlElement(name = "listedCompany",required=true)
	private String listedCompany;
	@XmlElement(name = "isinNo",required=true)
	private String isinNo;
	@XmlElement(name = "raroc",required=true)
	private String raroc;
	@XmlElement(name = "raraocPeriod",required=true)
	private String raraocPeriod;
	@XmlElement(name = "yearEndPeriod",required=true)
	private String yearEndPeriod;
	@XmlElement(name = "creditMgrEmpId",required=true)
	private String creditMgrEmpId;
	@XmlElement(name = "pfLrdCreditMgrEmpId",required=true)
	private String pfLrdCreditMgrEmpId;
	@XmlElement(name = "creditMgrSegment",required=true)
	private String creditMgrSegment;
	@XmlElement(name = "multBankFundBasedLeadBankPer",required=true)
	private String multBankFundBasedLeadBankPer;
	@XmlElement(name = "multBankFundBasedHdfcBankPer",required=true)
	private String multBankFundBasedHdfcBankPer;
	@XmlElement(name = "multBankNonFundBasedLeadBankPer",required=true)
	private String multBankNonFundBasedLeadBankPer;
	@XmlElement(name = "multBankNonFundBasedHdfcBankPer",required=true)
	private String multBankNonFundBasedHdfcBankPer;
	@XmlElement(name = "consBankFundBasedLeadBankPer",required=true)
	private String consBankFundBasedLeadBankPer;
	@XmlElement(name = "consBankFundBasedHdfcBankPer",required=true)
	private String consBankFundBasedHdfcBankPer;
	@XmlElement(name = "consBankNonFundBasedLeadBankPer",required=true)
	private String consBankNonFundBasedLeadBankPer;
	@XmlElement(name = "consBankNonFundBasedHdfcBankPer",required=true)
	private String consBankNonFundBasedHdfcBankPer;

	public String getListedCompany() {
		return listedCompany;
	}

	public void setListedCompany(String listedCompany) {
		this.listedCompany = listedCompany;
	}

	public String getIsinNo() {
		return isinNo;
	}

	public void setIsinNo(String isinNo) {
		this.isinNo = isinNo;
	}

	public String getRaroc() {
		return raroc;
	}

	public void setRaroc(String raroc) {
		this.raroc = raroc;
	}



	public String getRaraocPeriod() {
		return raraocPeriod;
	}

	public void setRaraocPeriod(String raraocPeriod) {
		this.raraocPeriod = raraocPeriod;
	}

	public String getYearEndPeriod() {
		return yearEndPeriod;
	}

	public void setYearEndPeriod(String yearEndPeriod) {
		this.yearEndPeriod = yearEndPeriod;
	}

	public String getCreditMgrEmpId() {
		return creditMgrEmpId;
	}

	public void setCreditMgrEmpId(String creditMgrEmpId) {
		this.creditMgrEmpId = creditMgrEmpId;
	}

	public String getPfLrdCreditMgrEmpId() {
		return pfLrdCreditMgrEmpId;
	}

	public void setPfLrdCreditMgrEmpId(String pfLrdCreditMgrEmpId) {
		this.pfLrdCreditMgrEmpId = pfLrdCreditMgrEmpId;
	}

	public String getCreditMgrSegment() {
		return creditMgrSegment;
	}

	public void setCreditMgrSegment(String creditMgrSegment) {
		this.creditMgrSegment = creditMgrSegment;
	}

	public String getMultBankFundBasedLeadBankPer() {
		return multBankFundBasedLeadBankPer;
	}

	public void setMultBankFundBasedLeadBankPer(String multBankFundBasedLeadBankPer) {
		this.multBankFundBasedLeadBankPer = multBankFundBasedLeadBankPer;
	}

	public String getMultBankNonFundBasedLeadBankPer() {
		return multBankNonFundBasedLeadBankPer;
	}

	public void setMultBankNonFundBasedLeadBankPer(String multBankNonFundBasedLeadBankPer) {
		this.multBankNonFundBasedLeadBankPer = multBankNonFundBasedLeadBankPer;
	}

	public String getConsBankFundBasedHdfcBankPer() {
		return consBankFundBasedHdfcBankPer;
	}

	public void setConsBankFundBasedHdfcBankPer(String consBankFundBasedHdfcBankPer) {
		this.consBankFundBasedHdfcBankPer = consBankFundBasedHdfcBankPer;
	}

	public String getConsBankNonFundBasedHdfcBankPer() {
		return consBankNonFundBasedHdfcBankPer;
	}

	public void setConsBankNonFundBasedHdfcBankPer(String consBankNonFundBasedHdfcBankPer) {
		this.consBankNonFundBasedHdfcBankPer = consBankNonFundBasedHdfcBankPer;
	}

	public String getMultBankFundBasedHdfcBankPer() {
		return multBankFundBasedHdfcBankPer;
	}

	public void setMultBankFundBasedHdfcBankPer(String multBankFundBasedHdfcBankPer) {
		this.multBankFundBasedHdfcBankPer = multBankFundBasedHdfcBankPer;
	}

	public String getMultBankNonFundBasedHdfcBankPer() {
		return multBankNonFundBasedHdfcBankPer;
	}

	public void setMultBankNonFundBasedHdfcBankPer(String multBankNonFundBasedHdfcBankPer) {
		this.multBankNonFundBasedHdfcBankPer = multBankNonFundBasedHdfcBankPer;
	}

	public String getConsBankFundBasedLeadBankPer() {
		return consBankFundBasedLeadBankPer;
	}

	public void setConsBankFundBasedLeadBankPer(String consBankFundBasedLeadBankPer) {
		this.consBankFundBasedLeadBankPer = consBankFundBasedLeadBankPer;
	}

	public String getConsBankNonFundBasedLeadBankPer() {
		return consBankNonFundBasedLeadBankPer;
	}

	public void setConsBankNonFundBasedLeadBankPer(String consBankNonFundBasedLeadBankPer) {
		this.consBankNonFundBasedLeadBankPer = consBankNonFundBasedLeadBankPer;
	}

	public List<BankingMethodRequestDTO> getBankingMethodComboBoxList() {
		return bankingMethodComboBoxList;
	}

	public void setBankingMethodComboBoxList(List<BankingMethodRequestDTO> bankingMethodComboBoxList) {
		this.bankingMethodComboBoxList = bankingMethodComboBoxList;
	}

	public String getBankingMethod() {
		return bankingMethod;
	}

	public void setBankingMethod(String bankingMethod) {
		this.bankingMethod = bankingMethod;
	}
}