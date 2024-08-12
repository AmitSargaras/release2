package com.integrosys.cms.app.ws.dto;

import java.util.Date;

public class OBInterfaceLog implements IInterfaceLog {
	
	public OBInterfaceLog() {}
	
    private long id;
    
	private String interfaceName;
	
	private Date requestDateTime;
	private Date responseDateTime;
	private String errorMessage;
	private String errorCode;
	private String status;
	
	private Long transactionId;
	private String requestMessage;
	private String responseMessage;
	private String wsClient;
	private int failCount;
	
	private String partyId,partyName,camId,camNo,facilityId,securityId,cpsSecId,cpsDocId,climsDocId,cpsDiscrId,climsDiscrId,secSubType,camExtDate;
	private String i_name,particulars,operation;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public Date getResponseDateTime() {
		return responseDateTime;
	}
	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	private String isacErrorCode;
	private String isacErrorMessage;
	private String isacmakerid;
	private String isaccheckerid;
	
	private String isacReferenceNo;
	
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
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

}
