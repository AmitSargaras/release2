package com.integrosys.cms.app.json.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;

public interface IJsInterfaceLog extends Serializable {
	public String getLimitProfileId() ;
	public void setLimitProfileId(String limitProfileId) ;
	public String getLine_no() ;
	public void setLine_no(String line_no);
	public String getSerial_no() ;
	public void setSerial_no(String serial_no);
	public String getScmFlag() ;
	public void setScmFlag(String scmFlag);
	public long getId();
	public void setId(long id);
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
	public String getStatus();
	public void setStatus(String status);
	public Long getTransactionId();
	public void setTransactionId(Long transactionId);
	public String getRequestMessage();
	public void setRequestMessage(String request);
	public String getResponseMessage();
	public void setResponseMessage(String responseMessage);
	public String getPartyId();
	public void setPartyId(String partyId);
	public String getPartyName();
	public void setPartyName(String partyName) ;
	public String getModuleName();
	public void setModuleName(String moduleName) ;
	public String getOperation() ;
	public void setOperation(String operation) ;
	public String getIs_udf_upload();
	public void setIs_udf_upload(String is_udf_upload);
}
