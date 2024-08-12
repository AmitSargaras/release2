/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/property/OBPropertyCollateral.java,v 1.10 2006/02/22 00:55:56 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.property;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;

/**
 * This class represents a Collateral of type Property entity.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/02/22 00:55:56 $ Tag: $Name: $
 */
public class OBPropertyCollateral extends OBCollateral implements IPropertyCollateral {
	private String envRiskyStatus;

	private Date envRiskyDate;

	private String envRiskyRemarks;

	private int physicalInspectionFreq;

	private String physicalInspectionFreqUnit;

	private String titleType;
	
	private String titleNumber;
	
	private String titleNumberPrefix;
	
	private String masterTitle;

	private String masterTitleNumber;

	private boolean isPhysicalInspection;

	private Date lastPhysicalInspectDate;

	private Date nextPhysicalInspectDate;

	private String lotNo;
	
	private String lotNumberPrefix;

	private String town;

	private String lotLocation;

	private String postcode;

	private double landArea;

	private String landAreaUOM;

	private int remainTenurePeriod;

	private String remainTenureUnit;

	private String restrictionCondition;

	private Amount quitRentPaid;

	private String description;

	private Amount salePurchaseValue;
	
	private Date salePurchaseDate;

	private int tenure;

	private String tenureUnit;

	private Amount nominalValue;

	private String remarksProperty;

	private String stdQuitRent = "N";

	private String nonStdQuitRent = "N";

	private String quitRentReceipt = "N";

	private String registedHolder;

	private String propertyAddress;

	private String propertyAddress2;

	private String propertyAddress3;

	private String combinedValueIndicator;

	private long valueNumber;

	private double combinedValueAmount;

	private String expressedCondition;

	private String locationMukim;

	private String locationDistrict;

	private String locationState;

	private String realEstateUsage;

	private char realEstateRentalFlag;

	private String propertyUsage;

	private char propertyCompletionStatus;
	
	private char propertyCompletionStage;
	
	private boolean assumption;
	
	private Amount amountRedeem;
	
	private Amount unitPrice;

	private String developerName;

	private char abandonedProject;

	private char reducedRiskWeightFlag;

	private char propertyCompletedFlag;

	private char propertyWellDevelopedFlag;

	private String longEstablishedMarketFlag;

	private char methodologyForValuationFlag;

	private char independentValuerFlag;

	private String categoryOfLandUse;

	private String propertyType;

	// private Date presentationDate;

	// private Date expiryDate;

	// private String caveatWaivedInd;

	// private char dateLodged;

	private String phaseNo;

	private String projectName;

	// private String solicitorName;

	// private String refNo;

	// private String presentationNo;

	private char breachInd;

	private String unitParcelNo;

	private double builtupArea;

	private String builtupAreaUOM;

	private String scheduledLocation;

	private String devGrpCo;

	private String taman;

	private String assessment = "N";

	private String tooltipsLand;

	private String tooltipsBuiltArea;

	private Double computedMFScore;

	private Double userInput;
	
	private Date chattelSoldDate;
	
	private Date nextQuitRentDate;
	
	private Date quitRentPaymentDate;
	
	private Amount assessmentRate;

	private int assessmentPeriod;

	private Date assessmentPaymentDate;
	
	private String assessmentOption;

	private Amount auctionPrice;

	private Date auctionDate;

	private Date priCaveatGuaranteeDate;

	private String nonPreferredLocation;

	private String commissionType;

    private String auctioneer;
    
    private String sectionNo;
    
    private String storeyNumber;
    
    //****** Added By Dattatray Thorat for Property - Commercial Security
    
    private String valuatorCompany;
    
    private String valuatorName;
    
    private String mortageRegisteredRef;
    
    private String advocateLawyerName;
    
    private String propertyLotLocation;
    
    private String country;
    
    private String region;
    
    private String nearestState;
    
    private String nearestCity;
    
    private String otherCity;
    
    private String pinCode;
    
    private Date valuationDate;
    
    private String typeOfMargage;
    
    private String morgageCreatedBy;
    
    private String documentReceived;
    
    private String documentBlock;
    
    private String binNumber;
    
    private Amount totalPropertyAmount;
    
    private String propertyAddress4;
    
    private String propertyAddress5;
    
    private String propertyAddress6;
    
    private String propertyId;
    
    private String constitution;
    
  //Added by Pramod Katkar for New Filed CR on 12-08-2013
	private String claim;
    private String claimType;
  //End by Pramod Katkar
    private double inSqftBuiltUpArea;
	
	private double inSqftLandArea;
	
	private Date tsrDate;
	
	private Date nextTsrDate;
	
	private String tsrFrequency;
   
	private Date cersiaRegistrationDate;
	
	//Start Santosh
	private double landValue;
	private double buildingValue; 
	private double reconstructionValueOfTheBuilding;
	
	public double getLandValue() {
		return landValue;
	}
	
	public void setLandValue(double landValue) {
		this.landValue = landValue;
	}
	
	public double getBuildingValue() {
		return buildingValue;
	}
	
	public void setBuildingValue(double buildingValue) {
		this.buildingValue = buildingValue;
	}
	public double getReconstructionValueOfTheBuilding() {
		return reconstructionValueOfTheBuilding;
	}
	
	public void setReconstructionValueOfTheBuilding(
			double reconstructionValueOfTheBuilding) {
		this.reconstructionValueOfTheBuilding = reconstructionValueOfTheBuilding;
	}
	//End Santosh
	
	public String getClaim() {
		return claim;
	}
	public void setClaim(String claim) {
		this.claim = claim;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	
	
    public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Date getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getTypeOfMargage() {
		return typeOfMargage;
	}

	public void setTypeOfMargage(String typeOfMargage) {
		this.typeOfMargage = typeOfMargage;
	}

	public String getMorgageCreatedBy() {
		return morgageCreatedBy;
	}

	public void setMorgageCreatedBy(String morgageCreatedBy) {
		this.morgageCreatedBy = morgageCreatedBy;
	}

	public String getDocumentReceived() {
		return documentReceived;
	}

	public void setDocumentReceived(String documentReceived) {
		this.documentReceived = documentReceived;
	}

	public String getDocumentBlock() {
		return documentBlock;
	}

	public void setDocumentBlock(String documentBlock) {
		this.documentBlock = documentBlock;
	}

	public String getBinNumber() {
		return binNumber;
	}

	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
	}

	public Amount getTotalPropertyAmount() {
		return totalPropertyAmount;
	}

	public void setTotalPropertyAmount(Amount totalPropertyAmount) {
		this.totalPropertyAmount = totalPropertyAmount;
	}

	public String getPropertyAddress4() {
		return propertyAddress4;
	}

	public void setPropertyAddress4(String propertyAddress4) {
		this.propertyAddress4 = propertyAddress4;
	}

	public String getPropertyAddress5() {
		return propertyAddress5;
	}

	public void setPropertyAddress5(String propertyAddress5) {
		this.propertyAddress5 = propertyAddress5;
	}

	public String getPropertyAddress6() {
		return propertyAddress6;
	}

	public void setPropertyAddress6(String propertyAddress6) {
		this.propertyAddress6 = propertyAddress6;
	}

	/**
	 * @return the valuatorCompany
	 */
	public String getValuatorCompany() {
		return valuatorCompany;
	}

	/**
	 * @param valuatorCompany the valuatorCompany to set
	 */
	public void setValuatorCompany(String valuatorCompany) {
		this.valuatorCompany = valuatorCompany;
	}

	/**
	 * @return the valuatorName
	 */
	public String getValuatorName() {
		return valuatorName;
	}

	/**
	 * @param valuatorName the valuatorName to set
	 */
	public void setValuatorName(String valuatorName) {
		this.valuatorName = valuatorName;
	}

	/**
	 * @return the mortageRegisteredRef
	 */
	public String getMortageRegisteredRef() {
		return mortageRegisteredRef;
	}

	/**
	 * @param mortageRegisteredRef the mortageRegisteredRef to set
	 */
	public void setMortageRegisteredRef(String mortageRegisteredRef) {
		this.mortageRegisteredRef = mortageRegisteredRef;
	}

	/**
	 * @return the advocateLawyerName
	 */
	public String getAdvocateLawyerName() {
		return advocateLawyerName;
	}

	/**
	 * @param advocateLawyerName the advocateLawyerName to set
	 */
	public void setAdvocateLawyerName(String advocateLawyerName) {
		this.advocateLawyerName = advocateLawyerName;
	}

	/**
	 * @return the propertyLotLocation
	 */
	public String getPropertyLotLocation() {
		return propertyLotLocation;
	}

	/**
	 * @param propertyLotLocation the propertyLotLocation to set
	 */
	public void setPropertyLotLocation(String propertyLotLocation) {
		this.propertyLotLocation = propertyLotLocation;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the nearestState
	 */
	public String getNearestState() {
		return nearestState;
	}

	/**
	 * @param nearestState the nearestState to set
	 */
	public void setNearestState(String nearestState) {
		this.nearestState = nearestState;
	}

	/**
	 * @return the nearestCity
	 */
	public String getNearestCity() {
		return nearestCity;
	}

	/**
	 * @param nearestCity the nearestCity to set
	 */
	public void setNearestCity(String nearestCity) {
		this.nearestCity = nearestCity;
	}

	/**
	 * @return the otherCity
	 */
	public String getOtherCity() {
		return otherCity;
	}

	/**
	 * @param otherCity the otherCity to set
	 */
	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}

	
	public String getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(String auctioneer) {
        this.auctioneer = auctioneer;
    }

    public Date getChattelSoldDate() {
		return chattelSoldDate;
	}

	public void setChattelSoldDate(Date chattelSoldDate) {
		this.chattelSoldDate = chattelSoldDate;
	}

	public String getScheduledLocation() {
		return scheduledLocation;
	}

	public void setScheduledLocation(String scheduledLocation) {
		this.scheduledLocation = scheduledLocation;
	}

	public String getUnitParcelNo() {
		return unitParcelNo;
	}

	public void setUnitParcelNo(String unitParcelNo) {
		this.unitParcelNo = unitParcelNo;
	}

	public char getAbandonedProject() {
		return abandonedProject;
	}

	public void setAbandonedProject(char abandonedProject) {
		this.abandonedProject = abandonedProject;
	}

	public char getBreachInd() {
		return breachInd;
	}

	public void setBreachInd(char breachInd) {
		this.breachInd = breachInd;
	}

	public String getCategoryOfLandUse() {
		return categoryOfLandUse;
	}

	public void setCategoryOfLandUse(String categoryOfLandUse) {
		this.categoryOfLandUse = categoryOfLandUse;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public String getExpressedCondition() {
		return expressedCondition;
	}

	public void setExpressedCondition(String expressedCondition) {
		this.expressedCondition = expressedCondition;
	}

	public char getIndependentValuerFlag() {
		return independentValuerFlag;
	}

	public void setIndependentValuerFlag(char independentValuerFlag) {
		this.independentValuerFlag = independentValuerFlag;
	}

	public String getLocationDistrict() {
		return locationDistrict;
	}

	public void setLocationDistrict(String locationDistrict) {
		this.locationDistrict = locationDistrict;
	}

	public String getLocationMukim() {
		return locationMukim;
	}

	public void setLocationMukim(String locationMukim) {
		this.locationMukim = locationMukim;
	}

	public String getLocationState() {
		return locationState;
	}

	public void setLocationState(String locationState) {
		this.locationState = locationState;
	}

	public String getLongEstablishedMarketFlag() {
		return longEstablishedMarketFlag;
	}

	public void setLongEstablishedMarketFlag(String longEstablishedMarketFlag) {
		this.longEstablishedMarketFlag = longEstablishedMarketFlag;
	}

	public char getMethodologyForValuationFlag() {
		return methodologyForValuationFlag;
	}

	public void setMethodologyForValuationFlag(char methodologyForValuationFlag) {
		this.methodologyForValuationFlag = methodologyForValuationFlag;
	}

	public String getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public char getPropertyCompletedFlag() {
		return propertyCompletedFlag;
	}

	public void setPropertyCompletedFlag(char propertyCompletedFlag) {
		this.propertyCompletedFlag = propertyCompletedFlag;
	}

	public char getPropertyCompletionStatus() {
		return propertyCompletionStatus;
	}

	public void setPropertyCompletionStatus(char setPropertyCompletionStatus) {
		this.propertyCompletionStatus = setPropertyCompletionStatus;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyUsage() {
		return propertyUsage;
	}

	public void setPropertyUsage(String propertyUsage) {
		this.propertyUsage = propertyUsage;
	}

	public char getPropertyWellDevelopedFlag() {
		return propertyWellDevelopedFlag;
	}

	public void setPropertyWellDevelopedFlag(char propertyWellDevelopedFlag) {
		this.propertyWellDevelopedFlag = propertyWellDevelopedFlag;
	}

	public char getRealEstateRentalFlag() {
		return realEstateRentalFlag;
	}

	public void setRealEstateRentalFlag(char realEstateRentalFlag) {
		this.realEstateRentalFlag = realEstateRentalFlag;
	}

	public String getRealEstateUsage() {
		return realEstateUsage;
	}

	public void setRealEstateUsage(String realEstateUsage) {
		this.realEstateUsage = realEstateUsage;
	}

	public char getReducedRiskWeightFlag() {
		return reducedRiskWeightFlag;
	}

	public void setReducedRiskWeightFlag(char reducedRiskWeightFlag) {
		this.reducedRiskWeightFlag = reducedRiskWeightFlag;
	}

	public double getCombinedValueAmount() {
		return combinedValueAmount;
	}

	public void setCombinedValueAmount(double combinedValueAmount) {
		this.combinedValueAmount = combinedValueAmount;
	}

	public String getCombinedValueIndicator() {
		return combinedValueIndicator;
	}

	public void setCombinedValueIndicator(String combinedValueIndicator) {
		this.combinedValueIndicator = combinedValueIndicator;
	}

	public long getValueNumber() {
		return valueNumber;
	}

	public void setValueNumber(long valueNumber) {
		this.valueNumber = valueNumber;
	}

	/**
	 * Default Constructor.
	 */
	public OBPropertyCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPropertyCollateral
	 */
	public OBPropertyCollateral(IPropertyCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Get security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @return String
	 */
	public String getEnvRiskyStatus() {
		return envRiskyStatus;
	}

	/**
	 * Set security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @param envRiskyStatus is of type String
	 */
	public void setEnvRiskyStatus(String envRiskyStatus) {
		this.envRiskyStatus = envRiskyStatus;
	}

	/**
	 * Get date the collateral confirmed as environmentally risky.
	 * 
	 * @return Date
	 */
	public Date getEnvRiskyDate() {
		return envRiskyDate;
	}

	/**
	 * Set date the collateral confirmed as environmentally risky.
	 * 
	 * @param envRiskyDate is of type Date
	 */
	public void setEnvRiskyDate(Date envRiskyDate) {
		this.envRiskyDate = envRiskyDate;
	}

	/**
	 * Get remarks for Security Environmentally Risky.
	 * 
	 * @return String
	 */
	public String getEnvRiskyRemarks() {
		return envRiskyRemarks;
	}

	/**
	 * Set remarks for Security Environmentally Risky.
	 * 
	 * @param envRiskyRemarks is of type String
	 */
	public void setEnvRiskyRemarks(String envRiskyRemarks) {
		this.envRiskyRemarks = envRiskyRemarks;
	}

	/**
	 * Get physical inspection frequency.
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq() {
		return physicalInspectionFreq;
	}

	/**
	 * Set physical inspection frequency.
	 * 
	 * @param physicalInspectionFreq is of type int
	 */
	public void setPhysicalInspectionFreq(int physicalInspectionFreq) {
		this.physicalInspectionFreq = physicalInspectionFreq;
	}

	/**
	 * Get physical inspection frequency unit.
	 * 
	 * @return String
	 */
	public String getPhysicalInspectionFreqUnit() {
		return physicalInspectionFreqUnit;
	}

	/**
	 * Set physical inspection frequency unit.
	 * 
	 * @param physicalInspectionFreqUnit of type String
	 */
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit) {
		this.physicalInspectionFreqUnit = physicalInspectionFreqUnit;
	}

	/**
	 * Get title type.
	 * 
	 * @return String
	 */
	public String getTitleType() {
		return titleType;
	}

	/**
	 * Set title type.
	 * 
	 * @param titleType of type String
	 */
	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	/**
	 * Get title number.
	 * 
	 * @return String
	 */
	public String getTitleNumber() {
		return titleNumber;
	}

	/**
	 * Set title number.
	 * 
	 * @param titleNumber of type String
	 */
	public void setTitleNumber(String titleNumber) {
		this.titleNumber = titleNumber;
	}

	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection() {
		return isPhysicalInspection;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}

	/**
	 * Get last physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate() {
		return lastPhysicalInspectDate;
	}

	/**
	 * Set last physical inspection date.
	 * 
	 * @param lastPhysicalInspectDate of type Date
	 */
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate) {
		this.lastPhysicalInspectDate = lastPhysicalInspectDate;
	}

	/**
	 * Get next physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate() {
		return nextPhysicalInspectDate;
	}

	/**
	 * Set next physical inspection date.
	 * 
	 * @param nextPhysicalInspectDate of type Date
	 */
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate) {
		this.nextPhysicalInspectDate = nextPhysicalInspectDate;
	}

	/**
	 * Get lot number.
	 * 
	 * @return String
	 */
	public String getLotNo() {
		return lotNo;
	}

	/**
	 * Set lot number.
	 * 
	 * @param lotNo of type String
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	/**
	 * Get town/state.
	 * 
	 * @return String
	 */
	public String getTown() {
		return town;
	}

	/**
	 * Set town/state.
	 * 
	 * @param town of type String
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * Get lot location.
	 * 
	 * @return String
	 */
	public String getLotLocation() {
		return lotLocation;
	}

	/**
	 * Set lot location.
	 * 
	 * @param lotLocation of type String
	 */
	public void setLotLocation(String lotLocation) {
		this.lotLocation = lotLocation;
	}

	/**
	 * Get postcode.
	 * 
	 * @return String
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Set postcode.
	 * 
	 * @param postcode of type String
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Get land area.
	 * 
	 * @return double
	 */
	public double getLandArea() {
		return landArea;
	}

	/**
	 * Set land area.
	 * 
	 * @param landArea of type double
	 */
	public void setLandArea(double landArea) {
		this.landArea = landArea;
	}

	/**
	 * Get unit of measurement of land area.
	 * 
	 * @return String
	 */
	public String getLandAreaUOM() {
		return landAreaUOM;
	}

	/**
	 * Set unit of measurement of land area.
	 * 
	 * @param landAreaUOM of type String
	 */
	public void setLandAreaUOM(String landAreaUOM) {
		this.landAreaUOM = landAreaUOM;
	}

	/**
	 * Get tenure.
	 * 
	 * @return int
	 */
	public int getTenure() {
		return tenure;
	}

	/**
	 * Set tenure.
	 * 
	 * @param tenure of type int
	 */
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	/**
	 * Get tenure unit.
	 * 
	 * @return String
	 */
	public String getTenureUnit() {
		return tenureUnit;
	}

	/**
	 * Set tenure unit.
	 * 
	 * @param tenureUnit of type String
	 */
	public void setTenureUnit(String tenureUnit) {
		this.tenureUnit = tenureUnit;
	}

	/**
	 * Get remaining tenure period.
	 * 
	 * @return int
	 */
	public int getRemainTenurePeriod() {
		return remainTenurePeriod;
	}

	/**
	 * Set remaining tenure period.
	 * 
	 * @param remainTenurePeriod of type int
	 */
	public void setRemainTenurePeriod(int remainTenurePeriod) {
		this.remainTenurePeriod = remainTenurePeriod;
	}

	/**
	 * Get unit for remaining tenure period.
	 * 
	 * @return String
	 */
	public String getRemainTenureUnit() {
		return remainTenureUnit;
	}

	/**
	 * Set unit for remaining tenure period.
	 * 
	 * @param remainTenureUnit of type String
	 */
	public void setRemainTenureUnit(String remainTenureUnit) {
		this.remainTenureUnit = remainTenureUnit;
	}

	/**
	 * Get restriction/expressed condition.
	 * 
	 * @return String
	 */
	public String getRestrictionCondition() {
		return restrictionCondition;
	}

	/**
	 * Set restriction/expressed condition.
	 * 
	 * @param restrictionCondition of type String
	 */
	public void setRestrictionCondition(String restrictionCondition) {
		this.restrictionCondition = restrictionCondition;
	}

	/**
	 * Get quit rent amount paid.
	 * 
	 * @return Amount
	 */
	public Amount getQuitRentPaid() {
		return quitRentPaid;
	}

	/**
	 * Set quit rent amount paid.
	 * 
	 * @param quitRentPaid of type Amount
	 */
	public void setQuitRentPaid(Amount quitRentPaid) {
		this.quitRentPaid = quitRentPaid;
	}

	/**
	 * Get description of property.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description of property.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	// Start CR CMS-574 add new field remarks
	/**
	 * Get remarks of property.
	 * 
	 * @return String
	 */
	public String getRemarksProperty() {
		return remarksProperty;
	}

	/**
	 * Set remarks of property.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarksProperty(String remarksProperty) {
		this.remarksProperty = remarksProperty;
	}

	// End CR CMS-574 add new field remarks
	/**
	 * Get sale and purchase agreement value.
	 * 
	 * @return Amount
	 */
	public Amount getSalePurchaseValue() {
		return salePurchaseValue;
	}

	/**
	 * Set sale and purchase agreement value.
	 * 
	 * @param salePurchaseValue of type Amount
	 */
	public void setSalePurchaseValue(Amount salePurchaseValue) {
		this.salePurchaseValue = salePurchaseValue;
	}

	/**
	 * Get nominal value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		return nominalValue;
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		this.nominalValue = nominalValue;
	}

	/**
	 * @return Returns the nonStdQuitRent.
	 */
	public String getNonStdQuitRent() {
		return nonStdQuitRent;
	}

	/**
	 * @param nonStdQuitRent The nonStdQuitRent to set.
	 */
	public void setNonStdQuitRent(String nonStdQuitRent) {
		this.nonStdQuitRent = nonStdQuitRent;
	}

	/**
	 * @return Returns the propertyAddress.
	 */
	public String getPropertyAddress() {
		return propertyAddress;
	}

	/**
	 * @param propertyAddress The propertyAddress to set.
	 */
	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	/**
	 * @return Returns the quitRentReceipt.
	 */
	public String getQuitRentReceipt() {
		return quitRentReceipt;
	}

	/**
	 * @param quitRentReceipt The quitRentReceipt to set.
	 */
	public void setQuitRentReceipt(String quitRentReceipt) {
		this.quitRentReceipt = quitRentReceipt;
	}

	/**
	 * @return Returns the registedHolder.
	 */
	public String getRegistedHolder() {
		return registedHolder;
	}

	/**
	 * @param registedHolder The registedHolder to set.
	 */
	public void setRegistedHolder(String registedHolder) {
		this.registedHolder = registedHolder;
	}

	/**
	 * @return Returns the stdQuitRent.
	 */
	public String getStdQuitRent() {
		return stdQuitRent;
	}

	/**
	 * @param stdQuitRent The stdQuitRent to set.
	 */
	public void setStdQuitRent(String stdQuitRent) {
		this.stdQuitRent = stdQuitRent;
	}

	public double getBuiltupArea() {
		return builtupArea;
	}

	public void setBuiltupArea(double builtupArea) {
		this.builtupArea = builtupArea;
	}

	public String getBuiltupAreaUOM() {
		return builtupAreaUOM;
	}

	public void setBuiltupAreaUOM(String builtupAreaUOM) {
		this.builtupAreaUOM = builtupAreaUOM;
	}

	// WLS 15 Aug,2007: Abank CMS add new field
	public String getDevGrpCo() {
		return devGrpCo;
	}

	/** Added by Grace;ABank CLIMS;11/8/2007;Description:add new field */
	public String getTaman() {
		return taman;
	}

	public void setTaman(String taman) {
		this.taman = taman;
	}

	/** Added by Grace;ABank CLIMS project;11/8/2007;Description:add new field */
	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public String getTooltipsLand() {
		return tooltipsLand;
	}

	public void setDevGrpCo(String devGrpCo) {
		this.devGrpCo = devGrpCo;
	}

	public void setTooltipsLand(String tooltipsLand) {
		this.tooltipsLand = tooltipsLand;
	}

	public String getTooltipsBuiltArea() {
		return tooltipsBuiltArea;
	}

	public void setTooltipsBuiltArea(String tooltipsBuiltArea) {
		this.tooltipsBuiltArea = tooltipsBuiltArea;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral#getComputedMFScore
	 */
	public Double getComputedMFScore() {
		return this.computedMFScore;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral#setComputedMFScore
	 */
	public void setComputedMFScore(Double value) {
		this.computedMFScore = value;
	}

	public Double getUserInput() {
		return userInput;
	}

	public void setUserInput(Double userInput) {
		this.userInput = userInput;
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBPropertyCollateral)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public String getMasterTitle() {
		return masterTitle;
	}

	public void setMasterTitle(String masterTitle) {
		this.masterTitle = masterTitle;
	}

	public Date getSalePurchaseDate() {
		return salePurchaseDate;
	}

	public void setSalePurchaseDate(Date salePurchaseDate) {
		this.salePurchaseDate = salePurchaseDate;
	}

	public String getMasterTitleNumber() {
		return masterTitleNumber;
	}

	public void setMasterTitleNumber(String masterTitleNumber) {
		this.masterTitleNumber = masterTitleNumber;
	}

	public Date getNextQuitRentDate() {
		return nextQuitRentDate;
	}

	public void setNextQuitRentDate(Date nextQuitRentDate) {
		this.nextQuitRentDate = nextQuitRentDate;
	}

	public Date getQuitRentPaymentDate() {
		return quitRentPaymentDate;
	}

	public void setQuitRentPaymentDate(Date quitRentPaymentDate) {
		this.quitRentPaymentDate = quitRentPaymentDate;
	}

	public Amount getAssessmentRate() {
		return assessmentRate;
	}

	public void setAssessmentRate(Amount assessmentRate) {
		this.assessmentRate = assessmentRate;
	}

	public int getAssessmentPeriod() {
		return assessmentPeriod;
	}

	public void setAssessmentPeriod(int assessmentPeriod) {
		this.assessmentPeriod = assessmentPeriod;
	}

	public Date getAssessmentPaymentDate() {
		return assessmentPaymentDate;
	}

	public void setAssessmentPaymentDate(Date assessmentPaymentDate) {
		this.assessmentPaymentDate = assessmentPaymentDate;
	}

	public Amount getAuctionPrice() {
		return auctionPrice;
	}

	public void setAuctionPrice(Amount auctionPrice) {
		this.auctionPrice = auctionPrice;
	}

	public Date getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}

	public Date getPriCaveatGuaranteeDate() {
		return priCaveatGuaranteeDate;
	}

	public void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate) {
		this.priCaveatGuaranteeDate = priCaveatGuaranteeDate;
	}

	public String getNonPreferredLocation() {
		return nonPreferredLocation;
	}

	public void setNonPreferredLocation(String nonPreferredLocation) {
		this.nonPreferredLocation = nonPreferredLocation;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public char getPropertyCompletionStage() {
		return propertyCompletionStage;
	}

	public void setPropertyCompletionStage(char propertyCompletionStage) {
		this.propertyCompletionStage = propertyCompletionStage;
	}

	public void setPhysicalInspection(boolean isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}

	public boolean getAssumption() {
		return assumption;
	}

	public void setAssumption(boolean assumption) {
		this.assumption = assumption;
	}

	public Amount getAmountRedeem() {
		return amountRedeem;
	}

	public void setAmountRedeem(Amount amountRedeem) {
		this.amountRedeem = amountRedeem;
	}

	public String getPropertyAddress2() {
		return propertyAddress2;
	}

	public void setPropertyAddress2(String propertyAddress2) {
		this.propertyAddress2 = propertyAddress2;
	}

	public String getPropertyAddress3() {
		return propertyAddress3;
	}

	public void setPropertyAddress3(String propertyAddress3) {
		this.propertyAddress3 = propertyAddress3;
	}

	public Amount getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Amount unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getAssessmentOption() {
		return assessmentOption;
	}

	public void setAssessmentOption(String assessmentOption) {
		this.assessmentOption = assessmentOption;
	}

	public String getTitleNumberPrefix() {
		return titleNumberPrefix;
	}

	public void setTitleNumberPrefix(String titleNumberPrefix) {
		this.titleNumberPrefix = titleNumberPrefix;
	}

	public String getLotNumberPrefix() {
		return lotNumberPrefix;
	}

	public void setLotNumberPrefix(String lotNumberPrefix) {
		this.lotNumberPrefix = lotNumberPrefix;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public String getStoreyNumber() {
		return storeyNumber;
	}

	public void setStoreyNumber(String storeyNumber) {
		this.storeyNumber = storeyNumber;
	}
	
	public double getInSqftBuiltUpArea() {
		return inSqftBuiltUpArea;
	}

	public void setInSqftBuiltUpArea(double inSqftBuiltUpArea) {
		this.inSqftBuiltUpArea = inSqftBuiltUpArea;
	}

	public double getInSqftLandArea() {
		return inSqftLandArea;
	}

	public void setInSqftLandArea(double inSqftLandArea) {
		this.inSqftLandArea = inSqftLandArea;
	}

	public Date getTsrDate() {
		return tsrDate;
	}

	public void setTsrDate(Date tsrDate) {
		this.tsrDate = tsrDate;
	}

	public Date getNextTsrDate() {
		return nextTsrDate;
	}

	public void setNextTsrDate(Date nextTsrDate) {
		this.nextTsrDate = nextTsrDate;
	}

	public String getTsrFrequency() {
		return tsrFrequency;
	}

	public void setTsrFrequency(String tsrFrequency) {
		this.tsrFrequency = tsrFrequency;
	}
	
	public Date getCersiaRegistrationDate() {
		return cersiaRegistrationDate;
	}
	
	public void setCersiaRegistrationDate(Date cersiaRegistrationDate) {
		this.cersiaRegistrationDate = cersiaRegistrationDate;
	}
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	
	private Date legalAuditDate;
	private Date interveingPeriSearchDate;
	private Date dateOfReceiptTitleDeed;
	private String waiver;
	private String deferral;
	private String deferralId;
	
	
	private Date valuationDate_v1;
	private Amount totalPropertyAmount_v1;
	private String valuatorCompany_v1;
	private String categoryOfLandUse_v1;
	private String developerName_v1;
	private String country_v1;
	private String region_v1;
	private String locationState_v1;
	private String nearestCity_v1;
	private String pinCode_v1;
	private double landArea_v1;
	private String landAreaUOM_v1;
	private double inSqftLandArea_v1;
	private double builtupArea_v1;
	private String builtupAreaUOM_v1;
	private double inSqftBuiltupArea_v1;
	private char propertyCompletionStatus_v1;
	private double landValue_v1;
	private double buildingValue_v1; 
	private double reconstructionValueOfTheBuilding_v1;
	/*private String isPhysInsp_v1;
	
	private String datePhyInspec_v1 = "";
	private String nextPhysInspDate_v1 = "";*/
	
	private String physicalInspectionFreqUnit_v1 = "";
	private boolean isPhysicalInspection_v1;
	private Date lastPhysicalInspectDate_v1;
    private Date nextPhysicalInspectDate_v1;
	
	private String remarksProperty_v1 = "";
	private String val1_id;
	//private String valEdited_v1;
	private Date valcreationdate_v1 ;
	
	//Valuation 3 start
	
	private Date valuationDate_v3;
	private Amount totalPropertyAmount_v3;
	private String valuatorCompany_v3;
	private String categoryOfLandUse_v3;
	private String developerName_v3;
	private String country_v3;
	private String region_v3;
	private String locationState_v3;
	private String nearestCity_v3;
	private String pinCode_v3;
	private double landArea_v3;
	private String landAreaUOM_v3;
	private double inSqftLandArea_v3;
	private double builtupArea_v3;
	private String builtupAreaUOM_v3;
	private double inSqftBuiltupArea_v3;
	private char propertyCompletionStatus_v3;
	private double landValue_v3;
	private double buildingValue_v3; 
	private double reconstructionValueOfTheBuilding_v3;
	/*private String isPhysInsp_v3;
	
	private String datePhyInspec_v3 = "";
	private String nextPhysInspDate_v3 = "";*/
	
	private String physicalInspectionFreqUnit_v3 = "";
	private boolean isPhysicalInspection_v3;
	private Date lastPhysicalInspectDate_v3;
    private Date nextPhysicalInspectDate_v3;
	
	private String remarksProperty_v3 = "";
	private String val3_id;
	//private String valEdited_v3;
	private Date valcreationdate_v3 ;
	private String preValDate_v3;
	
	//Valuation 3 end

	public Date getLegalAuditDate() {
		return legalAuditDate;
	}
	public void setLegalAuditDate(Date legalAuditDate) {
		this.legalAuditDate = legalAuditDate;
	}
	public Date getInterveingPeriSearchDate() {
		return interveingPeriSearchDate;
	}
	public void setInterveingPeriSearchDate(Date interveingPeriSearchDate) {
		this.interveingPeriSearchDate = interveingPeriSearchDate;
	}
	public Date getDateOfReceiptTitleDeed() {
		return dateOfReceiptTitleDeed;
	}
	public void setDateOfReceiptTitleDeed(Date dateOfReceiptTitleDeed) {
		this.dateOfReceiptTitleDeed = dateOfReceiptTitleDeed;
	}
	public String getWaiver() {
		return waiver;
	}
	public void setWaiver(String waiver) {
		this.waiver = waiver;
	}
	public String getDeferral() {
		return deferral;
	}
	public void setDeferral(String deferral) {
		this.deferral = deferral;
	}
	public String getDeferralId() {
		return deferralId;
	}
	public void setDeferralId(String deferralId) {
		this.deferralId = deferralId;
	}


	public Date getValuationDate_v1() {
		return valuationDate_v1;
	}
	public void setValuationDate_v1(Date valuationDate_v1) {
		this.valuationDate_v1 = valuationDate_v1;
	}
	public Amount getTotalPropertyAmount_v1() {
		return totalPropertyAmount_v1;
	}
	public void setTotalPropertyAmount_v1(Amount totalPropertyAmount_v1) {
		this.totalPropertyAmount_v1 = totalPropertyAmount_v1;
	}
	public String getValuatorCompany_v1() {
		return valuatorCompany_v1;
	}
	public void setValuatorCompany_v1(String valuatorCompany_v1) {
		this.valuatorCompany_v1 = valuatorCompany_v1;
	}
	public String getCategoryOfLandUse_v1() {
		return categoryOfLandUse_v1;
	}
	public void setCategoryOfLandUse_v1(String categoryOfLandUse_v1) {
		this.categoryOfLandUse_v1 = categoryOfLandUse_v1;
	}
	public String getDeveloperName_v1() {
		return developerName_v1;
	}
	public void setDeveloperName_v1(String developerName_v1) {
		this.developerName_v1 = developerName_v1;
	}
	public String getCountry_v1() {
		return country_v1;
	}
	public void setCountry_v1(String country_v1) {
		this.country_v1 = country_v1;
	}
	public String getRegion_v1() {
		return region_v1;
	}
	public void setRegion_v1(String region_v1) {
		this.region_v1 = region_v1;
	}
	public String getLocationState_v1() {
		return locationState_v1;
	}
	public void setLocationState_v1(String locationState_v1) {
		this.locationState_v1 = locationState_v1;
	}
	public String getNearestCity_v1() {
		return nearestCity_v1;
	}
	public void setNearestCity_v1(String nearestCity_v1) {
		this.nearestCity_v1 = nearestCity_v1;
	}
	public String getPinCode_v1() {
		return pinCode_v1;
	}
	public void setPinCode_v1(String pinCode_v1) {
		this.pinCode_v1 = pinCode_v1;
	}
	public double getLandArea_v1() {
		return landArea_v1;
	}
	public void setLandArea_v1(double landArea_v1) {
		this.landArea_v1 = landArea_v1;
	}
	public String getLandAreaUOM_v1() {
		return landAreaUOM_v1;
	}
	public void setLandAreaUOM_v1(String landAreaUOM_v1) {
		this.landAreaUOM_v1 = landAreaUOM_v1;
	}
	public double getInSqftLandArea_v1() {
		return inSqftLandArea_v1;
	}
	public void setInSqftLandArea_v1(double inSqftLandArea_v1) {
		this.inSqftLandArea_v1 = inSqftLandArea_v1;
	}
	public double getBuiltupArea_v1() {
		return builtupArea_v1;
	}
	public void setBuiltupArea_v1(double builtupArea_v1) {
		this.builtupArea_v1 = builtupArea_v1;
	}
	public String getBuiltupAreaUOM_v1() {
		return builtupAreaUOM_v1;
	}
	public void setBuiltupAreaUOM_v1(String builtupAreaUOM_v1) {
		this.builtupAreaUOM_v1 = builtupAreaUOM_v1;
	}
	public double getInSqftBuiltupArea_v1() {
		return inSqftBuiltupArea_v1;
	}
	public void setInSqftBuiltupArea_v1(double inSqftBuiltupArea_v1) {
		this.inSqftBuiltupArea_v1 = inSqftBuiltupArea_v1;
	}
	public char getPropertyCompletionStatus_v1() {
		return propertyCompletionStatus_v1;
	}
	public void setPropertyCompletionStatus_v1(char propertyCompletionStatus_v1) {
		this.propertyCompletionStatus_v1 = propertyCompletionStatus_v1;
	}
	public double getLandValue_v1() {
		return landValue_v1;
	}
	public void setLandValue_v1(double landValue_v1) {
		this.landValue_v1 = landValue_v1;
	}
	public double getBuildingValue_v1() {
		return buildingValue_v1;
	}
	public void setBuildingValue_v1(double buildingValue_v1) {
		this.buildingValue_v1 = buildingValue_v1;
	}
	public double getReconstructionValueOfTheBuilding_v1() {
		return reconstructionValueOfTheBuilding_v1;
	}
	public void setReconstructionValueOfTheBuilding_v1(double reconstructionValueOfTheBuilding_v1) {
		this.reconstructionValueOfTheBuilding_v1 = reconstructionValueOfTheBuilding_v1;
	}
	
	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection_v1() {
		return isPhysicalInspection_v1;
	}
	
	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection_v1(boolean isPhysicalInspection_v1) {
		this.isPhysicalInspection_v1 = isPhysicalInspection_v1;
	}

	/*private int physicalInspectionFreq_v1;
	public int getPhysicalInspectionFreq_v1() {
		return physicalInspectionFreq_v1;
	}
    public void setPhysicalInspectionFreq_v1(int physicalInspectionFreq_v1) {
		this.physicalInspectionFreq_v1 = physicalInspectionFreq_v1;
	}*/
	public String getPhysicalInspectionFreqUnit_v1() {
		return physicalInspectionFreqUnit_v1;
	}
	public void setPhysicalInspectionFreqUnit_v1(String physicalInspectionFreqUnit_v1) {
		this.physicalInspectionFreqUnit_v1 = physicalInspectionFreqUnit_v1;
	}

	/**
	 * Get last physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate_v1() {
		return lastPhysicalInspectDate_v1;
	}

	/**
	 * Set last physical inspection date.
	 * 
	 * @param lastPhysicalInspectDate of type Date
	 */
	public void setLastPhysicalInspectDate_v1(Date lastPhysicalInspectDate_v1) {
		this.lastPhysicalInspectDate_v1 = lastPhysicalInspectDate_v1;
	}

	/**
	 * Get next physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate_v1() {
		return nextPhysicalInspectDate_v1;
	}

	/**
	 * Set next physical inspection date.
	 * 
	 * @param nextPhysicalInspectDate of type Date
	 */
	public void setNextPhysicalInspectDate_v1(Date nextPhysicalInspectDate_v1) {
		this.nextPhysicalInspectDate_v1 = nextPhysicalInspectDate_v1;
	}

	public String getRemarksProperty_v1() {
		return remarksProperty_v1;
	}
	public void setRemarksProperty_v1(String remarksProperty_v1) {
		this.remarksProperty_v1 = remarksProperty_v1;
	}

	public String getVal1_id() {
		return val1_id;
	}
	public void setVal1_id(String val1_id) {
		this.val1_id = val1_id;
	}
	public Date getValcreationdate_v1() {
		return valcreationdate_v1;
	}
	public void setValcreationdate_v1(Date valcreationdate_v1) {
		this.valcreationdate_v1 = valcreationdate_v1;
	}
	private String mortgageCreationAdd;
	
	public String getMortgageCreationAdd() {
		return mortgageCreationAdd;
	}
	public void setMortgageCreationAdd(String mortgageCreationAdd) {
		this.mortgageCreationAdd = mortgageCreationAdd;
	}
	private Date previousMortCreationDate;
	public Date getPreviousMortCreationDate() {
		return previousMortCreationDate;
	}
	public void setPreviousMortCreationDate(Date previousMortCreationDate) {
		this.previousMortCreationDate = previousMortCreationDate;
	}
	
	private String preValDate_v1;
	public String getPreValDate_v1() {
		return preValDate_v1;
	}
	public void setPreValDate_v1(String preValDate_v1) {
		this.preValDate_v1 = preValDate_v1;
	}

	public Date getValuationDate_v3() {
		return valuationDate_v3;
	}

	public void setValuationDate_v3(Date valuationDate_v3) {
		this.valuationDate_v3 = valuationDate_v3;
	}

	public Amount getTotalPropertyAmount_v3() {
		return totalPropertyAmount_v3;
	}

	public void setTotalPropertyAmount_v3(Amount totalPropertyAmount_v3) {
		this.totalPropertyAmount_v3 = totalPropertyAmount_v3;
	}

	public String getValuatorCompany_v3() {
		return valuatorCompany_v3;
	}

	public void setValuatorCompany_v3(String valuatorCompany_v3) {
		this.valuatorCompany_v3 = valuatorCompany_v3;
	}

	public String getCategoryOfLandUse_v3() {
		return categoryOfLandUse_v3;
	}

	public void setCategoryOfLandUse_v3(String categoryOfLandUse_v3) {
		this.categoryOfLandUse_v3 = categoryOfLandUse_v3;
	}

	public String getDeveloperName_v3() {
		return developerName_v3;
	}

	public void setDeveloperName_v3(String developerName_v3) {
		this.developerName_v3 = developerName_v3;
	}

	public String getCountry_v3() {
		return country_v3;
	}

	public void setCountry_v3(String country_v3) {
		this.country_v3 = country_v3;
	}

	public String getRegion_v3() {
		return region_v3;
	}

	public void setRegion_v3(String region_v3) {
		this.region_v3 = region_v3;
	}

	public String getLocationState_v3() {
		return locationState_v3;
	}

	public void setLocationState_v3(String locationState_v3) {
		this.locationState_v3 = locationState_v3;
	}

	public String getNearestCity_v3() {
		return nearestCity_v3;
	}

	public void setNearestCity_v3(String nearestCity_v3) {
		this.nearestCity_v3 = nearestCity_v3;
	}

	public String getPinCode_v3() {
		return pinCode_v3;
	}

	public void setPinCode_v3(String pinCode_v3) {
		this.pinCode_v3 = pinCode_v3;
	}

	public double getLandArea_v3() {
		return landArea_v3;
	}

	public void setLandArea_v3(double landArea_v3) {
		this.landArea_v3 = landArea_v3;
	}

	public String getLandAreaUOM_v3() {
		return landAreaUOM_v3;
	}

	public void setLandAreaUOM_v3(String landAreaUOM_v3) {
		this.landAreaUOM_v3 = landAreaUOM_v3;
	}

	public double getInSqftLandArea_v3() {
		return inSqftLandArea_v3;
	}

	public void setInSqftLandArea_v3(double inSqftLandArea_v3) {
		this.inSqftLandArea_v3 = inSqftLandArea_v3;
	}

	public double getBuiltupArea_v3() {
		return builtupArea_v3;
	}

	public void setBuiltupArea_v3(double builtupArea_v3) {
		this.builtupArea_v3 = builtupArea_v3;
	}

	public String getBuiltupAreaUOM_v3() {
		return builtupAreaUOM_v3;
	}

	public void setBuiltupAreaUOM_v3(String builtupAreaUOM_v3) {
		this.builtupAreaUOM_v3 = builtupAreaUOM_v3;
	}

	public double getInSqftBuiltupArea_v3() {
		return inSqftBuiltupArea_v3;
	}

	public void setInSqftBuiltupArea_v3(double inSqftBuiltupArea_v3) {
		this.inSqftBuiltupArea_v3 = inSqftBuiltupArea_v3;
	}

	public char getPropertyCompletionStatus_v3() {
		return propertyCompletionStatus_v3;
	}

	public void setPropertyCompletionStatus_v3(char propertyCompletionStatus_v3) {
		this.propertyCompletionStatus_v3 = propertyCompletionStatus_v3;
	}

	public double getLandValue_v3() {
		return landValue_v3;
	}

	public void setLandValue_v3(double landValue_v3) {
		this.landValue_v3 = landValue_v3;
	}

	public double getBuildingValue_v3() {
		return buildingValue_v3;
	}

	public void setBuildingValue_v3(double buildingValue_v3) {
		this.buildingValue_v3 = buildingValue_v3;
	}

	public double getReconstructionValueOfTheBuilding_v3() {
		return reconstructionValueOfTheBuilding_v3;
	}

	public void setReconstructionValueOfTheBuilding_v3(double reconstructionValueOfTheBuilding_v3) {
		this.reconstructionValueOfTheBuilding_v3 = reconstructionValueOfTheBuilding_v3;
	}

	public String getPhysicalInspectionFreqUnit_v3() {
		return physicalInspectionFreqUnit_v3;
	}

	public void setPhysicalInspectionFreqUnit_v3(String physicalInspectionFreqUnit_v3) {
		this.physicalInspectionFreqUnit_v3 = physicalInspectionFreqUnit_v3;
	}
	
//	public boolean isPhysicalInspection_v3() {
//		return isPhysicalInspection_v3;
//	}
//
//	public void setPhysicalInspection_v3(boolean isPhysicalInspection_v3) {
//		this.isPhysicalInspection_v3 = isPhysicalInspection_v3;
//	}
	
	public boolean getIsPhysicalInspection_v3() {
		return isPhysicalInspection_v3;
	}
	
	public void setIsPhysicalInspection_v3(boolean isPhysicalInspection_v3) {
		this.isPhysicalInspection_v3 = isPhysicalInspection_v3;
	}

	public Date getLastPhysicalInspectDate_v3() {
		return lastPhysicalInspectDate_v3;
	}

	public void setLastPhysicalInspectDate_v3(Date lastPhysicalInspectDate_v3) {
		this.lastPhysicalInspectDate_v3 = lastPhysicalInspectDate_v3;
	}

	public Date getNextPhysicalInspectDate_v3() {
		return nextPhysicalInspectDate_v3;
	}

	public void setNextPhysicalInspectDate_v3(Date nextPhysicalInspectDate_v3) {
		this.nextPhysicalInspectDate_v3 = nextPhysicalInspectDate_v3;
	}

	public String getRemarksProperty_v3() {
		return remarksProperty_v3;
	}

	public void setRemarksProperty_v3(String remarksProperty_v3) {
		this.remarksProperty_v3 = remarksProperty_v3;
	}

	public String getVal3_id() {
		return val3_id;
	}

	public void setVal3_id(String val3_id) {
		this.val3_id = val3_id;
	}

	public Date getValcreationdate_v3() {
		return valcreationdate_v3;
	}

	public void setValcreationdate_v3(Date valcreationdate_v3) {
		this.valcreationdate_v3 = valcreationdate_v3;
	}

	public String getPreValDate_v3() {
		return preValDate_v3;
	}

	public void setPreValDate_v3(String preValDate_v3) {
		this.preValDate_v3 = preValDate_v3;
	}
	
	
	private Date valuationDate_v2;
	private Amount totalPropertyAmount_v2;
	private String valuatorCompany_v2;
	private String categoryOfLandUse_v2;
	private String developerName_v2;
	private String country_v2;
	private String region_v2;
	private String locationState_v2;
	private String nearestCity_v2;
	private String pinCode_v2;
	private double landArea_v2;
	private String landAreaUOM_v2;
	private double inSqftLandArea_v2;
	private double builtupArea_v2;
	private String builtupAreaUOM_v2;
	private double inSqftBuiltupArea_v2;
	private char propertyCompletionStatus_v2;
	private double landValue_v2;
	private double buildingValue_v2; 
	private double reconstructionValueOfTheBuilding_v2;
	private String physicalInspectionFreqUnit_v2 = "";
	private boolean isPhysicalInspection_v2;
	private Date lastPhysicalInspectDate_v2;
    private Date nextPhysicalInspectDate_v2;
	
	private String remarksProperty_v2 = "";
	private String val2_id;
	private Date valcreationdate_v2 ;
	

	public Date getValuationDate_v2() {
		return valuationDate_v2;
	}
	public void setValuationDate_v2(Date valuationDate_v2) {
		this.valuationDate_v2 = valuationDate_v2;
	}
	public Amount getTotalPropertyAmount_v2() {
		return totalPropertyAmount_v2;
	}
	public void setTotalPropertyAmount_v2(Amount totalPropertyAmount_v2) {
		this.totalPropertyAmount_v2 = totalPropertyAmount_v2;
	}
	public String getValuatorCompany_v2() {
		return valuatorCompany_v2;
	}
	public void setValuatorCompany_v2(String valuatorCompany_v2) {
		this.valuatorCompany_v2 = valuatorCompany_v2;
	}
	public String getCategoryOfLandUse_v2() {
		return categoryOfLandUse_v2;
	}
	public void setCategoryOfLandUse_v2(String categoryOfLandUse_v2) {
		this.categoryOfLandUse_v2 = categoryOfLandUse_v2;
	}
	public String getDeveloperName_v2() {
		return developerName_v2;
	}
	public void setDeveloperName_v2(String developerName_v2) {
		this.developerName_v2 = developerName_v2;
	}
	public String getCountry_v2() {
		return country_v2;
	}
	public void setCountry_v2(String country_v2) {
		this.country_v2 = country_v2;
	}
	public String getRegion_v2() {
		return region_v2;
	}
	public void setRegion_v2(String region_v2) {
		this.region_v2 = region_v2;
	}
	public String getLocationState_v2() {
		return locationState_v2;
	}
	public void setLocationState_v2(String locationState_v2) {
		this.locationState_v2 = locationState_v2;
	}
	public String getNearestCity_v2() {
		return nearestCity_v2;
	}
	public void setNearestCity_v2(String nearestCity_v2) {
		this.nearestCity_v2 = nearestCity_v2;
	}
	public String getPinCode_v2() {
		return pinCode_v2;
	}
	public void setPinCode_v2(String pinCode_v2) {
		this.pinCode_v2 = pinCode_v2;
	}
	public double getLandArea_v2() {
		return landArea_v2;
	}
	public void setLandArea_v2(double landArea_v2) {
		this.landArea_v2 = landArea_v2;
	}
	public String getLandAreaUOM_v2() {
		return landAreaUOM_v2;
	}
	public void setLandAreaUOM_v2(String landAreaUOM_v2) {
		this.landAreaUOM_v2 = landAreaUOM_v2;
	}
	public double getInSqftLandArea_v2() {
		return inSqftLandArea_v2;
	}
	public void setInSqftLandArea_v2(double inSqftLandArea_v2) {
		this.inSqftLandArea_v2 = inSqftLandArea_v2;
	}
	public double getBuiltupArea_v2() {
		return builtupArea_v2;
	}
	public void setBuiltupArea_v2(double builtupArea_v2) {
		this.builtupArea_v2 = builtupArea_v2;
	}
	public String getBuiltupAreaUOM_v2() {
		return builtupAreaUOM_v2;
	}
	public void setBuiltupAreaUOM_v2(String builtupAreaUOM_v2) {
		this.builtupAreaUOM_v2 = builtupAreaUOM_v2;
	}
	public double getInSqftBuiltupArea_v2() {
		return inSqftBuiltupArea_v2;
	}
	public void setInSqftBuiltupArea_v2(double inSqftBuiltupArea_v2) {
		this.inSqftBuiltupArea_v2 = inSqftBuiltupArea_v2;
	}
	public char getPropertyCompletionStatus_v2() {
		return propertyCompletionStatus_v2;
	}
	public void setPropertyCompletionStatus_v2(char propertyCompletionStatus_v2) {
		this.propertyCompletionStatus_v2 = propertyCompletionStatus_v2;
	}
	public double getLandValue_v2() {
		return landValue_v2;
	}
	public void setLandValue_v2(double landValue_v2) {
		this.landValue_v2 = landValue_v2;
	}
	public double getBuildingValue_v2() {
		return buildingValue_v2;
	}
	public void setBuildingValue_v2(double buildingValue_v2) {
		this.buildingValue_v2 = buildingValue_v2;
	}
	public double getReconstructionValueOfTheBuilding_v2() {
		return reconstructionValueOfTheBuilding_v2;
	}
	public void setReconstructionValueOfTheBuilding_v2(double reconstructionValueOfTheBuilding_v2) {
		this.reconstructionValueOfTheBuilding_v2 = reconstructionValueOfTheBuilding_v2;
	}
	
	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection_v2() {
		return isPhysicalInspection_v2;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection_v2(boolean isPhysicalInspection_v2) {
		this.isPhysicalInspection_v2 = isPhysicalInspection_v2;
	}

	
	public String getPhysicalInspectionFreqUnit_v2() {
		return physicalInspectionFreqUnit_v2;
	}
	public void setPhysicalInspectionFreqUnit_v2(String physicalInspectionFreqUnit_v2) {
		this.physicalInspectionFreqUnit_v2 = physicalInspectionFreqUnit_v2;
	}

	/**
	 * Get last physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate_v2() {
		return lastPhysicalInspectDate_v2;
	}

	/**
	 * Set last physical inspection date.
	 * 
	 * @param lastPhysicalInspectDate of type Date
	 */
	public void setLastPhysicalInspectDate_v2(Date lastPhysicalInspectDate_v2) {
		this.lastPhysicalInspectDate_v2 = lastPhysicalInspectDate_v2;
	}

	/**
	 * Get next physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate_v2() {
		return nextPhysicalInspectDate_v2;
	}

	/**
	 * Set next physical inspection date.
	 * 
	 * @param nextPhysicalInspectDate of type Date
	 */
	public void setNextPhysicalInspectDate_v2(Date nextPhysicalInspectDate_v2) {
		this.nextPhysicalInspectDate_v2 = nextPhysicalInspectDate_v2;
	}

	public String getRemarksProperty_v2() {
		return remarksProperty_v2;
	}
	public void setRemarksProperty_v2(String remarksProperty_v2) {
		this.remarksProperty_v2 = remarksProperty_v2;
	}

	public String getVal2_id() {
		return val2_id;
	}
	public void setVal2_id(String val2_id) {
		this.val2_id = val2_id;
	}
	public Date getValcreationdate_v2() {
		return valcreationdate_v2;
	}
	public void setValcreationdate_v2(Date valcreationdate_v2) {
		this.valcreationdate_v2 = valcreationdate_v2;
	}

	
	private String preValDate_v2;
	public String getPreValDate_v2() {
		return preValDate_v2;
	}
	public void setPreValDate_v2(String preValDate_v2) {
		this.preValDate_v2 = preValDate_v2;
	}
	
	private String version1;
	private String version2;
	private String version3;

	public String getVersion1() {
		return version1;
	}
	public void setVersion1(String version1) {
		this.version1 = version1;
	}
	public String getVersion2() {
		return version2;
	}
	public void setVersion2(String version2) {
		this.version2 = version2;
	}
	public String getVersion3() {
		return version3;
	}
	public void setVersion3(String version3) {
		this.version3 = version3;
	}
	
	private String revalOverride;

	public String getRevalOverride() {
		return revalOverride;
	}

	public void setRevalOverride(String revalOverride) {
		this.revalOverride = revalOverride;
	}
	
	
}
