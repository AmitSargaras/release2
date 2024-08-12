/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBContactBean.java,v 1.8 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for Contact Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBCriInfoBean implements EntityBean, ICriInfo {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CONTACT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getCriInfoID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCriInfoBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getCriInfoID() {
		if (null != getCriInfoPK()) {
			return getCriInfoPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	public long getLEID() {
		if (null != getCmsLeMainProfileIdFK()) {
			return getCmsLeMainProfileIdFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setLEID(long value) {
		setCmsLeMainProfileIdFK(new Long(value));
	}
	
	public abstract Long getCmsLeMainProfileIdFK();
	
	public abstract void setCmsLeMainProfileIdFK(Long value);
	/**
	 * Get Legal ID
	 * 
	 * @return long
	 */
	

	/**
	 * Set the contact ID
	 * 
	 * @param value is of type long
	 */
	public void setCriInfoID(long value) {
		setCriInfoPK(new Long(value));
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	
	// ************** Abstract methods ************
	/**
	 * Get the contact PK
	 * 
	 * @return Long
	 */
	public abstract Long getCriInfoPK();

	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	

	/**
	 * Set the contact PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setCriInfoPK(Long value);

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */


	// *****************************************************
	/**
	 * Create a Contact Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 * @return Long the contact ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICriInfo value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICriInfo is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getContactID() ==
			 * com.integrosys.cms.
			 * app.common.constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getContactID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); //to be set by cmr
			setCriInfoID(pk);

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 */
	public void ejbPostCreate(ICriInfo value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public ICriInfo getValue() {
		OBCriInfo value = new OBCriInfo();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(ICriInfo value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	public abstract  String getCustomerRamID();

	public abstract  void setCustomerRamID(String customerRamID);
	
	public abstract  String getCustomerAprCode();

	public abstract  void setCustomerAprCode(String customerAprCode);

	public abstract  String getCustomerExtRating();

	public abstract  void setCustomerExtRating(String customerExtRating);	

	public abstract  String getIsNbfs();

	public abstract  void setIsNbfs(String isNbfs);
	            
	public abstract  String getNbfsA();

	public abstract  void setNbfsA(String nbfsA);
	
	public abstract  String getNbfsB();

	public abstract  void setNbfsB(String nbfsB);
	
	public abstract  String getIsPrioritySector();

	public abstract  void setIsPrioritySector(String isPrioritySector);
	
	public abstract  String getMsmeClassification();

	public abstract  void setMsmeClassification(String msmeClassification);
	                
	public abstract  String getIsPermenentSsiCert();

	public abstract  void setIsPermenentSsiCert(String isPermenentSsiCert);
	            
	public abstract  String getIsWeakerSection();

	public abstract  void setIsWeakerSection(String isWeakerSection);
	
	public abstract  String getWeakerSection();

	public abstract  void setWeakerSection(String weakerSection);
	
	public abstract  String getGovtSchemeType();

	public abstract  void setGovtSchemeType(String govtSchemeType);
	
	public abstract  String getIsKisanCreditCard();

	public abstract  void setIsKisanCreditCard(String isKisanCreditCard);
	
	public abstract  String getIsMinorityCommunity();

	public abstract  void setIsMinorityCommunity(String isMinorityCommunity);	                           
	                            
	public abstract  String getMinorityCommunity();

	public abstract  void setMinorityCommunity(String minorityCommunity);
	                
	public abstract  String getIsCapitalMarketExpos();

	public abstract  void setIsCapitalMarketExpos(String isCapitalMarketExpos);
	            
	public abstract  String getIsRealEstateExpos();

	public abstract  void setIsRealEstateExpos(String isRealEstateExpos);
	
	public abstract  String getRealEstateExposType();

	public abstract  void setRealEstateExposType(String realEstateExposType);
	
	public abstract  String getRealEstateExposComm();

	public abstract  void setRealEstateExposComm(String realEstateExposComm);
	
	public abstract  String getIsCommoditiesExposer();

	public abstract  void setIsCommoditiesExposer(String isCommoditiesExposer);	  				         
	                 
	public abstract  String getIsSensitive();

	public abstract  void setIsSensitive(String isSensitive);
	
	public abstract  String getCommoditiesName();

	public abstract  void setCommoditiesName(String commoditiesName);
	
	public abstract  String getGrossInvsInPM();

	public abstract  void setGrossInvsInPM(String grossInvsInPM);		                                 
	                      
	public abstract  String getGrossInvsInEquip();

	public abstract  void setGrossInvsInEquip(String grossInvsInEquip);	 
	
	public abstract  String getPsu();

	public abstract  void setPsu(String psu);
	
	public abstract  String getPsuPercentage();

	public abstract  void setPsuPercentage(String psuPercentage);	                                 
	                                 
	public abstract  String getGovtUnderTaking();

	public abstract  void setGovtUnderTaking(String govtUnderTaking);
	
	public abstract  String getIsProjectFinance();

	public abstract  void setIsProjectFinance(String isProjectFinance);	                                    
	                                 
	public abstract  String getIsInfrastructureFinanace();

	public abstract  void setIsInfrastructureFinanace(String isInfrastructureFinanace);	                                 
	                                 
	public abstract  String getInfrastructureFinanaceType();

	public abstract  void setInfrastructureFinanaceType(String infrastructureFinanaceType);
	
	public abstract  String getIsSemsGuideApplicable();

	public abstract  void setIsSemsGuideApplicable(String isSemsGuideApplicable);		                                 
	                                 
	public abstract  String getIsFailIfcExcluList();

	public abstract  void setIsFailIfcExcluList(String isFailIfcExcluList);
	
	public abstract  String getIsTufs();

	public abstract  void setIsTufs(String isTufs);	                                    
	                                 
	public abstract  String getIsRbiDefaulter();

	public abstract  void setIsRbiDefaulter(String isRbiDefaulter);	                                 
	                                 
	public abstract  String getRbiDefaulter();

	public abstract  void setRbiDefaulter(String rbiDefaulter);
	
	public abstract  String getIsLitigation();

	public abstract  void setIsLitigation(String isLitigation);		                                 
	                                 
	public abstract  String getLitigationBy();

	public abstract  void setLitigationBy(String litigationBy);	                                 
	                                 
	public abstract  String getIsInterestOfBank();

	public abstract  void setIsInterestOfBank(String isInterestOfBank);
	
	public abstract  String getInterestOfBank();

	public abstract  void setInterestOfBank(String interestOfBank);		                                 
	               
	public abstract  String getIsAdverseRemark();

	public abstract  void setIsAdverseRemark(String isAdverseRemark);	                                 
	                                 
	public abstract  String getAdverseRemark();

	public abstract  void setAdverseRemark(String adverseRemark);
	
	public abstract  String getAuditType();

	public abstract  void setAuditType(String auditType);	                           
	                   
	public abstract  String getAvgAnnualTurnover();

	public abstract  void setAvgAnnualTurnover(String avgAnnualTurnover);	                                 
	    	
	public abstract  String getIndustryExposer();

	public abstract  void setIndustryExposer(String industryExposer);	                                 
	                                 
	public abstract  String getIsDirecOtherBank();

	public abstract  void setIsDirecOtherBank(String isDirecOtherBank);
	
	public abstract  String getDirecOtherBank();

	public abstract  void setDirecOtherBank(String direcOtherBank);	
	                                 
	public abstract  String getIsPartnerOtherBank();

	public abstract  void setIsPartnerOtherBank(String isPartnerOtherBank);	                                 
	                                 
	public abstract  String getPartnerOtherBank();

	public abstract  void setPartnerOtherBank(String partnerOtherBank);
	
	public abstract  String getIsSubstantialOtherBank();

	public abstract  void setIsSubstantialOtherBank(String isSubstantialOtherBank);		        
	
	public abstract  String getSubstantialOtherBank();

	public abstract  void setSubstantialOtherBank(String substantialOtherBank);	
	                                 
	public abstract  String getIsHdfcDirecRltv();

	public abstract  void setIsHdfcDirecRltv(String isHdfcDirecRltv);	                                 
	                                 
	public abstract  String getHdfcDirecRltv();

	public abstract  void setHdfcDirecRltv(String hdfcDirecRltv);
	
	public abstract  String getIsObkDirecRltv();

	public abstract  void setIsObkDirecRltv(String isObkDirecRltv);	
	                                 
	public abstract  String getObkDirecRltv();

	public abstract  void setObkDirecRltv(String obkDirecRltv);	                                 
	                                 
	public abstract  String getIsPartnerDirecRltv();

	public abstract  void setIsPartnerDirecRltv(String isPartnerDirecRltv);
	
	public abstract  String getPartnerDirecRltv();

	public abstract  void setPartnerDirecRltv(String partnerDirecRltv);	
             
	public abstract  String getIsSubstantialRltvHdfcOther();

	public abstract  void setIsSubstantialRltvHdfcOther(String isSubstantialRltvHdfcOther);	                                 
	                                 
	public abstract  String getSubstantialRltvHdfcOther();

	public abstract  void setSubstantialRltvHdfcOther(String substantialRltvHdfcOther);
	
	public abstract  String getDirecHdfcOther();

	public abstract  void setDirecHdfcOther(String direcHdfcOther);	
	
	public abstract  String getIsBackedByGovt();

	public abstract  void setIsBackedByGovt(String isBackedByGovt);
	
	public abstract String getFirstYear() ;
	
	public abstract   void setFirstYear(String firstYear) ;

	public abstract   String getFirstYearTurnover();

	public abstract   void setFirstYearTurnover(String firstYearTurnover) ;

	public abstract   String getFirstYearTurnoverCurr();

	public abstract   void setFirstYearTurnoverCurr(String firstYearTurnoverCurr);
	
	public abstract   String getSecondYear() ;

	public abstract   void setSecondYear(String secondYear) ;

	public abstract   String getSecondYearTurnover() ;

	public abstract   void setSecondYearTurnover(String secondYearTurnover) ;

	public abstract   String getSecondYearTurnoverCurr() ;

	public abstract   void setSecondYearTurnoverCurr(String secondYearTurnoverCurr);

	public abstract   String getThirdYear() ;

	public abstract   void setThirdYear(String thirdYear) ;

	public abstract   String getThirdYearTurnover() ;

	public abstract   void setThirdYearTurnover(String thirdYearTurnover) ;

	public abstract   String getThirdYearTurnoverCurr() ;

	public abstract   void setThirdYearTurnoverCurr(String thirdYearTurnoverCurr);
	
	public abstract String getCategoryOfFarmer() ;

	public abstract void setCategoryOfFarmer(String categoryOfFarmer) ;
	
	public abstract void setEntityType(String entityType);
	
	public abstract String getEntityType();
	
	
	public abstract String getIndirectCountryRiskExposure();

	public abstract void setIndirectCountryRiskExposure(String indirectCountryRiskExposure) ;
	
	
	public abstract String getIsSPVFunding() ;

	public abstract void setIsSPVFunding(String isSPVFunding) ;
	
	

	
	
	
	public abstract String getSalesPercentage();

	public abstract void setSalesPercentage(String salesPercentage) ;
	
	
	public abstract String getIsCGTMSE();

	public abstract void setIsCGTMSE(String isCGTMSE) ;
	
	public abstract String getIsIPRE();

	public abstract void setIsIPRE(String isIPRE) ;
	
	public abstract String getFinanceForAccquisition() ;

	public abstract void setFinanceForAccquisition(String financeForAccquisition) ;
	
	
	public abstract String getFacilityApproved();

	public abstract void setFacilityApproved(String facilityApproved);
	
	public abstract String getFacilityAmount() ;

	public abstract void setFacilityAmount(String facilityAmount) ;
	
	public abstract String getSecurityName() ;

	public abstract void setSecurityName(String securityName) ;
	
	
	public abstract String getSecurityType();

	public  abstract void setSecurityType(String securityType) ;
	
	
	public abstract String getSecurityValue() ;

	public abstract void setSecurityValue(String securityValue) ;
	
	
	public abstract String getCompany();

	public abstract void setCompany(String company) ;
	
	
	
	public abstract String getNameOfHoldingCompany();

	public abstract void setNameOfHoldingCompany(String nameOfHoldingCompany);
	
	
	public abstract String getType() ;

	public abstract void setType(String type);
	
	
	public abstract String getCompanyType() ;

	public abstract void setCompanyType(String CompanyType) ;
	
	public abstract String getIfBreachWithPrescriptions() ;

	public abstract void setIfBreachWithPrescriptions(String ifBreachWithPrescriptions) ;
	
	
	public abstract String getComments() ;

	public abstract void setComments(String comments);
	
	public abstract String getLandHolding() ;

	public abstract void setLandHolding(String landHolding) ;
	
	
	
	
	public abstract String getCountryOfGuarantor() ;

	public abstract void setCountryOfGuarantor(String countryOfGuarantor) ;
	
	public abstract String getIsAffordableHousingFinance() ;

	public  abstract void setIsAffordableHousingFinance(String isAffordableHousingFinance) ;
	
	public abstract String getIsInRestrictedList() ;

	public abstract void setIsInRestrictedList(String isInRestrictedList) ;
	
	
	public abstract String getRestrictedFinancing();

	public abstract void setRestrictedFinancing(String restrictedFinancing) ;
	
	
	
	public abstract String getRestrictedIndustries();

	public abstract void setRestrictedIndustries(String restrictedIndustries);
	
	public abstract String getIsQualifyingNotesPresent();

	public abstract void setIsQualifyingNotesPresent(String isQualifyingNotesPresent);
	
	public abstract String getStateImplications();

	public abstract void setStateImplications(String stateImplications);
	
	public abstract String getIsBorrowerInRejectDatabase() ;

	public abstract void setIsBorrowerInRejectDatabase(String isBorrowerInRejectDatabase) ;
	
	
	public abstract String getRejectHistoryReason();

	public abstract void setRejectHistoryReason(String rejectHistoryReason);
	
	
	public abstract String getCapitalForCommodityAndExchange() ;

	public abstract void setCapitalForCommodityAndExchange(
			String capitalForCommodityAndExchange) ;
	
	/*public abstract String getOdfdCategory() ;

	public abstract void setOdfdCategory(String odfdCategory) ;*/
	
	
	
	public abstract String getObjectFinance() ;

	public abstract void setObjectFinance(String objectFinance) ;
	
	
	
	public abstract String getIsCommodityFinanceCustomer() ;

	public  abstract void setIsCommodityFinanceCustomer(String isCommodityFinanceCustomer) ;
	
	
	public  abstract String getRestructuedBorrowerOrFacility() ;

	public  abstract void setRestructuedBorrowerOrFacility(
			String restructuedBorrowerOrFacility) ;
	
	
	
	public  abstract String getFacility();

	public abstract void setFacility(String facility) ;
	
	
	public abstract String getLimitAmount() ;

	public  abstract void setLimitAmount(String limitAmount) ;
	
	
	public abstract String getConductOfAccountWithOtherBanks() ;

	public abstract void setConductOfAccountWithOtherBanks(
			String conductOfAccountWithOtherBanks) ;
	
	
	public  abstract String getCrilicStatus() ;

	public  abstract void setCrilicStatus(String crilicStatus) ;
	
	
	
	public abstract String getComment() ;

	public abstract void setComment(String comment) ;
	
	public abstract String getSubsidyFlag() ;

	public abstract void setSubsidyFlag(String subsidyFlag) ;
	
	
	public abstract String getHoldingCompnay() ;

	public abstract void setHoldingCompnay(String holdingCompnay) ;
	
	
	public abstract String getCautionList() ;

	public abstract void setCautionList(String cautionList) ;
	
	
	public abstract String getDateOfCautionList() ;

	public abstract void setDateOfCautionList(String dateOfCautionList);
	
	
	public abstract String getDirectors();

	public abstract void setDirectors(String directors) ;
	
	
	public abstract String getGroupCompanies();

	public  abstract void setGroupCompanies(String groupCompanies) ;
	
	
	public abstract String getDefaultersList() ;

	public abstract void setDefaultersList(String defaultersList) ;
	
	
	public abstract  String getRbiCompany() ;

	public abstract void setRbiCompany(String rbiCompany) ;

	public abstract String getRbiDirectors() ;

	public abstract void setRbiDirectors(String rbiDirectors) ;

	public abstract String getRbiGroupCompanies() ;

	public abstract void setRbiGroupCompanies(String rbiGroupCompanies) ;
	
	
	public abstract String getRbiDateOfCautionList() ;

	public abstract void setRbiDateOfCautionList(String rbiDateOfCautionList) ;
	
	
	public abstract String getCommericialRealEstate() ;

	public abstract void setCommericialRealEstate(String commericialRealEstate);
	
	
	
	public abstract String getCommericialRealEstateValue() ;

	public abstract void setCommericialRealEstateValue(String commericialRealEstateValue) ;
	
	
	
	public abstract String getCommericialRealEstateResidentialHousing() ;

	public abstract void setCommericialRealEstateResidentialHousing(
			String commericialRealEstateResidentialHousing) ;

	public abstract String getResidentialRealEstate();

	public abstract
	void setResidentialRealEstate(String residentialRealEstate) ;

	public abstract String getIndirectRealEstate() ;

	public abstract void setIndirectRealEstate(String indirectRealEstate) ;
	
	public abstract String getRestrictedListIndustries() ;

	public abstract void setRestrictedListIndustries(String restrictedListIndustries) ;
	

	public abstract String getCriCountryName() ;

	public abstract void setCriCountryName(String criCountryName);
	
	public abstract String getIsBrokerTypeShare();
	public abstract void setIsBrokerTypeShare(String isBrokerTypeShare);

	public abstract String getIsBrokerTypeCommodity();
	public abstract void setIsBrokerTypeCommodity(String isBrokerTypeCommodity);
	
    public abstract String getCustomerFyClouser();
	
	public abstract void setCustomerFyClouser(String customerFyClouser);
	
	/*public abstract  String getGroupExposer(); 

	public abstract  void setGroupExposer(String groupExposer); */

}