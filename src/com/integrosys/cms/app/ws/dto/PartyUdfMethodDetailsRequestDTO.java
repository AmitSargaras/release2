package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PartyUdfMethodDetailsRequestDTO {

	@XmlElement(name = "udf63",required=true)
	private String udf63;
	@XmlElement(name = "udf78",required=true)
	private String udf78;
	@XmlElement(name = "udf79",required=true)
	private String udf79;
	

	public String getUdf63() {
		return udf63;
	}

	public void setUdf63(String udf63) {
		this.udf63 = udf63;
	}

	public String getUdf78() {
		return udf78;
	}

	public void setUdf78(String udf78) {
		this.udf78 = udf78;
	}

	public String getUdf79() {
		return udf79;
	}

	public void setUdf79(String udf79) {
		this.udf79 = udf79;
	}
}
