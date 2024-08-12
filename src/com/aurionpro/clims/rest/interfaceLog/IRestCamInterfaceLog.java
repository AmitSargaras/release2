package com.aurionpro.clims.rest.interfaceLog;

import java.util.Date;

public interface IRestCamInterfaceLog {
	
	public long getId();
	public void setId(long id);
	public String getRequestId();
	public void setRequestId(String requestId);
	public String getChannelCode();
	public void setChannelCode(String channelCode);
	public String getReferenceNo();
	public void setReferenceNo(String referenceNo);
	public String getInterfaceStatus();
	public void setInterfaceStatus(String interfaceStatus);
	public String getInterfaceFailedReason();
	public void setInterfaceFailedReason(String interfaceFailedReason);
	public String getRequestText();
	public void setRequestText(String requestText);
	public Date getRequestDate();
	public void setRequestDate(Date requestDate);
	public String getResponseText();
	public void setResponseText(String responseText);
	public Date getResponseDate();
	public void setResponseDate(Date responseDate);
	


}
