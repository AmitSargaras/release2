package com.integrosys.cms.app.collateral.bus.type.property;

import java.util.Date;

public class PropertyValuation1 implements IPropertyValuation1 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyValuation1() {
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
    private Date valuationDateV1;
    private String valuatorCompanyV1;
    private String totalPropertyAmontV1;
    private String categoryOfLandUseV1;
    private String developerNameV1;
    private String countryV1;
    private String regionV1;
    private String stateV1;
    private String nearestCityV1;
    private String pincodeV1;
    
    private double landAreaV1;
    private String landAreaUOMV1;
    private double inSqfLandAreaV1;
    private double buildupAreaV1;
    private String buildupAreaUOMV1;
    private double inSqfbuildupAreaV1;
    private char propertyCompletionStatusV1;
    private double landValueIRBV1;
    private double buildingValueIRBV1;
    private double reconstructionValueIRBV1;
    private Boolean isPhyInspectionV1;
    private String phyInspectionFreqUnitV1;
    private Date lastPhyInspectionDateV1;
    private Date nextPhyInspectionDateV1;
    private String remarksPropertyV1;
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
    private Date valCreationDateV1;
	private Date previousMortCreationDate;
	
	private String propertyUsage;
	private String mortageRegisteredRef;
    
	private String revalOverride;
	
	public String getRevalOverride() {
		return revalOverride;
	}
	public void setRevalOverride(String revalOverride) {
		this.revalOverride = revalOverride;
	}

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
	public Date getValuationDateV1() {
		return valuationDateV1;
	}
	public void setValuationDateV1(Date valuationDateV1) {
		this.valuationDateV1 = valuationDateV1;
	}
	public String getValuatorCompanyV1() {
		return valuatorCompanyV1;
	}
	public void setValuatorCompanyV1(String valuatorCompanyV1) {
		this.valuatorCompanyV1 = valuatorCompanyV1;
	}
	public String getTotalPropertyAmontV1() {
		return totalPropertyAmontV1;
	}
	public void setTotalPropertyAmontV1(String totalPropertyAmontV1) {
		this.totalPropertyAmontV1 = totalPropertyAmontV1;
	}
	public String getCategoryOfLandUseV1() {
		return categoryOfLandUseV1;
	}
	public void setCategoryOfLandUseV1(String categoryOfLandUseV1) {
		this.categoryOfLandUseV1 = categoryOfLandUseV1;
	}
	public String getDeveloperNameV1() {
		return developerNameV1;
	}
	public void setDeveloperNameV1(String developerNameV1) {
		this.developerNameV1 = developerNameV1;
	}
	public String getCountryV1() {
		return countryV1;
	}
	public void setCountryV1(String countryV1) {
		this.countryV1 = countryV1;
	}
	public String getRegionV1() {
		return regionV1;
	}
	public void setRegionV1(String regionV1) {
		this.regionV1 = regionV1;
	}
	public String getStateV1() {
		return stateV1;
	}
	public void setStateV1(String stateV1) {
		this.stateV1 = stateV1;
	}
	public String getNearestCityV1() {
		return nearestCityV1;
	}
	public void setNearestCityV1(String nearestCityV1) {
		this.nearestCityV1 = nearestCityV1;
	}
	public String getPincodeV1() {
		return pincodeV1;
	}
	public void setPincodeV1(String pincodeV1) {
		this.pincodeV1 = pincodeV1;
	}
	public double getLandAreaV1() {
		return landAreaV1;
	}
	public void setLandAreaV1(double landAreaV1) {
		this.landAreaV1 = landAreaV1;
	}
	public String getLandAreaUOMV1() {
		return landAreaUOMV1;
	}
	public void setLandAreaUOMV1(String landAreaUOMV1) {
		this.landAreaUOMV1 = landAreaUOMV1;
	}
	public double getInSqfLandAreaV1() {
		return inSqfLandAreaV1;
	}
	public void setInSqfLandAreaV1(double inSqfLandAreaV1) {
		this.inSqfLandAreaV1 = inSqfLandAreaV1;
	}
	public double getBuildupAreaV1() {
		return buildupAreaV1;
	}
	public void setBuildupAreaV1(double buildupAreaV1) {
		this.buildupAreaV1 = buildupAreaV1;
	}
	public String getBuildupAreaUOMV1() {
		return buildupAreaUOMV1;
	}
	public void setBuildupAreaUOMV1(String buildupAreaUOMV1) {
		this.buildupAreaUOMV1 = buildupAreaUOMV1;
	}
	public double getInSqfbuildupAreaV1() {
		return inSqfbuildupAreaV1;
	}
	public void setInSqfbuildupAreaV1(double inSqfbuildupAreaV1) {
		this.inSqfbuildupAreaV1 = inSqfbuildupAreaV1;
	}
	public char getPropertyCompletionStatusV1() {
		return propertyCompletionStatusV1;
	}
	public void setPropertyCompletionStatusV1(char propertyCompletionStatusV1) {
		this.propertyCompletionStatusV1 = propertyCompletionStatusV1;
	}
	public double getLandValueIRBV1() {
		return landValueIRBV1;
	}
	public void setLandValueIRBV1(double landValueIRBV1) {
		this.landValueIRBV1 = landValueIRBV1;
	}
	public double getBuildingValueIRBV1() {
		return buildingValueIRBV1;
	}
	public void setBuildingValueIRBV1(double buildingValueIRBV1) {
		this.buildingValueIRBV1 = buildingValueIRBV1;
	}
	public double getReconstructionValueIRBV1() {
		return reconstructionValueIRBV1;
	}
	public void setReconstructionValueIRBV1(double reconstructionValueIRBV1) {
		this.reconstructionValueIRBV1 = reconstructionValueIRBV1;
	}
	public Boolean getIsPhyInspectionV1() {
		return isPhyInspectionV1;
	}
	public void setIsPhyInspectionV1(Boolean isPhyInspectionV1) {
		this.isPhyInspectionV1 = isPhyInspectionV1;
	}
	public String getPhyInspectionFreqUnitV1() {
		return phyInspectionFreqUnitV1;
	}
	public void setPhyInspectionFreqUnitV1(String phyInspectionFreqUnitV1) {
		this.phyInspectionFreqUnitV1 = phyInspectionFreqUnitV1;
	}
	public Date getLastPhyInspectionDateV1() {
		return lastPhyInspectionDateV1;
	}
	public void setLastPhyInspectionDateV1(Date lastPhyInspectionDateV1) {
		this.lastPhyInspectionDateV1 = lastPhyInspectionDateV1;
	}
	public Date getNextPhyInspectionDateV1() {
		return nextPhyInspectionDateV1;
	}
	public void setNextPhyInspectionDateV1(Date nextPhyInspectionDateV1) {
		this.nextPhyInspectionDateV1 = nextPhyInspectionDateV1;
	}
	public String getRemarksPropertyV1() {
		return remarksPropertyV1;
	}
	public void setRemarksPropertyV1(String remarksPropertyV1) {
		this.remarksPropertyV1 = remarksPropertyV1;
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
	public Date getValCreationDateV1() {
		return valCreationDateV1;
	}
	public void setValCreationDateV1(Date valCreationDateV1) {
		this.valCreationDateV1 = valCreationDateV1;
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
