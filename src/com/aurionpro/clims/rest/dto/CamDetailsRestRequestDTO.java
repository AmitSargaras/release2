package com.aurionpro.clims.rest.dto;

import java.util.List;

import org.apache.struts.action.ActionErrors;

public class CamDetailsRestRequestDTO{

	private String partyId;
	private String camType;
	private String camNumber;
	private String camDate;
	private String ramRating;
	private String ramRatingYear;
	private String ratingType;
	private String ramRatingFinalizationDate;
	private String expiryDate;
	private String extendedNextReviewDate;
	private String noOfTimesExtended;
	private String totalSactionedAmount;
	private String relationshipMgr;
	private String branch;
	private String fullyCashCollateral;
	private String totalSanctionedAmountFacLevel;
	private String committeApproval;
	private String boardApproval;
	private String creditApproval1;
	private String creditApproval2;
	private String creditApproval3;
	private String creditApproval4;
	private String creditApproval5;
	private String RBIAssetClassification;
	private String assetClassification;
	private String isSpecialApprGridForCustBelowHDB8;
	private String isNonCooperativeBorrowers;
	private String isDirectorAsNonCooperativeForOther;
	private String nameOfDirectorsAndCompany;	
	private String isSingleBorrowerPrudCeiling;
	private String rbiApprovalForSingle;
	private String detailsOfRbiApprovalForSingle;
	private String isGroupBorrowerPrudCeiling;
	private String rbiApprovalForGroup;
	private String detailsOfRbiApprovalForGroup;
	private String docRemarks;
	private List<UdfRestRequestDTO> udfList;
	private String event;
	private ActionErrors errors;
	
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
	public String getRamRatingFinalizationDate() {
		return ramRatingFinalizationDate;
	}
	public void setRamRatingFinalizationDate(String ramRatingFinalizationDate) {
		this.ramRatingFinalizationDate = ramRatingFinalizationDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getExtendedNextReviewDate() {
		return extendedNextReviewDate;
	}
	public void setExtendedNextReviewDate(String extendedNextReviewDate) {
		this.extendedNextReviewDate = extendedNextReviewDate;
	}
	public String getNoOfTimesExtended() {
		return noOfTimesExtended;
	}
	public void setNoOfTimesExtended(String noOfTimesExtended) {
		this.noOfTimesExtended = noOfTimesExtended;
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
	public String getTotalSanctionedAmountFacLevel() {
		return totalSanctionedAmountFacLevel;
	}
	public void setTotalSanctionedAmountFacLevel(String totalSanctionedAmountFacLevel) {
		this.totalSanctionedAmountFacLevel = totalSanctionedAmountFacLevel;
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
	public void setRBIAssetClassification(String rBIAssetClassification) {
		RBIAssetClassification = rBIAssetClassification;
	}
	public String getAssetClassification() {
		return assetClassification;
	}
	public void setAssetClassification(String assetClassification) {
		this.assetClassification = assetClassification;
	}
	public String getIsSpecialApprGridForCustBelowHDB8() {
		return isSpecialApprGridForCustBelowHDB8;
	}
	public void setIsSpecialApprGridForCustBelowHDB8(String isSpecialApprGridForCustBelowHDB8) {
		this.isSpecialApprGridForCustBelowHDB8 = isSpecialApprGridForCustBelowHDB8;
	}
	public String getIsNonCooperativeBorrowers() {
		return isNonCooperativeBorrowers;
	}
	public void setIsNonCooperativeBorrowers(String isNonCooperativeBorrowers) {
		this.isNonCooperativeBorrowers = isNonCooperativeBorrowers;
	}
	public String getIsDirectorAsNonCooperativeForOther() {
		return isDirectorAsNonCooperativeForOther;
	}
	public void setIsDirectorAsNonCooperativeForOther(String isDirectorAsNonCooperativeForOther) {
		this.isDirectorAsNonCooperativeForOther = isDirectorAsNonCooperativeForOther;
	}
	public String getNameOfDirectorsAndCompany() {
		return nameOfDirectorsAndCompany;
	}
	public void setNameOfDirectorsAndCompany(String nameOfDirectorsAndCompany) {
		this.nameOfDirectorsAndCompany = nameOfDirectorsAndCompany;
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
	public String getDocRemarks() {
		return docRemarks;
	}
	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public List<UdfRestRequestDTO> getUdfList() {
		return udfList;
	}
	public void setUdfList(List<UdfRestRequestDTO> udfList) {
		this.udfList = udfList;
	}
	public ActionErrors getErrors() {
		return errors;
	}
	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

}
