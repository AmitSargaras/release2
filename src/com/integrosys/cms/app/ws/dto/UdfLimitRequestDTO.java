package com.integrosys.cms.app.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UdfLimitRequestDTO")
public class UdfLimitRequestDTO {

	@XmlElement(name = "udf")
	private List<UdfDetailLimitRequestDTO> udf;

	public List<UdfDetailLimitRequestDTO> getUdf() {
		return udf;
	}

	public void setUdf(List<UdfDetailLimitRequestDTO> udf) {
		this.udf = udf;
	}
	
}