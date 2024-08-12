package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;


public class OBReleaselinedetailsFile extends OBCommonFile{

	public OBReleaselinedetailsFile()
	{
		super();
	}
	
	private String systemID;
	private String lineNo;
	private String serialNo;
	private String liabBranch;
	private String releaseAmount;
	private String sourceRefNo;
	private String expDate;
	private Date dateOfReset;
	private String facilityID;
	private String pslFlag;
	private String pslValue;
	private String ruleID;
	
	
	
	
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
	public String getRuleID() {
		return ruleID;
	}
	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}
	public String getFacilityID() {
		return facilityID;
	}
	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}
	public Date getDateOfReset() {
		return dateOfReset;
	}
	public void setDateOfReset(Date dateOfReset) {
		this.dateOfReset = dateOfReset;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getSourceRefNo() {
		return sourceRefNo;
	}
	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
	}
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
	
	

//	private String securityID;
//	private String cerTrnRefNo;
//	private String cerSecIntID;
//	private String cerAssetID;
//	private Date registrationDate;
//	
//	public String getSecurityID() {
//		return securityID;
//	}
//	public void setSecurityID(String securityID) {
//		this.securityID = securityID;
//	}
//	public String getCerTrnRefNo() {
//		return cerTrnRefNo;
//	}
//	public void setCerTrnRefNo(String cerTrnRefNo) {
//		this.cerTrnRefNo = cerTrnRefNo;
//	}
//	public String getCerSecIntID() {
//		return cerSecIntID;
//	}
//	public void setCerSecIntID(String cerSecIntID) {
//		this.cerSecIntID = cerSecIntID;
//	}
//	public String getCerAssetID() {
//		return cerAssetID;
//	}
//	public void setCerAssetID(String cerAssetID) {
//		this.cerAssetID = cerAssetID;
//	}
//	public Date getRegistrationDate() {
//		return registrationDate;
//	}
//	public void setRegistrationDate(Date registrationDate) {
//		this.registrationDate = registrationDate;
//	}
	
	
}
