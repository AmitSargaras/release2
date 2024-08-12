/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/property/IPropertyCollateral.java,v 1.11 2006/02/22 00:55:56 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.property;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * This interface represents a Collateral of type Property
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/02/22 00:55:56 $ Tag: $Name: $
 */
public interface IPropertyCollateral extends ICollateral {
	/**
	 * Get security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @return String
	 */
	public String getEnvRiskyStatus();

	/**
	 * Set security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @param envRiskyStatus is of type String
	 */
	public void setEnvRiskyStatus(String envRiskyStatus);

	/**
	 * Get date the collateral confirmed as environmentally risky.
	 * 
	 * @return Date
	 */
	public Date getEnvRiskyDate();

	/**
	 * Set date the collateral confirmed as environmentally risky.
	 * 
	 * @param envRiskyDate is of type Date
	 */
	public void setEnvRiskyDate(Date envRiskyDate);

	/**
	 * Get remarks for Security Environmentally Risky.
	 * 
	 * @return String
	 */
	public String getEnvRiskyRemarks();

	/**
	 * Set remarks for Security Environmentally Risky.
	 * 
	 * @param envRiskyRemarks is of type String
	 */
	public void setEnvRiskyRemarks(String envRiskyRemarks);

	/**
	 * Get physical inspection frequency.
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq();

	/**
	 * Set physical inspection frequency.
	 * 
	 * @param physicalInspectionFreq is of type int
	 */
	public void setPhysicalInspectionFreq(int physicalInspectionFreq);

	/**
	 * Get physical inspection frequency unit.
	 * 
	 * @return String
	 */
	public String getPhysicalInspectionFreqUnit();

	/**
	 * Set physical inspection frequency unit.
	 * 
	 * @param physicalInspectionFreqUnit of type String
	 */
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	/**
	 * Get title type.
	 * 
	 * @return String
	 */
	public String getTitleType();

	/**
	 * Set title type.
	 * 
	 * @param titleType of type String
	 */
	public void setTitleType(String titleType);

	/**
	 * Get title number.
	 * 
	 * @return String
	 */
	public String getTitleNumber();

	/**
	 * Set title number.
	 * 
	 * @param titleNumber of type String
	 */
	public void setTitleNumber(String titleNumber);

	public String getMasterTitle();
	
	public void setMasterTitle(String masterTitle);
	
	/**
	 * Get title number.
	 * 
	 * @return String
	 */
	public String getMasterTitleNumber();

	/**
	 * Set title number.
	 * 
	 * @param titleNumber of type String
	 */
	public void setMasterTitleNumber(String titleNumber);

	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection();

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection);

	/**
	 * Get last physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate();

	/**
	 * Set last physical inspection date.
	 * 
	 * @param lastPhysicalInspectDate of type Date
	 */
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	/**
	 * Get next physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate();

	/**
	 * Set next physical inspection date.
	 * 
	 * @param nextPhysicalInspectDate of type Date
	 */
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

	/**
	 * Get lot number.
	 * 
	 * @return String
	 */
	public String getLotNo();

	/**
	 * Set lot number.
	 * 
	 * @param lotNo of type String
	 */
	public void setLotNo(String lotNo);

	/**
	 * Get town/state.
	 * 
	 * @return String
	 */
	public String getTown();

	/**
	 * Set town/state.
	 * 
	 * @param town of type String
	 */
	public void setTown(String town);

	/**
	 * Get lot location.
	 * 
	 * @return String
	 */
	public String getLotLocation();

	/**
	 * Set lot location.
	 * 
	 * @param lotLocation of type String
	 */
	public void setLotLocation(String lotLocation);

	/**
	 * Get postcode.
	 * 
	 * @return String
	 */
	public String getPostcode();

	/**
	 * Set postcode.
	 * 
	 * @param postcode of type String
	 */
	public void setPostcode(String postcode);

	/**
	 * Get land area.
	 * 
	 * @return double
	 */
	public double getLandArea();

	/**
	 * Set land area.
	 * 
	 * @param landArea of type double
	 */
	public void setLandArea(double landArea);

	/**
	 * Get unit of measurement of land area.
	 * 
	 * @return String
	 */
	public String getLandAreaUOM();

	/**
	 * Set unit of measurement of land area.
	 * 
	 * @param landAreaUOM of type String
	 */
	public void setLandAreaUOM(String landAreaUOM);

	/**
	 * Get tenure.
	 * 
	 * @return int
	 */
	public int getTenure();

	/**
	 * Set tenure.
	 * 
	 * @param tenure of type int
	 */
	public void setTenure(int tenure);

	/**
	 * Get tenure unit.
	 * 
	 * @return String
	 */
	public String getTenureUnit();

	/**
	 * Set tenure unit.
	 * 
	 * @param tenureUnit of type String
	 */
	public void setTenureUnit(String tenureUnit);

	/**
	 * Get remaining tenure period.
	 * 
	 * @return int
	 */
	public int getRemainTenurePeriod();

	/**
	 * Set remaining tenure period.
	 * 
	 * @param remainTenurePeriod of type int
	 */
	public void setRemainTenurePeriod(int remainTenurePeriod);

	/**
	 * Get unit for remaining tenure period.
	 * 
	 * @return String
	 */
	public String getRemainTenureUnit();

	/**
	 * Set unit for remaining tenure period.
	 * 
	 * @param remainTenureUnit of type String
	 */
	public void setRemainTenureUnit(String remainTenureUnit);

	/**
	 * Get restriction/expressed condition.
	 * 
	 * @return String
	 */
	public String getRestrictionCondition();

	/**
	 * Set restriction/expressed condition.
	 * 
	 * @param restrictionCondition of type String
	 */
	public void setRestrictionCondition(String restrictionCondition);

	/**
	 * Get quit rent amount paid.
	 * 
	 * @return Amount
	 */
	public Amount getQuitRentPaid();

	/**
	 * Set quit rent amount paid.
	 * 
	 * @param quitRentPaid of type Amount
	 */
	public void setQuitRentPaid(Amount quitRentPaid);

	/**
	 * Get description of property.
	 * 
	 * @return String
	 */
	public String getDescription();

	/**
	 * Set description of property.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description);

	// Start CR CMS-574 add new field remarks
	/**
	 * Get remarks of property.
	 * 
	 * @return String
	 */
	public String getRemarksProperty();

	/**
	 * Set remarks of property.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarksProperty(String remarksProperty);

	// End CR CMS-574 add new field remarks

	/**
	 * Get sale and purchase agreement value.
	 * 
	 * @return Amount
	 */
	public Amount getSalePurchaseValue();

	/**
	 * Set sale and purchase agreement value.
	 * 
	 * @param salePurchaseValue of type Amount
	 */
	public void setSalePurchaseValue(Amount salePurchaseValue);

	
	public Date getSalePurchaseDate();
	
	public void setSalePurchaseDate(Date salePurchaseDate);
	
	/**
	 * Get nominal value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue();

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue);

	public String getStdQuitRent();

	public void setStdQuitRent(String stdQuitRent);

	public String getNonStdQuitRent();

	public void setNonStdQuitRent(String nonStdQuitRent);

	public String getQuitRentReceipt();

	public void setQuitRentReceipt(String quitRentReceipt);

	public String getRegistedHolder();

	public void setRegistedHolder(String registedHolder);

	public String getPropertyAddress();

	public void setPropertyAddress(String propertyAddress);

	public String getPropertyAddress2();
	
	public void setPropertyAddress2(String address);

	public String getPropertyAddress3();
	
	public void setPropertyAddress3(String address);

	public String getCombinedValueIndicator();

	public void setCombinedValueIndicator(String indicator);

	// this is a NOT VALUE !!!
	public long getValueNumber();

	// this is a NOT VALUE !!!
	public void setValueNumber(long valueNo);

	public double getCombinedValueAmount();

	public void setCombinedValueAmount(double combinedValue);

	public String getUnitParcelNo();

	public void setUnitParcelNo(String unitParcelNo);

	public char getAbandonedProject();

	public void setAbandonedProject(char abandonedProject);

	public char getBreachInd();

	public void setBreachInd(char breachInd);

	public String getCategoryOfLandUse();

	public void setCategoryOfLandUse(String categoryOfLandUse);

	public String getDeveloperName();

	public void setDeveloperName(String developerName);

	public String getExpressedCondition();

	public void setExpressedCondition(String expressedCondition);

	public char getIndependentValuerFlag();

	public void setIndependentValuerFlag(char independentValuerFlag);

	public String getLocationDistrict();

	public void setLocationDistrict(String locationDistrict);

	public String getLocationMukim();

	public void setLocationMukim(String locationMukim);

	public String getLocationState();

	public void setLocationState(String locationState);

	public String getLongEstablishedMarketFlag();

	public void setLongEstablishedMarketFlag(String longEstablishedMarketFlag);

	public char getMethodologyForValuationFlag();

	public void setMethodologyForValuationFlag(char methodologyForValuationFlag);

	public String getPhaseNo();

	public void setPhaseNo(String phaseNo);

	public String getProjectName();

	public void setProjectName(String projectName);

	public char getPropertyCompletedFlag();

	public void setPropertyCompletedFlag(char propertyCompletedFlag);

	public char getPropertyCompletionStatus();

	public void setPropertyCompletionStatus(char propertyCompletionStatus);
	
	public char getPropertyCompletionStage();
	
	public void setPropertyCompletionStage(char propertyCompletionStage);
	
	public boolean getAssumption();
	
	public void setAssumption(boolean assumption);
	
	public Amount getAmountRedeem();
	
	public void setAmountRedeem(Amount amountRedeem);
	
	public Amount getUnitPrice();
	
	public void setUnitPrice(Amount unitPrice);
	
	public String getPropertyType();

	public void setPropertyType(String propertyType);

	public String getPropertyUsage();

	public void setPropertyUsage(String propertyUsage);

	public char getPropertyWellDevelopedFlag();

	public void setPropertyWellDevelopedFlag(char propertyWellDevelopedFlag);

	public char getRealEstateRentalFlag();

	public void setRealEstateRentalFlag(char realEstateRentalFlag);

	public String getRealEstateUsage();

	public void setRealEstateUsage(String realEstateUsage);

	public char getReducedRiskWeightFlag();

	public void setReducedRiskWeightFlag(char reducedRiskWeightFlag);

	public double getBuiltupArea();

	public void setBuiltupArea(double builtupArea);

	public String getBuiltupAreaUOM();

	public void setBuiltupAreaUOM(String builtupAreaUOM);

	public String getScheduledLocation();

	public void setScheduledLocation(String scheduledLocation);

	public String getDevGrpCo();

	public void setDevGrpCo(String devGrpCo);

	// Added by Grace;ABank CLIMS project;13/8/2007;
	public String getAssessment();

	public void setAssessment(String assessment);

	public String getTaman();

	public void setTaman(String taman);

	public String getTooltipsLand();

	public void setTooltipsLand(String tooltipsLand);

	public String getTooltipsBuiltArea();

	public void setTooltipsBuiltArea(String tooltipsBuiltArea);

	/**
	 * Get the computed MF Score from input MF Checklist.
	 * 
	 * @return Double, the computed MF Score or null if not applicable to this
	 *         property
	 */
	public Double getComputedMFScore();

	/**
	 *Set the computed MF Score from input MF Checklist.
	 * 
	 * @param value of type Double
	 */
	public void setComputedMFScore(Double value);

	public Double getUserInput();

	public void setUserInput(Double userInput);

	public Date getChattelSoldDate();
	
	public void setChattelSoldDate(Date chattelSoldDate);
	
	public Date getNextQuitRentDate();
	
	public void setNextQuitRentDate(Date nextQuitRentDate);
	
	public Date getQuitRentPaymentDate();
	
	public void setQuitRentPaymentDate(Date quitRentPaymentDate);
	
	public Amount getAssessmentRate();
	
	public void setAssessmentRate(Amount assessmentRate);
	
	public int getAssessmentPeriod();
	
	public void setAssessmentPeriod(int assessmentPeriod);
	
	public Date getAssessmentPaymentDate();
	
	public void setAssessmentPaymentDate(Date assessmentPaymentDate);
	
	public Amount getAuctionPrice();
	
	public void setAuctionPrice(Amount auctionPrice);
	
	public Date getAuctionDate();
	
	public void setAuctionDate(Date auctionDate);
	
	public Date getPriCaveatGuaranteeDate();
	
	public void setPriCaveatGuaranteeDate(Date priDate);
	
	public String getNonPreferredLocation();
	
	public void setNonPreferredLocation(String nonPreferredLocation);
	
	public String getCommissionType();
	
	public void setCommissionType(String commissionType);
	
	public String getAssessmentOption();
	
	public void setAssessmentOption(String assessmentOption);
	
	public String getTitleNumberPrefix();

	public void setTitleNumberPrefix(String titleNumberPrefix);
	
	public String getLotNumberPrefix();

	public void setLotNumberPrefix(String lotNumberPrefix);

    /**
     * Get Auctioneer for property auction
     * @return
     */
    public String getAuctioneer();

    /**
     * Set Auctioneer for property auction
     * @return
     */
    public void setAuctioneer(String auctioneer);
    
	public String getSectionNo();

	public void setSectionNo(String sectionNo);
	
	public String getStoreyNumber();

	public void setStoreyNumber(String storeyNumber);
	
//********** Added by Dattatray Thorat for Property - Commercial Security
	
	public  String getValuatorCompany();

	public  void setValuatorCompany(String valuatorCompany);
	
	public  String getMortageRegisteredRef();

	public  void setMortageRegisteredRef(String mortageRegisteredRef);
	
	public  String getValuatorName();

	public  void setValuatorName(String valuatorName);
	
	public  String getAdvocateLawyerName();

	public  void setAdvocateLawyerName(String advocateLawyerName);
	
	public  String getPropertyLotLocation();

	public  void setPropertyLotLocation(String propertyLotLocation);
	
	public  String getCountry();

	public  void setCountry(String country);
	
	public  String getRegion();

	public  void setRegion(String region);
	
	public  String getNearestState();

	public  void setNearestState(String State);
	
	public  String getNearestCity();

	public  void setNearestCity(String nearestCity);
	
	public  String getOtherCity();

	public  void setOtherCity(String otherCity);
	
	public String getPinCode();
	
	public void setPinCode(String pinCode);
	
	public Date getValuationDate();
	
	public void setValuationDate(Date valuationDate);
	
	public String getTypeOfMargage();
	
	public void setTypeOfMargage(String typeOfMargage);
	
	public String getMorgageCreatedBy();
	
	public void setMorgageCreatedBy(String morgageCreatedBy);
	
	public String getDocumentReceived();
	
	public void setDocumentReceived(String documentReceived);
	
	public String getDocumentBlock();
	
	public void setDocumentBlock(String documentBlock);
	
	public String getBinNumber();
	
	public void setBinNumber(String binNumber);
	
	public Amount getTotalPropertyAmount();
	
	public void setTotalPropertyAmount(Amount totalPropertyAmount);
	
	public String getPropertyAddress4();

	public void setPropertyAddress4(String propertyAddress4);
	
	public String getPropertyAddress5();

	public void setPropertyAddress5(String propertyAddress5);
	
	public String getPropertyAddress6();

	public void setPropertyAddress6(String propertyAddress6);
	
	public String getPropertyId() ;

	public void setPropertyId(String propertyId) ;
	
	//Added by Pramod Katkar for New Filed CR on 12-08-2013
	public String getClaim();
	public void setClaim(String claim);
	public String getClaimType() ;
	public void setClaimType(String claimType) ;
	
	//End by Pramod Katkar
	
	//CR : Tightening of property fields -start
	
	public double getInSqftBuiltUpArea();

	public void setInSqftBuiltUpArea(double inSqftBuiltUpArea);

	public double getInSqftLandArea();

	public void setInSqftLandArea(double inSqftLandArea);

	public Date getTsrDate();

	public void setTsrDate(Date tsrDate);

	public Date getNextTsrDate() ;

	public void setNextTsrDate(Date nextTsrDate);

	public String getTsrFrequency();

	public void setTsrFrequency(String tsrFrequency);
	
	public Date getCersiaRegistrationDate();
	
	public void setCersiaRegistrationDate(Date cersiaRegistrationDate);
	
	//	CR : Tightening of property fields - end
	
	//Start Santosh
	public double getLandValue();
	
	public void setLandValue(double landValue);
	
	public double getBuildingValue();
	
	public void setBuildingValue(double buildingValue);
	
	public double getReconstructionValueOfTheBuilding() ;
	
	public void setReconstructionValueOfTheBuilding(double reconstructionValueOfTheBuilding);
	//End Santosh
	
	public String getConstitution();

	public void setConstitution(String constitution);
	
	public Date getLegalAuditDate();
	public void setLegalAuditDate(Date legalAuditDate);
	public Date getInterveingPeriSearchDate();
	public void setInterveingPeriSearchDate(Date interveingPeriSearchDate);
	public Date getDateOfReceiptTitleDeed();
	public void setDateOfReceiptTitleDeed(Date dateOfReceiptTitleDeed);
	public String getWaiver();
	public void setWaiver(String waiver);
	public String getDeferral();
	public void setDeferral(String deferral);
	public String getDeferralId();
	public void setDeferralId(String deferralId);

	public Date getValuationDate_v1();
	public void setValuationDate_v1(Date valuationDate_v1);
	public Amount getTotalPropertyAmount_v1();
	public void setTotalPropertyAmount_v1(Amount totalPropertyAmount_v1);
	public String getValuatorCompany_v1();
	public void setValuatorCompany_v1(String valuatorCompany_v1);
	public String getCategoryOfLandUse_v1();
	public void setCategoryOfLandUse_v1(String categoryOfLandUse_v1);
	public String getDeveloperName_v1();
	public void setDeveloperName_v1(String developerName_v1);
	public String getCountry_v1();
	public void setCountry_v1(String country_v1);
	public String getRegion_v1();
	public void setRegion_v1(String region_v1);
	public String getLocationState_v1();
	public void setLocationState_v1(String locationState_v1);
	public String getNearestCity_v1();
	public void setNearestCity_v1(String nearestCity_v1);
	public String getPinCode_v1();
	public void setPinCode_v1(String pinCode_v1);
	public double getLandArea_v1();
	public void setLandArea_v1(double i);
	public String getLandAreaUOM_v1();
	public void setLandAreaUOM_v1(String landAreaUOM_v1);
	public double getInSqftLandArea_v1();
	public void setInSqftLandArea_v1(double inSqftLandArea_v1);
	public double getBuiltupArea_v1();
	public void setBuiltupArea_v1(double builtupArea_v1);
	public String getBuiltupAreaUOM_v1();
	public void setBuiltupAreaUOM_v1(String builtupAreaUOM_v1);
	public double getInSqftBuiltupArea_v1();
	public void setInSqftBuiltupArea_v1(double inSqftBuiltupArea_v1);
	public char getPropertyCompletionStatus_v1();
	public void setPropertyCompletionStatus_v1(char propertyCompletionStatus_v1);
	public double getLandValue_v1();
	public void setLandValue_v1(double landValue_v1);
	public double getBuildingValue_v1();
	public void setBuildingValue_v1(double buildingValue_v1);
	public double getReconstructionValueOfTheBuilding_v1();
	public void setReconstructionValueOfTheBuilding_v1(double reconstructionValueOfTheBuilding_v1);
	
	public boolean getIsPhysicalInspection_v1();
	public void setIsPhysicalInspection_v1(boolean isPhysicalInspection_v1);
	
	/*public int getPhysicalInspectionFreq_v1();
    public void setPhysicalInspectionFreq_v1(int physicalInspectionFreq_v1);
*/
	public  String getPhysicalInspectionFreqUnit_v1();
	public  void setPhysicalInspectionFreqUnit_v1(String physicalInspectionFreqUnit_v1);
	public  Date getLastPhysicalInspectDate_v1();
	public  void setLastPhysicalInspectDate_v1(Date lastPhysicalInspectDate_v1);
	public  Date getNextPhysicalInspectDate_v1();
	public  void setNextPhysicalInspectDate_v1(Date nextPhysicalInspectDate_v1);
	
	public String getRemarksProperty_v1();
	public void setRemarksProperty_v1(String remarksProperty_v1);
	public String getVal1_id();
	public void setVal1_id(String val1_id);
	public Date getValcreationdate_v1();
	public void setValcreationdate_v1(Date valcreationdate_v1);
	
	//for add button click
	public String getMortgageCreationAdd();
	public void setMortgageCreationAdd(String mortgageCreationAdd);
	public Date getPreviousMortCreationDate();
	public void setPreviousMortCreationDate(Date previousMortCreationDate);

	public String getPreValDate_v1();
	public void setPreValDate_v1(String preValDate_v1);
	
	//Valuation 3 start
	
	public Date getValuationDate_v3();
	public void setValuationDate_v3(Date valuationDate_v3);
	public Amount getTotalPropertyAmount_v3();
	public void setTotalPropertyAmount_v3(Amount totalPropertyAmount_v3);
	public String getValuatorCompany_v3();
	public void setValuatorCompany_v3(String valuatorCompany_v3);
	public String getCategoryOfLandUse_v3();
	public void setCategoryOfLandUse_v3(String categoryOfLandUse_v3);
	public String getDeveloperName_v3();
	public void setDeveloperName_v3(String developerName_v3);
	public String getCountry_v3();
	public void setCountry_v3(String country_v3);
	public String getRegion_v3();
	public void setRegion_v3(String region_v3);
	public String getLocationState_v3();
	public void setLocationState_v3(String locationState_v3);
	public String getNearestCity_v3();
	public void setNearestCity_v3(String nearestCity_v3);
	public String getPinCode_v3();
	public void setPinCode_v3(String pinCode_v3);
	public double getLandArea_v3();
	public void setLandArea_v3(double i);
	public String getLandAreaUOM_v3();
	public void setLandAreaUOM_v3(String landAreaUOM_v3);
	public double getInSqftLandArea_v3();
	public void setInSqftLandArea_v3(double inSqftLandArea_v3);
	public double getBuiltupArea_v3();
	public void setBuiltupArea_v3(double builtupArea_v3);
	public String getBuiltupAreaUOM_v3();
	public void setBuiltupAreaUOM_v3(String builtupAreaUOM_v3);
	public double getInSqftBuiltupArea_v3();
	public void setInSqftBuiltupArea_v3(double inSqftBuiltupArea_v3);
	public char getPropertyCompletionStatus_v3();
	public void setPropertyCompletionStatus_v3(char propertyCompletionStatus_v3);
	public double getLandValue_v3();
	public void setLandValue_v3(double landValue_v3);
	public double getBuildingValue_v3();
	public void setBuildingValue_v3(double buildingValue_v3);
	public double getReconstructionValueOfTheBuilding_v3();
	public void setReconstructionValueOfTheBuilding_v3(double reconstructionValueOfTheBuilding_v3);
	
	public boolean getIsPhysicalInspection_v3();
	public void setIsPhysicalInspection_v3(boolean isPhysicalInspection_v3);
	
	/*public int getPhysicalInspectionFreq_v3();
    public void setPhysicalInspectionFreq_v3(int physicalInspectionFreq_v3);
*/
	public  String getPhysicalInspectionFreqUnit_v3();
	public  void setPhysicalInspectionFreqUnit_v3(String physicalInspectionFreqUnit_v3);
	public  Date getLastPhysicalInspectDate_v3();
	public  void setLastPhysicalInspectDate_v3(Date lastPhysicalInspectDate_v3);
	public  Date getNextPhysicalInspectDate_v3();
	public  void setNextPhysicalInspectDate_v3(Date nextPhysicalInspectDate_v3);
	
	public String getRemarksProperty_v3();
	public void setRemarksProperty_v3(String remarksProperty_v3);
	public String getVal3_id();
	public void setVal3_id(String val3_id);
	public Date getValcreationdate_v3();
	public void setValcreationdate_v3(Date valcreationdate_v3);
	
	//for add button click
	/*public String getMortgageCreationAdd();
	public void setMortgageCreationAdd(String mortgageCreationAdd);
	public Date getPreviousMortCreationDate();
	public void setPreviousMortCreationDate(Date previousMortCreationDate);*/

	public String getPreValDate_v3();
	public void setPreValDate_v3(String preValDate_v3);
	
	// Valuation 3 ends
	
	public Date getValuationDate_v2();
	public void setValuationDate_v2(Date valuationDate_v2);
	public Amount getTotalPropertyAmount_v2();
	public void setTotalPropertyAmount_v2(Amount totalPropertyAmount_v2);
	public String getValuatorCompany_v2();
	public void setValuatorCompany_v2(String valuatorCompany_v2);
	public String getCategoryOfLandUse_v2();
	public void setCategoryOfLandUse_v2(String categoryOfLandUse_v2);
	public String getDeveloperName_v2();
	public void setDeveloperName_v2(String developerName_v2);
	public String getCountry_v2();
	public void setCountry_v2(String country_v2);
	public String getRegion_v2();
	public void setRegion_v2(String region_v2);
	public String getLocationState_v2();
	public void setLocationState_v2(String locationState_v2);
	public String getNearestCity_v2();
	public void setNearestCity_v2(String nearestCity_v2);
	public String getPinCode_v2();
	public void setPinCode_v2(String pinCode_v2);
	public double getLandArea_v2();
	public void setLandArea_v2(double i);
	public String getLandAreaUOM_v2();
	public void setLandAreaUOM_v2(String landAreaUOM_v2);
	public double getInSqftLandArea_v2();
	public void setInSqftLandArea_v2(double inSqftLandArea_v2);
	public double getBuiltupArea_v2();
	public void setBuiltupArea_v2(double builtupArea_v2);
	public String getBuiltupAreaUOM_v2();
	public void setBuiltupAreaUOM_v2(String builtupAreaUOM_v2);
	public double getInSqftBuiltupArea_v2();
	public void setInSqftBuiltupArea_v2(double inSqftBuiltupArea_v2);
	public char getPropertyCompletionStatus_v2();
	public void setPropertyCompletionStatus_v2(char propertyCompletionStatus_v2);
	public double getLandValue_v2();
	public void setLandValue_v2(double landValue_v2);
	public double getBuildingValue_v2();
	public void setBuildingValue_v2(double buildingValue_v2);
	public double getReconstructionValueOfTheBuilding_v2();
	public void setReconstructionValueOfTheBuilding_v2(double reconstructionValueOfTheBuilding_v2);
	
	public boolean getIsPhysicalInspection_v2();
	public void setIsPhysicalInspection_v2(boolean isPhysicalInspection_v2);
	
	/*public int getPhysicalInspectionFreq_v2();
    public void setPhysicalInspectionFreq_v2(int physicalInspectionFreq_v2);
*/
	public  String getPhysicalInspectionFreqUnit_v2();
	public  void setPhysicalInspectionFreqUnit_v2(String physicalInspectionFreqUnit_v2);
	public  Date getLastPhysicalInspectDate_v2();
	public  void setLastPhysicalInspectDate_v2(Date lastPhysicalInspectDate_v2);
	public  Date getNextPhysicalInspectDate_v2();
	public  void setNextPhysicalInspectDate_v2(Date nextPhysicalInspectDate_v2);
	
	public String getRemarksProperty_v2();
	public void setRemarksProperty_v2(String remarksProperty_v2);
	public String getVal2_id();
	public void setVal2_id(String val2_id);
	public Date getValcreationdate_v2();
	public void setValcreationdate_v2(Date valcreationdate_v2);
	
	public String getPreValDate_v2();
	public void setPreValDate_v2(String preValDate_v2);
	
	public String getVersion1();
	public void setVersion1(String version1);
	public String getVersion2();
	public void setVersion2(String version2);
	public String getVersion3() ;
	public void setVersion3(String version3);

	public abstract String getRevalOverride();
	public abstract void setRevalOverride(String revalOverride);
}
