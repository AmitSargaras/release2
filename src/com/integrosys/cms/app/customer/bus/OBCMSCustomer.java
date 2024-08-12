/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCMSCustomer.java,v 1.11 2005/07/07 05:24:50 jychong Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.customer.OBCustomer;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This class represents a CMS Customer entity. Customer contains a reference to
 * the CMS Legal Entity.
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.11 $
 * @since $Date: 2005/07/07 05:24:50 $ Tag: $Name: $
 */
public class OBCMSCustomer extends OBCustomer implements ICMSCustomer {
	private String _custRef = null;

	private ICMSLegalEntity _entity = null;

	private String _domCountry = null;

	private String _domCountryReg = null;

	private String _swiftCode = null;

	private String _custOriginType = null;

	private boolean _disclosureInd = false;

	private Date _disclosureDate = null;

	private String _languageCode = null;

	private String _serviceLevelPriority = null;

	private boolean _treasureAppInd = false;

	private String _operationStatus = null;

	private Date _operationDate = null;

	private IContact[] _addresses = null;
	
	private ISystem[] otherSystem = null;

	private ICustomerSysXRef[] _xRefs = null;

	private String _cccStatus = null;

	private IBookingLocation _orgLocation = null;

	private boolean _isNonBorrower = false;

	private boolean _isCoBorrower = false;

	private long _version = 0;

	private String customerSegment = null;
	
	/*
	 * Added below fields by Ankit dt. 17-FEB-2016
	 * 
	 */
	
	private Date lastModifiedOn;
	private char panValGenParamFlagValue;
	private char form6061checked;
	private char isPanValidated;
	
	//Start: Uma Khot:CRI Field addition enhancement
	private String isRBIWilfulDefaultersList;
	private String nameOfBank;
	private String isDirectorMoreThanOne;
	private String nameOfDirectorsAndCompany;
	private String isBorrDefaulterWithBank;
	private String detailsOfDefault;
	private String extOfCompromiseAndWriteoff;
	private String isCibilStatusClean;
	private String detailsOfCleanCibil;
	private String finalBankMethodList;
	private String[] revisedEmailIdsArrayList;
	
	
	//End: Uma Khot:CRI Field addition enhancement
	
	public String[] getRevisedEmailIdsArrayList() {
		return revisedEmailIdsArrayList;
	}

	public void setRevisedEmailIdsArrayList(String[] revisedEmailIdsArrayList) {
		this.revisedEmailIdsArrayList = revisedEmailIdsArrayList;
	}

	public String getFinalBankMethodList() {
		return finalBankMethodList;
	}

	public void setFinalBankMethodList(String finalBankMethodList) {
		this.finalBankMethodList = finalBankMethodList;
	}

	/**
	 * Default Constructor
	 */
	public OBCMSCustomer() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICMSCustomer
	 */
	public OBCMSCustomer(ICMSCustomer value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters
	/**
	 * Get the customer reference
	 * 
	 * @return String
	 */
	public String getCustomerReference() {
		return _custRef;
	}

	/**
	 * Get the CMS Legal Entity.
	 * 
	 * @return ICMSLegalEntity
	 */
	public ICMSLegalEntity getCMSLegalEntity() {
		return _entity;
	}

	/**
	 * Get the domicile country
	 * 
	 * @return String
	 */
	public String getDomicileCountry() {
		return _domCountry;
	}

	/**
	 * Get the customer domicile country registration number.
	 * 
	 * @return String
	 */
	public String getDomicileCountryRegNumber() {
		return _domCountryReg;
	}

	/**
	 * Get the swift code.
	 * 
	 * @return String
	 */
	public String getSwiftCode() {
		return _swiftCode;
	}

	/**
	 * Get customer origin type.
	 * 
	 * @return String
	 */
	public String getCustomerOriginType() {
		return _custOriginType;
	}

	/**
	 * Get the Disclosure Agreement Indicator.
	 * 
	 * @return boolean
	 */
	public boolean getDisclosureAgreementInd() {
		return _disclosureInd;
	}

	/**
	 * Get the Disclosure Agreement Date.
	 * 
	 * @return Date
	 */
	public Date getDisclosureAgreementDate() {
		return _disclosureDate;
	}

	/**
	 * Get the Customer Language Code.
	 * 
	 * @return String
	 */
	public String getLanguageCode() {
		return _languageCode;
	}

	/**
	 * Get customer service level priority.
	 * 
	 * @return String
	 */
	public String getServiceLevelPriority() {
		return _serviceLevelPriority;
	}

	/**
	 * Get treasury appropriateness indicator.
	 * 
	 * @return boolean
	 */
	public boolean getTreasuryAppInd() {
		return _treasureAppInd;
	}

	/**
	 * Get Operation Status
	 * 
	 * @return String
	 */
	public String getOperationStatus() {
		return _operationStatus;
	}

	/**
	 * Get Operation Effective Date
	 * 
	 * @return Date
	 */
	public Date getOperationEffectiveDate() {
		return _operationDate;
	}

	/**
	 * Get Official Address List. The addresses should be sorted such that the
	 * latest address is the element 0 of the array.
	 * 
	 * @return IContact[]
	 */
	public IContact[] getOfficialAddresses() {
		return _addresses;
	}

	/**
	 * Get all System X-Ref related to this customer
	 * 
	 * @return ICustomerSysXRef[]
	 */
	public ICustomerSysXRef[] getCustomerSysXRefs() {
		return _xRefs;
	}

	/**
	 * Get the CCC status. This is used for non borrower only
	 * @return String - the CCC status
	 */
	public String getCCCStatus() {
		return _cccStatus;
	}

	/**
	 * Get the Originating Location
	 * @return IBookingLocation - the originating location
	 */
	public IBookingLocation getOriginatingLocation() {
		return _orgLocation;
	}

	/**
	 * Get non-borrower indicator
	 * 
	 * @return boolean
	 */
	public boolean getNonBorrowerInd() {
		return _isNonBorrower;
		// return getCMSLegalEntity().isNonBorrower();
	}

	/**
	 * Get Co-borrower indicator
	 * 
	 * @return
	 */
	public boolean getCoBorrowerInd() {
		return _isCoBorrower;
	}

	/**
	 * Get Version Time
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return _version;
	}

	// Setters
	/**
	 * Set the customer reference
	 * 
	 * @param value is of type String
	 */
	public void setCustomerReference(String value) {
		_custRef = value;
	}

	/**
	 * Set the CMS Legal Entity.
	 * 
	 * @param value is of type ICMSLegalEntity
	 */
	public void setCMSLegalEntity(ICMSLegalEntity value) {
		_entity = value;
		super.setLegalEntity(value);
	}

	/**
	 * Set the domicile country
	 * 
	 * @param value is of type String
	 */
	public void setDomicileCountry(String value) {
		_domCountry = value;
	}

	/**
	 * Set the customer domicile country registration number.
	 * 
	 * @param value is of type String
	 */
	public void setDomicileCountryRegNumber(String value) {
		_domCountryReg = value;
	}

	/**
	 * Set the swift code.
	 * 
	 * @param value is of type String
	 */
	public void setSwiftCode(String value) {
		_swiftCode = value;
	}

	/**
	 * Set customer origin type.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerOriginType(String value) {
		_custOriginType = value;
	}

	/**
	 * Set the Disclosure Agreement Indicator.
	 * 
	 * @param value is of type boolean
	 */
	public void setDisclosureAgreementInd(boolean value) {
		_disclosureInd = false;
	}

	/**
	 * Set the Disclosure Agreement Date.
	 * 
	 * @param value is of type Date
	 */
	public void setDisclosureAgreementDate(Date value) {
		_disclosureDate = value;
	}

	/**
	 * Set the Customer Language Code.
	 * 
	 * @param value is of type String
	 */
	public void setLanguageCode(String value) {
		_languageCode = value;
	}

	/**
	 * Set customer service level priority.
	 * 
	 * @param value is of type String
	 */
	public void setServiceLevelPriority(String value) {
		_serviceLevelPriority = value;
	}

	/**
	 * Set treasury appropriateness indicator.
	 * 
	 * @param value is of type boolean
	 */
	public void setTreasuryAppInd(boolean value) {
		_treasureAppInd = false;
	}

	/**
	 * Set Operation Status
	 * 
	 * @param value is of type String
	 */
	public void setOperationStatus(String value) {
		_operationStatus = value;
	}

	/**
	 * Set Operation Effective Date
	 * 
	 * @param value is of type Date
	 */
	public void setOperationEffectiveDate(Date value) {
		_operationDate = value;
	}

	/**
	 * Set Official Address List. The addresses should be sorted such that the
	 * latest address is the element 0 of the array.
	 * 
	 * @param value is of type IContact[]
	 */
	public void setOfficialAddresses(IContact[] value) {
		_addresses = value;
	}

	/**
	 * Set all System X-Ref related to this customer
	 * 
	 * @param value is of type ICustomerSysXRef[]
	 */
	public void setCustomerSysXRefs(ICustomerSysXRef[] value) {
		_xRefs = value;
	}

	public void setCCCStatus(String aCCCStatus) {
		_cccStatus = aCCCStatus;
	}

	/**
	 * Set the originating location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setOriginatingLocation(IBookingLocation anIBookingLocation) {
		_orgLocation = anIBookingLocation;
	}

	/**
	 * Set non-borrower indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setNonBorrowerInd(boolean value) {
		_isNonBorrower = value;
	}

	/**
	 * Set Co-borrower indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCoBorrowerInd(boolean value) {
		_isCoBorrower = value;
	}

	/**
	 * Set Version Time
	 * 
	 * @param version is of type long
	 */
	public void setVersionTime(long version) {
		_version = version;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Property added by
	 *@author sandiip.shinde
	 * to make Customer status 'ACTIVE' where new Customer 
	 * is created and status 'INACTIVE' where it is deleted 
	 * 
	 */
	private String status;

	private String cifId;
	//added by bharat waghela
    private String partyGroupName;
    
    private String mainBranch;
    
    private String branchCode;
	
	private String relationshipMgr;
	
	private String rmRegion;
	
	private String cycle;
	
	private String entity;
	
	private String pan;
	
	private String cinLlpin;

	private String aadharNumber;
	
	private String rbiIndustryCode;
	
	private String industryName;
	
	private String region;
	
	private String subLine;
      
	private String bankingMethod;
      
	private String totalFundedLimit;
    
	private String totalNonFundedLimit;
     
	private String fundedSharePercent;
     
	private String nonFundedSharePercent;
     
	private String  memoExposure;
    
	private String  totalSanctionedLimit;
     
	private String  mpbf;
      
	private String  fundedShareLimit;
      
	private String  nonFundedShareLimit;
	
	private String customerNameUpper;
	
	private String fundedIncreDecre;
	
	private String nonFundedIncreDecre;
	
	private String memoExposIncreDecre;
	
	
	
    private String borrowerDUNSNo;
	
	private String classActivity1;
	
	private String classActivity2;
	
	private String classActivity3;
	
	private String willfulDefaultStatus;
	
	private String suitFilledStatus;
	
	private Date dateOfSuit;
	
	private String suitAmount;
	
	private String currency;
	
	private String partyConsent;
	
	private String regOffice;
	
	private String suitReferenceNo;
	
	private Date dateWillfulDefault;
	
	private String regOfficeDUNSNo;

	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpSharePercent;
	
	
	//added by bharat waghela end
	//Santosh LEI CR 11/09/2018
	private String leiCode;
	private Date leiExpDate;
	
	private String listedCompany;
	private String isinNo;
	private String raroc;
	private Date raraocPeriod;
	private String yearEndPeriod;
	private String creditMgrEmpId;
	private String pfLrdCreditMgrEmpId;
	private String creditMgrSegment;
	
	private String multBankFundBasedLeadBankPer;
	private String multBankFundBasedHdfcBankPer;
	private String multBankNonFundBasedLeadBankPer;
	private String multBankNonFundBasedHdfcBankPer;
	
	private char isLeiValidated;
	private String deferLEI;
	private char leiValGenParamFlag;
	private Date lastModifiedLei;
	
	private String consBankFundBasedLeadBankPer;
	private String consBankFundBasedHdfcBankPer;
	private String consBankNonFundBasedLeadBankPer;
	private String consBankNonFundBasedHdfcBankPer;


	
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

	public Date getRaraocPeriod() {
		return raraocPeriod;
	}

	public void setRaraocPeriod(Date raraocPeriod) {
		this.raraocPeriod = raraocPeriod;
	}

	public String getYearEndPeriod() {
		return yearEndPeriod;
	}

	public void setYearEndPeriod(String yearEndPeriod) {
		this.yearEndPeriod = yearEndPeriod;
	}

	public String getLeiCode() {
		return leiCode;
	}

	public void setLeiCode(String leiCode) {
		this.leiCode = leiCode;
	}

	public Date getLeiExpDate() {
		return leiExpDate;
	}

	public void setLeiExpDate(Date leiExpDate) {
		this.leiExpDate = leiExpDate;
	}
	
	public char getIsLeiValidated() {
		return isLeiValidated;
	}

	public void setIsLeiValidated(char isLeiValidated) {
		this.isLeiValidated = isLeiValidated;
	}

	public char getLeiValGenParamFlag() {
		return leiValGenParamFlag;
	}

	public void setLeiValGenParamFlag(char leiValGenParamFlag) {
		this.leiValGenParamFlag = leiValGenParamFlag;
	}

	public String getDeferLEI() {
		return deferLEI;
	}

	public void setDeferLEI(String deferLEI) {
		this.deferLEI = deferLEI;
	}

	public Date getLastModifiedLei() {
		return lastModifiedLei;
	}

	public void setLastModifiedLei(Date lastModifiedLei) {
		this.lastModifiedLei = lastModifiedLei;
	}

	//End Santosh
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCifId() {
		return cifId;
	}

	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	public String getPartyGroupName() {
		return partyGroupName;
	}

	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}

	public String getRelationshipMgr() {
		return relationshipMgr;
	}

	public void setRelationshipMgr(String relationshipMgr) {
		this.relationshipMgr = relationshipMgr;
	}

	public String getRmRegion() {
		return rmRegion;
	}

	public void setRmRegion(String rmRegion) {
		this.rmRegion = rmRegion;
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


	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public String getCustomerNameUpper() {
		return customerNameUpper;
	}

	public void setCustomerNameUpper(String customerNameUpper) {
		this.customerNameUpper = customerNameUpper;
	}

	public ISystem[] getOtherSystem() {
		return otherSystem;
	}

	public void setOtherSystem(ISystem[] otherSystem) {
		this.otherSystem = otherSystem;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getRbiIndustryCode() {
		return rbiIndustryCode;
	}

	public void setRbiIndustryCode(String rbiIndustryCode) {
		this.rbiIndustryCode = rbiIndustryCode;
	}

	public String getIndustryName() {
		return industryName;
	}
	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
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

	public Date getDateOfSuit() {
		return dateOfSuit;
	}

	public void setDateOfSuit(Date dateOfSuit) {
		this.dateOfSuit = dateOfSuit;
	}

	public String getSuitAmount() {
		return suitAmount;
	}

	public void setSuitAmount(String suitAmount) {
		this.suitAmount = suitAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSuitReferenceNo() {
		return suitReferenceNo;
	}

	public void setSuitReferenceNo(String suitReferenceNo) {
		this.suitReferenceNo = suitReferenceNo;
	}

	public Date getDateWillfulDefault() {
		return dateWillfulDefault;
	}

	public void setDateWillfulDefault(Date dateWillfulDefault) {
		this.dateWillfulDefault = dateWillfulDefault;
	}

	public String getRegOfficeDUNSNo() {
		return regOfficeDUNSNo;
	}

	public void setRegOfficeDUNSNo(String regOfficeDUNSNo) {
		this.regOfficeDUNSNo = regOfficeDUNSNo;
	}

	public String getPartyConsent() {
		return partyConsent;
	}

	public void setPartyConsent(String partyConsent) {
		this.partyConsent = partyConsent;
	}

	public String getMainBranch() {
		return mainBranch;
	}

	public void setMainBranch(String mainBranch) {
		this.mainBranch = mainBranch;
	}

	public String getRegOffice() {
		return regOffice;
	}

	public void setRegOffice(String regOffice) {
		this.regOffice = regOffice;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
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

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public char getPanValGenParamFlagValue() {
		return panValGenParamFlagValue;
	}

	public void setPanValGenParamFlagValue(char panValGenParamFlagValue) {
		this.panValGenParamFlagValue = panValGenParamFlagValue;
	}

	public char getForm6061checked() {
		return form6061checked;
	}

	public void setForm6061checked(char form6061checked) {
		this.form6061checked = form6061checked;
	}

	public char getIsPanValidated() {
		return isPanValidated;
	}

	public void setIsPanValidated(char isPanValidated) {
		this.isPanValidated = isPanValidated;
	}

	public void setDpSharePercent(String dpSharePercent) {
		this.dpSharePercent = dpSharePercent;
	}

	public String getDpSharePercent() {
		return dpSharePercent;
	}
/*
	public ISystem[] getOtherSystem() {
		return otherSystem;
	}

	public void setOtherSystem(ISystem[] otherSystem) {
		this.otherSystem = otherSystem;
	}
*/
	

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
	@Override
	public String getIsRBIWilfulDefaultersList() {
		return isRBIWilfulDefaultersList;
	}

	
	@Override
	public void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaulterslist) {
		this.isRBIWilfulDefaultersList = isRBIWilfulDefaulterslist;
	}
	
	private String exceptionalCases;
	
	public String getExceptionalCases() {
		return exceptionalCases;
	}

	public void setExceptionalCases(String exceptionalCases) {
		this.exceptionalCases = exceptionalCases;
	}
	
	private String sanctionedAmtUpdatedFlag;
	
	private String relationshipMgrEmpCode;
	
	public void setRelationshipMgrEmpCode(String relationshipMgrEmpCode) 
	{
		this.relationshipMgrEmpCode = relationshipMgrEmpCode;
	}
	
	public String getRelationshipMgrEmpCode() 
	{
		return relationshipMgrEmpCode;
	}
	
	public String getSanctionedAmtUpdatedFlag() {
		return sanctionedAmtUpdatedFlag;
	}

	public void setSanctionedAmtUpdatedFlag(String sanctionedAmtUpdatedFlag) {
		this.sanctionedAmtUpdatedFlag = sanctionedAmtUpdatedFlag;
	}
	
	private String partyNameAsPerPan;

	public String getPartyNameAsPerPan() {
		return partyNameAsPerPan;
	}

	public void setPartyNameAsPerPan(String partyNameAsPerPan) {
		this.partyNameAsPerPan = partyNameAsPerPan;
	}
	
	}