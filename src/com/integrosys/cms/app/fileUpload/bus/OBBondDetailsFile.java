package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;

public class OBBondDetailsFile extends OBCommonFile {

	private String partyId;
	private String sourceSecurityId;
	private String securitySubType;
	private String bondCode;
	private String noOfUnits;
	private String interest;
	private Date uploadDate;
	private String uploadedBy;
	
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getSourceSecurityId() {
		return sourceSecurityId;
	}
	public void setSourceSecurityId(String sourceSecurityId) {
		this.sourceSecurityId = sourceSecurityId;
	}
	public String getSecuritySubType() {
		return securitySubType;
	}
	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}
	public String getBondCode() {
		return bondCode;
	}
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}
	public String getNoOfUnits() {
		return noOfUnits;
	}
	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
}