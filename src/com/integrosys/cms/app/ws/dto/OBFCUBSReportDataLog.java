package com.integrosys.cms.app.ws.dto;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

public class OBFCUBSReportDataLog implements IFCUBSReportDataLog {
	
	public OBFCUBSReportDataLog() {}
	
    private long id;
    private String partyId;
	private String partyName;
	private String facilityName;
	private String facilityId;	
	private String facilityCategory;
	private String lineCode;
	private String serialNumber;
	private String typeOfCovenant;
	private String condition1;
	private String condition2;
	private String condition3;
	private String condition4;
	private String condition5;
	private String fileName;
	private String sourceRefNo;
	private String liabilityId;
	private String lineNo;
	private String serialNo;
	private String xrefId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityCategory() {
		return facilityCategory;
	}
	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	public String getTypeOfCovenant() {
		return typeOfCovenant;
	}
	public void setTypeOfCovenant(String typeOfCovenant) {
		this.typeOfCovenant = typeOfCovenant;
	}
	public String getCondition1() {
		return condition1;
	}
	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}
	public String getCondition2() {
		return condition2;
	}
	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}
	public String getCondition3() {
		return condition3;
	}
	public void setCondition3(String condition3) {
		this.condition3 = condition3;
	}
	public String getCondition4() {
		return condition4;
	}
	public void setCondition4(String condition4) {
		this.condition4 = condition4;
	}
	public String getCondition5() {
		return condition5;
	}
	public void setCondition5(String condition5) {
		this.condition5 = condition5;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLiabilityId() {
		return liabilityId;
	}
	public void setLiabilityId(String liabilityId) {
		this.liabilityId = liabilityId;
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
	public String getSourceRefNo() {
		return sourceRefNo;
	}
	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
	}
	public String getXrefId() {
		return xrefId;
	}
	public void setXrefId(String xrefId) {
		this.xrefId = xrefId;
	}
	
	
	
}
