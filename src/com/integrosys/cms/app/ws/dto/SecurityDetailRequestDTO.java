/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FacilityInfo")
public class SecurityDetailRequestDTO  {
	
	private static final long serialVersionUID = -114309476199266724L;
	@XmlElement(name = "securityCountry",required=true)
	private String securityCountry;
	
	@XmlElement(name = "securityCurrency",required=true)
	private String securityCurrency;
	
	@XmlElement(name = "securitySubType",required=true)
	private String securitySubType;
	
	@XmlElement(name = "collateralCodeName",required=true)
	private String collateralCodeName;
	
	@XmlElement(name = "securityPriority",required=true)
	private String securityPriority;
	
	@XmlElement(name = "securityCoverage",required=true)
	private String securityCoverage;
	
	@XmlElement(name = "cpsSecurityId",required=true)
	private String cpsSecurityId;
	
	//New General Fields
	
	 //New CAM ONLINE CR Begin
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
	//New CAM ONLINE CR End
	
	@XmlTransient
	private ActionErrors errors;
	
	public String getSecurityCountry() {
		return securityCountry;
	}

	public void setSecurityCountry(String securityCountry) {
		this.securityCountry = securityCountry;
	}

	public String getSecurityCurrency() {
		return securityCurrency;
	}

	public void setSecurityCurrency(String securityCurrency) {
		this.securityCurrency = securityCurrency;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getCollateralCodeName() {
		return collateralCodeName;
	}

	public void setCollateralCodeName(String collateralCodeName) {
		this.collateralCodeName = collateralCodeName;
	}

	public String getSecurityPriority() {
		return securityPriority;
	}

	public void setSecurityPriority(String securityPriority) {
		this.securityPriority = securityPriority;
	}

	public String getSecurityCoverage() {
		return securityCoverage;
	}

	public void setSecurityCoverage(String securityCoverage) {
		this.securityCoverage = securityCoverage;
	}

	public String getCpsSecurityId() {
		return cpsSecurityId;
	}

	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	
	

}