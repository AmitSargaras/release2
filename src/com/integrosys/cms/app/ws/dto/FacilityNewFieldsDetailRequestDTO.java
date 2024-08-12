package com.integrosys.cms.app.ws.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FacilityInfo")
public class FacilityNewFieldsDetailRequestDTO extends RequestDTO{
	
	@XmlElement(name = "facilityCategoryId",required=true)
	private String facilityCategoryId;
	
	@XmlElement(name = "facilityTypeId",required=true)
	private String facilityTypeId;
	
	@XmlElement(name = "mainFacilityId",required=true)
	private String mainFacilityId;
	
	@XmlElement(name = "camId",required=true)
	private String camId;
	
	@XmlElement(name = "climsFacilityId",required=true)
	private String climsFacilityId;	
	
	@XmlElement(name = "securityList",required=true)
	private List<SecurityNewFieldsDetailRequestDTO> securityList;
	
	@XmlElement(name = "riskType",required=true)
	private String riskType;
	
	@XmlElement(name = "tenorUnit",required=true)
	private String tenorUnit;
	
	@XmlElement(name = "tenor",required=true)
	private String tenor;	
	
	@XmlElement(name = "tenorDesc",required=true)
	private String tenorDesc;
	
	@XmlElement(name = "margin",required=true)
	private String margin;
	
	@XmlElement(name = "putCallOption",required=true)
	private String putCallOption;
	
	@XmlElement(name = "optionDate",required=true)
	private String optionDate;
	
	@XmlElement(name = "loanAvailabilityDate",required=true)
	private String loanAvailabilityDate;
	
	@XmlElement(name = "bankingArrangement",required=true)
	private String bankingArrangement;
	
	@XmlElement(name = "clauseAsPerPolicy",required=true)
	private String clauseAsPerPolicy;
	
	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String event;

	public String getFacilityCategoryId() {
		return facilityCategoryId;
	}

	public void setFacilityCategoryId(String facilityCategoryId) {
		this.facilityCategoryId = facilityCategoryId;
	}

	public String getFacilityTypeId() {
		return facilityTypeId;
	}

	public void setFacilityTypeId(String facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}

	public String getMainFacilityId() {
		return mainFacilityId;
	}

	public void setMainFacilityId(String mainFacilityId) {
		this.mainFacilityId = mainFacilityId;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getTenorUnit() {
		return tenorUnit;
	}

	public void setTenorUnit(String tenorUnit) {
		this.tenorUnit = tenorUnit;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getTenorDesc() {
		return tenorDesc;
	}

	public void setTenorDesc(String tenorDesc) {
		this.tenorDesc = tenorDesc;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getPutCallOption() {
		return putCallOption;
	}

	public void setPutCallOption(String putCallOption) {
		this.putCallOption = putCallOption;
	}

	public String getOptionDate() {
		return optionDate;
	}

	public void setOptionDate(String optionDate) {
		this.optionDate = optionDate;
	}

	public String getLoanAvailabilityDate() {
		return loanAvailabilityDate;
	}

	public void setLoanAvailabilityDate(String loanAvailabilityDate) {
		this.loanAvailabilityDate = loanAvailabilityDate;
	}

	
	
	public String getBankingArrangement() {
		return bankingArrangement;
	}

	public void setBankingArrangement(String bankingArrangement) {
		this.bankingArrangement = bankingArrangement;
	}

	public String getClauseAsPerPolicy() {
		return clauseAsPerPolicy;
	}

	public void setClauseAsPerPolicy(String clauseAsPerPolicy) {
		this.clauseAsPerPolicy = clauseAsPerPolicy;
	}

	public String getCamId() {
		return camId;
	}

	public void setCamId(String camId) {
		this.camId = camId;
	}

	public String getClimsFacilityId() {
		return climsFacilityId;
	}

	public void setClimsFacilityId(String climsFacilityId) {
		this.climsFacilityId = climsFacilityId;
	}

	public List<SecurityNewFieldsDetailRequestDTO> getSecurityList() {
		return securityList;
	}

	public void setSecurityList(List<SecurityNewFieldsDetailRequestDTO> securityList) {
		this.securityList = securityList;
	}
	
}
