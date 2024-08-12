package com.aurionpro.clims.rest.dto;

import java.util.List;

public class BodyRestResponseDTO {

	private String responseStatus;

	private List<ResponseMessageDetailDTO> responseMessageList;

	private List<CommonCodeDetailRestResponseDTO> commonCodeDetailRestList;
	
	private String camId;
	
	private String partyId;
	
	private String facilityId;
	
	private String limitId;

	private String securityId;
		
	private List<SecurityDetailRestResponseDTO> securityResponseList;
	
	private List<FacilitySCODRestResponseDTO> scodResponseList;
	
	private List<InsuranceDetailRestResponseDTO> insuranceResponseList;
	
	private List<AddtionalDocumentFacilityDetailsResponseDTO> addDocFacDetResList;
	
	private List<StockDetailRestResponseDTO> stockResponseList;
	
	private List<UdfEnqRestResponseDTO> udfEnqRestResponseDTO;

	
	
	public List<InsuranceDetailRestResponseDTO> getInsuranceResponseList() {
		return insuranceResponseList;
	}

	public void setInsuranceResponseList(
			List<InsuranceDetailRestResponseDTO> insuranceResponseList) {
		this.insuranceResponseList = insuranceResponseList;
	}

	public List<CommonCodeDetailRestResponseDTO> getCommonCodeDetailRestList() {
		return commonCodeDetailRestList;
	}

	public void setCommonCodeDetailRestList(List<CommonCodeDetailRestResponseDTO> commonCodeDetailRestList) {
		this.commonCodeDetailRestList = commonCodeDetailRestList;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<ResponseMessageDetailDTO> getResponseMessageList() {
		return responseMessageList;
	}

	public void setResponseMessageList(List<ResponseMessageDetailDTO> responseMessageList) {
		this.responseMessageList = responseMessageList;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	
	public String getCamId() {
		return camId;
	}

	public void setCamId(String camId) {
		this.camId = camId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public List<SecurityDetailRestResponseDTO> getSecurityResponseList() {
		return securityResponseList;
	}

	public void setSecurityResponseList(List<SecurityDetailRestResponseDTO> securityResponseList) {
		this.securityResponseList = securityResponseList;
	}

	public List<FacilitySCODRestResponseDTO> getScodResponseList() {
		return scodResponseList;
	}

	public void setScodResponseList(List<FacilitySCODRestResponseDTO> scodResponseList) {
		this.scodResponseList = scodResponseList;
	}
	
	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public List<StockDetailRestResponseDTO> getStockResponseList() {
		return stockResponseList;
	}

	public void setStockResponseList(List<StockDetailRestResponseDTO> stockResponseList) {
		this.stockResponseList = stockResponseList;
	}

	public List<AddtionalDocumentFacilityDetailsResponseDTO> getAddDocFacDetResList() {
		return addDocFacDetResList;
	}

	public void setAddDocFacDetResList(
			List<AddtionalDocumentFacilityDetailsResponseDTO> addDocFacDetResList) {
		this.addDocFacDetResList = addDocFacDetResList;
	}

	public List<UdfEnqRestResponseDTO> getUdfEnqRestResponseDTO() {
		return udfEnqRestResponseDTO;
	}

	public void setUdfEnqRestResponseDTO(List<UdfEnqRestResponseDTO> udfEnqRestResponseDTO) {
		this.udfEnqRestResponseDTO = udfEnqRestResponseDTO;
	}

}