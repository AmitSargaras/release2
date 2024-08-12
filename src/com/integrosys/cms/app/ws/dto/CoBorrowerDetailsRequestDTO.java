package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CoBorrowerDetailsRequestDTO")
public class CoBorrowerDetailsRequestDTO {

	@XmlElement(name = "coBorrowerLiabId", required=true)
	private String coBorrowerLiabId;
	
	@XmlElement(name = "coBorrowerName", required=true)
	private String coBorrowerName;

	public String getCoBorrowerLiabId() {
		return coBorrowerLiabId;
	}

	public void setCoBorrowerLiabId(String coBorrowerLiabId) {
		this.coBorrowerLiabId = coBorrowerLiabId;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}
}
