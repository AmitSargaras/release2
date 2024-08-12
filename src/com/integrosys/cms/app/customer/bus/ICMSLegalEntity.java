/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICMSLegalEntity.java,v 1.7 2003/09/18 11:40:15 hltan Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.customer.ILegalEntity;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This interface represents a Legal Entity in the customer package. A Legal
 * Entity refers to portion of a customer's attributes that are constant across
 * different subdiaries. However this interface itself is an "incomplete"
 * picture of a customer's attributes, and therefore should be extended to
 * complete the definition of a customer.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/18 11:40:15 $ Tag: $Name: $
 */
public interface ICMSLegalEntity extends ILegalEntity, IValueObject {
	// Getters
	/**
	 * Get the Legal Entity Reference. This is the legal identify as obtained
	 * from host.
	 * 
	 * @return String
	 */
	public String getLEReference();

	/**
	 * Get the customer segment code.
	 * 
	 * @return String
	 */
	public String getCustomerSegment();

	/**
	 * Get the customer sub-segment code.
	 * 
	 * @return String
	 */
	public String getCustomerSubSegment();

	/**
	 * Get the Basel segment code.
	 * 
	 * @return String
	 */
	public String getBaselSegment();

	/**
	 * Get the Basel sub-segment code.
	 * 
	 * @return String
	 */
	public String getBaselSubSegment();

	/**
	 * Get the Customer Business Type code.
	 * 
	 * @return String
	 */
	public String getCustomerBizType();

	/**
	 * Get the Environment Risk value.
	 * 
	 * @return String
	 */
	public String getEnvironmentRisk();

	/**
	 * Get the top 1000 enterprise indicator.
	 * 
	 * @return boolean
	 */
	public boolean getTop1000Ind();

	/**
	 * Get the black listed status.
	 * 
	 * @return boolean
	 */
	public boolean getBlackListedInd();

	/**
	 * Get operation status.
	 * 
	 * @return String
	 */
	public String getOperationStatus();

	/**
	 * Get the Operation Effective Date.
	 * 
	 * @return Date
	 */
	public Date getOperationEffectiveDate();

	/**
	 * Get the Operation Description.
	 * 
	 * @return String
	 */
	public String getOperationDescription();

	/**
	 * Get Legal Registered Address
	 * 
	 * @return IContact
	 */
	public IContact[] getRegisteredAddress();
	
	public ICMSCustomerUdf[] getUdfMtdList();

	/**
	 * Get a list of credit grades
	 * 
	 * @return ICreditGrade[]
	 */
	public ICreditGrade[] getCreditGrades();

	/**
	 * Get ISIC Codes
	 * 
	 * @return IISICCode[]
	 */
	public IISICCode[] getISICCode();

	/**
	 * Get Credit Status
	 * 
	 * @return ICreditStatus[]
	 */
	public ICreditStatus[] getCreditStatus();

	/**
	 * Get KYC Records
	 * 
	 * @return IKYC[]
	 */
	public IKYC[] getKYCRecords();

	/**
	 * Get the customer type
	 * 
	 * @return String
	 */
	public String getCustomerType();

	// Setters

	/**
	 * Set the Legal Entity Reference. This is the legal identify as obtained
	 * from host.
	 * 
	 * @param value is of type String
	 */
	public void setLEReference(String value);

	/**
	 * Set the customer segment code.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerSegment(String value);

	/**
	 * Set the customer sub-segment code.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerSubSegment(String value);

	/**
	 * Set the Basel segment code.
	 * 
	 * @param value is of type String
	 */
	public void setBaselSegment(String value);

	/**
	 * Set the Basel sub-segment code.
	 * 
	 * @param value is of type String
	 */
	public void setBaselSubSegment(String value);

	/**
	 * Set the Customer Business Type code.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerBizType(String value);

	/**
	 * Set the Environment Risk value.
	 * 
	 * @param value is of type String
	 */
	public void setEnvironmentRisk(String value);

	/**
	 * Set the top 1000 enterprise indicator.
	 * 
	 * @param value is of type boolean
	 */
	public void setTop1000Ind(boolean value);

	/**
	 * Set the black listed status.
	 * 
	 * @param value is of type boolean
	 */
	public void setBlackListedInd(boolean value);

	/**
	 * Set operation status.
	 * 
	 * @param value is of type String
	 */
	public void setOperationStatus(String value);

	/**
	 * Set the Operation Effective Date.
	 * 
	 * @param value is of type Date
	 */
	public void setOperationEffectiveDate(Date value);

	/**
	 * Set the Operation Description.
	 * 
	 * @param value is of type String
	 */
	public void setOperationDescription(String value);

	/**
	 * Set Legal Registered Address
	 * 
	 * @param value is of type IContact[]
	 */
	public void setRegisteredAddress(IContact[] value);
	
	public void setUdfMtdList(ICMSCustomerUdf[] udfMthdArray);

	/**
	 * Set a list of credit grades
	 * 
	 * @param value is of type ICreditGrade[]
	 */
	public void setCreditGrades(ICreditGrade[] value);

	/**
	 * Set ISIC Codes
	 * 
	 * @param value is of type IISICCode[]
	 */
	public void setISICCode(IISICCode[] value);

	/**
	 * Set Credit Status
	 * 
	 * @param value is of type ICreditStatus
	 */
	public void setCreditStatus(ICreditStatus[] value);

	/**
	 * Set KYC Records
	 * 
	 * @param value is of type IKYC[]
	 */
	public void setKYCRecords(IKYC[] value);

	/**
	 * Set the customer Type
	 * 
	 * @param value is of type String
	 */
	public void setCustomerType(String customerType);

	public boolean isNonBorrower();

	/**
	 * Get the business group
	 * 
	 * @return String
	 */
	public String getBusinessGroup();

	/**
	 * Set the business group.
	 * 
	 * @param value is of type String
	 */
	public void setBusinessGroup(String value);

	/**
	 * Get the TFA amount
	 * 
	 * @return Double
	 */
	public Double getTFAAmount();

	/**
	 * Set the TFA amount.
	 * 
	 * @param value is of type Double
	 */
	public void setTFAAmount(Double value);

	/**
	 * Get the income range code
	 * 
	 * @return String
	 */
	public String getIncomeRange();

	/**
	 * Set the income range code
	 * 
	 * @param value is of type String
	 */
	public void setIncomeRange(String value);

	/**
	 * Get the business sector code
	 * 
	 * @return String
	 */
	public String getBusinessSector();

	/**
	 * Set the business sector code.
	 * 
	 * @param value is of type String
	 */
	public void setBusinessSector(String value);

	/**
	 * Get the new ID number
	 * 
	 * @return String
	 */
	// public String getNewIDNumber();
	/**
	 * Set the new ID number.
	 * 
	 * @param value is of type String
	 */
	// public void setNewIDNumber(String value);
	/**
	 * Get the country of Permanent Residence
	 * 
	 * @return String
	 */
	public String getCountryPR();

	/**
	 * Set the country of Permanent Residence.
	 * 
	 * @param value is of type String
	 */
	public void setCountryPR(String value);

	/**
	 * Get the language prefer code
	 * 
	 * @return String
	 */
	public String getLanguagePrefer();

	/**
	 * Set the language prefer code.
	 * 
	 * @param value is of type String
	 */
	public void setLanguagePrefer(String value);

	public String getIdOldNO();

	public void setIdOldNO(String idOldNO);

	public Date getIncorporateDate();

	public void setIncorporateDate(Date incorporateDate);

	public String getSourceID();

	public void setSourceID(String sourceID);

	/**
	 * Set the ID Type Code.
	 * 
	 * @param value is of type String
	 */
	public void setIDTypeCode(String value);

	/**
	 * Get the ID Type Code
	 * 
	 * @return String
	 */
	public String getIDTypeCode();

	/**
	 * Set the ID Type value.
	 * 
	 * @param value is of type String
	 */
	public void setIDType(String value);

	/**
	 * Get the ID Type value
	 * 
	 * @return String
	 */
	public String getIDType();

	/**
	 * Get legal id source code.
	 * 
	 * @return String
	 */
	public String getLegalIDSourceCode();

	/**
	 * Set legal id source code.
	 * 
	 * @param value of type String
	 */
	public void setLegalIDSourceCode(String value);

	/**
	 * Get legal id source value.
	 * 
	 * @return String
	 */
	public String getLegalIDSource();

	/**
	 * Set legal id source value.
	 * 
	 * @param value of type String
	 */
	public void setLegalIDSource(String value);
	
	public String getAccountOfficerID();

	public void setAccountOfficerID(String accountOfficerID);

	public String getAccountOfficerName();

	public void setAccountOfficerName(String accountOfficerName);
	
	public String getPartyGroupName() ;

	public void setPartyGroupName(String partyGroupName);

	public String getRelationshipMgrEmpCode();

	public void setRelationshipMgrEmpCode(String relationshipMgrEmpCode);
	
	public String getRelationshipMgr();

	public void setRelationshipMgr(String relationshipMgr);

	public String getRmRegion(); 

	public void setRmRegion(String rmRegion);

	public String getCycle();

	public void setCycle(String cycle);

	public String getEntity(); 

	public void setEntity(String entity); 
	
	public String getAadharNumber();
	
	public void setAadharNumber(String aadharNumber);
	
	public String getCinLlpin();
	
	public void setCinLlpin(String cinLlpin);

	public String getRbiIndustryCode(); 

	public void setRbiIndustryCode(String rbiIndustryCode);

	public String getIndustryName(); 

	public void setIndustryName(String industryName); 

	public String getPan(); 

	public void setPan(String pan);

	public String getRegion(); 
	
	public void setRegion(String region);
	
	public String getSubLine(); 

	public void setSubLine(String subLine); 

	public String getBankingMethod(); 

	public void setBankingMethod(String bankingMethod);

	public String getTotalFundedLimit(); 

	public void setTotalFundedLimit(String totalFundedLimit);

	public String getTotalNonFundedLimit(); 

	public void setTotalNonFundedLimit(String totalNonFundedLimit);

	public String getFundedSharePercent(); 

	public void setFundedSharePercent(String fundedSharePercent); 

	public String getNonFundedSharePercent(); 

	public void setNonFundedSharePercent(String nonFundedSharePercent); 

	public String getMemoExposure(); 

	public void setMemoExposure(String memoExposure);

	public String getTotalSanctionedLimit();
	
	public void setTotalSanctionedLimit(String totalSanctionedLimit);

	public String getMpbf();

	public void setMpbf(String mpbf) ;

	public String getFundedShareLimit() ;

	public void setFundedShareLimit(String fundedShareLimit);

	public String getNonFundedShareLimit(); 

	public void setNonFundedShareLimit(String nonFundedShareLimit); 
	
	public String getFundedIncreDecre();

	public void setFundedIncreDecre(String fundedIncreDecre);
	
	public String getNonFundedIncreDecre();

	public void setNonFundedIncreDecre(String nonFundedIncreDecre);
	
	public String getMemoExposIncreDecre();

	public void setMemoExposIncreDecre(String memoExposIncreDecre);
	
	public ISubline[] getSublineParty(); 
	
	public void setSublineParty(ISubline[] sublineParty) ;
	
	public ISystem[] getOtherSystem();  

	public void setOtherSystem(ISystem[] otherSystem); 
	
	public IDirector[] getDirector();  

	public void setDirector(IDirector[] director); 
	
	public IVendor[] getVendor();

	public void setVendor(IVendor[] vendor);
	
	public String getCoBorrowerDetailsInd();

	public void setCoBorrowerDetailsInd(String coBorrowerDetailsInd);
	
	public List<ICoBorrowerDetails> getCoBorrowerDetails();
	
	public void setCoBorrowerDetails(List<ICoBorrowerDetails> coBorrowerDetailsList);
	
	public IBankingMethod[] getBankList(); 

	public void setBankList(IBankingMethod[] bankList); 
	
	public String getBorrowerDUNSNo();

	public void setBorrowerDUNSNo(String borrowerDUNSNo);

	public String getClassActivity1() ;

	public void setClassActivity1(String classActivity1) ;

	public String getClassActivity2();

	public void setClassActivity2(String classActivity2);

	public String getClassActivity3() ;

	public void setClassActivity3(String classActivity3) ;

	public String getWillfulDefaultStatus();

	public void setWillfulDefaultStatus(String willfulDefaultStatus) ;

	public String getSuitFilledStatus() ;

	public void setSuitFilledStatus(String suitFilledStatus);

	public Date getDateOfSuit() ;

	public void setDateOfSuit(Date dateOfSuit) ;

	public String getSuitAmount() ;

	public void setSuitAmount(String suitAmount);

	public String getSuitReferenceNo();

	public void setSuitReferenceNo(String suitReferenceNo);

	public Date getDateWillfulDefault() ;

	public void setDateWillfulDefault(Date dateWillfulDefault);

	public String getRegOfficeDUNSNo() ;

	public void setRegOfficeDUNSNo(String regOfficeDUNSNo) ;

	public String getCurrency();

	public void setCurrency(String currency);
	
	public String getPartyConsent();  

	public void setPartyConsent(String partyConsent) ; 
	
	public String getRegOffice();  

	public void setRegOffice(String regOffice) ; 
	
	public String getMainBranch();

	public void setMainBranch(String mainBranch);
	
	public String getBranchCode() ;

	public void setBranchCode(String branchCode); 

	//added by Shiv 	220811
	
	public ICriInfo[] getCriList(); 

	public void setCriList(ICriInfo[] criList); 
	
	//added by Shiv 	220811
	
	public ICriFac[] getCriFacList(); 

	public void setCriFacList(ICriFac[] criFacList); 
	
	
	public ICMSCustomerUdf[] getUdfData();
	public void setUdfData(ICMSCustomerUdf[] udfData);

	//Start:Uma Khot:CRI Field addition enhancement
	public String getIsRBIWilfulDefaultersList();
	public void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaulterlist);
	public String getNameOfBank();
	public void setNameOfBank(String nameOfBank);
	public String getIsDirectorMoreThanOne();
	public void setIsDirectorMoreThanOne(String isDirectorMoreThanOne);
	public String getNameOfDirectorsAndCompany();
	public void setNameOfDirectorsAndCompany(String nameOfDirectorsAndCompany);
	public String getIsBorrDefaulterWithBank();
	public void setIsBorrDefaulterWithBank(String isBorrDefaulterWithBank);
	public String getDetailsOfDefault();
	public void setDetailsOfDefault(String detailsOfDefault);
	public String getExtOfCompromiseAndWriteoff();
	public void setExtOfCompromiseAndWriteoff(String extOfCompromiseAndWriteoff);
	public String getIsCibilStatusClean();
	public void setIsCibilStatusClean(String isCibilStatusClean);
	public String getDetailsOfCleanCibil();
	public void setDetailsOfCleanCibil(String detailsOfCleanCibil);
	  //End:Uma Khot:CRI Field addition enhancement
	
	//Uma Khot:Cam upload and Dp field calculation CR
	public String getDpSharePercent();
	public void setDpSharePercent(String dpSharePercent);
	//Santosh LEI CR 11/09/2018
	public String getLeiCode();
	public void setLeiCode(String leiCode);
	public Date getLeiExpDate();
	public void setLeiExpDate(Date leiExpDate);
//	public char getIsLeiValidated();
//	public void setIsLeiValidated(char isLeiValidated);
	public String getDeferLEI();
	public void setDeferLEI(String deferLEI);
	public char getLeiValGenParamFlag();
	public void setLeiValGenParamFlag(char leiValGenParamFlag);
	public Date getLastModifiedLei();
	public void setLastModifiedLei(Date lastModifiedLei);
	//End Santosh
	public String getExceptionalCases();
	public void setExceptionalCases(String exceptionalCases);
	
	// NEW CAM UI FORMAT START
	public String getListedCompany();	
	public void setListedCompany(String listedCompany);
	
	public String getIsinNo();	
	public void setIsinNo(String isinNo);
	
	public String getRaroc();  
	public void setRaroc(String raroc);
	
	public Date getRaraocPeriod();  
	public void setRaraocPeriod(Date raraocPeriod);
	
	public String getYearEndPeriod();  
	public void setYearEndPeriod(String yearEndPeriod);
	
	public String getCreditMgrEmpId();
	public void setCreditMgrEmpId(String creditMgrEmpId);

	public String getPfLrdCreditMgrEmpId(); 
	public void setPfLrdCreditMgrEmpId(String pfLrdCreditMgrEmpId);

	public String getCreditMgrSegment();
	public void setCreditMgrSegment(String creditMgrSegment);
	
	public String getMultBankFundBasedLeadBankPer();
	public void setMultBankFundBasedLeadBankPer(String multBankFundBasedLeadBankPer);

	public String getMultBankFundBasedHdfcBankPer();
	public void setMultBankFundBasedHdfcBankPer(String multBankFundBasedHdfcBankPer);

	public String getMultBankNonFundBasedLeadBankPer();
	public void setMultBankNonFundBasedLeadBankPer(String multBankNonFundBasedLeadBankPer);

	public String getMultBankNonFundBasedHdfcBankPer();
	public void setMultBankNonFundBasedHdfcBankPer(String multBankNonFundBasedHdfcBankPer);

	public String getConsBankFundBasedLeadBankPer();
	public void setConsBankFundBasedLeadBankPer(String consBankFundBasedLeadBankPer) ;

	public String getConsBankFundBasedHdfcBankPer();
	public void setConsBankFundBasedHdfcBankPer(String consBankFundBasedHdfcBankPer);

	public String getConsBankNonFundBasedLeadBankPer();
	public void setConsBankNonFundBasedLeadBankPer(String consBankNonFundBasedLeadBankPer);

	public String getConsBankNonFundBasedHdfcBankPer();
	public void setConsBankNonFundBasedHdfcBankPer(String consBankNonFundBasedHdfcBankPer);

	public String getFinalBankMethodList();
	public void setFinalBankMethodList(String finalBankMethodList);
	
	public String getPartyNameAsPerPan();
	public void setPartyNameAsPerPan(String partyNameAsPerPan);

	//NEW CAM UI FORMAT END
}