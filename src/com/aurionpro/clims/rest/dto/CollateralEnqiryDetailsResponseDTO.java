package com.aurionpro.clims.rest.dto;

import java.util.List;
public class CollateralEnqiryDetailsResponseDTO {

	/*private String responseStatus;*/
	private List<ResponseMessageDetailDTO> responseMessageList;
	private String securityId;
	private String sciReferenceNote;
	private String collateralSubType;
	
	private String collateralType;
	private String secPriority;
	private String monitorProcess;
	private String monitorFrequency;
	private String monitorFrequencyCode;
	private String collateralCurrency;
	private String collateralCurrencyCode;
	private String exchangeRateINR;
	private String securityOrganization; // branch name in UI
	private String securityOrganizationCode;
	private String cmv;
	private String margin;
	private String valuationAmount;
	private String commonRevalFreq;
	private String commonRevalFreqCode;
	private String valuationDate;
	private String nextValDate;
	private String revalFreq; 
	private String typeOfChange;
	private String typeOfChangeCode;
	private String otherBankCharge;
	private String securityOwnership;
	private String securityOwnershipCode;
	private String ownerOfProperty;
	private String thirdPartyEntity;
	private String thirdPartyEntityCode;
	private String cinForThirdParty;
	private String cersaiTransactionRefNumber;
	private String cersaiSecurityInterestId;
	private String cersaiAssetId;
	private String dateOfCersaiRegisteration;
	private String cersaiId;
	private String saleDeedPurchaseDate;
	private String thirdPartyAddress;
	private String thirdPartyState;
	private String thirdPartyStateId;
	private String thirdPartyCity;
	private String thirdPartyCityId;
	private String thirdPartyPincode;
	private String statePincodeString;
	private String collateralCategory;
	private String cersaiApplicableInd;
	private String collateralCustodian;
	private String collateralCode;
	private String collateralLocation;
	private List<InsuranceDetailRestResponseDTO> insuranceResponseList;
	
	public String getCollateralCode() {
		return collateralCode;
	}
	public void setCollateralCode(String collateralCode) {
		this.collateralCode = collateralCode;
	}
	public String getCollateralLocation() {
		return collateralLocation;
	}
	public void setCollateralLocation(String collateralLocation) {
		this.collateralLocation = collateralLocation;
	}
	private String collateralCustodianType;
	/*public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}*/
	public List<ResponseMessageDetailDTO> getResponseMessageList() {
		return responseMessageList;
	}
	public void setResponseMessageList(
			List<ResponseMessageDetailDTO> responseMessageList) {
		this.responseMessageList = responseMessageList;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
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
	public String getCollateralCurrency() {
		return collateralCurrency;
	}
	public void setCollateralCurrency(String collateralCurrency) {
		this.collateralCurrency = collateralCurrency;
	}
	public String getExchangeRateINR() {
		return exchangeRateINR;
	}
	public void setExchangeRateINR(String exchangeRateINR) {
		this.exchangeRateINR = exchangeRateINR;
	}
	public String getSecurityOrganization() {
		return securityOrganization;
	}
	public void setSecurityOrganization(String securityOrganization) {
		this.securityOrganization = securityOrganization;
	}
	public String getCmv() {
		return cmv;
	}
	public void setCmv(String cmv) {
		this.cmv = cmv;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getValuationAmount() {
		return valuationAmount;
	}
	public void setValuationAmount(String valuationAmount) {
		this.valuationAmount = valuationAmount;
	}
	public String getCommonRevalFreq() {
		return commonRevalFreq;
	}
	public void setCommonRevalFreq(String commonRevalFreq) {
		this.commonRevalFreq = commonRevalFreq;
	}
	public String getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}
	public String getNextValDate() {
		return nextValDate;
	}
	public void setNextValDate(String nextValDate) {
		this.nextValDate = nextValDate;
	}
	public String getRevalFreq() {
		return revalFreq;
	}
	public void setRevalFreq(String revalFreq) {
		this.revalFreq = revalFreq;
	}
	public String getTypeOfChange() {
		return typeOfChange;
	}
	public void setTypeOfChange(String typeOfChange) {
		this.typeOfChange = typeOfChange;
	}
	public String getOtherBankCharge() {
		return otherBankCharge;
	}
	public void setOtherBankCharge(String otherBankCharge) {
		this.otherBankCharge = otherBankCharge;
	}
	public String getSecurityOwnership() {
		return securityOwnership;
	}
	public void setSecurityOwnership(String securityOwnership) {
		this.securityOwnership = securityOwnership;
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
	public String getCinForThirdParty() {
		return cinForThirdParty;
	}
	public void setCinForThirdParty(String cinForThirdParty) {
		this.cinForThirdParty = cinForThirdParty;
	}
	public String getCersaiTransactionRefNumber() {
		return cersaiTransactionRefNumber;
	}
	public void setCersaiTransactionRefNumber(String cersaiTransactionRefNumber) {
		this.cersaiTransactionRefNumber = cersaiTransactionRefNumber;
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
	public String getDateOfCersaiRegisteration() {
		return dateOfCersaiRegisteration;
	}
	public void setDateOfCersaiRegisteration(String dateOfCersaiRegisteration) {
		this.dateOfCersaiRegisteration = dateOfCersaiRegisteration;
	}
	public String getCersaiId() {
		return cersaiId;
	}
	public void setCersaiId(String cersaiId) {
		this.cersaiId = cersaiId;
	}
	public String getSaleDeedPurchaseDate() {
		return saleDeedPurchaseDate;
	}
	public void setSaleDeedPurchaseDate(String saleDeedPurchaseDate) {
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
	public String getStatePincodeString() {
		return statePincodeString;
	}
	public void setStatePincodeString(String statePincodeString) {
		this.statePincodeString = statePincodeString;
	}
	public String getCollateralCategory() {
		return collateralCategory;
	}
	public void setCollateralCategory(String collateralCategory) {
		this.collateralCategory = collateralCategory;
	}
	public String getCersaiApplicableInd() {
		return cersaiApplicableInd;
	}
	public void setCersaiApplicableInd(String cersaiApplicableInd) {
		this.cersaiApplicableInd = cersaiApplicableInd;
	}
	public String getSciReferenceNote() {
		return sciReferenceNote;
	}
	public void setSciReferenceNote(String sciReferenceNote) {
		this.sciReferenceNote = sciReferenceNote;
	}
	public String getCollateralSubType() {
		return collateralSubType;
	}
	public void setCollateralSubType(String collateralSubType) {
		this.collateralSubType = collateralSubType;
	}
	public String getCollateralType() {
		return collateralType;
	}
	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}
	public String getCollateralCustodian() {
		return collateralCustodian;
	}
	public void setCollateralCustodian(String collateralCustodian) {
		this.collateralCustodian = collateralCustodian;
	}
	public String getCollateralCustodianType() {
		return collateralCustodianType;
	}
	public void setCollateralCustodianType(String collateralCustodianType) {
		this.collateralCustodianType = collateralCustodianType;
	}
	public List<InsuranceDetailRestResponseDTO> getInsuranceResponseList() {
		return insuranceResponseList;
	}
	public void setInsuranceResponseList(
			List<InsuranceDetailRestResponseDTO> insuranceResponseList) {
		this.insuranceResponseList = insuranceResponseList;
	}
	public String getMonitorFrequencyCode() {
		return monitorFrequencyCode;
	}
	public void setMonitorFrequencyCode(String monitorFrequencyCode) {
		this.monitorFrequencyCode = monitorFrequencyCode;
	}
	public String getCommonRevalFreqCode() {
		return commonRevalFreqCode;
	}
	public void setCommonRevalFreqCode(String commonRevalFreqCode) {
		this.commonRevalFreqCode = commonRevalFreqCode;
	}
	public String getTypeOfChangeCode() {
		return typeOfChangeCode;
	}
	public void setTypeOfChangeCode(String typeOfChangeCode) {
		this.typeOfChangeCode = typeOfChangeCode;
	}
	public String getSecurityOwnershipCode() {
		return securityOwnershipCode;
	}
	public void setSecurityOwnershipCode(String securityOwnershipCode) {
		this.securityOwnershipCode = securityOwnershipCode;
	}
	public String getThirdPartyEntityCode() {
		return thirdPartyEntityCode;
	}
	public void setThirdPartyEntityCode(String thirdPartyEntityCode) {
		this.thirdPartyEntityCode = thirdPartyEntityCode;
	}
	public String getCollateralCurrencyCode() {
		return collateralCurrencyCode;
	}
	public void setCollateralCurrencyCode(String collateralCurrencyCode) {
		this.collateralCurrencyCode = collateralCurrencyCode;
	}
	public String getSecurityOrganizationCode() {
		return securityOrganizationCode;
	}
	public void setSecurityOrganizationCode(String securityOrganizationCode) {
		this.securityOrganizationCode = securityOrganizationCode;
	}
	public String getThirdPartyStateId() {
		return thirdPartyStateId;
	}
	public void setThirdPartyStateId(String thirdPartyStateId) {
		this.thirdPartyStateId = thirdPartyStateId;
	}
	public String getThirdPartyCityId() {
		return thirdPartyCityId;
	}
	public void setThirdPartyCityId(String thirdPartyCityId) {
		this.thirdPartyCityId = thirdPartyCityId;
	}
	
	
}