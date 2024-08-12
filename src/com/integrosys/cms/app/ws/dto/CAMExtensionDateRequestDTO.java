package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CAMExtensionDateRequestInfo")
public class CAMExtensionDateRequestDTO extends RequestDTO {

	@XmlElement(required=true)
	private String partyId;
	
	@XmlElement(required=true)
	private String camExtensionDate;

	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getCamExtensionDate() {
		return camExtensionDate;
	}

	public void setCamExtensionDate(String camExtensionDate) {
		this.camExtensionDate = camExtensionDate;
	}
	
	public void trim() {
		if(this.camExtensionDate != null) {
			this.camExtensionDate = this.camExtensionDate.trim();
		}else {
			this.camExtensionDate = "";
		}
		
		if(this.partyId != null) {
			this.partyId = this.partyId.trim();
		}else {
			this.partyId = "";
		}
		
		if(this.getWsConsumer() != null) {
			this.setWsConsumer(this.getWsConsumer().trim());
		}else {
			this.setWsConsumer("");
		}
	}
	
}