package com.aurionpro.clims.rest.dto;

import java.util.List;

import org.apache.struts.action.ActionErrors;

public class CollateralDetailslRestRequestDTO {
	
	private String event;
	
	private ActionErrors errors;

	private String securityId;
	
	private String secPriority;
	
	private String monitorProcess;
	
	private String monitorFrequency;
	
	private String collateralCurrency;
	
	private String exchangeRateINR;
	
	private String securityOrganization; // branch name in UI
	
	private String cmv;
	private String margin;
	
	public String getSecurityOrganization() {
		return securityOrganization;
	}



	public void setSecurityOrganization(String securityOrganization) {
		this.securityOrganization = securityOrganization;
	}

	private String valuationAmount;
	
	private String commonRevalFreq ;
	
	private String valuationDate ;
	
	private String nextValDate;
	
	private String revalFreq; // pending mapper
	
	private String typeOfChange; //% of First Charge by Other Bank
	
	private String otherBankCharge ;	
	//CERSAI fields
	
			private String securityOwnership;
			private String ownerOfProperty;
			private String thirdPartyEntity;
			private String cinForThirdParty;
			private String cersaiTransactionRefNumber;
			private String cersaiSecurityInterestId;
			private String cersaiAssetId;
			private String dateOfCersaiRegisteration;
			private String cersaiId;
			private String saleDeedPurchaseDate;
			private String thirdPartyAddress;
			private String thirdPartyState;
			private String thirdPartyCity;
			private String thirdPartyPincode;
		
			private String statePincodeString;
			private String collateralCategory;	
			private String cersaiApplicableInd;
			
			private List<StockRestRequestDTO> stockDetailsList;

	/*private List<> insurancePolicyList;*/
	
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

	
		
	public String getSecurityId() {
		return securityId;
	}
	
	private List<ABSpecRestRequestDTO> abSpecRestRequestDTOList;
	
	private List<InsurancePolicyRestRequestDTO> insurancePolicyList;
	
	private List<PropertyRestRequestDTO> propertyDetailsList;
	
	private List<AddtionalDocumentFacilityDetailsRestDTO> addDocFacDetailsList;
	
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}


	public List<AddtionalDocumentFacilityDetailsRestDTO> getAddDocFacDetailsList() {
		return addDocFacDetailsList;
	}



	public void setAddDocFacDetailsList(
			List<AddtionalDocumentFacilityDetailsRestDTO> addDocFacDetailsList) {
		this.addDocFacDetailsList = addDocFacDetailsList;
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



	public List<PropertyRestRequestDTO> getPropertyDetailsList() {
		return propertyDetailsList;
	}



	public void setPropertyDetailsList(
			List<PropertyRestRequestDTO> propertyDetailsList) {
		this.propertyDetailsList = propertyDetailsList;
	}



	public List<InsurancePolicyRestRequestDTO> getInsurancePolicyList() {
		return insurancePolicyList;
	}



	public void setInsurancePolicyList(
			List<InsurancePolicyRestRequestDTO> insurancePolicyList) {
		this.insurancePolicyList = insurancePolicyList;
	}



	public List<ABSpecRestRequestDTO> getAbSpecRestRequestDTOList() {
		return abSpecRestRequestDTOList;
	}
	


	public void setAbSpecRestRequestDTOList(
			List<ABSpecRestRequestDTO> abSpecRestRequestDTOList) {
		this.abSpecRestRequestDTOList = abSpecRestRequestDTOList;
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
	

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}
	
	private List<GuaranteesRestRequestDTO> guaranteeCorpRestRequestDTOList;

	public List<GuaranteesRestRequestDTO> getGuaranteeCorpRestRequestDTOList() {
		return guaranteeCorpRestRequestDTOList;
	}

	public void setGuaranteeCorpRestRequestDTOList(List<GuaranteesRestRequestDTO> guaranteeCorpRestRequestDTOList) {
		this.guaranteeCorpRestRequestDTOList = guaranteeCorpRestRequestDTOList;
	}
	
	private List<GuaranteesGovtRestRequestDTO> guaranteeGovtRestRequestDTOList;

	public List<GuaranteesGovtRestRequestDTO> getGuaranteeGovtRestRequestDTOList() {
		return guaranteeGovtRestRequestDTOList;
	}

	public void setGuaranteeGovtRestRequestDTOList(List<GuaranteesGovtRestRequestDTO> guaranteeGovtRestRequestDTOList) {
		this.guaranteeGovtRestRequestDTOList = guaranteeGovtRestRequestDTOList;
	}
	
	private List<GuaranteesIndRestRequestDTO> guaranteeIndRestRequestDTOList;

	public List<GuaranteesIndRestRequestDTO> getGuaranteeIndRestRequestDTOList() {
		return guaranteeIndRestRequestDTOList;
	}

	public void setGuaranteeIndRestRequestDTOList(List<GuaranteesIndRestRequestDTO> guaranteeIndRestRequestDTOList) {
		this.guaranteeIndRestRequestDTOList = guaranteeIndRestRequestDTOList;
	}



	public List<StockRestRequestDTO> getStockDetailsList() {
		return stockDetailsList;
	}



	public void setStockDetailsList(List<StockRestRequestDTO> stockDetailsList) {
		this.stockDetailsList = stockDetailsList;
	}



	public String getExchangeRateINR() {
		return exchangeRateINR;
	}



	public void setExchangeRateINR(String exchangeRateINR) {
		this.exchangeRateINR = exchangeRateINR;
	}
	

}
