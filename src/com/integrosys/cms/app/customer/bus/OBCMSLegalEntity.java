/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCMSLegalEntity.java,v 1.4 2003/09/18 11:40:15 hltan Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;
import java.util.List;
import com.integrosys.base.businfra.customer.OBLegalEntity;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Legal Entity in the customer package. A Legal Entity
 * refers to portion of a customer's attributes that are constant across
 * different subdiaries. However this interface itself is an "incomplete"
 * picture of a customer's attributes, and therefore should be extended to
 * complete the definition of a customer.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/18 11:40:15 $ Tag: $Name: $
 */
public class OBCMSLegalEntity extends OBLegalEntity implements ICMSLegalEntity {
	private String _leRef = null;

	private String _custSegment = null;

	private String _custSubSegment = null;

	private String _baselSegment = null;

	private String _baselSubSegment = null;

	private String _custBizType = null;

	private String _envRisk = null;

	private boolean _isTop1000 = false;

	private boolean _isBlackListed = false;

	private String _opStatus = null;

	private Date _opEffectiveDate = null;

	private String _opDesc = null;

	private IContact[] _regAddress = null;
	
	private ISystem[] otherSystem = null;
	
	private IDirector[] director = null;
	
	private IVendor[] vendor = null;
	
	private ISubline[] sublineParty = null;
	
	private IBankingMethod[] bankList = null;

	private ICreditGrade[] _creditGrades = null;

	private IISICCode[] _isicCodes = null;

	private ICreditStatus[] _creditStatus = null;

	private IKYC[] _kycs = null;

	private String _customerType = null;

	private long _version = 0;

	private String businessGroup = null;

	private Double TFAAmount = null;

	private String incomeRange = null;

	private String businessSector = null;

	private String idTypeCode = null;

	private String idType = null;

	// private String newIDNumber = null;
	private String countryPR = null;

	private String languagePrefer = null;

	private String idOldNO;

	private Date incorporateDate;

	private String sourceID;

	private String legalIDSourceCode;

	private String legalIDSource;
	
	private String accountOfficerID;
	
	private String accountOfficerName;
	
	private ICriInfo[] criList = null;  //added by Shiv 220811
	
	private ICriFac[] criFacList = null;  //added by Shiv 300811
	
	private String coBorrowerDetailsInd;
	
	private List<ICoBorrowerDetails> coBorrowerDetailsList;
	
	//private ISystem[] otherSystem = null;

	/**
	 * Default Constructor
	 */
	public OBCMSLegalEntity() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICMSLegalEntity
	 */
	public OBCMSLegalEntity(ICMSLegalEntity value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters
	/**
	 * Get the Legal Entity Reference. This is the legal identify as obtained
	 * from host.
	 * 
	 * @return String
	 */
	public String getLEReference() {
		return _leRef;
	}

	/**
	 * Get the customer segment code.
	 * 
	 * @return String
	 */
	public String getCustomerSegment() {
		return _custSegment;
	}

	/**
	 * Get the customer sub-segment code.
	 * 
	 * @return String
	 */
	public String getCustomerSubSegment() {
		return _custSubSegment;
	}

	/**
	 * Get the Basel segment code.
	 * 
	 * @return String
	 */
	public String getBaselSegment() {
		return _baselSegment;
	}

	/**
	 * Get the Basel sub-segment code.
	 * 
	 * @return String
	 */
	public String getBaselSubSegment() {
		return _baselSubSegment;
	}

	/**
	 * Get the Customer Business Type code.
	 * 
	 * @return String
	 */
	public String getCustomerBizType() {
		return _custBizType;
	}

	/**
	 * Get the Environment Risk value.
	 * 
	 * @return String
	 */
	public String getEnvironmentRisk() {
		return _envRisk;
	}

	/**
	 * Get the top 1000 enterprise indicator.
	 * 
	 * @return boolean
	 */
	public boolean getTop1000Ind() {
		return _isTop1000;
	}

	/**
	 * Get the black listed status.
	 * 
	 * @return boolean
	 */
	public boolean getBlackListedInd() {
		return _isBlackListed;
	}

	/**
	 * Get operation status.
	 * 
	 * @return String
	 */
	public String getOperationStatus() {
		return _opStatus;
	}

	/**
	 * Get the Operation Effective Date.
	 * 
	 * @return Date
	 */
	public Date getOperationEffectiveDate() {
		return _opEffectiveDate;
	}

	/**
	 * Get the Operation Description.
	 * 
	 * @return String
	 */
	public String getOperationDescription() {
		return _opDesc;
	}

	/**
	 * Get Legal Registered Address
	 * 
	 * @return IContact[]
	 */
	public IContact[] getRegisteredAddress() {
		return _regAddress;
	}

	

	/**
	 * Get a list of credit grades
	 * 
	 * @return ICreditGrade[]
	 */
	public ICreditGrade[] getCreditGrades() {
		return _creditGrades;
	}

	/**
	 * Get ISIC Codes
	 * 
	 * @return IISICCode[]
	 */
	public IISICCode[] getISICCode() {
		return _isicCodes;
	}

	/**
	 * Get Credit Status
	 * 
	 * @return ICreditStatus[]
	 */
	public ICreditStatus[] getCreditStatus() {
		return _creditStatus;
	}

	/**
	 * Get KYC Records
	 * 
	 * @return IKYC[]
	 */
	public IKYC[] getKYCRecords() {
		return _kycs;
	}

	/**
	 * Get the customer type
	 * 
	 * @return String
	 */
	public String getCustomerType() {
		return _customerType;
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
	 * Set the Legal Entity Reference. This is the legal identify as obtained
	 * from host.
	 * 
	 * @param value is of type String
	 */
	public void setLEReference(String value) {
		_leRef = value;
	}

	/**
	 * Set the customer segment code.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerSegment(String value) {
		_custSegment = value;
	}

	/**
	 * Set the customer sub-segment code.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerSubSegment(String value) {
		_custSubSegment = value;
	}

	/**
	 * Set the Basel segment code.
	 * 
	 * @param value is of type String
	 */
	public void setBaselSegment(String value) {
		_baselSegment = value;
	}

	/**
	 * Set the Basel sub-segment code.
	 * 
	 * @param value is of type String
	 */
	public void setBaselSubSegment(String value) {
		_baselSubSegment = value;
	}

	/**
	 * Set the Customer Business Type code.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerBizType(String value) {
		_custBizType = value;
	}

	/**
	 * Set the Environment Risk value.
	 * 
	 * @param value is of type String
	 */
	public void setEnvironmentRisk(String value) {
		_envRisk = value;
	}

	/**
	 * Set the top 1000 enterprise indicator.
	 * 
	 * @param value is of type boolean
	 */
	public void setTop1000Ind(boolean value) {
		_isTop1000 = false;
	}

	/**
	 * Set the black listed status.
	 * 
	 * @param value is of type boolean
	 */
	public void setBlackListedInd(boolean value) {
		_isBlackListed = false;
	}

	/**
	 * Set operation status.
	 * 
	 * @param value is of type String
	 */
	public void setOperationStatus(String value) {
		_opStatus = value;
	}

	/**
	 * Set the Operation Effective Date.
	 * 
	 * @param value is of type Date
	 */
	public void setOperationEffectiveDate(Date value) {
		_opEffectiveDate = value;
	}

	/**
	 * Set the Operation Description.
	 * 
	 * @param value is of type String
	 */
	public void setOperationDescription(String value) {
		_opDesc = value;
	}

	/**
	 * Set Legal Registered Address
	 * 
	 * @param value is of type IContact[]
	 */
	public void setRegisteredAddress(IContact[] value) {
		_regAddress = value;
	}

	/**
	 * Set a list of credit grades
	 * 
	 * @param value is of type ICreditGrade[]
	 */
	public void setCreditGrades(ICreditGrade[] value) {
		_creditGrades = value;
	}

	/**
	 * Set ISIC Codes
	 * 
	 * @param value is of type IISICCode[]
	 */
	public void setISICCode(IISICCode[] value) {
		_isicCodes = value;
	}

	/**
	 * Set Credit Status
	 * 
	 * @param value is of type ICreditStatus[]
	 */
	public void setCreditStatus(ICreditStatus[] value) {
		_creditStatus = value;
	}

	/**
	 * Set KYC Records
	 * 
	 * @param value is of type IKYC[]
	 */
	public void setKYCRecords(IKYC[] value) {
		_kycs = value;
	}

	/**
	 * Set the customer Type
	 * 
	 * @param value is of type String
	 */
	public void setCustomerType(String customerType) {
		_customerType = customerType;
	}

	public boolean isNonBorrower() {
		String customerType = getCustomerType();
		if (ICMSConstant.CUST_TYPE_NON_BORROWER_CORP.equals(customerType)
				|| ICMSConstant.CUST_TYPE_NON_BORROWER_PRIV.equals(customerType)) {
			return true;
		}
		return false;
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
	 * Get the business group
	 * 
	 * @return String
	 */
	public String getBusinessGroup() {
		return businessGroup;
	}

	/**
	 * Set the business group.
	 * 
	 * @param value is of type String
	 */
	public void setBusinessGroup(String value) {
		businessGroup = value;
	}

	/**
	 * Get the TFA amount
	 * 
	 * @return Double
	 */
	public Double getTFAAmount() {
		return TFAAmount;
	}

	/**
	 * Set the TFA amount.
	 * 
	 * @param value is of type Double
	 */
	public void setTFAAmount(Double value) {
		TFAAmount = value;
	}

	/**
	 * Get the income range code
	 * 
	 * @return String
	 */
	public String getIncomeRange() {
		return incomeRange;
	}

	/**
	 * Set the income range code
	 * 
	 * @param value is of type String
	 */
	public void setIncomeRange(String value) {
		incomeRange = value;
	}

	/**
	 * Get the business sector code
	 * 
	 * @return String
	 */
	public String getBusinessSector() {
		return businessSector;
	}

	/**
	 * Set the business sector code.
	 * 
	 * @param value is of type String
	 */
	public void setBusinessSector(String value) {
		businessSector = value;
	}

	/**
	 * Get the ID Type Code
	 * 
	 * @return String
	 */
	public String getIDTypeCode() {
		return this.idTypeCode;
	}

	/**
	 * Set the ID Type Code.
	 * 
	 * @param value is of type String
	 */
	public void setIDTypeCode(String value) {
		this.idTypeCode = value;
	}

	/**
	 * Get the ID Type value
	 * 
	 * @return String
	 */
	public String getIDType() {
		return this.idType;
	}

	/**
	 * Set the ID Type value.
	 * 
	 * @param value is of type String
	 */
	public void setIDType(String value) {
		this.idType = value;
	}

	/**
	 * Get the new ID number
	 * 
	 * @return String
	 */
	/*
	 * public String getNewIDNumber() { return newIDNumber; }
	 */

	/**
	 * Set the new ID number.
	 * 
	 * @param value is of type String
	 */
	/*
	 * public void setNewIDNumber(String value) { newIDNumber = value; }
	 */

	/**
	 * Get the country of Permanent Residence
	 * 
	 * @return String
	 */
	public String getCountryPR() {
		return countryPR;
	}

	/**
	 * Set the country of Permanent Residence.
	 * 
	 * @param value is of type String
	 */
	public void setCountryPR(String value) {
		countryPR = value;
	}

	/**
	 * Get the language prefer code
	 * 
	 * @return String
	 */
	public String getLanguagePrefer() {
		return languagePrefer;
	}

	/**
	 * Set the language prefer code.
	 * 
	 * @param value is of type String
	 */
	public void setLanguagePrefer(String value) {
		languagePrefer = value;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String getIdOldNO() {
		return idOldNO;
	}

	public void setIdOldNO(String idOldNO) {
		this.idOldNO = idOldNO;
	}

	public Date getIncorporateDate() {
		return incorporateDate;
	}

	public void setIncorporateDate(Date incorporateDate) {
		this.incorporateDate = incorporateDate;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getLegalIDSourceCode() {
		return this.legalIDSourceCode;
	}

	public void setLegalIDSourceCode(String value) {
		this.legalIDSourceCode = value;
	}

	public String getLegalIDSource() {
		return this.legalIDSource;
	}

	public void setLegalIDSource(String value) {
		this.legalIDSource = value;
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
	//added by bharat waghela
    private String partyGroupName;
    
	private String relationshipMgrEmpCode;
	
	private String relationshipMgr;
	
	private String rmRegion;
	
	private String mainBranch;
	
	private String branchCode;
	
	private String cycle;
	
	private String entity;
	
	private String cinLlpin;

	private String dateOfIncorporation;

	private String aadharNumber;
	
	private String rbiIndustryCode;
	
	private String industryName;
	
	private String pan;
	
	private String region;
	
	private String subLine;
    
	private String bankingMethod;
      
	private String finalBankMethodList;
      
	private String totalFundedLimit;
    
	private String totalNonFundedLimit;
     
	private String fundedSharePercent;
     
	private String nonFundedSharePercent;
     
	private String  memoExposure;
    
	private String  totalSanctionedLimit;
     
	private String  mpbf;
      
	private String  fundedShareLimit;
      
	private String  nonFundedShareLimit;
	
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
	//End: Uma Khot:CRI Field addition enhancement
	
	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpSharePercent;
	
	//added by bharat waghela end
	//Santosh LEI CR 11/09/2018
	private String leiCode;
	private Date leiExpDate;

	//NEW CAM UI FORMAT START
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

	private String consBankFundBasedLeadBankPer;
	private String consBankFundBasedHdfcBankPer;
	private String consBankNonFundBasedLeadBankPer;
	private String consBankNonFundBasedHdfcBankPer;


	public String getFinalBankMethodList() {
		return finalBankMethodList;
	}

	public void setFinalBankMethodList(String finalBankMethodList) {
		this.finalBankMethodList = finalBankMethodList;
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

//	private char isLeiValidated;
	private String deferLEI;
	private char leiValGenParamFlag;
	private Date lastModifiedLei;
	
	
//	public char getIsLeiValidated() {
//		return isLeiValidated;
//	}
//
//	public void setIsLeiValidated(char isLeiValidated) {
//		this.isLeiValidated = isLeiValidated;
//	}

	public String getDeferLEI() {
		return deferLEI;
	}

	public void setDeferLEI(String deferLEI) {
		this.deferLEI = deferLEI;
	}

	public char getLeiValGenParamFlag() {
		return leiValGenParamFlag;
	}

	public void setLeiValGenParamFlag(char leiValGenParamFlag) {
		this.leiValGenParamFlag = leiValGenParamFlag;
	}

	public Date getLastModifiedLei() {
		return lastModifiedLei;
	}

	public void setLastModifiedLei(Date lastModifiedLei) {
		this.lastModifiedLei = lastModifiedLei;
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
	//End Santosh
	public String getPartyGroupName() {
		return partyGroupName;
	}

	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}

	public String getRelationshipMgr() {
		return relationshipMgr;
	}

	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public String getRbiIndustryCode() {
		return rbiIndustryCode;
	}

	public void setRbiIndustryCode(String rbiIndustryCode) {
		this.rbiIndustryCode = rbiIndustryCode;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this .pan = pan;
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

	public ISystem[] getOtherSystem() {
		return otherSystem;
	}

	public void setOtherSystem(ISystem[] otherSystem) {
		this.otherSystem = otherSystem;
	}
	
	public IDirector[] getDirector() {
		return director;
	}

	public void setDirector(IDirector[] director) {
		this.director = director;
	}
	
	public IVendor[] getVendor() {
		return vendor;
	}

	public void setVendor(IVendor[] vendor) {
		this.vendor = vendor;
	}

	public IBankingMethod[] getBankList() {
		return bankList;
	}

	public void setBankList(IBankingMethod[] bankList) {
		this.bankList = bankList;
	}

	public ISubline[] getSublineParty() {
		return sublineParty;
	}

	public void setSublineParty(ISubline[] sublineParty) {
		this.sublineParty = sublineParty;
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

	public String getPartyConsent() {
		return partyConsent;
	}

	public void setPartyConsent(String partyConsent) {
		this.partyConsent = partyConsent;
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

	//added by Shiv 220811
	public ICriInfo[] getCriList() {
		return criList;
	}

	public void setCriList(ICriInfo[] criList) {
		this.criList = criList;
	}

	public ICriFac[] getCriFacList() {
		return criFacList;
	}

	public void setCriFacList(ICriFac[] criFacList) {
		this.criFacList = criFacList;
	}

	private ICMSCustomerUdf[] udfData = null;

	public ICMSCustomerUdf[] getUdfData() {
		return udfData;
	}

	public void setUdfData(ICMSCustomerUdf[] udfData) {
		this.udfData = udfData;
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

	
	//Start: Uma Khot:CRI Field addition enhancement


	public String getNameOfBank() {
		return nameOfBank;
	}

	public String getIsRBIWilfulDefaultersList() {
		return isRBIWilfulDefaultersList;
	}

	public void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaulterslist) {
		this.isRBIWilfulDefaultersList = isRBIWilfulDefaultersList;
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
	
	public void setDpSharePercent(String dpSharePercent) {
		this.dpSharePercent = dpSharePercent;
	}

	public String getDpSharePercent() {
		return dpSharePercent;
	}

	public String getRelationshipMgrEmpCode() {
		return relationshipMgrEmpCode;
	}

	public void setRelationshipMgrEmpCode(String relationshipMgrEmpCode) {
		this.relationshipMgrEmpCode = relationshipMgrEmpCode;
	}
	
	private String exceptionalCases;

	private ICMSCustomerUdf[] udfMthdArray=null;
	
	public String getExceptionalCases() {
		return exceptionalCases;
	}

	public void setExceptionalCases(String exceptionalCases) {
		this.exceptionalCases = exceptionalCases;
	}

	
	public String getCoBorrowerDetailsInd() {
		return coBorrowerDetailsInd;
	}

	public void setCoBorrowerDetailsInd(String coBorrowerDetailsInd) {
		this.coBorrowerDetailsInd = coBorrowerDetailsInd;
	}

	public List<ICoBorrowerDetails> getCoBorrowerDetails() {
		return coBorrowerDetailsList;
	}

	public void setCoBorrowerDetails(List<ICoBorrowerDetails> coBorrowerDetailsList) {
		this.coBorrowerDetailsList = coBorrowerDetailsList;
	}
	



	public ICMSCustomerUdf[] getUdfMtdList() {
		return udfMthdArray;
	}

	public void setUdfMtdList(ICMSCustomerUdf[] udfMthdArray) {
		this.udfMthdArray=udfMthdArray;
		
	}

	private String partyNameAsPerPan;

	public String getPartyNameAsPerPan() {
		return partyNameAsPerPan;
	}

	public void setPartyNameAsPerPan(String partyNameAsPerPan) {
		this.partyNameAsPerPan = partyNameAsPerPan;
	}

}