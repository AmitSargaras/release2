package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FacilityInfo")
public class SecurityNewFieldsDetailRequestDTO {
	
	
	@XmlElement(name = "cpsSecurityId",required=true)
	private String cpsSecurityId;
	
	@XmlElement(name = "primarySecurityAddress",required=true)
	private String primarySecurityAddress;
	
	@XmlElement(name = "securityValueAsPerCAM",required=true)
	private String securityValueAsPerCAM;
	
	@XmlElement(name = "secondarySecurityAddress",required=true)
	private String secondarySecurityAddress;
	
	@XmlElement(name = "securityMargin",required=true)
	private String securityMargin;
	
	@XmlElement(name = "chargePriority",required=true)
	private String chargePriority;
	
	@XmlTransient
	private ActionErrors errors;

	public String getCpsSecurityId() {
		return cpsSecurityId;
	}

	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}

	public String getPrimarySecurityAddress() {
		return primarySecurityAddress;
	}

	public void setPrimarySecurityAddress(String primarySecurityAddress) {
		this.primarySecurityAddress = primarySecurityAddress;
	}

	public String getSecurityValueAsPerCAM() {
		return securityValueAsPerCAM;
	}

	public void setSecurityValueAsPerCAM(String securityValueAsPerCAM) {
		this.securityValueAsPerCAM = securityValueAsPerCAM;
	}

	public String getSecondarySecurityAddress() {
		return secondarySecurityAddress;
	}

	public void setSecondarySecurityAddress(String secondarySecurityAddress) {
		this.secondarySecurityAddress = secondarySecurityAddress;
	}

	public String getSecurityMargin() {
		return securityMargin;
	}

	public void setSecurityMargin(String securityMargin) {
		this.securityMargin = securityMargin;
	}

	public String getChargePriority() {
		return chargePriority;
	}

	public void setChargePriority(String chargePriority) {
		this.chargePriority = chargePriority;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

}
