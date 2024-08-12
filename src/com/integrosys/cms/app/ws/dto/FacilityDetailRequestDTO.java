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
public class FacilityDetailRequestDTO extends RequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	/*@XmlElement(name = "facilityMasterId",required=true)
	private String facilityMasterId;*/
	@XmlElement(name = "facilityCategoryId",required=true)
	private String facilityCategoryId;
	
	@XmlElement(name = "facilityTypeId",required=true)
	private String facilityTypeId;
	
	@XmlElement(name = "subLimitFlag",required=true)
	private String subLimitFlag;
	@XmlTransient
	private String guaranteeFlag;
	
	@XmlElement(name = "mainFacilityId",required=true)
	private String mainFacilityId;
	
	@XmlElement(name = "grade",required=true)
	private String grade;
	
	@XmlElement(name = "secured",required=true)
	private String secured;
	
	@XmlElement(name = "currency",required=true)
	private String currency;
	
	@XmlElement(name = "sanctionedAmount",required=true)
	private String sanctionedAmount;
	
	@XmlElement(name = "camId",required=true)
	private String camId;
	
	@XmlElement(name = "climsFacilityId",required=true)
	private String climsFacilityId;
	
	@XmlElement(name = "securityList",required=true)
	private List<SecurityDetailRequestDTO> securityList;

	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String subFacilityName;
	
	@XmlTransient
	private String event;
	
	@XmlTransient
	private String totalPartySanctionedAmount;
	
	//New CAM ONLINE CR START
	
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
	
	
	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}
	
	public String getTenorUnit() {
		return tenorUnit;
	}

	public void setTenorUnit(String tenorUnit) {
		this.tenorUnit = tenorUnit;
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
	 //New CAM ONLINE CR END
	
	
	
	
	
	/*public String getFacilityMasterId() {
		return facilityMasterId;
	}

	public void setFacilityMasterId(String facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}
*/	
	
	public String getSubLimitFlag() {
		return subLimitFlag;
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

	public void setSubLimitFlag(String subLimitFlag) {
		this.subLimitFlag = subLimitFlag;
	}

	public String getGuaranteeFlag() {
		return guaranteeFlag;
	}

	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}

	public String getMainFacilityId() {
		return mainFacilityId;
	}

	public void setMainFacilityId(String mainFacilityId) {
		this.mainFacilityId = mainFacilityId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSecured() {
		return secured;
	}

	public void setSecured(String secured) {
		this.secured = secured;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(String sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public String getCamId() {
		return camId;
	}

	public void setCamId(String camId) {
		this.camId = camId;
	}

	public List<SecurityDetailRequestDTO> getSecurityList() {
		return securityList;
	}

	public void setSecurityList(List<SecurityDetailRequestDTO> securityList) {
		this.securityList = securityList;
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

	public String getClimsFacilityId() {
		return climsFacilityId;
	}

	public void setClimsFacilityId(String climsFacilityId) {
		this.climsFacilityId = climsFacilityId;
	}

	public String getTotalPartySanctionedAmount() {
		return totalPartySanctionedAmount;
	}

	public void setTotalPartySanctionedAmount(String totalPartySanctionedAmount) {
		this.totalPartySanctionedAmount = totalPartySanctionedAmount;
	}

	public String getSubFacilityName() {
		return subFacilityName;
	}

	public void setSubFacilityName(String subFacilityName) {
		this.subFacilityName = subFacilityName;
	}

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
	

}