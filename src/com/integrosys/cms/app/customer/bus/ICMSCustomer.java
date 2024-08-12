/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICMSCustomer.java,v 1.12 2003/12/08 06:11:31 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.customer.ICustomer;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface represents a CMS Customer entity. Customer contains a
 * reference to the CMS Legal Entity.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.12 $
 * @since $Date: 2003/12/08 06:11:31 $ Tag: $Name: $
 */
public interface ICMSCustomer extends ICustomer, IValueObject {
	// Getters

	/**
	 * Get the customer reference
	 * 
	 * @return String
	 */
	public String getCustomerReference();

	/**
	 * Get the CMS Legal Entity.
	 * 
	 * @return ICMSLegalEntity
	 */
	public ICMSLegalEntity getCMSLegalEntity();

	/**
	 * Get the domicile country
	 * 
	 * @return String
	 */
	public String getDomicileCountry();

	/**
	 * Get the customer domicile country registration number.
	 * 
	 * @return String
	 */
	public String getDomicileCountryRegNumber();

	/**
	 * Get the swift code.
	 * 
	 * @return String
	 */
	public String getSwiftCode();

	/**
	 * Get customer origin type.
	 * 
	 * @return String
	 */
	public String getCustomerOriginType();

	/**
	 * Get the Disclosure Agreement Indicator.
	 * 
	 * @return boolean
	 */
	public boolean getDisclosureAgreementInd();

	/**
	 * Get the Disclosure Agreement Date.
	 * 
	 * @return Date
	 */
	public Date getDisclosureAgreementDate();

	/**
	 * Get the Customer Language Code.
	 * 
	 * @return String
	 */
	public String getLanguageCode();

	/**
	 * Get customer service level priority.
	 * 
	 * @return String
	 */
	public String getServiceLevelPriority();

	/**
	 * Get treasury appropriateness indicator.
	 * 
	 * @return boolean
	 */
	public boolean getTreasuryAppInd();

	/**
	 * Get Operation Status
	 * 
	 * @return String
	 */
	public String getOperationStatus();

	/**
	 * Get Operation Effective Date
	 * 
	 * @return Date
	 */
	public Date getOperationEffectiveDate();

	/**
	 * Get Official Address List. The addresses should be sorted such that the
	 * latest address is the element 0 of the array.
	 * 
	 * @return IContact[]
	 */
	public IContact[] getOfficialAddresses();
	
	

	/**
	 * Get all System X-Ref related to this customer
	 * 
	 * @return ICustomerSysXRef[]
	 */
	public ICustomerSysXRef[] getCustomerSysXRefs();

	/**
	 * Get the CCC status for non borrower
	 * @return String - the CCC status
	 */
	public String getCCCStatus();

	/**
	 * Get the Originating Location
	 * @return IBookingLocation - the originating location
	 */
	public IBookingLocation getOriginatingLocation();

	/**
	 * Get non-borrower indicator
	 * 
	 * @return boolean
	 */
	public boolean getNonBorrowerInd();

	// Setters
	/**
	 * Set the customer reference
	 * 
	 * @param value is of type String
	 */
	public void setCustomerReference(String value);

	/**
	 * Set the CMS Legal Entity.
	 * 
	 * @param value is of type ICMSLegalEntity
	 */
	public void setCMSLegalEntity(ICMSLegalEntity value);

	/**
	 * Set the domicile country
	 * 
	 * @param value is of type String
	 */
	public void setDomicileCountry(String value);

	/**
	 * Set the customer domicile country registration number.
	 * 
	 * @param value is of type String
	 */
	public void setDomicileCountryRegNumber(String value);

	/**
	 * Set the swift code.
	 * 
	 * @param value is of type String
	 */
	public void setSwiftCode(String value);

	/**
	 * Set customer origin type.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerOriginType(String value);

	/**
	 * Set the Disclosure Agreement Indicator.
	 * 
	 * @param value is of type boolean
	 */
	public void setDisclosureAgreementInd(boolean value);

	/**
	 * Set the Disclosure Agreement Date.
	 * 
	 * @param value is of type Date
	 */
	public void setDisclosureAgreementDate(Date value);

	/**
	 * Set the Customer Language Code.
	 * 
	 * @param value is of type String
	 */
	public void setLanguageCode(String value);

	/**
	 * Set customer service level priority.
	 * 
	 * @param value is of type String
	 */
	public void setServiceLevelPriority(String value);

	/**
	 * Set treasury appropriateness indicator.
	 * 
	 * @param value is of type boolean
	 */
	public void setTreasuryAppInd(boolean value);

	/**
	 * Set Operation Status
	 * 
	 * @param value is of type String
	 */
	public void setOperationStatus(String value);

	/**
	 * Set Operation Effective Date
	 * 
	 * @param value is of type Date
	 */
	public void setOperationEffectiveDate(Date value);

	/**
	 * Set Official Address List. The addresses should be sorted such that the
	 * latest address is the element 0 of the array.
	 * 
	 * @param value is of type IContact[]
	 */
	public void setOfficialAddresses(IContact[] value);

	/**
	 * Set all System X-Ref related to this customer
	 * 
	 * @param value is of type ICustomerSysXRef[]
	 */
	public void setCustomerSysXRefs(ICustomerSysXRef[] value);

	/**
	 * Set the CCC status. This is used for the non borrower only
	 * 
	 * @param aCCCStatus of String type
	 */
	public void setCCCStatus(String aCCCStatus);

	/**
	 * Set the originating location
	 * @param anIBookingLocation of IBookingLocation type
	 */
	public void setOriginatingLocation(IBookingLocation anIBookingLocation);

	/**
	 * Set non-borrower indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setNonBorrowerInd(boolean value);
	
	/**
	 * Methods added by
	 *@author sandiip.shinde
	 * to make Customer status 'ACTIVE' where new Customer 
	 * is created and status 'INACTIVE' where it is deleted 
	 * 
	 */
	public String getStatus();
	
	public void setStatus(String status);
	
	public String getCifId();
	
	public void setCifId(String status);
	
	public String getPartyGroupName() ;

	public void setPartyGroupName(String partyGroupName);

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
	
	public  String getFundedIncreDecre();

	public  void setFundedIncreDecre(String fundedIncreDecre);
	
	public  String getNonFundedIncreDecre();

	public  void setNonFundedIncreDecre(String nonFundedIncreDecre);
	
	public  String getMemoExposIncreDecre();

	public  void setMemoExposIncreDecre(String memoExposIncreDecre);
	
	public String getCustomerNameUpper(); 

	public void setCustomerNameUpper(String customerNameUpper); 
	
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

	public String getCurrency() ;

	public void setCurrency(String currency);
	
	public String getPartyConsent();

	public void setPartyConsent(String partyConsent);

	public String getRegOffice();

	public void setRegOffice(String regOffice);
	
	public String getMainBranch();

	public void setMainBranch(String mainBranch);
	
	public String getBranchCode() ;

	public void setBranchCode(String branchCode);

	public String getCustomerSegment();

	public void setCustomerSegment(String customerSegment);

	//Below methods added by Ankit - PAN NSDL validation on dt. 17-02-2016
	public Date getLastModifiedOn();

	public void setLastModifiedOn(Date lastModifiedOn);

	public char getPanValGenParamFlagValue();

	public void setPanValGenParamFlagValue(char panValGenParamFlagValue);

	public char getForm6061checked();

	public void setForm6061checked(char form6061checked);

	public char getIsPanValidated();

	public void setIsPanValidated(char isPanValidated);

	//Uma Khot:Cam upload and Dp field calculation CR
	public String getDpSharePercent();

	public void setDpSharePercent(String dpSharePercent);
	
	/*public ISystem[] getOtherSystem(); 
	
	public void setOtherSystem(ISystem[] otherSystem);*/
	
	 //Start:Uma Khot:CRI Field addition enhancement
	public String getIsRBIWilfulDefaultersList();
	public void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaultersList);
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
	//Santosh LEI CR 11/09/2018
	public String getLeiCode();
	public void setLeiCode(String leiCode);
	public Date getLeiExpDate();
	public void setLeiExpDate(Date leiExpDate);
	public char getIsLeiValidated();
	public void setIsLeiValidated(char isLeiValidated);
	public String getDeferLEI();
	public void setDeferLEI(String deferLEI);
	public char getLeiValGenParamFlag();
	public void setLeiValGenParamFlag(char leiValGenParamFlag);
	public Date getLastModifiedLei();
	public void setLastModifiedLei(Date lastModifiedLei);
	//End Santosh
	public String getExceptionalCases();
	public void setExceptionalCases(String exceptionalCases);
	
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
	
	public String[] getRevisedEmailIdsArrayList();
	public void setRevisedEmailIdsArrayList(String[] revisedEmailIdsArrayList);

	public String getRelationshipMgrEmpCode();
	public void setRelationshipMgrEmpCode(String relationshipMgrEmpCode);

	public String getSanctionedAmtUpdatedFlag();
	public void setSanctionedAmtUpdatedFlag(String sanctionedAmtUpdatedFlag);
	
	public String getPartyNameAsPerPan();
	public void setPartyNameAsPerPan(String partyNameAsPerPan);

}