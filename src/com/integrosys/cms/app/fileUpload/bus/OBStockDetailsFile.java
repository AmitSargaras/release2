package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;

public class OBStockDetailsFile extends OBCommonFile {

	private String partyId;
	private String sourceSecurityId;
	private String securitySubType;
	private String nameOfStockExchange;
	private String scriptCode;
	private String noOfUnits;
	private String issuerIdType;
	private String nominalValue;
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
	public String getNameOfStockExchange() {
		return nameOfStockExchange;
	}
	public void setNameOfStockExchange(String nameOfStockExchange) {
		this.nameOfStockExchange = nameOfStockExchange;
	}
	public String getScriptCode() {
		return scriptCode;
	}
	public void setScriptCode(String scriptCode) {
		this.scriptCode = scriptCode;
	}
	public String getNoOfUnits() {
		return noOfUnits;
	}
	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
	public String getIssuerIdType() {
		return issuerIdType;
	}
	public void setIssuerIdType(String issuerIdType) {
		this.issuerIdType = issuerIdType;
	}
	public String getNominalValue() {
		return nominalValue;
	}
	public void setNominalValue(String nominalValue) {
		this.nominalValue = nominalValue;
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
