package com.integrosys.cms.ui.manualinput.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;
import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.manualinput.IManualInputConstants;
import com.integrosys.cms.ui.udf.IUserExtendable;

/***
 * class added by
 * 
 * @author sandiip.shinde
 * @since $Date 09-03-2011
 * 
 */

public class ManualInputCustomerInfoForm extends TrxContextForm implements
		Serializable, IUserExtendable {

	// Class MainProfile Attributes
	private String CIFId;
	

private String countryOfGuarantor;

private String restrictedListIndustries;

private String criCountryName;


private String exceptionalCases;
private String isNBFC;
private String  iFSCCode;
private String bankBranchName;
private String branchNameAddress;
private String emailID;
private String scfStatus;
private String scfFailedReason;
private String ecbfStatus;
private String ecbfFailedReason;


public String getScfStatus() {
	return scfStatus;
}

public void setScfStatus(String scfStatus) {
	this.scfStatus = scfStatus;
}

public String getScfFailedReason() {
	return scfFailedReason;
}

public void setScfFailedReason(String scfFailedReason) {
	this.scfFailedReason = scfFailedReason;
}



public String getEcbfStatus() {
	return ecbfStatus;
}

public void setEcbfStatus(String ecbfStatus) {
	this.ecbfStatus = ecbfStatus;
}

public String getEcbfFailedReason() {
	return ecbfFailedReason;
}

public void setEcbfFailedReason(String ecbfFailedReason) {
	this.ecbfFailedReason = ecbfFailedReason;
}



public String getEmailID() {
	return emailID;
}

public void setEmailID(String emailID) {
	this.emailID = emailID;
}

public String getExceptionalCases() {
	return exceptionalCases;
}

public void setExceptionalCases(String exceptionalCases) {
	this.exceptionalCases = exceptionalCases;
}

public String getIsNBFC() {
	return isNBFC;
}

public void setIsNBFC(String isNBFC) {
	this.isNBFC = isNBFC;
}

public String getiFSCCode() {
	return iFSCCode;
}

public void setiFSCCode(String iFSCCode) {
	this.iFSCCode = iFSCCode;
}

public String getBankBranchName() {
	return bankBranchName;
}

public void setBankBranchName(String bankBranchName) {
	this.bankBranchName = bankBranchName;
}



public String getBranchNameAddress() {
	return branchNameAddress;
}

public void setBranchNameAddress(String branchNameAddress) {
	this.branchNameAddress = branchNameAddress;
}

public String getCriCountryName() {
	return criCountryName;
}

public void setCriCountryName(String criCountryName) {
	this.criCountryName = criCountryName;
}

public String getRestrictedListIndustries() {
	return restrictedListIndustries;
}

public void setRestrictedListIndustries(String restrictedListIndustries) {
	this.restrictedListIndustries = restrictedListIndustries;
}

private String isRBIWilfulDefaultersList;

private String isInRestrictedList;

private String isQualifyingNotesPresent;

private String stateImplications;

private String isBorrowerInRejectDatabase;

private String rejectHistoryReason;

private String restrictedFinancing;

private String isCreditToRestrictedList;

private String creditListOfIndustries;

private String categoryOfFarmer;



private long cmsId;

private String systemId;

private String customerNameShort;

private String System;

private String regOffice;

private String SystemName;

private String SystemCustomerId;

private String customerNameLong;

private String legalName;



private String typeOfCompany;

private String customerClass;

private String domicileCountry;

private String incorporatedCountry;

private String incorporationNumber;

private Date incorporationDate;

private String customerType;

private String legalConstitution;

private Date customerStartDate;

private String customerSegment;

private String baselCustomerSegment;

private String baselCustomerSubSegment;

private String relationshipStartDate;

private String businessGroup;

private String restrictedIndustries;



private String natureOfBusinessCode;

private String businessSector;

private String blacklisted;

private String natureOfBiz;

private String accountOfficerID;

private String accountOfficerName;

private Vector idInfo;

private Vector address;

private List otherSystem;

private List otherBank;

private List sublineParty;

private List vendor;

private List udfMtdList;

private String vendorName;

private long vendorId;



public long getVendorId() {
	return vendorId;
}

public void setVendorId(long vendorId) {
	this.vendorId = vendorId;
}

public String getVendorName() {
	return vendorName;
}

public void setVendorName(String vendorName) {
	this.vendorName = vendorName;
}
public List getVendor() {
	return vendor;
}

public void setVendor(List vendor) {
	this.vendor = vendor;
}

private String coBorrowerDetailsInd;

private String coBorrowerLiabIdList;

private List<CoBorrowerDetailsForm> coBorrowerDetails;

public String getCoBorrowerDetailsInd() {
	return coBorrowerDetailsInd;
}

public void setCoBorrowerDetailsInd(String coBorrowerDetailsInd) {
	this.coBorrowerDetailsInd = coBorrowerDetailsInd;
}

public String getCoBorrowerLiabIdList() {
	return coBorrowerLiabIdList;
}

public void setCoBorrowerLiabIdList(String coBorrowerLiabIdList) {
	this.coBorrowerLiabIdList = coBorrowerLiabIdList;
}

public List<CoBorrowerDetailsForm> getCoBorrowerDetails() {
	return coBorrowerDetails;
}

public void setCoBorrowerDetails(List<CoBorrowerDetailsForm> coBorrowerDetails) {
	this.coBorrowerDetails = coBorrowerDetails;
}

public ManualInputCustomerInfoForm addCoBorrowerDetailsForms(CoBorrowerDetailsForm... coBorrowerDetailsForms) {
	if (this.coBorrowerDetails == null) {
		this.coBorrowerDetails = new ArrayList<CoBorrowerDetailsForm>();
	}
	this.coBorrowerDetails.addAll(Arrays.asList(coBorrowerDetailsForms));
	return this;
}

public CoBorrowerDetailsForm getIndexedCoBorrowerDetailsForm(int index) {
	while (coBorrowerDetails.size() <= index) {
		coBorrowerDetails.add(new CoBorrowerDetailsForm());
	}
	return (CoBorrowerDetailsForm) coBorrowerDetails.get(index);
}
private String sublineListEmpty;

private String systemListEmpty;

private String directorListEmpty;

private String incomeGroup;

private String bizSector;

private String idType;

private String index;

private String idNo = "";

private String isicCode;

private String oldIdNo = "";

private String lraLeId;

private String address1;

private String address2;

private String address3;

private String address4;

private String address5;

private String postcode;

private String telephoneNo;

private String stdCodeTelNo;

private String mainBranch;

private String branchCode;

private String telex;

private String stdCodeTelex;

private String contactType;

private String email;

private String country;

private String state;

private String city;

private String countryPermanent;

private String language;

private String officeAddress1;

private String officeAddress2;

private String officeAddress3;

private String officeAddress4;

private String officeAddress5;

private String officeTelephoneNo;

private String stdCodeOfficeTelNo;

private String officeTelex;

private String stdCodeOfficeTelex;

private String officeEmail;

private String officeCountry;

private String officeState;

private String officeCity;

private String officeRegion;



private String officePostCode;

private String regOfficeAddress1;

private String regOfficeAddress2;

private String regOfficeAddress3;

private String regOfficeAddress4;

private String regOfficeAddress5;

private String regOfficeTelephoneNo;

private String regOfficeTelex;

private String regOfficeEmail;

private String regOfficeCountry;

private String regOfficeState;

private String regOfficeCity;

private String regOfficeRegion;

private String regOfficePostCode;

private String source;

private Character updateStatusIndicator;

private Character changeIndicator;

private long subProfilePrimaryKey;

private CustomerIdInfo idInfo1;

private CustomerIdInfo idInfo2;

private CustomerIdInfo idInfo3;

private String customerBranch;

// Class SubProfile Attributes

private String cifId;

private long cmsMainProfileId;

private long subProfileId = 1;

private String subProfileName;

private String originatingCountry;

private String originatingOrganisation;

private char nonBorrowerIndicator;

private String tfaAmount;

private String amount;

private String lspId;

private String incomeRange;

// ------added by bharat for
// customers------------------------------start---------------------------------

private String status;

private String partyGroupName;

private String relationshipMgrEmpCode;

private String relationshipMgr;

private String rmRegion;

private String cycle;

private String entity;

private String industryName;

private String rbiIndustryCode;

private String cinLlpin;

private String dateOfIncorporation;

private String aadharNumber;

private String pan;

private String region;

private String subLine;

private String bankingMethod;

private String bankingMethodAll;

private String bankingMethodAllValue;

private List bankMethodList1;

private List bankMethodList2;

private String finalBankMethodList;

private String totalFundedLimit;

private String totalNonFundedLimit;

private String fundedSharePercent;

private String nonFundedSharePercent;

private String memoExposure;

private String totalSanctionedLimit;

private String mpbf;

private String fundedShareLimit;

private String nonFundedShareLimit;

private String fundedIncreDecre;

private String nonFundedIncreDecre;

private String memoExposIncreDecre;

private String bankName;

private String nodalLead;

private String partyId;

private String borrowerDUNSNo;

private String classActivity1;

private String comments;

private String classActivity2;

private String classActivity3;

private String willfulDefaultStatus;

private String suitFilledStatus;

private String dateOfSuit;

private String suitAmount;

private String suitReferenceNo;

private String dateWillfulDefault;

private String regOfficeDUNSNo;





private String relatedType;

private String relationship;

private String directorEmail;

private String directorFax;

private String dirStdCodeTelex;

private String dirStdCodeTelNo;

private String directorTelNo;

private String directorCountry;

private String directorState;

private String directorCity;

private String directorRegion;

private String directorPostCode;

private String directorAddress3;

private String directorAddress2;

private String directorAddress1;

private String percentageOfControl;

private String fullName;

private String namePrefix;

private String businessEntityName;

private String directorPan;

private String directorAadhar;

private String relatedDUNSNo;

private String dinNo;



private String currency;

private String partyConsent;

private String event;

private String subLineName;

private String partyName;

private String bankBranchId;



private String entityType;

private String isSPVFunding;

private String landHolding;



private String indirectCountryRiskExposure;
private String salesPercentage;
private String isCGTMSE;


private String objectFinance;
private String subsidyFlag;

private String nameOfTheBank;

private String isDirectorNamePresent;

private String directorName;

private String isBorrowerDefaulter;

private String defaultDetails;

private String extentOfCompromise;

//LEI CR
private String leiCode;
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
public String getDefaultDetails() {
	return defaultDetails;
}

public void setDefaultDetails(String defaultDetails) {
	this.defaultDetails = defaultDetails;
}

public String getExtentOfCompromise() {
	return extentOfCompromise;
}

public void setExtentOfCompromise(String extentOfCompromise) {
	this.extentOfCompromise = extentOfCompromise;
}



public String getIsBorrowerDefaulter() {
	return isBorrowerDefaulter;
}

public void setIsBorrowerDefaulter(String isBorrowerDefaulter) {
	this.isBorrowerDefaulter = isBorrowerDefaulter;
}




public String getIsDirectorNamePresent() {
	return isDirectorNamePresent;
}

public void setIsDirectorNamePresent(String isDirectorNamePresent) {
	this.isDirectorNamePresent = isDirectorNamePresent;
}

public String getNameOfTheBank() {
	return nameOfTheBank;
}

public void setNameOfTheBank(String nameOfTheBank) {
	this.nameOfTheBank = nameOfTheBank;
}

public String getLimitAmount() {
	return limitAmount;
}

public void setLimitAmount(String limitAmount) {
	this.limitAmount = limitAmount;
}

private String limitAmount;


public String getLandHolding() {
	return landHolding;
}

public void setLandHolding(String landHolding) {
	this.landHolding = landHolding;
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

public String getIsRBIWilfulDefaultersList() {
	return isRBIWilfulDefaultersList;
}

public void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaultersList) {
	this.isRBIWilfulDefaultersList = isRBIWilfulDefaultersList;
}

private String holdingCompnay;
private String commericialRealEstate;
private String commericialRealEstateValue;
private String commericialRealEstateResidentialHousing;
private String residentialRealEstate;
private String indirectRealEstate;


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

private String cautionList;
private String dateOfCautionList;
private String company;
private String defaultersList;
private String rbiDateOfCautionList;
private String rbiCompany;
private String rbiDirectors;
private String rbiGroupCompanies;
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

private String directors;
private String groupCompanies;


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

private String conductOfAccountWithOtherBanks;
public String getConductOfAccountWithOtherBanks() {
	return conductOfAccountWithOtherBanks;
}

public void setConductOfAccountWithOtherBanks(
		String conductOfAccountWithOtherBanks) {
	this.conductOfAccountWithOtherBanks = conductOfAccountWithOtherBanks;
}

public String getCrilicStatus() {
	return crilicStatus;
}

public void setCrilicStatus(String crilicStatus) {
	this.crilicStatus = crilicStatus;
}

public String getComment() {
	return comment;
}

public void setComment(String comment) {
	this.comment = comment;
}

private String crilicStatus;
private String comment;


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

private String isCommodityFinanceCustomer;

private String restructuedBorrowerOrFacility;


private String facility;

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

private String capitalForCommodityAndExchange;
private String brokerType;
private String isBrokerTypeShare;
private String isBrokerTypeCommodity;
/*private String odfdCategory;*/


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

public String getIsCreditToRestrictedList() {
	return isCreditToRestrictedList;
}

public void setIsCreditToRestrictedList(String isCreditToRestrictedList) {
	this.isCreditToRestrictedList = isCreditToRestrictedList;
}

public String getCreditListOfIndustries() {
	return creditListOfIndustries;
}

public void setCreditListOfIndustries(String creditListOfIndustries) {
	this.creditListOfIndustries = creditListOfIndustries;
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

	private String facilityApproved;
	private String isAffordableHousingFinance;
	
	
	
	
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

	private String isIPRE;
	
	private String financeForAccquisition;
	
	private String securityName;
	
	public String getFacilityApproved() {
		return facilityApproved;
	}

	public void setFacilityApproved(String facilityApproved) {
		this.facilityApproved = facilityApproved;
	}

	private String securityType;
	
	private String securityValue;
	

	
	
	public String getFinanceForAccquisition() {
		return financeForAccquisition;
	}

	public void setFinanceForAccquisition(String financeForAccquisition) {
		this.financeForAccquisition = financeForAccquisition;
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

	public String getSalesPercentage() {
		return salesPercentage;
	}

	public void setSalesPercentage(String salesPercentage) {
		this.salesPercentage = salesPercentage;
	}

	public String getIndirectCountryRiskExposure() {
		return indirectCountryRiskExposure;
	}

	public void setIndirectCountryRiskExposure(String indirectCountryRiskExposure) {
		this.indirectCountryRiskExposure = indirectCountryRiskExposure;
	}

	public String getIsSPVFunding() {
		return isSPVFunding;
	}

	public void setIsSPVFunding(String isSPVFunding) {
		this.isSPVFunding = isSPVFunding;
	}
	
	
	//added by Shiv 200811
	
	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

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
	
	private  String tempFacilityData;
	
	private  String groupExposer;
	
	//Add Shiv 290811
	private  String facilityFor;

	private  String facilityName;

	private  String facilityAmount;
	
	private  String lineNo;
	
	private  String serialNo;
	
	private  String estateType;
	
	private  String commRealEstateType;
	
	private  String tempCommRealEstateType;
	
	private  String prioritySector;
	
	private  String tempPrioritySector;
	
	private List criFacility;
	
	private String nameOfHoldingCompany;
	


	private String type;
	
	private String companyType;
	
	private String ifBreachWithPrescriptions;

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

	
	private String facilityList;
	
	private String gobutton;
	
	private String pName;

	private String cityName;

	private String stateName;

	private String regionName;

	private String countryName;

	private String cityRegName;

	private String stateRegName;

	private String regionRegName;

	private String countryRegName;

	private String rmRegionName;

	private String relManagerName;
	
	private String firstYear;
	
	private String firstYearTurnover;
	
	private String firstYearTurnoverCurr;
	
    private String secondYear;
	
	private String secondYearTurnover;
	
	private String secondYearTurnoverCurr;
	
    private String thirdYear;
	
	private String thirdYearTurnover;
	
	private String thirdYearTurnoverCurr;
	
	private String form6061Checked;
	private Character panValGenParamFlag;
	private Character isPanValidated;
	
	private Character leiValGenParamFlag;
	private Character isLeiValidated;
	private String deferLEI;
	
	
	public Character getIsLeiValidated() {
		return isLeiValidated;
	}

	public void setIsLeiValidated(Character isLeiValidated) {
		this.isLeiValidated = isLeiValidated;
	}

	public String getDeferLEI() {
		return deferLEI;
	}

	public void setDeferLEI(String deferLEI) {
		this.deferLEI = deferLEI;
	}

	public Character getLeiValGenParamFlag() {
		return leiValGenParamFlag;
	}

	public void setLeiValGenParamFlag(Character leiValGenParamFlag) {
		this.leiValGenParamFlag = leiValGenParamFlag;
	}

	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpSharePercent;

	//Start: Uma Khot:CRI Field addition enhancement
	private String nameOfBank;
	private String isDirectorMoreThanOne;
	private String nameOfDirectorsAndCompany;
	private String isBorrDefaulterWithBank;
	private String detailsOfDefault;
	private String extOfCompromiseAndWriteoff;
	private String isCibilStatusClean;
	private String detailsOfCleanCibil;
	//End: Uma Khot:CRI Field addition enhancement
	
	//Ram rating aditional field
	private String customerFyClouser;
	
	// ------------------------------------------------------------------end----------------------------------
	// Class ManualInputCustomerForm Attributes
	
	private String camTypePartyLevel;
	

	public String getCamTypePartyLevel() {
		return camTypePartyLevel;
	}

	public void setCamTypePartyLevel(String camTypePartyLevel) {
		this.camTypePartyLevel = camTypePartyLevel;
	}

	public String getFacilityList() {
		return facilityList;
	}

	public void setFacilityList(String facilityList) {
		this.facilityList = facilityList;
	}

	private String legalId = "";
	private String leIDType = "";
	private String customerName;
	
	private String listedCompany;  
	private String isinNo; 
	private String raroc;
	private String raraocPeriod;
	private String yearEndPeriod;
	private String creditMgrEmpId;
	private String pfLrdCreditMgrEmpId;
	private String creditMgrSegment;
	
	//New CAM UI Format CR 
	private String leadValue;

	private String multBankFundBasedLeadBankPer;
	private String multBankFundBasedHdfcBankPer;
	private String multBankNonFundBasedLeadBankPer;
	private String multBankNonFundBasedHdfcBankPer;

	private String consBankFundBasedLeadBankPer;
	private String consBankFundBasedHdfcBankPer;
	private String consBankNonFundBasedLeadBankPer;
	private String consBankNonFundBasedHdfcBankPer;
	
	private String revisedEmailIds;
	private String[] revisedEmailIdsArray;
	
	private List revisedArrayListN;
	private String fromEvents;
	

	public String getFromEvents() {
		return fromEvents;
	}

	public void setFromEvents(String fromEvents) {
		this.fromEvents = fromEvents;
	}

	public List getRevisedArrayListN() {
		return revisedArrayListN;
	}

	public void setRevisedArrayListN(List revisedArrayListN) {
		this.revisedArrayListN = revisedArrayListN;
	}

	public String[] getRevisedEmailIdsArray() {
		return revisedEmailIdsArray;
	}

	public void setRevisedEmailIdsArray(String[] revisedEmailIdsArray) {
		this.revisedEmailIdsArray = revisedEmailIdsArray;
	}

	public String getRevisedEmailIds() {
		return revisedEmailIds;
	}

	public void setRevisedEmailIds(String revisedEmailIds) {
		this.revisedEmailIds = revisedEmailIds;
	}

	public String getLeadValue() {
		return leadValue;
	}

	public void setLeadValue(String leadValue) {
		this.leadValue = leadValue;
	}

	public String getMultBankFundBasedLeadBankPer() {
		return multBankFundBasedLeadBankPer;
	}

	public void setMultBankFundBasedLeadBankPer(String multBankFundBasedLeadBankPer) {
		this.multBankFundBasedLeadBankPer = multBankFundBasedLeadBankPer;
	}

	public String getMultBankFundBasedHdfcBankPer() {
		return multBankFundBasedHdfcBankPer;
	}

	public void setMultBankFundBasedHdfcBankPer(String multBankFundBasedHdfcBankPer) {
		this.multBankFundBasedHdfcBankPer = multBankFundBasedHdfcBankPer;
	}

	public String getMultBankNonFundBasedLeadBankPer() {
		return multBankNonFundBasedLeadBankPer;
	}

	public void setMultBankNonFundBasedLeadBankPer(String multBankNonFundBasedLeadBankPer) {
		this.multBankNonFundBasedLeadBankPer = multBankNonFundBasedLeadBankPer;
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

	public String getConsBankFundBasedHdfcBankPer() {
		return consBankFundBasedHdfcBankPer;
	}

	public void setConsBankFundBasedHdfcBankPer(String consBankFundBasedHdfcBankPer) {
		this.consBankFundBasedHdfcBankPer = consBankFundBasedHdfcBankPer;
	}

	public String getConsBankNonFundBasedLeadBankPer() {
		return consBankNonFundBasedLeadBankPer;
	}

	public void setConsBankNonFundBasedLeadBankPer(String consBankNonFundBasedLeadBankPer) {
		this.consBankNonFundBasedLeadBankPer = consBankNonFundBasedLeadBankPer;
	}

	public String getConsBankNonFundBasedHdfcBankPer() {
		return consBankNonFundBasedHdfcBankPer;
	}

	public void setConsBankNonFundBasedHdfcBankPer(String consBankNonFundBasedHdfcBankPer) {
		this.consBankNonFundBasedHdfcBankPer = consBankNonFundBasedHdfcBankPer;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String cIFId) {
		CIFId = cIFId;
	}

	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public String getCustomerNameShort() {
		return customerNameShort;
	}

	public void setCustomerNameShort(String customerNameShort) {
		this.customerNameShort = customerNameShort;
	}

	public String getCustomerNameLong() {
		return customerNameLong;
	}

	public void setCustomerNameLong(String customerNameLong) {
		this.customerNameLong = customerNameLong;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getDomicileCountry() {
		return domicileCountry;
	}

	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public String getIncorporatedCountry() {
		return incorporatedCountry;
	}

	public void setIncorporatedCountry(String incorporatedCountry) {
		this.incorporatedCountry = incorporatedCountry;
	}

	public String getIncorporationNumber() {
		return incorporationNumber;
	}

	public void setIncorporationNumber(String incorporationNumber) {
		this.incorporationNumber = incorporationNumber;
	}

	public Date getIncorporationDate() {
		return incorporationDate;
	}

	public void setIncorporationDate(Date incorporationDate) {
		this.incorporationDate = incorporationDate;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getLegalConstitution() {
		return legalConstitution;
	}

	public void setLegalConstitution(String legalConstitution) {
		this.legalConstitution = legalConstitution;
	}

	public Date getCustomerStartDate() {
		return customerStartDate;
	}

	public void setCustomerStartDate(Date customerStartDate) {
		this.customerStartDate = customerStartDate;
	}

	public String getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}

	public String getNatureOfBusinessCode() {
		return natureOfBusinessCode;
	}

	public void setNatureOfBusinessCode(String natureOfBusinessCode) {
		this.natureOfBusinessCode = natureOfBusinessCode;
	}

	public String getBusinessSector() {
		return businessSector;
	}

	public void setBusinessSector(String businessSector) {
		this.businessSector = businessSector;
	}

	

	public String getRelationshipStartDate() {
		return relationshipStartDate;
	}

	public void setRelationshipStartDate(String relationshipStartDate) {
		this.relationshipStartDate = relationshipStartDate;
	}

	public String getBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(String blacklisted) {
		this.blacklisted = blacklisted;
	}

	public String getAccountOfficerID() {
		return accountOfficerID;
	}

	public void setAccountOfficerID(String accountOfficerID) {
		this.accountOfficerID = accountOfficerID;
	}

	public String getAccountOfficerName() {
		return accountOfficerName;
	}

	public void setAccountOfficerName(String accountOfficerName) {
		this.accountOfficerName = accountOfficerName;
	}

	public Vector getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(Vector idInfo) {
		this.idInfo = idInfo;
	}

	public Vector getAddress() {
		return address;
	}

	public void setAddress(Vector address) {
		this.address = address;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIsicCode() {
		return isicCode;
	}

	public void setIsicCode(String isicCode) {
		this.isicCode = isicCode;
	}

	public String getOldIdNo() {
		return oldIdNo;
	}

	public void setOldIdNo(String oldIdNo) {
		this.oldIdNo = oldIdNo;
	}

	public String getLraLeId() {
		return lraLeId;
	}

	public void setLraLeId(String lraLeId) {
		this.lraLeId = lraLeId;
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

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getAddress5() {
		return address5;
	}

	public void setAddress5(String address5) {
		this.address5 = address5;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getTelex() {
		return telex;
	}

	public void setTelex(String telex) {
		this.telex = telex;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOfficeAddress1() {
		return officeAddress1;
	}

	public void setOfficeAddress1(String officeAddress1) {
		this.officeAddress1 = officeAddress1;
	}

	public String getOfficeAddress2() {
		return officeAddress2;
	}

	public void setOfficeAddress2(String officeAddress2) {
		this.officeAddress2 = officeAddress2;
	}

	public String getOfficeAddress3() {
		return officeAddress3;
	}

	public void setOfficeAddress3(String officeAddress3) {
		this.officeAddress3 = officeAddress3;
	}

	public String getOfficeAddress4() {
		return officeAddress4;
	}

	public void setOfficeAddress4(String officeAddress4) {
		this.officeAddress4 = officeAddress4;
	}

	public String getOfficeAddress5() {
		return officeAddress5;
	}

	public void setOfficeAddress5(String officeAddress5) {
		this.officeAddress5 = officeAddress5;
	}

	public String getOfficeTelephoneNo() {
		return officeTelephoneNo;
	}

	public void setOfficeTelephoneNo(String officeTelephoneNo) {
		this.officeTelephoneNo = officeTelephoneNo;
	}

	public String getOfficeTelex() {
		return officeTelex;
	}

	public void setOfficeTelex(String officeTelex) {
		this.officeTelex = officeTelex;
	}

	public String getOfficeEmail() {
		return officeEmail;
	}

	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	public String getOfficeCountry() {
		return officeCountry;
	}

	public void setOfficeCountry(String officeCountry) {
		this.officeCountry = officeCountry;
	}

	public String getOfficeState() {
		return officeState;
	}

	public void setOfficeState(String officeState) {
		this.officeState = officeState;
	}

	public String getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(String officeCity) {
		this.officeCity = officeCity;
	}

	
	public String getOfficePostCode() {
		return officePostCode;
	}

	public void setOfficePostCode(String officePostCode) {
		this.officePostCode = officePostCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Character getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(Character updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public Character getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(Character changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public long getSubProfilePrimaryKey() {
		return subProfilePrimaryKey;
	}

	public void setSubProfilePrimaryKey(long subProfilePrimaryKey) {
		this.subProfilePrimaryKey = subProfilePrimaryKey;
	}

	public CustomerIdInfo getIdInfo1() {
		return idInfo1;
	}

	public void setIdInfo1(CustomerIdInfo idInfo1) {
		this.idInfo1 = idInfo1;
	}

	public CustomerIdInfo getIdInfo2() {
		return idInfo2;
	}

	public void setIdInfo2(CustomerIdInfo idInfo2) {
		this.idInfo2 = idInfo2;
	}

	public CustomerIdInfo getIdInfo3() {
		return idInfo3;
	}

	public void setIdInfo3(CustomerIdInfo idInfo3) {
		this.idInfo3 = idInfo3;
	}

	public String getCifId() {
		return cifId;
	}

	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	public long getCmsMainProfileId() {
		return cmsMainProfileId;
	}

	public void setCmsMainProfileId(long cmsMainProfileId) {
		this.cmsMainProfileId = cmsMainProfileId;
	}

	public long getSubProfileId() {
		return subProfileId;
	}

	public void setSubProfileId(long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public String getSubProfileName() {
		return subProfileName;
	}

	public void setSubProfileName(String subProfileName) {
		this.subProfileName = subProfileName;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	public String getOriginatingOrganisation() {
		return originatingOrganisation;
	}

	public void setOriginatingOrganisation(String originatingOrganisation) {
		this.originatingOrganisation = originatingOrganisation;
	}

	public char getNonBorrowerIndicator() {
		return nonBorrowerIndicator;
	}

	public void setNonBorrowerIndicator(char nonBorrowerIndicator) {
		this.nonBorrowerIndicator = nonBorrowerIndicator;
	}

	public String getTfaAmount() {
		return tfaAmount;
	}

	public void setTfaAmount(String tfaAmount) {
		this.tfaAmount = tfaAmount;
	}

	public String getLspId() {
		return lspId;
	}

	public void setLspId(String lspId) {
		this.lspId = lspId;
	}

	public String getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(String incomeRange) {
		this.incomeRange = incomeRange;
	}

	public String getLegalId() {
		return legalId;
	}

	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}

	public String getLeIDType() {
		return leIDType;
	}

	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	// Type Not Standard Code

	public String getCustomerClass() {
		return customerClass;
	}

	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public String getBaselCustomerSegment() {
		return baselCustomerSegment;
	}

	public void setBaselCustomerSegment(String baselCustomerSegment) {
		this.baselCustomerSegment = baselCustomerSegment;
	}

	public String getBaselCustomerSubSegment() {
		return baselCustomerSubSegment;
	}

	public void setBaselCustomerSubSegment(String baselCustomerSubSegment) {
		this.baselCustomerSubSegment = baselCustomerSubSegment;
	}

	public String getNatureOfBiz() {
		return natureOfBiz;
	}

	public void setNatureOfBiz(String natureOfBiz) {
		this.natureOfBiz = natureOfBiz;
	}

	public String getIncomeGroup() {
		return incomeGroup;
	}

	public void setIncomeGroup(String incomeGroup) {
		this.incomeGroup = incomeGroup;
	}

	public String getBizSector() {
		return bizSector;
	}

	public void setBizSector(String bizSector) {
		this.bizSector = bizSector;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getCountryPermanent() {
		return countryPermanent;
	}

	public void setCountryPermanent(String countryPermanent) {
		this.countryPermanent = countryPermanent;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCustomerBranch() {
		return customerBranch;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	// ------added by bharat for
	// customers------------------------------start---------------------------------
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPartyGroupName() {
		return partyGroupName;
	}

	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}
	
	public String getRelationshipMgrEmpCode() {
		return relationshipMgrEmpCode;
	}

	public void setRelationshipMgrEmpCode(String relationshipMgrEmpCode) {
		this.relationshipMgrEmpCode = relationshipMgrEmpCode;
	}

	public String getRelationshipMgr() {
		return relationshipMgr;
	}

	public void setRelationshipMgr(String relationshipMgr) {
		this.relationshipMgr = relationshipMgr;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	

	public String getRmRegion() {
		return rmRegion;
	}

	public void setRmRegion(String rmRegion) {
		this.rmRegion = rmRegion;
	}

	public String getSubLine() {
		return subLine;
	}

	public void setSubLine(String subLine) {
		this.subLine = subLine;
	}

	public String getBankingMethod() {
		return bankingMethod;
	}

	public void setBankingMethod(String bankingMethod) {
		this.bankingMethod = bankingMethod;
	}
	
	public String getBankingMethodAll() {
		return bankingMethodAll;
	}

	public void setBankingMethodAll(String bankingMethodAll) {
		this.bankingMethodAll = bankingMethodAll;
	}

	
	public String getBankingMethodAllValue() {
		return bankingMethodAllValue;
	}

	public void setBankingMethodAllValue(String bankingMethodAllValue) {
		this.bankingMethodAllValue = bankingMethodAllValue;
	}

	
	public List getBankMethodList1() {
		return bankMethodList1;
	}

	/*public void setBankMethodList1(List bankMethodList1) {
		this.bankMethodList1 = bankMethodList1;
	}*/

	public List getBankMethodList2() {
		return bankMethodList2;
	}

	/*public void setBankMethodList2(List bankMethodList2) {
		this.bankMethodList2 = bankMethodList2;
	}*/
	

	public String getFinalBankMethodList() {
		return finalBankMethodList;
	}

	public void setFinalBankMethodList(String finalBankMethodList) {
		this.finalBankMethodList = finalBankMethodList;
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

	public String getTotalSanctionedLimit() {
		return totalSanctionedLimit;
	}

	public void setTotalSanctionedLimit(String totalSanctionedLimit) {
		this.totalSanctionedLimit = totalSanctionedLimit;
	}

	public String getMpbf() {
		return mpbf;
	}

	public void setMpbf(String mpbf) {
		this.mpbf = mpbf;
	}

	public String getFundedShareLimit() {
		return fundedShareLimit;
	}

	public void setFundedShareLimit(String fundedShareLimit) {
		this.fundedShareLimit = fundedShareLimit;
	}

	public String getNonFundedShareLimit() {
		return nonFundedShareLimit;
	}

	public void setNonFundedShareLimit(String nonFundedShareLimit) {
		this.nonFundedShareLimit = nonFundedShareLimit;
	}

	public String getSystem() {
		return System;
	}

	public void setSystem(String system) {
		System = system;
	}

	public String getSystemCustomerId() {
		return SystemCustomerId;
	}

	public void setSystemCustomerId(String systemCustomerId) {
		SystemCustomerId = systemCustomerId;
	}

	public List getOtherSystem() {
		return otherSystem;
	}

	public void setOtherSystem(List otherSystem) {
		this.otherSystem = otherSystem;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getNodalLead() {
		return nodalLead;
	}

	public void setNodalLead(String nodalLead) {
		this.nodalLead = nodalLead;
	}

	public List getOtherBank() {
		return otherBank;
	}

	public void setOtherBank(List otherBank) {
		this.otherBank = otherBank;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}
	
	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	
	public String getCinLlpin() {
		return cinLlpin;
	}

	public void setCinLlpin(String cinLlpin) {
		this.cinLlpin = cinLlpin;
	}
	
	public String getRbiIndustryCode() {
		return rbiIndustryCode;
	}

	public void setRbiIndustryCode(String rbiIndustryCode) {
		this.rbiIndustryCode = rbiIndustryCode;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public List getSublineParty() {
		return sublineParty;
	}

	public void setSublineParty(List sublineParty) {
		this.sublineParty = sublineParty;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return SystemName;
	}

	public void setSystemName(String systemName) {
		SystemName = systemName;
	}

	public String getRegOffice() {
		return regOffice;
	}

	public void setRegOffice(String regOffice) {
		this.regOffice = regOffice;
	}

	public String getRegOfficeAddress1() {
		return regOfficeAddress1;
	}

	public void setRegOfficeAddress1(String regOfficeAddress1) {
		this.regOfficeAddress1 = regOfficeAddress1;
	}

	public String getRegOfficeAddress2() {
		return regOfficeAddress2;
	}

	public void setRegOfficeAddress2(String regOfficeAddress2) {
		this.regOfficeAddress2 = regOfficeAddress2;
	}

	public String getRegOfficeAddress3() {
		return regOfficeAddress3;
	}

	public void setRegOfficeAddress3(String regOfficeAddress3) {
		this.regOfficeAddress3 = regOfficeAddress3;
	}

	public String getRegOfficeAddress4() {
		return regOfficeAddress4;
	}

	public void setRegOfficeAddress4(String regOfficeAddress4) {
		this.regOfficeAddress4 = regOfficeAddress4;
	}

	public String getRegOfficeAddress5() {
		return regOfficeAddress5;
	}

	public void setRegOfficeAddress5(String regOfficeAddress5) {
		this.regOfficeAddress5 = regOfficeAddress5;
	}

	public String getRegOfficeTelephoneNo() {
		return regOfficeTelephoneNo;
	}

	public void setRegOfficeTelephoneNo(String regOfficeTelephoneNo) {
		this.regOfficeTelephoneNo = regOfficeTelephoneNo;
	}

	public String getRegOfficeTelex() {
		return regOfficeTelex;
	}

	public void setRegOfficeTelex(String regOfficeTelex) {
		this.regOfficeTelex = regOfficeTelex;
	}

	public String getRegOfficeEmail() {
		return regOfficeEmail;
	}

	public void setRegOfficeEmail(String regOfficeEmail) {
		this.regOfficeEmail = regOfficeEmail;
	}

	public String getRegOfficeCountry() {
		return regOfficeCountry;
	}

	public void setRegOfficeCountry(String regOfficeCountry) {
		this.regOfficeCountry = regOfficeCountry;
	}

	public String getRegOfficeState() {
		return regOfficeState;
	}

	public void setRegOfficeState(String regOfficeState) {
		this.regOfficeState = regOfficeState;
	}

	public String getRegOfficeCity() {
		return regOfficeCity;
	}

	public void setRegOfficeCity(String regOfficeCity) {
		this.regOfficeCity = regOfficeCity;
	}

	
	public String getRegOfficePostCode() {
		return regOfficePostCode;
	}

	public void setRegOfficePostCode(String regOfficePostCode) {
		this.regOfficePostCode = regOfficePostCode;
	}

	public String getOfficeRegion() {
		return officeRegion;
	}

	public void setOfficeRegion(String officeRegion) {
		this.officeRegion = officeRegion;
	}

	public String getRegOfficeRegion() {
		return regOfficeRegion;
	}

	public void setRegOfficeRegion(String regOfficeRegion) {
		this.regOfficeRegion = regOfficeRegion;
	}

	
	
	public String getBorrowerDUNSNo() {
		return borrowerDUNSNo;
	}

	public void setBorrowerDUNSNo(String borrowerDUNSNo) {
		this.borrowerDUNSNo = borrowerDUNSNo;
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

	public String getClassActivity3() {
		return classActivity3;
	}

	public void setClassActivity3(String classActivity3) {
		this.classActivity3 = classActivity3;
	}

	public String getWillfulDefaultStatus() {
		return willfulDefaultStatus;
	}

	public void setWillfulDefaultStatus(String willfulDefaultStatus) {
		this.willfulDefaultStatus = willfulDefaultStatus;
	}

	public String getSuitFilledStatus() {
		return suitFilledStatus;
	}

	public void setSuitFilledStatus(String suitFilledStatus) {
		this.suitFilledStatus = suitFilledStatus;
	}

	public String getDateOfSuit() {
		return dateOfSuit;
	}

	public void setDateOfSuit(String dateOfSuit) {
		this.dateOfSuit = dateOfSuit;
	}

	public String getSuitAmount() {
		return suitAmount;
	}

	public void setSuitAmount(String suitAmount) {
		this.suitAmount = suitAmount;
	}

	public String getSuitReferenceNo() {
		return suitReferenceNo;
	}

	public void setSuitReferenceNo(String suitReferenceNo) {
		this.suitReferenceNo = suitReferenceNo;
	}

	public String getDateWillfulDefault() {
		return dateWillfulDefault;
	}

	public void setDateWillfulDefault(String dateWillfulDefault) {
		this.dateWillfulDefault = dateWillfulDefault;
	}

	public String getRegOfficeDUNSNo() {
		return regOfficeDUNSNo;
	}

	public void setRegOfficeDUNSNo(String regOfficeDUNSNo) {
		this.regOfficeDUNSNo = regOfficeDUNSNo;
	}

	public String getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(String relatedType) {
		this.relatedType = relatedType;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getDirectorCountry() {
		return directorCountry;
	}

	public void setDirectorCountry(String directorCountry) {
		this.directorCountry = directorCountry;
	}

	public String getDirectorState() {
		return directorState;
	}

	public void setDirectorState(String directorState) {
		this.directorState = directorState;
	}

	public String getDirectorCity() {
		return directorCity;
	}

	public void setDirectorCity(String directorCity) {
		this.directorCity = directorCity;
	}

	public String getDirectorRegion() {
		return directorRegion;
	}

	public void setDirectorRegion(String directorRegion) {
		this.directorRegion = directorRegion;
	}

	public String getDirectorEmail() {
		return directorEmail;
	}

	public void setDirectorEmail(String directorEmail) {
		this.directorEmail = directorEmail;
	}

	public String getDirectorFax() {
		return directorFax;
	}

	public void setDirectorFax(String directorFax) {
		this.directorFax = directorFax;
	}

	public String getDirectorTelNo() {
		return directorTelNo;
	}

	public void setDirectorTelNo(String directorTelNo) {
		this.directorTelNo = directorTelNo;
	}

	public String getDirectorPostCode() {
		return directorPostCode;
	}

	public void setDirectorPostCode(String directorPostCode) {
		this.directorPostCode = directorPostCode;
	}

	public String getDirectorAddress3() {
		return directorAddress3;
	}

	public void setDirectorAddress3(String directorAddress3) {
		this.directorAddress3 = directorAddress3;
	}

	public String getDirectorAddress2() {
		return directorAddress2;
	}

	public void setDirectorAddress2(String directorAddress2) {
		this.directorAddress2 = directorAddress2;
	}

	public String getDirectorAddress1() {
		return directorAddress1;
	}

	public void setDirectorAddress1(String directorAddress1) {
		this.directorAddress1 = directorAddress1;
	}

	public String getPercentageOfControl() {
		return percentageOfControl;
	}

	public void setPercentageOfControl(String percentageOfControl) {
		this.percentageOfControl = percentageOfControl;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public String getBusinessEntityName() {
		return businessEntityName;
	}

	public void setBusinessEntityName(String businessEntityName) {
		this.businessEntityName = businessEntityName;
	}

	public String getDirectorPan() {
		return directorPan;
	}

	public void setDirectorPan(String directorPan) {
		this.directorPan = directorPan;
	}
	
	public String getDirectorAadhar() {
		return directorAadhar;
	}

	public void setDirectorAadhar(String directorAadhar) {
		this.directorAadhar = directorAadhar;
	}

	public String getRelatedDUNSNo() {
		return relatedDUNSNo;
	}

	public void setRelatedDUNSNo(String relatedDUNSNo) {
		this.relatedDUNSNo = relatedDUNSNo;
	}

	public String getDinNo() {
		return dinNo;
	}

	public void setDinNo(String dinNo) {
		this.dinNo = dinNo;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getPartyConsent() {
		return partyConsent;
	}

	public void setPartyConsent(String partyConsent) {
		this.partyConsent = partyConsent;
	}


	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getSubLineName() {
		return subLineName;
	}

	public void setSubLineName(String subLineName) {
		this.subLineName = subLineName;
	}

	public String getMainBranch() {
		return mainBranch;
	}

	public void setMainBranch(String mainBranch) {
		this.mainBranch = mainBranch;
	}
	
		public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	
	
	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	// ------added by bharat for
	// customers------------------------------end---------------------------------
	public String[][] getMapper() {
		DefaultLogger.debug(this, "Getting Mapper");
		String[][] input = { { "theOBTrxContext", TRX_MAPPER },
				{ "OBCMSCustomer", CUSTOMER_INFO_MAPPER } };
		return input;
	}

	public static final String CUSTOMER_INFO_MAPPER = "com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	

	
	//added by Shiv 200811
	
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
	
	public String getTypeOfCompany() {
		return typeOfCompany;
	}

	public void setTypeOfCompany(String typeOfCompany) {
		this.typeOfCompany = typeOfCompany;
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

	public String getFacilityFor() {
		return facilityFor;
	}

	public void setFacilityFor(String facilityFor) {
		this.facilityFor = facilityFor;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getEstateType() {
		return estateType;
	}

	public void setEstateType(String estateType) {
		this.estateType = estateType;
	}

	public String getCommRealEstateType() {
		return commRealEstateType;
	}

	public void setCommRealEstateType(String commRealEstateType) {
		this.commRealEstateType = commRealEstateType;
	}

	public String getTempCommRealEstateType() {
		return tempCommRealEstateType;
	}

	public void setTempCommRealEstateType(String tempCommRealEstateType) {
		this.tempCommRealEstateType = tempCommRealEstateType;
	}

	public String getGroupExposer() {
		return groupExposer;
	}

	public void setGroupExposer(String groupExposer) {
		this.groupExposer = groupExposer;
	}

	public String getPrioritySector() {
		return prioritySector;
	}

	public void setPrioritySector(String prioritySector) {
		this.prioritySector = prioritySector;
	}

	public String getTempPrioritySector() {
		return tempPrioritySector;
	}

	public void setTempPrioritySector(String tempPrioritySector) {
		this.tempPrioritySector = tempPrioritySector;
	}

	public List getCriFacility() {
		return criFacility;
	}

	public void setCriFacility(List criFacility) {
		this.criFacility = criFacility;
	}

	
	public List getUdfMtdList() {
		return udfMtdList;
	}

	public void setUdfMtdList(List udfMtdList) {
		this.udfMtdList = udfMtdList;
	}

	public String getIsBackedByGovt() {
		return isBackedByGovt;
	}

	public void setIsBackedByGovt(String isBackedByGovt) {
		this.isBackedByGovt = isBackedByGovt;
	}
	
	public String getTempFacilityData() {
		return tempFacilityData;
	}

	public void setTempFacilityData(String tempFacilityData) {
		this.tempFacilityData = tempFacilityData;
	}

	public String getGobutton() {
		return gobutton;
	}

	public void setGobutton(String gobutton) {
		this.gobutton = gobutton;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityRegName() {
		return cityRegName;
	}

	public void setCityRegName(String cityRegName) {
		this.cityRegName = cityRegName;
	}

	public String getStateRegName() {
		return stateRegName;
	}

	public void setStateRegName(String stateRegName) {
		this.stateRegName = stateRegName;
	}

	public String getRegionRegName() {
		return regionRegName;
	}

	public void setRegionRegName(String regionRegName) {
		this.regionRegName = regionRegName;
	}

	public String getCountryRegName() {
		return countryRegName;
	}

	public void setCountryRegName(String countryRegName) {
		this.countryRegName = countryRegName;
	}

	public String getRmRegionName() {
		return rmRegionName;
	}

	public void setRmRegionName(String rmRegionName) {
		this.rmRegionName = rmRegionName;
	}

	public String getRelManagerName() {
		return relManagerName;
	}

	public void setRelManagerName(String relManagerName) {
		this.relManagerName = relManagerName;
	}

	public String getStdCodeTelNo() {
		return stdCodeTelNo;
	}

	public void setStdCodeTelNo(String stdCodeTelNo) {
		this.stdCodeTelNo = stdCodeTelNo;
	}

	public String getStdCodeTelex() {
		return stdCodeTelex;
	}

	public void setStdCodeTelex(String stdCodeTelex) {
		this.stdCodeTelex = stdCodeTelex;
	}

	public String getStdCodeOfficeTelNo() {
		return stdCodeOfficeTelNo;
	}

	public void setStdCodeOfficeTelNo(String stdCodeOfficeTelNo) {
		this.stdCodeOfficeTelNo = stdCodeOfficeTelNo;
	}

	public String getStdCodeOfficeTelex() {
		return stdCodeOfficeTelex;
	}

	public void setStdCodeOfficeTelex(String stdCodeOfficeTelex) {
		this.stdCodeOfficeTelex = stdCodeOfficeTelex;
	}

	public String getSublineListEmpty() {
		return sublineListEmpty;
	}

	public void setSublineListEmpty(String sublineListEmpty) {
		this.sublineListEmpty = sublineListEmpty;
	}

	public String getSystemListEmpty() {
		return systemListEmpty;
	}

	public void setSystemListEmpty(String systemListEmpty) {
		this.systemListEmpty = systemListEmpty;
	}

	public String getDirectorListEmpty() {
		return directorListEmpty;
	}

	public void setDirectorListEmpty(String directorListEmpty) {
		this.directorListEmpty = directorListEmpty;
	}

	public String getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(String bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getDirStdCodeTelex() {
		return dirStdCodeTelex;
	}

	public void setDirStdCodeTelex(String dirStdCodeTelex) {
		this.dirStdCodeTelex = dirStdCodeTelex;
	}

	public String getDirStdCodeTelNo() {
		return dirStdCodeTelNo;
	}

	public void setDirStdCodeTelNo(String dirStdCodeTelNo) {
		this.dirStdCodeTelNo = dirStdCodeTelNo;
	}

	
	
	// ---------------- UDF Fields --------------------------------
	// Added by Pradeep on 11th Oct 2011
	// Currently 50 user defined fields (udfs) are supported.
	
	public String getGovtSchemeType() {
		return govtSchemeType;
	}

	public void setGovtSchemeType(String govtSchemeType) {
		this.govtSchemeType = govtSchemeType;
	}

	public String getFundedIncreDecre() {
		return fundedIncreDecre;
	}

	public void setFundedIncreDecre(String fundedIncreDecre) {
		this.fundedIncreDecre = fundedIncreDecre;
	}

	public String getNonFundedIncreDecre() {
		return nonFundedIncreDecre;
	}

	public void setNonFundedIncreDecre(String nonFundedIncreDecre) {
		this.nonFundedIncreDecre = nonFundedIncreDecre;
	}

	public String getMemoExposIncreDecre() {
		return memoExposIncreDecre;
	}

	public void setMemoExposIncreDecre(String memoExposIncreDecre) {
		this.memoExposIncreDecre = memoExposIncreDecre;
	}

	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String udf9;
	private String udf10;
	private String udf11;
	private String udf12;
	private String udf13;
	private String udf14;
	private String udf15;
	private String udf16;
	private String udf17;
	private String udf18;
	private String udf19;
	private String udf20;
	private String udf21;
	private String udf22;
	private String udf23;
	private String udf24;
	private String udf25;
	private String udf26;
	private String udf27;
	private String udf28;
	private String udf29;
	private String udf30;
	private String udf31;
	private String udf32;
	private String udf33;
	private String udf34;
	private String udf35;
	private String udf36;
	private String udf37;
	private String udf38;
	private String udf39;
	private String udf40;
	private String udf41;
	private String udf42;
	private String udf43;
	private String udf44;
	private String udf45;
	private String udf46;
	private String udf47;
	private String udf48;
	private String udf49;
	private String udf50;
	private String udf51;
	private String udf52;
	private String udf53;
	private String udf54;
	private String udf55;
	private String udf56;
	private String udf57;
	private String udf58;
	private String udf59;
	private String udf60;
	private String udf61;
	private String udf62;
	private String udf63;
	private String udf64;
	private String udf65;
	private String udf66;
	private String udf67;
	private String udf68;
	private String udf69;
	private String udf70;
	private String udf71;
	private String udf72;
	private String udf73;
	private String udf74;
	private String udf75;
	private String udf76;
	private String udf77;
	private String udf78;
	private String udf79;
	private String udf80;
	private String udf81;
	private String udf82;
	private String udf83;
	private String udf84;
	private String udf85;
	private String udf86;
	private String udf87;
	private String udf88;
	private String udf89;
	private String udf90;
	private String udf91;
	private String udf92;
	private String udf93;
	private String udf94;
	private String udf95;
	private String udf96;
	private String udf97;
	private String udf98;
	private String udf99;
	private String udf100;
	
		private String udf101;
		private String udf102;
		private String udf103;
		private String udf104;
		private String udf105;
		private String udf106;
	    private String udf107;
	    private String udf108;
	    private String udf109;
	    private String udf110;
	    private String udf111;
	    private String udf112;
	    private String udf113;
	    private String udf114;
	    private String udf115;
	  
	    
	    private String udf116;
	    private String udf117;
	    private String udf118;
	    private String udf119;
	    private String udf120;
	    private String udf121;
	    private String udf122;
	    private String udf123;
	    private String udf124;
	    private String udf125;
	    private String udf126;
	    private String udf127;
	    private String udf128;
	    private String udf129;
	    private String udf130;
	    private String udf131;
	    private String udf132;
	    private String udf133;
	    private String udf134;
	    private String udf135;
	    private String udf136;
	    private String udf137;
	    private String udf138;
	    private String udf139;
	    private String udf140;
	    private String udf141;
	    private String udf142;
	    private String udf143;
	    private String udf144;
	    private String udf145;
	    private String udf146;
	    private String udf147;
	    private String udf148;
	    private String udf149;
	    private String udf150;

	    private String udf151;
	    private String udf152;
	    private String udf153;
	    private String udf154;
	    private String udf155;
	    private String udf156;
	    private String udf157;
	    private String udf158;
	    private String udf159;
	    private String udf160;
	    private String udf161;
	    private String udf162;
	    private String udf163;
	    private String udf164;
	    private String udf165;
	    private String udf166;
	    private String udf167;
	    private String udf168;
	    private String udf169;
	    private String udf170;
	    private String udf171;
	    private String udf172;
	    private String udf173;
	    private String udf174;
	    private String udf175;
	    private String udf176;
	    private String udf177;
	    private String udf178;
	    private String udf179;
	    private String udf180;
	    private String udf181;
	    private String udf182;
	    private String udf183;
	    private String udf184;
	    private String udf185;
	    private String udf186;
	    private String udf187;
	    private String udf188;
	    private String udf189;
	    private String udf190;
	    private String udf191;
	    private String udf192;
	    private String udf193;
	    private String udf194;
	    private String udf195;
	    private String udf196;
	    private String udf197;
	    private String udf198;
	    private String udf199;
	    private String udf200;
	    private String udf201;
		private String udf202;
		private String udf203;
		private String udf204;
		private String udf205;
		private String udf206;
	    private String udf207;
	    private String udf208;
	    private String udf209;
	    private String udf210;
	    private String udf211;
	    private String udf212;
	    private String udf213;
	    private String udf214;
	    private String udf215;
	  
	   
	private long udfId;
	
	
	public long getUdfId() {
		return udfId;
	}

	public void setUdfId(long id) {
		this.udfId = id;
	}

	public String getUdf1() {
		return udf1;
	}

	public String getUdf10() {
		return udf10;
	}

	public String getUdf11() {
		return udf11;
	}

	public String getUdf12() {
		return udf12;
	}

	public String getUdf13() {
		return udf13;
	}

	public String getUdf14() {
		return udf14;
	}

	public String getUdf15() {
		return udf15;
	}

	public String getUdf16() {
		return udf16;
	}

	public String getUdf17() {
		return udf17;
	}

	public String getUdf18() {
		return udf18;
	}

	public String getUdf19() {
		return udf19;
	}

	public String getUdf2() {
		return udf2;
	}

	public String getUdf20() {
		return udf20;
	}

	public String getUdf21() {
		return udf21;
	}

	public String getUdf22() {
		return udf22;
	}

	public String getUdf23() {
		return udf23;
	}

	public String getUdf24() {
		return udf24;
	}

	public String getUdf25() {
		return udf25;
	}

	public String getUdf26() {
		return udf26;
	}

	public String getUdf27() {
		return udf27;
	}

	public String getUdf28() {
		return udf28;
	}

	public String getUdf29() {
		return udf29;
	}

	public String getUdf3() {
		return udf3;
	}

	public String getUdf30() {
		return udf30;
	}

	public String getUdf31() {
		return udf31;
	}

	public String getUdf32() {
		return udf32;
	}

	public String getUdf33() {
		return udf33;
	}

	public String getUdf34() {
		return udf34;
	}

	public String getUdf35() {
		return udf35;
	}

	public String getUdf36() {
		return udf36;
	}

	public String getUdf37() {
		return udf37;
	}

	public String getUdf38() {
		return udf38;
	}

	public String getUdf39() {
		return udf39;
	}

	public String getUdf4() {
		return udf4;
	}

	public String getUdf40() {
		return udf40;
	}

	public String getUdf41() {
		return udf41;
	}

	public String getUdf42() {
		return udf42;
	}

	public String getUdf43() {
		return udf43;
	}

	public String getUdf44() {
		return udf44;
	}

	public String getUdf45() {
		return udf45;
	}

	public String getUdf46() {
		return udf46;
	}

	public String getUdf47() {
		return udf47;
	}

	public String getUdf48() {
		return udf48;
	}

	public String getUdf49() {
		return udf49;
	}

	public String getUdf5() {
		return udf5;
	}

	public String getUdf50() {
		return udf50;
	}

	public String getUdf6() {
		return udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public String getUdf9() {
		return udf9;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public void setUdf10(String udf10) {
		this.udf10 = udf10;
	}

	public void setUdf11(String udf11) {
		this.udf11 = udf11;
	}

	public void setUdf12(String udf12) {
		this.udf12 = udf12;
	}

	public void setUdf13(String udf13) {
		this.udf13 = udf13;
	}

	public void setUdf14(String udf14) {
		this.udf14 = udf14;
	}

	public void setUdf15(String udf15) {
		this.udf15 = udf15;
	}

	public void setUdf16(String udf16) {
		this.udf16 = udf16;
	}

	public void setUdf17(String udf17) {
		this.udf17 = udf17;
	}

	public void setUdf18(String udf18) {
		this.udf18 = udf18;
	}

	public void setUdf19(String udf19) {
		this.udf19 = udf19;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public void setUdf20(String udf20) {
		this.udf20 = udf20;
	}

	public void setUdf21(String udf21) {
		this.udf21 = udf21;
	}

	public void setUdf22(String udf22) {
		this.udf22 = udf22;
	}

	public void setUdf23(String udf23) {
		this.udf23 = udf23;
	}

	public void setUdf24(String udf24) {
		this.udf24 = udf24;
	}

	public void setUdf25(String udf25) {
		this.udf25 = udf25;
	}

	public void setUdf26(String udf26) {
		this.udf26 = udf26;
	}

	public void setUdf27(String udf27) {
		this.udf27 = udf27;
	}

	public void setUdf28(String udf28) {
		this.udf28 = udf28;
	}

	public void setUdf29(String udf29) {
		this.udf29 = udf29;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public void setUdf30(String udf30) {
		this.udf30 = udf30;
	}

	public void setUdf31(String udf31) {
		this.udf31 = udf31;
	}

	public void setUdf32(String udf32) {
		this.udf32 = udf32;
	}

	public void setUdf33(String udf33) {
		this.udf33 = udf33;
	}

	public void setUdf34(String udf34) {
		this.udf34 = udf34;
	}

	public void setUdf35(String udf35) {
		this.udf35 = udf35;
	}

	public void setUdf36(String udf36) {
		this.udf36 = udf36;
	}

	public void setUdf37(String udf37) {
		this.udf37 = udf37;
	}

	public void setUdf38(String udf38) {
		this.udf38 = udf38;
	}

	public void setUdf39(String udf39) {
		this.udf39 = udf39;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public void setUdf40(String udf40) {
		this.udf40 = udf40;
	}

	public void setUdf41(String udf41) {
		this.udf41 = udf41;
	}

	public void setUdf42(String udf42) {
		this.udf42 = udf42;
	}

	public void setUdf43(String udf43) {
		this.udf43 = udf43;
	}

	public void setUdf44(String udf44) {
		this.udf44 = udf44;
	}

	public void setUdf45(String udf45) {
		this.udf45 = udf45;
	}

	public void setUdf46(String udf46) {
		this.udf46 = udf46;
	}

	public void setUdf47(String udf47) {
		this.udf47 = udf47;
	}

	public void setUdf48(String udf48) {
		this.udf48 = udf48;
	}

	public void setUdf49(String udf49) {
		this.udf49 = udf49;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public void setUdf50(String udf50) {
		this.udf50 = udf50;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}

	public void setUdf9(String udf9) {
		this.udf9 = udf9;
	}

	public String getUdf51() {
		return udf51;
	}

	public void setUdf51(String udf51) {
		this.udf51 = udf51;
	}

	public String getUdf52() {
		return udf52;
	}

	public void setUdf52(String udf52) {
		this.udf52 = udf52;
	}

	public String getUdf53() {
		return udf53;
	}

	public void setUdf53(String udf53) {
		this.udf53 = udf53;
	}

	public String getUdf54() {
		return udf54;
	}

	public void setUdf54(String udf54) {
		this.udf54 = udf54;
	}

	public String getUdf55() {
		return udf55;
	}

	public void setUdf55(String udf55) {
		this.udf55 = udf55;
	}

	public String getUdf56() {
		return udf56;
	}

	public void setUdf56(String udf56) {
		this.udf56 = udf56;
	}

	public String getUdf57() {
		return udf57;
	}

	public void setUdf57(String udf57) {
		this.udf57 = udf57;
	}

	public String getUdf58() {
		return udf58;
	}

	public void setUdf58(String udf58) {
		this.udf58 = udf58;
	}

	public String getUdf59() {
		return udf59;
	}

	public void setUdf59(String udf59) {
		this.udf59 = udf59;
	}

	public String getUdf60() {
		return udf60;
	}

	public void setUdf60(String udf60) {
		this.udf60 = udf60;
	}

	public String getUdf61() {
		return udf61;
	}

	public void setUdf61(String udf61) {
		this.udf61 = udf61;
	}

	public String getUdf62() {
		return udf62;
	}

	public void setUdf62(String udf62) {
		this.udf62 = udf62;
	}

	public String getUdf63() {
		return udf63;
	}

	public void setUdf63(String udf63) {
		this.udf63 = udf63;
	}

	public String getUdf64() {
		return udf64;
	}

	public void setUdf64(String udf64) {
		this.udf64 = udf64;
	}

	public String getUdf65() {
		return udf65;
	}

	public void setUdf65(String udf65) {
		this.udf65 = udf65;
	}

	public String getUdf66() {
		return udf66;
	}

	public void setUdf66(String udf66) {
		this.udf66 = udf66;
	}

	public String getUdf67() {
		return udf67;
	}

	public void setUdf67(String udf67) {
		this.udf67 = udf67;
	}

	public String getUdf68() {
		return udf68;
	}

	public void setUdf68(String udf68) {
		this.udf68 = udf68;
	}

	public String getUdf69() {
		return udf69;
	}

	public void setUdf69(String udf69) {
		this.udf69 = udf69;
	}

	public String getUdf70() {
		return udf70;
	}

	public void setUdf70(String udf70) {
		this.udf70 = udf70;
	}

	public String getUdf71() {
		return udf71;
	}

	public void setUdf71(String udf71) {
		this.udf71 = udf71;
	}

	public String getUdf72() {
		return udf72;
	}

	public void setUdf72(String udf72) {
		this.udf72 = udf72;
	}

	public String getUdf73() {
		return udf73;
	}

	public void setUdf73(String udf73) {
		this.udf73 = udf73;
	}

	public String getUdf74() {
		return udf74;
	}

	public void setUdf74(String udf74) {
		this.udf74 = udf74;
	}

	public String getUdf75() {
		return udf75;
	}

	public void setUdf75(String udf75) {
		this.udf75 = udf75;
	}

	public String getUdf76() {
		return udf76;
	}

	public void setUdf76(String udf76) {
		this.udf76 = udf76;
	}

	public String getUdf77() {
		return udf77;
	}

	public void setUdf77(String udf77) {
		this.udf77 = udf77;
	}

	public String getUdf78() {
		return udf78;
	}

	public void setUdf78(String udf78) {
		this.udf78 = udf78;
	}

	public String getUdf79() {
		return udf79;
	}

	public void setUdf79(String udf79) {
		this.udf79 = udf79;
	}

	public String getUdf80() {
		return udf80;
	}

	public void setUdf80(String udf80) {
		this.udf80 = udf80;
	}

	public String getUdf81() {
		return udf81;
	}

	public void setUdf81(String udf81) {
		this.udf81 = udf81;
	}

	public String getUdf82() {
		return udf82;
	}

	public void setUdf82(String udf82) {
		this.udf82 = udf82;
	}

	public String getUdf83() {
		return udf83;
	}

	public void setUdf83(String udf83) {
		this.udf83 = udf83;
	}

	public String getUdf84() {
		return udf84;
	}

	public void setUdf84(String udf84) {
		this.udf84 = udf84;
	}

	public String getUdf85() {
		return udf85;
	}

	public void setUdf85(String udf85) {
		this.udf85 = udf85;
	}

	public String getUdf86() {
		return udf86;
	}

	public void setUdf86(String udf86) {
		this.udf86 = udf86;
	}

	public String getUdf87() {
		return udf87;
	}

	public void setUdf87(String udf87) {
		this.udf87 = udf87;
	}

	public String getUdf88() {
		return udf88;
	}

	public void setUdf88(String udf88) {
		this.udf88 = udf88;
	}

	public String getUdf89() {
		return udf89;
	}

	public void setUdf89(String udf89) {
		this.udf89 = udf89;
	}

	public String getUdf90() {
		return udf90;
	}

	public void setUdf90(String udf90) {
		this.udf90 = udf90;
	}

	public String getUdf91() {
		return udf91;
	}

	public void setUdf91(String udf91) {
		this.udf91 = udf91;
	}

	public String getUdf92() {
		return udf92;
	}

	public void setUdf92(String udf92) {
		this.udf92 = udf92;
	}

	public String getUdf93() {
		return udf93;
	}

	public void setUdf93(String udf93) {
		this.udf93 = udf93;
	}

	public String getUdf94() {
		return udf94;
	}

	public void setUdf94(String udf94) {
		this.udf94 = udf94;
	}

	public String getUdf95() {
		return udf95;
	}

	public void setUdf95(String udf95) {
		this.udf95 = udf95;
	}

	public String getUdf96() {
		return udf96;
	}

	public void setUdf96(String udf96) {
		this.udf96 = udf96;
	}

	public String getUdf97() {
		return udf97;
	}

	public void setUdf97(String udf97) {
		this.udf97 = udf97;
	}

	public String getUdf98() {
		return udf98;
	}

	public void setUdf98(String udf98) {
		this.udf98 = udf98;
	}

	public String getUdf99() {
		return udf99;
	}

	public void setUdf99(String udf99) {
		this.udf99 = udf99;
	}

	public String getUdf100() {
		return udf100;
	}

	public void setUdf100(String udf100) {
		this.udf100 = udf100;
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

	public String getForm6061Checked() {
		return form6061Checked;
	}

	public void setForm6061Checked(String form6061Checked) {
		this.form6061Checked = form6061Checked;
	}

	public Character getPanValGenParamFlag() {
		return panValGenParamFlag;
	}

	public void setPanValGenParamFlag(Character panValGenParamFlag) {
		this.panValGenParamFlag = panValGenParamFlag;
	}

	public Character getIsPanValidated() {
		return isPanValidated;
	}

	public void setIsPanValidated(Character isPanValidated) {
		this.isPanValidated = isPanValidated;
	}

	public void setDpSharePercent(String dpSharePercent) {
		this.dpSharePercent = dpSharePercent;
	}

	public String getDpSharePercent() {
		return dpSharePercent;
	}

	//Start: Uma Khot:CRI Field addition enhancement


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
	//End: Uma Khot:CRI Field addition enhancement
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
	
	public String getRestrictedIndustries() {
		return restrictedIndustries;
	}

	public void setRestrictedIndustries(String restrictedIndustries) {
		this.restrictedIndustries = restrictedIndustries;
	}

	public String getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(String brokerType) {
		this.brokerType = brokerType;
	}
	public String getCapitalForCommodityAndExchange() {
		return capitalForCommodityAndExchange;
	}

	public void setCapitalForCommodityAndExchange(
			String capitalForCommodityAndExchange) {
		this.capitalForCommodityAndExchange = capitalForCommodityAndExchange;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCustomerFyClouser() {
		return customerFyClouser;
	}

	public void setCustomerFyClouser(String customerFyClouser) {
		this.customerFyClouser = customerFyClouser;
	}

		public String getUdf101() {
		return udf101;
	}

	public void setUdf101(String udf101) {
		this.udf101 = udf101;
	}

	public String getUdf102() {
		return udf102;
	}

	public void setUdf102(String udf102) {
		this.udf102 = udf102;
	}

	public String getUdf103() {
		return udf103;
	}

	public void setUdf103(String udf103) {
		this.udf103 = udf103;
	}

	public String getUdf104() {
		return udf104;
	}

	public void setUdf104(String udf104) {
		this.udf104 = udf104;
	}

	public String getUdf105() {
		return udf105;
	}

	public void setUdf105(String udf105) {
		this.udf105 = udf105;
	}

		 public String getUdf106() {
		return udf106;
	}

	public void setUdf106(String udf106) {
		this.udf106 = udf106;
	}

	public String getUdf107() {
		return udf107;
	}

	public void setUdf107(String udf107) {
		this.udf107 = udf107;
	}

	public String getUdf108() {
		return udf108;
	}

	public void setUdf108(String udf108) {
		this.udf108 = udf108;
	}

	public String getUdf109() {
		return udf109;
	}

	public void setUdf109(String udf109) {
		this.udf109 = udf109;
	}

	public String getUdf110() {
		return udf110;
	}

	public void setUdf110(String udf110) {
		this.udf110 = udf110;
	}

	public String getUdf111() {
		return udf111;
	}

	public void setUdf111(String udf111) {
		this.udf111 = udf111;
	}

	public String getUdf112() {
		return udf112;
	}

	public void setUdf112(String udf112) {
		this.udf112 = udf112;
	}

	public String getUdf113() {
		return udf113;
	}

	public void setUdf113(String udf113) {
		this.udf113 = udf113;
	}

	public String getUdf114() {
		return udf114;
	}

	public void setUdf114(String udf114) {
		this.udf114 = udf114;
	}

	public String getUdf115() {
		return udf115;
	}

	public void setUdf115(String udf115) {
		this.udf115 = udf115;
	}

	public String getUdf116() {
		return udf116;
	}

	public void setUdf116(String udf116) {
		this.udf116 = udf116;
	}

	public String getUdf117() {
		return udf117;
	}

	public void setUdf117(String udf117) {
		this.udf117 = udf117;
	}

	public String getUdf118() {
		return udf118;
	}

	public void setUdf118(String udf118) {
		this.udf118 = udf118;
	}

	public String getUdf119() {
		return udf119;
	}

	public void setUdf119(String udf119) {
		this.udf119 = udf119;
	}

	public String getUdf120() {
		return udf120;
	}

	public void setUdf120(String udf120) {
		this.udf120 = udf120;
	}

	public String getUdf121() {
		return udf121;
	}

	public void setUdf121(String udf121) {
		this.udf121 = udf121;
	}

	public String getUdf122() {
		return udf122;
	}

	public void setUdf122(String udf122) {
		this.udf122 = udf122;
	}

	public String getUdf123() {
		return udf123;
	}

	public void setUdf123(String udf123) {
		this.udf123 = udf123;
	}

	public String getUdf124() {
		return udf124;
	}

	public void setUdf124(String udf124) {
		this.udf124 = udf124;
	}

	public String getUdf125() {
		return udf125;
	}

	public void setUdf125(String udf125) {
		this.udf125 = udf125;
	}

	public String getUdf126() {
		return udf126;
	}

	public void setUdf126(String udf126) {
		this.udf126 = udf126;
	}

	public String getUdf127() {
		return udf127;
	}

	public void setUdf127(String udf127) {
		this.udf127 = udf127;
	}

	public String getUdf128() {
		return udf128;
	}

	public void setUdf128(String udf128) {
		this.udf128 = udf128;
	}

	public String getUdf129() {
		return udf129;
	}

	public void setUdf129(String udf129) {
		this.udf129 = udf129;
	}

	public String getUdf130() {
		return udf130;
	}

	public void setUdf130(String udf130) {
		this.udf130 = udf130;
	}

	public String getUdf131() {
		return udf131;
	}

	public void setUdf131(String udf131) {
		this.udf131 = udf131;
	}

	public String getUdf132() {
		return udf132;
	}

	public void setUdf132(String udf132) {
		this.udf132 = udf132;
	}

	public String getUdf133() {
		return udf133;
	}

	public void setUdf133(String udf133) {
		this.udf133 = udf133;
	}

	public String getUdf134() {
		return udf134;
	}

	public void setUdf134(String udf134) {
		this.udf134 = udf134;
	}

	public String getUdf135() {
		return udf135;
	}

	public void setUdf135(String udf135) {
		this.udf135 = udf135;
	}

	public String getUdf136() {
		return udf136;
	}

	public void setUdf136(String udf136) {
		this.udf136 = udf136;
	}

	public String getUdf137() {
		return udf137;
	}

	public void setUdf137(String udf137) {
		this.udf137 = udf137;
	}

	public String getUdf138() {
		return udf138;
	}

	public void setUdf138(String udf138) {
		this.udf138 = udf138;
	}

	public String getUdf139() {
		return udf139;
	}

	public void setUdf139(String udf139) {
		this.udf139 = udf139;
	}

	public String getUdf140() {
		return udf140;
	}

	public void setUdf140(String udf140) {
		this.udf140 = udf140;
	}

	public String getUdf141() {
		return udf141;
	}

	public void setUdf141(String udf141) {
		this.udf141 = udf141;
	}

	public String getUdf142() {
		return udf142;
	}

	public void setUdf142(String udf142) {
		this.udf142 = udf142;
	}

	public String getUdf143() {
		return udf143;
	}

	public void setUdf143(String udf143) {
		this.udf143 = udf143;
	}

	public String getUdf144() {
		return udf144;
	}

	public void setUdf144(String udf144) {
		this.udf144 = udf144;
	}

	public String getUdf145() {
		return udf145;
	}

	public void setUdf145(String udf145) {
		this.udf145 = udf145;
	}

	public String getUdf146() {
		return udf146;
	}

	public void setUdf146(String udf146) {
		this.udf146 = udf146;
	}

	public String getUdf147() {
		return udf147;
	}

	public void setUdf147(String udf147) {
		this.udf147 = udf147;
	}

	public String getUdf148() {
		return udf148;
	}

	public void setUdf148(String udf148) {
		this.udf148 = udf148;
	}

	public String getUdf149() {
		return udf149;
	}

	public void setUdf149(String udf149) {
		this.udf149 = udf149;
	}

	public String getUdf150() {
		return udf150;
	}

	public void setUdf150(String udf150) {
		this.udf150 = udf150;
	}

	public String getUdf151() {
		return udf151;
	}

	public void setUdf151(String udf151) {
		this.udf151 = udf151;
	}

	public String getUdf152() {
		return udf152;
	}

	public void setUdf152(String udf152) {
		this.udf152 = udf152;
	}

	public String getUdf153() {
		return udf153;
	}

	public void setUdf153(String udf153) {
		this.udf153 = udf153;
	}

	public String getUdf154() {
		return udf154;
	}

	public void setUdf154(String udf154) {
		this.udf154 = udf154;
	}

	public String getUdf155() {
		return udf155;
	}

	public void setUdf155(String udf155) {
		this.udf155 = udf155;
	}

	public String getUdf156() {
		return udf156;
	}

	public void setUdf156(String udf156) {
		this.udf156 = udf156;
	}

	public String getUdf157() {
		return udf157;
	}

	public void setUdf157(String udf157) {
		this.udf157 = udf157;
	}

	public String getUdf158() {
		return udf158;
	}

	public void setUdf158(String udf158) {
		this.udf158 = udf158;
	}

	public String getUdf159() {
		return udf159;
	}

	public void setUdf159(String udf159) {
		this.udf159 = udf159;
	}

	public String getUdf160() {
		return udf160;
	}

	public void setUdf160(String udf160) {
		this.udf160 = udf160;
	}

	public String getUdf161() {
		return udf161;
	}

	public void setUdf161(String udf161) {
		this.udf161 = udf161;
	}

	public String getUdf162() {
		return udf162;
	}

	public void setUdf162(String udf162) {
		this.udf162 = udf162;
	}

	public String getUdf163() {
		return udf163;
	}

	public void setUdf163(String udf163) {
		this.udf163 = udf163;
	}

	public String getUdf164() {
		return udf164;
	}

	public void setUdf164(String udf164) {
		this.udf164 = udf164;
	}

	public String getUdf165() {
		return udf165;
	}

	public void setUdf165(String udf165) {
		this.udf165 = udf165;
	}

	public String getUdf166() {
		return udf166;
	}

	public void setUdf166(String udf166) {
		this.udf166 = udf166;
	}

	public String getUdf167() {
		return udf167;
	}

	public void setUdf167(String udf167) {
		this.udf167 = udf167;
	}

	public String getUdf168() {
		return udf168;
	}

	public void setUdf168(String udf168) {
		this.udf168 = udf168;
	}

	public String getUdf169() {
		return udf169;
	}

	public void setUdf169(String udf169) {
		this.udf169 = udf169;
	}

	public String getUdf170() {
		return udf170;
	}

	public void setUdf170(String udf170) {
		this.udf170 = udf170;
	}

	public String getUdf171() {
		return udf171;
	}

	public void setUdf171(String udf171) {
		this.udf171 = udf171;
	}

	public String getUdf172() {
		return udf172;
	}

	public void setUdf172(String udf172) {
		this.udf172 = udf172;
	}

	public String getUdf173() {
		return udf173;
	}

	public void setUdf173(String udf173) {
		this.udf173 = udf173;
	}

	public String getUdf174() {
		return udf174;
	}

	public void setUdf174(String udf174) {
		this.udf174 = udf174;
	}

	public String getUdf175() {
		return udf175;
	}

	public void setUdf175(String udf175) {
		this.udf175 = udf175;
	}

	public String getUdf176() {
		return udf176;
	}

	public void setUdf176(String udf176) {
		this.udf176 = udf176;
	}

	public String getUdf177() {
		return udf177;
	}

	public void setUdf177(String udf177) {
		this.udf177 = udf177;
	}

	public String getUdf178() {
		return udf178;
	}

	public void setUdf178(String udf178) {
		this.udf178 = udf178;
	}

	public String getUdf179() {
		return udf179;
	}

	public void setUdf179(String udf179) {
		this.udf179 = udf179;
	}

	public String getUdf180() {
		return udf180;
	}

	public void setUdf180(String udf180) {
		this.udf180 = udf180;
	}

	public String getUdf181() {
		return udf181;
	}

	public void setUdf181(String udf181) {
		this.udf181 = udf181;
	}

	public String getUdf182() {
		return udf182;
	}

	public void setUdf182(String udf182) {
		this.udf182 = udf182;
	}

	public String getUdf183() {
		return udf183;
	}

	public void setUdf183(String udf183) {
		this.udf183 = udf183;
	}

	public String getUdf184() {
		return udf184;
	}

	public void setUdf184(String udf184) {
		this.udf184 = udf184;
	}

	public String getUdf185() {
		return udf185;
	}

	public void setUdf185(String udf185) {
		this.udf185 = udf185;
	}

	public String getUdf186() {
		return udf186;
	}

	public void setUdf186(String udf186) {
		this.udf186 = udf186;
	}

	public String getUdf187() {
		return udf187;
	}

	public void setUdf187(String udf187) {
		this.udf187 = udf187;
	}

	public String getUdf188() {
		return udf188;
	}

	public void setUdf188(String udf188) {
		this.udf188 = udf188;
	}

	public String getUdf189() {
		return udf189;
	}

	public void setUdf189(String udf189) {
		this.udf189 = udf189;
	}

	public String getUdf190() {
		return udf190;
	}

	public void setUdf190(String udf190) {
		this.udf190 = udf190;
	}

	public String getUdf191() {
		return udf191;
	}

	public void setUdf191(String udf191) {
		this.udf191 = udf191;
	}

	public String getUdf192() {
		return udf192;
	}

	public void setUdf192(String udf192) {
		this.udf192 = udf192;
	}

	public String getUdf193() {
		return udf193;
	}

	public void setUdf193(String udf193) {
		this.udf193 = udf193;
	}

	public String getUdf194() {
		return udf194;
	}

	public void setUdf194(String udf194) {
		this.udf194 = udf194;
	}

	public String getUdf195() {
		return udf195;
	}

	public void setUdf195(String udf195) {
		this.udf195 = udf195;
	}

	public String getUdf196() {
		return udf196;
	}

	public void setUdf196(String udf196) {
		this.udf196 = udf196;
	}

	public String getUdf197() {
		return udf197;
	}

	public void setUdf197(String udf197) {
		this.udf197 = udf197;
	}

	public String getUdf198() {
		return udf198;
	}

	public void setUdf198(String udf198) {
		this.udf198 = udf198;
	}

	public String getUdf199() {
		return udf199;
	}

	public void setUdf199(String udf199) {
		this.udf199 = udf199;
	}

	public String getUdf200() {
		return udf200;
	}

	public void setUdf200(String udf200) {
		this.udf200 = udf200;
	}

	public String getUdf201() {
		return udf201;
	}

	public void setUdf201(String udf201) {
		this.udf201 = udf201;
	}

	public String getUdf202() {
		return udf202;
	}

	public void setUdf202(String udf202) {
		this.udf202 = udf202;
	}

	public String getUdf203() {
		return udf203;
	}

	public void setUdf203(String udf203) {
		this.udf203 = udf203;
	}

	public String getUdf204() {
		return udf204;
	}

	public void setUdf204(String udf204) {
		this.udf204 = udf204;
	}

	public String getUdf205() {
		return udf205;
	}

	public void setUdf205(String udf205) {
		this.udf205 = udf205;
	}

	public String getUdf206() {
		return udf206;
	}

	public void setUdf206(String udf206) {
		this.udf206 = udf206;
	}

	public String getUdf207() {
		return udf207;
	}

	public void setUdf207(String udf207) {
		this.udf207 = udf207;
	}

	public String getUdf208() {
		return udf208;
	}

	public void setUdf208(String udf208) {
		this.udf208 = udf208;
	}

	public String getUdf209() {
		return udf209;
	}

	public void setUdf209(String udf209) {
		this.udf209 = udf209;
	}

	public String getUdf210() {
		return udf210;
	}

	public void setUdf210(String udf210) {
		this.udf210 = udf210;
	}

	public String getUdf211() {
		return udf211;
	}

	public void setUdf211(String udf211) {
		this.udf211 = udf211;
	}

	public String getUdf212() {
		return udf212;
	}

	public void setUdf212(String udf212) {
		this.udf212 = udf212;
	}

	public String getUdf213() {
		return udf213;
	}

	public void setUdf213(String udf213) {
		this.udf213 = udf213;
	}

	public String getUdf214() {
		return udf214;
	}

	public void setUdf214(String udf214) {
		this.udf214 = udf214;
	}

	public String getUdf215() {
		return udf215;
	}

	public void setUdf215(String udf215) {
		this.udf215 = udf215;
	}



	
	/*public String getOdfdCategory() {
		return odfdCategory;
	}

	public void setOdfdCategory(String odfdCategory) {
		this.odfdCategory = odfdCategory;
	}*/
	
	private String coBorrowerId;
	private String coBorrowerName;



	public String getCoBorrowerId() {
		return coBorrowerId;
	}

	public void setCoBorrowerId(String coBorrowerId) {
		this.coBorrowerId = coBorrowerId;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}
	
	private String partyNameAsPerPan;


	public String getPartyNameAsPerPan() {
		return partyNameAsPerPan;
	}

	public void setPartyNameAsPerPan(String partyNameAsPerPan) {
		this.partyNameAsPerPan = partyNameAsPerPan;
	}
	
	
	
	
}
