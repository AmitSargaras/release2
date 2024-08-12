package com.integrosys.cms.app.collateral.bus.type.property;

import java.util.Date;

public class PropertyValuation2 implements IPropertyValuation2 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public PropertyValuation2() {
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
    private Date valuationDateV2;
    private String valuatorCompanyV2;
    private String totalPropertyAmontV2;
    private String categoryOfLandUseV2;
    private String developerNameV2;
    private String countryV2;
    private String regionV2;
    private String stateV2;
    private String nearestCityV2;
    private String pincodeV2;
    
    private double landAreaV2;
    private String landAreaUOMV2;
    private double inSqfLandAreaV2;
    private double buildupAreaV2;
    private String buildupAreaUOMV2;
    private double inSqfbuildupAreaV2;
    private char propertyCompletionStatusV2;
    private double landValueIRBV2;
    private double buildingValueIRBV2;
    private double reconstructionValueIRBV2;
    private Boolean isPhyInspectionV2;
    private String phyInspectionFreqUnitV2;
    private Date lastPhyInspectionDateV2;
    private Date nextPhyInspectionDateV2;
    private String remarksPropertyV2;
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
    private Date valCreationDateV2;
	private Date previousMortCreationDate;
	
	private String propertyUsage;
	private String mortageRegisteredRef;
	
	private String deferral;
	private String waiver;
	private String deferralId;
	
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
	public Date getValuationDateV2() {
		return valuationDateV2;
	}
	public void setValuationDateV2(Date valuationDateV2) {
		this.valuationDateV2 = valuationDateV2;
	}
	public String getValuatorCompanyV2() {
		return valuatorCompanyV2;
	}
	public void setValuatorCompanyV2(String valuatorCompanyV2) {
		this.valuatorCompanyV2 = valuatorCompanyV2;
	}
	public String getTotalPropertyAmontV2() {
		return totalPropertyAmontV2;
	}
	public void setTotalPropertyAmontV2(String totalPropertyAmontV2) {
		this.totalPropertyAmontV2 = totalPropertyAmontV2;
	}
	public String getCategoryOfLandUseV2() {
		return categoryOfLandUseV2;
	}
	public void setCategoryOfLandUseV2(String categoryOfLandUseV2) {
		this.categoryOfLandUseV2 = categoryOfLandUseV2;
	}
	public String getDeveloperNameV2() {
		return developerNameV2;
	}
	public void setDeveloperNameV2(String developerNameV2) {
		this.developerNameV2 = developerNameV2;
	}
	public String getCountryV2() {
		return countryV2;
	}
	public void setCountryV2(String countryV2) {
		this.countryV2 = countryV2;
	}
	public String getRegionV2() {
		return regionV2;
	}
	public void setRegionV2(String regionV2) {
		this.regionV2 = regionV2;
	}
	public String getStateV2() {
		return stateV2;
	}
	public void setStateV2(String stateV2) {
		this.stateV2 = stateV2;
	}
	public String getNearestCityV2() {
		return nearestCityV2;
	}
	public void setNearestCityV2(String nearestCityV2) {
		this.nearestCityV2 = nearestCityV2;
	}
	public String getPincodeV2() {
		return pincodeV2;
	}
	public void setPincodeV2(String pincodeV2) {
		this.pincodeV2 = pincodeV2;
	}
	public double getLandAreaV2() {
		return landAreaV2;
	}
	public void setLandAreaV2(double landAreaV2) {
		this.landAreaV2 = landAreaV2;
	}
	public String getLandAreaUOMV2() {
		return landAreaUOMV2;
	}
	public void setLandAreaUOMV2(String landAreaUOMV2) {
		this.landAreaUOMV2 = landAreaUOMV2;
	}
	public double getInSqfLandAreaV2() {
		return inSqfLandAreaV2;
	}
	public void setInSqfLandAreaV2(double inSqfLandAreaV2) {
		this.inSqfLandAreaV2 = inSqfLandAreaV2;
	}
	public double getBuildupAreaV2() {
		return buildupAreaV2;
	}
	public void setBuildupAreaV2(double buildupAreaV2) {
		this.buildupAreaV2 = buildupAreaV2;
	}
	public String getBuildupAreaUOMV2() {
		return buildupAreaUOMV2;
	}
	public void setBuildupAreaUOMV2(String buildupAreaUOMV2) {
		this.buildupAreaUOMV2 = buildupAreaUOMV2;
	}
	public double getInSqfbuildupAreaV2() {
		return inSqfbuildupAreaV2;
	}
	public void setInSqfbuildupAreaV2(double inSqfbuildupAreaV2) {
		this.inSqfbuildupAreaV2 = inSqfbuildupAreaV2;
	}
	public char getPropertyCompletionStatusV2() {
		return propertyCompletionStatusV2;
	}
	public void setPropertyCompletionStatusV2(char propertyCompletionStatusV2) {
		this.propertyCompletionStatusV2 = propertyCompletionStatusV2;
	}
	public double getLandValueIRBV2() {
		return landValueIRBV2;
	}
	public void setLandValueIRBV2(double landValueIRBV2) {
		this.landValueIRBV2 = landValueIRBV2;
	}
	public double getBuildingValueIRBV2() {
		return buildingValueIRBV2;
	}
	public void setBuildingValueIRBV2(double buildingValueIRBV2) {
		this.buildingValueIRBV2 = buildingValueIRBV2;
	}
	public double getReconstructionValueIRBV2() {
		return reconstructionValueIRBV2;
	}
	public void setReconstructionValueIRBV2(double reconstructionValueIRBV2) {
		this.reconstructionValueIRBV2 = reconstructionValueIRBV2;
	}
	public Boolean getIsPhyInspectionV2() {
		return isPhyInspectionV2;
	}
	public void setIsPhyInspectionV2(Boolean isPhyInspectionV2) {
		this.isPhyInspectionV2 = isPhyInspectionV2;
	}
	public String getPhyInspectionFreqUnitV2() {
		return phyInspectionFreqUnitV2;
	}
	public void setPhyInspectionFreqUnitV2(String phyInspectionFreqUnitV2) {
		this.phyInspectionFreqUnitV2 = phyInspectionFreqUnitV2;
	}
	public Date getLastPhyInspectionDateV2() {
		return lastPhyInspectionDateV2;
	}
	public void setLastPhyInspectionDateV2(Date lastPhyInspectionDateV2) {
		this.lastPhyInspectionDateV2 = lastPhyInspectionDateV2;
	}
	public Date getNextPhyInspectionDateV2() {
		return nextPhyInspectionDateV2;
	}
	public void setNextPhyInspectionDateV2(Date nextPhyInspectionDateV2) {
		this.nextPhyInspectionDateV2 = nextPhyInspectionDateV2;
	}
	public String getRemarksPropertyV2() {
		return remarksPropertyV2;
	}
	public void setRemarksPropertyV2(String remarksPropertyV2) {
		this.remarksPropertyV2 = remarksPropertyV2;
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
	public Date getValCreationDateV2() {
		return valCreationDateV2;
	}
	public void setValCreationDateV2(Date valCreationDateV2) {
		this.valCreationDateV2 = valCreationDateV2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getPreviousMortCreationDate() {
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
	
	public String getDeferral() {
		return deferral;
	}
	public void setDeferral(String deferral) {
		this.deferral = deferral;
	}
	
	public String getWaiver() {
		return waiver;
	}
	public void setWaiver(String waiver) {
		this.waiver = waiver;
	}
	
	public String getDeferralId() {
		return deferralId;
	}
	public void setDeferralId(String deferralId) {
		this.deferralId = deferralId;
	}
	
}
