package com.integrosys.cms.app.lei.json.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;

public interface ILEIValidationLog extends Serializable {
	public long getId();
	public void setId(long id);
	public String getPartyId();
	public void setPartyId(String partyId);
	public String getLeiCode();
	public void setLeiCode(String leiCode);
	public String getInterfaceName();
	public void setInterfaceName(String interfaceName);
	public Date getRequestDateTime();
	public void setRequestDateTime(Date requestDateTime);
	public Date getResponseDateTime();
	public void setResponseDateTime(Date responseDateTime);
	public String getErrorMessage();
	public void setErrorMessage(String errorMsg);
	public String getErrorCode();
	public void setErrorCode(String errorCode);
	public String getRequestMessage();
	public void setRequestMessage(String request);
	public String getResponseMessage();
	public void setResponseMessage(String responseMessage);
	public Long getCmsMainProfileID();
	public void setCmsMainProfileID(Long cmsMainProfileID);
	public Date getLastValidatedDate();
	public void setLastValidatedDate(Date lastValidatedDate);
	public String getLastValidatedBy();
	public void setLastValidatedBy(String lastValidatedBy);
	public char getIsLEICodeValidated();
	public void setIsLEICodeValidated(char isLEICodeValidated);
	public String getStatus();
	public void setStatus(String status);
}
