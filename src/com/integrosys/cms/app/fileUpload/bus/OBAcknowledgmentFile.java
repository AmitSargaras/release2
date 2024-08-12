package com.integrosys.cms.app.fileUpload.bus;

import java.sql.Date;


public class OBAcknowledgmentFile extends OBCommonFile{

	public OBAcknowledgmentFile()
	{
		super();
	}

	private String securityID;
	private String cerTrnRefNo;
	private String cerSecIntID;
	private String cerAssetID;
	private Date registrationDate;
	
	public String getSecurityID() {
		return securityID;
	}
	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}
	public String getCerTrnRefNo() {
		return cerTrnRefNo;
	}
	public void setCerTrnRefNo(String cerTrnRefNo) {
		this.cerTrnRefNo = cerTrnRefNo;
	}
	public String getCerSecIntID() {
		return cerSecIntID;
	}
	public void setCerSecIntID(String cerSecIntID) {
		this.cerSecIntID = cerSecIntID;
	}
	public String getCerAssetID() {
		return cerAssetID;
	}
	public void setCerAssetID(String cerAssetID) {
		this.cerAssetID = cerAssetID;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
}
