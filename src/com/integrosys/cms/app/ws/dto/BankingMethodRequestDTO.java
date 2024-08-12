package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BankingMethodRequestDTO {
	
	@XmlElement(name = "bankingMethod",required=true)
	private String bankingMethod;

	public String getBankingMethod() {
		return bankingMethod;
	}

	public void setBankingMethod(String bankingMethod) {
		this.bankingMethod = bankingMethod;
	}
	
	

}
