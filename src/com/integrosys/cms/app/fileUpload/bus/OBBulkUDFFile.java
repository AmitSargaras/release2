package com.integrosys.cms.app.fileUpload.bus;

public class OBBulkUDFFile extends OBCommonFile{
	
	public OBBulkUDFFile() {
		super();
	}
	
	private String typeOfUDF;
	private String partyID;
	private String camNo;
	private String systemId;
	private String lineNumber;
	private String serialNumber;
	private String liabBranch;
	private String udfFieldSequence;
	private String udfFieldName;
	private String udfFieldValue;
	
	public String getTypeOfUDF() {
		return typeOfUDF;
	}
	public void setTypeOfUDF(String typeOfUDF) {
		this.typeOfUDF = typeOfUDF;
	}
	public String getPartyID() {
		return partyID;
	}
	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}
	public String getCamNo() {
		return camNo;
	}
	public void setCamNo(String camNo) {
		this.camNo = camNo;
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
	public String getUdfFieldSequence() {
		return udfFieldSequence;
	}
	public void setUdfFieldSequence(String udfFieldSequence) {
		this.udfFieldSequence = udfFieldSequence;
	}
	public String getUdfFieldName() {
		return udfFieldName;
	}
	public void setUdfFieldName(String udfFieldName) {
		this.udfFieldName = udfFieldName;
	}
	public String getUdfFieldValue() {
		return udfFieldValue;
	}
	public void setUdfFieldValue(String udfFieldValue) {
		this.udfFieldValue = udfFieldValue;
	}

}
