package com.integrosys.cms.app.ws.dto;

import java.util.Date;

public interface IPSRDataLog {

	public Date getMakerDateTime();
	public void setMakerDateTime(Date makerDateTime);

	public Date getCheckerDateTime();
	public void setCheckerDateTime(Date checkerDateTime);
	
	public String getMakerId();
	public void setMakerId(String makerId);

	public String getCheckerId();
	public void setCheckerId(String checkerId);

	public long getId();
	public void setId(long id);

	public String getPartyId();
	public void setPartyId(String partyId);

	public String getPartyName();
	public void setPartyName(String partyName);

	public String getFileName();
	public void setFileName(String fileName);

	public String getSourceRefNo();
	public void setSourceRefNo(String sourceRefNo);

	public String getLiabilityId();
	public void setLiabilityId(String liabilityId);

	public String getLineNo();
	public void setLineNo(String lineNo);

	public String getSerialNo();
	public void setSerialNo(String serialNo);

	public String getCurrency();
	public void setCurrency(String currency);

	public Double getLimitAmount();
	public void setLimitAmount(Double limitAmount);

	public String getTenor();
	public void setTenor(String tenor);

	public Date getStartDate();
	public void setStartDate(Date startDate);

	public Date getExpiryDate();
	public void setExpiryDate(Date expiryDate);

	public String getAction();
	public void setAction(String action);

	public Date getRequestDateTime();
	public void setRequestDateTime(Date requestDateTime);

	public String getStatus();
	public void setStatus(String status);

	public Date getResponseDateTime();
	public void setResponseDateTime(Date responseDateTime);

	public String getErrorCode();
	public void setErrorCode(String errorCode);

	public String getErrorDesc();
	public void setErrorDesc(String errorDesc);

	public String getSystemId();
	public void setSystemId(String systemId);

	public String getSellDownPeriod();
	public void setSellDownPeriod(String sellDownPeriod);
	
	public String getSegment();
	public void setSegment(String segment);
}
