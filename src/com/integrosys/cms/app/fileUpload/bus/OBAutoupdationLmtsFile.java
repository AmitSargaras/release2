package com.integrosys.cms.app.fileUpload.bus;

import java.sql.Date;

public class OBAutoupdationLmtsFile extends OBCommonFile{

	public OBAutoupdationLmtsFile()
	{
		super();
	}

	private String securityID;
	private String partyId;
	private String partyName;
	private String facilityId;
	private String facilityName;
	private String lineNo;
	private String serialNo;
	private String liabBranch;
	private String securitySubtype;
	private String lineStatus;
	private String xrefId;
	private String facilitySystemId;
	private String facilitySystemName;
	private String dueDateMax;
	private String docCode;
	
	
	
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getDueDateMax() {
		return dueDateMax;
	}
	public void setDueDateMax(String dueDateMax) {
		this.dueDateMax = dueDateMax;
	}
	public String getFacilitySystemId() {
		return facilitySystemId;
	}
	public void setFacilitySystemId(String facilitySystemId) {
		this.facilitySystemId = facilitySystemId;
	}
	public String getFacilitySystemName() {
		return facilitySystemName;
	}
	public void setFacilitySystemName(String facilitySystemName) {
		this.facilitySystemName = facilitySystemName;
	}
	public String getXrefId() {
		return xrefId;
	}
	public void setXrefId(String xrefId) {
		this.xrefId = xrefId;
	}
	public String getLineStatus() {
		return lineStatus;
	}
	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}
	public String getSecurityID() {
		return securityID;
	}
	public void setSecurityID(String securityID) {
		this.securityID = securityID;
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
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
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
	public String getSecuritySubtype() {
		return securitySubtype;
	}
	public void setSecuritySubtype(String securitySubtype) {
		this.securitySubtype = securitySubtype;
	}
	
	
}
