/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.aurionpro.clims.rest.dto;

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
 * @author $Author: Sachin Dhulap$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class SecurityDetailRestRequestDTO  { 
	
	
	
	private List<RestApiHeaderRequestDTO> headerDetails;
	
	private String securityCountry;
	private String securityBranch;//N
	private String securityCurrency;
	private String collateralCodeName;
	private String securityType;//N
	private String securitySubType;
	private String securityPriority;
	private String monitorProcessColl;//N
	private String monitorFrequencyColl;//N
	private String securityRefNote;//N
	private String cpsSecurityId;
	private String uniqueReqId;
	private String securityCoverage;
	private String partyId;
	private String existingSecurityId;
	
	
	private ActionErrors errors;
	
	public String getSecurityCountry() {
		return securityCountry;
	}
	public void setSecurityCountry(String securityCountry) {
		this.securityCountry = securityCountry;
	}
	public String getSecurityBranch() {
		return securityBranch;
	}
	public void setSecurityBranch(String securityBranch) {
		this.securityBranch = securityBranch;
	}
	public String getSecurityCurrency() {
		return securityCurrency;
	}
	public void setSecurityCurrency(String securityCurrency) {
		this.securityCurrency = securityCurrency;
	}
	public String getCollateralCodeName() {
		return collateralCodeName;
	}
	public void setCollateralCodeName(String collateralCodeName) {
		this.collateralCodeName = collateralCodeName;
	}
	public String getSecurityType() {
		return securityType;
	}
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}
	public String getSecuritySubType() {
		return securitySubType;
	}
	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}
	public String getSecurityPriority() {
		return securityPriority;
	}
	public void setSecurityPriority(String securityPriority) {
		this.securityPriority = securityPriority;
	}
	
	public String getSecurityRefNote() {
		return securityRefNote;
	}
	public void setSecurityRefNote(String securityRefNote) {
		this.securityRefNote = securityRefNote;
	}
	public String getCpsSecurityId() {
		return cpsSecurityId;
	}
	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}
	public String getMonitorProcessColl() {
		return monitorProcessColl;
	}
	public void setMonitorProcessColl(String monitorProcessColl) {
		this.monitorProcessColl = monitorProcessColl;
	}
	public String getMonitorFrequencyColl() {
		return monitorFrequencyColl;
	}
	public void setMonitorFrequencyColl(String monitorFrequencyColl) {
		this.monitorFrequencyColl = monitorFrequencyColl;
	}
	public ActionErrors getErrors() {
		return errors;
	}
	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}
	public String getUniqueReqId() {
		return uniqueReqId;
	}
	public void setUniqueReqId(String uniqueReqId) {
		this.uniqueReqId = uniqueReqId;
	}
	public String getSecurityCoverage() {
		return securityCoverage;
	}
	public void setSecurityCoverage(String securityCoverage) {
		this.securityCoverage = securityCoverage;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getExistingSecurityId() {
		return existingSecurityId;
	}
	public void setExistingSecurityId(String existingSecurityId) {
		this.existingSecurityId = existingSecurityId;
	}
	
	
	

}