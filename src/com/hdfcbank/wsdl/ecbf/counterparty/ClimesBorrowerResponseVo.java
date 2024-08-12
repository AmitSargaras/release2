package com.hdfcbank.wsdl.ecbf.counterparty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClimesBorrowerResponseVo", propOrder = {
    "errorMessage",
    "partyId",
    "status"
})
public class ClimesBorrowerResponseVo {

	@XmlElement(name = "errorMessage", nillable = true, required = true)
    protected String errorMessage;
    
	@XmlElement(name = "partyId", nillable = true, required = true)
	protected String partyId;
	
	@XmlElement(name = "status", nillable = true, required = true)
    protected String status;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}