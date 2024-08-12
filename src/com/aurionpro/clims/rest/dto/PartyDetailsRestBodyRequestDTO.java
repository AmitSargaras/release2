/**
 * 
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.ws.dto.PartyCoBorrowerDetailsRequestDTO;

public class PartyDetailsRestBodyRequestDTO{
	
	

	private String climsPartyId;
	private String partyName;
	private String event;
	private String mainBranch;	
	private String relationshipManager;
	private String segment;
	private String relationshipMgrCode; //RM Employee Code
	
	private String entity;	
	private String industryName;	

	private String PAN;	
	private String leiCode;
	private String leiExpDate;
	private String address1;
	private String address2;
	private String address3;
	private String pincode;
	
	
		private String relationshipStartDate;
		private String businessGroup;	
		
		
		private String cinLlpin; //@New	
		private String dateOfIncorporation; //@New	
		private String RBIIndustryCode;	
		private String cycle; //@New	
		private String form6061; //@New	
		
	
		
		private String country; //@New	
		private String region;
		private String state;
		private String city;
		
		private String telephoneStdCode;
		private String telephoneNo;
		private String faxStdCode;
		private String faxNumber;
		private String emailId;
		
		private String aadharNumber; //@New	
		
		private String multBankFundBasedLeadBankPer;     //@New	
		private String multBankNonFundBasedLeadBankPer;  //@New	
		private String multBankFundBasedHdfcBankPer;     //@New	
		private String multBankNonFundBasedHdfcBankPer;  //@New	
		private String consBankFundBasedLeadBankPer;     //@New	
		private String consBankNonFundBasedLeadBankPer;  //@New	
		private String consBankFundBasedHdfcBankPer;     //@New	
		private String consBankNonFundBasedHdfcBankPer;  //@New	
		
		
		//criFacilityList
		private List<PartycriFacilityDetailsRestRequestDTO> criFacilityList ;
		//vendorDetails List
		private List<PartyVendorDetailsRestRequestDTO> vendorDetReqList;
		//coBorrowerDetails List
		private String coBorrowerDetailsInd;
		private PartyCoBorrowerDetailsRestRequestDTO coBorrowerDetailsList;
		//System Info - Multiple
		private List<PartySystemDetailsRestRequestDTO> systemDetReqList;

		private List<PartyIFSCBankingMethodDetailsRestRequestDTO> ifscBankingMethodDetailList;

//		**************************************************
		
		private String subLine;	
		private List<PartySubLineDetailsRestRequestDTO> subLineDetailsList;  //@New
		
		private String bankingMethod;	
		private String exceptionalCasesSpan;	//@New

		private String iFSCCode;	//@New
		private String bankName;	//@New
		private String bankBranchName;	//@New
		
		
		
		private String totalFundedLimit;	
		private String totalNonFundedLimit;	
		private String fundedSharePercent;
		private String nonFundedSharePercent;
		private String memoExposure;
		
		
		
		private String mpbf;
		
		
		//CRI Info	
		private String customerRAMId;
		private String customerAPRCode;
		private String customerExtRating;
		private String customerFyClouser;//@New

		
		private String nbfcFlag;
		private String nbfcA;
		private String nbfcB;
		private String prioritySector;
		private String msmeClassification;
		private String permSSICert;
		private String weakerSection;
		private String weakerSectionType;
	
		private String weakerSectionValue;
		
		private String kisanCreditCard;
		private String minorityCommunity;
	    private String minorityCommunityType;
		private String capitalMarketExposure;
		private String realEstateExposure;
		private String commodityExposure;
		private String sensitive;
		private String commodityName;
		
		

		private String grossInvestmentPM;
		private String grossInvestmentEquip;
		private String psu;
		private String percentageOfShareholding;

		private String govtUndertaking;
		
		
		private String SEMSGuideApplicable;
	    private String failsUnderIFCExclusion;
		private String tufs;
		private String RBIDefaulterList;
		
		
		private String projectFinance;
		private String infraFinance;
		private String infraFinanceType;
		

		private String litigationPending;
		private String litigationPendingBy;
		private String interestOfDirectors;
		private String interestOfDirectorsType;
		
		
		private String adverseRemark;
		private String adverseRemarkValue;
		private String audit;
		private String avgAnnualTurnover;
		private String industryExposurePercent;
		
		
		private String isBorrowerDirector;
		private String isDirectorOfOtherBank;
		private String directorOfOtherBankValue;
		private String isBorrowerPartner;
		private String borrowerPartnerValue;
		
		
		private String isShareholderRelativeOfBank;
		private String isShareholderRelativeOfDirector;
		private String shareholderRelativeOfBankValue;
		
		private String isRelativeOfChairman;
		private String relativeOfChairmanValue;
		
		//Change for RBI defaulter Type i.e Company ,Director and Group Companies
		private String rbiDefaulterListTypeCompany;
		private String rbiDefaulterListTypeDirectors;
		private String rbiDefaulterListTypeGroupCompanies;
		
		
		private String businessGroupExposureLimit;
		private String borrowerDirectorValue;

		private String isRelativeOfHDFCBank;
		private String relativeOfHDFCBankValue;
		
		private String isPartnerRelativeOfBanks;
		private String partnerRelativeOfBanksValue;
		
		private String backedByGovt;
		private String firstYear;
		private String firstYearTurnover;
		private String turnoverCurrency;
		
		private String secondYear;  //@NEW
		private String secondYearTurnover;  //@NEW
		private String secondYearTurnoverCurr; //@NEW

		private String thirdYear; //@NEW
		private String thirdYearTurnover; //@NEW 
		private String thirdYearTurnoverCurr; //@NEW

		private String isSPVFunding;//@NEW 
		private String indirectCountryRiskExposure;   //@NEW
		
		
		private String criCountryName;  // @New
		private String salesPercentage;  // @New

		private String isCGTMSE;  // @New
		private String isIPRE;  // @New
		
		private String financeForAccquisition;  // @New
		private String facilityApproved;  // @New
		private String facilityAmount;  // @New
		private String securityName;  // @New
		private String securityType;  // @New
		private String securityValue;  // @New
		
		private String companyType;  // @New
		private String nameOfHoldingCompany;  // @New
		private String type;  // @New
		
		private String ifBreachWithPrescriptions ;  // @New
		private String comments ;  // @New
		private String categoryOfFarmer ;  // @New
		private String landHolding ;  // @New
		private String countryOfGuarantor ;  // @New
		private String isAffordableHousingFinance ;  // @New
		private String isInRestrictedList ;  // @New
		private String restrictedFinancing ;  // @New
		private String restrictedIndustries ;  // @New
		private String restrictedListIndustries ;  // @New
		private String isQualifyingNotesPresent ;  // @New
		private String stateImplications ;  // @New
		
		

		private String isBorrowerInRejectDatabase ;  // @New
		private String rejectHistoryReason ;  // @New
		private String capitalForCommodityAndExchange ;  // @New
		private String isBrokerTypeShare ;  // @New
		private String isBrokerTypeCommodity ;  // @New
		private String objectFinance ;  // @New
		private String isCommodityFinanceCustomer ;  // @New
		private String restructuedBorrowerOrFacility ;  // @New
		private String facility ;  // @New
		private String limitAmount ;  // @New
		private String conductOfAccountWithOtherBanks ;  // @New
		private String crilicStatus ;  // @New
		private String crilcComment ;  // @New
		private String subsidyFlag ;  // @New
		private String holdingCompnay ;  // @New
		private String cautionList ;  // @New
		private String dateOfCautionList ;  // @New
		private String company ;  // @New
		private String directors ;  // @New
		private String groupCompanies ;  // @New
		private String defaultersList ;  // @New
		private String rbiDateOfCautionList ;  // @New
		private String rbiCompany ;  // @New
		private String rbiDirectors ;  // @New
		private String rbiGroupCompanies ;  // @New
		private String commericialRealEstate ;  // @New
		private String commericialRealEstateValue ;  // @New
		private String commericialRealEstateResidentialHousing ;  // @New
		private String residentialRealEstate ;  // @New
		private String indirectRealEstate ;  // @New
		
		//*************************************************************
		
		private String borrowerDUNSNo;  // @New
		private String partyConsent;  // @New
		private String classActivity1;  // @New
		private String classActivity2;  // @New
		private String regOffice;  // @New
		private String regOfficeDUNSNo;  // @NewNo
		
		private String registeredAddr1;
		private String registeredAddr2;
		private String registeredAddr3;
		
		private String registeredCountry;
		private String registeredPincode;
		private String registeredRegion;
		private String registeredState;

		private String registeredCity;
		private String registeredTelephoneStdCode;
		private String registeredTelNo;
		
		private String registeredFaxStdCode;
		private String registeredFaxNumber;

		private String 	regOfficeEmail ;  // @New
		private String willfulDefaultStatus ;  // @New
		private String dateWillfulDefault ;  // @New
		private String suitFilledStatus ;  // @New
		private String 	suitReferenceNo ;  // @New
		private String 	suitAmount ;  // @Newv
		private String dateOfSuit ;  // @New
		private String isRBIWilfulDefaultersList ;  // @New	
		private String nameOfBank ;  // @New
		private String isDirectorMoreThanOne ;  // @New
		private String nameOfDirectorsAndCompany ;  // @New
		private String isBorrDefaulterWithBank ;  // @New
		private String detailsOfDefault ;  // @New
		private String extOfCompromiseAndWriteoff ;  // @New
		private String isCibilStatusClean ;  // @New
		private String detailsOfCleanCibil ;  // @New
		private String status;
		private String yearEndPeriod;
		
			
		//private ActionErrors errors;


		//Banking Method - Multiple 
		private List<PartyBankingMethodDetailsRestRequestDTO> bankingMethodDetailList;

		// Directer Info - Multiple
		
		private List<PartyDirectorDetailsRestRequestDTO> directorDetailList;

		public String getClimsPartyId() {
			return climsPartyId;
		}

		public void setClimsPartyId(String climsPartyId) {
			this.climsPartyId = climsPartyId;
		}

		public String getPartyName() {
			return partyName;
		}

		public void setPartyName(String partyName) {
			this.partyName = partyName;
		}

		public String getEvent() {
			return event;
		}

		public void setEvent(String event) {
			this.event = event;
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

		public String getSegment() {
			return segment;
		}

		public void setSegment(String segment) {
			this.segment = segment;
		}

		public String getRelationshipMgrCode() {
			return relationshipMgrCode;
		}

		public void setRelationshipMgrCode(String relationshipMgrCode) {
			this.relationshipMgrCode = relationshipMgrCode;
		}

		public String getEntity() {
			return entity;
		}

		public void setEntity(String entity) {
			this.entity = entity;
		}

		public String getIndustryName() {
			return industryName;
		}

		public void setIndustryName(String industryName) {
			this.industryName = industryName;
		}

		public String getPAN() {
			return PAN;
		}

		public void setPAN(String pAN) {
			PAN = pAN;
		}

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

		public String getPincode() {
			return pincode;
		}

		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

		public String getRelationshipStartDate() {
			return relationshipStartDate;
		}

		public void setRelationshipStartDate(String relationshipStartDate) {
			this.relationshipStartDate = relationshipStartDate;
		}

		public String getBusinessGroup() {
			return businessGroup;
		}

		public void setBusinessGroup(String businessGroup) {
			this.businessGroup = businessGroup;
		}

		public String getCinLlpin() {
			return cinLlpin;
		}

		public void setCinLlpin(String cinLlpin) {
			this.cinLlpin = cinLlpin;
		}

		public String getDateOfIncorporation() {
			return dateOfIncorporation;
		}

		public void setDateOfIncorporation(String dateOfIncorporation) {
			this.dateOfIncorporation = dateOfIncorporation;
		}

		public String getRBIIndustryCode() {
			return RBIIndustryCode;
		}

		public void setRBIIndustryCode(String rBIIndustryCode) {
			RBIIndustryCode = rBIIndustryCode;
		}

		public String getCycle() {
			return cycle;
		}

		public void setCycle(String cycle) {
			this.cycle = cycle;
		}

		public String getForm6061() {
			return form6061;
		}

		public void setForm6061(String form6061) {
			this.form6061 = form6061;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getTelephoneStdCode() {
			return telephoneStdCode;
		}

		public void setTelephoneStdCode(String telephoneStdCode) {
			this.telephoneStdCode = telephoneStdCode;
		}

		public String getTelephoneNo() {
			return telephoneNo;
		}

		public void setTelephoneNo(String telephoneNo) {
			this.telephoneNo = telephoneNo;
		}

		public String getFaxStdCode() {
			return faxStdCode;
		}

		public void setFaxStdCode(String faxStdCode) {
			this.faxStdCode = faxStdCode;
		}

		public String getFaxNumber() {
			return faxNumber;
		}

		public void setFaxNumber(String faxNumber) {
			this.faxNumber = faxNumber;
		}

		public String getEmailId() {
			return emailId;
		}

		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}

		public String getAadharNumber() {
			return aadharNumber;
		}

		public void setAadharNumber(String aadharNumber) {
			this.aadharNumber = aadharNumber;
		}

		public List<PartyVendorDetailsRestRequestDTO> getVendorDetReqList() {
			return vendorDetReqList;
		}

		public void setVendorDetReqList(List<PartyVendorDetailsRestRequestDTO> vendorDetReqList) {
			this.vendorDetReqList = vendorDetReqList;
		}
		
		public List<PartycriFacilityDetailsRestRequestDTO> getCriFacilityList() {
			return criFacilityList;
		}

		public void setCriFacilityList(List<PartycriFacilityDetailsRestRequestDTO> criFacilityList) {
			this.criFacilityList = criFacilityList;
		}

		public String getCoBorrowerDetailsInd() {
			return coBorrowerDetailsInd;
		}

		public void setCoBorrowerDetailsInd(String coBorrowerDetailsInd) {
			this.coBorrowerDetailsInd = coBorrowerDetailsInd;
		}

		
		public PartyCoBorrowerDetailsRestRequestDTO getCoBorrowerDetailsList() {
			return coBorrowerDetailsList;
		}

		public void setCoBorrowerDetailsList(PartyCoBorrowerDetailsRestRequestDTO coBorrowerDetailsList) {
			this.coBorrowerDetailsList = coBorrowerDetailsList;
		}

		public List<PartySystemDetailsRestRequestDTO> getSystemDetReqList() {
			return systemDetReqList;
		}

		public void setSystemDetReqList(List<PartySystemDetailsRestRequestDTO> systemDetReqList) {
			this.systemDetReqList = systemDetReqList;
		}

		public String getSubLine() {
			return subLine;
		}

		public void setSubLine(String subLine) {
			this.subLine = subLine;
		}

		public List<PartySubLineDetailsRestRequestDTO> getSubLineDetailsList() {
			return subLineDetailsList;
		}

		public void setSubLineDetailsList(List<PartySubLineDetailsRestRequestDTO> subLineDetailsList) {
			this.subLineDetailsList = subLineDetailsList;
		}

		
		public List<PartyIFSCBankingMethodDetailsRestRequestDTO> getIfscBankingMethodDetailList() {
			return ifscBankingMethodDetailList;
		}

		public void setIfscBankingMethodDetailList(
				List<PartyIFSCBankingMethodDetailsRestRequestDTO> ifscBankingMethodDetailList) {
			this.ifscBankingMethodDetailList = ifscBankingMethodDetailList;
		}

		public String getBankingMethod() {
			return bankingMethod;
		}

		public void setBankingMethod(String bankingMethod) {
			this.bankingMethod = bankingMethod;
		}

		public String getExceptionalCasesSpan() {
			return exceptionalCasesSpan;
		}

		public void setExceptionalCasesSpan(String exceptionalCasesSpan) {
			this.exceptionalCasesSpan = exceptionalCasesSpan;
		}

	
		public String getiFSCCode() {
			return iFSCCode;
		}

		public void setiFSCCode(String iFSCCode) {
			this.iFSCCode = iFSCCode;
		}

		public String getBankName() {
			return bankName;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public String getBankBranchName() {
			return bankBranchName;
		}

		public void setBankBranchName(String bankBranchName) {
			this.bankBranchName = bankBranchName;
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

		public String getNonFundedSharePercent() {
			return nonFundedSharePercent;
		}

		public void setNonFundedSharePercent(String nonFundedSharePercent) {
			this.nonFundedSharePercent = nonFundedSharePercent;
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

		public String getCustomerRAMId() {
			return customerRAMId;
		}

		public void setCustomerRAMId(String customerRAMId) {
			this.customerRAMId = customerRAMId;
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

		public String getCustomerFyClouser() {
			return customerFyClouser;
		}

		public void setCustomerFyClouser(String customerFyClouser) {
			this.customerFyClouser = customerFyClouser;
		}

		public String getNbfcFlag() {
			return nbfcFlag;
		}

		public void setNbfcFlag(String nbfcFlag) {
			this.nbfcFlag = nbfcFlag;
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

		public String getWeakerSectionType() {
			return weakerSectionType;
		}

		public void setWeakerSectionType(String weakerSectionType) {
			this.weakerSectionType = weakerSectionType;
		}

		

		public String getWeakerSectionValue() {
			return weakerSectionValue;
		}

		public void setWeakerSectionValue(String weakerSectionValue) {
			this.weakerSectionValue = weakerSectionValue;
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

		public String getMinorityCommunityType() {
			return minorityCommunityType;
		}

		public void setMinorityCommunityType(String minorityCommunityType) {
			this.minorityCommunityType = minorityCommunityType;
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

		public String getSEMSGuideApplicable() {
			return SEMSGuideApplicable;
		}

		public void setSEMSGuideApplicable(String sEMSGuideApplicable) {
			SEMSGuideApplicable = sEMSGuideApplicable;
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

		public void setRBIDefaulterList(String rBIDefaulterList) {
			RBIDefaulterList = rBIDefaulterList;
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

		public String getLitigationPending() {
			return litigationPending;
		}

		public void setLitigationPending(String litigationPending) {
			this.litigationPending = litigationPending;
		}

		public String getLitigationPendingBy() {
			return litigationPendingBy;
		}

		public void setLitigationPendingBy(String litigationPendingBy) {
			this.litigationPendingBy = litigationPendingBy;
		}

		public String getInterestOfDirectors() {
			return interestOfDirectors;
		}

		public void setInterestOfDirectors(String interestOfDirectors) {
			this.interestOfDirectors = interestOfDirectors;
		}

		public String getInterestOfDirectorsType() {
			return interestOfDirectorsType;
		}

		public void setInterestOfDirectorsType(String interestOfDirectorsType) {
			this.interestOfDirectorsType = interestOfDirectorsType;
		}

		public String getAdverseRemark() {
			return adverseRemark;
		}

		public void setAdverseRemark(String adverseRemark) {
			this.adverseRemark = adverseRemark;
		}

		public String getAdverseRemarkValue() {
			return adverseRemarkValue;
		}

		public void setAdverseRemarkValue(String adverseRemarkValue) {
			this.adverseRemarkValue = adverseRemarkValue;
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

		public String getIndustryExposurePercent() {
			return industryExposurePercent;
		}

		public void setIndustryExposurePercent(String industryExposurePercent) {
			this.industryExposurePercent = industryExposurePercent;
		}

		public String getIsBorrowerDirector() {
			return isBorrowerDirector;
		}

		public void setIsBorrowerDirector(String isBorrowerDirector) {
			this.isBorrowerDirector = isBorrowerDirector;
		}

		public String getIsDirectorOfOtherBank() {
			return isDirectorOfOtherBank;
		}

		public void setIsDirectorOfOtherBank(String isDirectorOfOtherBank) {
			this.isDirectorOfOtherBank = isDirectorOfOtherBank;
		}

		public String getDirectorOfOtherBankValue() {
			return directorOfOtherBankValue;
		}

		public void setDirectorOfOtherBankValue(String directorOfOtherBankValue) {
			this.directorOfOtherBankValue = directorOfOtherBankValue;
		}

		public String getIsBorrowerPartner() {
			return isBorrowerPartner;
		}

		public void setIsBorrowerPartner(String isBorrowerPartner) {
			this.isBorrowerPartner = isBorrowerPartner;
		}

		public String getBorrowerPartnerValue() {
			return borrowerPartnerValue;
		}

		public void setBorrowerPartnerValue(String borrowerPartnerValue) {
			this.borrowerPartnerValue = borrowerPartnerValue;
		}

		public String getIsShareholderRelativeOfBank() {
			return isShareholderRelativeOfBank;
		}

		public void setIsShareholderRelativeOfBank(String isShareholderRelativeOfBank) {
			this.isShareholderRelativeOfBank = isShareholderRelativeOfBank;
		}

		public String getIsShareholderRelativeOfDirector() {
			return isShareholderRelativeOfDirector;
		}

		public void setIsShareholderRelativeOfDirector(String isShareholderRelativeOfDirector) {
			this.isShareholderRelativeOfDirector = isShareholderRelativeOfDirector;
		}

		public String getShareholderRelativeOfBankValue() {
			return shareholderRelativeOfBankValue;
		}

		public void setShareholderRelativeOfBankValue(String shareholderRelativeOfBankValue) {
			this.shareholderRelativeOfBankValue = shareholderRelativeOfBankValue;
		}

		public String getIsRelativeOfChairman() {
			return isRelativeOfChairman;
		}

		public void setIsRelativeOfChairman(String isRelativeOfChairman) {
			this.isRelativeOfChairman = isRelativeOfChairman;
		}

		public String getRelativeOfChairmanValue() {
			return relativeOfChairmanValue;
		}

		public void setRelativeOfChairmanValue(String relativeOfChairmanValue) {
			this.relativeOfChairmanValue = relativeOfChairmanValue;
		}

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

		public String getBusinessGroupExposureLimit() {
			return businessGroupExposureLimit;
		}

		public void setBusinessGroupExposureLimit(String businessGroupExposureLimit) {
			this.businessGroupExposureLimit = businessGroupExposureLimit;
		}

		public String getBorrowerDirectorValue() {
			return borrowerDirectorValue;
		}

		public void setBorrowerDirectorValue(String borrowerDirectorValue) {
			this.borrowerDirectorValue = borrowerDirectorValue;
		}

		public String getIsRelativeOfHDFCBank() {
			return isRelativeOfHDFCBank;
		}

		public void setIsRelativeOfHDFCBank(String isRelativeOfHDFCBank) {
			this.isRelativeOfHDFCBank = isRelativeOfHDFCBank;
		}

		public String getRelativeOfHDFCBankValue() {
			return relativeOfHDFCBankValue;
		}

		public void setRelativeOfHDFCBankValue(String relativeOfHDFCBankValue) {
			this.relativeOfHDFCBankValue = relativeOfHDFCBankValue;
		}

		public String getIsPartnerRelativeOfBanks() {
			return isPartnerRelativeOfBanks;
		}

		public void setIsPartnerRelativeOfBanks(String isPartnerRelativeOfBanks) {
			this.isPartnerRelativeOfBanks = isPartnerRelativeOfBanks;
		}

		public String getPartnerRelativeOfBanksValue() {
			return partnerRelativeOfBanksValue;
		}

		public void setPartnerRelativeOfBanksValue(String partnerRelativeOfBanksValue) {
			this.partnerRelativeOfBanksValue = partnerRelativeOfBanksValue;
		}

		public String getBackedByGovt() {
			return backedByGovt;
		}

		public void setBackedByGovt(String backedByGovt) {
			this.backedByGovt = backedByGovt;
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

		public String getSecondYear() {
			return secondYear;
		}

		public void setSecondYear(String secondYear) {
			this.secondYear = secondYear;
		}

		public String getSecondYearTurnover() {
			return secondYearTurnover;
		}

		public void setSecondYearTurnover(String secondYearTurnover) {
			this.secondYearTurnover = secondYearTurnover;
		}

		public String getSecondYearTurnoverCurr() {
			return secondYearTurnoverCurr;
		}

		public void setSecondYearTurnoverCurr(String secondYearTurnoverCurr) {
			this.secondYearTurnoverCurr = secondYearTurnoverCurr;
		}

		public String getThirdYear() {
			return thirdYear;
		}

		public void setThirdYear(String thirdYear) {
			this.thirdYear = thirdYear;
		}

		public String getThirdYearTurnover() {
			return thirdYearTurnover;
		}

		public void setThirdYearTurnover(String thirdYearTurnover) {
			this.thirdYearTurnover = thirdYearTurnover;
		}

		public String getThirdYearTurnoverCurr() {
			return thirdYearTurnoverCurr;
		}

		public void setThirdYearTurnoverCurr(String thirdYearTurnoverCurr) {
			this.thirdYearTurnoverCurr = thirdYearTurnoverCurr;
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

		public String getIsCGTMSE() {
			return isCGTMSE;
		}

		public void setIsCGTMSE(String isCGTMSE) {
			this.isCGTMSE = isCGTMSE;
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

		public String getIfBreachWithPrescriptions() {
			return ifBreachWithPrescriptions;
		}

		public void setIfBreachWithPrescriptions(String ifBreachWithPrescriptions) {
			this.ifBreachWithPrescriptions = ifBreachWithPrescriptions;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
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

		public String getIsQualifyingNotesPresent() {
			return isQualifyingNotesPresent;
		}

		public void setIsQualifyingNotesPresent(String isQualifyingNotesPresent) {
			this.isQualifyingNotesPresent = isQualifyingNotesPresent;
		}

		public String getStateImplications() {
			return stateImplications;
		}

		public void setStateImplications(String stateImplications) {
			this.stateImplications = stateImplications;
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

		public String getConductOfAccountWithOtherBanks() {
			return conductOfAccountWithOtherBanks;
		}

		public void setConductOfAccountWithOtherBanks(String conductOfAccountWithOtherBanks) {
			this.conductOfAccountWithOtherBanks = conductOfAccountWithOtherBanks;
		}

		public String getCrilicStatus() {
			return crilicStatus;
		}

		public void setCrilicStatus(String crilicStatus) {
			this.crilicStatus = crilicStatus;
		}

		
		public String getCrilcComment() {
			return crilcComment;
		}

		public void setCrilcComment(String crilcComment) {
			this.crilcComment = crilcComment;
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

		public String getIndirectRealEstate() {
			return indirectRealEstate;
		}

		public void setIndirectRealEstate(String indirectRealEstate) {
			this.indirectRealEstate = indirectRealEstate;
		}

		public String getBorrowerDUNSNo() {
			return borrowerDUNSNo;
		}

		public void setBorrowerDUNSNo(String borrowerDUNSNo) {
			this.borrowerDUNSNo = borrowerDUNSNo;
		}

		

		public String getPartyConsent() {
			return partyConsent;
		}

		public void setPartyConsent(String partyConsent) {
			this.partyConsent = partyConsent;
		}

		public String getClassActivity1() {
			return classActivity1;
		}

		public void setClassActivity1(String classActivity1) {
			this.classActivity1 = classActivity1;
		}

		public String getClassActivity2() {
			return classActivity2;
		}

		public void setClassActivity2(String classActivity2) {
			this.classActivity2 = classActivity2;
		}

		

		public String getRegOffice() {
			return regOffice;
		}

		public void setRegOffice(String regOffice) {
			this.regOffice = regOffice;
		}

		

		public String getRegOfficeDUNSNo() {
			return regOfficeDUNSNo;
		}

		public void setRegOfficeDUNSNo(String regOfficeDUNSNo) {
			this.regOfficeDUNSNo = regOfficeDUNSNo;
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

		public String getRegisteredCountry() {
			return registeredCountry;
		}

		public void setRegisteredCountry(String registeredCountry) {
			this.registeredCountry = registeredCountry;
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

		public String getRegisteredState() {
			return registeredState;
		}

		public void setRegisteredState(String registeredState) {
			this.registeredState = registeredState;
		}

		public String getRegisteredCity() {
			return registeredCity;
		}

		public void setRegisteredCity(String registeredCity) {
			this.registeredCity = registeredCity;
		}

		public String getRegisteredTelephoneStdCode() {
			return registeredTelephoneStdCode;
		}

		public void setRegisteredTelephoneStdCode(String registeredTelephoneStdCode) {
			this.registeredTelephoneStdCode = registeredTelephoneStdCode;
		}

		public String getRegisteredTelNo() {
			return registeredTelNo;
		}

		public void setRegisteredTelNo(String registeredTelNo) {
			this.registeredTelNo = registeredTelNo;
		}

		public String getRegisteredFaxStdCode() {
			return registeredFaxStdCode;
		}

		public void setRegisteredFaxStdCode(String registeredFaxStdCode) {
			this.registeredFaxStdCode = registeredFaxStdCode;
		}

		public String getRegisteredFaxNumber() {
			return registeredFaxNumber;
		}

		public void setRegisteredFaxNumber(String registeredFaxNumber) {
			this.registeredFaxNumber = registeredFaxNumber;
		}

		public String getRegOfficeEmail() {
			return regOfficeEmail;
		}

		public void setRegOfficeEmail(String regOfficeEmail) {
			this.regOfficeEmail = regOfficeEmail;
		}

		public String getWillfulDefaultStatus() {
			return willfulDefaultStatus;
		}

		public void setWillfulDefaultStatus(String willfulDefaultStatus) {
			this.willfulDefaultStatus = willfulDefaultStatus;
		}

		public String getDateWillfulDefault() {
			return dateWillfulDefault;
		}

		public void setDateWillfulDefault(String dateWillfulDefault) {
			this.dateWillfulDefault = dateWillfulDefault;
		}

		public String getSuitFilledStatus() {
			return suitFilledStatus;
		}

		public void setSuitFilledStatus(String suitFilledStatus) {
			this.suitFilledStatus = suitFilledStatus;
		}

		public String getSuitReferenceNo() {
			return suitReferenceNo;
		}

		public void setSuitReferenceNo(String suitReferenceNo) {
			this.suitReferenceNo = suitReferenceNo;
		}

		public String getSuitAmount() {
			return suitAmount;
		}

		public void setSuitAmount(String suitAmount) {
			this.suitAmount = suitAmount;
		}

		public String getDateOfSuit() {
			return dateOfSuit;
		}

		public void setDateOfSuit(String dateOfSuit) {
			this.dateOfSuit = dateOfSuit;
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

		public String getIsBorrDefaulterWithBank() {
			return isBorrDefaulterWithBank;
		}

		public void setIsBorrDefaulterWithBank(String isBorrDefaulterWithBank) {
			this.isBorrDefaulterWithBank = isBorrDefaulterWithBank;
		}

		public String getDetailsOfDefault() {
			return detailsOfDefault;
		}

		public void setDetailsOfDefault(String detailsOfDefault) {
			this.detailsOfDefault = detailsOfDefault;
		}

		public String getExtOfCompromiseAndWriteoff() {
			return extOfCompromiseAndWriteoff;
		}

		public void setExtOfCompromiseAndWriteoff(String extOfCompromiseAndWriteoff) {
			this.extOfCompromiseAndWriteoff = extOfCompromiseAndWriteoff;
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

		public List<PartyBankingMethodDetailsRestRequestDTO> getBankingMethodDetailList() {
			return bankingMethodDetailList;
		}

		public void setBankingMethodDetailList(List<PartyBankingMethodDetailsRestRequestDTO> bankList) {
			this.bankingMethodDetailList = bankList;
		}

		public List<PartyDirectorDetailsRestRequestDTO> getDirectorDetailList() {
			return directorDetailList;
		}

		public void setDirectorDetailList(List<PartyDirectorDetailsRestRequestDTO> directorDetailList) {
			this.directorDetailList = directorDetailList;
		}

		


		
		// Contact Fields
		//	private String contactType;
		

		
		//UDF Fields?? Need discussion
		


		private List<UdfRestRequestDTO> udfList;

		public List<UdfRestRequestDTO> getUdfList() {
			return udfList;
		}

		public void setUdfList(List<UdfRestRequestDTO> udfList) {
			this.udfList = udfList;
		}


		private String dpSharePercent;  //newReview
		private String isNBFC;  //newReview
		private String entityType;  //newReview
		
		public String getDpSharePercent() {
			return dpSharePercent;
		}

		public void setDpSharePercent(String dpSharePercent) {
			this.dpSharePercent = dpSharePercent;
		}

		public String getIsNBFC() {
			return isNBFC;
		}

		public void setIsNBFC(String isNBFC) {
			this.isNBFC = isNBFC;
		}

		public String getEntityType() {
			return entityType;
		}

		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}

	
		public String getIsSPVFunding() {
			return isSPVFunding;
		}

		public void setIsSPVFunding(String isSPVFunding) {
			this.isSPVFunding = isSPVFunding;
		}





		private ActionErrors errors;
		public ActionErrors getErrors() {
			return errors;
		}
		public void setErrors(ActionErrors errors) {
			this.errors = errors;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getYearEndPeriod() {
			return yearEndPeriod;
		}

		public void setYearEndPeriod(String yearEndPeriod) {
			this.yearEndPeriod = yearEndPeriod;
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
		
		
}
