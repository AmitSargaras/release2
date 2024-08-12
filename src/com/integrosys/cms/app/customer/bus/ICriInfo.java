/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IContact.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface represents a contact information which includes address, email
 * and phone numbers.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface ICriInfo extends java.io.Serializable {
	
	public long getCriInfoID();

	public void setCriInfoID(long criInfoID);

	public long getLEID();
		
	public void setLEID(long value);

	public  String getCustomerRamID();

	public  void setCustomerRamID(String customerRamID);
	
	public  String getCustomerAprCode();

	public  void setCustomerAprCode(String customerAprCode);

	public  String getCustomerExtRating();

	public  void setCustomerExtRating(String customerExtRating);	

	public  String getIsNbfs();

	public  void setIsNbfs(String isNbfs);
	            
	public  String getNbfsA();

	public  void setNbfsA(String nbfsA);
	
	public  String getNbfsB();

	public  void setNbfsB(String nbfsB);
	
	public  String getIsPrioritySector();

	public  void setIsPrioritySector(String isPrioritySector);
	
	public  String getMsmeClassification();

	public  void setMsmeClassification(String msmeClassification);
	                
	public  String getIsPermenentSsiCert();

	public  void setIsPermenentSsiCert(String isPermenentSsiCert);
	            
	public  String getIsWeakerSection();

	public  void setIsWeakerSection(String isWeakerSection);
	
	public  String getWeakerSection();

	public  void setWeakerSection(String weakerSection);
	
	public  String getGovtSchemeType();

	public  void setGovtSchemeType(String govtSchemeType);
	
	public  String getIsKisanCreditCard();

	public  void setIsKisanCreditCard(String isKisanCreditCard);
	
	public  String getIsMinorityCommunity();

	public  void setIsMinorityCommunity(String isMinorityCommunity);	                           
	                            
	public  String getMinorityCommunity();

	public  void setMinorityCommunity(String minorityCommunity);
	                
	public  String getIsCapitalMarketExpos();

	public  void setIsCapitalMarketExpos(String isCapitalMarketExpos);
	            
	public  String getIsRealEstateExpos();

	public  void setIsRealEstateExpos(String isRealEstateExpos);
	
	public  String getRealEstateExposType();

	public  void setRealEstateExposType(String realEstateExposType);
	
	public  String getRealEstateExposComm();

	public  void setRealEstateExposComm(String realEstateExposComm);
	
	public  String getIsCommoditiesExposer();

	public  void setIsCommoditiesExposer(String isCommoditiesExposer);	  				         
	                 
	public  String getIsSensitive();

	public  void setIsSensitive(String isSensitive);
	
	public  String getCommoditiesName();

	public  void setCommoditiesName(String commoditiesName);
	
	public  String getGrossInvsInPM();

	public  void setGrossInvsInPM(String grossInvsInPM);		                                 
	                      
	public  String getGrossInvsInEquip();

	public  void setGrossInvsInEquip(String grossInvsInEquip);	 
		
	public  String getPsu();

	public  void setPsu(String psu);
	
	public  String getPsuPercentage();

	public  void setPsuPercentage(String psuPercentage);	                                 
	                                 
	public  String getGovtUnderTaking();

	public  void setGovtUnderTaking(String govtUnderTaking);
	
	public  String getIsProjectFinance();

	public  void setIsProjectFinance(String isProjectFinance);	                                    
	                                 
	public  String getIsInfrastructureFinanace();

	public  void setIsInfrastructureFinanace(String isInfrastructureFinanace);	                                 
	                                 
	public  String getInfrastructureFinanaceType();

	public  void setInfrastructureFinanaceType(String infrastructureFinanaceType);
	
	public  String getIsSemsGuideApplicable();

	public  void setIsSemsGuideApplicable(String isSemsGuideApplicable);		                                 
	                                 
	public  String getIsFailIfcExcluList();

	public  void setIsFailIfcExcluList(String isFailIfcExcluList);
	
	public  String getIsTufs();

	public  void setIsTufs(String isTufs);	                                    
	                                 
	public  String getIsRbiDefaulter();

	public  void setIsRbiDefaulter(String isRbiDefaulter);	                                 
	                                 
	public  String getRbiDefaulter();

	public  void setRbiDefaulter(String rbiDefaulter);
	
	public  String getIsLitigation();

	public  void setIsLitigation(String isLitigation);		                                 
	                                 
	public  String getLitigationBy();

	public  void setLitigationBy(String litigationBy);	                                 
	                                 
	public  String getIsInterestOfBank();

	public  void setIsInterestOfBank(String isInterestOfBank);
	
	public  String getInterestOfBank();

	public  void setInterestOfBank(String interestOfBank);		                                 
	               
	public  String getIsAdverseRemark();

	public  void setIsAdverseRemark(String isAdverseRemark);	                                 
	                                 
	public  String getAdverseRemark();

	public  void setAdverseRemark(String adverseRemark);
	
	public  String getAuditType();

	public  void setAuditType(String auditType);	                           
	                   
	public  String getAvgAnnualTurnover();

	public  void setAvgAnnualTurnover(String avgAnnualTurnover);	                                 
	
	public  String getIndustryExposer();

	public  void setIndustryExposer(String industryExposer);	                                 
	                                 
	public  String getIsDirecOtherBank();

	public  void setIsDirecOtherBank(String isDirecOtherBank);
	
	public  String getDirecOtherBank();

	public  void setDirecOtherBank(String direcOtherBank);	
	                                 
	public  String getIsPartnerOtherBank();

	public  void setIsPartnerOtherBank(String isPartnerOtherBank);	                                 
	                                 
	public  String getPartnerOtherBank();

	public  void setPartnerOtherBank(String partnerOtherBank);
	
	public  String getIsSubstantialOtherBank();

	public  void setIsSubstantialOtherBank(String isSubstantialOtherBank);		        
	
	public  String getSubstantialOtherBank();

	public  void setSubstantialOtherBank(String substantialOtherBank);	
	                                 
	public  String getIsHdfcDirecRltv();

	public  void setIsHdfcDirecRltv(String isHdfcDirecRltv);	                                 
	                                 
	public  String getHdfcDirecRltv();

	public  void setHdfcDirecRltv(String hdfcDirecRltv);
	
	public  String getIsObkDirecRltv();

	public  void setIsObkDirecRltv(String isObkDirecRltv);	
	                                 
	public  String getObkDirecRltv();

	public  void setObkDirecRltv(String obkDirecRltv);	                                 
	                                 
	public  String getIsPartnerDirecRltv();

	public  void setIsPartnerDirecRltv(String isPartnerDirecRltv);
	
	public  String getPartnerDirecRltv();

	public  void setPartnerDirecRltv(String partnerDirecRltv);	
             
	public  String getIsSubstantialRltvHdfcOther();

	public  void setIsSubstantialRltvHdfcOther(String isSubstantialRltvHdfcOther);	                                 
	                                 
	public  String getSubstantialRltvHdfcOther();

	public  void setSubstantialRltvHdfcOther(String substantialRltvHdfcOther);
	
	public  String getDirecHdfcOther();

	public  void setDirecHdfcOther(String direcHdfcOther);	
	
	public  String getIsBackedByGovt();

	public  void setIsBackedByGovt(String isBackedByGovt);
	
	/*public String getGroupExposer(); 

	public void setGroupExposer(String groupExposer); */
	
	public String getFirstYear() ;
	
	public void setFirstYear(String firstYear) ;

	public String getFirstYearTurnover();

	public void setFirstYearTurnover(String firstYearTurnover) ;

	public String getFirstYearTurnoverCurr();

	public void setFirstYearTurnoverCurr(String firstYearTurnoverCurr);
	
	public String getSecondYear() ;

	public void setSecondYear(String secondYear) ;

	public String getSecondYearTurnover() ;

	public void setSecondYearTurnover(String secondYearTurnover) ;

	public String getSecondYearTurnoverCurr() ;

	public void setSecondYearTurnoverCurr(String secondYearTurnoverCurr);

	public String getThirdYear() ;

	public void setThirdYear(String thirdYear) ;

	public String getThirdYearTurnover() ;

	public void setThirdYearTurnover(String thirdYearTurnover) ;

	public String getThirdYearTurnoverCurr() ;

	public void setThirdYearTurnoverCurr(String thirdYearTurnoverCurr);
	
	public String getCategoryOfFarmer() ;

	public void setCategoryOfFarmer(String categoryOfFarmer) ;
	
/*	public void setRbiIndustryCode(String rbiIndustryCode);*/
	
	public void setEntityType(String entityType);
	
	public String getEntityType() ;
	
	public String getIndirectCountryRiskExposure();

	public void setIndirectCountryRiskExposure(String indirectCountryRiskExposure) ;
	
	
	public String getIsSPVFunding() ;

	public void setIsSPVFunding(String isSPVFunding) ;
	
	

	
	public String getSalesPercentage();

	public void setSalesPercentage(String salesPercentage) ;
	
	public String getIsCGTMSE();

	public void setIsCGTMSE(String isCGTMSE) ;
	
	public String getIsIPRE();

	public void setIsIPRE(String isIPRE) ;
	
	
	public String getFinanceForAccquisition() ;

	public void setFinanceForAccquisition(String financeForAccquisition) ;
	
	public String getFacilityApproved();

	public void setFacilityApproved(String facilityApproved);
	
	
	public String getFacilityAmount() ;

	public void setFacilityAmount(String facilityAmount) ;
	
	public String getSecurityName() ;

	public void setSecurityName(String securityName) ;
	
	
	public String getSecurityType();

	public void setSecurityType(String securityType) ;
	
	
	public String getSecurityValue() ;

	public void setSecurityValue(String securityValue) ;
	
	
	public String getCompany();

	public void setCompany(String company) ;
	
	
	
	public String getNameOfHoldingCompany();

	public void setNameOfHoldingCompany(String nameOfHoldingCompany);
	
	
	
	public String getType() ;

	public void setType(String type);
	
	
	
	public String getIfBreachWithPrescriptions() ;

	public void setIfBreachWithPrescriptions(String ifBreachWithPrescriptions) ;
	
	public String getCompanyType();

	public void setCompanyType(String CompanyType) ;
	
	public String getComments() ;

	public void setComments(String comments);
	
	
	public String getLandHolding() ;

	public void setLandHolding(String landHolding) ;
	
	
	public String getCountryOfGuarantor() ;

	public void setCountryOfGuarantor(String countryOfGuarantor) ;
	
	public String getIsAffordableHousingFinance() ;

	public void setIsAffordableHousingFinance(String isAffordableHousingFinance) ;
	
	

	public String getIsInRestrictedList() ;

	public void setIsInRestrictedList(String isInRestrictedList) ;
	
	
	public String getRestrictedFinancing();

	public void setRestrictedFinancing(String restrictedFinancing) ;
	
	
	public String getRestrictedIndustries();

	public void setRestrictedIndustries(String restrictedIndustries);
	
	public String getIsQualifyingNotesPresent();

	public void setIsQualifyingNotesPresent(String isQualifyingNotesPresent);
	
	
	public String getStateImplications();

	public void setStateImplications(String stateImplications);
	
	
	public String getIsBorrowerInRejectDatabase() ;

	public void setIsBorrowerInRejectDatabase(String isBorrowerInRejectDatabase) ;
	
	public String getRejectHistoryReason();

	public void setRejectHistoryReason(String rejectHistoryReason);
	
	
	public String getCapitalForCommodityAndExchange() ;

	public void setCapitalForCommodityAndExchange(
			String capitalForCommodityAndExchange) ;
	
	/*public String getOdfdCategory() ;

	public void setOdfdCategory(String odfdCategory) ;*/
	
	
	
	public String getObjectFinance() ;

	public void setObjectFinance(String objectFinance) ;
	
	
	
	public String getIsCommodityFinanceCustomer() ;

	public void setIsCommodityFinanceCustomer(String isCommodityFinanceCustomer) ;
	
	
	
	public String getRestructuedBorrowerOrFacility() ;

	public void setRestructuedBorrowerOrFacility(
			String restructuedBorrowerOrFacility) ;
	
	
	
	public String getFacility();

	public void setFacility(String facility) ;
	
	
	public String getLimitAmount() ;

	public void setLimitAmount(String limitAmount) ;
	
	
	
	public String getConductOfAccountWithOtherBanks() ;

	public void setConductOfAccountWithOtherBanks(
			String conductOfAccountWithOtherBanks) ;
	
	
	
	
	
	public String getCrilicStatus() ;

	public void setCrilicStatus(String crilicStatus) ;
	
	
	public String getComment() ;

	public void setComment(String comment) ;
	
	
	public String getSubsidyFlag() ;

	public void setSubsidyFlag(String subsidyFlag) ;
	
	
	
	public String getHoldingCompnay() ;

	public void setHoldingCompnay(String holdingCompnay) ;
	
	
	public String getCautionList() ;

	public void setCautionList(String cautionList) ;
	
	
	
	public String getDateOfCautionList() ;

	public void setDateOfCautionList(String dateOfCautionList);
	
	
	
	public String getDirectors();

	public void setDirectors(String directors) ;
	
	
	
	public String getGroupCompanies();

	public void setGroupCompanies(String groupCompanies) ;
	
	
	public String getDefaultersList() ;

	public void setDefaultersList(String defaultersList) ;
	
	
	

	public String getRbiCompany() ;

	public void setRbiCompany(String rbiCompany) ;

	public String getRbiDirectors() ;

	public void setRbiDirectors(String rbiDirectors) ;

	public String getRbiGroupCompanies() ;

	public void setRbiGroupCompanies(String rbiGroupCompanies) ;
	
	
	public String getRbiDateOfCautionList() ;

	public void setRbiDateOfCautionList(String rbiDateOfCautionList) ;
	
	
	
	public String getCommericialRealEstate() ;

	public void setCommericialRealEstate(String commericialRealEstate);
	
	
	public String getCommericialRealEstateValue() ;

	public void setCommericialRealEstateValue(String commericialRealEstateValue) ;
	
	
	public String getCommericialRealEstateResidentialHousing() ;

	public void setCommericialRealEstateResidentialHousing(
			String commericialRealEstateResidentialHousing) ;

	public String getResidentialRealEstate();

	public void setResidentialRealEstate(String residentialRealEstate) ;

	public String getIndirectRealEstate() ;

	public void setIndirectRealEstate(String indirectRealEstate) ;
	
	public String getRestrictedListIndustries() ;

	public void setRestrictedListIndustries(String restrictedListIndustries);
	
	
	public String getCriCountryName();

	public void setCriCountryName(String countryName) ;
	
	public String getCustomerFyClouser();
	
	public void setCustomerFyClouser(String customerFyClouser);
	
	public String getIsBrokerTypeShare();
	public void setIsBrokerTypeShare(String isBrokerTypeShare);

	public String getIsBrokerTypeCommodity();
	public void setIsBrokerTypeCommodity(String isBrokerTypeCommodity);
}