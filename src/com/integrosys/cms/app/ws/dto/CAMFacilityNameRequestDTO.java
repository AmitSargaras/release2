package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class CAMFacilityNameRequestDTO {
	
	@XmlElement(name = "FacilityNameValue",required=true)
	private String FacilityNameValue;

	public String  getFacilityNameValue() {
		return FacilityNameValue;
	}

	public void setFacilityNameValue(String facilityNameValue) {
		FacilityNameValue = facilityNameValue;
	}

}