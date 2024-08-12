/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.systemBankBranch;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for System Bank Branch Master
 */

public class MaintainSystemBankBranchForm extends TrxContextForm implements Serializable {

	private String systemBankCode;
	private String systemBankName;
	private String address;
	private String cityTown;
	private String state;
	private String country;
	private String region;
	private String contactNumber;
	private String contactMail;
	private String systemBankId;
 
	private String versionTime;

	private String lastUpdateDate;
	private String disableForSelection;
	
	private String systemBankBranchCode;
	private String systemBankBranchName;
	private String creationDate;
	
	
	private String createBy;
	//private String lastUpdateDate;
	private String lastUpdateBy;
	
	
	
	private String status;
	private String deprecated;
    private String masterId;
	private String id;
	private String rbiCode;
	private String faxNumber;
	private String isHub;
	private String linkedHub;
	private String isVault;
	private String custodian1;
	private String custodian2;
	private String operationName;
	private List hubValueList;
	
	private FormFile fileUpload;
	private String cpsId;
	private String pincode;
	private String statePincodeString;
   
	public List getHubValueList() {
		return hubValueList;
	}
	public void setHubValueList(List hubValueList) {
		this.hubValueList = hubValueList;
	}
	public String getRbiCode() {
		return rbiCode;
	}
	public void setRbiCode(String rbiCode) {
		this.rbiCode = rbiCode;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
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
	public String[][] getMapper() {
		String[][] input = {  { "systemBankBranchObj", SYSTEM_BANK_BRANCH_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String SYSTEM_BANK_BRANCH_MAPPER = "com.integrosys.cms.ui.systemBankBranch.SystemBankBranchMapper";


	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
	public String getSystemBankCode() {
		return systemBankCode;
	}
	public void setSystemBankCode(String systemBankCode) {
		this.systemBankCode = systemBankCode;
	}
	public String getSystemBankName() {
		return systemBankName;
	}
	public void setSystemBankName(String systemBankName) {
		this.systemBankName = systemBankName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityTown() {
		return cityTown;
	}
	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactMail() {
		return contactMail;
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
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getDisableForSelection() {
		return disableForSelection;
	}
	public void setDisableForSelection(String disableForSelection) {
		this.disableForSelection = disableForSelection;
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
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	/**
	 * @return the cpsId
	 */
	public String getCpsId() {
		return cpsId;
	}
	/**
	 * @param cpsId the cpsId to set
	 */
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getStatePincodeString() {
		return statePincodeString;
	}
	public void setStatePincodeString(String statePincodeString) {
		this.statePincodeString = statePincodeString;
	}

}
