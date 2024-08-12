package com.integrosys.cms.app.ws.dto;

import java.io.Serializable;
import java.util.Date;

public class InterfaceLoggingDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5543480918088688069L;
	private String requestMessage, responseMessage, interfaceName, wsClient;
	private Date requestDate, responseDate;
	private String errorCode, errorMessage, status;
	private Long transactionId;
	private RequestDTO requestDto;
	private ResponseDTO responseDTO;
	private ErrorDetailDTO errorDetailDTO;
	private String partyId,partyName,camId,camNo,facilityId,securityId,cpsSecId,cpsDocId,climsDocId,cpsDiscrId,climsDiscrId,secSubType,camExtDate, isacReferenceNo,isacErrorCode,isacErrorMessage,isacmakerid,isaccheckerid;
	
	public String getIsacmakerid() {
		return isacmakerid;
	}

	public void setIsacmakerid(String isacmakerid) {
		this.isacmakerid = isacmakerid;
	}

	public String getIsaccheckerid() {
		return isaccheckerid;
	}

	public void setIsaccheckerid(String isaccheckerid) {
		this.isaccheckerid = isaccheckerid;
	}

	private String i_name,particulars,operation;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RequestDTO getRequestDto() {
		return requestDto;
	}

	public void setRequestDto(RequestDTO requestDto) {
		this.requestDto = requestDto;
	}

	public ResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(ResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public ErrorDetailDTO getErrorDetailDTO() {
		return errorDetailDTO;
	}

	public void setErrorDetailDTO(ErrorDetailDTO errorDetailDTO) {
		this.errorDetailDTO = errorDetailDTO;
	}

	public String getWsClient() {
		return wsClient;
	}

	public void setWsClient(String wsClient) {
		this.wsClient = wsClient;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
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

	public String getCpsSecId() {
		return cpsSecId;
	}

	public void setCpsSecId(String cpsSecId) {
		this.cpsSecId = cpsSecId;
	}

	public String getCpsDocId() {
		return cpsDocId;
	}

	public void setCpsDocId(String cpsDocId) {
		this.cpsDocId = cpsDocId;
	}

	public String getCpsDiscrId() {
		return cpsDiscrId;
	}

	public void setCpsDiscrId(String cpsDiscrId) {
		this.cpsDiscrId = cpsDiscrId;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getCamNo() {
		return camNo;
	}

	public void setCamNo(String camNo) {
		this.camNo = camNo;
	}

	public String getClimsDocId() {
		return climsDocId;
	}

	public void setClimsDocId(String climsDocId) {
		this.climsDocId = climsDocId;
	}

	public String getClimsDiscrId() {
		return climsDiscrId;
	}

	public void setClimsDiscrId(String climsDiscrId) {
		this.climsDiscrId = climsDiscrId;
	}

	public String getI_name() {
		return i_name;
	}

	public void setI_name(String i_name) {
		this.i_name = i_name;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSecSubType() {
		return secSubType;
	}

	public void setSecSubType(String secSubType) {
		this.secSubType = secSubType;
	}
	
	public String getCamExtDate() {
		return camExtDate;
	}

	public void setCamExtDate(String camExtDate) {
		this.camExtDate = camExtDate;
	}

	public String getIsacReferenceNo() {
		return isacReferenceNo;
	}

	public void setIsacReferenceNo(String isacReferenceNo) {
		this.isacReferenceNo = isacReferenceNo;
	}

	public String getIsacErrorCode() {
		return isacErrorCode;
	}

	public void setIsacErrorCode(String isacErrorCode) {
		this.isacErrorCode = isacErrorCode;
	}

	public String getIsacErrorMessage() {
		return isacErrorMessage;
	}

	public void setIsacErrorMessage(String isacErrorMessage) {
		this.isacErrorMessage = isacErrorMessage;
	}
	
	
	
}
