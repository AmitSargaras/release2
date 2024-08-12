package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;

public class OBMFSchemaDetailsFile extends OBCommonFile {

	private String partyId;
	private String sourceSecurityId;
	private String securitySubType;
	private String schemaCode;
	private String noOfUnits;
	private String certificateNo;
	private String issuerName;
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
	public String getSchemaCode() {
		return schemaCode;
	}
	public void setSchemaCode(String schemaCode) {
		this.schemaCode = schemaCode;
	}
	public String getNoOfUnits() {
		return noOfUnits;
	}
	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
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
