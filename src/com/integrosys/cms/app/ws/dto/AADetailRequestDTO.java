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
@XmlRootElement(name = "CAMInfo")
public class AADetailRequestDTO extends RequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	
	@XmlElement(name = "partyId",required=true)
	private String partyId;
	@XmlElement(name = "camType",required=true)
	private String camType;
	@XmlElement(name = "camNumber",required=true)
	private String camNumber;
	@XmlElement(name = "camDate",required=true)
	private String camDate;
	@XmlElement(name = "ramRating",required=true)
	private String ramRating;
	@XmlElement(name = "ramRatingYear",required=true)
	private String ramRatingYear;
	@XmlElement(name = "ratingType",required=true)
	private String ratingType;
	@XmlElement(name = "expiryDate",required=true)
	private String expiryDate;
	@XmlElement(name = "totalSactionedAmount",required=true)
	private String totalSactionedAmount;
	@XmlElement(name = "relationshipMgr",required=true)
	private String relationshipMgr;
	@XmlElement(name = "branch",required=true)
	private String branch;
	@XmlElement(name = "fullyCashCollateral",required=true)
	private String fullyCashCollateral;
	@XmlElement(name = "committeApproval",required=true)
	private String committeApproval;
	@XmlElement(name = "boardApproval",required=true)
	private String boardApproval;
	@XmlElement(name = "creditApproval1",required=true)
    private String creditApproval1;
	@XmlElement(name = "creditApproval2",required=true)
	private String creditApproval2;
	@XmlElement(name = "creditApproval3",required=true)
	private String creditApproval3;
	@XmlElement(name = "creditApproval4",required=true)
	private String creditApproval4;
	@XmlElement(name = "creditApproval5",required=true)
	private String creditApproval5;
	@XmlElement(name = "RBIAssetClassification",required=true)
	private String RBIAssetClassification;
	@XmlElement(name = "assetClassification",required=true)
	private String assetClassification;
	@XmlElement(name = "ramRatingFinalizationDate",required=true)
	private String ramRatingFinalizationDate;
	@XmlElement(name = "isSpecialApprGridForCustBelowHDB8",required=true)
	private String isSpecialApprGridForCustBelowHDB8;
	@XmlElement(name = "isSingleBorrowerPrudCeiling",required=true)
	private String isSingleBorrowerPrudCeiling;
	@XmlElement(name = "rbiApprovalForSingle",required=true)
	private String rbiApprovalForSingle;
	@XmlElement(name = "detailsOfRbiApprovalForSingle",required=true)
	private String detailsOfRbiApprovalForSingle;
	@XmlElement(name = "isGroupBorrowerPrudCeiling",required=true)
	private String isGroupBorrowerPrudCeiling;
	@XmlElement(name = "rbiApprovalForGroup",required=true)
	private String rbiApprovalForGroup;
	@XmlElement(name = "detailsOfRbiApprovalForGroup",required=true)
	private String detailsOfRbiApprovalForGroup;
	@XmlElement(name = "otherCovenantDetailsList",required=true)
	private List<OtherCovenantDetailsListRequestDTO> otherCovenantDetailsList;
	@XmlTransient
	private ActionErrors errors;
	@XmlTransient
	private String event;
	
	
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getCamType() {
		return camType;
	}
	public void setCamType(String camType) {
		this.camType = camType;
	}
	public String getCamNumber() {
		return camNumber;
	}
	public void setCamNumber(String camNumber) {
		this.camNumber = camNumber;
	}
	public String getCamDate() {
		return camDate;
	}
	public void setCamDate(String camDate) {
		this.camDate = camDate;
	}
	public String getRamRating() {
		return ramRating;
	}
	public void setRamRating(String ramRating) {
		this.ramRating = ramRating;
	}
	public String getRamRatingYear() {
		return ramRatingYear;
	}
	public void setRamRatingYear(String ramRatingYear) {
		this.ramRatingYear = ramRatingYear;
	}
	public String getRatingType() {
		return ratingType;
	}
	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getTotalSactionedAmount() {
		return totalSactionedAmount;
	}
	public void setTotalSactionedAmount(String totalSactionedAmount) {
		this.totalSactionedAmount = totalSactionedAmount;
	}
	public String getRelationshipMgr() {
		return relationshipMgr;
	}
	public void setRelationshipMgr(String relationshipMgr) {
		this.relationshipMgr = relationshipMgr;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getFullyCashCollateral() {
		return fullyCashCollateral;
	}
	public void setFullyCashCollateral(String fullyCashCollateral) {
		this.fullyCashCollateral = fullyCashCollateral;
	}
	public String getCommitteApproval() {
		return committeApproval;
	}
	public void setCommitteApproval(String committeApproval) {
		this.committeApproval = committeApproval;
	}
	public String getBoardApproval() {
		return boardApproval;
	}
	public void setBoardApproval(String boardApproval) {
		this.boardApproval = boardApproval;
	}
	public String getCreditApproval1() {
		return creditApproval1;
	}
	public void setCreditApproval1(String creditApproval1) {
		this.creditApproval1 = creditApproval1;
	}
	public String getCreditApproval2() {
		return creditApproval2;
	}
	public void setCreditApproval2(String creditApproval2) {
		this.creditApproval2 = creditApproval2;
	}
	public String getCreditApproval3() {
		return creditApproval3;
	}
	public void setCreditApproval3(String creditApproval3) {
		this.creditApproval3 = creditApproval3;
	}
	public String getCreditApproval4() {
		return creditApproval4;
	}
	public void setCreditApproval4(String creditApproval4) {
		this.creditApproval4 = creditApproval4;
	}
	public String getCreditApproval5() {
		return creditApproval5;
	}
	public void setCreditApproval5(String creditApproval5) {
		this.creditApproval5 = creditApproval5;
	}
	public String getRBIAssetClassification() {
		return RBIAssetClassification;
	}
	public void setRBIAssetClassification(String assetClassification) {
		RBIAssetClassification = assetClassification;
	}
	public String getAssetClassification() {
		return assetClassification;
	}
	public void setAssetClassification(String assetClassification) {
		this.assetClassification = assetClassification;
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
	
	public String getRamRatingFinalizationDate() {
		return ramRatingFinalizationDate;
	}
	public void setRamRatingFinalizationDate(String ramRatingFinalizationDate) {
		this.ramRatingFinalizationDate = ramRatingFinalizationDate;
	}
	public String getIsSpecialApprGridForCustBelowHDB8() {
		return isSpecialApprGridForCustBelowHDB8;
	}
	public void setIsSpecialApprGridForCustBelowHDB8(String isSpecialApprGridForCustBelowHDB8) {
		this.isSpecialApprGridForCustBelowHDB8 = isSpecialApprGridForCustBelowHDB8;
	}
	public String getIsSingleBorrowerPrudCeiling() {
		return isSingleBorrowerPrudCeiling;
	}
	public void setIsSingleBorrowerPrudCeiling(String isSingleBorrowerPrudCeiling) {
		this.isSingleBorrowerPrudCeiling = isSingleBorrowerPrudCeiling;
	}
	public String getRbiApprovalForSingle() {
		return rbiApprovalForSingle;
	}
	public void setRbiApprovalForSingle(String rbiApprovalForSingle) {
		this.rbiApprovalForSingle = rbiApprovalForSingle;
	}
	public String getDetailsOfRbiApprovalForSingle() {
		return detailsOfRbiApprovalForSingle;
	}
	public void setDetailsOfRbiApprovalForSingle(String detailsOfRbiApprovalForSingle) {
		this.detailsOfRbiApprovalForSingle = detailsOfRbiApprovalForSingle;
	}
	public String getIsGroupBorrowerPrudCeiling() {
		return isGroupBorrowerPrudCeiling;
	}
	public void setIsGroupBorrowerPrudCeiling(String isGroupBorrowerPrudCeiling) {
		this.isGroupBorrowerPrudCeiling = isGroupBorrowerPrudCeiling;
	}
	public String getRbiApprovalForGroup() {
		return rbiApprovalForGroup;
	}
	public void setRbiApprovalForGroup(String rbiApprovalForGroup) {
		this.rbiApprovalForGroup = rbiApprovalForGroup;
	}
	public String getDetailsOfRbiApprovalForGroup() {
		return detailsOfRbiApprovalForGroup;
	}
	public void setDetailsOfRbiApprovalForGroup(String detailsOfRbiApprovalForGroup) {
		this.detailsOfRbiApprovalForGroup = detailsOfRbiApprovalForGroup;
	}
	public List<OtherCovenantDetailsListRequestDTO> getOtherCovenantDetailsList() {
		return otherCovenantDetailsList;
	}
	public void setOtherCovenantDetailsList(List<OtherCovenantDetailsListRequestDTO> otherCovenantDetailsList) {
		this.otherCovenantDetailsList = otherCovenantDetailsList;
	}	

}