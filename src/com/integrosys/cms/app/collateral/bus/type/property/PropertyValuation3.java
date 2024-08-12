package com.integrosys.cms.app.collateral.bus.type.property;

import java.util.Date;

public class PropertyValuation3 implements IPropertyValuation3 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyValuation3() {
	}
	
    private long id;
    private long collateralId;	
    private String subTypeName;	
    private String typeName;
    private String sciRefNote;
    private String securityCurrency;	
    private String secPriority;
    private String monitorProcess;
    private String monitorFrequency;
    private String secLocation;
    private String secOrganization;
    private String collateralCode;
    private String revalFrequency;
    private Date nextValuationDate;
    private String changeType;
    private String otherBankCharge;
    private String secOwnership;
    private String ownerOfProperty;
    private String thirdPartyEntity;
    private String cinThirdParty;
    private String cersaiTrxRefNo;
    private String cersaiSecurityInterestId;
    private String cersaiAssetId;
    private Date dateCersaiRegi;
  
    private String cersaiId;
    private Date saleDeedPurchaseDate;
    private String thirdPartyAddress;
    private String thirdPartyState;
    private String thirdPartyCity;
    private String thirdPartyPincode;
    private String propertyId;
    private String descOfAsset;
    private String propertyType;
    
    private String salePurchaseValue;
    private Date salePurchaseDate;
    private String mortgageType;
    private String mortgagecretaedBy;
    private String documentReceived;
    private String documentBlock;
    private String binNumber;
    private String claim;
    private String claimType;

    private String advocateLawyerName;
    private String developerGroupCompany;
    private String lotNo;
    private String lotNumberPrefix;
    private String propertyLotLocation;
    private String otherCity;
    private String propertyAddress;
    private String propertyAddress2;
    private String propertyAddress3;
    private String propertyAddress4;
    private String propertyAddress5;
    private String propertyAddress6;
    
    private String projectName;
    private Date valuationDateV3;
    private String valuatorCompanyV3;
    private String totalPropertyAmontV3;
    private String categoryOfLandUseV3;
    private String developerNameV3;
    private String countryV3;
    private String regionV3;
    private String stateV3;
    private String nearestCityV3;
    private String pincodeV3;
    
    private double landAreaV3;
    private String landAreaUOMV3;
    private double inSqfLandAreaV3;
    private double buildupAreaV3;
    private String buildupAreaUOMV3;
    private double inSqfbuildupAreaV3;
    private char propertyCompletionStatusV3;
    private double landValueIRBV3;
    private double buildingValueIRBV3;
    private double reconstructionValueIRBV3;
    private Boolean isPhyInspectionV3;
    private String phyInspectionFreqUnitV3;
    private Date lastPhyInspectionDateV3;
    private Date nextPhyInspectionDateV3;
    private String remarksPropertyV3;
    private String environmentRiskyStatus;
    private Date environmentRiskDate;
    private Date tsrDate;
    private Date nextTsrDate;
    private String tsrFrequency;
    private Date cersaiRegDate;
    private String constitution;
    private String environmentRiskRemark;
    private Date legalAuditDate;
    private Date interveingPeriSerachDate;
    private Date dateOfReceiptTitleDeed;
    private Date valCreationDateV3;
	private Date previousMortCreationDate;
	private String propertyUsage;
	private String mortageRegisteredRef;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSciRefNote() {
		return sciRefNote;
	}
	public void setSciRefNote(String sciRefNote) {
		this.sciRefNote = sciRefNote;
	}
	public String getSecurityCurrency() {
		return securityCurrency;
	}
	public void setSecurityCurrency(String securityCurrency) {
		this.securityCurrency = securityCurrency;
	}
	public String getSecPriority() {
		return secPriority;
	}
	public void setSecPriority(String secPriority) {
		this.secPriority = secPriority;
	}
	public String getMonitorProcess() {
		return monitorProcess;
	}
	public void setMonitorProcess(String monitorProcess) {
		this.monitorProcess = monitorProcess;
	}
	public String getMonitorFrequency() {
		return monitorFrequency;
	}
	public void setMonitorFrequency(String monitorFrequency) {
		this.monitorFrequency = monitorFrequency;
	}
	public String getSecLocation() {
		return secLocation;
	}
	public void setSecLocation(String secLocation) {
		this.secLocation = secLocation;
	}
	public String getSecOrganization() {
		return secOrganization;
	}
	public void setSecOrganization(String secOrganization) {
		this.secOrganization = secOrganization;
	}
	public String getCollateralCode() {
		return collateralCode;
	}
	public void setCollateralCode(String collateralCode) {
		this.collateralCode = collateralCode;
	}
	public String getRevalFrequency() {
		return revalFrequency;
	}
	public void setRevalFrequency(String revalFrequency) {
		this.revalFrequency = revalFrequency;
	}
	public Date getNextValuationDate() {
		return nextValuationDate;
	}
	public void setNextValuationDate(Date nextValuationDate) {
		this.nextValuationDate = nextValuationDate;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getOtherBankCharge() {
		return otherBankCharge;
	}
	public void setOtherBankCharge(String otherBankCharge) {
		this.otherBankCharge = otherBankCharge;
	}
	public String getSecOwnership() {
		return secOwnership;
	}
	public void setSecOwnership(String secOwnership) {
		this.secOwnership = secOwnership;
	}
	public String getOwnerOfProperty() {
		return ownerOfProperty;
	}
	public void setOwnerOfProperty(String ownerOfProperty) {
		this.ownerOfProperty = ownerOfProperty;
	}
	public String getThirdPartyEntity() {
		return thirdPartyEntity;
	}
	public void setThirdPartyEntity(String thirdPartyEntity) {
		this.thirdPartyEntity = thirdPartyEntity;
	}
	public String getCinThirdParty() {
		return cinThirdParty;
	}
	public void setCinThirdParty(String cinThirdParty) {
		this.cinThirdParty = cinThirdParty;
	}
	public String getCersaiTrxRefNo() {
		return cersaiTrxRefNo;
	}
	public void setCersaiTrxRefNo(String cersaiTrxRefNo) {
		this.cersaiTrxRefNo = cersaiTrxRefNo;
	}
	public String getCersaiSecurityInterestId() {
		return cersaiSecurityInterestId;
	}
	public void setCersaiSecurityInterestId(String cersaiSecurityInterestId) {
		this.cersaiSecurityInterestId = cersaiSecurityInterestId;
	}
	public String getCersaiAssetId() {
		return cersaiAssetId;
	}
	public void setCersaiAssetId(String cersaiAssetId) {
		this.cersaiAssetId = cersaiAssetId;
	}
	public Date getDateCersaiRegi() {
		return dateCersaiRegi;
	}
	public void setDateCersaiRegi(Date dateCersaiRegi) {
		this.dateCersaiRegi = dateCersaiRegi;
	}
	public Date getCersaiRegDate() {
		return cersaiRegDate;
	}
	public void setCersaiRegDate(Date cersaiRegDate) {
		this.cersaiRegDate = cersaiRegDate;
	}
	public String getCersaiId() {
		return cersaiId;
	}
	public void setCersaiId(String cersaiId) {
		this.cersaiId = cersaiId;
	}
	public Date getSaleDeedPurchaseDate() {
		return saleDeedPurchaseDate;
	}
	public void setSaleDeedPurchaseDate(Date saleDeedPurchaseDate) {
		this.saleDeedPurchaseDate = saleDeedPurchaseDate;
	}
	public String getThirdPartyAddress() {
		return thirdPartyAddress;
	}
	public void setThirdPartyAddress(String thirdPartyAddress) {
		this.thirdPartyAddress = thirdPartyAddress;
	}
	public String getThirdPartyState() {
		return thirdPartyState;
	}
	public void setThirdPartyState(String thirdPartyState) {
		this.thirdPartyState = thirdPartyState;
	}
	public String getThirdPartyCity() {
		return thirdPartyCity;
	}
	public void setThirdPartyCity(String thirdPartyCity) {
		this.thirdPartyCity = thirdPartyCity;
	}
	public String getThirdPartyPincode() {
		return thirdPartyPincode;
	}
	public void setThirdPartyPincode(String thirdPartyPincode) {
		this.thirdPartyPincode = thirdPartyPincode;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getDescOfAsset() {
		return descOfAsset;
	}
	public void setDescOfAsset(String descOfAsset) {
		this.descOfAsset = descOfAsset;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getSalePurchaseValue() {
		return salePurchaseValue;
	}
	public void setSalePurchaseValue(String salePurchaseValue) {
		this.salePurchaseValue = salePurchaseValue;
	}
	public Date getSalePurchaseDate() {
		return salePurchaseDate;
	}
	public void setSalePurchaseDate(Date salePurchaseDate) {
		this.salePurchaseDate = salePurchaseDate;
	}
	public String getMortgageType() {
		return mortgageType;
	}
	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}
	public String getMortgagecretaedBy() {
		return mortgagecretaedBy;
	}
	public void setMortgagecretaedBy(String mortgagecretaedBy) {
		this.mortgagecretaedBy = mortgagecretaedBy;
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
	public String getAdvocateLawyerName() {
		return advocateLawyerName;
	}
	public void setAdvocateLawyerName(String advocateLawyerName) {
		this.advocateLawyerName = advocateLawyerName;
	}
	public String getDeveloperGroupCompany() {
		return developerGroupCompany;
	}
	public void setDeveloperGroupCompany(String developerGroupCompany) {
		this.developerGroupCompany = developerGroupCompany;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getLotNumberPrefix() {
		return lotNumberPrefix;
	}
	public void setLotNumberPrefix(String lotNumberPrefix) {
		this.lotNumberPrefix = lotNumberPrefix;
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getValuationDateV3() {
		return valuationDateV3;
	}
	public void setValuationDateV3(Date valuationDateV3) {
		this.valuationDateV3 = valuationDateV3;
	}
	public String getValuatorCompanyV3() {
		return valuatorCompanyV3;
	}
	public void setValuatorCompanyV3(String valuatorCompanyV3) {
		this.valuatorCompanyV3 = valuatorCompanyV3;
	}
	public String getTotalPropertyAmontV3() {
		return totalPropertyAmontV3;
	}
	public void setTotalPropertyAmontV3(String totalPropertyAmontV3) {
		this.totalPropertyAmontV3 = totalPropertyAmontV3;
	}
	public String getCategoryOfLandUseV3() {
		return categoryOfLandUseV3;
	}
	public void setCategoryOfLandUseV3(String categoryOfLandUseV3) {
		this.categoryOfLandUseV3 = categoryOfLandUseV3;
	}
	public String getDeveloperNameV3() {
		return developerNameV3;
	}
	public void setDeveloperNameV3(String developerNameV3) {
		this.developerNameV3 = developerNameV3;
	}
	public String getCountryV3() {
		return countryV3;
	}
	public void setCountryV3(String countryV3) {
		this.countryV3 = countryV3;
	}
	public String getRegionV3() {
		return regionV3;
	}
	public void setRegionV3(String regionV3) {
		this.regionV3 = regionV3;
	}
	public String getStateV3() {
		return stateV3;
	}
	public void setStateV3(String stateV3) {
		this.stateV3 = stateV3;
	}
	public String getNearestCityV3() {
		return nearestCityV3;
	}
	public void setNearestCityV3(String nearestCityV3) {
		this.nearestCityV3 = nearestCityV3;
	}
	public String getPincodeV3() {
		return pincodeV3;
	}
	public void setPincodeV3(String pincodeV3) {
		this.pincodeV3 = pincodeV3;
	}
	public double getLandAreaV3() {
		return landAreaV3;
	}
	public void setLandAreaV3(double landAreaV3) {
		this.landAreaV3 = landAreaV3;
	}
	public String getLandAreaUOMV3() {
		return landAreaUOMV3;
	}
	public void setLandAreaUOMV3(String landAreaUOMV3) {
		this.landAreaUOMV3 = landAreaUOMV3;
	}
	public double getInSqfLandAreaV3() {
		return inSqfLandAreaV3;
	}
	public void setInSqfLandAreaV3(double inSqfLandAreaV3) {
		this.inSqfLandAreaV3 = inSqfLandAreaV3;
	}
	public double getBuildupAreaV3() {
		return buildupAreaV3;
	}
	public void setBuildupAreaV3(double buildupAreaV3) {
		this.buildupAreaV3 = buildupAreaV3;
	}
	public String getBuildupAreaUOMV3() {
		return buildupAreaUOMV3;
	}
	public void setBuildupAreaUOMV3(String buildupAreaUOMV3) {
		this.buildupAreaUOMV3 = buildupAreaUOMV3;
	}
	public double getInSqfbuildupAreaV3() {
		return inSqfbuildupAreaV3;
	}
	public void setInSqfbuildupAreaV3(double inSqfbuildupAreaV3) {
		this.inSqfbuildupAreaV3 = inSqfbuildupAreaV3;
	}
	public char getPropertyCompletionStatusV3() {
		return propertyCompletionStatusV3;
	}
	public void setPropertyCompletionStatusV3(char propertyCompletionStatusV3) {
		this.propertyCompletionStatusV3 = propertyCompletionStatusV3;
	}
	public double getLandValueIRBV3() {
		return landValueIRBV3;
	}
	public void setLandValueIRBV3(double landValueIRBV3) {
		this.landValueIRBV3 = landValueIRBV3;
	}
	public double getBuildingValueIRBV3() {
		return buildingValueIRBV3;
	}
	public void setBuildingValueIRBV3(double buildingValueIRBV3) {
		this.buildingValueIRBV3 = buildingValueIRBV3;
	}
	public double getReconstructionValueIRBV3() {
		return reconstructionValueIRBV3;
	}
	public void setReconstructionValueIRBV3(double reconstructionValueIRBV3) {
		this.reconstructionValueIRBV3 = reconstructionValueIRBV3;
	}
	public Boolean getIsPhyInspectionV3() {
		return isPhyInspectionV3;
	}
	public void setIsPhyInspectionV3(Boolean isPhyInspectionV3) {
		this.isPhyInspectionV3 = isPhyInspectionV3;
	}
	public String getPhyInspectionFreqUnitV3() {
		return phyInspectionFreqUnitV3;
	}
	public void setPhyInspectionFreqUnitV3(String phyInspectionFreqUnitV3) {
		this.phyInspectionFreqUnitV3 = phyInspectionFreqUnitV3;
	}
	public Date getLastPhyInspectionDateV3() {
		return lastPhyInspectionDateV3;
	}
	public void setLastPhyInspectionDateV3(Date lastPhyInspectionDateV3) {
		this.lastPhyInspectionDateV3 = lastPhyInspectionDateV3;
	}
	public Date getNextPhyInspectionDateV3() {
		return nextPhyInspectionDateV3;
	}
	public void setNextPhyInspectionDateV3(Date nextPhyInspectionDateV3) {
		this.nextPhyInspectionDateV3 = nextPhyInspectionDateV3;
	}
	public String getRemarksPropertyV3() {
		return remarksPropertyV3;
	}
	public void setRemarksPropertyV3(String remarksPropertyV3) {
		this.remarksPropertyV3 = remarksPropertyV3;
	}
	public String getEnvironmentRiskyStatus() {
		return environmentRiskyStatus;
	}
	public void setEnvironmentRiskyStatus(String environmentRiskyStatus) {
		this.environmentRiskyStatus = environmentRiskyStatus;
	}
	public Date getEnvironmentRiskDate() {
		return environmentRiskDate;
	}
	public void setEnvironmentRiskDate(Date environmentRiskDate) {
		this.environmentRiskDate = environmentRiskDate;
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
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getEnvironmentRiskRemark() {
		return environmentRiskRemark;
	}
	public void setEnvironmentRiskRemark(String environmentRiskRemark) {
		this.environmentRiskRemark = environmentRiskRemark;
	}
	public Date getLegalAuditDate() {
		return legalAuditDate;
	}
	public void setLegalAuditDate(Date legalAuditDate) {
		this.legalAuditDate = legalAuditDate;
	}
	public Date getInterveingPeriSerachDate() {
		return interveingPeriSerachDate;
	}
	public void setInterveingPeriSerachDate(Date interveingPeriSerachDate) {
		this.interveingPeriSerachDate = interveingPeriSerachDate;
	}
	public Date getDateOfReceiptTitleDeed() {
		return dateOfReceiptTitleDeed;
	}
	public void setDateOfReceiptTitleDeed(Date dateOfReceiptTitleDeed) {
		this.dateOfReceiptTitleDeed = dateOfReceiptTitleDeed;
	}
	public Date getValCreationDateV3() {
		return valCreationDateV3;
	}
	public void setValCreationDateV3(Date valCreationDateV3) {
		this.valCreationDateV3 = valCreationDateV3;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getPreviousMortCreationDate() {
		// TODO Auto-generated method stub
		return previousMortCreationDate;
	}
	@Override
	public void setPreviousMortCreationDate(Date previousMortCreationDate) {
		this.previousMortCreationDate=previousMortCreationDate;
		
	}
	
	public String getPropertyUsage() {
		return propertyUsage;
	}
	public void setPropertyUsage(String propertyUsage) {
		this.propertyUsage = propertyUsage;
	}
	public String getMortageRegisteredRef() {
		return mortageRegisteredRef;
	}
	public void setMortageRegisteredRef(String mortageRegisteredRef) {
		this.mortageRegisteredRef = mortageRegisteredRef;
	}
	
	
}
