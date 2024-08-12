/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;


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
public class FacilitySCODDetailRequestDTO extends RequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	/*@XmlElement(name = "facilityMasterId",required=true)
	private String facilityMasterId;*/
	
	@XmlElement(name = "camId",required=true)
	private String camId;
	
	@XmlElement(name = "climsFacilityId",required=true)
	private String climsFacilityId;
	
	@XmlElement(name = "camType",required=true)
	private String camType;
	
	@XmlElement(name = "projectFinance",required=true)
	private String projectFinance;
	
	@XmlElement(name = "projectLoan",required=true)
	private String projectLoan;
	
	@XmlElement(name = "infraFlag",required=true)
	private String infraFlag;
	
	@XmlElement(name = "scod",required=true)
	private String scod;
	
	@XmlElement(name = "scodRemark",required=true)
	private String scodRemark;
	
	@XmlElement(name = "exeAssetClass",required=true)
	private String exeAssetClass;
	
	@XmlElement(name = "exeAssetClassDate",required=true)
	private String exeAssetClassDate;
	
	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String event;

	
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

	public String getCamType() {
		return camType;
	}

	public void setCamType(String camType) {
		this.camType = camType;
	}

	public String getProjectFinance() {
		return projectFinance;
	}

	public void setProjectFinance(String projectFinance) {
		this.projectFinance = projectFinance;
	}

	public String getProjectLoan() {
		return projectLoan;
	}

	public void setProjectLoan(String projectLoan) {
		this.projectLoan = projectLoan;
	}

	public String getInfraFlag() {
		return infraFlag;
	}

	public void setInfraFlag(String infraFlag) {
		this.infraFlag = infraFlag;
	}

	public String getScod() {
		return scod;
	}

	public void setScod(String scod) {
		this.scod = scod;
	}

	public String getScodRemark() {
		return scodRemark;
	}

	public void setScodRemark(String scodRemark) {
		this.scodRemark = scodRemark;
	}

	public String getExeAssetClass() {
		return exeAssetClass;
	}

	public void setExeAssetClass(String exeAssetClass) {
		this.exeAssetClass = exeAssetClass;
	}

	public String getExeAssetClassDate() {
		return exeAssetClassDate;
	}

	public void setExeAssetClassDate(String exeAssetClassDate) {
		this.exeAssetClassDate = exeAssetClassDate;
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
	
	//Interim 
	@XmlElement(name = "limitReleaseFlg",required=true)
	private String limitReleaseFlg;
	
	@XmlElement(name = "repayChngSched",required=true)
	private String repayChngSched;
	
	@XmlElement(name = "revAssetClass",required=true)
	private String revAssetClass;
	
	@XmlElement(name = "revAssetClassDt",required=true)
	private String revAssetClassDt;
	
	@XmlElement(name = "acod",required=true)
	private String acod;
	
	//Level 1
	
	@XmlElement(name = "delayFlagL1",required=true)
	private String delayFlagL1;
	
	@XmlElement(name = "interestFlag",required=true)
	private String interestFlag;
	
	@XmlElement(name = "priorReqFlag",required=true)
	private String priorReqFlag;
	
	@XmlElement(name = "delaylevel",required=true)
	private String delaylevel;
	
	@XmlElement(name = "defReasonL1",required=true)
	private String defReasonL1;
	
	@XmlElement(name = "repayChngSchedL1",required=true)
	private String repayChngSchedL1;
	
	@XmlElement(name = "escodL1",required=true)
	private String escodL1;
	
	@XmlElement(name = "ownershipQ1FlagL1",required=true)
	private String ownershipQ1FlagL1;
	
	@XmlElement(name = "ownershipQ2FlagL1",required=true)
	private String ownershipQ2FlagL1;
	
	@XmlElement(name = "ownershipQ3FlagL1",required=true)
	private String ownershipQ3FlagL1;
	
	@XmlElement(name = "scopeQ1FlagL1",required=true)
	private String scopeQ1FlagL1;
	
	@XmlElement(name = "scopeQ2FlagL1",required=true)
	private String scopeQ2FlagL1;
	
	@XmlElement(name = "scopeQ3FlagL1",required=true)
	private String scopeQ3FlagL1;
	
	@XmlElement(name = "revisedEscodL1",required=true)
	private String revisedEscodL1;
	
	@XmlElement(name = "exeAssetClassL1",required=true)
	private String exeAssetClassL1;
	
	@XmlElement(name = "exeAssetClassDtL1",required=true)
	private String exeAssetClassDtL1;
	
	@XmlElement(name = "revAssetClassL1",required=true)
	private String revAssetClassL1;
	
	@XmlElement(name = "revAssetClassDtL1",required=true)
	private String revAssetClassDtL1;
	
	//Level 2
	
	@XmlElement(name = "delayFlagL2",required=true)
	private String delayFlagL2;
	
	@XmlElement(name = "defReasonL2",required=true)
	private String defReasonL2;
	
	@XmlElement(name = "repayChngSchedL2",required=true)
	private String repayChngSchedL2;
	
	@XmlElement(name = "escodL2",required=true)
	private String escodL2;
	
	@XmlElement(name = "escodRevisionReasonQ1L2",required=true)
	private String escodRevisionReasonQ1L2;
	
	@XmlElement(name = "escodRevisionReasonQ2L2",required=true)
	private String escodRevisionReasonQ2L2;
	
	@XmlElement(name = "escodRevisionReasonQ3L2",required=true)
	private String escodRevisionReasonQ3L2;
	
	@XmlElement(name = "escodRevisionReasonQ4L2",required=true)
	private String escodRevisionReasonQ4L2;
	
	@XmlElement(name = "escodRevisionReasonQ5L2",required=true)
	private String escodRevisionReasonQ5L2;
	
	@XmlElement(name = "escodRevisionReasonQ6L2",required=true)
	private String escodRevisionReasonQ6L2;
	
	@XmlElement(name = "legalDetailL2",required=true)
	private String legalDetailL2;
	
	@XmlElement(name = "beyondControlL2",required=true)
	private String beyondControlL2;
	
	@XmlElement(name = "ownershipQ1FlagL2",required=true)
	private String ownershipQ1FlagL2;
	
	@XmlElement(name = "ownershipQ2FlagL2",required=true)
	private String ownershipQ2FlagL2;
	
	@XmlElement(name = "ownershipQ3FlagL2",required=true)
	private String ownershipQ3FlagL2;
	
	@XmlElement(name = "scopeQ1FlagL2",required=true)
	private String scopeQ1FlagL2;
	
	@XmlElement(name = "scopeQ2FlagL2",required=true)
	private String scopeQ2FlagL2;
	
	@XmlElement(name = "scopeQ3FlagL2",required=true)
	private String scopeQ3FlagL2;
	
	@XmlElement(name = "revisedEscodL2",required=true)
	private String revisedEscodL2;
	
	@XmlElement(name = "exeAssetClassL2",required=true)
	private String exeAssetClassL2;
	
	@XmlElement(name = "exeAssetClassDtL2",required=true)
	private String exeAssetClassDtL2;
	
	@XmlElement(name = "revAssetClassL2",required=true)
	private String revAssetClassL2;
	
	@XmlElement(name = "revAssetClassDtL2",required=true)
	private String revAssetClassDtL2;
	
	//Level 3
	
	
	@XmlElement(name = "delayFlagL3",required=true)
	private String delayFlagL3;
	
	@XmlElement(name = "defReasonL3",required=true)
	private String defReasonL3;
	
	@XmlElement(name = "repayChngSchedL3",required=true)
	private String repayChngSchedL3;
	
	@XmlElement(name = "escodL3",required=true)
	private String escodL3;
	
	@XmlElement(name = "escodRevisionReasonQ1L3",required=true)
	private String escodRevisionReasonQ1L3;
	
	@XmlElement(name = "escodRevisionReasonQ2L3",required=true)
	private String escodRevisionReasonQ2L3;
	
	@XmlElement(name = "escodRevisionReasonQ3L3",required=true)
	private String escodRevisionReasonQ3L3;
	
	@XmlElement(name = "escodRevisionReasonQ4L3",required=true)
	private String escodRevisionReasonQ4L3;
	
	@XmlElement(name = "escodRevisionReasonQ5L3",required=true)
	private String escodRevisionReasonQ5L3;
	
	@XmlElement(name = "escodRevisionReasonQ6L3",required=true)
	private String escodRevisionReasonQ6L3;
	
	@XmlElement(name = "legalDetailL3",required=true)
	private String legalDetailL3;
	
	@XmlElement(name = "beyondControlL3",required=true)
	private String beyondControlL3;
	
	@XmlElement(name = "ownershipQ1FlagL3",required=true)
	private String ownershipQ1FlagL3;
	
	@XmlElement(name = "ownershipQ2FlagL3",required=true)
	private String ownershipQ2FlagL3;
	
	@XmlElement(name = "ownershipQ3FlagL3",required=true)
	private String ownershipQ3FlagL3;
	
	@XmlElement(name = "scopeQ1FlagL3",required=true)
	private String scopeQ1FlagL3;
	
	@XmlElement(name = "scopeQ2FlagL3",required=true)
	private String scopeQ2FlagL3;
	
	@XmlElement(name = "scopeQ3FlagL3",required=true)
	private String scopeQ3FlagL3;
	
	@XmlElement(name = "revisedEscodL3",required=true)
	private String revisedEscodL3;
	
	@XmlElement(name = "exeAssetClassL3",required=true)
	private String exeAssetClassL3;
	
	@XmlElement(name = "exeAssetClassDtL3",required=true)
	private String exeAssetClassDtL3;
	
	@XmlElement(name = "revAssetClassL3",required=true)
	private String revAssetClassL3;
	
	@XmlElement(name = "revAssetClassDtL3",required=true)
	private String revAssetClassDtL3;


	public String getLimitReleaseFlg() {
		return limitReleaseFlg;
	}

	public void setLimitReleaseFlg(String limitReleaseFlg) {
		this.limitReleaseFlg = limitReleaseFlg;
	}

	public String getRepayChngSched() {
		return repayChngSched;
	}

	public void setRepayChngSched(String repayChngSched) {
		this.repayChngSched = repayChngSched;
	}

	public String getRevAssetClass() {
		return revAssetClass;
	}

	public void setRevAssetClass(String revAssetClass) {
		this.revAssetClass = revAssetClass;
	}

	public String getRevAssetClassDt() {
		return revAssetClassDt;
	}

	public void setRevAssetClassDt(String revAssetClassDt) {
		this.revAssetClassDt = revAssetClassDt;
	}

	public String getAcod() {
		return acod;
	}

	public void setAcod(String acod) {
		this.acod = acod;
	}

	public String getDelayFlagL1() {
		return delayFlagL1;
	}

	public void setDelayFlagL1(String delayFlagL1) {
		this.delayFlagL1 = delayFlagL1;
	}

	public String getInterestFlag() {
		return interestFlag;
	}

	public void setInterestFlag(String interestFlag) {
		this.interestFlag = interestFlag;
	}

	public String getPriorReqFlag() {
		return priorReqFlag;
	}

	public void setPriorReqFlag(String priorReqFlag) {
		this.priorReqFlag = priorReqFlag;
	}

	public String getDelaylevel() {
		return delaylevel;
	}

	public void setDelaylevel(String delaylevel) {
		this.delaylevel = delaylevel;
	}

	public String getDefReasonL1() {
		return defReasonL1;
	}

	public void setDefReasonL1(String defReasonL1) {
		this.defReasonL1 = defReasonL1;
	}

	public String getRepayChngSchedL1() {
		return repayChngSchedL1;
	}

	public void setRepayChngSchedL1(String repayChngSchedL1) {
		this.repayChngSchedL1 = repayChngSchedL1;
	}

	public String getEscodL1() {
		return escodL1;
	}

	public void setEscodL1(String escodL1) {
		this.escodL1 = escodL1;
	}

	public String getOwnershipQ1FlagL1() {
		return ownershipQ1FlagL1;
	}

	public void setOwnershipQ1FlagL1(String ownershipQ1FlagL1) {
		this.ownershipQ1FlagL1 = ownershipQ1FlagL1;
	}

	public String getOwnershipQ2FlagL1() {
		return ownershipQ2FlagL1;
	}

	public void setOwnershipQ2FlagL1(String ownershipQ2FlagL1) {
		this.ownershipQ2FlagL1 = ownershipQ2FlagL1;
	}

	public String getOwnershipQ3FlagL1() {
		return ownershipQ3FlagL1;
	}

	public void setOwnershipQ3FlagL1(String ownershipQ3FlagL1) {
		this.ownershipQ3FlagL1 = ownershipQ3FlagL1;
	}

	public String getScopeQ1FlagL1() {
		return scopeQ1FlagL1;
	}

	public void setScopeQ1FlagL1(String scopeQ1FlagL1) {
		this.scopeQ1FlagL1 = scopeQ1FlagL1;
	}

	public String getScopeQ2FlagL1() {
		return scopeQ2FlagL1;
	}

	public void setScopeQ2FlagL1(String scopeQ2FlagL1) {
		this.scopeQ2FlagL1 = scopeQ2FlagL1;
	}

	public String getScopeQ3FlagL1() {
		return scopeQ3FlagL1;
	}

	public void setScopeQ3FlagL1(String scopeQ3FlagL1) {
		this.scopeQ3FlagL1 = scopeQ3FlagL1;
	}

	public String getRevisedEscodL1() {
		return revisedEscodL1;
	}

	public void setRevisedEscodL1(String revisedEscodL1) {
		this.revisedEscodL1 = revisedEscodL1;
	}

	public String getExeAssetClassL1() {
		return exeAssetClassL1;
	}

	public void setExeAssetClassL1(String exeAssetClassL1) {
		this.exeAssetClassL1 = exeAssetClassL1;
	}

	public String getExeAssetClassDtL1() {
		return exeAssetClassDtL1;
	}

	public void setExeAssetClassDtL1(String exeAssetClassDtL1) {
		this.exeAssetClassDtL1 = exeAssetClassDtL1;
	}

	public String getRevAssetClassL1() {
		return revAssetClassL1;
	}

	public void setRevAssetClassL1(String revAssetClassL1) {
		this.revAssetClassL1 = revAssetClassL1;
	}

	public String getRevAssetClassDtL1() {
		return revAssetClassDtL1;
	}

	public void setRevAssetClassDtL1(String revAssetClassDtL1) {
		this.revAssetClassDtL1 = revAssetClassDtL1;
	}

	public String getDelayFlagL2() {
		return delayFlagL2;
	}

	public void setDelayFlagL2(String delayFlagL2) {
		this.delayFlagL2 = delayFlagL2;
	}

	public String getDefReasonL2() {
		return defReasonL2;
	}

	public void setDefReasonL2(String defReasonL2) {
		this.defReasonL2 = defReasonL2;
	}

	public String getRepayChngSchedL2() {
		return repayChngSchedL2;
	}

	public void setRepayChngSchedL2(String repayChngSchedL2) {
		this.repayChngSchedL2 = repayChngSchedL2;
	}

	public String getEscodL2() {
		return escodL2;
	}

	public void setEscodL2(String escodL2) {
		this.escodL2 = escodL2;
	}

	public String getEscodRevisionReasonQ1L2() {
		return escodRevisionReasonQ1L2;
	}

	public void setEscodRevisionReasonQ1L2(String escodRevisionReasonQ1L2) {
		this.escodRevisionReasonQ1L2 = escodRevisionReasonQ1L2;
	}

	public String getEscodRevisionReasonQ2L2() {
		return escodRevisionReasonQ2L2;
	}

	public void setEscodRevisionReasonQ2L2(String escodRevisionReasonQ2L2) {
		this.escodRevisionReasonQ2L2 = escodRevisionReasonQ2L2;
	}

	public String getEscodRevisionReasonQ3L2() {
		return escodRevisionReasonQ3L2;
	}

	public void setEscodRevisionReasonQ3L2(String escodRevisionReasonQ3L2) {
		this.escodRevisionReasonQ3L2 = escodRevisionReasonQ3L2;
	}

	public String getEscodRevisionReasonQ4L2() {
		return escodRevisionReasonQ4L2;
	}

	public void setEscodRevisionReasonQ4L2(String escodRevisionReasonQ4L2) {
		this.escodRevisionReasonQ4L2 = escodRevisionReasonQ4L2;
	}

	public String getEscodRevisionReasonQ5L2() {
		return escodRevisionReasonQ5L2;
	}

	public void setEscodRevisionReasonQ5L2(String escodRevisionReasonQ5L2) {
		this.escodRevisionReasonQ5L2 = escodRevisionReasonQ5L2;
	}

	public String getEscodRevisionReasonQ6L2() {
		return escodRevisionReasonQ6L2;
	}

	public void setEscodRevisionReasonQ6L2(String escodRevisionReasonQ6L2) {
		this.escodRevisionReasonQ6L2 = escodRevisionReasonQ6L2;
	}

	public String getLegalDetailL2() {
		return legalDetailL2;
	}

	public void setLegalDetailL2(String legalDetailL2) {
		this.legalDetailL2 = legalDetailL2;
	}

	public String getBeyondControlL2() {
		return beyondControlL2;
	}

	public void setBeyondControlL2(String beyondControlL2) {
		this.beyondControlL2 = beyondControlL2;
	}

	public String getOwnershipQ1FlagL2() {
		return ownershipQ1FlagL2;
	}

	public void setOwnershipQ1FlagL2(String ownershipQ1FlagL2) {
		this.ownershipQ1FlagL2 = ownershipQ1FlagL2;
	}

	public String getOwnershipQ2FlagL2() {
		return ownershipQ2FlagL2;
	}

	public void setOwnershipQ2FlagL2(String ownershipQ2FlagL2) {
		this.ownershipQ2FlagL2 = ownershipQ2FlagL2;
	}

	public String getOwnershipQ3FlagL2() {
		return ownershipQ3FlagL2;
	}

	public void setOwnershipQ3FlagL2(String ownershipQ3FlagL2) {
		this.ownershipQ3FlagL2 = ownershipQ3FlagL2;
	}

	public String getScopeQ1FlagL2() {
		return scopeQ1FlagL2;
	}

	public void setScopeQ1FlagL2(String scopeQ1FlagL2) {
		this.scopeQ1FlagL2 = scopeQ1FlagL2;
	}

	public String getScopeQ2FlagL2() {
		return scopeQ2FlagL2;
	}

	public void setScopeQ2FlagL2(String scopeQ2FlagL2) {
		this.scopeQ2FlagL2 = scopeQ2FlagL2;
	}

	public String getScopeQ3FlagL2() {
		return scopeQ3FlagL2;
	}

	public void setScopeQ3FlagL2(String scopeQ3FlagL2) {
		this.scopeQ3FlagL2 = scopeQ3FlagL2;
	}

	public String getRevisedEscodL2() {
		return revisedEscodL2;
	}

	public void setRevisedEscodL2(String revisedEscodL2) {
		this.revisedEscodL2 = revisedEscodL2;
	}

	public String getExeAssetClassL2() {
		return exeAssetClassL2;
	}

	public void setExeAssetClassL2(String exeAssetClassL2) {
		this.exeAssetClassL2 = exeAssetClassL2;
	}

	public String getExeAssetClassDtL2() {
		return exeAssetClassDtL2;
	}

	public void setExeAssetClassDtL2(String exeAssetClassDtL2) {
		this.exeAssetClassDtL2 = exeAssetClassDtL2;
	}

	public String getRevAssetClassL2() {
		return revAssetClassL2;
	}

	public void setRevAssetClassL2(String revAssetClassL2) {
		this.revAssetClassL2 = revAssetClassL2;
	}

	public String getRevAssetClassDtL2() {
		return revAssetClassDtL2;
	}

	public void setRevAssetClassDtL2(String revAssetClassDtL2) {
		this.revAssetClassDtL2 = revAssetClassDtL2;
	}

	public String getDelayFlagL3() {
		return delayFlagL3;
	}

	public void setDelayFlagL3(String delayFlagL3) {
		this.delayFlagL3 = delayFlagL3;
	}

	public String getDefReasonL3() {
		return defReasonL3;
	}

	public void setDefReasonL3(String defReasonL3) {
		this.defReasonL3 = defReasonL3;
	}

	public String getRepayChngSchedL3() {
		return repayChngSchedL3;
	}

	public void setRepayChngSchedL3(String repayChngSchedL3) {
		this.repayChngSchedL3 = repayChngSchedL3;
	}

	public String getEscodL3() {
		return escodL3;
	}

	public void setEscodL3(String escodL3) {
		this.escodL3 = escodL3;
	}

	public String getEscodRevisionReasonQ1L3() {
		return escodRevisionReasonQ1L3;
	}

	public void setEscodRevisionReasonQ1L3(String escodRevisionReasonQ1L3) {
		this.escodRevisionReasonQ1L3 = escodRevisionReasonQ1L3;
	}

	public String getEscodRevisionReasonQ2L3() {
		return escodRevisionReasonQ2L3;
	}

	public void setEscodRevisionReasonQ2L3(String escodRevisionReasonQ2L3) {
		this.escodRevisionReasonQ2L3 = escodRevisionReasonQ2L3;
	}

	public String getEscodRevisionReasonQ3L3() {
		return escodRevisionReasonQ3L3;
	}

	public void setEscodRevisionReasonQ3L3(String escodRevisionReasonQ3L3) {
		this.escodRevisionReasonQ3L3 = escodRevisionReasonQ3L3;
	}

	public String getEscodRevisionReasonQ4L3() {
		return escodRevisionReasonQ4L3;
	}

	public void setEscodRevisionReasonQ4L3(String escodRevisionReasonQ4L3) {
		this.escodRevisionReasonQ4L3 = escodRevisionReasonQ4L3;
	}

	public String getEscodRevisionReasonQ5L3() {
		return escodRevisionReasonQ5L3;
	}

	public void setEscodRevisionReasonQ5L3(String escodRevisionReasonQ5L3) {
		this.escodRevisionReasonQ5L3 = escodRevisionReasonQ5L3;
	}

	public String getEscodRevisionReasonQ6L3() {
		return escodRevisionReasonQ6L3;
	}

	public void setEscodRevisionReasonQ6L3(String escodRevisionReasonQ6L3) {
		this.escodRevisionReasonQ6L3 = escodRevisionReasonQ6L3;
	}

	public String getLegalDetailL3() {
		return legalDetailL3;
	}

	public void setLegalDetailL3(String legalDetailL3) {
		this.legalDetailL3 = legalDetailL3;
	}

	public String getBeyondControlL3() {
		return beyondControlL3;
	}

	public void setBeyondControlL3(String beyondControlL3) {
		this.beyondControlL3 = beyondControlL3;
	}

	public String getOwnershipQ1FlagL3() {
		return ownershipQ1FlagL3;
	}

	public void setOwnershipQ1FlagL3(String ownershipQ1FlagL3) {
		this.ownershipQ1FlagL3 = ownershipQ1FlagL3;
	}

	public String getOwnershipQ2FlagL3() {
		return ownershipQ2FlagL3;
	}

	public void setOwnershipQ2FlagL3(String ownershipQ2FlagL3) {
		this.ownershipQ2FlagL3 = ownershipQ2FlagL3;
	}

	public String getOwnershipQ3FlagL3() {
		return ownershipQ3FlagL3;
	}

	public void setOwnershipQ3FlagL3(String ownershipQ3FlagL3) {
		this.ownershipQ3FlagL3 = ownershipQ3FlagL3;
	}

	public String getScopeQ1FlagL3() {
		return scopeQ1FlagL3;
	}

	public void setScopeQ1FlagL3(String scopeQ1FlagL3) {
		this.scopeQ1FlagL3 = scopeQ1FlagL3;
	}

	public String getScopeQ2FlagL3() {
		return scopeQ2FlagL3;
	}

	public void setScopeQ2FlagL3(String scopeQ2FlagL3) {
		this.scopeQ2FlagL3 = scopeQ2FlagL3;
	}

	public String getScopeQ3FlagL3() {
		return scopeQ3FlagL3;
	}

	public void setScopeQ3FlagL3(String scopeQ3FlagL3) {
		this.scopeQ3FlagL3 = scopeQ3FlagL3;
	}

	public String getRevisedEscodL3() {
		return revisedEscodL3;
	}

	public void setRevisedEscodL3(String revisedEscodL3) {
		this.revisedEscodL3 = revisedEscodL3;
	}

	public String getExeAssetClassL3() {
		return exeAssetClassL3;
	}

	public void setExeAssetClassL3(String exeAssetClassL3) {
		this.exeAssetClassL3 = exeAssetClassL3;
	}

	public String getExeAssetClassDtL3() {
		return exeAssetClassDtL3;
	}

	public void setExeAssetClassDtL3(String exeAssetClassDtL3) {
		this.exeAssetClassDtL3 = exeAssetClassDtL3;
	}

	public String getRevAssetClassL3() {
		return revAssetClassL3;
	}

	public void setRevAssetClassL3(String revAssetClassL3) {
		this.revAssetClassL3 = revAssetClassL3;
	}

	public String getRevAssetClassDtL3() {
		return revAssetClassDtL3;
	}

	public void setRevAssetClassDtL3(String revAssetClassDtL3) {
		this.revAssetClassDtL3 = revAssetClassDtL3;
	}
	
	

}