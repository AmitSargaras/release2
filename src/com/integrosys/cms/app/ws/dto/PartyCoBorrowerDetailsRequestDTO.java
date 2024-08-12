package com.integrosys.cms.app.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PartyCoBorrowerDetailsRequestDTO")
public class PartyCoBorrowerDetailsRequestDTO {
	
	@XmlElement(name = "coBorrowerDetails", required=true)
	private List<CoBorrowerDetailsRequestDTO> coBorrowerDetails;

	public List<CoBorrowerDetailsRequestDTO> getCoBorrowerDetails() {
		return coBorrowerDetails;
	}

	public void setCoBorrowerDetails(List<CoBorrowerDetailsRequestDTO> coBorrowerDetails) {
		this.coBorrowerDetails = coBorrowerDetails;
	}

}
