package com.integrosys.cms.ui.createfacupload;

import java.util.Date;


public class OBCreatefacilitylineFile {

	public OBCreatefacilitylineFile()
	{
		super();
	}
	
	
	private Long  id;
	private int  tempCounter;
	private String  fileName;
	private String partyId;
	private String facilityCategory;
	private String facilityName;
	private String dummyRefId;
	private String system;
	private String grade;
	private String currency;
	private String isReleased;
	private String isAdhoc;
	private String adhoclimitAmt;
	private String sanctionedAmt;
	//private String sanctionedAmtInr;
	private String releasableAmt;
	//private String releasedAmt;
	
	private String systemID;
	private String lineNo;
	private String serialNo;
	private String liabBranch;
	private String releaseAmount;
	//private Date limitStartDate;
	private String limitStartDate;
	private String available;
	private String revolvingLine;
	private String sendToFile;
	//private Date limitExpiryDate;
	private String limitExpiryDate;
	private String freeze;
	private String segment1;
	
	private String isCapitalMarketExpo;
	private String isRealEstateExpo;
	private String ruleId;
	private String pslFlag;
	private String pslValue;
	private String uncondCancelComm;
	private String interestRate;
	private String rateValue;
	private String realEsExpoValue;
	private String commercialRealEstate;
	private String limitTenor;
	private String remark;
	private String uploadedBy;
	private String status;
	private String reason;
	
	private String totalReleasedAmt;

	private Date uploadDate;
	private String dbLineNo;

	public int getTempCounter() {
		return tempCounter;
	}
	public void setTempCounter(int tempCounter) {
		this.tempCounter = tempCounter;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getFacilityCategory() {
		return facilityCategory;
	}
	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getIsReleased() {
		return isReleased;
	}
	public void setIsReleased(String isReleased) {
		this.isReleased = isReleased;
	}
	public String getIsAdhoc() {
		return isAdhoc;
	}
	public void setIsAdhoc(String isAdhoc) {
		this.isAdhoc = isAdhoc;
	}
	public String getAdhoclimitAmt() {
		return adhoclimitAmt;
	}
	public void setAdhoclimitAmt(String adhoclimitAmt) {
		this.adhoclimitAmt = adhoclimitAmt;
	}
	public String getSanctionedAmt() {
		return sanctionedAmt;
	}
	public void setSanctionedAmt(String sanctionedAmt) {
		this.sanctionedAmt = sanctionedAmt;
	}
/*	public String getSanctionedAmtInr() {
		return sanctionedAmtInr;
	}
	public void setSanctionedAmtInr(String sanctionedAmtInr) {
		this.sanctionedAmtInr = sanctionedAmtInr;
	}*/
	public String getReleasableAmt() {
		return releasableAmt;
	}
	public void setReleasableAmt(String releasableAmt) {
		this.releasableAmt = releasableAmt;
	}
/*	public String getReleasedAmt() {
		return releasedAmt;
	}
	public void setReleasedAmt(String releasedAmt) {
		this.releasedAmt = releasedAmt;
	}*/
	public String getSystemID() {
		return systemID;
	}
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getLiabBranch() {
		return liabBranch;
	}
	public void setLiabBranch(String liabBranch) {
		this.liabBranch = liabBranch;
	}
	public String getReleaseAmount() {
		return releaseAmount;
	}
	public void setReleaseAmount(String releaseAmount) {
		this.releaseAmount = releaseAmount;
	}
	/*public Date getLimitStartDate() {
		return limitStartDate;
	}
	public void setLimitStartDate(Date limitStartDate) {
		this.limitStartDate = limitStartDate;
	}*/
	
	public String getLimitStartDate() {
		return limitStartDate;
	}
	public void setLimitStartDate(String limitStartDate) {
		this.limitStartDate = limitStartDate;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getRevolvingLine() {
		return revolvingLine;
	}
	public void setRevolvingLine(String revolvingLine) {
		this.revolvingLine = revolvingLine;
	}
	public String getSendToFile() {
		return sendToFile;
	}
	public void setSendToFile(String sendToFile) {
		this.sendToFile = sendToFile;
	}
	/*public Date getLimitExpiryDate() {
		return limitExpiryDate;
	}
	public void setLimitExpiryDate(Date limitExpiryDate) {
		this.limitExpiryDate = limitExpiryDate;
	}*/
	
	public String getLimitExpiryDate() {
		return limitExpiryDate;
	}
	public void setLimitExpiryDate(String limitExpiryDate) {
		this.limitExpiryDate = limitExpiryDate;
	}
	public String getFreeze() {
		return freeze;
	}
	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
	public String getSegment1() {
		return segment1;
	}
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}
	public String getIsCapitalMarketExpo() {
		return isCapitalMarketExpo;
	}
	public void setIsCapitalMarketExpo(String isCapitalMarketExpo) {
		this.isCapitalMarketExpo = isCapitalMarketExpo;
	}
	public String getIsRealEstateExpo() {
		return isRealEstateExpo;
	}
	public void setIsRealEstateExpo(String isRealEstateExpo) {
		this.isRealEstateExpo = isRealEstateExpo;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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
	public String getUncondCancelComm() {
		return uncondCancelComm;
	}
	public void setUncondCancelComm(String uncondCancelComm) {
		this.uncondCancelComm = uncondCancelComm;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getRateValue() {
		return rateValue;
	}
	public void setRateValue(String rateValue) {
		this.rateValue = rateValue;
	}
	public String getRealEsExpoValue() {
		return realEsExpoValue;
	}
	public void setRealEsExpoValue(String realEsExpoValue) {
		this.realEsExpoValue = realEsExpoValue;
	}
	public String getCommercialRealEstate() {
		return commercialRealEstate;
	}
	public void setCommercialRealEstate(String commercialRealEstate) {
		this.commercialRealEstate = commercialRealEstate;
	}
	public String getLimitTenor() {
		return limitTenor;
	}
	public void setLimitTenor(String limitTenor) {
		this.limitTenor = limitTenor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDummyRefId() {
		return dummyRefId;
	}
	public void setDummyRefId(String dummyRefId) {
		this.dummyRefId = dummyRefId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTotalReleasedAmt() {
		return totalReleasedAmt;
	}
	public void setTotalReleasedAmt(String totalReleasedAmt) {
		this.totalReleasedAmt = totalReleasedAmt;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public String getDbLineNo() {
		return dbLineNo;
	}
	public void setDbLineNo(String dbLineNo) {
		this.dbLineNo = dbLineNo;
	}

	private String adhocLine;

	public String getAdhocLine() {
		return adhocLine;
	}
	public void setAdhocLine(String adhocLine) {
		this.adhocLine = adhocLine;
	}
	

}
