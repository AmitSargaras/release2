package com.integrosys.cms.ui.bulkudfupdateupload;

import com.integrosys.cms.app.fileUpload.bus.OBCommonFile;

public class OBTempBulkUDFFileUpload extends OBCommonFile{
	
	public OBTempBulkUDFFileUpload()
	{
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
	private String valid;
	private String Remarks;
	//Setters Getter not created for below:
	private String CMS_LE_MAIN_PROFILE_ID;
	private String CMS_LSP_LMT_PROFILE_ID;
	private String SCI_LSP_SYS_XREF_ID;
	
	public String getCMS_LE_MAIN_PROFILE_ID() {
		return CMS_LE_MAIN_PROFILE_ID;
	}
	public void setCMS_LE_MAIN_PROFILE_ID(String cMS_LE_MAIN_PROFILE_ID) {
		CMS_LE_MAIN_PROFILE_ID = cMS_LE_MAIN_PROFILE_ID;
	}
	public String getCMS_LSP_LMT_PROFILE_ID() {
		return CMS_LSP_LMT_PROFILE_ID;
	}
	public void setCMS_LSP_LMT_PROFILE_ID(String cMS_LSP_LMT_PROFILE_ID) {
		CMS_LSP_LMT_PROFILE_ID = cMS_LSP_LMT_PROFILE_ID;
	}
	public String getSCI_LSP_SYS_XREF_ID() {
		return SCI_LSP_SYS_XREF_ID;
	}
	public void setSCI_LSP_SYS_XREF_ID(String sCI_LSP_SYS_XREF_ID) {
		SCI_LSP_SYS_XREF_ID = sCI_LSP_SYS_XREF_ID;
	}

	
	
	
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
	
	
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}


	

}
