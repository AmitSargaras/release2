/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBCriInfo implements ICriInfo {
	private long _criInfoID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
	private long LEID;

	private  String customerRamID;
	

	
	private  String customerAprCode;

	private  String customerExtRating;

	private  String isNbfs;
	            
	private  String nbfsA;
	
	private  String nbfsB;
	
	private  String isPrioritySector;
	
	private  String msmeClassification;
	                
	private  String isPermenentSsiCert;
	            
	private  String isWeakerSection;
	
	private  String weakerSection;
	
	private  String govtSchemeType;
	
	private  String isKisanCreditCard;
	
	private  String isMinorityCommunity;
	                            
	private  String minorityCommunity;
	                
	private  String isCapitalMarketExpos;
	            
	private  String isRealEstateExpos;
	
	private  String realEstateExposType;
	
	private  String realEstateExposComm;
	
	private  String isCommoditiesExposer;
	                 
	private  String isSensitive;
	
	private  String commoditiesName;
	
	private  String grossInvsInPM;
	
	/*private  String groupExposer;*/
	                      
	private  String grossInvsInEquip;
	
	private  String psu;
	
	private  String psuPercentage;
	                                 
	private  String govtUnderTaking;
	
	private  String isProjectFinance;
	                                 
	private  String isInfrastructureFinanace;
	                                 
	private  String infrastructureFinanaceType;
	
	private  String isSemsGuideApplicable;
	                                 
	private  String isFailIfcExcluList;
	
	private  String isTufs;
	                                 
	private  String isRbiDefaulter;
	                                 
	private  String rbiDefaulter;
	
	private  String isLitigation;
	
	private  String litigationBy;
	                                 
	private  String isInterestOfBank;
	
	private  String interestOfBank;
	               
	private  String isAdverseRemark;
	                                 
	private  String adverseRemark;
	
	private  String auditType;
	                   
	private  String avgAnnualTurnover;
	                                 
	private  String rbiIndustryCode;
	
	private  String industry;
	
	private  String industryExposer;
	                                 
	private  String isDirecOtherBank;
	
	private  String direcOtherBank;
	                                 
	private  String isPartnerOtherBank;

	private  String partnerOtherBank;

	private  String isSubstantialOtherBank;

	private  String substantialOtherBank;

	private  String isHdfcDirecRltv;

	private  String hdfcDirecRltv;

	private  String isObkDirecRltv;

	private  String obkDirecRltv;

	private  String isPartnerDirecRltv;

	private  String partnerDirecRltv;

	private  String isSubstantialRltvHdfcOther;

	private  String substantialRltvHdfcOther;

	private  String direcHdfcOther;
	
	private  String isBackedByGovt;
	
    private String firstYear;
	
	private String firstYearTurnover;
	
	private String firstYearTurnoverCurr;
	
    private String secondYear;
	
	private String secondYearTurnover;
	
	private String secondYearTurnoverCurr;
	
    private String thirdYear;
	
	private String thirdYearTurnover;
	
	private String thirdYearTurnoverCurr;
	
	//CRI 
	private String categoryOfFarmer;
	
	private String entityType;
	
	private String countryName;
	
	private String	salesPercentage;
	


	private String isCGTMSE;
	
	private String financeForAccquisition;
	
	private String facilityApproved;
	
	private String isIPRE;
	
	private String facilityAmount;


	private String securityName;
	
	private String securityType;
	


	private String securityValue;
	
	
	private String company;
	
	
	private String type;
	
	private String	companyType;
	
	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	private String	ifBreachWithPrescriptions;
	
	private String	comments;
	
	private String landHolding;
	
	
	private String	countryOfGuarantor;
	
	private String	isAffordableHousingFinance;
	
	
	private String	isInRestrictedList;
	
	private String	restrictedFinancing;
	
	private String restrictedIndustries;
	
	
	private String	isQualifyingNotesPresent;
	
	
	private String	stateImplications;
	
	
	private String	isBorrowerInRejectDatabase;
	
	
	
	private String rejectHistoryReason;
	
	
	private String	capitalForCommodityAndExchange;
	
	private String isBrokerTypeShare;
	private String isBrokerTypeCommodity;
	
	/*private String odfdCategory;*/
	
	private String	objectFinance;
	
	private String	isCommodityFinanceCustomer;
	
	private String	facility;
	
	private String	limitAmount;
	
	private String	conductOfAccountWithOtherBanks;
	
	private String	crilicStatus;
	
	private String	holdingCompnay;
	
	private String	dateOfCautionList;
	
	private String	directors;
	
	private String	groupCompanies;
	
	
	private String	defaultersList;
	
	
	private String	rbiCompany;
	
	private String	rbiDirectors;
	
	private String restrictedListIndustries;
	
	private String criCountryName;
	
	private String customerFyClouser;
	
	
	public String getCriCountryName() {
		return criCountryName;
	}

	public void setCriCountryName(String criCountryName) {
		this.criCountryName = criCountryName;
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

	private String	rbiGroupCompanies;
	
	
	
	public String getRbiDateOfCautionList() {
		return rbiDateOfCautionList;
	}

	public void setRbiDateOfCautionList(String rbiDateOfCautionList) {
		this.rbiDateOfCautionList = rbiDateOfCautionList;
	}

	private String rbiDateOfCautionList ;
	
	
	private String commericialRealEstate ;
	
	
	public String getCommericialRealEstateValue() {
		return commericialRealEstateValue;
	}

	public void setCommericialRealEstateValue(String commericialRealEstateValue) {
		this.commericialRealEstateValue = commericialRealEstateValue;
	}

	private String commericialRealEstateValue ;	
	
	
	private String commericialRealEstateResidentialHousing;
	private String residentialRealEstate;
	private String indirectRealEstate;
	
	
	
	
	
	
	
	
	
	public String getCommericialRealEstateResidentialHousing() {
		return commericialRealEstateResidentialHousing;
	}

	public void setCommericialRealEstateResidentialHousing(
			String commericialRealEstateResidentialHousing) {
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

	public String getCommericialRealEstate() {
		return commericialRealEstate;
	}

	public void setCommericialRealEstate(String commericialRealEstate) {
		this.commericialRealEstate = commericialRealEstate;
	}

	public String getDefaultersList() {
		return defaultersList;
	}

	public void setDefaultersList(String defaultersList) {
		this.defaultersList = defaultersList;
	}

	public String getGroupCompanies() {
		return groupCompanies;
	}

	public void setGroupCompanies(String groupCompanies) {
		this.groupCompanies = groupCompanies;
	}

	public String getDirectors() {
		return directors;
	}

	public void setDirectors(String directors) {
		this.directors = directors;
	}

	public String getDateOfCautionList() {
		return dateOfCautionList;
	}

	public void setDateOfCautionList(String dateOfCautionList) {
		this.dateOfCautionList = dateOfCautionList;
	}

	public String getCautionList() {
		return cautionList;
	}

	public void setCautionList(String cautionList) {
		this.cautionList = cautionList;
	}

	private String	cautionList;
	
	
	
	
	public String getHoldingCompnay() {
		return holdingCompnay;
	}

	public void setHoldingCompnay(String holdingCompnay) {
		this.holdingCompnay = holdingCompnay;
	}

	public String getSubsidyFlag() {
		return subsidyFlag;
	}

	public void setSubsidyFlag(String subsidyFlag) {
		this.subsidyFlag = subsidyFlag;
	}

	private String	subsidyFlag;
	
	
	


	private String comment;
	
	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getCrilicStatus() {
		return crilicStatus;
	}

	public void setCrilicStatus(String crilicStatus) {
		this.crilicStatus = crilicStatus;
	}

	public String getConductOfAccountWithOtherBanks() {
		return conductOfAccountWithOtherBanks;
	}

	public void setConductOfAccountWithOtherBanks(
			String conductOfAccountWithOtherBanks) {
		this.conductOfAccountWithOtherBanks = conductOfAccountWithOtherBanks;
	}

	public String getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getRestructuedBorrowerOrFacility() {
		return restructuedBorrowerOrFacility;
	}

	public void setRestructuedBorrowerOrFacility(
			String restructuedBorrowerOrFacility) {
		this.restructuedBorrowerOrFacility = restructuedBorrowerOrFacility;
	}

	private String	restructuedBorrowerOrFacility;
	
	
	public String getIsCommodityFinanceCustomer() {
		return isCommodityFinanceCustomer;
	}

	public void setIsCommodityFinanceCustomer(String isCommodityFinanceCustomer) {
		this.isCommodityFinanceCustomer = isCommodityFinanceCustomer;
	}

	public String getObjectFinance() {
		return objectFinance;
	}

	public void setObjectFinance(String objectFinance) {
		this.objectFinance = objectFinance;
	}
	
	
	/*public String getOdfdCategory() {
		return odfdCategory;
	}

	public void setOdfdCategory(String odfdCategory) {
		this.odfdCategory = odfdCategory;
	}*/

	public String getCapitalForCommodityAndExchange() {
		return capitalForCommodityAndExchange;
	}

	public void setCapitalForCommodityAndExchange(
			String capitalForCommodityAndExchange) {
		this.capitalForCommodityAndExchange = capitalForCommodityAndExchange;
	}

	public String getRejectHistoryReason() {
		return rejectHistoryReason;
	}

	public void setRejectHistoryReason(String rejectHistoryReason) {
		this.rejectHistoryReason = rejectHistoryReason;
	}

	public String getIsBorrowerInRejectDatabase() {
		return isBorrowerInRejectDatabase;
	}

	public void setIsBorrowerInRejectDatabase(String isBorrowerInRejectDatabase) {
		this.isBorrowerInRejectDatabase = isBorrowerInRejectDatabase;
	}

	public String getStateImplications() {
		return stateImplications;
	}

	public void setStateImplications(String stateImplications) {
		this.stateImplications = stateImplications;
	}

	public String getIsQualifyingNotesPresent() {
		return isQualifyingNotesPresent;
	}

	public void setIsQualifyingNotesPresent(String isQualifyingNotesPresent) {
		this.isQualifyingNotesPresent = isQualifyingNotesPresent;
	}

	public String getRestrictedIndustries() {
		return restrictedIndustries;
	}

	public void setRestrictedIndustries(String restrictedIndustries) {
		this.restrictedIndustries = restrictedIndustries;
	}

	public String getRestrictedFinancing() {
		return restrictedFinancing;
	}

	public void setRestrictedFinancing(String restrictedFinancing) {
		this.restrictedFinancing = restrictedFinancing;
	}

	public String getIsInRestrictedList() {
		return isInRestrictedList;
	}

	public void setIsInRestrictedList(String isInRestrictedList) {
		this.isInRestrictedList = isInRestrictedList;
	}

	public String getIsAffordableHousingFinance() {
		return isAffordableHousingFinance;
	}

	public void setIsAffordableHousingFinance(String isAffordableHousingFinance) {
		this.isAffordableHousingFinance = isAffordableHousingFinance;
	}

	public String getCountryOfGuarantor() {
		return countryOfGuarantor;
	}

	public void setCountryOfGuarantor(String countryOfGuarantor) {
		this.countryOfGuarantor = countryOfGuarantor;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIfBreachWithPrescriptions() {
		return ifBreachWithPrescriptions;
	}

	public void setIfBreachWithPrescriptions(String ifBreachWithPrescriptions) {
		this.ifBreachWithPrescriptions = ifBreachWithPrescriptions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String	nameOfHoldingCompany;
	
	public String getNameOfHoldingCompany() {
		return nameOfHoldingCompany;
	}

	public void setNameOfHoldingCompany(String nameOfHoldingCompany) {
		this.nameOfHoldingCompany = nameOfHoldingCompany;
	}
	
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSecurityValue() {
		return securityValue;
	}

	public void setSecurityValue(String securityValue) {
		this.securityValue = securityValue;
	}
	
	
	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	
	
	public String getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public String getFacilityApproved() {
		return facilityApproved;
	}

	public void setFacilityApproved(String facilityApproved) {
		this.facilityApproved = facilityApproved;
	}
	
	public String getFinanceForAccquisition() {
		return financeForAccquisition;
	}

	public void setFinanceForAccquisition(String financeForAccquisition) {
		this.financeForAccquisition = financeForAccquisition;
	}
	
	public String getIsIPRE() {
		return isIPRE;
	}

	public void setIsIPRE(String isIPRE) {
		this.isIPRE = isIPRE;
	}

	public String getIsCGTMSE() {
		return isCGTMSE;
	}

	public void setIsCGTMSE(String isCGTMSE) {
		this.isCGTMSE = isCGTMSE;
	}


	
	
	public String getSalesPercentage() {
		return salesPercentage;
	}

	public void setSalesPercentage(String salesPercentage) {
		this.salesPercentage = salesPercentage;
	}


	


	
	
	
	public String getIsSPVFunding() {
		return isSPVFunding;
	}

	public void setIsSPVFunding(String isSPVFunding) {
		this.isSPVFunding = isSPVFunding;
	}

	private String isSPVFunding;
	
	public String getIndirectCountryRiskExposure() {
		return indirectCountryRiskExposure;
	}

	public void setIndirectCountryRiskExposure(String indirectCountryRiskExposure) {
		this.indirectCountryRiskExposure = indirectCountryRiskExposure;
	}

	private String indirectCountryRiskExposure;




	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * Default Constructor
	 */
	public OBCriInfo() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBCriInfo(ICriInfo value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	
	public long getCriInfoID() {
		return _criInfoID;
	}

	public void setCriInfoID(long criInfoID) {
		_criInfoID = criInfoID;
	}

	public long getLEID() {
		return LEID;
	}

	
	public void setLEID(long LEID) {
		this.LEID = LEID;
		
	}
	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String getCustomerRamID() {
		return customerRamID;
	}

	public void setCustomerRamID(String customerRamID) {
		this.customerRamID = customerRamID;
	}

	public String getCustomerAprCode() {
		return customerAprCode;
	}

	public void setCustomerAprCode(String customerAprCode) {
		this.customerAprCode = customerAprCode;
	}

	public String getCustomerExtRating() {
		return customerExtRating;
	}

	public void setCustomerExtRating(String customerExtRating) {
		this.customerExtRating = customerExtRating;
	}

	public String getIsNbfs() {
		return isNbfs;
	}

	public void setIsNbfs(String isNbfs) {
		this.isNbfs = isNbfs;
	}

	public String getNbfsA() {
		return nbfsA;
	}

	public void setNbfsA(String nbfsA) {
		this.nbfsA = nbfsA;
	}

	public String getNbfsB() {
		return nbfsB;
	}

	public void setNbfsB(String nbfsB) {
		this.nbfsB = nbfsB;
	}

	public String getIsPrioritySector() {
		return isPrioritySector;
	}

	public void setIsPrioritySector(String isPrioritySector) {
		this.isPrioritySector = isPrioritySector;
	}

	public String getMsmeClassification() {
		return msmeClassification;
	}

	public void setMsmeClassification(String msmeClassification) {
		this.msmeClassification = msmeClassification;
	}

	public String getIsPermenentSsiCert() {
		return isPermenentSsiCert;
	}

	public void setIsPermenentSsiCert(String isPermenentSsiCert) {
		this.isPermenentSsiCert = isPermenentSsiCert;
	}

	public String getIsWeakerSection() {
		return isWeakerSection;
	}

	public void setIsWeakerSection(String isWeakerSection) {
		this.isWeakerSection = isWeakerSection;
	}

	public String getWeakerSection() {
		return weakerSection;
	}

	public void setWeakerSection(String weakerSection) {
		this.weakerSection = weakerSection;
	}

	public String getIsKisanCreditCard() {
		return isKisanCreditCard;
	}

	public void setIsKisanCreditCard(String isKisanCreditCard) {
		this.isKisanCreditCard = isKisanCreditCard;
	}

	public String getIsMinorityCommunity() {
		return isMinorityCommunity;
	}

	public void setIsMinorityCommunity(String isMinorityCommunity) {
		this.isMinorityCommunity = isMinorityCommunity;
	}

	public String getMinorityCommunity() {
		return minorityCommunity;
	}

	public void setMinorityCommunity(String minorityCommunity) {
		this.minorityCommunity = minorityCommunity;
	}

	public String getIsCapitalMarketExpos() {
		return isCapitalMarketExpos;
	}

	public void setIsCapitalMarketExpos(String isCapitalMarketExpos) {
		this.isCapitalMarketExpos = isCapitalMarketExpos;
	}

	public String getIsRealEstateExpos() {
		return isRealEstateExpos;
	}

	public void setIsRealEstateExpos(String isRealEstateExpos) {
		this.isRealEstateExpos = isRealEstateExpos;
	}

	public String getRealEstateExposType() {
		return realEstateExposType;
	}

	public void setRealEstateExposType(String realEstateExposType) {
		this.realEstateExposType = realEstateExposType;
	}

	public String getRealEstateExposComm() {
		return realEstateExposComm;
	}

	public void setRealEstateExposComm(String realEstateExposComm) {
		this.realEstateExposComm = realEstateExposComm;
	}

	public String getIsCommoditiesExposer() {
		return isCommoditiesExposer;
	}

	public void setIsCommoditiesExposer(String isCommoditiesExposer) {
		this.isCommoditiesExposer = isCommoditiesExposer;
	}

	public String getIsSensitive() {
		return isSensitive;
	}

	public void setIsSensitive(String isSensitive) {
		this.isSensitive = isSensitive;
	}

	public String getCommoditiesName() {
		return commoditiesName;
	}

	public void setCommoditiesName(String commoditiesName) {
		this.commoditiesName = commoditiesName;
	}

	public String getGrossInvsInPM() {
		return grossInvsInPM;
	}

	public void setGrossInvsInPM(String grossInvsInPM) {
		this.grossInvsInPM = grossInvsInPM;
	}

	public String getGrossInvsInEquip() {
		return grossInvsInEquip;
	}

	public void setGrossInvsInEquip(String grossInvsInEquip) {
		this.grossInvsInEquip = grossInvsInEquip;
	}

	public String getPsu() {
		return psu;
	}

	public void setPsu(String psu) {
		this.psu = psu;
	}

	public String getPsuPercentage() {
		return psuPercentage;
	}

	public void setPsuPercentage(String psuPercentage) {
		this.psuPercentage = psuPercentage;
	}

	public String getGovtUnderTaking() {
		return govtUnderTaking;
	}

	public void setGovtUnderTaking(String govtUnderTaking) {
		this.govtUnderTaking = govtUnderTaking;
	}

	public String getIsProjectFinance() {
		return isProjectFinance;
	}

	public void setIsProjectFinance(String isProjectFinance) {
		this.isProjectFinance = isProjectFinance;
	}

	public String getIsInfrastructureFinanace() {
		return isInfrastructureFinanace;
	}

	public void setIsInfrastructureFinanace(String isInfrastructureFinanace) {
		this.isInfrastructureFinanace = isInfrastructureFinanace;
	}

	public String getInfrastructureFinanaceType() {
		return infrastructureFinanaceType;
	}

	public void setInfrastructureFinanaceType(String infrastructureFinanaceType) {
		this.infrastructureFinanaceType = infrastructureFinanaceType;
	}

	public String getIsSemsGuideApplicable() {
		return isSemsGuideApplicable;
	}

	public void setIsSemsGuideApplicable(String isSemsGuideApplicable) {
		this.isSemsGuideApplicable = isSemsGuideApplicable;
	}

	public String getIsFailIfcExcluList() {
		return isFailIfcExcluList;
	}

	public void setIsFailIfcExcluList(String isFailIfcExcluList) {
		this.isFailIfcExcluList = isFailIfcExcluList;
	}

	public String getIsTufs() {
		return isTufs;
	}

	public void setIsTufs(String isTufs) {
		this.isTufs = isTufs;
	}

	public String getIsRbiDefaulter() {
		return isRbiDefaulter;
	}

	public void setIsRbiDefaulter(String isRbiDefaulter) {
		this.isRbiDefaulter = isRbiDefaulter;
	}

	public String getRbiDefaulter() {
		return rbiDefaulter;
	}

	public void setRbiDefaulter(String rbiDefaulter) {
		this.rbiDefaulter = rbiDefaulter;
	}

	public String getIsLitigation() {
		return isLitigation;
	}

	public void setIsLitigation(String isLitigation) {
		this.isLitigation = isLitigation;
	}

	public String getLitigationBy() {
		return litigationBy;
	}

	public void setLitigationBy(String litigationBy) {
		this.litigationBy = litigationBy;
	}

	public String getIsInterestOfBank() {
		return isInterestOfBank;
	}

	public void setIsInterestOfBank(String isInterestOfBank) {
		this.isInterestOfBank = isInterestOfBank;
	}

	public String getInterestOfBank() {
		return interestOfBank;
	}

	public void setInterestOfBank(String interestOfBank) {
		this.interestOfBank = interestOfBank;
	}

	public String getIsAdverseRemark() {
		return isAdverseRemark;
	}

	public void setIsAdverseRemark(String isAdverseRemark) {
		this.isAdverseRemark = isAdverseRemark;
	}

	public String getAdverseRemark() {
		return adverseRemark;
	}

	public void setAdverseRemark(String adverseRemark) {
		this.adverseRemark = adverseRemark;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getAvgAnnualTurnover() {
		return avgAnnualTurnover;
	}

	public void setAvgAnnualTurnover(String avgAnnualTurnover) {
		this.avgAnnualTurnover = avgAnnualTurnover;
	}

	public String getIndustryExposer() {
		return industryExposer;
	}

	public void setIndustryExposer(String industryExposer) {
		this.industryExposer = industryExposer;
	}

	public String getIsDirecOtherBank() {
		return isDirecOtherBank;
	}

	public void setIsDirecOtherBank(String isDirecOtherBank) {
		this.isDirecOtherBank = isDirecOtherBank;
	}

	public String getDirecOtherBank() {
		return direcOtherBank;
	}

	public void setDirecOtherBank(String direcOtherBank) {
		this.direcOtherBank = direcOtherBank;
	}

	public String getIsPartnerOtherBank() {
		return isPartnerOtherBank;
	}

	public void setIsPartnerOtherBank(String isPartnerOtherBank) {
		this.isPartnerOtherBank = isPartnerOtherBank;
	}

	public String getPartnerOtherBank() {
		return partnerOtherBank;
	}

	public void setPartnerOtherBank(String partnerOtherBank) {
		this.partnerOtherBank = partnerOtherBank;
	}

	public String getIsSubstantialOtherBank() {
		return isSubstantialOtherBank;
	}

	public void setIsSubstantialOtherBank(String isSubstantialOtherBank) {
		this.isSubstantialOtherBank = isSubstantialOtherBank;
	}

	public String getSubstantialOtherBank() {
		return substantialOtherBank;
	}

	public void setSubstantialOtherBank(String substantialOtherBank) {
		this.substantialOtherBank = substantialOtherBank;
	}

	public String getIsHdfcDirecRltv() {
		return isHdfcDirecRltv;
	}

	public void setIsHdfcDirecRltv(String isHdfcDirecRltv) {
		this.isHdfcDirecRltv = isHdfcDirecRltv;
	}

	public String getHdfcDirecRltv() {
		return hdfcDirecRltv;
	}

	public void setHdfcDirecRltv(String hdfcDirecRltv) {
		this.hdfcDirecRltv = hdfcDirecRltv;
	}

	public String getIsObkDirecRltv() {
		return isObkDirecRltv;
	}

	public void setIsObkDirecRltv(String isObkDirecRltv) {
		this.isObkDirecRltv = isObkDirecRltv;
	}

	public String getObkDirecRltv() {
		return obkDirecRltv;
	}

	public void setObkDirecRltv(String obkDirecRltv) {
		this.obkDirecRltv = obkDirecRltv;
	}

	public String getIsPartnerDirecRltv() {
		return isPartnerDirecRltv;
	}

	public void setIsPartnerDirecRltv(String isPartnerDirecRltv) {
		this.isPartnerDirecRltv = isPartnerDirecRltv;
	}

	public String getPartnerDirecRltv() {
		return partnerDirecRltv;
	}

	public void setPartnerDirecRltv(String partnerDirecRltv) {
		this.partnerDirecRltv = partnerDirecRltv;
	}

	public String getIsSubstantialRltvHdfcOther() {
		return isSubstantialRltvHdfcOther;
	}

	public void setIsSubstantialRltvHdfcOther(String isSubstantialRltvHdfcOther) {
		this.isSubstantialRltvHdfcOther = isSubstantialRltvHdfcOther;
	}

	public String getSubstantialRltvHdfcOther() {
		return substantialRltvHdfcOther;
	}

	public void setSubstantialRltvHdfcOther(String substantialRltvHdfcOther) {
		this.substantialRltvHdfcOther = substantialRltvHdfcOther;
	}

	public String getDirecHdfcOther() {
		return direcHdfcOther;
	}

	public void setDirecHdfcOther(String direcHdfcOther) {
		this.direcHdfcOther = direcHdfcOther;
	}

	public String getIsBackedByGovt() {
		return isBackedByGovt;
	}

	public void setIsBackedByGovt(String isBackedByGovt) {
		this.isBackedByGovt = isBackedByGovt;
	}

	public String getGovtSchemeType() {
		return govtSchemeType;
	}

	public void setGovtSchemeType(String govtSchemeType) {
		this.govtSchemeType = govtSchemeType;
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

	public String getFirstYearTurnoverCurr() {
		return firstYearTurnoverCurr;
	}

	public void setFirstYearTurnoverCurr(String firstYearTurnoverCurr) {
		this.firstYearTurnoverCurr = firstYearTurnoverCurr;
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
	
	public String getCategoryOfFarmer() {
		return categoryOfFarmer;
	}

	public void setCategoryOfFarmer(String categoryOfFarmer) {
		this.categoryOfFarmer = categoryOfFarmer;
	}



	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getLandHolding() {
		return landHolding;
	}

	public void setLandHolding(String landHolding) {
		this.landHolding = landHolding;
	}

	@Override
	public void setRestrictedListIndustries(String restrictedListIndustries) {
		// TODO Auto-generated method stub
		this.restrictedListIndustries = restrictedListIndustries;
	}

	@Override
	public String getRestrictedListIndustries() {
		// TODO Auto-generated method stub
		return restrictedListIndustries;
	}

	public String getCustomerFyClouser() {
		return customerFyClouser;
	}

	public void setCustomerFyClouser(String customerFyClouser) {
		this.customerFyClouser = customerFyClouser;
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
	
	
	
}