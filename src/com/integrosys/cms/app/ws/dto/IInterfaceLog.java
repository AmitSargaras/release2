package com.integrosys.cms.app.ws.dto;

import java.io.Serializable;
import java.util.Date;

	public interface IInterfaceLog extends Serializable {
	public long getId();        
	public void setId(long id) ;
	
	public String getInterfaceName() ;
	public void setInterfaceName(String interfaceName) ;

	public Date getRequestDateTime() ;
	public void setRequestDateTime(Date requestDateTime) ;
	
	public Date getResponseDateTime() ;
	public void setResponseDateTime(Date responseDateTime);
	
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage) ;
	
	public String getErrorCode();
	public void setErrorCode(String errorCode) ;
	
	public String getStatus();
	public void setStatus(String status) ;
	
	public String getRequestMessage() ;
	public void setRequestMessage(String requestMessage) ;
	
	public String getResponseMessage() ;
	public void setResponseMessage(String responseMessage) ;
	
	public String getWsClient() ;
	public void setWsClient(String WsClient) ;
	
	public String getPartyId();
	public void setPartyId(String partyId);
	
	public String getPartyName();
	public void setPartyName(String partyName);
	
	public String getCamId();
	public void setCamId(String camId);
	
	public String getFacilityId();
	public void setFacilityId(String facilityId);
	
	public String getCpsSecId();
	public void setCpsSecId(String cpsSecId);
	
	public String getCpsDocId();
	public void setCpsDocId(String cpsDocId);
	
	public String getCpsDiscrId();
	public void setCpsDiscrId(String cpsDiscrId);
	
	public String getSecurityId();
	public void setSecurityId(String securityId);
	
	public String getCamNo();
	public void setCamNo(String camNo);
	public String getClimsDocId();
	public void setClimsDocId(String climsDocId);
	public String getClimsDiscrId();
	public void setClimsDiscrId(String climsDiscrId);
	public String getI_name();
	public void setI_name(String i_name);
	public String getParticulars();
	public void setParticulars(String particulars);
	public String getOperation();
	public void setOperation(String operation);
	public String getSecSubType();
	public void setSecSubType(String secSubType);
}
