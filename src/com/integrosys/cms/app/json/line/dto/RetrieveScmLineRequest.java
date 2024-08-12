package com.integrosys.cms.app.json.line.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RetrieveScmLineRequest {
	
	@JsonProperty("uniqueReferenceID")
	private String uniqueReferenceID;
	@JsonProperty("action")
	private String action;
	@JsonProperty("scmFlag")
	private String scmFlag;
	@JsonProperty("partyId")
	private String partyId;
	@JsonProperty("partyName")
	private String partyName;
	@JsonProperty("facilityName")
    private String facilityName;
	@JsonProperty("system")
	private String system;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("releaseFlag")
	private String releaseFlag;
	@JsonProperty("adhocFlag")
	private String adhocFlag;
	@JsonProperty("adhocLimitAmount")
	private String adhocLimitAmount;
	@JsonProperty("sanctionAmount")
	private String sanctionAmount;
	@JsonProperty("sublimitFlag")
	private String sublimitFlag;
	@JsonProperty("sanctionAmountINR")
	private String sanctionAmountINR;
	@JsonProperty("guarantee")
	private String guarantee;
	@JsonProperty("guaranteePartyName")
	private String guaranteePartyName;
	@JsonProperty("guaranteeliabilityId")
	private String guaranteeliabilityId;
	@JsonProperty("releaseableAmount")
	private String releaseableAmount;
	@JsonProperty("releasedAmount")
	private String releasedAmount;
	@JsonProperty("mainLineNumber")
	private String mainLineNumber;
	@JsonProperty("mainLinePartyId")
	private String mainLinePartyId;
	@JsonProperty("mainLinePartyName")
	private String mainLinePartyName;
	@JsonProperty("mainLineSystemID")
	private String mainLineSystemID;
	@JsonProperty("systemId")
	private String systemId;
	@JsonProperty("lineNumber")
	private String lineNumber;
	@JsonProperty("serialNumber")
	private String serialNumber;
	@JsonProperty("liabBranch")
	private String liabBranch;
	@JsonProperty("limitStartDate")
	private String limitStartDate;
	@JsonProperty("availableFlag")
	private String availableFlag;
	@JsonProperty("revolvingLine")
	private String revolvingLine;
	@JsonProperty("limitExpiryDate")
	private String limitExpiryDate;
	@JsonProperty("freezeFlag")
	private String freezeFlag;
	@JsonProperty("segment")
	private String segment;
	@JsonProperty("pslFlag")
	private String pslFlag;
	@JsonProperty("pslValue")
	private String pslValue;
	@JsonProperty("commitment")
	private String commitment;
	@JsonProperty("rateValue")
	private String rateValue;
	@JsonProperty("tenorDays")
	private String tenorDays;
	@JsonProperty("remarks")
	private String remarks;
	@JsonProperty("npa")
	private String npa;
	@JsonProperty("productAllowed")
	private String productAllowed;
	@JsonProperty("limitProfileId")
	private String limitProfileId;
	
	
	public String getLimitProfileId() {
		return limitProfileId;
	}
	public void setLimitProfileId(String limitProfileId) {
		this.limitProfileId = limitProfileId;
	}
	public String getUniqueReferenceID() {
		return uniqueReferenceID;
	}
	public void setUniqueReferenceID(String uniqueReferenceID) {
		this.uniqueReferenceID = uniqueReferenceID;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getScmFlag() {
		return scmFlag;
	}
	public void setScmFlag(String scmFlag) {
		this.scmFlag = scmFlag;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReleaseFlag() {
		return releaseFlag;
	}
	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}
	public String getAdhocFlag() {
		return adhocFlag;
	}
	public void setAdhocFlag(String adhocFlag) {
		this.adhocFlag = adhocFlag;
	}
	public String getAdhocLimitAmount() {
		return adhocLimitAmount;
	}
	public void setAdhocLimitAmount(String adhocLimitAmount) {
		this.adhocLimitAmount = adhocLimitAmount;
	}
	public String getSanctionAmount() {
		return sanctionAmount;
	}
	public void setSanctionAmount(String sanctionAmount) {
		this.sanctionAmount = sanctionAmount;
	}
	public String getSublimitFlag() {
		return sublimitFlag;
	}
	public void setSublimitFlag(String sublimitFlag) {
		this.sublimitFlag = sublimitFlag;
	}
	public String getSanctionAmountINR() {
		return sanctionAmountINR;
	}
	public void setSanctionAmountINR(String sanctionAmountINR) {
		this.sanctionAmountINR = sanctionAmountINR;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	public String getGuaranteePartyName() {
		return guaranteePartyName;
	}
	public void setGuaranteePartyName(String guaranteePartyName) {
		this.guaranteePartyName = guaranteePartyName;
	}
	public String getGuaranteeliabilityId() {
		return guaranteeliabilityId;
	}
	public void setGuaranteeliabilityId(String guaranteeliabilityId) {
		this.guaranteeliabilityId = guaranteeliabilityId;
	}
	public String getReleaseableAmount() {
		return releaseableAmount;
	}
	public void setReleaseableAmount(String releaseableAmount) {
		this.releaseableAmount = releaseableAmount;
	}
	public String getReleasedAmount() {
		return releasedAmount;
	}
	public void setReleasedAmount(String releasedAmount) {
		this.releasedAmount = releasedAmount;
	}
	public String getMainLineNumber() {
		return mainLineNumber;
	}
	public void setMainLineNumber(String mainLineNumber) {
		this.mainLineNumber = mainLineNumber;
	}
	public String getMainLinePartyId() {
		return mainLinePartyId;
	}
	public void setMainLinePartyId(String mainLinePartyId) {
		this.mainLinePartyId = mainLinePartyId;
	}
	public String getMainLinePartyName() {
		return mainLinePartyName;
	}
	public void setMainLinePartyName(String mainLinePartyName) {
		this.mainLinePartyName = mainLinePartyName;
	}
	public String getMainLineSystemID() {
		return mainLineSystemID;
	}
	public void setMainLineSystemID(String mainLineSystemID) {
		this.mainLineSystemID = mainLineSystemID;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getLiabBranch() {
		return liabBranch;
	}
	public void setLiabBranch(String liabBranch) {
		this.liabBranch = liabBranch;
	}
	public String getLimitStartDate() {
		return limitStartDate;
	}
	public void setLimitStartDate(String limitStartDate) {
		this.limitStartDate = limitStartDate;
	}
	public String getAvailableFlag() {
		return availableFlag;
	}
	public void setAvailableFlag(String availableFlag) {
		this.availableFlag = availableFlag;
	}
	public String getRevolvingLine() {
		return revolvingLine;
	}
	public void setRevolvingLine(String revolvingLine) {
		this.revolvingLine = revolvingLine;
	}
	public String getLimitExpiryDate() {
		return limitExpiryDate;
	}
	public void setLimitExpiryDate(String limitExpiryDate) {
		this.limitExpiryDate = limitExpiryDate;
	}
	public String getFreezeFlag() {
		return freezeFlag;
	}
	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getPslFlag() {
		return pslFlag;
	}
	public void setPslFlag(String pslFlag) {
		this.pslFlag = pslFlag;
	}
	public String getPslValue() {
		return pslValue;
	}
	public void setPslValue(String pslValue) {
		this.pslValue = pslValue;
	}
	public String getCommitment() {
		return commitment;
	}
	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}
	public String getRateValue() {
		return rateValue;
	}
	public void setRateValue(String rateValue) {
		this.rateValue = rateValue;
	}
	public String getTenorDays() {
		return tenorDays;
	}
	public void setTenorDays(String tenorDays) {
		this.tenorDays = tenorDays;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNpa() {
		return npa;
	}
	public void setNpa(String npa) {
		this.npa = npa;
	}
	public String getProductAllowed() {
		return productAllowed;
	}
	public void setProductAllowed(String productAllowed) {
		this.productAllowed = productAllowed;
	}
}
