/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateral.java,v 1.35 2006/09/15 08:30:10 hshii Exp $
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;
/**
 * This class represents a Collateral entity.
 * 
 * @author $Author: Mn $<br>
 * @version $Revision: 1.35 $
 * @since $String: 2006/09/15 08:30:10 $ Tag: $Name: $
 */
public class PropertyRestRequestDTO {
	
private String propertyId;
	
	private String propertyType;
	
	//Borrower's Name pending 
	private String description;
	
	private String propertyUsage;
	
	private String salePurchaseValue;
	
	private String dateOfReceiptTitleDeed;
	
	private String previousMortCreationDate; 
	
	private String salePurchaseDate; //Mortgage Creation /Extension date
	
	private String mortgageCreExtDateAdd;
    
	private String legalAuditDate;
	
	private String interveingPeriSearchDate;
	
	private String typeOfMargage;
	
	private String documentReceived;
	
	private String morgageCreatedBy;
	
	private String documentBlock;
	
	private String binNumber;
	
	private String claim;
	
    private String claimType;
    
    private String mortageRegisteredRef;
    
    private String advocateLawyerName;
    
    private String devGrpCo;
    
    private String projectName;
    
    private String lotNumberPrefix;
    
    private String lotNo;
    
    private String propertyLotLocation;
    
    private String otherCity;
    
    private String propertyAddress;

	private String propertyAddress2;

	private String propertyAddress3;
    
    private String propertyAddress4;
    
    private String propertyAddress5;
    
    private String propertyAddress6;
    
    private List<AddtionalDocumentFacilityDetailsRestDTO> addDocFacDetailsList;
    
    private String envRiskyStatus;
    
    private String envRiskyDate;
    
	private String tsrDate;
	
	private String tsrFrequency;
	
	private String nextTsrDate;
	
	private String cersiaRegistrationDate;
	
	private String constitution;
	
	private String envRiskyRemarks;
	
	private String revalOverride;
	
	// Valuation 1
	
	private String revaluation_v1_add;  //If y consider as add revaulation IF N edit existing Valuation
	
	private String preValDate_v1; //getPreviousValList
	
	private String valcreationdate_v1 ;
	
	private String totalPropertyAmount_v1;
	
	private String valuationDate_v1;
	
	private String valuatorCompany_v1;
	
	private String categoryOfLandUse_v1;
	
	private String developerName_v1;
	
	private String country_v1;
	
	private String region_v1;
	
	private String isAdDocFac;
	
	private String locationState_v1;
	
	private String nearestCity_v1;
	
	private String pinCode_v1;
	
	private String landArea_v1;
	
	private String landAreaUOM_v1;
	
	private String inSqftLandArea_v1;
	
	private String builtupArea_v1;
	
	private String builtupAreaUOM_v1;
	
	private String inSqftBuiltupArea_v1;
	
	
	
	private String landValue_v1;
	
	private String buildingValue_v1; 
	
	private String reconstructionValueOfTheBuilding_v1;
	
	private String propertyCompletionStatus_v1;
	
	// Add for prop val 1
	
	private String isPhysicalInspection_v1;
	
	private String lastPhysicalInspectDate_v1;
	
    private String nextPhysicalInspectDate_v1;
	
    private String physicalInspectionFreqUnit_v1;
    
    private String remarksProperty_v1;
    
    // Val 2
    
    private String waiver;
    
	private String deferral;
	
	private String deferralId;
	
	private String revaluation_v2_add;
	
	private String preValDate_v2;
	
	private String valcreationdate_v2 ;
	
	private String totalPropertyAmount_v2;
	
	private String valuationDate_v2;
	
	private String valuatorCompany_v2;
	
	private String categoryOfLandUse_v2;
	
	private String developerName_v2;
	
	private String country_v2;
	
	private String region_v2;
	
	private String locationState_v2;
	
	private String nearestCity_v2;
	
	private String pinCode_v2;
	
	private String landArea_v2;
	
	private String landAreaUOM_v2;
	
	private String inSqftLandArea_v2;
	
	private String builtupArea_v2;
	
	private String builtupAreaUOM_v2;
	
	private String inSqftBuiltupArea_v2;
	
	private String landValue_v2;
	
	private String buildingValue_v2;
	
	private String reconstructionValueOfTheBuilding_v2;
	
	private String propertyCompletionStatus_v2;
	
	private String isPhysicalInspection_v2;
	
	private String lastPhysicalInspectDate_v2;
	
    private String nextPhysicalInspectDate_v2;
    
    private String physicalInspectionFreqUnit_v2;
    
    private String remarksProperty_v2;
    
    private String propertyTypeLabel;
    
    //Val 3
    
    public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPropertyUsage() {
		return propertyUsage;
	}

	public void setPropertyUsage(String propertyUsage) {
		this.propertyUsage = propertyUsage;
	}

	public String getSalePurchaseValue() {
		return salePurchaseValue;
	}

	public void setSalePurchaseValue(String salePurchaseValue) {
		this.salePurchaseValue = salePurchaseValue;
	}

	public String getDateOfReceiptTitleDeed() {
		return dateOfReceiptTitleDeed;
	}

	public void setDateOfReceiptTitleDeed(String dateOfReceiptTitleDeed) {
		this.dateOfReceiptTitleDeed = dateOfReceiptTitleDeed;
	}

	public String getPreviousMortCreationDate() {
		return previousMortCreationDate;
	}

	public void setPreviousMortCreationDate(String previousMortCreationDate) {
		this.previousMortCreationDate = previousMortCreationDate;
	}

	public String getSalePurchaseDate() {
		return salePurchaseDate;
	}

	public void setSalePurchaseDate(String salePurchaseDate) {
		this.salePurchaseDate = salePurchaseDate;
	}

	public String getMortgageCreExtDateAdd() {
		return mortgageCreExtDateAdd;
	}

	public void setMortgageCreExtDateAdd(String mortgageCreExtDateAdd) {
		this.mortgageCreExtDateAdd = mortgageCreExtDateAdd;
	}

	public String getLegalAuditDate() {
		return legalAuditDate;
	}

	public void setLegalAuditDate(String legalAuditDate) {
		this.legalAuditDate = legalAuditDate;
	}

	public String getInterveingPeriSearchDate() {
		return interveingPeriSearchDate;
	}

	public void setInterveingPeriSearchDate(String interveingPeriSearchDate) {
		this.interveingPeriSearchDate = interveingPeriSearchDate;
	}

	public String getTypeOfMargage() {
		return typeOfMargage;
	}

	public void setTypeOfMargage(String typeOfMargage) {
		this.typeOfMargage = typeOfMargage;
	}

	public String getDocumentReceived() {
		return documentReceived;
	}

	public void setDocumentReceived(String documentReceived) {
		this.documentReceived = documentReceived;
	}

	public String getMorgageCreatedBy() {
		return morgageCreatedBy;
	}

	public void setMorgageCreatedBy(String morgageCreatedBy) {
		this.morgageCreatedBy = morgageCreatedBy;
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

	public String getMortageRegisteredRef() {
		return mortageRegisteredRef;
	}

	public void setMortageRegisteredRef(String mortageRegisteredRef) {
		this.mortageRegisteredRef = mortageRegisteredRef;
	}

	public String getAdvocateLawyerName() {
		return advocateLawyerName;
	}

	public void setAdvocateLawyerName(String advocateLawyerName) {
		this.advocateLawyerName = advocateLawyerName;
	}

	public String getDevGrpCo() {
		return devGrpCo;
	}

	public void setDevGrpCo(String devGrpCo) {
		this.devGrpCo = devGrpCo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLotNumberPrefix() {
		return lotNumberPrefix;
	}

	public void setLotNumberPrefix(String lotNumberPrefix) {
		this.lotNumberPrefix = lotNumberPrefix;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getPropertyLotLocation() {
		return propertyLotLocation;
	}

	public void setPropertyLotLocation(String propertyLotLocation) {
		this.propertyLotLocation = propertyLotLocation;
	}

	public String getOtherCity() {
		return otherCity;
	}

	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
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

	public List<AddtionalDocumentFacilityDetailsRestDTO> getAddDocFacDetailsList() {
		return addDocFacDetailsList;
	}

	public void setAddDocFacDetailsList(
			List<AddtionalDocumentFacilityDetailsRestDTO> addDocFacDetailsList) {
		this.addDocFacDetailsList = addDocFacDetailsList;
	}

	public String getEnvRiskyStatus() {
		return envRiskyStatus;
	}

	public void setEnvRiskyStatus(String envRiskyStatus) {
		this.envRiskyStatus = envRiskyStatus;
	}

	public String getEnvRiskyDate() {
		return envRiskyDate;
	}

	public void setEnvRiskyDate(String envRiskyDate) {
		this.envRiskyDate = envRiskyDate;
	}

	public String getTsrDate() {
		return tsrDate;
	}

	public void setTsrDate(String tsrDate) {
		this.tsrDate = tsrDate;
	}

	public String getTsrFrequency() {
		return tsrFrequency;
	}

	public void setTsrFrequency(String tsrFrequency) {
		this.tsrFrequency = tsrFrequency;
	}

	public String getNextTsrDate() {
		return nextTsrDate;
	}

	public void setNextTsrDate(String nextTsrDate) {
		this.nextTsrDate = nextTsrDate;
	}

	public String getCersiaRegistrationDate() {
		return cersiaRegistrationDate;
	}

	public void setCersiaRegistrationDate(String cersiaRegistrationDate) {
		this.cersiaRegistrationDate = cersiaRegistrationDate;
	}

	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public String getEnvRiskyRemarks() {
		return envRiskyRemarks;
	}

	public void setEnvRiskyRemarks(String envRiskyRemarks) {
		this.envRiskyRemarks = envRiskyRemarks;
	}

	public String getRevalOverride() {
		return revalOverride;
	}

	public void setRevalOverride(String revalOverride) {
		this.revalOverride = revalOverride;
	}

	public String getRevaluation_v1_add() {
		return revaluation_v1_add;
	}

	public void setRevaluation_v1_add(String revaluation_v1_add) {
		this.revaluation_v1_add = revaluation_v1_add;
	}

	public String getPreValDate_v1() {
		return preValDate_v1;
	}

	public void setPreValDate_v1(String preValDate_v1) {
		this.preValDate_v1 = preValDate_v1;
	}

	public String getValcreationdate_v1() {
		return valcreationdate_v1;
	}

	public void setValcreationdate_v1(String valcreationdate_v1) {
		this.valcreationdate_v1 = valcreationdate_v1;
	}

	public String getTotalPropertyAmount_v1() {
		return totalPropertyAmount_v1;
	}

	public void setTotalPropertyAmount_v1(String totalPropertyAmount_v1) {
		this.totalPropertyAmount_v1 = totalPropertyAmount_v1;
	}

	public String getValuationDate_v1() {
		return valuationDate_v1;
	}

	public void setValuationDate_v1(String valuationDate_v1) {
		this.valuationDate_v1 = valuationDate_v1;
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

	public String getLandArea_v1() {
		return landArea_v1;
	}

	public void setLandArea_v1(String landArea_v1) {
		this.landArea_v1 = landArea_v1;
	}

	public String getLandAreaUOM_v1() {
		return landAreaUOM_v1;
	}

	public void setLandAreaUOM_v1(String landAreaUOM_v1) {
		this.landAreaUOM_v1 = landAreaUOM_v1;
	}

	public String getInSqftLandArea_v1() {
		return inSqftLandArea_v1;
	}

	public void setInSqftLandArea_v1(String inSqftLandArea_v1) {
		this.inSqftLandArea_v1 = inSqftLandArea_v1;
	}

	public String getBuiltupArea_v1() {
		return builtupArea_v1;
	}

	public void setBuiltupArea_v1(String builtupArea_v1) {
		this.builtupArea_v1 = builtupArea_v1;
	}

	public String getBuiltupAreaUOM_v1() {
		return builtupAreaUOM_v1;
	}

	public void setBuiltupAreaUOM_v1(String builtupAreaUOM_v1) {
		this.builtupAreaUOM_v1 = builtupAreaUOM_v1;
	}

	public String getInSqftBuiltupArea_v1() {
		return inSqftBuiltupArea_v1;
	}

	public void setInSqftBuiltupArea_v1(String inSqftBuiltupArea_v1) {
		this.inSqftBuiltupArea_v1 = inSqftBuiltupArea_v1;
	}

	public String getLandValue_v1() {
		return landValue_v1;
	}

	public void setLandValue_v1(String landValue_v1) {
		this.landValue_v1 = landValue_v1;
	}

	public String getBuildingValue_v1() {
		return buildingValue_v1;
	}

	public void setBuildingValue_v1(String buildingValue_v1) {
		this.buildingValue_v1 = buildingValue_v1;
	}

	public String getReconstructionValueOfTheBuilding_v1() {
		return reconstructionValueOfTheBuilding_v1;
	}

	public void setReconstructionValueOfTheBuilding_v1(
			String reconstructionValueOfTheBuilding_v1) {
		this.reconstructionValueOfTheBuilding_v1 = reconstructionValueOfTheBuilding_v1;
	}

	public String getPropertyCompletionStatus_v1() {
		return propertyCompletionStatus_v1;
	}

	public void setPropertyCompletionStatus_v1(String propertyCompletionStatus_v1) {
		this.propertyCompletionStatus_v1 = propertyCompletionStatus_v1;
	}

	public String getIsPhysicalInspection_v1() {
		return isPhysicalInspection_v1;
	}

	public void setIsPhysicalInspection_v1(String isPhysicalInspection_v1) {
		this.isPhysicalInspection_v1 = isPhysicalInspection_v1;
	}

	public String getLastPhysicalInspectDate_v1() {
		return lastPhysicalInspectDate_v1;
	}

	public void setLastPhysicalInspectDate_v1(String lastPhysicalInspectDate_v1) {
		this.lastPhysicalInspectDate_v1 = lastPhysicalInspectDate_v1;
	}

	public String getNextPhysicalInspectDate_v1() {
		return nextPhysicalInspectDate_v1;
	}

	public void setNextPhysicalInspectDate_v1(String nextPhysicalInspectDate_v1) {
		this.nextPhysicalInspectDate_v1 = nextPhysicalInspectDate_v1;
	}

	public String getPhysicalInspectionFreqUnit_v1() {
		return physicalInspectionFreqUnit_v1;
	}

	public void setPhysicalInspectionFreqUnit_v1(
			String physicalInspectionFreqUnit_v1) {
		this.physicalInspectionFreqUnit_v1 = physicalInspectionFreqUnit_v1;
	}

	public String getRemarksProperty_v1() {
		return remarksProperty_v1;
	}

	public void setRemarksProperty_v1(String remarksProperty_v1) {
		this.remarksProperty_v1 = remarksProperty_v1;
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

	public String getRevaluation_v2_add() {
		return revaluation_v2_add;
	}

	public void setRevaluation_v2_add(String revaluation_v2_add) {
		this.revaluation_v2_add = revaluation_v2_add;
	}

	public String getPreValDate_v2() {
		return preValDate_v2;
	}

	public void setPreValDate_v2(String preValDate_v2) {
		this.preValDate_v2 = preValDate_v2;
	}

	public String getValcreationdate_v2() {
		return valcreationdate_v2;
	}

	public void setValcreationdate_v2(String valcreationdate_v2) {
		this.valcreationdate_v2 = valcreationdate_v2;
	}

	public String getTotalPropertyAmount_v2() {
		return totalPropertyAmount_v2;
	}

	public void setTotalPropertyAmount_v2(String totalPropertyAmount_v2) {
		this.totalPropertyAmount_v2 = totalPropertyAmount_v2;
	}

	public String getValuationDate_v2() {
		return valuationDate_v2;
	}

	public void setValuationDate_v2(String valuationDate_v2) {
		this.valuationDate_v2 = valuationDate_v2;
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

	public String getLandArea_v2() {
		return landArea_v2;
	}

	public void setLandArea_v2(String landArea_v2) {
		this.landArea_v2 = landArea_v2;
	}

	public String getLandAreaUOM_v2() {
		return landAreaUOM_v2;
	}

	public void setLandAreaUOM_v2(String landAreaUOM_v2) {
		this.landAreaUOM_v2 = landAreaUOM_v2;
	}

	public String getInSqftLandArea_v2() {
		return inSqftLandArea_v2;
	}

	public void setInSqftLandArea_v2(String inSqftLandArea_v2) {
		this.inSqftLandArea_v2 = inSqftLandArea_v2;
	}

	public String getBuiltupArea_v2() {
		return builtupArea_v2;
	}

	public void setBuiltupArea_v2(String builtupArea_v2) {
		this.builtupArea_v2 = builtupArea_v2;
	}

	public String getBuiltupAreaUOM_v2() {
		return builtupAreaUOM_v2;
	}

	public void setBuiltupAreaUOM_v2(String builtupAreaUOM_v2) {
		this.builtupAreaUOM_v2 = builtupAreaUOM_v2;
	}

	public String getInSqftBuiltupArea_v2() {
		return inSqftBuiltupArea_v2;
	}

	public void setInSqftBuiltupArea_v2(String inSqftBuiltupArea_v2) {
		this.inSqftBuiltupArea_v2 = inSqftBuiltupArea_v2;
	}

	public String getLandValue_v2() {
		return landValue_v2;
	}

	public void setLandValue_v2(String landValue_v2) {
		this.landValue_v2 = landValue_v2;
	}

	public String getBuildingValue_v2() {
		return buildingValue_v2;
	}

	public void setBuildingValue_v2(String buildingValue_v2) {
		this.buildingValue_v2 = buildingValue_v2;
	}

	public String getReconstructionValueOfTheBuilding_v2() {
		return reconstructionValueOfTheBuilding_v2;
	}

	public void setReconstructionValueOfTheBuilding_v2(
			String reconstructionValueOfTheBuilding_v2) {
		this.reconstructionValueOfTheBuilding_v2 = reconstructionValueOfTheBuilding_v2;
	}

	public String getPropertyCompletionStatus_v2() {
		return propertyCompletionStatus_v2;
	}

	public void setPropertyCompletionStatus_v2(String propertyCompletionStatus_v2) {
		this.propertyCompletionStatus_v2 = propertyCompletionStatus_v2;
	}

	public String getIsPhysicalInspection_v2() {
		return isPhysicalInspection_v2;
	}

	public void setIsPhysicalInspection_v2(String isPhysicalInspection_v2) {
		this.isPhysicalInspection_v2 = isPhysicalInspection_v2;
	}

	public String getLastPhysicalInspectDate_v2() {
		return lastPhysicalInspectDate_v2;
	}

	public void setLastPhysicalInspectDate_v2(String lastPhysicalInspectDate_v2) {
		this.lastPhysicalInspectDate_v2 = lastPhysicalInspectDate_v2;
	}

	public String getNextPhysicalInspectDate_v2() {
		return nextPhysicalInspectDate_v2;
	}

	public void setNextPhysicalInspectDate_v2(String nextPhysicalInspectDate_v2) {
		this.nextPhysicalInspectDate_v2 = nextPhysicalInspectDate_v2;
	}

	public String getPhysicalInspectionFreqUnit_v2() {
		return physicalInspectionFreqUnit_v2;
	}

	public void setPhysicalInspectionFreqUnit_v2(
			String physicalInspectionFreqUnit_v2) {
		this.physicalInspectionFreqUnit_v2 = physicalInspectionFreqUnit_v2;
	}

	public String getRemarksProperty_v2() {
		return remarksProperty_v2;
	}

	public void setRemarksProperty_v2(String remarksProperty_v2) {
		this.remarksProperty_v2 = remarksProperty_v2;
	}

	public String getRevaluation_v3_add() {
		return revaluation_v3_add;
	}

	public void setRevaluation_v3_add(String revaluation_v3_add) {
		this.revaluation_v3_add = revaluation_v3_add;
	}

	public String getPreValDate_v3() {
		return preValDate_v3;
	}

	public void setPreValDate_v3(String preValDate_v3) {
		this.preValDate_v3 = preValDate_v3;
	}

	public String getValcreationdate_v3() {
		return valcreationdate_v3;
	}

	public void setValcreationdate_v3(String valcreationdate_v3) {
		this.valcreationdate_v3 = valcreationdate_v3;
	}

	public String getTotalPropertyAmount_v3() {
		return totalPropertyAmount_v3;
	}

	public void setTotalPropertyAmount_v3(String totalPropertyAmount_v3) {
		this.totalPropertyAmount_v3 = totalPropertyAmount_v3;
	}

	public String getValuationDate_v3() {
		return valuationDate_v3;
	}

	public void setValuationDate_v3(String valuationDate_v3) {
		this.valuationDate_v3 = valuationDate_v3;
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

	public String getLandArea_v3() {
		return landArea_v3;
	}

	public void setLandArea_v3(String landArea_v3) {
		this.landArea_v3 = landArea_v3;
	}

	public String getLandAreaUOM_v3() {
		return landAreaUOM_v3;
	}

	public void setLandAreaUOM_v3(String landAreaUOM_v3) {
		this.landAreaUOM_v3 = landAreaUOM_v3;
	}

	public String getInSqftLandArea_v3() {
		return inSqftLandArea_v3;
	}

	public void setInSqftLandArea_v3(String inSqftLandArea_v3) {
		this.inSqftLandArea_v3 = inSqftLandArea_v3;
	}

	public String getBuiltupArea_v3() {
		return builtupArea_v3;
	}

	public void setBuiltupArea_v3(String builtupArea_v3) {
		this.builtupArea_v3 = builtupArea_v3;
	}

	public String getBuiltupAreaUOM_v3() {
		return builtupAreaUOM_v3;
	}

	public void setBuiltupAreaUOM_v3(String builtupAreaUOM_v3) {
		this.builtupAreaUOM_v3 = builtupAreaUOM_v3;
	}

	public String getInSqftBuiltupArea_v3() {
		return inSqftBuiltupArea_v3;
	}

	public void setInSqftBuiltupArea_v3(String inSqftBuiltupArea_v3) {
		this.inSqftBuiltupArea_v3 = inSqftBuiltupArea_v3;
	}

	public String getLandValue_v3() {
		return landValue_v3;
	}

	public void setLandValue_v3(String landValue_v3) {
		this.landValue_v3 = landValue_v3;
	}

	public String getBuildingValue_v3() {
		return buildingValue_v3;
	}

	public void setBuildingValue_v3(String buildingValue_v3) {
		this.buildingValue_v3 = buildingValue_v3;
	}

	public String getReconstructionValueOfTheBuilding_v3() {
		return reconstructionValueOfTheBuilding_v3;
	}

	public void setReconstructionValueOfTheBuilding_v3(
			String reconstructionValueOfTheBuilding_v3) {
		this.reconstructionValueOfTheBuilding_v3 = reconstructionValueOfTheBuilding_v3;
	}

	public String getPropertyCompletionStatus_v3() {
		return propertyCompletionStatus_v3;
	}

	public void setPropertyCompletionStatus_v3(String propertyCompletionStatus_v3) {
		this.propertyCompletionStatus_v3 = propertyCompletionStatus_v3;
	}

	public String getIsPhysicalInspection_v3() {
		return isPhysicalInspection_v3;
	}

	public void setIsPhysicalInspection_v3(String isPhysicalInspection_v3) {
		this.isPhysicalInspection_v3 = isPhysicalInspection_v3;
	}

	public String getLastPhysicalInspectDate_v3() {
		return lastPhysicalInspectDate_v3;
	}

	public void setLastPhysicalInspectDate_v3(String lastPhysicalInspectDate_v3) {
		this.lastPhysicalInspectDate_v3 = lastPhysicalInspectDate_v3;
	}

	public String getNextPhysicalInspectDate_v3() {
		return nextPhysicalInspectDate_v3;
	}

	public void setNextPhysicalInspectDate_v3(String nextPhysicalInspectDate_v3) {
		this.nextPhysicalInspectDate_v3 = nextPhysicalInspectDate_v3;
	}

	public String getPhysicalInspectionFreqUnit_v3() {
		return physicalInspectionFreqUnit_v3;
	}

	public void setPhysicalInspectionFreqUnit_v3(
			String physicalInspectionFreqUnit_v3) {
		this.physicalInspectionFreqUnit_v3 = physicalInspectionFreqUnit_v3;
	}

	public String getRemarksProperty_v3() {
		return remarksProperty_v3;
	}

	public void setRemarksProperty_v3(String remarksProperty_v3) {
		this.remarksProperty_v3 = remarksProperty_v3;
	}

	private String revaluation_v3_add;
    
    private String preValDate_v3;
    
    private String valcreationdate_v3 ;
	
	private String totalPropertyAmount_v3;
	
	private String valuationDate_v3;
	
	private String valuatorCompany_v3;
	
	private String categoryOfLandUse_v3;
	
	private String developerName_v3;
	
	private String country_v3;
	
	private String region_v3;
	
	private String locationState_v3;
	
	private String nearestCity_v3;
	
	private String pinCode_v3;
	
	private String landArea_v3;
	
	private String landAreaUOM_v3;
	
	private String inSqftLandArea_v3;
	
	private String builtupArea_v3;
	
	private String builtupAreaUOM_v3;
	
	private String inSqftBuiltupArea_v3;
	
	private String landValue_v3;
	
	private String buildingValue_v3;
	
	private String reconstructionValueOfTheBuilding_v3;
	
	private String propertyCompletionStatus_v3;
	
	private String isPhysicalInspection_v3;
	
	private String lastPhysicalInspectDate_v3;
	
    private String nextPhysicalInspectDate_v3;
    
    private String physicalInspectionFreqUnit_v3;
    
    private String remarksProperty_v3;


	public String getIsAdDocFac() {
		return isAdDocFac;
	}

	public void setIsAdDocFac(String isAdDocFac) {
		this.isAdDocFac = isAdDocFac;
	}

	public String getPropertyTypeLabel() {
		return propertyTypeLabel;
	}

	public void setPropertyTypeLabel(String propertyTypeLabel) {
		this.propertyTypeLabel = propertyTypeLabel;
	}
    
    
}

