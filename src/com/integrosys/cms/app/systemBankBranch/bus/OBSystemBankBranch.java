/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.systemBankBranch.bus;

//app

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBSystemBankBranch implements ISystemBankBranch {
	
	/**
	 * constructor
	 */
	public OBSystemBankBranch() {
		
	}
    private long id;
	private String systemBankBranchCode;
	private String systemBankBranchName;
	private String address;
//	private String cityTown;
//	private String state;
//	private String country;
//	private String region;
	private ICity cityTown;
	private IState state;
	private ICountry country;
	private IRegion region;
	private String contactNumber;
	private String contactMail;
	private long versionTime;
	private String systemBankId;
	private String status;
	private long masterId=0l;
	private String deprecated;
	private String code;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private ISystemBank systemBankCode;
	private String rbiCode;
	private String faxNumber;
	private String isHub;
	private String linkedHub;
	private String isVault;
	private String custodian1;
	private String custodian2;
	private FormFile fileUpload;
	private String cpsId;
	private String operationName;//Added by Anil for EOD migration 
	private String cpsCode;
	private String pincode;
	
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	public String getRbiCode() {
		return rbiCode;
	}
	public void setRbiCode(String rbiCode) {
		this.rbiCode = rbiCode;
	}
	
	public String getIsHub() {
		return isHub;
	}
	public void setIsHub(String isHub) {
		this.isHub = isHub;
	}
	public String getLinkedHub() {
		return linkedHub;
	}
	public void setLinkedHub(String linkedHub) {
		this.linkedHub = linkedHub;
	}
	public String getIsVault() {
		return isVault;
	}
	public void setIsVault(String isVault) {
		this.isVault = isVault;
	}
	public String getCustodian1() {
		return custodian1;
	}
	public void setCustodian1(String custodian1) {
		this.custodian1 = custodian1;
	}
	public String getCustodian2() {
		return custodian2;
	}
	public void setCustodian2(String custodian2) {
		this.custodian2 = custodian2;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
//	public String getCityTown() {
//		return cityTown;
//	}
//	public void setCityTown(String cityTown) {
//		this.cityTown = cityTown;
//	}
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}
//	public String getCountry() {
//		return country;
//	}
//	public void setCountry(String country) {
//		this.country = country;
//	}
//	public String getRegion() {
//		return region;
//	}
//	public void setRegion(String region) {
//		this.region = region;
//	}
	
	public String getContactMail() {
		return contactMail;
	}
	public ICity getCityTown() {
		return cityTown;
	}
	public void setCityTown(ICity cityTown) {
		this.cityTown = cityTown;
	}
	public IState getState() {
		return state;
	}
	public void setState(IState state) {
		this.state = state;
	}
	public ICountry getCountry() {
		return country;
	}
	public void setCountry(ICountry country) {
		this.country = country;
	}
	public IRegion getRegion() {
		return region;
	}
	public void setRegion(IRegion region) {
		this.region = region;
	}
	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}
	public String getSystemBankId() {
		return systemBankId;
	}
	public void setSystemBankId(String systemBankId) {
		this.systemBankId = systemBankId;
	}

	
	
	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
		
	}
	
	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public String getCode()
	{
		return code;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSystemBankBranchCode() {
		return systemBankBranchCode;
	}
	public void setSystemBankBranchCode(String systemBankBranchCode) {
		this.systemBankBranchCode = systemBankBranchCode;
	}
	public String getSystemBankBranchName() {
		return systemBankBranchName;
	}
	public void setSystemBankBranchName(String systemBankBranchName) {
		this.systemBankBranchName = systemBankBranchName;
	}
	public ISystemBank getSystemBankCode() {
		return systemBankCode;
	}
	public void setSystemBankCode(ISystemBank systemBankCode) {
		this.systemBankCode = systemBankCode;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getCpsCode() {
		return cpsCode;
	}
	public void setCpsCode(String cpsCode) {
		this.cpsCode = cpsCode;
	}
	
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	

	


	
	

//	public String toString() {
//		StringBuffer buf = new StringBuffer(getClass().getName());
//		buf.append("@").append(System.identityHashCode(this));
//
//		buf.append(" CIF [").append(customerReference).append("], ");
//		buf.append("Customer Name [").append(customerName).append("], ");
//		buf.append("Short Description [").append(description).append("], ");
//		buf.append("Expiry Date [").append(expiryDate).append("], ");
//		buf.append("Reminder Date [").append(dueDate).append("], ");
//		buf.append("Access Country [").append(allowedCountry).append("]");
//
//		return buf.toString();
//	}
}